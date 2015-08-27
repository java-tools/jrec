package net.sf.RecordEditor.re.fileWriter;

import java.io.IOException;

import net.sf.JRecord.Common.Conversion;

public class WriterBuilder {

	public static FieldWriter newCsvWriter(String fileName, String delimiter,
			String fontName, String quoteStr, boolean quoteAllTextFields,
			boolean[]  includeFields) throws IOException {
		
		if (Conversion.isMultiByte(fontName)) {
			return new CsvWriterDoubleByteCharset(fileName, delimiter, fontName, quoteStr, quoteAllTextFields, includeFields);
		} else {
			return new CsvWriterSingleByteCharset(fileName, delimiter, fontName, quoteStr, quoteAllTextFields, includeFields);
		}
	}
}
