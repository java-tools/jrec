package net.sf.RecordEditor.utils.fileStorage;

import net.sf.JRecord.Details.AbstractLine;

public class DataStoreIterator implements IDataStoreIterator {
	private final IDataStore<? extends AbstractLine> store;
	private int count = 0;
	
	
	public DataStoreIterator(IDataStore<? extends AbstractLine> store) {
		super();
		this.store = store;
	}

	@Override
	public boolean hasNext() {
		return count < store.size();
	}

	@Override
	public AbstractLine next() {
		return store.get(count++);
	}

	@Override
	public void remove() {
		throw new RuntimeException("Remove is not implemented");
	}

	@Override
	public AbstractLine nextTempRE() {
		return store.getTempLineRE(count++);
	}

	@Override
	public AbstractLine currentLineRE() {
		return store.get(count - 1);
	}
}
