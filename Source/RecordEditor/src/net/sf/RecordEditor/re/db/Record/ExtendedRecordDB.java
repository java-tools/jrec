/*
 * @Author Bruce Martin
 * Created on 10/01/2007
 *
 * Purpose:
 * RecordDB object for saving ExtendedRecord to the DB
 *
 * # Version 0.60 Bruce Martin 2007/02/16
 *   - new fetch record
 *   - fixed bug when saving a childrecord
 *
 *
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - Major Rewrite due to changes in the RecordRec,
 *     creation of JRecord etc
 */
package net.sf.RecordEditor.re.db.Record;

import java.sql.SQLException;
import java.util.HashMap;

import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.ExternalRecordSelection.ExternalFieldSelection;
import net.sf.JRecord.ExternalRecordSelection.ExternalGroupSelection;
import net.sf.JRecord.ExternalRecordSelection.ExternalSelection;
import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.JRecord.detailsSelection.RecordSel;
import net.sf.RecordEditor.re.db.Table.TableDB;
import net.sf.RecordEditor.re.db.Table.TableRec;
import net.sf.RecordEditor.trove.map.hash.TIntObjectHashMap;
import net.sf.RecordEditor.utils.ReadRecordSelection;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.jdbc.AbsDB;
import net.sf.RecordEditor.utils.jdbc.AbsRecord;


/**
 * RecordDB object for saving ExtendedRecord to the DB
 *
 * @author Bruce Martin
 *
 */
public class ExtendedRecordDB extends RecordDB {

	private ChildRecordsDB childDb = null;
    private RecordFieldsDB fieldDb = null;
    private boolean recSelWarn;
    
    private TIntObjectHashMap<String> systemNamesMap = null;
    private HashMap<String, Integer> tableId = null;
    private int maxTblKey = 0;
    
    public ExternalRecord fetchExternal() {
    	RecordRec rec = fetch();
    	if (rec == null) {
    		return null;
    	}
    	return rec.getValue();
    }


    /**
	 * @see net.sf.RecordEditor.re.db.Record.RecordDB#fetch()
	 */
	public RecordRec fetch() {
		boolean free = super.isSetDoFree(false);

		RecordRec rec = super.fetch();

		if (rec == null) {
			closeChildDbs();
		} else {
			getSystemName(rec);
			defineChildDbs();

			fetch_Records(rec.getValue());
			fetch_Fields(rec.getValue());
		}
		super.setDoFree(free);
		return rec;
	}

	/**
	 * Fetch child records of a record
	 * @param rec Record to retrieve child records for.
	 */
	private void fetch_Records(ExternalRecord rec) {
		ChildRecordsRec child;
		childDb.setParams(rec.getRecordId());

		childDb.open();
		child = childDb.fetch();
//		System.out.println(" >>> " + rec.getRecordId());
//		System.out.println();
		if (child != null) {
			int i, j, id, parentId;
			ReadRecordSelection readSel = ReadRecordSelection.getInstance();
			RecordSel recSel;
			RecordDB recDb = new RecordDB();
			RecordRec r;
			ExternalRecord childRecord;
			recDb.setConnection(this.connect);
			while (child != null) {
				recDb.resetSearch();
				recDb.setSearchArg("RecordId", AbsDB.opEquals, "" + child.getChildRecordId());
				recDb.open();
				r = recDb.fetch();
				//System.out.print(" ~~ " + child.getChildKey() + " " + child.getChildRecord() + " " + (r == null));
				if (r != null) {
					getSystemName(r);
					childRecord = r.getValue();
					//System.out.print(" " + r.getRecordName());

					//childRecord.addTstField(child.getField(), child.getFieldValue());
					String field = child.getField();
					String fieldValue = child.getFieldValue();
					recSel = readSel.getRecordSelection(
									connect, rec.getRecordId(), child.getChildKey(), null,
									field, fieldValue);
					if (recSel != null) {
						childRecord.setRecordSelection(recSel);
					} 
					
					if ((field == null || field.length() == 0) && ("*".equals(fieldValue))) {
						childRecord.setDefaultRecord(true);
					}
					
					childRecord.setParentRecord(child.getParentRecord());

					fetch_Fields(childRecord);

					rec.addRecord(childRecord);
				}
				//System.out.println();
				child = childDb.fetch();
			}

			for (i = 0; i < rec.getNumberOfRecords(); i++) {
				parentId = rec.getRecord(i).getParentRecord();
				for (j = 0; j < rec.getNumberOfRecords(); j++) {
					id = rec.getRecord(j).getRecordId();
					if (parentId == id) {
						rec.getRecord(i).setParentRecord(j);
						break;
					}
				}
			}
		}
		//childDb.freeConnection();
	}

