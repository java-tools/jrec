/**
 * 
 */
package net.sf.RecordEditor.diff;

import net.sf.RecordEditor.re.openFile.LayoutSelectionFileCreator;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;

/**
 * @author Bruce Martin
 *
 */
public class CompareFileLayout {

	public final static void newMenu() {
		 new Menu(new LayoutSelectionFileCreator(), "CobolFiles.txt");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		 new ReMainFrame("File Compare", "", "Cmp");
		 newMenu();
	}

}
