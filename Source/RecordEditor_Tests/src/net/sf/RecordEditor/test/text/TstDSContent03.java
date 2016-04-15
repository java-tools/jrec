package net.sf.RecordEditor.test.text;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.ElementIterator;
import javax.swing.text.Segment;

import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.file.textDocument.FileDocument3;
import net.sf.RecordEditor.re.file.textDocument.FileDocument5;
import net.sf.RecordEditor.test.TstConstants;
import net.sf.RecordEditor.utils.fileStorage.IDataStorePosition;
import net.sf.RecordEditor.utils.params.Parameters;
import junit.framework.TestCase;

public class TstDSContent03 extends TestCase {

	public void testNormal() throws Exception  {
		tst1(TstConstants.DS_NORMAL, TstConstants.SIZE_SMALL);
		tst1(TstConstants.DS_NORMAL, TstConstants.SIZE_LARGE);
	}

	public void testLvNormal() throws Exception  {
		tst1(TstConstants.DS_LARGE_VIEW_NORMAL, TstConstants.SIZE_SMALL);
		tst1(TstConstants.DS_LARGE_VIEW_NORMAL, TstConstants.SIZE_LARGE);
	}

	public void testVB() throws Exception  {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
		tst1(TstConstants.DS_VB, TstConstants.SIZE_SMALL);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		tst1(TstConstants.DS_VB, TstConstants.SIZE_LARGE);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}

	public void testChar() throws Exception  {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
		tst1(TstConstants.DS_CHAR, TstConstants.SIZE_SMALL);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		tst1(TstConstants.DS_CHAR, TstConstants.SIZE_LARGE);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}

	public void testLvChar() throws Exception  {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
		tst1(TstConstants.DS_LARGE_VIEW_CHAR, TstConstants.SIZE_SMALL);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		tst1(TstConstants.DS_LARGE_VIEW_CHAR, TstConstants.SIZE_LARGE);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}

	private void tst1(int dsType, int sizeId) throws Exception {
		FileView v = TstConstants.getSalesCsvFile(dsType, sizeId);
		StringBuilder b = new StringBuilder(v.getLine(0).getFullLine());

		for (int i = 1; i < v.getRowCount(); i++) {
			b.append('\n').append(v.getLine(i).getFullLine());
		}

		JTextArea ta = new JTextArea();

		ta.setText(b.toString());



		Document document1 = ta.getDocument();
		Document document2 = new FileDocument3( v.asDocumentContent(), false);
		Document document3 = new FileDocument5( v.asDocumentContent(), false);
		ElementIterator iterator1 = new ElementIterator(document1);
		ElementIterator iterator2 = new ElementIterator(document2);
		ElementIterator iterator3 = new ElementIterator(document3);
		
		Element element1 = iterator1.first();
		Element element2 = iterator2.first();
		Element element3 = iterator3.first();
		int count = 0;
        while (element1 != null) {
          System.out.println(element1.getStartOffset() + " " + element1.getEndOffset()
        		  + " / " +  element2.getStartOffset() + " " + element2.getEndOffset());
          assertEquals("Check Start Offset 2 " + count + ": ", element1.getStartOffset(), element2.getStartOffset());
          assertEquals("Check End   Offset 2 " + count + ": ", element1.getEndOffset(),   element2.getEndOffset());
          assertEquals("Check Start Offset 3 " + count + ": ", element1.getStartOffset(), element3.getStartOffset());
          assertEquals("Check End   Offset 3 " + count + ": ", element1.getEndOffset(),   element3.getEndOffset());
          element1 = iterator1.next();
          element2 = iterator2.next();
          element3 = iterator3.next();
        }

        
        check(v, document2);
        check(v, document3);
        
        int textSize = document2.getLength();
        String s;
        Segment seg = new Segment();
		for (int i = 0; i< textSize - 15; i++) {
			for (int j = 0; j < Math.min(100, textSize - 3 - i); j++) {
					s = document1.getText(i, j);
					assertEquals(s, document2.getText(i, j));
					assertEquals(s, document3.getText(i, j));
					
					document2.getText(i, j, seg);
					assertEquals(s, seg.toString());
					document3.getText(i, j, seg);
					assertEquals(s, seg.toString());
			}
		}
	}
	
	private void check(FileView v, Document doc) throws BadLocationException {
		IDataStorePosition p = (IDataStorePosition) doc.getStartPosition();
		
		assertEquals(0, p.getLineNumberRE());
		assertEquals(0, p.getPositionInLineRE());
		assertEquals(0, p.getOffset());
		
		p = (IDataStorePosition) doc.getEndPosition();
		
		int lineCount = v.getRowCount();
		int textSize = 0;
		int lastLinesLength = v.getLine(lineCount - 1).getData().length;
		
		for (int i = 0; i < lineCount; i++) {
			textSize += v.getLine(i).getData().length + 1;
		}
		assertEquals(lineCount - 1, p.getLineNumberRE());
		assertEquals(lastLinesLength, p.getPositionInLineRE());
		assertEquals(textSize - 1, p.getOffset());	
		assertEquals(textSize - 1, doc.getLength());	
		
		
		int o = 0;
		String s;
		Segment seg = new Segment();
		for (int i = 0; i < lineCount; i++) {
			s = v.getLine(i).getFullLine();
			assertEquals(s, doc.getText(o, s.length()));
			doc.getText(o, s.length(), seg);
			assertEquals(s, seg.toString());
			o +=  s.length() + 1;
		}
		

	}
}
