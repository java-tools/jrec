/**
 * 
 */
package com.zbluesoftware.java.bm;

//import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JPopupMenu;

/**
 * @author Bruce Martin
 *
 */
public abstract class AbstractPopup extends JPopupMenu {
	
	public static final String POPUP_CHANGED = "popupChanged"; 

    protected final FocusAdapter lostFocus = new FocusAdapter() {
    	public void focusLost(FocusEvent e) {
    		//Object s = getValue();
    		//System.out.println("~~~ Focus lost ... " + getValue());
    		AbstractPopup.this.firePropertyChange(AbstractPopup.POPUP_CHANGED, null, getValue());
       	}
    };

	public abstract void setValue(Object value);
	
	public abstract Object getValue();
	
	/**
	 * convert object to string
	 * @param o object
	 * @return equivalent string value
	 */
	public final static String toString(Object o) {
		if (o == null) {
			return "";
		}
		return o.toString();
	}
}
