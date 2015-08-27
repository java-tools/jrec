package net.sf.RecordEditor.utils.approxSearch;

/**
 * This class does an approximate search of a array / collection
 * it uses an average 
 * @author Bruce Martin
 *
 */
public class ApproximateSearch {
	
	/**
	 * This method will search an array / collection 
	 * using an approximation of the position. 
	 * This should give slightly faster searches than using a binary
	 * 
	 * @param length length/size of array / collection
	 * @param chk class to check the array
	 * 
	 * @return the index index being searched for.
	 */
	public static final int search(int length, IApproximateCheck chk) {
		int c;
		int n = 0;
		
		if ((c = chk.check(n)) == 0) {
			
		} else if (c < 0) {
			n = -1;
		} else {
			int high = length - 1;
			n = Math.min(high - 1, Math.max(1, chk.getNextIndex()));;
			if (chk.check(high) < 0) {
				int low = 1; 
				while ((c = chk.check(n)) != 0)  {
					if (c < 0) {
						high = n - 1;
					} else {
						low = n + 1;
					}
					n = Math.min(high, Math.max(low, chk.getNextIndex()));
				}
			} else {
				n = high;
			}
		}
		
		return n;
	}
}
