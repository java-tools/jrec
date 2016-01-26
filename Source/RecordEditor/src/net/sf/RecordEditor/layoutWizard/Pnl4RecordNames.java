package net.sf.RecordEditor.layoutWizard;

import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.RecordEditor.utils.MenuPopupListener;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.lang.ReAbstractAction;
import net.sf.RecordEditor.utils.lang.ReOptionDialog;
import net.sf.RecordEditor.utils.screenManager.ReAction;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.CheckBoxTableRender;
import net.sf.RecordEditor.utils.swing.SwingUtils;

@SuppressWarnings("serial")
public class Pnl4RecordNames extends WizardPanel {

	private static final String[] COLUMN_NAMES = LangConversion.convertColHeading(
			"LayoutWizard RecordNames",
			new String[] {"Record Name", "Default", "Include"});
    private JEditorPane tips;

    private JTable recordTbl = new JTable();

    private Details currentDetails;

    private String lastFile = "";

    private MenuPopupListener popup;
    private RecordNames recordNamesMdl;

    private boolean rebuildRecords;

    public Pnl4RecordNames() {
    	int height = Math.max(
    					40,
    				    ReFrame.getDesktopHeight() - TIP_HEIGHT
    				  - 12 * SwingUtils.NORMAL_FIELD_HEIGHT);
		String formDescription
		    = "This screen will display the first 60 lines of the file. "
		    + "<br>Indicate the <i>start</i> of a <b>field</b> by clicking on the starting column"
		    + "<br>Each succesive <b>field</b> will have alternating background color"
	    	+ "<p>To remove a <b>field</b> click on the starting column again.";
		tips = new JEditorPane("text/html", formDescription);

		this.setHelpURLre(Common.formatHelpURL(Common.HELP_WIZARD_RECORD_NAMES));
		this.addComponentRE(1, 5, TIP_HEIGHT, BasePanel.GAP1,
		        BasePanel.FULL, BasePanel.FULL,
				tips);
		//this.setGap(BasePanel.GAP1);
		this.addComponentRE(1, 5,
				height,
				BasePanel.GAP1,
		        BasePanel.FULL, BasePanel.FULL,
				recordTbl);

		popup = new MenuPopupListener();
		popup.setTable(recordTbl);
		popup.getPopup().add(new ReAbstractAction("Clear Value") {
			public void actionPerformed(ActionEvent e) {
				int col = popup.getPopupCol();
				int row = popup.getPopupRow();
				RecordDefinition rec = currentDetails.recordDtlsFull.get(row);

				if (col < rec.getKeyValue().length) {
					rec.getKeyValue()[col] = null;
					recordNamesMdl.fireTableCellUpdated(row, col);
					rebuildRecords = true;
				}
			}
		});
		popup.getPopup().add(new ReAbstractAction("Reset Value") {
			public void actionPerformed(ActionEvent e) {
				int col = popup.getPopupCol();
				int row = popup.getPopupRow();
				RecordDefinition rec = currentDetails.recordDtlsFull.get(row);

				System.out.print(" == Clear: " + row + ", " + col
						+ " " +  rec.getKeyValue().length);
				if (col < rec.getKeyValue().length) {
					rec.getKeyValue()[col] = rec.keyValueHold[col];
					recordNamesMdl.fireTableCellUpdated(row, col);
					rebuildRecords = true;
					System.out.print(" ***");
				}
				System.out.println();
			}
		});
		popup.getPopup().add(new ReAbstractAction("Generate Record Names") {
			public void actionPerformed(ActionEvent e) {
				String pref = ReOptionDialog.showInputDialog(popup.getPopup(), "Record Name Prefix:", "");
				
				for (RecordDefinition rec : currentDetails.recordDtlsFull) {
					if ("".equals(rec.name)) {
						rec.name = pref + rec.getStringKey("");
					}
				}
				recordNamesMdl.fireTableDataChanged();
			}
		});
		recordTbl.addMouseListener(popup);
    }

	@Override
	public Details getValues() throws Exception {
		Common.stopCellEditing(recordTbl);

		if (rebuildRecords) {

		}

		currentDetails.recordDtls.clear();
		for (RecordDefinition rd : currentDetails.recordDtlsFull) {
			if (rd.include) {
				currentDetails.recordDtls.add(rd);
			}
		}

		return currentDetails;
	}

