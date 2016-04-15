package net.sf.RecordEditor.test.filestorage1;

import java.io.ByteArrayInputStream;

import junit.framework.TestCase;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.RecordEditorXmlLoader;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.params.Parameters;

public class TstLargeListVB51  extends TestCase {

	private TstLargeListVB50 main = new TstLargeListVB50();
	private LayoutDetail layout;

	public TstLargeListVB51() throws Exception {
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
				 + "				<FIELD NAME=\"f13\" POSITION=\"14\" LENGTH=\"4\" TYPE=\"Binary Integer Big Endian (Mainframe?)\"/>"
				 + "			</FIELDS>"
				 + "		</RECORD>"
				 + "		<RECORD RECORDNAME=\"VbRec3\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" FILESTRUCTURE=\"Default\" STYLE=\"0\" RECORDTYPE=\"RecordLayout\" LIST=\"Y\" PARENT=\"VbRec1\" QUOTE=\"\" RecSep=\"default\">"
				 + "			<FIELDS>"
				 + "				<FIELD NAME=\"f11\" POSITION=\"1\" LENGTH=\"4\" TYPE=\"Binary Integer Big Endian (Mainframe?)\"/>"
				 + "				<FIELD NAME=\"f12\" POSITION=\"5\" LENGTH=\"2\" TYPE=\"Binary Integer Big Endian (Mainframe?)\"/>"
				 + "				<FIELD NAME=\"f13\" POSITION=\"20\" LENGTH=\"4\" TYPE=\"Binary Integer Big Endian (Mainframe?)\"/>"
				 + "			</FIELDS>"
				 + "		</RECORD>"
				 + "	</RECORDS>"
				 + "</RECORD>";
		ByteArrayInputStream bs = new ByteArrayInputStream(c.getBytes());
		RecordEditorXmlLoader loader = new RecordEditorXmlLoader();
	       
		layout =  loader.loadCopyBook(bs, "Csv Layout").asLayoutDetail();
		
		main.setup(layout);
	}
	
	public void testVB51() {
		main.checkLines = false;
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.agressiveCompress.set(false);
		Common.OPTIONS.doCompress.set(false);
		System.out.println("Test 1:");
		tst("VB 51.1.1 ", 1, 0, 1000);
		tst("VB 51.1.2 ", 12, 0, 1000);
		tst("VB 51.1.3 ", 15, 0, 1000);
		tst("VB 51.1.4 ", 17, 0, 1000);
		
		tst("VB 51.2.1 ", 121, 0, 400);
		tst("VB 51.2.2 ", 1213, 0, 800);
		tst("VB 51.2.3 ", 1215, 0, 400);
		tst("VB 51.2.4 ", 1217, 0, 800);
	}
	
	public void testVB52() {
		main.checkLines = false;
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.agressiveCompress.set(false);
		Common.OPTIONS.doCompress.set(false);
		tst("VB 52.2.1 ", System.nanoTime(), 0, 1000);
		tst("VB 52.2.2 ", System.nanoTime() + 12, 0, 1000);
		tst("VB 52.2.3 ", System.nanoTime() + 15, 0, 500);
		tst("VB 52.2.4 ", System.nanoTime() + 17, 0, 1000);
		
		tst("VB 52.3.1 ", System.nanoTime() + 121, 0, 400);
		tst("VB 52.3.2 ", System.nanoTime() + 1213, 0, 800);
		tst("VB 52.3.3 ", System.nanoTime() + 1215, 0, 400);
		tst("VB 52.3.4 ", System.nanoTime() + 1217, 0, 800);
	}
	
	public void testVB53() {
		main.checkLines = true;
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.agressiveCompress.set(false);
		Common.OPTIONS.doCompress.set(false);
		System.out.println("Test 3:");
		tst("VB 53.1.1 ", 1, 0, 1000);
		tst("VB 53.1.2 ", 12, 0, 1000);
		tst("VB 53.1.3 ", 15, 0, 1000);
		tst("VB 53.1.4 ", 17, 0, 1000);
		
		tst("VB 53.2.1 ", 121, 0, 400);
		tst("VB 53.2.2 ", 1213, 0, 800);
		tst("VB 53.2.3 ", 1215, 0, 400);
		tst("VB 53.2.4 ", 1217, 0, 800);
	}
	
