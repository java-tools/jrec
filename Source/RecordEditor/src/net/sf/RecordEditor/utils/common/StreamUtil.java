package net.sf.RecordEditor.utils.common;

import java.io.IOException;
import java.io.InputStream;

public class StreamUtil {

	public static byte[] read(InputStream in, int bytesToRead) throws IOException{
		byte[] data = new byte[bytesToRead];
		int total = 0;
		
		try {
		    int num = in.read(data);
		    total = num;
	
		    while (num >= 0 && total < bytesToRead) {
		        num = in.read(data, total, bytesToRead - total);
		        total += Math.max(0, num);
		    }
		} finally {
			in.close();
		}

	    if (total <= 0) {
	    	data = new byte[0];
	    } else if (total < bytesToRead) {
	    	byte[] t = new byte[total];
	    	System.arraycopy(data, 0, t, 0, total);
	    	data = t;
	    }

	    return data;
	}
}
