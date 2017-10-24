/*
 * Created on 14/05/2004
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *   - changed to use ReFrame (from JFrame) which keeps track
 *     of the active form
 *   - changed to use ReActionHandler instead of ActionListener
 * # Version 0.60 Bruce Martin 2007/02/16
 *   - Support for sorting, removed unused vars
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - Support for Printing, Full-Line Display and Selected views added
 * # Version 0.62
 *   - Class divided with most code going into BaseDisplay
 */
package net.sf.RecordEditor.edit.display;


import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;



/**
 * This class displays file details on the screen.  This class has 2 subclasses
 * <ul compact>
 * <li>RecordFrame - displays on record
 * <li>RecordList - displays all records in a table format
 * </ul>
 *
 * <p>This class holds common code / loads buttons etc.
 *
 * @see LineFrame - displays on record
 * @see LineList - displays all records in a table format
 *
 * <p>
 * @author Bruce Martin
 * @version 0.56
 */
public abstract class BaseLineDisplay extends BaseDisplay {


	/**
	 * base class for editing a file
	 *
	 * @param formType panel name
	 * @param group record layout group used to display a record
	 * @param viewOfFile current file table representation
	 * @param masterfile internal representation of a File
	 * @param primary wether the screen is a primary screen
	 */
	public BaseLineDisplay(
			             final BaseHelpPanel panel,
			             final String formType,
	        			 final FileView viewOfFile,
	        			 final boolean primary,
	        			 final boolean fullLine,
		        		 final boolean prefered,
		        		 final boolean hex,
		        		 final int layoutLineOption
//		        		 final boolean changeRow
	) {
		super(panel, formType, viewOfFile, primary, fullLine, true, prefered, hex, layoutLineOption);
//				changeRow ? STD_OPTION_PANEL: NO_OPTION_PANEL);
        
//        SwingUtilities.invokeLater(new Runnable() {			
//			@Override public void run() {
//				Common.calcColumnWidths(tblDetails, 2);
//			}
//		});

	}


	/**
	 * Get the insert / paste position
	 * @return insert / paste position
	 */
	protected int getInsertAfterPosition() {
		return getStandardPosition();
	}



	/**
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#executeAction(int)
	 */
	@Override
	public void executeAction(int action) {
		if (action == ReActionHandler.PASTE_TABLE_OVERWRITE) {
			pasteOverTbl();
		} else {
			super.executeAction(action);
		}
	}


	/**
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#isActionAvailable(int)
	 */
	@Override
	public boolean isActionAvailable(int action) {

		return action == ReActionHandler.PASTE_TABLE_OVERWRITE
			|| super.isActionAvailable(action);
	}
}
