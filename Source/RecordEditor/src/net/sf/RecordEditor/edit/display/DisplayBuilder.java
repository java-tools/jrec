package net.sf.RecordEditor.edit.display;

import net.sf.RecordEditor.re.display.IDisplayFrame;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.screenManager.ReFrame;

public class DisplayBuilder {


//	public static void doOpen(FileView file, int initialRow, boolean pBrowse) {
//
//		BaseDisplay display = null;
//		AbstractLayoutDetails layoutDtls = file.getLayout();
//
//		if (layoutDtls.hasChildren()) {
//			display = new LineTreeChild(file, new LineNodeChild("File", file), true, 0);
//			new DisplayFrame(display);
//
//			if (file.getRowCount() == 0 && ! pBrowse) {
//				display.insertLine(0);
//			}
//		} else if (layoutDtls.isXml()) {
//			new DisplayFrame(new LineTree(file, TreeParserXml.getInstance(), true, 1));
//		} else {
//			display = LineList.newLineList(layoutDtls, file, file);
//			display.setCurrRow(initialRow, -1, -1);
//			new DisplayFrame(display);
//
//			if (file.getRowCount() == 0 && ! pBrowse) {
//				display.insertLine(0);
//			}
//		}
//
//
//	}


//	public static AbstractFileDisplay newLineList(
//			final IDisplayFrame<? extends AbstractFileDisplay> frame,
//			final AbstractLayoutDetails group,
//            final FileView viewOfFile,
//            final FileView masterFile) {
//		LineList pnl = LineList.newLineList(group, viewOfFile, masterFile);
//		addToScreen(frame, pnl);
//		return pnl;
//	}

//	public static LineTree newLineTree(
//			@SuppressWarnings("rawtypes") IDisplayFrame df,
//			@SuppressWarnings("rawtypes") FileView viewOfFile,
//			AbstractLineNodeTreeParser treeParser,
//			boolean mainView, final int columnsToSkip) {
//		LineTree ret = new LineTree(viewOfFile, treeParser, mainView, columnsToSkip);
//
//		addToScreen(df, ret);
//
//		return ret;
//	}

//	public static AbstractFileDisplay newLineFrame(
//			@SuppressWarnings("rawtypes") IDisplayFrame df,
//			final FileView viewOfFile,
//	   		final AbstractLine<?> line) {
//		LineFrame ret = new LineFrame(viewOfFile, line, true);
//
//		addToScreen(df, ret);
//
//		return ret;
//
//	}

//	public static AbstractFileDisplay newLinesAsColumns(IDisplayFrame<BaseDisplay> df, FileView viewOfFile) {
//		LinesAsColumns ret = new LinesAsColumns(viewOfFile);
//		addToScreen(df, ret);
//		return ret;
//	}

//	public LineTreeChild newLineTreeChild(
//			IDisplayFrame<? extends AbstractFileDisplay> df,
//			@SuppressWarnings("rawtypes") FileView viewOfFile, AbstractLineNode rootNode,
//			boolean mainView, final int columnsToSkip) {
//		LineTreeChild ret = new LineTreeChild(viewOfFile, rootNode, mainView, columnsToSkip);
//		addToScreen(df, ret);
//		return ret;
//	}

//	public static AbstractFileDisplayWithFieldHide newLineFrameTree(
//			IDisplayFrame<BaseDisplay> df,
//			@SuppressWarnings("rawtypes") final FileView viewOfFile, final int cRow) {
//		LineFrameTree ret = new LineFrameTree(viewOfFile, cRow, true);
//
//		addToScreen(df, ret);
//		return ret;
//	}

//	public static AbstractFileDisplayWithFieldHide newLineFrameTree(
//			IDisplayFrame<BaseDisplay> df,
//			@SuppressWarnings("rawtypes") final FileView viewOfFile,
//	   		 @SuppressWarnings("rawtypes") final AbstractLine line) {
//		LineFrameTree ret = new LineFrameTree(viewOfFile, line, true);
//
//		addToScreen(df, ret);
//		return ret;
//	}

//	@SuppressWarnings("rawtypes")
//	public static LineFrame newLineDisplay(IDisplayFrame df, FileView view, int row) {
//		return newLineDisplay(df, "Record:", view, row);
//	}


//	@SuppressWarnings("rawtypes")
//	public static LineFrame newLineDisplay(IDisplayFrame df, String screenName, FileView view, int row) {
//		LineFrame ret = new LineFrame(screenName, view, row, true);
//
//		addToScreen(df, ret);
//
//		return ret;
//	}

//	public static LineTreeChild newLineTreeChild(
//			@SuppressWarnings("rawtypes") IDisplayFrame df,
//			@SuppressWarnings("rawtypes") FileView viewOfFile,
//			AbstractLineNode rootNode,
//			boolean mainView, final int columnsToSkip) {
//		LineTreeChild ret = new LineTreeChild(viewOfFile, rootNode, mainView, columnsToSkip);
//
//		addToScreen(df, ret);
//
//		return ret;
//	}

//	public static final void addToScreen(@SuppressWarnings("rawtypes") IDisplayFrame df, BaseDisplay d) {
//		if (Common.OPTIONS.useSeperateScreens.isSelected() || df == null) {
//			new DisplayFrame(d);
//		} else {
//			df.addScreen(d);
//			if (df.getActiveDisplay() instanceof ReFrame) {
//				ReFrame.setActiveFrame((ReFrame) df.getActiveDisplay());
//			}
//		}
//	}
}
