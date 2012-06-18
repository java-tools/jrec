/*
 * Created on 25/08/2004
 *
 */
package net.sf.RecordEditor.utils.screenManager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JDesktopPane;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.text.DefaultEditorKit;

import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.JRecord.Log.ScreenLog;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.Parameters;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.swing.HelpWindow;
import net.sf.RecordEditor.utils.swing.SwingUtils;


/**
 * Main LayoutEdit class. This class lets the user
 * <ul compact>
 * <li>Edit Record Layouts
 * <li>Edit Tables DB
 * </ul>
 *
 * @author Bruce Martin
 * @version 0.55
 */
@SuppressWarnings("serial")
public class ReMainFrame extends JFrame
					  implements ReActionHandler, ReWindowChanged {

    { setLookAndFeel(); }
 	private JDesktopPane desktop = new JDesktopPane();

	private JInternalFrame logFrame = new JInternalFrame("Program Log", true, false, true, true);
	private ScreenLog log;

    private static ReMainFrame masterFrame = null;


    private static final int TOOL_BAR_SEPARATOR_WIDTH = SwingUtils.STANDARD_FONT_WIDTH;
	private static final int LOG_SCREEN_HEIGHT = SwingUtils.STANDARD_FONT_HEIGHT * 20;
	private static final int LOG_SCREEN_STARTS_FROM_BOTTOM = SwingUtils.STANDARD_FONT_HEIGHT * 10;
	private static final int LOG_SCREEN_WIDTH_ADJUSTMENT = SwingUtils.STANDARD_FONT_WIDTH * 2;


    private JMenuBar menuBar = new JMenuBar();
    private JToolBar toolBar = new JToolBar();
    private JMenu windowMenu = new JMenu("Window");

    private ArrayList<JMenu> windowList  = new ArrayList<JMenu>();
    private HashMap<ReFrame,JMenuItem> windowActionMap = new HashMap<ReFrame,JMenuItem>();
    private HashMap<Object, JMenu> datafileItemMap = new HashMap<Object, JMenu>();

    private static final String[] SYSTEM_ACTION_NAMES = {
            DefaultEditorKit.copyAction,
            DefaultEditorKit.cutAction,
            DefaultEditorKit.pasteAction};
    private static Action[] systemActions = null;

    private ReActionHandler closeAction = new ReActionHandler() {
        public void executeAction(int action) {
            closeAction(action);
        }
        public boolean isActionAvailable(int action) {
            return true;
        }
    };

	private HelpWindow help;

	private int numActiveScreenActions = 0;
	private AbstractActiveScreenAction[] activeScreenActions
								= new AbstractActiveScreenAction[ReActionHandler.MAX_ACTION + 10];

	private final ReAction open = new ReAction("Open", "Open",
	        Common.getRecordIcon(Common.ID_OPEN_ICON), ReActionHandler.OPEN, this);
//	private final ReAction newAction = new ReAction("New", "New Layout",
//	        Common.getRecordIcon(Common.ID_NEW_ICON), ReActionHandler.NEW, this);
	private final ReActionActiveScreen save
								= newAction(ReActionHandler.SAVE);
	private final ReActionActiveScreen saveAs
								= newAction(ReActionHandler.SAVE_AS);
	private final ReActionActiveScreen export
								= newAction(ReActionHandler.EXPORT);
	private final ReActionActiveScreen SAVE_AS_CSV         = newAction(ReActionHandler.EXPORT_AS_CSV);
	private final ReActionActiveScreen SAVE_AS_HTML        = newAction(ReActionHandler.EXPORT_AS_HTML);
	private final ReActionActiveScreen SAVE_AS_HTML_TBLS   = newAction(ReActionHandler.EXPORT_AS_HTML_TBL_PER_ROW);
	private final ReActionActiveScreen SAVE_AS_HTML_TREE   = newAction(ReActionHandler.EXPORT_HTML_TREE);
//	private final ReActionActiveScreen SAVE_AS_VELOCITY    = newAction(ReActionHandler.EXPORT_VELOCITY);
	private final ReAction close = new ReAction("Close", "Close DB's and exit application",
												//Common.getRecordIcon(Common.ID_EXIT_ICON),
												ReActionHandler.CLOSE, closeAction);

	private final ReActionActiveScreen delete
		= newAction(ReActionHandler.DELETE);
	private final ReActionActiveScreen helpAction
							= newAction(ReActionHandler.HELP);
	private final ReActionActiveScreen findAction = newAction(ReActionHandler.FIND);
	private final ReActionActiveScreen print
		= newAction(ReActionHandler.PRINT);
	private final ReActionActiveScreen printSelected
	= newAction(ReActionHandler.PRINT_SELECTED);


	private final ReActionActiveScreen copyRecords
		= newAction(ReActionHandler.COPY_RECORD);
	private final ReActionActiveScreen cutRecords
		= newAction(ReActionHandler.CUT_RECORD);
	private final ReActionActiveScreen pasteRecords
		= newAction(ReActionHandler.PASTE_RECORD);
	private final ReActionActiveScreen pasteRecordsPrior
		= newAction(ReActionHandler.PASTE_RECORD_PRIOR);
	private final ReActionActiveScreen pasteTableOver
	= newAction(ReActionHandler.PASTE_TABLE_OVERWRITE);
	private final ReActionActiveScreen pasteTableInsert
	= newAction(ReActionHandler.PASTE_TABLE_INSERT);

	private final ReActionActiveScreen insertRecords
		= newAction(ReActionHandler.INSERT_RECORDS);
	private final ReActionActiveScreen deleteRecords
	= newAction(ReActionHandler.DELETE_RECORD);

	private JMenu fileMenu = new JMenu("File");
	private JMenu editMenu = new JMenu("Edit");


	private JMenu scriptList = null;
	private JMenu velocityTemplateList = null;
	private JMenu xslTransformList = null;

	private final String applId;
	static {
        int j;
        systemActions = new Action[SYSTEM_ACTION_NAMES.length];

        systemActions[0] = new DefaultEditorKit.CopyAction();
        systemActions[1] = new DefaultEditorKit.CutAction();
        systemActions[2] = new DefaultEditorKit.PasteAction();
        for (j = 0; j < SYSTEM_ACTION_NAMES.length; j++) {
            systemActions[j].putValue(AbstractAction.SHORT_DESCRIPTION,
                    SYSTEM_ACTION_NAMES[j]);
        }

	}



	/**
	 * Standard Frame for the recordEditor / Layout Editor
	 * @param name frame name
	 * @param helpName help screen name
	 * @param applicationId Very short name for the application
	 */
	public ReMainFrame(final String name, final String helpName, String applicationId) {
	    super(name);
	    masterFrame = this;
	    this.applId = applicationId;
	    init_100_Frame(helpName);
	    init_200_SetSizes();
	    init_300_BuildScreen();

	    init_400_AddListners();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	/**
	 * Frame initialising
	 * @param helpName help file name
	 */
	private void init_100_Frame(String helpName) {

		if (Common.OPTIONS.logToFront.isSelected()) {
			log = new ScreenLog(logFrame) {
				public void logMsg(int level, String msg) {
					super.logMsg(level, msg);
					if (level >= AbsSSLogger.ERROR) {
						logFrame.moveToFront();
						logFrame.requestFocus();
				        try {
							logFrame.setIcon(false);
				        	logFrame.setSelected(true);
				        } catch (Exception ex) {
				        }
					}

			 	}


			};
		} else {
			log = new ScreenLog(logFrame);
		}
	    //setLookAndFeel();
	    systemActions[0].putValue(AbstractAction.SMALL_ICON,
	            Common.getRecordIcon(Common.ID_COPY_ICON));
	    systemActions[1].putValue(AbstractAction.SMALL_ICON,
	            Common.getRecordIcon(Common.ID_CUT_ICON));
	    systemActions[2].putValue(AbstractAction.SMALL_ICON,
	            Common.getRecordIcon(Common.ID_PASTE_ICON));

	    ReFrame.setDesktop(desktop);
	    Common.setCurrClass(this);
	    Common.setLogger(log);

	    help = new HelpWindow(Common.formatHelpURL(helpName));

	    this.addKeyListener(help);
	    ReFrame.addFocusChangedListner(this);

	    desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);

	}

	/**
	 * Setup screen size and define log screen
	 *
	 */
	private void init_200_SetSizes() {
	    int start, height;
	    Dimension desktopSize;

	    Common.setBounds1(HelpWindow.HELP_FRAME, applId);
	    Common.setBounds1(this, applId);

	    desktopSize = this.getSize();

	    start = desktopSize.height - LOG_SCREEN_HEIGHT - LOG_SCREEN_STARTS_FROM_BOTTOM;
	    height  = LOG_SCREEN_HEIGHT;
	    if (desktopSize.height - 40 < LOG_SCREEN_HEIGHT) {
	        start = 4;
	        height  = height - start;
	    }
//	    System.out.println();
//	    System.out.println("Desktop Size: " + desktopSize.height + " " + desktopSize.width);
//	    System.out.println("    Log Size: " + height + " " + (desktopSize.width - LOG_SCREEN_WIDTH_ADJUSTMENT));
//	    System.out.println();

	    logFrame.getContentPane().add(log);
	    logFrame.setBounds(2, start,
	            desktopSize.width - LOG_SCREEN_WIDTH_ADJUSTMENT, height);
	    logFrame.setVisible(true);

	    desktop.add(logFrame);
	}


	/**
	 * Build (or layout) the screen
	 */
	private void init_300_BuildScreen() {
	    JPanel topPanel = new JPanel();
	    JPanel fullPanel = new JPanel();

	    //buildMenubar(menuBar);
	    //buildToolbar(toolBar);

	    topPanel.setLayout(new BorderLayout());
	    topPanel.add("North", menuBar);
	    topPanel.add("South", toolBar);

	    fullPanel.setLayout(new BorderLayout());
	    fullPanel.add("North", topPanel);
	    fullPanel.add("Center", desktop);
	    setContentPane(fullPanel);
    }

	/**
	 * add listners
	 */
	private void init_400_AddListners() {

		ReFrame.addFocusChangedListner(this);

	    this.addWindowListener(new WindowAdapter() {
	    	@Override
	        public void windowClosing(final WindowEvent e) {
	    		System.out.println("Window Closing");

	            quit();
	            super.windowClosing(e);
	        }

			/* (non-Javadoc)
			 * @see java.awt.event.WindowAdapter#windowClosed(java.awt.event.WindowEvent)
			 */
			@Override
			public void windowClosed(WindowEvent e) {
				System.out.println("Window Closed");
				Common.closeConnection();
				System.exit(0);
				super.windowClosed(e);
			}
	    });
	}



	/**
	 * build and store an active screen action
	 *
	 * @param action action to be performed
	 *
	 * @return active screen action
	 */
	protected final ReActionActiveScreen newAction(int action) {
	    ReActionActiveScreen ret = new ReActionActiveScreen(action);

	    activeScreenActions[numActiveScreenActions++] = ret;

	    return ret;
	}

	protected final AbstractActiveScreenAction addAction(AbstractActiveScreenAction action) {
		 activeScreenActions[numActiveScreenActions++] = action;
		 return action;
	}

	/**
	 * Build the Menu Bar
	 *
	 */
	protected final void buildMenubar(JMenu velocityPopup, JMenu xsltPopup, JMenu scriptPopup) {

		scriptList = scriptPopup;
		velocityTemplateList = velocityPopup;
		xslTransformList = xsltPopup;
	    menuBar.add(fileMenu);
	    menuBar.add(buildEditMenu());

	    addProgramSpecificMenus(menuBar);

	    menuBar.add(windowMenu);
	    menuBar.add(buildHelpMenu());
	}


	/**
	 * Builds the file menu
	 *
	 * @return file menu
	 */
	protected void buildFileMenu( JMenu recentFiles, boolean addSaveAsXml, boolean addSaveLayout, AbstractAction newAction) {

		open.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
	            KeyEvent.VK_O, ActionEvent.CTRL_MASK));
	    fileMenu.add(open);
	    if (newAction != null) {
	    	newAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		            KeyEvent.VK_N, ActionEvent.CTRL_MASK));
	    	fileMenu.add(newAction);
	    }
	    //save.putValue(Action.MNEMONIC_KEY /*ACCELERATOR_KEY*/, Integer.valueOf(KeyEvent.VK_S));

	    fileMenu.add(save);
	    fileMenu.add(saveAs);
	    if (recentFiles != null) {
	    	fileMenu.add(recentFiles);
	    }

	    fileMenu.addSeparator();
	    fileMenu.add(export);
	    fileMenu.add(SAVE_AS_CSV);
	    fileMenu.add(SAVE_AS_HTML);
	    fileMenu.add(SAVE_AS_HTML_TBLS);
	    fileMenu.add(SAVE_AS_HTML_TREE);
	    if (velocityTemplateList != null && Common.isVelocityAvailable()) {
//	    	fileMenu.add(SAVE_AS_VELOCITY);
	    	fileMenu.add(velocityTemplateList);
	    }
	    if (xslTransformList != null) {
	    	fileMenu.add(xslTransformList);
	    }
	    if (scriptList != null) {
	    	fileMenu.add(scriptList);
	    }
	    if (addSaveAsXml) {
	    	fileMenu.add(newAction(ReActionHandler.SAVE_AS_XML));
	    }

	    if (addSaveLayout) {
	    	fileMenu.add(newAction(ReActionHandler.SAVE_LAYOUT_XML));
	    }

	    fileMenu.addSeparator();
	    fileMenu.add(print);
	    fileMenu.add(printSelected);


	}

	protected final void addExit() {

	    fileMenu.addSeparator();
		fileMenu.add(new AbstractAction("Exit") {
	        public void actionPerformed(ActionEvent e) {
	            quit();
	        }
	    });
	}

	/**
	 * Build the edit menu
	 *
	 * @return edit menu
	 */
	private JMenu buildEditMenu() {

	    for (int i = 0; i < systemActions.length; i++) {
	        editMenu.add(systemActions[i]);
	    }
	    editMenu.addSeparator();
	    editMenu.add(copyRecords);
	    editMenu.add(cutRecords);
	    editMenu.add(pasteRecords);
	    editMenu.add(pasteRecordsPrior);
	    editMenu.add(pasteTableInsert);
	    editMenu.add(pasteTableOver);
	    editMenu.add(insertRecords);
	    editMenu.add(deleteRecords);

	    editMenu.addSeparator();
	    editMenu.add(findAction);

	    return editMenu;
	}


	/**
	 * Add program specific dropdown menus
	 * @param menubar top level menu
	 */
	protected void addProgramSpecificMenus(JMenuBar menubar) {

	}


	/**
	 * Build the Help Menu
	 * @return Help Menu
	 */
	private JMenu buildHelpMenu() {
		JMenu helpMenu = new JMenu("Help");

		helpMenu.add(helpAction);
		helpMenu.add(
				new showURI(
						"RecordEditor Manual",
						(new File(Common.formatHelpURL("RecordEdit.htm").substring(5))).toURI()));

		addWebsitesToHelpMenu(helpMenu);

		helpMenu.addSeparator();
		helpMenu.add(new AbstractAction("About") {

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(ActionEvent arg0) {
				showAbout();
			}
		});
//		helpMenu.add(new AbstractAction("Online Help") {
//
//			/**
//			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
//			 */
//			public void actionPerformed(ActionEvent arg0) {
//				showOnlineHelp();
//			}
//		});
//

		if (Common.TEST_MODE) {
			helpMenu.addSeparator();
			helpMenu.add(new AbstractAction("Allocated Jars") {
		        public void actionPerformed(ActionEvent e) {
		            showURLS();
		        }
		    });
		}


		return helpMenu;
	}


	protected void addWebsitesToHelpMenu(JMenu helpMenu) {

		try {
			helpMenu.addSeparator();
			helpMenu.add(new showURI("RecordEditor Web Page", new URI("http://record-editor.sourceforge.net/")));
			helpMenu.add(new showURI("RecordEditor Forum", new URI("https://sourceforge.net/projects/record-editor/forums")));
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 *
	 *
	 */
	protected void showAbout() {
		showAbout(
				"The <b>RecordEditor</b> is an editor for Cobol / Fixed Field Width / CSV "
			  + "data files.<br><br>"
			  + "It is distributed under a GPL 3 (or later) license<br/><pre>"
			  +	" <br><b>Authors:</b><br><br> "
			  + "\t<b>Bruce Martin</b>: Main author<br>"
			  + "\t<b>Jean-Francois Gagnon</b>: Provided Fujitsu IO / Types<br><br>"
			  + " <b>Associated:</b><br><br> "
			  + "\t<b>Peter Thomas</b>: Wrote the <b>cb2xml</b> which provides the cobol interface<br/><br/> &nbsp; "
			  + " <b>Websites:</b><br><br> "
			  + "\t<b>RecordEditor:</b> http://record-editor.sourceforge.net<br>"
			  + "<br><br>"
			  + "<b>Packages Used:</b><br/>"
			  + "\t<b>cb2xml<b>:\t\tCobol Copybook Analysis<br/>"
			  + "\t<b>jibx<b>:\t\tXml Bindings<br/>"
			  + "\t<b>TableLayout<b>:\tSwing Layout manager used<br>"
			  + "\t<b>jlibdif<b>:\tFile Compare<br/>"
		);
	}

	protected void showAbout(String s) {
		showHtmlPnl("About", s);
	}

//	protected void showOnlineHelp() {
//		showHtmlPnl("Online Help",
//					"<h1>Online Help</h1>"
//				  + "If you need help using the <b>RecordEditor</b>, try asking at the<br/>"
//				  + "RecordEditor Forum "
//				  + "<b><font color=blue>http://sourceforge.net/projects/record-editor/forums</font></b>"
//				  + "&nbsp;  &nbsp;  &nbsp;  "
//				  + "<br/><br/>If you discover any problems, please report it at either the forum<br/>"
//				  + "or at <b>http://sourceforge.net/tracker/?group_id=139274&atid=742839</b>   <br/><br/>"
//		);
//	}

	protected void showHtmlPnl(String name, String s) {
		ReFrame aboutFrame = new ReFrame(name, null, null);
		JEditorPane aboutText = new JEditorPane("text/html", s);

		aboutFrame.getContentPane().add(aboutText);
		aboutFrame.pack();
		aboutFrame.setVisible(true);
	}


	/**
	 * Build the Tool Bar
	 *
	 * @param toolBar to be built
	 */
	protected final void buildToolbar(AbstractAction newAction, AbstractAction[] toolbarActions) {

	    toolBar.add(open);
	    if (newAction != null) {
	    	toolBar.add(newAction);
	    }
	    toolBar.add(save);
	    toolBar.add(saveAs);
	    toolBar.add(export);
	    toolBar.add(delete);
	    Dimension seperatorSize = new Dimension(TOOL_BAR_SEPARATOR_WIDTH,
	            							    toolBar.getHeight());
	    toolBar.addSeparator(seperatorSize);
	    for (int i = 0; i < systemActions.length; i++) {
	        toolBar.add(systemActions[i]);
	    }
	    //toolbar.add(new JToolBar.Separator());
	    toolBar.addSeparator(seperatorSize);
	    toolBar.add(copyRecords);
	    toolBar.add(cutRecords);
	    toolBar.add(pasteRecords);
	    toolBar.add(pasteRecordsPrior);
	    toolBar.add(deleteRecords);
	    toolBar.add(findAction);
	    //toolbar.add(new JToolBar.Separator());
	    if (toolbarActions != null) {
		    toolBar.addSeparator(seperatorSize);
		    for (int i = 0; i < toolbarActions.length; i++) {
		    	if (toolbarActions[i] == null) {
		    		toolBar.addSeparator(seperatorSize);
		    	} else {
		    		toolBar.add(toolbarActions[i]);
		    	}
		    }
	    }

	    toolBar.addSeparator(seperatorSize);
	    toolBar.addSeparator(seperatorSize);

	    toolBar.add(close);

	    try {
	        toolBar.add(new JSeparator());
	    } catch (Exception e) {
	        toolBar.addSeparator(seperatorSize);
        }
	    toolBar.add(helpAction);
	}


	/**
	 * Quit the application
	 */
	protected void quit() {
		System.out.println("ReMainframe Quit");

		Dimension d = ReMainFrame.this.getSize();
		if (d != null && applId != null) {
			Parameters.setSavePropertyChanges(false);
			Parameters.setProperty(applId + Parameters.LAST_SCREEN_WIDTH, String.valueOf(d.width));
			Parameters.setSavePropertyChanges(true);
			Parameters.setProperty(applId + Parameters.LAST_SCREEN_HEIGHT, String.valueOf(d.height));
		}

		ReFrame[] allFrames = ReFrame.getAllFrames();
        for (int i = allFrames.length - 1; i >= 0; i--) {
            if (allFrames[i].isPrimaryView()) {
                allFrames[i].doDefaultCloseAction();
            }
        }

		Common.closeConnection();
		System.exit(0);
	}


    /**
     * @return Returns the desktop.
     */
    public JDesktopPane getDesktop() {
        return desktop;
    }


    /**
     * @see net.sf.RecordEditor.utils.common.ReActionHandler#executeAction(int)
     */
    public void executeAction(int action) {

    }


    /**
     * @see net.sf.RecordEditor.utils.common.ReActionHandler#isActionAvailable(int)
     */
    public boolean isActionAvailable(int action) {
        return false;
    }

    /**
     * Excute close action
     * @param action action to perform
     */
    public void closeAction(int action) {

        if (action == ReActionHandler.CLOSE) {
            ReFrame frame = ReFrame.getActiveFrame();
            ReFrame frame2close = frame;
            Object doc = frame.getDocument();

            if (doc != null && ! frame.isPrimaryView()) {
                ReFrame[] allFrames = ReFrame.getAllFrames();

                for (int i = allFrames.length - 1; i >= 0; i--) {
                    if (allFrames[i].isPrimaryView()
                            &&  allFrames[i].getDocument() == doc) {
                        frame2close = allFrames[i];
                        break;
                    }
                }
            }
            frame2close.doDefaultCloseAction();
            Common.freeConnection(Common.getConnectionIndex());
        }
    }

    /**
     * @see net.sf.RecordEditor.utils.screenManager.ReWindowChanged#focusChanged(net.sf.RecordEditor.utils.swing.ReFrame)
     */
    public void focusChanged(ReFrame newFrame) {
        int i;

        for (i = 0; i < numActiveScreenActions; i++) {
            activeScreenActions[i].checkActionEnabled();
        }

    	ReFrame actionHandler = ReFrame.getActiveFrame();
        if (velocityTemplateList != null) {
            velocityTemplateList.setEnabled(
            		actionHandler != null
            		&& actionHandler.isActionAvailable(ReActionHandler.EXPORT_VELOCITY));
         }

         if (xslTransformList != null) {
            	xslTransformList.setEnabled(
            			actionHandler != null
            			&& actionHandler.isActionAvailable(ReActionHandler.EXPORT_XSLT));
         }


         if (scriptList != null) {
        	 scriptList.setEnabled(
        			 actionHandler != null
        			 && actionHandler.isActionAvailable(ReActionHandler.EXPORT_SCRIPT));
         }
   }

    /**
     * Called when a window has been created
     *
     * @param newFrame frame that was created
     */
    public void newWindow(ReFrame newFrame) {

        int i;
        boolean cont = true;
        JMenu m;
        boolean isDocument = newFrame.getDocument() != null;
        String name = newFrame.getDocumentName();
        String frameId = newFrame.getFrameId();
        //JMenuItem menuItem = newWinAction(frameId, newFrame);

        int itemCount = windowMenu.getItemCount();
        if (itemCount > 1) {
        	windowMenu.remove(itemCount - 1);
        	windowMenu.remove(itemCount - 2);
        }
        if (isDocument) {
            if (datafileItemMap.containsKey(newFrame.getDocument())) {
                m = datafileItemMap.get(newFrame.getDocument());
                m.add(newWinAction(frameId, newFrame));
                cont = false;
            }
        } else {
            for (i = 0; cont && (i < windowList.size()); i++) {
                m =  windowList.get(i);


                if ((m.getText() != null) && m.getText().equals(name)) {
                    cont = false;

                    m.add(newWinAction(frameId, newFrame));
                }
            }
        }

        if (cont) {
            if (frameId == null || "".equals(frameId)) {
                if (! windowActionMap.containsKey(newFrame)) {
                    windowMenu.add(newWinAction(name, newFrame));
                }
            } else {
                m = new JMenu(name);

                if (isDocument) {
                    datafileItemMap.put(newFrame.getDocument(), m);
                }
                m.add(newWinAction(frameId, newFrame));
                windowMenu.add(m);

                windowList.add(m);
            }
        }

        windowMenu.addSeparator();
        windowMenu.add(close);
    }


    /**
     * Create (and save) a new menu Item
     * @param label label for the Menu Item
     * @param newFrame frame that was created
     *
     * @return menu Item
     */
    private JMenuItem newWinAction(String label, ReFrame newFrame) {

        if (windowActionMap.containsKey(newFrame)) {
            return windowActionMap.get(newFrame);
        }

        JMenuItem mi = new JMenuItem(new WindowAction(label, newFrame));

        windowActionMap.put(newFrame, mi);
        return mi;
    }


    /**
     * Called when a window has been deleted
     *
     * @param oldFrame frame that was deleted
     */
    public void deleteWindow(ReFrame oldFrame) {
        int i;
        boolean cont = true;
        JMenu m;
        if (oldFrame == null) {
        	return;
        }
        String name = oldFrame.getDocumentName();
        JMenuItem w =  windowActionMap.get(oldFrame);

        if (w != null) {
            windowMenu.remove(w);
            for (i = 0; cont && (i < windowList.size()); i++) {
                m = windowList.get(i);

                if ((m.getText() != null) && m.getText().equals(name)) {
                    m.remove(w);

                    if (m.getItemCount() == 0) {
                        windowMenu.remove(m);
                        windowList.remove(i);
                    }
                }
            }
        }
    }

    /**
     * Show the URL's
     *
     */
    private void showURLS() {
        try {
            URL[] urls = ((URLClassLoader) ReMainFrame.class.getClassLoader()).getURLs();
            String s;

            Common.logMsg("", null);
            for (int i = 0; i < urls.length; i++) {
            	s = urls[i].getFile();
                Common.logMsg("url " + i + " = " + s + "\t" + (new File(s)).exists(), null);
            }
        } catch (Exception e) {
        }
    }
    /**
     * @return Returns the log.
     */
    public ScreenLog getLog() {
        return log;
    }

    /**
     * Set the Look and Feel
     * @return nothing, allows me to assign to a variable
     */
	public final void setLookAndFeel() {

        int idx = Common.LOOKS_INDEX;

        try {
//        	System.out.println(">>> LAF >>> " + UIManager.getSystemLookAndFeelClassName() + " "
//        			+ UIManager.getLookAndFeel()
//        			+ " " + UIManager.getCrossPlatformLookAndFeelClassName());
             if (idx == 0) {
                JFrame.setDefaultLookAndFeelDecorated(true);
            } else if (idx == 1) {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } else {
                String lafName = Parameters.getString(Parameters.PROPERTY_LOOKS_CLASS_NAME);

                if (Common.NIMBUS_LAF) {
                	boolean useSysLaf = true;
                    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                        if ("Nimbus".equals(info.getName())) {
                            UIManager.setLookAndFeel(info.getClassName());
                            useSysLaf = false;
                            break;
                        }
                    }

                    if (useSysLaf) {
                    	setSysLAF();
                    }
                } else if (Common.RECORD_EDITOR_LAF) {
                	setSysLAF();
                } else {
	                System.out.println("Setting Class loader " + lafName);
	                UIManager.put("ClassLoader", getClass().getClassLoader());
	                @SuppressWarnings("rawtypes")
					Class c = getClass().getClassLoader().loadClass(lafName);
	                //Class c = Class.forName(lafName);
	                LookAndFeel laf = (LookAndFeel) c.newInstance();
	                UIManager.setLookAndFeel(laf);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JFrame.setDefaultLookAndFeelDecorated(true);
        }
    }

    private static void setSysLAF() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
    	if (UIManager.getSystemLookAndFeelClassName().endsWith("GTKLookAndFeel")) {
    		JFrame.setDefaultLookAndFeelDecorated(true);
    	} else {
    		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    	}
    }

    /**
     * @return Returns the masterFrame.
     */
    public static ReMainFrame getMasterFrame() {
        return masterFrame;
    }

    /**
     * @param newMasterFrame The masterFrame to set.
     */
    public static void setMasterFrame(ReMainFrame newMasterFrame) {
        ReMainFrame.masterFrame = newMasterFrame;
    }



	/**
	 * @return the fileMenu
	 */
	public final JMenu getFileMenu() {
		return fileMenu;
	}

	/**
	 * @return the editMenu
	 */
	public final JMenu getEditMenu() {
		return editMenu;
	}



    /* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.screenManager.ReWindowChanged#getApplicationId()
	 */
	@Override
	public String getApplicationId() {
		return applId;
	}





	/**
     * Action to bring window to the front; used by the Window menu
     *
     *
     * @author Bruce Martin
     *
     */
    private static class WindowAction extends AbstractAction {
        private ReFrame window;

        /**
         * Create Window action
         * @param label label to display
         * @param frame frame to be displayed if need be
         */
        public WindowAction(final String label, final ReFrame frame) {
            super(label);
            window = frame;
        }

        /**
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {

            try {
                window.setIcon(false);
            } catch (Exception ex) { }

            ReFrame.setActiveFrame(window);
        }
    }


    public static final class showURI extends AbstractAction {
    	private URI uri;

		public showURI(String name, URI uri) {
			super(name);
			this.uri = uri;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			if (java.awt.Desktop.isDesktopSupported()) {
				try {
					java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

	                desktop.browse(uri);
	            } catch ( Exception ex ) {
	                System.err.println( "Error showing Web page" + ex.getMessage() );
	            }
			}
		}
    }
}
