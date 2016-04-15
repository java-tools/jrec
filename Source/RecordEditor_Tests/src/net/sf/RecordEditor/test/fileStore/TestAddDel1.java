package net.sf.RecordEditor.test.fileStore;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.Line;
import net.sf.RecordEditor.testcode.StdSchemas;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.fileStorage.DataStoreLarge;
import net.sf.RecordEditor.utils.fileStorage.DataStoreStd;
import net.sf.RecordEditor.utils.fileStorage.FileDetails;
import net.sf.RecordEditor.utils.fileStorage.IDataStore;
import net.sf.RecordEditor.utils.params.Parameters;
import junit.framework.TestCase;


/**
 * This class does a basic test of adding and deleting lines
 * to  data store.
 *  
 * @author Bruce Martin
 *
 */
public class TestAddDel1 extends TestCase {

	private final static LayoutDetail SCHEMA = StdSchemas.TWENTY_BYTE_RECORD_SCHEMA;
	private final static int TST_LEN = 300;
	
	public void testStdDataStore1() {
		tstAddRemove1(new DataStoreStd.DataStoreStdBinary<AbstractLine>(SCHEMA) );
	}
	
	public void testFixedLargeDataStore1() {
		tstAddRemove1(new DataStoreLarge(SCHEMA, FileDetails.FIXED_LENGTH, SCHEMA.getMaximumRecordLength()) );
	}
	
	public void testVLargeDataStore1() {
		tstAddRemove1(new DataStoreLarge(SCHEMA, FileDetails.VARIABLE_LENGTH, SCHEMA.getMaximumRecordLength()) );
	}

	
	public void testStdDataStore2() {
		tstAddRemove2(new DataStoreStd.DataStoreStdBinary<AbstractLine>(SCHEMA) );
	}
	
	public void testFixedLargeDataStore2() {
		tstAddRemove2(new DataStoreLarge(SCHEMA, FileDetails.FIXED_LENGTH, SCHEMA.getMaximumRecordLength()) );
	}
	
	public void testFixedLargeDataStore2a() {
		Parameters.setSavePropertyChanges(false);
		String p  = Parameters.getString(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		
		tstAddRemove2(new DataStoreLarge(SCHEMA, FileDetails.FIXED_LENGTH, SCHEMA.getMaximumRecordLength()) );
		
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, p);
	}
	
	public void testVLargeDataStore2() {
		tstAddRemove2(new DataStoreLarge(SCHEMA, FileDetails.VARIABLE_LENGTH, SCHEMA.getMaximumRecordLength()) );
	}

	
	public void testVLargeDataStore2a() {
		Parameters.setSavePropertyChanges(false);
		String p  = Parameters.getString(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		
		tstAddRemove2(new DataStoreLarge(SCHEMA, FileDetails.VARIABLE_LENGTH, SCHEMA.getMaximumRecordLength()) );
		
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, p);
	}

	private void tstAddRemove1(IDataStore<AbstractLine> ds) {
		load(ds, TST_LEN);
		for (int i = 0; i  < TST_LEN; i++) {
			ds.add(i, new Line(SCHEMA, addVal(i)));
			
			assertEquals("add Size: " + i, TST_LEN + 1,  ds.size());
			checkList(ds, i, 0);

			ds.remove(i);
			assertEquals("remove Size: " + i, TST_LEN,  ds.size());
			for (int j = 0; j  < ds.size(); j++) {
				assertEquals("remove: " + i + ", " + j, stdVal(i), ds.get(i).getFullLine());
			}
		}
	}
	
	
	private void tstAddRemove2(IDataStore<AbstractLine> ds) {
		int l = 500;
		int addCount = 30;
		load(ds, l);
		for (int i = 0; i  < l; i++) {
			for (int k = 0; k  < addCount; k++) {
				ds.add(i+k, new Line(SCHEMA, addVal(i + k)));
				
				assertEquals("add Size: " + i + ", " + k, l + k + 1,  ds.size());
				checkList(ds, i, k);
			}
			for (int k = addCount - 1; k >0; k--) {
				ds.remove(i + k);
				assertEquals("add Size: " + i + ", " + k, l + k,  ds.size());
				checkList(ds, i, k - 1);
			}
			ds.remove(i);
			assertEquals("remove Size: " + i, l,  ds.size());
			for (int j = 0; j  < ds.size(); j++) {
				assertEquals("remove: " + i + ", " + j, stdVal(j), ds.get(j).getFullLine());
			}
		}
	}

	private void checkList(IDataStore<AbstractLine> ds, int i, int k) {
		
		for (int j = 0; j  < ds.size(); j++) {
			String message = i + ", " + j + ", " + k;
			if (i > j) {
				assertEquals("add 1: " + message, stdVal(j), ds.get(j).getFullLine());
			} else if (j <= i + k) {
				assertEquals("add 2: " + message, addVal(j), ds.get(j).getFullLine());
			} else {
				assertEquals("add 3: " + message, stdVal(j - k - 1), ds.get(j).getFullLine());
			}
		}
	}

	
	private void load(IDataStore<AbstractLine> ds, int len) {
		
		for (int i = ds.size() - 1; i >= 0; i--) {
			ds.remove(i);
		}
		for (int i = 0; i  < len; i++) {
			ds.add(new Line(SCHEMA, stdVal(i)));
		}
	}
	
	
	private static String stdVal(int i) {
		return incLength("Line: " + i);
	}
	
	
	private static String addVal(int i) {
		return incLength("Add: " + i);
	}
	
	private static String incLength(String s) {
		return (s + "                    ").substring(0, 20);
	}
}
