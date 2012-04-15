package net.sf.JRecord.zTest.io;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import junit.framework.TestCase;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.RecordEditorXmlLoader;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.JRecord.Types.Type;

/**
 * This class test checks the Column Attributes in the layout definition get married up with the 
 * Columns defined in the File.
 * 
 * @author Bruce Martin
 *
 */
public class TstCsvFormat  extends TestCase {

	private final static String XML_LAYOUT1 = 
				  "<?xml version=\"1.0\" ?>"
				+ "<RECORD RECORDNAME=\"XslDesignCsv\" COPYBOOK=\"\" DELIMITER=\",\" FILESTRUCTURE=\"CSV_NAME_1ST_LINE\" STYLE=\"0\" RECORDTYPE=\"Delimited\" LIST=\"Y\" QUOTE=\"\" RecSep=\"default\" LINE_NO_FIELD_NAMES=\"1\">"
				+ "	<FIELDS>"
				+ "		<FIELD NAME=\"XSL-Path\" POSITION=\"1\" TYPE=\"CSV array\" PARAMETER=\"\\\\/\\\"/>"
				+ "		<FIELD NAME=\"Xsl Attribute\" POSITION=\"2\" TYPE=\"Char\"/>"
				+ "		<FIELD NAME=\"Xsl Value\" POSITION=\"3\" TYPE=\"Char\"/>"
				+ "		<FIELD NAME=\"Xml-Path__Z\" POSITION=\"4\" TYPE=\"Char\"/>"
				+ "		<FIELD NAME=\"Xml Attribute__Z\" POSITION=\"5\" TYPE=\"Num (Left Justified)\"/>"
				+ "		<FIELD NAME=\"Xml Value\" POSITION=\"6\" TYPE=\"Char\"/>"
				+ "	</FIELDS>"
				+ "</RECORD>";
		
	private final static String XML_LAYOUT2 = 
			  "<?xml version=\"1.0\" ?>"
			+ "<RECORD RECORDNAME=\"XslDesignCsv\" COPYBOOK=\"\" DELIMITER=\",\" FILESTRUCTURE=\"CSV_NAME_1ST_LINE\" STYLE=\"0\" RECORDTYPE=\"Delimited\" LIST=\"Y\" QUOTE=\"\" RecSep=\"default\" LINE_NO_FIELD_NAMES=\"1\">"
			+ "	<FIELDS>"
			+ "		<FIELD NAME=\"XSL-Path\" POSITION=\"1\" TYPE=\"Char\"/>"
			+ "		<FIELD NAME=\"Xsl Attribute\" POSITION=\"2\" TYPE=\"Char\"/>\n"
			+ "		<FIELD NAME=\"Xsl Value\" POSITION=\"3\" TYPE=\"CSV array\" PARAMETER=\"\\\\/\\\"/>"
			+ "		<FIELD NAME=\"Xml-Path__Z\" POSITION=\"4\" TYPE=\"Char\"/>"
			+ "		<FIELD NAME=\"Xml Attribute__Z\" POSITION=\"5\" TYPE=\"Num (Left Justified)\"/>"
			+ "		<FIELD NAME=\"Xml Value\" POSITION=\"6\" TYPE=\"Char\"/>"
			+ "	</FIELDS>"
			+ "</RECORD>";

	private final static String[] LINES = {
		"XSL-Path,XSL Attribute,XSL value,XML-Path,XML Attribute,XML value\n",
		",@Xml~Standalone,false\n",
		"\n",
		"xsl:stylesheet,@version,2.0\n",
		",@exclude-result-prefixes,xsl date XQHeaderFunc\n",
		",@extension-element-prefixes,XQMessageElem saxon\n",
	};
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void testCsvLayout1() throws Exception {
		String[] fieldNames = {"XSL-Path","XSL Attribute","XSL value","XML-Path","XML Attribute","XML value"};
		AbstractLayoutDetails layout = getLayout1();
		AbstractRecordDetail rec;
		AbstractLineReader reader = LineIOProvider.getInstance().getLineReader(Constants.IO_NAME_1ST_LINE);
		reader.open(getInput(), layout);

		while (reader.read() != null);

		layout = reader.getLayout();

		assertEquals("Check Record Count ", 1, layout.getRecordCount());
		assertEquals("Check Field Count", fieldNames.length, layout.getRecord(0).getFieldCount());

		rec = layout.getRecord(0);
		for (int i = 0; i < fieldNames.length; i++) {
			assertEquals("Field Name ", fieldNames[i], rec.getField(i).getName());
			assertEquals("Field Decimal ", 0, rec.getField(i).getDecimal());
		}
		assertEquals("Field Type ", Type.ftCsvArray, rec.getField(0).getType());
		assertEquals("Field Param ", "\\\\/\\", rec.getField(0).getParamater());
		for (int i = 1; i < fieldNames.length; i++) {
			assertEquals("Field Format ", 0, rec.getField(0).getFormat());
			assertEquals("Field Type ", Type.ftChar, rec.getField(i).getType());
			assertEquals("Field Format ", 0, rec.getField(i).getFormat());
			assertEquals("Field Param ", "", rec.getField(i).getParamater());
		}
	}

	
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	public void testCsvLayout2() throws Exception {
		 String[] fieldNames = {"XSL-Path","XSL Attribute","XSL value","XML-Path","XML Attribute","XML value"};
		 AbstractLayoutDetails layout = getLayout2();
		 AbstractRecordDetail rec;
		 AbstractLineReader reader = LineIOProvider.getInstance().getLineReader(Constants.IO_NAME_1ST_LINE);
		 reader.open(getInput(), layout);

		 while (reader.read() != null);

		 layout = reader.getLayout();

		 assertEquals("Check Record Count ", 1, layout.getRecordCount());
		 assertEquals("Check Field Count", fieldNames.length, layout.getRecord(0).getFieldCount());

		 rec = layout.getRecord(0);
		 for (int i = 0; i < fieldNames.length; i++) {
			 assertEquals("Field Name ", fieldNames[i], rec.getField(i).getName());
			 assertEquals("Field Decimal ", 0, rec.getField(i).getDecimal());
		 }
		 assertEquals("Field Type ", Type.ftCsvArray, rec.getField(2).getType());
		 assertEquals("Field Param ", "\\\\/\\", rec.getField(2).getParamater());
		 for (int i = 0; i < fieldNames.length; i++) {
			 if (i != 2) {
				 assertEquals("Field Format ", 0, rec.getField(0).getFormat());
				 assertEquals("Field Type ", Type.ftChar, rec.getField(i).getType());
				 assertEquals("Field Format ", 0, rec.getField(i).getFormat());
				 assertEquals("Field Param ", "", rec.getField(i).getParamater());
			 }
		 }
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

	private LayoutDetail getLayout2() throws RecordException, Exception {		
		return getExternalLayout(XML_LAYOUT2).asLayoutDetail();
	}
	
	private ExternalRecord getExternalLayout(String s) throws Exception {
		return RecordEditorXmlLoader.getExternalRecord(s, "Csv Layout");
	}
}
