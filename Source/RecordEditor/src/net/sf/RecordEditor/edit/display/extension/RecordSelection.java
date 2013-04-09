package net.sf.RecordEditor.edit.display.extension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.edit.display.BaseDisplay;
import net.sf.RecordEditor.edit.display.DisplayFrame;
import net.sf.RecordEditor.edit.display.common.AbstractFileDisplayWithFieldHide;
import net.sf.RecordEditor.edit.display.util.LinePosition;
import net.sf.RecordEditor.edit.display.util.MovementBtnPnl;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.swing.BasePanel;

public abstract class RecordSelection extends BaseDisplay
implements AbstractFileDisplayWithFieldHide {


	private MovementBtnPnl movementPnl;

	protected SplitPaneRecord splitPane;



	public RecordSelection(
			String formType, FileView viewOfFile, int lineNo) {
		super(formType, viewOfFile, false, false, false, false, false, NO_LAYOUT_LINE);

		splitPane = new SplitPaneRecord(viewOfFile, lineNo);

		setJTable(new JTable());
	}


	protected final void init_200_layoutScreen() {

		movementPnl = new MovementBtnPnl(Common.getArrowIcons(), true, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				btnPressed(event);
			}
		});

		splitPane.layoutFieldPane();

		actualPnl.addComponent(1, 3, BasePanel.FILL, BasePanel.GAP,
	            BasePanel.FULL, BasePanel.FULL, splitPane.splitPane);

		actualPnl.addComponent(1, 5, BasePanel.PREFERRED, BasePanel.GAP,
				BasePanel.FULL, BasePanel.FULL, movementPnl);
		actualPnl.done();
		setDirectionButtonStatus();
	}


	public void setScreenSize(boolean mainframe, int width, int height) {

		if (mainframe) {
			DisplayFrame parentFrame = getParentFrame();

			parentFrame.bldScreen();
			parentFrame.setBounds(parentFrame.getY(), parentFrame.getX(), width, height);
			parentFrame.show();
			parentFrame.setToMaximum(false);
			parentFrame.addCloseOnEsc(actualPnl);
		} else {
			this.actualPnl.done();
		}
	}

	private void btnPressed(ActionEvent event) {

		if (event.getSource() == movementPnl.buttons[0]) {
			splitPane.setCurrRow(0);
		} else if (event.getSource() == movementPnl.buttons[1] && splitPane.getCurrRow() > 0) {
			splitPane.setCurrRow(splitPane.getCurrRow() -1);
		} else if (event.getSource() == movementPnl.buttons[2]) {
			splitPane.setCurrRow(splitPane.getCurrRow() +1);
		} else if (event.getSource() == movementPnl.buttons[3]) {
			splitPane.setCurrRow(getFileView().getRowCount() - 1);
		}

		setDirectionButtonStatus();
	}


	/**
	 * Set enabled / disabled status of the direction buttons
	 */
	protected void setDirectionButtonStatus() {

		boolean allowBack = splitPane.getCurrRow() > 0;
		boolean allowForward = fileView != null && splitPane.getCurrRow() < fileView.getRowCount() - 1;

		movementPnl.buttons[0].setEnabled(allowBack);
		movementPnl.buttons[1].setEnabled(allowBack);
		movementPnl.buttons[2].setEnabled(allowForward);
		movementPnl.buttons[3].setEnabled(allowForward);
	}

	@Override
	protected int getInsertAfterPosition() {
		return getStandardPosition();
	}

	/**
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#getInsertAfterLine()
	 */
	@Override
	protected LinePosition getInsertAfterLine(boolean prev) {
		return super.getInsertAfterLine(splitPane.getCurrRow(), prev);
	}

	@Override
	public void fireLayoutIndexChanged() {

	}

	/**
	 * @return get the selected rows
	 */
	public final int[] getSelectedRows() {
		return new int[] {getCurrRow()};
	}

	@Override
	public int getCurrRow() {
		return splitPane.getCurrRow();
	}

	public void setCurrRow(int newRow) {
		splitPane.setCurrRow(newRow);
	}

	@Override
	public void setCurrRow(int newRow, int layoutId, int fieldNum) {
		setCurrRow(newRow);
	}



	@Override
	public boolean[] getFieldVisibility(int recordIndex) {
		return splitPane.getFieldVisibility(recordIndex);
	}

	@Override
	public void setFieldVisibility(int recordIndex, boolean[] fieldVisibility) {
		splitPane.setFieldVisibility(recordIndex, fieldVisibility);
	}


	@Override
	public void stopCellEditing() {
		splitPane.flush();
	}

    /**
     * @see net.sf.RecordEditor.utils.common.ReActionHandler#executeAction(int)
     */
    public void executeAction(int action) {

        if (action == ReActionHandler.REPEAT_RECORD_POPUP) {
        	getFileView().repeatLine(splitPane.getCurrRow());
        	splitPane.setCurrRow(splitPane.getCurrRow() + 1);
        } else {
            super.executeAction(action);
        }
    }

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#isActionAvailable(int)
	 */
	@Override
	public boolean isActionAvailable(int action) {
       return (action == ReActionHandler.REPEAT_RECORD_POPUP) || super.isActionAvailable(action);
	}

    public void insertLine(int adj) {
    	splitPane.setCurrRow(fileView.newLine(getInsertAfterPosition(), adj));
    }
}