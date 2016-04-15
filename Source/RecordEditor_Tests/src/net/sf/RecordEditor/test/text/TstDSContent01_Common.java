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
public class TstDSContent01_Common {

	public void tstCreatePosition01(int dsType) throws Exception {
		FileView v = TstConstants.getSalesCsvFile(dsType);
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

			TestCase.assertEquals("Check Offset, i=" + i, offset, positions[i].getOffset());
			TestCase.assertEquals("Check Line Number, i=" + i, i, positions[i].getLineNumberRE());
			TestCase.assertEquals("Check Line Start, i=" + i, pos, positions[i].getLineStartRE());
			TestCase.assertEquals("Check Line pos, i=" + i, i, positions[i].getPositionInLineRE());
			TestCase.assertEquals("Check Line, i=" + i, v.getLine(i), positions[i].getLineRE());

			pos += v.getLine(i).getFullLine().length() + 1;
		}

		int len = v.getLine(2).getFullLine().length() + 1;

		v.getLine(2).setField(0, 0, v.getLine(2).getField(0, 0) + "1234");
		v.fireRowUpdated(2, null, v.getLine(2));


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
			TestCase.assertEquals("Check Line Number, i=" + i, i, positions[i].getLineNumberRE());
			if (i == 2) {
				TestCase.assertEquals("Check Offset, i=" + i, offset, positions[i].getOffset());
				TestCase.assertEquals("Check Line Start, i=" + i, pos, positions[i].getLineStartRE());
			} else {
				TestCase.assertEquals("Check Offset, i=" + i, offset + diff, positions[i].getOffset());
				TestCase.assertEquals("Check Line Start, i=" + i, pos + diff, positions[i].getLineStartRE());
			}
			TestCase.assertEquals("Check Line pos, i=" + i, i, positions[i].getPositionInLineRE());

