package net.sf.RecordEditor.utils.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import net.sf.RecordEditor.utils.common.AbstractSaveDetails;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;


@SuppressWarnings("serial")
public class SaveButton<what> extends JButton implements ActionListener {


	private AbstractSaveDetails<what> saveCallBack;
	private String dir;

	private DirectoryFrame saveFrame= null;


	public SaveButton(AbstractSaveDetails<what> save, String directory) {
		super(LangConversion.convert(LangConversion.ST_BUTTON, "Save"), Common.getRecordIcon(Common.ID_SAVE_ICON));

		saveCallBack = save;
		dir = directory;

		this.addActionListener(this);
	}


	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this) {
			ap_InitFrame();
		} else {
			what saveDetails = saveCallBack.getSaveDetails();
			try {
				(new net.sf.RecordEditor.jibx.JibxCall<what>(saveDetails.getClass()))
						.unmarshal(saveFrame.getFileName(), saveDetails);
				saveFrame.setVisible(false);
				saveFrame = null;
			} catch (Exception ex) {
				ex.printStackTrace();
				Common.logMsg("jibx- Save to Xml failed:", ex);
			}
		}
	}

	private void ap_InitFrame() {
		if (saveFrame == null) {
			saveFrame = new DirectoryFrame("Save to Xml",  dir, false, false, true);

			saveFrame.setActionListner(this);
		}

		saveFrame.setVisible(true);
	}
}
