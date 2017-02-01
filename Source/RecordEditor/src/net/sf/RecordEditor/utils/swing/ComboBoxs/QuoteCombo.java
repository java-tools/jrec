package net.sf.RecordEditor.utils.swing.ComboBoxs;


import java.util.List;

import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboItemStr;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboString;

@SuppressWarnings("serial")
public final class QuoteCombo extends TreeComboString {



   	private QuoteCombo(List<TreeComboItemStr> itms) {
  		super("QuoteCombo", itms);
   	}
   	
	
	public String getQuote() {
		TreeComboItemStr itm = super.getSelectedItemUseText();
		return itm == null ? super.getTextValue() : itm.getKey();
	}

	
	public void setQuote(String quote) {
		super.setValue(quote);
	}


   	public static QuoteCombo newCombo() {
   		return new QuoteCombo(BldOptionList.getQuoteComboItems());
   	}
}
