package net.sf.RecordEditor.re.jrecord.format;

import java.awt.Font;

import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.IFieldDetail;
import net.sf.RecordEditor.utils.swing.StandardRendor;

public class BoldFormat implements CellFormat {

	private StandardRendor boldRendor = new StandardRendor(true);

	@Override
	public int getFieldHeight() {
		return Constants.NULL_INTEGER;
	}

	@Override
	public int getFieldWidth() {
		return Constants.NULL_INTEGER;
	}

	@Override
	public TableCellEditor getTableCellEditor(IFieldDetail fld) {
		JTextField txtFld = new JTextField();
		Font font = txtFld.getFont();
		txtFld.setFont(
				new Font(font.getFamily(), Font.BOLD, font.getSize())
		);
		return new DefaultCellEditor(txtFld);
	}

	@Override
	public TableCellRenderer getTableCellRenderer(IFieldDetail fld) {
		return boldRendor;
	}

}
