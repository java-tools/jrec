/**
 * 
 */
package net.sf.RecordEditor.edit.display.util;



import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.sf.RecordEditor.re.file.FileView;

/**
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class XFullDisplay1 extends JFrame {



	/**
	 * @param docName
	 * @param name
	 * @param doc
	 */
	public XFullDisplay1( FileView t) {

		System.out.println(t.getRowCount());
		JScrollPane comp = new JScrollPane(new JTable(t));
		this.getContentPane().add(comp);
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
	}

}
