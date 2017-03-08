package net.sf.RecordEditor.re.script;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.tree.TreeNode;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Common.TranslateXmlChars;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.LineCompare;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.External.base.ExternalConversion;
import net.sf.JRecord.cg.schema.LayoutDef;
import net.sf.RecordEditor.edit.display.BaseDisplay;
import net.sf.RecordEditor.re.display.AbstractFileDisplay;
import net.sf.RecordEditor.re.display.AbstractFileDisplayWithFieldHide;
import net.sf.RecordEditor.re.display.DisplayBuilderFactory;
import net.sf.RecordEditor.re.display.DisplayDetails;
import net.sf.RecordEditor.re.display.IDisplayBuilder;
import net.sf.RecordEditor.re.display.IDisplayFrame;
import net.sf.RecordEditor.re.file.AbstractLineNode;
import net.sf.RecordEditor.re.file.AbstractTreeFrame;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.script.extensions.LanguageTrans;
import net.sf.RecordEditor.trove.TIntArrayList;
import net.sf.RecordEditor.utils.common.ActionConstants;
import net.sf.RecordEditor.utils.fileStorage.DataStoreStd;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.screenManager.ReFrame;


/**
 * Class used by Macros (Scripts) and Velocity templates as an
 * Interface to the RecordEditor
 * 
 * <pre>
 *    ScriptData
 *        |
 *        +-------------- List<FileBeingEdited> 
 *        |                 (getFileList)
 *        |                       |
 *        |                       +------------------------ File Details + ScriptData
 *        |
 *        +------------- File / View / Selected View
 *                                |
 *                                +------------------------ Lines
 * </pre>
 *                                 
 * @author Bruce Martin
 *
 */
public class ScriptData {
	private static final int[] EMPTY_ARRAY = {};
	
    public static final int SELECTION_ONLY         = 1;
    public static final int SELECTION_AND_UPDATE   = 2;
    private static final String sortDescending = "Descending Constant";
    public static IOpenCsvFile openCsvFile;
    public static IOpenReFile openReFile;
 

    public final float JAVA_VERSION = Parameters.JAVA_VERSION ;
    
	public final DisplConstants displayConstants          = new DisplConstants();
	public final ExecDirectoryConstants executeConstansts = new ExecDirectoryConstants();
	public final ActionConstants actionConstants          = new ActionConstants();

	
	public final List<AbstractLine> selectedLines, viewLines, fileLines;

	public final FileView view, file, selectedView;
	public final Object root;
	public final List<List<TreeNode>> nodes = new ArrayList<List<TreeNode>>();
	public final int treeDepth;
	public final boolean onlyData, showBorder;
	public final int recordIdx;
	public final String inputFile;
	public       String outputFile;
	private final String dir, script;
	private final ReFrame initialActiveFrame;
	@SuppressWarnings("rawtypes")
	public final IDisplayFrame initialDataFrame;
	public final AbstractFileDisplay initialTab;
	
	public final String descending = sortDescending;
	
	public final int currentRecordNumber;
	
	public final AbstractLayoutDetails layout;
	
	private List<FileBeingEditted> fileList;


