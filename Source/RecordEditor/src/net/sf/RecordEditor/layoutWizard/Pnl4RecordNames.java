package net.sf.RecordEditor.layoutWizard;

import java.util.HashMap;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.BasePanel;

@SuppressWarnings("serial")
public class Pnl4RecordNames extends WizardPanel {
	
	private static final String[] COLUMN_NAMES = {"Key Value", "Record Name"};
    private JEditorPane tips;
    
    private JTable recordTbl = new JTable();

    private Details currentDetails;
    private HashMap<Object, RecordDefinition> keyToRecordMap = new HashMap<Object, RecordDefinition>();
    
    private String lastFile = "";
    
    public Pnl4RecordNames() {

		String formDescription
		    = "This screen will display the first 60 lines of the file. "
		    + "<br>Indicate the <i>start</i> of a <b>field</b> by clicking on the starting column"
		    + "<br>Each succesive <b>field</b> will have alternating background color"
	    	+ "<p>To remove a <b>field</b> click on the starting column again.";
		tips = new JEditorPane("text/html", formDescription);

		this.setHelpURL(Common.formatHelpURL(Common.HELP_WIZARD_RECORD_NAMES));
		this.addComponent(1, 5, TIP_HEIGHT, BasePanel.GAP1,
		        BasePanel.FULL, BasePanel.FULL,
				new JScrollPane(tips));
		//this.setGap(BasePanel.GAP1);
		this.addComponent(1, 5, PREFERRED, BasePanel.GAP1,
		        BasePanel.FULL, BasePanel.FULL,
				new JScrollPane(recordTbl));
    }
    
	@Override
	public Details getValues() throws Exception {
		Common.stopCellEditing(recordTbl);
		
		return currentDetails;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setValues(Details detail) throws Exception {
		Object key;
		RecordDefinition recDef;
        FieldDetail keyDef = new FieldDetail(
        		"", "", detail.keyType.intValue(), 0, detail.fontName, 0, "");
        keyDef.setPosLen(detail.keyStart, detail.keyLength);

		currentDetails = detail;
		
		keyToRecordMap.clear();
		
		if (lastFile.equals(detail.filename)) {
			for (RecordDefinition rec : detail.recordDtls) {
				keyToRecordMap.put(rec.keyValue, rec);
			}
		} else {
			AbstractLineReader<?> reader = detail.getReader();
			AbstractLine<?> line;
			
			lastFile = detail.filename;
			detail.recordDtls.clear();
					
			while ((line = reader.read()) != null) {
				key = line.getField(keyDef);
				if (key != null) {
					if (keyToRecordMap.containsKey(key)) {
						recDef = keyToRecordMap.get(key);
						if (recDef.numRecords < recDef.records.length) {
							recDef.records[recDef.numRecords++] = line.getData();
						}
					} else {
						recDef = new RecordDefinition();
						recDef.keyValue = key;
						recDef.records[recDef.numRecords++] = line.getData();
						
						recDef.addKeyField(detail, true);
						System.out.println("Setting up Record " + key + " " + recDef.columnDtls.size());
						
						keyToRecordMap.put(key, recDef);
						detail.recordDtls.add(recDef);
					}
				}
			}
			reader.close();
		}
		
		recordTbl.setModel(new RecordNames());
	}

	private class RecordNames extends AbstractTableModel {

		
		
		/* (non-Javadoc)
		 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
		 */
		@Override
		public String getColumnName(int column) {
			return COLUMN_NAMES[column];
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object, int, int)
		 */
		@Override
		public void setValueAt(Object value, int rowIndex, int columnIndex) {
			if (columnIndex == 1) {
				String val = "";
				RecordDefinition rec = currentDetails.recordDtls.get(rowIndex);
				if (value != null) {
					val = value.toString();
				}
				rec.name = val;
			}

		}

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getColumnCount()
		 */
		@Override
		public int getColumnCount() {
			return 2;
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getRowCount()
		 */
		@Override
		public int getRowCount() {
			return currentDetails.recordDtls.size();
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
		 */
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return columnIndex > 0;
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		@Override
		public Object getValueAt(int row, int col) {
			RecordDefinition rec = currentDetails.recordDtls.get(row);
			if (col == 0) {
				return rec.keyValue;
			}
			return rec.name;
		}
		
	}
}
