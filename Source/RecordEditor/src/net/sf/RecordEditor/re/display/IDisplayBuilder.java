package net.sf.RecordEditor.re.display;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.re.file.AbstractLineNode;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.tree.AbstractLineNodeTreeParser;

public interface IDisplayBuilder {

	public static int ST_INITIAL_EDIT     = 1;
	public static int ST_INITIAL_BROWSE   = 2;
	public static int ST_LIST_SCREEN      = 3;
	public static int ST_RECORD_SCREEN    = 4;
	public static int ST_RECORD_TREE      = 5;
	public static int ST_CB2XML_TREE      = 6;
	public static int ST_LINES_AS_COLUMNS = 7;
	public static int ST_LINE_TREE_CHILD  = 8;
	public static int ST_LINE_TREE_CHILD_EXPAND_PROTO  = 9;
	public static int ST_DOCUMENT         = 10;
	public static int ST_COLORED_DOCUMENT = 11;

	public AbstractFileDisplayWithFieldHide newDisplay (
			final int screenType,
			final String screenName,
			final IDisplayFrame<? extends AbstractFileDisplay> parentFrame,
			final AbstractLayoutDetails group,
            final FileView viewOfFile,
            final int lineNo);

	public AbstractFileDisplayWithFieldHide newDisplay (
			final int screenType,
			final String screenName,
			final IDisplayFrame<? extends AbstractFileDisplay> parentFrame,
			final AbstractLayoutDetails group,
            final FileView viewOfFile,
            final AbstractLine line);

	public AbstractFileDisplayWithFieldHide newDisplay(
			int screenType,
			IDisplayFrame<? extends AbstractFileDisplay> parentFrame,
			AbstractLayoutDetails group, FileView viewOfFile,
			AbstractLineNodeTreeParser treeParser, boolean mainView, int columnsToSkip);

	public AbstractFileDisplayWithFieldHide newLineTreeChildScreen(
			int screenType,
			@SuppressWarnings("rawtypes") IDisplayFrame df,
			FileView viewOfFile,
			AbstractLineNode rootNode,
			boolean mainView, final int columnsToSkip);
}
