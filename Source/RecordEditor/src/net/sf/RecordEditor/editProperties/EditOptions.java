/*
 * @Author Bruce Martin
 * Created on 24/01/2007 for version 0.60
 *
 * Purpose:
 * Edit RecordEditor Startup options
 *
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - Major rewrite with tags being organised into
 *     logical groups + code removed to EditParams
 *   - JRecord Spun off, code reorg
 */
package net.sf.RecordEditor.editProperties;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import net.sf.JRecord.Common.AbstractManager;
import net.sf.JRecord.External.CopybookLoaderFactory;
import net.sf.JRecord.External.CopybookWriterManager;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.JRecord.Numeric.ConversionManager;
import net.sf.JRecord.Types.Type;
import net.sf.RecordEditor.edit.util.CopybookLoaderFactoryDB;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.Parameters;
import net.sf.RecordEditor.utils.swing.BasePanel;

/**
 * Edit RecordEditor Startup options
 *
 * @author Bruce Martin
 *
 */
public class EditOptions {

    private static final int PROGRAM_DESCRIPTION_HEIGHT = 400;
    private EditParams params = new EditParams();

    private JFrame frame = new JFrame("Record Editor Options Editor");
    private JEditorPane programDescription;

    private JTabbedPane mainTabbed = new JTabbedPane();
	private JTabbedPane propertiesTabbed = new JTabbedPane();
	private JTabbedPane jdbcTabbed  = new JTabbedPane();
	private JTabbedPane jarsTabbed  = new JTabbedPane();
	private JTabbedPane userTabbed  = new JTabbedPane();
	private JTabbedPane looksTabbed = new JTabbedPane();
	//private JTextArea msgFld = new JTextArea("");

	private AbstractAction save = new AbstractAction("Save", Common.getRecordIcon(Common.ID_SAVE_ICON)) {
	    public void actionPerformed(ActionEvent e) {
	        params.writeProperties();
	        params.writeJarFiles();
	    }
	};

	private String description
		= "<h1>Edit RecordEditor Properties Editor</h1>"
		+ "This program lets you edit the <b>RecordEditor</b> properties files "
		+ "and jar files."
		+ "<br>There is little validation, so be very careful what changes you make."
		+ "<br>Any changes will not take affect until the next time"
		+ "the <b>RecordEditor</b> / <b>Layout Editor</b> is run again."
		+ "<br><br>There are 4 basic tab types in the program: "
		+ "<table border=\"1\" cellpadding=\"3\">"
		+ "<tr><TD><b>Properties</b></td>"
		   +  "<td>Lets you update system properties like"
		   +  "<b>Screen Position, Directories, Other Options</b></td></tr>"
		+ "<tr><td><b>JDBC Properties</b></td><td>Lets you set / update the Database (JDBC) "
		        + "Connection properties "
		        + "<br>to the <b>RecordEditor</b> backend DB </td></tr>"
		+ "<tr><td><b>Jars</b></td><td>These options let you update "
		    + "the various JAR (java libraries) used by the <b>RecordEditor</b>."
		    + "<br>The only time you should need to do this "
		    + "is if you are adding your own code to the <b>RecordEditor</b><br>or installing Velocity.</td></tr>"
		+ "<tr><td><b>Extensions</b></td><td>These option let you define your own Types, formats to "
		   + "the <b>RecordEditor</b>.</td></tr>"
		+ "<tr><td><b>Looks</b></td><td>This option lets you define the look and feel of the RecordEditor</td</tr>"
		+ "</table>"
		+ "<br><br><b>Files being updated are:</b>"
		+ "<br>Full Editor Jars File=" + CommonCode.FULL_EDITOR_JAR_FILE
        + "<br>Editor Jars File=" + CommonCode.EDITOR_JAR_FILE
        + "<br>Properties file=" + Parameters.getPropertyFileName();

//    tabbed.addTab("Copybook Loaders", loadersPnl);
//    tabbed.addTab("User Types", typePnl);
//    tabbed.addTab("User Formats", formatPnl);

	private String screenLocationDescription
		= "<h1>Screen positioning Properties</h1>"
		+ "The properties on this panel are for setting the amount "
		+ "of space to be left around the edge of the <b>RecordEditor</b>."
		+ "<br>The editor can start using the full screen or any part "
		+ "of the screen that you desire.";
    private String[][] screenLocationParams = {
            {"spaceAtBottomOfScreen", "Space to be left at the bottom of the screen.", null},
            {"spaceAtTopOfScreen", "Space to be left at the top of the screen.", null},
            {"spaceAtLeftOfScreen", "Space to be left at the left of the screen.", null},
            {"spaceAtRightOfScreen", "Space to be left at the Right of the screen.", null},
    };

