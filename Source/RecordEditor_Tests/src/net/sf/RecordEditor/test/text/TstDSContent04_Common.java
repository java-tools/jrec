package net.sf.RecordEditor.test.text;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.GapContent;
import javax.swing.text.PlainDocument;
import javax.swing.text.Position;

import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.Line;
import net.sf.RecordEditor.re.file.DataStoreContent;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.file.textDocument.FileDocument3;
import net.sf.RecordEditor.re.file.textDocument.FileDocument5;
import net.sf.RecordEditor.test.TstConstants;
import net.sf.RecordEditor.utils.fileStorage.IDataStorePosition;
import junit.framework.TestCase;

public class TstDSContent04_Common  {

	private static String CHARS = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ 1234567890.;$"
					    + "         \n\n\n\n\n\n\n\n\n\n";
	private static String[] STRINGS = new String[300];
	static {
		Random r = new Random(1332345);
		int count, code, idx;
		StringBuilder b;
		for (int i = 0; i <STRINGS.length; i++) {
			code = r.nextInt(315);
			count = code - 15;
			if (code < 5) {
				count = 1;
			} else if (code < 15) {
				count = (code - 5) / 2;
			}
			b = new StringBuilder();
			for (int j = 0; j < count; j++) {
				idx = r.nextInt(CHARS.length());
				b.append(CHARS.charAt(idx));
			}
			STRINGS[i] = b.toString();
		}
	}

	private Random r = new Random();
	private FileView v;
//	private GapContent gapContent;
	public boolean testPosition = false;
	private ArrayList<IDataStorePosition> dsPos = new ArrayList<IDataStorePosition>(40);
	private Position[] gapPos;

	int[] origOffset;
	int[] origLineOffset;
	int[] origLineNo;

	final int dsType, inc;
	final boolean usedDoc3;
	
	DataStoreContent dsc;
	Document stdDoc, tstDoc;
	
	

	public TstDSContent04_Common(int dsType, boolean usedDoc3, int seedInc) {
		super();
		this.dsType = dsType;
		this.usedDoc3 = usedDoc3;
		this.inc = seedInc;
	}

	private void setUp(int sizeType) throws Exception {
		
		GapContent gapContent;

		v = TstConstants.getSalesCsvFile(dsType, sizeType);
		dsc = v.asDocumentContent();
		gapContent = new GapContent(0);
		//iL = gapContent.length();
		LayoutDetail l = (LayoutDetail) v.getLayout();

		int count = v.getRowCount();

		for (int j = 0; j < 5; j++) {
			for (int i = 0; i < count; i++) {
				v.add(new Line(l, v.getLine(i).getData().clone()));
			}
		}
		gapContent.insertString(0, dsc.getString(0, dsc.length()));
		
		stdDoc = new PlainDocument(gapContent);
		
		if (usedDoc3) {
			tstDoc = new FileDocument3(dsc, false);
		} else {
			tstDoc = new FileDocument5(dsc, false);
		}
	}


	public final void runTest(int seed, int numTests, int sizeType)  throws Exception  {
		r.setSeed(seed + 1221 + inc);
		setUp(sizeType);

		for (int i = 0; i < 15; i++) {
			addText(i * -1 - 1);
		}

		for (int i = 0; i < numTests; i++) {
			runATest(i);
			if (i % 100 == 0) System.out.print('.');
		}
	}

	private void runATest(int i)  throws BadLocationException {
//		if (i % 15 == 0) System.out.println();
//		System.out.print("Test " + i);
		
//		if (i == 82) {
//			System.out.print('*');
//		}
		
		switch (r.nextInt(5)) {
		case 0:
		case 1:
		case 2:
//			System.out.print(" add ");
			addText(i);
			break;
		case 3:
		case 4:
//			System.out.print(" del ");
			delText(i);
			break;
		}

		TestCase.assertEquals("Length "+ i, stdDoc.getLength(), tstDoc.getLength());

		if (i % 200 == 0) { 
			TestCase.assertEquals("Full Compare "+ i, stdDoc.getText(0, stdDoc.getLength() -1), tstDoc.getText(0, tstDoc.getLength() - 1));
		}
	}

	private void addText(int i) throws BadLocationException {
		String s = STRINGS[r.nextInt(STRINGS.length - 1)];
		int cs, cl;
		int pos = r.nextInt(stdDoc.getLength() - 10);
		if (pos > stdDoc.getLength() + 9) {
			pos = stdDoc.getLength() - 1;
		} else if (pos > stdDoc.getLength() - 1) {
			pos = 0;
		}
		cs = Math.max(0, pos - 300);
		cl = Math.min(stdDoc.getLength() - 2, pos + 300 + s.length()) - cs;



		setupPositions(i, cs, cs);

		//dsc.printPositions(-987);
		stdDoc.insertString(pos, s, null);
		tstDoc.insertString(pos, s, null);


		//System.out.println("%% " + gapContent.length() + " " + dsc.length() + " / " + cs + " " + cl);
		TestCase.assertEquals("Insert "+ i,
				stdDoc.getText(cs, cl),
				tstDoc.getText(cs, cl));

		checkPositions(i, "Insert ", pos, s.length());
	}

