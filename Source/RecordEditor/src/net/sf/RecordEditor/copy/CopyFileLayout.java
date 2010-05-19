/**
 * 
 */
package net.sf.RecordEditor.copy;

import net.sf.RecordEditor.utils.openFile.LayoutSelectionFileCreator;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;

/**
 * @author Bruce Martin
 *
 */
public class CopyFileLayout {

	public final static void newMenu() {
		 new Menu(new LayoutSelectionFileCreator(), "CobolFiles.txt");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		 new ReMainFrame("File Copy", "");
		 newMenu();
	}

}