	private String directoryDescription
		= "<h1>Directories</h1>"
		+ "The properties on this panel are for the various directories "
		+ "used by the <b>RecordEditor</b>";

    private String[][] directoryParams = {
            {"HelpDir",	"Directory holding the help files", null},
            {"DefaultFileDirectory",	"Directory where the Editor Starts in (if no file specified)", null},
            {"DefaultCobolDirectory", "The Directory where Cobol Copybooks are stored.", null},
            {Parameters.VELOCITY_TEMPLATE_DIRECTORY, "Velocity Template directory (Editor)", null},
            {Parameters.VELOCITY_COPYBOOK_DIRECTORY, "Velocity Template directory (Copybooks)", null},
            {Parameters.COPYBOOK_DIRECTORY, "Directory to read / write file copybooks to", null},
            {Parameters.COMPARE_SAVE_DIRECTORY, "Compare Save Directory",null},
            {Parameters.FILTER_SAVE_DIRECTORY, "Filter Save Directory", null},
            {Parameters.SORT_TREE_SAVE_DIRECTORY, "Sort Tree Save Directory", null},
            {Parameters.RECORD_TREE_SAVE_DIRECTORY, "Record Tree Save Directory", null},
            {Parameters.COPY_SAVE_DIRECTORY, "Copy Save Directory",null},

    };

    private String otherDescription
    	= "<H1>Other Properties</h1>"
    	+ "This panels lists the other non database Properties";

    private String[][] otherParams = {
            {"UserInitilizeClass", "This User written class will be invoked when the <b>RecordEditor</b starts.", null},
            {"DateFormat", "Date Format String eg dd/MM/yy or dd.MMM.yy. the field is case sensitive", null},
            {Parameters.PROPERTY_TEST_MODE, "Weather we are running automated Tests (Marathon ?) or not ", null},
            {Parameters.BRING_LOG_TO_FRONT, "Bring Log to the Front if Data is written to it", null},
            {Parameters.INVALID_FILE_CHARS, "Characters that are invalid in a file Name", null},
            {Parameters.FILE_REPLACEMENT_CHAR, "Char to Replace invalid Filename Chars", null},
            {Parameters.ASTERIX_IN_FILE_NAME, "Allow the asterix ('*') character in file Names", null},
            {Parameters.PREFERED_AS_DEFAULT, "Default to prefered layout", null},
            {Parameters.WARN_BINARY_FIELDS_DEFAULT, "Warn the user if Binary-Fields and Structure=Default", null},
            {"SignificantCharInFiles.1", "Number of characters to use when looking up record layouts (small)", null},
            {"SignificantCharInFiles.2", "Number of characters to use when looking up record layouts (medium)", null},
            {"SignificantCharInFiles.3", "Number of characters to use when looking up record layouts (large)", null},
    };

    private String layoutWizardParamsDescription
	= "<H1>Layout Wizard Properties</h1>"
	+ "This panels holds various Field Search options used by the Layout Wizard";

    private String[][] layoutWizardParams = {
            {Parameters.FS_RUN_AUTOMATIC, "Weather to Run the field search Automatically or not ", null},
            {Parameters.FS_MAINFRAME_ZONED, "Look for Mainframe Zoned numeric fields", null},
            {Parameters.FS_PC_ZONED, "Look for PC Zoned numeric fields (Cobol PIC 9 fields)", null},
            {Parameters.FS_COMP3, "Look for comp-3 fields", null},
            {Parameters.FS_COMP_BIG_ENDIAN, "Look for Big Endian Binary", null},
            {Parameters.FS_COMP_Little_ENDIAN, "Look for Little Endian Binary", null},
   };

    private EditPropertiesPanel screenPosPnl
    	= new EditPropertiesPanel(params, screenLocationDescription, screenLocationParams);
    private EditPropertiesPanel directoryPnl
    	= new EditPropertiesPanel(params, directoryDescription, directoryParams);
    private EditPropertiesPanel otherPnl
		= new EditPropertiesPanel(params, otherDescription, otherParams);
    private EditPropertiesPanel layoutWizardPnl
		= new EditPropertiesPanel(params, layoutWizardParamsDescription, layoutWizardParams);

    private EditJdbcParamsPanel jdbcParamsPnl = new EditJdbcParamsPanel(params, params.jdbcJars);

