package net.sf.RecordEditor.utils;

import java.io.IOException;

public interface FieldWriter {

	public abstract void newLine() throws IOException;

//	public void writeColumnHeadings(int layoutIdx, ColumnMappingInterface fieldMapping,
//			AbstractLayoutDetails layout) throws IOException;
	
	public abstract void writeFieldHeading(String field) throws IOException;

	public abstract void writeField(String field) throws IOException;

	public abstract void close() throws IOException;

	/**
	 * @param numericFields the numericFields to set
	 */
	public abstract void setNumericFields(boolean[] numericFields);

	public abstract boolean isFieldToBePrinted(int fldNo);

	public abstract void setPrintField(int idx, boolean include);

	public abstract void setupInitialFields(int numberOfInitialFields, int[] levelSizes);
	

}