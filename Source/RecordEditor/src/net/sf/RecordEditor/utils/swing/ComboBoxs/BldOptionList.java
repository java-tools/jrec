package net.sf.RecordEditor.utils.swing.ComboBoxs;

import java.util.ArrayList;
import java.util.List;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboItemStr;


/**
 * This class will create Csv-Delimiter and quote lists from either
 * a supplied array or Parameter values
 * 
 * @author Bruce Martin
 *
 */
public class BldOptionList {

  	private final static String[] FIELD_SEPARATOR_LIST_VALUES;
   	public final static String[] FIELD_SEPARATOR_FOREIGN = Common.FIELD_SEPARATOR_LIST.clone();

    static {
   		String[] l = Common.FIELD_SEPARATOR_LIST.clone();

   		l[0] = ",";
   		l[1] = "\t";
   		l[2] = " ";
   		FIELD_SEPARATOR_LIST_VALUES = l;
   		FIELD_SEPARATOR_FOREIGN[0] = LangConversion.convertComboItms("CsvDelim_Default", FIELD_SEPARATOR_FOREIGN[0]);
  		FIELD_SEPARATOR_FOREIGN[1] = LangConversion.convertComboItms("CsvDelim_Tab", FIELD_SEPARATOR_FOREIGN[1]);
  		FIELD_SEPARATOR_FOREIGN[2] = LangConversion.convertComboItms("CsvDelim_Space", FIELD_SEPARATOR_FOREIGN[2]);
  	};


  	public static List<TreeComboItemStr> getDelimiterComboItems(boolean includeDefault, boolean includeHexDelims) {
  		return getComboList(
  				FIELD_SEPARATOR_LIST_VALUES, FIELD_SEPARATOR_FOREIGN, 
  				Parameters.DELIMITER_CHARS, 
  				includeDefault, includeHexDelims); 
  	}

    private static List<TreeComboItemStr> getComboList(String[] values, String[] text, String paramCode, 
    		boolean includeDefault, boolean includeHexDelims ) {
    	List<ListItem> itemList = getList(values, text, paramCode, includeDefault, includeHexDelims);
    	List<TreeComboItemStr> ret = new ArrayList<TreeComboItemStr>(itemList.size());
    	
    	for (int i = 0; i < itemList.size(); i++) {
    		ListItem itm = itemList.get(i);
			ret.add(new TreeComboItemStr(itm.value, itm.text, itm.text));
    	}
    	
    	return ret;
  	}

    private static List<ListItem> getList(String[] values, String[] text, String paramCode, 
    		boolean includeDefault, boolean includeHexDelims ) {
    	String countStr = getParam(paramCode);
    	int count = values.length;
    	if (countStr != null && countStr.length() > 0) {
    		try {
				count = Math.min(0, Integer.parseInt(countStr));
			} catch (NumberFormatException e) {
			}
    	}

    	
    	ArrayList<ListItem> ret = new ArrayList<ListItem>(count);
    	ListItem itm;
    	String val, txt;
    	String pc = paramCode + '.';
    	String tc = paramCode + "text.";
    	for (int i = 0; i < count; i++) {
    		val = getParam(pc + i);
    		
    		if (val == null || val.length() == 0) {
    			val = values[i];
    			txt = text[i];
    		} else {
    			txt = getParam(tc + i, val);
    		}
    		itm = new ListItem(val, txt);
    		if ( (includeDefault || ! itm.isDefault)
    		&&   (includeHexDelims || itm.isText)) {
    			ret.add(itm);
    		}
    	}
    	
    	return ret;
    }

	public static String getParam(String paramCode, String defaultValue) {
		String value = getParam(paramCode);
		
		return value == null ? defaultValue : value;
	}

	/**
	 * @param paramCode
	 * @return
	 */
	public static String getParam(String paramCode) {
		String str =  Parameters.getString(paramCode + ".count");
		return str == null? null : str.trim();
	}

    
    private static class ListItem {
    	final String value, text;
    	final boolean isText, isDefault;
    	private ListItem(String value, String text) {
			super();
			this.value = value;
			this.text = text;
			
			this.isText = value.startsWith("x'") || value.startsWith("X'");
			this.isDefault =  "<default>".equalsIgnoreCase(text);
		}
    	
    	 
    }
}
