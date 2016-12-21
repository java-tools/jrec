/*  -------------------------------------------------------------------------
 *
 *            Sub-Project: RecordEditor's version of JRecord 
 *    
 *    Sub-Project purpose: Low-level IO and record translation  
 *                        code + Cobol Copybook Translation
 *    
 *                 Author: Bruce Martin
 *    
 *                License: GPL 2.1 or later
 *                
 *    Copyright (c) 2016, Bruce Martin, All Rights Reserved.
 *   
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU General Public License
 *    as published by the Free Software Foundation; either
 *    version 2.1 of the License, or (at your option) any later version.
 *   
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 * ------------------------------------------------------------------------ */
      
package net.sf.JRecord.External;

import net.sf.JRecord.Common.CommonBits;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.External.Def.ExternalField;
import net.sf.JRecord.External.base.BaseExternalRecord;
import net.sf.JRecord.ExternalRecordSelection.ExternalSelection;
import net.sf.JRecord.detailsSelection.Convert;



//import net.sf.RecordEditor.utils.Common;

/**
 *  This class holds the interchange format of of a RecordLayout.
 *  It can be<ul>
 *    <li>Read from a file (See <b>CopybookLoaderFactory</b>).
 *    <li>Written to an external file (See <b>CopybookWriterManager</b>).
 *    <li>Converted to the internal format (<b>LayoutDetail</b>). See the method <b>asLayoutDetail</b>.
 *    <li>Read from RecordEditor's DB's with SQL like:
 *      <pre>
 *       Select
 *              RecordId,
 *              RecordName,
 *              Description,
 *              RecordType,
 *              System,
 *              ListChar,
 *              CopyBook,
 *              Delimiter,
 *              Quote,
 *              PosRecInd,
 *              RecSepList,
 *              RecordSep,
 *              Canonical_Name,
 *              Record_Style recordStyle,
 *              File_Structure fileStructure
 *       From Tbl_R_Records
 *
 *     </pre>
 *  </ul>
 *
 * This class also provides both specific field access methods
 * and Generic (based on Field number) access
 *
 * <pre>
 * Example:
 *       CopybookLoader loader = CopybookLoaderFactory.getInstance()
 *                                    .getLoader(CopybookLoaderFactory.RECORD_EDITOR_XML_LOADER);
 *       ExternalRecord externalLayout = loader.loadCopyBook(copybookName, 0, 0, "", 0, 0, null);
 *       LayoutDetail layout = externalLayout.asLayoutDetail();
 * </pre>
 */
public class ExternalRecord extends BaseExternalRecord<ExternalRecord> {


  /**
   * Create External Record Definition
   *
   */
  public ExternalRecord () {
      super();
  }

  public ExternalRecord (
                    final int pRecordId
                  , final String pRecordName
                  , final String pDescription
                  , final int pRecordType
                  , final int pSystem
                  , final String pListChar
                  , final String pCopyBook
                  , final String pDelimiter
                  , final String pQuote
                  , final int pPosRecInd
                  , final String pRecSepList
                  , final byte[] pRecordSep
                  , final String pFontName
                  , final int precordStyle
                  , final int pfileStructure
                  , final boolean pEmbeddedCr
                  ) {
      super(  pRecordId, pRecordName, pDescription, pRecordType, pSystem, pListChar, pCopyBook,
    		  pDelimiter, pQuote, pPosRecInd, pRecSepList, pRecordSep, pFontName, precordStyle, pfileStructure, pEmbeddedCr);
  }




  /**
   *  This method returns clones the current record
   *
   *  @return a duplicate of the current record
   */
  public Object clone() {
      return fullClone();
  }


  /**
   * clone as a ExternalRecord
   * @return cloned record
   */
  @SuppressWarnings("deprecation")
public ExternalRecord fullClone() {

      ExternalRecord ret;

      try {
          ret = (ExternalRecord) super.clone();
      } catch (Exception e) {
          ret = new ExternalRecord(
                  getRecordId()
                  , getRecordName()
                  , getDescription()
                  , getRecordType()
                  , getSystem()
                  , getListChar()
                  , getCopyBook()
                  , getDelimiter()
                  , getQuote()
                  , getPosRecInd()
                  , getRecSepList()
                  , getRecordSep()
                  , getFontName()
                  , getRecordStyle()
                  , getFileStructure()
                  , false
          );
      }
      return ret;
  }



 



