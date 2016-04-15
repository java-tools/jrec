package net.sf.RecordEditor.test.text;
 
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.ElementIterator;

import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.file.textDocument.FileDocument3;
import net.sf.RecordEditor.test.TstConstants;

public class Xxx {

	public static void main(String[] x) throws Exception {
		FileView v = TstConstants.getSalesCsvFile();
		StringBuilder b = new StringBuilder(v.getLine(0).getFullLine());

		for (int i = 1; i < v.getRowCount(); i++) {
			b.append('\n').append(v.getLine(i).getFullLine());
		}

		JFrame f1 = new JFrame();
		JTextArea ta = new JTextArea();

		ta.setText(b.toString());

		f1.getContentPane().add(new JScrollPane(ta));

		f1.pack();
		f1.setVisible(true);


		Document document1 = ta.getDocument();
		Document document2 = new FileDocument3( v.asDocumentContent(), false);
		ElementIterator iterator1 = new ElementIterator(document1);
		ElementIterator iterator2 = new ElementIterator(document2);

		Element element1 = iterator1.first();
		Element element2 = iterator2.first();
        while (element1 != null) {
          System.out.println(element1.getStartOffset() + " " + element1.getEndOffset()
        		  + " / " +  element2.getStartOffset() + " " + element2.getEndOffset());
          element1 = iterator1.next();
          element2 = iterator2.next();
        }
	}
}
