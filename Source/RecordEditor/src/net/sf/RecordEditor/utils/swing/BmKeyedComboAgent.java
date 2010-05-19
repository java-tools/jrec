/*
 * Created on 12/09/2004
 *
 */
package net.sf.RecordEditor.utils.swing;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.table.TableCellRenderer;




/**
 * This class implements a ComboBox Agent to allow user
 * to partially enter an item in a Combo Box
 *
 * @author Bruce Martin
 */
public class BmKeyedComboAgent extends AbsComboAgent implements TableCellRenderer {

	private boolean cellRender = false;

	private AbstractRowList list;
	private BmKeyedComboModel mdl = null;

	private ComboEditor cbEditor;


	/**
	 * This class implements a ComboBox Agent to allow user
	 * to partially enter an item in a Combo Box
	 *
	 * @param comboBox ComboBox that the user will partially enter
	 *                 values for
	 * @param rowList List of rows displayed in a combo boxs drop down
	 * @param sorted wether the items are sorted or not.
	 */
	public BmKeyedComboAgent(final JComboBox comboBox,
						     final AbstractRowList rowList,
						     final boolean sorted) {

		super();
		this.list = rowList;

		try {
			mdl = (BmKeyedComboModel) comboBox.getModel();
		} catch (Exception ex) {
			mdl = null;
		}

		cbEditor = new ComboEditor();
		comboBox.setEditor(cbEditor);

		init(comboBox, sorted);
	}


	/**
	 * @see AbsComboAgent#setRow
	 */
	protected void setRow(int row) {

		if (! cellRender) {
			combo.setSelectedIndex(row);
		} else if (mdl != null) {
			mdl.setIndex(row);
		}

		cbEditor.val = list.getElementAt(row);
	}


	/**
	 * @see AbsComboAgent#getRow
	 */
	protected String getRow(int row) {
		Object obj = list.getFieldAt(row);

		if (obj == null) {
		    return null;
		}
		return obj.toString();
	}


	/**
	 * @see TableCellRenderer#getTableCellRendererComponent
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        if (isSelected) {
        	textFld.setForeground(table.getSelectionForeground());
            textFld.setBackground(table.getSelectionBackground());
        } else {
        	textFld.setForeground(table.getForeground());
            textFld.setBackground(table.getBackground());
        }

        return textFld;
    }


	/**
	 * Set the value displayed in the Text Field part of the Combo box
	 * @param text Text to assign to the ComboBox
	 */
	protected void setTextFld(String text) {
		textFld.setText(text);
	}


	/**
	 * @param newCellRender The cellRender to set.
	 */
	public void setCellRender(boolean newCellRender) {
		this.cellRender = newCellRender;
	}


	/**
	 *  To allow the editing of Combo consisting of
	 *   Key (non display)
	 *   Display  Displayed
	 *
	 * @author Bruce Martin
	 *
	 */
	private class ComboEditor extends BasicComboBoxEditor {

		private Object val;

		/**
		 *  To allow the editing of Combo consisting of
		 *   Key (non display)
		 *   Display  Displayed
		 */
		public ComboEditor() {
			super();

			textFld = (JTextField) getEditorComponent();
		}

		/**
		 * @see javax.swing.ComboBoxEditor#getItem()
		 */
		public Object getItem() {
			return val;
		}

		/**
		 * @see javax.swing.ComboBoxEditor#setItem(java.lang.Object)
		 */
		public void setItem(Object obj) {

			val = obj;
			String s = "";

			if (obj != null) {
			    s = list.getFieldFromKey(obj).toString();
			}

			textFld.setText(s);
		}
	}

}


