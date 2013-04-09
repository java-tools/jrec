package net.sf.RecordEditor.test.text;

import java.util.ArrayList;

import javax.swing.text.AbstractDocument.Content;
import javax.swing.text.BadLocationException;
import javax.swing.text.GapContent;
import javax.swing.text.Position;

import net.sf.RecordEditor.re.file.DataStoreContent;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.test.TstConstants;
import net.sf.RecordEditor.utils.fileStorage.IDataStorePosition;
import junit.framework.TestCase;


/**
 * Basic Checks on DataStoreContent class
 *
 * @author Bruce Martin
 *
 */
public class TstDSContent01 extends TestCase {

	public void testCreatePosition() throws Exception {
		FileView v = TstConstants.getSalesCsvFile();
		DataStoreContent dsc = v.asDocumentContent();
		IDataStorePosition[] positions = new IDataStorePosition[10];
		int[] origPos = new int[positions.length];
		int offset, diff;
		int pos = 0;

		for (int i = 0; i < positions.length; i++) {
			origPos[i] = pos;
			offset = pos + i;
			positions[i] = dsc.createPosition(offset);
//			System.out.println();
//			System.out.println(" --> " + pos + " " + offset
//					+ " " + v.getLine(i).getFullLine() + " ~~ " + v.getLine(i).getFullLine().length());

			assertEquals("Check Offset, i=" + i, offset, positions[i].getOffset());
			assertEquals("Check Line Number, i=" + i, i, positions[i].getLineNumber());
			assertEquals("Check Line Start, i=" + i, pos, positions[i].getLineStart());
			assertEquals("Check Line pos, i=" + i, i, positions[i].getPositionInLine());
			assertEquals("Check Line, i=" + i, v.getLine(i), positions[i].getLine());

			pos += v.getLine(i).getFullLine().length() + 1;
		}

		int len = v.getLine(2).getFullLine().length() + 1;

		v.getLine(2).setField(0, 0, v.getLine(2).getField(0, 0) + "1234");
		v.fireRowUpdated(2, v.getLine(2));


		for (int i = 0; i < positions.length; i++) {
			offset = origPos[i] + i;
			pos =  origPos[i];

			diff = 4;
			if (i < 2) {
				diff = 0;
			}
			System.out.println();
			System.out.println(" --> " + pos + " " + offset
					+ " " + v.getLine(i).getFullLine() + " ~~ " + v.getLine(i).getFullLine().length());
			assertEquals("Check Line Number, i=" + i, i, positions[i].getLineNumber());
			if (i == 2) {
				assertEquals("Check Offset, i=" + i, offset, positions[i].getOffset());
				assertEquals("Check Line Start, i=" + i, pos, positions[i].getLineStart());
			} else {
				assertEquals("Check Offset, i=" + i, offset + diff, positions[i].getOffset());
				assertEquals("Check Line Start, i=" + i, pos + diff, positions[i].getLineStart());
			}
			assertEquals("Check Line pos, i=" + i, i, positions[i].getPositionInLine());

			assertEquals("Check Line, i=" + i, v.getLine(i), positions[i].getLine());
		}

		int i = 0;

		v.deleteLine(2);

		for (int j = 0; j < positions.length; j++) {
			pos =  origPos[j];

			if (j > 2) {
				pos -= len;
			}
			offset = pos + j;
			System.out.println();
			System.out.println(" --> " + pos  + " " + len + " / " + offset
					+ " " + positions[j].getLineNumber()
					+ " " + positions[j].getPositionInLine()
					+ " " + positions[j].getLineStart());
			if (j != 2) {
				assertEquals("Check Line Number, i=" + i, i, positions[j].getLineNumber());
				assertEquals("Check Line pos, i=" + i, j, positions[j].getPositionInLine());

				assertEquals("Check Line, i=" + i, v.getLine(i), positions[j].getLine());
				assertEquals("Check Line Start, i=" + i, pos, positions[j].getLineStart());
				assertEquals("Check Offset, i=" + i, offset , positions[j].getOffset());
				i += 1;
			}
		}
	}

