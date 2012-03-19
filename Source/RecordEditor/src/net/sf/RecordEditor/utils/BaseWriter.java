package net.sf.RecordEditor.utils;


public abstract class BaseWriter implements FieldWriter {

	private boolean[] numericFields = null;
	private boolean[] printField;
	private int numberOfInitialFields = 0;


	public final void setNumericFields(boolean[] numericFields) {
		this.numericFields = numericFields;
	}



	@Override
	public final boolean isFieldToBePrinted(int fldNo) {
		return printField == null 
			|| fldNo-numberOfInitialFields >= printField.length 
			|| fldNo < numberOfInitialFields
			|| printField[fldNo-numberOfInitialFields];
	}
	
	protected final boolean isNumeric(int fieldNo) {
		return     numericFields != null 
				&& fieldNo >= numberOfInitialFields
				&& fieldNo-numberOfInitialFields < numericFields.length
				&& numericFields[fieldNo-numberOfInitialFields];
	}

	protected final int adjustFieldNo(int fieldNo) {
		return fieldNo-numberOfInitialFields;
	}

	@Override
	public void setupInitialFields(int numberOfInitialFields, int[] levelSizes) {
		this.numberOfInitialFields = numberOfInitialFields;
	}

	/**
	 * @param printField the printField to set
	 */
	public final void setPrintField(boolean[] printField) {
		this.printField = printField;
	}

	@Override
	public final void setPrintField(int idx, boolean include) {
		if (printField != null && idx >= 0 && idx < printField.length) {
			printField[idx] = include;
		}
	}

}