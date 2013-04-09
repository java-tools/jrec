package net.sf.RecordEditor.edit.display.Action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.sf.JRecord.External.CopybookWriterManager;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.ToExternalRecord;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.script.AbstractFileDisplay;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.DirectoryFrame;

@SuppressWarnings("serial")
public class SaveFileLayout2Xml extends ReSpecificScreenAction implements AbstractActiveScreenAction {

	private static final String MSG = "Save File Description as Xml";
	/**
	 * @param creator
	 */
	public SaveFileLayout2Xml() {
		super(MSG);

		checkActionEnabled();
	}

	/**
	 * @see net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction#checkActionEnabled()
	 */
	@Override
	public void checkActionEnabled() {
		super.setEnabled(isActive(getDisplay(AbstractFileDisplay.class)));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		AbstractFileDisplay fileDisplay = getDisplay(AbstractFileDisplay.class);
		if (isActive(fileDisplay)) {
			String dir = Parameters.getFileName(Parameters.COPYBOOK_DIRECTORY);
			String s = "";
			String fn = fileDisplay.getFileView().getBaseFile().getFileNameNoDirectory();

			if (fn != null && ! "".equals(fn)) {
				s = removeExtension(fn) + ".Xml";
			}
			new SaveLayout(
					fileDisplay,
					dir + s);
		}
	}

	private boolean isActive(AbstractFileDisplay activeScreen) {
		boolean active = false;

		if (activeScreen != null) {
			AbstractFileDisplay source = (AbstractFileDisplay) activeScreen;
			active =  source.getFileView().isSimpleCsvFile();
		}

		return active;
	}

	private static String removeExtension(String s) {
		if (s.indexOf('.') >= 0) {
			int l = s.lastIndexOf('.');
			s = s.substring(0, l);
		}
		return s;
	}

	public static class SaveLayout
	extends DirectoryFrame implements ActionListener {

		private AbstractFileDisplay panel;
		public SaveLayout(AbstractFileDisplay pnl, String dir) {
			super(MSG,  dir, false, false, true);

			panel = pnl;
			setActionListner(this);
			this.setVisible(true);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {

			FileView view = panel.getFileView().getBaseFile();
			CopybookWriterManager writers = CopybookWriterManager.getInstance();
			String fname = super.getFileName();
			String lname = Common.stripDirectory(fname);

			if (lname == null || "".equals(lname)) {
				lname = view.getLayout().getLayoutName();
			} else {
				lname = removeExtension(lname);
			}

			try {
				ExternalRecord rec = ToExternalRecord.getInstance()
					.getExternalRecord(view.getLayout(), lname, 0);
				//rec.setDelimiter(loaders.getFieldDelim(loaderId));
				writers.get(CopybookWriterManager.RECORD_EDITOR_XML_WRITER)
					.writeCopyBook(
							super.getFile().getParent(),
							rec,
							Common.getLogger());
				this.setVisible(false);
				ReFrame.setActiveFrame((ReFrame) panel);
			} catch (Exception ex) {
				Common.logMsg("Can not save Field Sequences", ex);
			}
		}
	}
}
