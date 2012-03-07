/*
 * Created on 19/09/2004
 *
 */
package net.sf.RecordEditor.utils.swing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import net.sf.JRecord.Common.AbsRow;


//import jdbcUtil.AbsRecord;


/**
 * This class holds a List of Rows (or records). It is used
 * by the Combobox's and TableModels
 *
 * @author Bruce Martin
 */
public class AbsRowList implements AbstractRowList {

    private int keyIdx, dtlIdx;

    private AbsRow[] rows = null;
    private HashMap<Object, Integer> keyMap = new HashMap<Object, Integer>();

	private boolean toInit = true;
	private boolean sorted;
	private boolean allowNulls;


	/**
	 * Set Row list with a certain size;
	 * @param size list size
	 * @param sort wether to sort the list
	 * @param nullFirstRow wether the first line is null
	 */
	public AbsRowList(final int size,
  		  final boolean sort,
  		  final boolean nullFirstRow) {
		this(0,1,sort,nullFirstRow);
		
		rows = new AbsRow[size];
	}

	/**
	 * This classCreate List or Rows (or records)
	 *
	 * @param keyIndex Column Number of the Key Field
	 * @param dtlIndex Column Number of the Detail (or display field)
	 * @param sort wether to sort the rows
	 * @param nullFirstRow wether the first row in the list is a virtual
	 *        null row
	 */
	public AbsRowList(final int keyIndex,
	        		  final int dtlIndex,
	        		  final boolean sort,
	        		  final boolean nullFirstRow) {
		super();

		this.keyIdx = keyIndex;
		this.dtlIdx = dtlIndex;
		this.sorted = sort;
		this.allowNulls = nullFirstRow;

		//System.out.println("==> " + nullAllowed + " " + allowNulls);
	}


	/**
	 * perform common initialise functions
	 *
	 */
	private void commonInit() {
		int i;
		Integer intObj;

		if (toInit) {
			toInit = false; // setting here to allow loadData to reset it
							// if necessary

			loadData();

			if (sorted) {
				Arrays.sort(rows, this);
			}

			for (i = 0; i < rows.length; i++) {
				if (rows[i] != null) {
					intObj = Integer.valueOf(i);
					keyMap.put(rows[i].getField(keyIdx), intObj);
				}
			}
		}
	}
	
	public final void setRow(int idx, Object key, Object value) {
		Object[] a = new Object[2];
		
		a[0] = key;
		a[1] = value;
		rows[idx] = new ArrayRow(a);
	}


	/**
	 * This method Loads data into the Fields into fields array.
	 * It should be overidden in Sub Classes
	 */
	protected void loadData() {

	}


	/**
	 * load arraylist of ob
	 * @param listOfRows array of rows
	 */
	public final AbsRowList loadData(AbsRow[] listOfRows) {
		rows = listOfRows;
		return this;
	}


	/**
	 * load arraylist of ob
	 * @param a arraylist of details
	 */
	@SuppressWarnings("unchecked")
	public final AbsRowList loadData(ArrayList a) {
		int i;

		rows = new AbsRow[a.size()];

		for (i = 0; i < a.size(); i++) {
			rows[i] = (AbsRow) a.get(i);
		}
		return this
		;
	}


	/**
	 * loads supplied data into the list
	 *
	 * @param data data to load
	 */
	public final void loadData(Object[][] data) {
		int i;

		rows = new AbsRow[data.length];

		for (i = 0; i < data.length; i++) {
			rows[i] = new ArrayRow(data[i]);
		}
	}


	/**
	 * @see net.sf.RecordEditor.utils.swing.AbstractRowList#getElementAt(int)
	 */
	public Object getElementAt(int idx) {
		commonInit();

		if (allowNulls) {
			if (idx == 0) {
			    return null;
			}
			idx -= 1;
		}

		if ((idx < 0) || (idx >= rows.length)) {
			return null;
		}

		return rows[idx].getField(keyIdx);
	}


	/**
	 * @see net.sf.RecordEditor.utils.swing.AbstractRowList#getIndexOf(java.lang.Object)
	 */
	public int getIndexOf(Object arg0) {
		commonInit();
		int ret = 0;

		if (arg0 != null) {
			try {
				ret = (keyMap.get(arg0)).intValue();

				if (allowNulls) {
					ret += 1;
				}
			} catch (Exception ex) {
				//System.out.println("ComboModel getIndex failure " + arg0 + " " +  ex.getMessage());
			}
		}
		//if (trace) System.out.println("ComboModel getIndex " + arg0 + " -> " + ret);

		return ret;
	}



	/**
	 * @see net.sf.RecordEditor.utils.swing.AbstractRowList#getSize()
	 */
	public int getSize() {
		commonInit();

		int ret = rows.length;

		if (allowNulls) {
			ret += 1;
		}
		return ret;
	}


	/**
	 * @see net.sf.RecordEditor.utils.swing.AbstractRowList#getFieldAt(int)
	 */
	public Object getFieldAt(int idx) {
		commonInit();

		if (allowNulls) {
			if (idx == 0) {
			    return null;
			}
			idx -= 1;
		}

		return rows[idx].getField(dtlIdx);
	}

	/**
	 * @see net.sf.RecordEditor.utils.swing.AbstractRowList#getFieldAt(int)
	 */
	public Object getKeyAt(int idx) {
		commonInit();

		if (allowNulls) {
			if (idx == 0) {
			    return null;
			}
			idx -= 1;
		}

		return rows[idx].getField(keyIdx);
	}

	/**
	 * @see net.sf.RecordEditor.utils.swing.AbstractRowList#getFieldFromKey(java.lang.Object)
	 */
	public Object getFieldFromKey(Object key) {
		return getFieldAt(getIndexOf(key));
	}


	/**
	 * @see net.sf.RecordEditor.utils.swing.AbstractRowList#isSorted()
	 */
	public boolean isSorted() {
		return sorted;
	}


	/**
	 * @see net.sf.RecordEditor.utils.swing.AbstractRowList#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(AbsRow row1, AbsRow row2) {

		if (row1 == null) {
		    return 1;
		}
		if (row2 == null) {
		    return -1;
		}
		return  (row1).getField(dtlIdx).toString().compareToIgnoreCase(
				(row2).getField(dtlIdx).toString());
	}


	/**
	 * @see net.sf.RecordEditor.utils.swing.AbstractRowList#reload()
	 */
	public void reload() {
		toInit = true;
		rows = null;
	}
}
