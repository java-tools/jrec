package net.sf.RecordEditor.edit.file;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.utils.ExpandLineTree;

public class TreeIteratorBackward implements Iterator<AbstractLine> {

	private List<? extends AbstractLine> list;
	private ArrayList<AbstractLine> expandedLine = null;
	private int currentLine;
	private int currentChild;
	
	/**
	 * @param lines
	 */
	public TreeIteratorBackward(List<? extends AbstractLine> lines, AbstractLine lastLine) {
		this.list = lines;
		
		if (lines == null || lines.size() == 0) {
			
		} else {
			if (lastLine == null) {
				expandedLine = ExpandLineTree.expandTree(list.get(currentLine++));
				currentLine = list.size() - 1;
			} else {		
				expandedLine = ExpandLineTree.expandTo(lastLine);
				currentLine = list.indexOf(ExpandLineTree.getRootLine(lastLine));
			}
			
			currentChild = expandedLine.size() - 1;
		}
	}

	/**
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return expandedLine != null && list != null
			&& (currentLine > 0 || currentChild >= 0);
	}

	/**
	 * @see java.util.Iterator#next()
	 */
	@Override
	public AbstractLine next() {
		if (currentChild <= 0) {
			expandedLine = ExpandLineTree.expandTree(list.get(--currentLine));
			currentChild = expandedLine.size() - 1;
		}
		return expandedLine.get(currentChild--);
	}

	/**
	 * @see java.util.Iterator#remove()
	 */
	@Override
	public void remove() {
		
	}

}
