/*
 * Created on 7/10/2005
 *
 * This is an example of editing a specific file
 * (without displaying the RecordEditor File selection screen)
 * using the Record Frame (ie one record per frame)
 *
 *
 * Requirements:
 *
 * 1) Check the values in Constants.java are correct !!!
 *
 */
package net.sf.RecordEditor.examples;

import net.sf.RecordEditor.edit.display.DisplayBuilder;
import net.sf.RecordEditor.edit.display.LineFrame;
import net.sf.RecordEditor.edit.open.DisplayBuilderFactory;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.CopyBookDbReader;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;


/**
 * This is an example of editing a specific file
 * (without displaying the RecordEditor File selection screen)
 * using the Record Frame (ie one record per frame)
 *
 * @author Bruce Martin
 *
 */
public class XmplEditSpecificFile2 {

	private static String filename   = TstConstants.EXAMPLE_FILE_DIRECTORY
									 + "Ams_LocDownload_20041228.txt";
	private static String layoutName = "ams Store";

    public static void main(String[] args) {
    	CopyBookDbReader copyBookInterface = new CopyBookDbReader();
		LayoutDetail fileDescription = copyBookInterface.getLayout(layoutName);

		if (fileDescription == null) {
			System.out.println(
				"Record Layout \"" + layoutName +  "\" not loaded: "
				+ copyBookInterface.getMessage());
		} else {
		    try {
		        FileView file = new FileView(filename,
		                			   fileDescription,
		                			   false);

		        new ReMainFrame("Specific file", "", "X");
		        DisplayBuilderFactory.getInstance().newDisplay(
		        		DisplayBuilderFactory.ST_RECORD_SCREEN, "Record:", null, fileDescription, file, 0);
		        //DisplayBuilder.newLineDisplay(null, file, 0);
	/*			lineFrame.addWindowListener(new WindowAdapter() {
					public void windowClosed(WindowEvent e) {
						Common.closeConnection();

						System.exit(0);
					}
				});*/
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    }
}
