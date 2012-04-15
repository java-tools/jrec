package net.sf.RecordEditor.utils;


import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;

@SuppressWarnings("serial")
public class XsltPopup extends FilePopup {

	private static FileItem[] fileList = null;
	
	public XsltPopup() {
		super("Export via Xsl Transform");
		this.setIcon(Common.getReActionIcon(ReActionHandler.EXPORT_XSLT));
		
		fileList = getActions(
						fileList, 
						Common.OPTIONS.DEFAULT_XSLT_DIRECTORY.get(), 
						ReActionHandler.EXPORT_XSLT,
						"(Transform)");
	}
	
	public static final XsltPopup getPopup() {
		XsltPopup ret = null;
		if (Common.OPTIONS.xsltAvailable.isSelected()) {
			ret = new XsltPopup();
		}
		return ret;
	}
}
