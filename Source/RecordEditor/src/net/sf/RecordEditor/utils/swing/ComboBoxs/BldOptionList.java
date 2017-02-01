package net.sf.RecordEditor.utils.swing.ComboBoxs;

import java.util.ArrayList;
import java.util.List;

import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.swing.common.CsvTextItem;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboItemStr;


/**
 * This class will create Csv-Delimiter and quote lists from either
 * a supplied array or Parameter values
 * 
 * @author Bruce Martin
 *
 */
public class BldOptionList {

  	private final static String[] FIELD_SEPARATOR_LIST_VALUES = CsvTextItem.FIELD_SEPARATOR_LIST_VALUES.clone();
   	public final static String[] FIELD_SEPARATOR_FOREIGN = CsvTextItem.FIELD_SEPARATOR_TEXT.clone();

  	private final static String[] QUOTE_VALUES = CsvTextItem.CSV_QUOTE_VALUES.clone();
   	private final static String[] QUOTE_TEXT   = CsvTextItem.CSV_QUOTE_LIST.clone();


    static {    	
   		FIELD_SEPARATOR_FOREIGN[0] = LangConversion.convertComboItms("CsvDelim_Default", FIELD_SEPARATOR_FOREIGN[0]);
  		FIELD_SEPARATOR_FOREIGN[1] = LangConversion.convertComboItms("CsvDelim_Tab", FIELD_SEPARATOR_FOREIGN[1]);
  		FIELD_SEPARATOR_FOREIGN[2] = LangConversion.convertComboItms("CsvDelim_Space", FIELD_SEPARATOR_FOREIGN[2]);
  	};


  	public static List<TreeComboItemStr> getDelimiterComboItems(boolean includeDefault, boolean includeHexDelims) {
  		return getComboList(
  				FIELD_SEPARATOR_LIST_VALUES, FIELD_SEPARATOR_FOREIGN, 
  				Parameters.CSV_DELIMITER_CHARS, 
  				includeDefault, includeHexDelims); 
  	}
  	

  	public static List<TreeComboItemStr> getQuoteComboItems() {
  		return getComboList(
  				QUOTE_VALUES, QUOTE_TEXT, 
  				Parameters.CSV_QUOTE_CHARS, 
  				true, true); 
  	}


    private static List<TreeComboItemStr> getComboList(String[] values, String[] text, String paramCode, 
    		boolean includeDefault, boolean includeHexDelims ) {
    	List<CsvTextItem> itemList = CsvTextItem.getList(values, text, paramCode, includeDefault, includeHexDelims);
    	List<TreeComboItemStr> ret = new ArrayList<TreeComboItemStr>(itemList.size());
    	
    	for (int i = 0; i < itemList.size(); i++) {
    		CsvTextItem itm = itemList.get(i);
			ret.add(new TreeComboItemStr(itm.value, itm.text, itm.text));
    	}
    	
    	return ret;
  	}
}
