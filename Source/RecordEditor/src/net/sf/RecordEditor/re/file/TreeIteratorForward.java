package net.sf.RecordEditor.re.file;

import java.util.ArrayList;
import java.util.List;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.utils.ExpandLineTree;
import net.sf.RecordEditor.utils.fileStorage.IDataStoreIterator;

public class TreeIteratorForward implements IDataStoreIterator {

	private List<? extends AbstractLine> list;
	private ArrayList<AbstractLine> expandedLine = null;
	private int currentLine = 0;
	private int currentChild = 0;
	private AbstractLine last;
	
	/**
	 * @param lines
	 */
	public TreeIteratorForward(List<? extends AbstractLine> lines, AbstractLine firstLine) {
		this.list = lines;
		
		if (lines == null || lines.size() == 0) {
			
		} else if (firstLine == null) {
			expandedLine = ExpandLineTree.expandTree(list.get(currentLine));
		} else {		
			expandedLine = ExpandLineTree.expandFrom(firstLine);
			currentLine = list.indexOf(ExpandLineTree.getRootLine(firstLine));
		}
	}

	/**
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return expandedLine != null && list != null
			&& (currentLine < list.size() - 1 || currentChild < expandedLine.size());
	}
	
	

	@Override
	public AbstractLine nextTempRE() {
		return next();
	}

	@Override
	public AbstractLine currentLineRE() {
		return last;
	}

	/**
	 * @see java.util.Iterator#next()
	 */
	@Override
	public AbstractLine next() {
		if (currentChild >= expandedLine.size()) {
			expandedLine = ExpandLineTree.expandTree(list.get(++currentLine));
			currentChild = 0;
		}
		last =  expandedLine.get(currentChild++);
		return last;
	}

	/**
	 * @see java.util.Iterator#remove()
	 */
	@Override
	public void remove() {
		
	}

}