	@Override
	public void setValues(Details detail) throws Exception {
		int noKeys = detail.keyFields.size();
		Object[] key;// = new Object[noKeys];
		FieldDetail[] keyDef = new FieldDetail[noKeys];
		RecordDefinition recDef;
		KeyField k;
		for (int i = 0; i < noKeys; i++) {
			k = detail.keyFields.get(i);
			keyDef[i] = new FieldDetail(
        		"", "", k.keyType.intValue(), 0, detail.fontName, 0, "");
			keyDef[i].setPosLen(k.keyStart, k.keyLength);
		}

		currentDetails = detail;

		if (lastFile.equals(detail.filename)) {
		} else {
			HashMap<String, RecordDefinition> keyToRecordMap = new HashMap<String, RecordDefinition>();
			AbstractLineReader<?> reader = detail.getReader();
			AbstractLine line;
			String keyStr;
			boolean keyPresent;

			lastFile = detail.filename;
			detail.recordDtlsFull.clear();

			while ((line = reader.read()) != null) {
				key = new Object[noKeys];

				keyPresent = false;
				for (int i = 0; i < noKeys; i++) {
					key[i] = line.getField(keyDef[i]);
					if (key[i] != null) {
						keyPresent = true;
					}
				}
				if (keyPresent) {
					keyStr = RecordDefinition.getStringKey(key);
					if (keyToRecordMap.containsKey(keyStr)) {
						recDef = keyToRecordMap.get(keyStr);
						if (recDef.numRecords < recDef.records.length) {
							recDef.records[recDef.numRecords++] = line.getData();
						}
					} else {
						recDef = new RecordDefinition();
						recDef.setKeyValue(key);
						recDef.records[recDef.numRecords++] = line.getData();

						recDef.addKeyField(detail, true);
						//System.out.println("Setting up Record " + keyStr + " " + recDef.columnDtls.size());

						keyToRecordMap.put(keyStr, recDef);
						detail.recordDtlsFull.add(recDef);
					}
				}
			}
			reader.close();
		}

		recordNamesMdl = new RecordNames();
		recordTbl.setModel(recordNamesMdl);


		TableColumn tc = recordTbl.getColumnModel().getColumn(noKeys+2);
        tc.setCellRenderer(new CheckBoxTableRender());
        tc.setCellEditor(new DefaultCellEditor(new JCheckBox()));

        tc = recordTbl.getColumnModel().getColumn(noKeys+1);
        tc.setCellRenderer(new CheckBoxTableRender());
        tc.setCellEditor(new DefaultCellEditor(new JCheckBox()));

        rebuildRecords = false;
	}


	private class RecordNames extends AbstractTableModel {


		/* (non-Javadoc)
		 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
		 */
		@Override
		public String getColumnName(int column) {
			if (column < currentDetails.keyFields.size()) {
				return "key " + (column + 1);
			}
			return COLUMN_NAMES[column - currentDetails.keyFields.size()];
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object, int, int)
		 */
		@Override
		public void setValueAt(Object value, int rowIndex, int columnIndex) {
			if (columnIndex >= currentDetails.keyFields.size()) {

				RecordDefinition rec = currentDetails.recordDtlsFull.get(rowIndex);

				switch (columnIndex - currentDetails.keyFields.size()) {
				case 1:
					rec.defaultRec = (Boolean) value;
					break;
				case 2:
					rec.include = (Boolean) value;
					break;
				default:
					String val = "";
					if(value != null) {
						val = value.toString();
					}
					rec.name = val;
				}
			}

		}

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getColumnCount()
		 */
		@Override
		public int getColumnCount() {
			return currentDetails.keyFields.size() + 3;
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getRowCount()
		 */
		@Override
		public int getRowCount() {
			return currentDetails.recordDtlsFull.size();
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
		 */
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return columnIndex >= currentDetails.keyFields.size();
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		@Override
		public Object getValueAt(int row, int col) {
			RecordDefinition rec = currentDetails.recordDtlsFull.get(row);
			if (col >= currentDetails.keyFields.size()) {
				switch (col - currentDetails.keyFields.size()) {
				case 1:  return rec.defaultRec;
				case 2:  return rec.include;
				default: return rec.name;
				}
			}
			if (rec.getKeyValue().length <= col) {
				return null;
			}
			if (rec.getKeyValue()[col] == null) {
				return "[cleared]";
			}
			return rec.getKeyValue()[col];
		}

	}
}
