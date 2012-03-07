package net.sf.JRecord.Details.Selection;

import java.util.List;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.ExternalRecordSelection.ExternalSelection;

public interface RecordSel extends ExternalSelection {

	public boolean isSelected(AbstractLine line);
	
//	public int getSize();
	
	public FieldSelect getFirstField();
	
	public void getAllFields(List<FieldSelect> fields);
	
	public int getSize();

}
