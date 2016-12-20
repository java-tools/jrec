package net.sf.RecordEditor.re.cobol;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.common.ComboLikeObject;


@SuppressWarnings("serial")
public class TblFieldCombo extends AbstractCellEditor implements
		TableCellRenderer, TableCellEditor {

	private final CblLoadData data;
	private final JTextField dfltField = new JTextField();
	private JComponent last;
	
	
	protected TblFieldCombo(CblLoadData data) {
		super();
		this.data = data;
	}

	@Override
	public Object getCellEditorValue() {
		String s;
		if (last  == dfltField) {
			s = dfltField.getText();
		} else {
			s = ((ComboLikeObject) last).getText();
		}
		
		return s;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		last = getComponent(row, value);
		return last;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		JComponent ret = getComponent(row, value);
		SwingUtils.setTableCellColors(ret, table, row, isSelected);
		return ret;
	}
	
	private JComponent getComponent(int recordIdx, Object value) {
		String val = value==null?"":value.toString();
		JComponent ret;
		ComboLikeObject jc = data.getFieldCombo(recordIdx);
		if (jc == null) {
			dfltField.setText(val);
			ret = dfltField;
		} else {
			jc.setText(val);
			ret = jc;
		}
		
		return ret;
	}

}
