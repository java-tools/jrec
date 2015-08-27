package net.sf.RecordEditor.layoutEd.panels;

import java.util.List;

import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sf.RecordEditor.layoutEd.Record.ChildRecordsTblMdl;
import net.sf.RecordEditor.re.db.Record.ChildRecordsRec;
import net.sf.RecordEditor.re.db.Record.RecordDB;
import net.sf.RecordEditor.re.db.Record.RecordRec;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;

@SuppressWarnings("serial")
public class RecordSelectionFrm extends ReFrame {

	private IntRecordSelectPnl[] pnls;
	private JTabbedPane tab = new JTabbedPane();
	private final int dbConnectionIdx;
	private final int recordIdentifier;
    private final List<ChildRecordsRec> parents;
    private final ChildRecordsTblMdl childMdl;

	private JTextField message = new JTextField();

	private int currIndex;

	public RecordSelectionFrm(
			final String dbName,
            final int dbConnectionIdx,
            final int recordIdentifier,
            final ChildRecordsTblMdl childMdl,
            final int idx) {
		super(  dbName, "",
				LangConversion.convert(LangConversion.ST_FRAME_HEADING, "Record Selection:")
					+ " " + getRecordName(dbConnectionIdx, recordIdentifier),
				"", null);

		this.dbConnectionIdx = dbConnectionIdx;
		this.recordIdentifier = recordIdentifier;
		this.parents = childMdl.getRecords();
		this.childMdl = childMdl;

		init_200_LayoutScreen();

		currIndex = idx;
		tab.setSelectedIndex(idx);

		init_300_Listners();

		this.setVisible(true);
		this.setToMaximum(true);
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.screenManager.ReFrame#windowClosing()
	 */
	@Override
	public void windowClosing() {
		saveCurrentPnl();

		super.windowClosing();
	}

	private void init_200_LayoutScreen() {

		BaseHelpPanel pnl = new BaseHelpPanel();
		RecordSelectionPnl recSel;
		SelectionTreeTablePnl summary = new SelectionTreeTablePnl(dbConnectionIdx, "", childMdl);

		super.addCloseOnEsc(pnl);
		pnls = new IntRecordSelectPnl[parents.size() + 1];
		for (int i = 0; i < pnls.length-1; i++) {
			recSel = new RecordSelectionPnl(this.dbConnectionIdx, recordIdentifier, parents.get(i));
			pnls[i] = recSel;

			tab.addTab(getRecordName(this.dbConnectionIdx, parents.get(i).getChildRecordId()), recSel.panel);

			pnl.registerComponentRE(recSel.panel);
		}
		pnls[pnls.length - 1] = summary;

		SwingUtils.addTab(tab, "LayoutEdit_RecordSelection", "Summary", summary.panel);

		pnl.registerComponentRE(summary.panel);

		pnl.addComponentRE(1, 3, BasePanel.FILL, BasePanel.GAP,
				BasePanel.FULL, BasePanel.FULL,
				tab);
		pnl.addMessage(message);

		this.addMainComponent(pnl);
	}



	private void init_300_Listners() {
		tab.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (currIndex >= 0 && currIndex < pnls.length) {
					pnls[currIndex].save(false);
				}
				currIndex = tab.getSelectedIndex();
				if (currIndex >= 0 && currIndex < pnls.length) {
					pnls[currIndex].load();
				}
			}
		});
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.common.ReActionHandler#executeAction(int)
	 */
	@Override
	public void executeAction(int action) {
		switch (action) {
		case  ReActionHandler.SAVE:
			saveCurrentPnl();

			break;
		}
	}


	private void saveCurrentPnl() {

		int idx = tab.getSelectedIndex();
		if (idx >= 0 && idx < pnls.length) {
			pnls[idx].save(false);
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
