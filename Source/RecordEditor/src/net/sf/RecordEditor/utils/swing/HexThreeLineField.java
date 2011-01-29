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


/**
 * Displays
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class HexThreeLineField extends JTextArea implements AbstractHexDisplay {

    private String fontName;

    /**
     * Build a three line (Mainframe style) hex display
     * @param font font name
     */
    public HexThreeLineField(final String font) {
        super();
        this.setRows(3);
        this.setFont(new Font("Monospaced", Font.PLAIN,  SwingUtils.STANDARD_FONT_HEIGHT));
        this.setBorder(BorderFactory.createEmptyBorder());
        fontName = font;
    }

    /**
     * Build a three line (Mainframe style) hex display
     * @param text data to display as text
     * @param hex data in hex
     * @param font font name
     */
    public HexThreeLineField(final String text, final String hex, final String font) {
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
        StringBuffer buf = new StringBuffer(text);
        StringBuffer line1 = new StringBuffer();
        StringBuffer line2 = new StringBuffer();


        setHex_100_ReplaceChar(buf, "\n");
        setHex_100_ReplaceChar(buf, "\t");


        for (i = 0; i < hex.length() - 1; i += 2) {
            //System.out.println("~~> " + i);
            line1.append(hex.substring(i, i + 1));
            line2.append(hex.substring(i + 1, i + 2));
        }

        buf.append("\n")
           .append(line1).append("\n")
           .append(line2);
        //System.out.println("~~> " + buf.toString());
        setText(buf.toString());
    }
    
    /* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.AbstractHexDisplay#getBytes(byte[])
	 */
    public byte[] getBytes(byte[] oldValue) {
    	int pos = 0;
    	byte[] temp;
      	String s = this.getText();
    	if ((pos = s.indexOf("\n")) >= 0) {
    		int len;
    		byte[] ret;
    		int lastPos = s.lastIndexOf("\n");
    		String line1 = s.substring(0, pos);
    		String line2 = s.substring(pos + 1, lastPos);
    		String line3 = s.substring(lastPos + 1);
    		String oldValueStr = Conversion.toString(oldValue, fontName); 
    		String tmp = "";
    		
    		int hexLen = Math.min(line2.length(), line3.length());
//    		System.out.println(" --- " + (line2.length()) + " " + line1.length()
//    				+ " ~~ " + ((Math.max(line2.length() - 1, line1.length())) / 2));
    		ret = new byte[(Math.max(hexLen, line1.length()))];

//    		System.out.println(line2);
//    		System.out.println(line3);
//
//    		System.out.println();
    		for (int i = 0; i <hexLen; i += 1) {
//    			System.out.print(" " + line2.substring(i, i+1) + line3.substring(i, i+1));
    			try {
    				tmp = line2.substring(i, i+1) + line3.substring(i, i+1);
    				ret[i] = Conversion.long2byte(
							Integer.parseInt(tmp, 16));
 				} catch (Exception e) {
					throw new RuntimeException("Error Converting Hex; byteNumber=" + i 
							+ " value=" + tmp + " Message: "  +e.getMessage());
				}
				
			}
//    		System.out.println();
    		
    		len = Math.min(Math.min(line1.length(), oldValueStr.length()), ret.length);

     	
//    		System.out.println(" -->> " + len + " " + line1 + "< " + line1.length());
    		for (int i = 0; i < len; i++) {
//    			System.out.print(" > " + line1.substring(i*2, i*2 + 1) 
//    					+ " " + oldValueStr.substring(i, i+1));
    			if (! line1.substring(i, i + 1)
    					.equals(oldValueStr.substring(i, i+1))) {
    				try {
     					temp =  Conversion.getBytes(line1.substring(i, i + 1), fontName);
     					ret[i] = temp[0];
     				} catch (Exception e) {
    					throw new RuntimeException("Error Converting Text; byteNumber=" + i + " " +e.getMessage());
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
		 HexThreeLineField ret = new HexThreeLineField(fontName);
		 ret.setText(this.getText());
		 return ret;
	}
	


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.AbstractHexDisplay#isTwoBytesPerCharacter()
	 */
	@Override
	public boolean isTwoBytesPerCharacter() {
		return false;
	}
}
