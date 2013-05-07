/*
 * @Author Bruce Martin
 * Created on 24/07/2005
 *
 * Purpose:
 *   Allow the user to create a filtered view of the file being
 *  editted.
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *   - changed to use ReFrame (from JFrame) which keeps track
 *     of the active form
 *   - changed to use ReActionHandler instead of ActionListener
 *   - add Check / uncheck fields buttons
 *
 * # Version 0.60 Bruce Martin 2007/02/16
 *   - Changed to use standard ComboBoxRender (instead of internal version
 *
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - Removed call to BasePanel.done() (done automatically)
 *   - hex and Text updates
 *   - Creating views from selected records
 *   - JRecord Spun off
 *
 * # Version 0.62 Bruce Martin 2007/04/30
 *   - adding support for enter key
 **/
package net.sf.RecordEditor.re.file.filter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.CheckBoxTableRender;
import net.sf.RecordEditor.utils.swing.SwingUtils;



/**
 * Class to display and select fields
 *
 * @author Bruce Martin
 *
 * @version 0.56
 */
@SuppressWarnings("serial")
public abstract class BaseFieldSelection extends BaseHelpPanel implements ActionListener {

    private   static final int FIRST_COLUMN_WIDTH     = SwingUtils.STANDARD_FONT_WIDTH * 35;
    private   static final int SECOND_COLUMN_WIDTH    = SwingUtils.STANDARD_FONT_WIDTH * 9;

    protected static final int FIELD_VALUE_ROW_HEIGHT = SwingUtils.COMBO_TABLE_ROW_HEIGHT;
    protected static final int FIELD_NAME_WIDTH       = SwingUtils.STANDARD_FONT_WIDTH * 22;


    private BaseHelpPanel pnl2 = new BaseHelpPanel("Filter");
    //private JTabbedPane tabOption    = new JTabbedPane();
    private JPanel recordOptionPanel = new JPanel();
    private JPanel fieldOptionPanel  = new JPanel();
    protected final JTable recordTbl = new JTable();
    private JTable fieldTbl  = new JTable();

    private AbstractTableModel recordMdl;
    private AbstractTableModel fieldMdl;


    private JButton execute
    		  = SwingUtils.newButton("Filter", Common.getRecordIcon(Common.ID_FILTER_ICON));
    private JTextField msgTxt     = new JTextField();

    private JButton checkAllRecordsBtn   = SwingUtils.newButton("Check Records");
    private JButton uncheckAllRecordsBtn = SwingUtils.newButton("Uncheck Records");

    private JButton checkAllFieldsBtn    = SwingUtils.newButton("Check Fields");
    private JButton uncheckAllFieldsBtn  = SwingUtils.newButton("Uncheck Fields");
//    private JButton showHideFieldBtn     = new JButton(showFldBtnText);

    private FilterDetails filter;
	private AbstractLayoutDetails recordLayout;

    private boolean toInit = true;

//	private final MouseAdapter fieldMouseListner = new MouseAdapter() {
//
//	  /**
//       * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
//       */
//		public void mousePressed(MouseEvent m) {
//			try {
//				int col = filterFieldTbl.columnAtPoint(m.getPoint());
//				int row = filterFieldTbl.rowAtPoint(m.getPoint());
//				int tblCol = filterFieldTbl.getColumnModel().getColumn(col).getModelIndex();
//
//				if (tblCol == FilterField.FLD_AND_VAL || tblCol == FilterField.FLD_OR_VAL) {
//					FilterField rec = filterFieldMdl.getFilterField(row);
//
//					//rec.setBooleanOperator(1 - rec.getBooleanOperator());
//					//filterFieldMdl.clearRecordSelection();
//					filterFieldMdl.setValueAt(1 - rec.getBooleanOperator(), row, tblCol);
//
//					filterFieldMdl.fireTableRowsUpdated(row, row);
//					System.out.print(" ## " + row);
//				}
//			} catch (Exception e) {
//				// if it does not work so what
//				e.printStackTrace();
//			}
//		}
//	};




