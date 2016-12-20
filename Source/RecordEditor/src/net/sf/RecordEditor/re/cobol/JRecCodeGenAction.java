package net.sf.RecordEditor.re.cobol;

import java.awt.event.ActionEvent;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.ReAbstractAction;

@SuppressWarnings("serial")
public class JRecCodeGenAction extends ReAbstractAction{

	public JRecCodeGenAction() {
		super("Java~JRecord code for Cobol", Common.getRecordIcon(Common.ID_WIZARD_ICON));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		new net.sf.RecordEditor.re.cobol.CodeGenMaster();
	}

}