	private void fetch_Fields(ExternalRecord rec) {
		RecordFieldsRec field;

		fieldDb.setParams(rec.getRecordId());

		fieldDb.open();
		field = fieldDb.fetch();
		while (field != null) {
			rec.addRecordField(field.getValue());
			field = fieldDb.fetch();
		}
	}


	/**
     * RecordDB object for saving ExtendedRecord to the DB
     * @see net.sf.RecordEditor.utils.jdbc.AbsDB#checkAndUpdate(net.sf.RecordEditor.utils.jdbc.AbsRecord)
     */
    public void checkAndUpdate(ExternalRecord rec) {

        checkAndUpdate(new RecordRec(rec));
    }


    /**
     * RecordDB object for saving ExtendedRecord to the DB
     * @see net.sf.RecordEditor.utils.jdbc.AbsDB#checkAndUpdate(net.sf.RecordEditor.utils.jdbc.AbsRecord)
     */
    public void checkAndUpdate(RecordRec rec) {
		boolean free = super.isSetDoFree(false);


//        System.out.println("~~~> " + rec.getRecordName()
//                + " " + rec.isNew()
//                + " " + rec.getUpdateStatus());

		if (rec.isNew()) {
		    //System.out.println("~~~> inserting ");
			RecordRec old;
			super.resetSearch();
			super.setSearchRecordName(AbsDB.opEquals, rec.getRecordName());
			super.open();
			old = super.fetch();
			super.close();

			if (old != null) {
				rec.getValue().setRecordId(old.getValue().getRecordId());
				rec.setNew(false);
				rec.setUpdateStatus(AbsRecord.UPDATED);
			}
		}

		super.checkAndUpdate(rec);
		super.setDoFree(free);
    }


    /**
     * @see net.sf.RecordEditor.utils.jdbc.AbsDB#insert(net.sf.RecordEditor.utils.jdbc.AbsRecord)
     */
    public void insert(RecordRec value) {
		boolean free = super.isSetDoFree(false);

    	/*System.out.println();
        System.out.println("X Insert ~~~> " + value.getRecordName()
                + " " + value.isNew()
                + " " + value.getUpdateStatus());*/
        super.insert(value);

        updateChildSegments(value.getValue());
        writeFields(value.getValue());

        super.setDoFree(free);
     }


    /**
     * @see net.sf.RecordEditor.utils.jdbc.AbsDB#update(net.sf.RecordEditor.utils.jdbc.AbsRecord)
     */
    public void update(RecordRec val) {
		boolean free = super.isSetDoFree(false);

    	try {
    		super.doUpdate(val);
    	} catch (Exception e) {
			super.insert(val);
		}

        updateChildSegments(val.getValue());
        writeFields(val.getValue());

        super.setDoFree(free);
    }


