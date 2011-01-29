package net.sf.RecordEditor.edit.display.Action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;

import net.sf.JRecord.External.CopybookLoaderFactory;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.RecordEditor.edit.display.common.AbstractFileDisplay;
import net.sf.RecordEditor.edit.display.util.Code;
import net.sf.RecordEditor.edit.file.FileView;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.Parameters;
import net.sf.RecordEditor.utils.filter.DirectoryFrame;
import net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction;
import net.sf.RecordEditor.utils.screenManager.ReFrame;

@SuppressWarnings("serial")
public class LoadFileLayoutFromXml extends AbstractAction implements AbstractActiveScreenAction {

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
		super.setEnabled(isActive(ReFrame.getActiveFrame()));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		ReFrame actionHandler = ReFrame.getActiveFrame();
		if (isActive(actionHandler)) {
			AbstractFileDisplay fileDisplay = (AbstractFileDisplay) actionHandler;
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
	
	private boolean isActive(ReFrame activeScreen) {
		boolean active = false;
		
		if (activeScreen instanceof AbstractFileDisplay) {
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
	
			FileView<?> masterView = panel.getFileView().getBaseFile();
			CopybookLoaderFactory readers = CopybookLoaderFactory.getInstance();
			String fname = super.getFileName();
			String lname = Common.stripDirectory(fname);

			if (lname == null || "".equals(lname)) {
				lname = masterView.getLayout().getLayoutName();
			} else {
				lname = removeExtension(lname);
			}
							
			try {
			
				ExternalRecord rec = readers.getLoader(CopybookLoaderFactory.RECORD_EDITOR_XML_LOADER)
						.loadCopyBook(super.getFileName(), 0, 0, "",
									  0, 0, Common.getLogger());
				
				this.setVisible(false);
				
				Code.notifyFramesOfNewLayout(masterView, rec.asLayoutDetail());
				ReFrame.setActiveFrame((ReFrame) panel);
			} catch (Exception ex) {
				Common.logMsg("Can not save Field Sequences", ex);
			}			
		}
	}
}
