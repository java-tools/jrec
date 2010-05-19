package net.sf.RecordEditor.diff;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import net.sf.JRecord.Common.AbstractRecord;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.RecordEditor.jibx.compare.DiffDefinition;
import net.sf.RecordEditor.utils.common.Common;

public class WriteHtml {
	
	private final static String HTML_START="<Body><h1>Formated Compare</h1>";
	private final static String HTML_SINGLE_TBL_START="<p>&nbsp;<p><table border=\"1\" cellpadding=\"2\">"
				+ "<tr><td><b>What</b></td><td><b>Line</b></td>";
	private final static String HTML_MANY_TBL_START="<p>&nbsp;<p>&nbsp;<table border=\"1\" cellpadding=\"2\">"
				+ "<tr><td><b>Field Name</b></td><td><b>Pos</b></td><td><b>Length</b></td>"
				+ "<td><b>Old Value</b></td><td><b>New Value</b></td></tr>";
	private final static String HTML_LINE_NUMBER = "<TR><td>Line Number</td><td>&nbsp;</td><td>&nbsp;</td>";
//	private final static String NEW_COLUMN = "</td>";
	private final static String HTML_END="</td></tr></table></body>";
	
	private static WriteHtml instance = new WriteHtml();
	

	public final void writeSingleTbl(DiffDefinition diff,
			AbstractLayoutDetails recordLayout, 
			ArrayList<LineCompare> before, ArrayList<LineCompare> after)
	throws IOException {
		int i, j, pref;
		String f1, f2;
		BufferedWriter writer = new BufferedWriter(new FileWriter(diff.htmlFile));
		AbstractRecordDetail rec;
		LineCompare old, newL;
		
		htmlStart(writer, diff);
		writer.write(HTML_SINGLE_TBL_START); 		writer.newLine();
		
		rec = recordLayout.getRecord(0);
		for (i = 0; i < rec.getFieldCount(); i++) {
			writer.write("<td><b>" + rec.getField(i).getName() + "</b></td>");
		}
		writer.write("</TR>");		writer.newLine();
		
		for (i = 0; i < before.size(); i++) {
			old = before.get(i);
			newL = after.get(i);
			writeLineStart(writer, "old", old);
			if (old == null) {
				pref = newL.line.getPreferredLayoutIdx();
				writeBlankFields(writer, pref, recordLayout);
			} else {
				pref = old.line.getPreferredLayoutIdx();
				if (old.code == LineCompare.DELETED || newL == null || newL.line == null) {
					writeStandardFields(writer, "cyan", pref, recordLayout, old);
				} else {
					for (j = 0; j < recordLayout.getRecord(pref).getFieldCount(); j++) {
						f1 = old.line.getFieldValue(pref, j).asString();
						f2 = newL.line.getFieldValue(pref, j).asString();
						
						if (f1.equals(f2)) {
							writer.write("<TD>" + f1 + "</td>");
						} else {
							writer.write("<TD bgcolor=lime><b>" + f1 + "</b></td>");
						}
					}
				}
			}
			
			writeLineStart(writer, "new", newL);
			if (newL == null) {
				writeBlankFields(writer, pref, recordLayout);
			} else {
				if (newL.code == LineCompare.INSERT || old == null || old.line == null) {
					writeStandardFields(writer, "yellow", pref, recordLayout, newL);
				} else {
					if (recordLayout.isMapPresent()) {
						f1 = old.line.getFieldValue(pref, Constants.KEY_INDEX).asString();
						f2 = newL.line.getFieldValue(pref, Constants.KEY_INDEX).asString();
						writeField(writer, f1, f2);
					}
					for (j = 0; j < recordLayout.getRecord(pref).getFieldCount(); j++) {
						f1 = old.line.getFieldValue(pref, j).asString();
						f2 = newL.line.getFieldValue(pref, j).asString();
						writeField(writer, f1, f2);
					}
				}
				
			}
		}
		writer.write(HTML_END);
		writer.close();
	}
	
	private void writeField(BufferedWriter writer, String f1, String f2) throws IOException {
		
		if (f1.equals(f2)) {
			writer.write("<TD>&nbsp;</td>");
		} else {
			writer.write("<TD bgcolor=lime><b>" + f2 + "</b></td>");
		}

	}
	
	private void writeLineStart(BufferedWriter writer, String what, LineCompare cmp)
	throws IOException {
		
		writer.write("<tr><td>" + what + "</td><td>");
		if (cmp != null) {
			writer.write(cmp.lineNo + "</td>" );
		} else {
			writer.write("&nbsp;</td>");
		}
		writer.newLine();
	}
	
