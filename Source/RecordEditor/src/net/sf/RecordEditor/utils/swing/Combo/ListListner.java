package net.sf.RecordEditor.utils.swing.Combo;

import javax.swing.event.EventListenerList;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Abstract Class to serve as a base of Lists and ComboBox models
 * 
 * @author Bruce Martin
 *
 */
public abstract class ListListner {

	private EventListenerList listenerList = new EventListenerList();

	/**
	 * @see javax.swing.ListModel#addListDataListener(javax.swing.event.ListDataListener)
	 */
	public final void addListDataListener(ListDataListener l) {

	    listenerList.add(ListDataListener.class, l);
	}



	/**
	 * @see javax.swing.ListModel#removeListDataListener(javax.swing.event.ListDataListener)
	 */
	public final void removeListDataListener(ListDataListener l) {
		listenerList.remove(ListDataListener.class, l);
	}


	/**
	 * Tell all listners of updates
	 */
	public final void tellOfUpdates(int currIdx) {

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
}
