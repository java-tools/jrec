package edit.display.saveAs;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.LineProvider;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.AbstractLineWriter;
import net.sf.JRecord.IO.LineIOProvider;

import org.junit.Test;

import xCommon.XFileData;
import xCommon.XSchemas;

public class TstReadJapanese {

	@Test
	public void testReadCase1() throws IOException {
		String[][] case1Data = XFileData.case1Data();
		
		tstRead(XSchemas.CASE_1, Constants.IO_UNICODE_TEXT, XFileData.CASE_1_STR, case1Data);
		tstWriteAndRead(XSchemas.CASE_1, Constants.IO_UNICODE_TEXT, case1Data);
		tstWriteAndRead(XSchemas.CASE_1, Constants.IO_FIXED_LENGTH_CHAR, case1Data);
	}

	@Test
	public void testReadCase2() throws IOException {
		String[][] case2Data = XFileData.case2Data();
		
		tstRead(XSchemas.CASE_2, Constants.IO_BIN_TEXT, XFileData.CASE_2_STR, case2Data);

		System.out.println(XFileData.CASE_2_STR);
		tstWriteAndRead(XSchemas.CASE_2, Constants.IO_BIN_TEXT, case2Data);
		tstWriteAndRead(XSchemas.CASE_2, Constants.IO_FIXED_LENGTH, case2Data);
	}
	
	private void tstWriteAndRead(XSchemas schemaDef, int fileStructure,  String[][] expected) throws IOException {
		LayoutDetail layout = schemaDef.toLayout("Shift_JIS", fileStructure);
		LineIOProvider ioProvider = LineIOProvider.getInstance();
		AbstractLineWriter writer = ioProvider.getLineWriter(fileStructure, "Shift_JIS");
		LineProvider lineProvider = ioProvider.getLineProvider(layout);
		ByteArrayOutputStream os = new ByteArrayOutputStream(4000);
		
		writer.open(os);
		for (int lineNum = 0; lineNum < expected.length; lineNum++) {
			AbstractLine line = lineProvider.getLine(layout);
			for (int col = 0; col < expected[lineNum].length; col++) {
				line.getFieldValue(0, col).set(expected[lineNum][col]);
			}

			writer.write(line);
		}
		writer.close();
		
		byte[] byteArray = os.toByteArray();
		
		tstRead(byteArray, expected, layout);
	}

	private void tstRead(XSchemas schemaDef, int fileStructure, String fileData, String[][] expected) throws IOException {
		tstRead(Conversion.getBytes(fileData, "Shift_JIS"), expected, schemaDef.toLayout("Shift_JIS", fileStructure));
	}

	/**
	 * @param fileData
	 * @param expected
	 * @param layout
	 * @throws IOException
	 * @throws RecordException
	 */
	private void tstRead(byte[] fileData, String[][] expected,
			LayoutDetail layout) throws IOException, RecordException {
		AbstractLineReader reader = LineIOProvider.getInstance().getLineReader(layout);
		AbstractLine line;
		reader.open(new ByteArrayInputStream(fileData), layout);
		int lineNum = 0;
		
		while ((line = reader.read()) != null) {
			for (int col = 0; col < expected[lineNum].length; col++) {
				assertEquals(lineNum + ", " + col,expected[lineNum][col].trim(), line.getFieldValue(0, col).asString());
			}
			lineNum += 1;
		}
	}
}
