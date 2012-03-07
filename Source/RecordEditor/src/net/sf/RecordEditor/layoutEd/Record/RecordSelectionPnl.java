package net.sf.RecordEditor.layoutEd.Record;

import javax.swing.JTextField;

import net.sf.RecordEditor.layoutEd.utils.TableUpdatePnl;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.jdbc.DBtableModel;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;

@SuppressWarnings("serial")
public class RecordSelectionPnl extends ReFrame implements ReActionHandler {
	
	private TableUpdatePnl<ChildRecordsRec> updatePnl;
	private RecordSelectionJTbl optionsDetailsTbl;
	private DBtableModel<RecordSelectionRec> selectionMdl;
	private JTextField message = new JTextField();
	
	private final String databaseName;
	private final int dbIdx,
					  recordIdentifier,
					  childRecKey;
	
	public RecordSelectionPnl(
			final String dbName,
            final int dbConnectionIdx,
            final int recordId,
            final int childKey) {
		super(null, "Record Selection");
		databaseName = dbName;
		dbIdx = dbConnectionIdx;
		recordIdentifier = recordId;
		childRecKey = childKey;
		
		init_100_SetupFields();
		init_200_LayoutScreen();
	}
	
	private void init_100_SetupFields() {
		RecordSelectionDB db = new RecordSelectionDB();
		db.setConnection(new ReConnection(dbIdx));
		db.setParams(recordIdentifier, childRecKey);
		selectionMdl = new DBtableModel<RecordSelectionRec>(db);
		
		optionsDetailsTbl = new RecordSelectionJTbl(selectionMdl, dbIdx);
	}
	
	private void init_200_LayoutScreen() {
		
		BaseHelpPanel pnl = new BaseHelpPanel();
		
		
		updatePnl = new TableUpdatePnl<ChildRecordsRec>(pnl, this);

		
		pnl.addComponent(1, 3, BasePanel.FILL, BasePanel.GAP,
				BasePanel.FULL, BasePanel.FULL,
				optionsDetailsTbl);
		pnl.addMessage(message);
		
		this.addMainComponent(pnl);
		
		//TODO updatePnl.setTableDtls(comboDetailsModel, optionsDetailsTbl, );

	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.common.ReActionHandler#executeAction(int)
	 */
	@Override
	public void executeAction(int action) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.common.ReActionHandler#isActionAvailable(int)
	 */
	@Override
	public boolean isActionAvailable(int action) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
