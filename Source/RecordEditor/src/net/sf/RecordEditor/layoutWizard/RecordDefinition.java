package net.sf.RecordEditor.layoutWizard;

import java.util.ArrayList;

public class RecordDefinition {

	protected boolean searchForFields = true;
	protected boolean displayedFieldSelection = false;
	protected boolean displayedFieldNames = false;
	protected Object keyValue;
	protected String name = "";
	protected     int numRecords = 0;
	protected byte[][] records = new byte[60][];
	protected ArrayList<ColumnDetails> columnDtls = new ArrayList<ColumnDetails>();
	
	public final void addKeyField(Details detail, boolean addRest) {
		ColumnDetails colDtls = new ColumnDetails(detail.keyStart, detail.keyType.intValue());
		colDtls.name = detail.keyName; 
		colDtls.length = detail.keyLength;
		
		columnDtls.add(colDtls);
		
		if (addRest) {
			columnDtls.add(
					new ColumnDetails(
							detail.keyStart + detail.keyLength, 
							detail.defaultType.intValue()));
		}
	}
}
