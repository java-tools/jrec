package net.sf.RecordEditor.re.fileWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import net.sf.RecordEditor.utils.common.TranslateXmlChars;

public class HtmlWriter extends BaseWriter {

	private boolean newLine = false;
	private BufferedWriter writer;

	private int lineNo = 0;

	private final HtmlColors colors;


	public HtmlWriter(String fileName, boolean tblBorder, HtmlColors colors, String tittle) throws IOException {

		this.colors = colors;


		writer = new BufferedWriter(
				new FileWriter(fileName), 4096);

		String bg = bgColor(colors.headingBackground);
		String borderColor = "";
		if (colors.borderColor != null) {
			borderColor = " bordercolor=\"" + colors.borderColor + "\"";
		}

		//bgcolor=""
		writer.write("<html><body" + bgColor(colors.backgroundColor) + ">");
		writer.write(
				  "<table align=\"CENTER\" border=\"1\" width=\"60%\"><tbody><tr><td><center>"
				+ "<font size=\"12pt\">" + tittle +"</font></center></td></tr>"
				+ "</tbody></table>"
				+ "<p>&nbsp;</p><p>&nbsp;</p>");

        if (tblBorder) {
        	writer.write("<table BORDER=\"1\" CELLSPACING=\"1\"" + borderColor + "><tr" + bg + ">");
        } else {
        	writer.write("<table><tr" + bg + ">");
        }
	}


	@Override
	public void newLine() throws IOException {
		writer.write("</tr>");
		writer.newLine();

		newLine = true;
	}

	@Override
	public void writeFieldHeading(String field) throws IOException {
		writer.write("<th>");
		if (colors.headingColor == null) {
			writer.write("<b>");
			writeAField(field);
			writer.write("</b>");
		} else {
			writer.write("<font color=\"" + colors.headingColor + "\"><b>");
			writeAField(field);
			writer.write("</b></font>");
		}
		writer.write("</th>");
	}

	@Override
	public void writeField(String field) throws IOException {
		if (newLine) {
			String bg = "";
			lineNo += 1;

			if (lineNo % 2 == 0) {
				bg = bgColor(colors.evenBackground);
			} else {
				bg = bgColor(colors.oddBackground);
			}
			writer.write("<tr" + bg + ">");
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
		newLine = false;
	}

	@Override
	public void close() throws IOException {

		if (! newLine) {
			newLine();
		}
	    writer.write("</table></body></html>");
	    writer.close();
	}

	private String bgColor(String color) {
		String ret = "";
		if (color != null) {
			ret = " bgcolor=\"" + color + "\"";
		}

		return ret;
	}



	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.fileWriter.FieldWriter#printAllFields()
	 */
	@Override
	public boolean printAllFields() {
		return true;
	}

}
