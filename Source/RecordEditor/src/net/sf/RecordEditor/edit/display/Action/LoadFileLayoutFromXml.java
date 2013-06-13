package net.sf.RecordEditor.edit.display.Action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.RecordEditorXmlLoader;
import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.RecordEditor.edit.display.util.Code;
import net.sf.RecordEditor.re.display.AbstractFileDisplay;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction;
import net.sf.RecordEditor.utils.swing.DirectoryFrame;

@SuppressWarnings("serial")
public class LoadFileLayoutFromXml extends ReSpecificScreenAction implements AbstractActiveScreenAction {

	private static final String MSG = "Load File Description from Xml";
	/**
	 * @param creator
	 */
	public LoadFileLayoutFromXml() {
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
			new LoadLayout(
					fileDisplay,
					dir + s);
		}
	}

	private boolean isActive(AbstractFileDisplay activeScreen) {
		return activeScreen != null && activeScreen.getFileView().isSimpleCsvFile();
	}

	private static String removeExtension(String s) {
		if (s.indexOf('.') >= 0) {
			int l = s.lastIndexOf('.');
			s = s.substring(0, l);
		}
		return s;
	}

	public static class LoadLayout
	extends DirectoryFrame implements ActionListener {

		private AbstractFileDisplay panel;
		public LoadLayout(AbstractFileDisplay pnl, String dir) {
			super(MSG,  dir, false, false, false);

			panel = pnl;
			setActionListner(this);
			this.setVisible(true);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {

			FileView masterView = panel.getFileView().getBaseFile();
			//CopybookLoaderFactory readers = CopybookLoaderFactory.getInstance();
			//String fname = super.getFileName();
			//String lname = Common.stripDirectory(fname);

//			if (lname == null || "".equals(lname)) {
//				lname = masterView.getLayout().getLayoutName();
//			} else {
//				lname = removeExtension(lname);
//			}

			try {

				ExternalRecord rec = new RecordEditorXmlLoader()
						.loadCopyBook(super.getFileName(), 0, 0, "",
									  0, 0, Common.getLogger());
				LayoutDetail l = rec.asLayoutDetail();


				if (l == null || l.getRecordCount() < 1 || l.getRecord(0).getFieldCount() < 1) {
					Common.logMsg("Error in the layout that was loaded", null);
				} else {
					Code.notifyFramesOfNewLayout(masterView, l);
				}

				panel.getParentFrame().setToActiveFrame();
				panel.getParentFrame().setToActiveTab(panel);
				this.setVisible(false);
			} catch (Exception ex) {
				ex.printStackTrace();
				Common.logMsg(AbsSSLogger.ERROR, "Can not Load Xml Layout:", ex.getMessage(), ex);
			}
		}
	}
}
