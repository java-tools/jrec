/*
 * @Author Bruce Martin
 * Created on 11/08/2005
 *
 * Purpose: A Panel with built in Help screens
 */
package net.sf.RecordEditor.utils.swing;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import net.sf.RecordEditor.utils.common.Common;


/**
 * This class will display a Help Screen when the user hits the
 * PF1 key.
 *
 * @author Bruce Martin
 *
 */
public class HtmlWindow implements HyperlinkListener {

    public final JFrame frame;

//    private JTextArea ta = new JTextArea();
    private JEditorPane htmlpDtls = new JEditorPane();
    private boolean toInit = true;
    


    /**
     * define a help window adapter
     *
     * @param url Help URL
     * @throws MalformedURLException 
     */
    public HtmlWindow(final String url, String title) throws MalformedURLException {
        this(new File(url).toURI().toURL(), title);
    }

    /**
     * define a help window adapter
     *
     * @param url Help URL
	 * @param title Screen title
     */
    public HtmlWindow(final URL url, String title) {
        super();

        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        showURL(url);
    }


    /**
     * Shows the help screen
     *
     * @param url url to display
     */
    public final void showURL(URL url) {

        if (url != null) {
            if (toInit) {
 //           	BasePanel bp = new BasePanel();
 //               htmlpDtls.setEditable(false);
                
//                bp.addComponent(1, 5, BasePanel.PREFERRED, BasePanel.GAP, BasePanel.FULL, BasePanel.FULL,  htmlpDtls);
//                frame.getContentPane().add(bp);
//            	if (Common.TEST_MODE) {
//            		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(htmlpDtls), new JScrollPane(ta));
//            		splitPane.setDividerLocation(0.50);
//					frame.add(splitPane);
//            	} else {
            		frame.add(new JScrollPane(htmlpDtls));
//            	}
                htmlpDtls.addHyperlinkListener(this);
                Common.setMaximised(frame, Common.getUsableScreenArea(frame));
                toInit = false;
            }

            try {
                htmlpDtls.setPage(url);
//                if (Common.TEST_MODE) {
//                	ta.setText(readFile(url.getFile()));
//                }
                frame.pack();
                frame.setVisible(true);
            } catch (Exception e) {
                System.out.println("Error Loading Help Screen "
                        + url + ": " + e.getMessage());
            }
        }
    }
    
//    private String readFile(String fName) {
//    	StringBuffer b = new StringBuffer();
//    	String s;
//    	
//    	try {
//			BufferedReader r = new BufferedReader( new FileReader(fName));
//			while ((s = r.readLine()) != null) {
//				b.append(s).append('\n');
//			}
//			r.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//    	return b.toString();
//    }


    /**
     * @see javax.swing.event.HyperlinkListher#hyperlinkUpdate
     */
    public final void hyperlinkUpdate(HyperlinkEvent event) {

        if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
             showURL(event.getURL());
        }
    }
}
