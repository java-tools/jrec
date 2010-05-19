package net.sf.RecordEditor.utils.swing.Combo;

public class ComboOption {
	public final String string;
	public final int index;
	
	public ComboOption(int idx,String str) {
		string = str;
		index = idx;
	}
	
	public String toString() {
		return string;
	}
}