	public void testGetLinePosition() throws Exception {
		FileView v = TstConstants.getSalesCsvFile();
		DataStoreContent dsc = v.asDocumentContent();
		IDataStorePosition[] positions = new IDataStorePosition[10];
		int[] origPos = new int[positions.length];
		int offset, diff;
		int pos = 0;

		for (int i = 0; i < positions.length; i++) {
			origPos[i] = pos;
			offset = pos;
			positions[i] = dsc.getLinePosition(i);
//			System.out.println();
//			System.out.println(" --> " + pos + " " + offset
//					+ " " + v.getLine(i).getFullLine() + " ~~ " + v.getLine(i).getFullLine().length());

			assertEquals("Check Offset, i=" + i, offset, positions[i].getOffset());
			assertEquals("Check Line Number, i=" + i, i, positions[i].getLineNumber());
			assertEquals("Check Line Start, i=" + i, pos, positions[i].getLineStart());
			assertEquals("Check Line pos, i=" + i, 0, positions[i].getPositionInLine());
			assertEquals("Check Line, i=" + i, v.getLine(i), positions[i].getLine());

			pos += v.getLine(i).getFullLine().length() + 1;
		}

		int len = v.getLine(2).getFullLine().length() + 1;

		v.getLine(2).setField(0, 0, v.getLine(2).getField(0, 0) + "1234");
		v.fireRowUpdated(2, v.getLine(2));


		for (int i = 0; i < positions.length; i++) {
			offset = origPos[i];
			pos =  origPos[i];

			diff = 4;
			if (i < 2) {
				diff = 0;
			}
			System.out.println();
			System.out.println(" --> " + pos + " " + offset
					+ " " + v.getLine(i).getFullLine() + " ~~ " + v.getLine(i).getFullLine().length());
			assertEquals("Check Line Number, i=" + i, i, positions[i].getLineNumber());
			if (i == 2) {
				assertEquals("Check Offset, i=" + i, offset, positions[i].getOffset());
				assertEquals("Check Line Start, i=" + i, pos, positions[i].getLineStart());
			} else {
				assertEquals("Check Offset, i=" + i, offset + diff, positions[i].getOffset());
				assertEquals("Check Line Start, i=" + i, pos + diff, positions[i].getLineStart());
			}
			assertEquals("Check Line pos, i=" + i, 0, positions[i].getPositionInLine());

			assertEquals("Check Line, i=" + i, v.getLine(i), positions[i].getLine());
		}

		int i = 0;

		v.deleteLine(2);

		for (int j = 0; j < positions.length; j++) {
			pos =  origPos[j];

			if (j > 2) {
				pos -= len;
			}
			offset = pos;
			System.out.println();
			System.out.println(" --> " + pos + " " + offset
					+ " " + v.getLine(j).getFullLine() + " ~~ " + v.getLine(j).getFullLine().length());
			if (j != 2) {
				assertEquals("Check Line Number, i=" + i, i, positions[j].getLineNumber());
				assertEquals("Check Line pos, i=" + i, 0, positions[j].getPositionInLine());

				assertEquals("Check Line, i=" + i, v.getLine(i), positions[j].getLine());
				assertEquals("Check Line Start, i=" + i, pos, positions[j].getLineStart());
				assertEquals("Check Offset, i=" + i, offset , positions[j].getOffset());
				i += 1;
			}
		}
	}


	public void testLength() throws Exception {
		FileView v = TstConstants.getSalesCsvFile();
		DataStoreContent dsc = v.asDocumentContent();

		int len = 0;
		for (int i = 0; i < v.getRowCount(); i++) {
			len += v.getLine(i).getFullLine().length() + 1;
		}

		assertEquals("Test Length", len - 1, dsc.length());
	}

	public void testNumberOfLines() throws Exception {
		FileView v = TstConstants.getSalesCsvFile();
		DataStoreContent dsc = v.asDocumentContent();
		assertEquals("Number of lines", v.getRowCount(), dsc.numberOfLines());
	}

