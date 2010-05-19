/*
 * Created on 7/10/2005
 *
 * This is an example of editing a specific file
 * (without displaying the RecordEditor File selection screen)
 * using the Record Frame (ie one record per frame)
*
 * Requirements:
 *
 * 1) Check the values in Constants.java are correct !!!
 *
 */
package net.sf.RecordEditor.examples;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import net.sf.RecordEditor.edit.file.FileView;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.RecordEditor.utils.CopyBookDbReader;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.BasePanel;


/**
 * This is an example of editing a specific file using your own
 * JTable
 *
 * @author Bruce Martin
 *
 */
public final class XmplEditSpecificFile3 {

    private static final int ROW_WIDTH = 40;

	private static String filename   = TstConstants.EXAMPLE_FILE_DIRECTORY
									 + "Ams_LocDownload_20041228.txt";
	private static String layoutName = "ams Store";

	private static BasePanel pnl = new BasePanel();
	private static JFrame frame = new JFrame();
	private static JTable tbl;

	/**
	 * This is an example of editing a specific file using your own
	 * JTable
	 */
	private XmplEditSpecificFile3() {

    	CopyBookDbReader copyBookInterface = new CopyBookDbReader();
		LayoutDetail fileDescription = copyBookInterface.getLayout(layoutName);

		if (fileDescription == null) {
			System.out.println(
				"Record Layout \"" + layoutName + "\" not loaded: "
				+ copyBookInterface.getMessage());
		} else {
		    try {
		        FileView file = new MyFileView(filename,
		                			   fileDescription,
		                			   false);
		        tbl = new JTable(file);

				pnl.addComponent(1, 5,
				         BasePanel.FILL,
				         BasePanel.GAP1,
				         BasePanel.FULL, BasePanel.FULL,
						 new JScrollPane(tbl));

				tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				TableColumnModel tcm = tbl.getColumnModel();
				tcm.getColumn(0).setPreferredWidth(1);
				tcm.getColumn(1).setPreferredWidth(ROW_WIDTH);

				pnl.done();

		        frame.getContentPane().add(pnl);
		        frame.pack();
		        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

				frame.addWindowListener(new WindowAdapter() {
					public void windowClosed(WindowEvent e) {
						Common.closeConnection();

						System.exit(0);
					}
				});

				frame.setVisible(true);

				System.out.println(file.getMsg());
			} catch (Exception e) {
					e.printStackTrace();
			}
		}
	}


	/**
	 * Creating my own fileView with a getColumn for use with the
	 * standard column header rendor
	 */
	private class MyFileView extends FileView {

		/**
		 * Creating my own fileView with a getColumn for use with the
		 * standard column header rendor
		 *
		 * @param pFileName file name
		 * @param pFd record layout
		 * @param pBrowse wether to browse the file or not
		 *
		 * @throws IOException any IOError
		 */
	    public MyFileView(final String pFileName,
	    		   final LayoutDetail pFd,
	        	   final boolean pBrowse)
	    throws IOException, RecordException {
	        super(pFileName, pFd, pBrowse);
	    }

        /**
         * @see net.sf.RecordEditor.edit.file.FileView#getColumnName(int, int)
         */
        public String getColumnName(int col) {
    		if (col > 1) {
    		    int layout = this.getCurrLayoutIdx();
    		    return getRealColumnName(layout,
    		            				 getRealColumn(layout, col - 2));
    		}
            return super.getColumnName(col);
        }
	}

	/**
	 * editing a specific file
	 * @param args program args
	 */
    public static void main(String[] args) {

        new XmplEditSpecificFile3();
    }
}
