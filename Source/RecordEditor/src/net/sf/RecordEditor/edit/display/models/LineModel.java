
/*
 * @Author Bruce Martin
 * Created on 7/08/2005
 *
 * Purpose:
 *    A TableModel to display a single line (or Record) with each
 * field displayed as a row going down the screen.
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/10
 *   -  changed to get Active JFrame via ReMainFrame.getMasterFrame()
 *
 *  # Version 0.60 Bruce Martin 2007/02/16
 *   - Added support for sorting (we now store the actual line
 *     and retrieve the linenumber as needed)
 *
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - Support for Full line mode
 *   - hex and Text updates
 *   - JRecord Spun off, code reorg
 */
package net.sf.RecordEditor.edit.display.models;


import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.edit.file.FileView;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;





/**
 * A TableModel to display a single line (or Record) with each
 * field displayed as a row going down the screen.
 *
 * @author Bruce Martin
 *
 */
public class LineModel extends BaseLineModel {

	public static final int DATA_COLUMN = 3;
	public static final int HEX_COLUMN = 5;
	static final int TEXT_COLUMN = 4;

	private int currentRow;

	private AbstractLine<?> currentLine;

	boolean oneLineHex = true;
	
	private int columnCount;


    /**
     * Create Record View of a file
     *
     * @param file file to display
     */
   public LineModel(final FileView<?> file) {
       super(file);
       
       columnCount = columnName.length - 1;
       if (getFileView().isBinaryFile()) {
    	   columnCount += 1;
       }
   }


	/**
	 * @see javax.swing.table.TableModel#getColumnCount
	 */
	@Override
	public int getColumnCount() {
		return columnCount;
	}

	/**
	 * @param columnCount the columnCount to set
	 */
	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}

	/**
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(final int row, final int col) {
	
		if (col < FIRST_DATA_COLUMN) {
			return super.getValueAt(row, col);
		} else if (currentLine == null) {
		    return null;
		}
	    int idx = getFixedCurrentLayout();
	    if (idx >= layout.getRecordCount()) {
	        if (col == FIRST_DATA_COLUMN) { // Formated Data Column
				return  currentLine.getFullLine();
			} else if (col == TEXT_COLUMN) {						  // Straight Text
				return currentLine.getFullLine();
			} else if (oneLineHex) {
				return null; // currentLine.getFieldHex(getFixedCurrentLayout(), row);
			} else {
			    return currentLine.getData();
	        }
		} else if (col == FIRST_DATA_COLUMN) { // Formated Data Column
			try {
				return  currentLine.getField(getFixedCurrentLayout(), getRealRowWithKey(row));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else if (isKeyRow(row)) {
			return null;
		} else if (col == TEXT_COLUMN) {						  // Straight Text
			return currentLine.getFieldText(getFixedCurrentLayout(), getRealRow(row));
		} else if (oneLineHex) {
			return currentLine.getFieldHex(getFixedCurrentLayout(), getRealRow(row));
		} else {
		    return currentLine.getFieldBytes(getFixedCurrentLayout(), getRealRow(row));
		}
	//		return fileView.getValueAt(getFixedCurrentLayout(), currentRow, row);
	//	} else if (col == TEXT_COLUMN) {						  // Straight Text
	//		return fileView.getTextValueAt(getFixedCurrentLayout(), currentRow, row);
	//	} else {						  // Straight Text
	//		return fileView.getHexValueAt(getFixedCurrentLayout(), currentRow, row);
	//	}
		}

	/**
	 * @see javax.swing.table.AbstractTableModel.setValueAt
	 */
	@Override
	public void setValueAt(Object val, int row, int col) {

//	    System.out.println("~~ " + currentRow + ", " + row
//	    		+ " " + isKeyRow(row) );
	    //int column = fileView.getRealColumn(getFixedCurrentLayout(), row);

		if (col == FIRST_DATA_COLUMN) {
			getFileView().setValueAt(
			        ReMainFrame.getMasterFrame(),
			        getFixedCurrentLayout(), currentLine, currentRow, getRowLocal(row), val);
		} else if (isKeyRow(row)) {
			
		} else if (getCurrentLayout() < layout.getRecordCount()
		       && (col == HEX_COLUMN || col == TEXT_COLUMN)) {
		    int inputType = FileView.SET_USING_HEX;
		    String value = "";
		    if (val != null) {
		        value = val.toString();
		    }

		    if (col == TEXT_COLUMN) {
		        inputType = FileView.SET_USING_TEXT;
		    }
		    getFileView().setHexTextValueAt(
			        ReMainFrame.getMasterFrame(), inputType,
			        getFixedCurrentLayout(), currentRow, getRowLocal(row), value);
		}
	}




	/**
	 * Get the current position of the line int the line view
	 * @return current line number
	 */
	public int getActualLineNumber() {
	    currentRow = getFileView().indexOf(currentLine);
	    return currentRow;
	}


	/**
	 * @see javax.swing.table.TableModel#isCellEditable(int, int)
	 */
	public boolean isCellEditable(int row, int col) {
		return col >= FIRST_DATA_COLUMN
		    && (oneLineHex || col < HEX_COLUMN)
		    && (getFixedCurrentLayout() < layout.getRecordCount()
		     || ! getFileView().isBinaryFile());
	}


    /**
     * Set Oneline hex value
     * @param newOneLineHex The threeLineHex to set.
     */
    public void setOneLineHex(boolean newOneLineHex) {
        this.oneLineHex = newOneLineHex;
    }

    /**
     * get the current line
     * @return current line
     */
    public AbstractLine<?> getCurrentLine() {
        return currentLine;
    }
    
	/**
	 * Sets record to display
	 *
	 * @param newCurrentRow new Record to display
	 * @param defaultLayout default record layout to use
	 *
	 */
	public void setCurrentLine(int newCurrentRow, int defaultLayout) {
	    currentRow  = newCurrentRow;
	    currentLine = null;
	    int newIdx = defaultLayout;
	    //System.out.println("~~~> " + newCurrentRow + " " + fileView.getRowCount());
	    if (newCurrentRow < getFileView().getRowCount()) {
	        currentLine = getFileView().getLine(newCurrentRow);
		    newIdx = currentLine.getPreferredLayoutIdx();
	    }
	    
	    setIndex(newIdx, defaultLayout);
	}
	
	/**
	 * Set the Current Line
	 * @param line Current Line
	 * @param defaultLayout default record layout
	 */
	public void setCurrentLine(AbstractLine<?> line, int defaultLayout) {
		currentLine = line;
		setIndex(currentLine.getPreferredLayoutIdx(), defaultLayout);
	}
	
	private void setIndex(int newIdx, int defaultLayout) {
		
	    if (newIdx == Common.NULL_INTEGER) {
	        this.fireTableDataChanged();
	        newIdx = defaultLayout;
	    } else if  (newIdx == getFixedCurrentLayout()) {
	        this.fireTableDataChanged();
	    } else {
	        this.fireTableStructureChanged();
	    }

	    if (newIdx != Common.NULL_INTEGER) {
	        setCurrentLayout(newIdx);
	    }
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.models.BaseLineModel#showKey()
	 */
	@Override
	protected boolean showKey() {
		boolean ret = false;
		
		if (currentLine.getTreeDetails().getChildDefinitionInParent() != null) {
			ret = currentLine.getTreeDetails().getChildDefinitionInParent().isMap();
		}
		return ret;
	}
	
	
}


