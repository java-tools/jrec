package net.sf.RecordEditor.test.fileStore;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.test.largeFileView.FileAndView;
import net.sf.RecordEditor.utils.fileStorage.IDataStore;

public class ExtractTestData1 implements IExtractTestData {
	private final FileView file;
	private final IDataStore<AbstractLine> store;
	private IDataStore<AbstractLine> extract;
	
	public final LayoutDetail schema;
	public final int extractType;
	
	public ExtractTestData1(LayoutDetail schema, int fileType, boolean normalView, boolean useView, int extractType) {
		FileAndView fv = new FileAndView(schema, fileType, normalView);
		
		this.schema = schema;
		this.extractType = extractType;
		
		if (useView) {
			file = fv.viewFile;
			store = fv.viewStore;
		} else {
			file = fv.parentFile;
			store = fv.parentStore;
		}
		
		newExtract();
	}
	

	@Override
	public final FileView getFile() {
		return file;
	}

	@Override
	public final FileView getLoadFile() {
		return file;
	}

	@Override
	public final IDataStore<AbstractLine> getStore() {
		return store;
	}
	
	@Override
	public void newExtract() {
		FileAndView fv = new FileAndView(schema, extractType, true);
		extract = fv.parentStore;
	}

	@Override
	public final IDataStore<AbstractLine> getExtract() {
		return extract;
	}
}
