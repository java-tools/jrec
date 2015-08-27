package net.sf.RecordEditor.re.openFile;

import net.sf.JRecord.Details.AbstractLayoutDetails;

public interface ISchemaProvider {

	/**
	 * retrieve the layout
	 * @return the layout
	 */
	public abstract AbstractLayoutDetails getRecordLayout(String name,
			String fileName);

}