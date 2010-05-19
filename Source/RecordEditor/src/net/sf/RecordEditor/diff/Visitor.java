package net.sf.RecordEditor.diff;

import java.util.ArrayList;

import jlibdiff.Hunk;
import jlibdiff.HunkAdd;
import jlibdiff.HunkChange;
import jlibdiff.HunkDel;
import jlibdiff.HunkVisitor;;

public class Visitor extends HunkVisitor {

	
	private LineBufferedReader oldR, newR;
	private ArrayList<LineCompare> oldList, newList, oldChanged, newChanged;
	
	private int oldPos = 1;
	private int newPos = 1;
	
	private boolean fin = false;
	
	
	public Visitor(LineBufferedReader oldReader, LineBufferedReader newReader) {
		int size = Math.max(oldReader.getCount(), newReader.getCount());
		oldR = oldReader;
		newR = newReader;
		
		oldList = new ArrayList<LineCompare>(size);
		newList = new ArrayList<LineCompare>(size);
		
		oldChanged = new ArrayList<LineCompare>(size/2);
		newChanged = new ArrayList<LineCompare>(size/2);
}
	
	@Override
	public void visitHunkAdd(HunkAdd arg0) {
/*		System.out.println("Add   ... " + arg0.lowLine(0) + " " + arg0.highLine(0)
				+ " ~ " + + arg0.lowLine(1) + " " + arg0.highLine(1)
				+ " / " + arg0.numLines(0) + " / " + arg0.numLines(1));*/
		
		copySkipped(arg0);
		copyNew(arg0.lowLine(1), arg0.highLine(1), LineCompare.INSERT);
		copyOld(arg0.highLine(0), arg0.highLine(0), LineCompare.UNCHANGED);
		addNull(oldList, arg0.lowLine(1), arg0.highLine(1), LineCompare.INSERT);


		copy(   newChanged, newR, arg0.lowLine(1), arg0.highLine(1), LineCompare.INSERT);
		addNull(oldChanged, arg0.lowLine(1), arg0.highLine(1), LineCompare.INSERT);
		
//		System.out.println("Insert Add Null " + (arg0.highLine(1) - arg0.lowLine(1) + 1 )
//				+ " " + arg0.highLine(1) + " " + arg0.lowLine(1));

	}

	@Override
	public void visitHunkChange(HunkChange arg0) {
		
		int oldDiff = arg0.highLine(0) - arg0.lowLine(0);
		int newDiff = arg0.highLine(1) - arg0.lowLine(1);
		//arg0.getNewContents().
		//arg0.get
		//System.out.println();
		//System.out.println();
		//System.out.println("Change  ... " + arg0.lowLine(0) + " " + arg0.highLine(0)
		//		+ " ~ " + + arg0.lowLine(1) + " " + arg0.highLine(1)
		//		+ " / " + arg0.numLines(0) + " / " + arg0.numLines(1) );
		
		copySkipped(arg0);
		copyOld(arg0.lowLine(0), arg0.highLine(0), LineCompare.CHANGED);
		copyNew(arg0.lowLine(1), arg0.highLine(1), LineCompare.CHANGED);
		
		copy(oldChanged, oldR, arg0.lowLine(0), arg0.highLine(0), LineCompare.CHANGED);
		//System.out.println("~~~> " + oldChanged.size() + " " + arg0.lowLine(0) + " " + arg0.highLine(0));
		copy(newChanged, newR, arg0.lowLine(1), arg0.highLine(1), LineCompare.CHANGED);
		
		//System.out.print(" ==> " + oldDiff + " " + newDiff);
		if (oldDiff > newDiff) {
			int count = oldDiff - newDiff;
			for (int i = 1; i <= count; i++) {
				oldChanged.get(oldChanged.size() - i).code = LineCompare.DELETED;
				oldList.get(oldList.size() - i).code = LineCompare.DELETED;
			}
			addNull(newChanged, 1, count, LineCompare.DELETED);			
			addNull(newList, 1, count, LineCompare.DELETED);			
		} else if (oldDiff < newDiff) {
			int count = newDiff - oldDiff;
			for (int i = 1; i <= count; i++) {
				newChanged.get(newChanged.size() - i).code = LineCompare.INSERT;
				newList.get(newList.size() - i).code = LineCompare.INSERT;
			}
			addNull(oldChanged, 1, count, LineCompare.INSERT);			
			addNull(oldList, 1, count, LineCompare.INSERT);			
		}
		
//		System.out.println(">>>> " + oldList.size() + " " + newList.size());
}