	public void testInsertString() throws Exception {
		FileView v = TstConstants.getSalesCsvFile();


		StringBuilder b = new StringBuilder(v.getLine(0).getFullLine());

		for (int i = 1; i < v.getRowCount(); i++) {
			b.append('\n').append(v.getLine(i).getFullLine());
		}

		chkInsert1(b, "1234");
		chkInsert1(b, "21\n34");
		chkInsert1(b, "21\n34\n56");
		chkInsert1(b, "\n21\n34");
		chkInsert1(b, "213\n34567\n");

		chkInsert1(b, "21\n34\n");
		chkInsert1(b, "\n1234");
		chkInsert1(b, "1234\n");

		chkInsert1(b, "\n\n1234");
		chkInsert1(b, "12\n\n34");
		chkInsert1(b, "\n\n1234\n\n");
		chkInsert1(b, "1234\n\n");
		chkInsert1(b, "12\n\n34\n\n");
		chkInsert1(b, "12\n\n34\n\n56");
		chkInsert1(b, "12\n\n34\n\n56\n\n");
		chkInsert1(b, "1\na\n4\nb\n6\nc\n");


		chkInsert1(b, "\n\n\n1234");
		chkInsert1(b, "12\n\n\n34");
		chkInsert1(b, "\n\n\n1234\n\n\n");
		chkInsert1(b, "1234\n\n\n");
		chkInsert1(b, "12\n\n\n34\n\n\n");
		chkInsert1(b, "12\n\n\n34\n\n\n56");
		chkInsert1(b, "12\n\n\n34\n\n\n56\n\n\n");

	}


	private void chkInsert1(StringBuilder b, String what) throws Exception {
		chkInsert(b, 0, what);
		chkInsert(b, 1, what);
		chkInsert(b, 2, what);
		chkInsert(b, 30, what);
		chkInsert(b, 60, what);

		chkInsert(b, b.length() - 3, what);
		chkInsert(b, b.length() - 1, what);
		chkInsert(b, b.length() - 0, what);
	}


	private void chkInsert(StringBuilder b, int offset, String what) throws Exception {
		FileView v = TstConstants.getSalesCsvFile();
		DataStoreContent dsc = v.asDocumentContent();
		GapContent gapContent = new GapContent(0);
		int iL = gapContent.length();
		gapContent.insertString(0, b.toString());

		chkInsert(gapContent, dsc, offset,  what, iL);
	}


	private void chkInsert(Content c1, Content c2, int where, String what,  int diff) throws BadLocationException {


		c1.insertString(where, what);
		c2.insertString(where, what);

		assertEquals(c1.getString(0, c1.length() - diff), c2.getString(0, c2.length()));
	}
	public void testInsertString2() throws Exception {
		FileView v = TstConstants.getSalesCsvFile();


		StringBuilder b = new StringBuilder(v.getLine(0).getFullLine());

		for (int i = 1; i < v.getRowCount(); i++) {
			b.append('\n').append(v.getLine(i).getFullLine());
		}

		chkInsert2(b, "1234");
		chkInsert2(b, "21\n34");
		chkInsert2(b, "21\n34\n56");
		chkInsert2(b, "\n21\n34");
		chkInsert2(b, "213\n34567\n");

		chkInsert2(b, "21\n34\n");
		chkInsert2(b, "\n1234");
		chkInsert2(b, "1234\n");

		chkInsert2(b, "\n\n1234");
		chkInsert2(b, "12\n\n34");
		chkInsert2(b, "\n\n1234\n\n");
		chkInsert2(b, "1234\n\n");
		chkInsert2(b, "12\n\n34\n\n");
		chkInsert2(b, "12\n\n34\n\n56");
		chkInsert2(b, "12\n\n34\n\n56\n\n");
		chkInsert2(b, "1\na\n4\nb\n6\nc\n");


		chkInsert2(b, "\n\n\n1234");
		chkInsert2(b, "12\n\n\n34");
		chkInsert2(b, "\n\n\n1234\n\n\n");
		chkInsert2(b, "1234\n\n\n");
		chkInsert2(b, "12\n\n\n34\n\n\n");
		chkInsert2(b, "12\n\n\n34\n\n\n56");
		chkInsert2(b, "12\n\n\n34\n\n\n56\n\n\n");

	}


