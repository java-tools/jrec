package net.sf.JRecord.detailsSelection;

import net.sf.JRecord.Common.AbstractIndexedLine;
import net.sf.JRecord.ExternalRecordSelection.ExternalGroupSelection;

public class AndSelection extends AbsGroup {

	public AndSelection() {
		super(10);
	}

	public AndSelection(@SuppressWarnings("rawtypes") ExternalGroupSelection sel) {
		super(sel.size());
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.Selection.RecordSelection#isSelected(net.sf.JRecord.Details.AbstractLine)
	 */
	@Override
	public boolean isSelected(AbstractIndexedLine line) {

		if (size() > 0) {
			RecordSel sel;
			for (int i = 0; i < size(); i++) {
				sel = get(i);
				
				if (! sel.isSelected(line)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	
	 
}