package net.sf.RecordEditor.test.filestorage1;

import java.io.ByteArrayInputStream;

import junit.framework.TestCase;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.RecordEditorXmlLoader;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.fileStorage.FileDetails;
import net.sf.RecordEditor.utils.params.Parameters;

public class TstLargeListVB61  extends TestCase {

	private TstLargeListVB50 main = new TstLargeListVB50();
	private LayoutDetail layout;

	public TstLargeListVB61() throws Exception {
		String c = "<?xml version=\"1.0\" ?>"
				 + "<RECORD RECORDNAME=\"VbFile1\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\"  FONTNAME=\"utf8\" FILESTRUCTURE=\"Text_Unicode\"  STYLE=\"0\" RECORDTYPE=\"GroupOfRecords\" LIST=\"Y\" QUOTE=\"\" RecSep=\"default\">"
				 + "	<RECORDS>"
				 + "		<RECORD RECORDNAME=\"VbRec1\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" FONTNAME=\"utf8\" FILESTRUCTURE=\"Default\" STYLE=\"0\" RECORDTYPE=\"RecordLayout\" LIST=\"Y\" PARENT=\"VbRec1\" QUOTE=\"\" RecSep=\"default\">"
				 + "			<FIELDS>"
				 + "				<FIELD NAME=\"f11\" POSITION=\"1\" LENGTH=\"6\" TYPE=\"Num (Right Justified zero padded)\"/>"
				 + "				<FIELD NAME=\"f12\" POSITION=\"7\" LENGTH=\"3\" TYPE=\"Num (Right Justified zero padded)\"/>"
				 + "				<FIELD NAME=\"f13\" POSITION=\"10\" LENGTH=\"6\" TYPE=\"Num (Right Justified zero padded)\"/>"
				 + "			</FIELDS>"
				 + "		</RECORD>"
				 + "		<RECORD RECORDNAME=\"VbRec2\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" FONTNAME=\"utf8\" FILESTRUCTURE=\"Default\" STYLE=\"0\" RECORDTYPE=\"RecordLayout\" LIST=\"Y\" PARENT=\"VbRec1\" QUOTE=\"\" RecSep=\"default\">"
				 + "			<FIELDS>"
				 + "				<FIELD NAME=\"f11\" POSITION=\"1\" LENGTH=\"6\" TYPE=\"Num (Right Justified zero padded)\"/>"
				 + "				<FIELD NAME=\"f12\" POSITION=\"7\" LENGTH=\"3\" TYPE=\"Num (Right Justified zero padded)\"/>"
				 + "				<FIELD NAME=\"f13\" POSITION=\"24\" LENGTH=\"6\" TYPE=\"Num (Right Justified zero padded)\"/>"
				 + "			</FIELDS>"
				 + "		</RECORD>"
				 + "		<RECORD RECORDNAME=\"VbRec3\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" FONTNAME=\"utf8\" FILESTRUCTURE=\"Default\" STYLE=\"0\" RECORDTYPE=\"RecordLayout\" LIST=\"Y\" PARENT=\"VbRec1\" QUOTE=\"\" RecSep=\"default\">"
				 + "			<FIELDS>"
				 + "				<FIELD NAME=\"f11\" POSITION=\"1\" LENGTH=\"6\" TYPE=\"Num (Right Justified zero padded)\"/>"
				 + "				<FIELD NAME=\"f12\" POSITION=\"7\" LENGTH=\"3\" TYPE=\"Num (Right Justified zero padded)\"/>"
				 + "				<FIELD NAME=\"f13\" POSITION=\"36\" LENGTH=\"6\" TYPE=\"Num (Right Justified zero padded)\"/>"
				 + "			</FIELDS>"
				 + "		</RECORD>"
				 + "	</RECORDS>"
				 + "</RECORD>";
		ByteArrayInputStream bs = new ByteArrayInputStream(c.getBytes());
		RecordEditorXmlLoader loader = new RecordEditorXmlLoader();
	       
		layout =  loader.loadCopyBook(bs, "Csv Layout").asLayoutDetail();
		
		main.setup(layout);
	}
	
	public void testCharLine611() {
		main.checkLines = false;
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.agressiveCompress.set(false);
		
		System.out.println("Test 1:");
		tst("CharLine 611.1.1 ", 1, 0, 1000);
		tst("CharLine 611.1.2 ", 12, 0, 1000);
		tst("CharLine 611.1.3 ", 15, 0, 1000);
		tst("CharLine 611.1.4 ", 17, 0, 1000);
		
		tst("CharLine 611.2.1 ", 121, 0, 400);
		tst("CharLine 611.2.2 ", 1213, 0, 800);
		tst("CharLine 611.2.3 ", 1215, 0, 400);
		tst("CharLine 611.2.4 ", 1217, 0, 800);
	}
	
	public void testCharLine612() {
		main.checkLines = false;
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.agressiveCompress.set(false);
		
		tst("CharLine 612.2.1 ", System.nanoTime(), 0, 1000);
		tst("CharLine 612.2.2 ", System.nanoTime() + 12, 0, 1000);
		tst("CharLine 612.2.3 ", System.nanoTime() + 15, 0, 500);
		tst("CharLine 612.2.4 ", System.nanoTime() + 17, 0, 1000);
		
		tst("CharLine 612.3.1 ", System.nanoTime() + 121, 0, 400);
		tst("CharLine 612.3.2 ", System.nanoTime() + 1213, 0, 800);
		tst("CharLine 612.3.3 ", System.nanoTime() + 1215, 0, 400);
		tst("CharLine 612.3.4 ", System.nanoTime() + 1217, 0, 800);
	}
	
