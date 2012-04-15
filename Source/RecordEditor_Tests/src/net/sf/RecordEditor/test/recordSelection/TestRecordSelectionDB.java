package net.sf.RecordEditor.test.recordSelection;

import java.util.List;

import net.sf.RecordEditor.re.db.Record.RecordSelectionDB;
import net.sf.RecordEditor.re.db.Record.RecordSelectionRec;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReConnection;
import junit.framework.TestCase;

public class TestRecordSelectionDB extends TestCase {
	TestValues vals = new TestValues();
	

	
	
	public void testRead() {
		RecordSelectionDB db = new RecordSelectionDB();
		int i = 0;
		
		db.setConnection(new ReConnection(Common.getConnectionIndex()));
		db.setParams(-1331, -1330);
		
		for (RecordSelectionRec[] tst : vals.tests) {
			vals.insertTest(-1331, tst);
			
			cmp("Read " + i, tst, db.fetchAll());
			
			db.deleteAll();
			i += 1;
		}
	}
	
	
	public void testWrite() {
		RecordSelectionDB db = new RecordSelectionDB();
		int i = 0;
		
		db.setConnection(new ReConnection(Common.getConnectionIndex()));
		db.setParams(-1331, -1330);
		
		for (RecordSelectionRec[] tst : vals.tests) {
			db.deleteAll();
			
			for (int j = 0; j <tst.length; j++) {
				tst[j].setFieldNo(j);
				db.insert(tst[j]);
			}
			
			cmp("Read " + i, tst, db.fetchAll());
			
			db.deleteAll();
			i += 1;
		}
	}
	
	
	private void cmp(String id, RecordSelectionRec[] expected, List<RecordSelectionRec> actual) {
		vals.compare(id, expected, actual);
	}
}