	/**
	 * Create a new record
	 * @param pRecordName name of the new record
	 * @param fontName fontname to use
	 *
	 * @return the new record
	 */
	public static final ExternalRecord getNullRecord(final String pRecordName,
			final String fontName) {

	    return getNullRecord(pRecordName, Constants.rtRecordLayout, fontName);
	}


	/**
	 * Create a new record
	 * @param pRecordName name of the new record
	 * @param recordType record type for the record
	 * @param fontName fontname to use
	 *
	 * @return the new record
	 */
	public static final ExternalRecord getNullRecord(final String pRecordName,
	        									final int recordType,
	        									final String fontName) {

	    return new ExternalRecord(-1, pRecordName, "", recordType, 0, "N",
			"", "<Tab>", "", 0, Constants.DEFAULT_STRING, Constants.SYSTEM_EOL_BYTES, fontName, 0, -1, false);
	}



	/**
	 * Get a copy of all subrecords.
	 *
	 * @return Sub records
	 */
	public ExternalRecord[] toArray() {
	    return subRecords.toArray(new ExternalRecord[subRecords.size()]);
	}


//	/**
//	 * Get the Field that should be tested to determine if this is the valid
//	 * Sub-Record for the current line.
//	 *
//	 * @return the tstField
//	 *
//	 * @deprecated Use getTstFields
//	 */ @Deprecated
//	 @Override public String getTstField() {
//
//		ExternalFieldSelection f = getFirstSelection(recSelect);
//		if (f == null) {
//			return null;
//		} else {
//			return f.getFieldName();
//        }
//	}

//	/**
//	 * Set the Field / value that should be tested to determine if this is the valid
//	 * Sub-Record for the current line.
//	 *
//	 * @param tstField the tstField to set
//	 * @param value Value to compare field to
//	 *
//	 *  @deprecated  use addTstField
//	 */ @Deprecated
//	@Override public void setTstField(String tstField, String value) {
//
//		recSelect = new ExternalFieldSelection(tstField, value);
//	}
//
//	/**
//	 * Add a Field/Value that should be tested to determine if this is the valid
//	 * Sub-Record for the current line.
//	 *
//	 * @param tstField the tstField to set
//	 * @param value Value to compare field to
//	 */
//	@Override public void addTstField(String tstField, String value) {
//		addTstField(tstField, ExternalFieldSelection.EQUALS_OPERATOR, value);
//	}
//
//	@Override public void addTstField(String tstField, String op, String value) {
//
//		if (recSelect == null) {
//			recSelect = new ExternalGroupSelection<ExternalSelection>(1);
//		}
//		if (recSelect instanceof ExternalGroupSelection) {
//			ExternalGroupSelection g = (ExternalGroupSelection) recSelect;
//			g.add(new ExternalFieldSelection(tstField, value, op));
//			return;
//		}
//		//System.out.println();
//		//System.out.println("-->" + recSelect);
//		//System.out.println("-->" + recSelect.getClass().getName());
//		throw new RuntimeException("Can not add Test Field");
//	}
//
//	/**
//	 * Get the value the TestField should be compared to
//	 *
//	 * @return the tstFieldValue
//	 * @deprecated Use getTstFields
//	 */
//	public String getTstFieldValue() {
//			ExternalFieldSelection f = getFirstSelection(recSelect);
//			if (f == null) {
//				return null;
//			} else {
//				return f.getFieldValue();
//			}
//	}
//


	/**

	/**
	 * Convert to internal format
	 * @return the internal LayoutDetail equivalent
	 *
	 */
	public final LayoutDetail asLayoutDetail() {

	    LayoutDetail ret = null;

	    RecordDetail[] layouts;
	    String recordSepString = this.getRecSepList();

	    String fontName = this.getFontName();
	    byte[] recordSep = CommonBits.getEolBytes( this.getRecordSep(), recordSepString, fontName);

		
	    if (this.getNumberOfRecords() == 0) {
	        layouts = new RecordDetail[1];
	        layouts[0] = this.toRecordDetail(0);
	        ExternalSelection recordSelection = this.getRecordSelection();
		    if (recordSelection != null && recordSelection.getSize() > 0) {
		    	layouts[0].getRecordSelection().setRecSel((new Convert()).convert(recordSelection, layouts[0]));
		    }
			ret = genSchema(layouts, recordSepString, fontName, recordSep);
	    } else {
	        layouts = new RecordDetail[this.getNumberOfRecords()];
	        for (int i = 0; i < layouts.length; i++) {
	            layouts[i] = this.getRecord(i).toRecordDetail(i);
	        }
	    
	        ret = genSchema(layouts, recordSepString, fontName, recordSep);
		    for (int i = 0; i < layouts.length; i++) {
			    ExternalSelection recordSelection = this.getRecord(i).getRecordSelection();
			    if (recordSelection != null && recordSelection.getSize() > 0) {
			    	layouts[i].getRecordSelection().setRecSel(
			    			(new Convert()).convert(recordSelection, new LayoutGetFieldByName(ret,  layouts[i])));
			    }
		    }
	    }
	    ret.setDelimiter(this.getDelimiter());
	    ret.setLineNumberOfFieldNames(this.getLineNumberOfFieldNames());

	    return ret;

//		return ToLayoutDetail.getInstance().getLayout(this);
	}
	

