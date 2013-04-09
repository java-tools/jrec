/*
 * @Author Bruce Martin
 * Created on 17/03/2007
 *
 * Purpose:
 */
package net.sf.RecordEditor.test;

import junit.framework.TestCase;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.Def.ExternalField;
import net.sf.JRecord.Types.Type;
import net.sf.RecordEditor.re.db.Record.RecordFieldsRec;
import net.sf.RecordEditor.re.db.Record.RecordRec;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.jdbc.AbsRecord;

/**
 *
 *
 * @author Bruce Martin
 *
 */
public class TestDBrecords extends TestCase {

    /**
     * Test Field record is updating correctly
     *
     */
    public void testNewField() {
        RecordFieldsRec rec = new RecordFieldsRec();

        assertTrue("1) Record is not inserted", rec.isNew());
        assertEquals("2) is not null update status " + rec.getUpdateStatus(), AbsRecord.NULL_INT_VALUE, rec.getUpdateStatus());
        assertEquals("2a) is not null update status " + rec.getValue().getUpdateStatus(), AbsRecord.NULL_INT_VALUE, rec.getValue().getUpdateStatus());

        rec.getValue().setName("Test Record ");

        System.out.println("~~> " + rec.getUpdateStatus() + " " + rec.getValue().getUpdateStatus());
        assertTrue("3) Record is not inserted ", rec.isNew());
        assertEquals("4) is not updated update status " + rec.getUpdateStatus(), AbsRecord.UPDATED, rec.getUpdateStatus());
        assertEquals("4a) is not updated update status " + rec.getValue().getUpdateStatus(), AbsRecord.UPDATED, rec.getValue().getUpdateStatus());

        rec.setNew(false);
        rec.setUpdateStatus(AbsRecord.UNCHANGED);

        assertFalse("5) Record is inserted ", rec.isNew());
        assertEquals("6) is not unchanged update status " + rec.getUpdateStatus(), AbsRecord.UNCHANGED, rec.getUpdateStatus());
        assertEquals("6a) is not unchanged update status " + rec.getValue().getUpdateStatus(), AbsRecord.UNCHANGED, rec.getValue().getUpdateStatus());

        rec.getValue().setName("Test Record ");
        assertFalse("7) Record is inserted ", rec.isNew());
        assertEquals("8) is not unchanged update status " + rec.getUpdateStatus(), AbsRecord.UNCHANGED, rec.getUpdateStatus());
        assertEquals("8a) is not unchanged update status " + rec.getValue().getUpdateStatus(), AbsRecord.UNCHANGED, rec.getValue().getUpdateStatus());

        rec.getValue().setName("new value");
        assertFalse("9) Record is inserted ", rec.isNew());
        assertEquals("10) is not updated update status " + rec.getUpdateStatus(), AbsRecord.UPDATED, rec.getUpdateStatus());
        assertEquals("10a) is not updated update status " + rec.getValue().getUpdateStatus(), AbsRecord.UPDATED, rec.getValue().getUpdateStatus());

        System.out.println("~~> " + rec.isNew() + " " + rec.getUpdateStatus());

    }


    public void testField() {
        RecordFieldsRec rec = new RecordFieldsRec(
    	        1,
    	        4,
    	        "Field Name",
    	        "",
    	        Type.ftChar,
    	        0,
    	        Common.FORMAT_DEFAULT,
    	        "",
    	        "",
    	        "",
    	        1
    	  	 );

        assertFalse("1) Record is inserted ", rec.isNew());
        assertEquals("2) is not unchanged update status " + rec.getUpdateStatus(), AbsRecord.UNCHANGED, rec.getUpdateStatus());
        assertEquals("2a) is not unchanged update status " + rec.getValue().getUpdateStatus(), AbsRecord.UNCHANGED, rec.getValue().getUpdateStatus());

        rec.getValue().setName("new value");
        assertFalse("3) Record is inserted ", rec.isNew());
        assertEquals("4) is not updated update status " + rec.getUpdateStatus(), AbsRecord.UPDATED, rec.getUpdateStatus());
        assertEquals("4a) is not updated update status " + rec.getValue().getUpdateStatus(), AbsRecord.UPDATED, rec.getValue().getUpdateStatus());
        System.out.println("~~> " + rec.isNew() + " " + rec.getUpdateStatus());

    }


