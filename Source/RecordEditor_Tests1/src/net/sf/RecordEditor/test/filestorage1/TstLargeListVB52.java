package net.sf.RecordEditor.test.filestorage1;

import java.io.ByteArrayInputStream;

import junit.framework.TestCase;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.RecordEditorXmlLoader;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.params.Parameters;

public class TstLargeListVB52  extends TestCase {

	private TstLargeListVB50 main = new TstLargeListVB50();
	private LayoutDetail layout;

	public TstLargeListVB52() throws Exception {
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
	
	public void testVB521() {
		main.checkLines = false;
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.agressiveCompress.set(false);
		Common.OPTIONS.doCompress.set(false);
		System.out.println("Test 1:");
		tst("VB 521.1.1 ", 1, 0, 1000);
		tst("VB 521.1.2 ", 12, 0, 1000);
		tst("VB 521.1.3 ", 15, 0, 1000);
		tst("VB 521.1.4 ", 17, 0, 1000);
		
		tst("VB 521.2.1 ", 121, 0, 400);
		tst("VB 521.2.2 ", 1213, 0, 800);
		tst("VB 521.2.3 ", 1215, 0, 400);
		tst("VB 521.2.4 ", 1217, 0, 800);
	}
	
	public void testVB522() {
		main.checkLines = false;
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.agressiveCompress.set(false);
		Common.OPTIONS.doCompress.set(false);
		
		tst("VB 522.2.1 ", System.nanoTime(), 0, 1000);
		tst("VB 522.2.2 ", System.nanoTime() + 12, 0, 1000);
		tst("VB 522.2.3 ", System.nanoTime() + 15, 0, 500);
		tst("VB 522.2.4 ", System.nanoTime() + 17, 0, 1000);
		
		tst("VB 522.3.1 ", System.nanoTime() + 121, 0, 400);
		tst("VB 522.3.2 ", System.nanoTime() + 1213, 0, 800);
		tst("VB 522.3.3 ", System.nanoTime() + 1215, 0, 400);
		tst("VB 522.3.4 ", System.nanoTime() + 1217, 0, 800);
	}
	
	public void testVB523() {
		main.checkLines = true;
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.agressiveCompress.set(false);
		System.out.println("Test 3:");
		tst("VB 523.1.1 ", 1, 0, 1000);
		tst("VB 523.1.2 ", 12, 0, 1000);
		tst("VB 523.1.3 ", 15, 0, 1000);
		tst("VB 523.1.4 ", 17, 0, 1000);
		
		tst("VB 523.2.1 ", 121, 0, 400);
		tst("VB 523.2.2 ", 1213, 0, 800);
		tst("VB 523.2.3 ", 1215, 0, 400);
		tst("VB 523.2.4 ", 1217, 0, 800);
	}
	
	public void testVB524() {
		main.checkLines = true;
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.agressiveCompress.set(false);
		tst("VB 524.2.1 ", System.nanoTime(), 0, 1000);
		tst("VB 524.2.2 ", System.nanoTime() + 12, 0, 1000);
		tst("VB 524.2.3 ", System.nanoTime() + 15, 0, 500);
		tst("VB 524.2.4 ", System.nanoTime() + 17, 0, 1000);
		
		tst("VB 524.3.1 ", System.nanoTime() + 121, 0, 400);
		tst("VB 524.3.2 ", System.nanoTime() + 1213, 0, 800);
		tst("VB 524.3.3 ", System.nanoTime() + 1215, 0, 400);
		tst("VB 524.3.4 ", System.nanoTime() + 1217, 0, 800);
	}

	public void testVB525() {

		Common.OPTIONS.agressiveCompress.set(false);
		main.checkLines = false;
		System.out.println("Test 1:");
		
		tst("VB 525.1.1 ", 1, 0, 1000);
		tst("VB 525.1.2 ", 12, 0, 1000);
		tst("VB 525.1.3 ", 15, 0, 1000);
		tst("VB 525.1.4 ", 17, 0, 1000);
		
		tst("VB 525.2.1 ", 121, 0, 400);
		tst("VB 525.2.2 ", 1213, 0, 800);
		tst("VB 525.2.3 ", 1215, 0, 400);
		tst("VB 525.2.4 ", 1217, 0, 800);
	}
	
	public void testVB526() {
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "100");
		Common.OPTIONS.agressiveCompress.set(false);
		main.checkLines = false;

		tst("VB 526.2.1 ", System.nanoTime(), 0, 1000);
		tst("VB 526.2.2 ", System.nanoTime() + 12, 0, 1000);
		tst("VB 526.2.3 ", System.nanoTime() + 15, 0, 500);
		tst("VB 526.2.4 ", System.nanoTime() + 17, 0, 1000);
		
		tst("VB 526.3.1 ", System.nanoTime() + 121, 0, 400);
		tst("VB 526.3.2 ", System.nanoTime() + 1213, 0, 800);
		tst("VB 526.3.3 ", System.nanoTime() + 1215, 0, 400);
		tst("VB 526.3.4 ", System.nanoTime() + 1217, 0, 800);
	}

	public void testVB528() {
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "100");
		Common.OPTIONS.agressiveCompress.set(false);
		TstLargeListVB1 main = new TstLargeListVB1();
		main.checkLines = true;
		tst("VB 528.2.1 ", System.nanoTime(), 0, 1000);
		tst("VB 528.2.2 ", System.nanoTime() + 12, 0, 1000);
		tst("VB 528.2.3 ", System.nanoTime() + 15, 0, 500);
		tst("VB 528.2.4 ", System.nanoTime() + 17, 0, 1000);
		
		tst("VB 528.3.1 ", System.nanoTime() + 121, 0, 400);
		tst("VB 528.3.2 ", System.nanoTime() + 1213, 0, 800);
		tst("VB 528.3.3 ", System.nanoTime() + 1215, 0, 400);
		tst("VB 528.3.4 ", System.nanoTime() + 1217, 0, 800);
	}

	public void testVB527() {
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "100");
		Common.OPTIONS.agressiveCompress.set(false);
		TstLargeListVB1 main = new TstLargeListVB1();
		main.checkLines = true;
		
		System.out.println("Test 7:");
		tst("VB 527.1.1 ", 1, 0, 1000);
		tst("VB 527.1.2 ", 12, 0, 1000);
		tst("VB 527.1.3 ", 15, 0, 1000);
		tst("VB 527.1.4 ", 17, 0, 1000);
		
		tst("VB 527.2.1 ", 121, 0, 400);
		tst("VB 527.2.2 ", 1213, 0, 800);
		tst("VB 527.2.3 ", 1215, 0, 400);
		tst("VB 527.2.4 ", 1217, 0, 800);
	}

	public void tst(String desc, long seed, int firstLine, int numActions) {
		main.tst(desc, seed, firstLine, numActions, layout.getMaximumRecordLength());
	}

}
