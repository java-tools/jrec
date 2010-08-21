package net.sf.RecordEditor.edit.display.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTable;

import net.sf.RecordEditor.edit.display.common.AbstractRowChanged;

public class RowChangeListner implements KeyListener {

	private JTable tbl;
	private AbstractRowChanged notify;
	
	
	/**
	 * @param table table being watched
	 * @param rowChangeNotify class to notify of the current row;
	 */
	public RowChangeListner(JTable table, AbstractRowChanged rowChangeNotify) {
		this.tbl = table;
		this.notify = rowChangeNotify;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		notify.checkRowChange(tbl.getSelectedRow());
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

}
