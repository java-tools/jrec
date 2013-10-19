/**
 *
 */
package net.sf.JRecord.zTest.ByteIO;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import net.sf.JRecord.ByteIO.AbstractByteReader;
import net.sf.JRecord.ByteIO.ByteTextReader;
import net.sf.JRecord.ByteIO.CsvByteReader;
import net.sf.JRecord.Common.Conversion;

import junit.framework.TestCase;

/**
 * Purpose:<ol>
 *  <li>Test Mismatched End-of-line characters</li>
 *  <li>Volume tests
 * </ol>
 * @author Bruce Martin
 *
 */
public class TstBinTextReader3 extends TestCase {

	public void testStdCr() throws IOException {

		doFontCrTest("StdLine", "", new StdLine());
	}

	public void testStdCrCp037() throws IOException {

		doFontCrTest("StdLine cp037 ", "cp037", new StdLine());
	}


	public void testCsvStdCr() throws IOException {

		doCsvFontCrTest("Embedded Cr", "");
	}


	public void testCsvStdCrCp037() throws IOException {

		doCsvFontCrTest("Embedded Cr", "cp037");
	}

	private void doFontCrTest(String id, String font, FormatLine fl) throws IOException {

		doTest(id + " \\n) ", new ByteTextReader(font), fl, font, "\n", "\n");
		doTest(id + " \\r) ", new ByteTextReader(font), fl, font, "\r", "\r");
		doTest(id + " \\r\\n) ", new ByteTextReader(font), fl, font, "\r\n", "\r\n");

		doTest(id + " \\n) ", new CsvByteReader(font, ",", "", "", true), fl, font, "\n", "\n");
		doTest(id + " \\r) ", new CsvByteReader(font, ",", "", "", true), fl, font, "\r", "\r");
		doTest(id + " \\r\\n) ", new CsvByteReader(font, ",", "", "", true), fl, font, "\r\n", "\r\n");

		doTest(id + " \\r, \\r\\n) ", new ByteTextReader(font), fl, font, "\r", "\r\n");
		doTest(id + " \\n, \\r\\n) ", new ByteTextReader(font), fl, font, "\r", "\r\n");

		doTest(id + " \\r, \\r\\n) ", new CsvByteReader(font, ",", "", "", true), fl, font, "\r", "\r\n");
		doTest(id + " \\n, \\r\\n) ", new CsvByteReader(font, ",", "", "", true), fl, font, "\n", "\r\n");
	}

	private void doCsvFontCrTest(String id, String font) throws IOException {

		//FormatLine fl = EmbeddedCrFormater();


		doTest(id + " \\n) ", new CsvByteReader(font, ",", "\"", "\"\"", true), new EmbeddedCrFormater("\n"), font, "\n", "\n");
		doTest(id + " \\r) ", new CsvByteReader(font, ",", "\"", "\"\"", true),  new EmbeddedCrFormater("\n"), font, "\r", "\r");
		doTest(id + " \\r\\n) ", new CsvByteReader(font, ",", "\"", "\"\"", true),  new EmbeddedCrFormater("\n"), font, "\r\n", "\r\n");

		doTest(id + " \\n) ", new CsvByteReader(font, ",", "\"", "\"\"", true), new EmbeddedCrFormater("\r"), font, "\n", "\n");
		doTest(id + " \\r) ", new CsvByteReader(font, ",", "\"", "\"\"", true),  new EmbeddedCrFormater("\r"), font, "\r", "\r");
		doTest(id + " \\r\\n) ", new CsvByteReader(font, ",", "\"", "\"\"", true),  new EmbeddedCrFormater("\r"), font, "\r\n", "\r\n");

		doTest(id + " \\n) ", new CsvByteReader(font, ",", "\"", "\"\"", true), new EmbeddedCrFormater("\r\n"), font, "\n", "\n");
		doTest(id + " \\r) ", new CsvByteReader(font, ",", "\"", "\"\"", true),  new EmbeddedCrFormater("\r\n"), font, "\r", "\r");
		doTest(id + " \\r\\n) ", new CsvByteReader(font, ",", "\"", "\"\"", true),  new EmbeddedCrFormater("\r\n"), font, "\r\n", "\r\n");

		doTest(id + " \\r, \\r\\n) ", new CsvByteReader(font, ",", "\"", "\"\"", true), new EmbeddedCrFormater("\r"), font, "\r", "\r\n");
		doTest(id + " \\n, \\r\\n) ", new CsvByteReader(font, ",", "\"", "\"\"", true),  new EmbeddedCrFormater("\n"), font, "\n", "\r\n");
	}

	private void doTest(String id, AbstractByteReader r, FormatLine fl, String font, String eol1, String eol2)
	throws IOException {
		String s;
		byte[] b;
		int i = 0;

		r.open(getFile1(fl, font, eol1, eol2));

		while ((b = r.read()) != null) {
			assertEquals(id + ": " + i, fl.formatLine(i), Conversion.toString(b, font));
			i += 1;
		}
		r.close();
	}

	public static ByteArrayInputStream getFile1(FormatLine fl, String font, String eol1, String eol2) {
		StringBuilder b = new StringBuilder(fl.formatLine(0) + eol1);

		for (int i = 1; i < 50000; i++) {
			b.append(fl.formatLine(i) + eol2);
		}

		return new ByteArrayInputStream(Conversion.getBytes(b.toString(), font));
	}

	public static interface FormatLine {
		public String formatLine(int code);
	}

	public static class StdLine implements FormatLine {
	private static long l = Integer.MAX_VALUE;

		public String formatLine(int c) {
			long code = l + c;
			String k = "" + code;
			StringBuilder b = new StringBuilder(k);
			int n = (c % 16) + 5;

			for (int i = 5; i < n; i++) {
				b.append(',').append((120 + i) + "").append(',').append(k);
			}

			return b.toString();
		}
	}

	public static class EmbeddedCrFormater implements FormatLine {
		private static long l = Integer.MAX_VALUE;

		private final String eol;


		public EmbeddedCrFormater(String eol) {
			super();
			this.eol = eol;
		}


		public String formatLine(int c) {
			long code = l + c;
			String k = "" + code;
			StringBuilder b = new StringBuilder(k);
			int n = (c % 16) + 5;

			for (int i = 5; i < n; i++) {
				b.append(',').append('"').append((120 + i) + eol + " " + k).append('"').append(',').append(k);
			}

			return b.toString();
		}
	}
}
