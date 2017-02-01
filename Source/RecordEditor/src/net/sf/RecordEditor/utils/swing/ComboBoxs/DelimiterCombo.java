package net.sf.RecordEditor.utils.swing.ComboBoxs;


import java.util.List;

import net.sf.JRecord.Common.Conversion;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboItemStr;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboString;

/**
 * Let the user select a Combo
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class DelimiterCombo extends TreeComboString {

//	public static final boolean NAME_COMPONENTS = "Y".equalsIgnoreCase(
//			Parameters.getString(Parameters.NAME_FIELDS));


   	public static DelimiterCombo NewDelimComboWithDefault() {
   		return new DelimiterCombo(BldOptionList.getDelimiterComboItems(true, true));
   	}


   	public static DelimiterCombo NewDelimCombo() {
   		return new DelimiterCombo(BldOptionList.getDelimiterComboItems(false, true));
   	}


   	public static DelimiterCombo NewTextDelimCombo() {
   		return new DelimiterCombo(BldOptionList.getDelimiterComboItems(false, false));
   	}


	protected DelimiterCombo(List<TreeComboItemStr> itms) {
		super("DelimiterCombo", itms);
	}
	
	public void setDelimiter(String value) {
		super.setValue(value);
	}
	
	public String getDelimiter() {
		TreeComboItemStr itm = super.getSelectedItemUseText();
		String t = itm == null ? super.getTextValue() : itm.getKey();
		
		return Conversion.encodeCharStr(t);
	}
	
	public final void ensureDelimitierExists(String englishText) {
		super.ensureValueExists(englishText);
	}
}