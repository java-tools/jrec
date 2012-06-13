package net.sf.RecordEditor.utils.common;

import java.io.IOException;
import java.io.InputStream;

public class StreamUtil {

	public static byte[] read(InputStream in, int bytesToRead) throws IOException{
		byte[] data = new byte[bytesToRead];
	    int num = in.read(data);
	    int total = num;

	    while (num >= 0 && total < bytesToRead) {
	        num = in.read(data, total, bytesToRead - total);
	        total += Math.max(0, num);
	    }
	    in.close();

	    if (total < bytesToRead) {
	    	byte[] t = new byte[total];
	    	System.arraycopy(data, 0, t, 0, total);
	    	data = t;
	    }

	    return data;
	}
}
