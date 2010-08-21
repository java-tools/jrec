package net.sf.RecordEditor.utils.screenManager;

import javax.swing.Action;

public interface AbstractActiveScreenAction extends Action {

//	/**
//	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
//	 */
//	public abstract void actionPerformed(ActionEvent e);

	/**
	 * Check if action is enabled
	 */
	public abstract void checkActionEnabled();

}