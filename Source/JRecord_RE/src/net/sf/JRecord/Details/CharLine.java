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

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.CsvParser.CsvDefinition;
import net.sf.JRecord.CsvParser.ICsvCharLineParser;
import net.sf.JRecord.CsvParser.ICsvDefinition;
import net.sf.JRecord.CsvParser.CsvParserManagerChar;
import net.sf.JRecord.Details.RecordDetail.FieldDetails;
import net.sf.JRecord.Types.Type;
import net.sf.JRecord.Types.TypeChar;
import net.sf.JRecord.Types.TypeManager;


/**
 * Line implementation where the line is represented as a String.
 *
 * @author Bruce Martin
 *
 */
public class CharLine extends BasicLine<CharLine>  {

	private static LineProvider<LayoutDetail, CharLine> defaultProvider = new CharLineProvider();
	private static final AbstractTreeDetails<FieldDetail, RecordDetail, LayoutDetail, CharLine>
				NULL_TREE_DETAILS = new NullTreeDtls<FieldDetail, RecordDetail, LayoutDetail, AbstractChildDetails<RecordDetail>, CharLine>();

	private String data;

	public CharLine(LayoutDetail layoutDef, String line) {
		super(defaultProvider, layoutDef, NULL_TREE_DETAILS);


		if (line == null) {
			data = "";
		} else {
			data = line;
		}
		init();
	}


	@Override
	public byte[] getData(int start, int len) {
		String tmpData = getLineData();
	    int tempLen = Math.min(len, tmpData.length() - start + 1);

	    if ((tmpData.length() < start) || (tempLen < 1) || (start < 1)) {
	        return NULL_RECORD;
	    }
	    //System.out.println();
	    //System.out.println("getData --> " + " " + start + ", " + len + ", " + tempLen + " ~ " + tmpData.length()
	    // 		+ " " + tmpData);
		return Conversion.getBytes(
				tmpData.substring(
						start - 1,
						start + tempLen - 1
				),
				layout.getFontName());
	}


	@Override
	public byte[] getData() {
		return Conversion.getBytes(getLineData(), layout.getFontName());
	}



	@Override
	public Object getField(IFieldDetail field) {

		if (field.isFixedFormat()) {
			if (field.getType() == Type.ftChar
			||  field.getType() == Type.ftCharRightJust
			||  field.getType() == Type.ftCharRestOfRecord 
			||  TypeManager.getInstance().getType(field.getType()) == TypeManager.getInstance().getType(Type.ftChar)) {
				return getFieldText(field);
			}

			Type type = TypeManager.getSystemTypeManager().getType(field.getType());
			byte[] bytes = getData(field.getPos(), field.getLen());
			FieldDetail tmpField = new FieldDetail(field.getFontName(), field.getDescription(),
					field.getType(), field.getDecimal() ,field.getFontName(), field.getFormat(), field.getParamater());
			tmpField.setPosLen(1, bytes.length);

			return type.getField(bytes, 1, tmpField);
		} else {
			return layout.formatCsvField(field, field.getType(), getLineData());
		}
	}



	@Override
	public byte[] getFieldBytes(int recordIdx, int fieldIdx) {
		return null;
	}


	@Override
	public String getFieldText(int recordIdx, int fieldIdx) {
		return  getFieldText(layout.getRecord(recordIdx).getField(fieldIdx));
	}


	private String getFieldText(IFieldDetail fldDef) {
		int start = fldDef.getPos() - 1;
		String tData = getLineData();

		int tempLen = fldDef.getLen();
		if (fldDef.getType() == Type.ftCharRestOfRecord || tempLen > tData.length() - start) {
			tempLen = tData.length() - start;
		}

	    if (tData.length() < start || tempLen < 1 || start < 0) {
	        return "";
	    }
		String s = tData.substring(start, start + tempLen);
		int len = s.length() - 1;
		if (s.length() > 0 && " ".equals(s.substring(len))) {
			while (len > 0 && " ".equals(s.substring(len - 1, len))) {
				len -= 1;
			}
			s = s.substring(0, len);
		}

		return s;
	}


	@Override
	public String getFullLine() {
		return getLineData();
	}


