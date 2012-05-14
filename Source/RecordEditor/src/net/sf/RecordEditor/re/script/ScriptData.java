package net.sf.RecordEditor.re.script;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.tree.TreeNode;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.re.file.AbstractLineNode;
import net.sf.RecordEditor.re.file.AbstractTreeFrame;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.common.TranslateXmlChars;
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

	@SuppressWarnings("rawtypes")
	public ScriptData(
			List<AbstractLine> selectedList,
			FileView view,
			AbstractLineNode root,
			boolean onlyData, boolean showBorder,
			int recordIdx, String outputFile) {
		super();

		this.selectedLines = selectedList;

		if (view == null) {
			this.viewLines = null;
			this.fileLines = null;
			this.inputFile = null;
		} else {
			this.viewLines = view.getLines();
			this.fileLines = view.getBaseFile().getLines();
			this.inputFile = view.getFileNameNoDirectory();
		}
		this.view = view;
		this.root = root;
		this.treeDepth = buildNodeList(this.nodes, root, 0);
		this.onlyData = onlyData;
		this.showBorder = showBorder;
		this.recordIdx = recordIdx;
		this.outputFile = outputFile;
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
