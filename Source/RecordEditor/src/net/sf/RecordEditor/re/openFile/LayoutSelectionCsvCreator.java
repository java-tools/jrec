package net.sf.RecordEditor.re.openFile;

public class LayoutSelectionCsvCreator implements
		AbstractLayoutSelectCreator<LayoutSelectionCsv> {

	/**
	 * @see net.sf.RecordEditor.re.openFile.AbstractLayoutSelectCreator#create()
	 */
	@Override
	public LayoutSelectionCsv create() {
		LayoutSelectionCsv ret = new LayoutSelectionCsv();
		
		ret.setLoadFromFile(true);
		return ret;
	}
}
