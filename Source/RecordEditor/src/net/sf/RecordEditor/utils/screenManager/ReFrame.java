/*
 * @Author Bruce Martin
 * Created on 4/01/2007 for RecordEditor Version 0.56
 *
 * Purpose:
 * This class forms the basis of all record editor frames.
 * It will keep track of the active frame and notify the
 * appropriate classes (ReMainFrame) of new Frames via alistner
 */
package net.sf.RecordEditor.utils.screenManager;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;

/**
 * This class forms the basis of all record editor frames
 * It will keep track of the active frame and notify the
 * appropriate classes (ReMainFrame) of new Frames via alistner
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class ReFrame extends JInternalFrame
				  implements ReActionHandler  {

//    private static ReMainFrame masterFrame = null;
    private static JDesktopPane desktop = null;
    
    private static ReFrame activeFrame = null;
    private static ReWindowChanged focusChangedListner;
    private static ArrayList<ReFrame> allFrames = new ArrayList<ReFrame>();

//    private ReFrame me = this;
    private String documentName = "";
    private String frameId = "";
    private Object document;
    private boolean primaryView = false;

    private static ArrayList<ReFrame> activeHistory = new ArrayList<ReFrame>();



    /**
     * standard record editor internal fram
     *
     * @param docName Name of the document being editted
     */
    public ReFrame(final String docName) {
        super();

        documentName = docName;

        init();
    }

    /**
     * standard record editor internal frame
     *
     * @param docName Name of the document being edited
     */
    public ReFrame(final String docName, String screenName) {
        super(screenName);

        documentName = docName;

        init();
    }



    /**
     * standard record editor internal frame
     *
     * @param docName Name of the document being edited
     * @param name name of the frame
     * @param doc data document
     */
    public ReFrame(final String docName, final String name, final Object doc) {
        super(name + " - " + docName, true, true, true, true);

        documentName = docName;
        frameId = name;
        document = doc;

        init();
    }


    /**
     * Standard initialise
     *
     */
    private void init() {

        InternalFrameListener listener = new InternalFrameListener() {
            public void internalFrameActivated(InternalFrameEvent e) {
            	setActiveFrame(ReFrame.this);

                if (focusChangedListner != null) {
                    focusChangedListner.focusChanged(ReFrame.this);
                }
            }

            public void internalFrameClosed(InternalFrameEvent e) {

                allFrames.remove(ReFrame.this);
                focusLost();
                if (focusChangedListner != null) {
                    focusChangedListner.deleteWindow(ReFrame.this);
                    
                    //System.out.println("Frame Closing 1 ... " + activeHistory.size()
                    //		+ " " + (activeHistory.get(activeHistory.size() - 1) == ReFrame.this));
                    //System.out.println("Frame Closing 2 ... " + activeHistory.get(activeHistory.size() - 1)
                    //		+ " >> " + ReFrame.this);
                    if (activeHistory.size() > 1
                    && activeHistory.get(activeHistory.size() - 1) == ReFrame.this) {
                    	ReFrame nf = activeHistory.get(activeHistory.size() - 2);
                    	setActiveFrame(nf);
                    	
                    	focusChangedListner.focusChanged(nf);
                    }
                }
                activeHistory.remove(ReFrame.this);
                windowClosing();
            }

            public void internalFrameClosing(InternalFrameEvent e) {
            }

            public void internalFrameDeactivated(InternalFrameEvent e) {
                focusLost();
            }

            public void internalFrameDeiconified(InternalFrameEvent e) {
            }

            public void internalFrameIconified(InternalFrameEvent e) {
                focusLost();
            }

            public void internalFrameOpened(InternalFrameEvent e) {
            }

//            private void dumpInfo(String s, InternalFrameEvent e) {
//                System.out.println("Source: " + e.getInternalFrame().getName()
//                        + " : " + s);
//            }
        };

        allFrames.add(this);
        this.addInternalFrameListener(listener);

        if (desktop != null) {
            desktop.add(this);
        }
   }

    public void reClose() {
    	doDefaultCloseAction();
    }

	/**
	 * Window closing actions
	 */
	public void windowClosing() {
	    //this.dispose();
	}


    /**
     * update fields when status has been lost
     *
     */
    private void focusLost() {
    	
        if (activeFrame == this) {
        	setActiveFrame(null);
        }
    }
    
	/**
	 * Execute standard RecordEditor action with an option
	 * @param action action to perform
	 * @param o option supplied
	 */
	public void executeAction(int action, Object o) {
		
		executeAction(action);
	}
	
	

    /**
     * Execute a form action
     *
     * @param action action to be performed
     */
    public void executeAction(int action) {

     }

    /**
     * check if a form action is available
     *
     * @param action action to be checked
     *
     * @return wether action is available
     */
    public boolean isActionAvailable(int action) {

        return false;
    }


    /**
     * gets the currently active frame
     *
     * @return active frame
     */
    public static ReFrame getActiveFrame() {
       return activeFrame;
    }


    /**
     * adds a focus changed listner
     * @param focusChanged The focusChangedNotification to set.
     */
    public static void addFocusChangedListner(
            ReWindowChanged focusChanged) {
        focusChangedListner = focusChanged;
    }

    /**
     * @return Returns the documentName.
     */
    public String getDocumentName() {
        return documentName;
    }

    public static void closeAllFrames() {
    	for (int i = 0; i < allFrames.size(); i++) {
            allFrames.get(i).reClose();
        }
    }

    /**
     * @return Returns the allFrames.
     */
    public static ReFrame[] getAllFrames() {
        ReFrame[] ret = new ReFrame[allFrames.size()];

        for (int i = 0; i < ret.length; i++) {
            ret[i] = allFrames.get(i);
        }
        return ret;
    }

    /**
     * @return Returns the primaryView.
     */
    public boolean isPrimaryView() {
        return primaryView;
    }

    /**
     * @param newPrimaryView The primaryView to set.
     */
    public void setPrimaryView(boolean newPrimaryView) {
        this.primaryView = newPrimaryView;
    }


    /**
     * @return Returns the frameId.
     */
    public String getFrameId() {
        return frameId;
    }


    /**
     * @see java.awt.Component#setVisible(boolean)
     */
    public void setVisible(boolean visible) {

        updateWindowStatus(visible);
        super.setVisible(visible);
    }

    /**
     * Display the frame
     */
    public void show() {
        updateWindowStatus(true);
        super.show();
    }


    /**
     * Update the Window list
     * @param visible wether to make the frame vissable
     */
    private void updateWindowStatus(boolean visible) {

        if (focusChangedListner != null) {
            if (visible) {
                focusChangedListner.newWindow(this);
            } else {
                focusChangedListner.deleteWindow(this);
            }
        }
    }


    /**
     * @return Returns the document.
     */
    public Object getDocument() {
        return document;
    }

    
    /**
     * Add main component panel
     * @param panel panel to add
     */
    public void addMainComponent(BaseHelpPanel panel) {
        getContentPane().add(panel);

		pack();
		setResizable (true);

		panel.registerOneComponent(this);
		panel.registerOneComponent(getContentPane());
    }


    /**
     * Add main component panel
     * @param panel panel to add
     */
    public void addMainComponent(JComponent panel) {

        getContentPane().add(panel);

		pack();
		setResizable(true);
    }
    
    
    /**
     * Change active frame
     * @param newActiveFrame
     */
    public static void setActiveFrame(ReFrame newActiveFrame) {
    	
    	if (newActiveFrame != activeFrame) {
	       	if (activeFrame != null) {
	    		try {
	    			activeFrame.setSelected(false);
	    		} catch (Exception ex) {
				}
	    	}
	       	
	       	if (newActiveFrame != null) {
	       		newActiveFrame.moveToFront();
	       		newActiveFrame.requestFocus(true);
		        try {
		        	newActiveFrame.setSelected(true);
		        } catch (Exception ex) { 
		        }   	
	            activeHistory.remove(newActiveFrame);
	            activeHistory.add(newActiveFrame);
	       	}
	        activeFrame = newActiveFrame;
    	}
    }


    /**
     * Set the desktop being used to display the ReFrames
     * @param newDesktop The desktop to set.
     */
    public static void setDesktop(JDesktopPane newDesktop) {
        ReFrame.desktop = newDesktop;
    }

	/**
	 * @return the desktopHeight
	 */
	public static int getDesktopHeight() {
		int desktopHeight = 20;
		if (desktop != null) {
			desktopHeight = desktop.getBounds().height;
		}
		return desktopHeight;
	}
	

	/**
	 * @return the desktopHeight
	 */
	public static int getDesktopWidth() {
		int desktopWidth = 20;
		if (desktop != null) {
			desktopWidth = desktop.getBounds().width;
		}
		return desktopWidth;
	}
}
