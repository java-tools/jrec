package net.sf.JRecord.zTest.ByteIO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import net.sf.JRecord.ByteIO.FixedLengthByteReader;
import net.sf.JRecord.ByteIO.FixedLengthByteWriter;
import junit.framework.TestCase;

public class TstFixedByteReaderWriter extends TestCase {
	private static final int WRITE_COUNT = 65;
	private static final String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRWXYZ1234567890,.<>:=-!@#$";
	
	private static final int[] TEST_SIZES = {1, 5, 10, 20};
	
	
	public void testWrite() throws IOException {
		
		for (int size : TEST_SIZES) {
			testWriter(size, buildByteArray1(size));
		}
	}
	
	private void testWriter(int size, byte[] b) {
		
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
	
	
	public void testRead() throws IOException { 
		
		for (int size : TEST_SIZES) {
			byte[] buf = buildByteArray1(size);
			byte[] b;
			FixedLengthByteReader r = new FixedLengthByteReader(size);
			
			r.open(new ByteArrayInputStream(buf));
			
			int i = 0;
			while ((b = r.read()) != null) {
				byte[] expected = getExpected(i, size);
				
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
	
	private byte[] buildByteArray1(int recordLength) throws IOException {
		FixedLengthByteWriter w = new FixedLengthByteWriter(recordLength);
		
		ByteArrayOutputStream os = new ByteArrayOutputStream(650);
		w.open(os);
		for (int i = 0; i < WRITE_COUNT; i++) {
			w.write(getStr(i).getBytes());
		}
		w.close();
		
		return os.toByteArray();
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