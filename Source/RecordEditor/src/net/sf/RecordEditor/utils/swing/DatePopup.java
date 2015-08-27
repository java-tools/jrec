package net.sf.RecordEditor.utils.swing;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JTextField;

import net.sf.RecordEditor.utils.common.Common;

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
public class DatePopup extends AbstractPopup implements ActionListener {
	public static final int TIP_HEIGHT = SwingUtils.STANDARD_FONT_HEIGHT * 11;
    private JEditorPane tips = new JEditorPane("text/html", Common.DATE_FORMAT_DESCRIPTION);
    private JTextField dateFormat = new JTextField();

    //private JTextField message = new JTextField();
    private JButton test = SwingUtils.newButton("Test");

    private boolean isPacked = false;

    private BasePanel pnl = null;

    private static final Calendar XMAS_CAL = new GregorianCalendar(1998, Calendar.DECEMBER, 25);
    private static Date xmas = XMAS_CAL.getTime();



    /**
     * created a Date field field with a popup date selector
     * @param dateFormat format of the dates
     */
    public DatePopup() {
    	super();

    	dateFormat.addFocusListener(lostFocus);
    	test.addActionListener(this);
    	tips.setCaretPosition(0);
     	//this.addP
    }

    public Object getValue() {

	    return dateFormat.getText();
   }

    /**
     * Set the Text value
     * @param text new text value
     */
	public void setValue(Object value) {
		dateFormat.setText(toString(value));
	}






	//private Thread packThread;


	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {

		String format = dateFormat.getText();
		if (format != null) {
			try {
//				System.out.println("~~ " + format);
				SimpleDateFormat sd = new SimpleDateFormat(format);
				pnl.setMessageTxtRE("25.Dec.98 will be formatted as", sd.format(xmas));
				this.firePropertyChange(AbstractPopup.POPUP_CHANGED, null, format);
			} catch (Exception e) {
				pnl.setMessageTxtRE("Date Format Error: " + e.getMessage());
			}
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
	public void show(Component invoker,
			int x, int y) {

		// ignore if thread is stuck.
//		if (!isThreadDone()) {
//		return;
//		}

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

		if (pnl == null) {
			pnl = new BasePanel();

			pnl.addComponentRE(1, 5, TIP_HEIGHT, BasePanel.GAP2,
					BasePanel.FULL, BasePanel.FULL,
					tips);


			pnl.addLineRE("Date Format", dateFormat, test);
			pnl.setGapRE(BasePanel.GAP2);
			pnl.addMessageRE();
		}
		add(pnl);
		super.pack();
		isPacked = true;
	}
}
