package net.sf.RecordEditor.test.largeFileView;

import net.sf.JRecord.Details.LayoutDetail;
import net.sf.RecordEditor.re.file.FileView;

/**
 * This class holds test data
 * @author Bruce Martin
 *
 */
public class ViewTestData {

	public static final int UPD_PARENT = 1;
	public static final int UPD_VIEW = 2;
	public static final int UPD_BOTH = 3;
	
	public final FileAndView baselineFV;
	public final FileAndView testFV;
	
	public final FileView[] baseLineFile = new FileView[2];
	public final FileView[] testFile     = new FileView[2];
	
	public ViewTestData(boolean normalFile, boolean normalView, int updateType) {
		this(FileAndView.getType(normalFile), normalView, updateType);
	}
	
	public ViewTestData(int fileType, boolean normalView, int updateType) {
		this(ViewTest.DTAR020, fileType, normalView, updateType);
	}
	
	public ViewTestData(LayoutDetail schema, int fileType, boolean normalView, int updateType) {
		baselineFV = new FileAndView(schema, true, true);
		testFV = new FileAndView(schema, fileType, normalView);
		
		switch (updateType) {
		case UPD_PARENT:
			baseLineFile[0] = baselineFV.parentFile;
			baseLineFile[1] = baselineFV.parentFile;
			testFile[0] = testFV.parentFile;
			testFile[1] = testFV.parentFile;
			break;
		case UPD_VIEW:
			baseLineFile[0] = baselineFV.viewFile;
			baseLineFile[1] = baselineFV.viewFile;
			testFile[0] = testFV.viewFile;
			testFile[1] = testFV.viewFile;
			break;
		case UPD_BOTH:
			baseLineFile[0] = baselineFV.parentFile;
			baseLineFile[1] = baselineFV.viewFile;
			testFile[0] = testFV.parentFile;
			testFile[1] = testFV.viewFile;
			break;
		}
	}

}
