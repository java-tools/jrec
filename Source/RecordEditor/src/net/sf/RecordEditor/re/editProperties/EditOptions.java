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
package net.sf.RecordEditor.re.editProperties;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import net.sf.JRecord.External.CopybookWriterManager;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.JRecord.Numeric.ConversionManager;
import net.sf.JRecord.Types.Type;
import net.sf.RecordEditor.re.util.CopybookLoaderFactoryDB;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.lang.ReAbstractAction;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.params.ProgramOptions;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.ComboBoxs.EnglishStrModel;

/**
 * Edit RecordEditor Startup options
 *
 * @author Bruce Martin
 *
 */

public class EditOptions {

    private static final int PROGRAM_DESCRIPTION_HEIGHT
            = Math.min(
                    SwingUtils.NORMAL_FIELD_HEIGHT * 21,
                    Toolkit.getDefaultToolkit().getScreenSize().height * 3 / 5);
    private EditParams params = new EditParams();

    private JFrame frame = new JFrame("Record Editor Options Editor");
    private JEditorPane programDescription;

    private JTabbedPane mainTabbed = new JTabbedPane();
    private JTabbedPane propertiesTabbed = new JTabbedPane();
    private JTabbedPane xmlTabbed   = new JTabbedPane();
    private JTabbedPane jdbcTabbed  = new JTabbedPane();
    private JTabbedPane jarsTabbed  = new JTabbedPane();
    private JTabbedPane userTabbed  = new JTabbedPane();
    private JTabbedPane looksTabbed = new JTabbedPane();
    //private JTextArea msgFld = new JTextArea("");

    @SuppressWarnings("serial")
    private AbstractAction saveBtn = new ReAbstractAction("Save", Common.ID_SAVE_ICON) {
        public void actionPerformed(ActionEvent e) {
        	save();
            params.writeProperties();
            params.writeJarFiles();
        }
    };

    private String description
        = LangConversion.convertId(LangConversion.ST_MESSAGE, "EditProps_PnlDescription",

          "<h2>Edit RecordEditor Properties Editor</h2>"
        + "This program lets you edit the <b>RecordEditor</b> properties files "
        + "and jar files."
        + "<br>There is little validation, so be very careful what changes you make."
        + "<br>Any changes will not take affect until the next time"
        + "the <b>RecordEditor</b> / <b>Layout Editor</b> is run again."
        + "<br><br>There are 5 basic tab types in the program: "
        + "<table border=\"1\" cellpadding=\"3\">"
        + "<tr><TD><b>Properties</b></td>"
           +  "<td>Lets you update system properties like "
           +  "<b>Screen Position, Directories, Other Options, Wizard Option and Big File Options</b></td></tr>"
        + "<tr><TD><b>Properties</b></td>"
               +  "<td>Lets you update system properties like "
               +  "<b>Screen Position, Directories, Other Options, Wizard Option and Big File Options</b></td></tr>"
        + "<tr><td><b>Xml</b></td><td>Xml Xslt properties / jars "
        + "<tr><td><b>Jars</b></td><td>These options let you update "
            + "the various JAR (java libraries) used by the <b>RecordEditor</b>."
            + "<br>The only time you should need to do this "
            + "is if you are adding your own code to the <b>RecordEditor</b> or installing Velocity.</td></tr>"
        + "<tr><td><b>Extensions</b></td><td>These option let you define your own Types, formats to "
           + "the <b>RecordEditor</b>.</td></tr>"
        + "<tr><td><b>Looks</b></td><td>This option lets you define the look and feel of the RecordEditor</td</tr>"
        + "</table>"
        + "<br><br><b>Files being updated are:</b><pre>"
        + "<br>System       Jars File={0}"
        + "<br>System JDBC  Jars File={1}"
        + "<br>  User       Jars File={2}"
       //+ "<br>Editor Jars File=" + CommonCode.EDITOR_JAR_FILE
        + "<br>       Properties file={3}</pre>",
        new Object[] {
            CommonCode.SYSTEM_JAR_FILE,
            CommonCode.SYSTEM_JDBC_JAR_FILE,
            CommonCode.USER_JAR_FILE,
            Parameters.getPropertyFileName()
        });

//    tabbed.addTab("Copybook Loaders", loadersPnl);
//    tabbed.addTab("User Types", typePnl);
//    tabbed.addTab("User Formats", formatPnl);

    private String directoryDescription
        = LangConversion.convertId(LangConversion.ST_MESSAGE, "EditProps_Directories",

        "<h2>Directories</h2>"
        + "The properties on this panel are for the various directories "
        + "used by the <b>RecordEditor</b>");

