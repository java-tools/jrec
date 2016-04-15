package net.sf.RecordEditor.test.fileStore;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.fileStorage.IDataStore;

public interface IExtractTestData {

	public abstract IDataStore<AbstractLine> getExtract();

	public abstract IDataStore<AbstractLine> getStore();

	public abstract FileView getFile();

	public void newExtract();

	FileView getLoadFile();

}