package net.sf.RecordEditor.utils.common;

/**
 * Garbage collection manager
 * 
 * @author Bruce Martin
 *
 */
public final class GcManager {

	private static boolean canDoGC         = true;
	private static long lastGC = Long.MIN_VALUE;

	private  GcManager() {
	}

	
	public static final void doGcIfNeccessary() {
		doGcIfNeccessary(getSpaceUsedRatio(), 0.80);
	}
	
	public static final void doGcIfNeccessary(double maxRatio) {
		doGcIfNeccessary(getSpaceUsedRatio(), maxRatio);
	}
	
	public static final void doGcIfNeccessarySupplyRatio(double spaceRatio) {
		doGcIfNeccessary(spaceRatio, 0.80);
	}
	
	public static final void doGcIfNeccessary(double spaceRatio, double maxRatio) {
		long time = System.currentTimeMillis();
		if (spaceRatio > maxRatio || (Math.abs(time - lastGC) > 10000)) {
			if (canDoGC && (Math.abs(time - lastGC) > 1000)) {		
				try {
					lastGC = time;
					System.gc();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			canDoGC = false;
		} else {
			canDoGC = true;
		}
	}


	public static double getSpaceUsedRatio() {
		Runtime rt = Runtime.getRuntime();
		return ((double)rt.totalMemory()) / ((double) rt.maxMemory());
	}
}
