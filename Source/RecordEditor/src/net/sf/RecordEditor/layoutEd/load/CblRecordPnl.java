package net.sf.RecordEditor.layoutEd.load;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.Def.ExternalField;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.CheckBoxTableRender;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboItem;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboRendor;

public final class CblRecordPnl {
	
//	private String[] EMPTY_FIELDS = {"", ""};
	private static String[] COLUMN_HEADINS = {
		"Record Name", "Selection Field", "Value", "Default ?"
	};
	private final CblLoadData cblDtls;
	//private JTabbedPane cblDtlsTab = new JTabbedPane();
	public final BaseHelpPanel recordPnl = new BaseHelpPanel("CblRec");
	
	//private TreeCombo fields = new TreeCombo();
	private recordTblMdl recordMdl = new recordTblMdl();
	private JTable recordTbl;
	
	private CheckBoxTableRender checkBoxRender = new CheckBoxTableRender();
	private DefaultCellEditor checkBoxEditor = new DefaultCellEditor(new JCheckBox());
	
	private final TblFieldCombo fieldRender, fieldEditor;

//	private int defaultRow = -1;
//	private ArrayList<String[]> recordSelect = new ArrayList<String[]>();
	
//	private TableCellRenderer dfltRender;
//	private TableCellEditor dfltEditor;
	
	
	public CblRecordPnl(CblLoadData cblDtls) {
		this.cblDtls = cblDtls;
		cblDtls.setRecordMdl(recordMdl);
		
		recordTbl = new JTable(recordMdl);
		
		fieldRender = new TblFieldCombo(cblDtls);
		fieldEditor = new TblFieldCombo(cblDtls);
		
		init_200_LayoutScreen();
		init_300_ScreenUpdates();
		
		recordPnl.done();
	}
	
	private void init_200_LayoutScreen() {
		
		//fields.setEditable(true);
		
		recordPnl.addLineRE("Selection Field", cblDtls.fields)
					.setGapRE(BasePanel.GAP2)
		         .addComponentRE(
			         	1, 3, BasePanel.FILL,
						BasePanel.GAP0,
				        BasePanel.FULL, BasePanel.FULL,
				        recordTbl);
	}
	
	private void init_300_ScreenUpdates() {

		
		recordTbl.setRowHeight(SwingUtils.COMBO_TABLE_ROW_HEIGHT);
		recordTbl.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		recordTbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

//		TableColumn col2 = recordTbl.getColumnModel().getColumn(2);
//		dfltRender = col2.getCellRenderer();
//		dfltEditor = col2.getCellEditor();
		
		setupDefaultRenders();
	}
	
	private void setupDefaultRenders() {
		TableColumn col1 = recordTbl.getColumnModel().getColumn(1);
		TableColumn col3 = recordTbl.getColumnModel().getColumn(3);
		
		col1.setCellRenderer(fieldRender);
		col1.setCellEditor(fieldEditor);
		col3.setCellRenderer(checkBoxRender);
		col3.setCellEditor(checkBoxEditor);
	}
	
