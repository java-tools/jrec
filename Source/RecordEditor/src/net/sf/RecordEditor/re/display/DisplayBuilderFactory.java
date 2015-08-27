package net.sf.RecordEditor.re.display;

import java.util.ArrayList;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.re.file.AbstractLineNode;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.tree.AbstractLineNodeTreeParser;
import net.sf.RecordEditor.utils.fileStorage.DataStoreStd;

/**
 * Display-Builder class. It will create a screen of the desired
 * Screen-Type for the RecordEditor.
 *
 * @author Bruce Martin
 *
 */
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
	
	public static void startEditorNewFile(AbstractLayoutDetails layout) {
		FileView file = new FileView(
				DataStoreStd.newStore(layout),
				null,
				null,
				false);
		instance.newDisplay(DisplayBuilderFactory.ST_INITIAL_EDIT, "", null, file.getLayout(), file, 0);
	}

	public static void  register(IDisplayBuilder builder) {
		instance.builders.add(builder);
	}

	public static AbstractFileDisplay newLineList(
				final IDisplayFrame<? extends AbstractFileDisplay> frame,
				final AbstractLayoutDetails group,
				final FileView viewOfFile,
				final FileView masterFile) {

		return instance.newDisplay(ST_LIST_SCREEN, "", frame, group, viewOfFile, 0);
	}

	public static AbstractFileDisplayWithFieldHide newLineTree(
			IDisplayFrame<? extends AbstractFileDisplay> parentFrame,
			FileView viewOfFile,
			AbstractLineNodeTreeParser treeParser,
			boolean mainView, final int columnsToSkip) {

		return instance.newDisplay(ST_LIST_SCREEN, parentFrame, viewOfFile.getLayout(), viewOfFile, treeParser, mainView, columnsToSkip);
	}

	public static AbstractFileDisplayWithFieldHide newLineTreeChild(
						@SuppressWarnings("rawtypes") IDisplayFrame df,
						 FileView viewOfFile,
						AbstractLineNode rootNode,
						boolean mainView, final int columnsToSkip) {

		return instance.newLineTreeChildScreen(ST_LINE_TREE_CHILD, df, viewOfFile, rootNode, mainView, columnsToSkip);
	}

	@Override
	public AbstractFileDisplayWithFieldHide newDisplay(int screenType, String screenName,
			IDisplayFrame<? extends AbstractFileDisplay> parentFrame,
			AbstractLayoutDetails group, FileView viewOfFile,
			int lineNo) {
		AbstractFileDisplayWithFieldHide disp = null;
		int i;

		for (i = builders.size() - 1; disp == null && i >= 0; i--) {
			disp = builders.get(i).newDisplay(screenType, screenName, parentFrame, group, viewOfFile, lineNo);
		}
		//System.out.println("== getDisplay " + screenType + " " + i);

		return disp;
	}

	@Override
	public AbstractFileDisplayWithFieldHide newDisplay(int screenType, String screenName,
			IDisplayFrame<? extends AbstractFileDisplay> parentFrame,
			AbstractLayoutDetails group, FileView viewOfFile,
			AbstractLine line) {
		AbstractFileDisplayWithFieldHide disp = null;

		for (int i = builders.size() - 1; disp == null && i >= 0; i--) {
			disp = builders.get(i).newDisplay(screenType, screenName, parentFrame, group, viewOfFile, line);
		}

		return disp;
	}

	@Override
	public AbstractFileDisplayWithFieldHide newDisplay(int screenType,
			IDisplayFrame<? extends AbstractFileDisplay> parentFrame,
			AbstractLayoutDetails group, FileView viewOfFile,
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
			FileView viewOfFile,
			AbstractLineNode rootNode,
			boolean mainView, final int columnsToSkip) {
		AbstractFileDisplayWithFieldHide disp = null;

		for (int i = builders.size() - 1; disp == null && i >= 0; i--) {
			disp = builders.get(i).newLineTreeChildScreen(screenType, df, viewOfFile, rootNode, mainView, columnsToSkip);
		}

		return disp;
	}
}
