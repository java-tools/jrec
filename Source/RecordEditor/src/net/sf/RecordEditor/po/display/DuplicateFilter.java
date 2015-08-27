package net.sf.RecordEditor.po.display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JCheckBox;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.edit.display.BaseDisplay;
import net.sf.RecordEditor.po.def.PoField;
import net.sf.RecordEditor.re.display.AbstractFileDisplay;
import net.sf.RecordEditor.re.display.DisplayBuilderFactory;
import net.sf.RecordEditor.re.display.IDisplayBuilder;
import net.sf.RecordEditor.re.display.IDisplayFrame;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.fileStorage.IDataStore;
import net.sf.RecordEditor.utils.fileStorage.DataStoreStd;
import net.sf.RecordEditor.utils.lang.ReAbstractAction;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;

public class DuplicateFilter implements ActionListener {

	private final ReFrame displayFrame;
	private final BaseHelpPanel panel = new BaseHelpPanel("PoDuplicateFilter");
	private final IDisplayFrame<? extends AbstractFileDisplay> parentFrame;
	private final FileView view;

	private final JCheckBox removeDuplicateChk = new JCheckBox();
//	private final JCheckBox keepMsgIdChk = new JCheckBox();
	private final JCheckBox removeBlankDuplicateChk = new JCheckBox();
	private final JButton runBtn = new JButton("Generate Duplicate View");


	public DuplicateFilter(final IDisplayFrame<? extends AbstractFileDisplay> sourceFrame, final FileView fileTbl) {

		this.displayFrame = new ReFrame(fileTbl.getFileNameNoDirectory(), "Duplicate Filter",
                fileTbl.getBaseFile());
		this.parentFrame = sourceFrame;
		this.view = fileTbl;

		init_100_initialise();
		init_200_LayoutScreen();
		init_300_Finalize();
	}

	private void init_100_initialise() {

		removeDuplicateChk.setSelected(true);
//		keepMsgIdChk.setSelected(true);
		removeBlankDuplicateChk.setSelected(true);
	}

	private void init_200_LayoutScreen() {

		panel.addLineRE("Remove if (msgctx, msgid*, msgstr*) the same", removeDuplicateChk);
//		panel.addLine("Keep/Merge to MsgId", keepMsgIdChk);
		panel.addLineRE("Remove Blank Duplicate Transalations", removeBlankDuplicateChk)
	         .setGapRE(BaseHelpPanel.GAP2);

		panel.addLineRE("", runBtn)
		     .setGapRE(BaseHelpPanel.GAP2);

		panel.addMessageRE();
	}

