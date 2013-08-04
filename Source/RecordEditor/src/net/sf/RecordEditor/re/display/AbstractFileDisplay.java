package net.sf.RecordEditor.re.display;

import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JTable;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.re.file.FilePosition;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;

/**
 * Description of a class that will display a view of a file on
 * the screen
 *
 * @author Bruce Martin
 *
 */
public interface AbstractFileDisplay extends ReActionHandler {


	/**
	 * Get the Current Row number
	 * @return Current Row number
	 */
	public abstract int getCurrRow();


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
	public abstract List<AbstractLine> getSelectedLines();

	/**
	 * Get the current line (tree display only)
	 * @return current line (tree display only, otherwise null)
	 */
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
	public abstract FileView getFileView();

	/**
	 * Stop editing a cell
	 */
	public abstract void stopCellEditing();

	/**
	 *
	 * @return the table being displayed
	 */
	public abstract  JTable getJTable();

	/**
	 * Notify screens of updated layout
	 * @param newLayout
	 */
	public void setNewLayout(AbstractLayoutDetails newLayout);

	public abstract IDisplayFrame<? extends AbstractFileDisplay> getParentFrame();

	public abstract BaseHelpPanel getActualPnl();

	public abstract void doClose();


	public abstract void setDockingPopup(MouseListener dockingPopup);


	public abstract void insertLine(int adj);


	public abstract void setCurrRow(int newRow, int layoutId, int fieldNum);


	public abstract IExecuteSaveAction getExecuteTasks();
}