    private Object[][] directoryParams1 = {
            {"HelpDir",	"Directory holding the help files", null, EditPropertiesPnl.FLD_DIR, null},
            {"DefaultFileDirectory",	"Directory where the Editor Starts in (if no file specified)", null, EditPropertiesPnl.FLD_DIR, null},
            {"DefaultCobolDirectory", "The Directory where Cobol Copybooks are stored.", null, EditPropertiesPnl.FLD_DIR, null},
            {Parameters.VELOCITY_TEMPLATE_DIRECTORY, "Velocity Template directory (Editor)", null, EditPropertiesPnl.FLD_DIR, null},
            {Parameters.VELOCITY_COPYBOOK_DIRECTORY, "Velocity Template directory (Copybooks)", null, EditPropertiesPnl.FLD_DIR, null},
            {Parameters.COPYBOOK_DIRECTORY, "Directory to read / write file copybooks to", null, EditPropertiesPnl.FLD_DIR, null},
            {Parameters.XSLT_TEMPLATE_DIRECTORY, "Xslt Template directory (Editor)", null, EditPropertiesPnl.FLD_DIR, null},
            {Parameters.EXPORT_SCRIPT_DIRECTORY, "Export Script directory", null, EditPropertiesPnl.FLD_DIR, null},
            {Parameters.SCRIPT_DIRECTORY, "Extension Script directory", null, EditPropertiesPnl.FLD_DIR, null},
    };


    private Object[][] directoryParams2 = {
            {Parameters.COMPARE_SAVE_DIRECTORY,  "Compare Save Directory",     null, EditPropertiesPnl.FLD_DIR, null},
            {Parameters.FILTER_SAVE_DIRECTORY,   "Filter Save Directory",      null, EditPropertiesPnl.FLD_DIR, null},
            {Parameters.FIELD_SAVE_DIRECTORY,     "Fields used Save Directory",null, EditPropertiesPnl.FLD_DIR, null},
            {Parameters.SORT_TREE_SAVE_DIRECTORY, "Sort Tree Save Directory",  null, EditPropertiesPnl.FLD_DIR, null},
            {Parameters.RECORD_TREE_SAVE_DIRECTORY, "Record Tree Save Directory",    null, EditPropertiesPnl.FLD_DIR, null},
            {Parameters.COPY_SAVE_DIRECTORY,      "Copy Save Directory",             null, EditPropertiesPnl.FLD_DIR, null},
            {Parameters.LAYOUT_EXPORT_DIRECTORY,  "Layout Velocity Export Directory",null, EditPropertiesPnl.FLD_DIR, null},
    };

    private String testDescription
        = LangConversion.convertId(LangConversion.ST_MESSAGE, "EditProps_Test",

          "<h2>Test Properties</h2>"
        + "The options on this screen are used in Testing the Editor.");

    private Object[][] testParams = {
            {Parameters.PROPERTY_TEST_MODE, "Weather we are running automated Tests (Marathon ?) or not", null, EditPropertiesPnl.FLD_BOOLEAN, "Test Mode"},
 //           {Parameters.BRING_LOG_TO_FRONT, "Bring Log to the Front if Data is written to it", null, EditPropertiesPnl.FLD_BOOLEAN, "Bring log to Front"}, // Checked
 //           {Parameters.ASTERIX_IN_FILE_NAME, "Allow the asterix ('*') character in file Names", null, EditPropertiesPnl.FLD_BOOLEAN, null},
 //           {Parameters.PREFERED_AS_DEFAULT, "Default to prefered layout", null, EditPropertiesPnl.FLD_BOOLEAN, null},
            {Parameters.WARN_BINARY_FIELDS_DEFAULT, "Warn the user if Binary-Fields and Structure=Default", null, EditPropertiesPnl.FLD_BOOLEAN, "Warn on Structure change"}, // CHECK
            {Parameters.PROPERTY_LOAD_FILE_BACKGROUND, "Load File in Background thread", null, EditPropertiesPnl.FLD_BOOLEAN, "Load In background"}, // Checked
            {Parameters.USE_NEW_TREE_EXPANSION, "Use New Tree Expansion", null, EditPropertiesPnl.FLD_BOOLEAN,  "Use New Tree Expansion"},
            {Parameters.SEARCH_ALL_FIELDS, "Search: All Fields", null, EditPropertiesPnl.FLD_BOOLEAN,  "On Search Screen default to \"All Fields\""},
            {Parameters.NAME_FIELDS, "Add names to screen Components", null, EditPropertiesPnl.FLD_BOOLEAN,  "Add names to JComponents for use by testing tools"},
            {Parameters.LOG_TEXT_FIELDS,  "Keep a record of all Text Fields going through the Language section. This can be used to generate the language conversion file (GetText PO)", null, EditPropertiesPnl.FLD_BOOLEAN,  "Record Text Fields",},
            {Parameters.SPECIAL_FIND_BTN_NAME,  "This option adds >> to the search button (on the find search screen) to better identifier it in testing", null, EditPropertiesPnl.FLD_BOOLEAN,  "Rename Search btn",},
            {Parameters.INCLUDE_TYPE_NAME,  "Include the type name on the record screen", null, EditPropertiesPnl.FLD_BOOLEAN,  "Include Type Name on Record Screen",},
//            {Parameters.SHOW_ALL_EXPORT_OPTIONS, "Show all export panels", null, EditPropertiesPnl.FLD_BOOLEAN,  "Show all export panels on the export Screen"},
//            {Parameters.DEL_SELECTED_WITH_DEL_KEY, "Delete Selected Rows using the delete key", null, EditPropertiesPnl.FLD_BOOLEAN,  "Delete Selected with delete key"},
//            {Parameters.WARN_WHEN_USING_DEL_KEY,  "Warn the user before deleteing Selected Rows using the delete key", null, EditPropertiesPnl.FLD_BOOLEAN,  "Warn user with delete key"},
    };

