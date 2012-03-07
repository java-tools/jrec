package net.sf.JRecord.Details.Selection;

import java.util.List;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.ExternalRecordSelection.FieldSelection;

public abstract class FieldSelect extends FieldSelection implements RecordSel {

	protected final FieldDetail field;

//	public FieldSelect(FieldSelection fs, FieldDetail fieldDef) {
//		field = fieldDef;
//		set(fs);
//	}
	
	
	
	
	protected FieldSelect(String name, String value, FieldDetail fieldDef) {
		super(name, value);
		field = fieldDef;
	}
	
	



	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.Selection.RecordSel#getAllFields(java.util.List)
	 */
	@Override
	public void getAllFields(List<FieldSelect> fields) {
		fields.add(this);
	}




	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.Selection.RecordSel#getFirstField()
	 */
	@Override
	public FieldSelect getFirstField() {
		return this;
	}



	public static class EqualsSelect extends FieldSelect {		

		protected EqualsSelect(String name, String value, FieldDetail fieldDef) {
			super(name, value, fieldDef);
		}



		/* (non-Javadoc)
		 * @see net.sf.JRecord.Details.Selection.RecordSelection#isSelected(net.sf.JRecord.Details.AbstractLine)
		 */
		@Override
		public boolean isSelected(AbstractLine line) {
			
			Object o = line.getField(field);
			return  o != null 
			   && (o.toString().equals(getFieldValue()));
		}
	}


	public static class NotEqualsSelect extends FieldSelect {

		protected NotEqualsSelect(String name, String value, FieldDetail fieldDef) {
			super(name, value, fieldDef);
		}



		/* (non-Javadoc)
		 * @see net.sf.JRecord.Details.Selection.RecordSelection#isSelected(net.sf.JRecord.Details.AbstractLine)
		 */
		@Override
		public boolean isSelected(AbstractLine line) {
			
			Object o = line.getField(field);
			return  o != null 
			   && (! o.toString().equals(getFieldValue()));
		}
	}
}