	@Override
	public void visitHunkDel(HunkDel arg0) {
//		System.out.println("Delete   ... " + arg0.lowLine(0) + " " + arg0.highLine(0)
//				+ " ~ " + + arg0.lowLine(1) + " " + arg0.highLine(1)
//				+ " / " + arg0.numLines(0) + " / " + arg0.numLines(1));
		
		copySkipped(arg0);
		copyOld(arg0.lowLine(0), arg0.highLine(0), LineCompare.DELETED);
		copyNew(arg0.highLine(1), arg0.highLine(1), LineCompare.UNCHANGED);
		addNull(newList, arg0.lowLine(0), arg0.highLine(0), LineCompare.DELETED);

		
		copy(   oldChanged, oldR, arg0.lowLine(0), arg0.highLine(0), LineCompare.DELETED);
		System.out.println("Delete Add Null " + (arg0.highLine(0) - arg0.lowLine(0) + 1 )
				+ " " + arg0.highLine(0) + " " + arg0.lowLine(0));
		addNull(newChanged, arg0.lowLine(0), arg0.highLine(0), LineCompare.DELETED);
	}
	
	
	private void copySkipped(Hunk hunk) {
	
//		System.out.print("Skipped " + oldPos + " "+ (hunk.lowLine(0) - 1));
		copyOld(oldPos, hunk.lowLine(0) - 1, LineCompare.UNCHANGED);
//		System.out.println(" --> " + oldPos);
		
		copyNew(newPos, hunk.lowLine(1) - 1, LineCompare.UNCHANGED);
	}

	private void copyOld(int start, int end, int code) {
		
		copy(oldList, oldR, start, end, code);
		oldPos = end + 1;
	}
	
	private void copyNew(int start, int end, int code) {
	
		copy(newList, newR, start, end, code) ;
		
		newPos = end + 1;
	}
	
	
	private void copy(ArrayList<LineCompare> list, LineBufferedReader reader, int start, int end, int code) {
	
		for (int i = Math.max(1,start); i <= end; i++) {
			//if (code != LineCompare.UNCHANGED)
				//System.out.print(" " + code + ": " + i);
			list.add(new LineCompare(code, i, reader.getLine(i-1)));
		}
//		System.out.println();
	}
	
	private void addNull(ArrayList<LineCompare> list, int start, int end, int code) {

		for (int i = Math.max(1,start); i <= end; i++) {
			list.add(null);
		}
		
		//System.out.println(">>>> " + oldList.size() + " " + newList.size());
	}

	/**
	 * @return the oldList
	 */
	public final ArrayList<LineCompare> getOldList() {
		
		check();
		return oldList;
	}

	/**
	 * @return the newList
	 */
	public final ArrayList<LineCompare> getNewList() {
		
		check();
		return newList;
	}

	/**
	 * @return the oldChanged
	 */
	public final ArrayList<LineCompare> getOldChanged() {
		
		check();
		return oldChanged;
	}

	/**
	 * @return the newChanged
	 */
	public final ArrayList<LineCompare> getNewChanged() {
		
		check();
		return newChanged;
	}
	
	private void check() {
		
		if (! fin) {
			fin = true;
			int oldId = LineCompare.DELETED;
			int newId = LineCompare.INSERT;
			
			//System.out.println("Copy Rest " + oldPos + " " + oldR.getCount() 
			//		+ " / " + newPos + " " + newR.getCount());
			//System.out.println(" / " + (5 / 2));
			if (oldR.getCount() == newR.getCount()) {
				oldId = LineCompare.UNCHANGED;
				newId = LineCompare.UNCHANGED;
			}
			copyOld(oldPos, oldR.getCount(), oldId);
			copyNew(newPos, newR.getCount(), newId);
		}
	}
}
