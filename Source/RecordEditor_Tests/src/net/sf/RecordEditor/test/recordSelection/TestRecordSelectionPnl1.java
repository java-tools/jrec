package net.sf.RecordEditor.test.recordSelection;

import java.util.ArrayList;

import net.sf.RecordEditor.layoutEd.panels.RecordSelectionPnl;
import net.sf.RecordEditor.layoutEd.panels.TableUpdatePnl;
import net.sf.RecordEditor.re.db.Record.ChildRecordsRec;
import net.sf.RecordEditor.re.db.Record.RecordSelectionDB;
import net.sf.RecordEditor.re.db.Record.RecordSelectionRec;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.jdbc.DBtableModel;
import junit.framework.TestCase;

/**
 * This class perform basic tests on the screen RecordSelectionPnl
 * 
 * @author Bruce Martin
 *
 */
public class TestRecordSelectionPnl1 extends TestCase {

	TestValues vals = new TestValues();
	
	
	public void testRead() {
		RecordSelectionDB db = new RecordSelectionDB();
		int i = 0;
		int idx = Common.getConnectionIndex();
		ArrayList<RecordSelectionRec> screenRecs;
		rsPnl pnl;
		 DBtableModel<RecordSelectionRec> mdl;
		
		db.setConnection(new ReConnection(Common.getConnectionIndex()));
		db.setParams(-1331, -1330);
		
		for (RecordSelectionRec[] tst : vals.tests) {
			vals.insertTest(-1331, tst);
			pnl = new rsPnl("xx", idx, -1331);
			
			screenRecs = new ArrayList<RecordSelectionRec>(tst.length);
			
			mdl = pnl.getSelectionMdl();
			for (int j = 0 ; j < mdl.size(); j++) {
				screenRecs.add(mdl.getRecord(j));
			}
			vals.compare("Read " + i, tst, screenRecs);
			
			db.deleteAll();
			i += 1;
		}
	}
	
	public void testRead1() {
		RecordSelectionDB db = new RecordSelectionDB();
		int i = 0;
		int idx = Common.getConnectionIndex();
		rsPnl pnl;
		DBtableModel<RecordSelectionRec> mdl;
		String s;
		
		db.setConnection(new ReConnection(Common.getConnectionIndex()));
		db.setParams(-1331, -1330);
		
		for (RecordSelectionRec[] tst : vals.tests) {
			vals.insertTest(-1331, tst);
			pnl = new rsPnl("xx", idx, -1331);
			
			
			mdl = pnl.getSelectionMdl();
			assertEquals("Read1 ", tst.length, mdl.size());
			for (int j = 0 ; j < tst.length; j++) {
				s = i + "," + j;
				if (j == 0) {
					assertEquals("Read1 Or " + s, "", mdl.getValueAt(j, 0));
					assertEquals("Read1 And " + s, "", mdl.getValueAt(j, 1));
				} else if (tst[j].getBooleanOperator() == Common.BOOLEAN_OPERATOR_OR) {
					assertEquals("Read1 Or " + s, "Or", mdl.getValueAt(j, 0));
					assertEquals("Read1 And " + s, "", mdl.getValueAt(j, 1));
				} else {
					assertEquals("Read1 Or " + s, "", mdl.getValueAt(j, 0));
					assertEquals("Read1 And " + s, "And", mdl.getValueAt(j, 1));
				}
				assertEquals("Read1 Field Name " + s, tst[j].getFieldName(), mdl.getValueAt(j, 2));
				assertEquals("Read1 Operator " + s, tst[j].getOperator(), mdl.getValueAt(j, 3));
				assertEquals("Read1 Value " + s, tst[j].getFieldValue(), mdl.getValueAt(j, 4));
				i +=1;
			}
			
			db.deleteAll();
			i += 1;
		}
	}
	
	public void testWrite() {
		RecordSelectionDB db = new RecordSelectionDB();
		int i = 0;
		int idx = Common.getConnectionIndex();
		rsPnl pnl;
		DBtableModel<RecordSelectionRec> mdl;
		
		db.setConnection(new ReConnection(Common.getConnectionIndex()));
		db.setParams(-1331, -1330);
		
		for (RecordSelectionRec[] tst : vals.tests) {
			db.deleteAll();
			pnl = new rsPnl("xx", idx, -1331);
			
			mdl = pnl.getSelectionMdl();
			for (int j = 0 ; j < tst.length; j++) {
				mdl.setRecord(j, tst[j]);
			}
			pnl.save(false);
			vals.compare("Read " + i + " ", tst, db.fetchAll());
			
			//db.deleteAll();
			i += 1;
		}
	}

	
	public void testWrite1() {
		RecordSelectionDB db = new RecordSelectionDB();
		int i = 0;
		int idx = Common.getConnectionIndex();
		rsPnl pnl;
		DBtableModel<RecordSelectionRec> mdl;
		TableUpdatePnl<RecordSelectionRec> upd;
		
		db.setConnection(new ReConnection(Common.getConnectionIndex()));
		db.setParams(-1331, -1330);
		
		for (RecordSelectionRec[] tst : vals.tests) {
			db.deleteAll();
			pnl = new rsPnl("xx", idx, -1331);
			
			mdl = pnl.getSelectionMdl();
			upd = pnl.getUpdatePnl();
			for (int j = 0 ; j < tst.length; j++) {
				upd.executeAction(ReActionHandler.INSERT_RECORDS);
			}
			for (int j = 0 ; j < tst.length; j++) {
				mdl.getRecord(j).setBooleanOperator(tst[j].getBooleanOperator());
				mdl.setValueAt(tst[j].getFieldName(), j, 2);
				mdl.setValueAt(tst[j].getOperator(), j, 3);
				mdl.setValueAt(tst[j].getFieldValue(), j, 4);
			}
			pnl.save(false);
			vals.compare("Read " + i + " ", tst, db.fetchAll());
			
			//db.deleteAll();
			i += 1;
		}
	}

	@SuppressWarnings("serial")
	private class rsPnl extends RecordSelectionPnl {

		public rsPnl(String dbName, int dbConnectionIdx, int recordIdentifier) {
			super(dbName, dbConnectionIdx, recordIdentifier, 
					new ChildRecordsRec(-1332, 0, "", "", recordIdentifier, -1330, 0, false, "", -1332));
		}
		
		public rsPnl(String dbName, int dbConnectionIdx, int recordIdentifier,
				ChildRecordsRec parent) {
			super(dbName, dbConnectionIdx, recordIdentifier, parent);
		}
		
		
		public DBtableModel<RecordSelectionRec> getSelectionMdl() {
			return selectionMdl;
		}

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.layoutEd.Record.RecordSelectionPnl#getUpdatePnl()
		 */
		@Override
		protected TableUpdatePnl<RecordSelectionRec> getUpdatePnl() {
			// TODO Auto-generated method stub
			return super.getUpdatePnl();
		}
	}
}
