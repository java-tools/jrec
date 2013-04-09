package net.sf.RecordEditor.utils.fileStorage;

import net.sf.JRecord.Details.AbstractLine;

public interface IDataStoreText<L extends AbstractLine> extends DataStore<L> {

	public int length();

	public DataStorePosition getPosition(int pos);

	public DataStorePosition getLinePosition(int lineNo) ;

	public void updatePosition(DataStorePosition pos);

}