	public final void notifyOfUpdate(ExternalRecord xRecord) {
		
		checkForField(xRecord);
		
		recordMdl.fireTableDataChanged();
		Common.calcColumnWidths(recordTbl, 0, -1, SwingUtils.STANDARD_FONT_WIDTH * 4);
	}
	
	
	/**
	 * Setup Selection fields
	 */
	private void checkForField(ExternalRecord xRecord) {
		int firstField = 0;
		//ExternalRecord xRecord = cblDtls.getXRecord();
		HashMap<String, Integer> fldCount = new HashMap<String, Integer>(400);
		ArrayList<TreeComboItem> items = new ArrayList<TreeComboItem>(20);
		
		if (xRecord != null && xRecord.getNumberOfRecords() > 0 && xRecord.getRecord(0).getNumberOfRecordFields() > 0
		&& xRecord.getRecord(0).getRecordField(0).getLen() < 3) {
			firstField = 1;
			
			String name = xRecord.getRecord(0).getRecordField(0).getName();
			items.add(new TreeComboItem(items.size(), name, name));
		}
		
		for (int i = 0; i < xRecord.getNumberOfRecords(); i++) {
			HashSet<String> used = new HashSet<String>(50);
			ExternalRecord xr = xRecord.getRecord(i);
			for (int j = firstField; j < xr.getNumberOfRecordFields(); j++) {
				ExternalField fld = xr.getRecordField(j);
				String key = fld.getName().toLowerCase();
				if (! used.contains(key)) {
					Integer num = fldCount.get(key);
					
					fldCount.put(key, num==null?1:num+1);
					used.add(key);
				}
			}
		}
		
		
		Set<Entry<String, Integer>> entrySet = fldCount.entrySet();
		ExternalField selField = null;
		for (Entry<String, Integer> e : entrySet) {
			if (e.getValue().intValue() > 1) {
					items.add(new TreeComboItem(items.size(), e.getKey(), e.getKey()));
			}
		}
		
		cblDtls.initRecordSelection(xRecord);

		if (items.size() > 0) {
			String s = items.get(0).getEnglish();
			cblDtls.fields.setText(s);
			
			for (int i = 0; i < xRecord.getNumberOfRecords(); i++) {
				ExternalField field = getField(xRecord.getRecord(i), s);
				if (field != null) {
					cblDtls.setRecordSelection(i, 0, s, false);

					if (selField == null) {
						selField = field;
					}
				}
			}
			
			if (selField != null) {
				for (int i = 0; i < xRecord.getNumberOfRecords(); i++) {
					if (cblDtls.getRecordSelection(i, 0).length() == 0) {
						ExternalRecord r = xRecord.getRecord(i);
						for (int j = 0; j < r.getNumberOfRecordFields(); j++) {
							ExternalField f = r.getRecordField(j);
							if (f.getPos() > selField.getPos()) {
								break;
							} else if (f.getPos() == selField.getPos() && f.getLen() == selField.getLen()) {
								cblDtls.setRecordSelection(i,0, f.getName(), false);
								break;
							}
						}
					}
				}
				
				cblDtls.updateTestValues();
			}
		}
		
		cblDtls.fields.setTree(items.toArray(new TreeComboItem[items.size()]));
		setUpValues();
	}
	
	
	/**
	 * Setup selection field values
	 */
	public void setUpValues() {
		ExternalRecord xRecord = cblDtls.getXRecord();
		String fldName = cblDtls.fields.getText();
		byte[] bytes = cblDtls.getFileBytes();

		recordMdl.fireTableStructureChanged();
		TableColumn col2 = recordTbl.getColumnModel().getColumn(2);
		//col2.setCellRenderer(dfltRender);
		//col2.setCellEditor(dfltEditor);
		setupDefaultRenders();
		
		if (xRecord == null || xRecord.getNumberOfRecords() < 2 || bytes == null) {return;}
		
		LayoutDetail schema = xRecord.asLayoutDetail();
		IFieldDetail fld = schema.getFieldFromName(fldName);
		
		if (fld == null) {return;}
		
		TreeSet<String> vals = new TreeSet<String>();
		AbstractLineReader r = LineIOProvider.getInstance().getLineReader(schema);
		AbstractLine l;
		String s;
		
		try {
			r.open(new ByteArrayInputStream(bytes), schema);
			while ((l = r.read()) != null) {
				s = l.getFieldValue(fld).asString();
				if (s != null && s.length() > 0 && ! vals.contains(s)) {
					vals.add(s);
				}
			}
			r.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		TreeComboItem[] tcValues = TreeComboItem.toTreeItemArray(vals.toArray(new String[vals.size()]));
		col2.setCellRenderer(new TreeComboRendor(tcValues));
		col2.setCellEditor(new TreeComboRendor(tcValues));
		recordMdl.fireTableDataChanged();
		
		Common.calcColumnWidths(recordTbl, 0, -1, SwingUtils.STANDARD_FONT_WIDTH * 4);
	}
	
	private ExternalField getField(ExternalRecord r, String name) {
		for (int i = 0; i < r.getNumberOfRecordFields(); i++) {
			if ((name.equalsIgnoreCase(r.getRecordField(i).getName()))) {
				return r.getRecordField(i);
			}
		}
		return null;
	}
	
	@SuppressWarnings("serial")
	private class recordTblMdl extends AbstractTableModel {

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getRowCount()
		 */
		@Override
		public int getRowCount() {
			ExternalRecord xRecord = cblDtls.getXRecord();
			if (xRecord == null) {
				return 0;
			}
			return  xRecord.getNumberOfRecords();
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getColumnCount()
		 */
		@Override
		public int getColumnCount() {
			return COLUMN_HEADINS.length;
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Object s = "";
			switch (columnIndex) {
			case 0: s = cblDtls.getXRecord().getRecord(rowIndex).getRecordName();			break;
			case 3: s = rowIndex == cblDtls.getDefaultRow();								break;
			default:
					s = cblDtls.getRecordSelection(rowIndex, columnIndex - 1);
			}
			return s;
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
		 */
		@Override
		public String getColumnName(int column) {
			return COLUMN_HEADINS[column];
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
		 */
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return columnIndex != 0;
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object, int, int)
		 */
		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			switch (columnIndex) {			
			case 3:
				if (cblDtls.getDefaultRow() == rowIndex) {
					cblDtls.setDefaultRow(-1);
				} else {
					cblDtls.setDefaultRow(rowIndex);
					super.fireTableDataChanged();
				}
				break;
			default:
				cblDtls.setRecordSelection(rowIndex,columnIndex -1, fix(aValue), true);
			}	
		}
		
		private String fix(Object o) {
			if (o == null) {
				return "";
			}
			return o.toString();
		}
	}
}
