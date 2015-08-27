package net.sf.RecordEditor.re.file;

import javax.swing.JFrame;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.utils.fileStorage.DataStoreLarge;
import net.sf.RecordEditor.utils.fileStorage.DataStoreLargeView;
import net.sf.RecordEditor.utils.fileStorage.DataStoreStd;
import net.sf.RecordEditor.utils.fileStorage.IDataStore;

public class ViewDataStore {
	private final IDataStore<AbstractLine> selectedLines;
//	private int limit;
//	private JFrame frame;
	private boolean cont = true;



	public ViewDataStore(AbstractLayoutDetails layout, boolean standardDataStore, JFrame currentFrame, IDataStore<AbstractLine> parentStore) {
		super();

		
//		frame = currentFrame;
		if (parentStore instanceof DataStoreLarge)  {
			selectedLines = new DataStoreLargeView(parentStore);
		} else {
			selectedLines = DataStoreStd.newStore(layout);
		}
			
//        limit = Integer.MAX_VALUE;
//        if (! (standardDataStore)) {
//        	limit = Common.OPTIONS.filterLimit.get();
//        }
	}

	/**
	 * @param line
	 * @return
	 * @see java.util.ArrayList#add(java.lang.Object)
	 */
	public final void add(AbstractLine line) {
		selectedLines.add(line);

//		if (selectedLines.size() >= limit) {
//        	int resp = JOptionPane.showConfirmDialog(
//        		    frame,
//        		    UtMessages.FILTER_LIMIT_REACHED.get(Integer.toString(limit)),
////        		    LangConversion.convert(
////        		    		"The Filter limit of {0} has been reached, do you wish to continue?",
////        		    		Integer.toString(limit)),
//        		    "",
//        		    JOptionPane.YES_NO_OPTION);
//        	if (resp == JOptionPane.YES_OPTION) {
//        		limit +=  Common.OPTIONS.filterLimit.get();
//        	} else {
//        		Common.logMsgRaw(
//        				UtMessages.FILTER_LIMIT_EXCEEDED.get(Integer.toString(limit)),
////        				LangConversion.convert(
////        						"Filter limit of {0} exceeded; only the first {0} lines in the filtered view",
////        						Integer.toString(limit)),
//        				null);
//        		cont = false;
//        	}
//        }
	}


	public int size() {
		return selectedLines.size();
	}

	/**
	 * @return the selectedLines
	 */
	public IDataStore<AbstractLine> getSelectedLines() {
		return selectedLines;
	} 

	public final boolean continueProcessing() {
		return cont;
	}
}
