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
package net.sf.RecordEditor.utils.filter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.RecordEditor.jibx.compare.EditorTask;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.Parameters;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.CheckBoxTableRender;
import net.sf.RecordEditor.utils.swing.ComboBoxRender;
import net.sf.RecordEditor.utils.swing.SwingUtils;


/**
 * Class to display / update Filter details
 * (i.e. which records are to be displayed).
 *
 * @author Bruce Martin
 *
 * @version 0.56
 */
@SuppressWarnings("serial")
public class FilterPnl extends BaseHelpPanel implements ActionListener, AbstractSaveDetails<EditorTask> {

    private static final int FIRST_COLUMN_WIDTH      = SwingUtils.STANDARD_FONT_WIDTH * 35;
    private static final int SECOND_COLUMN_WIDTH     = SwingUtils.STANDARD_FONT_WIDTH * 9;

    protected static final int FIELD_VALUE_ROW_HEIGHT  = SwingUtils.COMBO_TABLE_ROW_HEIGHT;
    protected static final int FIELD_NAME_WIDTH      = SwingUtils.STANDARD_FONT_WIDTH * 22;
    private static final int CASE_SENSTIVE_WIDTH     = SwingUtils.STANDARD_FONT_WIDTH * 10;
    private static final int OPERATOR_WIDTH          = CASE_SENSTIVE_WIDTH;
    private static final int VALUE_WIDTH             = FIELD_NAME_WIDTH;
 
//    private static final double TABLE_HEIGHT         = 8 * BasePanel.NORMAL_HEIGHT;
//    private static final double FIELD_VALUE_TABLE_HEIGHT
//    	= (FilterFieldList.NUMBER_FIELD_FILTER_ROWS + 2)
//        * FIELD_VALUE_ROW_HEIGHT;

    private BaseHelpPanel pnl2 = new BaseHelpPanel();
    //private JTabbedPane tabOption    = new JTabbedPane();
    private JPanel recordOptionPanel = new JPanel();
    private JPanel fieldOptionPanel  = new JPanel();
    protected final JTable recordTbl = new JTable();
    private JTable fieldTbl  = new JTable();
    private JTable filterFieldTbl = new JTable();
    private AbstractTableModel recordMdl;
    private AbstractTableModel fieldMdl;
    private AbstractTableModel filterFieldMdl;
    
   // private DefaultCellEditor includeEditor = new DefaultCellEditor(new JCheckBox());
    private JButton execute
    		  = new JButton("Filter", Common.getRecordIcon(Common.ID_FILTER_ICON));
    private JTextField messageFld     = new JTextField();

    private JButton checkAllRecords   = new JButton("Check Records");
    private JButton uncheckAllRecords = new JButton("Uncheck Records");

    private JButton checkAllFields    = new JButton("Check Fields");
    private JButton uncheckAllFields  = new JButton("Uncheck Fields");

    private FilterDetails filter;
    private AbstractLayoutDetails recordLayout;
 
    private boolean addExecute;
    private boolean toInit = true;
    
    private boolean addFieldFilter = true;
    
    public FilterPnl(boolean pAddExecute) {  
    	addExecute = pAddExecute;
    	
    	addFieldFilter = false;
    }
    

    /**
     * Display Filter on the screen for the user to update
     *
     * @param fileTbl file to be filtered
     */
    public FilterPnl(final AbstractLayoutDetails layout, boolean pAddExecute) {
    	
    	addExecute = pAddExecute;

    	setRecordLayout(layout, null, false, 0);
    }
    
