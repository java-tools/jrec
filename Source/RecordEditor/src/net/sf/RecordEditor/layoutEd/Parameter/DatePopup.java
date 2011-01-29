package net.sf.RecordEditor.layoutEd.Parameter;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
//import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.zbluesoftware.java.bm.AbstractPopup;

import net.sf.RecordEditor.editProperties.CommonCode;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.BasePanel;


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
public class DatePopup extends AbstractPopup implements ActionListener {
     
    private JEditorPane tips = new JEditorPane("text/html", Common.DATE_FORMAT_DESCRIPTION);
    private JTextField dateFormat = new JTextField();

    private JTextField message = new JTextField();
    private JButton test = new JButton("Test");

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
				message.setText("25.Dec.98 will be formatted as " + sd.format(xmas));
			} catch (Exception e) {
				message.setText("Date Format Error: " + e.getMessage());
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

			pnl.addComponent(1, 5, CommonCode.TIP_HEIGHT, BasePanel.GAP2,
					BasePanel.FULL, BasePanel.FULL,
					new JScrollPane(tips));


			pnl.addLine("Date Format", dateFormat, test);
			pnl.setGap(BasePanel.GAP2);
			pnl.addMessage(message);
		}
		add(pnl);
		super.pack();
		isPacked = true;
	}
}
