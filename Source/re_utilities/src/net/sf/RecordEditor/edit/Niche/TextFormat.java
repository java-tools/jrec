package net.sf.RecordEditor.edit.Niche;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.RecordEditor.re.jrecord.format.CellFormat;

public class TextFormat implements CellFormat {
	public static final int NICHE_POLICY = 1;
	public static final int NICHE_SEQPOLICY = 2;
	private int format = 0;
	
	private txtRender render =new txtRender();
	
	public TextFormat(int fieldFormat) {
		format = fieldFormat;
	}
	
	@Override
	public final int getFieldHeight() {
		return 0;
	}

	@Override
	public final int getFieldWidth() {
		return 0;
	}

	@Override
	public final TableCellEditor getTableCellEditor(FieldDetail fld) {
		return new txtRender();
	}

	@Override
	public final TableCellRenderer getTableCellRenderer(FieldDetail fld) {
		return render;
	}
	
	protected String convertValue(int fldFormat, Object value) {
		String ret;
		if (value == null || ("".equals(value))) {
			ret="";
			//return "";
		} else {
			String s = value.toString();
			if ((fldFormat == NICHE_POLICY) && s.length() < 9) {
				ret =  s + "0";
			} else if (fldFormat == NICHE_POLICY) {
				ret = s.substring(2) + s.substring(0, 1);
			} if (s.length() < 9) {
/*				System.out.print(s + " " + s.length() + " ");
				System.out.print(s.substring(s.length() - 1, s.length() - 1) + " ! ");
				System.out.print( "0000000000".substring(s.length()) + " !1 ");
				System.out.print( s.substring(1, s.length() - 1) + " !; ");*/
				ret = s.substring(s.length() - 1, s.length())
					+ "000000000".substring(s.length())
					+ s.substring(0, s.length() - 1);
			} else {
				ret =  s.substring(s.length() - 1, s.length()) + s.substring(0, s.length() - 1);
			}
		}
		System.out.println("==> " + fldFormat + " " + value + " | " + ret);
		return ret;
	}

	private class txtRender extends AbstractCellEditor implements TableCellRenderer , TableCellEditor {
		

		private JTextField fld = new JTextField();

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
			fld.setText(convertValue(format, value));
			fld.setBorder(null);
			return fld;
		}

		@Override
	    public Component getTableCellEditorComponent(JTable table, Object value,
	            boolean isSelected, int row, int column) {
			
			fld.setBorder(null);
			fld.setText(convertValue(format, value));
			return fld;
		}

		@Override
		public Object getCellEditorValue() {
			
			return convertValue(3 - format, fld.getText());
		}

	}
}