    private String behaviourDescription
    = LangConversion.convertId(LangConversion.ST_MESSAGE, "EditProps_Behaviour",

      "<h2>Behavior Properties</h2>"
    + "The options on this screen affect the behavior of the editor.<br/>"
    + "<b>Bring Log to the Front</b>: Wether the log file will be brought to the fron when "
    + "data is written to it.<br/>"
    + "<b>Default to prefered</b>: Wether to use the prefered layout by default when editting<br/>"
    + "<b>Show all export panels</b>: Wether to show all export options or just the option selected<br/>");

    private Object[][] behaviourParams = {
        {Parameters.BRING_LOG_TO_FRONT, "Bring Log to the Front if Data is written to it", null, EditPropertiesPnl.FLD_BOOLEAN, "Bring log to Front"}, // Checked
        {Parameters.ASTERIX_IN_FILE_NAME, "Allow the asterix ('*') character in file Names", null, EditPropertiesPnl.FLD_BOOLEAN, null},
        {Parameters.PREFERED_AS_DEFAULT, "Default to prefered layout", null, EditPropertiesPnl.FLD_BOOLEAN, null},
        {Parameters.SHOW_ALL_EXPORT_OPTIONS, "Show all export panels", null, EditPropertiesPnl.FLD_BOOLEAN,  "Show all export panels on the export Screen"},
        {Parameters.DEL_SELECTED_WITH_DEL_KEY, "Delete Selected Rows using the delete key", null, EditPropertiesPnl.FLD_BOOLEAN,  "Delete Selected rows with the delete key"},
        {Parameters.WARN_WHEN_USING_DEL_KEY,  "Warn the user before deleteing Selected Rows using the delete key", null, EditPropertiesPnl.FLD_BOOLEAN,  "Warn when deleteing rows via delete key"},
        {Parameters.USE_FILE_WIZARD,  "Use File Wizard when no Layout is known for the file", null, EditPropertiesPnl.FLD_BOOLEAN, "Use file Wizard"},
        {Parameters.HIGHLIGHT_MISSING_TRANSLATIONS,  "Highlight text for which there is no translation by adding a # to the start of the text", null, EditPropertiesPnl.FLD_BOOLEAN,  "Highlight missing translations",},
        {Parameters.SEPERATE_WINDOWS,  "Create views in seperate windows instead of tabs on the file panel", null, EditPropertiesPnl.FLD_BOOLEAN,  "Create Screens in seperate Windows",},
        {Parameters.SHOW_RECORDEDITOR_TIPS,  "Show RecordEditor Tips on program startup", null, EditPropertiesPnl.FLD_BOOLEAN,  "Show RecordEditor Tips",},
        {Parameters.EDIT_RAW_TEXT,  "Allow the user to edit file as Raw Text ??", null, EditPropertiesPnl.FLD_BOOLEAN,  "Allow Editting Raw Text",},
        {Parameters.CSV_SHOW_FILECHOOSER_OPTIONS,  "Show File Options on Csv Open Screen", null, EditPropertiesPnl.FLD_BOOLEAN,  "Show File Options on Csv Open",},
   };

    private String fileDescription
    = LangConversion.convertId(LangConversion.ST_MESSAGE, "EditProps_FileParams",

      "<h2>File Propertie 2s</h2>"
    + "This panels lists various File related parameters.");

    private Object[][] fileParams = {
            {"UserInitilizeClass", "This User written class will be invoked when the <b>RecordEditor</b starts.", EditPropertiesPnl.FLD_TEXT, "User Init Class"},
            {Parameters.INVALID_FILE_CHARS, "Characters that are invalid in a file Name", null, EditPropertiesPnl.FLD_TEXT, null},
            {Parameters.FILE_REPLACEMENT_CHAR, "Char to Replace invalid Filename Chars", null, EditPropertiesPnl.FLD_TEXT, null},
            {"DateFormat", "Date Format String eg dd/MM/yy or dd.MMM.yy. the field is case sensitive, it uses Java date Formatting", null, EditPropertiesPnl.FLD_DATE, "Date Format"},
            {"SignificantCharInFiles.1", "Number of characters to use when looking up record layouts (small)", null, EditPropertiesPnl.FLD_INT, "Significant chars 1"},
            {"SignificantCharInFiles.2", "Number of characters to use when looking up record layouts (medium)", null, EditPropertiesPnl.FLD_INT, "Significant chars 2"},
            {"SignificantCharInFiles.3", "Number of characters to use when looking up record layouts (large)", null, EditPropertiesPnl.FLD_INT, "Significant chars 3"},
    };

    private String layoutWizardParamsDescription
    = LangConversion.convertId(LangConversion.ST_MESSAGE, "EditProps_WizardParams",
      "<h2>Layout Wizard Properties</h2>"
    + "This panels holds various Field Search options used by the Layout Wizard");

