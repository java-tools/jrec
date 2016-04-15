package net.sf.RecordEditor.test.filestorage3;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import junit.framework.TestCase;
import net.sf.JRecord.ByteIO.AbstractByteReader;
import net.sf.JRecord.ByteIO.AbstractByteWriter;
import net.sf.JRecord.ByteIO.ByteIOProvider;
import net.sf.JRecord.Common.Constants;
//import net.sf.JRecord.ByteIO.AbstractByteReader;
//import net.sf.JRecord.ByteIO.AbstractByteWriter;
//import net.sf.JRecord.ByteIO.ByteIOProvider;
//import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.CharLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.Line;
import net.sf.JRecord.External.RecordEditorXmlLoader;

//import net.sf.RecordEditor.utils.fileStorage.AbstractChunkLine;
//import net.sf.RecordEditor.utils.fileStorage.DataStoreLarge;
//import net.sf.RecordEditor.utils.fileStorage.FileDetails;
import net.sf.RecordEditor.test.filestorage1.Code;
import net.sf.RecordEditor.utils.fileStorage.DataStoreLarge;
import net.sf.RecordEditor.utils.fileStorage.FileDetails;
import net.sf.RecordEditor.utils.fileStorage.IChunkLine;
import net.sf.RecordEditor.utils.fileStorage.IFileChunk;
import net.sf.RecordEditor.utils.fileStorage.IRecordStore;

public class TstLargeListVB60   {

	public static final int NUMBER_OF_DATA_LINES = 2500;
	private LayoutDetail layout;

	private AbstractLine[] dataLines = new AbstractLine[NUMBER_OF_DATA_LINES];


	private ArrayList<AbstractLine> items = new ArrayList<AbstractLine>();

	@SuppressWarnings("rawtypes")
	private DataStoreLarge<IChunkLine<IFileChunk<IChunkLine, IRecordStore>>, IRecordStore> fc;
	private String lastAction = "";

	public boolean checkLines = false;

	public TstLargeListVB60(int fileStructure) throws Exception {
		
		if (fileStructure == Constants.IO_BIN_TEXT) {
			setup("<?xml version=\"1.0\" ?>"
					 + "<RECORD RECORDNAME=\"VbFile1\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\"  FILESTRUCTURE=\"" + fileStructure + "\"  STYLE=\"0\" RECORDTYPE=\"GroupOfRecords\" LIST=\"Y\" QUOTE=\"\" RecSep=\"default\">"
					 + "	<RECORDS>"
					 + "		<RECORD RECORDNAME=\"VbRec1\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" FILESTRUCTURE=\"Default\" STYLE=\"0\" RECORDTYPE=\"RecordLayout\" LIST=\"Y\" PARENT=\"VbRec1\" QUOTE=\"\" RecSep=\"default\">"
					 + "			<FIELDS>"
					 + "				<FIELD NAME=\"f11\" POSITION=\"1\" LENGTH=\"6\" TYPE=\"Num (Right Justified zero padded)\"/>"
					 + "				<FIELD NAME=\"f12\" POSITION=\"7\" LENGTH=\"3\" TYPE=\"Num (Right Justified zero padded)\"/>"
					 + "				<FIELD NAME=\"f13\" POSITION=\"10\" LENGTH=\"6\" TYPE=\"Num (Right Justified zero padded)\"/>"
					 + "			</FIELDS>"
					 + "		</RECORD>"
					 + "		<RECORD RECORDNAME=\"VbRec2\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\"  FILESTRUCTURE=\"Default\" STYLE=\"0\" RECORDTYPE=\"RecordLayout\" LIST=\"Y\" PARENT=\"VbRec1\" QUOTE=\"\" RecSep=\"default\">"
					 + "			<FIELDS>"
					 + "				<FIELD NAME=\"f11\" POSITION=\"1\" LENGTH=\"6\" TYPE=\"Num (Right Justified zero padded)\"/>"
					 + "				<FIELD NAME=\"f12\" POSITION=\"7\" LENGTH=\"3\" TYPE=\"Num (Right Justified zero padded)\"/>"
					 + "				<FIELD NAME=\"f13\" POSITION=\"24\" LENGTH=\"6\" TYPE=\"Num (Right Justified zero padded)\"/>"
					 + "			</FIELDS>"
					 + "		</RECORD>"
					 + "		<RECORD RECORDNAME=\"VbRec3\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\"  FILESTRUCTURE=\"Default\" STYLE=\"0\" RECORDTYPE=\"RecordLayout\" LIST=\"Y\" PARENT=\"VbRec1\" QUOTE=\"\" RecSep=\"default\">"
					 + "			<FIELDS>"
					 + "				<FIELD NAME=\"f11\" POSITION=\"1\" LENGTH=\"6\" TYPE=\"Num (Right Justified zero padded)\"/>"
					 + "				<FIELD NAME=\"f12\" POSITION=\"7\" LENGTH=\"3\" TYPE=\"Num (Right Justified zero padded)\"/>"
					 + "				<FIELD NAME=\"f13\" POSITION=\"36\" LENGTH=\"6\" TYPE=\"Num (Right Justified zero padded)\"/>"
					 + "			</FIELDS>"
					 + "		</RECORD>"
					 + "	</RECORDS>"
					 + "</RECORD>");
		} else {
			setup("<?xml version=\"1.0\" ?>"
				 + "<RECORD RECORDNAME=\"VbFile1\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" FILESTRUCTURE=\"" + fileStructure + "\" STYLE=\"0\" RECORDTYPE=\"GroupOfRecords\" LIST=\"Y\" QUOTE=\"\" RecSep=\"default\">"
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
				 + "</RECORD>");
		}
	}
	