    public final void setRecordLayout(final AbstractLayoutDetails layout, 
    		final AbstractLayoutDetails layout2, 
    		boolean is2ndLayout, int heightOverhead) {
    	recordLayout = layout;
    	

        setupScreenFields(is2ndLayout, layout2);

        if (toInit) {
        	int height;
        	int desktopHeight = ReFrame.getDesktopHeight() - 50 - heightOverhead
        	                    - 2 * ((int) BasePanel.GAP1);
        	JScrollPane scrollPane = new JScrollPane(pnl2);
        	if (! is2ndLayout) {
        		desktopHeight -= SwingUtils.BUTTON_HEIGHT + 6;
			}
        	if (addExecute) {
        		desktopHeight -= SwingUtils.BUTTON_HEIGHT * 3;
        	}
        	
        	pnl2.setBorder(BorderFactory.createEmptyBorder());
        	scrollPane.setBorder(BorderFactory.createEmptyBorder());
        	
        	if (this.getClass() != FilterPnl.class 
        	|| is2ndLayout
        	|| layout.getRecordCount() > 1) {
        		int maxHeight = desktopHeight / 3;
        		if (addFieldFilter) {
        			maxHeight = desktopHeight / 4;
        		}
	        	if (is2ndLayout) {
	        		pnl2.addHelpBtn(Common.getHelpButton());
					height = SwingUtils.calculateComboTableHeight(recordTbl.getRowCount(), maxHeight);
	        	} else {
	        		pnl2.addHelpBtn(recordOptionPanel, Common.getHelpButton());
					height = SwingUtils.calculateTableHeight(recordTbl.getRowCount(), maxHeight);
	        	}
				pnl2.setHeight(SwingUtils.BUTTON_HEIGHT + 6);
				
				desktopHeight -= height + SwingUtils.BUTTON_HEIGHT + 6;
				pnl2.addComponent(1, 5,
						 height,
				         BasePanel.GAP1,
				         BasePanel.FULL, BasePanel.FULL,
						 recordTbl);
	        }
       	
			if (! is2ndLayout) {
				pnl2.addLine("", fieldOptionPanel);
				pnl2.setHeight(SwingUtils.BUTTON_HEIGHT + 6);
			}

	
			if (addFieldFilter) {
				int rows = fieldTbl.getRowCount();
				for (int i =0; i < recordLayout.getRecordCount(); i++) {
					rows = Math.max(rows, recordLayout.getRecord(i).getFieldCount());
				}
				height = SwingUtils.calculateTableHeight(rows, desktopHeight / 2);
				desktopHeight -= height;

				pnl2.addComponent(1, 5, height, BasePanel.GAP1,
				         BasePanel.FULL, BasePanel.FULL,
						 fieldTbl);
				
				height = SwingUtils.calculateComboTableHeight(FilterFieldList.NUMBER_FIELD_FILTER_ROWS, desktopHeight * 7 / 10);
				pnl2.addComponent(1, 5,
						height, 3,
			         BasePanel.FULL, BasePanel.FULL,
					 filterFieldTbl);
			} else {
				//height = SwingUtils.calculateComboTableHeight(fieldTbl.getRowCount(), desktopHeight * 8 / 10);
				height =  desktopHeight  * 4 / 5;

				pnl2.addComponent(1, 5, height, 3,
				         BasePanel.FULL, BasePanel.FULL,
						 fieldTbl);
			}
			
			setGap(0);
			setHelpURL(Common.formatHelpURL(Common.HELP_FILTER));
			addComponent(0, 6, BasePanel.FILL, BasePanel.GAP, BasePanel.FULL,
			        BasePanel.FULL, new JScrollPane(pnl2));
		
			
			if (addExecute) {
				setGap(BasePanel.GAP0);	
				String dir = Parameters.getFileName(Parameters.FILTER_SAVE_DIRECTORY);
				JPanel p = new JPanel();
				p.add(new SaveButton<EditorTask>(this, dir));
				addLine("", p, execute);
				setHeight(BasePanel.GAP1 * 2);
				setGap(BasePanel.GAP0);
			}
	
			addMessage(messageFld);
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
    private void setupScreenFields(boolean is2ndLayout, final AbstractLayoutDetails layout2) {
    	
        filter = new FilterDetails(recordLayout); // getFilterDetails(recordLayout);
        filter.setMessageFld(messageFld);
        filter.set2Layouts(is2ndLayout);
        if (is2ndLayout) {
        	filter.set2ndLayout(layout2);
        }

        fieldMdl       = filter.getFieldListMdl();
        filterFieldMdl = filter.getFilterFieldListMdl();
        recordMdl      = filter.getLayoutListMdl();
    
    	recordTbl.setModel(recordMdl);
        fieldTbl.setModel(fieldMdl);
        
        if (addFieldFilter) {
        	filterFieldTbl.setModel(filterFieldMdl);
        	buildFieldFilterTable();
        }
	
        setRecordTableDetails(recordTbl);
        setFieldTableDetails(fieldTbl);
        
        recordOptionPanel.add(uncheckAllRecords);
        recordOptionPanel.add(checkAllRecords);

        fieldOptionPanel.add(uncheckAllFields);
        fieldOptionPanel.add(checkAllFields);
	
	    if (toInit) {
			recordTbl.addMouseListener(new MouseAdapter() {
				public void mousePressed(final MouseEvent m) {
				    int idx = recordTbl.getSelectedRow();
				    
				    if (idx >= 0 && idx < recordTbl.getRowCount()) {
				        if (addFieldFilter && idx != filter.getLayoutIndex()) {
				        	Common.stopCellEditing(filterFieldTbl);
				        }
				        filter.setLayoutIndex(idx);
				        fieldMdl.fireTableDataChanged();
				        if (addFieldFilter) {
				        	filterFieldMdl.fireTableDataChanged();
				        }
				    }
				}
			});
	
			uncheckAllRecords.addActionListener(this);
			checkAllRecords.addActionListener(this);
			uncheckAllFields.addActionListener(this);
			checkAllFields.addActionListener(this);
	    } else {
	    	recordMdl.fireTableDataChanged();
	    	fieldMdl.fireTableDataChanged();
	    	
	    	if (addFieldFilter) {
	    		filterFieldMdl.fireTableDataChanged();
	    	}
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

    /**
     * build Field Filter Table
     */
    private void buildFieldFilterTable() {
        TableColumnModel tcm;
        TableColumn tc;
        DefaultComboBoxModel operatorMdl = new DefaultComboBoxModel
                (Compare.OPERATOR_STRING_VALUES);
        ComboBoxRender operatorRendor = new ComboBoxRender(operatorMdl);
        ComboBoxRender fieldRendor = new ComboBoxRender(
	  	        filter.getFilterFieldListMdl().getFieldModel());


        filterFieldTbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        filterFieldTbl.setRowHeight(FIELD_VALUE_ROW_HEIGHT);
        tcm = filterFieldTbl.getColumnModel();
		tc = tcm.getColumn(0);
		tc.setPreferredWidth(FIELD_NAME_WIDTH);
	  	tc.setCellRenderer(fieldRendor);
		tc.setCellEditor(new DefaultCellEditor(
		    new JComboBox(filter.getFilterFieldListMdl().getFieldModel())));

		tc = tcm.getColumn(1);
		tc.setPreferredWidth(CASE_SENSTIVE_WIDTH);
	  	tc.setCellRenderer(new CheckBoxTableRender());
		tc.setCellEditor(new DefaultCellEditor(new JCheckBox()));

		tc = tcm.getColumn(2);
		tc.setPreferredWidth(OPERATOR_WIDTH);
	  	tc.setCellRenderer(operatorRendor);
		tc.setCellEditor(
		    new DefaultCellEditor(
		        new JComboBox(Compare.OPERATOR_STRING_VALUES)));

		tcm.getColumn(3).setPreferredWidth(VALUE_WIDTH);

    }

    /**
     * Build filtered view of the data
     *
     * @see java.awt.event.ActionListner#actionPerformed
     */
    public void actionPerformed(ActionEvent event) {

    	stopTblEdit();
    	
        if (event.getSource() == uncheckAllFields) {
            updateIncludeFlag(Boolean.FALSE);
        } else if (event.getSource() == checkAllFields) {
            updateIncludeFlag(Boolean.TRUE);
        } else if (event.getSource() == uncheckAllRecords) {
        	updateRecordFlag(Boolean.FALSE);
        } else if (event.getSource() == checkAllRecords) {
        	updateRecordFlag(Boolean.TRUE);
        }
    }

    
    
    /**
	 * @see net.sf.RecordEditor.utils.filter.AbstractSaveDetails#getSaveDetails()
	 */
	@Override
	public EditorTask getSaveDetails() {
		stopTblEdit();
		return (
			    new net.sf.RecordEditor.jibx.compare.EditorTask())
			    .setFilter(filter.getExternalLayout());
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
		return messageFld;
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
        
        if (addFieldFilter) {
        	Common.stopCellEditing(filterFieldTbl);
        }
	}


	/**
	 * @return the execute
	 */
	public final JButton getExecute() {
		return execute;
	}
}
