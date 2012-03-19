package net.sf.RecordEditor.re.tree;

import net.sf.RecordEditor.re.file.FileView;

public interface AbstractLineNodeTreeParser {

	/**
	 * Parse a whole file into a Tree and return the root node
	 * @param view file view to be parsed
	 * @return Root node of the Tree
	 */
	public abstract LineNode parse(FileView view);

	/**
	 * Rebuild the children of a node from the file view
	 * @param view file to use in the rebuild
	 * @param node node to be rebuilt
	 */
	public abstract void parseAppend(FileView view, LineNode node);

	/**
	 * Rebuild the children of a node from the file view
	 * @param view file to use in the rebuild
	 * @param node node to be rebuilt
	 * @param start where to start scanning from
	 * @param where toend the scanning
	 */
	public abstract void parseAppend(FileView view, LineNode root, int start,
			int end);

}