	private void chkInsert2(StringBuilder b, String what) throws Exception {
		chkInsert2(b, 0, what);
		chkInsert2(b, 1, what);
		chkInsert2(b, 2, what);
		chkInsert2(b, 30, what);
		chkInsert2(b, 60, what);

		chkInsert2(b, b.length() - 3, what);
		chkInsert2(b, b.length() - 1, what);
		chkInsert2(b, b.length() - 0, what);
	}


	private void chkInsert2(StringBuilder b, int offset, String what) throws Exception {
		FileView v = TstConstants.getSalesCsvFile();
		DataStoreContent dsc = v.asDocumentContent();
		GapContent gapContent = new GapContent(0);
		int iL = gapContent.length();
		gapContent.insertString(0, b.toString());

		chkInsert2(gapContent, dsc, offset,  what, iL, v.getRowCount());
	}


	private void chkInsert2(Content c1, DataStoreContent c2, int where, String what,  int diff, int numLines) throws BadLocationException {
		IDataStorePosition
				p,
				start = c2.createPosition(where);
		//		fin   = c2.createPosition(where + what.l);
		int
				idxSt = Math.max(0, start.getLineNumber() - 3),
				idxEn = Math.min(numLines, start.getLineNumber() + 3);
		int listSize = 7 + 3 * (idxEn - idxSt);
		int[] origOffset;
		int[] origLineOffset;
		int[] origLineNo;

		ArrayList<Position> c1Pos = new ArrayList<Position>(listSize);
		ArrayList<IDataStorePosition> c2Pos = new ArrayList<IDataStorePosition>(listSize);

		//c1Pos.add(c2.createPosition(where));
		//c1Pos.add(c2.createPosition(where + length));
		c2Pos.add(start);


		for (int i = idxSt; i < idxEn; i++) {
			p = c2.getLinePosition(i);
			c2Pos.add(p);
			c2Pos.add(c2.createPosition(p.getOffset() + 1));
			c2Pos.add(c2.createPosition(p.getOffset() + p.getLine().getData().length / 2));
			c2Pos.add(c2.createPosition(p.getOffset() + p.getLine().getData().length - 1));
			c2Pos.add(c2.createPosition(p.getOffset() + p.getLine().getData().length));
		}

		//c2.printPositions(-1331);

		origOffset = new int[c2Pos.size()];
		origLineOffset = new int[c2Pos.size()];
		origLineNo = new int[c2Pos.size()];
		for (int i = 0; i < c2Pos.size(); i++) {
			p = c2Pos.get(i);
			origOffset[i] = p.getOffset();
			origLineOffset[i] = p.getPositionInLine();
			origLineNo[i] = p.getLineNumber();
			c1Pos.add(c1.createPosition(p.getOffset()));
		}

		c1.insertString(where, what);
		c2.insertString(where, what);

		System.out.println();
		System.out.println(" Positions after " + where);
		System.out.println(c2.getString(0, Math.min(120, c2.length())));
		System.out.println();
		assertEquals(c1.getString(0, c1.length() - diff), c2.getString(0, c2.length()));


		for (int i = 0; i < c2Pos.size(); i++) {
			p = c2Pos.get(i);
			System.out.println(" ~~>> " + i + "} " + p.getLineNumber() + " " + p.getPositionInLine()
					+ " " + p.getOffset() + " ~~ " + c1Pos.get(i).getOffset()
					+ " / " + origOffset[i] + " " + origLineNo[i] + " " + origLineOffset[i]);
			assertEquals("Check Position:" + " " + i, c1Pos.get(i).getOffset(), p.getOffset());
		}
		System.out.println();

	}


