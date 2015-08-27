/*
 * @Author Bruce Martin
 * Created on 11/08/2005
 *
 * Purpose: A Panel with built in Help screens
 */
package net.sf.RecordEditor.utils.swing;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;


/**
 * This class will display a Help Screen when the user hits the
 * PF1 key.
 *
 * @author Bruce Martin
 *
 */
public class HelpWindow extends KeyAdapter implements HyperlinkListener {

    public static final JFrame HELP_FRAME = new JFrame("Help");

    private static JEditorPane helpDtls = new JEditorPane();
    private static boolean toInit = true;

    private URL helpURL = null;



    /**
     * define a help window adapter
     *
     * @param url Help URL
     */
    public HelpWindow(final URL url) {
        super();

        helpURL = url;
    }


    /**
     * @see java.awt.event.KeyAdapter#keyReleased
     */
    public final void keyReleased(KeyEvent event) {
         if (event.getKeyCode() == KeyEvent.VK_F1) {
            showURL(helpURL);
        }
    }


    /**
     * Shows the help screen
     *
     */
    public final void showHelp() {

        showURL(helpURL);
     }


    /**
     * Shows the help screen
     *
     * @param url url to display
     */
    public final void showURL(URL url) {

        if (url != null) {
            if (toInit) {
                helpDtls.setEditable(false);
                HELP_FRAME.getContentPane().add(new JScrollPane(helpDtls));
                helpDtls.addHyperlinkListener(this);
                toInit = false;
            }

            try {
                helpDtls.setPage(url);
                HELP_FRAME.setVisible(true);
            } catch (Exception e) {
                System.out.println("Error Loading Help Screen "
                        + url + ": " + e.getMessage());
            }
        }
    }

    /**
     * Define the Help URL
     *
     * @param helpUrl name of the Help URL
     */
    public final void setHelpURL(URL helpUrl) {
        this.helpURL = helpUrl;
    }


    /**
     * @see javax.swing.event.HyperlinkListher#hyperlinkUpdate
     */
    public final void hyperlinkUpdate(HyperlinkEvent event) {

        if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
             showURL(event.getURL());
        }
    }
}
