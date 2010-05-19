package net.sf.RecordEditor.jibx.compare;

public class BaseCopyDif {
	
	public String type;

	public String complete = "NO";
	public Layout layoutDetails = null;
	public File oldFile = new File();
	public File newFile = new File();
	
	public String saveFile = "";
	
	public boolean stripTrailingSpaces = true;
	
	
	public boolean fileSaved = false;


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
