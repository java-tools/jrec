package net.sf.RecordEditor.utils.swing.editors;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextArea;

import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.common.ComboLikeObject;
import net.sf.RecordEditor.utils.swing.common.TextRender;

@SuppressWarnings("serial")
public class HtmlField extends ComboLikeObject implements TextRender {

	private JTable table;
	private int lastRow, lastCol;

	public HtmlField() {
		super(new JTextArea(), null, new JPopupMenu(), (JButton[]) null);
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.common.ComboLikeObject#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (table != null) {
			new HtmlDisplay(table, lastRow, lastCol);
		}
	}


	/* (non-Javadoc)
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		if (value == null) {
			this.setText("");
		} else {
			this.setText(value.toString());
		}
		SwingUtils.setTableCellBackGround(this, table, row, isSelected);

		this.table = table;
//		System.out.println(isSelected + " " + hasFocus);
		//if (hasFocus) {
			lastRow = row;
			lastCol = column;
		//}
		return this;
	}


}
