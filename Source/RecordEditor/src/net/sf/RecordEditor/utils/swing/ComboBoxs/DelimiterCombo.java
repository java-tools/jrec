package net.sf.RecordEditor.utils.swing.ComboBoxs;


import java.util.List;

import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboGeneric;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboItemStr;

/**
 * Let the user select a Combo
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class DelimiterCombo extends TreeComboGeneric<String, TreeComboItemStr> {

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
		super("DelimiterCombo", TreeComboItemStr.BLANK_ITEM, false, itms);
		super.setTextFieldWidth(12 * SwingUtils.CHAR_FIELD_WIDTH);
	}
	
	public void setDelimiter(String value) {
		if (value == null) { 
			setSelectedItemSilently(TreeComboItemStr.BLANK_ITEM);
			return;
		};
		for (TreeComboItemStr itm : items) {
			if (value.equalsIgnoreCase(itm.getEnglish())) {
				setSelectedItemSilently(itm);
				return;
			}
		}
		super.setTextSilently(value);
	}
	
	public String getDelimiter() {
		TreeComboItemStr selectedItem = getSelectedItem();
		if (selectedItem != null && selectedItem != super.BLANK_ITEM) {
			return selectedItem.getEnglish();
		}
		return super.getText();
	}
	
	public final void ensureDelimitierExists(String englishText) {
		if (englishText == null || englishText.length() == 0) { return; }
		
		for (int i = 0; i < items.size(); i++) {
			if (englishText.equalsIgnoreCase(items.get(i).getKey())) {
				setSelectedItemSilently(items.get(i));
				return;
			}
		}
		
		TreeComboItemStr newEntry = new TreeComboItemStr(englishText, englishText, englishText);
		items.add(newEntry);
		setSelectedItemSilently(newEntry);
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.common.ComboLikeObject#setTextSilently(java.lang.String)
	 */
	@Override
	public void setTextSilently(String t) {
		if (t == null) {
			t = "";
		}
		for (int i = 0; i < items.size(); i++) {
			if (t.equalsIgnoreCase(items.get(i).getKey())) {
				super.setSelectedItemSilently(items.get(i));
				return;
			}
		}
		super.setTextSilently(t);
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.treeCombo.TreeComboGeneric#updateKeyForMap(java.lang.Object)
	 */
	@Override
	protected String updateKeyForMap(String k) {
		return k.toLowerCase();
	}
}