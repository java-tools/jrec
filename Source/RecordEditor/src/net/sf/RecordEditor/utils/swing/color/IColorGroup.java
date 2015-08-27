package net.sf.RecordEditor.utils.swing.color;

import java.awt.Color;

public interface IColorGroup {

	public static final int FIELDS = 0;
	public static final int SPECIAL = 1;
	public static final int RECORDS = 2;
	
	public static final int SPECIAL_FIELD_SEPERATOR = 0;
	
	
	public abstract int size();
	
	public abstract int getGroupType();
	
	public abstract Color getForegroundColor(int idx);
	
	public abstract Color getBackgroundColor(int idx);
}
