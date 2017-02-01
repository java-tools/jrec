package net.sf.RecordEditor.utils.swing.treeCombo;


import java.util.List;

import net.sf.RecordEditor.utils.swing.SwingUtils;

/**
 * Let the user select a Combo
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class TreeComboString extends TreeComboGeneric<String, TreeComboItemStr> {

	protected TreeComboString(String id, List<TreeComboItemStr> itms) {
		super(id, TreeComboItemStr.BLANK_ITEM, false, itms);
		super.setTextFieldWidth(12 * SwingUtils.CHAR_FIELD_WIDTH);
	}
	
	protected final void setValue(String value) {
		if (value == null) { 
			setSelectedItemSilently(TreeComboItemStr.BLANK_ITEM);
			return;
		};
		for (TreeComboItemStr itm : items) {
			if (value.equalsIgnoreCase(itm.getEnglish()) || value.equals(itm.key)) {
				setSelectedItemSilently(itm);
				return;
			}
		}
		super.setTextSilently(value);
	}
	

	public final void setTextValue(String value) {

		TreeComboItemStr saveItm = null;
		for (TreeComboItemStr itm : items) {
			if (value.equalsIgnoreCase(itm.getEnglish())) {
				setSelectedItemSilently(itm);
				return;
			}
			if ( value.equals(itm.key)) {
				saveItm = itm;
			}
		}
		if (saveItm != null) {
			setSelectedItemSilently(saveItm);
			return;
		}
		super.setTextSilently(value);
	}
	
	protected final String getTextValue() {
		TreeComboItemStr selectedItem = getSelectedItem();
		String text = super.getText();

		if (selectedItem != null 
		&& selectedItem != super.BLANK_ITEM
		&& selectedItem.getEnglish().equalsIgnoreCase(text)) {
			return selectedItem.getEnglish();
		}
		return text;
	}
	
	
	public final String getKeyValue() {
		TreeComboItemStr selectedItem = getSelectedItem();
		String text = super.getText();

		if (selectedItem != null 
		&& selectedItem != super.BLANK_ITEM
		&& selectedItem.getEnglish().equalsIgnoreCase(text)) {
			return selectedItem.key;
		}
		return text;
	}

	protected final void ensureValueExists(String englishText) {
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
		super.setSelectedItemSilently(TreeComboItemStr.BLANK_ITEM);
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