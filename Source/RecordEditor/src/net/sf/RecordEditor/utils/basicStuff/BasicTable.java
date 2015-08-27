package net.sf.RecordEditor.utils.basicStuff;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Retrieves a table 1 row / column at a time
 * @author Bruce Martin
 *
 */
public class BasicTable implements IRetrieveTable {

	private final StringTokenizer rowTokenizer;
	private StringTokenizer columnTokenizer;
	
	public BasicTable(String tblString) {
		super();
		rowTokenizer = new StringTokenizer(tblString, "\n");
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.basicStuff.ITableReader#hasMoreRows()
	 */
	@Override
	public boolean hasMoreRows() {
		return rowTokenizer.hasMoreElements();
	}

	@Override
	public List<String> nextRowAsList() { 
		ArrayList<String> columns = new ArrayList<String>();
		nextRow();
		
		while (columnTokenizer.hasMoreTokens()) {
			columns.add(columnTokenizer.nextToken());
		}
		return columns;
	}
	
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.basicStuff.ITableReader#nextRow()
	 */
	@Override
	public String nextRow() {
		String s = rowTokenizer.nextToken();
		columnTokenizer = new StringTokenizer(s, "\t");
		return s;
	}
	
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.basicStuff.ITableReader#hasMoreColumns()
	 */
	@Override
	public boolean hasMoreColumns() {
		return columnTokenizer.hasMoreElements();
	}
	
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.basicStuff.ITableReader#nextColumn()
	 */
	@Override
	public String nextColumn() {
		return columnTokenizer.nextToken();
	}
}
