package net.sf.RecordEditor.edit.file.storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.CharLine;

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
	
	public final AbstractLayoutDetails getLayout() {
		if (layout == null && super.size() > 0) {
			layout = get(0).getLayout();
		}
		
		return layout;
	}
	
	@Override
	public void setLayout(AbstractLayoutDetails layout) {
		if (this.layout != layout) {
			this.layout = layout;
			for (int i = size() - 1; i >= 0; i--) {
				get(i).setLayout(layout);
			}
		}
	}

	@Override
	public void sort(Comparator<L> compare) {
		Collections.sort(this, compare);
	}

	
	@Override
	public long getSpace() {
		long kb = 0;
		
		if (size() > 0) {
			if (get(0) instanceof CharLine) {
				for (AbstractLine l : this) {
					kb += l.getFullLine().length();
				}
			} else {
				for (AbstractLine l : this) {
					kb += l.getData().length;
				}
			}
		}
		return kb;
	}

	@Override
	public String getSizeDisplay() {
		return "Record Count: " + size();
	}
}