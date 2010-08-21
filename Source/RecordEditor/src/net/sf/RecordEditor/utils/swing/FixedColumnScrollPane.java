/*
 * @Author Bruce Martin Created on 13/01/2007
 *
 * Purpose:
 *   Display a fixed column JTable in a JScrollpane
 */
package net.sf.RecordEditor.utils.swing;

import javax.swing.JMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;


/**
 *  Create a fixed column scroll pane
 *
 *
 * @author Unknown (copied from the web)
 *
 */
public class FixedColumnScrollPane extends JScrollPane  {
	public final int STD_LINE_HEIGHT;
	public final int TWO_LINE_HEIGHT;
	public final int THREE_LINE_HEIGHT;

    private JTable fixedTable = null;
    private JTable main;
    private int fixedColumns;

    private TableColumn[] fixedColumnModels;
    private TableColumn[] mainColumnModels;
    
    ShowFieldsMenu showFields = new ShowFieldsMenu() {

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.edit.display.common.ShowFieldsMenu#showColumn(javax.swing.table.TableColumn, int)
		 */
		@Override
		public boolean showColumn(TableColumn colDef, int originalColumn) {
			TableColumnModel mdl = main.getColumnModel();
			mdl.addColumn(colDef);

			System.out.println(" -- display " + colDef.getHeaderValue() 
					+ " " + colDef.getModelIndex()
					+ " ! "+ originalColumn
					+" $ " + mdl.getColumnCount());

			try {
				mdl.moveColumn(mdl.getColumnCount() - 1, originalColumn);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("-----------------------------------------------");
				System.out.println("Error Moving column: " + mdl.getColumnCount() + " " + originalColumn
						+ " " + colDef.getModelIndex() + " ~ "
						+ colDef.getHeaderValue());
				System.out.println("-----------------------------------------------");
			}
			return true;
		}
    };

    /**
     * Create FixedColumnTable
     * @param tbl main table to be displayed
     */
    public FixedColumnScrollPane(final JTable tbl) {
        super(tbl);

        main = tbl;
        fixedTable = new JTable(main.getModel());
        
        STD_LINE_HEIGHT = main.getRowHeight();
        TWO_LINE_HEIGHT = (STD_LINE_HEIGHT + 1) * 2;
        THREE_LINE_HEIGHT = (STD_LINE_HEIGHT + 1) * 3;
    }


    /**
     * Create a fixed column scroll pane
     * @param tbl main Table to display
     * @param numberFixedColumns number of fixed columns
     */
    public FixedColumnScrollPane(final JTable tbl,  final int numberFixedColumns) {
        this(tbl);

        setFixedColumns(numberFixedColumns);
    }

    /**
     * Set the number of fixed columns
     * @param numberFixedColumns number of columns to be fixed
     */
    public void setFixedColumns(final int numberFixedColumns) {

        fixedColumns = numberFixedColumns;
        //  Use the table to create a new table sharing
        //  the DataModel and ListSelectionModel

        fixedTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        fixedTable.setRowHeight(main.getRowHeight());
        //fixedTable.setFocusable(false);
        fixedTable.setSelectionModel(main.getSelectionModel());
        fixedTable.getTableHeader().setReorderingAllowed(false);
        fixedTable.getTableHeader().setResizingAllowed(false);
        fixedTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        main.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        setColumnsToDisplay();
    }

    /**
     * setup the columns to be displayed in each table
     *
     */
    private void setColumnsToDisplay() {
        int i;
        int numCols; 
        TableColumnModel columnModel = main.getColumnModel();
        TableColumnModel fixedModel  = fixedTable.getColumnModel();
        
        checkColumnModel(main, mainColumnModels);
        checkColumnModel(fixedTable, fixedColumnModels);
               
//        System.out.print(">> Starting Columns to display:  ");
//        if (mainColumnModels != null) {
//        	System.out.print(mainColumnModels.length + " " + fixedColumnModels.length);
//        }
        System.out.println();

        fixedTable.setModel(main.getModel());
        numCols = main.getModel().getColumnCount();
        mainColumnModels  = new TableColumn[numCols];
        fixedColumnModels = new TableColumn[numCols];
        
//        System.out.println("   >>> Column Counts " + numCols + " " + fixedModel.getColumnCount() + " "
//        		+ " " + fixedTable.getModel().getColumnCount()
//        		+ " == " + main.getModel().getColumnCount()
//        		+ " name " + fixedModel.getClass().getName()
//        		+ " " + fixedTable.getModel().getClass().getName());
        
        for (i = 0; i < numCols; i++) {
            mainColumnModels[i]  = columnModel.getColumn(i);
            fixedColumnModels[i] = fixedModel.getColumn(i);
//            if (i < main.getColumnCount()) {
        	fixedColumnModels[i].setHeaderRenderer(
        			mainColumnModels[i].getHeaderRenderer()
        	);
//            }
        }

        //  Remove the fixed columns from the main table
//        System.out.println("   >>> Fixed Count " + fixedColumns);
        for (i = fixedColumns - 1; i >= 0; i--) {
            columnModel.removeColumn(columnModel.getColumn(i));
        }

        //  Remove the non-fixed columns from the fixed table

        while (fixedTable.getColumnCount() > fixedColumns) {
            fixedModel.removeColumn(fixedModel.getColumn(fixedColumns));
        }

        correctFixedSize();
//        System.out.println("<< End of Columns to display");
    }
    