    private Object[][] layoutWizardParams = {
            {Parameters.FS_RUN_AUTOMATIC, "Weather to Run the field search Automatically or not", null, EditPropertiesPnl.FLD_BOOLEAN, "Run the field search Automatically"}, // Check
            {Parameters.FS_MAINFRAME_ZONED, "Look for Mainframe Zoned numeric fields", null, EditPropertiesPnl.FLD_BOOLEAN, null},
            {Parameters.FS_PC_ZONED, "Look for PC Zoned numeric fields (Cobol PIC 9 fields)", null, EditPropertiesPnl.FLD_BOOLEAN, null},
            {Parameters.FS_COMP3, "Look for comp-3 fields", null, EditPropertiesPnl.FLD_BOOLEAN, null},
            {Parameters.FS_COMP_BIG_ENDIAN, "Look for Big Endian Binary", null, EditPropertiesPnl.FLD_BOOLEAN, null},
            {Parameters.FS_COMP_LITTLE_ENDIAN, "Look for Little Endian Binary", null, EditPropertiesPnl.FLD_BOOLEAN, null},
   };


    private String bigModelDescription
        = LangConversion.convertId(LangConversion.ST_MESSAGE, "EditProps_BigFiles",

          "<h2>Big Model Properties</h2>"
        + "This panels lists parameters for the \"Big File\" Data Models.\n"
        + "You can use these options to optimise the Read Time for very big files");

//    private String[][] bigModelParams = {
//            {Parameters.PROPERTY_BIG_FILE_PERCENT, "File Size to Memory Percent to Start using the Big-File-Model", null},
//            {Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "Big-File-Model Memory Chunks (KB)", null},
//            {Parameters.PROPERTY_BIG_FILE_COMPRESS_OPT, "Big-File-Model compress option (N - No, R - Read, F - Read (fast cpu), S - Space, Y - Yes)", null},
//            {Parameters.PROPERTY_BIG_FILE_FILTER_LIMIT, "Big-File Filter/Tree limit (thousands)", null},
//            {Parameters.PROPERTY_BIG_FILE_DISK_FLAG, "Force Storing chunks on Disk (Used in Testing)", null},
//            {Parameters.PROPERTY_BIG_FILE_USE_SPECIAL_FIXED_MODEL, "Use Fixed Length Model (Used in Testing)", null},
//            {Parameters.PROPERTY_BIG_FILE_LARGE_VB, "Use Large VB Model (Y/N)", null},
//            {Parameters.PROPERTY_LOAD_FILE_BACKGROUND, "Load File in Background thread (default Y)", null},
//    };

    private String[][] COMPRESS_OPTION = {{"N", "No"}, {"R", "Read"}, {"F", "Read (fast cpu)"}, {"S", "Space"}, {"Y", "Yes"}};
    private String[][] YNT_OPTION = {{"Y", "Yes"}, {"N", "No"}, {"T", "Test"}};
    private Object[][] bigModelParams = {
            {Parameters.PROPERTY_BIG_FILE_PERCENT, "File Size to Memory Percent to Start using the Big-File-Model", null, EditPropertiesPnl.FLD_INT, "Big File Percentage"},
            {Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "Big-File-Model Memory Chunks (KB) default=1000", null, EditPropertiesPnl.FLD_INT, "Chunk Size (KB)"},
            {Parameters.PROPERTY_BIG_FILE_COMPRESS_OPT, "Big-File-Model compress option (Default Read)", null, EditPropertiesPnl.FLD_LIST, "Compress Option", COMPRESS_OPTION},
            {Parameters.PROPERTY_BIG_FILE_FILTER_LIMIT, "Big-File Filter/Tree limit (thousands)", null, EditPropertiesPnl.FLD_INT, null},
            {Parameters.PROPERTY_BIG_FILE_DISK_FLAG, "Force Storing chunks on Disk (Used in Testing)", null, EditPropertiesPnl.FLD_BOOLEAN, "Storing chunks on Disk"},
            {Parameters.PROPERTY_BIG_FILE_USE_SPECIAL_FIXED_MODEL, "Use Fixed Length Model (Used in Testing)", null, EditPropertiesPnl.FLD_BOOLEAN, "Use Fixed Length Model"}, // Checked
            {Parameters.PROPERTY_BIG_FILE_LARGE_VB, "Use Large VB Model (Default Yes)", null, EditPropertiesPnl.FLD_LIST, "Use Large VB Model", YNT_OPTION}, // Checked
            {Parameters.PROPERTY_LOAD_FILE_BACKGROUND, "Load File in Background thread", null, EditPropertiesPnl.FLD_BOOLEAN, "Load In background"}, // Checked
    };

   private EditPropertiesPnl directoryPnl1
        = new EditPropertiesPnl(params, directoryDescription, directoryParams1);
    private EditPropertiesPnl directoryPnl2
        = new EditPropertiesPnl(params, directoryDescription, directoryParams2);
    private EditPropertiesPnl testPnl
        = new EditPropertiesPnl(params, testDescription, testParams);
    private EditPropertiesPnl behaviourPnl
         = new EditPropertiesPnl(params, behaviourDescription, behaviourParams);
    private EditPropertiesPnl file2Pnl
         = new EditPropertiesPnl(params, fileDescription, fileParams);
    private EditPropertiesPnl layoutWizardPnl
        = new EditPropertiesPnl(params, layoutWizardParamsDescription, layoutWizardParams);
    private EditPropertiesPnl bigModelPnl
        = new EditPropertiesPnl(params, bigModelDescription, bigModelParams);

    private String xsltDescription
        = LangConversion.convertId(LangConversion.ST_MESSAGE, "EditProps_Xslt",

          "<h2>Xslt Properties</h2>"
        + "This panels let you specify XSLT related jars and the XSLT transform class<br>"
        + "For Saxon or Xalan, you can just enter Saxon or Xalan, Or you can enter<pre>"
        + "        net.sf.saxon.TransformerFactoryImpl\n"
        + "    or  org.apache.xalan.processor.TransformerFactoryImpl</pre>");