    private EditJarsPanel jdbcPnl = new EditJarsPanel(params,
            "<h1>JDBC Jars</h1>This panel lets you change the "
          + "JDBC (Java Database Conectivity) Jars that are needed by "
          + "the <b>RecordEditor</b>."
          + "<br><br>If you click on a row in the table, the fields at the bottom "
          + "will be updated with the values from the selected row.<br>"
          + "You can update values either using the fields at the bottom "
          + "of the screen or directly into the table itself.",
          params.jdbcJars,
            "jdbc."
            );
    private EditJarsPanel systemPnl = new EditJarsPanel(params,
            "<h1>System Jars</h1>This panel lets you change the "
          + "Jars supplied with the <b>RecordEditor</b> like cb2xml."
          + "<br>These jars are used by the <b>RecordEditor</b> but are written "
          + "and maintained by other people",
            params.systemJars,
            ""
            );
    private EditJarsPanel optionalPnl = new EditJarsPanel(params,
            "<h1>Optional Jars</h1>This panel lets you specify jars "
          + "the <b>full editor</b> needs but the light weight editor does not.",
            params.optionalJars,
            "optional."
            );
    private EditJarsPanel userPnl = new EditJarsPanel(params,
            "<h1>User Jars</h1>This panel lets you specify your own jars "
          + "<br>These jars will be used when the <b>RecordEditor</b starts. "
          + "<br>When adding your own jars, <br>you should also invoke your own "
          + "initialisation class via the <i>UserInitilizeClass</i> property "
          + "on the other screen.",
            params.userJars,
            "User."
            );

    private static String jarsNote = "<b>Note:</b>You will also need to add the jar with your class "
	+ "to the <b>User Jars</b> tab.";

    private static String loaderDescription
		= "<h2>Copybook Loaders</h2>"
		+ "This tab is for defining user written <b>Copybook Loader's</b> to the <b>RecordEditor</b>."
		+ "<br>A <b>Copybook Loader's</b> is a Java Class that can load "
		+ "a copybook from a file into the <b>RecordEditor</b>."
		+ "<br><br><b>Copybook Loader's</b> are used in <ul>"
		+ "<li>Load Copybook function in the <b>LayoutEditor</b>"
		+ "<li>Cobol Editor</ul><br>"
		+ jarsNote;

    private static String pluginDescription
		= "<h2>Plugins</h2>"
		+ "This tab is for defining user written <b>Functions</b> that will listed on the <br/><b>Plugin</b> "
		+ "Drop down menu in the   <b>RecordEditor</b>.<br> "
		+ "Local Functions must implement the Plugin interface";

    private static String optionDescription 
    	= "<h2>Default Options</h2>"
    	+ "This Tab is for defining the default value for several Combo Box's used by the Package"
    	+ "<br/><br/>Click on a row to change the default Value";
    
    private static final String[] LOADER_COLUMN_HEADINGS = {"Loader Name", "Loader Class"};
    private static final String[] LOADER_COLUMN_NAMES = {
            Parameters.PROPERTY_COPYBOOK_NAME_PREFIX, Parameters.PROPERTY_COPYBOOK_CLASS_PREFIX
    };
    private static final String[] PLUGIN_COLUMN_HEADINGS = {"Name", "Class Name", "Parameter"};
    private static final String[] PLUGIN_COLUMN_NAMES = {
        Parameters.PROPERTY_PLUGIN_FUNC_NAME, 
        Parameters.PROPERTY_PLUGIN_FUNC_CLASS,
        Parameters.PROPERTY_PLUGIN_FUNC_PARAM
    };

    private static String typeDescription
		= "<h2>Type Definition</h2>"
		+ "This tab for defining user written <b>Type's</b> to the <b>RecordEditor</b>."
		+ "<br>A <b>Type's</b> is a Java Class that converts a fields "
		+ "between the external representation and the internal Java Representation."
		+ "<br>Columns in the Table are <br><table>"
		+ "<tr><td>Type Number</td><td>Unique number used to identify the type."
		+ "It should be between " + Type.USER_RANGE_START + " and "
		    + (Type.USER_RANGE_START + Type.DEFAULT_USER_RANGE_SIZE - 1) + "</td></tr>"
		+ "<tr><td>Type Name</td><td>Name of the Type</td></tr>"
		+ "<tr><td>Type Class</td><td>Java Type class.</td></tr>"
		+ "<tr><td>Format Class</td><td>Class that implements table cell Formating. Leave blank if there is not one</td></tr>"
		+ "</table><br>"
		+ jarsNote;
    private static String formatDescription
		= "<h2>Format Definition</h2>"
		    + "This tab for defining user written <b>Format's</b> to the <b>RecordEditor</b>."
		    + "<br><b>Format's</b> define TableCellRenders and TableCellEditors for a field."
		    + "<br><br>You may want to use Format to edit Dates using a Date Popup";


