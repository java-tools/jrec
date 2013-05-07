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
package net.sf.RecordEditor.re.db.Table;

import java.util.ArrayList;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.swing.AbsRowList;

/**
 * This class reads both DB values and properties
 * into a list for display in a Combobox
 *
 * @author Bruce Martin
 *
 */
public class TableList extends AbsRowList {

	public static final int FOREIGN_LANGUAGE_FIELD_NO = 2;
	private TableDB tableDb = new TableDB();
	private String idName;
	private String name;
	private int arraySize, tableIdent;

	private final boolean foreignTrans;
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
    	this(connectionId, tableId, sort, nullFirstRow, propertiesIdName, propertiesName, propertiesArraySize, true);
    }

    public TableList(final int connectionId, final int tableId,
            final boolean sort, final boolean nullFirstRow,
            final String propertiesIdName, final String propertiesName,
            final int propertiesArraySize,
            final boolean foreignTranslation) {
        super(0, getNameIdx(tableId), sort, nullFirstRow);

        tableIdent = tableId;
    	idName = propertiesIdName;
    	name   = propertiesName;
    	arraySize = propertiesArraySize;
    	foreignTrans = foreignTranslation;

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
	protected final ArrayList<TableRec> getPropertiesAndDB() {
		ArrayList<TableRec> list = tableDb.fetchAll();

		int[] keys = Common.readIntPropertiesArray(idName, arraySize);
		String s;

		getForeignTranslation(list);

		if (keys != null) {
		    for (int i = 0; i < arraySize; i++) {
		        if (keys[i] != Common.NULL_INTEGER) {
		            s = Parameters.getString(name + i);

		            if (s != null && ! "".equals(s)) {
		                list.add(new TableRec(keys[i], s));
		            }
		            //System.out.println();
		        }
		    }
		}
		return list;
	}

	protected void getForeignTranslation(ArrayList<TableRec> list) {

		if (foreignTrans && isForeignTranslation(tableIdent)) {
			String foreignLookUpId = TableDB.getTblLookupKey(tableIdent);
			for (TableRec rec : list) {
				rec.setField(
						FOREIGN_LANGUAGE_FIELD_NO,
						LangConversion.convertId(
								LangConversion.ST_EXTERNAL,
								foreignLookUpId + rec.getField(getKeyIdx()),
								fix(rec.getField(1))
						));
			}

		}
	}

	private String fix(Object o) {
		if (o == null) return "";
		return o.toString();
	}

	private static int getNameIdx(int tableId) {
		int ret = 1;
		if (isForeignTranslation(tableId)) {
			ret = 2;
		}
		return ret;
	}

	private static boolean isForeignTranslation(int tableId) {
		return tableId != Common.TI_SYSTEMS;
	}
}
