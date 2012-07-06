package net.sf.RecordEditor.utils.swing.ComboBoxs;


import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.swing.Combo.ComboStrOption;

@SuppressWarnings("serial")
public final class DelimitierCombo extends EnglishCombo<String> {

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
  	}


   	private DelimitierCombo(int start, int end) {
   		super(new ComboStrOption("", "", ""));
   		for (int i = start; i < end; i++) {
   			super.addItem(
   					new ComboStrOption(
   							FIELD_SEPARATOR_LIST_VALUES[i],
   							FIELD_SEPARATOR_FOREIGN[i],
   							Common.FIELD_SEPARATOR_LIST[i]
   					));
   		}
   	}

   	public static DelimitierCombo NewDelimComboWithDefault() {
   		return new DelimitierCombo(0, Common.FIELD_SEPARATOR_LIST.length);
   	}


   	public static DelimitierCombo NewDelimCombo() {
   		return new DelimitierCombo(1, Common.FIELD_SEPARATOR_LIST.length);
   	}


   	public static DelimitierCombo NewTextDelimCombo() {
   		return new DelimitierCombo(1, Common.FIELD_SEPARATOR_LIST.length - 6);
   	}
}
