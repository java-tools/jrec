/**
 *
 */
package net.sf.RecordEditor.po.display;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.po.def.PoField;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.swing.ITableColoringAgent;

/**
 * @author mum
 *
 */
public class TableCellColoringAgent implements ITableColoringAgent {

	private static Color brown = new Color(102, 43, 0);
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.ITableColoringAgent#setTableCellColors(java.awt.Component, javax.swing.JTable, int, boolean)
	 */
	@Override
	public void setTableCellColors(Component component, JTable table, int row, int col,
			boolean isSelected) {
		Object mdl = table.getModel();
		if (mdl instanceof FileView) {
			@SuppressWarnings("rawtypes")
			AbstractLine l = ((FileView) mdl).getLine(row);

			Object o = l.getField(0, PoField.obsolete.fieldIdx);
			if (o != null && "Y".equals(o.toString())) {
				component.setForeground(Color.gray);
			} else {
				o = l.getField(0, PoField.fuzzy.fieldIdx);
				if (o != null && "Y".equals(o.toString())) {
					component.setForeground(brown);
				}
			}
		}
	}


}
