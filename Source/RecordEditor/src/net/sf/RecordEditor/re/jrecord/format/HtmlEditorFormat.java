package net.sf.RecordEditor.re.jrecord.format;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import net.sf.JRecord.Common.IFieldDetail;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.common.Editor4Combos;
import net.sf.RecordEditor.utils.swing.editors.HtmlField;

public class HtmlEditorFormat implements CellFormat {

	private HtmlField htmlField = new HtmlField();
	@Override
	public TableCellRenderer getTableCellRenderer(IFieldDetail fld) {
		return htmlField;
	}

	@Override
	public TableCellEditor getTableCellEditor(IFieldDetail fld) {
		return new Editor4Combos(new HtmlField());
	}

    /**
     * @see net.sf.RecordEditor.re.jrecord.format.CellFormat#getFieldHeight()
     */
    public int getFieldHeight() {
        return SwingUtils.COMBO_TABLE_ROW_HEIGHT;
    }

    /**
     * @see net.sf.RecordEditor.re.jrecord.format.CellFormat#getFieldWidth()
     */
    public int getFieldWidth() {
        return -1;
    }
}
