/*
 * This class holds a Record Layout (ie it describes one line in
 * the file).
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *   - remove unused field editorStatus
 */
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
      
package net.sf.JRecord.Details;

import java.util.ArrayList;
import java.util.HashMap;

import net.sf.JRecord.Common.BasicKeyedField;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.CsvParser.ICsvLineParser;
import net.sf.JRecord.CsvParser.BasicCsvLineParser;
import net.sf.JRecord.CsvParser.ICsvDefinition;
import net.sf.JRecord.CsvParser.ParserManager;
import net.sf.JRecord.External.base.ExternalConversion;
import net.sf.JRecord.Option.IRecordPositionOption;
import net.sf.JRecord.Types.TypeManager;
import net.sf.JRecord.cgen.defc.IRecordDetail4gen;
import net.sf.JRecord.detailsSelection.FieldSelectX;
import net.sf.JRecord.detailsSelection.RecordSelection;




/**
 * This class holds a Record Description. A <b>Record</b> consists of
 * one ore more <b>Fields</b> (Class FieldDetail). The class is used by
 * {@link LayoutDetail}. Each LayoutDetail holds one or more RecordDetail's
 *
 * <pre>
 *     LayoutDetail  - Describes a file
 *       |
 *       +----- RecordDetail (1 or More) - Describes one record in the file
 *                |
 *                +------  FieldDetail (1 or More)  - Describes one field in the file
 * </pre>
 *
 * @param pRecordName  Record Name
 * @param pSelectionField Selection Field
 * @param pSelectionValue Selection Value
 * @param pRecordType Record Type
 * @param pDelim Record Delimiter
 * @param pQuote String Quote (for Comma / Tab Delimeted files)
 * @param pFontName fontname to be used
 * @param pFields Fields belonging to the record
 *
 *
 * @author Bruce Martin
 * @version 0.55
 */
