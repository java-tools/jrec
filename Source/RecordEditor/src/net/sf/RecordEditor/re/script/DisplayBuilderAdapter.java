/**
 *
 */
package net.sf.RecordEditor.re.script;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.edit.display.common.AbstractFileDisplayWithFieldHide;
import net.sf.RecordEditor.re.file.AbstractLineNode;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.tree.AbstractLineNodeTreeParser;

/**
 * @author mum
 *
 */
public class DisplayBuilderAdapter implements IDisplayBuilder {

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.script.IDisplayBuilder#newDisplay(int, java.lang.String, net.sf.RecordEditor.re.script.IDisplayFrame, net.sf.JRecord.Details.AbstractLayoutDetails, net.sf.RecordEditor.re.file.FileView, int)
	 */
	@Override
	public AbstractFileDisplayWithFieldHide newDisplay(int screenType,
			String screenName,
			IDisplayFrame<? extends AbstractFileDisplay> parentFrame,
			AbstractLayoutDetails<?, ?> group, FileView<?> viewOfFile,
			int lineNo) {
		return null;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.script.IDisplayBuilder#newDisplay(int, java.lang.String, net.sf.RecordEditor.re.script.IDisplayFrame, net.sf.JRecord.Details.AbstractLayoutDetails, net.sf.RecordEditor.re.file.FileView, net.sf.JRecord.Details.AbstractLine)
	 */
	@Override
	public AbstractFileDisplayWithFieldHide newDisplay(int screenType,
			String screenName,
			IDisplayFrame<? extends AbstractFileDisplay> parentFrame,
			AbstractLayoutDetails<?, ?> group, FileView<?> viewOfFile,
			@SuppressWarnings("rawtypes") AbstractLine line) {
		return null;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.script.IDisplayBuilder#newDisplay(int, net.sf.RecordEditor.re.script.IDisplayFrame, net.sf.JRecord.Details.AbstractLayoutDetails, net.sf.RecordEditor.re.file.FileView, net.sf.RecordEditor.re.tree.AbstractLineNodeTreeParser, boolean, int)
	 */
	@Override
	public AbstractFileDisplayWithFieldHide newDisplay(int screenType,
			IDisplayFrame<? extends AbstractFileDisplay> parentFrame,
			AbstractLayoutDetails<?, ?> group, FileView<?> viewOfFile,
			AbstractLineNodeTreeParser treeParser, boolean mainView,
			int columnsToSkip) {
		return null;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.script.IDisplayBuilder#newLineTreeChildScreen(int, net.sf.RecordEditor.re.script.IDisplayFrame, net.sf.RecordEditor.re.file.FileView, net.sf.RecordEditor.re.file.AbstractLineNode, boolean, int)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public AbstractFileDisplayWithFieldHide newLineTreeChildScreen(
			int screenType, IDisplayFrame df,  FileView viewOfFile,
			AbstractLineNode rootNode, boolean mainView, int columnsToSkip) {
		return null;
	}

}
