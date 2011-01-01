package net.sf.RecordEditor.edit.display.util;


import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import net.sf.JRecord.Details.AbstractLayoutDetails;

import net.sf.RecordEditor.jibx.compare.EditorTask;
import net.sf.RecordEditor.jibx.compare.RecordParent;
import net.sf.RecordEditor.jibx.compare.RecordTree;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.Parameters;
import net.sf.RecordEditor.utils.edit.RecordList;
import net.sf.RecordEditor.utils.filter.AbstractSaveDetails;
import net.sf.RecordEditor.utils.filter.SaveButton;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBox;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBoxRender;
import net.sf.RecordEditor.utils.swing.BmKeyedComboModel;

public class CreateRecordTreePnl  implements AbstractSaveDetails<EditorTask> {
	
	private static final int PARENT_INDEX     = 1;
	private static final double TABLE_HEIGHT  = 8 * BasePanel.NORMAL_HEIGHT;
	public static final Integer BLANK_PARENT = new Integer(-1);
	
	private static final String[] LAYOUT_COLUMN_HEADINGS = {
		"Record", "Parent Record"
	};
	
	private BaseHelpPanel pnl = new BaseHelpPanel();
	
	private LayoutTblMdl recordMdl = new LayoutTblMdl();
	public final JTable recordTbl = new JTable(recordMdl);
	
	public final JButton execute
	  = new JButton("Build", Common.getRecordIcon(Common.ID_TREE_ICON));
	public final JTextField messageFld = new JTextField();

	
	private AbstractLayoutDetails<?, ?> layout;
	private Integer[] parent;


	public CreateRecordTreePnl() {
		init_200_FormatScreen(false, BasePanel.NORMAL_HEIGHT * 10);
	}
	
	public CreateRecordTreePnl(AbstractLayoutDetails<?, ?> layoutDetails, boolean addSave) {
		super();
		
		setLayout(layoutDetails);
		
		init_200_FormatScreen(addSave, BasePanel.NORMAL_HEIGHT * (recordTbl.getRowCount() + 1));
	}
	
	/**
	 * Set the layout
	 * @param layoutDetails new layout details
	 */
	public final void setLayout(AbstractLayoutDetails<?, ?> layoutDetails) {

		layout = layoutDetails;
		parent = new Integer[layout.getRecordCount()];
		for (int i = 0; i < parent.length; i++) {
			parent[i] = BLANK_PARENT;
		}
		
		init_100_ScreenFields();	
	}
	
	/**
	 * @return the layout
	 */
	public final AbstractLayoutDetails<?, ?> getLayout() {
		return layout;
	}

	/**
	 * Setup screen fields etc
	 *
	 */
	private void init_100_ScreenFields() {
		TableColumn tc;
		RecordList records = new RecordList(layout, false, true, true);
		BmKeyedComboBox editor = new BmKeyedComboBox(new BmKeyedComboModel(records), false);
		//pnl.registerComponent(parentFrame);
		pnl.registerComponent(editor);

		tc = recordTbl.getColumnModel().getColumn(1);
		tc.setCellRenderer(new BmKeyedComboBoxRender(new BmKeyedComboModel(records), false));
		tc.setCellEditor(new DefaultCellEditor(editor));
		
		recordTbl.setRowHeight(Common.COMBO_TABLE_ROW_HEIGHT);
	}
	
	
	/**
	 * 
	 * Format the screen
	 */
	private void init_200_FormatScreen(boolean addSave, double height) {
		
		pnl.setHelpURL(Common.formatHelpURL(Common.HELP_RECORD_TREE));
		
		pnl.addComponent(1, 5,
		         Math.min(TABLE_HEIGHT, height),
		         BasePanel.GAP1,
		         BasePanel.FULL, BasePanel.FULL,
				 recordTbl);

		if (addSave) {
			SaveButton<EditorTask> saveBtn = new SaveButton<EditorTask>(this, 
					Parameters.getFileName(Parameters.RECORD_TREE_SAVE_DIRECTORY)); 
			pnl.addComponent("", saveBtn, execute);
		}
		pnl.setGap(BasePanel.GAP2);
		
		pnl.addMessage(messageFld);

		pnl.done();
//		if (parentFrame != null) {
//			parentFrame.addMainComponent(pnl);
//		}
	}


	
	
