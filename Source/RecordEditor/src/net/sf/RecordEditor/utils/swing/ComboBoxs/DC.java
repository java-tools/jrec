package net.sf.RecordEditor.utils.swing.ComboBoxs;

import java.util.List;

import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboGeneric;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboItemStr;

@SuppressWarnings("serial")
public class DC extends TreeComboGeneric<String, TreeComboItemStr> {


   	public static DC NewDelimComboWithDefault() {
   		return new DC(BldOptionList.getDelimiterComboItems(true, true));
   	}


   	public static DC NewDelimCombo() {
   		return new DC(BldOptionList.getDelimiterComboItems(false, true));
   	}


   	public static DC NewTextDelimCombo() {
   		return new DC(BldOptionList.getDelimiterComboItems(false, false));
   	}


	protected DC(List<TreeComboItemStr> itms) {
		super(TreeComboItemStr.BLANK_ITEM, itms);
	}
}
