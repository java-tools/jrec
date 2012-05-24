package net.sf.JRecord.detailsSelection;

import java.util.List;

import net.sf.JRecord.Common.AbstractIndexedLine;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.ExternalRecordSelection.ExternalFieldSelection;

public abstract class FieldSelect extends ExternalFieldSelection implements RecordSel {

	protected final FieldDetail fieldDetail;



	public FieldSelect(String name, String value, String op, FieldDetail fieldDef) {
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
	public boolean isSelected(AbstractIndexedLine line) {
		return false;
	}



	public static class Contains extends FieldSelect {

		protected Contains(String name, String value, FieldDetail fieldDef) {
			super(name, value, "Contains", fieldDef);
		}


		/* (non-Javadoc)
		 * @see net.sf.JRecord.Details.Selection.RecordSelection#isSelected(net.sf.JRecord.Details.AbstractLine)
		 */
		@Override
		public boolean isSelected(AbstractIndexedLine line) {

			Object o = line.getField(fieldDetail);
			if (super.isCaseSensitive() || o == null) {
				return  o != null
					&& (o.toString().indexOf(getFieldValue()) >= 0);
			}
			return (o.toString().toLowerCase().indexOf(getFieldValue()) >= 0);
		}
	}

	public static class DoesntContain extends FieldSelect {

		protected DoesntContain(String name, String value, FieldDetail fieldDef) {
			super(name, value, "Doesnt_Contain", fieldDef);
		}



		/* (non-Javadoc)
		 * @see net.sf.JRecord.Details.Selection.RecordSelection#isSelected(net.sf.JRecord.Details.AbstractLine)
		 */
		@Override
		public boolean isSelected(AbstractIndexedLine line) {

			Object o = line.getField(fieldDetail);
			if (super.isCaseSensitive() || o == null) {
				return  o == null
					|| (o.toString().indexOf(getFieldValue()) < 0);
			}
			return (o.toString().toLowerCase().indexOf(getFieldValue()) < 0);
		}
	}

	public static class StartsWith extends FieldSelect {

		protected StartsWith(String name, String value, FieldDetail fieldDef) {
			super(name, value, "Doesnt_Contain", fieldDef);
		}



		/* (non-Javadoc)
		 * @see net.sf.JRecord.Details.Selection.RecordSelection#isSelected(net.sf.JRecord.Details.AbstractLine)
		 */
		@Override
		public boolean isSelected(AbstractIndexedLine line) {

			Object o = line.getField(fieldDetail);
			if (super.isCaseSensitive() || o == null) {
				return  o != null
					&& (o.toString().startsWith(getFieldValue()));
			}
			return (o.toString().toLowerCase().startsWith(getFieldValue()));
		}
	}

	public static class TrueSelect extends FieldSelect {

		protected TrueSelect() {
			super("", "", "True", null);

		}

		/* (non-Javadoc)
		 * @see net.sf.JRecord.Details.Selection.RecordSelection#isSelected(net.sf.JRecord.Details.AbstractLine)
		 */
		@Override
		public boolean isSelected(AbstractIndexedLine line) {

			return true;
		}
	}

	/**
	 * @return the fieldDetail
	 */
	public FieldDetail getFieldDetail() {
		return fieldDetail;
	}
}
