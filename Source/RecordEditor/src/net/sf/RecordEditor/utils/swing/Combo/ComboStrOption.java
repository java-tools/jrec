package net.sf.RecordEditor.utils.swing.Combo;

public class ComboStrOption {
	public final String string;
	public final String key;
	
	public ComboStrOption(String keyValue, String str) {
		string = str;
		key = keyValue;
	}
	
	public String toString() {
		return string;
	}
}