    private void checkColumnModel(
    		JTable table,
    		TableColumn[] columnArray) {
    	
    	TableColumnModel columnModel = table.getColumnModel();
    	
        if (columnModel != null 
        && columnArray != null
        && table.getModel().getColumnCount()  == columnArray.length
        && columnModel.getColumnCount() != columnArray.length) {
        	System.out.println("-- Different number of columns 1 ??? " + columnModel.getColumnCount() 
        			+ " ~~ " + columnArray.length
        			+ " ~~ " + table.getModel().getColumnCount());
        	for (int i = 0; i < columnArray.length; i++) {
        		columnModel.removeColumn(columnArray[i]);
        		columnModel.addColumn(columnArray[i]);
        	}
           	System.out.println("-- Different number of columns 2 ??? " + columnModel.getColumnCount() 
           			+ " ~~ " + columnArray.length
        			+ " ~~ " + table.getModel().getColumnCount());

        }

    }

    /**
     * fix the view sizes for the two tables
     *
     */
    public void correctFixedSize() {

        fixedTable.setPreferredScrollableViewportSize(fixedTable.getPreferredSize());
        setRowHeaderView(fixedTable);
        setCorner(JScrollPane.UPPER_LEFT_CORNER, fixedTable.getTableHeader());
    }

     
    /**
     * Move a column from the right hand scrollable table to the left
     * hand fixed table
     *
     * @param col column to move to fixed table
     */
    public void doFixColumn(int col) {

        TableColumn mainColumn = main.getColumnModel().getColumn(col);
        int actualCol = mainColumn.getModelIndex();
        TableColumnModel colMdl = fixedTable.getColumnModel();
 
        fixedColumnModels[actualCol].setPreferredWidth(mainColumn.getPreferredWidth());
        fixedColumnModels[actualCol].setCellEditor(mainColumn.getCellEditor());
        fixedColumnModels[actualCol].setCellRenderer(mainColumn.getCellRenderer());
        colMdl.addColumn(fixedColumnModels[actualCol]);
        main.getColumnModel().removeColumn(mainColumn);
        correctFixedSize();
        
        showFields.addjustHiddenColumns(col, -1,  Integer.MAX_VALUE);
    }

    
    
    /**
     * Move a column from the left hand fixed table to the right
     * hand scrollable table
     *
     * @param col column to move from fixed table
     */
    public void doUnFixColumn(int col) {

        TableColumn fixedColumn = fixedTable.getColumnModel().getColumn(col);
        int actualCol = fixedColumn.getModelIndex();

        fixedTable.getColumnModel().removeColumn(fixedColumn);
        main.getColumnModel().addColumn(mainColumnModels[actualCol]);
        
        showFields.addjustHiddenColumns(col, 1, Integer.MAX_VALUE);
        correctFixedSize();
    }


    /**
     * Move a column from the right hand scrollable table to the left
     * hand fixed table
     *
     * @param col column to move to fixed table
     */
    public void doHideColumn(int col) {

    	int idx;
    	TableColumn mainColumn = main.getColumnModel().getColumn(col);
      	String s = mainColumn.getHeaderValue().toString();
    	if ((idx = s.indexOf('|')) >= 0) {
    		s = s.substring(idx + 1);
    	}


       showFields.hideColumn(mainColumn, col);
//       adjustHiddenCol(col, -1);
//       if (restoreCol.length <= hiddenColumns.size()) {
//        	int[] temp =new int[restoreCol.length + 10];
//        	System.arraycopy(restoreCol, 0, temp, 0, restoreCol.length);
//        	restoreCol = temp;
//        }
//        restoreCol[hiddenColumns.size()] = col;
//        hiddenColumns.add(mainColumn);
         //hiddenColumnNo.entrySet()
        
        main.getColumnModel().removeColumn(mainColumn);
        correctFixedSize();
    }

    /**
     * Show a column
     * @param column column to be displayed
     * @return column index
     */
    public int doShowColumn(TableColumn column) {
    	int col = showFields.doShowColumn(column);

		correctFixedSize();

    	return col;
    }
    
    
    public void clearHiddenCols() {
    	showFields.removeAll();
    }
  

    /**
	 * get Field visibility
	 */
	public boolean[] getFieldVisibility(int colsToSkip) {
		return showFields.getFieldVisibility(mainColumnModels.length, colsToSkip);
	}

	/**
	 * set Field visibility
	 */
	public void setFieldVisibility(int colsToSkip, boolean[] fieldVisibility) {
		
		showFields.setFieldVisibility(main.getColumnModel(), colsToSkip, 0, fieldVisibility);
		
		correctFixedSize();
	}
    
    /**
     * @return Returns the fixedTable.
     */
    public JTable getFixedTable() {
        return fixedTable;
    }
    
    public JMenu getShowFieldsMenu() {
    	return showFields.getMenu();
    }
}