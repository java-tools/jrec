package net.sf.RecordEditor.re.script;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;
import javax.swing.tree.TreeNode;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Common.TranslateXmlChars;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.External.ExternalConversion;
import net.sf.RecordEditor.re.display.AbstractFileDisplay;
import net.sf.RecordEditor.re.display.AbstractFileDisplayWithFieldHide;
import net.sf.RecordEditor.re.display.DisplayBuilderFactory;
import net.sf.RecordEditor.re.display.IChildDisplay;
import net.sf.RecordEditor.re.display.IDisplayBuilder;
import net.sf.RecordEditor.re.display.IDisplayFrame;
import net.sf.RecordEditor.re.file.AbstractLineNode;
import net.sf.RecordEditor.re.file.AbstractTreeFrame;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.script.extensions.LanguageTrans;
import net.sf.RecordEditor.utils.common.DefaultActionHandler;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.fileStorage.DataStoreStd;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.screenManager.ReFrame;


/**
 * Class used by Macros (Scripts) and Velocity templates as an
 * Interface to the RecordEditor
 *
 * @author Bruce Martin
 *
 */
public class ScriptData {

	public final IDisplayBuilder displayConstants = DisplayBuilderFactory.getInstance();
	public final IExecDirectoryConstants executeConstansts = new ExecConsts();
	public final ReActionHandler actionConstants = new DefaultActionHandler();

	public final List<AbstractLine> selectedLines, viewLines, fileLines;

	public final FileView view;
	public final Object root;
	public final List<List<TreeNode>> nodes = new ArrayList<List<TreeNode>>();
	public final int treeDepth;
	public final boolean onlyData, showBorder;
	public final int recordIdx;
	public final String inputFile, outputFile;
	private final String dir, script;
	private final ReFrame initialActiveFrame;
	@SuppressWarnings("rawtypes")
	public final IDisplayFrame initialDataFrame;
	public final AbstractFileDisplay initialTab;

	@SuppressWarnings("rawtypes")
	public ScriptData(
			List<AbstractLine> selectedList,
			FileView view,
			AbstractLineNode root,
			boolean onlyData, boolean showBorder,
			int recordIdx, String outputFile,
			ReFrame frame,
			String scriptFile) {
		super();

		this.selectedLines = selectedList;
		this.script = scriptFile;

		this.initialActiveFrame = frame;
		if (frame instanceof IDisplayFrame) {
			initialDataFrame = (IDisplayFrame) frame;
			initialTab = initialDataFrame.getActiveDisplay();
		} else {
			initialTab = getFileDisplay(frame);

			if (initialTab == null) {
				initialDataFrame = null;
			} else {
				initialDataFrame = initialTab.getParentFrame();
			}
		}

		if (view == null) {
			this.viewLines = null;
			this.fileLines = null;
			this.inputFile = null;
		} else {
			this.viewLines = Collections.unmodifiableList(view.getLines());
			this.fileLines = Collections.unmodifiableList(view.getBaseFile().getLines());
			this.inputFile = view.getFileNameNoDirectory();
		}
		this.view = view;
		this.root = root;
		this.treeDepth = buildNodeList(this.nodes, root, 0);
		this.onlyData = onlyData;
		this.showBorder = showBorder;
		this.recordIdx = recordIdx;
		this.outputFile = outputFile;

		dir = (new java.io.File(view.getFileName())).getParent();
//		System.out.println("Directory ??? ~ " + dir);
		LanguageTrans.clear();


//		System.out.println(Conversion.replace(new StringBuilder("1\n 2\n  3\n4\n     5"), "\n", "\n# "));
//		System.out.println(Conversion.replace
//				(new StringBuilder(" \n    SQL: {0}"
//				+ "\nMessage: {1}\n"), "\n", "\n# "));
	}


	private int buildNodeList(List<List<TreeNode>> nodeList, AbstractLineNode node, int lvl) {
		if (node == null) return lvl;

		int ret = lvl + 1;
		List<TreeNode> nodes = Arrays.asList(node.getPath());
		if (nodes==null) {
			nodes=new ArrayList<TreeNode>(1);
			nodes.add(node);
		}

		nodeList.add((nodes));
		for (int i = 0; i < node.getChildCount(); i++) {
			ret = Math.max(ret, buildNodeList(nodeList, (AbstractLineNode) node.getChildAt(i), lvl));
		}

		return ret;
	}

