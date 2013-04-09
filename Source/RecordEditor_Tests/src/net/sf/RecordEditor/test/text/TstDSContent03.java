package net.sf.RecordEditor.test.text;

import javax.swing.JTextArea;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.ElementIterator;

import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.test.TstConstants;
import junit.framework.TestCase;

public class TstDSContent03 extends TestCase {


	public void test1() throws Exception {
		FileView v = TstConstants.getSalesCsvFile();
		StringBuilder b = new StringBuilder(v.getLine(0).getFullLine());

		for (int i = 1; i < v.getRowCount(); i++) {
			b.append('\n').append(v.getLine(i).getFullLine());
		}

		JTextArea ta = new JTextArea();

		ta.setText(b.toString());



		Document document1 = ta.getDocument();
		Document document2 = v.asDocument();
		ElementIterator iterator1 = new ElementIterator(document1);
		ElementIterator iterator2 = new ElementIterator(document2);

		Element element1 = iterator1.first();
		Element element2 = iterator2.first();
        while (element1 != null) {
          System.out.println(element1.getStartOffset() + " " + element1.getEndOffset()
        		  + " / " +  element2.getStartOffset() + " " + element2.getEndOffset());
          assertEquals("Check Start Offset: ", element2.getStartOffset(), element1.getStartOffset());
          assertEquals("Check End   Offset: ", element2.getEndOffset(), element1.getEndOffset());
          element1 = iterator1.next();
          element2 = iterator2.next();
        }

	}
}
