package net.sf.RecordEditor.utils.swing.treeCombo;

import java.util.List;

public class TreeComboItemStr extends TreeComboItemGeneric<String, TreeComboItemStr> {

	public static final TreeComboItemStr BLANK_ITEM = new TreeComboItemStr("", "", "");
	
	public TreeComboItemStr(String idx, String str, String englishString) {
		super(idx, str, englishString);
	}

	public TreeComboItemStr(String idx, String str, String englishString,
			boolean showParentName) {
		super(idx, str, englishString, showParentName);
	}

	public TreeComboItemStr(String idx, String str, String englishString,
			List<TreeComboItemStr> children) {
		super(idx, str, englishString, children);
	}

}
