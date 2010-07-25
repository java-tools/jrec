/*
 * @Author Bruce Martin
 * Created on 4/01/2007
 *
 * Purpose:
 *   Record Editor Main screen.
 *
 * Changes
 * # New Version 0.56 the old EditRec code was moved to OpenFile
 *
 * # Version 0.60 Bruce Martin 2007/02/16
 *   - new Data drop down menu
 *   - added support for reading user types from Parameter file
 *   - support for new java run program
 *   - support user_Initialise_class's added
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - Support for User Date-Types
 *   - JRecord Spun off, code reorg
 */
package net.sf.RecordEditor.edit;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import net.sf.JRecord.IO.AbstractLineIOProvider;
import net.sf.JRecord.Types.Type;
import net.sf.RecordEditor.edit.display.BaseDisplay;
import net.sf.RecordEditor.edit.display.Action.LoadSavedVisibilityAction;
import net.sf.RecordEditor.edit.util.DisplayCobolCopybook;
import net.sf.RecordEditor.editProperties.EditOptions;
import net.sf.RecordEditor.record.format.CellFormat;
import net.sf.RecordEditor.record.types.ReTypeManger;
import net.sf.RecordEditor.record.types.TypeDateWrapper;
import net.sf.RecordEditor.utils.CopyBookDbReader;
import net.sf.RecordEditor.utils.CopyBookInterface;
import net.sf.RecordEditor.utils.Plugin;
import net.sf.RecordEditor.utils.VelocityPopup;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.Parameters;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.edit.ParseArgs;
import net.sf.RecordEditor.utils.edit.ReIOProvider;
import net.sf.RecordEditor.utils.openFile.LayoutSelectionDB;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;

