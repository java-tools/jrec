package net.sf.JRecord.zTest.ByteIO;

import net.sf.JRecord.ByteIO.CsvByteReader;
import junit.framework.TestCase;

public class TstCsvByteReader01 extends TestCase {



	/**
	 * Testing the check for function
	 */
	public void testCheck4() {
		CC tst = new CC("", ",", "'", "''");
		byte[] byteAComma = "a,".getBytes();
		byte[] byteCommaA = ",a".getBytes();
		byte[] byteComma = ",".getBytes();
		byte[] byteQuoteComma = "',".getBytes();
		byte[] byteCommaQuote = ",'".getBytes();
		byte[] byteEnd = ",'bbb',".getBytes();
		byte[] byteMiddle = ",'aaa',".getBytes();

		byte[] byteData = "a,'aaa','bbb',".getBytes();
		int endData = byteData.length-1;
		int middle = 7;

		tst.setBuffer( byteData);
		assertFalse("check ,a at start", tst.check4(0, byteCommaA));
		assertFalse("check a, at 1", tst.check4(1, byteCommaA));
		assertFalse("check a, at 2", tst.check4(2, byteCommaA));
		assertFalse("check a, at " + (endData), tst.check4(endData, byteCommaA));
		assertFalse("check ,' at 1", tst.check4(1, byteCommaQuote));

		assertTrue("check a, at 1", tst.check4(1, byteAComma));
		assertTrue("check ,' at 2", tst.check4(2, byteCommaQuote));

		assertFalse("check a, at " + (endData), tst.check4(endData, byteCommaA));
		assertFalse("check  at " + (endData-1), tst.check4(endData-1, byteQuoteComma));
		assertFalse("check  at " + (endData), tst.check4(endData, byteCommaQuote));

		assertTrue("check  at " + (endData), tst.check4(endData, byteComma));
		assertTrue("check  at " + (endData), tst.check4(endData, byteQuoteComma));
		assertTrue("check  at " + (endData), tst.check4(endData, byteEnd));

		assertTrue("check  at " + (middle), tst.check4(middle, byteComma));
		assertTrue("check  at " + (middle), tst.check4(middle, byteQuoteComma));
		assertTrue("check  at " + (middle), tst.check4(middle, byteMiddle));

	}

	private static class CC extends CsvByteReader {

		public CC(String charSet, String fieldSep, String quote, String quoteEsc) {
			super(charSet, fieldSep, quote, quoteEsc, true);
		}

		public void setBuffer(byte[] buf) {
			System.arraycopy(buf, 0, super.buffer, 0, buf.length);
			super.bytesInBuffer = buf.length;
		}

		public boolean check4(int pos, byte[] search) {
			return super.checkFor(pos, search);
		}
	}
}