    /**
     * Test Field record is updating correctly
     *
     */
    public void testNewRecord() {
        RecordRec rec = new RecordRec();
        ExternalRecord val = rec.getValue();

        assertTrue("1) Record is not inserted", rec.isNew());
        assertEquals("2) is not null update status " + rec.getUpdateStatus(), AbsRecord.BLANK_RECORD, rec.getUpdateStatus());
        assertEquals("2a) is not null update status " + val.getUpdateStatus(), AbsRecord.BLANK_RECORD, val.getUpdateStatus());

        rec.getValue().setRecordName("Test Record ");

        assertTrue("3) Record is not inserted ", rec.isNew());
        assertEquals("4) is not updated update status " + rec.getUpdateStatus(), AbsRecord.UPDATED, rec.getUpdateStatus());
        assertEquals("4a) is not updated update status " + val.getUpdateStatus(), AbsRecord.UPDATED, val.getUpdateStatus());

        rec.setNew(false);
        rec.setUpdateStatus(AbsRecord.UNCHANGED);

        assertFalse("5) Record is inserted ", rec.isNew());
        assertEquals("6) is not unchanged update status " + rec.getUpdateStatus(), AbsRecord.UNCHANGED, rec.getUpdateStatus());
        assertEquals("6a) is not unchanged update status " + val.getUpdateStatus(), AbsRecord.UNCHANGED, val.getUpdateStatus());

        rec.getValue().setRecordName("Test Record ");
        assertFalse("7) Record is inserted ", rec.isNew());
        assertEquals("8) is not unchanged update status " + rec.getUpdateStatus(), AbsRecord.UNCHANGED, rec.getUpdateStatus());
        assertEquals("8a) is not unchanged update status " + val.getUpdateStatus(), AbsRecord.UNCHANGED, val.getUpdateStatus());

        rec.getValue().setRecordName("new value");
        assertFalse("9) Record is inserted ", rec.isNew());
        assertEquals("10) is not updated update status " + val.getUpdateStatus(), rec.getUpdateStatus(), AbsRecord.UPDATED);

        System.out.println("~~> " + rec.isNew() + " " + rec.getUpdateStatus());

    }

    /**
     * Test Field record is updating correctly
     *
     */
    public void testRecord() {
        RecordRec rec = RecordRec.getNullRecord("", Constants.rtGroupOfRecords, "");

        assertFalse("1) Record is inserted ", rec.isNew());
        assertEquals("2) is not unchanged update status " + rec.getUpdateStatus(), AbsRecord.BLANK_RECORD, rec.getUpdateStatus());
        assertEquals("2a) is not unchanged update status " + rec.getValue().getUpdateStatus(), AbsRecord.BLANK_RECORD, rec.getValue().getUpdateStatus());

        rec.getValue().setRecordName("new value");
        assertFalse("3) Record is inserted ", rec.isNew());
        assertEquals("4) is not updated update status " + rec.getUpdateStatus(), AbsRecord.UPDATED, rec.getUpdateStatus());
        assertEquals("4a) is not updated update status " + rec.getValue().getUpdateStatus(), AbsRecord.UPDATED, rec.getValue().getUpdateStatus());
        System.out.println("~~> " + rec.isNew() + " " + rec.getUpdateStatus());
   }


    public void testFieldFromExtarnal() {
        ExternalField val = new ExternalField();
        RecordFieldsRec rec;

        assertTrue("1) Value is inserted ", val.isNew());

        rec = new RecordFieldsRec(val);


        assertTrue("2) Value is inserted ", val.isNew());
        assertTrue("3) Value is inserted ", val.isNew());

        val = new ExternalField(
    	        1,
    	        4,
    	        "Field Name",
    	        "",
    	        Type.ftChar,
    	        0,
    	        Common.FORMAT_DEFAULT,
    	        "",
    	        "",
    	        "",
    	        1
    	  	 );
        System.out.println("4 ~~> " + val.isNew() + " " + val.getUpdateStatus());
        assertFalse("4) Value is not inserted ", val.isNew());
        assertEquals("5) is unchanged " + val.getUpdateStatus(), AbsRecord.UNCHANGED, val.getUpdateStatus());

        rec = new RecordFieldsRec(val);

        System.out.println("5 ~~> " + val.isNew() + " " + val.getUpdateStatus());

        System.out.println("6 ~~> " + rec.isNew() + " " + rec.getUpdateStatus());

        assertFalse("6) Value is not inserted ", val.isNew());
        assertFalse("7) Record is not inserted ", rec.isNew());
        assertEquals("8) is unchanged " + val.getUpdateStatus(), AbsRecord.UNCHANGED, val.getUpdateStatus());
        assertEquals("9) is unchanged " + val.getUpdateStatus(), AbsRecord.UNCHANGED, rec.getUpdateStatus());

        val.setName("New Name ...");
        assertFalse("10) Value is not inserted ", val.isNew());
        assertFalse("11) Record is not inserted ", rec.isNew());
        assertEquals("12) is changed " + val.getUpdateStatus(), AbsRecord.UPDATED, val.getUpdateStatus());
        assertEquals("13) is changed " + val.getUpdateStatus(), AbsRecord.UPDATED, rec.getUpdateStatus());

    }
}
