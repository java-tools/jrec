/*
 * Created on 19/09/2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.sf.RecordEditor.utils.jdbc;

import net.sf.RecordEditor.utils.swing.AbsRowList;


/**
 * @author Bruce Martin
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class DBList<record extends AbsRecord> extends AbsRowList {

	private AbsDB<record> db;


	/**
	 * Create Data Base List
	 * @param dbInterface Database interface to retrieve the rows from
	 * @param keyIdx Column Number of the Key Field
	 * @param dtlIdx Column Number of the Detail (or display field)
	 * @param sort wether to sort the rows
	 * @param nullFirstRow wether the first row in the list is a virtual
	 *        null row
	 */
	public DBList(final AbsDB<record> dbInterface,
	        	  final int keyIdx,
	        	  final int dtlIdx,
	        	  final boolean sort,
	        	  final boolean nullFirstRow) {
		super(keyIdx, dtlIdx, sort, nullFirstRow);

		this.db = dbInterface;

		//System.out.println("==> " + nullAllowed + " " + allowNulls);
	}


	/**
	 * perform common initialise functions
	 */
	public void loadData() {
		//int i;

		loadData(db.fetchAll());
/*		Object[] a = db.fetchAll().toArray();
		fields = new AbsRecord[a.length];


		for (i = 0; i < a.length; i++) {
			fields[i] = (AbsRecord) a[i];
		}*/
	}
}
