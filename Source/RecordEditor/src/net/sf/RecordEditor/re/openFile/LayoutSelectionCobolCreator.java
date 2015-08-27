package net.sf.RecordEditor.re.openFile;

public class LayoutSelectionCobolCreator implements
		AbstractLayoutSelectCreator<LayoutSelectionCobolAlt> {

	/**
	 * @see net.sf.RecordEditor.re.openFile.AbstractLayoutSelectCreator#create()
	 */
	@Override
	public LayoutSelectionCobolAlt create() {
		LayoutSelectionCobolAlt ret = new LayoutSelectionCobolAlt();

		ret.setLoadFromFile(true);
		return ret;
	}
}