	/**
	 * Convert character like <> to html variables
	 * @param o input string
	 * @return value with html chars converted to variables
	 */
	public String htmlCharsToVars(Object o) {
		if (o == null) return "";
		return TranslateXmlChars.replaceXmlCharsStr(o.toString());
	}



	/**
	 * author specific function
	 * @param type
	 * @param val
	 * @return
	 */
	public String trans4getText(String type, String val) {
		StringBuilder b;
		if ("u".equalsIgnoreCase(type)) {
			StringTokenizer t = new StringTokenizer(val, ":");
			String sep = "";

			b = new StringBuilder();

			while (t.hasMoreTokens()) {
				b.append(sep);
				try {
					b.append(LangConversion.MSG_NAMES[Integer.parseInt(t.nextToken().trim())]);
				} catch (Exception e) {
				}
				sep = " : ";
			}
		} else {
			b = new StringBuilder(val);

			if ("c".equalsIgnoreCase(type)) {
				Conversion.replace(b, "<br>", "<br>\n# ");
				Conversion.replace(b, "<br/>", "<br/>\n# ");
				Conversion.replace(b, "<p>", "\n# <p>");
				Conversion.replace(b, "\n", "\n# ");

				Conversion.replace(b, "</h1>", "</h1>\n# ");
				Conversion.replace(b, "</h2>", "</h2>\n# ");
				Conversion.replace(b, "</h3>", "</h3>\n# ");
				Conversion.replace(b, "</ul>", "</ul>\n# ");
				Conversion.replace(b, "</ol>", "</ol>\n# ");
				Conversion.replace(b, "</table>", "<table>\n# ");

				Conversion.replace(b, "<table>", "\n# <table>");
				Conversion.replace(b, "<tr>", "\n# <tr>");
				Conversion.replace(b, "<li>", "\n# <li>");
				Conversion.replace(b, "<pre>", "\n# <pre>");

			} else if ("m".equalsIgnoreCase(type)) {
				Conversion.replace(b, "\\", "\\\\");
				Conversion.replace(b, "\"", "\\\"");
				Conversion.replace(b, "\n", "\\n\"\n\"");
				Conversion.replace(b, "<br>", "<br>\"\n\"");
				Conversion.replace(b, "<br/>", "<br/>\"\n\"");
				Conversion.replace(b, "<p>", "\"\n\"<p>");

				Conversion.replace(b, "</h1>", "</h1>\"\n\"");
				Conversion.replace(b, "</h2>", "</h2>\"\n\"");
				Conversion.replace(b, "</h3>", "</h3>\"\n\"");
				Conversion.replace(b, "</ol>", "</ol>\"\n\"");
				Conversion.replace(b, "</ul>", "</ul>\"\n\"");
				Conversion.replace(b, "</table>", "</table>\"\n\"");

				Conversion.replace(b, "<table>", "\"\n\"<table>");
				Conversion.replace(b, "<tr>", "\"\n\"<tr>");
				Conversion.replace(b, "<li>", "\"\n\"<li>");
				Conversion.replace(b, "<pre>", "\"\n\"<pre>");
			} else if ("g".equalsIgnoreCase(type)) {
				Conversion.replace(b, "<br>", "\n\n<br>\n\n");
				Conversion.replace(b, "<br/>", "\n\n<br/>\n\n");
				Conversion.replace(b, "<p>", "\n\n<p>\n\n");
				Conversion.replace(b, "<b>", "");
				Conversion.replace(b, "</b>", "");
				Conversion.replace(b, "<i>", "");
				Conversion.replace(b, "</i>", "");

				Conversion.replace(b, "<h1>", "<h1>\n\n");
				Conversion.replace(b, "<h2>", "<h2>\n\n");
				Conversion.replace(b, "<h3>", "<h3>\n\n");
				Conversion.replace(b, "</h1>", "\n\n</h1>\n\n");
				Conversion.replace(b, "</h2>", "\n\n</h2>\n\n");
				Conversion.replace(b, "</h3>", "\n\n</h3>\n\n");
				Conversion.replace(b, "</ol>", "</ol>\n\n");
				Conversion.replace(b, "</ul>", "</ul>\n\n");
				Conversion.replace(b, "</table>", "</table>\n\n");

				Conversion.replace(b, "<table>", "\n\n<table>");
				Conversion.replace(b, "<tr>", "\n\n<tr>");
				Conversion.replace(b, "<li>", "\n\n<li>\n\n");
				Conversion.replace(b, "</li>", "\n\n</li>");
				Conversion.replace(b, "<td>", "<td>\n\n");
				Conversion.replace(b, "</td>", "\n\n</td>");
				Conversion.replace(b, "<pre>", "\n\n<pre>\n\n");
				Conversion.replace(b, "</pre>", "\n\n</pre>\n\n");
			}
		}
		return b.toString();
	}

