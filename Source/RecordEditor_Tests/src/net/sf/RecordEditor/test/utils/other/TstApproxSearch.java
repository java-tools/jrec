package net.sf.RecordEditor.test.utils.other;


import junit.framework.TestCase;
import net.sf.RecordEditor.test.TstConstants;
import net.sf.RecordEditor.utils.approxSearch.ApproximateSearch;
import net.sf.RecordEditor.utils.approxSearch.CheckLong;
import net.sf.RecordEditor.utils.approxSearch.IApproximateCheck;


/**
 * Checking the approximate search algorithm 
 * @author Bruce Martin
 *
 */
public class TstApproxSearch extends TestCase {

	private static final int TEST_SIZE = 32000;
	int[] a;
	private int totalCalls = 0;
	
	public void testSearch1() {
		a = TstConstants.getRandomIncreasingArray(TEST_SIZE, 50, 75);
		totalCalls = 0;
		
		int m = a[TEST_SIZE - 1];
		int idx, chkIdx = 0;
		Check chk = new Check();
		chk.ave = Math.max(1, m / TEST_SIZE);
		System.out.println("== Size " + TEST_SIZE + " " + m + " " + chk.ave);
		for (int i = 0; i <= m; i++) {
			chk.search = i;
			chk.n = 0;

			idx = ApproximateSearch.search(TEST_SIZE, chk);
			
			while (chkIdx < TEST_SIZE - 1 && i >= a[chkIdx + 1]) {
				chkIdx += 1;
			}
			if (chkIdx != idx) {
				int min = Math.min(chkIdx, idx);
				int max = Math.max(chkIdx, idx);
				for (int k = Math.max(0, min - 1); k <= Math.min(a.length - 1, max + 1); k++) {
					System.out.print("\t" + k + " " + a[k]);
				}
				
				chk.search = i;
				chk.n = 0;
				idx = ApproximateSearch.search(TEST_SIZE, chk);
			}
			assertEquals("Search For: " + i, chkIdx, idx);
		}
		
		double callCountAve = ((double) totalCalls + 1) / m;
		System.out.println("--> Average count " + callCountAve + " m=" + m + " " + totalCalls );
		assertTrue("Performance issue - To many calls: " + callCountAve, callCountAve < 6);
	}
	
	
	/**
	 * Checking for large array & more equal records
	 */
	public void testSearch2() {
		a = TstConstants.getRandomIncreasingArray(TEST_SIZE * 16, 200, 420);
		totalCalls = 0;
		
		int m = a[TEST_SIZE - 1];
		int idx, chkIdx = 0;
		Check chk = new Check();
		chk.ave = Math.max(1, m / TEST_SIZE);
		int x = m /200;
		int tcalls = 0;
		System.out.println("== Size " + TEST_SIZE + " " + m + " " + chk.ave);
		for (int i = 0; i <= m; i+=x) {
			chk.search = i;
			//System.out.print("\t" + (i / x) +"\t");
			chk.n = 0;

			totalCalls = 0;
			idx = ApproximateSearch.search(TEST_SIZE, chk);
			tcalls += totalCalls;
			
			while (chkIdx < TEST_SIZE - 1 && i >= a[chkIdx + 1]) {
				chkIdx += 1;
			}

			assertEquals("Search For: " + i, chkIdx, idx);
			System.out.println("--> Average count Test 2: " + (i / x) + ": " + (totalCalls) +  " " + totalCalls  +  " " );
		}
		
		double callCountAve = ((double)tcalls) / 200;
		System.out.println("--> Average count Test 2: " + callCountAve +  " " + tcalls  +  " " );
		assertTrue("Performance issue - To many calls: " + callCountAve, callCountAve < 6);
	}
	
	
	/**
	 * Checking the Test Long class
	 */
	public void testSearch3() {
		a = TstConstants.getRandomIncreasingArray(TEST_SIZE, 50, 75);
		totalCalls = 0;
		
		int m = a[TEST_SIZE - 1];
		int idx, chkIdx = 0;
		CheckLong chk = new CheckLong() {
			@Override public long get(int index) {
				return a[index];
			}

			/* (non-Javadoc)
			 * @see net.sf.RecordEditor.utils.other.CheckLong#check(int)
			 */
			@Override
			public int check(int idx) {
				totalCalls += 1;
				return super.check(idx);
			}
			
			
		};
		
		int tc;
		int ave = Math.max(1, m / TEST_SIZE);
		chk.set(TEST_SIZE, 0, ave);
		System.out.println("== Size " + TEST_SIZE + " " + m + " " + ave);
		for (int i = 0; i <= m; i++) {
			chk.setSearch(i);

			tc = totalCalls;
			idx = ApproximateSearch.search(TEST_SIZE, chk);
			if (totalCalls - tc > 9) {
				idx = ApproximateSearch.search(TEST_SIZE, chk);
			}
			
			while (chkIdx < TEST_SIZE - 1 && i >= a[chkIdx + 1]) {
				chkIdx += 1;
			}

			assertEquals("Search For: " + i, chkIdx, idx);
		}
		
		double callCountAve = ((double) totalCalls + 1) / m;
		System.out.println("--> Average count 3 " + callCountAve + " m=" + m + " " + totalCalls );
		assertTrue("Performance issue - To many calls: " + callCountAve, callCountAve < 6);
	}
	


	private class Check implements IApproximateCheck {
		int ave;
		int n = 0;
		public int search;
		@Override
		public int getNextIndex() {
			return n;
		}
		
		@Override
		public int check(int idx) {
			totalCalls += 1;
			
			int r = 0;

			if (search < a[idx]) {
				r = -1;
				n = idx + (search - a[idx]) / ave - 1;
				//System.out.println("==>> " + idx + " " + search + " ~ " + a[idx] + " " + ave + " / " + n + " | ");
			} else if (idx < a.length - 1 && search >= a[idx + 1]) {
				r = 1;
				n = idx + (search - a[idx + 1]) / ave + 1;
			}
			
			return r;
		}
		
	}
}
