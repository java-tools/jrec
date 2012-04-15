package net.sf.RecordEditor.testcode;

import net.sf.RecordEditor.examples2.USdate8;

public class TestCreate1 {
	/* time 2698 2418 2597 2466 */
	/* 449 448 448*/
	static Class<USdate8> c = USdate8.class;
	public static void main(String[] args) {
//		int i;
		
		runTesy();
		long t = System.currentTimeMillis();
		
		runTesy();
		System.out.print((System.currentTimeMillis() - t));
	}
	
	public static void runTesy() {
//		USdate8 v;
		for (int i = 0; i < 500000; i++) {
			try {
				c.newInstance();
			} catch (Exception e) {
			}
		}
	}
}
