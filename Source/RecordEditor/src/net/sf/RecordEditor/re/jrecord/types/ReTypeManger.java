/*
 * @Author Bruce Martin
 * Created on 13/03/2007
 *
 * Purpose:
 */
package net.sf.RecordEditor.re.jrecord.types;

import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Types.Type;
import net.sf.JRecord.Types.TypeChar;
import net.sf.JRecord.Types.TypeManager;
import net.sf.JRecord.Types.TypeNum;
import net.sf.RecordEditor.re.jrecord.format.BoldFormat;
import net.sf.RecordEditor.re.jrecord.format.CellFormat;
import net.sf.RecordEditor.re.jrecord.format.CheckBoxBooleanFormat;
import net.sf.RecordEditor.re.jrecord.format.CheckBoxFldFormat;
import net.sf.RecordEditor.re.jrecord.format.CheckBoxFormat;
import net.sf.RecordEditor.re.jrecord.format.ColorFormat;
import net.sf.RecordEditor.re.jrecord.format.ComboFormat;
import net.sf.RecordEditor.re.jrecord.format.CsvArrayFormat;
import net.sf.RecordEditor.re.jrecord.format.DateFormat;
import net.sf.RecordEditor.re.jrecord.format.MultiLineFormat;

/**
 *
 *
 * @author Bruce Martin
 *
 */
public class ReTypeManger extends TypeManager {

    public static final int FORMAT_SYSTEM_ENTRIES = 40;

    private static final int INVALID_FORMAT_INDEX = FORMAT_SYSTEM_ENTRIES - 1;

    private CellFormat[] typesFormat;

    private CellFormat[] formats;


    private int userFormatSize;

    private static String dateFormat = null;

    private static ReTypeManger systemTypeManager = null;

    private static DateFormat standardDateFormat = null;


    /**
     *
     */
    public ReTypeManger() {
        this(true, Type.DEFAULT_USER_RANGE_SIZE, CellFormat.DEFAULT_USER_RANGE_SIZE);
    }

