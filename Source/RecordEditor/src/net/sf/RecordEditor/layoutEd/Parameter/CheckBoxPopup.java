package net.sf.RecordEditor.layoutEd.Parameter;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

import com.zbluesoftware.java.bm.AbstractPopup;

import net.sf.JRecord.CsvParser.BasicParser;
import net.sf.RecordEditor.layoutEd.utils.LayoutCommon;
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
public class CheckBoxPopup extends AbstractPopup  {
    //private static final int FIELD_WIDTH = 20;
     
    private JTextField trueValue   = new JTextField();
    private JTextField falseValue  = new JTextField();
    private JTextField defaltValue = new JTextField();
    private JCheckBox  caseSensitive = new JCheckBox();
    
    private BasicParser parser = BasicParser.getInstance();
    
    private boolean isPacked = false;
    
    private BasePanel pnl = null;
 

    /**
     * created a Date field field with a popup date selector
     * @param dateFormat format of the dates
     */
    public CheckBoxPopup() {
    	super();
    	
    	trueValue.addFocusListener(lostFocus);
    	falseValue.addFocusListener(lostFocus);
    	defaltValue.addFocusListener(lostFocus);
    	caseSensitive.addFocusListener(lostFocus);
    }
    
    public Object getValue() {
    	String cs = "Y";
    	if (! caseSensitive.isSelected()) {
    		cs = "N";
    	}
    	
   	    String[] fields = new String[]{trueValue.getText(), falseValue.getText(), 
   	    		defaltValue.getText(), cs};
    
	    return LayoutCommon.buildCsvField(fields);
   }
    
    /**
     * Set the Text value 
     * @param text new text value
     */
	public void setValue(Object value) {
		String text = toString(value);
		
		if (text.length() > 0) {
			String delim = text.substring(0, 1);
			String line = text.substring(1);
		
			trueValue.setText(parser.getField(0, line, delim, ""));
			falseValue.setText(parser.getField(1, line, delim, ""));
			defaltValue.setText(parser.getField(2, line, delim, ""));
			caseSensitive.setSelected("Y".equalsIgnoreCase(parser.getField(3, line, delim, "")));
		}
	}





                   
        //private Thread packThread;

  
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
            //datePane.pack();
        	
        	if (pnl == null) {
        		pnl = new BasePanel();
        		
        		pnl.addComponent("True Value", trueValue);
        		pnl.addComponent("False Value", falseValue);
        		
        		pnl.setGap(BasePanel.GAP1);
        		pnl.addComponent("Default Value", defaltValue);
        		pnl.addComponent("Case Sensitive", caseSensitive);
        	}
            add(pnl);
            super.pack();
            isPacked = true;
        }
}