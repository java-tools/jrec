package net.sf.JRecord.zTest.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import net.sf.JRecord.ByteIO.FixedLengthByteReader;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.Line;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.AbstractLineWriter;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.JRecord.IO.LineReaderWrapper;
import net.sf.JRecord.Numeric.ICopybookDialects;
import net.sf.JRecord.zTest.Common.TestCommonCode;
import junit.framework.TestCase;

public class TstFixedLineReaderWriter extends TestCase {
	private static final int WRITE_COUNT = 65;
	private static final String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRWXYZ1234567890,.<>:=-!@#$";
	
	private static final int[] TEST_SIZES = {1, 5, 10, 20};
	
	private static String[] copybooks = {
		"          01  fld1         pic x(1).\n",
		"          01  fld1         pic x(5).\n",
		"          01  fld1         pic x(10).\n",
		"          01  fld1         pic x(20).\n",
	};
	
	
	public void testWrite() throws IOException, RecordException {
		
		for (int k = 0; k < TEST_SIZES.length; k++) {
			int size = TEST_SIZES[k];
			byte[] b = buildByteArray(k);
			
			for (int i = 0; i < WRITE_COUNT; i++) {
				int st = i * size;
				int en1 = st + Math.min(size, i);
				int en2 = st + size;
				byte[] expected = getExpected(i, size);
				for (int j = st; j < en1; j++) {
					assertEquals("Tst1: " + size + " - " + i + ", " + j,
							expected[j-st], 
							b[j]);
				}
				for (int j = en1; j < en2; j++) {
					assertEquals("Tst2: " + size + " - "+ i + ", " + j, 0, b[j]);
				}
			}
		}
	}
	
	public void testRead() throws IOException, RecordException { 
		
		for (int k = 0; k < TEST_SIZES.length; k++) {
			int size = TEST_SIZES[k];
			byte[] buf = buildByteArray(k);
			AbstractLine l;
			LayoutDetail schema = getBldr(k);	
			
			AbstractLineReader r;
			
			LineIOProvider ioProvider = LineIOProvider.getInstance();
			
			for (int readerType = 0; readerType < 4; readerType++) {
				switch (readerType) {
				case 0: r =  ioProvider.getLineReader(schema);	break;
				case 1: r =  new LineReaderWrapper(new FixedLengthByteReader(schema.getMaximumRecordLength()));	break;
				//case 2: r =  new Fixed();	break;
				default:
					r =  ioProvider.getLineReader(schema.getFileStructure());
				}
			
				r.open(new ByteArrayInputStream(buf), schema); 
				int i = 0;
				while ((l = r.read()) != null) {
					byte[] expected = getExpected(i, size);
					byte[] b = l.getData();
					
					for (int j = 0; j < expected.length; j++) {
						assertEquals(expected[j], b[j]);
					}
					for (int j = expected.length; j < b.length; j++) {
						assertEquals(0, b[j]);
					}
					assertEquals(size, b.length);
					i += 1;
				}
				r.close();
			}
		}
	}
	
	private byte[] buildByteArray(int idx) throws IOException, RecordException {
		
		LayoutDetail schema = getBldr(idx);		
		
		ByteArrayOutputStream os = new ByteArrayOutputStream(650);
		AbstractLineWriter w = LineIOProvider.getInstance().getLineWriter(Constants.IO_FIXED_LENGTH);
		w.open(os); 
		for (int i = 0; i < WRITE_COUNT; i++) {
			Line l = new Line(schema, getStr(i).getBytes());
			
			w.write(l);
		}
		w.close();
		
		return os.toByteArray();
	}
	
	private LayoutDetail getBldr(int idx) throws RecordException {
		return TestCommonCode.getExternalRecordFromCobolStr(copybooks[idx], "Copybook", 0, "", ICopybookDialects.FMT_FUJITSU)
					.setFileStructure(Constants.IO_FIXED_LENGTH)
					.asLayoutDetail();
				
				
				/*JRecordInterface1.COBOL.newIOBuilder(
				new ByteArrayInputStream(copybooks[idx].getBytes()),
				"Copybook"
		).setFileOrganization(Constants.IO_FIXED_LENGTH);*/
	}
	
	private static byte[] getExpected(int idx, int recordLength) {
		String s = getStr(idx);
		if (s.length() > recordLength) {
			s = s.substring(0, recordLength);
		}
		
		return s.getBytes();
	}
	
	private static String getStr(int idx) {
		return CHARS.substring(0, idx);
	}
}
