/**
 *
 */
package net.sf.RecordEditor.edit.display;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.re.display.AbstractFileDisplay;
import net.sf.RecordEditor.re.display.AbstractFileDisplayWithFieldHide;
import net.sf.RecordEditor.re.display.DisplayBuilderFactory;
import net.sf.RecordEditor.re.display.IDisplayBuilder;
import net.sf.RecordEditor.re.display.IDisplayFrame;
import net.sf.RecordEditor.re.file.AbstractLineNode;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.tree.AbstractLineNodeTreeParser;
import net.sf.RecordEditor.re.tree.LineNodeChild;
import net.sf.RecordEditor.re.tree.TreeParserXml;
import net.sf.RecordEditor.utils.common.Common;


/**
 * @author mum
 *
 */
public class DisplayBuilderImp implements IDisplayBuilder {

	public static AbstractFileDisplayWithFieldHide doOpen(FileView file, int initialRow, boolean pBrowse) {

		AbstractFileDisplayWithFieldHide display = null;
		AbstractLayoutDetails layoutDtls = file.getLayout();


		if (layoutDtls.hasChildren()) {
			LineTreeChild displ = new LineTreeChild(file, new LineNodeChild("File", file), true, 0);
			new DisplayFrame(displ);
			display = displ;

			if (file.getRowCount() == 0 && ! pBrowse) {
				display.insertLine(0);
			}
		} else if (layoutDtls.isXml()) {
			LineTree displ1 = new LineTree(file, TreeParserXml.getInstance(), true, 1);
			display = displ1;
			new DisplayFrame(displ1);
		} else {
			LineList displ2 = LineList.newLineList(layoutDtls, file, file);
			display = displ2;
			display.setCurrRow(initialRow, -1, -1);
			new DisplayFrame(displ2);

			if (file.getRowCount() == 0 && ! pBrowse) {
				display.insertLine(0);
			}
		}

		return display;
	}

	/**
	 * @see net.sf.RecordEditor.re.display.IDisplayBuilder#newDisplay(int, String, IDisplayFrame, AbstractLayoutDetails, FileView, AbstractLine)
	 */
	@Override
	public AbstractFileDisplayWithFieldHide newDisplay(
			int screenType, String screenName,
			IDisplayFrame<? extends AbstractFileDisplay> parentFrame,
			AbstractLayoutDetails group, FileView viewOfFile,
			int lineNo) {
		switch (screenType) {
		case ST_INITIAL_BROWSE:
		case ST_INITIAL_EDIT:
			return doOpen(viewOfFile, lineNo, screenType == ST_INITIAL_BROWSE);
		case ST_LIST_SCREEN:
			return addToScreen(
						parentFrame,
						LineList.newLineList(group, viewOfFile, viewOfFile.getBaseFile())
					);
		case ST_RECORD_SCREEN:
			return addToScreen(
					parentFrame,
					new LineFrame(screenName, viewOfFile, lineNo, true)
				);
		case ST_RECORD_TREE:
			return addToScreen(
					parentFrame,
					new LineFrameTree(viewOfFile, lineNo, true)
				);
		case ST_LINES_AS_COLUMNS:
			return addToScreen(
					parentFrame,
					new LinesAsColumns(viewOfFile)
				);
		case ST_COLORED_DOCUMENT:
		case ST_DOCUMENT:
			 addToScreen(
					parentFrame,
					new DocumentScreen(viewOfFile, screenType == ST_COLORED_DOCUMENT)
			 );
		default:
			break;
		}
		return null;
	}

	/**
	 * @see net.sf.RecordEditor.re.display.IDisplayBuilder#newDisplay(int, String, IDisplayFrame, AbstractLayoutDetails, FileView, int)
	 */
	@Override
	public AbstractFileDisplayWithFieldHide newDisplay(
			final int screenType,
			final String screenName,
			final IDisplayFrame<? extends AbstractFileDisplay> parentFrame,
			final AbstractLayoutDetails group, FileView viewOfFile,
			final AbstractLine line) {
		switch (screenType) {
		case ST_LIST_SCREEN:
			return addToScreen(
						parentFrame,
						LineList.newLineList(screenName, group, viewOfFile, viewOfFile.getBaseFile())
					);
		case ST_RECORD_SCREEN:
			String s = screenName;
			if (screenName == null || "".equals(screenName)) {
				s = "Record:";
			}
			return addToScreen(
					parentFrame,
					new LineFrame(s, viewOfFile, line, true)
				);
		case ST_RECORD_TREE:
			return addToScreen(
					parentFrame,
					new LineFrameTree(viewOfFile, line, true)
				);
		case ST_LINES_AS_COLUMNS:
			LinesAsColumns displ;
			if (screenName == null || "".equals(screenName)) {
				displ = new LinesAsColumns(viewOfFile);
			} else {
				displ = new LinesAsColumns(screenName, viewOfFile);
			}
			return addToScreen(
					parentFrame,
					displ
				);
		case ST_COLORED_DOCUMENT:
		case ST_DOCUMENT:
			 addToScreen(
					parentFrame,
					new DocumentScreen(viewOfFile, screenType == ST_COLORED_DOCUMENT)
			 );
		default:
			break;
		}
		return null;
	}

	/**
	 * @see net.sf.RecordEditor.re.display.IDisplayBuilder#newDisplay(int, IDisplayFrame, AbstractLayoutDetails, FileView, AbstractLineNodeTreeParser, boolean, int)
	 */
	@Override
	public AbstractFileDisplayWithFieldHide newDisplay(
			int screenType,
			IDisplayFrame<? extends AbstractFileDisplay> parentFrame,
			AbstractLayoutDetails group, FileView viewOfFile,
			AbstractLineNodeTreeParser treeParser, boolean mainView, int columnsToSkip) {
		switch (screenType) {
		case ST_LIST_SCREEN:
			return addToScreen(
						parentFrame,
						new LineTree(viewOfFile, treeParser, mainView, columnsToSkip)
					);
		case ST_CB2XML_TREE:
			LineTree t = new LineTree(viewOfFile, treeParser, mainView, columnsToSkip);
			addToScreen(parentFrame, t);
			t.cb2xmlStuff();

			return t;
		default:
			break;
		}
		return null;
	}

	public  LineTreeChild newLineTreeChildScreen(
			int screenType,
			@SuppressWarnings("rawtypes") IDisplayFrame df,
			FileView viewOfFile,
			AbstractLineNode rootNode,
			boolean mainView, final int columnsToSkip) {
		LineTreeChild ret = addToScreen(df, new LineTreeChild(viewOfFile, rootNode, mainView, columnsToSkip));

		if (screenType == ST_LINE_TREE_CHILD_EXPAND_PROTO) {
			ret.expandTree("FieldDescriptorProto");
		}
		return ret;
	}


	@SuppressWarnings("unchecked")
	public static final <X extends BaseDisplay> X addToScreen(@SuppressWarnings("rawtypes") IDisplayFrame df, X d) {
		if (Common.OPTIONS.useSeperateScreens.isSelected() || df == null) {
			new DisplayFrame(d);
		} else {
			df.addScreen(d);
		}
		return d;
	}

	public static void register() {
		DisplayBuilderFactory.register(new DisplayBuilderImp());
	}
}