public class RecordDetail extends BasicRecordDetail<RecordDetail.FieldDetails, RecordDetail, AbstractChildDetails<RecordDetail>>
implements AbstractRecordDetail,  ICsvDefinition, IRecordDetail4gen {


	private static final byte UNDEFINED = -121;
	private static final byte NO = 1;
	private static final byte YES = 2;
   //private static final int STATUS_EXISTS         =  1;

	private String recordName;

	//private FieldDetail selectionFld = null;
	//private int    selectionFieldIdx = Constants.NULL_INTEGER;
	private int    recordType;

	//private String selectionField;
	//private String selectionValue;
	private RecordSelection recordSelection = new RecordSelection();

	private String  delimiterUneditted;
	private String  delimiter;
	private int     length = 0;
//	private int     minumumPossibleLength;
	private String  fontName;
	private final String quote, quoteUneditted;

	private int     recordStyle;

	private int     numberOfFieldsAdded = 0;

	private int     childId=0;

	private int     delimiterOrganisation = ICsvDefinition.NORMAL_SPLIT;

	//private int editorStatus = STATUS_UNKOWN;

	private static final HashMap<Integer, String> typeNames = new HashMap<Integer, String>(400);
	private static boolean toInit = true;

	private byte singleByteFont = UNDEFINED;
	private boolean embeddedNewLine = false;

	/**
	 * Create a Record
	 *
	 * @param pRecordName  Record Name
	 * @param pSelectionField Selection Field
	 * @param pSelectionValue Selection Value
	 * @param pRecordType Record Type
	 * @param pDelim Record Delimiter
	 * @param pQuote String Quote (for Comma / Tab Delimeted files)
	 * @param pFontName fontname to be used
	 * @param pFields Fields belonging to the record
	 */
	public RecordDetail(final String pRecordName,
	        			final String pSelectionField,
	        			final String pSelectionValue,
						final int pRecordType,
						final String pDelim,
						final String pQuote,
						final String pFontName,
						final RecordDetail.FieldDetails[] pFields,
						final int pRecordStyle,
						final int childId
						) {
		this(pRecordName, pSelectionField, pSelectionValue, pRecordType, pDelim,
				pQuote, pFontName, pFields, pRecordStyle, childId, false);
	}

	/**
	 * Create a Record
	 *
	 * @param pRecordName  Record Name
	 * @param pSelectionField Selection Field
	 * @param pSelectionValue Selection Value
	 * @param pRecordType Record Type
	 * @param pDelim Record Delimiter
	 * @param pQuote String Quote (for Comma / Tab Delimeted files)
	 * @param pFontName fontname to be used
	 * @param pFields Fields belonging to the record
	 * @param embeddedCr wether there are emmbeded Cr in csv fields
	 */
	public RecordDetail(final String pRecordName,
	        			final String pSelectionField,
	        			final String pSelectionValue,
						final int pRecordType,
						final String pDelim,
						final String pQuote,
						final String pFontName,
						final RecordDetail.FieldDetails[] pFields,
						final int pRecordStyle,
						final int childId,
						final boolean embeddedCr
						) {
		this(pRecordName, pRecordType, pDelim,
			 pQuote, pFontName, pFields, pRecordStyle, childId, embeddedCr);

		if (!"".equals(pSelectionField)) {
			recordSelection.setRecSel(FieldSelectX.get(pSelectionField, pSelectionValue, "=", -1, getField(pSelectionField), false));
		}
	}

	/**
	 * Create a Record
	 *
	 * @param pRecordName  Record Name
	 * @param pRecordType Record Type
	 * @param pDelim Record Delimiter
	 * @param pQuote String Quote (for Comma / Tab Delimeted files)
	 * @param pFontName fontname to be used
	 * @param pFields Fields belonging to the record
	 * @param pRecordStyle Record Style
	 * @param selection RecordSelection
	 */
	public RecordDetail(final String pRecordName,
						final int pRecordType,
						final String pDelim,
						final String pQuote,
						final String pFontName,
						final RecordDetail.FieldDetails[] pFields,
						final int pRecordStyle,
						final RecordSelection selection,
						final int childId
						) {
		this(pRecordName, pRecordType, pDelim,
			 pQuote, pFontName, pFields, pRecordStyle, childId, false);

		recordSelection = selection;
	}

	/**
	 * Create a Record
	 *
	 * @param pRecordName  Record Name
	 * @param pRecordType Record Type
	 * @param pDelim Record Delimiter
	 * @param pQuote String Quote (for Comma / Tab Delimited files)
	 * @param pFontName font name to be used
	 * @param pFields Fields belonging to the record
	 * @param pRecordStyle Record Style
	 */
	public RecordDetail(final String pRecordName,
						final int pRecordType,
						final String pDelim,
						final String pQuote,
						final String pFontName,
						final RecordDetail.FieldDetails[] pFields,
						final int pRecordStyle,
						final int childId,
						final boolean embeddedCr
						) {
		super();

		int j, l;
		this.recordName = pRecordName;
		this.recordType = pRecordType;

		this.fields   = pFields;
		this.quote    = Conversion.decodeCharStr(pQuote, pFontName);
		this.quoteUneditted = pQuote;
		this.fontName = pFontName;
		this.recordStyle = pRecordStyle;
		this.childId = childId;
		this.embeddedNewLine = embeddedCr;

		this.fieldCount = pFields.length;
		while (fieldCount > 0 && pFields[fieldCount - 1] == null) {
		    fieldCount -= 1;
		}

		delimiterUneditted = pDelim;
		delimiter = Conversion.decodeFieldDelim(pDelim, pFontName);

		//System.out.println("Quote 1 ==>" + pQuote + "<==");
		for (j = 0; j < fieldCount; j++) {
		    pFields[j].setRecord(this);
		    l = pFields[j].getPos() + pFields[j].getLen() - 1;
		    if (pFields[j].getLen() >= 0 && length < l) {
		    	length = l;
		    }
		}
//		minumumPossibleLength = length;

		ICsvLineParser parser = ParserManager.getInstance().get(pRecordStyle);
		if (parser != null && parser instanceof BasicCsvLineParser) {
			BasicCsvLineParser bp = (BasicCsvLineParser) parser;
			delimiterOrganisation = bp.delimiterOrganisation;
		}
	}


	protected ICsvLineParser getCsvParser() {
		ICsvLineParser parser = null;

		switch (recordType) {
		case Constants.rtDelimited:
		case Constants.rtDelimitedAndQuote:
			parser = ParserManager.getInstance().get(recordStyle);
		}
		return parser;
	}

//	/**
//	 * if it is null then return "" else return s
//	 *
//	 * @param str string to test
//	 *
//	 * @return Corrected string
//	 */
//	private String correct(String str) {
//		if (str == null) {
//			return "";
//		}
//		return str;
//	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#addField(net.sf.JRecord.Common.FieldDetail)
	 */
	public void addField(RecordDetail.FieldDetails field) {

	    if (fieldCount >= fields.length) {
	    	FieldDetail[] temp = fields;
	        fields = new RecordDetail.FieldDetails[fieldCount + 5];
	        System.arraycopy(temp, 0, fields, 0, temp.length);
	        fieldCount = temp.length;
	    }
	    field.setRecord(this);
	    fields[fieldCount] = field;
	    fieldCount += 1;
	    numberOfFieldsAdded += 1;
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#getRecordName()
	 */
	public String getRecordName() {
		return recordName;
	}



	/**
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#getSelectionFieldIdx()
	 * @deprecated use {@link #getRecordSelection()}
	 */
	public int getSelectionFieldIdx() {
		 if (recordSelection.getElementCount() <= 0) {
			 return Constants.NULL_INTEGER;
		 }
		 return getFieldIndex(recordSelection.getFirstField().getFieldName());
	}

//
//	/**
//	 * @see net.sf.JRecord.Details.AbstractRecordDetail#getSelectionField()
//	 */ @Deprecated
//	public final FieldDetail getSelectionField() {
//		 if (recordSelection.size() <= 0) {
//			 return null;
//		 }
//		return recordSelection.get(0).field;
//	}


	/**
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#setSelectionField(net.sf.JRecord.Common.FieldDetail)
	 * @deprecated use {@link #getRecordSelection()}
	 */ 
	public final void setSelectionField(FieldDetail newSelectionField) {

		 recordSelection.setRecSel(
				 FieldSelectX.get(newSelectionField.getName(), getSelectionValue(), "=", -1, newSelectionField, false));
	}


	/**
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#getSelectionValue()
	 * @deprecated use {@link #getRecordSelection()}
	 */ 
	public String getSelectionValue() {
		 if (recordSelection.size() <= 0) {
			 return null;
		 }
		 return recordSelection.getFirstField().getFieldValue();
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#getRecordType()
	 */
	public int getRecordType() {
		return recordType;
	}

	public final boolean isDelimited() {
		return recordType == Constants.rtDelimited
			|| recordType == Constants.rtDelimitedAndQuote;
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#getLength()
	 */
	public int getLength() {
		return length;
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#getFontName()
	 */
    public String getFontName() {
        return fontName;
    }


    /* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#isBinary(int)
	 */
    public boolean isBinary(int fldNum) {
        return TypeManager.getSystemTypeManager()
        		.getType(fields[fldNum].getType()).isBinary();
    }

    /**
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#isNumericField(int)
	 * @deprecated use getFieldsNumericType == Type.NT_NUMBER
	 */
    public boolean isNumericField(int fldNum) {
        return TypeManager.getSystemTypeManager()
        		.getType(fields[fldNum].getType()).isNumeric();
    }



    /* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#getFieldTypeName(int)
	 */
	@Override
	public String getFieldTypeName(int idx) {

		if (idx < 0 || idx >= getFieldCount()) return "";

		doTypeNameInit();
		return typeNames.get(getField(idx).getType());
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#getFieldsNumericType(int)
	 */
    public int getFieldsNumericType(int idx) {
        return TypeManager.getSystemTypeManager()
        		.getType(this.fields[idx].getType())
        		.getFieldType();
    }


    /* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#getWidths()
	 */
    public int[] getWidths() {
        return null;
    }


    /* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#getDelimiter()
	 */
    public String getDelimiter() {
        return delimiter;
    }

    protected void setDelimiter(String delimiter) {
		this.delimiter = Conversion.decodeFieldDelim(delimiter, fontName);
		this.delimiterUneditted = delimiter;
	}


	/**
	 * @return the delimiterUneditted
	 */
	public final String getDelimiterUneditted() {
		return delimiterUneditted;
	}

	/**
	 * @see net.sf.JRecord.Common.AbstractRecord#getQuote()
	 */
    public String getQuote() {
        return quote;
    }


	/**
	 * @return the quoteUneditted
	 */
	public final String getQuoteUneditted() {
		return quoteUneditted;
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#getRecordStyle()
	 */
	public int getRecordStyle() {
		return recordStyle;
	}


	/**
	 * @return the numberOfFieldsAdded
	 */
	protected final int getNumberOfFieldsAdded() {
		return numberOfFieldsAdded;
	}


	/**
	 * @param numberOfFieldsAdded the numberOfFieldsAdded to set
	 */
	protected final void setNumberOfFieldsAdded(int numberOfFieldsAdded) {
		this.numberOfFieldsAdded = numberOfFieldsAdded;
	}

//	public final String convertFieldDelim(String pDelim) {
//		String delimiter = pDelim;
//
//		if ((pDelim == null) || pDelim == "\t" || ((pDelim = pDelim.trim()).equalsIgnoreCase("<tab>"))) {
//			delimiter = "\t";
//		} else if (pDelim.equalsIgnoreCase("<space>")) {
//			delimiter = " ";
//		} else if (pDelim.length() == 0 || pDelim.startsWith("x'") || pDelim.startsWith("X'")) {
//			
//		} else {
//			delimiter = Conversion.decodeCharStr(pDelim, fontName);
//		}
//		return delimiter;
//	}

//	/**
//	 * The input to this method can be either:<ul>
//	 * <li>A single character
//	 * <li>A character represented in unicode format: \\u0001
//	 * (\\u followed by the character code in hex format).
//	 * </ul>
//	 * 
//	 * @param charId character id to be decoded
//	 * @return character decoded character.
//	 * @throws NumberFormatException
//	 */
//	public static String decodeCharStr(String charId) {
//		char ch;
//		int charLength = charId.length();
//		
//		if (charLength > 2 && charLength < 7 && charId.charAt(0) == '\\'
//		&&((ch = charId.charAt(1)) == 'u' || ch == 'U')) {
//			char[] chars = { (char)Integer.parseInt(charId.substring(2), 16) };
//			charId = new String(chars);
//		}
//		return charId;
//	}

	/**
	 * @return the recordSelection
	 */
	public RecordSelection getRecordSelection() {
		return recordSelection;
	}

	/**
	 * @return the childId
	 */
	public int getChildId() {
		return childId;
	}

	@Override
	public int getOption(int option) {
		switch (option) {
		case Options.OPT_SELECTION_PRESENT:
			return Options.getValue(recordSelection != null);
		}
		return Options.UNKNOWN;
	}
	
	

	/* (non-Javadoc)
	 * @see net.sf.JRecord.cgen.defc.IRecordDetail4gen#getRecordPositionOption()
	 * 
	 * added to implement IRecordDetail4gen
	 */
	@Override
	public IRecordPositionOption getRecordPositionOption() {
		return null;
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.CsvParser.ICsvDefinition#getDelimiterOrganisation()
	 */
	@Override
	public int getDelimiterOrganisation() {
		return delimiterOrganisation;
	}

	/**
	 * @return the singleByteFont
	 */
	public boolean isSingleByteFont() {
		if (singleByteFont == UNDEFINED) {
			try {
				singleByteFont = YES;
				if (Conversion.isMultiByte(fontName)) {
					singleByteFont = NO;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return singleByteFont == YES;
	}

	/**
	 * @return the embeddedNewLine
	 */
	@Override
	public boolean isEmbeddedNewLine() {
		return embeddedNewLine;
	}
	

	private static void doTypeNameInit() {
		if (toInit) {
			try {
				ArrayList<BasicKeyedField> types = ExternalConversion.getTypes(ExternalConversion.USE_DEFAULT_DB);

				for (BasicKeyedField type : types) {
					if ( type.name != null && !  type.name.equals(Integer.toString(type.key))) {
						typeNames.put(type.key,  type.name);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			toInit = false;
		}
	}

	public static class FieldDetails extends FieldDetail implements AbstractRecordDetail.FieldDetails {

		public FieldDetails(String pName, String pDescription, int pType,
				int pDecimal, String pFont, int pFormat, String pParamater) {
			super(pName, pDescription, pType, pDecimal, pFont, pFormat, pParamater);

		}

		public FieldDetails setPosOnly(final int pPosition) {
			super.setPosOnly(pPosition);

			return this;
		}
	}

}