    /**
     * Update Child records and fields
     *
     * @param rec record to be saved
     */
    public void updateChildSegments(ExternalRecord rec) {
    	boolean free = super.isSetDoFree(false);

    	int i;
    	if (rec.getNumberOfRecords() > 0) {
    		ExternalRecord child;
    		ChildRecordsRec childRec;
    		//ArrayList<ChildRecordsRec> children = new ArrayList<ChildRecordsRec>();
    		ChildRecordsDB db = new ChildRecordsDB();
    		db.setDoFree(false);
    		for (i = 0; i < rec.getNumberOfRecords(); i++) {
    			child = rec.getRecord(i);
    			checkAndUpdate(child);
    		}

    		db.setConnection(this.connect);
    		db.setParams(rec.getRecordId());
    		db.deleteAll();


    		for (i = 0; i < rec.getNumberOfRecords(); i++) {
    			child = rec.getRecord(i);
    			//System.out.print("Insert Child: " + i + " " + child.getRecordName()
    			//		+ " > " + child.getParentRecord());
    			if (child.getParentRecord() >= 0) {
    				//System.out.print(" " + rec.getRecord(child.getParentRecord()).getRecordName());
    			}
    			//System.out.println();
    			childRec = ChildRecordsRec.getBlankChildRec(child.getRecordId());
    			childRec.setChildId(i);
    			childRec.setParentRecord(child.getParentRecord());

    			writeChild(db, rec.getRecordId(), childRec, child.getRecordSelection());
    		}


//    		for (i = 0; i < rec.getNumberOfRecords(); i++) {
//    			child = rec.getRecord(i);
//    			try {
//    				child.setParentRecord(rec.getRecord(child.getParentRecord()).getRecordId());
//    			} catch (Exception e) {	}
//
//    			childRec = ChildRecordsRec.getBlankChildRec(child.getRecordId());
//    			childRec.setParentRecord(child.getParentRecord());
////    			childRec.setField(child.getTstField());
////    			childRec.setFieldValue(child.getTstFieldValue());
////
////    			db.insert(childRec);
//    			writeChild(db, rec.getRecordId(), childRec, child.getRecSelect());
//    		}
    	}

    	if (rec.getNumberOfRecordFields() > 0) {
    		RecordFieldsDB db = new RecordFieldsDB();
    		db.setDoFree(false);
    		db.setConnection(this.connect);
    		db.setParams(rec.getRecordId());
    		db.deleteAll();

    		for (i = 0; i < rec.getNumberOfRecordFields(); i++) {
    			db.insert(new RecordFieldsRec(rec.getRecordField(i)));
    		}
    	}
    	super.setDoFree(free);
    }


    public final void writeChild(ChildRecordsDB db, int recordId, ChildRecordsRec childRec, ExternalSelection recordSel) {

    	if (recordSel == null) {
    	} else if (recordSel instanceof ExternalGroupSelection) {
    		@SuppressWarnings("rawtypes")
			ExternalGroupSelection grp = (ExternalGroupSelection) recordSel;
    		if (grp.size() == 1 && updateSelectionFields(childRec, grp.get(0))) {
    			recordSel = null;
    		} else if (grp.size() == 2
    			   && grp.getType() == ExternalSelection.TYPE_AND
    			   && grp.get(1).getType() != ExternalSelection.TYPE_ATOM
    			   && updateSelectionFields(childRec, grp.get(0))) {
    			recordSel = grp.get(1);
    		}
    	} else if (updateSelectionFields(childRec, recordSel)) {
    		recordSel = null;
    	}

    	db.insert(childRec);

    	writeRecordSelection(recordId, childRec.getChildKey(), recordSel);
    }

    public final void writeRecordSelection(int recordId, int childKey, ExternalSelection recordSel) {
    	RecordSelectionDB db = new RecordSelectionDB();

    	db.setConnection(this.connect);
		db.setParams(recordId, childKey);
    	db.deleteAll();

    	recSelWarn = false;
    	if (recordSel == null) {
    	} else if (recordSel instanceof ExternalGroupSelection) {
    		@SuppressWarnings("rawtypes")
			ExternalGroupSelection grp = (ExternalGroupSelection) recordSel;

    		if (grp.getType() == ExternalSelection.TYPE_AND) {
    			writeAndSelection(db, recordId, childKey, 1, Common.BOOLEAN_OPERATOR_AND, grp);
    		} else {
    			 writeOrSelection(db, recordId, childKey, grp);
    		}
    	} else {
    		writeSelectionField(db, recordId, childKey, 1, Common.BOOLEAN_OPERATOR_AND, recordSel);
    	}

    	if (recSelWarn) {
    		String s = "     ----- Warning  -- Waring ---------\n"
    				 + "     ----------------------------------\n\n"
    				 + "  The record Selection was to complicated to be loaded in to the Database\n\n"
    				 + "  You need to update Record Selection manualy ";

    		System.out.println(s);
    		Common.logMsgId("RecSel001", AbsSSLogger.SHOW, s, null);
    	}
    	db.close();
    }

