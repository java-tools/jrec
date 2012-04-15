package net.sf.RecordEditor.layoutEd.panels;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.sf.RecordEditor.layoutEd.Record.RecordSelectionJTbl;
import net.sf.RecordEditor.re.db.Record.ChildRecordsDB;
import net.sf.RecordEditor.re.db.Record.ChildRecordsRec;
import net.sf.RecordEditor.re.db.Record.RecordDB;
import net.sf.RecordEditor.re.db.Record.RecordRec;
import net.sf.RecordEditor.re.db.Record.RecordSelectionDB;
import net.sf.RecordEditor.re.db.Record.RecordSelectionRec;
import net.sf.RecordEditor.utils.common.AbsConnection;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.jdbc.DBtableModel;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.Combo.ComboOption;

@SuppressWarnings("serial")
public class RecordSelectionPnl extends ReFrame {
	
	private TableUpdatePnl<RecordSelectionRec> updatePnl;
	private RecordSelectionJTbl recordSelectionTbl;
	private RecordSelectionDB db;
	protected final DBtableModel<RecordSelectionRec> selectionMdl;
	protected final JCheckBox defaultSelectionChk = new JCheckBox();
	private JButton copyFromBtn = new JButton(new AbstractAction("Copy Selections") {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			copyFromDialog();
		}
	});
	private JTextField message = new JTextField();
	
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
			final String dbName,
            final int dbConnectionIdx,
            final int recordIdentifier,
            final ChildRecordsRec parent) {
		super(dbName, "Record Selection: " + getRecordName(dbConnectionIdx, parent.getChildRecordId()), null);
		
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
		
		this.setVisible(true);
		recordSelectionTbl.addMouseListener(mouseListner);
	}
	
	private void init_100_SetupFields() {
		
		defaultSelectionChk.setSelected(parentRec.isDefaultRecord());
		
		selectionMdl.setCellEditable(true);
		
		recordSelectionTbl = new RecordSelectionJTbl(selectionMdl, dbIdx, parentRec);
	}
	
	private void init_200_LayoutScreen() {
		
		BaseHelpPanel pnl = new BaseHelpPanel();
		
		super.addCloseOnEsc(pnl);
		pnl.setHelpURL(Common.formatHelpURL(Common.HELP_LAYOUT_RECSEL));
		
		pnl.addLine("Default Record", defaultSelectionChk, copyFromBtn)
		   .setGap(BasePanel.GAP1);
		
		updatePnl = new TableUpdatePnl<RecordSelectionRec>(pnl, null);
		
		pnl.addComponent(1, 5, BasePanel.PREFERRED, BasePanel.GAP, BasePanel.FULL, BasePanel.FULL, updatePnl);
		
		pnl.addComponent(1, 5, BasePanel.FILL, BasePanel.GAP,
				BasePanel.FULL, BasePanel.FULL,
				recordSelectionTbl);
		pnl.addMessage(message);
		
		this.addMainComponent(pnl);
		
		updatePnl.setTableDtls(selectionMdl, recordSelectionTbl, new RecordSelectionRec());
	}

	
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.screenManager.ReFrame#windowClosing()
	 */
	@Override
	public void windowClosing() {
		save(true);
		
		super.windowClosing();
	}

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
	
	
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.common.ReActionHandler#executeAction(int)
	 */
	@Override
	public void executeAction(int action) {
		switch (action) {
		case  ReActionHandler.SAVE: save(false);
		}
	}

	/**
	 * @see net.sf.RecordEditor.utils.common.ReActionHandler#isActionAvailable(int)
	 */
	@Override
	public boolean isActionAvailable(int action) {
		return action == ReActionHandler.SAVE;
	}
	
	private static String getRecordName(int dbIdx, int recId) {
		RecordRec rec = getRecord(dbIdx, recId);
		
		if(rec == null) return "";
		
		return rec.getRecordName();
	}
	
	private static RecordRec getRecord(int dbIdx, int recId) {
		RecordDB rDb = new RecordDB();
		RecordRec ret;
		rDb.setConnection(new ReConnection(dbIdx));
		rDb.setSearchRecordId(RecordDB.opEquals, recId);
		
		ret = rDb.fetch();
		rDb.close();
		return ret;
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
			Object ret = JOptionPane.showInputDialog(
					this, "Record to Copy From", "Record Selecetion", JOptionPane.QUESTION_MESSAGE,
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
