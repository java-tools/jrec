/*
 * Created on 19/09/2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.sf.RecordEditor.utils.jdbc;

import net.sf.RecordEditor.utils.swing.BmKeyedComboModel;

/**
 * @author Bruce Martin
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class DBComboModel<record extends AbsRecord> extends BmKeyedComboModel<DBList<record>> {


	/**
	 * Create a ComboBox model from records retrieved from a Abstract
	 * Database Interface
	 *
	 * Create DB Combo Model
	 * @param db db to read data from
	 * @param keyIdx keyIndex
	 * @param dtlIdx detail Index
	 * @param sort wether the list is sorted
	 * @param nullAllowed wether the first row is a Null record
	 *
	 */
	public DBComboModel(final AbsDB<record> db,
	        			final int keyIdx,
	        			final int dtlIdx,
	        			final boolean sort,
	        			final boolean nullAllowed) {
		super(new DBList<record>(db, keyIdx, dtlIdx, sort, nullAllowed));
	}


	/**
	 * Create Model from List of Records
	 *
	 * @param list list of Record (from a DB)
	 */
	public DBComboModel(final DBList<record> list) {
		super(list);
	}
}
