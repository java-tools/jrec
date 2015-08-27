package net.sf.RecordEditor.layoutEd.Parameter;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;

import net.sf.RecordEditor.re.db.Combo.ComboDB;
import net.sf.RecordEditor.re.db.Combo.ComboJTbl;
import net.sf.RecordEditor.re.db.Combo.ComboRec;
import net.sf.RecordEditor.re.db.Table.TableDB;
import net.sf.RecordEditor.re.db.Table.TableRec;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.jdbc.AbsDB;
import net.sf.RecordEditor.utils.jdbc.DBComboModel;
import net.sf.RecordEditor.utils.jdbc.DBtableModel;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBox;
import net.sf.RecordEditor.utils.swing.SwingUtils;

/**
 * Search for selected Combos
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class ComboSearch extends BaseHelpPanel implements ActionListener, FocusListener {

	public static final String SELECTION_CHANGED = "SelectionChanged";

	private JTextField sfName = new JTextField();
	private BmKeyedComboBox sfSystem;

	private ComboDB dbCombo = new ComboDB();
	private DBtableModel<ComboRec> dbComboListModel = null;
	private ComboJTbl tblComboList;

	private JButton search ;

	//private NotifyRowChanged notifyRowChanged;

	public ComboSearch(final int connectionIdx, boolean showSystem) { //, NotifyRowChanged notify) {
		super();

		//notifyRowChanged = notify;

		init_100_setupFields(connectionIdx, showSystem);
		init_200_setupScreen(showSystem);
	}

	@SuppressWarnings("unchecked")
	private void init_100_setupFields(int connectionIdx, boolean showSystem) {
		ReConnection con = new ReConnection(connectionIdx);

		this.setHelpURLre(Common.formatHelpURL(Common.HELP_COMBO_SEARCH));

		if (showSystem) {
			TableDB tableDb = new TableDB();
			DBComboModel<TableRec> systemModel = new DBComboModel<TableRec>(tableDb, 0, 1, true, true);

			tableDb.setConnection(con);
			tableDb.setParams(Common.TI_SYSTEMS);
			sfSystem  = new BmKeyedComboBox(systemModel, true);
			sfSystem.addActionListener(this);
		} else {
			search = SwingUtils.newButton("Search");
		}

		dbCombo.setConnection(con);

		dbComboListModel   = new DBtableModel(dbCombo);
		tblComboList  = new ComboJTbl(dbComboListModel, connectionIdx);

		tblComboList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblComboList.setRowHeight(SwingUtils.COMBO_TABLE_ROW_HEIGHT);
		//tblComboList.getR
		//tblComboList.setSelectionMode(JTable.)
//		TableColumn tc = tblComboList.getColumnModel().getColumn(0);
//		tc.setPreferredWidth(40);
//		tc = tblComboList.getColumnModel().getColumn(1);
//		tc.setPreferredWidth(80);
		Common.calcColumnWidths(tblComboList, 0);

		sfName.addFocusListener(this);
	}


	private void init_200_setupScreen(boolean showSystem) {

		addLineRE("Combo Name", sfName);

		if (showSystem) {
			addLineRE("System", sfSystem);
		} else {
			addLineRE("", search);
		}

		setGapRE(BasePanel.GAP0);

		addComponentRE(1, 3, BasePanel.FILL, BasePanel.GAP, BasePanel.FULL,
		        BasePanel.FULL, tblComboList);

	}


	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public final void actionPerformed(ActionEvent arg0) {

		changeSearchArguments();
	}

	/**
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	public final void focusGained(FocusEvent arg0) {
	}

	/**
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	public final void focusLost(FocusEvent arg0) {

		changeSearchArguments();
	}

	/**
	 * get the currrently selected Combo name
	 * @return selected Combo name
	 */
	public final String getSelectedCombo() {
		String ret = "";
		int idx = tblComboList.getSelectedRow();

		if (idx >= 0) {
			ret = dbComboListModel.getRecord(idx).getComboName();
		}

		return ret;
	}

	public final void setSelectedCombo(String comboName) {

		if (comboName != null && ! "".equals(comboName)) {
			for (int i = 0; i < dbComboListModel.size(); i++) {
				if (comboName.equals(dbComboListModel.getRecord(i).getComboName())) {
					tblComboList.clearSelection();
					tblComboList.changeSelection(i, 1, false, false);
				}
			}
		}
	}


	/**
	 * Change the search arguments
	 */
	protected void changeSearchArguments() {

		dbCombo.resetSearch();

		if (! "".equals(sfName.getText())) {
			dbCombo.setSearchComboName(AbsDB.opLike, sfName.getText());
		}


		if (sfSystem != null && sfSystem.getSelectedIndex() >= 0) {
			dbCombo.setSearchSystem(
			        AbsDB.opEquals,
			        ((Integer) sfSystem.getSelectedItem()).intValue());
		}


		dbComboListModel.load();
		dbComboListModel.fireTableDataChanged();

		this.firePropertyChange(SELECTION_CHANGED, null, null);
	}

	/**
	 * @return the dbComboListModel
	 */
	public final DBtableModel<ComboRec> getComboListModel() {
		return dbComboListModel;
	}

	/**
	 * @return the tblComboList
	 */
	public final ComboJTbl getComboList() {
		return tblComboList;
	}

	/**
	 * @param val
	 * @see net.sf.RecordEditor.utils.jdbc.AbsDB#checkAndUpdate(net.sf.RecordEditor.utils.jdbc.AbsRecord)
	 */
	public final void checkAndUpdate(ComboRec val) {
		dbCombo.checkAndUpdate(val);
		dbComboListModel.fireTableDataChanged();
	}

	/**
	 * Get The row at a point
	 * @param point point to find the row for
	 * @return row
	 */
	public int rowAtPoint(Point point) {
		return tblComboList.rowAtPoint(point);
	}

}