    private static final String[] TYPE_COLUMN_HEADINGS = {"Type Number", "Type Name", "Type Class", "Format Class"};
    private static final String[] TYPE_COLUMN_NAMES = {
            Parameters.TYPE_NUMBER_PREFIX, Parameters.TYPE_NAME_PREFIX,
            Parameters.TYPE_CLASS_PREFIX, Parameters.TYPE_FORMAT_PREFIX
    };

    private static final String[] FORMAT_COLUMN_HEADINGS = {"Format Number", "Format Name", "Format Class"};
    private static final String[] FORMAT_COLUMN_NAMES = {
            Parameters.FORMAT_NUMBER_PREFIX, Parameters.FORMAT_NAME_PREFIX,
            Parameters.FORMAT_CLASS_PREFIX
    };

    private EditPropertiesTblPanel loadersPnl
    	= new  EditPropertiesTblPanel(params, loaderDescription,
    	       LOADER_COLUMN_NAMES, LOADER_COLUMN_HEADINGS,
    	       Parameters.NUMBER_OF_LOADERS);
    private EditPropertiesTblPanel pluginPnl
		= new EditPropertiesTblPanel(params, pluginDescription,
			  PLUGIN_COLUMN_NAMES, PLUGIN_COLUMN_HEADINGS,
			  Parameters.NUMBER_OF_USER_FUNCTIONS);
   private EditPropertiesTblPanel typePnl
		= new EditPropertiesTblPanel(params, typeDescription,
		      TYPE_COLUMN_NAMES, TYPE_COLUMN_HEADINGS,
		      Parameters.NUMBER_OF_TYPES);
    private EditPropertiesTblPanel formatPnl
		= new EditPropertiesTblPanel(params, formatDescription,
		        FORMAT_COLUMN_NAMES, FORMAT_COLUMN_HEADINGS,
	      Parameters.NUMBER_OF_FORMATS);

    private static String[][] defaultDetails = {
    		{Parameters.DEFAULT_COPYBOOK_READER,	"The default copybook reader"},
    		{Parameters.DEFAULT_COPYBOOK_WRITER,	"The default copybook writer"},
    		{Parameters.DEFAULT_DATABASE,					"The default Database to use"},
    		{Parameters.DEFAULT_IO,								"The default IO Routine to use"},
       		{Parameters.DEFAULT_BINARY,						"The default Binary Encoding"},
   };
    

    private static  ComboBoxModel[] defaultModels = new ComboBoxModel[5];
    static {
	    CopybookLoaderFactory loaders = CopybookLoaderFactoryDB.getInstance();
	    DefaultComboBoxModel mdl = new DefaultComboBoxModel();
	    int i;
	    for (i = 0; i < loaders.getNumberofLoaders(); i++) {
	    	mdl.addElement(loaders.getName(i));
	    }
	    defaultModels[0] = mdl;
	    
	    CopybookWriterManager manager = CopybookWriterManager.getInstance();
	    String s;
	    mdl = new DefaultComboBoxModel();
	    for (i = 0; i < manager.getNumberOfEntries(); i++) {
	    	s = manager.getName(i);
	    	if (s != null && ! "".equals(s)) {
	    		mdl.addElement(s);
	    	}
	    }
	    defaultModels[1] = getManagerModel(CopybookWriterManager.getInstance());
	    
	    String[] ids = Common.getSourceId();
	    mdl = new DefaultComboBoxModel();
	    
	    for (i = 0; i < ids.length; i++) {
	    	if (ids[i] != null && ! "".equals(ids[i])) {
	    		mdl.addElement(ids[i]);
	    	}
	    }
	    defaultModels[2] = mdl;
	    
	    defaultModels[3] = getManagerModel(LineIOProvider.getInstance());
	    defaultModels[4] = getManagerModel(ConversionManager.getInstance());
    }
		
    
    private static ComboBoxModel getManagerModel(AbstractManager manager) {
	    String s;
	    DefaultComboBoxModel mdl = new DefaultComboBoxModel();
	    for (int i = 0; i < manager.getNumberOfEntries(); i++) {
	    	s = manager.getName(i);
	    	if (s != null && ! "".equals(s)) {
	    		mdl.addElement(s);
	    	}
	    }
	    return mdl;
    }

    /**
	 * @param theDefaultDetails the defaultDetails to set
	 */
	public static final void setDefaultDetails(
			String[][] theDefaultDetails, ComboBoxModel[] theDefaultModels) {

		defaultDetails = theDefaultDetails;
		defaultModels = theDefaultModels;
	}

