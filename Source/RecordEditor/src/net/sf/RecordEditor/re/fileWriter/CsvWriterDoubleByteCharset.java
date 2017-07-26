package net.sf.RecordEditor.re.fileWriter;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import net.sf.JRecord.Common.CommonBits;
import net.sf.JRecord.Common.Conversion;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;

public class CsvWriterDoubleByteCharset extends BaseWriter {

	private Writer fileWriter;
	private String fieldSep, sep;
	private String eolString;
	public final String quote;
	private int lineNo = 0;
	private int fieldNo = 0;
	public final boolean quoteAllTextFlds;


	/**
	 * Write a Single Byte Csv file
	 * @param fileName Output file name
	 * @param delimiter 
	 * @param fontName
	 * @param quoteStr
	 * @param quoteAllTextFields
	 * @param includeFields
	 * @throws IOException
	 */
	protected CsvWriterDoubleByteCharset(OutputStream outStream, String delimiter,
			String fontName, String quoteStr, boolean quoteAllTextFields,
			boolean[]  includeFields) throws IOException {
		quote = quoteStr;
		quoteAllTextFlds = quoteAllTextFields;
		setPrintField(includeFields);

//		OutputStream fileWriter = new BufferedOutputStream(
//				new FileOutputStream(fileName), 4096);

		BufferedOutputStream out = new BufferedOutputStream(outStream, 4096);
		if (fontName == null || "".equals(fontName)) {
			fileWriter = new OutputStreamWriter(out);
		} else {
			fileWriter = new OutputStreamWriter(out, fontName);
		}
 
		eolString = CommonBits.getEolString("", fontName);
				//Conversion.getBytes(System.getProperty("line.separator"), font);
//		fieldSepByte = Conversion.getBytes(delimiter, font);
		fieldSep = delimiter;

		if ("<tab>".equalsIgnoreCase(delimiter)) {
//			fieldSepByte = Conversion.getBytes("\t", font);
			fieldSep = "\t";
		} else if ("<space>".equalsIgnoreCase(delimiter)) {
			fieldSep = " ";
		}

		sep = "";
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.FieldWriter#newLine()
	 */
	public void newLine() throws IOException {
		fileWriter.write(eolString);
		sep = "";
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
				fileWriter.write(field);
			} catch (Exception e) {
			}

			sep = fieldSep;
		}
		fieldNo += 1;
		lineNo = -1;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.FieldWriter#writeField(java.lang.String)
	 */
	public final void writeField(String field) throws IOException {
		writeField(field, isNumeric(fieldNo));
	}

	public void writeField(String field, boolean isNumeric) throws IOException {

		if (isFieldToBePrinted(fieldNo)) {

			fileWriter.write(sep);

			if (field != null) {
				if (isNumeric) {
				} else if ("".equals(quote)) {
					if (!"".equals(fieldSep) && field.indexOf(fieldSep) >= 0) {
						StringBuilder b = new StringBuilder(field);
						Conversion.replace(b, fieldSep, "");
						Common.logMsgRaw(
								LangConversion.convertMsg(
										"Warning: on line {0} Field {1} Seperator {2} Dropped",
										new Object[] {lineNo, fieldNo , fieldSep}),
								null);
						field = b.toString();
					}
				} else if ((   (!"".equals(fieldSep))
							&& 	field.indexOf(fieldSep) >= 0)
						||  quoteAllTextFlds
						||	field.indexOf("\n") >= 0
						||	field.indexOf("\r") >= 0) {
					StringBuffer b = new StringBuffer(field);

					int pos;
					int j = 0;

					while ((pos = b.indexOf(quote, j)) >= 0) {
						b.insert(pos, quote);
						j = pos + 2;
					}

					field = (b.insert(0, quote).append(quote)).toString();
//				} else if (quoteAllTextFlds) {
//					field = quote + field + quote;
				}
				fileWriter.write(field);
			}

			sep = fieldSep;
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
