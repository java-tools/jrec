package net.sf.RecordEditor.re.openFile;

public class LayoutSelectionCobolCreator implements
		AbstractLayoutSelectCreator<LayoutSelectionCobol> {

	/**
	 * @see net.sf.RecordEditor.re.openFile.AbstractLayoutSelectCreator#create()
	 */
	@Override
	public LayoutSelectionCobol create() {
		LayoutSelectionCobol ret = new LayoutSelectionCobol();

		ret.setLoadFromFile(true);
		return ret;
	}
}
