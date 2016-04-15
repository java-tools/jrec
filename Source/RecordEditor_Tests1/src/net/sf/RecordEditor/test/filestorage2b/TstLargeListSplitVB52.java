package net.sf.RecordEditor.test.filestorage2b;

import java.io.ByteArrayInputStream;

import junit.framework.TestCase;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.RecordEditorXmlLoader;
import net.sf.RecordEditor.test.filestorage1.TstLargeListVB50;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.params.Parameters;

public class TstLargeListSplitVB52  extends TestCase {

	private TstLargeListVB50 main = new TstLargeListVB50();
	private LayoutDetail layout;

	public TstLargeListSplitVB52() throws Exception {
		String c = "<?xml version=\"1.0\" ?>"
			 + "<RECORD RECORDNAME=\"VbFile1\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" FILESTRUCTURE=\"Default\" STYLE=\"0\" RECORDTYPE=\"GroupOfRecords\" LIST=\"Y\" QUOTE=\"\" RecSep=\"default\">"
			 + "	<RECORDS>"
			 + "		<RECORD RECORDNAME=\"VbRec1\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" FILESTRUCTURE=\"Default\" STYLE=\"0\" RECORDTYPE=\"RecordLayout\" LIST=\"Y\" PARENT=\"VbRec1\" QUOTE=\"\" RecSep=\"default\">"
			 + "			<FIELDS>"
			 + "				<FIELD NAME=\"f11\" POSITION=\"1\" LENGTH=\"4\" TYPE=\"Binary Integer Big Endian (Mainframe?)\"/>"
			 + "				<FIELD NAME=\"f12\" POSITION=\"5\" LENGTH=\"2\" TYPE=\"Binary Integer Big Endian (Mainframe?)\"/>"
			 + "				<FIELD NAME=\"f13\" POSITION=\"7\" LENGTH=\"4\" TYPE=\"Binary Integer Big Endian (Mainframe?)\"/>"
			 + "			</FIELDS>"
			 + "		</RECORD>"
			 + "		<RECORD RECORDNAME=\"VbRec2\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" FILESTRUCTURE=\"Default\" STYLE=\"0\" RECORDTYPE=\"RecordLayout\" LIST=\"Y\" PARENT=\"VbRec1\" QUOTE=\"\" RecSep=\"default\">"
			 + "			<FIELDS>"
			 + "				<FIELD NAME=\"f11\" POSITION=\"1\" LENGTH=\"4\" TYPE=\"Binary Integer Big Endian (Mainframe?)\"/>"
			 + "				<FIELD NAME=\"f12\" POSITION=\"5\" LENGTH=\"2\" TYPE=\"Binary Integer Big Endian (Mainframe?)\"/>"
			 + "				<FIELD NAME=\"f13\" POSITION=\"114\" LENGTH=\"4\" TYPE=\"Binary Integer Big Endian (Mainframe?)\"/>"
			 + "			</FIELDS>"
			 + "		</RECORD>"
			 + "		<RECORD RECORDNAME=\"VbRec3\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" FILESTRUCTURE=\"Default\" STYLE=\"0\" RECORDTYPE=\"RecordLayout\" LIST=\"Y\" PARENT=\"VbRec1\" QUOTE=\"\" RecSep=\"default\">"
			 + "			<FIELDS>"
			 + "				<FIELD NAME=\"f11\" POSITION=\"1\" LENGTH=\"4\" TYPE=\"Binary Integer Big Endian (Mainframe?)\"/>"
			 + "				<FIELD NAME=\"f12\" POSITION=\"5\" LENGTH=\"2\" TYPE=\"Binary Integer Big Endian (Mainframe?)\"/>"
			 + "				<FIELD NAME=\"f13\" POSITION=\"260\" LENGTH=\"4\" TYPE=\"Binary Integer Big Endian (Mainframe?)\"/>"
			 + "			</FIELDS>"
			 + "		</RECORD>"
			 + "	</RECORDS>"
			 + "</RECORD>";
		ByteArrayInputStream bs = new ByteArrayInputStream(c.getBytes());
		RecordEditorXmlLoader loader = new RecordEditorXmlLoader();
	       
		layout =  loader.loadCopyBook(bs, "Csv Layout").asLayoutDetail();
		
		main.setup(layout);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Parameters.getInitialisedProperties()
			.put(Parameters.PROPERTY_BIG_FILE_DISK_FLAG, "Y");
	}

	public void testSplitVB51() {
		main.checkLines = false;
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.doCompress.set(false);
		
		
		System.out.println("Test 1:");

		doAdd(0,0);
		doAdd(1,1);
	}
	
	
	public void testSplitVB52() {
		main.checkLines = true;
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.doCompress.set(false);
		
		System.out.println("Test 1:");

		doAdd(0,2);
		doAdd(1,3);
	}
	
	
	public void testSplitVB53() {
		main.checkLines = true;
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.doCompress.set(false);
		
		System.out.println("Test 1:");

		for (int i = 40; i < 65; i++) {
			doAdd(i,i);
		}
	}
	
	public void testSplitVB54() {
		main.checkLines = true;
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.doCompress.set(false);
		
		System.out.println("Test 1:");

		for (int i = 65; i < 90; i++) {
			doAdd(i,i);
		}
	}
	
	public void testSplitVB55() {
		main.checkLines = true;
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.doCompress.set(false);
		
		System.out.println("Test 1:");

		doAdd(TstLargeListVB50.NUMBER_OF_DATA_LINES - 2, 9);
		doAdd(TstLargeListVB50.NUMBER_OF_DATA_LINES - 1, 10);
	}


	private  void doAdd(int pos, int actionNo) {
		main.doAdd(layout, pos, actionNo);
	}

}
