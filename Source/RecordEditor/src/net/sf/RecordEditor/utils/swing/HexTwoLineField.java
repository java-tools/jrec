/*
 * @Author Bruce Martin
 * Created on 22/03/2007
 *
 * Purpose:
 */
package net.sf.RecordEditor.utils.swing;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Common.RecordRunTimeException;


/**
 * Displays
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class HexTwoLineField extends JTextArea implements AbstractHexDisplay {

    private String fontName;

    /**
     * Build a three line (Mainframe style) hex display
     * @param font font name
     */
    public HexTwoLineField(final String font) {
        super();
        this.setRows(3);
        this.setFont(new Font("Monospaced", Font.PLAIN,  SwingUtils.STANDARD_FONT_HEIGHT));
        this.setBorder(BorderFactory.createEmptyBorder());
        //this.setBorder(((new DefaultTableCellRenderer()).getBorder()));
        //(new DefaultTableCellRenderer()).g


        fontName = font;
    }

    /**
     * Build a three line (Mainframe style) hex display
     * @param text data to display as text
     * @param hex data in hex
     * @param font font name
     */
    public HexTwoLineField(final String text, final String hex, final String font) {
        this(font);

        setHex(text, hex);
    }

    /* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.AbstractHexDisplay#setHex(byte[])
	 */
    public void setHex(byte[] bytes) {

        String hex  = Conversion.getDecimal(bytes, 0, bytes.length);
        String text = Conversion.getString(bytes, 0, bytes.length, fontName);

        setHex(text, hex);
    }


    /* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.AbstractHexDisplay#setHex(java.lang.String, java.lang.String)
	 */
    public void setHex(String text, String hex) {
        int i;
        StringBuffer buf = new StringBuffer();

        for (i = 0; i < text.length(); i ++) {
        	buf.append(text.substring(i, i + 1))
        		.append(" ");
        }

        setHex_100_ReplaceChar(buf, "\n");
        setHex_100_ReplaceChar(buf, "\t");

        //System.out.println("~~~" + text +"   ~~  " + hex);

        buf.append("\n")
           .append(hex);

        //System.out.println(buf);

        setText(buf.toString());
    }

    /* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.AbstractHexDisplay#getBytes(byte[])
	 */
    public byte[] getBytes(byte[] oldValue) {
    	int pos = 0;
    	byte[] temp;
      	String s = this.getText();
      	String tmp = "";
    	if ((pos = s.indexOf("\n")) >= 0) {
    		int len;
    		byte[] ret;
    		String line1 = s.substring(0, pos);
    		String line2 = s.substring(pos + 1);
    		String oldValueStr = Conversion.toString(oldValue, fontName);
    		ret = new byte[(Math.max(line2.length(), line1.length())) / 2];

    		for (int i = 0; i < line2.length(); i += 2) {
				try {
					tmp = line2.substring(i, i+2);
					ret[i/2] = Conversion.long2byte(Integer.parseInt(tmp, 16));
 				} catch (Exception e) {
					throw new RecordRunTimeException(
							ERROR_CONVERTING_HEX,
							new Object[] {(i/2), tmp, e.getMessage()});
				}
			}
    		//System.out.println();

    		len = Math.min(Math.min((line1.length() + 1) / 2, oldValueStr.length()), ret.length);


//    		System.out.println(" -->> " + len + " " + line1 + "< ");
    		for (int i = 0; i < len; i++) {
//    			System.out.print(" > " + line1.substring(i*2, i*2 + 1)
//    					+ " " + oldValueStr.substring(i, i+1));
    			if (! line1.substring(i*2, i*2 + 1)
    					.equals(oldValueStr.substring(i, i+1))) {
    				try {
     					temp =  Conversion.getBytes(line1.substring(i*2, i*2 + 1), fontName);
     					ret[i] = temp[0];
     				} catch (Exception e) {
       					throw new RuntimeException("Error Converting Text; byteNumber=" + (i / 2)
       							+ " " +e.getMessage());
					}
    			}
    		}
//    		System.out.println();

    		return ret;
    	}
    	return oldValue;
    }

    /**
     * Replace a supplied string with "."
     * @param buf String Buffer to update
     * @param s string to be replaces
     */
    private void setHex_100_ReplaceChar(StringBuffer buf, String s) {
        int i;
        while ((i = buf.indexOf(s)) >= 0) {
            buf.replace(i, i + 1, ".");
        }
    }


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.AbstractHexDisplay#getComponent()
	 */
	@Override
	public JTextComponent getComponent() {
		return this;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public AbstractHexDisplay clone() {
		 try {super.clone(); } catch (Exception e) {
		 }
		 HexTwoLineField ret = new HexTwoLineField(fontName);
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
