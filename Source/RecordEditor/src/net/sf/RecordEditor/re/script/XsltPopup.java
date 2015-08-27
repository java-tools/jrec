package net.sf.RecordEditor.re.script;


import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;

@SuppressWarnings("serial")
public class XsltPopup extends FilePopup  implements Runnable {

	private static FileItem[] fileList = null;
	
	public XsltPopup() {
		super("Export via Xsl Transform");
		this.setIcon(Common.getReActionIcon(ReActionHandler.EXPORT_XSLT));
		
		javax.swing.SwingUtilities.invokeLater(this);
	}


	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			fileList = getActions(
							fileList, 
							Common.OPTIONS.DEFAULT_XSLT_DIRECTORY.get(), 
							ReActionHandler.EXPORT_XSLT,
							"(Transform)",
							null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static final XsltPopup getPopup() {
		XsltPopup ret = null;
		if (Common.OPTIONS.xsltAvailable.isSelected()) {
			ret = new XsltPopup();
		}
		return ret;
	}
}
