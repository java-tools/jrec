package net.sf.RecordEditor.layoutEd.Parameter;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.zbluesoftware.java.bm.AbstractPopup;

/*
 * @Author Bruce Martin
 * Created on 9/02/2007
 *
 * Purpose:
 * create a Date field field with a popup date selector
 */

/**
 * create a Date field field with a popup date selector
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class ComboPopup extends AbstractPopup  {

	private ComboSearch search;
	private boolean isPacked = false;
	//private final int dbId;

    /**
     * created a Date field field with a popup date selector
     * @param databaseId database index
     */
    public ComboPopup(final int databaseId) {
    	super();
    	
    	search = new ComboSearch(databaseId, false);
    	
    	search.getComboList().addMouseListener(new MouseAdapter() {
			/**
			 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
			 */
			@Override
			public void mouseReleased(MouseEvent arg0) {
				ComboPopup.this.firePropertyChange(AbstractPopup.POPUP_CHANGED, null, getValue());
				super.mouseReleased(arg0);
				
				if (arg0.getClickCount() == 2) {
					ComboPopup.this.setVisible(false);
				}
			}
		});

    	//quote.addFocusListener(lostFocus);
    	//separator1.addFocusListener(lostFocus);
    	//separator2.addFocusListener(lostFocus);
    	//this.addP
    }
    
    public Object getValue() {

	    return search.getSelectedCombo();
   }
    
    /**
     * Set the Text value 
     * @param text new text value
     */
	public void setValue(Object value) {
		search.setSelectedCombo(toString(value));
	}





                   
	/**
	 * show the JPopupCalendar. Consistent with the original behavior of
	 * <tt>show</tt>.
	 * @param invoker Component
	 * @param x int
	 * @param y int
	 */
	public void show(Component invoker,
			int x, int y) {

		if (! isPacked) {
			pack();
		}

		super.show(invoker, x, y);
	}


	/**
	 * Create an instance of DateChooser before <tt>show</tt> is called and
	 * can make the popup window pop up a bit quicker. Internally called by
	 * <tt>show</tt>.
	 */
	public void pack() {
		//datePane.pack();

		add(search);
		super.pack();
		isPacked = true;
	}
}
