package net.sf.RecordEditor.utils.swing.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.params.Parameters;

/**
 * Retrieve standard Csv-Field-Separators and Csv-Quotes
 * 
 * @author Bruce Martin
 *
 */
public class CsvTextItem {
	
  	public final static String[] FIELD_SEPARATOR_LIST_VALUES  = Common.FIELD_SEPARATOR_LIST_VALUES.clone();
   	public final static String[] FIELD_SEPARATOR_TEXT = Common.FIELD_SEPARATOR_LIST.clone();

   	public final static String[] CSV_QUOTE_LIST = Common.QUOTE_LIST.clone();
  	public final static String[] CSV_QUOTE_VALUES = Common.QUOTE_VALUES.clone();
	
	public static final CsvList DELIMITER
			= new CsvList(FIELD_SEPARATOR_LIST_VALUES, FIELD_SEPARATOR_TEXT, Parameters.CSV_DELIMITER_CHARS);
	public static final CsvList QUOTE
			= new CsvList(CSV_QUOTE_VALUES, CSV_QUOTE_LIST, Parameters.CSV_QUOTE_CHARS);

	public final String value, text;
  	public final boolean isText, isDefault, isNone;
   	
	public CsvTextItem(String value, String text) {
		super();
		this.value = value;
		this.text = text;
		
		this.isText = ! (value.startsWith("x'") || value.startsWith("X'"));
		this.isDefault =  "<default>".equalsIgnoreCase(text);
		this.isNone = "<none>".equalsIgnoreCase(text);
	}
	
 	/**
	 * @return the text
	 */
	public final String getText() {
		return text == null || text.length() == 0 ? value : text;
	}

//  			
//   	public static List<CsvTextItem> getCsvDelimiterList(boolean includeDefault, boolean includeHexDelims) {
//   		return getList(
//   					FIELD_SEPARATOR_LIST_VALUES, FIELD_SEPARATOR_TEXT,
//   					Parameters.CSV_DELIMITER_CHARS, includeDefault, includeHexDelims);
//   	}
//
//   	public static List<CsvTextItem> getDefaultCsvDelimiterList(boolean includeDefault, boolean includeHexDelims) {
//   		return getDefaultList(
//   					FIELD_SEPARATOR_LIST_VALUES, FIELD_SEPARATOR_TEXT,
//   					includeDefault, includeHexDelims);
//   	}
//   	
//		
//	public static List<CsvTextItem> getCsvQuoteList(boolean includeDefault) {
//		return getList(
//					CSV_QUOTE_VALUES, CSV_QUOTE_LIST,
//					Parameters.CSV_DELIMITER_CHARS, includeDefault, false);
//	}
//
//
//   	public static List<CsvTextItem> getDefaultCsvQuoteList(boolean includeDefault) {
//   		return getDefaultList(
//   					CSV_QUOTE_VALUES, CSV_QUOTE_LIST,
//   					includeDefault, false);
//   	}
	
    public static List<CsvTextItem> getList(String[] values, String[] text, String paramCode, 
    		boolean includeDefault, boolean includeHexDelims ) {
    	String countStr = getParam(paramCode + ".count");
    	int count = values.length;
    	if (countStr != null && countStr.length() > 0) {
    		try {
				count = Math.max(0, Integer.parseInt(countStr));
			} catch (NumberFormatException e) {
			}
    	}

    	ArrayList<CsvTextItem> ret = new ArrayList<CsvTextItem>(count);
    	CsvTextItem itm;
    	String val, txt;
    	String pc = paramCode + '.';
    	String tc = paramCode + "text.";
    	for (int i = 0; i < count; i++) {
    		val = getParam(pc + i);
    		
    		if (val == null || val.length() == 0) {
    			val = "";
    			txt = "";
    			if (i < values.length) {
    				val = values[i];
    				txt = text[i];
    			}
    		} else {
    			txt = getParam(tc + i, val);
    		}
    		itm = new CsvTextItem(val, txt);
    		if ( (includeDefault || ! (itm.isDefault || itm.isNone))
    		&&   (includeHexDelims || itm.isText)) {
    			ret.add(itm);
    		}
    	}
    	
    	return ret;
    }
//    
//    public static void updateDelimParams(Properties properties, List<CsvTextItem> items) {
//    	updateParams(properties, Parameters.CSV_DELIMITER_CHARS, items, FIELD_SEPARATOR_LIST_VALUES, FIELD_SEPARATOR_TEXT);
//    }
    
