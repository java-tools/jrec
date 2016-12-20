package zCommon;

import static org.junit.Assert.assertEquals;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.RecordEditor.layoutWizard.ColumnDetails;
import net.sf.RecordEditor.layoutWizard.Details;
import net.sf.RecordEditor.layoutWizard.FieldSearch;
import net.sf.RecordEditor.layoutWizard.FileAnalyser;
import net.sf.RecordEditor.re.util.fw.FixedWidthSelectionPane;

public class ZCommonCode {

	
	public static final String PO_HEADER_FILE = "Ams_PODownload_Header.txt";

	public static final ColumnDetails[] AMS_LOCATION_FIELDS = {
			newColDtls(1, 3, 0),
			newColDtls(4, 4, 6),
			newColDtls(8, 37, 0),
			newColDtls(45, 40, 0),
			newColDtls(85, 40, 0),
			newColDtls(125, 35, 0),
			newColDtls(160, 4, 6),
			newColDtls(164, 6, 0),
			newColDtls(170, 4, 0),
	};
	
	public static final ColumnDetails[] AMS_PO_KEYCODE_FIELDS = {
			newColDtls(1, 1, 0),
			newColDtls(2, 1, 6),
			newColDtls(3, 5, 6),
			newColDtls(8, 15, 6),
			newColDtls(23, 15, 6),
			newColDtls(38, 1, 0),
			newColDtls(39, 8, 6),
			newColDtls(47, 11, 6),
			newColDtls(58, 7, 6),
			newColDtls(65, 7, 0),
			newColDtls(72, 7, 6),
			newColDtls(79, 8, 0),
			newColDtls(87, 8, 6),
			newColDtls(95, 7, 0),
			newColDtls(102, 50, 0),	
	};
	
	public static final ColumnDetails[] AMS_PO_HEADER_FIELDS = {
			newColDtls(1, 1, 0),
			newColDtls(2, 6, 6),
			newColDtls(8, 16, 6),
			newColDtls(24, 6, 0),
			newColDtls(30, 6, 6),
			newColDtls(36, 8, 0),
			newColDtls(44, 2, 6),
			newColDtls(46, 2, 0),
			newColDtls(48, 3, 6),
			newColDtls(51, 1, 0),
			newColDtls(52, 6, 6),
			newColDtls(58, 2, 6),
			newColDtls(60, 2, 6),
			newColDtls(62, 6, 6),
			newColDtls(68, 8, 0),
			newColDtls(76, 13, 0),
	};
	
	public static final ColumnDetails[] UPDATED_AMS_PO_HEADER_FIELDS = {
			newColDtls("Record-Type", 1, 2, 0),
			newColDtls("Sequence-Number", 3, 5, 2, 6),
			newColDtls("Vendor", 8, 9, 6),
			newColDtls("PO", 17, 13, 6),
			newColDtls("Entry-Date", 30, 6, 6),
			newColDtls("", 36, 8, 0),
			newColDtls("Code", 44, 2, 6),
			newColDtls("", 46, 2, 0),
			newColDtls("Department", 48, 4, 6),
			newColDtls("Reciept-Date", 52, 6, 6),
			newColDtls("", 58, 4, 6),
			newColDtls("Cancel-by-date", 62, 6, 6),
			newColDtls("EDI-Flag", 68, 1, 0),
			newColDtls("", 69, 7, 0),
			newColDtls("Department-Name", 76, 13, 0),

	};

	
	public static String[] FW_FILES = {
			"Ams_LocDownload_20041228.txt",
			"Ams_PODownload_Keycode.txt",
			PO_HEADER_FILE,			
			"Ebcdic_Ams_LocDownload_20041228_Extract2.txt",
	};
	
	public static ColumnDetails[][] FILE_COLUMNS = {
			ZCommonCode.AMS_LOCATION_FIELDS,
			ZCommonCode.AMS_PO_KEYCODE_FIELDS,
			ZCommonCode.AMS_PO_HEADER_FIELDS,
			ZCommonCode.AMS_LOCATION_FIELDS,
	};
	
