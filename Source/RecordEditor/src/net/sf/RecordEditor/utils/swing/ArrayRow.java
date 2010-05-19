/*
 * Created on 20/10/2005
 *
 */
package net.sf.RecordEditor.utils.swing;

import net.sf.JRecord.Common.AbsRow;


/**
 * Implementation of a row (or record) based on an array
 *
 * @author bymartin
 *
 */
public class ArrayRow implements AbsRow {

	private Object[] row;

	/**
	 * create a Row (or record) based on an Array of Objects
	 * @param pRow Array to create the row from
	 */
	public ArrayRow(final Object[] pRow) {
		super();
		row = pRow;
	}


	/**
	 * @see net.sf.JRecord.Common.AbsRow#getField(int)
	 */
	public Object getField(int fldNum) {
		return row[fldNum];
	}
}
