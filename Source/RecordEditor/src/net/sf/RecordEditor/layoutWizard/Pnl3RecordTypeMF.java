/*
 * @Author Bruce Martin
 * Created on 8/01/2007
 *
 * Purpose:
 * 2nd wizard screen where the user selects the starting
 * position of every field in the file
 *
 * Changes
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - Name changes, remove done method call
 *   - JRecord creation changes
 *
 * # Version 0.61b Bruce Martin 2007/05/05
 *  - Changes o support user selecting the default type
 *  - Changing Column heading from "" to " " so it will be
 *    displayed under Windows look and Feel
 *
 */
package net.sf.RecordEditor.layoutWizard;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.JTextComponent;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.AbsRowList;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBox;
import net.sf.RecordEditor.utils.swing.SwingUtils;

/**
 * 2nd wizard screen where the user selects the starting
 * position of every field in the file
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class Pnl3RecordTypeMF extends WizardPanel {
	private static final String[] FIELD_COL_NAMES = {
		"Field Name", "Start", "Length", "Type"
	};
    private static final int FILE_HEIGHT =  SwingUtils.TABLE_ROW_HEIGHT * 15 - 3;

    private JEditorPane tips;
   
    private FieldMdl keyFieldMdl = new FieldMdl();
    private JTable keyFieldTbl = new JTable(keyFieldMdl);
//    private JTextField nameTxt = new JTextField();
//    private JTextField startTxt = new JTextField();
//    private JTextField lengthTxt = new JTextField();
//    private BmKeyedComboBox typeCombo;

    private Details dtl;
    private ArrayList<KeyField> keys;
    
    private ColumnSelector columnSelector;
    
    //private ArrayList<ColumnDetails> columnList = new ArrayList<ColumnDetails>();
    private RecordDefinition recordDef = new RecordDefinition();
    
    
//    FocusListener focusListner = new FocusAdapter() {
//
//		/* (non-Javadoc)
//		 * @see java.awt.event.FocusAdapter#focusLost(java.awt.event.FocusEvent)
//		 */
//		@Override
//		public void focusLost(FocusEvent e) {
//			super.focusLost(e);
//			
//			setSelectedFieldOnTable();
//		}
//    	
//    };
    
    /**
     * Panel1 File Details
     *
     */
    public Pnl3RecordTypeMF(AbsRowList typeList, JTextComponent message) {
        super();
        
        columnSelector = new ColumnSelector(message);
        
        keyFieldTbl.getColumnModel().getColumn(3).setCellRenderer(
        		(new BmKeyedComboBox(typeList, false)).getTableCellRenderer()
        );
        keyFieldTbl.getColumnModel().getColumn(3).setCellEditor(
        		new DefaultCellEditor(new BmKeyedComboBox(typeList, false))
        );
        keyFieldTbl.setRowHeight(SwingUtils.COMBO_TABLE_ROW_HEIGHT);
        
		String formDescription
		    = "This screen will display the first 60 lines of the file. "
		    + "<br>Indicate the <i>start</i> of the <b>Record-Type field</b> by clicking on the starting column"
		    + "<br>Then click on the start of the Next Field."
		    + "<br>To remove a position click on it again.";
		tips = new JEditorPane("text/html", formDescription);

		this.setHelpURL(Common.formatHelpURL(Common.HELP_WIZARD_RECORD_TYPE));
		
		this.addComponent(1, 5, TIP_HEIGHT, BasePanel.GAP0,
		        BasePanel.FULL, BasePanel.FULL,
				new JScrollPane(tips));
		//this.setGap(BasePanel.GAP1);
//		this.addLine("Name", nameTxt);
//		this.addLine("Field Start", startTxt);
//		this.addLine("Field Length", lengthTxt);
//		this.addLine("Type", typeCombo);
		this.addComponent(1, 5, SwingUtils.COMBO_TABLE_ROW_HEIGHT * 7,
				BasePanel.GAP0,
		        BasePanel.FULL, BasePanel.FULL,
		        keyFieldTbl);
		
		//this.setGap(BasePanel.GAP0);
		
		this.addLine("Show Hex", columnSelector.hexChk);
		this.setGap(BasePanel.GAP0);
		this.addComponent(1, 5, FILE_HEIGHT, BasePanel.GAP1,
		        BasePanel.FULL, BasePanel.FULL,
				columnSelector.fileTbl);

//		startTxt.addFocusListener(focusListner);
//		lengthTxt.addFocusListener(focusListner);
		
		columnSelector.addMouseListner(new MouseAdapter() {
			public void mousePressed(MouseEvent m) {
			    int col = columnSelector.fileTbl.columnAtPoint(m.getPoint());
			    columnSelector.columnSelected(col);
			    checkSelectedFieldOnTable();
			}
		});
    }
    
    private void checkSelectedFieldOnTable() {
    	ArrayList<ColumnDetails> cd = recordDef.columnDtls;
//    	ArrayList<ColumnDetails> cd = new ArrayList<ColumnDetails>(recordDef.columnDtls.size());
//    	
//    	for (int i = cd.size() - 1; i >= 0; i--) {
//    		if (recordDef.columnDtls.get(i).length > 0) {
//    			cd.add(recordDef.columnDtls.get(i));
//    		}
//    	}
     	
    	if (cd.size() > 0) {
    		int start = 1;
	    	int cmp = cd.size() % 2;
	    	int fieldCount = 1;
	    	String name = "Record_Type";
	    	
	    	keys.clear();
    		for (int i = 0; i < cd.size(); i++) {
	    		if (i % 2 == cmp) {
	    			start = cd.get(i).start;
	    		} else {
	    			keys.add(new KeyField(name, start, cd.get(i).start - start, dtl.defaultType));
	    			fieldCount += 1;
	    			name = "Record_Type_" + fieldCount;
	    		}
	    	}
    	
    		padKeys();
    		keyFieldMdl.fireTableDataChanged();
    	}
    }
   
    private void setSelectedFieldOnTable() {
     	for (KeyField k : keys) {
     		if ((k.keyStart <= 0 && k.keyLength <= 0)
     		||  (k.keyStart > 0 && k.keyLength > 0)) {
     			
     		} else {
     			return;
     		}
     	}
    	try {    		
    		if (keys.size() > 0) {
    			int prev = 1;
    			recordDef.columnDtls.clear();
    			//if (keys.get(0).keyStart > 1) {
       			//	recordDef.columnDtls.add(
    			//			new ColumnDetails(keys.get(0).keyStart, 
    			//					columnSelector.getCurrentDetails().defaultType.intValue()));
    			//}
    			
    			for (KeyField k : keys) {
    				if (k.keyStart >= 1 && k.keyLength >= 1) {
    					if (k.keyStart > prev) {
	          				recordDef.columnDtls.add(
	        						new ColumnDetails(k.keyStart, 
	        								columnSelector.getCurrentDetails().defaultType.intValue()));
//	    					System.out.println(" *+ " 
//	    							+ (k.keyStart)
//	    							+ " " + k.keyStart + " " + k.keyLength);
    					}
//    					System.out.println(" ** " 
//    							+ (k.keyStart + k.keyLength)
//    							+ " " + k.keyStart + " " + k.keyLength);
    					prev = k.keyStart + k.keyLength;
    					recordDef.columnDtls.add(
    							new ColumnDetails(prev, k.keyType.intValue()));
    				}
    			}
    			columnSelector.setColorIndicator();
    		}    		
    	} catch (Exception e) {
			// TODO: handle exception
		}
    }


    /**
     * @see net.sf.RecordEditor.layoutWizard.WizardPanel#getValues(net.sf.RecordEditor.LayoutWizard.Details)
     */
    public Details getValues() throws Exception {
    	Details ret = columnSelector.getCurrentDetails();
  
        for (int i = keys.size() - 1; i >= 0; i--) {
        	if (keys.get(i).keyStart < 0) {
        		keys.remove(i);
        	}
        }
     	ret.keyFields = keys;
     	
    	return ret;
    }
  

