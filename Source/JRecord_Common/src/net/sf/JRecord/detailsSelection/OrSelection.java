package net.sf.JRecord.detailsSelection;

import net.sf.JRecord.Common.AbstractIndexedLine;
import net.sf.JRecord.ExternalRecordSelection.ExternalGroupSelection;

public class OrSelection extends AbsGroup {

	public OrSelection() {
		super(10);
		setType(TYPE_OR);
	}

	public OrSelection(@SuppressWarnings("rawtypes") ExternalGroupSelection sel) {
		super(sel.size());
		setType(TYPE_OR);
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.Selection.RecordSelection#isSelected(net.sf.JRecord.Details.AbstractLine)
	 */
	@Override
	public boolean isSelected(AbstractIndexedLine line) {

		if (size() > 0) {
			RecordSel sel;
			
			//System.out.println();
			//System.out.print("Or --> ");
			for (int i = 0; i < size(); i++) {
				sel = get(i);

				if (sel.isSelected(line)) {
					//System.out.println();
					return true;
				}
			}
			//System.out.println();
		} else {
			return true;
		}
		return false;
	}
	
	
	 
}
