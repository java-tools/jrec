package net.sf.RecordEditor.edit.file.storage;

import java.util.Comparator;
import java.util.List;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;


@SuppressWarnings("unchecked")
public interface DataStore<L extends AbstractLine> extends List<L> {

	public L getTempLine(int idx);
	
	public String getSizeDisplay();
	
	public DataStore<L> newDataStore();

	public void sort(Comparator<L> compare);

	public L addCopy(int lineNo, L line);

	public void setLayout(AbstractLayoutDetails layout);
}
