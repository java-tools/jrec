/**
 * 
 */
package net.sf.RecordEditor.diff;

import net.sf.RecordEditor.re.openFile.LayoutSelectionDBCreator;
import net.sf.RecordEditor.utils.CopyBookDbReader;
import net.sf.RecordEditor.utils.CopyBookInterface;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;

/**
 * @author Bruce Martin
 *
 */
public class CompareDBLayout {

	public final static void newMenu(final CopyBookInterface cpyInterface) {
		 new Menu(new LayoutSelectionDBCreator(cpyInterface) , "Files.txt");
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		 new ReMainFrame("File Compare", "");
		 newMenu(CopyBookDbReader.getInstance());

	}

}
