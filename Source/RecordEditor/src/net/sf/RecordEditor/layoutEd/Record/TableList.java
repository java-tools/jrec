/*
 * @Author Bruce Martin
 * Created on 8/02/2007 for version 0.60
 *
 * Purpose:
 * This class reads both DB values and properties
 * into a list for display in a Combobox
 *
 * Changes
 *
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - Changed to allow extension of class by TypeList
 */
package net.sf.RecordEditor.layoutEd.Record;

import java.util.ArrayList;

import net.sf.RecordEditor.layoutEd.Table.TableDB;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.Parameters;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.swing.AbsRowList;
import net.sf.RecordEditor.utils.swing.ArrayRow;

/**
 * This class reads both DB values and properties
 * into a list for display in a Combobox
 *
 * @author Bruce Martin
 *
 */
public class TableList extends AbsRowList {

	private TableDB tableDb = new TableDB();
	private String idName;
	private String name;
	private int arraySize;

	/**
	 * This class reads both DB values and properties
     * into a list for display in a Combobox
     *
	 * @param connectionId database connection
	 * @param tableId table identifier
	 * @param sort wether to sort the table
	 * @param nullFirstRow wether the first row should be null
	 * @param propertiesIdName name if the Identifier property
	 * @param propertiesName name
	 * @param propertiesArraySize number of elements to load
	 *        from the system properties
	 */
    public TableList(final int connectionId, final int tableId,
            final boolean sort, final boolean nullFirstRow,
            final String propertiesIdName, final String propertiesName,
            final int propertiesArraySize) {
        super(0, 1, sort, nullFirstRow);

    	idName = propertiesIdName;
    	name   = propertiesName;
    	arraySize = propertiesArraySize;

        tableDb.setConnection(new ReConnection(connectionId));
        tableDb.setParams(tableId);
    }

	/**
	 * Load the list from DB / properties file
	 */
	public void loadData() {
		super.loadData(getPropertiesAndDB());
	}


	/**
	 * load standard types defined in the parameter file into an array
	 * list
	 * @return requested list
	 */
	@SuppressWarnings("unchecked")
	protected final ArrayList getPropertiesAndDB() {
		ArrayList list = tableDb.fetchAll();

		int[] keys = Common.readIntPropertiesArray(idName, arraySize);
		String s;

		if (keys != null) {
		    for (int i = 0; i < arraySize; i++) {
		        if (keys[i] != Common.NULL_INTEGER) {
		            //System.out.print(i + " " + name + " ");
		            s = Parameters.getString(name + i);
		            //System.out.print(" ~~ " + s);
		            if (s != null && ! "".equals(s)) {
		                //System.out.print(" ~~~");
		                list.add(new ArrayRow(new Object[]{Integer.valueOf(keys[i]), s}));
		            }
		            //System.out.println();
		        }
		    }
		}
		return list;
	}
}
