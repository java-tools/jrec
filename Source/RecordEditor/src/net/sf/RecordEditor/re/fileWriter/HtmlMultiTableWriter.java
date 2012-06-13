package net.sf.RecordEditor.re.fileWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.External.ExternalConversion;
import net.sf.RecordEditor.utils.common.TranslateXmlChars;

public class HtmlMultiTableWriter extends BaseWriter {

	private boolean newRow = false;
	private BufferedWriter writer;

	private int lineNo  = 0;
	private int fieldNo = 1;

	private String recordId = null;
	private final boolean tblBorder, showTxt,  showHex;
	private final HtmlColors colors;


	public HtmlMultiTableWriter(
			String fileName, boolean tblBorder, boolean showTxt, boolean showHex, HtmlColors colors, String tittle) throws IOException {

		this.tblBorder = tblBorder;
		this.showTxt = showTxt;
		this.showHex = showHex;
		this.colors = colors;


		writer = new BufferedWriter(
				new FileWriter(fileName), 4096);


		//bgcolor=""
		writer.write("<html><body" + bgColor(colors.backgroundColor) + ">");
		writer.write("</head><body size=\"1\" bgcolor=\"#E6E7FF\">"
				+ "<table align=\"CENTER\" border=\"1\" width=\"60%\"><tbody><tr><td><center>"
				+ "<font size=\"12pt\">" + tittle +"</font></center></td></tr>"
				+ "</tbody></table>"
				+ "<p>&nbsp;</p><p>&nbsp;</p>");

 	}


	@Override
	public void newLine() throws IOException {
		writer.write("</table>");
		writer.newLine();

		lineNo += 1;
		newRow = true;
	}

	public void rowHeading() throws IOException {
		String bg = bgColor(colors.headingBackground);
		String borderColor = "";
		String recId = recordId;
		if (colors.borderColor != null) {
			borderColor = " bordercolor=\"" + colors.borderColor + "\"";
		}
		if (recId == null) {
			recId = Integer.toString(lineNo);
		}

		writer.write("<p>&nbsp;</p><p><b>Record: " + recId  + "</b></p>");
        if (tblBorder) {
        	writer.write("<table BORDER=\"1\" CELLSPACING=\"1\"" + borderColor + "><tr" + bg + ">");
        } else {
        	writer.write("<table><tr" + bg + ">");
        }

		writer.write(
				  fmtHeading("Field Name")
				+ fmtHeading("Position")
				+ fmtHeading("Length")
				+ fmtHeading("Type")
				+ fmtHeading("Field Value"));

		if (showTxt) {
			writer.write( fmtHeading("Text Value"));
		}
		if (showHex) {
			writer.write( fmtHeading("Hex Value"));
		}
		writer.write("</tr>");
		writer.newLine();
		newRow = false;
		fieldNo = 1;
	}

	private String fmtHeading(String fldName) {
		String s;
		if (colors.headingColor == null) {
			s = "<th><b>" + fldName +"</b></th>";
		} else {
			s = "<th><font color=\"" + colors.headingColor + "\"><b>" + fldName +"</b></th>";
		}
		return s;
	}


	@Override
	public void writeFieldHeading(String field) throws IOException {

	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.fileWriter.BaseWriter#writeFieldDetails(net.sf.JRecord.Common.FieldDetail, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void writeFieldDetails(FieldDetail fldDetail, String fieldValue,
			String textValue, String HexValue) throws IOException {
		if (newRow) {
			rowHeading();
		}

		String bg;
		if (fieldNo++ % 2 == 0) {
			bg = bgColor(colors.evenBackground);
		} else {
			bg = bgColor(colors.oddBackground);
		}
		writer.write("<tr" + bg + ">");


		writeOneField(fldDetail.getName());
		writeOneField(Integer.toString(fldDetail.getPos()));
		if (fldDetail.getLen() >= 0) {
			writeOneField(Integer.toString(fldDetail.getLen()));
		} else {
			writeOneField(null);
		}

		writer.write("<td>");
		writeAField(ExternalConversion.getTypeAsString(0, fldDetail.getType()));
		writer.write(" &nbsp;</td>");

		writeOneField(fieldValue);
		if (showTxt) {
			writeOneField(textValue);
		}
		if (showHex) {
			writeOneField(HexValue);
		}
		writer.write("</tr>");
		writer.newLine();
	}



	public void writeOneField(String field) throws IOException {
		writer.write("<td>");
		writeAField(field);
		writer.write("</td>");
	}

	@Override
	public void writeField(String field) throws IOException {
		if (newRow) {
			rowHeading();
		}
		writer.write("<td>");
		writeAField(field);
		writer.write("</td>");
	}

	protected final void writeAField(String field) throws IOException {
		if (field == null || "".equals(field.trim())) {
			writer.write("&nbsp;");
		} else {
			writer.write(TranslateXmlChars.replaceXmlCharsStr(field));
		}
		newRow = false;
	}




	/**
	 * @see net.sf.RecordEditor.re.fileWriter.BaseWriter#startLevel(java.lang.String)
	 */
	@Override
	public void startLevel(boolean indent, String id) {
		try {
			String indentStr = "";
			if (indent) {
				indentStr = "&nbsp;&nbsp;&nbsp;&nbsp;";
			}
			recordId = id;
			writer.write(
					"<table><tr><td>" + indentStr + "</td><td><p>&nbsp;</p>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.fileWriter.BaseWriter#endLevel()
	 */
	@Override
	public void endLevel() {
		try {
			writer.write("</td></tr></table>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void close() throws IOException {

		if (! newRow) {
			newLine();
		}
	    writer.write("</body></html>");
	    writer.close();
	}

	private String bgColor(String color) {
		String ret = "";
		if (color != null) {
			ret = " bgcolor=\"" + color + "\"";
		}

		return ret;
	}
}
