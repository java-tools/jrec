/**
 * 
 */
package net.sf.RecordEditor.utils.fileStorage;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.trove.TIntArrayList;
import net.sf.RecordEditor.utils.swing.common.IProgressDisplay;

/**
 * @author Bruce Martin
 *
 */
public class DataStoreLargeView
extends DataStoreBase
implements IDataStoreView<AbstractLine>, ISortNotify, TableModelListener {

//	private IDataStore<? extends AbstractLine> parent;
	private TIntArrayList list;
	
	public DataStoreLargeView(IDataStore<? extends AbstractLine> parent, int... listValue) {
		this(parent, true, listValue);
	}
	protected DataStoreLargeView(IDataStore<? extends AbstractLine> parent, boolean notifyParent, int... listValue) {

		super(parent); 
		this.list = new TIntArrayList(listValue);
		
		int max = parent.size();
		for (int i = list.size() - 1; i >= 0; i--) {
			if (list.get(i) < 0 || list.get(i) >= max) {
				list.removeAt(i);
			}
		}
		
		if (notifyParent) {
			parent.addChildViewRE(this);
		}
	}
	

	public DataStoreLargeView addRange(int start, int end) {
		
		list.ensureCapacity(end - start);
		for (int i = start; i <= end; i++) {
			list.add(i);
		}
		return this;
	}
	
//
//	@Override
//	public void addChildView(ISortNotify notify) {
//		throw new RuntimeException("AddChildView is not supported DataStoreRange");
//	}
	
	@Override
	public void addCopyRE(int pos, IDataStore<? extends AbstractLine> cpyLines,
			IProgressDisplay progressDisplay) {
		parent.addCopyRE(list.get(pos), cpyLines, progressDisplay);
	}

	@Override
	public void extractLinesRE(IDataStore<AbstractLine> cpyLines,
			int[] linesToExtract, IProgressDisplay progressDisplay) {
		
		int[] parentLines = new int[linesToExtract.length]; 
		for (int i = 0; i < parentLines.length; i++) {
			parentLines[i] = list.get(linesToExtract[i]);
		}
		parent.extractLinesRE(cpyLines, parentLines, progressDisplay);
	}


	public void ensureCapacity(int capacity) {
		list.ensureCapacity(capacity);
	}


	@Override
	public AbstractLine getTempLineRE(int index) {
		if (index < 0 || index > list.size()) {
			return null;
		}
		return parent.getTempLineRE(list.get(index));
	}

//	@Override
//	public String getSizeDisplay() {
//		return "";
//	}
//
//	@Override
//	public long getSpace() {
//		return 0;
//	}

	@Override
	public IDataStore<AbstractLine> newDataStoreRE() {
		return new DataStoreLargeView(parent, list.toArray());
	}

	@Override
	public DataStoreLargeView newDataStoreRE(int[] lines) {
		return new DataStoreLargeView(parent, getParentIndexs(lines));
	}
	
	private int[] getParentIndexs(int[] lines) {
		int[] parentLines = new int[lines.length];
		for (int i = 0; i <parentLines.length; i++) {
			parentLines[i] = list.get(lines[i]);
		}
		return parentLines;
	}
	
	@Override
	public void sortRE(Comparator<? super AbstractLine> compare) {
		Collections.sort(this, compare);
	}

	
	@Override
	public void sortRE(int[] rows, Comparator<? super AbstractLine> compare) {
		DataStoreLargeView sv = this.newDataStoreRE(rows);
		
		sv.sortRE(compare);
		for (int i = 0; i < rows.length; i++) {
			list.set(rows[i], sv.list.get(i));
		}
		sv.clear();
	}



//	@Override
//	public AbstractLine addCopy(int index, AbstractLine line) {
//		throw new RuntimeException("addCopy should not be called for views");
//	}
//
//	@Override
//	public AbstractLayoutDetails getLayout() {
//		return parent.getLayout();
//	}
//
//	@Override
//	public void setLayout(AbstractLayoutDetails layout) {
//		parent.setLayout(layout);
//	}
//
//	@Override
//	public boolean add(AbstractLine e) {
//		// TO DO Auto-generated method stub
//		return super.add(e);
//	}

	
	@Override
	public AbstractLine get(int index) {
		if (index < 0 || index > list.size()) {
			return null;
		}

		//System.out.println(" ~~> " + index + " " + list.get(index));
		return parent.get(list.get(index));
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.IDataStore#getLineLength(int)
	 */
	@Override
	public int getLineLengthRE(int index) {
		if (index < 0 || index > list.size()) {
			return 0;
		}

		return parent.getLineLengthRE(list.get(index));
	}
	
	@Override
	public AbstractLine getNewLineRE(int index) {
		if (index < 0 || index > list.size()) {
			return null;
		}

		return parent.getNewLineRE(list.get(index));
	}
	
	
//
//	@Override
//	public byte[] getLineAsBytes(int index) {
//		return parent.getLineAsBytes(list.get(index));
//	}


	@Override
	public AbstractLine set(int index, AbstractLine element) {
		int parentIndex = getParentIndex(element);
		if (parentIndex < 0 ) {
			return null;
		}
		list.set(index, parentIndex);
		return element;
	}

	@Override
	public void add(int index, AbstractLine element) {
		
		//System.out.print(" ==> " + index);
		if (index < 0 || index >= parent.size()) {
			return;
		} 
		int parentIndex = getParentIndex(element);

		//System.out.println(" : " + index + " " + parentIndex);
		list.insert(index, parentIndex);
	}



	@Override
	public boolean addAll(int index, Collection<? extends AbstractLine> c) {
		if (c instanceof DataStoreLargeView) {
			int[] newLines = ((DataStoreLargeView) c).list.toArray();
			list.insert(index, newLines);
			return newLines.length > 0;
		} else if (c instanceof DataStoreRange) {
			DataStoreRange cr = (DataStoreRange) c;
			int[] il = new int[cr.size()];
			for (int i = 0; i < il.length; i++) {
				il[i] = cr.getStart() + i;
			} 
			list.insert(index, il);
			return il.length > 0;
		}
		return super.addAll(index, c);
	}

	@Override
	public boolean remove(Object o) {
		int idx = parent.indexOf(o);
		if (idx >= 0) {
			int idx2 = list.indexOf(idx);
			if (idx2 >= 0) {
				list.remove(idx2);
				parent.remove(idx);
				return true;
			}
		}
		return false;
	}

	@Override
	public AbstractLine remove(int index) {
		if (index < 0 || index > list.size()) {
			return null;
		}
		list.removeAt(index);
		return null;
	}

	@Override
	public int indexOf(Object o) {
		
		int ret = getParentIndex(o);
		if (ret >= 0) {
			ret = list.indexOf(ret);
		}
		
		return ret;
	}

	@Override
	public int lastIndexOf(Object o) {
		int ret = getParentIndex(o);
		if (ret >= 0) {
			ret = list.lastIndexOf(ret);
		}
		
		return ret;
	}

	@Override
	public void clear() {
		list.clear();
	}


	@Override
	public int size() {
		return list.size();
	}
	
	
	@Override
	public int[] asIntArray() { 
		return list.toArray(); 
	}
	
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.SortNotify#updateForParentSort(java.util.Map)
	 */
	@Override
	public final void updateForParentSort(int[] newOrder) {
		for (int i = 0; i < list.size(); i++) {
			if (newOrder[list.get(i)] >= 0) {
				list.set(i, newOrder[list.get(i)]);
			}
		}
	}
	

	
	@Override
	public void tableChangedUpdateRE(TableModelEvent event) {

		int adj = event.getLastRow() - event.getFirstRow() + 1;
		switch (event.getType()) {
		case (TableModelEvent.INSERT):
		    for (int i = 0; i < list.size(); i++) {
		    	if (list.get(i) >= event.getFirstRow()) {
		    		list.set(i, list.get(i) + adj);
		    	}
		    }
		break;
		case (TableModelEvent.DELETE):
		    for (int i = 0; i < list.size(); i++) {
		    	if (list.get(i) > event.getLastRow()) {
		    		list.set(i, list.get(i) - adj);
		    	}
		    }
		break;
		}
	}

//	private int getParentIndex(Object o) {
//		if (o instanceof IChunkLine) {
//			@SuppressWarnings("rawtypes")
//			IChunkLine lc = (IChunkLine) o;
//			return (lc.getActualLine());
//		}
//		
//		return parent.indexOf(o);
//	}

	@Override
	public int getParentIndexRE(int index) {
		return list.get(index);
	}
}
