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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.JTextComponent;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.common.UpdatableItem;

import com.zbluesoftware.java.bm.ArrowButton;

/**
 * create a Date field field with a popup date selector
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class ArrayRender extends JPanel implements ActionListener, TableCellRenderer, UpdatableItem {

    private static final int FIELD_WIDTH = 20;
    private JTextComponent fld = new JTextField();
    private JButton btn = new ArrowButton(ArrowButton.SOUTH);


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
     * Create Array field
      */
	public ArrayRender(boolean multiline, boolean editable) {

        if (multiline) {
        	fld = new JTextArea();
        }
        fld.setEditable(editable);
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


    public Object getArrayValue() {
    	if (initialValue.equals(fld.getText())) {
    		return arrayDtls.getReturn();
    	}
    	return fld.getText();
    }




	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.common.UpdatableItem#getValue()
	 */
	@Override
	public Object getValue() {
		fld.setText(arrayDtls.toString());
		return arrayDtls.getReturn();
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.common.UpdatableItem#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(Object value) {
		if (value instanceof ArrayInterface) {
			this.arrayDtls = (ArrayInterface) value;
			fld.setText(arrayDtls.toString());
		}
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
}
