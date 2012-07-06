package net.sf.RecordEditor.utils.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import net.sf.JRecord.Common.Conversion;
import net.sf.RecordEditor.utils.lang.LangConversion;

@SuppressWarnings("serial")
public class HexTwoLineFieldAlt extends JTable implements AbstractHexDisplay {

	private byte[] byteArray;
	private String font = "";
	private TblModel model = null;
	private int cellWidth;
	private int lastCellSetup = 0;


	public HexTwoLineFieldAlt(String fontname) {
		font = fontname;
		this.setAutoResizeMode(AUTO_RESIZE_OFF);
		//this.setBorder(BorderFactory.createEmptyBorder());
		//this.setBorder(BorderFactory.createLineBorder(Color.lightGray));
		setIntercellSpacing(new Dimension(0, 0));
		setDefaultRenderer(
				Object.class,
				(new TextFieldRender())
					.setColumnBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)
				)
		);
	}

	@Override
	public byte[] getBytes(byte[] oldValue) {
		return byteArray;
	}

	@Override
	public JComponent getComponent() {
		return this;
	}

	@Override
	public boolean isTwoBytesPerCharacter() {
		return true;
	}

	@Override
	public void setHex(byte[] bytes) {
		TableColumnModel columns = getColumnModel();

		byteArray = bytes;

		if (model == null) {
			model = new TblModel();
			this.setModel(model);
			TableCellRenderer r = getCellRenderer(1, 0);

			Component c = r.getTableCellRendererComponent(
					this, model.getValueAt(1, 0), false, false, 1, 0);

			cellWidth =  c.getPreferredSize().width+3;
		}

		for (int i = columns.getColumnCount() - 1; i >= lastCellSetup; --i) {
			columns.getColumn(i).setPreferredWidth(cellWidth);
		}

		lastCellSetup = columns.getColumnCount() - 1;
	}

	/* (non-Javadoc)
	 * @see javax.swing.JTable#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int row, int column) {
		return true;
	}

	@Override
	public void setHex(String text, String hex) {
		// TODO Auto-generated method stub

	}

	public AbstractHexDisplay clone() {
		HexTwoLineFieldAlt ret = new HexTwoLineFieldAlt(font);
		ret.setHex(byteArray);
		return ret;
	}

	public class TblModel extends AbstractTableModel {

		/* (non-Javadoc)
		 * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object, int, int)
		 */
		@Override
		public void setValueAt(Object value, int rowIndex, int columnIndex) {
			String msg = tryToSetValueAt(value, rowIndex, columnIndex);
			while (msg != null) {
				value= JOptionPane.showInputDialog(null,
		                msg,
		                LangConversion.convert(LangConversion.ST_MESSAGE, "Invalid Hex"),
		                JOptionPane.ERROR_MESSAGE,
		                null, null, value);
				if (value == null) {
					break;
				}
				msg = tryToSetValueAt(value, rowIndex, columnIndex);
			}
		}

		public String tryToSetValueAt(Object value, int rowIndex, int columnIndex) {
			String ret = null;
			try {
				if (rowIndex == 0) {
					byte[] tmp = Conversion.getBytes(value.toString(), font);
					byteArray[columnIndex] = tmp[0];
				} else {
					byteArray[columnIndex] = Conversion.long2byte(Integer.parseInt(value.toString(), 16));
				}
				fireTableCellUpdated(1 - rowIndex, columnIndex);
			} catch (Exception e) {
				ret = LangConversion.convert(
						"Error Converting Hex; {0} value={1} Message:",
						new Object[] {rowIndex, value});

//						"Error Converting Hex; " + rowIndex
//						+ " value=" + value + " Message: "  +e.getMessage();
			}
			return ret;
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getColumnCount()
		 */
		@Override
		public int getColumnCount() {
			return byteArray.length;
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getRowCount()
		 */
		@Override
		public int getRowCount() {
			return 2;
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		@Override
		public Object getValueAt(int row, int col) {

			if (row== 0) {
				return Conversion.getString(byteArray, col, col+1, font);
			} else {
				return Conversion.getDecimal(byteArray, col, col+1);
			}
		}

	}
}