	/**
	 * Get the Prefered Record Layou Index for this record
	 *
	 * @return Index of the Record Layout based on the Values
	 */
	@Override
	public int getPreferredLayoutIdx() {
		int ret = preferredLayout;
		String d = getLineData();

		if (ret == Constants.NULL_INTEGER) {
			ret = getPreferredLayoutIdxAlt();

			if (ret < 0) {
				for (int i=0; i< layout.getRecordCount(); i++) {
					if (d.length() == layout.getRecord(i).getLength()) {
						ret = i;
						preferredLayout = i;
						break;
					}
				}
			}
		}

		return ret;
	}


	@Override
	public void replace(byte[] rec, int start, int len) {

	}

	protected void clearData() {
		data = "";
	}

	protected String getLineData() {
		return data;
	}


	@Override
	public void setData(String newVal) {
		data = newVal;
	}


	public void setDataRaw(String newVal) {
		data = newVal;
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractLine#setData(byte[])
	 */
	@Override
	public void setData(byte[] newVal) {
		data = toStr(newVal, layout.getFontName());
	}


	@Override
	public void setField(IFieldDetail field, Object value)
			throws RecordException {

		if (field.isFixedFormat()) {
			String s = "";
			Type type = TypeManager.getSystemTypeManager().getType(field.getType());
			if (value != null) {
				s = value.toString();
			}

			s = type.formatValueForRecord(field, s);

			if (s.length() < field.getLen()
			&&	type instanceof TypeChar && ! ((TypeChar) type).isLeftJustified()) {
				s = Conversion.padFront(s, field.getLen() - s.length(), ' ');
			} 
			updateData(field.getPos(), field.getLen(), s);
		} else {
	        ICsvCharLineParser parser = CsvParserManagerChar.getInstance().get(field.getRecord().getRecordStyle());
	        Type typeVal = TypeManager.getSystemTypeManager().getType(field.getType());
	        String s = typeVal.formatValueForRecord(field, value.toString());
	    	ICsvDefinition csvDef;
	    	if (field.getRecord() instanceof ICsvDefinition) {
	    		csvDef = (ICsvDefinition) field.getRecord();
	    	} else {
	    		csvDef = new CsvDefinition(layout.getDelimiterDetails(), field.getQuoteDefinition());
	    	}

            data =
            		parser.setField(field.getPos() - 1,
            				typeVal.getFieldType(),
            				data,
            				csvDef,
            				s);
		}
	}

	@Override
	public String setFieldHex(int recordIdx, int fieldIdx, String val)
			throws RecordException {

		return null;
	}

	@Override
	public void setFieldText(int recordIdx, int fieldIdx, String value)
			throws RecordException {
		IFieldDetail fldDef = layout.getRecord(recordIdx).getField(fieldIdx);

		updateData(fldDef.getPos(), fldDef.getLen(), value);
	}
	
	protected static String toStr(byte[] b, String charset) {
		String str;
		if (b == null || b.length == 0) {
			str = "";
		} else {
			str = Conversion.toString(b, charset);
		}
		
		return str;
	}

	private void updateData(int pos, int length, String value) {
		int i;
		int start = pos -1;
		int len = Math.min(value.length(), length);
		StringBuilder dataBld = new StringBuilder(getLineData());
		int en = start + Math.max(len, length) - dataBld.length();

		//System.out.print(" --> " + dataBld.length());
		for (i = 0; i <= en; i++) {
			dataBld.append(' ');
		}

//		System.out.println(" --> " + dataBld.length()
//				+ " pos=" + start + " len=" + len
//				+ " " + en
//				+ " " + value.length()
//				+ " " + value);
		for (i = 0; i < len; i++) {
			dataBld.setCharAt(start + i, value.charAt(i));
		}

		for (i = len; i < length; i++) {
			dataBld.setCharAt(start + i, ' ');
		}
		data = dataBld.toString();
	}


    /**
     * @see java.lang.Object#clone()
     */
    protected final Object clone() {
    	return lineProvider.getLine(layout, getLineData());
    }


	@Override
	public boolean isDefined(int rec, int fldNum) {
		FieldDetails field = layout.getRecord(rec).getField(fldNum);
		if (this.data == null || data.length() <= field.getPos()) {
			return false;
		}

		if (TypeManager.isNumeric(field.getType())) {
			int e = Math.min(field.getPos() + field.getLen(), data.length());
			for (int i = field.getPos() - 1; i < e; i++) {
				switch (data.charAt(i)) {
				case ' ':
				case 0:
					break;
				default:
					return true;
				}
			}
			return false;
		}
		return true;
		
	}
}
