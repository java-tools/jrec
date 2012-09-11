package net.sf.RecordEditor.re.script;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;
import javax.swing.tree.TreeNode;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.re.file.AbstractLineNode;
import net.sf.RecordEditor.re.file.AbstractTreeFrame;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.script.extensions.LanguageTrans;
import net.sf.RecordEditor.utils.common.TranslateXmlChars;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.screenManager.ReFrame;

public class ScriptData {
	@SuppressWarnings("rawtypes")
	//public final List<List<AbstractLine>> recordList;
	public final List<AbstractLine> selectedLines, viewLines, fileLines;
	@SuppressWarnings("rawtypes")
	public final FileView view;
	public final Object root;
	public final List<List<TreeNode>> nodes = new ArrayList<List<TreeNode>>();
	public final int treeDepth;
	public final boolean onlyData, showBorder;
	public final int recordIdx;
	public final String inputFile, outputFile;
	private final String dir;

	@SuppressWarnings("rawtypes")
	public ScriptData(
			List<AbstractLine> selectedList,
			FileView view,
			AbstractLineNode root,
			boolean onlyData, boolean showBorder,
			int recordIdx, String outputFile) {
		super();

		this.selectedLines =selectedList;

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
		System.out.println("Directory ??? ~ " + dir);
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

	public String htmlCharsToVars(Object o) {
		if (o == null) return "";
		return TranslateXmlChars.replaceXmlCharsStr(o.toString());
	}



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

	public String ask(String message) {
		return JOptionPane.showInputDialog(message);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ScriptData getScriptData(ReFrame frame) {
		ScriptData  data = null;
		AbstractLineNode root = null;
		FileView file = null;
		int recordIdx = 0;

		if (frame == null) {
		} else if (frame instanceof AbstractFileDisplay) {
			AbstractFileDisplay disp = (AbstractFileDisplay) frame;
			file = disp.getFileView();
			recordIdx = disp.getLayoutIndex();
		} else if (frame.getDocument() instanceof FileView) {
			file = (FileView) frame.getDocument();
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
			        		file.getFileName() + ".xxx");
		}

		return data;
	}
}