			TestCase.assertEquals("Check Line, i=" + i, v.getLine(i), positions[i].getLineRE());
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
					+ " " + positions[j].getLineNumberRE()
					+ " " + positions[j].getPositionInLineRE()
					+ " " + positions[j].getLineStartRE());
			if (j != 2) {
				TestCase.assertEquals("Check Line Number, i=" + i, i, positions[j].getLineNumberRE());
				TestCase.assertEquals("Check Line pos, i=" + i, j, positions[j].getPositionInLineRE());

				TestCase.assertEquals("Check Line, i=" + i, v.getLine(i), positions[j].getLineRE());
				TestCase.assertEquals("Check Line Start, i=" + i, pos, positions[j].getLineStartRE());
				TestCase.assertEquals("Check Offset, i=" + i, offset , positions[j].getOffset());
				i += 1;
			}
		}
	}


	public void tstCreatePosition02(int dsType, int sizeType) throws Exception {
		FileView v = TstConstants.getSalesCsvFile(dsType, sizeType);
		DataStoreContent dsc = v.asDocumentContent();
		IDataStorePosition[] positions = new IDataStorePosition[10];
		int[] origPos = new int[positions.length];
		int offset, diff;
		int pos = 0;

		for (int i = 0; i < positions.length; i++) {
			origPos[i] = pos;
			offset = pos + i % 4;
			positions[i] = dsc.createPosition(offset);
//			System.out.println();
//			System.out.println(" --> " + pos + " " + offset
//					+ " " + v.getLine(i).getFullLine() + " ~~ " + v.getLine(i).getFullLine().length());

			TestCase.assertEquals("Check Offset, i=" + i, offset, positions[i].getOffset());
			TestCase.assertEquals("Check Line Number, i=" + i, i, positions[i].getLineNumberRE());
			TestCase.assertEquals("Check Line Start, i=" + i, pos, positions[i].getLineStartRE());
			TestCase.assertEquals("Check Line pos, i=" + i, i % 4, positions[i].getPositionInLineRE());
			TestCase.assertEquals("Check Line, i=" + i, v.getLine(i), positions[i].getLineRE());

			pos += v.getLine(i).getFullLine().length() + 1;
		}

		int len = v.getLine(2).getFullLine().length() + 1;

		v.getLine(2).setField(0, 0, v.getLine(2).getField(0, 0) + "1234");
		v.fireRowUpdated(2, null, v.getLine(2));


		for (int i = 0; i < positions.length; i++) {
			offset = origPos[i] + i % 4;
			pos =  origPos[i];

			diff = 4;
			if (i < 2) {
				diff = 0;
			}
			System.out.println();
			System.out.println(" --> " + pos + " " + offset
					+ " " + v.getLine(i).getFullLine() + " ~~ " + v.getLine(i).getFullLine().length());
			TestCase.assertEquals("Check Line Number, i=" + i, i, positions[i].getLineNumberRE());
			if (i == 2) {
				TestCase.assertEquals("Check Offset, i=" + i, offset, positions[i].getOffset());
				TestCase.assertEquals("Check Line Start, i=" + i, pos, positions[i].getLineStartRE());
			} else {
				TestCase.assertEquals("Check Offset, i=" + i, offset + diff, positions[i].getOffset());
				TestCase.assertEquals("Check Line Start, i=" + i, pos + diff, positions[i].getLineStartRE());
			}
			TestCase.assertEquals("Check Line pos, i=" + i, i % 4, positions[i].getPositionInLineRE());

			TestCase.assertEquals("Check Line, i=" + i, v.getLine(i), positions[i].getLineRE());
		}

		int i = 0;

		v.deleteLine(2);

		for (int j = 0; j < positions.length; j++) {
			pos =  origPos[j];

			if (j > 2) {
				pos -= len;
			}
			offset = pos + j % 4;
			System.out.println();
			System.out.println(" --> " + pos  + " " + len + " / " + offset
					+ " " + positions[j].getLineNumberRE()
					+ " " + positions[j].getPositionInLineRE()
					+ " " + positions[j].getLineStartRE());
			if (j != 2) {
				TestCase.assertEquals("Check Line Number, i=" + i, i, positions[j].getLineNumberRE());
				TestCase.assertEquals("Check Line pos, i=" + i , j % 4, positions[j].getPositionInLineRE());

				TestCase.assertEquals("Check Line, i=" + i, v.getLine(i), positions[j].getLineRE());
				TestCase.assertEquals("Check Line Start, i=" + i, pos, positions[j].getLineStartRE());
				TestCase.assertEquals("Check Offset, i=" + i, offset , positions[j].getOffset());
				i += 1;
			}
		}
	}

	public void tstGetLinePosition(int dsType, int sizeType) throws Exception {
		FileView v = TstConstants.getSalesCsvFile(dsType, sizeType);
		DataStoreContent dsc = v.asDocumentContent();
		IDataStorePosition[] positions = new IDataStorePosition[10];
		int[] origPos = new int[positions.length];
		int offset, diff;
		int pos = 0;

		for (int i = 0; i < positions.length; i++) {
			origPos[i] = pos;
			offset = pos;
			positions[i] = dsc.getPositionByLineNumber(i);
//			System.out.println();
//			System.out.println(" --> " + pos + " " + offset
//					+ " " + v.getLine(i).getFullLine() + " ~~ " + v.getLine(i).getFullLine().length());

			TestCase.assertEquals("Check Offset, i=" + i, offset, positions[i].getOffset());
			TestCase.assertEquals("Check Line Number, i=" + i, i, positions[i].getLineNumberRE());
			TestCase.assertEquals("Check Line Start, i=" + i, pos, positions[i].getLineStartRE());
			TestCase.assertEquals("Check Line pos, i=" + i, 0, positions[i].getPositionInLineRE());
			TestCase.assertEquals("Check Line, i=" + i, v.getLine(i), positions[i].getLineRE());

			pos += v.getLine(i).getFullLine().length() + 1;
		}

		int len = v.getLine(2).getFullLine().length() + 1;

		v.getLine(2).setField(0, 0, v.getLine(2).getField(0, 0) + "1234");
		v.fireRowUpdated(2, null, v.getLine(2));


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
			TestCase.assertEquals("Check Line Number, i=" + i, i, positions[i].getLineNumberRE());
			if (i == 2) {
				TestCase.assertEquals("Check Offset, i=" + i, offset, positions[i].getOffset());
				TestCase.assertEquals("Check Line Start, i=" + i, pos, positions[i].getLineStartRE());
			} else {
				TestCase.assertEquals("Check Offset, i=" + i, offset + diff, positions[i].getOffset());
				TestCase.assertEquals("Check Line Start, i=" + i, pos + diff, positions[i].getLineStartRE());
			}
			TestCase.assertEquals("Check Line pos, i=" + i, 0, positions[i].getPositionInLineRE());

			TestCase.assertEquals("Check Line, i=" + i, v.getLine(i), positions[i].getLineRE());
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
				TestCase.assertEquals("Check Line Number, i=" + i, i, positions[j].getLineNumberRE());
				TestCase.assertEquals("Check Line pos, i=" + i, 0, positions[j].getPositionInLineRE());

				TestCase.assertEquals("Check Line, i=" + i, v.getLine(i), positions[j].getLineRE());
				TestCase.assertEquals("Check Line Start, i=" + i, pos, positions[j].getLineStartRE());
				TestCase.assertEquals("Check Offset, i=" + i, offset , positions[j].getOffset());
				i += 1;
			}
		}
	}


	public void tstLength(int dsType, int sizeType) throws Exception {
		FileView v = TstConstants.getSalesCsvFile(dsType, sizeType);
		DataStoreContent dsc = v.asDocumentContent();

		int len = 0;
		for (int i = 0; i < v.getRowCount(); i++) {
			len += v.getLine(i).getFullLine().length() + 1;
		}

		TestCase.assertEquals("Test Length", len - 1, dsc.length());
	}

	public void tstNumberOfLines(int dsType, int sizeType) throws Exception {
		FileView v = TstConstants.getSalesCsvFile(dsType, sizeType);
		DataStoreContent dsc = v.asDocumentContent();
		TestCase.assertEquals("Number of lines", v.getRowCount(), dsc.numberOfLines());
	}

	public void tstInsertString(int dsType) throws Exception {
		FileView v = TstConstants.getSalesCsvFile(dsType);


		StringBuilder b = new StringBuilder(v.getLine(0).getFullLine());

		for (int i = 1; i < v.getRowCount(); i++) {
			b.append('\n').append(v.getLine(i).getFullLine());
		}

		chkInsert1(dsType, b, "1234");
		chkInsert1(dsType, b, "21\n34");
		chkInsert1(dsType, b, "21\n34\n56");
		chkInsert1(dsType, b, "\n21\n34");
		chkInsert1(dsType, b, "213\n34567\n");

		chkInsert1(dsType, b, "21\n34\n");
		chkInsert1(dsType, b, "\n1234");
		chkInsert1(dsType, b, "1234\n");

		chkInsert1(dsType, b, "\n\n1234");
		chkInsert1(dsType, b, "12\n\n34");
		chkInsert1(dsType, b, "\n\n1234\n\n");
		chkInsert1(dsType, b, "1234\n\n");
		chkInsert1(dsType, b, "12\n\n34\n\n");
		chkInsert1(dsType, b, "12\n\n34\n\n56");
		chkInsert1(dsType, b, "12\n\n34\n\n56\n\n");
		chkInsert1(dsType, b, "1\na\n4\nb\n6\nc\n");


		chkInsert1(dsType, b, "\n\n\n1234");
		chkInsert1(dsType, b, "12\n\n\n34");
		chkInsert1(dsType, b, "\n\n\n1234\n\n\n");
		chkInsert1(dsType, b, "1234\n\n\n");
		chkInsert1(dsType, b, "12\n\n\n34\n\n\n");
		chkInsert1(dsType, b, "12\n\n\n34\n\n\n56");
		chkInsert1(dsType, b, "12\n\n\n34\n\n\n56\n\n\n");

	}


	private void chkInsert1(int dsType, StringBuilder b, String what) throws Exception {
		chkInsert(dsType, b, 0, what);
		chkInsert(dsType, b, 1, what);
		chkInsert(dsType, b, 2, what);
		chkInsert(dsType, b, 30, what);
		chkInsert(dsType, b, 60, what);

		chkInsert(dsType, b, b.length() - 3, what);
		chkInsert(dsType, b, b.length() - 1, what);
		chkInsert(dsType, b, b.length() - 0, what);
	}


	private void chkInsert(int dsType, StringBuilder b, int offset, String what) throws Exception {
		FileView v = TstConstants.getSalesCsvFile(dsType);
		DataStoreContent dsc = v.asDocumentContent();
		GapContent gapContent = new GapContent(0);
		int iL = gapContent.length();
		gapContent.insertString(0, b.toString());

		chkInsert(gapContent, dsc, offset,  what, iL);
	}


	private void chkInsert(Content c1, Content c2, int where, String what,  int diff) throws BadLocationException {


		c1.insertString(where, what);
		c2.insertString(where, what);

		TestCase.assertEquals(c1.getString(0, c1.length() - diff), c2.getString(0, c2.length()));
	}
	public void tstInsertString2(int dsType) throws Exception {
		FileView v = TstConstants.getSalesCsvFile(dsType);


		StringBuilder b = new StringBuilder(v.getLine(0).getFullLine());

		for (int i = 1; i < v.getRowCount(); i++) {
			b.append('\n').append(v.getLine(i).getFullLine());
		}

		chkInsert2(dsType, b, "1234");
		chkInsert2(dsType,b, "21\n34");
		chkInsert2(dsType,b, "21\n34\n56");
		chkInsert2(dsType,b, "\n21\n34");
		chkInsert2(dsType,b, "213\n34567\n");

		chkInsert2(dsType,b, "21\n34\n");
		chkInsert2(dsType,b, "\n1234");
		chkInsert2(dsType,b, "1234\n");

		chkInsert2(dsType,b, "\n\n1234");
		chkInsert2(dsType,b, "12\n\n34");
		chkInsert2(dsType,b, "\n\n1234\n\n");
		chkInsert2(dsType,b, "1234\n\n");
		chkInsert2(dsType,b, "12\n\n34\n\n");
		chkInsert2(dsType,b, "12\n\n34\n\n56");
		chkInsert2(dsType,b, "12\n\n34\n\n56\n\n");
		chkInsert2(dsType,b, "1\na\n4\nb\n6\nc\n");


		chkInsert2(dsType,b, "\n\n\n1234");
		chkInsert2(dsType,b, "12\n\n\n34");
		chkInsert2(dsType,b, "\n\n\n1234\n\n\n");
		chkInsert2(dsType,b, "1234\n\n\n");
		chkInsert2(dsType,b, "12\n\n\n34\n\n\n");
		chkInsert2(dsType,b, "12\n\n\n34\n\n\n56");
		chkInsert2(dsType,b, "12\n\n\n34\n\n\n56\n\n\n");

	}


	private void chkInsert2(int dsType, StringBuilder b, String what) throws Exception {
		chkInsert2(dsType,b, 0, what);
		chkInsert2(dsType,b, 1, what);
		chkInsert2(dsType,b, 2, what);
		chkInsert2(dsType,b, 30, what);
		chkInsert2(dsType,b, 60, what);

		chkInsert2(dsType,b, b.length() - 3, what);
		chkInsert2(dsType,b, b.length() - 1, what);
		chkInsert2(dsType,b, b.length() - 0, what);
	}


	private void chkInsert2(int dsType, StringBuilder b, int offset, String what) throws Exception {
		FileView v = TstConstants.getSalesCsvFile(dsType);
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
				idxSt = Math.max(0, start.getLineNumberRE() - 3),
				idxEn = Math.min(numLines, start.getLineNumberRE() + 3);
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
			p = c2.getPositionByLineNumber(i);
			c2Pos.add(p);
			c2Pos.add(c2.createPosition(p.getOffset() + 1));
			c2Pos.add(c2.createPosition(p.getOffset() + p.getLineRE().getData().length / 2));
			c2Pos.add(c2.createPosition(p.getOffset() + p.getLineRE().getData().length - 1));
			c2Pos.add(c2.createPosition(p.getOffset() + p.getLineRE().getData().length));
		}

		//c2.printPositions(-1331);

		origOffset = new int[c2Pos.size()];
		origLineOffset = new int[c2Pos.size()];
		origLineNo = new int[c2Pos.size()];
		for (int i = 0; i < c2Pos.size(); i++) {
			p = c2Pos.get(i);
			origOffset[i] = p.getOffset();
			origLineOffset[i] = p.getPositionInLineRE();
			origLineNo[i] = p.getLineNumberRE();
			c1Pos.add(c1.createPosition(p.getOffset()));
		}

		c1.insertString(where, what);
		c2.insertString(where, what);

		System.out.println();
		System.out.println(" Positions after " + where);
		System.out.println(c2.getString(0, Math.min(120, c2.length())));
		System.out.println();
		TestCase.assertEquals(c1.getString(0, c1.length() - diff), c2.getString(0, c2.length()));


		for (int i = 0; i < c2Pos.size(); i++) {
			p = c2Pos.get(i);
			System.out.println(" ~~>> " + i + "} " + p.getLineNumberRE() + " " + p.getPositionInLineRE()
					+ " " + p.getOffset() + " ~~ " + c1Pos.get(i).getOffset()
					+ " / " + origOffset[i] + " " + origLineNo[i] + " " + origLineOffset[i]);
			TestCase.assertEquals("Check Position:" + " " + i, c1Pos.get(i).getOffset(), p.getOffset());
		}
		System.out.println();

	}


	public void tstRemove1(int dsType) throws Exception {
		FileView v = TstConstants.getSalesCsvFile(dsType);

		StringBuilder b = new StringBuilder(v.getLine(0).getFullLine());

		for (int i = 1; i < v.getRowCount(); i++) {
			b.append('\n').append(v.getLine(i).getFullLine());
		}


		chkRemove(dsType, b, v.getLine(0).getData().length + 1,  v.getLine(1).getData().length);

		chkRemove(dsType, b, v.getLine(0).getData().length + 2,  v.getLine(1).getData().length - 2);

		chkRemove(dsType, b, v.getLine(0).getData().length + 2,  4 * v.getLine(1).getData().length + 10);

		chkRemove(dsType, b, v.getLine(0).getData().length - 1,  v.getLine(1).getData().length + 7);

		chkRemove(dsType, b, v.getLine(0).getData().length,  v.getLine(1).getData().length + 7);

		chkRemove(dsType, b, v.getLine(0).getData().length - 1,  1);
		chkRemove(dsType, b, v.getLine(0).getData().length - 1,  2);
		chkRemove(dsType, b, v.getLine(0).getData().length - 1,  3);

		chkRemove(dsType, b, v.getLine(0).getData().length ,  1);
		chkRemove(dsType, b, v.getLine(0).getData().length ,  2);
		chkRemove(dsType, b, v.getLine(0).getData().length ,  3);

		chkRemove(dsType, b, v.getLine(0).getData().length + 1,  1);
		chkRemove(dsType, b, v.getLine(0).getData().length + 1,  2);
		chkRemove(dsType, b, v.getLine(0).getData().length + 1,  3);

		chkRemove(dsType, b, v.getLine(0).getData().length + 2,  1);
		chkRemove(dsType, b, v.getLine(0).getData().length + 2,  2);
		chkRemove(dsType, b, v.getLine(0).getData().length + 2,  3);

		chkRemove(dsType, b, v.getLine(0).getData().length + 3,  1);
		chkRemove(dsType, b, v.getLine(0).getData().length + 3,  2);
		chkRemove(dsType, b, v.getLine(0).getData().length + 3,  3);

		chkRemove(dsType, b, 0,  v.getLine(0).getData().length - 7);
		chkRemove(dsType, b, 0,  v.getLine(0).getData().length + 7);
		chkRemove(dsType, b, 1,  v.getLine(0).getData().length - 7);
		chkRemove(dsType, b, 1,  v.getLine(0).getData().length + 7);
		chkRemove(dsType, b, 2,  v.getLine(0).getData().length - 7);
		chkRemove(dsType, b, 2,  v.getLine(0).getData().length + 7);

		int lastLinePos = b.length() - v.getLine(v.getRowCount() - 1).getData().length;

		chkRemove(dsType, b, lastLinePos - 10,  7);
		chkRemove(dsType, b, lastLinePos - 3,  7);
		chkRemove(dsType, b, lastLinePos - 2,  7);
		chkRemove(dsType, b, lastLinePos - 1,  7);
		chkRemove(dsType, b, lastLinePos ,  7);
		chkRemove(dsType, b, lastLinePos + 1,  7);
		chkRemove(dsType, b, lastLinePos + 2,  7);


		chkRemove(dsType, b, b.length() - 9,  7);
		chkRemove(dsType, b, b.length() - 8,  7);
		chkRemove(dsType, b, b.length() - 7,  7);
	}

	private void chkRemove(int dsType, StringBuilder b, int offset, int length) throws Exception {
		FileView v = TstConstants.getSalesCsvFile(dsType);
		DataStoreContent dsc = v.asDocumentContent();
		GapContent gapContent = new GapContent(0);
		int iL = gapContent.length();
		gapContent.insertString(0, b.toString());

		chkRemove(gapContent, dsc, offset,  length, iL);
	}

	private void chkRemove(Content c1, Content c2, int where, int length,  int diff) throws BadLocationException {

		TestCase.assertEquals(c1.getString(0, c1.length() - diff), c2.getString(0, c2.length()));
		c1.remove(where, length);
		c2.remove(where, length);

		TestCase.assertEquals(c1.getString(0, c1.length() - diff), c2.getString(0, c2.length()));
	}

	public void tstRemove2(int dsType) throws Exception {
		FileView v = TstConstants.getSalesCsvFile(dsType);

		StringBuilder b = new StringBuilder(v.getLine(0).getFullLine());

		for (int i = 1; i < v.getRowCount(); i++) {
			b.append('\n').append(v.getLine(i).getFullLine());
		}


		chkRemove2(dsType, b, v.getLine(0).getData().length + 1,  v.getLine(1).getData().length);

		chkRemove2(dsType, b, v.getLine(0).getData().length + 2,  v.getLine(1).getData().length - 2);

		chkRemove2(dsType, b, v.getLine(0).getData().length + 2,  4 * v.getLine(1).getData().length + 10);

		chkRemove2(dsType, b, v.getLine(0).getData().length - 1,  v.getLine(1).getData().length + 7);

		chkRemove2(dsType, b, v.getLine(0).getData().length,  v.getLine(1).getData().length + 7);

		chkRemove2(dsType, b, 0,  1);
		chkRemove2(dsType, b, 0,  2);
		chkRemove2(dsType, b, 0,  3);

		chkRemove2(dsType, b, v.getLine(0).getData().length - 1,  1);
		chkRemove2(dsType, b, v.getLine(0).getData().length - 1,  2);
		chkRemove2(dsType, b, v.getLine(0).getData().length - 1,  3);


		chkRemove2(dsType, b, v.getLine(0).getData().length ,  1);
		chkRemove2(dsType, b, v.getLine(0).getData().length ,  2);
		chkRemove2(dsType, b, v.getLine(0).getData().length ,  3);

		chkRemove2(dsType, b, v.getLine(0).getData().length + 1,  1);
		chkRemove2(dsType, b, v.getLine(0).getData().length + 1,  2);
		chkRemove2(dsType, b, v.getLine(0).getData().length + 1,  3);

		chkRemove2(dsType, b, v.getLine(0).getData().length + 2,  1);
		chkRemove2(dsType, b, v.getLine(0).getData().length + 2,  2);
		chkRemove2(dsType, b, v.getLine(0).getData().length + 2,  3);

		chkRemove2(dsType, b, v.getLine(0).getData().length + 3,  1);
		chkRemove2(dsType, b, v.getLine(0).getData().length + 3,  2);
		chkRemove2(dsType, b, v.getLine(0).getData().length + 3,  3);


		chkRemove2(dsType, b, 0,  v.getLine(0).getData().length - 7);
		chkRemove2(dsType, b, 0,  v.getLine(0).getData().length + 7);
		chkRemove2(dsType, b, 1,  v.getLine(0).getData().length - 7);
		chkRemove2(dsType, b, 1,  v.getLine(0).getData().length + 7);
		chkRemove2(dsType, b, 2,  v.getLine(0).getData().length - 7);
		chkRemove2(dsType, b, 2,  v.getLine(0).getData().length + 7);

		int lastLinePos = b.length() - v.getLine(v.getRowCount() - 1).getData().length;

		chkRemove2(dsType, b, lastLinePos - 10,  7);
		chkRemove2(dsType, b, lastLinePos - 3,  7);
		chkRemove2(dsType, b, lastLinePos - 2,  7);
		chkRemove2(dsType, b, lastLinePos - 1,  7);
		chkRemove2(dsType, b, lastLinePos ,  7);
		chkRemove2(dsType, b, lastLinePos + 1,  7);
		chkRemove2(dsType, b, lastLinePos + 2,  7);


		chkRemove2(dsType, b, b.length() - 9,  7);
		chkRemove2(dsType, b, b.length() - 8,  7);
		chkRemove2(dsType, b, b.length() - 7,  7);
	}

	private void chkRemove2(int dsType, StringBuilder b, int offset, int length) throws Exception {
		FileView v = TstConstants.getSalesCsvFile(dsType);
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
				idxSt = Math.max(0, start.getLineNumberRE() - 3),
				idxEn = Math.min(numLines, fin.getLineNumberRE() + 3);
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
			p = c2.getPositionByLineNumber(i);
			c2Pos.add(p);
			c2Pos.add(c2.createPosition(p.getOffset() + 1));
			c2Pos.add(c2.createPosition(p.getOffset() + p.getLineRE().getData().length / 2));
			c2Pos.add(c2.createPosition(p.getOffset() + p.getLineRE().getData().length - 1));
			c2Pos.add(c2.createPosition(p.getOffset() + p.getLineRE().getData().length));
		}
		//c2.printPositions(-1331);

		origOffset = new int[c2Pos.size()];
		origLineOffset = new int[c2Pos.size()];
		origLineNo = new int[c2Pos.size()];
		for (int i = 0; i < c2Pos.size(); i++) {
			p = c2Pos.get(i);
			origOffset[i] = p.getOffset();
			origLineOffset[i] = p.getPositionInLineRE();
			origLineNo[i] = p.getLineNumberRE();
			c1Pos.add(c1.createPosition(p.getOffset()));
		}

		c1.remove(where, length);
		c2.remove(where, length);

		TestCase.assertEquals(c1.getString(0, c1.length() - diff), c2.getString(0, c2.length()));

		System.out.println();
		System.out.println(" Positions after " + where + " " + length);
		System.out.println(c2.getString(0, Math.min(120, c2.length())));
		System.out.println();
		for (int i = 0; i < c2Pos.size(); i++) {
			p = c2Pos.get(i);
			System.out.println(" ~~>> " + i + "} " + p.getLineNumberRE() + " " + p.getPositionInLineRE()
					+ " " + p.getOffset() + " ~~ " + c1Pos.get(i).getOffset()
					+ " / " + origOffset[i] + " " + origLineNo[i] + " " + origLineOffset[i]);
			if (c1Pos.get(i).getOffset() != p.getOffset()) {
				System.out.println();
			}
			TestCase.assertEquals("Check Position:" + " " + i, c1Pos.get(i).getOffset(), p.getOffset());
		}
		System.out.println();
	}

	public void tstGetString(int dsType, int sizeType) throws Exception{
		FileView v = TstConstants.getSalesCsvFile(dsType, sizeType);
		DataStoreContent dsc = v.asDocumentContent();

		
		StringBuilder b = new StringBuilder(v.getLine(0).getFullLine());

		for (int i = 1; i < v.getRowCount(); i++) {
			b.append('\n').append(v.getLine(i).getFullLine());
		}

		TestCase.assertEquals("Test Length", b.length(), dsc.length());
//		System.out.println(b.toString());
//		System.out.println();
//		System.out.println(dsc.getString(0, b.length()));
		TestCase.assertEquals("Test Value", b.toString(), dsc.getString(0, b.length()));
		int pos = 0;
		String s, s1, s2, s4;

		for (int i = 0; i < v.getRowCount(); i++) {
			s= v.getLine(i).getFullLine();
			TestCase.assertEquals("Test Value", s,  dsc.getString(pos, s.length()));
			TestCase.assertEquals("Test Value", s.substring(1, s.length() - 1),  dsc.getString(pos+1, s.length() - 2));
			pos += v.getLine(i).getFullLine().length() + 1;
		}

		int oldPos = 0;
		pos = v.getLine(0).getFullLine().length() + 1;
		for (int i = 1; i < v.getRowCount(); i++) {
			s1 = v.getLine(i - 1).getFullLine();
			s2 = v.getLine(i).getFullLine();
			s4 = s1 + "\n" + s2;
			TestCase.assertEquals("Test Value", s4,  dsc.getString(oldPos, s4.length()));
			TestCase.assertEquals("Test Value", s4.substring(1, s4.length() - 1),  dsc.getString(oldPos + 1, s4.length() - 2));
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
			TestCase.assertEquals("Test Value", s4,  dsc.getString(oldPos, s4.length()));
			TestCase.assertEquals("Test Value", s4.substring(1, s4.length() - 1),  dsc.getString(oldPos + 1, s4.length() - 2));
			oldPos = oldPos1;
			oldPos1 = pos;
			pos += v.getLine(i).getFullLine().length() + 1;
		}
	}
	
	
	public void tstInsertDelete(int dsType, int sizeType) throws Exception {
		FileView v = TstConstants.getSalesCsvFile(dsType, sizeType);
		DataStoreContent dsc = v.asDocumentContent();
		String[] strings = {
				"1234",
				"21\n34",
				"21\n34\n56",  
				"\n21\n34",  
				"\n",
				"\n\n",
				"\n\n\n",
				"\n\n\n\n",
				"abc\nqwer\n123\n",
				"abc\nqwer\n123\n67",
		};

		StringBuilder b = new StringBuilder(v.getLine(0).getFullLine());

		for (int i = 1; i < v.getRowCount(); i++) {
			b.append('\n').append(v.getLine(i).getFullLine());
		}
		String f = b.toString();
		int len = b.length();
		
		for (int i = 0; i < len; i++) {
			for (String s :strings) {
				dsc.insertString(i, s);
				b.insert(i, s);
				TestCase.assertEquals("Checking insert - " + i + " " + s, b.toString(), dsc.getString(0, len + s.length()));
				
				b.delete(i, i + s.length());
				dsc.remove(i, s.length());
				TestCase.assertEquals("Checking remove - " + i + " " + s, f, dsc.getString(0, len));
			}
		}
	}

//	public void testTableChanged() {
//		fail("Not yet implemented");
//	}

}
