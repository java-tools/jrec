package net.sf.RecordEditor.edit.display.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.RecordEditor.edit.display.common.AbstractFieldSequencePnl;
import net.sf.RecordEditor.edit.display.common.AbstractFileDisplayWithFieldHide;

import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.common.Common;

import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.CheckBoxTableRender;
import net.sf.RecordEditor.utils.swing.SwingUtils;

public class HideFields implements ActionListener { //, AbstractSaveDetails<EditorTask> {

    private static final String[] FIELD_COLUMN_HEADINGS  = LangConversion.convertColHeading(
			"Show/Hide Fields",
			new String[] {"Field", "Show"});
    private static final int SHOW_INDEX  = 1;

    private JTable fieldTbl  = new JTable();

    private JButton checkAllFields    = SwingUtils.newButton("Check Fields");
    private JButton uncheckAllFields  = SwingUtils.newButton("Uncheck Fields");

    private JCheckBox saveColSeq = new JCheckBox("Save Column Sequence");
    private JButton goBtn  = SwingUtils.newButton("Go");

    private AbstractFileDisplayWithFieldHide sourcePnl;
    private AbstractLayoutDetails<?, ?> layout;
    private int recordIndex;
    private FieldList fieldMdl;
    private ReFrame frame;

    private TableModelListener listner = new TableModelListener() {

		/* (non-Javadoc)
		 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
		 */
		@Override
		public void tableChanged(TableModelEvent arg0) {
			if (layout != sourcePnl.getFileView().getBaseFile().getLayout()) {
				frame.setVisible(false);
				sourcePnl.getFileView().removeTableModelListener(this);
			}
		}
    };

    public HideFields(AbstractFileDisplayWithFieldHide sourcePanel) {
    	FileView<?> view;
	    TableColumnModel tcm;
	    TableColumn tc;


       	sourcePnl = sourcePanel;
       	view = sourcePnl.getFileView();
    	layout = view.getLayout();
    	recordIndex = sourcePnl.getLayoutIndex();
    	if (recordIndex > layout.getRecordCount() || recordIndex < 0) {
    		return;
    	} else if (recordIndex == layout.getRecordCount()) {
    		recordIndex = view.getDefaultPreferredIndex();
    	}


    	BaseHelpPanel pnl = new BaseHelpPanel();
    	JPanel fieldOptionPanel  = new JPanel();
		frame = new ReFrame(
				view.getBaseFile().getFileNameNoDirectory(), "Field Visibility", view.getBaseFile());
		frame.addCloseOnEsc(pnl);

    	saveColSeq.setSelected(false);
    	fieldMdl = new FieldList(layout, recordIndex, sourcePnl.getFieldVisibility(recordIndex));
    	fieldTbl.setModel(fieldMdl);
        fieldOptionPanel.add(uncheckAllFields);
        fieldOptionPanel.add(checkAllFields);

		pnl.addLine("", fieldOptionPanel);
		pnl.setHeight(BasePanel.NORMAL_HEIGHT * 2);

		pnl.addComponent(1, 5, BasePanel.FILL, BasePanel.GAP1,
		         BasePanel.FULL, BasePanel.FULL,
				 fieldTbl);

		if (sourcePanel instanceof AbstractFieldSequencePnl) {
			pnl.setGap(BasePanel.GAP0);
			saveColSeq.setSelected(true);
			pnl.addLine("", saveColSeq);
		}

		pnl.setGap(BasePanel.GAP1);

//		pnl.addComponent("", null, goBtn);
		JPanel p = new JPanel();
		try {
			p.add(net.sf.RecordEditor.edit.display.util.SaveRestoreHiddenFields.getSaveButton(sourcePanel, this));
		} catch(NoClassDefFoundError nce) {
		} catch (Exception e) {
		}
		pnl.addLine("", p, goBtn);
		pnl.setHeight(BasePanel.GAP1 * 2);

		frame.addMainComponent(pnl);
		frame.setDefaultCloseOperation(ReFrame.DISPOSE_ON_CLOSE);

		tcm = fieldTbl.getColumnModel();
		tc = tcm.getColumn(1);
		tc.setCellRenderer(new CheckBoxTableRender());
		tc.setCellEditor(new DefaultCellEditor(new JCheckBox()));

		uncheckAllFields.addActionListener(this);
		checkAllFields.addActionListener(this);
		goBtn.addActionListener(this);

		view.addTableModelListener(listner);

		frame.setVisible(true);
		frame.setToMaximum(false);
    }

    public boolean isSaveSeqSelected() {
    	return saveColSeq.isSelected();
    }

    public boolean[] getVisibleFields() {
    	return fieldMdl.include.clone();
    }

    public int getRecordIndex() {
		return recordIndex;
	}

	/**
     * Build filtered view of the data
     *
     * @see java.awt.event.ActionListner#actionPerformed
     */
    public void actionPerformed(ActionEvent event) {

    	Common.stopCellEditing(fieldTbl);

        if (event.getSource() == uncheckAllFields) {
        	fieldMdl.updateIncludeFlag(false);
        } else if (event.getSource() == checkAllFields) {
        	fieldMdl.updateIncludeFlag(true);
        } else if (event.getSource() == goBtn) {
        	sourcePnl.setFieldVisibility(recordIndex, fieldMdl.include);
        	frame.setVisible(false);
        	sourcePnl.getFileView().removeTableModelListener(listner);
       }
    }



@SuppressWarnings("serial")
private static class FieldList extends AbstractTableModel {
		private AbstractLayoutDetails<?, ?> layout;
		private AbstractRecordDetail<?> rec;
		public final boolean[] include;
		int recordIdx;


	   /**
		 * @param record
		 * @param showField
		 */
		public FieldList(AbstractLayoutDetails<?, ?> layoutDetails, int recordIndex, boolean[] showField) {
			this.layout = layoutDetails;
			recordIdx = recordIndex;
			this.rec = layout.getRecord(recordIndex);
			this.include = showField;
		}

		public void updateIncludeFlag(boolean newValue) {
			for (int i = 0; i < include.length; i++) {
				include[i] = newValue;
			}

			super.fireTableDataChanged();
		}

	/**
         * @see javax.swing.table.TableModel#getColumnCount
         */
        public int getColumnCount() {
            return FIELD_COLUMN_HEADINGS.length;
        }


        /**
         * @see javax.swing.table.TableModel#getColumnName
         */
        public String getColumnName(int columnIndex) {
            return FIELD_COLUMN_HEADINGS[columnIndex];
        }


        /**
         * @see javax.swing.table.TableModel#isCellEditable
         */
        public int getRowCount() {

        	if (rec == null) {
        		return 0;
        	}
            return rec.getFieldCount();
        }


        /**
         * @see javax.swing.table.TableModel#getValueAt
         */
        public Object getValueAt(int rowIndex, int columnIndex) {

        	if (columnIndex == SHOW_INDEX) {
                return Boolean.valueOf(include[rowIndex]);
            }
            return rec.getField(layout.getAdjFieldNumber(recordIdx, rowIndex)).getName();
        }


        /**
         * @see javax.swing.table.TableModel#isCellEditable
         */
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == SHOW_INDEX;
        }


        /**
         * @see javax.swing.table.TableModel#setValueAt
         */
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        	include[rowIndex] =  aValue == null
        				 || (aValue.getClass() != Boolean.class)
        				 || ((Boolean) aValue).booleanValue();

        }
    }
}
