package net.sf.RecordEditor.utils.fileStorage;

import java.util.Comparator;
import java.util.List;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.utils.swing.common.IProgressDisplay;


public interface IDataStore<L extends AbstractLine> extends List<L> {

	public void addCopyRE(int pos, IDataStore<? extends AbstractLine> cpyLines, IProgressDisplay progressDisplay);
	
	public void extractLinesRE(IDataStore<AbstractLine> cpyLines, int[] linesToExtract, IProgressDisplay progressDisplay);

	public AbstractLayoutDetails getLayoutRE();

	public int getLineLengthRE(int lineNo);
	 
	public AbstractLine getNewLineRE(int index);

	public String getSizeDisplayRE();

	public long getSpaceRE();

	public AbstractLine getTempLineRE(int idx);

	public IDataStore<L> newDataStoreRE();

	public IDataStore<AbstractLine> newDataStoreRE(int[] lines);

	public void setLayoutRE(AbstractLayoutDetails layout);
	
	public void sortRE(Comparator<AbstractLine> compare);
	
	public void sortRE(int[] rows, Comparator<AbstractLine> compare);
	
	public void addChildViewRE(ISortNotify notify);
	
	public boolean hasEmbeddedCr();
	
	public boolean isTextViewPossibleRE();
	
	public ITextInterface getTextInterfaceRE();
}
