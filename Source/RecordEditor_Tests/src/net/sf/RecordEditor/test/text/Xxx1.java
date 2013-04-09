package net.sf.RecordEditor.test.text;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.test.TstConstants;
import net.sf.RecordEditor.utils.swing.TextLineNumber;

public class Xxx1 {

	public static void main(String[] x) throws Exception {
		FileView v = TstConstants.getSalesCsvFile();
		StringBuilder b = new StringBuilder(v.getLine(0).getFullLine());

		for (int i = 1; i < v.getRowCount(); i++) {
			b.append('\n').append(v.getLine(i).getFullLine());
		}

		JFrame f1 = new JFrame();
		JTextArea ta = new JTextArea();
		ta.setText(b.toString());
		//TextLineNumber tln = new TextLineNumber(ta, 5);
		//JScrollPane sc = new JScrollPane(ta);

		//sc.setRowHeaderView( tln );


//		f1.getContentPane().add(sc);
//		f1.getContentPane().add(new JScrollPane(sc));

		f1.getContentPane().add(TextLineNumber.getTextLineNumber(ta, 5));
		//f1.getContentPane().add(tln);
		//f1.getContentPane().add(ta);

		f1.pack();
		f1.setVisible(true);
	}
}
