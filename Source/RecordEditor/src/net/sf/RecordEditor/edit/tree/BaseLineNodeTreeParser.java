package net.sf.RecordEditor.edit.tree;

import java.util.ArrayList;

import net.sf.RecordEditor.edit.file.FileView;

public abstract class BaseLineNodeTreeParser {

	public BaseLineNodeTreeParser() {
		super();
	}

	/**
	 * @see net.sf.RecordEditor.edit.tree.AbstractLineNodeTreeParser#parse(net.sf.RecordEditor.edit.file.FileView)
	 */
	public LineNode parse(FileView view) {
		LineNode root = new LineNode("File", view, -1);
		
		parseAppend(view, root);
		
		return root;
	}

	/**
	 * @see net.sf.RecordEditor.edit.tree.AbstractLineNodeTreeParser#parseAppend(net.sf.RecordEditor.edit.file.FileView, net.sf.RecordEditor.edit.tree.LineNode)
	 */
	public void parseAppend(FileView view, LineNode node) {
		parseAppend(view, node, 0, view.getRowCount() - 1);
	}
	
	/**
	 * @see net.sf.RecordEditor.edit.tree.AbstractLineNodeTreeParser#parseAppend(net.sf.RecordEditor.edit.file.FileView, net.sf.RecordEditor.edit.tree.LineNode, int, int)
	 */
	public abstract void parseAppend(FileView view, LineNode root, int start, int end);


	/**
	 * Create "Array" of existing child nodes.
	 * @param root base node to create list from
	 * @param start Starting line number
	 * @param end last line number
	 * @return Array of nodes
	 */
	protected ArrayList<LineNode> buildExisting(LineNode root, int start, int end) {
		ArrayList<LineNode> existing = new ArrayList<LineNode>();
		LineNode node = (LineNode) root.getNextNode();
		
		for (int i = start; i <= end; i++) {
			existing.add(null);
		}
		
		while (node != null && node.getLineNumber() <= end) {
			if (node.getLineNumber() >= start) {
				existing.set(node.getLineNumber() - start, node);
			}
			node = (LineNode) node.getNextNode();
		}
		
		
		return existing;
	}


	/**
	 * Convert object to string
	 * @param o Object to be converted
	 * @return String equivalent
	 */
	protected String toString(Object o) {
		String s = "";
		if (o != null) {
			s = o.toString();
		}
		return s;
	}

}