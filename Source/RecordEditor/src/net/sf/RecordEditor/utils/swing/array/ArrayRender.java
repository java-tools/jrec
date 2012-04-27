/*
 * @Author Bruce Martin
 * Created on 9/02/2007
 *
 * Purpose:
 * create a Date field field with a popup date selector
 */

package net.sf.RecordEditor.utils.swing.array;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BasePanel;


import com.zbluesoftware.java.bm.ArrowButton;

/**
 * create a Date field field with a popup date selector
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class ArrayRender extends JPanel implements ActionListener, TableCellRenderer {

	private static String[] ARRAY_COLUMN_NAMES = {"Index", "Data"};
	private static String[] MAP_COLUMN_NAMES = {"Key", ARRAY_COLUMN_NAMES[1]};
	
    private static final int FIELD_WIDTH = 20;
    private JTextField fld = new JTextField();
    private JButton btn = new ArrowButton(ArrowButton.SOUTH);
    //private JPopupArray popup = null;


    private JTable parentTable = null;
    private int parentRow, parentColumn;
    private ArrayInterface arrayDtls;
    
    private String docName; 
    private Object doc;
    private String initialValue;

    /**
     * Create Array field
      */
	public ArrayRender() {

        init();
    }


    /**
     * Perform initialisation
     */
    private void init() {

        this.setLayout(new BorderLayout());
        this.add(BorderLayout.CENTER, fld);
        this.add(BorderLayout.EAST, btn);

        fld.setMinimumSize(new Dimension(FIELD_WIDTH, fld.getHeight()));

        //this.setBorder();
        fld.setBorder(BorderFactory.createEmptyBorder());
        btn.addActionListener(this);
        this.setOpaque(true);
    }

    /**
     * Set the parent table details
     * @param tbl table being displayed
     * @param row row being displayed
     * @param col column being displayed
     * @param value field value;
     */
    public void setTableDetails(JTable tbl, int row, int col, Object value,
    		String documentName, Object document) {
        parentTable  = tbl;
        parentRow    = row;
        parentColumn = col;
		docName = documentName;
		doc = document;
       
		if (value instanceof ArrayInterface) {
			arrayDtls = (ArrayInterface) value;
			fld.setText(arrayDtls.toString());
		}
		initialValue = fld.getText();
    }

    
    public Object getValue() {
    	if (initialValue.equals(fld.getText())) {
    		return arrayDtls.getReturn();
    	}
    	return fld.getText();
    }




	/**
	 * @param arrayDetails the arrayDtls to set
	 */
	public final void setArrayDtls(ArrayInterface arrayDetails) {
		this.arrayDtls = arrayDetails;
	}


	/**
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent
	 * (javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	public Component getTableCellRendererComponent(
		JTable tbl,
		Object value,
		boolean isSelected,
		boolean hasFocus,
		int row,
		int column) {
		//System.out.println("Getting Render Component: " + row + ", " + column);
		
		Color foreground, background;
		if (value != null && value instanceof ArrayInterface) {
			arrayDtls = (ArrayInterface) value;
			fld.setText(arrayDtls.toString());
			btn.setVisible(true);
		} else {
			fld.setText("");
			btn.setVisible(false);
		}
		initialValue = fld.getText();
		
        if (isSelected) {
            foreground = tbl.getSelectionForeground();
            background = tbl.getSelectionBackground();
        } else {
        	foreground = tbl.getForeground();
            background = tbl.getBackground();
        }
 
        fld.setForeground(foreground);
        fld.setBackground(background);


		return this;
	}

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {

    	if (e.getSource() == btn) {
    		 new JPopupArray(docName, doc,
    				arrayDtls, 
    				parentTable, parentRow , parentColumn);
    		 Common.stopCellEditing(parentTable);
    	}
    }


    /**
     * Display array Field in a Popup Table
     *
     *
     * @author Bruce Martin
     *
     */
    public static class JPopupArray extends ReFrame 
    implements ActionListener,  TableModelListener {
        //private Calendar date = null;
        private ArrayTableModel model;
        private JTable table;
        private JTable parentTbl;
        private int parentR, parentC;
        private ArrayInterface arrayDtls;

        private JButton addBtn = new JButton("Add Row Before");
        private JButton addAfterBtn = new JButton("Add Row After");
        private JButton deleteBtn = new JButton("Delete Rows");
        private Object parentDoc;
        private boolean changed = false;

        public JPopupArray(String documentName, Object document,
        		ArrayInterface array, JTable parentTable, int parentRow, int parentColumn) {
            super(documentName, "Array View: " + parentRow + ", " + parentColumn, document);
            
            arrayDtls = array;
            parentTbl = parentTable;
            parentR = parentRow;
            parentC = parentColumn;
            parentDoc = document;
             
        	model = new ArrayTableModel(array);
        	table = new JTable(model);

            BasePanel pnl = new BasePanel();
            JPanel p = new JPanel();
           
            table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

            p.add(addBtn);
            p.add(addAfterBtn);
            p.add(deleteBtn);
      		pnl.addComponent(1, 5, BasePanel.PREFERRED, BasePanel.GAP,
    		        BasePanel.FULL, BasePanel.FULL, p);

    		pnl.addComponent(1, 5, BasePanel.PREFERRED, BasePanel.GAP,
    		        BasePanel.FULL, BasePanel.FULL, table);
    		
    		addMainComponent(pnl);
    		
    		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    		addBtn.addActionListener(this);
    		addAfterBtn.addActionListener(this);
    		deleteBtn.addActionListener(this);
    		setVisible(true);
    		
    		TableCellRenderer render = array.getTableCellRenderer();
    		if (render != null) {
    			TableCellEditor editor = array.getTableCellEditor();
    			int height = array.getFieldHeight();
    			TableColumn tc =  table.getColumnModel().getColumn(1);
    			tc.setCellRenderer(render);
    			
    			if (editor != null) {
    				tc.setCellEditor(editor);
    			}
    			
    			if (height > 0) {
    				table.setRowHeight(height);
    			}
    			table.addMouseListener(new MouseAdapter() {
    		 		   /**
    			     * @see MouseAdapter#mousePressed(java.awt.event.MouseEvent)
    			     */
    			    public void mousePressed(MouseEvent e) {
    			    	super.mousePressed(e);			      	
 			    	    			    	
    		      		int col = table.columnAtPoint(e.getPoint());
    		            int row = table.rowAtPoint(e.getPoint());  		
    		            
  			       		if (! table.hasFocus()) {
			       			table.requestFocusInWindow();
			       		}

     		            if (row >= 0 && row != table.getEditingRow() 
    		        	&&  col >= 0 && col != table.getEditingColumn()) {
    		            	table.editCellAt(row, col);
    		            }
    			    }
				
    			});
    		}
    		
    		
    		this.addFocusListener(new FocusAdapter() {
    	    	public void focusLost(FocusEvent e) {
    	    		notifyParent();
    	    	}
    		});
    		
    	    this.addInternalFrameListener(new InternalFrameAdapter() {
                public void internalFrameClosing(InternalFrameEvent e)  {
                	notifyParent();
                }
    	    });

    	    AbstractTableModel fv;
    	    TableModel parentMdl = parentTbl.getModel();
    	    if (parentMdl instanceof AbstractTableModel) {
    	    	fv = (AbstractTableModel) parentMdl;
    	    	fv.addTableModelListener(this);
    	    } else if (parentDoc instanceof AbstractTableModel) {
    	    	fv = (AbstractTableModel) parentDoc;
    	    	fv.addTableModelListener(this);
    	    }
        }

		private void notifyParent() {
        	TableModel parentMdl = parentTbl.getModel();
        	
        	Common.stopCellEditing(table);
        	arrayDtls.flush();
        	if (parentMdl instanceof AbstractTableModel) {
        		((AbstractTableModel) parentMdl).fireTableDataChanged();
        	} else {
        		parentTbl.setValueAt(parentTbl.getValueAt(parentR, parentC), parentR, parentC);
        	}
        	
        	if (parentDoc instanceof ArrayNotifyInterface) {
        		ArrayNotifyInterface doc = (ArrayNotifyInterface) parentDoc;

        		doc.notifyOfChange(arrayDtls.getLine());
	        	if ((changed || model.changed)) {
	        		 doc.setChanged(true);
	        	}
        	}
        }
        

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent event) {

			if (event.getSource() == deleteBtn) {
				int[] rows = table.getSelectedRows();
				if (rows != null && rows.length > 0) {
					//Common.stopCellEditing(table);
					for (int i = rows.length - 1; i>= 0; i--) {
						arrayDtls.remove(rows[i]);
					}
					model.fireTableDataChanged();
				}
			} else {
				int row = table.getSelectedRow();
				if (event.getSource() == addAfterBtn) {
					row += 1;
				}
				
				//Common.stopCellEditing(table);
				if (row >= 0 && row < arrayDtls.size()) {
					arrayDtls.add(row, null);
				} else {
					arrayDtls.add(null);
				}
				model.fireTableDataChanged();
			}			
			changed = true;
		}


		/* (non-Javadoc)
		 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
		 */
		@Override
		public void tableChanged(TableModelEvent arg0) {
			//System.out.println("## Table Changed");
			arrayDtls.retrieveArray();
			model.fireTableDataChanged();
		}		
    }

    /**
     * create a Table Model from a CSV style field
     *
     *
     * @author Bruce Martin
     *
     */
    private static class ArrayTableModel extends AbstractTableModel {
    	ArrayInterface array;
    	boolean changed = false;
    	String[] columnNames = ARRAY_COLUMN_NAMES;
    	int colAdj = 1;
    	
        /**
		 * @param array
		 */
		public ArrayTableModel(ArrayInterface array) {
			this.array = array;
			
			if (array.getColumnCount() == 2) {
				columnNames = MAP_COLUMN_NAMES;
				colAdj = 0;
			}
		}

		
		
		/**
		 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
		 */
		@Override
		public String getColumnName(int column) {
			return columnNames[column];
		}



		/**
         * Wether the cell is editable
         * @see javax.swing.table.TableModel#isCellEditable(int, int)
         */
        public boolean isCellEditable(int row, int column) {
            return column > 0 || (array.getColumnCount() == 2);
        }

        /**
         * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
         */
		public void setValueAt(Object newValue, int row, int column) {
            changed |= newValue == null || ! newValue.equals(array.get(row, column - colAdj)) ;
 
            array.set(row, column - colAdj, newValue);
        }

        /**
         * @see javax.swing.table.TableModel#getColumnCount()
         */
        public int getColumnCount() {
            return 2;
        }

        /**
         * @see javax.swing.table.TableModel#getRowCount()
         */
        public int getRowCount() {
            
            return array.size();
        }

        /**
         * @see javax.swing.table.TableModel#getValueAt(int, int)
         */
        public Object getValueAt(int row, int column) {
        	
       		if (column == 0 && array.getColumnCount() == 1) {
         		return Integer.valueOf(row);
        	}
        	return array.get(row, column - colAdj);
        }
    }
}
