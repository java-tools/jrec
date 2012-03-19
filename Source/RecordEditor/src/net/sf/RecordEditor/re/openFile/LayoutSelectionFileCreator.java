package net.sf.RecordEditor.re.openFile;

public class LayoutSelectionFileCreator implements
		AbstractLayoutSelectCreator<LayoutSelectionFile> {

	/**
	 * @see net.sf.RecordEditor.re.openFile.AbstractLayoutSelectCreator#create()
	 */
	@Override
	public LayoutSelectionFile create() {
		LayoutSelectionFile ret = new LayoutSelectionFile();
		
		ret.setLoadFromFile(true);
		return ret;
	}
}
