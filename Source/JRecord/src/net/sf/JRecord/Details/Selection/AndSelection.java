package net.sf.JRecord.Details.Selection;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.ExternalRecordSelection.GroupSelection;

public class AndSelection extends AbsGroup {

	public AndSelection(GroupSelection sel) {
		super(sel.size());
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.Selection.RecordSelection#isSelected(net.sf.JRecord.Details.AbstractLine)
	 */
	@Override
	public boolean isSelected(AbstractLine line) {

		if (size() > 0) {
			RecordSel sel;
			for (int i = 0; i < size(); i++) {
				sel = get(i);
				
				if (! sel.isSelected(line)) {
					return false;
				}
			}
		}
		return true;
	}
	
	
	 
}
