/*
 * @Author Bruce Martin
 * Created on 23/03/2007
 *
 * Purpose:
 */
package net.sf.RecordEditor.utils.swing;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.JTextComponent;

import net.sf.JRecord.Common.Conversion;

/**
 * Mono spaced text field table rendor
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class HexOneLineRender extends JTextField implements TableCellRenderer, AbstractHexDisplay  {

    /**
     * Mono spaced text field table rendor
     */
    public HexOneLineRender() {
        super();

        this.setFont(SwingUtils.getMonoSpacedFont());
		this.setBorder(BorderFactory.createEmptyBorder());
    }


    /**
     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
     */
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        String val = "";
        if (value != null && value instanceof byte[]) {
        	byte[] bytes = (byte[]) value;
            val = Conversion.getDecimal(bytes, 0, bytes.length);
        }
        setText(val);

        if (isSelected) {
            super.setForeground(table.getSelectionForeground());
            super.setBackground(table.getSelectionBackground());
        } else {
            super.setForeground(table.getForeground());
            super.setBackground(table.getBackground());
        }

        return this;
    }


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.AbstractHexDisplay#getBytes(byte[])
	 */
	@Override
	public byte[] getBytes(byte[] oldValue) {
		String tmp = "";
		String line = this.getText();
		byte[] ret = new byte[(line.length()) / 2];

		for (int i = 0; i < line.length(); i += 2) {
			try {
				tmp = line.substring(i, i+2);
				ret[i/2] = Conversion.long2byte(Integer.parseInt(tmp, 16));
			} catch (Exception e) {
				throw new RuntimeException("Error Converting Hex; byteNumber=" + (i/2) 
						+ " value=" + tmp + " Message: "  +e.getMessage());
			}
		}
		return ret;
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.AbstractHexDisplay#setHex(byte[])
	 */
	@Override
	public void setHex(byte[] bytes) {
		this.setText(Conversion.getDecimal(bytes, 0, bytes.length));
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.AbstractHexDisplay#setHex(java.lang.String, java.lang.String)
	 */
	@Override
	public void setHex(String text, String hex) {
		this.setText(hex);
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.AbstractHexDisplay#getComponent()
	 */
	@Override
	public JTextComponent getComponent() {
		// TODO Auto-generated method stub
		return this;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public AbstractHexDisplay clone() {
		// TODO Auto-generated method stub
		 try {super.clone(); } catch (Exception e) {
		 }
		 HexOneLineRender ret = new HexOneLineRender();
		 ret.setText(this.getText());
		 return ret;
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.AbstractHexDisplay#isTwoBytesPerCharacter()
	 */
	@Override
	public boolean isTwoBytesPerCharacter() {
		return true;
	}
}
