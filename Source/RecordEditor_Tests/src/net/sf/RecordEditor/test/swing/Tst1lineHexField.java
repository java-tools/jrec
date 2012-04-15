/**
 * 
 */
package net.sf.RecordEditor.test.swing;

import java.io.UnsupportedEncodingException;

import net.sf.JRecord.Common.Conversion;
import net.sf.RecordEditor.utils.swing.HexOneLineRender;
import junit.framework.TestCase;

/**
 * @author bm
 *
 */
public class Tst1lineHexField extends TestCase {

	/**
	 * Test method for {@link net.sf.RecordEditor.utils.swing.HexOneLineRender#setHex(byte[])}.
	 */
	public void testSetHexByteArray() throws UnsupportedEncodingException {
		boolean ok = true;
		for (int i = 0; i < 3100; i++) {
			ok &= checkSetHexByteArray("Test Text " + i);
		}
		
		assertTrue("Error in setHex, see above messages", ok);
	}

	private boolean checkSetHexByteArray(String s) throws UnsupportedEncodingException {
		boolean ret = true;

		String line1;
		byte[] retBytes;
		HexOneLineRender fld = new HexOneLineRender();
		byte[] bytes = s.getBytes();
		fld.setHex(bytes);
		
		line1 = fld.getText();
		
		retBytes = convertHex(line1);
		
		if (! equals(bytes,retBytes)) {
			System.out.println("Error in Bytes: " + s + " " + line1);
			printbytes(bytes);
			printbytes(retBytes);
			ret = false;
		}
		
		return ret;
	}
	
	private void printbytes(byte[] b) {
		String s;
		for (int i = 0; i < b.length; i++) {
			s = "     " + b[i];
			System.out.print(s.substring(s.length() - 4));
		}
		System.out.println();
	}
	
	private byte[] convertHex(String s) {
		int len = s.length();
		byte[] ret = new byte[len / 2];
		for (int i = 0; i < len - 1; i += 2) {
			ret[i/2] = Conversion.long2byte(Integer.parseInt(s.substring(i, i+2), 16));
		}
		
		return ret;
	}
	
	private boolean equals(byte[] b1, byte[] b2) {
		boolean ret = b1.length == b2.length;
		
		for (int i =0; ret && i < b1.length; i++) {
			ret = b1[i] == b2[i];
		}
		
		return ret;
	}
	
	/**
	 * Test method for {@link net.sf.RecordEditor.utils.swing.HexOneLineRender#getBytes(byte[])}.
	 */
	public void testGetBytes1() {
		boolean ok = true;
		String s= "String to test 1001";
		
		System.out.println();

		for (int i = 0; i < s.length(); i++ ) {
			ok &= tstGetBytes2(s, i, i);
		}
		assertTrue("Error in getBytes2, see above messages", ok);

		for (int i = 0; i < s.length(); i++ ) {
			for (int j = 0; j < s.length(); j++ ) {
				ok &= tstGetBytes2(s, i, j);
			}
		}
		
		assertTrue("Error in getBytes3, see above messages", ok);
	}

	

	
	private boolean tstGetBytes2(String str, int position1, int position2) {
		boolean ret = true; 
		String line2;
		StringBuilder buf;// = new StringBuilder();
		byte[] bytes = str.getBytes();
		byte[] newBytes = bytes.clone();
		byte[] retBytes;
		HexOneLineRender fld = new HexOneLineRender();
		fld.setHex(bytes);
 
		newBytes[position1] = 0;
		newBytes[position2] = 1;

		line2 = fld.getText();

		buf= new StringBuilder(line2);
		buf.replace(position1*2, position1*2 + 2, "00");
		buf.replace(position2*2, position2*2 + 2, "01");
		fld.setText(buf.toString());
//		System.out.println(line2);
//		System.out.println(buf.toString());
		
		retBytes = fld.getBytes(bytes);
		
		if (! equals(retBytes, newBytes)) {
			System.out.println("Error getBytes 2: " + str + " " + position1+ " " + position2);
			printbytes(newBytes);
			printbytes(retBytes);
			ret = false;
		}

		return ret;
	}
}
