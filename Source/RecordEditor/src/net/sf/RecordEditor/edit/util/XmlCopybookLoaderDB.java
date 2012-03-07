/*
 * @Author Bruce Martin
 * Created on 6/04/2005
 *
 * Purpose:
 * This class will insert a XML Copybook into the Record Editor DB
 *
 * Modification log:
 * Changes
 * # Version 0.56
 *   - On 2006/06/28 by Jean-Francois Gagnon:
 *    - Added the ability to process copybooks so they get
 *      processed with the Fujitsu Flavor
 *    - Added handling of the Sign Separate type field
 *
 *   - Bruce Martin 2007/01/16
 *     - Changed to use ExtendedRecord to get null records
 *
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - Renamed to XmlCopybookLoaderDB from XmlCopybookLoader
 *
 */
package net.sf.RecordEditor.edit.util;

import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.XmlCopybookLoader;
import net.sf.RecordEditor.layoutEd.Record.ExtendedRecordDB;
import net.sf.RecordEditor.layoutEd.Record.RecordRec;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.jdbc.AbsDB;
import net.sf.RecordEditor.utils.jdbc.AbsRecord;
//import net.sf.RecordEditor.utils.log.TextLog;




/**
 * This class will insert a XML Copybook into the Record Editor DB
 *
 * @author Bruce Martin
 */
public class XmlCopybookLoaderDB extends XmlCopybookLoader {

    private int lastDBidx = -1;

    private ExtendedRecordDB recordDB   = null;



    /**
     * Allocate the Database Interfaces
     *
     * @param dbIdx Database index
     */ @Override
    protected void allocDBs(final int dbIdx) {

        if (lastDBidx != dbIdx || recordDB == null) {
            this.recordDB   = new ExtendedRecordDB();
            this.recordDB.setDoFree(false);

            this.recordDB.setConnection(new ReConnection(dbIdx));
         } else if (lastDBidx != dbIdx) {
            this.recordDB.fullClose();
            this.recordDB.setDoFree(true);
            this.recordDB.setDoFree(false);

            this.recordDB.setConnection(new ReConnection(dbIdx));
        }

       lastDBidx = dbIdx;
    }


    protected void freeDBs(int pDbIdx) {
    	if (recordDB != null) {
    		recordDB.close();
    		recordDB.setDoFree(true);
       		recordDB.setDoFree(false);
   	}
    		
    }


    /**
     * Update the record details. This method will read current
     * DB details (if there are any) and apply standard updates to
     * the record
     *
     * @param copyBook Copybook name
     * @param rec Record Details
     * @param updateRequired Wether a update of existing record is required
      */
    protected void updateRecord(String copyBook,
            					   ExternalRecord rec,
            					   boolean updateRequired) {
        RecordRec oldRec;


        recordDB.resetSearch();
        recordDB.setSearchArg("COPYBOOK", AbsDB.opEquals, "'" + copyBook + "'");
        //System.out.println("Searching >" + copyBook + "<");
        recordDB.open();

        if ((oldRec =  recordDB.fetch()) != null) {
            ExternalRecord oldValues = oldRec.getValue();
            rec.setRecordId(oldValues.getRecordId());
            rec.setNew(false);
            //System.out.println("Found old record ...");
            //fieldsDB.setParams(rec.getRecordId());

            if (updateRequired) {
                rec.setUpdateStatus(AbsRecord.UPDATED);
            } else {
                String oldFont = oldValues.getFontName();

                if (oldFont == null) {
                    oldFont = "";
                }

                if (! getFontName().toUpperCase().equals(oldFont.toUpperCase())) {
                    oldValues.setFontName(getFontName());
                    rec = oldValues;
                    rec.setUpdateStatus(AbsRecord.UPDATED);
                }
            }
        } else {
            super.updateRecord(copyBook, rec, updateRequired);
        }
        recordDB.freeConnection();
    }


}