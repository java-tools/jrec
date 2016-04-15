package net.sf.RecordEditor.test.fileStore;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.test.largeFileView.FileAndView;
import net.sf.RecordEditor.utils.fileStorage.IDataStore;

public class ExtractTestData2 implements IExtractTestData {
	private FileAndView fv;
	private FileView file = null;

	private IDataStore<AbstractLine> extract;
	
	public final LayoutDetail schema;
	public final int extractType;
	
	public ExtractTestData2(LayoutDetail schema, int fileType, int extractType) {
		fv = new FileAndView(schema, fileType, false);
		
		this.schema = schema;
		this.extractType = extractType;
		
		newExtract();
	}
	

	@Override
	public final FileView getFile() {
		if (file == null) {
			for (int i = 5; i < fv.parentFile.getRowCount() - 5; i++) {
				if (i != 3000 && i != 16000) {
					fv.viewFile.add(fv.parentFile.getLine(i));
				}
			}
			file = fv.viewFile;
		}
		return file;
	}

	@Override
	public final FileView getLoadFile() {
		return fv.parentFile;
	}

	@Override
	public final IDataStore<AbstractLine> getStore() {
		return fv.viewStore;
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