    public static void updateParams(Properties properties, String paramCode, List<CsvTextItem> items, String[] values, String[] text) {
    	String val = null;
    	String txt;
    	int count = Math.min(items.size(), values.length);
       	String pc = paramCode + '.';
    	String tc = paramCode + "text.";

//    	try {
    		//Parameters.setSavePropertyChanges(false);
			if (items.size() != values.length) {
				val = Integer.toString(items.size());
			}
			updateProperties(properties, pc + "count", val);
			
			for (int i = 0; i < count; i++) {
				CsvTextItem textItems = items.get(i);
				txt = null; 
				String textValue = textItems.getText();
				if (textItems.value == null || (textItems.value.equals(values[i]) && text[i].equals(textValue))) {
					val = null;
				} else {
					val = textItems.value;
					if (! textItems.value.equals(textValue)) {
						txt = textValue;
					}
				}
				updateProperties(properties, pc + i, val);
				updateProperties(properties, tc + i, txt);
			}
			for (int i = count; i < items.size(); i++) {
				CsvTextItem textItems = items.get(i);
				String textValue = textItems.getText();
				txt = null; 
				val = textItems.value;
				if (! textItems.value.equals(textValue)) {
					txt = textValue;
				}
				updateProperties(properties, pc + i, val);
				updateProperties(properties, tc + i, txt);
			}
//		} finally {
//			Parameters.setSavePropertyChanges(true);
//		}
    }
    
    private static void updateProperties(Properties properties, String key, String value) {
    	if (value == null) {
    		properties.remove(key);
    	} else {
    		properties.setProperty(key, value);
    	}
    }

    public static List<CsvTextItem> getDefaultList(String[] values, String[] text, 
    		boolean includeDefault, boolean includeHexDelims ) {
 
    	int count = values.length;
 
    	ArrayList<CsvTextItem> ret = new ArrayList<CsvTextItem>(count);
    	CsvTextItem itm;

    	for (int i = 0; i < count; i++) {
    		itm = new CsvTextItem(values[i], text[i]);
    		if ( (includeDefault || ! (itm.isDefault || itm.isNone))
    		&&   (includeHexDelims || itm.isText)) {
    			ret.add(itm);
    		}
    	}
    	
    	return ret;
    }

    private static String getParam(String paramCode, String defaultValue) {
		String value = getParam(paramCode);
		
		return value == null ? defaultValue : value;
	}

	/**
	 * @param paramCode
	 * @return
	 */
    private static String getParam(String paramCode) {
		String str =  Parameters.getString(paramCode);
		return str == null? null : str.trim();
	}

    
    /**
     * Class to retrieve / Update a list of items.
     * 
     * @author Bruce Martin
     *
     */
    public static class CsvList {
    	private final String[] values, text;
    	private final String paramCode;
    	
    	CsvList(String[] values, String[] text, String paramCode) {
    		this.values = values;
    		this.text = text;
    		this.paramCode = paramCode;
    	}
    	
    	public List<CsvTextItem> getCsvList(boolean includeDefault, boolean includeHexDelims) {
    		return getList( values, text, paramCode, includeDefault, includeHexDelims );
    	}
    	
    	public List<CsvTextItem> getDefaultCsvList(boolean includeDefault, boolean includeHexDelims) {
    		return getDefaultList(values, text, includeDefault, includeHexDelims);
    	}
	
    	public void updateList(Properties properties, List<CsvTextItem> items) {
        	updateParams(properties, paramCode, items, values, text);
    	}
    }
    
}
