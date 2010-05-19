/*
 * Created on 12/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.sf.RecordEditor.test;

import java.util.ArrayList;

import net.sf.JRecord.Log.TextLog;
import net.sf.RecordEditor.layoutEd.Record.RecordFieldsDB;
import net.sf.RecordEditor.layoutEd.Record.RecordFieldsRec;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.jdbc.AbsDB;

import junit.framework.TestCase;

/**
 * @author bymartin
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TstRecordFieldsDB extends TestCase {

//	private byte[] nullBytes = {};
	private int dbIdx = TstConstants.DB_INDEX;
	private final int recordId = -1;
	private final int typeChar = 0;
//	private final int typeNum = 7;
//	private RecordFieldsRec insRec = null;
	private RecordFieldsDB db;



	public static void main(String[] args) {
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		db = commonInit();

	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();

		commonClose();
	}

	private RecordFieldsDB commonInit() {
		RecordFieldsDB rfDatabase = new RecordFieldsDB();

		AbsDB.setSystemLog(new TextLog());

		//System.out.println("Common Init ...");
		try {
			rfDatabase.setConnection(new ReConnection(dbIdx));
		} catch (Exception e) {
		}

		rfDatabase.setParams(recordId);


		return rfDatabase;
	}


	private RecordFieldsRec getRecord() {
		db.open();
		RecordFieldsRec rec = db.fetch();
		db.close();

		return rec;
	}


	private RecordFieldsRec insRecord(
					int pos, int length, int type) {
		RecordFieldsRec rec = new RecordFieldsRec(pos, length,
									"Field " + pos,
									"", type, 0, -1, "", "",
									"CobolName" + pos, pos);
/*
 *
                     int pPos
                  , int pLen
                  , String pName
                  , String pDescription
                  , int pType
                  , int pDecimal
                  , String pDefault
                  , String pCobolName
                  , int pSubKey

 */
		db.insert(rec);

		return rec;
	}


	private void commonClose() {

		db.setParams(recordId);
		try {
			db.deleteAll();
		} catch (Exception e) {
		}

		db.close();

		Common.closeConnection();
	}


	public void testInsert() {
		RecordFieldsRec rec = insRecord(1, 3, typeChar);
		db.resetSearch();
		db.setSearchArg("SubKey", AbsDB.opEquals, Integer.toString(rec.getValue().getSubKey()));
		RecordFieldsRec gotRecord = getRecord();

		assertNotNull("Insert: record is null", gotRecord);
		assertEquals("Insert: Differences", rec.getValue().getName(), gotRecord.getValue().getName());
	}

	@SuppressWarnings("unchecked")
	public void testFetchAll() {
		RecordFieldsRec rec1 = insRecord(1, 3, typeChar);
//		RecordFieldsRec rec2 = insRecord(1, 3, typeChar);
		db.resetSearch();

		ArrayList l = db.fetchAll();
		assertEquals("FetchAll: Fetch Failed Count=" + l.size(),
					1, l.size());

		RecordFieldsRec gotRecord = (RecordFieldsRec) l.get(0);
		assertEquals("Insert: Differences", rec1.getValue().getName(), gotRecord.getValue().getName());
	}

	@SuppressWarnings("unchecked")
	public void testDeleteAll() {
		db.resetSearch();
		db.deleteAll();

		ArrayList l = db.fetchAll();
		assertEquals("DeleteAll: Fetch Failed Count=" + l.size(),
					0, l.size());
	}

	public void testUpdate() {
		RecordFieldsRec rec = insRecord(1, 3, typeChar);
		rec.getValue().setName("Updated - " + rec.getValue().getName());
		db.update(rec);
		db.resetSearch();
		db.setSearchArg("SubKey", AbsDB.opEquals, Integer.toString(rec.getValue().getSubKey()));
		RecordFieldsRec gotRecord = getRecord();

		assertNotNull("Update: record is null", gotRecord);
		assertEquals("Update: Differences", rec.getValue().getName(), gotRecord.getValue().getName());
	}

	@SuppressWarnings("unchecked")
	public void testDelete() {
		RecordFieldsRec rec1 = insRecord(1, 3, typeChar);
		db.delete(rec1);
		db.resetSearch();

		ArrayList l = db.fetchAll();
		assertEquals("Delete: Fetch Failed Count=" + l.size(),
					0, l.size());
	}

}