	private void writeStandardFields(BufferedWriter writer, String color, int pref,
			AbstractLayoutDetails recordLayout, LineCompare cmp)
	throws IOException {
		
		String start    = "<TD>";
		String endField = "</td>";
		
		if (! "".equals(color)) {
			start    = "<TD bgcolor=\"" + color + "\"><b>";
			endField = "</b></td>";
			
		}
		
		for (int j = 0; j < recordLayout.getRecord(pref).getFieldCount(); j++) {
			writer.write(start + cmp.line.getField(pref, j) + endField);
		}
		
		writer.write("</tr>"); writer.newLine();
	}

	
	private void writeBlankFields(BufferedWriter writer, int pref,
			AbstractLayoutDetails recordLayout)
	throws IOException {
		if (recordLayout.isMapPresent()) {
			writer.write("<td>&nbsp;</td>");
		}

		for (int j = 0; j < recordLayout.getRecord(pref).getFieldCount(); j++) {
			writer.write("<td>&nbsp;</td>");
		}
	}

	/**
	 * Write Compare result as HTML with each row written as a individual table. 
	 * All fields are to be displayed
	 * 
	 * @param filename output file name
	 * @param recordLayout record layout
	 * @param before before file details 
	 * @param after  after  file details
	 * @throws IOException any IO Errors
	 */
	public final void writeTblChgFields(DiffDefinition diff,
			AbstractLayoutDetails recordLayout, 
			ArrayList<LineCompare> before, ArrayList<LineCompare> after)
	throws IOException {
		int i, pref;
		BufferedWriter writer = new BufferedWriter(new FileWriter(diff.htmlFile));
		LineCompare l1, l2;
		FieldDetail fld;
		
		System.out.println("--> " + diff.htmlFile);
		htmlStart(writer, diff);
		for (int j = 0; j < before.size(); j++) {
			l1 = before.get(j); 
			l2 = after.get(j);
			
			if (l1 == null || l2 == null) {
				writeOneRow(writer, recordLayout, l1, l2);
			} else {
				pref = l1.line.getPreferredLayoutIdx();
				writer.write(HTML_MANY_TBL_START);      writer.newLine();
				writer.write(HTML_LINE_NUMBER 
						+ "<td>" + l1.lineNo + "</td><td>" + l2.lineNo + "</td></tr>");	
				writer.newLine();
				for (i = 0; i < recordLayout.getRecord(pref).getFieldCount(); i++ ) {
					if (! l1.line.getField(pref, i).equals( l2.line.getField(pref, i))) {
						fld = recordLayout.getRecord(pref).getField(i);
						writeOneFieldHeader(writer, fld, i);
						writer.write("<td bgcolor=lime>" + fix(l1.line.getField(pref, i)) 
								+ "</td><td bgcolor=lime>"
								+ fix(l2.line.getField(pref, i)) + "</td></tr>");
						writer.newLine();
					}
				}
				writer.write("</table>");               writer.newLine();
			}
		}
		
		writer.write(HTML_END);							writer.newLine();
		writer.close();
	}