	public final void setRecordLayout(final AbstractLayoutDetails layout,
    		final AbstractLayoutDetails layout2,
    		boolean is2ndLayout, int heightOverhead) {
    	recordLayout = layout;


        setupScreenFields(is2ndLayout, layout2);

        if (toInit) {
        	int height;
        	int desktopHeight = ReFrame.getDesktopHeight() - 50 - heightOverhead
        	                    - 2 * ((int) BasePanel.GAP1);
        	//JScrollPane scrollPane = new JScrollPane(pnl2);
        	if (! is2ndLayout) {
        		desktopHeight -= SwingUtils.BUTTON_HEIGHT + 6;
			}

        	this.registerComponent(pnl2);

        	pnl2.setBorder(BorderFactory.createEmptyBorder());
        	//scrollPane.setBorder(BorderFactory.createEmptyBorder());

        	if (this.getClass() != BaseFieldSelection.class
        	|| is2ndLayout
        	|| layout.getRecordCount() > 1) {
        		int maxHeight = desktopHeight / 3;

	        	if (is2ndLayout) {
	        		pnl2.addHelpBtn(SwingUtils.getHelpButton());
					height = SwingUtils.calculateComboTableHeight(recordTbl.getRowCount(), maxHeight);
	        	} else {
	        		pnl2.addHelpBtn(recordOptionPanel, SwingUtils.getHelpButton());
					height = SwingUtils.calculateTableHeight(recordTbl.getRowCount(), maxHeight);
	        	}
				pnl2.setHeight(SwingUtils.BUTTON_HEIGHT + 6);

				desktopHeight -= height + SwingUtils.BUTTON_HEIGHT + 6;
				pnl2.addComponent(1, 5,
						 height,
				         BasePanel.GAP1,
				         BasePanel.FULL, BasePanel.FULL,
						 recordTbl);
				pnl2.setComponentName(recordTbl, "RecordSelection");
	        }

//        	showFieldTable = true;
			if (! is2ndLayout) {
				pnl2.addLine("", fieldOptionPanel);
				pnl2.setHeight(SwingUtils.BUTTON_HEIGHT + 6);
//				showFieldTable = false;
			}


			height =  desktopHeight  * 4 / 5;

			pnl2.addComponent(1, 5, height, 3,
			         BasePanel.FULL, BasePanel.FULL,
			         fieldTbl);

			//fldPane.setVisible(showFieldTable);
			pnl2.setComponentName(fieldTbl, "FieldSelection");

			setGap(0);
			setHelpURL(Common.formatHelpURL(Common.HELP_FILTER));
			addComponent(0, 6, BasePanel.FILL, BasePanel.GAP, BasePanel.FULL,
			        BasePanel.FULL, new JScrollPane(pnl2));

			addMessage(msgTxt);
			super.done();
			toInit = false;
        }
    }


    /**
	 * @return the recordLayout
	 */
	public final AbstractLayoutDetails getRecordLayout() {
		return recordLayout;
	}


	/**
     * define screen fields
     */
    private void setupScreenFields(
    		boolean is2ndLayout,
    		final AbstractLayoutDetails layout2) {

        filter = new FilterDetails(recordLayout, FilterDetails.FT_NORMAL); // getFilterDetails(recordLayout);
        filter.setMessageFld(msgTxt);
        filter.set2Layouts(is2ndLayout);
        if (is2ndLayout) {
        	filter.set2ndLayout(layout2);
        }

        fieldMdl       = filter.getFieldListMdl();
        recordMdl      = filter.getLayoutListMdl();

    	recordTbl.setModel(recordMdl);
        fieldTbl.setModel(fieldMdl);


        setRecordTableDetails(recordTbl);
        setFieldTableDetails(fieldTbl);

        recordOptionPanel.add(uncheckAllRecordsBtn);
        recordOptionPanel.add(checkAllRecordsBtn);

        fieldOptionPanel.add(uncheckAllFieldsBtn);
        fieldOptionPanel.add(checkAllFieldsBtn);
//        fieldOptionPanel.add(showHideFieldBtn);

	    if (toInit) {
			recordTbl.addMouseListener(new MouseAdapter() {
				public void mousePressed(final MouseEvent m) {
				    int idx = recordTbl.getSelectedRow();

				    if (idx >= 0 && idx < recordTbl.getRowCount()) {
				        filter.setLayoutIndex(idx);
				        fieldMdl.fireTableDataChanged();
				    }
				}
			});

			uncheckAllRecordsBtn.addActionListener(this);
			checkAllRecordsBtn.addActionListener(this);
			uncheckAllFieldsBtn.addActionListener(this);
			checkAllFieldsBtn.addActionListener(this);
//			showHideFieldBtn.addActionListener(this);

			registerComponent(uncheckAllRecordsBtn);
			registerComponent(checkAllRecordsBtn);
			registerComponent(uncheckAllFieldsBtn);
			registerComponent(checkAllFieldsBtn);
//			registerComponent(showHideFieldBtn);
	    } else {
	    	recordMdl.fireTableDataChanged();
	    	fieldMdl.fireTableDataChanged();
	    }
		//execute.addActionListener(this);
    }


