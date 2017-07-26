package net.sf.RecordEditor.re.fileWriter;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import net.sf.JRecord.Common.CommonBits;
import net.sf.JRecord.Common.Conversion;

public class FixedWriter extends BaseWriter {

	private static final byte[] noBytes = {};
	private OutputStream fileWriter;

	private byte[] fieldSepByte, sep;
	private byte[] eolBytes;
	private String font;

//	private int lineNo = 0;
	private int fieldNo = 0;

	private final int[] fieldLen;
	private  int[] prefixLen = null;
	
	public FixedWriter(OutputStream fileName, String delimiter, 
			String fontName, int[] fieldLengths, boolean[] includeField) throws IOException {
		font = fontName;
		fieldLen = fieldLengths;
		setPrintField(includeField);

		fileWriter = new BufferedOutputStream(fileName, 4096);

		
		eolBytes = CommonBits.getEolBytes(null, "", font);
				//Conversion.getBytes(System.getProperty("line.separator"), font);
		fieldSepByte = Conversion.getCsvDelimBytes(delimiter, fontName, '\t');

//		fieldSepByte = Conversion.getBytes(delimiter, font);
//
//		if ("<tab>".equalsIgnoreCase(delimiter)) {
//			fieldSepByte = Conversion.getBytes("\t", font);
//		} else if ("<space>".equalsIgnoreCase(delimiter)) {
//			fieldSepByte = Conversion.getBytes(" ", font);
//		} else if (delimiter != null && delimiter.toLowerCase().startsWith("x'")) {
//			try {
//				fieldSepByte = new byte[1];
//				fieldSepByte[0] = Conversion.getByteFromHexString(delimiter);
//				
//			} catch (Exception e) {
//				Common.logMsg("Invalid Hex Seperator", null);
//				e.printStackTrace();
//			}
//		}
		
		sep = noBytes;
	}
	
	
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.BaseWriter#setupInitialFields(int, java.util.List)
	 */
	@Override
	public void setupInitialFields(int numberOfInitialFields, int[] levelSizes) {
		prefixLen = levelSizes;
		super.setupInitialFields(numberOfInitialFields, levelSizes);
	} 


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.FieldWriter#newLine()
	 */
	public void newLine() throws IOException {
		fileWriter.write(eolBytes);
		sep = noBytes;
//		lineNo += 1;
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
										getFieldLength(fieldNo, field.length()), 
										isNumeric(fieldNo)),
								font));
			} catch (Exception e) {
				e.printStackTrace();
			}
			sep = fieldSepByte;
		}
		
		fieldNo += 1;
//		lineNo = -1;
	}
	
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.FieldWriter#writeField(java.lang.String)
	 */
	public void writeField(String field) throws IOException {
		writeField(field, isNumeric(fieldNo));
	}
	
	public void writeField(String field, boolean isNumeric) throws IOException {

		if (isFieldToBePrinted(fieldNo)) {
			fileWriter.write(sep);
			
			if (field == null) {
				field = "";
			}
			
			fileWriter.write(
					Conversion.getBytes(
							pad(field, getFieldLength(fieldNo, field.length()), isNumeric),
							font)
							);
		
			sep = fieldSepByte;
		}
		fieldNo += 1;
	}
	
	private int getFieldLength(int fldNo, int fieldLength) {
		int ret = fieldLength;
		
		if (prefixLen!= null && fldNo < prefixLen.length) {
			if (fieldLength > prefixLen[fldNo]) {
				prefixLen[fldNo] = fieldLength;
			}
			ret = prefixLen[fldNo];
		} else {
			int idx = adjustFieldNo(fieldNo);
			if (idx < fieldLen.length) {
				if (fieldLength > fieldLen[idx]) {
					fieldLen[idx] = fieldLength;
				}
				ret = fieldLen[idx];
			}
		}
		return ret;
	}
	
	private String pad(String val, int len, boolean rightJustified) {
		char[] bld = new char[len];
		int i;
		int l; 
		int start = 0;
		
		if (val == null) {
			val = "";
		}
		l = Math.min(len, val.length());
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
	
	public int[] getColumnWidths() {
		int[] ret;
		int st = 0;
		int diff = 0;
		if (prefixLen == null) {
			ret = new int[fieldLen.length];
		} else {
			ret = new int[prefixLen.length + fieldLen.length];
			System.arraycopy(prefixLen, 0, ret, 0, prefixLen.length);
			st = prefixLen.length;
			diff = st;
		}
		
		for (int i = 0; i < fieldLen.length; i++) {
			if (isFieldToBePrinted(i+diff)) {
				ret[st++] = fieldLen[i];
			}
		}
		
		return ret;
	}
}
