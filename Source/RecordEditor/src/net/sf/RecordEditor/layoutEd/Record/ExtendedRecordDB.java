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
package net.sf.RecordEditor.layoutEd.Record;

import net.sf.JRecord.External.ExternalRecord;
import net.sf.RecordEditor.utils.jdbc.AbsDB;
import net.sf.RecordEditor.utils.jdbc.AbsRecord;


/**
 * RecordDB object for saving ExtendedRecord to the DB
 *
 * @author Bruce Martin
 *
 */
public class ExtendedRecordDB extends RecordDB {

	ChildRecordsDB childDb = null;
    RecordFieldsDB fieldDb = null;


    public ExternalRecord fetchExternal() {
    	RecordRec rec = fetch();
    	if (rec == null) {
    		return null;
    	}
    	return rec.getValue();
    }
    
    
    /**
	 * @see net.sf.RecordEditor.layoutEd.Record.RecordDB#fetch()
	 */
	public RecordRec fetch() {
		boolean free = super.isSetDoFree(false);

		RecordRec rec = super.fetch();
		
		if (rec == null) {
			if (childDb != null) {
				childDb.close();
				fieldDb.close();
				childDb = null;
			}
			fieldDb = null;
		} else {
			if (childDb == null) {
				childDb = new ChildRecordsDB();
				fieldDb = new RecordFieldsDB();
				childDb.setConnection(this.connect);
				childDb.setDoFree(false);
				fieldDb.setConnection(this.connect);
				fieldDb.setDoFree(false);
			}
			
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
		if (child != null) {
			int i, j, id, parentId;
			RecordDB recDb = new RecordDB();
			RecordRec r;
			ExternalRecord childRecord;
			recDb.setConnection(this.connect);
			while (child != null) {
				recDb.resetSearch();
				recDb.setSearchArg("RecordId", AbsDB.opEquals, "" + child.getChildRecord());
				recDb.open();
				r = recDb.fetch();
				childRecord = r.getValue();
				childRecord.setTstField(child.getField());
				childRecord.setTstFieldValue(child.getFieldValue());
				childRecord.setParentRecord(child.getParentRecord());

				fetch_Fields(childRecord);
				
				if (r != null) {
					rec.addRecord(childRecord);
				}
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
                try {
                	child.setParentRecord(rec.getRecord(child.getParentRecord()).getRecordId());
                } catch (Exception e) {	}

                childRec = new ChildRecordsRec(child.getRecordId(), 0, "", "", -1);
                childRec.setParentRecord(child.getParentRecord());
                childRec.setField(child.getTstField());
                childRec.setFieldValue(child.getTstFieldValue());

                db.insert(childRec);
            }
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
}
