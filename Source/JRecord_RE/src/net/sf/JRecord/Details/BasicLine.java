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


import java.util.List;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.CsvParser.BinaryCsvParser;
import net.sf.JRecord.CsvParser.ICsvDefinition;
import net.sf.JRecord.CsvParser.ICsvLineParser;
import net.sf.JRecord.Types.Type;
import net.sf.JRecord.detailsSelection.RecordSelection;

/**
 * Parent class for lines built aroud an array of (bytes or characters).
 *
 * @author Bruce Martin
 *
 * @param <ActualLine>
 */
public abstract class BasicLine<ActualLine extends AbstractLine> extends BaseLine<LayoutDetail>
implements AbstractLine, ISetLineProvider<LayoutDetail, ActualLine>, IColumnInsertDelete {


	protected static final byte[] NULL_RECORD = new byte[0];
	protected LineProvider<LayoutDetail, ? extends ActualLine> lineProvider;
	protected int preferredLayoutAlt = Constants.NULL_INTEGER;
	protected int preferredLayout = Constants.NULL_INTEGER;
	protected int writeLayout = Constants.NULL_INTEGER;



	protected AbstractTreeDetails<FieldDetail, RecordDetail, LayoutDetail, ActualLine>
							children;
	//ChildLines<FieldDetail, RecordDetail, LayoutDetail, AbstractChildDetails<RecordDetail>, ActualLine> children = null;

	public BasicLine(LineProvider<LayoutDetail, ActualLine> defaultProvider, LayoutDetail linesLayout,
			AbstractTreeDetails<FieldDetail, RecordDetail, LayoutDetail, ActualLine> defaultTree) {
		super(linesLayout);

		lineProvider = defaultProvider;
		//layout = linesLayout;
		children = defaultTree;
	}



	protected final void init() {
		if (layout != null && layout.hasChildren()) {
			children = new TreeDetails<FieldDetail, RecordDetail, LayoutDetail, AbstractChildDetails<RecordDetail>, ActualLine>();
//			                        ChildLines<ProtoFieldDef, ProtoRecordDef, ProtoLayoutDef, ProtoChildDefinition, ProtoLine>
		}
	}


	/**
	 * Get the field value as Hex
	 *
	 * @param recordIdx Index of the current layout used to retrieve the field
	 * @param fieldIdx Index of the current field
	 *
	 * @return field value as a Hex String
	 */
	public final String getFieldHex(final int recordIdx, final int fieldIdx) {

		try {
			IFieldDetail field = layout.getField(recordIdx, fieldIdx);

			return layout.getField(getData(),
			        				Type.ftHex,
			        				field).toString();

		} catch (final Exception ex) {
			return "";
		}
	}


	/**
	 * Alternative get layout method without length checks
	 */
	@Override
	public int getPreferredLayoutIdxAlt() {
		if (preferredLayoutAlt == Constants.NULL_INTEGER) {
			int defaultIdx = Constants.NULL_INTEGER;
			int i = 0;
			int defCount = -1;
			RecordSelection sel;
			int size = layout.getRecordCount();

			if (size == 1) {
			    preferredLayoutAlt = 0;
			} else if (layout.getDecider() != null) {
			    preferredLayoutAlt = layout.getDecider().getPreferedIndex(this);
			}


			// TODO fix default record
			while ((i < size) && (preferredLayoutAlt == Constants.NULL_INTEGER)) {
				sel = layout.getRecord(i).getRecordSelection();
				switch (sel.isSelected(this)) {
				case DEFAULT:
					if (sel.size() > defCount) {
						defaultIdx = i;
						defCount = sel.size();
					}
					break;

				case YES:
					preferredLayoutAlt = i;
					break;
					
				case NO:
				}

				i += 1;
			}
			if (preferredLayoutAlt == Constants.NULL_INTEGER) {
				preferredLayoutAlt = defaultIdx;
			}
		}

		return preferredLayoutAlt;
	}


	/**
	 * @param pWriteLayout The recordIndex to be used calculate size (when writing the record)
	 */
	public void setWriteLayout(final int pWriteLayout) {
		this.preferredLayoutAlt = pWriteLayout;
		this.writeLayout = pWriteLayout;
	}

	/**
	 * Gets a fields value
	 *
	 * @param recordIdx Index of the RecordDescription to be used.
	 * @param fieldIdx Index of the required field
	 *
	 * @return the request field (formated)
	 */
	public Object getField(final int recordIdx, final int fieldIdx) {
		try {
			switch (fieldIdx) {
			case Constants.FULL_LINE:	return getFullLine();
			case Constants.KEY_INDEX:	return null;
			}

			Object fieldDef = getField(layout.getField(recordIdx, fieldIdx));
			if (fieldDef == null) {
				return "";
			}
			return fieldDef;
		} catch (final Exception ex) {
			System.out.println("Field Index: " + fieldIdx);
			ex.printStackTrace();
			return "";
		}
	}

	/**
     * Test if Tree rebuild is required
     */
	public boolean isRebuildTreeRequired() {
		return false;
	}

	/**
	 * @param pLayout The layouts to set.
	 */
	public void setLayout(final AbstractLayoutDetails pLayout) {
		super.layout = (LayoutDetail) pLayout;
		preferredLayoutAlt = Constants.NULL_INTEGER;

		init();
	}


	/**
	 * Sets a field to a new value
	 *
	 * @param recordIdx record layout
	 * @param fieldIdx field number in the record
	 * @param val new value
	 *
	 * @throws RecordException any error that occurs during the save
	 */
	public void setField(final int recordIdx, final int fieldIdx, Object val)
			throws RecordException {

	    IFieldDetail field = layout.getField(recordIdx, fieldIdx);

	    //adjustLengthIfNecessary(field, recordIdx);

	   	setField(field, val);
	}

	/**
     * Set the line provider
     *
     * @param pLineProvider The lineProvider to set.
     */
	@Override
    public void setLineProvider(LineProvider<LayoutDetail, ? extends ActualLine> pLineProvider) {
        this.lineProvider = pLineProvider;
    }


	/**
	 * @return the children
	 */
	@Override
	public AbstractTreeDetails<FieldDetail, RecordDetail, LayoutDetail, ActualLine> getTreeDetails() {
		return children;
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.Common.AbstractChildLines#isRebuildTreeRequired()
	 */
	@Override
	public boolean isError() {
		return false;
	}



	@SuppressWarnings("unchecked")
	@Override
	public ActualLine getNewDataLine() {
		try {
			return (ActualLine) clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public int getOption(int optionId) {
		switch (optionId) {
		case Options.OPT_GET_FIELD_COUNT:
			if (layout.getOption(Options.OPT_IS_CSV) == Options.YES) {
				return layout.getCsvFieldCount(getPreferredLayoutIdx(), getFullLine());
			}

			break;
		}
		return super.getOption(optionId);
	}



	@Override
	public void deleteColumns(int[] cols) {
		int idx = getPreferredLayoutIdx();
		if (layout.getOption(Options.OPT_IS_CSV) == Options.YES && idx >= 0) {
			if (layout.isBinCSV()) {
				BinaryCsvParser cp = new BinaryCsvParser(layout.getDelimiter());
				String font = layout.getFontName();
				setData(cp.formatFieldList(removeCols(cp.getFieldList(getData(), font), cols), font));
			} else {
				RecordDetail record = layout.getRecord(idx);
				ICsvLineParser csvParser = record.getCsvParser();
				if (csvParser != null) {
					ICsvDefinition csvDef = layout.getCsvDef(record, layout.getQuote());
					List<String> fieldList = removeCols(csvParser.getFieldList(getFullLine(), csvDef), cols);
					setData(csvParser.formatFieldList(fieldList, csvDef, getFieldsType(record)));
				}
			}
		}
	}

	private List<String> removeCols(List<String> fieldList, int[] cols) {
		for (int i = cols.length - 1; i>= 0; i--) {
			fieldList.remove(cols[i]);
		}
		return fieldList;
	}

	@Override
	public int insetColumns(int column, String[] colValues) {
		int idx = getPreferredLayoutIdx();
		int maxColCount = 0;
		if (layout.getOption(Options.OPT_IS_CSV) == Options.YES && idx >= 0) {
			RecordDetail record = layout.getRecord(idx);
			if (layout.isBinCSV()) {
				BinaryCsvParser cp = new BinaryCsvParser(layout.getDelimiter());
				String font = layout.getFontName();
				List<String> fieldList = addCols(cp.getFieldList(getData(), font), column, colValues);;
				setData(cp.formatFieldList(fieldList, font));
				maxColCount = Math.max(maxColCount, fieldList.size());
			} else {
				ICsvLineParser csvParser = record.getCsvParser();
				if (csvParser != null) {
					ICsvDefinition csvDef = layout.getCsvDef(record, layout.getQuote());
					List<String> fieldList = addCols(csvParser.getFieldList(getFullLine(), csvDef), column, colValues);;
					setData(csvParser.formatFieldList(fieldList, csvDef, getFieldsType(record)));
					maxColCount = Math.max(maxColCount, fieldList.size());
				}
			}
			for (int i = record.getFieldCount() + 1; i <= maxColCount; i++) {
				RecordDetail.FieldDetails f = new RecordDetail.FieldDetails("Column_" + i, "", Type.ftChar, 0, layout.getFontName(), 0, "");
				f.setPosOnly(i);
				//f.setRecord(record, i);
				record.addField(f);
			}
		}
		return maxColCount;
	}
	
	private List<String> addCols(List<String> fieldList, int column, String[] colValues) {
		if (column >= fieldList.size()) {			
			for (int i = fieldList.size(); i < column; i++) {
				fieldList.add("");
			}
			for (int i = 0; i < colValues.length; i++) {
				fieldList.add(colValues[i]);
			}
		} else {
			for (int i = 0; i < colValues.length; i++) {
				fieldList.add(column + i, colValues[i]);
			}
		}
		
		return fieldList;
	}
	
	private int[] getFieldsType(RecordDetail record) {
		int[] types = new int[record.getFieldCount()];
		
		for (int i = 0; i < types.length; i++) {
			types[i] = record.getFieldsNumericType(i);
		}
		
		return types;
	}
}