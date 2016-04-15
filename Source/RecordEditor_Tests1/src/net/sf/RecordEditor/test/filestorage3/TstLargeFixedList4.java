package net.sf.RecordEditor.test.filestorage3;

import java.io.IOException;

import junit.framework.TestCase;
import net.sf.JRecord.Common.RecordException;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.params.Parameters;

public class TstLargeFixedList4 extends TestCase {
	
	public void testFixed9() throws IOException, RecordException {
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.agressiveCompress.set(false);
		
		TstLargeFixedList main = new TstLargeFixedList();
		
		for (int i = 0; i < 40; i ++) {
			main.tstMultipleChanges(false, "Fixed 9.1." + i + " ", i * 10 + 1217, 0, 15 + i);
		}
	}
	
	
	public void testFixed10() throws IOException, RecordException {
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "3");
		Common.OPTIONS.agressiveCompress.set(false);
		
		TstLargeFixedList main = new TstLargeFixedList();
		
		for (int i = 0; i < 15; i ++) {
			main.tstMultipleChanges(false, "Fixed 10.1." + i + " ", i * 10 + 1417, 0, 15 + i);
		}
	}
	
	public void testFixed11() throws IOException, RecordException {
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "100");
		Common.OPTIONS.agressiveCompress.set(false);
		
		TstLargeFixedList main = new TstLargeFixedList();
		
		for (int i = 0; i < 5; i ++) {
			main.tstMultipleChanges(false, "Fixed 11.1." + i + " ", i * 10 + 1517, 0, 15 + i);
		}
	}
}
