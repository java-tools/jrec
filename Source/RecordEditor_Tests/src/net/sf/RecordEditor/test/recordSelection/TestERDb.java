package net.sf.RecordEditor.test.recordSelection;

import junit.framework.TestCase;
import net.sf.JRecord.detailsSelection.RecordSel;
import net.sf.RecordEditor.re.db.Record.ChildRecordsDB;
import net.sf.RecordEditor.re.db.Record.ChildRecordsRec;
import net.sf.RecordEditor.re.db.Record.ExtendedRecordDB;
import net.sf.RecordEditor.re.db.Record.RecordSelectionDB;
import net.sf.RecordEditor.re.db.Record.RecordSelectionRec;
import net.sf.RecordEditor.utils.ReadRecordSelection;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReConnection;
/**
 * This class tests writing Record Selection records from ExtendedRecordDB
 * It depends on the ReadRecordSelection tests working
 * 
 * @author Bruce Martin
 *
 */
public class TestERDb extends TestCase {

	TestValues vals = new TestValues();
	
	public void testWrite1() {

		DBs dbs = new DBs();
		RecordSel sel;
		RecordSelectionRec[] tst;
		
		for (int i = 0; i < vals.tests.length; i++) {
			tst =  vals.tests[i];
			vals.insertTest(-1331, tst);
			sel = ReadRecordSelection.getInstance().getRecordSelection(
					Common.getConnectionIndex(), dbs.key1, dbs.key1+1, null, "", "");
			
			dbs.w.writeRecordSelection(dbs.key2, dbs.key2+1, sel);
			vals.compare("Write RecSel " + i, tst, dbs.db2.fetchAll());
			
			dbs.deleteAll();
		}
		
		dbs.close();
	}
	
	
	public void testWrite2() {

		DBs dbs = new DBs();
		ChildRecordsDB childDb = new ChildRecordsDB();
		ChildRecordsRec childRec;
		RecordSel sel;
		RecordSelectionRec[] tst;
		
		dbs.db2.setParams(dbs.key2, 1);
		childDb.setConnection(dbs.con);
		childDb.setParams(dbs.key2);
		childDb.deleteAll();
		
		for (int i = 1; i < vals.tests.length; i++) {
			tst =  vals.tests[i];
			vals.insertTest(-1331, tst);
			sel = ReadRecordSelection.getInstance().getRecordSelection(
					Common.getConnectionIndex(), dbs.key1, dbs.key1+1, null, "fff", "vvv");
			childRec = new ChildRecordsRec(dbs.key2 + 1, 0, "", "", dbs.key2, dbs.key2 + 1, 0, false, "", dbs.key2);
			
			dbs.w.writeChild(childDb, dbs.key2, childRec, sel);
			
			assertEquals("Check Field " + i, "fff", childRec.getField());
			assertEquals("Check Value " + i, "vvv", childRec.getFieldValue());
			vals.compare("Write RecSel " + i, tst, dbs.db2.fetchAll());
			
			dbs.deleteAll();
			childDb.deleteAll();
		}
		
		childDb.close();
		dbs.close();
	}

	private static class DBs {
		int key1 = -1331;
		int key2 = -2332;

		RecordSelectionDB db1 = new RecordSelectionDB();
		RecordSelectionDB db2 = new RecordSelectionDB();
		ReConnection con = new ReConnection(Common.getConnectionIndex());

		
		ExtendedRecordDB w = new ExtendedRecordDB();
		
		public DBs() {
			db1.setConnection(con);
			db2.setConnection(con);
			
			db1.setParams(key1, key1 + 1);
			db2.setParams(key2, key2 + 1);
			
			db1.deleteAll();
			db2.deleteAll();
			
			w.setConnection(con);

		}

		public void deleteAll() {
			db1.deleteAll();
			db2.deleteAll();
		}

		public void close() {
			db1.close();
			db2.close();
			w.close();
		}
	}
}
