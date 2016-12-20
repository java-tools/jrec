/*
 * @Author Bruce Martin Created on 13/01/2007
 *
 * Purpose:
 *   Display a fixed column JTable in a JScrollpane
 */
package net.sf.RecordEditor.utils.swing;

import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.AbstractCellEditor;
import javax.swing.JMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import net.sf.RecordEditor.utils.common.Common;



/**
 *  Create a fixed column scroll pane
 *
 *
 * @author Unknown (copied from the web)
 *
 */
@SuppressWarnings("serial")
public class FixedColumnScrollPane extends JScrollPane  {
	public final int STD_LINE_HEIGHT;
	public final int TWO_LINE_HEIGHT;
	public final int THREE_LINE_HEIGHT;
	public final static int FIXED_INDEX = 0;
	public final static int MAIN_INDEX = 1;

	private ButtonTableRendor tableBtn = new ButtonTableRendor();
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

//			System.out.println(" -- display " + colDef.getHeaderValue()
//					+ " " + colDef.getModelIndex()
//					+ " ! "+ originalColumn
//					+" $ " + mdl.getColumnCount());

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
    	this(tbl, new JTable(tbl.getModel()));
    }
    
    /**
     * Fixed table (suplying both the main and Fixed Table.
     * @param tbl
     * @param fixedTbl
     */
    public FixedColumnScrollPane(final JTable tbl, JTable fixedTbl) {
        super(tbl);

        main = tbl;
        fixedTable = fixedTbl;

        STD_LINE_HEIGHT = main.getRowHeight();
        TWO_LINE_HEIGHT = (STD_LINE_HEIGHT + 1) * 2;
        THREE_LINE_HEIGHT = (STD_LINE_HEIGHT + 1) * 3;

        fixedTable.addKeyListener(new KeyAdapter() {
        	//private boolean first = true;


			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP) {
					addjustViewport();
				}
			}
		});


        fixedTable.addFocusListener(new FocusAdapter() {

			/* (non-Javadoc)
			 * @see java.awt.event.FocusAdapter#focusGained(java.awt.event.FocusEvent)
			 */
			@Override
			public void focusGained(FocusEvent e) {
				addjustViewport();
			}

		});


    }


    private void addjustViewport() {

		correctFixedSize();
		main.scrollRectToVisible(fixedTable.getVisibleRect());
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
        Common.calcColumnWidths(fixedTable, 0, SwingUtils.ONE_CHAR_TABLE_CELL_WIDTH * 20);
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
 
//        checkColumnModel(main, mainColumnModels, true);
//        checkColumnModel(fixedTable, fixedColumnModels, false);
        main.setColumnModel(new DefaultTableColumnModel());
        main.createDefaultColumnsFromModel();

        fixedTable.setModel(main.getModel());
        fixedTable.setColumnModel(new DefaultTableColumnModel());
        fixedTable.createDefaultColumnsFromModel();
      
        TableColumnModel columnModel = main.getColumnModel();
        TableColumnModel fixedModel  = fixedTable.getColumnModel();

//        System.out.print(">> Starting Columns to display:  ");
//        if (mainColumnModels != null) {
//        	System.out.print(mainColumnModels.length + " " + fixedColumnModels.length);
//        }
//        System.out.println();

//        fixedTable.setModel(main.getModel());
        numCols = Math.min(columnModel.getColumnCount(), fixedModel.getColumnCount()) ;
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
        for (i = Math.min(fixedColumns, columnModel.getColumnCount()) - 1; i >= 0; i--) {
            columnModel.removeColumn(columnModel.getColumn(i));
        }

        //  Remove the non-fixed columns from the fixed table

        while (fixedTable.getColumnCount() > fixedColumns) {
            fixedModel.removeColumn(fixedModel.getColumn(fixedColumns));
        }

        correctFixedSize();
//        System.out.println("<< End of Columns to display");
    }

