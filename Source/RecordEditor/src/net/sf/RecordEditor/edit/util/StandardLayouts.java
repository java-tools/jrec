package net.sf.RecordEditor.edit.util;

import java.util.List;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.TranslateXmlChars;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.RecordEditorXmlLoader;
import net.sf.JRecord.External.Def.ExternalField;
import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.RecordEditor.utils.common.Common;

public final class StandardLayouts {

	private static final String ERROR_CREATING_LAYOUT = "Internal Error Creating Layout:";

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


	public final AbstractLayoutDetails getXmlLayout() {
		return getLayout(xmlExternalRec);
	}

	public final AbstractLayoutDetails getGenericCsvLayout() {
		return getLayout(genericCsvExternalRec);
	}

	public final AbstractLayoutDetails getCsvLayoutNamesFirstLine(String delim, String charset, String quote, boolean embeddedCr) {
		return  getLayout(
					getCsvExternal("CSV_NAME_1ST_LINE", delim, charset, quote, embeddedCr)
				);
	}


//	public final AbstractLayoutDetails getCsvLayout(List<ExternalField> fields, String delim, String quote, boolean embeddedCr) {
//		return getCsvLayout(fields, "Default", delim, null, quote, embeddedCr);
//	}
//

	public final AbstractLayoutDetails getCsvLayout(List<ExternalField> fields, String fileStructure, String delim, String charset, String quote, boolean embeddedCr) {
		ExternalRecord rec = getCsvExternal(fileStructure, delim, charset, quote, embeddedCr);

		if (rec == null ) return null;

		if (fields != null && fields.size() > 0) {
			rec.clearRecordFields();
	
			for (ExternalField field : fields) {
				rec.addRecordField(field);
			}
		}

		return  getLayout(rec);
	}


	private final ExternalRecord getCsvExternal(String fileStructure, String delim, String charset, String quote, boolean embeddedCr) {
		String xml;
		String embeddedStr = "";
		String font = "";

		if ( "<none>".equals(quote.toLowerCase())) {
			quote = "";
		}
		if (embeddedCr) {
			embeddedStr = " " + Constants.RE_XML_EMBEDDED_CR + "=\"Y\" ";
		}
		if ("\t".equals(delim)) {
			delim = "<tab>";
		}
		
		if (charset != null && ! "".equals(charset)) {
			font = Constants.RE_XML_FONTNAME + "=\""+ charset + "\"";
		}

		xml = "<RECORD RECORDNAME=\"Delimited\" COPYBOOK=\"\" STYLE=\"0\""
			+ "        FILESTRUCTURE=\"" + fileStructure + "\""
			+ "        DELIMITER=\"" + TranslateXmlChars.replaceXmlCharsStr(delim) + "\""
			+ "        QUOTE=\"" + TranslateXmlChars.replaceXmlCharsStr(quote) + "\""
			+          embeddedStr + " " + font
			+ "		   DESCRIPTION=\"Delimited\" RECORDTYPE=\"Delimited\" RecSep=\"default\">"
			+ "	<FIELDS>"
			+ "		<FIELD NAME=\"Dummy\" DESCRIPTION=\" \" POSITION=\"1\" TYPE=\"Char\"/>"
			+ "	</FIELDS>"
			+ "</RECORD>";

		System.out.println("Generated Xml: " + xml);

		return getExternal(xml, "CsvNamesFirstLine");
	}


	public final AbstractLayoutDetails getFixedLayout(List<ExternalField> fields, String charset) {
		ExternalRecord rec;
		String charsetTag = "";
		
		if (charset != null && ! "".equals(charset)) {
			charsetTag = Constants.RE_XML_FONTNAME + "=\""+ charset + "\"";
		}

		
		String xml = "<RECORD RECORDNAME=\"Fixed\" COPYBOOK=\"\" STYLE=\"0\""
			+ "        FILESTRUCTURE=\"RecordLayout\" DELIMITER=\"\" " +  charsetTag
			+ "		   DESCRIPTION=\"Fixed\" RECORDTYPE=\"RecordLayout\" RecSep=\"default\">"
			+ "	<FIELDS>"
			+ "		<FIELD NAME=\"Dummy\" DESCRIPTION=\" \" POSITION=\"1\" TYPE=\"Char\"/>"
			+ "	</FIELDS>"
			+ "</RECORD>";

		System.out.println("Generated Xml: " + xml);

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


	private AbstractLayoutDetails getLayout(ExternalRecord rec) {
		AbstractLayoutDetails ret = null;
		try {
			ret = rec.asLayoutDetail();
		} catch (Exception e) {
			Common.logMsg(AbsSSLogger.ERROR, ERROR_CREATING_LAYOUT, rec.getRecordName(), e);
			e.printStackTrace();
		}

		return ret;
	}

	private static ExternalRecord getExternal(String xml, String name) {
		ExternalRecord ret = null;

		try {
			ret = RecordEditorXmlLoader.getExternalRecord(xml, name);
		} catch (Exception e) {
			Common.logMsg(AbsSSLogger.ERROR, ERROR_CREATING_LAYOUT, name, e);
			Common.logMsg(AbsSSLogger.ERROR, "Problem Xml:", xml, null);
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
