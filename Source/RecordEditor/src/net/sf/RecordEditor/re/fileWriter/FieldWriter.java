package net.sf.RecordEditor.re.fileWriter;

import java.io.IOException;

import net.sf.JRecord.Common.FieldDetail;

public interface FieldWriter {

	public abstract void newLine() throws IOException;

	public abstract void writeFieldHeading(String field) throws IOException;

	public abstract void writeFieldDetails(
			FieldDetail fldDetail, String fieldValue, String textValue, String HexValue) throws IOException;

	public abstract void writeField(String field) throws IOException;

	public abstract void close() throws IOException;

	/**
	 * @param numericFields the numericFields to set
	 */
	public abstract void setNumericFields(boolean[] numericFields);

	public abstract boolean isFieldToBePrinted(int fldNo);

	public abstract void setPrintField(int idx, boolean include);

	public abstract void setupInitialFields(int numberOfInitialFields, int[] levelSizes);

	public abstract void startLevel(boolean indent, String id);

	public abstract void endLevel();

	public abstract boolean printAllFields();
}