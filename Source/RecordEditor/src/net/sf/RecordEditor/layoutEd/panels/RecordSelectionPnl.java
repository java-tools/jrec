package net.sf.RecordEditor.layoutEd.panels;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import net.sf.RecordEditor.layoutEd.Record.RecordSelectionJTbl;
import net.sf.RecordEditor.re.db.Record.ChildRecordsDB;
import net.sf.RecordEditor.re.db.Record.ChildRecordsRec;
import net.sf.RecordEditor.re.db.Record.RecordSelectionDB;
import net.sf.RecordEditor.re.db.Record.RecordSelectionRec;
import net.sf.RecordEditor.utils.common.AbsConnection;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.jdbc.DBtableModel;
import net.sf.RecordEditor.utils.lang.ReAbstractAction;
import net.sf.RecordEditor.utils.lang.ReOptionDialog;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.Combo.ComboOption;

@SuppressWarnings("serial")
public class RecordSelectionPnl implements IntRecordSelectPnl {
	public final BaseHelpPanel panel = new BaseHelpPanel();

	private TableUpdatePnl<RecordSelectionRec> updatePnl;
	private RecordSelectionJTbl recordSelectionTbl;
	private RecordSelectionDB db;
	protected final DBtableModel<RecordSelectionRec> selectionMdl;
	protected final JCheckBox defaultSelectionChk = new JCheckBox();
	private JButton copyFromBtn = new JButton(new ReAbstractAction("Copy Selections") {

		@Override
		public void actionPerformed(ActionEvent e) {
			copyFromDialog();
		}
	});

	//private final String databaseName;
	private final int dbIdx, recordId;
	private final ChildRecordsRec parentRec;

	private final MouseAdapter mouseListner = new MouseAdapter() {

	      /**
         * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
         */
		public void mousePressed(MouseEvent m) {
			int col = recordSelectionTbl.columnAtPoint(m.getPoint());
			int row = recordSelectionTbl.rowAtPoint(m.getPoint());
			int tblCol = recordSelectionTbl.getColumnModel().getColumn(col).getModelIndex();

			if (tblCol < 2) {
				RecordSelectionRec rec = selectionMdl.getRecord(row);

				rec.setBooleanOperator(1 - rec.getBooleanOperator());

				selectionMdl.fireTableRowsUpdated(row, row);
			}
		}
	};


	public RecordSelectionPnl(
            final int dbConnectionIdx,
            final int recordIdentifier,
            final ChildRecordsRec parent) {

		//databaseName = dbName;
		dbIdx = dbConnectionIdx;
		parentRec = parent;
		recordId = recordIdentifier;

		db = new RecordSelectionDB();
		db.setConnection(new ReConnection(dbIdx));
		db.setParams(recordId, parentRec.getChildKey());
		selectionMdl = new RecSelMdl(db);

		init_100_SetupFields();
		init_200_LayoutScreen();

		init_300_Listners();
	}

	private void init_100_SetupFields() {

		defaultSelectionChk.setSelected(parentRec.isDefaultRecord());

		selectionMdl.setCellEditable(true);

		recordSelectionTbl = new RecordSelectionJTbl(selectionMdl, dbIdx, parentRec);
	}

	private void init_200_LayoutScreen() {


		panel.setHelpURL(Common.formatHelpURL(Common.HELP_LAYOUT_RECSEL));

		panel.addLine("Default Record", defaultSelectionChk, copyFromBtn)
		   .setGap(BasePanel.GAP1);

		updatePnl = new TableUpdatePnl<RecordSelectionRec>(panel, null);

		panel.addComponent(1, 5, BasePanel.PREFERRED, BasePanel.GAP, BasePanel.FULL, BasePanel.FULL, updatePnl);

		panel.addComponent(1, 5, BasePanel.FILL, BasePanel.GAP,
				BasePanel.FULL, BasePanel.FULL,
				recordSelectionTbl);

		updatePnl.setTableDtls(selectionMdl, recordSelectionTbl, new RecordSelectionRec());
	}