    private Object[][] xsltParams = {

            {Parameters.XSLT_ENGINE, "Xslt Transform factory class", null, EditPropertiesPnl.FLD_TEXT, null},
    };
    private EditPropertiesPnl xsltPnl
        = new EditPropertiesPnl(params, xsltDescription, xsltParams);

    private EditJarsPanel xsltJarsPnl = new EditJarsPanel(
            params,   "EditProps_XsltJars",
            "<h2>Xslt Jars</h2>This panel lets you specify your XSLT jars",
            params.xsltJars,
            "xslt",
            true);


    private String csvDescription
        = LangConversion.convertId(LangConversion.ST_MESSAGE, "EditProps_CsvParams",
          "<h2>Csv Editor Properties</h2>"
        + "This panels holds Csv-Editor specific options<br>");

    private Object[][] csvParams = {

            {Parameters.CSV_LOOK_4_FIXED_WIDTH, "Check for binary Fixed Width", null, EditPropertiesPnl.FLD_BOOLEAN, "Should the program check for binary Fixed Width Files ???"},
    };
    private EditPropertiesPnl csvPnl
        = new EditPropertiesPnl(params, csvDescription, csvParams);

    private String poDescription
    	= LangConversion.convertId(LangConversion.ST_MESSAGE, "EditProps_CsvParams",
    			"<h2>GetText-Po Properties</h2>"
    		  + "This panels holds GetText-PO editor specific options<br>");

    String[][] poChildPosOptions = {{Parameters.CHILD_SCREEN_RIGHT, "Right"}, {Parameters.CHILD_SCREEN_BOTTOM, "Bottom"},};
    private Object[][] poParams = {
            {Parameters.PO_EDIT_CHILD_SCREEN_POS, "Position of Child Screen", null, EditPropertiesPnl.FLD_LIST, null, poChildPosOptions},
            {Parameters.PO_EDIT_OPEN_FUZZY_VIEW, "Open Fuzzy / Blank View", null, EditPropertiesPnl.FLD_BOOLEAN, "Open a view containing Just the Fuzzy / Blank translations"},
    };


    private EditPropertiesPnl poPnl
    	= new EditPropertiesPnl(params, poDescription, poParams);

    private EditJdbcParamsPanel jdbcParamsPnl = new EditJdbcParamsPanel(params, params.jdbcJars);

    private EditJarsPanel jdbcPnl = new EditJarsPanel(
            params,
            "EditProps_JdbcJars",
            "<h2>JDBC Jars</h2>This panel lets you change the "
          + "JDBC (Java Database Conectivity) Jars that are needed by "
          + "the <b>RecordEditor</b>."
          + "<br><br>If you click on a row in the table, the fields at the bottom "
          + "will be updated with the values from the selected row.<br>"
          + "You can update values either using the fields at the bottom "
          + "of the screen or directly into the table itself.",
          params.jdbcJars,
            "jdbc.",
            true);
    private EditJarsPanel systemPnl = new EditJarsPanel(
            params, "EditProps_Jars",
            "<h2>System Jars</h2>This panel lets you change the "
          + "Jars supplied with the <b>RecordEditor</b> like cb2xml."
          + "<br>These jars are used by the <b>RecordEditor</b> but are written "
          + "and maintained by other people",
            params.systemJars,
            "",
            false);
    private EditJarsPanel optionalPnl = new EditJarsPanel(
            params, "EditProps_OptionalJars",
            "<h2>Optional Jars</h2>This panel lets you specify your own jars. <br/>"
          + "This could include User code or scripting languages (JRuby.jar, Jython.jar etc).",
            params.optionalJars,
            "optional.",
            true
            );
    private EditJarsPanel userPnl = new EditJarsPanel(
            params, "EditProps_UserJars",
            "<h2>User Jars</h2>This panel lets you specify your own jars "
          + "<br>These jars will be used when the <b>RecordEditor</b starts. "
          + "<br>When adding your own jars, <br>you should also invoke your own "
          + "initialisation class via the <i>UserInitilizeClass</i> property "
          + "on the other screen.",
            params.userJars,
            "User.",
            true
            );

    private static String jarsNote = LangConversion.convertId(LangConversion.ST_MESSAGE, "EditProps_JarsNote",

       "<b>Note:</b>You will also need to add the jar with your class "
    + "to the <b>User Jars</b> tab.");

    private static String loaderDescription
        = LangConversion.convertId(LangConversion.ST_MESSAGE, "EditProps_CopyBook_Loaders",
          "<h2>Copybook Loaders</h2>"
        + "This tab is for defining user written <b>Copybook Loader's</b> to the <b>RecordEditor</b>."
        + "<br>A <b>Copybook Loader's</b> is a Java Class that can load "
        + "a copybook from a file into the <b>RecordEditor</b>."
        + "<br><br><b>Copybook Loader's</b> are used in <ul>"
        + "<li>Load Copybook function in the <b>LayoutEditor</b>"
        + "<li>Cobol Editor</ul><br>")
        + jarsNote;

