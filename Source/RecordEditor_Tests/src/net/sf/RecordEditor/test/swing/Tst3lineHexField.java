/**
 * 
 */
package net.sf.RecordEditor.test.swing;

import java.io.UnsupportedEncodingException;

import junit.framework.TestCase;
import net.sf.JRecord.Common.Conversion;
import net.sf.RecordEditor.utils.swing.HexThreeLineField;

/**
 * @author bm
 *
 */
public class Tst3lineHexField extends TestCase {

	/**
	 * Test method for {@link net.sf.RecordEditor.utils.swing.HexThreeLineField#setHex(byte[])}.
	 */
	public void testSetHexByteArray() throws UnsupportedEncodingException {
		boolean ok = true;
		for (int i = 0; i < 1000; i++) {
			ok &= checkSetHexByteArray("Test Text " + i);
		}
		
		assertTrue("Error in setHex, see above messages", ok);
	}

	private boolean checkSetHexByteArray(String s) throws UnsupportedEncodingException {
		boolean ret = true;
		int pos, lastPos;
		String line1, line2, line3, out;
		//StringBuilder sb = new StringBuilder();
		byte[] retBytes;
		HexThreeLineField fld = new HexThreeLineField("");
		byte[] bytes = s.getBytes();
		fld.setHex(bytes);
		
		out = fld.getText();
		pos = out.indexOf("\n");
		lastPos = out.lastIndexOf("\n");
   		line1 = out.substring(0, pos);
		line2 = out.substring(pos + 1, lastPos);
		line3 = out.substring(lastPos + 1);
		
		retBytes = convertHex(line2, line3);
		
		if (! equals(bytes,retBytes)) {
			System.out.println("Error in Bytes: " + s + " " + line2);
			printbytes(bytes);
			printbytes(retBytes);
			ret = false;
		}
		
		if (! s.equals(line1)) {
			System.out.println("Error in String: " + s + " " + line1);
			System.out.println(s);
			System.out.println(line1 + "<");
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
	
	private byte[] convertHex(String s, String t) {
		int len = s.length();
		byte[] ret = new byte[len];
		for (int i = 0; i < len; i++) {
			ret[i] = Conversion.long2byte(Integer.parseInt(s.substring(i, i+1) + t.substring(i, i+1), 16));
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
	 * Test method for {@link net.sf.RecordEditor.utils.swing.HexThreeLineField#getBytes(byte[])}.
	 */
	public void testGetBytes1() {
		boolean ok = true;
		String s= "String to test 1001";
		StringBuilder tst;
		
		System.out.println();
		ok &= tstGetBytes1(s, s);
		for (int i = 0; i < s.length(); i++ ) {
			tst = new StringBuilder(s);
			tst.replace(i, i+1, "*");
			ok &= tstGetBytes1(s, tst.toString());
		}
		
		assertTrue("Error in getBytes1, see above messages", ok);
		
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

	
	private boolean tstGetBytes1(String str, String newStr) {
		boolean ret = true; 
		String line2, out;
		//StringBuilder buf = new StringBuilder();
		int pos;
		byte[] bytes = str.getBytes();
		byte[] newBytes = newStr.getBytes();
		byte[] retBytes;
		HexThreeLineField fld = new HexThreeLineField("");
		fld.setHex(bytes);
 
//        for (int i = 0; i < newStr.length(); i ++) {
//        	buf.append(newStr.substring(i, i + 1))
//        		.append(" ");
//        }


		out = fld.getText();
		pos = out.indexOf("\n");
   		//line1 = out.substring(0, pos);
		line2 = out.substring(pos + 1);
		fld.setText(newStr +"\n" + line2);
		
		retBytes = fld.getBytes(bytes);
		
		if (! equals(retBytes, newBytes)) {
			System.out.println("Error getBytes 1: " + str + " - " + newStr);
			printbytes(newBytes);
			printbytes(retBytes);
			ret = false;
		}

		return ret;
	}
	
	private boolean tstGetBytes2(String str, int position1, int position2) {
		boolean ret = true; 
		String line1, line2, line3, out;
		StringBuilder buf2, buf3;// = new StringBuilder();
		int pos, lastPos;
		byte[] bytes = str.getBytes();
		byte[] newBytes = bytes.clone();
		byte[] retBytes;
		HexThreeLineField fld = new HexThreeLineField("");
		fld.setHex(bytes);
 
		newBytes[position1] = 0;
		newBytes[position2] = 1;

		out = fld.getText();
		pos = out.indexOf("\n");
		lastPos = out.lastIndexOf("\n");
   		line1 = out.substring(0, pos);
   		line2 = out.substring(pos + 1, lastPos);
		line3 = out.substring(lastPos + 1);
		buf2 = new StringBuilder(line2);
		buf3 = new StringBuilder(line3);
		buf2.replace(position1, position1 + 1, "0");
		buf3.replace(position1, position1 + 1, "0");
		buf2.replace(position2, position2 + 1, "0");
		buf3.replace(position2, position2 + 1, "1");
		fld.setText(line1 +"\n" + buf2.toString() +"\n" + buf3.toString());
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
