package net.sf.RecordEditor.utils.swing.Combo;

import java.awt.Color;

public class ColorItem {

	public Color color;
	public Object data;
	
	@Override
	public String toString() {
		if (data == null) {
			return null;
		}
		return data.toString();
	}
	
	
}
