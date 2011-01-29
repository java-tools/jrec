package net.sf.RecordEditor.utils.swing.Combo;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.RecordEditor.utils.swing.StandardRendor;

@SuppressWarnings("serial")
public class ColorRender  extends DefaultTableCellRenderer {

	private boolean isBold;
	
	private StandardRendor stdRender = null;
	private Font font = null;
	
	public ColorRender(boolean bold) {
		isBold = bold;
		if (bold) {
			Font currFont = super.getFont();
			font = new Font(currFont.getFamily(), Font.BOLD, currFont.getSize());
		} 
	}

	@Override
	public Component getTableCellRendererComponent(JTable tbl, Object value,
			boolean isSelected, boolean hasFocus, int row, int col) {
		Component ret;
		
		if (value instanceof ColorItem) {
			ColorItem val = (ColorItem) value;
			ret =  super.getTableCellRendererComponent(tbl, val.data, isSelected, hasFocus, row, col);
			ret.setBackground(val.color);
			if (font != null) {
				super.setFont(font);
			}
		} else {
			if (stdRender == null) {
				stdRender = new StandardRendor(isBold);
			}
			ret = stdRender.getTableCellRendererComponent(tbl, value, isSelected, hasFocus,
				row, col);
		}
		
		return ret;
	}

}
