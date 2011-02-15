/*
 * Created on 29/05/2007
 *
 */
package net.sf.RecordEditor.layoutEd;

//import info.clearthought.layout.TableLayout;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import net.sf.RecordEditor.layoutEd.Parameter.ComboSearch;
import net.sf.RecordEditor.utils.Combo.ComboPnl;
import net.sf.RecordEditor.utils.Combo.ComboRec;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.screenManager.ReFrame;




/**
 * This class will display:
 * <ul compact>
 * <li>A pane where the user can select various Combo Lists
 * <li>A pane where a Combo Details are updated
 * </ul>
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class ComboEdit extends ReFrame {

	
	private ComboSearch comboSearch;

	private ComboPnl pnlCombo;

	private int currRow = 0;
	private int connectionId;
	private ComboRec firstRecord = null;



	/**
	 * This class lets the user edit the Combo Lists
	 *
	 * @param dbName Database Name
	 * @param jframe parent frame
	 * @param connectionIdx Database connection index
	 */
	public ComboEdit(final String dbName, final int connectionIdx)   {
		super(dbName, "Edit Combo Contents ", null);
		boolean free = Common.isSetDoFree(false);
		
		Container cont = getContentPane();
		//cont.setLayout(new BorderLayout());

		connectionId = connectionIdx;

		defLeftPnl();
		defRightPanel();

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				comboSearch,
				pnlCombo);

		cont.add(splitPane);

		pack();

		this.setBounds(1, 1, getWidth() + 60, Math.min(getHeight()+ 5, ReFrame.getDesktopHeight()-1));

		this.addInternalFrameListener(new InternalFrameAdapter() {
			public void internalFrameClosed(final InternalFrameEvent e) {
				saveRecord();
			}
		});

		setVisible(true);
		Common.setDoFree(free, connectionIdx);
	}


	/**
	 * Define the left panel
	 *
	 */
	public void defLeftPnl() {

		comboSearch = new ComboSearch(connectionId, true) {
			protected void changeSearchArguments() {
				if (saveRecord().isUpdateSuccessful()) {
					super.changeSearchArguments();
				}
			} 
		};

		comboSearch.getComboList().addMouseListener(new MouseAdapter() {
			public void mouseReleased(final MouseEvent m) {
				int row = comboSearch.rowAtPoint(m.getPoint());
			    pnlCombo.stopCellEditing();
			    
			    if (row >= 0) {
			    	setRow(row);
			    }
			}
		});
		
		comboSearch.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent event) {

				if (event.getPropertyName().equals(ComboSearch.SELECTION_CHANGED)) {
					saveRecord2db();
					ComboRec rec =   comboSearch.getComboListModel().getRecord(0);
					pnlCombo.setValues(rec);

					comboSearch.getComboListModel().fireTableDataChanged();
				}				
			}
			
		});
		
		firstRecord = comboSearch.getComboListModel().getRecord(0);

		//scrollComboList = new JScrollPane(comboSearch.getComboList());

		comboSearch.setPreferredSize(new Dimension(300, 90));
	}


	/**
	 * Define the right panel
	 *
	 */
	public void defRightPanel() {

		pnlCombo = new ComboPnl(firstRecord, connectionId);

		pnlCombo.setMinimumSize(new Dimension(400, 50));

		pnlCombo.registerComponent(comboSearch.getComboList());
		
		pnlCombo.setValues(firstRecord);
	}

	/**
	 * This method changes the row being displayed
	 *
	 * @param row new row to be displayed
	 */
	private void setRow(final int row) {

		pnlCombo.stopCellEditing();
		if (currRow != row) {
			ComboRec rec = saveRecord();

			if (rec.isUpdateSuccessful()) {
				currRow = row;
				rec =   comboSearch.getComboListModel().getRecord(row);
				pnlCombo.setValues(rec);

				comboSearch.getComboListModel().fireTableDataChanged();
			}
		}
	}



	/**
	 * Save the Table record being displayed
	 *
	 * @return Table record just saved
	 */
	private ComboRec saveRecord() {

		ComboRec rec = pnlCombo.getValues();
		
		if (rec.isUpdateSuccessful() 
		&& rec.getComboName() != null && ! "".equals(rec.getComboName())) {
			boolean free = Common.isSetDoFree(false);
			comboSearch.getComboListModel().setRecord(currRow, rec);

			comboSearch.checkAndUpdate(rec);
			//pnlTableList.saveTableDetails();
			
			pnlCombo.saveItems();
			Common.setDoFree(free, connectionId);
		}

		return rec;
	}
	

	/**
	 * Save the Table record being displayed
	 *
	 * @return Table record just saved
	 */
	private void saveRecord2db() {

		ComboRec rec = pnlCombo.getValues();
		
		if (rec.isUpdateSuccessful() 
		&& rec.getComboName() != null && ! "".equals(rec.getComboName())) {
			comboSearch.checkAndUpdate(rec);
			
			pnlCombo.saveItems();
		}

	}



    /**
     * @see net.sf.RecordEditor.utils.common.ReActionHandler#executeAction(int)
     */
    public void executeAction(int action) {
    	
    	ComboRec rec;

        if (action == ReActionHandler.HELP) {
            pnlCombo.showHelp();
//        } else if (action == ReActionHandler.NEW) {
//        	if (saveRecord().isUpdateSuccessful()) {
//        		pnlCombo.setValues(null);
//        	}        	
	    } else if ((action == ReActionHandler.NEW)
	            || (action == ReActionHandler.SAVE_AS)
	    ) {
	    	rec = saveRecord();

	        if (rec != null && rec.isUpdateSuccessful()) {
	            if (action == ReActionHandler.NEW) {
	            	pnlCombo.setValues(null);
	            	comboSearch.getComboListModel().fireTableDataChanged();
	            	currRow = comboSearch.getComboListModel().getRowCount();
	            } else if (action == ReActionHandler.SAVE_AS) {
	                String newName = JOptionPane.showInputDialog(this, "New Combo Name", rec.getComboName());

	                if (newName != null && ! "".equals(newName)) {
	                    rec = pnlCombo.getClone(newName);
	                    if (rec != null) {
	                    	//rec.setComboName(newName);
	                    	comboSearch.getComboListModel().addRecordtoDB(rec);
	                    	currRow = comboSearch.getComboListModel().getRowCount() - 1;
	                    	//comboSearch.getComboListModel().add(rec);
	                    	comboSearch.getComboListModel().fireTableDataChanged();
	                    }
	                }
	            }
	        }
	    } else if (action == ReActionHandler.DELETE) {
	        if (pnlCombo.isOkToDelete()) {
	        	rec = pnlCombo.getValues();
	        	
	        	currRow = comboSearch.getComboListModel().indexOf(rec);
	        	
	        	if (currRow >= 0 && currRow < comboSearch.getComboListModel().getRowCount()) {
		        	comboSearch.getComboListModel().deleteRecordFromDB(currRow);
		        	comboSearch.getComboListModel().fireTableRowsDeleted(currRow, currRow);
	
	                currRow = Math.min(0, currRow - 1);
	                
	        		if (comboSearch.getComboListModel().getRowCount() > currRow) {
	        			pnlCombo.setValues(comboSearch.getComboListModel().getRecord(currRow));
	        		} else {
	        			pnlCombo.setValues(null);
	        		}
	        	}
	        }
        } else if (action == ReActionHandler.SAVE) {
        	saveRecord();
        } else {
            pnlCombo.getLinesActionHandler().executeAction(action);
        }
    }

    /**
     * @see net.sf.RecordEditor.utils.common.ReActionHandler#isActionAvailable(int)
     */
    public boolean isActionAvailable(int action) {
        return action == ReActionHandler.HELP
        	|| action == ReActionHandler.NEW
        	|| action == ReActionHandler.SAVE
        	|| action == ReActionHandler.SAVE_AS
        	|| action == ReActionHandler.DELETE
    		|| pnlCombo.getLinesActionHandler().isActionAvailable(action);
    }
}