    @SuppressWarnings("rawtypes")
	private void writeOrSelection(
    		RecordSelectionDB db,
    		int recordId, int childKey,
    		ExternalGroupSelection grp) {
    	int idx = 1;
    	int op = Common.BOOLEAN_OPERATOR_AND;
    	ExternalSelection child;

    	for (int i = 0; i < grp.size(); i++) {
    		child = grp.get(i);
    		if (child.getType() ==  ExternalSelection.TYPE_ATOM) {
    			writeSelectionField(db, recordId, childKey, idx++, op, child);
    		} else if (child.getType() ==  ExternalSelection.TYPE_AND) {
    			idx = writeAndSelection(db, recordId, childKey, idx, op, (ExternalGroupSelection) child);
    		} else {
    			recSelWarn = true;
    		}

    		op = Common.BOOLEAN_OPERATOR_OR;
    	}
    }

    private int writeAndSelection(
    		RecordSelectionDB db,
    		int recordId, int childKey, int fieldNo,
    		int boolOp, @SuppressWarnings("rawtypes") ExternalGroupSelection grp) {

    	int idx = fieldNo;

    	for (int i = 0; i < grp.size(); i++) {
    		if (grp.get(i).getType() ==  ExternalSelection.TYPE_ATOM) {
    			writeSelectionField(db, recordId, childKey, idx++, boolOp, grp.get(i));
    			boolOp = Common.BOOLEAN_OPERATOR_AND;
    		} else {
    			recSelWarn = true;
    		}
    	}
    	return idx;
    }

    private void writeSelectionField(
    		RecordSelectionDB db,
    		int recordId, int childKey, int fieldNo,
    		int boolOp, ExternalSelection recordSel) {

    	if (recordSel instanceof ExternalFieldSelection) {
    		ExternalFieldSelection fieldSel = (ExternalFieldSelection) recordSel;
    		RecordSelectionRec selRec = new RecordSelectionRec(
    				recordId, childKey, fieldNo, boolOp,
    				fieldSel.getFieldName(), fieldSel.getOperator(), fieldSel.getFieldValue()
    		);

    		db.insert(selRec);
    	}
    }

    private boolean updateSelectionFields(ChildRecordsRec childRec, ExternalSelection recordSel) {
    	boolean ret = false;
    	if (recordSel instanceof ExternalFieldSelection) {
    		ExternalFieldSelection fieldSel = (ExternalFieldSelection) recordSel;

    		if ("=".equals(fieldSel.getOperator()) || "eq".equalsIgnoreCase(fieldSel.getOperator())) {
    			childRec.setField(fieldSel.getFieldName());
    			childRec.setFieldValue(fieldSel.getFieldValue());
    			ret = true;
    		}
    	}

    	return ret;
    }

    public void writeFields(ExternalRecord rec) {
    	boolean free = super.isSetDoFree(false);
    	int count = rec.getNumberOfRecordFields();
    	if (count > 0) {
    		RecordFieldsRec field;
    		RecordFieldsDB db = new RecordFieldsDB();
    		db.setDoFree(false);
    		db.setConnection(this.connect);

    		db.setParams(rec.getRecordId());
    		db.deleteAll();

    		for (int i = 0; i < count; i++) {
    			field = new RecordFieldsRec(rec.getRecordField(i));
    			field.setNew(true);
    			db.insert(field);
    		}
    	}
    	super.setDoFree(free);
    }

    public void delete(int recordId) {
    	RecordRec rec = RecordRec.getNullRecord("xx", "");
    	rec.getValue().setRecordId(recordId);


    	delete(rec);
    }


