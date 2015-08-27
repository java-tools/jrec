package net.sf.RecordEditor.utils.fileStorage;

import java.util.Iterator;

import net.sf.JRecord.Details.AbstractLine;

public interface IDataStoreIterator extends Iterator<AbstractLine> {

	public abstract AbstractLine nextTempRE();
	
	public abstract AbstractLine currentLineRE();
}
