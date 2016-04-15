package net.sf.RecordEditor.test.filestorage3;

import java.io.IOException;

import junit.framework.TestCase;
import net.sf.JRecord.Common.RecordException;
import net.sf.RecordEditor.utils.params.Parameters;


public class TstLargeFixedList5 extends TestCase {
	
	public void testFixed21() throws IOException, RecordException {
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		TstLargeFixedList main = new TstLargeFixedList();
		
		for (int i = 0; i < 40; i ++) {
			main.tstMultipleChanges(true, "Fixed 21.1." + i + " ", i * 10 + 1217, 0, 15 + i);
		}
	}
	
	
	public void testFixed22() throws IOException, RecordException {
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "3");
		TstLargeFixedList main = new TstLargeFixedList();
		
		for (int i = 0; i < 15; i ++) {
			main.tstMultipleChanges(true, "Fixed 22.1." + i + " ", i * 10 + 1417, 0, 15 + i);
		}
	}
	
	public void testFixed23() throws IOException, RecordException {
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "100");
		TstLargeFixedList main = new TstLargeFixedList();
		
		for (int i = 0; i < 5; i ++) {
			main.tstMultipleChanges(true, "Fixed 23.1." + i + " ", i * 10 + 1517, 0, 15 + i);
		}
	}
}
