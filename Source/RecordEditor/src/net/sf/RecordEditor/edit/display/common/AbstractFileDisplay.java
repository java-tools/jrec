package net.sf.RecordEditor.edit.display.common;

import java.util.List;

import javax.swing.JTable;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.edit.file.FilePosition;
import net.sf.RecordEditor.edit.file.FileView;

public interface AbstractFileDisplay {


	/**
	 * Get the Current Row number
	 * @return Current Row number
	 */
	public abstract int getCurrRow();

//	/**
//	 * Set the current Row
//	 * @param newRow new Current row
//	 * @param layoutId layout the field was found on
//	 * @param fieldNum new field number
//	 */
//	public abstract void setCurrRow(int newRow, int layoutId, int fieldNum);
	
	/**
	 * Set the position
	 * @param position
	 */
	public abstract void setCurrRow(FilePosition position);

	/**
	 * get records that are currently selected or highlighted.
	 *
	 * @return get the selected rows
	 */
	public abstract int[] getSelectedRows();
	
	/**
	 * Get  Selected Lines
	 * @return Selected Lines
	 */
	@SuppressWarnings("unchecked")
	public abstract List<AbstractLine> getSelectedLines();

	/**
	 * Get the current line (tree display only)
	 * @return current line (tree display only, otherwise null)
	 */
	@SuppressWarnings("unchecked")
	public AbstractLine getTreeLine();
	
	/**
	 * @return
	 * @see javax.swing.JComboBox#getSelectedIndex()
	 */
	public abstract int getLayoutIndex();

	/**
	 * @param recordIndex new record index 
	 * @see javax.swing.JComboBox#setSelectedIndex(int)
	 */
	public abstract void setLayoutIndex(int recordIndex);

	/**
	 * @return the fileView
	 */
	@SuppressWarnings("unchecked")
	public abstract FileView getFileView();

	/**
	 * Stop editing a cell
	 */
	public abstract void stopCellEditing();
	
	/**
	 * 
	 * @return
	 */
	public abstract  JTable getJTable();

}