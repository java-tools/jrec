/*
 * Created on 12/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.sf.RecordEditor.test;

import java.util.ArrayList;

import net.sf.JRecord.Log.TextLog;
import net.sf.RecordEditor.re.db.Record.RecordDB;
import net.sf.RecordEditor.re.db.Record.RecordRec;
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
public class TstRecordDB extends TestCase {

	private static final  int DB_INDEX = TstConstants.DB_INDEX;
	private RecordRec insRec = null;
	private RecordDB db = null;



	public static void main(String[] args) {
	}

	/**
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

		commonClose(db);
	}

	private RecordDB commonInit() {
		RecordDB db = new RecordDB();


		db.setConnection(new ReConnection(DB_INDEX));
		AbsDB.setSystemLog(new TextLog());

		return db;
	}


	private RecordRec getRecord(RecordDB db) {
		db.open();
		RecordRec rec = db.fetch();
		db.close();

		return rec;
	}


	private void insRecord(RecordDB db) {
		insRec = RecordRec.getNullRecord("zzTest Recordzz", "");

		db.insert(insRec);
	}


	private void commonClose(RecordDB db) {

		if (insRec != null) {
			db.delete(insRec);
			insRec = null;
		}

		db.close();
		Common.closeConnection();
	}


	public void testInsert() {
		insRecord(db);
		db.resetSearch();
		db.setSearchArg("RecordId",
						AbsDB.opEquals,
						Integer.toString(insRec.getRecordId()));
		RecordRec rec = getRecord(db);
		assertNotNull("Insert: No Record Retrieved", rec);
		assertEquals("Insert: Different Name", rec.getRecordName(), insRec.getRecordName());
	}


	public void testDelete() {
		insRecord(db);

		db.delete(insRec);

		db.resetSearch();
		db.setSearchArg("RecordId",
						AbsDB.opEquals,
						Integer.toString(insRec.getRecordId()));
		RecordRec rec = getRecord(db);
		assertNull("Delete: Record Retrieved", rec);
	}


	public void testGetColumnCount() {
		assertEquals(16, db.getColumnCount());
		commonClose(db);
	}

	public void testFetch() {
		RecordRec rec = getRecord(db);
		assertNotNull("Fetch: No Record Retrieved", rec);
	}

	public void testSetSearchRecordName() {
		String pref = "am";
		RecordDB db = commonInit();
		db.setSearchRecordName(AbsDB.opLike, pref + "%");
		RecordRec rec = getRecord(db);

		assertTrue(rec.getRecordName().startsWith(pref));
		commonClose(db);
	}

	public void testSetSearchDescription() {
		//TODO Implement setSearchDescription().
	}

	public void testSetSearchRecordType() {
		int rType = 1;
		db.setSearchRecordType(AbsDB.opEquals, rType);
		RecordRec rec = getRecord(db);

		assertEquals(rType, rec.getValue().getRecordType());
		commonClose(db);
	}

	public void testSetSearchSystem() {
		int sys = 7;
		db.setSearchSystem(AbsDB.opEquals, sys);
		RecordRec rec = getRecord(db);

		assertEquals(sys, rec.getSystem());
		commonClose(db);
	}

	public void testSetSearchListChar() {
		//TODO Implement setSearchListChar().
	}


	public void testUpdate() {
		insRec = RecordRec.getNullRecord("zzTest Recordzz", "");

		insRecord(db);
		db.resetSearch();
		db.setSearchArg("RecordId",
						AbsDB.opEquals,
						Integer.toString(insRec.getRecordId()));
		insRec.getValue().setDescription("new Description");

		db.update(insRec);

		RecordRec rec = getRecord(db);
		assertNotNull("Update: No Record Retrieved", rec);
		assertEquals("Update: Different Name",
					 rec.getValue().getDescription(), insRec.getValue().getDescription());
	}


	public void testFetchAll() {
		db.open();
		ArrayList l = db.fetchAll();
		commonClose(db);
		assertTrue((l.size() > 0));
	}

	public void testSetSystemLog() {
		//TODO Implement setSystemLog().
	}

}
