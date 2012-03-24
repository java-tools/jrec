/**
 * 
 */
package net.sf.RecordEditor.copy;

import net.sf.RecordEditor.re.openFile.LayoutSelectionDBCreator;
import net.sf.RecordEditor.utils.CopyBookDbReader;
import net.sf.RecordEditor.utils.CopyBookInterface;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;

/**
 * @author Bruce Martin
 *
 */
public class CopyDBLayout {

	public final static void newMenu(final CopyBookInterface cpyInterfact) {
		 new Menu(new LayoutSelectionDBCreator(cpyInterfact) , "Files.txt");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		 new ReMainFrame("File Copy", "", "Cpy");
		 newMenu(CopyBookDbReader.getInstance());

	}
}
