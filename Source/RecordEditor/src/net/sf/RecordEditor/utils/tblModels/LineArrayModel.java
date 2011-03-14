package net.sf.RecordEditor.utils.tblModels;

import javax.swing.table.AbstractTableModel;

import net.sf.JRecord.Common.Conversion;

@SuppressWarnings("serial")
public class LineArrayModel extends AbstractTableModel {
    private byte[][] lines;
    private String font;
    private int columnCount = 0;
    private int numberOfRecords;
    

    /**
     * Table Model for displaying the sample file
     * @param lines2show lines to display
     */
    public LineArrayModel(final byte[][] lines2show, String fontname, int recordCount) {
        super();
        lines = lines2show;
        font = fontname;
        numberOfRecords = recordCount;

        for (int i = 0; i < numberOfRecords; i++) {
            if (columnCount < lines[i].length) {
                columnCount = lines[i].length;
            }
        }
        columnCount = Math.min(columnCount, 32000);
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
        String s = "";
        if (rowIndex < numberOfRecords && lines[rowIndex].length > columnIndex) {
        	String t  = Conversion.getString(lines[rowIndex], 0, lines[rowIndex].length, font);
            if (columnIndex < t.length()) {
            	s = t.substring(columnIndex, columnIndex + 1);
            }
        }

        return s;
    }
    
    public byte[] getLine(int lineNumber) {
    	return lines[lineNumber];
    }
}

