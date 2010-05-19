/*
 * Created on 15/08/2004
 *
 * Basic Database record
 *
  * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *   - corrected variable name (insert)
 *
 * # Version 0.60 Bruce Martin 2007/02/16
 *   - Added new Record to updateStatus procedure
 *
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - split class in 2 (with some code going to AbstractUpdatableRecord
 *     in the new JRecord package).
 */
package net.sf.RecordEditor.utils.jdbc;

import javax.swing.JFrame;

import net.sf.JRecord.Common.AbsRow;
import net.sf.JRecord.External.AbstractUpdatableRecord;


/**
 * @author Bruce Martin
 *
 * Basic Database record
 */
public class AbsRecord extends AbstractUpdatableRecord implements AbsRow {

//    public static final int UNCHANGED  = 1;
//	//final public static int Inserted  = 2;
//    public static final int UPDATED    = 3;
//    public static final int NEW_RECORD = 2;
//    public static final int NULL_INT_VALUE  = -1;


//	private boolean insert = false;
    private boolean updateSuccessful = true;

    private JFrame tmpFrame;

    private AbstractUpdatableRecord externalValue = null;

	/**
	 * Create a null record
	 *
	 */
	public AbsRecord() {
		super(true);
	}


	/**
	 * Create a new record
	 *
	 * @param isNull wether it is a Null record
	 */
	public AbsRecord(final boolean isNull) {
		super(isNull);
	}


	/**
	 * @see java.lang.Object#clone()
	 */
	public Object clone() {
	    Object ret = null;
	    try {
	        ret = super.clone();
	    } catch (Exception e) { }

	    if (ret != null && ret instanceof AbsRecord) {
            return ret;
        }
		return new AbsRecord();
	}




	/**
	 * Set the column value
	 *
	 * @param frame JFrame being displayed
	 * @param fldNum the column to replace
	 * @param val new value of the column
	 */
	public void setField(JFrame frame, int fldNum, Object val) {
		tmpFrame = frame;

		setField(fldNum, val);
	}


	/**
	 * Get the value of a field (by field number)
	 *
	 * @param fldNum Field number
	 * @return value of the field
	 */
	public Object getField(int fldNum) {
		return null;
	}


	/**
	 * Update a fields value
	 * @param fldNum field number
	 * @param val new value to be assigned to the field
	 */
	public void setField(int fldNum, Object val) {

		if (val.getClass() == String.class) {
			setFieldWithString(fldNum, (String) val);
		} else {
			setFieldWithObject(fldNum, val);
		}
	}


	/**
	 * Update a fields value
	 * @param fldNum field number
	 * @param val new value to be assigned to the field
	 */
	protected void setFieldWithObject(int fldNum, Object val) { }


	/**
	 * Update a fields value
	 * @param fldNum field number
	 * @param val new value to be assigned to the field
	 */
	protected void setFieldWithString(int fldNum, String val) { }




	/**
	 * Get the Column Count
	 *
	 * @return Column Count
	 */
	public int getFieldCount() {
	    return 0;
	}


	/**
	 * @see AbstractUpdatableRecord
	 */
	public int getUpdateStatus() {
	    int ret = super.getUpdateStatus();
		if (externalValue != null) {
		    ret = externalValue.getUpdateStatus();
		}

		return ret;
	}

	/**
	 * Set update Status
	 *
	 * @param status new update status
	 */
	public void setUpdateStatus(int status) {
		if (status == AbsRecord.UNCHANGED) {
			setKeys();
		} else if (status == NEW_RECORD) {
		    setNew(true);
		}
		super.setUpdateStatus(status);

		if (externalValue != null) {
		    externalValue.setUpdateStatus(status);
		}
	}


	/**
	 * @return if itis inserted
	 */
	public boolean isNew() {
	    boolean ret = super.isNew();

	    if (externalValue != null) {
		    ret = externalValue.isNew();
		}

	    return ret;
	}

	/**
	 * @param pInserted The inserted to set.
	 */
	public void setNew(boolean pInserted) {
		if (! pInserted) {
			setKeys();
		}

		if (externalValue != null) {
		    externalValue.setNew(pInserted);
		}

		super.setNew(pInserted);
	}

	
	public boolean hasTheKeyChanged() {
		return false;
	}

	/**
	 * Setup key fields
	 *
	 */
	public void setKeys() { }

	/**
	 * @return Returns the updateSuccessful.
	 */
	public boolean isUpdateSuccessful() {
		return updateSuccessful;
	}


	/**
	 * @param pUpdateSuccessful The updateSuccessful to set.
	 */
	public void setUpdateSuccessful(boolean pUpdateSuccessful) {
		this.updateSuccessful = pUpdateSuccessful;
	}


	/**
	 * Converts a string into an integer. It will prompt the user if there
	 * is a problem
	 *
	 * @param currVal the existing value
	 * @param newVal  the new updated value
	 * @param name    name of the field
	 * @return        new value (or existing value if the user prefers)
	 */
	protected int cnvToInt(int currVal, String newVal, String name) {

		CorrectFieldInt cf = new CorrectFieldInt();

        return	cf.correctInt(tmpFrame, currVal, newVal, name);
	}




	/**
	 * Converts a string into an boolean. It will prompt the user if there
	 * is a problem
	 *
	 * @param currVal the existing value
	 * @param newVal  the new updated value
	 * @param name    name of the field
	 * @return        new value (or existing value if the user prefers)
	 */
	protected boolean cnvToBoolean(boolean currVal, String newVal, String name) {

		CorrectFieldBoolean cf = new CorrectFieldBoolean();

        return	cf.correctBoolean(tmpFrame, currVal, newVal, name);
	}


    /**
     * @param newValue The externalValue to set.
     */
    protected void setExternalValue(AbstractUpdatableRecord newValue) {
        this.externalValue = newValue;
    }
}