    private static String pluginDescription
        =  LangConversion.convertId(LangConversion.ST_MESSAGE, "EditProps_Plugin",
          "<h2>Plugins</h2>"
        + "This tab is for defining user written <b>Functions</b> that will listed on the <br/><b>Plugin</b> "
        + "Drop down menu in the   <b>RecordEditor</b>.<br> "
        + "Local Functions must implement the Plugin interface");

    private static String optionDescription
        = LangConversion.convertId(LangConversion.ST_MESSAGE, "EditProps_DefaultOpts",
          "<h2>Default Options</h2>"
        + "This Tab is for defining the default value for several Combo Box's used by the Package"
        + "<br/><br/>Click on a row to change the default Value");

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
        = LangConversion.convertId(LangConversion.ST_MESSAGE, "EditProps_TypeDef",

          "<h2>Type Definition</h2>"
        + "This tab for defining user written <b>Type's</b> to the <b>RecordEditor</b>."
        + "<br>A <b>Type's</b> is a Java Class that converts a fields "
        + "between the external representation and the internal Java Representation."
        + "<br>Columns in the Table are <br><table>"
        + "<tr><td>Type Number</td><td>Unique number used to identify the type. "
        + "It should be between {0} and {1}</td></tr>"
        + "<tr><td>Type Name</td><td>Name of the Type</td></tr>"
        + "<tr><td>Type Class</td><td>Java Type class.</td></tr>"
        + "<tr><td>Format Class</td><td>Class that implements table cell Formating. Leave blank if there is not one</td></tr>"
        + "</table><br>"
        + jarsNote,

        new Object[] {
             Type.USER_RANGE_START,
             (Type.USER_RANGE_START + Type.DEFAULT_USER_RANGE_SIZE - 1)
        });
    private static String formatDescription
        = LangConversion.convertId(LangConversion.ST_MESSAGE, "EditProps_Format",
              "<h2>Format Definition</h2>"
            + "This tab for defining user written <b>Format's</b> to the <b>RecordEditor</b>."
            + "<br><b>Format's</b> define TableCellRenders and TableCellEditors for a field."
            + "<br><br>You may want to use Format to edit Dates using a Date Popup");


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
            {Parameters.DEFAULT_DATABASE,			"The default Database to use"},
            {Parameters.DEFAULT_IO,					"The default IO Routine to use"},
            {Parameters.DEFAULT_BINARY,				"The default Binary Encoding"},
   };

    private String screenLocationDescription
        = LangConversion.convertId(LangConversion.ST_MESSAGE, "EditProps_ScreenLocation",

          "<h2>Program initial Size</h2>"
        + "This screen controls the program size when it inially opens. "
        + "The options are <b>Maximised</b>, <b>Last Size</b>,<br/>"
        + "<b>Space around Edges</b> and <b>Initial Size</b>.<br/>"
        + "The <b>retrieve screen size</b> button at the bottom of the screen."
        + "retrieves the current program Size.");
//    private String[][] screenLocationParams = {
//            {"spaceAtBottomOfScreen", "Space to be left at the bottom of the screen.", null},
//            {"spaceAtTopOfScreen", "Space to be left at the top of the screen.", null},
//            {"spaceAtLeftOfScreen", "Space to be left at the left of the screen.", null},
//            {"spaceAtRightOfScreen", "Space to be left at the Right of the screen.", null},
//    };

    private String applId = getApplId();


    private static final String[][] SIZE_OPTION = {
            {String.valueOf(ProgramOptions.SIZE_MAXIMISED), "Maximized"},
            {String.valueOf(ProgramOptions.SIZE_LAST), "Last Screen Size"},
            {String.valueOf(ProgramOptions.SIZE_SPACE_AROUND), "Use Space around parameters"},
            {String.valueOf(ProgramOptions.SIZE_SPECIFIED), "Height, Width Below"},
    };

    private Object[][] screenLocationParams = {
            {Parameters.SCREEN_SIZE_OPTION, "Program Initial Size", null, EditPropertiesPnl.FLD_LIST,  "Size of the program when it opens", SIZE_OPTION},
            {"spaceAtBottomOfScreen", "Space to be left at the bottom of the screen.", null, EditPropertiesPnl.FLD_INT, null},
            {"spaceAtTopOfScreen", "Space to be left at the top of the screen.", null, EditPropertiesPnl.FLD_INT, null},
            {"spaceAtLeftOfScreen", "Space to be left at the left of the screen.", null, EditPropertiesPnl.FLD_INT, null},
            {"spaceAtRightOfScreen", "Space to be left at the Right of the screen.", null, EditPropertiesPnl.FLD_INT, null},
            {"", "", null, EditPropertiesPnl.FLD_EMPTY, null},
            {applId + Parameters.SCREEN_START_HEIGHT, "Screen Height", null, EditPropertiesPnl.FLD_INT, null},
            {applId + Parameters.SCREEN_START_WIDTH,  "Screen Width",  null, EditPropertiesPnl.FLD_INT, null},
            {"", "", null, EditPropertiesPnl.FLD_RETRIEVE_SIZE, null}
    };

    private EditPropertiesPnl screenPosPnl
    = new EditPropertiesPnl(params, screenLocationDescription, screenLocationParams);


    private static  EnglishStrModel[] defaultModels = new EnglishStrModel[5];
    static {
//        CopybookLoaderFactory loaders = CopybookLoaderFactoryDB.getInstance();
//        DefaultComboBoxModel mdl = new DefaultComboBoxModel();
//        int i;
//        for (i = 0; i < loaders.getNumberOfEntries(); i++) {
//            mdl.addElement(loaders.getName(i));
//        }

        defaultModels[0] =  DefaultOptModel.newModel(CopybookLoaderFactoryDB.getInstance());;

//        CopybookWriterManager manager = CopybookWriterManager.getInstance();
//        String s;
//        mdl = new DefaultComboBoxModel();
//        for (i = 0; i < manager.getNumberOfEntries(); i++) {
//            s = manager.getName(i);
//            if (s != null && ! "".equals(s)) {
//                mdl.addElement(s);
//            }
//        }
        defaultModels[1] = DefaultOptModel.newModel(CopybookWriterManager.getInstance());

//        String[] ids = Common.getSourceId();
//        mdl = new DefaultComboBoxModel();
//
//        for (i = 0; i < ids.length; i++) {
//            if (ids[i] != null && ! "".equals(ids[i])) {
//                mdl.addElement(ids[i]);
//            }
//        }
        defaultModels[2] = DefaultOptModel.newModel( Common.getSourceId());

        defaultModels[3] = DefaultOptModel.newModel(LineIOProvider.getInstance());
        defaultModels[4] = DefaultOptModel.newModel(ConversionManager.getInstance());
    }


