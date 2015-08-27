/**
 * 
 */
package net.sf.RecordEditor.utils.fileStorage;

import java.util.Comparator;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.utils.swing.common.IProgressDisplay;


/**
 * 
 * This class implements a view as a range of lines in the primary view.
 * it is used to update a view after lines have been pasted to the primary file
 * 
 * @author Bruce Martin
 *
 */
public class DataStoreRange
extends DataStoreBase {

//	private IDataStore<AbstractLine> parent;
	private final int start, size;
	
	public DataStoreRange(IDataStore<? extends AbstractLine> parent, int start, int size) {
		super(parent);
		this.start = start;
		this.size = size;
	}




	@Override
	public void addCopyRE(int pos, IDataStore<? extends AbstractLine> cpyLine,
			IProgressDisplay progressDisplay) {
		throw new RuntimeException("AddCopy is not supported DataStoreRange");
	}

	
	@Override
	public void extractLinesRE(IDataStore<AbstractLine> cpyLines,
			int[] linesToExtract, IProgressDisplay progressDisplay) {
		throw new RuntimeException("Extract is not supported DataStoreRange");
	}


	@Override
	public AbstractLine getTempLineRE(int index) {
		if (index < 0 || index > size) {
			return null;
		}
		return parent.getTempLineRE(start + index);
	}



	protected final int getStart() {
		return start;
	}


	@Override
	public IDataStore<AbstractLine> newDataStoreRE() {
		return new DataStoreRange(parent, start, size);
	}
	

	@Override
	public IDataStore<AbstractLine> newDataStoreRE(int[] lines) {
		return new DataStoreLargeView(this, lines); 
	}


	@Override
	public void sortRE(Comparator<AbstractLine> compare) {
		 throw new RuntimeException("Sort is not allowed");
	}

	
	@Override
	public void sortRE(int[] rows, Comparator<AbstractLine> compare) {
		 throw new RuntimeException("Sort is not allowed");
	}




	@Override
	public AbstractLine get(int index) {
		if (index < 0 || index > size) {
			return null;
		}

		//System.out.println(" ~~> " + index + " " + list.get(index));
		return parent.get(index + start);
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.IDataStore#getLineLength(int)
	 */
	@Override
	public int getLineLengthRE(int lineNo) {
		if (lineNo < 0 || lineNo > size) {
			return 0;
		}

		return parent.getLineLengthRE(start + lineNo);
	}




	@Override
	public AbstractLine getNewLineRE(int index) {
		if (index < 0 || index > size) {
			return null;
		}

		//System.out.println(" ~~> " + index + " " + list.get(index));
		return parent.getNewLineRE(index + start);
	}

//	@Override
//	public byte[] getLineAsBytes(int index) {
//		// TODO Auto-generated method stub
//		return parent.getLineAsBytes(index + start);
//	}


	@Override
	public AbstractLine set(int index, AbstractLine element) {
		throw new RuntimeException("Set is not allowed");
	}

	@Override
	public void add(int index, AbstractLine element) {
		
		throw new RuntimeException("Add is not allowed");
	}


	@Override
	public AbstractLine remove(int index) {
		throw new RuntimeException("Remove is not allowed");
	}

	@Override
	public int indexOf(Object o) {
		
		int ret = getParentIndex(o) - start;
		if (ret < 0 && ret >= size) {
			ret = -1;			
		}
		
		return ret;
	}

	@Override
	public int lastIndexOf(Object o) {
		int ret = getParentIndex(o) - start;
		if (ret < 0 && ret >= size) {
			ret = -1;			
		}
		
		return ret;
	}



	@Override
	public int size() {
		return size;
	}
	
	
	

//
//	private int getParentIndex(Object o) {
//		if (o instanceof IChunkLine) {
//			IChunkLine lc = (IChunkLine) o;
//			return (lc.getActualLine());
//		}
//		
//		return parent.indexOf(o);
//	}
//
//	@Override
//	public int getParentIndex(int index) {
//		return index + start;
//	}
}
