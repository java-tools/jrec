package net.sf.JRecord.Details.Selection;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.ExternalRecordSelection.GroupSelection;

public class OrSelection extends AbsGroup {

	public OrSelection(GroupSelection sel) {
		super(sel.size());
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.Selection.RecordSelection#isSelected(net.sf.JRecord.Details.AbstractLine)
	 */
	@Override
	public boolean isSelected(AbstractLine line) {

		if (size() > 0) {
			RecordSel sel;
			
			System.out.println();
			System.out.print("Or --> ");
			for (int i = 0; i < size(); i++) {
				sel = get(i);

				if (sel.isSelected(line)) {
					System.out.println();
					return true;
				}
			}
			System.out.println();
		} else {
			return true;
		}
		return false;
	}
	
	
	 
}