    public void delete(RecordRec rec) {
    	boolean free = super.isSetDoFree(false);
    	int recordId = rec.getRecordId();
    	String deleteSQL = "Delete From  " + RecordSelectionDB.DB_NAME
    					 + " Where RecordId = " + recordId;

    	defineChildDbs();

    	childDb.setParams(recordId);
    	fieldDb.setParams(recordId);

    	childDb.deleteAll();
    	fieldDb.deleteAll();

    	try {
			connect.getUpdateConnection().createStatement().executeUpdate(deleteSQL);
		} catch (SQLException ex) {
			setMessage(deleteSQL, "Error Deleting: " + rec.getRecordName() + "\n" + ex.toString(), ex);
		}

    	super.delete(rec);

    	closeChildDbs();
    	super.setDoFree(free);
    }

    private void defineChildDbs() {

		if (childDb == null) {
			childDb = new ChildRecordsDB();
			fieldDb = new RecordFieldsDB();
			childDb.setConnection(this.connect);
			childDb.setDoFree(false);
			fieldDb.setConnection(this.connect);
			fieldDb.setDoFree(false);
		}
    }

    private void closeChildDbs() {
		if (childDb != null) {
			childDb.close();
			fieldDb.close();
			childDb = null;
		}
		fieldDb = null;
    }
    
    private void getSystemName(RecordRec rec) {
    	int system = rec.getSystem();
    	if (systemNamesMap == null) {
    		TIntObjectHashMap<String> sysNamesMap = new TIntObjectHashMap<String>(30);
    		TableRec tblDtl;
    		TableDB tblsDB = new TableDB();
    		tblsDB.setConnection(super.getConnect());
    		tblsDB.resetSearch();
			tblsDB.setParams(Common.TI_SYSTEMS);
			tblsDB.open();
			while ((tblDtl = tblsDB.fetch()) != null) {
				sysNamesMap.put(tblDtl.getTblKey(), tblDtl.getDetails());				
			}
			systemNamesMap = sysNamesMap;
			tblsDB.close();
    	}
    	
    	String s = "";
    	if (systemNamesMap.contains(system)) {
    		s = systemNamesMap.get(system);
    	}
    	rec.getValue().setSystemName(s);
    }

    public static RecordRec getRecord(int dbIdx, int recordId) {

		RecordRec r;

		ExtendedRecordDB dbFrom = new ExtendedRecordDB();
		boolean free = dbFrom.isSetDoFree(false);

		dbFrom.setConnection(new ReConnection(dbIdx));
		dbFrom.resetSearch();
		dbFrom.setSearchRecordId(AbsDB.opEquals, recordId);
		dbFrom.open();

		r = dbFrom.fetch();
		dbFrom.close();
		dbFrom.setDoFree(free);

		return r;
    }
    
    
	/**
	 * Set the System Id based on the System Name
	 *
	 */
	public final void updateSystemCode(ExternalRecord rec) throws SQLException {

		int i, ckey;
		String s;
		TableRec aTbl;

		for (i = 0; i < rec.getNumberOfRecords(); i++) {
			updateSystemCode(rec.getRecord(i));
		}

		s =  rec.getSystemName();
		if (s != null) {
			TableDB tblsDB = new TableDB();
			if (tableId == null) {
				tableId = new HashMap<String, Integer>();
				maxTblKey = 0;

				tblsDB.setConnection(this.getConnect());
				tblsDB.resetSearch();
				tblsDB.setParams(Common.TI_SYSTEMS);
				tblsDB.open();
				while ((aTbl = tblsDB.fetch()) != null) {
					ckey = aTbl.getTblKey();
					tableId.put(aTbl.getDetails(), Integer.valueOf(ckey));
					if (maxTblKey < ckey) {
						maxTblKey = ckey;
					}
				}
				tblsDB.close();
			}


			if (tableId.containsKey(s)) {
				Integer tblKey = tableId.get(s);
				rec.setSystem(tblKey.intValue());
			} else {
				maxTblKey += 1;
				aTbl = new TableRec(maxTblKey, s);
				i = 0;
				while ((i++ < 15) && (! tblsDB.tryToInsert(aTbl))) {
					maxTblKey += 1;
					aTbl.setTblKey(maxTblKey);
			    }
				tableId.put(s, Integer.valueOf(maxTblKey));
				
				if (systemNamesMap != null) {
					systemNamesMap.put(maxTblKey, s);
				}
			}
		}
	}

}
