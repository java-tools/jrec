package net.sf.RecordEditor.re.openFile;

import net.sf.RecordEditor.utils.CopyBookInterface;

public class LayoutSelectionDBCreator 
implements AbstractLayoutSelectCreator<LayoutSelectionDB> {
	private CopyBookInterface cpyInterface;
	
	public LayoutSelectionDBCreator(CopyBookInterface copyBookInterface) {
		cpyInterface = copyBookInterface;
	}
	
	
	/**
	 * @see net.sf.RecordEditor.re.openFile.AbstractLayoutSelectCreator#create()
	 */
	@Override
	public LayoutSelectionDB create() {
		LayoutSelectionDB ret = new LayoutSelectionDB(cpyInterface, true);
		
		ret.setLoadFromFile(true);
		
		return ret;
	}
}
