/*
 * @Author Bruce Martin
 * Created on 6/01/2007
 *
 * Purpose:
 *  Store File position details
 *
 * # Version 0.60 Bruce Martin 2007/02/16
 *   - support for all fields search and replace options
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - code reorg (refactoring)
 */
package net.sf.RecordEditor.edit.file;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.filter.Compare;

/**
 * stores a position in the file view
 * (for searching purposes)
 *
 * @author Bruce Martin
 *
 */
public final class FilePosition {
    public static final int END_OF_COLUMN = Integer.MAX_VALUE;
    public int row;
    public int col;
    public int recordId;
    private   int fieldId;
    public boolean found = false;
    public int currentFieldNumber;
    
    public AbstractLine currentLine = null;

    private boolean forward;
    private int  inc, initCol;
    
    private boolean readPending = false;


    /**
     * Create a position
     * @param theRow row
     * @param theCol column
     * @param theRecord Record id to use
     * @param theField field being searched
     * @param forwardDirection wether we are going forward or not
     */
    public FilePosition(final int theRow, final int theCol,
            final int theRecord, final int theField,
            final boolean forwardDirection) {
        super();
        setAll(theRow, theCol, theRecord, theField, forwardDirection);
    }

    /**
     * Update the position
     * @param theRow row
     * @param theCol column
     * @param theRecord Record id to use
     * @param theField field being searched
     * @param forwardDirection wether we are going forward or not
     */
    public void setAll(final int theRow, final int theCol,
            final int theRecord, final int theField,
            final boolean forwardDirection) {

        setForward(forwardDirection);
        setFieldId(theField);
        row = theRow;
        col = theCol;
        recordId = theRecord;

    }

    /**
     * Adjust the position by a supplied length
     *
     * @param length length of search text
     * @param normalSearch wether it is a normal search or
     */
    public void adjustPosition(int length, int operator) {

        //System.out.print("Pos " + row + " " + currentFieldNumber + " " + col);
    	readPending = false;
        if (col < 0 || col == FilePosition.END_OF_COLUMN || operator == Compare.OP_EQUALS) {
            adjustPosField();
        } else if (forward) {
            col += length;
        } else {
            col -= 1;
            if (col <= 0) {
                adjustPosField();
            }
        }
        //System.out.println("  New Pos " + row + " " + currentFieldNumber + " " + col);

    }


    /**
     * Adjust the field being searched
     *
     */
    private void adjustPosField() {

        if (fieldId == Common.ALL_FIELDS) {
            currentFieldNumber += inc;
            if (currentFieldNumber < 0) {
                row += inc;
                readPending = true;
            }
        } else {
            row += inc;
            readPending = true;
        }
        col = initCol;
    }

    /**
     * @return Returns the fieldId.
     */
    public int getFieldId() {
        return fieldId;
    }

    /**
     * @param newFieldId The fieldId to set.
     */
    public void setFieldId(int newFieldId) {
        int nFieldId = newFieldId;

		if (nFieldId == -1) {
		    nFieldId = Common.ALL_FIELDS;
		} else if (nFieldId < -1) {
		    nFieldId = Common.FULL_LINE;
		    currentFieldNumber = Common.FULL_LINE;
		}

        if (fieldId != nFieldId) {
            col = initCol;
            currentFieldNumber = newFieldId;
        }
        fieldId = nFieldId;
    }

    /**
     * @return Returns the forward.
     */
    public boolean isForward() {
        return forward;
    }

    /**
     * @param pForward The forward to set.
     */
    public void setForward(boolean pForward) {
        this.forward = pForward;

		inc = -1;
		initCol = FilePosition.END_OF_COLUMN;
		if (pForward) {
			inc = 1;
			initCol = 0;
		}
    }

    /**
     * move to the next row
     */
    public void nextRow() {
	    row += inc;
	    col = initCol;
    }
    
    public void resetCol() {
    	col = initCol;
    }

    /**
     * move to the next row
     */
    public void nextField() {
        currentFieldNumber += inc;
        //System.out.print(">> " + currentFieldNumber);
	    col = initCol;
    }


    /**
     * Check if current field number is in the valid range
     *
     * @param maxField maximum valid field number
     *
     * @return wether the field number is in the valid range
     */
    public boolean isValidFieldNum(int maxField) {
        return currentFieldNumber >= 0
        &&  currentFieldNumber < maxField;
    }

	/**
	 * @return the readPending
	 */
	public final boolean isReadPending() {
		return readPending;
	}

	/**
	 * @param readPending the readPending to set
	 */
	public final void setReadPending(boolean readPending) {
		this.readPending = readPending;
	}
}
