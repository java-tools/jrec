package net.sf.RecordEditor.utils.basicStuff;

import java.util.List;

/**
 * Interface to Retrieve a Table one row / column at a time
 * @author Bruce Martin
 *
 */
public interface IRetrieveTable {

	public abstract boolean hasMoreRows();

	public abstract String nextRow();

	public abstract boolean hasMoreColumns();

	public abstract String nextColumn();

	public abstract List<String> nextRowAsList();

}