	private void setup(String c) throws Exception{
		ByteArrayInputStream bs = new ByteArrayInputStream(c.getBytes());
		RecordEditorXmlLoader loader = new RecordEditorXmlLoader();
	      
		layout =  loader.loadCopyBook(bs, "Csv Layout").asLayoutDetail();
		
		setup(layout);
		
	}
	
	public void setup(LayoutDetail l) {
		int rec;
		
		layout = l;
		try {
			Random random = new Random(1331);
			
			for (int i = 0; i < dataLines.length; i++) {
				rec = Math.abs(random.nextInt()) % 3;

				if (layout.getFileStructure() == Constants.IO_UNICODE_TEXT) {
					dataLines[i] = new CharLine(layout, "");
				} else {
					dataLines[i] = new Line(layout);
				}
				dataLines[i].setField(rec, 0, new Long(Math.abs(random.nextLong()) % 100000));
				dataLines[i].setField(rec, 1, new Long(Math.abs(random.nextLong()) % 1000));
				dataLines[i].setField(rec, 2, new Long(Math.abs(random.nextLong()) % 100000));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//static Line[] hold;
	private  void doAction(Random random, int actionNo) {
		lastAction = Code.doAction(dataLines, items, fc, checkLines, random, actionNo);
	}



	public void tstMultipleChanges(
			boolean check, String desc, long seed, int firstLine, int numActions)
	throws IOException {
		Random random = new Random(seed);
		checkLines = false;
		
		createData();
		
		System.out.println();
		System.out.println(desc);
		
		if (check) {
			HashSet<Integer> ignore = new HashSet<Integer>();
			AbstractLine[] l1 = new AbstractLine[dataLines.length];
			IChunkLine[] l2 = new IChunkLine[dataLines.length];
			
			for (int i = 0; i < dataLines.length; i++) {
				l1[i] = items.get(i);
				l2[i] = (IChunkLine)fc.get(i);
			}
			for (int i = 0; i < numActions; i++) {
				doAction(random, i);
				Code.doAction(dataLines, items, fc, checkLines, random, i, ignore);
			}
			
			for (int i = 0; i < dataLines.length; i++) {

				if (l2[i].getChunkLine() >= l2[i].getChunk().getCount()
				||  Code.compare(l1[i].getData(), l2[i].getData())
				||  Code.compare(items.get(l2[i].getActualLine()).getData(), l2[i].getData())
				) {
				} else {
					for (int j = Math.max(0, i-2); j < Math.min(i+9, dataLines.length - 1); j++) {
						System.out.print(" == " + j + " " + l2[j].getActualLine() + " " + fc.size());
						Code.writeByteArray(l1[j].getData());
						Code.writeByteArray(l2[j].getData());
						Code.writeByteArray(items.get(l2[j].getActualLine()).getData());
						System.out.println();
					}
					TestCase.assertTrue("Line: " + i, false);
				}
			}

		} else {
			for (int i = 0; i < numActions; i++) {
				doAction(random, i);
			}
		}
		
		TestCase.assertTrue(desc + " " + " Seed: " + seed,
				Code.check(lastAction, items, fc));
	}

	@SuppressWarnings("unchecked")
	private void createData() throws IOException {
		ByteIOProvider ioProvider = ByteIOProvider.getInstance();
		AbstractByteWriter writer = ioProvider.getByteWriter(layout.getFileStructure());
		AbstractByteReader reader =  ioProvider.getByteReader(layout.getFileStructure());
		String tstFile = Code.recordEditorOutputDir + "SampleFiles/tstVB.bin";
		byte[] b;
		
		
		items = new ArrayList<AbstractLine>();
		
		writer.open(tstFile);
		
		for (int i = 0; i < dataLines.length; i++) {
			items.add((Line) dataLines[i].getNewDataLine()); 
			writer.write(dataLines[i].getData());
		}
		writer.close();

		reader.open(tstFile);
		fc = new DataStoreLarge(layout, FileDetails.VARIABLE_LENGTH_BASEFILE,
				layout.getMaximumRecordLength(), reader, tstFile);
		
		while ((b = reader.read()) != null) {
			fc.add(new Line(layout, b));
		}
		
		reader.close();
		
		fc.getTempLineRE(0);
	}

}
