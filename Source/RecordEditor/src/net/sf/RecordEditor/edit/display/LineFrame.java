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

import javax.swing.event.TableModelEvent;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.edit.display.models.LineModel;
import net.sf.RecordEditor.edit.display.util.LinePosition;
import net.sf.RecordEditor.re.file.FieldMapping;
import net.sf.RecordEditor.re.file.FileView;
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
public class LineFrame extends  BaseLineFrame implements ILineDisplay {

    private int currRow;
    //private ImageIcon[] icons = Common.getArrowIcons();

	private ActionListener listner = new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				stopCellEditing();

				if (event.getSource() == btnPanel.buttons[0]) {
					currRow = 0;
					rowChanged();

				} else if (event.getSource() == btnPanel.buttons[1]) {
						changeRow(-1);
				} else if (event.getSource() == btnPanel.buttons[2]) {
						changeRow(1);
				} else if (event.getSource() == btnPanel.buttons[3]) {
					currRow = getFileView().getRowCount() - 1;
					rowChanged();
				} else if (event.getSource() == oneLineHex) {
				    ap_100_setHexFormat();
				}
			}
	};



	protected LineFrame(
			final String screenName,
			final FileView viewOfFile,
   		 	final int cRow,
   		 	final boolean changeRow) {
		super(screenName, viewOfFile, false, ! viewOfFile.getLayout().isXml(), changeRow);

		init_101_setRecord(changeRow);

		init_100_setupRow(cRow);
		init_200_setupFields(Common.getArrowIcons(), listner, changeRow);
		init_300_setupScreen(changeRow);
	}

	/**
	 * Define the screen fields
	 * @param cRow current row (row to display)
	 * @param btnPanel button panel
	 */
	public void init_100_setupRow(int cRow) {

		lineNum.setText(Integer.toString(cRow + 1));
		lineNum.setEditable(false);

		record.setCurrentLine(cRow, fileView.getCurrLayoutIdx());
		currRow = cRow;
	}

	protected LineFrame(
			final String screenName,
			final FileView viewOfFile,
   		 	final AbstractLine line,
   		 	final boolean changeRow) {
		super(screenName, viewOfFile, false, ! viewOfFile.getLayout().isXml(), changeRow);

		init_101_setRecord(changeRow);

		record.setCurrentLine(line, fileView.getCurrLayoutIdx());
		currRow = Common.NULL_INTEGER;
		init_200_setupFields(Common.getArrowIcons(), listner, changeRow);
		init_300_setupScreen(changeRow);
	}

	private void init_101_setRecord(boolean changeRow) {

		if (changeRow) {
			record = new LineModel(fileView);
		} else {
			record = new LineModel(fileView, 2);
		}
		setModel(record);
	}

	/**
	 * Get the Row being displayed
	 *
	 * @return current row
	 */
	public int getCurrRow() {
		return currRow;
	}



	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.ILineDisplay#setLine(net.sf.JRecord.Details.AbstractLine)
	 */
	@Override
	public void setLine(AbstractLine line) {
		setCurrRow(fileView.indexOf(line));
	}

	/**
	 * Set the current display row
	 *
	 * @param newRow new row to be displayed
	 */
	public void setCurrRow(int newRow) {
		if ((newRow >= 0) && (currRow != newRow)) {
			currRow = newRow;
			rowChanged();
		}
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

		setCurrRow(newRow);

		if (fieldNum > 0 && getLayoutIndex() == layout) {
			int fNo =  FieldMapping.getAdjColumn(getModel().getFieldMapping(), layout, fieldNum);
		    tblDetails.getSelectionModel().clearSelection();
		    tblDetails.getSelectionModel().setSelectionInterval(fNo, fNo);
		    tblDetails.editCellAt(fNo, record.firstDataColumn);
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
	@Override
	public void tableChanged(TableModelEvent event) {

		int firstRow = event.getFirstRow();
		switch (event.getType()) {
			case (TableModelEvent.INSERT):
				if (firstRow <= currRow) {
					changeRow(event.getLastRow() 	-  firstRow + 1);
				}
			break;
			case (TableModelEvent.DELETE):
				if (currRow > event.getLastRow()) {
					currRow -= event.getLastRow() - firstRow + 1;
					rowChanged();
				} else if (currRow > firstRow) {
					currRow -= Math.min(fileView.getRowCount(), firstRow);
					rowChanged();
				}
			break;
			default:
			    currRow = record.getActualLineNumber();
				lineNum.setText(Integer.toString(currRow + 1));
			    /*System.out.println("~~>> " + currRow + " | "
			            + event.getType()
			            + " ! " + event.getFirstRow()
			            + " # " + event.getLastRow());*/
				//System.out.println("LineFrame Table changed: " + event.getType() + " " + TableModelEvent.UPDATE);
				if (super.hasTheFormatChanged(event) || firstRow < 0) {
					record.fireTableDataChanged();
					record.layoutChanged(layout);
				} else if (firstRow <= currRow
				&&  event.getLastRow() >= currRow
				&&  currRow >= 0 ) {		// Table Structure changed
					record.fireTableDataChanged();
				}
		}
		setFullLine();
	}


    /**
     * @see net.sf.RecordEditor.utils.common.ReActionHandler#executeAction(int)
     */
    public void executeAction(int action) {

        if (action == ReActionHandler.REPEAT_RECORD_POPUP) {
        	getFileView().repeatLine(currRow);
        	changeRow(1);
        } else {
            super.executeAction(action);
        }
    }


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.BaseLineDisplay#isActionAvailable(int)
	 */
	@Override
	public boolean isActionAvailable(int action) {
		if (action == ReActionHandler.REPEAT_RECORD_POPUP) {
			return true;
	    }
		return super.isActionAvailable(action);
	}

	/**
	 * This method updates the Screen Display after a change of record.
	 * It updates the layout if required
	 */
	private void rowChanged() {
		int[] colWidths = getColumnWidths();
		try {
			fileView.removeTableModelListener(this);

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
			if (record.getCurrentLine() == null) {
				getParentFrame().close(this);
			}
			int newIdx = record.getCurrentLayout();
	
			if ((newIdx != Common.NULL_INTEGER)
			&&  (newIdx != getLayoutIndex())) {
		    	   setLayoutIndex(newIdx);
	
		    	   fireLayoutIndexChanged();
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
			super.notifyChangeListners();
		} finally {
			fileView.addTableModelListener(this);
		}
	}


	/**
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#getInsertAfterLine()
	 */
	@Override
	protected LinePosition getInsertAfterLine(boolean prev) {

		if (prev) {
			return getInsertAfterLine(currRow, prev);
		}
		return new LinePosition(record.getCurrentLine(), prev);
	}

	/**
	 * Set enabled / disabled status of the direction buttons
	 */
	protected void setDirectionButtonStatus() {

		boolean allowBack = currRow > 0;
		boolean allowForward = fileView != null && currRow < fileView.getRowCount() - 1;

	    btnPanel.buttons[0].setEnabled(allowBack);
	    btnPanel.buttons[1].setEnabled(allowBack);
	    btnPanel.buttons[2].setEnabled(allowForward);
	    btnPanel.buttons[3].setEnabled(allowForward);
	}

	private void changeRow(int amount) {
		currRow += amount;
		rowChanged();
	}


	@Override
	public String getScreenName() {
		return super.getScreenName() + " " + (currRow + 1);
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#getNewDisplay(net.sf.RecordEditor.edit.file.FileView)
	 */
	@Override
	protected BaseDisplay getNewDisplay(FileView view) {
		return new LineFrame("Record:", view, 0, true);
	}
}