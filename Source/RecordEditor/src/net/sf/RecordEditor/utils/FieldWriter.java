package net.sf.RecordEditor.utils;

import java.io.IOException;

public interface FieldWriter {

	public abstract void newLine() throws IOException;

	public abstract void writeFieldHeading(String field) throws IOException;

	public abstract void writeField(String field) throws IOException;

	public abstract void close() throws IOException;

	/**
	 * @param numericFields the numericFields to set
	 */
	public abstract void setNumericFields(boolean[] numericFields);

}