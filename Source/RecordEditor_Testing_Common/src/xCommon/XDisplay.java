/**
 * 
 */
package xCommon;

import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.re.display.AbstractFileDisplay;
import net.sf.RecordEditor.re.display.IDisplayFrame;
import net.sf.RecordEditor.re.display.IExecuteSaveAction;
import net.sf.RecordEditor.re.file.FilePosition;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;

/**
 * @author Bruce01
 *
 */
public class XDisplay implements AbstractFileDisplay {
	private final static int[] EMPTY_ROW_LIST = {};
	final FileView view;
	final int[] selectedRows;
	
	int currentRow = 0,
		layoutIdx = 0;
	
	/**
	 * 
	 */
	public XDisplay(FileView view, int[] selectedRows) {
		this.view = view;
		this.selectedRows = selectedRows == null ? EMPTY_ROW_LIST : selectedRows;
	}

	
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.display.AbstractFileDisplay#getActionHandler()
	 */
	@Override
	public ReActionHandler getActionHandler() {
		return null;
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.display.AbstractFileDisplay#getCurrRow()
	 */
	@Override
	public int getCurrRow() {
		return currentRow;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.display.AbstractFileDisplay#setCurrRow(net.sf.RecordEditor.re.file.FilePosition)
	 */
	@Override
	public void setCurrRow(FilePosition position) {
		currentRow = position.row;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.display.AbstractFileDisplay#isOkToUseSelectedRows()
	 */
	@Override
	public boolean isOkToUseSelectedRows() {
		return false;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.display.AbstractFileDisplay#doClose()
	 */
	@Override
	public void doClose() {
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.display.AbstractFileDisplay#getSelectedRows()
	 */
	@Override
	public int[] getSelectedRows() {	
		return selectedRows;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.display.AbstractFileDisplay#getSelectedLines()
	 */
	@Override
	public List<AbstractLine> getSelectedLines() {
		ArrayList<AbstractLine> lines = new ArrayList<AbstractLine>(selectedRows.length);
		
		for (int idx : selectedRows) {
			lines.add(view.getLine(idx));
		}
		
		return lines;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.display.AbstractFileDisplay#getTreeLine()
	 */
	@Override
	public AbstractLine getTreeLine() {
		return null;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.display.AbstractFileDisplay#getLayoutIndex()
	 */
	@Override
	public int getLayoutIndex() {
		return layoutIdx;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.display.AbstractFileDisplay#setLayoutIndex(int)
	 */
	@Override
	public void setLayoutIndex(int recordIndex) {
		layoutIdx = recordIndex;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.display.AbstractFileDisplay#getFileView()
	 */
	@Override
	public FileView getFileView() {
		return view;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.display.AbstractFileDisplay#stopCellEditing()
	 */
	@Override
	public void stopCellEditing() {

	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.display.AbstractFileDisplay#getJTable()
	 */
	@Override
	public JTable getJTable() {
		return null;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.display.AbstractFileDisplay#setNewLayout(net.sf.JRecord.Details.AbstractLayoutDetails)
	 */
	@Override
	public void setNewLayout(AbstractLayoutDetails newLayout) {

	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.display.AbstractFileDisplay#getParentFrame()
	 */
	@Override
	public IDisplayFrame<? extends AbstractFileDisplay> getParentFrame() {
		return null;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.display.AbstractFileDisplay#getActualPnl()
	 */
	@Override
	public BaseHelpPanel getActualPnl() {
		return null;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.display.AbstractFileDisplay#setDockingPopup(java.awt.event.MouseListener)
	 */
	@Override
	public void setDockingPopup(MouseListener dockingPopup) {
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.display.AbstractFileDisplay#insertLine(int)
	 */
	@Override
	public void insertLine(int adj) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.display.AbstractFileDisplay#setCurrRow(int, int, int)
	 */
	@Override
	public void setCurrRow(int newRow, int layoutId, int fieldNum) {
		currentRow = newRow;
		layoutIdx = layoutId;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.display.AbstractFileDisplay#getExecuteTasks()
	 */
	@Override
	public IExecuteSaveAction getExecuteTasks() {
		return null;
	}

}