	@SuppressWarnings("rawtypes")
	public ScriptData(
			List<AbstractLine> selectedList,
			FileView view,
			AbstractLineNode r,
			boolean onlyData, boolean showBorder,
			int recordIdx, String outputFile,
			ReFrame frame,
			String scriptFile) {
		super();

		this.selectedLines = selectedList;
		this.script = scriptFile;

		this.initialActiveFrame = frame;
		
		FileView tmpSelected = null;
		
		if (frame instanceof IDisplayFrame) {
			initialDataFrame = (IDisplayFrame) frame;
			initialTab = initialDataFrame.getActiveDisplay();
		} else {
			initialTab = DisplayDetails.getDisplayDetails(frame);

			if (initialTab == null) {
				initialDataFrame = null;
			} else {
				initialDataFrame = initialTab.getParentFrame();
			}
		}
		
		int recNum = 0;
		if (view == null) {
			this.viewLines = null;
			this.fileLines = null;
			this.inputFile = null;
			this.file = null;
			this.layout = null;
		} else {
			this.file      = view.getBaseFile(); 
			this.viewLines = Collections.unmodifiableList(view.getLines());
			this.fileLines = Collections.unmodifiableList(this.file.getLines());
			this.inputFile = view.getFileNameNoDirectory();
			this.layout    = view.getLayout();
			
			if (selectedList == null || selectedList.size() == 0) {
				tmpSelected = view.getView(EMPTY_ARRAY);
			} else {
				tmpSelected = view.getView(selectedList);
			}
			if (initialTab != null) {
				recNum = initialTab.getCurrRow();
			}
		}
		this.view = view;
		this.selectedView = tmpSelected;
		this.currentRecordNumber = recNum;
		
//		file.getLine(0).getFieldValue(0, 0).asBigInteger();
		if (r == null && initialTab != null && initialTab instanceof AbstractTreeFrame) {
			r = ((AbstractTreeFrame) initialTab).getRoot();
		}
		this.root = r;
		this.treeDepth = buildNodeList(this.nodes, r, 0);
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
	
	public List<FileBeingEditted> getFileList() {
		if (fileList == null) {
			fileList = FileBeingEditted.getList(view);
		}
		
		return fileList;
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

		return DisplayDetails.getDisplayDetails(f);
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

	public int confirmYNC(String message) {
		return JOptionPane.showConfirmDialog(initialActiveFrame, message);
	}
	
	public boolean confirmYN(String title, String message) {
		return JOptionPane.showConfirmDialog(initialActiveFrame, message, title, JOptionPane.YES_NO_OPTION) 
			== JOptionPane.YES_OPTION;
	}
	
	public boolean confirmOK(String title, String message) {
		return JOptionPane.showConfirmDialog(initialActiveFrame, message, title, JOptionPane.OK_CANCEL_OPTION) 
			== JOptionPane.OK_OPTION;
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

		return view.getIoProvider().getLineProvider(layout).getLine(layout);
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
		return view.getIoProvider().getLineProvider(layout).getLine(layout, data);
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
		FileView v;
		if (lineList instanceof FileView) {
			v = (FileView) lineList;
		} else {
			v = new FileView(DataStoreStd.newStore(layout, lineList), view, null, false);
		}

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
		return DisplayBuilderFactory.getInstance().newDisplay(displayType, screenName, initialDataFrame, newView.getLayout(), newView, l);
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
		FileView file = null;
		int recordIdx = 0;

		if (frame != null) {
			AbstractFileDisplay disp = DisplayDetails.getDisplayDetails(frame);
			if (disp != null) {
				file = disp.getFileView();
				recordIdx = disp.getLayoutIndex();
			} else if (frame.getDocument() instanceof FileView) {
				file = (FileView) frame.getDocument();
			}
		}

		if (file != null) {
			AbstractFileDisplay initialTab;
			List<AbstractLine> selected = file.getLines();
			if (frame instanceof IDisplayFrame) {
				initialTab = ((IDisplayFrame) frame).getActiveDisplay();
			} else {
				initialTab = DisplayDetails.getDisplayDetails(frame);
			}
			
			if (initialTab != null) {
				selected = initialTab.getSelectedLines();
			}
			data = new ScriptData(
							selected,
			   				file,
			        		null,
			        		false, true,
			        		recordIdx,
			        		file.getFileName() + ".xxx",
			        		frame,
			        		scriptFile);
		}
		

		return data;
	}

	/**
	 * Get LineCompare for use in sorting / comparing lines
	 * 
	 * @param layout schema of the lines
	 * @param recordIndex recordIndex of the lines being compared
	 * 
	 * @return requested line-compare
	 */
	public LineCompare getLineCompareAllFields(LayoutDetail layout, int recordIndex) {
		RecordDetail record = layout.getRecord(recordIndex);
		int[] flds = new int[record.getFieldCount()];
		boolean[] descending = new boolean[flds.length];
		
		for (int i = 0; i < flds.length; i++) {
			flds[i] = i;
		}
		Arrays.fill(descending, false);
		
		return new LineCompare(layout, recordIndex, flds, descending);
	}
	
	/**
	 * Get LineCompare that compares specified fields for use in sorting / comparing lines
	 * 
	 * @param layout schema of the records
	 * @param recordIndex record index of the lines
	 * @param fields fields to be compared
	 * 
	 * @return requested line-compare
	 */
	public LineCompare getLineCompare(LayoutDetail layout, int recordIndex, String... fields) {
		
		if (fields == null || fields.length == 0) {
			return getLineCompareAllFields(layout, recordIndex);
		}
		int yes = 1, no = 2;
		int idx, d;
		TIntArrayList flds = new TIntArrayList(fields.length);
		TIntArrayList desc = new TIntArrayList(fields.length);
		RecordDetail record = layout.getRecord(recordIndex);

		for (int i = 0; i < fields.length; i++) {
			if (fields[i] != sortDescending
			&& (idx = record.getFieldIndex(fields[i]))>= 0) {
				d = no;
				if (i < fields.length-1 && fields[i+1] == sortDescending) {
					d = yes;
				}
				flds.add(idx);
				desc.add(d);
			}
		}
		
		boolean[] descending = new boolean[desc.size()];
		for (int i = 0; i < descending.length; i++) {
			descending[i] = desc.get(i) == yes;
		}
		
		return new LineCompare(layout, recordIndex, flds.toArray(), descending);
	}

	/**
	 * Ask for a file name
	 * 
	 * @param initialFileName initial or default filename
	 * @param open wether to show open or save dialogs
	 * 
	 * @return filename
	 */
	public String askForFileName(String initialFileName, boolean open) {
		JFileChooser fc = new JFileChooser();
		String ret = "";
		int result;
		
		fc.setSelectedFile(new File(initialFileName));
		if (open) {
			result = fc.showOpenDialog(null);
		} else {
			result = fc.showSaveDialog(null);
		}

		if (result == JFileChooser.APPROVE_OPTION) {
			ret = fc.getSelectedFile().getPath();
		}
		return ret;
	}
	
	/**
	 * Ask the user to explain the Record Hierarchy
	 * 
	 * @return Record Hierarchy
	 */
	public Integer[] getRecordHierarchy() {
		CreateRecordTree rt = new CreateRecordTree(view);
		
		if (rt.isOk()) {
			return rt.treeDisplay.getParent();
		}
		return null;
	}
	
	/**
	 * Get the directory where the file is being edited resides
	 * 
	 * @return irectory where the file is being edited resides
	 */
	public String getDirectoryName() {
		String name = null;
		if (view != null && isPresent(name = view.getFileName())) {
			name = Parameters.addPathSeperator(new File(name).getParent());
		}
		
		return name;
	}
	
	public Dimension getScreenDimensions() {
		return new Dimension(ReFrame.getDesktopWidth(), ReFrame.getDesktopHeight());
	}
	
	private boolean isPresent(String s) {
		return s != null && s.length() > 0;
	}
	
	/**
	 * Create ScriptData for a screen
	 * 
	 * @param screen display screen
	 * @return
	 */
	public ScriptData newScriptData(BaseDisplay screen) {
		return new ScriptData(
				null, screen.getFileView(), null, onlyData, false,
				0, outputFile + ".xxx", screen.getParentFrame(), "");
	}

	/**
	 * Edit a Csv file
	 * 
	 * @param fileName file to be openned
	 * @param font character-set of the file
	 * @param delim field delimiter
	 * @param quote Quote character
	 * @see net.sf.RecordEditor.re.script.IOpenCsvFile#open(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public AbstractFileDisplayWithFieldHide openCsvFile(String fileName, String font, String delim, String quote) {
		return openCsvFile.open(fileName, font, delim,  quote, false);
	}

	/**
	 * Edit a Csv file
	 *  
	 * @param fileName file to be openned
	 * @param font character-set of the file
	 * @param delim field delimiter
	 * @param quote Quote character
	 * @param embededCr wether there are embededCr's 
	 * @see net.sf.RecordEditor.re.script.IOpenCsvFile#open(java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	public AbstractFileDisplayWithFieldHide openCsvFile(String fileName, String font, String delim, String quote,
			boolean embededCr) {
		return openCsvFile.open(fileName, font,  delim, quote, embededCr);
	}

	/**
	 * Edit a specified file
	 * 
	 * @param fileName filename to edit
	 * @param layoutName layout (schema) of the file.
	 * 
	 * @return new Screen;
	 */
	public AbstractFileDisplayWithFieldHide openFile(String fileName, String layoutName) {
		return openReFile.openFile(fileName, layoutName);
	}

	
	public LayoutDef getCodeGenLayoutDef() {
		if (layout == null || !(layout instanceof LayoutDetail)) {
			return null;
		}
		return new LayoutDef((LayoutDetail) layout, layout.getLayoutName(), null);
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
	
	public static class DisplConstants {
		public int ST_INITIAL_EDIT     = IDisplayBuilder.ST_INITIAL_EDIT    ;
		public int ST_INITIAL_BROWSE   = IDisplayBuilder.ST_INITIAL_BROWSE  ;
		public int ST_LIST_SCREEN      = IDisplayBuilder.ST_LIST_SCREEN     ;
		public int ST_RECORD_SCREEN    = IDisplayBuilder.ST_RECORD_SCREEN   ;
		public int ST_RECORD_TREE      = IDisplayBuilder.ST_RECORD_TREE     ;
		public int ST_CB2XML_TREE      = IDisplayBuilder.ST_CB2XML_TREE     ;
		public int ST_LINES_AS_COLUMNS = IDisplayBuilder.ST_LINES_AS_COLUMNS;
		public int ST_LINE_TREE_CHILD  = IDisplayBuilder.ST_LINE_TREE_CHILD ;
		public int ST_DOCUMENT         = IDisplayBuilder.ST_DOCUMENT        ;
		public int ST_COLORED_DOCUMENT = IDisplayBuilder.ST_COLORED_DOCUMENT;
		
		public int ST_LINE_TREE_CHILD_EXPAND_PROTO  = IDisplayBuilder.ST_LINE_TREE_CHILD_EXPAND_PROTO;
	}
}