	public static final String[] CHARCTER_SETS = {"", "", "", "cp037"};
	
	/**
	 * Check FixedWidthSelectionPane selection pane
	 * @param pane
	 * @param fldIdx
	 */
	public static void checkPane(FixedWidthSelectionPane pane, ColumnDetails[] expectedColumns, String charset) {
		assertEquals(charset, pane.getFontName());
		
		LayoutDetail l = pane.getLayout(charset, null);
		for (int j = 0; j < expectedColumns.length; j++) {
			IFieldDetail fld = l.getField(0, j);
			assertEquals(expectedColumns[j].getStart(),fld.getPos());
			assertEquals(expectedColumns[j].getLength(),fld.getLen());
			assertEquals(expectedColumns[j].getType(), fld.getType());
			assertEquals(0, fld.getDecimal());
		}
	}
	
	public static void chkFieldSearch(ColumnDetails[] expected, FileAnalyser fileAnalyser) {
		chkSearchDetails(expected, fileAnalyser.getFileDetails(false, false));
	}

	public static void chkSearchDetails(ColumnDetails[] expected, Details details) {
		int maxLineLength = details.standardRecord.getMaxRecordLength();
		
		FieldSearch fieldSearch = new FieldSearch(details, details.standardRecord);
		fieldSearch.findFields(true, false, true, true, false);
		
		ArrayList<ColumnDetails> columnDtls = details.standardRecord.columnDtls;

		if (expected == null) {
			for (ColumnDetails c : columnDtls) {
				//System.out.println(c.getStart() + "\t" + c.getLength() + "\t" + c.getType());
				System.out.println("\t\tnewColDtls(" 
							+ c.getStart() 
							+ ", " + c.getLength()
							+ ", " + c.getType() + "),");
			}
		} else {
			compareColumnDtlsArray(expected, columnDtls, maxLineLength);
		}
	}
	
	public static void compareColumnDtlsArray(ColumnDetails[] expected, ArrayList<ColumnDetails> columnDtls, int maxLineLength) {
		ColumnDetails c, e;
		for (int i = 0; i < expected.length; i++) {
			c = columnDtls.get(i);
			e = expected[i];
			String id = i + "";
			assertEquals(id, e.getStart(), c.getStart());
			assertEquals(id, e.getLength(), c.getLength());
			assertEquals(id, e.getType(), c.getType());
		}
		assertEquals(expected.length, columnDtls.size());
		e = expected[expected.length - 1];
		assertEquals(e.getStart() + e.getLength() - 1, maxLineLength);
	}




	public static byte[] readDataFile(String name) {
		return loadFile(getFileName(name));
	}

	public static String getFileName(String name) {
		return ZCommonCode.class.getResource( "files/" + name).getFile();
	}
	
	public static byte[] loadFile(String filename) {
		File f = new File(filename);
		byte[] b = new byte[(int)f.length()];
		try {
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));
			int n = in.read(b);
			int num = n;
			
			while (n > 0) {
				n = in.read(b, num, b.length - num);
				num += n;
			}
			in.close();
			return b;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new byte[0];
	}

	public static ColumnDetails newColDtls(int colStart, int length, int type) {
		ColumnDetails c = new ColumnDetails(colStart, type);
		c.setValue(ColumnDetails.LENGTH_IDX, length);
		
		return c;
	}

	public static ColumnDetails newColDtls(String name, int colStart, int length, int decimal, int type) {
		ColumnDetails c = newColDtls(name, colStart, length, type);
		c.decimal = decimal;
		return c;
	}

	public static ColumnDetails newColDtls(String name, int colStart, int length, int type) {
		ColumnDetails c = new ColumnDetails(colStart, type);
		c.setValue(ColumnDetails.NAME_IDX, name);
		c.setValue(ColumnDetails.LENGTH_IDX, length);
		
		return c;
	}

}