//    private void checkColumnModel(
//    		JTable table,
//    		TableColumn[] columnArray,
//    		boolean add) {
//
//    	TableColumnModel columnModel = table.getColumnModel();
//
//    	//table.setColumnModel(new DefaultTableColumnModel());
//    	table.setAutoCreateColumnsFromModel(true);
////        if (columnModel != null
////        && columnArray != null
////        && table.getModel().getColumnCount()  == columnArray.length
////        && columnModel.getColumnCount() != columnArray.length) {
////        	System.out.println("-- Different number of columns 1 ??? " + columnModel.getColumnCount()
////        			+ " ~~ " + columnArray.length
////        			+ " ~~ " + table.getModel().getColumnCount());
////        	table.setColumnModel(new DefaultTableColumnModel());
////        	System.out.println("-- Different number of columns 1 ??? " + columnModel.getColumnCount()
////        			+ " ~~ " + columnArray.length
////        			+ " ~~ " + table.getModel().getColumnCount());
////
////        	for (int i = 0; i < columnArray.length && columnModel.getColumnCount() > 0; i++) {
////        		columnModel.removeColumn(columnArray[i]);
////        		
////        		if (add) {
////        			columnModel.addColumn(columnArray[i]);
////        		}
////        	}
////           	System.out.println("-- Different number of columns 2 ??? " + columnModel.getColumnCount()
////           			+ " ~~ " + columnArray.length
////        			+ " ~~ " + table.getModel().getColumnCount());
////
////        }
//
//    }

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
    	doFixColumn(main.getColumnModel().getColumn(col), col);
    }

    public void doFixColumn(TableColumn mainColumn, int col) {
        int actualCol = mainColumn.getModelIndex();
        TableColumnModel colMdl = fixedTable.getColumnModel();

        fixedColumnModels[actualCol].setPreferredWidth(mainColumn.getPreferredWidth());
        fixedColumnModels[actualCol].setCellEditor(mainColumn.getCellEditor());
        fixedColumnModels[actualCol].setCellRenderer(mainColumn.getCellRenderer());
        fixedColumnModels[actualCol].setHeaderRenderer(mainColumn.getHeaderRenderer());
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

    	TableColumn mainColumn = main.getColumnModel().getColumn(col);
//      	String s = mainColumn.getHeaderValue().toString();
//    	if ((idx = s.indexOf('|')) >= 0) {
//    		s = s.substring(idx + 1);
//    	}


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

	public int[][] getColumnSequence() {
       	int[][] fields = new int[2][];

    	fields[MAIN_INDEX] = getTableCols(main.getColumnModel());
    	fields[FIXED_INDEX] =  getTableCols(fixedTable.getColumnModel());

    	return fields;
	}

	private int[] getTableCols(TableColumnModel mdl) {
		int[] fields = new int[mdl.getColumnCount()];
    	for (int i = 0; i < fields.length; i++) {
    		fields[i] = mdl.getColumn(i).getModelIndex();
    	}

		return fields;
	}

	public void setColumnSequence(int[][] seq, int start) {

		int idx;
		TableColumnModel mdl = fixedTable.getColumnModel();
		for (int i = mdl.getColumnCount() - 1; i >= 0; i--) {
			if (mdl.getColumn(i).getModelIndex() >= start) {
				doUnFixColumn(i);
			}
		}
		mdl = main.getColumnModel();
		int[] ix = new int[mainColumnModels.length];
		for (int i = mdl.getColumnCount() - 1; i>= 0; i--) {
			ix[mdl.getColumn(i).getModelIndex()] = i;
		}

		for (int i=0 ; i < seq[FIXED_INDEX].length; i++) {
			idx = seq[FIXED_INDEX][i];
			this.doFixColumn(mainColumnModels[idx], ix[idx]);
		}
		setTableCols(
				main.getColumnModel(),
				start,
				seq[MAIN_INDEX],
				mainColumnModels);
//		setTableCols(
//				fixedTable.getColumnModel(),
//				start,
//				seq[FIXED_INDEX],
//				fixedColumnModels);
	}

	private void setTableCols(TableColumnModel mdl, int start, int[] fields,  TableColumn[] columns) {
		int i;

		for (i = mdl.getColumnCount() - 1; i>= 0; i--) {
			if (mdl.getColumn(i).getModelIndex() >= start) {
				mdl.removeColumn(mdl.getColumn(i));
			} else {
				System.out.println(
						mdl.getColumn(i).getModelIndex()
						+ " " + mdl.getColumn(i).getHeaderValue()
				);
			}
		}

		for (i = 0; i < fields.length; i++) {
			mdl.addColumn(columns[fields[i]]);
		}
	}
	
	public void setupLineFields(int rowCount, int numControlColumns, TableCellRenderer headerRender, boolean lineCommandField) {

		int width = SwingUtils.CHAR_FIELD_WIDTH;
		//System.out.println("Setup fixed columns ... " + width);
		setFixedColumns(numControlColumns);
		TableColumn tc = fixedTable.getColumnModel().getColumn(0);
		tc.setCellRenderer(tableBtn);
		tc.setPreferredWidth(5);
		if ( fixedTable.getColumnModel().getColumnCount() <= 1) {
			Common.logMsg("Error No Fields defined in the layout !!!", null);
		} else {
			tc = fixedTable.getColumnModel().getColumn(1);
			if (rowCount > 10000000) {
				width = 8;
			} else if (rowCount > 1000000) {
				width = 7;
			} else if (rowCount > 100000) {
				width = 6;
			}
			tc.setPreferredWidth(SwingUtils.STANDARD_FONT_WIDTH * width);
			tc.setResizable(false);
			
			if (lineCommandField) {
				tc.setCellEditor(new LineNumberCellEditor());
			}

			correctFixedSize();
		}

		TableColumnModel tcm = main.getColumnModel();

		for (int i = 0; i < tcm.getColumnCount(); i++) {
			tcm.getColumn(i).setHeaderRenderer(headerRender);
		}

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
    
    private static class LineNumberCellEditor extends AbstractCellEditor implements TableCellEditor {
    	JTextField textField = new JTextField();

    	
		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			textField.setText("");
			return textField;
		}



		@Override
		public Object getCellEditorValue() {
			return textField.getText();
		}
    }


}