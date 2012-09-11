package net.sf.RecordEditor.edit.open;

import java.util.ArrayList;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.edit.display.common.AbstractFileDisplayWithFieldHide;
import net.sf.RecordEditor.re.file.AbstractLineNode;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.script.AbstractFileDisplay;
import net.sf.RecordEditor.re.script.IDisplayBuilder;
import net.sf.RecordEditor.re.script.IDisplayFrame;
import net.sf.RecordEditor.re.tree.AbstractLineNodeTreeParser;

public class DisplayBuilderFactory implements IDisplayBuilder {

	private final static DisplayBuilderFactory instance = new DisplayBuilderFactory();

	private ArrayList<IDisplayBuilder> builders = new ArrayList<IDisplayBuilder>();

//	static {
//		try {
//			@SuppressWarnings("rawtypes")
//			Class c = Class.forName("net.sf.RecordEditor.edit.display.DisplayBuilderImp");
//			if (c != null) {
//				Object o = c.newInstance();
//				if (o instanceof IDisplayBuilder) {
//					register((IDisplayBuilder) o);
//				}
//			}
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * @return the instance
	 */
	public static IDisplayBuilder getInstance() {
		return instance;
	}

	public static void  register(IDisplayBuilder builder) {
		instance.builders.add(builder);
	}

	public static AbstractFileDisplay newLineList(
				final IDisplayFrame<? extends AbstractFileDisplay> frame,
				final AbstractLayoutDetails<?, ?> group,
				final FileView<?> viewOfFile,
				final FileView<?> masterFile) {

		return instance.newDisplay(ST_LIST_SCREEN, "", frame, group, viewOfFile, 0);
	}

	public static AbstractFileDisplayWithFieldHide newLineTree(
			@SuppressWarnings("rawtypes") IDisplayFrame parentFrame,
			@SuppressWarnings("rawtypes") FileView viewOfFile,
			AbstractLineNodeTreeParser treeParser,
			boolean mainView, final int columnsToSkip) {

		return instance.newDisplay(ST_LIST_SCREEN, parentFrame, viewOfFile.getLayout(), viewOfFile, treeParser, mainView, columnsToSkip);
	}

	public static AbstractFileDisplayWithFieldHide newLineTreeChild(
						@SuppressWarnings("rawtypes") IDisplayFrame df,
						@SuppressWarnings("rawtypes") FileView viewOfFile,
						AbstractLineNode rootNode,
						boolean mainView, final int columnsToSkip) {

		return instance.newLineTreeChildScreen(ST_LINE_TREE_CHILD, df, viewOfFile, rootNode, mainView, columnsToSkip);
	}

	@Override
	public AbstractFileDisplayWithFieldHide newDisplay(int screenType, String screenName,
			IDisplayFrame<? extends AbstractFileDisplay> parentFrame,
			AbstractLayoutDetails<?, ?> group, FileView<?> viewOfFile,
			int lineNo) {
		AbstractFileDisplayWithFieldHide disp = null;
		int i;

		for (i = builders.size() - 1; disp == null && i >= 0; i--) {
			disp = builders.get(i).newDisplay(screenType, screenName, parentFrame, group, viewOfFile, lineNo);
		}
		System.out.println("== getDisplay " + screenType + " " + i);

		return disp;
	}

	@Override
	public AbstractFileDisplayWithFieldHide newDisplay(int screenType, String screenName,
			IDisplayFrame<? extends AbstractFileDisplay> parentFrame,
			AbstractLayoutDetails<?, ?> group, FileView<?> viewOfFile,
			@SuppressWarnings("rawtypes") AbstractLine line) {
		AbstractFileDisplayWithFieldHide disp = null;

		for (int i = builders.size() - 1; disp == null && i >= 0; i--) {
			disp = builders.get(i).newDisplay(screenType, screenName, parentFrame, group, viewOfFile, line);
		}

		return disp;
	}

	@Override
	public AbstractFileDisplayWithFieldHide newDisplay(int screenType,
			IDisplayFrame<? extends AbstractFileDisplay> parentFrame,
			AbstractLayoutDetails<?, ?> group, FileView<?> viewOfFile,
			AbstractLineNodeTreeParser treeParser, boolean mainView,
			int columnsToSkip) {
		AbstractFileDisplayWithFieldHide disp = null;

		for (int i = builders.size() - 1; disp == null && i >= 0; i--) {
			disp = builders.get(i).newDisplay(screenType, parentFrame, group, viewOfFile, treeParser, mainView, columnsToSkip);
		}

		return disp;
	}

	@Override
	public AbstractFileDisplayWithFieldHide newLineTreeChildScreen(
			int screenType,
			@SuppressWarnings("rawtypes") IDisplayFrame df,
			@SuppressWarnings("rawtypes") FileView viewOfFile,
			AbstractLineNode rootNode,
			boolean mainView, final int columnsToSkip) {
		AbstractFileDisplayWithFieldHide disp = null;

		for (int i = builders.size() - 1; disp == null && i >= 0; i--) {
			disp = builders.get(i).newLineTreeChildScreen(screenType, df, viewOfFile, rootNode, mainView, columnsToSkip);
		}

		return disp;
	}
}