	/**
     * Edit the record Editor Parameter and Jar files
     */
    public EditOptions() {
        this(true, true, true);
    }

    /**
     * @param terminateOnExit terminate java on exit
     *
     * Edit the record Editor Parameter and Jar files
     *
     */
    public EditOptions(final boolean terminateOnExit, boolean includeJDBC, boolean includeWizardOptions) {
        super();

        init_100_ScreenFields(terminateOnExit);
        init_200_Screen(includeJDBC, includeWizardOptions);
    }



    /**
     * Initialise screen fields
     *
     * @param terminateOnExit terminate jave on exit from class
     */
    private void init_100_ScreenFields(boolean terminateOnExit) {

       save.putValue(AbstractAction.SHORT_DESCRIPTION, "Save ...");

       programDescription = new JEditorPane("text/html", description);
       jdbcPnl.setInitialValues();
       systemPnl.setInitialValues();
       optionalPnl.setInitialValues();
       userPnl.setInitialValues();
       jdbcParamsPnl.buildJarCombo();

       mainTabbed.setTabPlacement(JTabbedPane.LEFT);

       if (terminateOnExit) {
           frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       } else {
           frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       }

       frame.addWindowListener(new WindowAdapter() {
           public void windowClosing(WindowEvent e) {
               params.writeProperties();
               params.writeJarFiles();
           }
       });
    }
 
    /**
     * Setup the screen
     *
     */
    private void init_200_Screen(boolean includeJDBC, boolean includeWizardOptions) {
        JPanel pnl = new JPanel();
        JPanel topPnl = new JPanel();
        JToolBar toolBar = new JToolBar();

        toolBar.add(save);

        propertiesTabbed.addTab("Screen Position", screenPosPnl);
        propertiesTabbed.addTab("Directories", directoryPnl);
        propertiesTabbed.addTab("Other Options", otherPnl);
        
        if (includeWizardOptions) {
        	propertiesTabbed.addTab("Layout Wizard Options", layoutWizardPnl);
        }
        
        propertiesTabbed.addTab("Defaults", 
        		new EditDefaults(params, optionDescription, 
        				defaultDetails, defaultModels
        ));

        jarsTabbed.addTab("System Jars", systemPnl);
        jarsTabbed.addTab("Optional Jars", optionalPnl);
        

        userTabbed.addTab("User Jars", userPnl);
        userTabbed.addTab("Copybook Loaders", loadersPnl);
        userTabbed.addTab("Plugins", pluginPnl);
        userTabbed.addTab("User Types", typePnl);
        userTabbed.addTab("User Formats", formatPnl);
        if (includeJDBC) {
        	userTabbed.addTab("Date Types", new EditDateTypes(params));
        }

        looksTabbed.addTab("Look and Feel", new LooksPanel(params));
        looksTabbed.addTab("Icons", new EditIcons(params));

        mainTabbed.addTab("Description", init_310_Screen());
        mainTabbed.addTab("Properties", propertiesTabbed);
        if (includeJDBC) {
        	jdbcTabbed.addTab("JDBC Jars", jdbcPnl);
        	jdbcTabbed.addTab("JDBC Properties", jdbcParamsPnl);
        	mainTabbed.addTab("JDBC Parameters", jdbcTabbed);
        }
        mainTabbed.addTab("Jars", jarsTabbed);
        mainTabbed.addTab("Extensions", userTabbed);
        mainTabbed.addTab("Looks", looksTabbed);

        topPnl.setLayout(new BorderLayout());
        topPnl.add("North", toolBar);
        //topPnl.add("Center", tips);
        topPnl.add("South", new JPanel());
        pnl.setLayout(new BorderLayout());
        pnl.add("North", topPnl);
        pnl.add("Center", mainTabbed);
        pnl.add("South", params.msgFld);
        frame.getContentPane().add(pnl);

        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Build Program description panel
     * @return Program description panel
     */
    private BasePanel init_310_Screen() {
        BasePanel pnl = new BasePanel();

        pnl.addComponent(1, 5, PROGRAM_DESCRIPTION_HEIGHT, BasePanel.GAP1,
		        BasePanel.FULL, BasePanel.FULL,
				new JScrollPane(programDescription));

        return pnl;
    }


    /**
     *  Run the options editor
     * @param args program arguments
     */
    public static void main(String[] args) {

     	boolean jdbc = ! (args != null && args.length > 0 && "nojdbc".equalsIgnoreCase(args[args.length-1]));
 
        new EditOptions(true, jdbc, true);
    }
}
