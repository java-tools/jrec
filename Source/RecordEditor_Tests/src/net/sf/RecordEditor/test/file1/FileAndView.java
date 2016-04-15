package net.sf.RecordEditor.test.file1;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.fileStorage.DataStoreLarge;
import net.sf.RecordEditor.utils.fileStorage.DataStoreLargeView;
import net.sf.RecordEditor.utils.fileStorage.DataStoreStd;
import net.sf.RecordEditor.utils.fileStorage.FileDetails;
import net.sf.RecordEditor.utils.fileStorage.IDataStore;

/**
 * Defines and holds a Base-File and a File View.
 * It is used in testing
 * 
 * @author Bruce
 *
 */
public class FileAndView {

	public final IDataStore<AbstractLine> parentStore;
	public final FileView parentFile;
	public final IDataStore<AbstractLine> viewStore;
	public final FileView viewFile;
	
	public FileAndView(LayoutDetail schema, boolean normalFile, boolean normalView) {

		parentStore = getStore(schema, normalFile);
		parentFile =  new FileView(parentStore, null, null);

		if (normalView) {
			viewStore = DataStoreStd.newStore(schema);
		} else {
			viewStore = new DataStoreLargeView(parentStore);
		}

		viewFile =  new FileView(viewStore, parentFile, null);
	}

	private IDataStore<AbstractLine> getStore(LayoutDetail schema, boolean normal) {
		if (normal) {
			return DataStoreStd.newStore(schema);
		} else {
			return new DataStoreLarge(schema, FileDetails.FIXED_LENGTH, schema.getMaximumRecordLength());
		}
	}
}
