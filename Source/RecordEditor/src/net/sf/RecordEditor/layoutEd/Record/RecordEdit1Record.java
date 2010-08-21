/*
 * @Author Bruce Martin
 * Created on 6/01/2007
 *
 * Purpose:
 *    Class is used to
 *    1) create new record layout
 *    2) edit a specific record layout
 *
 *  version 0.61 (Bruce Martin)
 *   - correct sizing of message box in split pane
 *   - Better callback support
 *   - Can edit a list of records (with arrow buttons to
 *     move between the records)
 */
package net.sf.RecordEditor.layoutEd.Record;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import net.sf.RecordEditor.utils.LayoutConnectionAction;
import net.sf.RecordEditor.utils.LayoutConnection;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.jdbc.AbsDB;
import net.sf.RecordEditor.utils.jdbc.DBtableModel;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;

/**
 *    Class is used to<ol>
 *    <li> create new record layouts</li>
 *    <li> edit a specific record layout</li>
 *    </ol>
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class RecordEdit1Record extends ReFrame implements ReActionHandler {

    /* Space for message box consists of */
    private static final int SPACE_FOR_MESSAGE_BOX = 50;
    private static final int SPACE_ON_THE_RIGHT = 180;
	private RecordPnl pnlRecord;
	private int connectionIdx;

	private JTextField message     = new JTextField("");

	private RecordDB dbRecord = new RecordDB();

	private LayoutConnection callback;

	private int recordIdx = 0;
	private DBtableModel<ChildRecordsRec> editList;

    /**
     * Edit / Create 1 record
     *
     * @param dbName db name
     * @param dbConnectionIdx connection id
     * @param callbackClass class to be notified of when a layout
     *        has been created / selected
     */
    public RecordEdit1Record(final String dbName,
                             final int dbConnectionIdx,
                             final LayoutConnection callbackClass) {
        super(dbName, "Create Record Layout", null);

        boolean free = Common.isSetDoFree(false);
        connectionIdx = dbConnectionIdx;
        //Connection dbConnection = Common.getDBConnectionLogErrors(connectionIdx);
        dbRecord.setConnection(new ReConnection(connectionIdx));
        callback = callbackClass;

        pnlRecord = new RecordPnl(ReMainFrame.getMasterFrame(),
				  connectionIdx,
				  this, false, false);

        init(null);
        Common.setDoFree(free, dbConnectionIdx);
    }

    /**
     * Edit / Create 1 record
     *
     * @param dbName db name
     * @param dbConnectionIdx connection id
     * @param callbackClass class to be notified of when a layout
     *        has been created / selected
     * @param recordIndex index of record to be edited
     * @param recordList list of records to be editted
     */
    public RecordEdit1Record(final String dbName,
            				 final int dbConnectionIdx,
            				 final LayoutConnection callbackClass,
            				 final int recordIndex,
            				 final DBtableModel<ChildRecordsRec> recordList) {
        super(dbName, "Edit Record Layout", null);

        connectionIdx = dbConnectionIdx;
        //Connection dbConnection = Common.getDBConnectionLogErrors(connectionIdx);

        recordIdx = recordIndex;
        editList  = recordList;
        callback  = callbackClass;

        dbRecord.setConnection(new ReConnection(connectionIdx));

        pnlRecord = new RecordPnl(ReMainFrame.getMasterFrame(),
				  connectionIdx,
				  this, false, true);

        init(getRecord());
    }


    /**
     * Standard initialisation code
     *
     * @param rec record to display
     */
    private void init(RecordRec rec) {
        JSplitPane splitPane;
		ReMainFrame  frame = ReMainFrame.getMasterFrame();
		int height = frame.getDesktop().getHeight() - 1;
		int width  = frame.getDesktop().getWidth() - SPACE_ON_THE_RIGHT;


 	    pnlRecord.setValues(rec);

		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
		        pnlRecord,
		        message);
		splitPane.setDividerLocation(height - SPACE_FOR_MESSAGE_BOX
		        - splitPane.getDividerSize());

        this.getContentPane().add(splitPane);
        this.pack();
		this.setBounds(1, 1, width, height);

		this.addInternalFrameListener(new InternalFrameAdapter() {
			public void internalFrameClosed(final InternalFrameEvent e) {
				saveRecord();
				Common.checkpoint(connectionIdx);
			}
		});

        this.setVisible(true);
    }


	/**
	 * save the Record Layout details being displayed to the user
	 *
	 * @return Record Layout just saved
	 */
	public RecordRec saveRecord() {

		RecordRec rec = pnlRecord.getValues();

		if (rec == null) {
		    message.setText("Can not save, Invalid Record definition ");
		} else {
	        dbRecord.checkAndUpdate(rec);
			if (rec.isUpdateSuccessful()) {
				pnlRecord.saveRecordDetails();
			} else {
				message.setText(pnlRecord.getMsg());
			}
			if (callback != null) {
			    callback.setRecordLayout(rec.getRecordId(), rec.getRecordName(), "");
			}
		}
		//System.out.println(pnlRecord.getMsg());

		return rec;
	}


	/**
	 * Execut the requested action
	 *
	 * @param action action to perform
	 */
	public void executeAction(int action) {

		switch (action) {
		case ReActionHandler.HELP: 							pnlRecord.showHelp();    	break;
		case ReActionHandler.SAVE:							saveRecord();					break;
		case ReActionHandler.NEXT_RECORD:			changeRecord(1);				break;
		case ReActionHandler.PREVIOUS_RECORD:		changeRecord(-1);				break;
		case ReActionHandler.DELETE:
		    if (pnlRecord.isOkToDelete()) {
		        dbRecord.delete(pnlRecord.getValues());
		        pnlRecord.deleteRecordDetails();
		        pnlRecord.setValues(null);
		    }
		    break;
		case ReActionHandler.NEW:
			RecordRec rec1 = saveRecord();
			if (rec1 != null && rec1.isUpdateSuccessful()) {
				pnlRecord.setValues(null);
			}
			break;
		case ReActionHandler.SAVE_AS:
			RecordRec rec = saveRecord();
	
			if (rec != null && rec.isUpdateSuccessful()) {
			    String newName = JOptionPane.showInputDialog(this, "New Record Layout Name", rec.getRecordName());
	
			    if (newName != null && ! "".equals(newName)) {
			        rec = (RecordRec) rec.clone();
			        rec.getValue().setRecordName(newName);
			        pnlRecord.set2clone(rec);
			        saveRecord();
			    }
			}
			break;
	    case ReActionHandler.SAVE_LAYOUT_XML:
	        RecordRec rec2 = saveRecord();

	        if (rec2 != null && rec2.isUpdateSuccessful()) {
	        	new SaveRecordAsXml(connectionIdx, rec2.getRecordId());
	        }
	    	break;
		default:
			 if (pnlRecord != null) {
				 pnlRecord.getRecordActionHandler().executeAction(action);
			 }
		}
	}

    /**
     * @see net.sf.RecordEditor.utils.common.ReActionHandler#isActionAvailable(int)
     */
    public boolean isActionAvailable(int action) {

        //System.out.println("!! " + (pnlRecord == null));
        return (action == ReActionHandler.HELP)
        	|| (action == ReActionHandler.DELETE)
        	|| (action == ReActionHandler.SAVE)
            || (action == ReActionHandler.NEW)
        	|| (action == ReActionHandler.SAVE_AS)
        	|| (action == ReActionHandler.SAVE_LAYOUT_XML)
        	|| (action == ReActionHandler.NEXT_RECORD)
        	|| (action == ReActionHandler.PREVIOUS_RECORD)
        	|| ((pnlRecord != null)
        	 &&  pnlRecord.getRecordActionHandler().isActionAvailable(action));
    }


    /**
     * Change to a selected record;
     * @param inc direction of the change
     */
    private void changeRecord(int inc) {

        int newIdx = recordIdx + inc;

        //System.out.println("~~> " + newIdx + " " + recordIdx);

        if (newIdx >= 0 && newIdx < editList.getRowCount()) {
            saveRecord();
            recordIdx = newIdx;

            pnlRecord.setValues(getRecord());
        }
    }

    /**
     * get a record to be editted
     * @return requested record
     */
    private RecordRec getRecord() {
        ChildRecordsRec r = editList.getRecord(recordIdx);
        if (r == null) {
            return null;
        }

        dbRecord.resetSearch();
        dbRecord.setSearchArg("RecordId", AbsDB.opEquals, "" + r.getChildRecord());
        dbRecord.open();
        RecordRec rec = dbRecord.fetch();
        dbRecord.close();

        pnlRecord.setNextEnabledNext(recordIdx < editList.getRowCount() - 1);
        pnlRecord.setPrevEnabled(recordIdx > 0);

        return rec;
    }

    /**
     * create Action to start this form
     *
     * @param callbackClass Interface back  to invoking class
     *
     * @return requested action
     */
    public static LayoutConnectionAction getAction(LayoutConnection callbackClass) {
        return new LayoutConnectionAction("Create Layout", callbackClass) {
            public void actionPerformed(ActionEvent e) {
                new RecordEdit1Record(getCallback().getCurrentDbName(),
                        			  getCallback().getCurrentDbIdentifier(),
                        			  getCallback());
            }
        };
    }

}
