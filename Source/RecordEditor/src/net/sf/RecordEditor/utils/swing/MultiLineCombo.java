package net.sf.RecordEditor.utils.swing;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.zbluesoftware.java.bm.AbstractGenericCombo;
import com.zbluesoftware.java.bm.AbstractPopup;

/*
 * @Author Bruce Martin
 * Created on 28/05/2007
 *
 * Purpose:
 * create Multiline popup editor
 */

/**
 * create a Date field field with a popup date selector
 *
 * @author Bruce Martin
 *
 */
public class MultiLineCombo extends AbstractGenericCombo {
    
	private JTextPopup popup = new JTextPopup();
    private JTextArea tArea;

    protected void init() {
    	tArea = new JTextArea();
    	fld = tArea;
    	
    	super.init();
    }
     
    /**
     * 
     * @see com.zbluesoftware.java.bm.AbstractGenericCombo#getValue()
     */
    public Object getValue() {
    	return getText();
    }



    
    public void setValue(Object value) {
    	
        setText(toString(value));
    } 


	/**
	 * @see com.zbluesoftware.java.bm.AbstractGenericCombo#getPopup()
	 */
	@Override
	public AbstractPopup getPopup() {
		return popup;
	}
	
 
    /**
     * Display date selector in a Popup menu
     *
     *
     * @author Bruce Martin
     *
     */
    public class JTextPopup extends AbstractPopup {
        private JTextArea area = new JTextArea();
        private boolean isPacked = false;
        //private Thread packThread;

        /**
         * Display date selector in a Popup menu
         *
         */
        public JTextPopup() {
            super();

            
        }

         /**
		 * @see com.zbluesoftware.java.bm.AbstractPopup#getValue()
		 */
		@Override
		public Object getValue() {
			//System.out.println("Getting Value ... " + area.getText());
			return area.getText();
		}

		/**
		 * @see com.zbluesoftware.java.bm.AbstractPopup#setValue(java.lang.Object)
		 */
		@Override
		public void setValue(Object value) {
	        area.setText(toString(value));
	    }

		// public methods
        /**
         * show the JPopupCalendar. Consistent with the original behavior of
         * <tt>show</tt>.
         * @param invoker Component
         * @param x int
         * @param y int
         */
        public void show(Component invoker,
                         int x, int y) {

          // ignore if thread is stuck.
//          if (!isThreadDone()) {
//              return;
//          }

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
            //BasePanel p = new BasePanel();
            int h = 10 + (int) (BasePanel.NORMAL_HEIGHT * 8);
            JScrollPane sp = new JScrollPane(area);
            
//            p.addComponent(1, 3, 
//            		h, BasePanel.GAP, h, 600, 
//            		new JScrollPane(area));
//            sp.setMaximumSize(new Dimension(500, h + 20));
//            this.setMaximumSize(new Dimension(500, h + 20));
//            add(p);
            
            sp.setPreferredSize(new Dimension(750, h));
            add(sp);
            super.pack();
            isPacked = true;
            
            area.addFocusListener(lostFocus);
        }
    }
}
