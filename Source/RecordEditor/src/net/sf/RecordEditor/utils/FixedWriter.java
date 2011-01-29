package net.sf.RecordEditor.utils;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import net.sf.JRecord.Common.Conversion;
import net.sf.RecordEditor.utils.common.Common;

public class FixedWriter implements FieldWriter {

	private static final byte[] noBytes = {};
	private OutputStream fileWriter;

	private byte[] fieldSepByte, sep;
	private byte[] eolBytes;
	private String font;

	private int lineNo = 0;
	private int fieldNo = 0;

	private boolean[] numericFields = null;
	private final int[] fieldLen;
	private final boolean[] printField;

	public FixedWriter(String fileName, String delimiter, 
			String fontName, int[] fieldLengths, boolean[] includeField) throws IOException {
		font = fontName;
		fieldLen = fieldLengths;
		printField = includeField;

		fileWriter = new BufferedOutputStream(
				new FileOutputStream(fileName), 4096);

		
		eolBytes = Conversion.getBytes(System.getProperty("line.separator"), font);
		fieldSepByte = Conversion.getBytes(delimiter, font);

		if ("<tab>".equalsIgnoreCase(delimiter)) {
			fieldSepByte = Conversion.getBytes("\t", font);
		} else if ("<space>".equalsIgnoreCase(delimiter)) {
			fieldSepByte = Conversion.getBytes(" ", font);
		} else if (delimiter != null && delimiter.toLowerCase().startsWith("x'")) {
			try {
				fieldSepByte = new byte[1];
				fieldSepByte[0] = Conversion.getByteFromHexString(delimiter);
				
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
				fileWriter.write(
						Conversion.getBytes(
								pad(	field, 
										fieldLen[fieldNo], 
										numericFields != null && numericFields[fieldNo]),
								font));
			} catch (Exception e) {
				e.printStackTrace();
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
		writeField(field, numericFields != null && numericFields[fieldNo]);
	}
	
	public void writeField(String field, boolean isNumeric) throws IOException {

		if (isFieldToBePrinted(fieldNo)) {
			fileWriter.write(sep);
			
			fileWriter.write(
					Conversion.getBytes(
							pad(field, fieldLen[fieldNo], isNumeric),
							font)
							);
		
			sep = fieldSepByte;
		}
		fieldNo += 1;
	}
	
	private String pad(String val, int len, boolean rightJustified) {
		char[] bld = new char[len];
		int i;
		int l = Math.min(len, val.length());
		int start = 0;
		if (rightJustified) {
			start = len - l;
		}
		
		for (i = 0; i < bld.length; i++) {
			bld[i] = ' ';
		}
		for (i = 0; i < l; i++, start++) {
			bld[start] = val.charAt(i);
		}
		
		return new String(bld, 0, len);
	}
	
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.FieldWriter#close()
	 */
	public void close() throws IOException {
		fileWriter.close();
	}
	
	
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.FieldWriter#setNumericFields(boolean[])
	 */
	public void setNumericFields(boolean[] numericFields) {
		this.numericFields = numericFields;
	}


	private boolean isFieldToBePrinted(int fldNo) {
		return printField == null 
			|| (fldNo < printField.length && printField[fldNo]);
	}
}
