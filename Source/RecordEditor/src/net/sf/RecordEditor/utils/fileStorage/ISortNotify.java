package net.sf.RecordEditor.utils.fileStorage;

public interface ISortNotify {

	public abstract int[] asIntArray();
	
	public abstract int size();

	public abstract void updateForParentSort(int[] newOrder);
}