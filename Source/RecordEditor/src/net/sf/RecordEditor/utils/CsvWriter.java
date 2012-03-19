package net.sf.RecordEditor.utils;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import net.sf.JRecord.Common.Conversion;
import net.sf.RecordEditor.utils.common.Common;

public class CsvWriter extends BaseWriter {

	private static final byte[] noBytes = {};
	private OutputStream fileWriter;
	private String fieldSep;
	private byte[] fieldSepByte, sep;
	private byte[] eolBytes;
	private String font;
	public final String quote;
	private int lineNo = 0;
	private int fieldNo = 0;
	public final boolean quoteAllTextFlds;
	public CsvWriter(String fileName, String delimiter, 
			String fontName, String quoteStr, boolean quoteAllTextFields,
			boolean[]  includeFields) throws IOException {
		font = fontName;
		quote = quoteStr;
		quoteAllTextFlds = quoteAllTextFields;
		setPrintField(includeFields);
		
		fileWriter = new BufferedOutputStream(
				new FileOutputStream(fileName), 4096);

		
		eolBytes = Conversion.getBytes(System.getProperty("line.separator"), font);
		fieldSepByte = Conversion.getBytes(delimiter, font);
		fieldSep = delimiter;

		if ("<tab>".equalsIgnoreCase(delimiter)) {
			fieldSepByte = Conversion.getBytes("\t", font);
			fieldSep = "\t";
		} else if ("<space>".equalsIgnoreCase(delimiter)) {
			fieldSepByte = Conversion.getBytes(" ", font);
			fieldSep = " ";
		} else if (delimiter != null && delimiter.toLowerCase().startsWith("x'")) {
			try {
				fieldSepByte = new byte[1];
				fieldSepByte[0] = Conversion.getByteFromHexString(delimiter);
				try {
					fieldSep = new String(fieldSepByte);
				} catch (Exception e) {
					fieldSep = "";
				}
			} catch (Exception e) {
				Common.logMsg("Invalid Hex Seperator", null);
				e.printStackTrace();
			}
		}
		
		sep = noBytes;
	}
	
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.FieldWriter#newLine()
	 */
	public void newLine() throws IOException {
		fileWriter.write(eolBytes);
		sep = noBytes;
		lineNo += 1;
		fieldNo = 0;
	}
	
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.FieldWriter#writeFieldHeading(java.lang.String)
	 */
	public void writeFieldHeading(String field) throws IOException {

		if (isFieldToBePrinted(fieldNo)) {
			fileWriter.write(sep);
			try {
				fileWriter.write(Conversion.getBytes(field, font));
			} catch (Exception e) {
			}
			
			sep = fieldSepByte;
		}
		fieldNo += 1;
		lineNo = -1;
	}
	
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.FieldWriter#writeField(java.lang.String)
	 */
	public void writeField(String field) throws IOException {
		writeField(field, isNumeric(fieldNo));
//				   numericFields != null 
//				&& fieldNo >= numberOfInitialFields
//				&& numericFields[fieldNo-numberOfInitialFields]);
	}
	
	public void writeField(String field, boolean isNumeric) throws IOException {

		if (isFieldToBePrinted(fieldNo)) {
	
			fileWriter.write(sep);
				
			if (field != null) {
				if (isNumeric) {
				} else if ("".equals(quote)) {		 
					if (!"".equals(fieldSep) && field.indexOf(fieldSep) >= 0) {
						StringBuffer b = new StringBuffer(field);
						Conversion.replace(b, fieldSep, "");
						Common.logMsg("Warning: on line " + lineNo + " Field " + fieldNo + ", Seperator " + fieldSep + " Dropped" , null);
						field = b.toString();
					}
				} else if (!"".equals(fieldSep) && field.indexOf(fieldSep) >= 0) {
					StringBuffer b = new StringBuffer(field);
		
					int pos;
					int j = 0;
												
					while ((pos = b.indexOf(quote, j)) >= 0) {
						b.insert(pos, quote);
						j = pos + 2;
					}
					
					field = quote + (b.append(quote)).toString();
				} else if (quoteAllTextFlds) {
					field = quote + field + quote;
				}
				fileWriter.write(Conversion.getBytes(field, font));
			}

			sep = fieldSepByte;
			
		}
		fieldNo += 1;
	}
	
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.FieldWriter#close()
	 */
	public void close() throws IOException {
		fileWriter.close();
	}
}
