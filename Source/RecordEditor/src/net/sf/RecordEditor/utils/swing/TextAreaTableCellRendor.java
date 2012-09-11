package net.sf.RecordEditor.utils.swing;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

import net.sf.JRecord.Common.Conversion;

/**
 * Table Cell Render using a Text Area
 *
 * @author Bruce Martin
 *
 */
public class TextAreaTableCellRendor  implements TableCellRenderer {

	private JTextArea area = new JTextArea();

	private JEditorPane editPane = new JEditorPane("text/html", "");
//	private JScrollPane scrollArea = new JScrollPane(area);
//	private JScrollPane scrollEdit = new JScrollPane(editPane);

	public TextAreaTableCellRendor() {
		area.setBorder(BorderFactory.createEmptyBorder());
		editPane.setBorder(BorderFactory.createEmptyBorder());
	}


	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		JComponent ret;
		String s = "";
		if (value != null) {
			s = value.toString();
		}
		if (Conversion.isHtml(s)) {
			ret = editPane;
			editPane.setText(s);
		} else {
			ret = area;
			area.setText(s);

			SwingUtils.setTableCellColors(area, table, row, isSelected);
		}
		return ret;
	}

}
