package net.sf.RecordEditor.edit.display;

import java.util.List;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import net.sf.RecordEditor.edit.display.util.OptionPnl;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.MenuPopupListener;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ILinkedReActionHandler;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.FixedColumnScrollPane;


/**
 * LineList screen for embedding in other screens
 * and editing RecordEditor objects.
 *  
 * @author Bruce Martin
 *
 */
public class LineListReItems extends BaseLineDisplay {

	private static final int NUMBER_OF_CONTROL_COLUMNS = 2;

    private HeaderRender headerRender = new HeaderRender();

    private FixedColumnScrollPane tblScrollPane = null;
	
	private ReActionHandler actionHandler = super.getActionHandler();
	
	public final List<JButton> buttons;

	public LineListReItems(
			BaseHelpPanel panel, FileView viewOfFile, boolean primary, 
			ILinkedReActionHandler handler, List<Action> actions) {
		super(panel, "Table:", viewOfFile, primary, false, false, false, NO_LAYOUT_LINE);

		if (handler != null) {
			handler.setParentActionHandler(getActionHandler());
			actionHandler = handler;
		}

		OptionPnl optionPnl = new OptionPnl(OptionPnl.NO_FILTER, getActionHandler(), actions);
		buttons = optionPnl.getButtons();
				
		init_100_Setup(viewOfFile);
		init_200_LayoutScreen(actions, optionPnl);
		init_300_ListnersEtc(viewOfFile);
	}
	
	
	private void init_100_Setup(FileView viewOfFile) {
		TblCellAdapter tableCellAdapter = new TblCellAdapter(this, 2, 2);
		JTable tableDetails = new LinesTbl(viewOfFile, tableCellAdapter);
 
        super.setJTable(tableDetails);
        tblScrollPane = new FixedColumnScrollPane(tableDetails, new LinesTbl(viewOfFile, tableCellAdapter));

        initToolTips(2);
	}
	
	private void init_200_LayoutScreen(List<Action> actions, OptionPnl optionPnl) {
		BaseHelpPanel actualPnl = getActualPnl();
		
    	actualPnl.addReKeyListener(new DelKeyWatcher());
        actualPnl.setHelpURLre(Common.formatHelpURL(Common.HELP_RECORD_TABLE));

		actualPnl.addComponentRE(
				1, 5, BasePanel.PREFERRED, BasePanel.GAP, BasePanel.CENTER, BasePanel.CENTER, 
				optionPnl);
        actualPnl.addComponentRE(1, 5, BasePanel.FILL, BasePanel.GAP,
                         BasePanel.FULL, BasePanel.FULL,
                         tblScrollPane);

        setLayoutIndex(0);
        setupForChangeOfLayout();

        SwingUtilities.invokeLater(new Runnable() {			
 			@Override public void run() {
 				Common.calcColumnWidths(tblDetails, 2);
 			}
 		});

	}

	
	private void init_300_ListnersEtc(FileView viewOfFile) {
		
		MenuPopupListener mainPopup = new MenuPopupListener(null, true, super.tblDetails, null, true);
		BaseHelpPanel actualPnl = getActualPnl();

		super.setAlternativeTbl(tblScrollPane.getFixedTable());
		
        viewOfFile.setFrame(ReMainFrame.getMasterFrame());
        
        actualPnl.registerComponentRE(super.tblDetails);
        actualPnl.registerComponentRE(tblScrollPane.getFixedTable());

        super.tblDetails.addMouseListener(mainPopup);
        
        tblScrollPane.getFixedTable().addMouseListener(new MenuPopupListener(null, true, null));

        defColumns(super.tblDetails, fileView, tblScrollPane);
	}

    private void defColumns(
    		JTable tbl,
    		FileView view,
    		FixedColumnScrollPane scrollPane) {
    	
       if (view == null) return;

       defineColumns(tbl, view.getColumnCount(), 2, 0);

       if (scrollPane != null) {
    	   scrollPane.setupLineFields(view.getRowCount(), NUMBER_OF_CONTROL_COLUMNS, headerRender, true);
       }
}

	@Override
	public void fireLayoutIndexChanged() {
	}

	@Override
	public int getCurrRow() {
       if (tblDetails.getSelectedRowCount() > 0) {
            return tblDetails.getSelectedRow();
        }
        return -1;
	}

	@Override
	public void setCurrRow(int newRow, int layoutId, int fieldNum) {
        if ((newRow >= 0) && newRow <= fileView.getRowCount()) {
            if ((getCurrRow() != newRow)) {
                fileView.fireTableDataChanged();
                tblDetails.changeSelection(newRow, 1, false, false);
            }

            if (fieldNum >= 0 && isCurrLayoutIdx(layoutId))  {
                stopCellEditing();
                tblDetails.editCellAt(newRow, fieldNum);
                //System.out.println("Found " + newRow + " " + fieldNum);
            }
        }
	}

	@Override
	protected BaseDisplay getNewDisplay(FileView view) {
		throw new RuntimeException("Internal Error: New display is not supported");
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#getActionHandler()
	 */
	@Override
	public ReActionHandler getActionHandler() {
		return actionHandler;
	}

}
