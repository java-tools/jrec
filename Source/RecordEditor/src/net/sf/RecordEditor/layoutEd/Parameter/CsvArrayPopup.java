package net.sf.RecordEditor.layoutEd.Parameter;
import java.awt.Component;

import javax.swing.JTextField;

import com.zbluesoftware.java.bm.AbstractPopup;

import net.sf.JRecord.CsvParser.BasicParser;
import net.sf.RecordEditor.re.util.LayoutCommon;
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
public class CsvArrayPopup extends AbstractPopup  {
    //private static final int FIELD_WIDTH = 20;
     
    private JTextField quote = new JTextField(5);
    private JTextField separator1 = new JTextField();
    private JTextField separator2 = new JTextField();
    
    private BasicParser parser = BasicParser.getInstance();
    
    private boolean isPacked = false;
    
    private BasePanel pnl = null;
    
//    FocusAdapter lostFocus = new FocusAdapter() {
//    	public void focusLost(FocusEvent e) {
//    		//Object s = getValue();
//    		//System.out.println("~~~ " + s + " > " + separator1.getText() + " > " + separator2.getText());
//     	    CsvArrayPopup.this.firePropertyChange(AbstractPopup.POPUP_CHANGED, null, getValue());
//     	
//       	}
//    };


    /**
     * created a Date field field with a popup date selector
     * @param dateFormat format of the dates
     */
    public CsvArrayPopup() {
    	super();
    	
    	quote.addFocusListener(lostFocus);
    	separator1.addFocusListener(lostFocus);
    	separator2.addFocusListener(lostFocus);
    	//this.addP
    }
    
    public Object getValue() {
   	    String[] fields;
	    String s = separator2.getText();
	    if (s == null || "".equals(s)) {
	    	fields = new String[2];
	    } else {
	    	fields = new String[3];
	    	fields[2] = s;
	    }
	    
	    fields[0] = quote.getText();
	    fields[1] = separator1.getText();
	    
	    return LayoutCommon.buildCsvField(fields);
   }
    
    /**
     * Set the Text value 
     * @param text new text value
     */
	public void setValue(Object value) {
		String text = "|";
		if (value != null) {
			text = value.toString();
		}
		
		if (text.length() > 0) {
			String delim = text.substring(0, 1);
			String line = text.substring(1);
		
			quote.setText(parser.getField(0, line, delim, ""));
			separator1.setText(parser.getField(1, line, delim, ""));
			separator2.setText(parser.getField(2, line, delim, ""));
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
        		
        		pnl.addLine("Quote", quote);
        		pnl.setGap(BasePanel.GAP1);
        		pnl.addLine("Seperator 1", separator1);
        		pnl.setGap(BasePanel.GAP1);
        		pnl.addLine("Seperator 2", separator2);
        	}
            add(pnl);
            super.pack();
            isPacked = true;
        }
}
