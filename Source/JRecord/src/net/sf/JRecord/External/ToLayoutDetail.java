/*
 * @Author Bruce Martin
 * Created on 28/01/2007
 *
 * Purpose:
 * Convert an ExternalRecord into a LayoutDetail (internal form)
 */
package net.sf.JRecord.External;

import net.sf.JRecord.Common.CommonBits;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.External.Def.ExternalField;
import net.sf.JRecord.ExternalRecordSelection.ExternalSelection;
import net.sf.JRecord.detailsSelection.Convert;


/**
 * Convert an ExternalRecord (interface format) into a LayoutDetail (internal format)
 *
 * @author Bruce Martin
 *
 */
public class ToLayoutDetail {

    private static ToLayoutDetail instance = new ToLayoutDetail();

	/**
	 * onvert an ExternalRecord into a LayoutDetail (internal form)
	 *
	 * @param recordDefinition Standard record definition
	 *
	 * @return Group of records
	 * @throws RecordException any error that occurs
	 */
	public LayoutDetail getLayout(ExternalRecord recordDefinition)
	throws  RecordException {

		if (recordDefinition == null) {
			return null;
		}
	    LayoutDetail ret = null;

	    RecordDetail[] layouts;
	    String recordSepString = recordDefinition.getRecSepList();

	    String fontName = recordDefinition.getFontName();
	    byte[] recordSep = CommonBits.getEolBytes( recordDefinition.getRecordSep(), recordSepString, fontName);

		
	    if (recordDefinition.getNumberOfRecords() == 0) {
	        layouts = new RecordDetail[1];
	        layouts[0] = toRecordDetail(recordDefinition, 0);
	        ExternalSelection recordSelection = recordDefinition.getRecordSelection();
		    if (recordSelection != null && recordSelection.getSize() > 0) {
		    	layouts[0].getRecordSelection().setRecSel((new Convert()).convert(recordSelection, layouts[0]));
		    }
			ret = genSchema(recordDefinition, layouts, recordSepString, fontName, recordSep);
	    } else {
	        layouts = new RecordDetail[recordDefinition.getNumberOfRecords()];
	        for (int i = 0; i < layouts.length; i++) {
	            layouts[i] = toRecordDetail(recordDefinition.getRecord(i), i);
	        }
	    
	        ret = genSchema(recordDefinition, layouts, recordSepString, fontName, recordSep);
		    for (int i = 0; i < layouts.length; i++) {
			    ExternalSelection recordSelection = recordDefinition.getRecord(i).getRecordSelection();
			    if (recordSelection != null && recordSelection.getSize() > 0) {
			    	layouts[i].getRecordSelection().setRecSel(
			    			(new Convert()).convert(recordSelection, new LayoutGetFieldByName(ret,  layouts[i])));
			    }
		    }
	    }
	    ret.setDelimiter(recordDefinition.getDelimiter());
	    ret.setLineNumberOfFieldNames(recordDefinition.getLineNumberOfFieldNames());

	    return ret;
	}


	/**
	 * @param recordDefinition
	 * @param layouts
	 * @param recordSepString
	 * @param fontName
	 * @param recordSep
	 * @return
	 */
	private LayoutDetail genSchema(ExternalRecord recordDefinition,
			RecordDetail[] layouts, String recordSepString, String fontName,
			byte[] recordSep) {
		return new LayoutDetail(recordDefinition.getRecordName(),
	            layouts,
	            recordDefinition.getDescription(),
	            recordDefinition.getRecordType(),
	            recordSep,
	            recordSepString,
	            fontName,
	            null,
	            recordDefinition.getFileStructure());
	}


	/**
	 * converts an ExtendedRecord (ie used for storage of records externally)
	 * to the format used in the record editor
	 *
	 * @param def record definition
	 *
	 * @return the same definition as used in the record editor
	 */
	private RecordDetail toRecordDetail(ExternalRecord def, int idx) {
		RecordDetail.FieldDetails[] fields = new RecordDetail.FieldDetails[def.getNumberOfRecordFields()];
	    ExternalField fieldRec;
	    int i;

	    for (i = 0; i < fields.length; i++) {
	        fieldRec = def.getRecordField(i);
	        fields[i] = new RecordDetail.FieldDetails(fieldRec.getName(),
	                fieldRec.getDescription(), fieldRec.getType(),
	                fieldRec.getDecimal(), def.getFontName(), 0, fieldRec.getParameter());

	        if (fieldRec.getLen() < 0) {
	        	fields[i].setPosOnly(fieldRec.getPos());
	        } else {
	        	fields[i].setPosLen(fieldRec.getPos(), fieldRec.getLen());
	        }

	        fields[i].setGroupName(fieldRec.getGroup());

		    String s = fieldRec.getDefault();
		    if (s != null && ! "".equals(s)) {
		    	fields[i].setDefaultValue(s);
		    }
	    }


	    RecordDetail ret = new RecordDetail(def.getRecordName(),
//	    		def.getTstField(), def.getTstFieldValue(),
	            def.getRecordType(), def.getDelimiter(), def.getQuote(),
	            def.getFontName(), fields, def.getRecordStyle(), idx, def.isEmbeddedCr());
	    ret.setParentRecordIndex(def.getParentRecord());

//	    if (def.getRecordSelection() != null && def.getRecordSelection().getSize() > 0) {
//	    	ret.getRecordSelection().setRecSel((new Convert()).convert(def.getRecordSelection(), ret));
//	    }

	    if (def.isDefaultRecord()) {
	    	ret.getRecordSelection().setDefaultRecord(true);
	    }

	    return ret;
	}



	/**
	 * @return the instance
	 */
	public static final ToLayoutDetail getInstance() {
		return instance;
	}
}