	private void init_300_Listners() {
		recordSelectionTbl.addMouseListener(mouseListner);
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.layoutEd.panels.IntRecordSelectPnl#load()
	 */
	@Override
	public final void load() {
		selectionMdl.load();
		selectionMdl.fireTableDataChanged();
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.layoutEd.panels.IntRecordSelectPnl#save(boolean)
	 */
	@Override
	public final void save(boolean deleteBlanks) {
		int rowNo = 0;
		RecordSelectionRec rec;
		AbsConnection con = new ReConnection(dbIdx);
		ChildRecordsDB childDB = new ChildRecordsDB();
		boolean autoCommitOff;


		childDB.setConnection(con);
		autoCommitOff = childDB.setAutoCommit(false);

		try {
			Common.stopCellEditing(this.recordSelectionTbl);

			parentRec.setDefaultRecord(defaultSelectionChk.isSelected());
			childDB.setParams(recordId);

			if (parentRec.isNew()) {
				childDB.insert(parentRec);
			} else {
				childDB.update(parentRec);
			}

			//if (deleteBlanks) {
			db.deleteAll();
			for (int i = 0; i < selectionMdl.getRowCount(); i++) {
				rec = selectionMdl.getRecord(i);
				if (rec == null
				|| (deleteBlanks && "".equals(rec.getFieldName()))) {
				} else {
					rec.setFieldNo(rowNo++);

					db.insert(rec);
				}
			}

		} catch (Exception e) {
			if (autoCommitOff) {
				childDB.rollback();
			}
			throw new RuntimeException(e);
		} finally {
			if (autoCommitOff) {
				childDB.setAutoCommit(true);
			}
		}
	}



	/**
	 * @return the updatePnl
	 */
	protected TableUpdatePnl<RecordSelectionRec> getUpdatePnl() {
		return updatePnl;
	}

	private void copyFromDialog() {
		String sql = "Select distinct r.RECORDNAME, sr.Child_Key " +
				"       from     TBL_RS2_SUBRECORDS sr "
                   + "  join TBL_R_RECORDS r "
                   +  "   on sr.Child_record = r.RECORDID "
                   +  " join TBL_RFS_FIELDSELECTION rfs "
                   +  "   on sr.RECORDID  = rfs.RECORDID "
                   +  "  and sr.CHILD_KEY = rfs.CHILD_KEY "
                   + " where sr.RECORDID = "   + recordId
                   + "   and sr.CHILD_KEY != " + parentRec.getChildKey();

		ArrayList<ComboOption> records = new ArrayList<ComboOption>();

		records.add(new ComboOption(-121, ""));


		try {

			ResultSet resultset = Common.getDBConnection(dbIdx).createStatement().executeQuery(sql);
			while (resultset.next()) {
				records.add(new ComboOption(resultset.getInt(2), resultset.getString(1)));
			}
			resultset.close();
		} catch (SQLException e) {
			System.out.println(sql);
			e.printStackTrace();
		}

		if (records.size() > 1) {
			Object ret = ReOptionDialog.showInputDialog(
					panel, "Record to Copy From", "Record Selection", JOptionPane.QUESTION_MESSAGE,
					null, records.toArray(), "");
			if (ret != null && ! "".equals(ret.toString()) && ret instanceof ComboOption) {
				ComboOption co = (ComboOption) ret;
				RecordSelectionDB rsDb = new RecordSelectionDB();
				RecordSelectionRec rs;
				int idx = selectionMdl.getRowCount();

				rsDb.setConnection(new ReConnection(dbIdx));
				rsDb.resetSearch();
				rsDb.setParams(recordId, co.index);
				rsDb.open();
				while ((rs = rsDb.fetch()) != null) {
					rs.setChildKey(parentRec.getChildKey());
					selectionMdl.addRow(idx++, rs);
				}
				rsDb.close();
				selectionMdl.fireTableDataChanged();
			}
		}
	}

	private static class RecSelMdl extends DBtableModel<RecordSelectionRec> {

		private RecSelMdl(RecordSelectionDB db) {
			super(db);
		}


		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.utils.jdbc.DBtableModel#getValueAt(int, int)
		 */
		@Override
		public Object getValueAt(int row, int col) {
			if (row == 0 && col < 2) {
				return "";
			}
			return super.getValueAt(row, col);
		}

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.utils.jdbc.AbsDBTableModel#isCellEditable(int, int)
		 */
		@Override
		public boolean isCellEditable(int row, int col) {
			if (col < 2) {
				return false;
			}
			return super.isCellEditable(row, col);
		}

	}
}
