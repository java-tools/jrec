/*
 * Created on 7/10/2005
 *
 * This is an example of editing a specific file
 * (without displaying the RecordEditor File selection screen)
 *
 *
 * Requirements:
 *
 * 1) Check the values in Constants.java are correct !!!
 *
 */
package net.sf.RecordEditor.examples;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import net.sf.RecordEditor.edit.display.LineList;
import net.sf.RecordEditor.edit.file.FileView;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.RecordEditor.utils.CopyBookDbReader;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;


/**
 * This is an example of editing a specific file
 * (without displaying the RecordEditor File selection screen)
 *
 * @author Bruce Martin
 * 
 */
public class XmplEditSpecificFile1 {

	private static String filename   = TstConstants.EXAMPLE_FILE_DIRECTORY
									 + "Ams_LocDownload_20041228.txt";
	private static String layoutName = "ams Store";
	private static int initialRow = 0;

	/**
	 * This is an example of editing a specific file
	 * (without displaying the RecordEditor File selection screen)
	 *
	 * @param args program args
	 */
    public static void main(String[] args) {
    	CopyBookDbReader copyBookInterface = new CopyBookDbReader();
		LayoutDetail fileDescription = copyBookInterface.getLayout(layoutName);

		if (fileDescription == null) {
			System.out.println(
				"Record Layout \"" + layoutName + "\" not loaded: "
				+ copyBookInterface.getMessage());
		} else {
		    try {
		        FileView file = new FileView(filename,
		                			   fileDescription,
		                			   false);

		        new ReMainFrame("Specific file", "");
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
}