	private void init_300_Finalize() {

		runBtn.addActionListener(this);

		displayFrame.setDefaultCloseOperation(ReFrame.DISPOSE_ON_CLOSE);
		displayFrame.addMainComponent(panel);

		displayFrame.setVisible(true);
		displayFrame.setToMaximum(false);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public final void actionPerformed(ActionEvent arg0) {
		IDataStore<AbstractLine> ds = getDuplicates();

		if (ds != null && ds.size() > 0) {
			FileView newView = new FileView(ds, view.getBaseFile(), null);

			AbstractFileDisplay l = DisplayBuilderFactory.getInstance().newDisplay(
					IDisplayBuilder.ST_LIST_SCREEN, "Duplicates", parentFrame, view.getLayout(), newView, 0);
			displayFrame.setVisible(false);

			l.getParentFrame().setToActiveTab(l);
			l.executeAction(ReActionHandler.AUTOFIT_COLUMNS);
			l.getParentFrame().executeAction(ReActionHandler.ADD_CHILD_SCREEN_RIGHT);
			l.getParentFrame().moveToFront();
		} else {
			panel.setMessageTxtRE("No Duplicates found !!!");
		}
	}

	/**
	 *
	 * @return Duplicate set
	 */
	public final IDataStore<AbstractLine> getDuplicates() {

		int en = view.getRowCount();
		HashMap<String, AbstractLine> lineMap = new HashMap<String, AbstractLine>(en * 13 / 10);
		HashSet<AbstractLine> lineSet = new HashSet<AbstractLine>(en * 13 / 10);

		String key;
		AbstractLine l;


		for (int i = en - 1; i >= 0; i--) {
			l = view.getLine(i);
			key = getKey(l);

			if (lineMap.containsKey(key)) {
				AbstractLine newLine = lineMap.get(key);
				if ("Y".equals(l.getField(0,  PoField.obsolete.fieldIdx))) {

				} else if (removeDuplicateChk.isSelected() && checkEqivalent(l, newLine)) {
					view.deleteLine(i);
//				} else  if (keepMsgIdChk.isSelected()
//						&&  checkForMsgId(lineMap, lineSet, key, l, newLine)) {
				} else  if (removeBlankDuplicateChk.isSelected()
						&& checkForSpace(lineMap, lineSet, i, key, l, newLine)) {
				} else {
					lineSet.add(newLine);
					lineSet.add(l); //
				}
			} else {
				lineMap.put(key, l);
			}
		}

		if (lineSet.size() > 0) {
			IDataStore<AbstractLine> ds = DataStoreStd.newStore(view.getLayout(), lineSet);
			ds.sortRE(new Cmp());

			for (int i = ds.size()-2; i >= 0; i--) {
				if (checkEqivalent(ds.get(i+1), ds.get(i))) {
					view.deleteLine(ds.get(i+1));
					ds.remove(i+1);
				}
			}
			return ds;
		}
		return null;
	}

	private static String getKey(AbstractLine l) {
		return	  l.getFieldValue(0, PoField.msgctxt.fieldIdx).asString()
				+ "\t~.~\t"
				+ l.getFieldValue(0, PoField.msgid.fieldIdx).asString()
				+ l.getFieldValue(0, PoField.msgidPlural.fieldIdx).asString()
				;
	}


	private static boolean checkEqivalent(AbstractLine l1, AbstractLine l2) {

		return l1.getFieldValue(0, PoField.msgid.fieldIdx)
					.asString().equals(l2.getFieldValue(0, PoField.msgid.fieldIdx).asString())
			&& l1.getFieldValue(0, PoField.msgidPlural.fieldIdx)
					.asString().equals(l2.getFieldValue(0, PoField.msgidPlural.fieldIdx).asString())
			&& l1.getFieldValue(0, PoField.msgstr.fieldIdx)
					.asString().equals(l2.getFieldValue(0, PoField.msgstr.fieldIdx).asString())
			&& l1.getFieldValue(0, PoField.msgstrPlural.fieldIdx)
					.asString().equals(l2.getFieldValue(0, PoField.msgstrPlural.fieldIdx).asString())
		;
	}


//	private boolean checkForMsgId(
//			HashMap<String, AbstractLine> lineMap,
//			HashSet<AbstractLine> lineSet,
//			String key,
//			AbstractLine l1, AbstractLine l2) {
//		return checkForMsgId_100_check1(lineMap, lineSet, key, l1, l2)
//			|| checkForMsgId_100_check1(lineMap, lineSet, key, l2, l1);
//	}
//
//
//	private boolean checkForMsgId_100_check1(
//			HashMap<String, AbstractLine> lineMap,
//			HashSet<AbstractLine> lineSet,
//			String key,
//			AbstractLine l1, AbstractLine l2) {
//
//		if (l1.getFieldValue(0, PoField.msgid.fieldIdx).asString().equals("")
//		&& (! l2.getFieldValue(0, PoField.msgid.fieldIdx).asString().equals(""))
//		&& l2.getFieldValue(0, PoField.msgid.fieldIdx).asString()
//			.equals(l1.getFieldValue(0, PoField.msgstr.fieldIdx).asString())
//		) {
//			if ( l2.getFieldValue(0, PoField.msgstr.fieldIdx).asString().equals("")
//			&& ! l1.getFieldValue(0, PoField.msgstr.fieldIdx).asString().equals("")
//			&&   l1.getFieldValue(0, PoField.msgstrPlural.fieldIdx).asString().equals("")
//			&&   l2.getFieldValue(0, PoField.msgstrPlural.fieldIdx).asString().equals("") ) {
//				try {
//					l2.setField(0, PoField.msgstr.fieldIdx,  l1.getFieldValue(0, PoField.msgstr.fieldIdx).asString());
//					lineMap.put(key, l2);
//					view.deleteLine(l1);
//				} catch (RecordException e) {
//				}
//			}
//		}
//		return false;
//	}


	private boolean checkForSpace(
			HashMap<String, AbstractLine> lineMap,
			HashSet<AbstractLine> lineSet,
			int idx, String key,
			AbstractLine l1, AbstractLine l2) {
		if (	l1.getFieldValue(0, PoField.msgid.fieldIdx).asString().equals("")
			==  l2.getFieldValue(0, PoField.msgid.fieldIdx).asString().equals("")) {
			if (l1.getFieldValue(0, PoField.msgstr.fieldIdx).asString().equals("")
			&&  l1.getFieldValue(0, PoField.msgstrPlural.fieldIdx).asString().equals("")) {
				view.deleteLine(idx);
				return true;
			}
			if (l2.getFieldValue(0, PoField.msgstr.fieldIdx).asString().equals("")
			&&  l2.getFieldValue(0, PoField.msgstrPlural.fieldIdx).asString().equals("")) {
				lineMap.put(key, l1);
				view.deleteLine(l2);
				return true;
			}
		}
		return false;
	}

	/**
	 * Used for testing
	 * @param removeDuplicate  removeDuplicate line values ??
	 * @param removeBlankDuplicate removeDuplicate line with new message = space  ??
	 */
	public final void setOptions(boolean removeDuplicate, boolean removeBlankDuplicate) {

		removeDuplicateChk.setSelected(removeDuplicate);
		removeBlankDuplicateChk.setSelected(removeBlankDuplicate);

	}


	@SuppressWarnings("serial")
	public static ReAbstractAction getDuplicateAction(
			final BaseDisplay sourceFrame) {

		return new ReAbstractAction("Show Duplicate Rows") {
            public void actionPerformed(ActionEvent e) {
            	new DuplicateFilter(sourceFrame.getParentFrame(), sourceFrame.getFileView());
            };
        };
	}

	private static class Cmp implements Comparator<AbstractLine> {

		/* (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(AbstractLine o1, AbstractLine o2) {
			return getSortKey(o1).compareToIgnoreCase(getSortKey(o2));
		}

		private String getSortKey(AbstractLine o1) {
			return    getKey(o1)
					+ "~\t~"
					+ o1.getFieldValue(0, PoField.msgstr.fieldIdx).asString()
					+ "~\t~"
					+ o1.getFieldValue(0, PoField.MSGSTR_PLURAL_INDEX).asString()
					+ "~\t~"
					+ o1.getFieldValue(0, PoField.comments.fieldIdx).asString()
					;
		}
	}

}
