package net.sf.RecordEditor.utils.tblModels;

import javax.swing.table.AbstractTableModel;

public class LineArrayHexModel extends AbstractTableModel {
	
	private static final byte[] EMPTY_ARRAY = {};
    private byte[][] lines;

    private int columnCount = 0;
    private int numberOfRecords;
    

    /**
     * Table Model for displaying the sample file
     * @param lines2show lines to display
     */
    public LineArrayHexModel(final byte[][] lines2show, int recordCount) {
        super();
        lines = lines2show;
        numberOfRecords = recordCount;

        for (int i = 0; i < numberOfRecords; i++) {
            if (columnCount < lines[i].length) {
                columnCount = lines[i].length;
            }
        }
    }


    /**
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public int getColumnCount() {
        return columnCount;
    }

    /**
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount() {
        return numberOfRecords;
    }

    /**
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        byte[] s = EMPTY_ARRAY;
        if (rowIndex < numberOfRecords && lines[rowIndex].length > columnIndex) {
        	s = new byte[1];
        	s[0] = lines[rowIndex][columnIndex];
        }

        return s;
    }
    
    public byte[] getLine(int lineNumber) {
    	return lines[lineNumber];
    }
}

