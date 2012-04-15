package net.sf.RecordEditor.testcode;


public class TestCreate2 {
	/* time 2698 2418 2597 2466 */
	static CreateClass cc = new xx();
	public static void main(String[] args) {

		runTesy();
		long t = System.currentTimeMillis();
		
		runTesy();
		System.out.print((System.currentTimeMillis() - t));
	}
	
	public static void runTesy() {
	
		for (int i = 0; i < 500000; i++) {
			cc.create();
		}
		
	}
}
