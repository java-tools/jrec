package net.sf.RecordEditor.utils.openFile;

public class LayoutSelectionFileCreator implements
		AbstractLayoutSelectCreator<LayoutSelectionFile> {

	/**
	 * @see net.sf.RecordEditor.utils.openFile.AbstractLayoutSelectCreator#create()
	 */
	@Override
	public LayoutSelectionFile create() {
		LayoutSelectionFile ret = new LayoutSelectionFile();
		
		ret.setLoadFromFile(true);
		return ret;
	}
}
