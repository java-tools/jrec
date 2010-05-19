package net.sf.RecordEditor.utils.edit;

import java.util.ArrayList;

import net.sf.JRecord.Common.AbsRow;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.RecordEditor.utils.swing.AbsRowList;
import net.sf.RecordEditor.utils.swing.ArrayRow;

public class RecordList extends AbsRowList {

	private AbstractLayoutDetails layout;
	private boolean allRecords, firstRowNull;
	
	public RecordList(AbstractLayoutDetails recordLayout, 
			boolean sort, boolean nullFirstRow, boolean loadAllRecords) {
		super(0, 1, false, false);
		layout       = recordLayout;
		allRecords   = loadAllRecords;
		firstRowNull = nullFirstRow;
	}

	
	/**
	 * Load the list layout
	 */
	public void loadData() {
		int i;
		String name;
		
		Object[] aRow;
		ArrayList<AbsRow> rows = new ArrayList<AbsRow>();
		
		if (firstRowNull) {
			aRow = new Object[]{Integer.valueOf(-1), ""};
			rows.add(new ArrayRow(aRow));
		}

		for (i = 0; i < layout.getRecordCount(); i++) {
			name = layout.getRecord(i).getRecordName();
			
			if (allRecords || ! layout.isXml() || ! name.startsWith("/")) {
				aRow = new Object[]{Integer.valueOf(i), name};
				rows.add(new ArrayRow(aRow));
			}
		}
		
		super.loadData(rows);
	}
	

}
