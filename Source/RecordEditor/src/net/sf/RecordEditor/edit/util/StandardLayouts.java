package net.sf.RecordEditor.edit.util;

import java.util.List;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.External.ExternalField;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.RecordEditorXmlLoader;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.TranslateXmlChars;

public final class StandardLayouts {

	private static StandardLayouts instance = new StandardLayouts();

	private static final ExternalRecord xmlExternalRec = getExternal(
			"<RECORD RECORDNAME=\"XML - Build Layout\" COPYBOOK=\"\" DELIMITER=\"|\" "
			+   " DESCRIPTION=\"XML file, build the layout based on the files contents\""
			+   " FILESTRUCTURE=\"XML_Build_Layout\" STYLE=\"0\" RECORDTYPE=\"XML\" LIST=\"Y\" "
			+   " QUOTE=\"'\" RecSep=\"default\" LINE_NO_FIELD_NAMES=\"1\">"
			+   "  <TSTFIELDS></TSTFIELDS>"
			+     "<FIELDS><FIELD NAME=\"Dummy\" "
			+          " DESCRIPTION=\"1 field is Required for the layout to load\" "
			+          " POSITION=\"1\" TYPE=\"Char\"/>"
			+   "</FIELDS>"
			+"</RECORD>",
			"Build Xml"
			);
	
	private static final ExternalRecord genericCsvExternalRec = getExternal(
			"<RECORD RECORDNAME=\"Generic CSV - enter details\" COPYBOOK=\"\" "
			+     "DELIMITER=\"|\" DESCRIPTION=\"Generic CSV - user supplies details\" " 
			+     "FILESTRUCTURE=\"CSV_GENERIC\" STYLE=\"0\" RECORDTYPE=\"Delimited\" LIST=\"Y\" "
			+     "QUOTE=\"\" RecSep=\"default\">"
			+        "<FIELDS><FIELD NAME=\"Field 1\" POSITION=\"1\" TYPE=\"Char\"/></FIELDS>"
			+ "</RECORD>",
			"Generic Csv"
			);
	
	
	@SuppressWarnings("rawtypes")
	public final AbstractLayoutDetails getXmlLayout() {
		return getLayout(xmlExternalRec);
	}
	
	@SuppressWarnings("rawtypes")
	public final AbstractLayoutDetails getGenericCsvLayout() {
		return getLayout(genericCsvExternalRec);
	}
	
	@SuppressWarnings("rawtypes")
	public final AbstractLayoutDetails getCsvLayoutNamesFirstLine(String delim, String quote) {
		return  getLayout(
					getCsvExternal("CSV_NAME_1ST_LINE", delim, quote)
				);
	}
	

	@SuppressWarnings("rawtypes")
	public final AbstractLayoutDetails getCsvLayout(List<ExternalField> fields, String delim, String quote) {
		ExternalRecord rec = getCsvExternal("Default", delim, quote);
		
		if (rec == null ) return null;
		
		rec.clearRecordFields();
		
		for (ExternalField field : fields) {
			rec.addRecordField(field);
		}

		return  getLayout(rec);
	}
	
	private final ExternalRecord getCsvExternal(String fileStructure, String delim, String quote) {
		String xml;
		
		if ( "<none>".equals(quote.toLowerCase())) {
			quote = "";
		}

		xml = "<RECORD RECORDNAME=\"Delimited\" COPYBOOK=\"\" STYLE=\"0\""
			+ "        FILESTRUCTURE=\"" + fileStructure + "\""
			+ "        DELIMITER=\"" + TranslateXmlChars.replaceXmlCharsStr(delim) + "\""
			+ "        QUOTE=\"" + TranslateXmlChars.replaceXmlCharsStr(quote) + "\""
			+ "		   DESCRIPTION=\"Delimited\" RECORDTYPE=\"Delimited\" RecSep=\"default\">"
			+ "	<FIELDS>"
			+ "		<FIELD NAME=\"Dummy\" DESCRIPTION=\" \" POSITION=\"1\" TYPE=\"Char\"/>"
			+ "	</FIELDS>"
			+ "</RECORD>";
		
		System.out.println("Generated Xml: " +xml);
		
		return getExternal(xml, "CsvNamesFirstLine");
	}
	

	@SuppressWarnings("rawtypes")
	public final AbstractLayoutDetails getFixedLayout(List<ExternalField> fields, String delim, String quote) {
		ExternalRecord rec; 
		String xml = "<RECORD RECORDNAME=\"Fixed\" COPYBOOK=\"\" STYLE=\"0\""
			+ "        FILESTRUCTURE=\"RecordLayout\" DELIMITER=\"\""
			+ "		   DESCRIPTION=\"Fixed\" RECORDTYPE=\"Delimited\" RecSep=\"default\">"
			+ "	<FIELDS>"
			+ "		<FIELD NAME=\"Dummy\" DESCRIPTION=\" \" POSITION=\"1\" TYPE=\"Char\"/>"
			+ "	</FIELDS>"
			+ "</RECORD>";
		
		System.out.println("Generated Xml: " +xml);
		
		rec =  getExternal(xml, "FixedName");
		if (rec == null ) {
			return null;
		}
		
		rec.clearRecordFields();
		
		for (ExternalField field : fields) {
			rec.addRecordField(field);
		}

		return  getLayout(rec);
	}

	
	@SuppressWarnings("rawtypes")
	private AbstractLayoutDetails getLayout(ExternalRecord rec) {
		AbstractLayoutDetails ret = null;
		try {
			ret = rec.asLayoutDetail();
		} catch (Exception e) {
			Common.logMsg("Internal Error Creating Layout: " + rec.getRecordName(), e);
			e.printStackTrace();
		}
		
		return ret;
	}
	
	private static ExternalRecord getExternal(String xml, String name) {
		ExternalRecord ret = null;
		
		try {
			ret = RecordEditorXmlLoader.getExternalRecord(xml, name);
		} catch (Exception e) {
			Common.logMsg("Internal Error Creating Layout: " + name, e);
			Common.logMsg("Problem Xml: " + xml, null);
			System.out.println("Problem Xml: " + xml);
			e.printStackTrace();
		}
		
		return ret;
	}
	
	/**
	 * @return the instance
	 */
	public static StandardLayouts getInstance() {
		return instance;
	}
	
	
	
}
