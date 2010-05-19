package net.sf.RecordEditor.jibx.compare;

public class File {
	public String name = "";
	public Layout layoutDetails = null;
	
	/**
	 * @return the layoutDetails
	 */
	public Layout getLayoutDetails() {
		if (layoutDetails == null) {
			layoutDetails = new Layout();
		}
		return layoutDetails;
	}
}