/**
 * Record Editor Main screen
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class EditRec extends ReMainFrame  {

    private OpenFile open; 
    private JMenu dataMenu;
    private JMenu viewMenu;

    private boolean incJdbc;
//    private CopyBookInterface copybookInterface;




    /**
     * Creating the File & record selection screenm
     *
     * @param pInFile File to be read (optional)
     * @param pInitialRow initinial Row (optional)
     * @param pInterfaceToCopyBooks interface to copybooks
     */
    public EditRec(final String pInFile,
                   final int pInitialRow,
                   final CopyBookInterface pInterfaceToCopyBooks) {
        this(pInFile, pInitialRow, ReIOProvider.getInstance(), pInterfaceToCopyBooks);
    }


    /**
     * Creating the File & record selection screenm
     *
     * @param pInFile File to be read (optional)
     * @param pInitialRow initinial Row (optional)
     * @param pIoProvider ioProvider to use when creating
     *        lines
     * @param pInterfaceToCopyBooks interface to copybooks
     */
    public EditRec(final String pInFile,
                   final int pInitialRow,
                   final AbstractLineIOProvider pIoProvider,
                   final CopyBookInterface pInterfaceToCopyBooks) {
       super("Record Editor", "");

 //      copybookInterface = pInterfaceToCopyBooks;
       open = new OpenFile(pInFile,
                  pInitialRow,
                  pIoProvider,
                  null, null,
                  new LayoutSelectionDB(pInterfaceToCopyBooks, null, true));

        init(true);
        setupFileMenu(null, null, true);  
     }


    /**
     * Creating the File & record selection screen
     *
     */
    public EditRec(final boolean includeJdbc) {
        this(includeJdbc, "Record Editor");
    }

    
    public EditRec(final boolean includeJdbc, final String name) {
    	super(name, "");
    	init(includeJdbc);
    }
    /**
     * standard initialize
     *
     */
    @SuppressWarnings("unchecked")
	private void init(final boolean includeJdbc) {
    	LoadSavedVisibilityAction savedVisibiltyAction = new LoadSavedVisibilityAction();
    	incJdbc = includeJdbc;
    	
        ReMainFrame.setMasterFrame(this);
        ReTypeManger.setDateFormat(Common.DATE_FORMAT_STR);
        
        buildMenubar(VelocityPopup.getPopup());

        
      
        dataMenu.add(newAction(ReActionHandler.FILTER));
        dataMenu.add(newAction(ReActionHandler.TABLE_VIEW_SELECTED));
        dataMenu.add(newAction(ReActionHandler.SORT));
        dataMenu.add(newAction(ReActionHandler.REBUILD_TREE));
        dataMenu.addSeparator();
        dataMenu.add(newAction(ReActionHandler.ADD_ATTRIBUTES));
        dataMenu.addSeparator();
        dataMenu.add(newAction(ReActionHandler.FULL_TREE_REBUILD));
        
        viewMenu.add(newAction(ReActionHandler.BUILD_FIELD_TREE));
        viewMenu.add(newAction(ReActionHandler.BUILD_SORTED_TREE));
        viewMenu.add(newAction(ReActionHandler.BUILD_RECORD_TREE));
        viewMenu.addSeparator();
        viewMenu.add(newAction(ReActionHandler.BUILD_LAYOUT_TREE));
        viewMenu.add(newAction(ReActionHandler.BUILD_LAYOUT_TREE_SELECTED));
        viewMenu.addSeparator();
        viewMenu.add(newAction(ReActionHandler.TABLE_VIEW_SELECTED));
        viewMenu.add(newAction(ReActionHandler.RECORD_VIEW_SELECTED));
        viewMenu.add(newAction(ReActionHandler.COLUMN_VIEW_SELECTED));
        viewMenu.add(newAction(ReActionHandler.BUILD_XML_TREE_SELECTED));
        viewMenu.addSeparator();
        viewMenu.add(newAction(ReActionHandler.EXECUTE_SAVED_FILTER));
        viewMenu.add(newAction(ReActionHandler.EXECUTE_SAVED_SORT_TREE));
        viewMenu.add(newAction(ReActionHandler.EXECUTE_SAVED_RECORD_TREE));
        addAction(savedVisibiltyAction);
        viewMenu.add(savedVisibiltyAction);



        if (Common.USER_INIT_CLASS != null && ! "".equals(Common.USER_INIT_CLASS)) {
            try {
                Class c = ClassLoader.getSystemClassLoader().loadClass(Common.USER_INIT_CLASS);
                c.newInstance();
            } catch (Exception e) {
                Common.logMsg("Error running user Initialize: "
                        + e.getMessage(), null);
            }
        }

        loadUserTypes();
        loadUserFormats();

//        this.addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//                closeProcessing();
//            }
//            
//            public void windowClosed(WindowEvent e) {
//                Common.closeConnection();
//
//                System.exit(0);
//            }
//        });
    }
    
    /**
     * Build File menu
     * @param compareAction compare action
     */
    private void setupFileMenu(Action copyAction, Action compareAction, final boolean includeWizardOptions) {
    	JMenu fm;
    	
        buildFileMenu(open.getOpenFilePanel().getRecentFileMenu() , true, false);
 
        fm = getFileMenu();
        
        if (copyAction != null) {
        	fm.addSeparator();
        	fm.add(new AbstractAction("Cobol Copybook Analysis") {

					/**
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					@Override
					public void actionPerformed(ActionEvent e) {
						new DisplayCobolCopybook();
					}
		        });
	        fm.addSeparator();
	        fm.add(copyAction);
	    }
        fm.addSeparator();
        fm.add(newAction(ReActionHandler.COMPARE_WITH_DISK));
        if (compareAction != null) {
        	fm.add(compareAction);
        }
        
	    getEditMenu().addSeparator();
	    getEditMenu().add(
	    		new AbstractAction("Edit Startup Options",
	    				Common.getRecordIcon(Common.ID_PREF_ICON)) {
	    			 ;
	        public void actionPerformed(ActionEvent e) {
	            new EditOptions(false, incJdbc, includeWizardOptions);
	        }
	    });

        super.addExit();
   }


	/**
	 * Add program specific dropdown menus
	 * @param menubar top level menu
	 */
	protected void addProgramSpecificMenus(JMenuBar menubar) {
	    dataMenu = new JMenu("Data");
	    viewMenu = new JMenu("View");

        menubar.add(dataMenu);
        menubar.add(viewMenu);
        
        loadUserFunctions(menubar);
	}
	
	private void loadUserFunctions(JMenuBar menubar) {
		Object[] userFunctions = Common.readPropertiesArray(Parameters.PROPERTY_PLUGIN_FUNC_CLASS, 
				Parameters.NUMBER_OF_USER_FUNCTIONS);

		if (userFunctions != null) {
			JMenu localMenu = new JMenu("Plugin");
			String name, param;
			Plugin userClass;
			int j = 0;
			
			for (int i = 0; i < Parameters.NUMBER_OF_USER_FUNCTIONS; i++) {
				if (userFunctions[i] != null) {
					try {
						userClass = (Plugin) userFunctions[i];
						name = Parameters.getString(Parameters.PROPERTY_PLUGIN_FUNC_NAME + i);
						param = Parameters.getString(Parameters.PROPERTY_PLUGIN_FUNC_PARAM + i);
						localMenu.add(new LocalAction(name, userClass, param));
						j += 1;
					} catch (Exception e) {
						Common.logMsg("Error loading Function " + e.getMessage(), e);
					}
				}
			}
			
			if (j > 0) {
				menubar.add(localMenu);
			}
		}

	}


    /**
     * Load user defined types
     *
     */
    public static void loadUserTypes() {
        int i, typeNum, baseType;
        ReTypeManger sysTypes = ReTypeManger.getInstance();
        Type newType;
        String name, format, baseTypeStr;
        CellFormat newFormat;
        int[] typeIds = Common.readIntPropertiesArray(Parameters.TYPE_NUMBER_PREFIX, Parameters.NUMBER_OF_TYPES);
        Object[] types = Common.readPropertiesArray(typeIds, Parameters.TYPE_CLASS_PREFIX);

        if (types != null) {
            Object[] formats = Common.readPropertiesArray(typeIds, Parameters.TYPE_FORMAT_PREFIX);
            for (i = 0; i < Parameters.NUMBER_OF_TYPES; i++) {
                if (types[i] != null) {
                    try {
                        newType = (Type) types[i];
                        if (formats == null || formats[i] == null) {
                            sysTypes.registerType(typeIds[i], newType);
                        }  else {
                             newFormat = (CellFormat) formats[i];
                             //System.out.println("~~~> " + newFormat.getClass().getName());
                             sysTypes.registerType(typeIds[i], newType, newFormat);
                        }
                    } catch (Exception e) {
                        Common.logMsg("Error Defining Type > " + typeIds[i]
                                    + " class=" + Parameters.getString(Parameters.TYPE_CLASS_PREFIX + i)
                                    + "  " + e.getMessage(), e);
                    }
                }
            }
        }

        // Define User Date Types
        for (i = 0; i < Parameters.DATE_TYPE_TABLE_SIZE; i++) {
		    name = Parameters.getString(Parameters.PROPERTY_DATE_TYPE_NAME + i);
		    format = Parameters.getString(Parameters.PROPERTY_DATE_FORMAT + i);
		    baseTypeStr = Parameters.getString(Parameters.PROPERTY_DATE_BASE_TYPE + i);
		    if (name   != null && ! "".equals(name)
		    &&  format != null && ! "".equals(format)
		    &&  baseTypeStr != null && ! "".equals(baseTypeStr)) {
		        try {
		            typeNum = Parameters.FIRST_USER_DATE_TYPE + i;
		            baseType = Integer.parseInt(baseTypeStr);
		            newFormat = ReTypeManger.getFormatFor(format);

		            newType = new TypeDateWrapper(sysTypes.getType(baseType), format);

		            sysTypes.registerType(typeNum, newType, newFormat);
                } catch (Exception e) {
                    Common.logMsg("Error Defining Type > " + name
                                + "  " + e.getMessage(), e);
                }
		    }
		}

    }
    
    public static void loadUserFormats() {
    	
    	int i;
    	CellFormat fmt;
    	ReTypeManger sysTypes = ReTypeManger.getInstance();
        int[] formatIds = Common.readIntPropertiesArray(Parameters.FORMAT_NUMBER_PREFIX, Parameters.NUMBER_OF_FORMATS);
        Object[] formats = Common.readPropertiesArray(formatIds, Parameters.FORMAT_CLASS_PREFIX);
        
        if (formats != null) {
            for (i = 0; i < Parameters.NUMBER_OF_FORMATS; i++) {
            	if (formats[i] != null) {
            		try {
            			fmt = (CellFormat) formats[i];
            			sysTypes.registerFormat(formatIds[i], fmt);
            		} catch (Exception e) {
            			Common.logMsg("Error Defining Type > " + formatIds[i]
            			                                                   + " class=" + Parameters.getString(Parameters.TYPE_CLASS_PREFIX + i)
            			                                                   + "  " + e.getMessage(), e);
            		}
            	}
            }
        }

    }
    	 



//    /**
//     * Checks if the Program can close
//     * @return wether the program can close
//     */
//    protected void quit() {
//        //boolean cont = true;
//        ReFrame[] allFrames = ReFrame.getAllFrames();
//        
//        System.out.println("Edit Rec quit");
//
//        for (int i = allFrames.length - 1; i >= 0; i--) {
//            if (allFrames[i].isPrimaryView()
//            &&  allFrames[i].getDocument() != null) {
//                //if (allFrames[i].canCloseWindow()) {
//                    allFrames[i].doDefaultCloseAction();
//                    //cont = cont && allFrames[i].isClosed();
//                //}
//            }
//        }
//        Common.closeConnection();
//
//        System.exit(0);
//    }


    /**
     * @see net.sf.RecordEditor.utils.common.ReActionHandler#executeAction(int)
     */
    public void executeAction(int action) {

        switch (action) {
            case ReActionHandler.OPEN:
                //System.out.println("--->");
                open.setVisible(true);
                open.setTheBounds();
                open.moveToFront();
                open.requestFocus();
                break;
            default:
        }
    }

    /**
     * @see net.sf.RecordEditor.utils.common.ReActionHandler#isActionAvailable(int)
     */
    public boolean isActionAvailable(int action) {
        return action == ReActionHandler.OPEN
            || super.isActionAvailable(action);
    }


    /**
     * @return Returns the open.
     */
    public OpenFile getOpenFileWindow() {
        return open;
    }

    /**
     * @param openWindow The open to set.
     */
    protected void setOpenFileWindow(OpenFile openWindow, 
    		Action copyAction, 
    		Action compareAction,
    		boolean incWizard) {
        this.open = openWindow;
        
        setupFileMenu(copyAction, compareAction, incWizard);
    }

    /**
	 * @return the viewMenu
	 */
	public final JMenu getViewMenu() {
		return viewMenu;
	}

	/**
     * This Class Executes a user written function. It is initiated from
     * the Local Dropdown Menu
     * 
     * @author Bruce Martin
     *
     */
    public class LocalAction extends AbstractAction {

    	private Plugin extension = null;
    	private String param;
    	
    	public LocalAction(String name, Plugin userClass, String userParam) {
    		super(name);
    		extension = userClass;
    		param = userParam;
    	}
    	
    	@Override
    	public void actionPerformed(ActionEvent event) {
    		
    	    ReFrame actionHandler = ReFrame.getActiveFrame();
    	    if (actionHandler != null) {
    	    	try {
    	    		BaseDisplay display = ((BaseDisplay) actionHandler);
    	    		extension.execute(param, display.getFileView(), display.getSelectedRows());
    	    	} catch (Exception e) { }
    	    }
    	}

    }

    /**
     * Edit a record oriented file
     * @param pgmArgs program arguments
     */
    public static void main(final String[] pgmArgs) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //JFrame.setDefaultLookAndFeelDecorated(true);
                ParseArgs args = new ParseArgs(pgmArgs);
                Common.setReadOnly(true);
                new EditRec(args.getDfltFile(), args.getInitialRow(),
                        CopyBookDbReader.getInstance());
            }
        });
    }
}
