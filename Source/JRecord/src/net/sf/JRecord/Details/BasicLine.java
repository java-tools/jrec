package net.sf.JRecord.Details;


import net.sf.JRecord.Common.AbstractFieldValue;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Types.Type;

public abstract class BasicLine<ActualLine extends AbstractLine>
implements AbstractLine, ISetLineProvider<LayoutDetail, ActualLine> {


	protected static final byte[] NULL_RECORD = new byte[0];
	protected LineProvider<LayoutDetail, ? extends ActualLine> lineProvider;
	protected LayoutDetail layout;
	protected int preferredLayoutAlt = Constants.NULL_INTEGER;
	protected int preferredLayout = Constants.NULL_INTEGER;
	protected int writeLayout = Constants.NULL_INTEGER;


	protected AbstractTreeDetails<FieldDetail, RecordDetail, LayoutDetail, ActualLine>
							children;
	//ChildLines<FieldDetail, RecordDetail, LayoutDetail, AbstractChildDetails<RecordDetail>, ActualLine> children = null;

	public BasicLine(LineProvider<LayoutDetail, ActualLine> defaultProvider, LayoutDetail linesLayout,
			AbstractTreeDetails<FieldDetail, RecordDetail, LayoutDetail, ActualLine> defaultTree) {
		super();

		lineProvider = defaultProvider;
		layout = linesLayout;
		children = defaultTree;
	}



	protected final void init() {
		if (layout != null && layout.hasChildren()) {
			children = new TreeDetails<FieldDetail, RecordDetail, LayoutDetail, AbstractChildDetails<RecordDetail>, ActualLine>();
//			                        ChildLines<ProtoFieldDef, ProtoRecordDef, ProtoLayoutDef, ProtoChildDefinition, ProtoLine>
		}
	}


	/**
	 * Get the field value as Hex
	 *
	 * @param recordIdx Index of the current layout used to retrieve the field
	 * @param fieldIdx Index of the current field
	 *
	 * @return field value as a Hex String
	 */
	public final String getFieldHex(final int recordIdx, final int fieldIdx) {

		try {
			IFieldDetail field = layout.getField(recordIdx, fieldIdx);

			return layout.getField(getData(),
			        				Type.ftHex,
			        				field).toString();

		} catch (final Exception ex) {
			return "";
		}
	}


	/**
	 * @param pLayout The layouts to set.
	 */
	public void setLayout(final AbstractLayoutDetails pLayout) {
		this.layout = (LayoutDetail) pLayout;
		preferredLayoutAlt = Constants.NULL_INTEGER;

		init();
	}

	/**
	 * Get the Layout
	 * @return Returns the layouts.
	 */
	public LayoutDetail getLayout() {
	    return layout;
	}


	/**
	 * Alternative get layout method without length checks
	 */
	@Override
	public int getPreferredLayoutIdxAlt() {
		if (preferredLayoutAlt == Constants.NULL_INTEGER) {
			int defaultIdx = Constants.NULL_INTEGER;
			int i = 0;
			int defCount = -1;
			RecordSelection sel;
			int size = layout.getRecordCount();

			if (size == 1) {
			    preferredLayoutAlt = 0;
			} else if (layout.getDecider() != null) {
			    preferredLayoutAlt = layout.getDecider().getPreferedIndex(this);
			}


			// TODO fix default record
			while ((i < size) && (preferredLayoutAlt == Constants.NULL_INTEGER)) {
				sel = layout.getRecord(i).getRecordSelection();
				switch (sel.isSelected(this)) {
				case DEFAULT:
					if (sel.size() > defCount) {
						defaultIdx = i;
						defCount = sel.size();
					}
					break;

				case YES:
					preferredLayoutAlt = i;
					break;
				}

				i += 1;
			}
			if (preferredLayoutAlt == Constants.NULL_INTEGER) {
				preferredLayoutAlt = defaultIdx;
			}
		}

		return preferredLayoutAlt;
	}



	/**
	 * @param pWriteLayout The writeLayout to set.
	 */
	public void setWriteLayout(final int pWriteLayout) {
		this.preferredLayoutAlt = pWriteLayout;
		this.writeLayout = pWriteLayout;
	}

	/**
	 * Gets a fields value
	 *
	 * @param recordIdx Index of the RecordDescription to be used.
	 * @param fieldIdx Index of the required field
	 *
	 * @return the request field (formated)
	 */
	public Object getField(final int recordIdx, final int fieldIdx) {
		try {
			switch (fieldIdx) {
			case Constants.FULL_LINE:	return getFullLine();
			case Constants.KEY_INDEX:	return null;
			}

			return getField(layout.getField(recordIdx, fieldIdx));
		} catch (final Exception ex) {
			ex.printStackTrace();
			return "";
		}
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
	public AbstractFieldValue getFieldValue(IFieldDetail field) {
		return new FieldValue(this, field);
	}

	@Override
	public AbstractFieldValue getFieldValue(int recordIdx, int fieldIdx) {
		return new FieldValue(this, recordIdx, fieldIdx);
	}

	@Override
	public AbstractFieldValue getFieldValue(String fieldName) {
		return  getFieldValue(layout.getFieldFromName(fieldName));
	}


    /**
     * Test if Tree rebuild is required
     */
	public boolean isRebuildTreeRequired() {
		return false;
	}

	/**
	 * Set a field via its name
	 *
	 * @param fieldName fieldname to be updated
	 * @param value value to be applied to the field
	 *
	 * @throws RecordException any conversion error
	 */
	public void setField(String fieldName, Object value) throws RecordException {
		IFieldDetail fld = layout.getFieldFromName(fieldName);

		if (fld != null) {
			setField(fld, value);
		}
	}

	/**
	 * Sets a field to a new value
	 *
	 * @param recordIdx record layout
	 * @param fieldIdx field number in the record
	 * @param val new value
	 *
	 * @throws RecordException any error that occurs during the save
	 */
	public void setField(final int recordIdx, final int fieldIdx, Object val)
			throws RecordException {

	    IFieldDetail field = layout.getField(recordIdx, fieldIdx);

	    //adjustLengthIfNecessary(field, recordIdx);

	   	setField(field, val);
	}

	/**
     * Set the line provider
     *
     * @param pLineProvider The lineProvider to set.
     */
	@Override
    public void setLineProvider(LineProvider<LayoutDetail, ? extends ActualLine> pLineProvider) {
        this.lineProvider = pLineProvider;
    }


	public abstract Object clone();


	/**
	 * @return the children
	 */
	@Override
	public AbstractTreeDetails<FieldDetail, RecordDetail, LayoutDetail, ActualLine> getTreeDetails() {
		return children;
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.Common.AbstractChildLines#isRebuildTreeRequired()
	 */
	@Override
	public boolean isError() {
		return false;
	}



	@Override
	public <L extends AbstractLine> L getNewDataLine() {
		return (L) clone();
	}



}