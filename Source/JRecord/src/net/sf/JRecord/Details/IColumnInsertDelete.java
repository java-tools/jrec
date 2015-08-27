package net.sf.JRecord.Details;

public interface IColumnInsertDelete {

	public abstract void deleteColumns(int[] cols);
	
	public abstract int insetColumns(int column, String[] colValues);
}