//    private int getInteger(JTextField fld)  throws Exception {
//    
//    	try {
//    		return Integer.parseInt(fld.getText());
//    	} catch (Exception e) {
//			fld.requestFocus();
//			throw e;
//		}
//    }
    
    
    /**
     * @see net.sf.RecordEditor.layoutWizard.WizardPanel#setValues(net.sf.RecordEditor.LayoutWizard.Details)
     */
    public final void setValues(Details detail) throws Exception {

        columnSelector.readFile(detail, recordDef); 
        columnSelector.setValues(detail, recordDef, false);

        dtl = detail;
        keys = detail.keyFields;
        padKeys();
       	keyFieldMdl.fireTableDataChanged();

    }
    
    
    private void padKeys() {
    	for (int i = keys.size(); i < 5; i++) {
    		keys.add(new KeyField("", -1, -1, dtl.defaultType));
    	}
    }
    
    private class FieldMdl extends AbstractTableModel {

		/* (non-Javadoc)
		 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
		 */
		@Override
		public String getColumnName(int column) {
			return FIELD_COL_NAMES[column];
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
		 */
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return true;
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object, int, int)
		 */
		@Override
		public void setValueAt(Object value, int rowIndex, int columnIndex) {
			KeyField k = keys.get(rowIndex);
			int t;
			
			switch (columnIndex) {
			case 0:  
				if (value == null) {
					k.keyName = "";
				} else {
					k.keyName = value.toString();
				}
				break;
			case 1:
				t = k.keyStart;
				k.keyStart = toInt(value);
				if (t != k.keyStart) {
					setSelectedFieldOnTable();
				}
				break;
			case 2:
				t = k.keyLength;
				k.keyLength = toInt(value);
				if (t != k.keyLength) {
					setSelectedFieldOnTable();
				}
				break;
			default: 
				if (value != null && value instanceof Integer) {
					k.keyType = (Integer) value;
				}
			}
		}
		
		private int toInt(Object o) {
			int ret = -1;
			if (o == null) {
				
			} else if (o instanceof Number) {
				ret = ((Number) o).intValue();
			} else {
				try {
					ret = Integer.parseInt(o.toString());
				} catch (Exception e) {
				}
			}
			
			return ret;
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getColumnCount()
		 */
		@Override
		public int getColumnCount() {
			return FIELD_COL_NAMES.length;
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getRowCount()
		 */
		@Override
		public int getRowCount() {
			return keys.size();
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			KeyField k = keys.get(rowIndex);
			switch (columnIndex) {
			case 0:  return k.keyName;
			case 1:  return fixInt(k.keyStart);
			case 2:  return fixInt(k.keyLength);
			default: return k.keyType;
			}
			
		}
    	
		public Object fixInt(int ii) {
			if (ii < 0) {
				return "";
			}
			return ii;
		}
    }
}
