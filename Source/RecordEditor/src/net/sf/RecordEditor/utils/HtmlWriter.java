package net.sf.RecordEditor.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import net.sf.RecordEditor.utils.common.TranslateXmlChars;

public class HtmlWriter extends BaseWriter {
	private boolean newLine = false;
	private BufferedWriter writer;
	
	public HtmlWriter(String fileName, boolean tblBorder) throws IOException {
		writer = new BufferedWriter(
				new FileWriter(fileName), 4096);
		

		writer.write("<html><body>");
        if (tblBorder) {
        	writer.write("<table BORDER=\"1\" CELLSPACING=\"1\"><tr>");
        } else {
        	writer.write("<table><tr>");
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
		writeAField(field);
		writer.write("</th>");
	}

	@Override
	public void writeField(String field) throws IOException {
		if (newLine) {
			writer.write("<tr>");
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
}
