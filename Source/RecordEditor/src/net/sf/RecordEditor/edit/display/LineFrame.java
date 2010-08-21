/*
 * Created on 14/05/2004
 *
 *  This class displays one line (or Record) on the screen using the supplied record
 * layout. It displays each field of the record one field at a time going down
 * the screen. It displays each field in both a Formated view and a straight Text
 * field. For binary files, Hex format is also displayed
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *   - Added right mouse button click popup menu on file list table
 *
 * # Version 0.60 Bruce Martin 2007/02/16
 *   - Support for sorting (will find the new record position after
 *     the file has been sorted
 *
 * # Version 0.61 Bruce Martin 2007/04/05
 *   - Added three line hex display option
 *   - Added full line display at the bottom of the screen
 *   - code ReOrg
 */
package net.sf.RecordEditor.edit.display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.event.TableModelEvent;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.edit.display.models.LineModel;
import net.sf.RecordEditor.edit.file.FileView;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;




/**
 *  This class displays one line (or Record) on the screen using the supplied record
 * layout. It displays each field of the record one field at a time going down
 * the screen. It displays each field in both a Formated view and a straight Text
 * field. For binary files, Hex format is also displayed
 *
 * @author Bruce Martin
 * @version 0.51
 */
public class LineFrame extends    BaseLineFrame {

    private int currRow;
    private ImageIcon[] icons = Common.getArrowIcons();

	private ActionListener listner = new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				stopCellEditing();

