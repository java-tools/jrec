/*
 * @Author Bruce Martin
 * Created on 9/02/2007
 *
 * Purpose:
 */
package com.zbluesoftware.java.bm;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

/**
 *
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public abstract class GenericComboTableRender extends AbstractCellEditor
    implements TableCellRenderer, TableCellEditor, PropertyChangeListener /*ActionListener*/ {


	private AbstractGenericCombo comboField;
    private final boolean usesValue;

    /**
     * Create a Checkbox table render based on Strings
     * @param useValue wether to return dates or Text to the Table
     * @param dateFormat format of the date 
     */
    public GenericComboTableRender(final boolean useValue, final AbstractGenericCombo field) {

        this.usesValue  = useValue;
        comboField = field;
        if (comboField != null) {
        	setComboField(comboField);
        }
    }


    /**
     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent
     * (javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
     */
    public Component getTableCellRendererComponent(
            final JTable table,
            final Object value,
            final boolean isSelected,
            final boolean hasFocus,
            final int row,
            final int column) {

        setValue(value);
        setColor(isSelected, table);
        
        return comboField;
    }



    /**
     * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
     */
    public Component getTableCellEditorComponent(final JTable table, final Object value,
            final boolean isSelected, final int row, final int column) {

    	setComboField(getCombo());
        setValue(value);
        setColor(isSelected, table);
        comboField.addPropertyChangeListener(new TableNotify(table, row, column));
        
        return comboField;
    }

    /**
     * Set the date field with a value
     * @param value new date value
     */
    public void setValue(final Object value) {

        comboField.setValue(value);
    }

    /**
     * @see javax.swing.CellEditor#getCellEditorValue()
     */
    public Object getCellEditorValue() {

        if (usesValue) {
            return comboField.getValue();
        }
        return comboField.getText();
    }
    
	/**
	 * Set the foreground / background colors based on wther the
	 * cell is selected
	 * 
	 * @param isSelected wether the cell is selected
	 * @param tbl table being displayed
	 */   
    private void setColor(final boolean isSelected, final JTable tbl) {
    	
        if (isSelected) {
        	comboField.fld.setForeground(tbl.getSelectionForeground());
        	comboField.fld.setBackground(tbl.getSelectionBackground());
        } else {
        	comboField.fld.setForeground(tbl.getForeground());
        	comboField.fld.setBackground(tbl.getBackground());
        }
    }
    
    /**
     * @see javax.swing.CellEditor#isCellEditable(java.util.EventObject)
     */
/*    public boolean isCellEditable(EventObject anEvent) {
        if (anEvent instanceof MouseEvent) { 
    		return ((MouseEvent)anEvent).getClickCount() >= 2;
   	    }
       return true;
    }
*/
    
    /**
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(final PropertyChangeEvent arg0) {
		this.stopCellEditing();
	}

//	/**
//	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
//	 */
//	public void actionPerformed(ActionEvent e) {
//		this.stopCellEditing();
//		System.out.println("Event --> " + e.toString() + " > " + e.getActionCommand());
//	}


	/**
	 * @return the comboField
	 */
	public AbstractGenericCombo getComboField() {
		return comboField;
	}


	/**
	 * @param newComboField the comboField to set
	 */
	public void setComboField(final AbstractGenericCombo newComboField) {
		if (comboField != null) {
			comboField.removePropertyChangeListener(this);
		}
		comboField = newComboField;
		//comboField.addActionListener(this);
		comboField.addPropertyChangeListener(this);
		comboField.setBorder(null);
	}

	
	protected abstract AbstractGenericCombo getCombo();
	
	private static class TableNotify implements PropertyChangeListener {
		private JTable table;
		private int row, col;
		public TableNotify(JTable table, int row, int col) {
			super();
			this.table = table;
			this.row = row;
			this.col = col;
		}
		
		
		/* (non-Javadoc)
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getPropertyName().equals(AbstractPopup.POPUP_CHANGED)) {
				TableModel mdl = table.getModel();
				table.setValueAt(evt.getNewValue(), row, col);
				if (mdl instanceof AbstractTableModel) {
					((AbstractTableModel) mdl).fireTableDataChanged();
				}
			}
		}
		
		
	}
}
