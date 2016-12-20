/**
 * 
 */
package net.sf.RecordEditor.utils.fileStorage;

import java.util.AbstractList;
import java.util.Collection;
import java.util.Comparator;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;

/**
 * This class combines 2 Datastores to create a larger Datastore
 * @author Bruce
 *
 *
 * Started and abandonded
 */
public abstract class MergedDataStore
extends AbstractList<AbstractLine>
implements IDataStore<AbstractLine> {

	private final IDataStore<AbstractLine> dataStore1, dataStore2;
	/**
	 * 
	 */
	public MergedDataStore(IDataStore<AbstractLine> dataStr1, IDataStore<AbstractLine> dataStr2) {
		dataStore1 = dataStr1;
		dataStore2 = dataStr2;
	}

	@Override
	public int size() {
		return dataStore1.size() + dataStore2.size();
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public boolean contains(Object o) {
		return dataStore1.contains(o) || dataStore2.contains(o);
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(AbstractLine e) {
		return dataStore2.add(e);
	}

	@Override
	public boolean remove(Object o) {
		if (dataStore1.remove(o)) {
			return true;
		}
		return dataStore2.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends AbstractLine> c) {
		return dataStore2.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends AbstractLine> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		dataStore1.clear();
		dataStore2.clear();
	}

	@Override
	public AbstractLine get(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractLine set(int index, AbstractLine element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(int index, AbstractLine element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AbstractLine remove(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int indexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int lastIndexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public AbstractLine getTempLineRE(int idx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSizeDisplayRE() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getSpaceRE() {
		return dataStore1.getSpaceRE() + dataStore2.getSpaceRE();
	}

	@Override
	public IDataStore<AbstractLine> newDataStoreRE() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sortRE(Comparator<? super AbstractLine> compare) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public AbstractLine addCopy(int lineNo, AbstractLine line) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public AbstractLayoutDetails getLayoutRE() {
		return dataStore2.getLayoutRE();
	}

	@Override
	public void setLayoutRE(AbstractLayoutDetails layout) {
		dataStore1.setLayoutRE(layout);
		dataStore2.setLayoutRE(layout);
	}

}