	private void delText(int i) throws BadLocationException {
		int cs, cl;
		int pos = r.nextInt(stdDoc.getLength() - 20);
		int numToDelete = 1 + r.nextInt(17);
		if (pos > stdDoc.getLength()) {
			pos = stdDoc.getLength() - 1;
		} else if (pos > stdDoc.getLength() - 1) {
			pos = 0;
		}
		//len = Math.min(190, gapContent.length() - pos);
		cs = Math.max(0, pos - 300);
		cl = Math.min(stdDoc.getLength() - 1 - numToDelete, pos + 300) - cs;
		setupPositions(i, cs, cs + cl);

		if ( i== 336) {
			System.out.print(' ');
		}
		stdDoc.remove(pos, numToDelete);
		tstDoc.remove(pos, numToDelete);

//		try {
			String t1 = stdDoc.getText(cs, cl);
			String t2 = tstDoc.getText(cs, cl);
			
			if (! t1.equals(t2)) {
				t2 = tstDoc.getText(cs, cl);
			}
			TestCase.assertEquals("Delete "+ i, t1, t2);
//		} catch (Exception e) {
//			System.out.print(">> " + cs + " " + cl + " " + gapContent.length() );
//		}

		checkPositions(i, "Delete ", pos, cl);

	}


	private void setupPositions(int idx, int start, int end) throws BadLocationException {

		if (testPosition) {
			IDataStorePosition startPos, endPos, p;

			dsPos.clear();
			startPos = (IDataStorePosition) tstDoc.createPosition(start);
			endPos = startPos;
			int st = Math.max(0, startPos.getLineNumberRE() - 4);
			int en = Math.min(v.getRowCount() - 1, endPos.getLineNumberRE() + 4);

			if (idx % 100 == 0) {
				dsPos.clear();
				st = 1;
				en = v.getRowCount() - 1;
			} else if (idx % 5 == 1 || idx < 0) {
				dsPos.clear();
			}
			dsPos.add(startPos);
			if (start != end) {
				endPos = (IDataStorePosition) tstDoc.createPosition(end);
				dsPos.add(endPos);
			}

			int off = -1;

			for (int i = st; i <= en; i++) {
				p = createPos(off + 1);

				dsPos.add(p);
				dsPos.add(createPos(p.getOffset() + 1));
				dsPos.add(createPos(p.getOffset() + p.getLineRE().getData().length / 3));
				dsPos.add(createPos(p.getOffset() + p.getLineRE().getData().length * 2 / 3));
				dsPos.add(createPos(p.getOffset() + p.getLineRE().getData().length - 1));

				off = p.getOffset() + p.getLineRE().getData().length;
				if (off < tstDoc.getLength()) {
					dsPos.add(createPos(off));
				}
				
			}

			origOffset = new int[dsPos.size()];
			origLineOffset = new int[dsPos.size()];
			origLineNo = new int[dsPos.size()];

			gapPos = new Position[dsPos.size()];
			for (int i = 0; i < dsPos.size(); i++) {
				try {
				p = dsPos.get(i);

				origOffset[i] = p.getOffset();
				origLineOffset[i] = p.getPositionInLineRE();
				origLineNo[i] = p.getLineNumberRE();
				gapPos[i] = stdDoc.createPosition(p.getOffset());
				} catch (Exception e) {
					System.out.print("\t" + i);
				}
			}
		}
	}

	private IDataStorePosition createPos(int where) throws BadLocationException {
		IDataStorePosition ret = (IDataStorePosition) tstDoc.createPosition(where);

		if (ret == null) {
			ret = (IDataStorePosition) tstDoc.createPosition(where);
		}
		return ret;
	}


	private void checkPositions(int idx, String type, int where, int len) {


		if (testPosition) {
			IDataStorePosition pos;
			for (int i = 0; i < dsPos.size(); i++) {
				pos = dsPos.get(i);
				if (gapPos[i].getOffset() != pos.getOffset()
				&& ((! "Delete ".equals(type))
				||  origOffset[i] < where ||  origOffset[i] > where + len)
				) {
					dsc.printPositions(origOffset[i] - 300, origOffset[i] + 400);

					System.out.println( ">" + type + "< " + (! "Delete ".equals(type))
							+ "|| " + where + " ||" + (origOffset[i] < where) + "||" + (origOffset[i] > where + len));
					System.out.println();
					System.out.println(type + " " + idx+ ", " + i  + " / " + where + " " + len
							+ " | " + pos.getLineNumberRE() + " " + pos.getPositionInLineRE()
							+ " | " + pos.getOffset() + " ~ " + gapPos[i].getOffset()
							);
					System.out.println(" ~~>> " + i + "} " + pos.getLineNumberRE() + " " + pos.getPositionInLineRE()
							+ " " + pos.getOffset() + " ~~ " + gapPos[i].getOffset()
							+ " / " + origOffset[i] + " " + origLineNo[i] + " " + origLineOffset[i]);

					TestCase.assertEquals("Pos check: " + idx + ", " + i, gapPos[i].getOffset(), pos.getOffset());
				}
			}
			String s = type + " " + idx + " where: " + where + " " + len;
			TestCase.assertTrue("Check positions in correct sequence: " +  s , dsc.checkPositionSequence(s));
		}
	}
}
