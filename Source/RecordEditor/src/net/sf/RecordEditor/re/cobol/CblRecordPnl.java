package net.sf.RecordEditor.re.cobol;

import java.awt.BorderLayout;


import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;


import net.sf.JRecord.External.ExternalRecord;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.CheckBoxTableRender;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboRendor;


/**
 * Cobol Record Panel - Cobol Record-Details / Record-Selection 
 * options
 * 
 * @author Bruce Martin
 *
 */
public final class CblRecordPnl {
	
	private final static int FIELD_NAME_COLUMN = 1; 

	private final static int DEFAULT_COLUMN = 5; 
//	private String[] EMPTY_FIELDS = {"", ""};
	private static String[] COLUMN_HEADINS = {
		"Record Name", "Selection Field", "Value 1", "Value 2", "Value 3", "Default ?"
	};
	private final CblLoadData cblDtls;
	//private JTabbedPane cblDtlsTab = new JTabbedPane();
	public final BaseHelpPanel recordPnl = new BaseHelpPanel("CblRec");
	
	
	//private TreeCombo fields = new TreeCombo();
	private RecordTblMdl recordMdl = new RecordTblMdl();
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
		
		JPanel pnl = new JPanel(new BorderLayout());
		
		pnl.add(BorderLayout.WEST, cblDtls.regenSelectionBtn);
		pnl.add(BorderLayout.EAST, cblDtls.addSelectionBtn);
		//fields.setEditable(true);
		
		recordPnl.addLineRE("Selection Field", cblDtls.fields)
					.setGapRE(BasePanel.GAP1)
				 .addComponentRE(1, 3, BasePanel.PREFERRED, BasePanel.GAP2, BasePanel.FULL, BasePanel.FULL, pnl)
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
		TableColumn defaultCol = recordTbl.getColumnModel().getColumn(DEFAULT_COLUMN);
		
		col1.setCellRenderer(fieldRender);
		col1.setCellEditor(fieldEditor);
		defaultCol.setCellRenderer(checkBoxRender);
		defaultCol.setCellEditor(checkBoxEditor);
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
		
		cblDtls.initRecordSelection(xRecord);
		
		setUpValues();

//		int firstField = 0;
//		//ExternalRecord xRecord = cblDtls.getXRecord();
//		HashMap<String, Integer> fldCount = new HashMap<String, Integer>(400);
//		ArrayList<TreeComboItem> items = new ArrayList<TreeComboItem>(20);
//		cblDtls.fields.setText("");
//		
//		if (xRecord != null && xRecord.getNumberOfRecords() > 0 && xRecord.getRecord(0).getNumberOfRecordFields() > 0
//		&& xRecord.getRecord(0).getRecordField(0).getLen() < 4) {
//			firstField = 1;
//			
//			String name = xRecord.getRecord(0).getRecordField(0).getName();
//			items.add(new TreeComboItem(items.size(), name, name));
//		}
//		
//		for (int i = 0; i < xRecord.getNumberOfRecords(); i++) {
//			HashSet<String> used = new HashSet<String>(50);
//			ExternalRecord xr = xRecord.getRecord(i);
//			for (int j = firstField; j < xr.getNumberOfRecordFields(); j++) {
//				ExternalField fld = xr.getRecordField(j);
//				String key = fld.getName().trim().toLowerCase();
//				if (key.length() > 0 &&(!"filler".equals(key)) && (! used.contains(key))) {
//					Integer num = fldCount.get(key);
//					
//					fldCount.put(key, num==null?1:num+1);
//					used.add(key);
//				}
//			}
//		}
//		
//		
//		Set<Entry<String, Integer>> entrySet = fldCount.entrySet();
//		for (Entry<String, Integer> e : entrySet) {
//			if (e.getValue().intValue() > 1) {
//				items.add(new TreeComboItem(items.size(), e.getKey(), e.getKey()));
//			}
//		}
		
//		cblDtls.initRecordSelection(xRecord);


//		if (items.size() > 0) {
//			String s = items.get(0).getEnglish();
//			cblDtls.fields.setText(s);
//			
//			for (int i = 0; i < xRecord.getNumberOfRecords(); i++) {
//				ExternalRecord rec = xRecord.getRecord(i);
//				ExternalField field = getField(rec, s);
//				if (field == null) {
//					if (rec.getNumberOfRecordFields() > 0) {
//						ExternalField f = rec.getRecordField(0);
//					
//						String fn = f.getName().toLowerCase();
//						if (f.getLen() < 5 && (fn.endsWith("id") || fn.endsWith("type"))) {
//							cblDtls.setFieldName(i, f.getName(), false);
//						}
//					}
//				} else {
//					cblDtls.setFieldName(i, s, false);
//
//					if (selField == null) {
//						selField = field;
//					}
//				}
//			}
//			
//			if (selField != null) {
//				for (int i = 0; i < xRecord.getNumberOfRecords(); i++) {
//					if (cblDtls.getFieldName(i).length() == 0) {
//						ExternalRecord r = xRecord.getRecord(i);
//						for (int j = 0; j < r.getNumberOfRecordFields(); j++) {
//							ExternalField f = r.getRecordField(j);
//							if (f.getPos() > selField.getPos()) {
//								break;
//							} else if (f.getPos() == selField.getPos() && f.getLen() == selField.getLen()) {
//								cblDtls.setFieldName(i, f.getName(), false);
//								break;
//							}
//						}
//					}
//				}
//				
//				cblDtls.updateTestValues(false);
//			}
//			cblDtls.fields.setTree(items.toArray(new TreeComboItem[items.size()]));
//		}
		
//		setUpValues();
	}
	
	
	/**
	 * Setup selection field values
	 */
	public void setUpValues() {

		recordMdl.fireTableStructureChanged();

		setupDefaultRenders();
		
		String[] valArray = cblDtls.getRecordTypeValues();
		
		if (valArray != null) {
			for (int c = 2; c < DEFAULT_COLUMN; c++) {
				TableColumn col = recordTbl.getColumnModel().getColumn(c);
				
				col.setCellRenderer(new TreeComboRendor(valArray));
				col.setCellEditor(new TreeComboRendor(valArray));
			}
			recordMdl.fireTableDataChanged();
			
			Common.calcColumnWidths(recordTbl, 0, -1, SwingUtils.STANDARD_FONT_WIDTH * 4);
		}
	}

	
	@SuppressWarnings("serial")
	private class RecordTblMdl extends AbstractTableModel {

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
			case DEFAULT_COLUMN: s = rowIndex == cblDtls.getDefaultRow();					break;
			case FIELD_NAME_COLUMN: s = cblDtls.getFieldName(rowIndex);						break;
			default:
					s = cblDtls.getFieldValue(rowIndex, columnIndex - 2);
					//System.out.println(rowIndex + ", " + columnIndex + ": " + s);
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
			case DEFAULT_COLUMN:
				if (cblDtls.getDefaultRow() == rowIndex) {
					cblDtls.setDefaultRow(-1);
				} else {
					cblDtls.setDefaultRow(rowIndex);
					super.fireTableDataChanged();
				}
				break;
			case FIELD_NAME_COLUMN: 
				cblDtls.setFieldName(rowIndex, fix(aValue), true);
				break;
			default:
				cblDtls.setFieldValue(rowIndex, columnIndex - 2, fix(aValue), true);
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
