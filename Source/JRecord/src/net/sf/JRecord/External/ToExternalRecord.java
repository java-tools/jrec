package net.sf.JRecord.External;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractRecordDetail;

/**
 * Class to convert a RecordLayout (internal Format) to The Interface format (ExternalRecord)]
 * that can be written to a file / saved in a DB (RecordEditor) 
 * @author Bruce Martin
 *
 */
public final class ToExternalRecord {

	private static ToExternalRecord instance = null;
	
	/**
	 * Convert a RecordLayout (internal format) to an ExternalRecord (interface format).
	 * @param layout
	 * @param copybookName
	 * @param system
	 * @return layout in ExternalRecord format
	 * @throws RecordException
	 */
	public final ExternalRecord getExternalRecord(AbstractLayoutDetails layout, String copybookName, int system)
	throws  RecordException {
		//int rt = Constants.rtGroupOfRecords;
		//int type = recordDefinition.getLayoutType();
		
//		System.out.println();
//		System.out.println("layout type >> " + layout.getFileStructure());
		ExternalRecord rec = new ExternalRecord( 
				-1, copybookName, layout.getDescription(),
				Constants.rtGroupOfRecords, system, "Y", copybookName, 
				layout.getDelimiter(), "", 0, "default",
				layout.getRecordSep(), layout.getFontName(), 
				0, fixIOType(layout.getFileStructure())
		);
		rec.setNew(true);
		
		for (int i = 0; i < layout.getRecordCount(); i++) {
			if (! layout.getRecord(i).getRecordName().startsWith("XML")) {
				rec.addRecord(convert(i, layout, copybookName, system));
			}
		}
		
		return rec;
	}
	
	/**
	 * Convert a RecordDetail to an External record 
	 * @param id record index 
	 * @param layout record Description
	 * @param copybookName Layout Name
	 * @param system System Id
	 * 
	 * @return equivalent ExternalRecord
	 */
	private ExternalRecord convert(int id, AbstractLayoutDetails layout, String copybookName, int system) {
		FieldDetail dtl;
		ExternalField field;
		ExternalRecord rec; 
		AbstractRecordDetail record = layout.getRecord(id);
		String name = record.getRecordName();
		
		rec = new ExternalRecord( 
				id, name, "", record.getRecordType(), 
				system, "N", copybookName + "_" + name, record.getDelimiter(),
				"", 0, "default", layout.getRecordSep(), record.getFontName(), 
				0, fixIOType(layout.getFileStructure())
		);
		rec.setNew(true);
		System.out.println("Record >> " + id +  " " + record.getRecordName());
		
		for (int i = 0; i < record.getFieldCount(); i++) {
			dtl = record.getField(i);
			field = new ExternalField(dtl.getPos(), dtl.getLen(),
							dtl.getName(), dtl.getDescription(),
							dtl.getType(), dtl.getDecimal(), dtl.getFormat(), dtl.getParamater(),
							"", "", i					
			);
			rec.addRecordField(field);
		}
		return rec;
	}
	
//	/**
//	 * Correct the length for XML / CSV files
//	 * @param len length
//	 * @return corrected length
//	 */
//	private int fixLength(int len) {
//		int ret = len;
//		if (len < 0) {
//			ret = 0;
//		}
//		return ret;
//	}
	
	private int fixIOType(int ioType) {
		int ret = ioType;
		if (ret == Constants.IO_XML_BUILD_LAYOUT) {
			ret = Constants.IO_XML_USE_LAYOUT;
		}
		return ret;
	}


	/**
	 * Get an instance of ToExternalRecord
	 * @return an instance of ToExternalRecord
	 */
	public final static ToExternalRecord getInstance() {
		if (instance == null) {
			instance = new ToExternalRecord();
		}
		return instance;
	}
}
