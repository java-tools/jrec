package net.sf.RecordEditor.utils.fileStorage;

import javax.swing.event.TableModelEvent;

import net.sf.JRecord.Details.AbstractLine;

public interface IDataStoreView<L extends AbstractLine> extends IDataStore<L> {

	public int getParentIndexRE(int index);

	public void tableChangedUpdateRE(TableModelEvent event);
}
