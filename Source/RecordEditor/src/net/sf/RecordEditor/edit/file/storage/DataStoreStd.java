package net.sf.RecordEditor.edit.file.storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;

@SuppressWarnings({ "serial", "unchecked" })
public class DataStoreStd<L extends AbstractLine>
extends ArrayList<L> implements DataStore<L> {

	private AbstractLayoutDetails layout;
	public DataStoreStd(AbstractLayoutDetails layoutDtls ) {
		layout = layoutDtls;
	}

	public DataStoreStd(AbstractLayoutDetails layoutDtls, int arg0) {
		super(arg0);
		layout = layoutDtls;
	}

	public DataStoreStd(AbstractLayoutDetails layoutDtls, Collection<L> arg0) {
		super(arg0);
		layout = layoutDtls;
	}

	
	@Override
	public L addCopy(int lineNo, L line) {
		L l = (L) line.clone();
		
		l.setLayout(getLayout());
		
		add(lineNo, l);
		
		return l;
	}

	@Override
	public L getTempLine(int idx) {
		return super.get(idx);
	}
//
//	@Override
//	public L getResolvedLine(int idx) {
//		return (L)super.get(idx).clone();
//	}

	@Override
	public DataStore<L> newDataStore() {
		return new DataStoreStd<L>(getLayout(), this);
	}
	
	private AbstractLayoutDetails getLayout() {
		if (layout == null && super.size() > 0) {
			layout = get(0).getLayout();
		}
		
		return layout;
	}
	
	@Override
	public void setLayout(AbstractLayoutDetails layout) {
		this.layout = layout;
	}

	@Override
	public void sort(Comparator<L> compare) {
		Collections.sort(this, compare);
	}

	@Override
	public String getSizeDisplay() {
		return "Record Count: " + size();
	}
}