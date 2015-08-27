package net.sf.JRecord.zTest.ioProvider;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.BinTextReader;
import net.sf.JRecord.IO.FixedLengthTextReader;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.JRecord.IO.TextLineReader;
import junit.framework.TestCase;

public class TstIOProvider extends TestCase {

	public void testGetReader() {
		LineIOProvider iop = LineIOProvider.getInstance();
	
		AbstractLineReader lineReader = iop.getLineReader(Constants.IO_FIXED_LENGTH_CHAR);
		assertTrue(lineReader.getClass().getSimpleName(), lineReader instanceof FixedLengthTextReader);
		
		lineReader = iop.getLineReader(Constants.IO_UNICODE_TEXT);
		assertTrue(lineReader.getClass().getSimpleName(), lineReader instanceof TextLineReader);
		
		lineReader = iop.getLineReader(Constants.IO_UNICODE_NAME_1ST_LINE);
		assertTrue(lineReader.getClass().getSimpleName(), lineReader instanceof TextLineReader);
		
		lineReader = iop.getLineReader(Constants.IO_BIN_TEXT);
		assertTrue(lineReader.getClass().getSimpleName(), lineReader instanceof BinTextReader);
		
		lineReader = iop.getLineReader(Constants.IO_BIN_NAME_1ST_LINE);
		assertTrue(lineReader.getClass().getSimpleName(), lineReader instanceof BinTextReader);
	}

}
