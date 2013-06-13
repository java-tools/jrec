package net.sf.JRecord.Details;

import net.sf.JRecord.Common.AbstractFieldValue;
import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.Common.RecordException;

public abstract class BaseLine<Layout extends AbstractLayoutDetails>  implements AbstractLine {

	protected Layout layout;

//	public BaseLine() {
//		super();
//	}

	public BaseLine(Layout layout) {
		super();
		this.layout = layout;
	}

//	/**
//	 * @param pLayout The layouts to set.
//	 */
//	public void setLayout(final AbstractLayoutDetails pLayout) {
//		this.layout = (Layout) pLayout;
//	}

	/**
	 * Get the Layout
	 * @return Returns the layouts.
	 */
	public final Layout getLayout() {
	    return layout;
	}


	/**
	 * Get a fields value
	 *
	 * @param fieldName field to retrieve
	 *
	 * @return fields Value
	 */
	public Object getField(String fieldName) {
		IFieldDetail fld = layout.getFieldFromName(fieldName);

	   	if (fld == null) {
	   		return null;
	   	}

	   	return getField(fld);
	}



	@Override
	public final AbstractFieldValue getFieldValue(IFieldDetail field) {
		return new FieldValue(this, field);
	}

	@Override
	public final  AbstractFieldValue getFieldValue(int recordIdx, int fieldIdx) {
		return new FieldValue(this, recordIdx, fieldIdx);
	}

	@Override
	public final AbstractFieldValue getFieldValue(String fieldName) {
		return  getFieldValue(layout.getFieldFromName(fieldName));
	}

	/**
	 * Set a field via its name
	 *
	 * @param fieldName fieldname to be updated
	 * @param value value to be applied to the field
	 *
	 * @throws RecordException any conversion error
	 */
	public final void setField(String fieldName, Object value) throws RecordException {
		IFieldDetail fld = layout.getFieldFromName(fieldName);

		if (fld != null) {
			setField(fld, value);
		}
	}

	/**
	 * Get Field Iterator for the requested Record-Type
	 * @param recordNumber Record Name
	 * @return Field Iterator
	 */
	public final FieldIterator getFieldIterator(String recordName) {
		int recordNumber = layout.getRecordIndex(recordName);
		if (recordNumber < 0) {
			throw new RuntimeException("Record: " + recordName + " does not exist in layout");
		}
		return new FieldIterator(this, recordNumber);
	}

	/**
	 * Get Field Iterator for the preferred-Record-Type
	 * @return Field Iterator
	 */
	public final FieldIterator getFieldIterator() {
		return new FieldIterator(this, getPreferredLayoutIdx());
	}

	/**
	 * Get Field Iterator for the requested Record-Type
	 * @param recordNumber record number
	 * @return Field Iterator
	 */
	public final FieldIterator getFieldIterator(int recordNumber) {
		return new FieldIterator(this, recordNumber);
	}

}