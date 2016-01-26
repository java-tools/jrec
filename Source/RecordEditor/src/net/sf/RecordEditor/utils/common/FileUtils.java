package net.sf.RecordEditor.utils.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {

	public static String readFile(File file) throws IOException {

		StringBuilder b = new StringBuilder(8192);

		char[] buff = new char[8192];
		int l;
		BufferedReader r = new BufferedReader(new FileReader(file), buff.length);
		try {
			while ((l = r.read(buff)) > 0) {
				if (l == buff.length) {
					b.append(buff);
				} else {
					char[] buff1 = new char[l];
					System.arraycopy(buff, 0, buff1, 0, l);
					b.append(buff1);
				}
			}
		} finally {
			r.close();
		}
		
		return b.toString();
	}
}
