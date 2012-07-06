/*
 * Created on 19/09/2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.sf.RecordEditor.utils.jdbc;

import java.util.ArrayList;

import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.swing.AbsRowList;


/**
 * Purpose Create a "List or Rows" from a Sql Table
 *
 * @author Bruce Martin
 *
 * License GPL
 */
public class DBList<record extends AbsRecord> extends AbsRowList {

	private AbsDB<record> db;
	private int sourceIndex = -1,
			    foreignIndex;
	private String foreignLookUpId;

	/**
	 * Create Data Base List
	 * @param dbInterface Database interface to retrieve the rows from
	 * @param keyIdx Column Number of the Key Field
	 * @param foreignIdx index for the foreign language index
	 * @param dtlIdx Column Number of the Detail (or display field)
	 * @param sort wether to sort the rows
	 * @param nullFirstRow wether the first row in the list is a virtual
	 *        null row
	 */
	public DBList(final AbsDB<record> dbInterface,
	        	  final int keyIdx,
	        	  final int dtlIdx,
	        	  final int foreignIdx,
	        	  final String foreignId,
	        	  final boolean sort,
	        	  final boolean nullFirstRow) {
		super(keyIdx, foreignIdx, sort, nullFirstRow);

		this.db = dbInterface;
		this.sourceIndex = dtlIdx;
		this.foreignIndex = foreignIdx;
		this.foreignLookUpId = foreignId;
		//System.out.println("==> " + nullAllowed + " " + allowNulls);
	}


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

		ArrayList<record> recs = db.fetchAll();

		if (sourceIndex >= 0 && sourceIndex != foreignIndex) {
			for (record rec : recs) {
				rec.setField(
						foreignIndex,
						LangConversion.convertId(
								LangConversion.ST_EXTERNAL,
								foreignLookUpId + rec.getField(getKeyIdx()),
								fix(rec.getField(sourceIndex))
						));
			}
		}
		loadData(recs);
	}

	private String fix(Object o) {
		if (o == null) return "";
		return o.toString();
	}
}
