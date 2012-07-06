package net.sf.RecordEditor.utils.swing.ComboBoxs;


import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.swing.Combo.ComboStrOption1;

@SuppressWarnings("serial")
public final class QuoteCombo extends EnglishCombo<String> {

 	public final static String QUOTE_FOREIGN[] = {
 		LangConversion.convertComboItms("Csv_Quote", Common.QUOTE_LIST[0]),
 		LangConversion.convertComboItms("Csv_Quote", Common.QUOTE_LIST[1]),
 		Common.QUOTE_LIST[2], Common.QUOTE_LIST[3],  Common.QUOTE_LIST[4]
	};


   	private QuoteCombo() {
  		super(new ComboStrOption1("", "", ""));

   		for (int i = 0; i < QUOTE_FOREIGN.length; i++) {
   			super.addItem(
   					new ComboStrOption1(
   							Common.QUOTE_VALUES[i],
   							QUOTE_FOREIGN[i],
   							Common.QUOTE_LIST[i]));
   		}
   	}


   	public static QuoteCombo NewCombo() {
   		return new QuoteCombo();
   	}


}