	public String getLangTrans(String lang, String key) {
		String ret = "";

		try {
			ret = LanguageTrans.getTrans(dir + "/Trans" + lang + ".txt").get(Integer.parseInt(key.trim()));
		} catch (Exception e) {
		}
		return ret;
	}

	public ReFrame getCurrentFrame() {
		return ReFrame.getActiveFrame();
	}

	public AbstractFileDisplay getCurrentEditTab() {
		ReFrame f = ReFrame.getActiveFrame();

		return getFileDisplay(f);
	}

	/**
	 * method for scripts to notify the RecordEditor that
	 * the display has been changed
	 *
	 * @param viewDataChanged wether the file/view data was changed
	 */
	public final void fireScreenChanged(boolean viewDataChanged) {
		view.fireTableDataChanged();

		if (viewDataChanged) {
			view.setChanged(true);
		}
	}


	public String ask(String message) {
		return JOptionPane.showInputDialog(initialActiveFrame, message);
	}

	public void showMessage(String message) {
		JOptionPane.showMessageDialog(initialActiveFrame, message);
	}

	/**
	 * Create a Line-List for use by macro's
	 * @return a Line-List
	 */
	public List<AbstractLine> getNewLineList() {
		return new ArrayList<AbstractLine>();
	}


	/**
	 * create a new line at the specified position and return the position
	 * @param position to insert line
	 * @return the position of the new line
	 */
	public int insertLine(int position) {
		return view.newLine(position, 0);
	}

	/**
	 * Get a Line for use in a macro
	 * @return a new line
	 */
	@SuppressWarnings("unchecked")
	public AbstractLine newLine() {
		AbstractLayoutDetails layout = view.getLayout();

		return view.getIoProvider().getLineProvider(layout.getFileStructure()).getLine(layout);
	}

	/**
	 * Create a new Line from supplied string data
	 * (only works with text files !!!!
	 * @param data text data to bu7ild the line from
	 * @return newly created line
	 */
	@SuppressWarnings("unchecked")
	public AbstractLine newLine(String data) {
		AbstractLayoutDetails layout = view.getLayout();
		return view.getIoProvider().getLineProvider(layout.getFileStructure()).getLine(layout, data);
	}


	/**
	 * Display a list of lines on the appropriate screen. To be used by script macro's
	 * @param displayType screen Type
	 * @param tabName screen name (may be ignored)
	 * @param lineList list of lines to be displayed
	 * @return
	 */
	public final AbstractFileDisplayWithFieldHide displayList(int displayType, String tabName, List<AbstractLine> lineList) {
		AbstractLayoutDetails layout = view.getLayout();
		FileView v = new FileView(DataStoreStd.newStore(layout, lineList), view, null, false);

		return displayView(displayType, tabName, v);
	}


	/**
	 * Display a view  on the appropriate screen. To be used by script macro's
	 * @param displayType screen Type
	 * @param screenName screen name (may be ignored)
	 * @param lineList list of lines to be displayed
	 * @return
	 */
	@SuppressWarnings({ "unchecked"})
	public final AbstractFileDisplayWithFieldHide displayView(int displayType, String screenName,  FileView newView) {
		AbstractLine l = null;
		if (newView != null && newView.getRowCount() > 0) {
			l = newView.getLine(0);
		}
		return displayConstants.newDisplay(displayType, screenName, initialDataFrame, newView.getLayout(), newView, l);
	}


	/**
	 * Execute a saved task (like a sort tree)
	 * @param location location of the Saved task definition (Xml File)
	 * @param name Task to execute
	 */
	public final AbstractFileDisplay executeSavedTask(String location, String name) {
		if (initialTab == null) {
			throw new RuntimeException("No Active edit screen to act on !!!");
		}
		return executeSavedTask(initialTab, location, name);
	}


