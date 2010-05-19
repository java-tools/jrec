/*
 * Created on 7/10/2005
 *
 * This is an example of editing a program supplied data with the
 * RecordEditor
*
 * Requirements:
 *
 * 1) Check the values in Constants.java are correct !!!
 *
 */
package net.sf.RecordEditor.examples;

import java.io.IOException;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import net.sf.RecordEditor.edit.display.LineList;
import net.sf.RecordEditor.edit.file.FileView;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.RecordEditor.utils.CopyBookDbReader;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;


/**
 * This is an example of editing program supplied data with the
 * RecordEditor
 *
 * @author Bruce Martin
 *
 */
public final class XmplEditSuppliedData {

 	private static int initialRow = 0;


	private static String layoutName = "DTAR020";


	private static final String[] KEYCODES = {
			"69684558", "69694158", "69694158", "69694158", "63604808", "62684671",
			"62684671", "64634429", "66624458", "63674861", "65674532", "64614401",
			"64614401", "61664713", "61664713"};

	private static final int[] STORES = {
			20, 20, 20, 20, 20, 20, 20, 20, 20,
			20, 20, 59, 59, 59, 59
	};

	private static String date = "040118";

	private static final String[] DEPT = {
			"280", "280", "280", "280", "170", "685",
			"685", "957", "957", "957", "929", "957",
			"957", "335", "335"
	};

	private static final int[] QUANTITIES = {
			1, 1, -1, 1, 1, 1, -1, 1,
			1, 10, 1, 1, 1, 1, -1
	};

	private static final double[] RETAIL_SALES = {
			5.01, 19.00, -19.00, 5.01, 4.87, 69.99,
			-69.99, 3.99, 0.89, 2.70, 3.59, 1.99,
			1.99, 17.99, -17.99
	};

	/**
	 * This is an example of a program supplying the data to be editted
	 */
	/**
	 *
	 */
	private XmplEditSuppliedData() {

    	CopyBookDbReader copyBookInterface = new CopyBookDbReader();
		LayoutDetail fileDescription = copyBookInterface.getLayout(layoutName);

		if (fileDescription == null) {
			System.out.println(
				"Record Layout \"" + layoutName + "\" not loaded: "
				+ copyBookInterface.getMessage());
		} else {
		    try {
		        FileView file = new MyFileView(
		        					   			fileDescription,
												false);

		        setupData(file);

		        new ReMainFrame("", "");
		        LineList list = new LineList(fileDescription, file, file);
				list.setCurrRow(initialRow, -1, -1);

				list.addInternalFrameListener(new InternalFrameAdapter() {
					public void internalFrameClosed(final InternalFrameEvent e) {
						Common.closeConnection();

						System.exit(0);
					}
				});


			} catch (Exception e) {
					e.printStackTrace();
			}
		}
	}


	/**
	 * Setup the file
	 *
	 * @param file file to load data into
	 */
	private void setupData(FileView file) {
		LineDTAR0020 line;
		int i;

		for (i = 0; i < KEYCODES.length; i++) {
			line = new LineDTAR0020(file.getLayout());

			try {
				line.setStore(STORES[i]);
				line.setField("KEYCODE-NO", KEYCODES[i]);
				line.setField("DATE", date);
				line.setField("DEPT-NO", DEPT[i]);
				line.setQuantity(QUANTITIES[i]);
				line.setSalesRetail(RETAIL_SALES[i]);

				file.add(line);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}


	/**
	 * Creating my own fileView with a getColumn for use with the
	 * standard column header and my file save.
	 */
	private class MyFileView extends FileView {

		/**
		 * Creating my own fileView with a getColumn for use with the
		 * standard column header rendor
		 *
		 * @param pFd record layout
		 * @param pBrowse wether to browse the file or not
		 *
		 * @throws IOException any IOError
		 */
	    public MyFileView(
	        	   final LayoutDetail pFd,
	        	   final boolean pBrowse)
	    throws IOException {
	        super(pFd, null, pBrowse);
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


        /**
         * @see net.sf.RecordEditor.edit.file.FileView#writeFile()
         */
        public void writeFile() {
            // TODO Code to save the FileView details goes here
        }

	}

	/**
	 * editing program supplied data
	 * @param args program args
	 */
    public static void main(String[] args) {

        new XmplEditSuppliedData();
    }
}
