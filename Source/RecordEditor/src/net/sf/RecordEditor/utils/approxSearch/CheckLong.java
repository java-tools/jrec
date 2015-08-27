/**
 * 
 */
package net.sf.RecordEditor.utils.approxSearch;

/**
 * @author Bruce Martin
 *
 */
public abstract class CheckLong implements IApproximateCheck {

	private int n = 0;
	private int ave;
	private int size;
	private  long search;
	
	public CheckLong() {
		
	}
	public CheckLong(int size, long search, int ave) {
		set(size, search, ave);
	}

	public CheckLong set(int size, long search, int ave) {
		this.size = size;
		this.search = search;
		this.ave = ave;
		
		this.n = 0;
		
		return this;
	}
	
	/**
	 * @param search the search to set
	 */
	public final void setSearch(long search) {
		this.search = search;
		this.n = 0;
	}

	@Override
	public int getNextIndex() {
		return n;
	}
	
	@Override
	public int check(int idx) {
		
		int r = 0;
		long g;

		if (search < (g = get(idx))) {
			r = -1;
			n = idx + ((int) ((search - g) / ave)) - 1;
			//System.out.println("==>> " + idx + " " + search + " ~ " + a[idx] + " " + ave + " / " + n + " | ");
		} else if (idx < size - 1 && search >= (g = get( idx + 1))) {
			r = 1;
			n = idx + ((int)((search - g)) / ave) + 1;
		}
		
		return r;
	}

	public abstract long get(int index);
}