	/**
	 * Execute a saved task (like a sort tree)
	 * @param display edit screen to act on
	 * @param location location of the Saved task definition (Xml File)
	 * @param name Task to execute
	 */
	@SuppressWarnings("static-access")
	public final AbstractFileDisplay executeSavedTask(AbstractFileDisplay display, String location, String name) {

		if (display == null) {
			throw new RuntimeException("Display is null");
		}

		String dir = "";
		if (executeConstansts.scriptDir.equalsIgnoreCase(location)) {
			if (script != null && ! "".equals(script)) {
				File f = new File(script);
				dir = f.getParent();
			}
		} else if (executeConstansts.sortTreeDir.equalsIgnoreCase(location)) {
			dir = Parameters.getFileName(Parameters.SORT_TREE_SAVE_DIRECTORY);
		} else if (executeConstansts.recordTreeDir.equalsIgnoreCase(location)) {
			dir = Parameters.getFileName(Parameters.RECORD_TREE_SAVE_DIRECTORY);
		} else if (executeConstansts.fieldDir.equalsIgnoreCase(location)) {
			dir = Parameters.getFileName(Parameters.FIELD_SAVE_DIRECTORY);
		} else if (executeConstansts.filterDir.equalsIgnoreCase(location)) {
			dir = Parameters.getFileName(Parameters.FILTER_SAVE_DIRECTORY);
		} else if (executeConstansts.absolute.equalsIgnoreCase(location)) {

		} else {
			throw new RuntimeException("Invalid location: " + location);
		}

		if (! "".equals(dir)) {
			dir = ExternalConversion.fixDirectory(dir);
		}

		String filename = dir + name;

		if ((new File(filename)).exists()) {
			return display.getExecuteTasks().executeSavedTask(filename);
		} else {
			throw new RuntimeException("Task: " + filename + " does not exist");
		}
	}

	/**
	 * Write supplied lines to the supplied file
	 *
	 * @param fileName output file name
	 * @param list lines to be written
	 *
	 * @throws RecordException
	 * @throws IOException
	 */
	public final void write(String fileName, List<AbstractLine> list) throws RecordException, IOException {
		view.writeLinesToFile(fileName, list);
	}

	@SuppressWarnings({ "rawtypes" })
	public static ScriptData getScriptData(ReFrame frame, String scriptFile) {
		ScriptData  data = null;
		AbstractLineNode root = null;
		FileView file = null;
		int recordIdx = 0;

		if (frame != null) {
			AbstractFileDisplay disp = getFileDisplay(frame);
			if (disp != null) {
				file = disp.getFileView();
				recordIdx = disp.getLayoutIndex();
			} else if (frame.getDocument() instanceof FileView) {
				file = (FileView) frame.getDocument();
			}
		}

		if (frame instanceof AbstractTreeFrame) {
			root = ((AbstractTreeFrame) frame).getRoot();
		}

		if (file != null) {
			data = new ScriptData(
							file.getLines(),
			   				file,
			        		root,
			        		false, true,
			        		recordIdx,
			        		file.getFileName() + ".xxx",
			        		frame,
			        		scriptFile);
		}

		return data;
	}

	@SuppressWarnings("rawtypes")
	private static AbstractFileDisplay getFileDisplay(ReFrame frame) {

		AbstractFileDisplay parentTab = null;
		if (frame instanceof AbstractFileDisplay) {
			parentTab = (AbstractFileDisplay) frame;
		} else if (frame instanceof IChildDisplay) {
			parentTab = ((IChildDisplay) frame).getSourceDisplay();
		} else if (frame instanceof IDisplayFrame) {
			parentTab = ((IDisplayFrame) frame).getActiveDisplay();
		}
		return parentTab;
	}

	/**
	 * Directory constants for use in Scripts:
	 * <pre>
	 *
	 *   RecordEditorData.executeSavedTask(
	 *           RecordEditorData.executeConstansts.sortDir,
	 *   	    "Script_DTAR020_SortTree_1.xml");
	 *
	 * </pre>
	 *
	 * @author Bruce Martin
	 *
	 */
	public static class ExecConsts implements IExecDirectoryConstants {

	}
}