	/**
	 * @param layouts
	 * @param recordSepString
	 * @param fontName
	 * @param recordSep
	 * @return
	 */
	private LayoutDetail genSchema(
			RecordDetail[] layouts, String recordSepString, String fontName,
			byte[] recordSep) {
		return new LayoutDetail(this.getRecordName(),
	            layouts,
	            this.getDescription(),
	            this.getRecordType(),
	            recordSep,
	            recordSepString,
	            fontName,
	            null,
	            this.getFileStructure(),
	            this.getRecordLength());
	}


	/**
	 * converts an ExtendedRecord (ie used for storage of records externally)
	 * to the format used in the record editor
	 *
	 * @param idx record definition
	 *
	 * @return the same definition as used in the record editor
	 */
	private final RecordDetail toRecordDetail(int idx) {
		RecordDetail.FieldDetails[] fields = this.toFieldDetailArray();
				//new RecordDetail.FieldDetails[def.getNumberOfRecordFields()];
//	    ExternalField fieldRec;
//	    int i;
//
//	    
//	    for (i = 0; i < fields.length; i++) {
//	        fieldRec = def.getRecordField(i);
//	        fields[i] = new RecordDetail.FieldDetails(fieldRec.getName(),
//	                fieldRec.getDescription(), fieldRec.getType(),
//	                fieldRec.getDecimal(), def.getFontName(), 0, fieldRec.getParameter());
//
//	        if (fieldRec.getLen() < 0) {
//	        	fields[i].setPosOnly(fieldRec.getPos());
//	        } else {
//	        	fields[i].setPosLen(fieldRec.getPos(), fieldRec.getLen());
//	        }
//
//	        fields[i].setGroupName(fieldRec.getGroup());
//
//		    String s = fieldRec.getDefault();
//		    if (s != null && ! "".equals(s)) {
//		    	fields[i].setDefaultValue(s);
//		    }
//	    }


	    RecordDetail ret = new RecordDetail(this.getRecordName(),
//	    		def.getTstField(), def.getTstFieldValue(),
	            this.getRecordType(), this.getDelimiter(), this.getQuote(),
	            this.getFontName(), fields, this.getRecordStyle(), idx, this.isEmbeddedCr());
	    ret.setParentRecordIndex(this.getParentRecord());

//	    if (def.getRecordSelection() != null && def.getRecordSelection().getSize() > 0) {
//	    	ret.getRecordSelection().setRecSel((new Convert()).convert(def.getRecordSelection(), ret));
//	    }

	    if (this.isDefaultRecord()) {
	    	ret.getRecordSelection().setDefaultRecord(true);
	    }

	    return ret;
	}

	
	private RecordDetail.FieldDetails[] toFieldDetailArray() {
		RecordDetail.FieldDetails[] fields = new RecordDetail.FieldDetails[this.getNumberOfRecordFields()];
	    ExternalField fieldRec;

	    int[][] posLength = super.getPosLength();
	    for (int i = 0; i < fields.length; i++) {
	        fieldRec = getRecordField(i);
	        fields[i] = new RecordDetail.FieldDetails(fieldRec.getName(),
	                fieldRec.getDescription(), fieldRec.getType(),
	                fieldRec.getDecimal(), getFontName(), 0, fieldRec.getParameter());

	        if (posLength[LENGTH_IDX][i] < 0) {
	        	fields[i].setPosOnly(posLength[POSITION_IDX][i]);
	        } else {
	        	fields[i].setPosLen(posLength[POSITION_IDX][i], posLength[LENGTH_IDX][i]);
	        }

	        fields[i].setGroupName(fieldRec.getGroup());

		    String s = fieldRec.getDefault();
		    if (s != null && ! "".equals(s)) {
		    	fields[i].setDefaultValue(s);
		    }
	    }
	    return fields;
	}


}
