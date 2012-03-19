/*
 * Created on 25/08/2004
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *   - changed to use ReFrame (from JInternalFrame) which keeps track
 *     of the active form
 *   - changed to use ReActionHandler instead of ActionListener
 */
package net.sf.RecordEditor.layoutEd;

//import info.clearthought.layout.TableLayout;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import net.sf.RecordEditor.re.db.Combo.ComboDB;
import net.sf.RecordEditor.re.db.Combo.ComboPnl;
import net.sf.RecordEditor.re.db.Combo.ComboRec;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.screenManager.ReFrame;




/**
 * This class will display:
 * <ul compact>
 * <li>A pane where the user can select various tables
 * <li>A pane where a table details is updated
 * </ul>
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class ComboCreate extends ReFrame {

	private ComboDB dbTblList = new ComboDB();
	//private DBtableModel<ComboRec> dbComboListModel = null;
	//private ComboJTbl tblComboList;

	private ComboPnl pnlCombo;

	//private JSplitPane splitPane;
	private JScrollPane scrollComboList;

	//private JTextArea message = new JTextArea(" ");

	//private int currRow = 0;
	private int connectionId;
	private static final ComboRec firstRecord = null;



	/**
	 * This class lets the user edit the tables database
	 *
	 * @param dbName Database Name
	 * @param jframe parent frame
	 * @param connectionIdx Database connection index
	 */
	public ComboCreate(final String dbName, final int connectionIdx)   {
		super(dbName, "Create Combo List ", null);

		Container cont = getContentPane();
		//cont.setLayout(new BorderLayout());

		connectionId = connectionIdx;

		defLeftPnl();
		defRightPanel();

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				scrollComboList,
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
	}


	/**
	 * Define the left panel
	 *
	 */
	public void defLeftPnl() {

		dbTblList.setConnection(new ReConnection(connectionId));

	}


	/**
	 * Define the right panel
	 *
	 */
	public void defRightPanel() {

		pnlCombo = new ComboPnl(firstRecord, connectionId);

		pnlCombo.setMinimumSize(new Dimension(400, 50));
		
		pnlCombo.setValues(firstRecord);
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
			dbTblList.checkAndUpdate(rec);
			//pnlTableList.saveTableDetails();
			
			pnlCombo.saveItems();
		}

		return rec;
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
	            } else if (action == ReActionHandler.SAVE_AS) {
	                String newName = JOptionPane.showInputDialog(this, "New Combo Name", rec.getComboName());

	                if (newName != null && ! "".equals(newName)) {
	                    rec = pnlCombo.getClone(newName);
	                }
	            }
	        }
	    } else if (action == ReActionHandler.DELETE) {
	        if (pnlCombo.isOkToDelete()) {
	        	rec = pnlCombo.getValues();
	        	
	        	dbTblList.delete(rec);
                
       			pnlCombo.setValues(null);
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
