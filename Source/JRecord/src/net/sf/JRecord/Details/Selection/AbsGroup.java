package net.sf.JRecord.Details.Selection;

import java.util.List;

import net.sf.JRecord.ExternalRecordSelection.GroupSelection;

public abstract class AbsGroup extends GroupSelection<RecordSel> implements RecordSel  {

	
	public AbsGroup(int size) {
		super(size);
	}



	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.Selection.RecordSel#getFirstField()
	 */
	@Override
	public FieldSelect getFirstField() {
		FieldSelect s = null;
		
		for (int i = 0; i < getSize() && s == null; i++) {
			s = get(i).getFirstField();
		}
		return s;
	}



	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.Selection.RecordSel#getAllFields(java.util.List)
	 */
	@Override
	public void getAllFields(List<FieldSelect> fields) {
		for (int i = 0; i < getSize(); i++) {
			get(i).getAllFields(fields);
		}
	}
	
	
	
	
}
