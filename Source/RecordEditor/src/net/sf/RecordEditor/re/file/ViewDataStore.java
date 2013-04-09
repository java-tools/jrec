package net.sf.RecordEditor.re.file;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.fileStorage.DataStoreStd;
import net.sf.RecordEditor.utils.msg.UtMessages;

public class ViewDataStore {
	private final DataStoreStd<AbstractLine> selectedLines;
	private int limit;
	private JFrame frame;
	private boolean cont = true;


	public ViewDataStore(AbstractLayoutDetails layout, boolean standardDataStore, JFrame currentFrame) {
		super();

		selectedLines = DataStoreStd.newStore(layout);
		frame = currentFrame;

        limit = Integer.MAX_VALUE;
        if (! (standardDataStore)) {
        	limit = Common.OPTIONS.filterLimit.get();
        }
	}

	/**
	 * @param line
	 * @return
	 * @see java.util.ArrayList#add(java.lang.Object)
	 */
	public final void add(AbstractLine line) {
		selectedLines.add(line);

        if (selectedLines.size() >= limit) {
        	int resp = JOptionPane.showConfirmDialog(
        		    frame,
        		    UtMessages.FILTER_LIMIT_REACHED.get(Integer.toString(limit)),
//        		    LangConversion.convert(
//        		    		"The Filter limit of {0} has been reached, do you wish to continue?",
//        		    		Integer.toString(limit)),
        		    "",
        		    JOptionPane.YES_NO_OPTION);
        	if (resp == JOptionPane.YES_OPTION) {
        		limit +=  Common.OPTIONS.filterLimit.get();
        	} else {
        		Common.logMsgRaw(
        				UtMessages.FILTER_LIMIT_EXCEEDED.get(Integer.toString(limit)),
//        				LangConversion.convert(
//        						"Filter limit of {0} exceeded; only the first {0} lines in the filtered view",
//        						Integer.toString(limit)),
        				null);
        		cont = false;
        	}
        }
	}


	/**
	 * @return the selectedLines
	 */
	public DataStoreStd<AbstractLine> getSelectedLines() {
		return selectedLines;
	}

	public final boolean continueProcessing() {
		return cont;
	}
}