				if (event.getSource() == btn[0]) {
					currRow = 0;
					rowChanged();

				} else if (event.getSource() == btn[1]) {
						changeRow(-1);
				} else if (event.getSource() == btn[2]) {
						changeRow(1);
				} else if (event.getSource() == btn[3]) {
					currRow = getFileView().getRowCount() - 1;
					rowChanged();
				} else if (event.getSource() == oneLineHex) {
				    ap_100_setHexFormat();
				}
			}
	};

	/**
	 * Creates a Record Screen
	 *
	 * @param group      - Layout Definition of the file
	 * @param viewOfFile - Current view of the file
	 * @param masterFile - Internal representation of the file
	 * @param cRow       - current row
	 */
	public LineFrame(final FileView<?> viewOfFile,
	        		 final int cRow) {
		this("Record: ", viewOfFile, cRow);
	}
	
	
	public LineFrame(
			final String screenName,
			final FileView<?> viewOfFile,
   		 	final int cRow) {
		super(screenName, viewOfFile, false, ! viewOfFile.getLayout().isXml());

		JPanel btnPanel = new JPanel();

		record = new LineModel(fileView);
		setModel(record);

		init_100_setupRow(cRow);
		init_200_setupFields(btnPanel, icons, listner);
		init_300_setupScreen(btnPanel);

		show();
	}
	
	/**
	 * Define the screen fields
	 * @param cRow current row (row todisplay)
	 * @param btnPanel button panel
	 */
	public void init_100_setupRow(int cRow) {
	
		lineNum.setText(Integer.toString(cRow + 1));
		lineNum.setEditable(false);
	
		record.setCurrentLine(cRow, fileView.getCurrLayoutIdx());
		currRow = cRow;
	}

	public LineFrame(final FileView<?> viewOfFile,
   		 final AbstractLine<?> line) {
		super("Record: ", viewOfFile, false, ! viewOfFile.getLayout().isXml());
		
		JPanel btnPanel = new JPanel();
		
		record = new LineModel(fileView);
		setModel(record);
		
		record.setCurrentLine(line, fileView.getCurrLayoutIdx());
		currRow = Common.NULL_INTEGER;
		init_200_setupFields(btnPanel, icons, listner);
		init_300_setupScreen(btnPanel);
		
		show();
	}


	/**
	 * Get the Row being displayed
	 *
	 * @return current row
	 */
	public int getCurrRow() {
		return currRow;
	}

	

	/**
	 * Set the row to be displayed
	 *
	 * @param newRow new row to display
	 * @param layout layout the field was found on
	 * @param fieldNum new field number
	 *
	 * @see net.sf.RecordEditor.edit.BaseLineDisplay.setCurrRow
	 */
	public void setCurrRow(int newRow, int layout, int fieldNum) {
		if ((newRow >= 0) && (currRow != newRow)) {
			currRow = newRow;
			rowChanged();
		}

		if (fieldNum > 0 && getLayoutIndex() == layout) {
		    tblDetails.getSelectionModel().clearSelection();
		    tblDetails.getSelectionModel().setSelectionInterval(fieldNum, fieldNum);
		    tblDetails.editCellAt(fieldNum, LineModel.DATA_COLUMN);
		}
	}


	/**
	 * Deleting one record
	 */
	public void deleteLines() {
		getFileView().deleteLine(currRow);

		rowChanged();
	}


	/**
	 * @return get the selected rows
	 */
	public int[] getSelectedRows() {
		return new int[] {getCurrRow()};
	}



	/**
	 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
	 */
	public void tableChanged(TableModelEvent event) {

		switch (event.getType()) {
			case (TableModelEvent.INSERT):
				changeRow(event.getLastRow() 	-  event.getFirstRow() + 1);
			break;
			case (TableModelEvent.DELETE):
				if (currRow > event.getLastRow()) {
					currRow -= event.getLastRow() - event.getFirstRow() + 1;
					rowChanged();
				} else if (currRow > event.getFirstRow()) {
					currRow -= Math.min(fileView.getRowCount(), event.getFirstRow());
					rowChanged();
				}
			break;
			default:
			    currRow = record.getActualLineNumber();
				lineNum.setText(Integer.toString(currRow));
			    /*System.out.println("~~>> " + currRow + " | "
			            + event.getType()
			            + " ! " + event.getFirstRow()
			            + " # " + event.getLastRow());*/
				//System.out.println("LineFrame Table changed: " + event.getType() + " " + TableModelEvent.UPDATE);
				if (super.hasTheFormatChanged(event)
				|| (event.getFirstRow() <= currRow
				&&  event.getLastRow() >= currRow)) {
					record.fireTableDataChanged();
				}
		}
		setFullLine();
	}


    /**
     * @see net.sf.RecordEditor.utils.common.ReActionHandler#executeAction(int)
     */
    public void executeAction(int action) {

        if (action == ReActionHandler.REPEAT_RECORD) {
        	getFileView().repeatLine(currRow);
        	changeRow(1);
        } else {
            super.executeAction(action);
        }
    }


	/**
	 * This method updates the Screen Display after a change of record.
	 * It updates the layout if required
	 */
	private void rowChanged() {
		int[] colWidths = getColumnWidths();
	    stopCellEditing();

		if (currRow >= getFileView().getRowCount()) {
			if (currRow == 0) {
				this.closeWindow();
			} else {
				currRow -= 1;
			}
		}

		lineNum.setText(Integer.toString(currRow + 1));

		record.setCurrentLayout(getLayoutIndex());

		record.setCurrentLine(currRow, getLayoutIndex());
		int newIdx = record.getCurrentLayout();

		if ((newIdx != Common.NULL_INTEGER)
		&&  (newIdx != getLayoutIndex())) {
	    	   setLayoutIndex(newIdx);

	    	   changeLayout();
	    	   setColWidths();
	    	   setDirectionButtonStatus();
	    	   setFullLine();
		} else {
//			System.out.println("::Setting up display ");
			setColWidths();
			setDirectionButtonStatus();
			setFullLine();
		}

		setColumnWidths(colWidths);
	}


	/**
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#getInsertAfterLine()
	 */
	@Override
	protected AbstractLine<?> getInsertAfterLine(boolean prev) {

		if (prev) {
			if (currRow > 0) {
				return fileView.getLine(currRow - 1);
			}
			return null;
		}
		return record.getCurrentLine();
	}

	/**
	 * Set enabled / disabled status of the direction buttons
	 */
	protected void setDirectionButtonStatus() {

		boolean allowBack = currRow > 0;
		boolean allowForward = currRow < fileView.getRowCount() - 1;
		
	    btn[0].setEnabled(allowBack);
	    btn[1].setEnabled(allowBack);
	    btn[2].setEnabled(allowForward);
	    btn[3].setEnabled(allowForward);
	}
	
	private void changeRow(int amount) {
		currRow += amount;
		rowChanged();
	}
}