	/**
	 * @see net.sf.RecordEditor.utils.filter.AbstractSaveDetails#getSaveDetails()
	 */
	@Override
	public final EditorTask getSaveDetails() {
		RecordTree tree = new net.sf.RecordEditor.jibx.compare.RecordTree();
		int i, j, size;
		
		Common.stopCellEditing(recordTbl);
		size = 0;
		for (i = 0; i < parent.length; i++) {
			if (parent[i] != CreateRecordTreePnl.BLANK_PARENT) {
				size += 1;
			}
		}
		
		j = 0;
		tree.parentRelationship = new RecordParent[size];
		for (i = 0; i < parent.length; i++) {
			if (parent[i] != CreateRecordTreePnl.BLANK_PARENT) {
				tree.parentRelationship[j] = new net.sf.RecordEditor.jibx.compare.RecordParent();
				tree.parentRelationship[j].recordName   = layout.getRecord(i).getRecordName();
				
				tree.parentRelationship[j++].parentName 
					= layout.getRecord(parent[i].intValue()).getRecordName();
			}
		}
		return (new EditorTask())
					.setRecordTree(layout.getLayoutName(), tree);
	}
	
	/**
	 * update tree definition from save details
	 * @param saveDetails details that have been saved
	 */
	public final void setFromSavedDetails(EditorTask saveDetails) {
		setFromSavedDetails(saveDetails.recordTree);
	}
	
	public final void setFromSavedDetails(RecordTree recordTree) {
		int i, idx;
		RecordParent[] parentRel = recordTree.parentRelationship;

		if (parentRel != null) {
			//parent = new Integer[parentRel.length];
			for (i = 0; i < parentRel.length; i++) {
				idx = layout.getRecordIndex(parentRel[i].recordName);
				if (idx >= 0) {
					parent[idx] = Integer.valueOf(layout.getRecordIndex(parentRel[i].parentName));
				}
			}
		}

	}

	
    /**
     * Table model to display records
     *
     * @author Bruce Martin
     *
     */
    private class LayoutTblMdl extends AbstractTableModel {


        /**
         * @see javax.swing.table.TableModel#getColumnCount
         */
        public int getColumnCount() {
            return LAYOUT_COLUMN_HEADINGS.length;
        }


        /**
         * @see javax.swing.table.TableModel#getColumnName
         */
        public String getColumnName(int columnIndex) {
            return LAYOUT_COLUMN_HEADINGS[columnIndex];
        }


        /**
         * @see javax.swing.table.TableModel#getRowCount
         */
        public int getRowCount() {
        	if (layout == null) {
        		return 0;
        	}
            return layout.getRecordCount();
        }


        /**
         * @see javax.swing.table.TableModel#getValueAt
         */
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (columnIndex == PARENT_INDEX) {
                return parent[rowIndex];
            }
            return layout.getRecord(rowIndex).getRecordName();
        }


        /**
         * @see javax.swing.table.TableModel#isCellEditable
         */
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == PARENT_INDEX;
        }


        /**
         * @see javax.swing.table.TableModel#setValueAt
         */
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        	
        	if (aValue == null) {
        		parent[rowIndex] = BLANK_PARENT;
        	} else if (aValue instanceof Integer) {
				parent[rowIndex] = (Integer) aValue;
        	} else {
        		parent[rowIndex] = new Integer(aValue.toString());
        	}
        }
    }
    
	/**
	 * @return the pnl
	 */
	public final BaseHelpPanel getPanel() {
		return pnl;
	}

	/**
	 * @return the parent
	 */
	public final Integer[] getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public final void setParent(Integer[] parent) {
		this.parent = parent;
	}

//	/**
//	 *  Execute standard RecordEditor actions
//	 *
//	 * @param action action to perform
//	 */
//	public void executeAction(int action) {
//		if (action == ReActionHandler.HELP) {
//		    pnl.showHelp();
//		} else {
//			super.executeAction(action);
//		}
//	}
//
//	/**
//	 * Check if action is available
//	 *
//	 * @param action action to be checked
//	 *
//	 * @return wether action is available
//	 */
//	public boolean isActionAvailable(final int action) {
//		return  (action == ReActionHandler.HELP) 
//		    ||  super.isActionAvailable(action);
//	}



}