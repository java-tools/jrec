package net.sf.RecordEditor.layoutEd.panels;



import net.sf.RecordEditor.re.db.Record.ChildRecordsRec;
import net.sf.RecordEditor.re.db.Record.RecordDB;
import net.sf.RecordEditor.re.db.Record.RecordRec;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.screenManager.ReFrame;


@SuppressWarnings("serial")
public class RecordSelectionFrm1 extends ReFrame {

	private RecordSelectionPnl pnl;

	private final int dbConnectionIdx;
	private final int recordIdentifier;
    private final ChildRecordsRec parent;


	public RecordSelectionFrm1(
			final String dbName,
            final int dbConnectionIdx,
            final int recordIdentifier,
            final ChildRecordsRec parent) {
		super(  dbName,
				"",
				LangConversion.convert(LangConversion.ST_FRAME_HEADING, "Record Selection:")
					+ " " + getRecordName(dbConnectionIdx, parent.getChildRecordId()),
				"",
				null);

		this.dbConnectionIdx = dbConnectionIdx;
		this.recordIdentifier = recordIdentifier;
		this.parent = parent;

		init_200_LayoutScreen();

		this.setVisible(true);
		this.setToMaximum(false);
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.screenManager.ReFrame#windowClosing()
	 */
	@Override
	public void windowClosing() {
		pnl.save(true);

		super.windowClosing();
	}

	private void init_200_LayoutScreen() {


		pnl = new RecordSelectionPnl(this.dbConnectionIdx, recordIdentifier, parent);

		this.addMainComponent(pnl.panel);
	}






	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.common.ReActionHandler#executeAction(int)
	 */
	@Override
	public void executeAction(int action) {
		switch (action) {
		case  ReActionHandler.SAVE:
			pnl.save(false);

			break;
		}
	}

	/**
	 * @see net.sf.RecordEditor.utils.common.ReActionHandler#isActionAvailable(int)
	 */
	@Override
	public boolean isActionAvailable(int action) {
		return action == ReActionHandler.SAVE;
	}


	private static String getRecordName(int dbIdx, int recId) {
		RecordRec rec = getRecord(dbIdx, recId);

		if(rec == null) return "";

		return rec.getRecordName();
	}


	private static RecordRec getRecord(int dbIdx, int recId) {
		RecordDB rDb = new RecordDB();
		RecordRec ret;
		rDb.setConnection(new ReConnection(dbIdx));
		rDb.setSearchRecordId(RecordDB.opEquals, recId);

		ret = rDb.fetch();
		rDb.close();
		return ret;
	}

}
