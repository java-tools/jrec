package net.sf.RecordEditor.utils.fileStorage;

import java.util.Comparator;
import java.util.List;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;


@SuppressWarnings("unchecked")
public interface DataStore<L extends AbstractLine> extends List<L> {

	public AbstractLine getTempLine(int idx);
	
	public String getSizeDisplay();
	
	public long getSpace();
	
	public DataStore<L> newDataStore();

	public void sort(Comparator<L> compare);

	public L addCopy(int lineNo, L line);

	public AbstractLayoutDetails getLayout();
	public void setLayout(AbstractLayoutDetails layout);
}
