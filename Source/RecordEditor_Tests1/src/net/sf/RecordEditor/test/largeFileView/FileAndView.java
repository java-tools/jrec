package net.sf.RecordEditor.test.largeFileView;

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

	public static final int NORMAL_FILE = 1;
	public static final int LARGE_FL_FILE = 2;
	public static final int LARGE_VL_FILE = 3;
	public static final int LARGE_CHAR_FILE = 4;
	
	public final IDataStore<AbstractLine> parentStore;
	public final FileView parentFile;
	public final IDataStore<AbstractLine> viewStore;
	public final FileView viewFile;
	
	public FileAndView(LayoutDetail schema, boolean normalFile, boolean normalView) {
		this(schema, getType(normalFile), normalView);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public FileAndView(LayoutDetail schema, int fileType, boolean normalView) {

		switch (fileType) {
//		case NORMAL_FILE: parentStore = DataStoreStd.newStore(schema); break;
		case LARGE_FL_FILE: parentStore = new DataStoreLarge(schema, FileDetails.FIXED_LENGTH, schema.getMaximumRecordLength()); break;
		case LARGE_VL_FILE: parentStore = new DataStoreLarge(schema, FileDetails.VARIABLE_LENGTH, schema.getMaximumRecordLength()); break;
		case LARGE_CHAR_FILE: parentStore = new DataStoreLarge(schema, FileDetails.CHAR_LINE, schema.getMaximumRecordLength()); break;
		default:
			parentStore = DataStoreStd.newStore(schema); break;
		}

		parentFile =  new FileView(parentStore, null, null);
	
		if (normalView) {
			viewStore = DataStoreStd.newStore(schema);
		} else {
			viewStore = new DataStoreLargeView(parentStore);
		}

		viewFile =  new FileView(viewStore, parentFile, null);
	}

	public static int getType(boolean normal) {
		if (normal) {
			return NORMAL_FILE;
		}
		return LARGE_FL_FILE;
	}
}
