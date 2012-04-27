package com.zbluesoftware.java.bm;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
//import java.util.Calendar;
import java.util.Date;

import com.zbluesoftware.java.swing.ZCalendar;

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
public class ZDateField extends AbstractGenericCombo implements ActionListener {
    //private static final int FIELD_WIDTH = 20;
    SimpleDateFormat df;
    final String dateFormatStr;
    
    JPopupCalendar popup = new JPopupCalendar();

    /**
     * created a Date field field with a popup date selector
     * @param dateFormat format of the dates
     */
    public ZDateField(final String dateFormat) {
    	super();
    	
        dateFormatStr = dateFormat;
 
        df = new SimpleDateFormat(dateFormat);
    }
    
    /**
     * 
     * @see com.zbluesoftware.java.bm.AbstractGenericCombo#getValue()
     */
    public Object getValue() {
    	return getDate();
    }


    /**
     * Get the date value of the field
     * @return value as date
     */
    public Date getDate() {
        Date ret = null;
        try {
            ret = df.parse(padZeros(dateFormatStr, getField().getText()));
        } catch (final Exception ex) {
        }

        return ret;
    }

    
    public void setValue(final Object value) {
    	
        if (value instanceof Date) {
            setDate((Date) value);
        } else if (value == null) {
            setText("");
        } else {
            setText(value.toString());
        }

    }
 
    /**
     * Set fields value
     * @param date date to set
     */
    public void setDate(final Date date) {
        String s = "";
        if (date != null) {
            s = df.format(date);
        }
        getField().setText(s);
    }

    /**
     * pad string with zero's to format length
     *
     * @param dateFormatStr date format string
     * @param s string to be padded
     *
     * @return padded string
     */
    public static String padZeros(final String dateFormatStr, final String s) {

        String ret = s;
        if (dateFormatStr != null && s.length() < dateFormatStr.length()
        && (dateFormatStr.startsWith("y") || dateFormatStr.startsWith("M")
        || dateFormatStr.startsWith("d"))) {
            ret = "00000000".substring(0, Math.min(8, dateFormatStr.length() - s.length()))
                + s;
        }
        return ret;
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
    public class JPopupCalendar extends AbstractPopup {
        private final ZCalendar dateSelector = new ZCalendar();
        private boolean isPacked = false;
        //private Thread packThread;

        /**
         * Display date selector in a Popup menu
         *
         */
        public JPopupCalendar() {
            super();

            dateSelector.addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(final PropertyChangeEvent event) {
                    if (event.getPropertyName().equals("SelectedDay")) {
                        JPopupCalendar.this.setVisible(false);

                        final Date d = (Date) event.getNewValue();
                        dateSelector.setCurrentDate(d);
                        if (d != null) {
                        	getField().setText(df.format(d));
                        }
                    }
                }
            });
        }

         /**
		 * @see com.zbluesoftware.java.bm.AbstractPopup#getValue()
		 */
		@Override
		public Object getValue() {
			return dateSelector.getCurrentDate();
		}

		/**
		 * @see com.zbluesoftware.java.bm.AbstractPopup#setValue(java.lang.Object)
		 */
		@Override
		public void setValue(final Object value) {
	        try {
	            popup.setCurrentDate(df.parse(padZeros(dateFormatStr, getField().getText())));
	        } catch (final Exception ex) {
	            System.out.println("Error Parsing date ))" + getField().getText() + "((");
	        }
		}

		// public methods
        /**
         * show the JPopupCalendar. Consistent with the original behavior of
         * <tt>show</tt>.
         * @param invoker Component
         * @param x int
         * @param y int
         */
        public void show(final Component invoker,
                         final int x, final int y) {

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
            //datePane.pack();
            add(dateSelector);
            super.pack();
            isPacked = true;
        }




        /**
         * Returns the selected date. If now date has been selected and
         * no Calendar instance has been registered with JPopupCalendar,
         * <tt>getDate</tt> can return null.
         * @return Calendar
         */
//        public Date getCurrentDate() {
//          return dateSelector.getCurrentDate();
//        }


        /**
         * Set the startup date of the popup calendar.
         * @param newDate Calendar
         */
        public void setCurrentDate(final Date newDate) {
          dateSelector.setCurrentDate(newDate);
        }


//        /**
//         * Check if the packing thread has completed.
//         * @return boolean
//         */
//        private boolean isThreadDone() {
//          if (packThread != null) {
//            try {
//              packThread.join();
//              return true;
//            } catch (Exception e) {
//              System.err.println("Can't join pack thread.");
//              return false;
//            }
//          }
//          return true;
//        }
    }
}