	public void testRemove1() throws Exception {
		FileView v = TstConstants.getSalesCsvFile();

		StringBuilder b = new StringBuilder(v.getLine(0).getFullLine());

		for (int i = 1; i < v.getRowCount(); i++) {
			b.append('\n').append(v.getLine(i).getFullLine());
		}


		chkRemove(b, v.getLine(0).getData().length + 1,  v.getLine(1).getData().length);

		chkRemove(b, v.getLine(0).getData().length + 2,  v.getLine(1).getData().length - 2);

		chkRemove(b, v.getLine(0).getData().length + 2,  4 * v.getLine(1).getData().length + 10);

		chkRemove(b, v.getLine(0).getData().length - 1,  v.getLine(1).getData().length + 7);

		chkRemove(b, v.getLine(0).getData().length,  v.getLine(1).getData().length + 7);

		chkRemove(b, v.getLine(0).getData().length - 1,  1);
		chkRemove(b, v.getLine(0).getData().length - 1,  2);
		chkRemove(b, v.getLine(0).getData().length - 1,  3);

		chkRemove(b, v.getLine(0).getData().length ,  1);
		chkRemove(b, v.getLine(0).getData().length ,  2);
		chkRemove(b, v.getLine(0).getData().length ,  3);

		chkRemove(b, v.getLine(0).getData().length + 1,  1);
		chkRemove(b, v.getLine(0).getData().length + 1,  2);
		chkRemove(b, v.getLine(0).getData().length + 1,  3);

		chkRemove(b, v.getLine(0).getData().length + 2,  1);
		chkRemove(b, v.getLine(0).getData().length + 2,  2);
		chkRemove(b, v.getLine(0).getData().length + 2,  3);

		chkRemove(b, v.getLine(0).getData().length + 3,  1);
		chkRemove(b, v.getLine(0).getData().length + 3,  2);
		chkRemove(b, v.getLine(0).getData().length + 3,  3);

		chkRemove(b, 0,  v.getLine(0).getData().length - 7);
		chkRemove(b, 0,  v.getLine(0).getData().length + 7);
		chkRemove(b, 1,  v.getLine(0).getData().length - 7);
		chkRemove(b, 1,  v.getLine(0).getData().length + 7);
		chkRemove(b, 2,  v.getLine(0).getData().length - 7);
		chkRemove(b, 2,  v.getLine(0).getData().length + 7);

		int lastLinePos = b.length() - v.getLine(v.getRowCount() - 1).getData().length;

		chkRemove(b, lastLinePos - 10,  7);
		chkRemove(b, lastLinePos - 3,  7);
		chkRemove(b, lastLinePos - 2,  7);
		chkRemove(b, lastLinePos - 1,  7);
		chkRemove(b, lastLinePos ,  7);
		chkRemove(b, lastLinePos + 1,  7);
		chkRemove(b, lastLinePos + 2,  7);


		chkRemove(b, b.length() - 9,  7);
		chkRemove(b, b.length() - 8,  7);
		chkRemove(b, b.length() - 7,  7);
	}

	private void chkRemove(StringBuilder b, int offset, int length) throws Exception {
		FileView v = TstConstants.getSalesCsvFile();
		DataStoreContent dsc = v.asDocumentContent();
		GapContent gapContent = new GapContent(0);
		int iL = gapContent.length();
		gapContent.insertString(0, b.toString());

		chkRemove(gapContent, dsc, offset,  length, iL);
	}

	private void chkRemove(Content c1, Content c2, int where, int length,  int diff) throws BadLocationException {

		assertEquals(c1.getString(0, c1.length() - diff), c2.getString(0, c2.length()));
		c1.remove(where, length);
		c2.remove(where, length);

		assertEquals(c1.getString(0, c1.length() - diff), c2.getString(0, c2.length()));
	}

