package net.sf.RecordEditor.test.jrecord;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;

import junit.framework.TestCase;

import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.Line;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.RecordEditorXmlLoader;
import net.sf.RecordEditor.re.jrecord.types.ReTypeManger;


/**
 * This class test checks the Column Attributes in the layout definition get married up with the 
 * Columns defined in the File.
 * 
 * @author Bruce Martin
 *
 */
public class TstCsvDate  extends TestCase {

	private final static String XML_LAYOUT1 = 
			  "<?xml version=\"1.0\" ?>"
			+ "<RECORD RECORDNAME=\"zzCsv1\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" FILESTRUCTURE=\"Default\" STYLE=\"0\" RECORDTYPE=\"Delimited\" LIST=\"Y\" QUOTE=\"\" RecSep=\"default\" LINE_NO_FIELD_NAMES=\"1\">"
			+ "	<FIELDS>"
			+ "		<FIELD NAME=\"ddmmyy\" POSITION=\"1\" TYPE=\"Date - DDMMYY\"/>"
			+ "		<FIELD NAME=\"yymmdd\" POSITION=\"2\" TYPE=\"Date - YYMMDD\"/>"
			+ "		<FIELD NAME=\"ddmmyyyy\" POSITION=\"3\" TYPE=\"Date - DDMMYYYY\"/>"
			+ "		<FIELD NAME=\"dd-MMM-yyyy\" POSITION=\"4\" TYPE=\"Date - Format in Parameter field\" PARAMETER=\"dd-MMM-yyyy\"/>"
			+ "	</FIELDS>"
			+ "</RECORD>";
		

	private final static String[] LINES = {
		
	};
	
	static {
		ReTypeManger.getInstance();
	}
	

	@SuppressWarnings("deprecation")
	public void testCsvLayout1() throws Exception {
		String expected = "210812	120921	21102012	21-Nov-2012         ";
		Date date1 = new Date(112,  7, 21);
		Date date2 = new Date(112,  8, 21);
		Date date3 = new Date(112,  9, 21);
		Date date4 = new Date(112, 10, 21);
		LayoutDetail layout = getLayout1();
		Line line = new Line(layout);
		
		line.setField(0, 0, date1);
		line.setField(0, 1, date2);
		line.setField(0, 2, date3);
		line.setField(0, 3, date4);
		System.out.print(line.getFullLine());
		

		
		assertEquals("Checking line updated correctly", expected, line.getFullLine());
		
		checkDate(date1, line.getField(0, 0));
		checkDate(date2, line.getField(0, 1));
		checkDate(date3, line.getField(0, 2));
		checkDate(date4, line.getField(0, 3));
	}


	private void checkDate(Date expected, Object actual) {
		assertTrue("Check Class", actual instanceof Date);
		assertEquals("Check Date", expected, (Date) actual);
	}

	private InputStream getInput() {
		StringBuilder b = new StringBuilder();
		
		for (String s : LINES) {
			b.append(s);
		}
		
		return new ByteArrayInputStream(b.toString().getBytes());
	}

	private LayoutDetail getLayout1() throws RecordException, Exception {		
		return getExternalLayout(XML_LAYOUT1).asLayoutDetail();
	}

	private ExternalRecord getExternalLayout(String s) throws Exception {
		return RecordEditorXmlLoader.getExternalRecord(s, "Csv Layout");
	}
}