	/**
	 * Write Compare result as HTML with each row written as a individual table. 
	 * All fields are to be displayed
	 * 
	 * @param filename output file name
	 * @param recordLayout record layout
	 * @param before before file details 
	 * @param after  after  file details
	 * @throws IOException any IO Errors
	 */
	public final void writeTblAllFields(DiffDefinition diff,
			AbstractLayoutDetails recordLayout, 
			ArrayList<LineCompare> before, ArrayList<LineCompare> after)
	throws IOException {
		int i;
		BufferedWriter writer = new BufferedWriter(new FileWriter(diff.htmlFile));
		
		htmlStart(writer, diff);
		for (i = 0; i < before.size(); i++) {
			writeOneRow(writer, recordLayout, before.get(i), after.get(i));
		}
		
		writer.write(HTML_END);
		writer.close();
	}

	
	private void writeOneRow(BufferedWriter writer, AbstractLayoutDetails recordLayout, 
			LineCompare l1, LineCompare l2) 
	throws IOException {
		int pref, i;
		FieldDetail fld;
		
		writer.write(HTML_MANY_TBL_START);      writer.newLine();
		writer.write(HTML_LINE_NUMBER); 
		if (l1 == null) {
			pref = l2.line.getPreferredLayoutIdx();
			writer.write("<td>&nbsp;</td><td>" + l2.lineNo + "</td></tr>");				
			writer.newLine();
			if (recordLayout.isMapPresent()) {
				writeKeyFieldHeader(writer);
				writeNewFieldNew(writer, 
						l2.line.getField(pref, Common.KEY_INDEX));
			}
			for (i = 0; i < recordLayout.getRecord(pref).getFieldCount(); i++ ) {
				fld = recordLayout.getRecord(pref).getField(i);
				writeOneFieldHeader(writer, fld, i);
				writeNewFieldNew(writer, l2.line.getField(pref, i));
			}
		} else if (l2 == null) {
			pref = l1.line.getPreferredLayoutIdx();
			writer.write("<td>" + l1.lineNo + "</td><td>&nbsp;</td></tr>");				
			writer.newLine();
			if (recordLayout.isMapPresent()) {
				writeKeyFieldHeader(writer);
				writeNewFieldOld(writer, 
						l1.line.getField(pref, Common.KEY_INDEX));
			}
			for (i = 0; i < recordLayout.getRecord(pref).getFieldCount(); i++ ) {
				fld = recordLayout.getRecord(pref).getField(i);
				writeOneFieldHeader(writer, fld, i);
				writeNewFieldOld(writer, l1.line.getField(pref, i));
			}
		} else {
			pref = l1.line.getPreferredLayoutIdx();
			if (recordLayout.isMapPresent()) {
				writeKeyFieldHeader(writer);
				writeNewFieldBoth(writer, 
						l1.line.getField(pref, Common.KEY_INDEX),
						l2.line.getField(pref, Common.KEY_INDEX));
			}
			writer.write("<td>" + l1.lineNo + "</td><td>" + l2.lineNo + "</td></tr>");	
			writer.newLine();
			for (i = 0; i < recordLayout.getRecord(pref).getFieldCount(); i++ ) {
				fld = recordLayout.getRecord(pref).getField(i);
				writeOneFieldHeader(writer, fld, i);

				writeNewFieldBoth(writer,
						l1.line.getField(pref, i), l2.line.getField(pref, i));
			}
		}
		writer.write("</table>");               writer.newLine();
	}

	private void writeNewFieldNew(BufferedWriter writer, Object value)
	throws IOException {
		
		writer.write("<td>&nbsp;</td><td bgcolor=yellow>" 
				+ fix(value) + "</td></tr>");
		writer.newLine();

	}

	private void writeNewFieldOld(BufferedWriter writer,  Object value)
	throws IOException {
		
		writer.write("<td bgcolor=aqua>" + fix(value) + "</td><td>&nbsp;</td></tr>");
		writer.newLine();

	}

	private void writeNewFieldBoth(BufferedWriter writer,  
			Object value1, Object value2)
	throws IOException {
		
		if ( value1.equals( value1)) {
			writer.write("<td>" + fix(value1) + "</td><td>&nbsp;</td></tr>");
		} else {
			writer.write("<td bgcolor=lime>" + fix(value1) 
					+ "</td><td bgcolor=lime>" + fix(value2) + "</td></tr>");
		}
		writer.newLine();

	}
	
	private void htmlStart(BufferedWriter writer, DiffDefinition diff) 
	throws IOException {
		
		writer.write(HTML_START);            		writer.newLine();
		writer.write("<p> <b>Old File: </b>" + diff.oldFile.name); 			writer.newLine();
		writer.write("<p> <b>New File: </b>" + diff.newFile.name + "<p>"); 	writer.newLine();
	}

	/**
	 * Write one field header
	 * 
	 * @param writer writer (where HTML is to be written)
	 * @param fld field definition
	 * @param fieldId field id (number)
	 * 
	 * @throws IOException any error that occurs
	 */
	private void writeOneFieldHeader(BufferedWriter writer, FieldDetail fld, int fieldId)  
	throws IOException {
		String len = "&nbsp;";
		
		if (fld.getLen() >= 0) {
			len = Integer.toString(fld.getLen());
		}

		writer.write("<TR><td>" + fld.getName()
				   + "</td><td>" + fld.getPos() + "</td><td>" + len + "</td>"); 
	}

	private void writeKeyFieldHeader(BufferedWriter writer)  
	throws IOException {

		writer.write("<TR><td>Key Field</td><td>&nbsp;</td><td>&nbsp;</td>"); 
	}
	
	private String fix(Object o) {
		if (o == null || "".equals(o.toString().trim())) {
			return "&nbsp;";
		}
		return o.toString();
	}
	
	/**
	 * @return the instance
	 */
	public static final WriteHtml getInstance() {
		return instance;
	}
}
