package net.sf.RecordEditor.utils;


import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;

@SuppressWarnings("serial")
public class XsltPopup extends FilePopup {

	private static FileItem[] fileList = null;
	
	public XsltPopup() {
		super("Save via Xsl Transform");
		this.setIcon(Common.getReActionIcon(ReActionHandler.SAVE_AS_XSLT));
		
		fileList = getActions(
						fileList, 
						Common.OPTIONS.DEFAULT_XSLT_DIRECTORY.get(), 
						ReActionHandler.SAVE_AS_XSLT,
						"Transform");
	}
	
	public static final XsltPopup getPopup() {
		XsltPopup ret = null;
		if (Common.OPTIONS.XSLT_AVAILABLE.isSelected()) {
			ret = new XsltPopup();
		}
		return ret;
	}
}
