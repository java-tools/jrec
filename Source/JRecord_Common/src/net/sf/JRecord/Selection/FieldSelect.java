package net.sf.JRecord.Details.Selection;

import java.util.List;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.ExternalRecordSelection.ExternalFieldSelection;

public abstract class FieldSelect extends ExternalFieldSelection implements RecordSel {

	protected final FieldDetail fieldDetail;

//	public FieldSelect(FieldSelection fs, FieldDetail fieldDef) {
//		field = fieldDef;
//		set(fs);
//	}
	
	
	
	
	protected FieldSelect(String name, String value, String op, FieldDetail fieldDef) {
		super(name, value, op);
		fieldDetail = fieldDef;
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
	
	



	/* (non-Javadoc)
	 * @see net.sf.JRecord.ExternalRecordSelection.ExternalSelection#getElementCount()
	 */
	@Override
	public int getElementCount() {
		return 1;
	}





	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.Selection.RecordSel#isSelected(net.sf.JRecord.Details.AbstractLine)
	 */
	@Override
	public boolean isSelected(AbstractLine line) {
		// TODO Auto-generated method stub
		return false;
	}





	public static class EqualsSelect extends FieldSelect {		

		protected EqualsSelect(String name, String value, FieldDetail fieldDef) {
			super(name, value, "=", fieldDef);
		}



		/* (non-Javadoc)
		 * @see net.sf.JRecord.Details.Selection.RecordSelection#isSelected(net.sf.JRecord.Details.AbstractLine)
		 */
		@Override
		public boolean isSelected(AbstractLine line) {
			
			Object o = line.getField(fieldDetail);
			return  o != null 
			   && (o.toString().equals(getFieldValue()));
		}
	}


	public static class NotEqualsSelect extends FieldSelect {

		protected NotEqualsSelect(String name, String value, FieldDetail fieldDef) {
			super(name, value, "!=", fieldDef);
		}



		/* (non-Javadoc)
		 * @see net.sf.JRecord.Details.Selection.RecordSelection#isSelected(net.sf.JRecord.Details.AbstractLine)
		 */
		@Override
		public boolean isSelected(AbstractLine line) {
			
			Object o = line.getField(fieldDetail);
			return  o != null 
			   && (! o.toString().equals(getFieldValue()));
		}
	}


	/**
	 * @return the fieldDetail
	 */
	protected FieldDetail getFieldDetail() {
		return fieldDetail;
	}
}