    /**
     * @param addSystemTypes wether to add standard Types
     * @param numberOfUserTypes Number of user types to allow for
     * @param numberOfUserFormats number of user cell formats to allow for
     */
    public ReTypeManger(final boolean addSystemTypes, final int numberOfUserTypes,
            final int numberOfUserFormats) {
        super(addSystemTypes, numberOfUserTypes);

        int i;

        userFormatSize = numberOfUserFormats;

        typesFormat = new CellFormat[super.getNumberOfTypes()];
        formats  = new CellFormat[FORMAT_SYSTEM_ENTRIES + userFormatSize];

        for (i = 0; i < typesFormat.length; i++) {
            typesFormat[i] = null;
        }

        for (i = 0; i < formats.length; i++) {
        	formats[i] = null;
        }

        if (addSystemTypes) {
            Type charTypes    = new TypeChar(true);
            TypeNum zeroPaded = new TypeNum(Type.ftNumZeroPadded);

            typesFormat[Type.ftCsvArray]      = new CsvArrayFormat();
            typesFormat[Type.ftMultiLineEdit] = new MultiLineFormat();

            try {
                super.registerType(Type.ftDate, new TypeDateWrapper(charTypes, null));
                super.registerType(Type.ftDateDMY, new TypeDateWrapper(zeroPaded, "ddMMyy"));
                super.registerType(Type.ftDateDMYY, new TypeDateWrapper(zeroPaded, "ddMMyyyy"));
                super.registerType(Type.ftDateYMD, new TypeDateWrapper(zeroPaded, "yyMMdd"));
                super.registerType(Type.ftDateYYMD, new TypeDateWrapper(zeroPaded, "yyyyMMdd"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (dateFormat == null) {
                typesFormat[Type.ftDate]      = new DateFormat(true, null);
                typesFormat[Type.ftDateDMY]   = new DateFormat(true, "ddMMyy");
                typesFormat[Type.ftDateDMYY]  = new DateFormat(true, "ddMMyyyy");
                typesFormat[Type.ftDateYMD]   = new DateFormat(true, "yyMMdd");
                typesFormat[Type.ftDateYYMD]  = new DateFormat(true, "yyyyMMdd");
            } else {
                //standardDateFormat = new DateFormat(true, dateFormat);
                typesFormat[Type.ftDate]      = standardDateFormat;
                typesFormat[Type.ftDateDMY]   = standardDateFormat;
                typesFormat[Type.ftDateDMYY]  = standardDateFormat;
                typesFormat[Type.ftDateYMD]   = standardDateFormat;
                typesFormat[Type.ftDateYYMD]  = standardDateFormat;
            }

            typesFormat[Type.ftCheckBoxTrue]  = new CheckBoxFormat("True", "", false);
            typesFormat[Type.ftCheckBoxYN]    = new CheckBoxFormat("Y", "N", true);
            typesFormat[Type.ftCheckBoxTF]    = new CheckBoxFormat("T", "F", true);
            typesFormat[Type.ftCheckBoxBoolean]    = new CheckBoxBooleanFormat();

            formats[CellFormat.FMT_CHECKBOX]  = new CheckBoxFldFormat();
            formats[CellFormat.FMT_DATE]      = new DateFormat(false, null);
            formats[CellFormat.FMT_DATE_DMYY] = new DateFormat(false, "ddMMyyyy");
            formats[CellFormat.FMT_DATE_YYMD] = new DateFormat(false, "yyyyMMdd");
            formats[CellFormat.FMT_COMBO]     = new ComboFormat();
            formats[CellFormat.FMT_BOLD]      = new BoldFormat();
            formats[CellFormat.FMT_COLOR]     = new ColorFormat();
        }

    }

    /**
     * register a type definition
     *
     * @param typeId type being defined
     * @param typeDef type definition
     *
     * @throws RecordException any error that occurs
     */
    public void registerType(int typeId, Type typeDef) throws RecordException {
    	registerType(typeId, typeDef, null);
    }




    /**
     * register a type & format
     *
     * @param typeId type identifier of the type being top
     * @param typeDef type being to defined
     * @param format format of the type
     *
     * @throws RecordException any error that occurs
     */
    public void registerType(int typeId, Type typeDef, CellFormat format)
    throws RecordException {
        int idx = super.getIndex(typeId);

        super.registerType(typeId, typeDef);

        typesFormat[idx] = format;
    }



    /**
     * Registers a new format to the system
     *
     * @param formatId identifier of the new format being registered
     * @param format format being registered
     *
     * @throws RecordException an error that occurs
     */
    public void registerFormat(int formatId, CellFormat format) throws RecordException {
        int idx = getFormatIndex(formatId);

        if (idx == INVALID_INDEX) {
            throw new RecordException("Invalid Index Supplied " + formatId
                    + " Should be between " + CellFormat.USER_RANGE_START
                    + " and " + (CellFormat.USER_RANGE_START + userFormatSize));
        }

        formats[idx] = format;
    }


    /**
     * Get the Table format associated with a type
     *
     * @param typeId type id to get the table format details for
     *
     * @return Table format details
     */
    public CellFormat getTypeFormat(int typeId) {
        return typesFormat[getIndex(typeId)];
    }


    /**
     * Get system format
     *
     * @param formatId format identifier
     *
     * @return requested format
     */
    public CellFormat getFormat(int formatId) {
        return formats[getFormatIndex(formatId)];
    }


    /**
     * Adjusts the index
     *
     * @param index index supplied user
     *
     * @return adjusted index
     */
    public final int getFormatIndex(int index) {
        int idx = INVALID_FORMAT_INDEX;

        if (index >= 0 && index < FORMAT_SYSTEM_ENTRIES) {
            idx = index;
        } else if (index >= CellFormat.USER_RANGE_START
               &&  index <  CellFormat.USER_RANGE_START + userFormatSize) {
            idx =  index -  CellFormat.USER_RANGE_START + SYSTEM_ENTRIES;
        }

        return idx;
    }



    /**
     * @param dateFormatStr The date Format to use.
     */
    public static void setDateFormat(String dateFormatStr) {
        dateFormat = dateFormatStr;

        standardDateFormat = null;
        if (dateFormat != null) {
            standardDateFormat = new DateFormat(true, dateFormat);
        }
    }

    /**
     * Get The Format for a a formast string
     * @param dateFormatStr date format string
     * @return requested format
     */
    public static final DateFormat getFormatFor(String dateFormatStr) {
        DateFormat ret = standardDateFormat;

        if (ret == null) {
            ret = new DateFormat(true, dateFormatStr);
        }
        return ret;
    }


    /**
     * Get the standard system Type Manager
     *
     * @return standard system Type Manager
     */
	public static ReTypeManger getInstance() {

	    if (systemTypeManager == null) {
	        systemTypeManager = new ReTypeManger();
	        TypeManager.setSystemTypeManager(systemTypeManager);
	    }
		return systemTypeManager;
	}

	/**
	 * @return the userFormatSize
	 */
	public int getUserFormatSize() {
		return userFormatSize;
	}

	
    public final int getNumberOfFormats() {
	     return formats.length;
	}

}