    /**
     * Set Record Table
     * @param tbl table to update
     */
    protected void setRecordTableDetails(JTable tbl) {
    	setTableDetails(tbl);
    }

    /**
     * Set Record Table
     * @param tbl table to update
     */
    protected void setFieldTableDetails(JTable tbl) {
    	setTableDetails(tbl);
    }

    /**
     * Allocate Table attributes for the 2 include tables
     *
     * @param tbl table to have attributes set
     */
    private void setTableDetails(JTable tbl) {
        DefaultCellEditor includeEditor = new DefaultCellEditor(new JCheckBox());
        TableColumn tc;

        setTableDetailsCol0(tbl);

		tc = tbl.getColumnModel().getColumn(1);
	  	tc.setCellRenderer(new CheckBoxTableRender());
		tc.setCellEditor(includeEditor);
		tc.setPreferredWidth(SECOND_COLUMN_WIDTH);
    }

    protected final void setTableDetailsCol0(JTable tbl) {

        tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tbl.getColumnModel().getColumn(0).setPreferredWidth(FIRST_COLUMN_WIDTH);
    }

//
    /**
     * Build filtered view of the data
     *
     * @see java.awt.event.ActionListner#actionPerformed
     */
    public void actionPerformed(ActionEvent event) {

    	stopTblEdit();

        if (event.getSource() == uncheckAllFieldsBtn) {
            updateIncludeFlag(Boolean.FALSE);
        } else if (event.getSource() == checkAllFieldsBtn) {
            updateIncludeFlag(Boolean.TRUE);
        } else if (event.getSource() == uncheckAllRecordsBtn) {
        	updateRecordFlag(Boolean.FALSE);
        } else if (event.getSource() == checkAllRecordsBtn) {
        	updateRecordFlag(Boolean.TRUE);
//        } else if (event.getSource() == showHideFieldBtn) {
//        	showFieldTable = ! showFieldTable;
//        	fldPane.setVisible(showFieldTable);
//
//        	if (showFieldTable) {
//        		showHideFieldBtn.setText(hideFldBtnText);
//        	} else {
//        		showHideFieldBtn.setText(showFldBtnText);
//        	}
//        	this.revalidate();
        }
    }



	/**
     * Updates the include flag in the field list table
     *
     * @param val new value for the field table
     */
    private void updateRecordFlag(Boolean val) {
        int i;

        for (i = recordTbl.getRowCount() - 1; i >= 0; i--) {
        	recordTbl.setValueAt(val, i, 1);
        }
        recordMdl.fireTableDataChanged();
    }


    /**
     * Updates the include flag in the field list table
     *
     * @param val new value for the field table
     */
    private void updateIncludeFlag(Boolean val) {
        int i;

        for (i = fieldTbl.getRowCount() - 1; i >= 0; i--) {
            fieldMdl.setValueAt(val, i, 1);
        }
        fieldMdl.fireTableDataChanged();
    }


	/**
	 * @return the messageFld
	 */
	public final JTextField getMessageFld() {
		return msgTxt;
	}


	/**
	 * @return the filter
	 */
	public final FilterDetails getFilter() {
		stopTblEdit();

		return filter;
	}

	private void stopTblEdit() {

        Common.stopCellEditing(recordTbl);
        Common.stopCellEditing(fieldTbl);
	}


	/**
	 * @return the execute
	 */
	public final JButton getExecute() {
		return execute;
	}
}
