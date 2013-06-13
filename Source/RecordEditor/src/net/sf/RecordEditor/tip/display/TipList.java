package net.sf.RecordEditor.tip.display;

import java.awt.event.ActionEvent;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

import net.sf.RecordEditor.edit.display.AbstractCreateChildScreen;
import net.sf.RecordEditor.edit.display.BaseDisplay;
import net.sf.RecordEditor.edit.display.common.AbstractRowChangedListner;
import net.sf.RecordEditor.edit.display.extension.EditPaneListScreen;
import net.sf.RecordEditor.re.display.AbstractFileDisplay;
import net.sf.RecordEditor.re.display.AbstractFileDisplayWithFieldHide;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.MenuPopupListener;
import net.sf.RecordEditor.utils.lang.ReAbstractAction;
import net.sf.RecordEditor.utils.swing.SwingUtils;

import org.jdesktop.swingx.JXTipOfTheDay;

public class TipList extends EditPaneListScreen
implements AbstractRowChangedListner, TableModelListener, AbstractFileDisplayWithFieldHide, AbstractCreateChildScreen {


	private static final int TIP_COLUMN_WIDTH = SwingUtils.STANDARD_FONT_WIDTH * 45;

	public TipList(FileView viewOfFile, boolean primary) {
		super("Tip List", viewOfFile, primary, false, false, false, false, NO_LAYOUT_LINE);


		addTipAction(mainPopup);
		addTipAction(fixedPopupMenu);
	}

	private void addTipAction(MenuPopupListener popup) {

		popup.getPopup().addSeparator();
		popup.getPopup().add(new TipViewer(popup, fileView));
	}

	@Override
	public void setScreenSize(boolean mainframe) {
		super.setScreenSize(mainframe);

		TableColumn tc = getJTable().getColumnModel().getColumn(1);
		if (tc.getPreferredWidth() > TIP_COLUMN_WIDTH) {
			tc.setPreferredWidth(TIP_COLUMN_WIDTH);
		}
	}


    /* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.AbstractRowChangedImplementor#createChildScreen()
	 */
	@Override
	public AbstractFileDisplay createChildScreen(int position) {
		if (childScreen != null) {
			removeChildScreen();
		}

		currChildScreenPosition = position;
		if (position == CS_RIGHT) {
			childScreen = new TipChildRecordScreen(fileView, Math.max(0, getCurrRow()), CS_RIGHT);
		} else {
			childScreen = new TipChildRecordScreen(fileView, Math.max(0, getCurrRow()), CS_BOTTOM);
		}
		setKeylistner(tblDetails);
		setKeylistner(getAlternativeTbl());

		return childScreen;
	}


	@Override
	protected BaseDisplay getNewDisplay(FileView view) {
		return new TipList(view, false);
	}

	public int getAvailableChildScreenPostion() {
		return CS_BOTH;
	}


	@Override
	public void stopCellEditing() {
		super.stopCellEditing();
		//splitPane.updateFile();
	}

	/**
	 * Show the Tips in a tip dialog
	 *
	 * @author Bruce Martin
	 *
	 */
	@SuppressWarnings("serial")
	private static class TipViewer extends ReAbstractAction {
		private MenuPopupListener popup;

		private FileView fileView;


		public TipViewer(MenuPopupListener popupMenu, FileView fileView) {
			super("Show in Hints Dialog");
			this.popup = popupMenu;
			this.fileView  = fileView;
		}


		@Override
		public void actionPerformed(ActionEvent e) {
			JXTipOfTheDay tipOfTheDay = new JXTipOfTheDay(new TipModel(fileView));
			int row = popup.getPopupRow();
			for (int i = 0; i < row; i++) {
				tipOfTheDay.nextTip();
			}

			tipOfTheDay.showDialog(null); //setVisible(true);
		}

	}
}
