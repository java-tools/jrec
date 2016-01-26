/**
 * 
 */
package net.sf.RecordEditor.edit.display.util;


import java.awt.Point;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BasePanel;

/**
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class XFullDisplay extends ReFrame {



	/**
	 * @param docName
	 * @param name
	 * @param doc
	 */
	public XFullDisplay( FileView t) {
		super(t.getFileNameNoDirectory(), "XX", t);
		
		BasePanel p = new BasePanel();
		JTable tbl = new JTable(t);
		JScrollPane scrollpane = new JScrollPane(tbl);
		p.addComponentRE(1, 5, BasePanel.FILL, BasePanel.GAP,
                BasePanel.FULL, BasePanel.FULL,
                scrollpane);
		
		super.addMainComponent(p);
		this.setVisible(true);
		this.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		
		System.out.println();
		System.out.println(scrollpane.getViewport().getHeight()
				+ " " + scrollpane.getHeight()
				+ " " + scrollpane.getViewport().getY()
				+ " " + tbl.getVisibleRect().y
				+ " " + tbl.getVisibleRect().height
				+ " " + tbl.getRowHeight()
				
				+ " " + tbl.rowAtPoint(new Point(5, tbl.getVisibleRect().height - 2))
				+ " " + tbl.rowAtPoint(new Point(1, 33000000 * 16))
		);
		System.out.println((Integer.MAX_VALUE / 31560000));
	}

}
