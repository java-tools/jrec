package net.sf.RecordEditor.testcode;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractRecordDetail;

public class TstLayoutPrint {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AbstractLayoutDetails l = null;

		for (int i = 0; i < l.getRecordCount(); i++) {
			AbstractRecordDetail record = l.getRecord(i);
			for (int j=0; j < record.getFieldCount(); j++) {
				System.out.print("\t" + record.getField(j).getName());
			}
		}
	}

}