//    private static ComboBoxModel getManagerModel(AbstractManager manager) {
//        String s;
//        DefaultComboBoxModel mdl = new DefaultComboBoxModel();
//        for (int i = 0; i < manager.getNumberOfEntries(); i++) {
//            s = manager.getName(i);
//            if (s != null && ! "".equals(s)) {
//                mdl.addElement(s);
//            }
//        }
//        return mdl;
//    }

    /**
     * @param theDefaultDetails the defaultDetails to set
     */
    public static final void setDefaultDetails(
            String[][] theDefaultDetails, EnglishStrModel[] theDefaultModels) {

        defaultDetails = theDefaultDetails;
        defaultModels = theDefaultModels;
    }

//	/**
//     * Edit the record Editor Parameter and Jar files
//     */
//    public EditOptions() {
//        this(true, true, true, true);
//    }

    /**
     * @param terminateOnExit terminate java on exit
     *
     * Edit the record Editor Parameter and Jar files
     *
     */
    public EditOptions(final boolean terminateOnExit, boolean includeJDBC, boolean includeWizardOptions) {
        this(terminateOnExit, includeJDBC, includeWizardOptions, true);
    }


    private EditPropertiesPnl[] propertiesPnl = {
    		 directoryPnl1,
    		 directoryPnl2,
    		 testPnl,
    		 behaviourPnl,
    		 file2Pnl,
    		 layoutWizardPnl,
    		 bigModelPnl,
    		 xsltPnl,
    		 csvPnl,
    		 poPnl,
    		 screenPosPnl,
    };

    /**
     * @param terminateOnExit terminate java on exit
     *
     * Edit the record Editor Parameter and Jar files
     *
     */
    public EditOptions(final boolean terminateOnExit, boolean includeJDBC, boolean includeWizardOptions, boolean display) {
        super();

        init_100_ScreenFields(terminateOnExit);
        init_200_Screen(includeJDBC, includeWizardOptions);
        if (display) {
            displayScreen();
        }
    }



    /**
     * Initialise screen fields
     *
     * @param terminateOnExit terminate jave on exit from class
     */
    private void init_100_ScreenFields(boolean terminateOnExit) {

       saveBtn.putValue(AbstractAction.SHORT_DESCRIPTION, LangConversion.convert(LangConversion.ST_MESSAGE, "Save ..."));

       programDescription = new JEditorPane("text/html", description);
       xsltJarsPnl.setInitialValues();
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
        	   save();
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

        String[][] defaultDescription = defaultDetails.clone();

        for (int i = 0; i < defaultDescription.length; i++) {
        	defaultDescription[i][1] = LangConversion.convert(LangConversion.ST_MESSAGE, defaultDescription[i][1]);
        }

        toolBar.add(saveBtn);

        SwingUtils.addTab(propertiesTabbed, "EditOpts_Properties","Directories", directoryPnl1);
        SwingUtils.addTab(propertiesTabbed, "EditOpts_Properties","Save Directories", directoryPnl2);
        SwingUtils.addTab(propertiesTabbed, "EditOpts_Properties","Test", testPnl);
        SwingUtils.addTab(propertiesTabbed, "EditOpts_Properties","Behaviour", behaviourPnl);
        SwingUtils.addTab(propertiesTabbed, "EditOpts_Properties","File Options", file2Pnl);

        if (includeWizardOptions) {
            SwingUtils.addTab(propertiesTabbed, "EditOpts_Properties","Layout Wizard", layoutWizardPnl);
        }
           SwingUtils.addTab(propertiesTabbed, "EditOpts_Properties","Big Model", bigModelPnl);

        SwingUtils.addTab(propertiesTabbed, "EditOpts_Properties","Defaults",
                new EditDefaults(params, optionDescription,
                		defaultDescription, defaultModels
        ));
        SwingUtils.addTab(xmlTabbed, "EditOpts_Xml","Xslt Options", xsltPnl);
        SwingUtils.addTab(xmlTabbed, "EditOpts_Xml","Xslt Jars", xsltJarsPnl);

        SwingUtils.addTab(jarsTabbed, "EditOpts_Jars","System Jars", systemPnl);
        SwingUtils.addTab(jarsTabbed, "EditOpts_Jars","Optional Jars", optionalPnl);


        SwingUtils.addTab(userTabbed, "EditOpts_User","User Jars", userPnl);
        SwingUtils.addTab(userTabbed, "EditOpts_User","Copybook Loaders", loadersPnl);
        SwingUtils.addTab(userTabbed, "EditOpts_User","Plugins", pluginPnl);
        SwingUtils.addTab(userTabbed, "EditOpts_User","User Types", typePnl);
        SwingUtils.addTab(userTabbed, "EditOpts_User","User Formats", formatPnl);
        if (includeJDBC) {
            SwingUtils.addTab(propertiesTabbed, "EditOpts_User","Date Types", new EditDateTypes(params));
        }

        SwingUtils.addTab(looksTabbed, "EditOpts_User","Look and Feel", new LooksPanel(params));
        SwingUtils.addTab(looksTabbed, "EditOpts_User","Icons", new EditIcons(params));
        SwingUtils.addTab(looksTabbed, "EditOpts_User","Initial Program Size", screenPosPnl);

        addMainTab("Description", init_310_Screen());
        addMainTab("Properties", propertiesTabbed);

        System.out.println("Application Id: " + getApplId());
        if (Common.CSV_PROGRAM_ID.equals(getApplId())) {
            addMainTab("Csv Options", csvPnl);
        }
        if (Common.OPTIONS.getTextPoPresent.isSelected()) {
            addMainTab("Special Formats", poPnl);
        }
//poPnl
        addMainTab("Xml", xmlTabbed);
        if (includeJDBC) {
            SwingUtils.addTab(jdbcTabbed, "EditOpts_JDBC","JDBC Jars", jdbcPnl);
            SwingUtils.addTab(jdbcTabbed, "EditOpts_JDBC","JDBC Properties", jdbcParamsPnl);
            addMainTab("JDBC Parameters", jdbcTabbed);
        }
        addMainTab("Language", new EditLanguage(params));
        addMainTab("Jars", jarsTabbed);
        addMainTab("Extensions", userTabbed);
        addMainTab("Looks", looksTabbed);

        topPnl.setLayout(new BorderLayout());
        topPnl.add("North", toolBar);
        //topPnl.add("Center", tips);
        topPnl.add("South", new JPanel());
        pnl.setLayout(new BorderLayout());
        pnl.add("North", topPnl);
        pnl.add("Center", mainTabbed);
        pnl.add("South", params.msgFld);
        frame.getContentPane().add(pnl);

//        printTabDetails("mainTabbed", mainTabbed);
//        printTabDetails("propertiesTabbed", propertiesTabbed);
//        printTabDetails("jarsTabbed", jarsTabbed);
//        printTabDetails("userTabbed", userTabbed);
//        printTabDetails("looksTabbed", looksTabbed);
    }


    private void addMainTab(String name, JComponent item) {
        item.setName(name + "Tab");
        SwingUtils.addTab(mainTabbed, "EdProps_MainTab_" + name, name, item);
    }
