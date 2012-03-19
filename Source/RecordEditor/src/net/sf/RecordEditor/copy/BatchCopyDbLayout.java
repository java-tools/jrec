/**
 * 
 */
package net.sf.RecordEditor.copy;


import net.sf.RecordEditor.re.openFile.LayoutSelectionDB;
import net.sf.RecordEditor.utils.CopyBookDbReader;


/**
 * @author Bruce Martin
 *
 */
public class BatchCopyDbLayout {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		if (args == null || args.length == 0) {
			System.out.println("You must supply a Copy definition !!! ");
		} else {
			CopyBookDbReader cpyInterfact = CopyBookDbReader.getInstance();
			LayoutSelectionDB db1 = new LayoutSelectionDB(cpyInterfact, true);
			LayoutSelectionDB db2 = new LayoutSelectionDB(cpyInterfact, true);
			
			db1.setLoadFromFile(true);
			db2.setLoadFromFile(false);
			
			new BatchCopy(db1, db2, args);
		}
	}

}