	public void testCharLine613() {
		main.checkLines = true;
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.agressiveCompress.set(false);
		
		System.out.println("Test 3:");
		tst("CharLine 613.1.1 ", 1, 0, 1000);
		tst("CharLine 613.1.2 ", 12, 0, 1000);
		tst("CharLine 613.1.3 ", 15, 0, 1000);
		tst("CharLine 613.1.4 ", 17, 0, 1000);
		
		tst("CharLine 613.2.1 ", 121, 0, 400);
		tst("CharLine 613.2.2 ", 1213, 0, 800);
		tst("CharLine 613.2.3 ", 1215, 0, 400);
		tst("CharLine 613.2.4 ", 1217, 0, 800);
	}
	
	public void testCharLine614() {
		main.checkLines = true;
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.agressiveCompress.set(false);
		
		tst("CharLine 614.2.1 ", System.nanoTime(), 0, 1000);
		tst("CharLine 614.2.2 ", System.nanoTime() + 12, 0, 1000);
		tst("CharLine 614.2.3 ", System.nanoTime() + 15, 0, 500);
		tst("CharLine 614.2.4 ", System.nanoTime() + 17, 0, 1000);
		
		tst("CharLine 614.3.1 ", System.nanoTime() + 121, 0, 400);
		tst("CharLine 614.3.2 ", System.nanoTime() + 1213, 0, 800);
		tst("CharLine 614.3.3 ", System.nanoTime() + 1215, 0, 400);
		tst("CharLine 614.3.4 ", System.nanoTime() + 1217, 0, 800);
	}

	public void testCharLine615() {

		main.checkLines = false;
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "100");
		Common.OPTIONS.agressiveCompress.set(false);
		
		System.out.println("Test 1:");
		tst("CharLine 615.1.1 ", 1, 0, 1000);
		tst("CharLine 615.1.2 ", 12, 0, 1000);
		tst("CharLine 615.1.3 ", 15, 0, 1000);
		tst("CharLine 615.1.4 ", 17, 0, 1000);
		
		tst("CharLine 615.2.1 ", 121, 0, 400);
		tst("CharLine 615.2.2 ", 1213, 0, 800);
		tst("CharLine 615.2.3 ", 1215, 0, 400);
		tst("CharLine 615.2.4 ", 1217, 0, 800);
	}
	
	public void testCharLine616() {
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "100");
		Common.OPTIONS.agressiveCompress.set(false);
		main.checkLines = false;

		tst("CharLine 616.2.1 ", System.nanoTime(), 0, 1000);
		tst("CharLine 616.2.2 ", System.nanoTime() + 12, 0, 1000);
		tst("CharLine 616.2.3 ", System.nanoTime() + 15, 0, 500);
		tst("CharLine 616.2.4 ", System.nanoTime() + 17, 0, 1000);
		
		tst("CharLine 616.3.1 ", System.nanoTime() + 121, 0, 400);
		tst("CharLine 616.3.2 ", System.nanoTime() + 1213, 0, 800);
		tst("CharLine 616.3.3 ", System.nanoTime() + 1215, 0, 400);
		tst("CharLine 616.3.4 ", System.nanoTime() + 1217, 0, 800);
	}

	public void testCharLine618() {
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "100");
		Common.OPTIONS.agressiveCompress.set(false);
		TstLargeListVB1 main = new TstLargeListVB1();
		main.checkLines = true;
		tst("CharLine 618.2.1 ", System.nanoTime(), 0, 1000);
		tst("CharLine 618.2.2 ", System.nanoTime() + 12, 0, 1000);
		tst("CharLine 618.2.3 ", System.nanoTime() + 15, 0, 500);
		tst("CharLine 618.2.4 ", System.nanoTime() + 17, 0, 1000);
		
		tst("CharLine 618.3.1 ", System.nanoTime() + 121, 0, 400);
		tst("CharLine 618.3.2 ", System.nanoTime() + 1213, 0, 800);
		tst("CharLine 618.3.3 ", System.nanoTime() + 1215, 0, 400);
		tst("CharLine 618.3.4 ", System.nanoTime() + 1217, 0, 800);
	}

	public void testCharLine617() {
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "100");
		Common.OPTIONS.agressiveCompress.set(false);
		TstLargeListVB1 main = new TstLargeListVB1();
		main.checkLines = true;
		
		System.out.println("Test 7:");
		tst("CharLine 617.1.1 ", 1, 0, 1000);
		tst("CharLine 617.1.2 ", 12, 0, 1000);
		tst("CharLine 617.1.3 ", 15, 0, 1000);
		tst("CharLine 617.1.4 ", 17, 0, 1000);
		
		tst("CharLine 617.2.1 ", 121, 0, 400);
		tst("CharLine 617.2.2 ", 1213, 0, 800);
		tst("CharLine 617.2.3 ", 1215, 0, 400);
		tst("CharLine 617.2.4 ", 1217, 0, 800);
	}

	public void tst(String desc, long seed, int firstLine, int numActions) {
		main.tst(FileDetails.CHAR_LINE, desc, seed, firstLine, numActions, layout.getMaximumRecordLength());
	}

}