//    private void printTabDetails(String name, JTabbedPane tab) {
//
//    	System.out.println(name + " " + tab.getTabCount());
//    	for (int i = 0; i < tab.getTabCount(); i++) {
//    		Component c = tab.getComponentAt(i);
//    		if (c != null) {
//	    		System.out.println(name + " " + i
//	    				+ "\t" + c.getPreferredSize().height
//	    				+ "\t" + c.getClass().getName());
//    		} else {
//    			System.out.print('.');
//    		}
//    	}
//
//    }

    private void save() {
    	for (EditPropertiesPnl p : propertiesPnl) {
    		p.save();
    	}
    }

    public EditParams getParams() {
        return params;
    }

    public final void displayScreen() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.pack();
        frame.setBounds(
                frame.getY(), frame.getX(),
                Math.min(frame.getWidth(),
                         screenSize.width  - Common.getSpaceAtRightOfScreen()
                                            - frame.getY()),
                Math.min(frame.getHeight(),
                         screenSize.height - Common.getSpaceAtBottomOfScreen()
                           - SwingUtils.NORMAL_FIELD_HEIGHT * 2
                            - frame.getX()));
        frame.setVisible(true);
    }

    /**
     * Add a component to the startup properties editor
     * @param name Tab name
     * @param component component to add
     */
    public void add(String name, JComponent component) {
        SwingUtils.addTab(mainTabbed, "EditOptions_MainTab", name, component);
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


    private static String getApplId() {
        String ret = "";
        ReMainFrame f = ReMainFrame.getMasterFrame();
        if (f != null) {
            ret = f.getApplicationId();
        }

        return ret;
    }

//    /**
//     *  Run the options editor
//     * @param args program arguments
//     */
//    public static void main(String[] args) {
//
//     	boolean jdbc = ! (args != null && args.length > 0 && "nojdbc".equalsIgnoreCase(args[args.length-1]));
//
//        new EditOptions(true, jdbc, true);
//    }
}
