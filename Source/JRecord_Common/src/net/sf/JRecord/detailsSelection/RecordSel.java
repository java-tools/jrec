package net.sf.JRecord.detailsSelection;

import java.util.List;

import net.sf.JRecord.Common.AbstractIndexedLine;
import net.sf.JRecord.ExternalRecordSelection.ExternalSelection;

public interface RecordSel extends ExternalSelection {

	public boolean isSelected(AbstractIndexedLine line);
	
//	public int getSize();
	
	public FieldSelect getFirstField();
	
	public void getAllFields(List<FieldSelect> fields);
	
	public int getSize();
}