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

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.util.StringTokenizer;

import javax.swing.JTable;

import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.common.ReActionHandler;



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
	public BaseLineDisplay(final String formType,
	        			 final FileView viewOfFile,
	        			 final boolean primary,
	        			 final boolean fullLine,
		        		 final boolean prefered,
		        		 final boolean hex,
		        		 final boolean changeRow) {
		super(formType, viewOfFile, primary, fullLine, true, prefered, hex,
				changeRow ? STD_OPTION_PANEL: NO_OPTION_PANEL);
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

			int startRow = getJTable().getSelectedRow();
			int startCol = getJTable().getSelectedColumn();
			//System.out.println("))) " + startRow + " " + startCol);
			if (startRow >= 0) {
				Clipboard system = Toolkit.getDefaultToolkit().getSystemClipboard();
				try {
					pasteTable(startRow, startCol,
							(String) (system.getContents(this)
									.getTransferData(DataFlavor.stringFlavor)));
				} catch (Exception e) {
				}
			}
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


	protected final void pasteTable(int startRow, int startCol, String trstring) {
		String val, rowStr;
		try {
			JTable tblDetails = getJTable();
			int rowCount = tblDetails.getRowCount();
			int colCount = tblDetails.getColumnCount();
			super.fileMaster.setDisplayErrorDialog(false);

			System.out.println("String is:" + trstring);
			StringTokenizer st1 = new StringTokenizer(trstring, "\n");
			for (int i = 0; st1.hasMoreTokens() && startRow + i < rowCount ; i++) {
				rowStr = st1.nextToken();
				StringTokenizer st2 = new StringTokenizer(rowStr, "\t");
				for (int j = 0; st2.hasMoreTokens() && startCol+ j < colCount ; j++) {
					val = st2.nextToken();

					tblDetails.setValueAt(val, startRow + i, startCol + j);
//					System.out.println("Putting " + val + "at row="
//							+ startRow + i + "column=" + startCol + j);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			super.fileMaster.setDisplayErrorDialog(true);
		}
	}

}
