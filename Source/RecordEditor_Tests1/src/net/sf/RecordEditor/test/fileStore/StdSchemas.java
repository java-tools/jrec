package net.sf.RecordEditor.test.fileStore;

import java.io.ByteArrayInputStream;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.CobolCopybookLoader;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.ToLayoutDetail;
import net.sf.JRecord.Numeric.ICopybookDialects;

public final class StdSchemas {

	public final static LayoutDetail TWENTY_BYTE_RECORD_SCHEMA;
	public final static LayoutDetail LARGE_RECORD_SCHEMA;
	private final static byte[] schemaLines = 
			( "        01  Rec-20.\n"
			+ "            03 Field-1          Pic x(20).").getBytes();
		
	private final static byte[] largeSchemaLines = 
			( "        01  Rec-20.\n"
			+ "            03 Field-1          Pic x(500).").getBytes();
		

	static {
		CobolCopybookLoader loaderCBL = new CobolCopybookLoader();
		LayoutDetail tmp = null;
		LayoutDetail tmp1 = null;
		
		try {
			ExternalRecord extlayoutCBL = loaderCBL.loadCopyBook(
			    	    new ByteArrayInputStream(schemaLines),
			    	    Conversion.getCopyBookId("Fixed.cbl"),
			    	    CopybookLoader.SPLIT_NONE, 0, "",
		                ICopybookDialects.FMT_MAINFRAME, 0, null);
			tmp = ToLayoutDetail.getInstance().getLayout(extlayoutCBL);
			extlayoutCBL = loaderCBL.loadCopyBook(
		    	    new ByteArrayInputStream(largeSchemaLines),
		    	    Conversion.getCopyBookId("Fixed.cbl"),
		    	    CopybookLoader.SPLIT_NONE, 0, "",
	                ICopybookDialects.FMT_MAINFRAME, 0, null);
			tmp1 = ToLayoutDetail.getInstance().getLayout(extlayoutCBL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		TWENTY_BYTE_RECORD_SCHEMA = tmp;
		LARGE_RECORD_SCHEMA = tmp1;
	}

}
