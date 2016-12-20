package net.sf.RecordEditor.utils.fileStorage;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import net.sf.JRecord.Details.AbstractLine;

public class SortParent {

	private ArrayList<WeakReference<ISortNotify>> notifyList = new ArrayList<WeakReference<ISortNotify>>(10);
	
	public void add(ISortNotify notify) {
		notifyList.add(new WeakReference<ISortNotify>(notify));
	}
	
	public <L extends AbstractLine> int[] sort(IDataStore<L> store, Comparator<? super AbstractLine> compare) {
		DataStoreLargeView lv = new DataStoreLargeView(store, false).addRange(0, store.size() - 1);
		
		return doSort(lv, compare, null, store.size());
	}
	
	public final <L extends AbstractLine> int[] sort(IDataStore<L> store, int[] rows, Comparator<? super AbstractLine> compare) {
		DataStoreLargeView lv = new DataStoreLargeView(store, false, rows);
		
		return doSort(lv, compare, rows, store.size());  
	}
	
	private  int[] doSort(DataStoreLargeView lv, Comparator<? super AbstractLine> compare, int[] rows, int mapSize) {
		lv.sortRE(compare);
		
		checkListIsActive();
		
		int[] order = lv.asIntArray();
		if (notifyList.size() > 0) {
			int[] map = getMapping(order, rows, mapSize);
			for (int i = notifyList.size() - 1; i >= 0; i-- ) {
				ISortNotify sn = notifyList.get(i).get();
				if (sn != null) {
					sn.updateForParentSort(map);
				}
			}
		}
		
		//int[][] ret = {order, map};
		return order;
	}
	
	public static int[] getMapping(int[] order) {
		return getMapping(order, null, order.length);
	}
	
	public static int[] getMapping(int[] order, int[] rows, int size) {
		int[] map = new int[size];
		if (rows == null) {
			for (int i = 0; i < order.length; i++) {
				map[order[i]] =  i;
			}
		} else {
			Arrays.fill(map, -1);

			for (int i = 0; i < order.length; i++) {
				map[order[i]] =  rows[i];
			}
		}
		
		return map;
	}
	
	public boolean hasSortNotifies() {
		checkListIsActive();
		return notifyList.size() > 0;
	} 

	private void checkListIsActive() {
		for (int i = notifyList.size() - 1; i >= 0; i-- ) {
			if (notifyList.get(i).get() == null) {
				notifyList.remove(i);
			}
		}
	}
}
