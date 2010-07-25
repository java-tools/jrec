/*
 * Created on 19/09/2004
 *
 */
package net.sf.RecordEditor.utils.swing;

import java.awt.Component;

import javax.swing.ComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;


/**
 * A keyed ComboModel (has a key value the program uses and
 * a display value the user sees).
 *
 * <p>i.e. the ComboBox has 2 values:
 * <ol compact>
 *   <li>Key value that the program see's but the user does not
 *   <li>Display value the user see's but the program does not.
 *  </ol>
 *
 * @author Bruce Martin
 *
 */
public class BmKeyedComboModel implements ComboBoxModel, ListCellRenderer {

	//public boolean trace=false;

	private JLabel display = new JLabel();

	private AbstractRowList list;

	private int currIdx = 0;

	private EventListenerList listenerList = new EventListenerList();


	/**
	 * Create Model from List of Records
	 *
	 * @param rowList list of Record (from a DB)
	 */
	public BmKeyedComboModel(final AbstractRowList rowList) {
		super();

		this.list = rowList;
		display.setOpaque(true);
	}



	/**
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	public Object getElementAt(int idx) {
		//System.out.println(" $$$ " + idx + " " + list.getElementAt(idx));
		return list.getElementAt(idx);
	}


	/**
	 * @see javax.swing.DefaultComboBoxModel#getIndexOf(java.lang.Object)
	 */
	public int getIndexOf(Object arg0) {
		return list.getIndexOf(arg0);
	}


	/**
	 * @see javax.swing.ComboBoxModel#setSelectedItem(java.lang.Object)
	 */
	public void setSelectedItem(Object arg0) {

		currIdx = getIndexOf(arg0);

	    tellOfUpdates();
	}



	/**
	 * @see javax.swing.ComboBoxModel#getSelectedItem()
	 */
	public Object getSelectedItem() {
		return list.getElementAt(currIdx);
	}



	/**
	 * @see javax.swing.ListModel#getSize()
	 */
	public int getSize() {
		//System.out.println(" $$$  size " +  list.getSize());
		return list.getSize();
	}


	/**
	 * @see javax.swing.ListModel#addListDataListener(javax.swing.event.ListDataListener)
	 */
	public void addListDataListener(ListDataListener l) {

	    listenerList.add(ListDataListener.class, l);
	}



	/**
	 * @see javax.swing.ListModel#removeListDataListener(javax.swing.event.ListDataListener)
	 */
	public void removeListDataListener(ListDataListener l) {
		listenerList.remove(ListDataListener.class, l);
	}


	/**
	 * Tell all listners of updates
	 */
	private void tellOfUpdates() {

		Object[] listeners = listenerList.getListenerList();

		if (listeners.length > 0) {
			ListDataEvent le = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, currIdx);

			for (int i = listeners.length - 1; i >= 0; i -= 1) {
				if (listeners[i] == ListDataListener.class) {
					((ListDataListener) listeners[i + 1]).contentsChanged(le);
				}
            }
        }
	}


	/**
	 * @return Returns the sorted.
	 */
	public boolean isSorted() {
		return list.isSorted();
	}


	/**
	 * @see javax.swing.ListCellRenderer
	 * #getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	public Component getListCellRendererComponent(JList jlist, Object obj,
			int index, boolean isSelected, boolean focus) {


		if ((obj == null)) {
			display.setText(" ");
		} else {
			Object rec = list.getFieldFromKey(obj);
			//System.out.println(" $$$ Get Render: " + obj + " " + rec);

			display.setText(rec.toString());
		}

	    if (isSelected) {
	    	display.setForeground(jlist.getSelectionForeground());
	    	display.setBackground(jlist.getSelectionBackground());
        } else {
        	display.setForeground(jlist.getForeground());
        	display.setBackground(jlist.getBackground());
        }

		return display;
	}


	/**
	 * Set the current index
	 *
	 * @see idx new current index
	 */
	public void setIndex(int idx) {
		currIdx = idx;
	}


	/**
	 * Force a reload of the DB List
	 *
	 */
	public void reload() {
		list.reload();
	}


	/**
	 * @return Returns the list.
	 */
	public AbstractRowList getList() {
		return list;
	}
}