	public void testRemove2() throws Exception {
		FileView v = TstConstants.getSalesCsvFile();

		StringBuilder b = new StringBuilder(v.getLine(0).getFullLine());

		for (int i = 1; i < v.getRowCount(); i++) {
			b.append('\n').append(v.getLine(i).getFullLine());
		}


		chkRemove2(b, v.getLine(0).getData().length + 1,  v.getLine(1).getData().length);

		chkRemove2(b, v.getLine(0).getData().length + 2,  v.getLine(1).getData().length - 2);

		chkRemove2(b, v.getLine(0).getData().length + 2,  4 * v.getLine(1).getData().length + 10);

		chkRemove2(b, v.getLine(0).getData().length - 1,  v.getLine(1).getData().length + 7);

		chkRemove2(b, v.getLine(0).getData().length,  v.getLine(1).getData().length + 7);

		chkRemove2(b, 0,  1);
		chkRemove2(b, 0,  2);
		chkRemove2(b, 0,  3);

		chkRemove2(b, v.getLine(0).getData().length - 1,  1);
		chkRemove2(b, v.getLine(0).getData().length - 1,  2);
		chkRemove2(b, v.getLine(0).getData().length - 1,  3);


		chkRemove2(b, v.getLine(0).getData().length ,  1);
		chkRemove2(b, v.getLine(0).getData().length ,  2);
		chkRemove2(b, v.getLine(0).getData().length ,  3);

		chkRemove2(b, v.getLine(0).getData().length + 1,  1);
		chkRemove2(b, v.getLine(0).getData().length + 1,  2);
		chkRemove2(b, v.getLine(0).getData().length + 1,  3);

		chkRemove2(b, v.getLine(0).getData().length + 2,  1);
		chkRemove2(b, v.getLine(0).getData().length + 2,  2);
		chkRemove2(b, v.getLine(0).getData().length + 2,  3);

		chkRemove2(b, v.getLine(0).getData().length + 3,  1);
		chkRemove2(b, v.getLine(0).getData().length + 3,  2);
		chkRemove2(b, v.getLine(0).getData().length + 3,  3);


		chkRemove2(b, 0,  v.getLine(0).getData().length - 7);
		chkRemove2(b, 0,  v.getLine(0).getData().length + 7);
		chkRemove2(b, 1,  v.getLine(0).getData().length - 7);
		chkRemove2(b, 1,  v.getLine(0).getData().length + 7);
		chkRemove2(b, 2,  v.getLine(0).getData().length - 7);
		chkRemove2(b, 2,  v.getLine(0).getData().length + 7);

		int lastLinePos = b.length() - v.getLine(v.getRowCount() - 1).getData().length;

		chkRemove2(b, lastLinePos - 10,  7);
		chkRemove2(b, lastLinePos - 3,  7);
		chkRemove2(b, lastLinePos - 2,  7);
		chkRemove2(b, lastLinePos - 1,  7);
		chkRemove2(b, lastLinePos ,  7);
		chkRemove2(b, lastLinePos + 1,  7);
		chkRemove2(b, lastLinePos + 2,  7);


		chkRemove2(b, b.length() - 9,  7);
		chkRemove2(b, b.length() - 8,  7);
		chkRemove2(b, b.length() - 7,  7);
	}

	private void chkRemove2(StringBuilder b, int offset, int length) throws Exception {
		FileView v = TstConstants.getSalesCsvFile();
		DataStoreContent dsc = v.asDocumentContent();
		GapContent gapContent = new GapContent(0);
		int iL = gapContent.length();
		gapContent.insertString(0, b.toString());

		chkRemove2(gapContent, dsc, offset,  length, iL, v.getRowCount());
	}