	public void testVB54() {
		main.checkLines = true;
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.agressiveCompress.set(false);
		Common.OPTIONS.doCompress.set(false);
		tst("VB 54.2.1 ", System.nanoTime(), 0, 1000);
		tst("VB 54.2.2 ", System.nanoTime() + 12, 0, 1000);
		tst("VB 54.2.3 ", System.nanoTime() + 15, 0, 500);
		tst("VB 54.2.4 ", System.nanoTime() + 17, 0, 1000);
		
		tst("VB 54.3.1 ", System.nanoTime() + 121, 0, 400);
		tst("VB 54.3.2 ", System.nanoTime() + 1213, 0, 800);
		tst("VB 54.3.3 ", System.nanoTime() + 1215, 0, 400);
		tst("VB 54.3.4 ", System.nanoTime() + 1217, 0, 800);
	}

	public void testVB55() {
		Common.OPTIONS.agressiveCompress.set(false);
		Common.OPTIONS.doCompress.set(false);

		main.checkLines = false;
		System.out.println("Test 1:");
		tst("VB 55.1.1 ", 1, 0, 1000);
		tst("VB 55.1.2 ", 12, 0, 1000);
		tst("VB 55.1.3 ", 15, 0, 1000);
		tst("VB 55.1.4 ", 17, 0, 1000);
		
		tst("VB 55.2.1 ", 121, 0, 400);
		tst("VB 55.2.2 ", 1213, 0, 800);
		tst("VB 55.2.3 ", 1215, 0, 400);
		tst("VB 55.2.4 ", 1217, 0, 800);
	}
	
	public void testVB56() {
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "100");
		Common.OPTIONS.agressiveCompress.set(false);
		main.checkLines = false;

		tst("VB 56.2.1 ", System.nanoTime(), 0, 1000);
		tst("VB 56.2.2 ", System.nanoTime() + 12, 0, 1000);
		tst("VB 56.2.3 ", System.nanoTime() + 15, 0, 500);
		tst("VB 56.2.4 ", System.nanoTime() + 17, 0, 1000);
		
		tst("VB 56.3.1 ", System.nanoTime() + 121, 0, 400);
		tst("VB 56.3.2 ", System.nanoTime() + 1213, 0, 800);
		tst("VB 56.3.3 ", System.nanoTime() + 1215, 0, 400);
		tst("VB 56.3.4 ", System.nanoTime() + 1217, 0, 800);
	}

	public void testVB58() {
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "100");
		Common.OPTIONS.agressiveCompress.set(false);
		TstLargeListVB1 main = new TstLargeListVB1();
		main.checkLines = true;
		tst("VB 58.2.1 ", System.nanoTime(), 0, 1000);
		tst("VB 58.2.2 ", System.nanoTime() + 12, 0, 1000);
		tst("VB 58.2.3 ", System.nanoTime() + 15, 0, 500);
		tst("VB 58.2.4 ", System.nanoTime() + 17, 0, 1000);
		
		tst("VB 58.3.1 ", System.nanoTime() + 121, 0, 400);
		tst("VB 58.3.2 ", System.nanoTime() + 1213, 0, 800);
		tst("VB 58.3.3 ", System.nanoTime() + 1215, 0, 400);
		tst("VB 58.3.4 ", System.nanoTime() + 1217, 0, 800);
	}

	public void testVB57() {
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "100");
		Common.OPTIONS.agressiveCompress.set(false);
		TstLargeListVB1 main = new TstLargeListVB1();
		main.checkLines = true;
		
		System.out.println("Test 7:");
		tst("VB 57.1.1 ", 1, 0, 1000);
		tst("VB 57.1.2 ", 12, 0, 1000);
		tst("VB 57.1.3 ", 15, 0, 1000);
		tst("VB 57.1.4 ", 17, 0, 1000);
		
		tst("VB 57.2.1 ", 121, 0, 400);
		tst("VB 57.2.2 ", 1213, 0, 800);
		tst("VB 57.2.3 ", 1215, 0, 400);
		tst("VB 57.2.4 ", 1217, 0, 800);
	}

	public void tst(String desc, long seed, int firstLine, int numActions) {
		main.tst(desc, seed, firstLine, numActions, layout.getMaximumRecordLength());
	}

}