	private void chkRemove2(Content c1, DataStoreContent c2, int where, int length,  int diff, int numLines) throws BadLocationException {

		IDataStorePosition
				p,
				start = c2.createPosition(where),
				fin   = c2.createPosition(where + length);
		int
				idxSt = Math.max(0, start.getLineNumber() - 3),
				idxEn = Math.min(numLines, fin.getLineNumber() + 3);
		int listSize = 7 + 3 * (idxEn - idxSt);
		int[] origOffset;
		int[] origLineOffset;
		int[] origLineNo;

		ArrayList<Position> c1Pos = new ArrayList<Position>(listSize);
		ArrayList<IDataStorePosition> c2Pos = new ArrayList<IDataStorePosition>(listSize);

//		c1Pos.add(c2.createPosition(where));
//		c1Pos.add(c2.createPosition(where + length));
		c2Pos.add(start);
		c2Pos.add(fin);

		for (int i = idxSt; i < idxEn; i++) {
			p = c2.getLinePosition(i);
			c2Pos.add(p);
			c2Pos.add(c2.createPosition(p.getOffset() + 1));
			c2Pos.add(c2.createPosition(p.getOffset() + p.getLine().getData().length / 2));
			c2Pos.add(c2.createPosition(p.getOffset() + p.getLine().getData().length - 1));
			c2Pos.add(c2.createPosition(p.getOffset() + p.getLine().getData().length));
		}
		//c2.printPositions(-1331);

		origOffset = new int[c2Pos.size()];
		origLineOffset = new int[c2Pos.size()];
		origLineNo = new int[c2Pos.size()];
		for (int i = 0; i < c2Pos.size(); i++) {
			p = c2Pos.get(i);
			origOffset[i] = p.getOffset();
			origLineOffset[i] = p.getPositionInLine();
			origLineNo[i] = p.getLineNumber();
			c1Pos.add(c1.createPosition(p.getOffset()));
		}

		c1.remove(where, length);
		c2.remove(where, length);

		assertEquals(c1.getString(0, c1.length() - diff), c2.getString(0, c2.length()));

		System.out.println();
		System.out.println(" Positions after " + where + " " + length);
		System.out.println(c2.getString(0, Math.min(120, c2.length())));
		System.out.println();
		for (int i = 0; i < c2Pos.size(); i++) {
			p = c2Pos.get(i);
			System.out.println(" ~~>> " + i + "} " + p.getLineNumber() + " " + p.getPositionInLine()
					+ " " + p.getOffset() + " ~~ " + c1Pos.get(i).getOffset()
					+ " / " + origOffset[i] + " " + origLineNo[i] + " " + origLineOffset[i]);
			if (c1Pos.get(i).getOffset() != p.getOffset()) {
				System.out.println();
			}
			assertEquals("Check Position:" + " " + i, c1Pos.get(i).getOffset(), p.getOffset());
		}
		System.out.println();
	}

	public void testGetString() throws Exception{
		FileView v = TstConstants.getSalesCsvFile();
		DataStoreContent dsc = v.asDocumentContent();

		StringBuilder b = new StringBuilder(v.getLine(0).getFullLine());

		for (int i = 1; i < v.getRowCount(); i++) {
			b.append('\n').append(v.getLine(i).getFullLine());
		}

		assertEquals("Test Length", b.length(), dsc.length());
//		System.out.println(b.toString());
//		System.out.println();
//		System.out.println(dsc.getString(0, b.length()));
		assertEquals("Test Value", b.toString(), dsc.getString(0, b.length()));
		int pos = 0;
		String s, s1, s2, s4;

		for (int i = 0; i < v.getRowCount(); i++) {
			s= v.getLine(i).getFullLine();
			assertEquals("Test Value", s,  dsc.getString(pos, s.length()));
			assertEquals("Test Value", s.substring(1, s.length() - 1),  dsc.getString(pos+1, s.length() - 2));
			pos += v.getLine(i).getFullLine().length() + 1;
		}

		int oldPos = 0;
		pos = v.getLine(0).getFullLine().length() + 1;
		for (int i = 1; i < v.getRowCount(); i++) {
			s1 = v.getLine(i - 1).getFullLine();
			s2 = v.getLine(i).getFullLine();
			s4 = s1 + "\n" + s2;
			assertEquals("Test Value", s4,  dsc.getString(oldPos, s4.length()));
			assertEquals("Test Value", s4.substring(1, s4.length() - 1),  dsc.getString(oldPos + 1, s4.length() - 2));
			oldPos = pos;
			pos += v.getLine(i).getFullLine().length() + 1;
		}

		oldPos = 0;
		int oldPos1 = v.getLine(0).getFullLine().length() + 1;
		pos = oldPos1 + v.getLine(1).getFullLine().length() + 1;
		for (int i = 2; i < v.getRowCount(); i++) {
			s1 = v.getLine(i - 2).getFullLine();
			s2 = v.getLine(i - 1).getFullLine();
			s4 = s1 + "\n" + s2 + "\n" +  v.getLine(i).getFullLine();
			assertEquals("Test Value", s4,  dsc.getString(oldPos, s4.length()));
			assertEquals("Test Value", s4.substring(1, s4.length() - 1),  dsc.getString(oldPos + 1, s4.length() - 2));
			oldPos = oldPos1;
			oldPos1 = pos;
			pos += v.getLine(i).getFullLine().length() + 1;
		}
	}

//	public void testTableChanged() {
//		fail("Not yet implemented");
//	}

}
