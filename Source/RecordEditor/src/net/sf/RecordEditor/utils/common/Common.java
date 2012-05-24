/*
 * @Author Bruce Martin
 * Created on 20/03/2005
 *
 * Purpose:
  *
 * Modification log:
 * On 2006/06/28 by Jean-Francois Gagnon:
 *    - Added a constant for the Fujitsu Variable Length
 *      Line IO Provider
 *  2007/01/10 Bruce Martin
 *      Version 0.56 modifications
 *       - Space at variables for space at top, bottom, left right
 *         side of the screen are read from properties file
 *       - support for ReAction's (recordeditor screen actions) added
 *       - procedure calcColumnWidths to calculate JTable column widths
 *         added
 * # Version 0.60 modifications 16 Feb 2007 (Bruce Martin)
 *   - Sort Support (icons, dropdown menus etc)
 *   - new All_Fields, Velocity_Directory, User_init_class and Date_format
 *     constant
 *   - Fixed bugs in calcColumnWidths
 *   - new methods to read PropertyArrays
 *
 *
 */
package net.sf.RecordEditor.utils.common;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.External.CopybookWriterManager;
import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.JRecord.Log.TextLog;

/**
 *
 * Holds common routines for RecordEdit and layoutEdit programs
 *
 * @author Bruce Martin
 * @version 0.521
 */
public final class Common implements Constants {


    /*
     // get metrics from the graphics
    FontMetrics metrics = graphics.getFontMetrics(font);
    // get the height of a line of text in this font and render context
    int hgt = metrics.getHeight();
    // get the advance of my text in this font and render context
    int adv = metrics.stringWidth(text);
    // calculate the size of a box to hold the text with some padding.
    Dimension size = new Dimension(adv+2, hgt+2);
     */

	public static final int BOOLEAN_OPERATOR_OR  = 0;
    public static final int BOOLEAN_OPERATOR_AND = 1;

	public static final ProgramOptions OPTIONS = new ProgramOptions();
	public static final Object MISSING_VALUE = new StringBuilder("");
	public static final Object MISSING_REQUIRED_VALUE = new StringBuilder("");

	public static final Color EMPTY_COLOR = new Color(230, 230, 255);
	public static final Color MISSING_COLOR = new Color(255, 230, 230);

	public static final String COLUMN_LINE_SEP =  "|";

	public static final String[] COMPARISON_OPERATORS =
		{"=", "!=", "<>", ">", ">=",  "<", "<=", STARTS_WITH, CONTAINS};

	public static final String CSV_PROGRAM_ID = "csv";
//	public static final boolean LOG_TO_FRONT = ! ("N".equalsIgnoreCase(
//			Parameters.getString(Parameters.BRING_LOG_TO_FRONT)));
	public static final boolean TEST_MODE = "Y".equalsIgnoreCase(
			Parameters.getString(Parameters.PROPERTY_TEST_MODE));
	public static final boolean NAME_COMPONENTS = "Y".equalsIgnoreCase(
			Parameters.getString(Parameters.NAME_FIELDS));
	public static final boolean RECORD_EDITOR_LAF
		  = Parameters.VAL_RECORD_EDITOR_DEFAULT.equalsIgnoreCase(
			Parameters.getString(Parameters.PROPERTY_LOOKS_CLASS_NAME));
	public static final boolean NIMBUS_LAF ;

	static {
		boolean w = false;
		try {
			String s = System.getProperty("os.name").toLowerCase();
			w = (RECORD_EDITOR_LAF && (s.indexOf("nix") >= 0 || s.indexOf("nux") >= 0));
		} catch (Exception e) {
		}

//		System.out.println(" >>>" + System.getProperty("os.name") + "<<< "
//				+ w + " " + RECORD_EDITOR_LAF);
		NIMBUS_LAF = w || "Nimbus".equalsIgnoreCase(
				Parameters.getString(Parameters.PROPERTY_LOOKS_CLASS_NAME));
	}


//	public static final boolean LOAD_FILE_BACKGROUND_THREAD = ! "N".equalsIgnoreCase(
//			Parameters.getString(Parameters.PROPERTY_LOAD_FILE_BACKGROUND));
//	public static final boolean ASTERIX_IN_FILENAME = "Y".equalsIgnoreCase(
//			Parameters.getString(Parameters.ASTERIX_IN_FILE_NAME));

	private static String jdbcJarNames[] = null;

	private static boolean searchActiveDB = true;
	//private static boolean highlightEmpty = "Y".equals(Parameters.getString(Parameters.PROPERTY_HIGHLIGHT_EMPTY));

	//private static boolean highlightEmptyActive = false;

	/**
	 * record editor Help Screen
	 */
	public static final String HELP_CSV_EDITOR     = "HlpCsv02.htm";
	public static final String HELP_COBOL_EDITOR   = "HlpCe02.htm";
	public static final String HELP_RECORD_MAIN    = "HlpRe02.htm";
	public static final String HELP_RECORD_TABLE   = "HlpRe03.htm";
	public static final String HELP_SINGLE_RECORD  = "HlpRe04.htm";
	public static final String HELP_SEARCH         = "HlpRe05.htm";
	public static final String HELP_FILTER         = "HlpRe06.htm";
	public static final String HELP_SAVE_AS        = "HlpRe07.htm";
	public static final String HELP_SORT           = "HlpRe10.htm";
	public static final String HELP_FIELD_TREE     = "HlpRe11.htm";
	public static final String HELP_SORT_TREE      = "HlpRe12.htm";
	public static final String HELP_RECORD_TREE    = "HlpRe13.htm";
	public static final String HELP_TREE_VIEW      = "HlpRe14.htm";
	public static final String HELP_COLUMN_VIEW    = "HlpRe15.htm";
	public static final String HELP_GENERIC_CSV    = "HlpRe16.htm#HDRGENERICCSV";

	public static final String DATE_FORMAT_DESCRIPTION
		= "Please remember that Date formats are case sensitive:<ul compact>"
		+ "<li>dd   - is 2 charcter day (lowercase d for day)</li>"
		+ "<li>MM   - is 2 charcter month (uppercase M for month)</li>"
		+ "<li>MMM  - is 3 charcter month (uppercase M for month)</li>"
		+ "<li>yy   - is 2 charcter year (lowercase y for month)</li>"
		+ "<li>yyyy - is 4 charcter year (lowercase y for month)</li>"
		+ "</ul><br>use dd/MM/yy for 25/12/98, dd.MM.yyyy for 25.Dec.1998";


	public static final String[] FIELD_SEPERATOR_OPTIONS  = {"<Tab>", "<Space>", ",", ";", ":", "|", "/", "\\", "~", "!", "*", "#", "@"};


	/**
	 * Record Layout Editor Help Screen
	 */
	public static final String HELP_DIFF           = "diff.html";
	public static final String HELP_DIFF_SL        = "diff2.html";
	public static final String HELP_DIFF_TL        = "diff3.html";
	public static final String HELP_MENU           = "HlpLe.htm";
	public static final String HELP_UPGRADE        = "HlpLe.htm#Upgrade";
	//public static final String HELP_LAYOUT_EDIT    = "HlpLe02.htm";
	public static final String HELP_LAYOUT_RECORD_DEF = "HlpLe02.htm";
	public static final String HELP_LAYOUT_CHILD   = "HlpLe02.htm#HDRCHILD";
	public static final String HELP_LAYOUT_FIELD   = "HlpLe02.htm#HDRFIELD";
	public static final String HELP_LAYOUT_RECSEL  = "HlpLe02.htm#HDRRECSEL";
	public static final String HELP_LAYOUT_EXTRA   = "HlpLe02.htm#HDREXTRA";
	public static final String HELP_LAYOUT_RECORD  = "HlpLe03.htm";
	public static final String HELP_LAYOUT_DETAILS = "HlpLe03.htm#RecordDtls";
	public static final String HELP_LAYOUT_LIST    = "HlpLe03.htm#RecordList";
	public static final String HELP_WIZARD         = "HlpLe04.htm";
	public static final String HELP_WIZARD_PNL2    = "HlpLe04.htm#HDRWIZ2";
	public static final String HELP_WIZARD_PNL3    = "HlpLe04.htm#HDRWIZ3";
	public static final String HELP_WIZARD_PNL4    = "HlpLe04.htm#HDRWIZ4";
	public static final String HELP_WIZARD_PNL5    = "HlpLe04.htm#HDRWIZ5";
	public static final String HELP_WIZARD_RECORD_TYPE       = "HlpLe04.htm#HDRWIZRECORDTYPE";
	public static final String HELP_WIZARD_RECORD_NAMES      = "HlpLe04.htm#HDRWIZRECORDNAMES";
	public static final String HELP_WIZARD_RECORD_FIELD_DEF  = "HlpLe04.htm#HDRWIZ2M";
	public static final String HELP_WIZARD_RECORD_FIELD_NAMES= "HlpLe04.htm#HDRWIZ3M";
	public static final String HELP_WIZARD_FILE_STRUCTURE    = "HlpLe04.htm#HDRWIZFILESTRUCTURE";
	public static final String HELP_WIZARD_SAVE    = "HlpLe04.htm#HDRWIZSAVE1";

	public static final String HELP_COMBO_SEARCH   = "HlpLe05.htm#HDRCOMBOSEL";
	public static final String HELP_COMBO_EDIT     = "HlpLe05.htm#HDRCOMBOPNL";
	//public static final String HELP_COMBO_CREATE   = "HlpLe05.htm#HDRCOMBOPNL";
	public static final String HELP_TABLE          = "HlpLe07.htm";
	public static final String HELP_COPYBOOK       = "HlpLe08.htm";
	public static final String HELP_COPYBOOK_CHOOSE= "HlpLe09.htm";
	public static final String HELP_COPY_LAYOUT    = "HlpLe10.htm";

	private static final String DATABASE_NAME       = fix(Parameters.getString(Parameters.DEFAULT_DATABASE));
//	public static final String DEFAULT_IO_NAME       = fix(Parameters.getString(Parameters.DEFAULT_IO));
//	public static final String DEFAULT_BIN_NAME  = fix(Parameters.getString(Parameters.DEFAULT_BINARY));

	private static final int DEFAULT_ROOM_AROUND_SCREEN = 32;
	private static       int spaceAtBottomOfScreen = DEFAULT_ROOM_AROUND_SCREEN;
	private static       int spaceAtLeftOfScreen   = 1;
	private static       int spaceAtRightOfScreen  = 1;
	private static       int spaceAtTopOfScreen    = 1;
	//public static final int SPACE_AT_TOP_OF_SCREEN    = 25;


	public static final long MAX_MEMORY = Runtime.getRuntime().maxMemory();
	public static final BigDecimal MAX_MEMORY_BD = BigDecimal.valueOf(MAX_MEMORY);
 		/* Icon Indexs */
    public static final int ID_SEARCH_ICON    = 0;
    public static final int ID_FILTER_ICON    = 1;
    public static final int ID_SAVE_ICON      = 2;
    public static final int ID_SAVE_AS_ICON   = 3;
    public static final int ID_COPY_ICON      = 4;
    public static final int ID_CUT_ICON       = 5;
    public static final int ID_PASTE_ICON     = 6;
    public static final int ID_PASTE_PRIOR_ICON = 7;
    public static final int ID_NEW_ICON       = 8;
    public static final int ID_SETLENGTH_ICON = 9;
    public static final int ID_DELETE_ICON    = 10;
    public static final int ID_OPEN_ICON      = 12;
    public static final int ID_SORT_ICON      = 13;
    public static final int ID_HELP_ICON      = 11;
    public static final int ID_NEXT_ICON      = 14;
    public static final int ID_PREV_ICON      = 15;
    public static final int ID_PRINT_ICON     = 16;
    public static final int ID_PREF_ICON      = 17;
    public static final int ID_TREE_ICON      = 18;
    public static final int ID_AUTOFIT_ICON   = 19;
    public static final int ID_COLUMN_DTLS_ICON   = 20;
    public static final int ID_COLUMN_VIEW_ICON   = 21;
    public static final int ID_COLUMN_COPY_ICON   = 22;
    public static final int ID_COLUMN_DELETE_ICON = 23;
    public static final int ID_COLUMN_MOVE_ICON   = 24;
    public static final int ID_COLUMN_INSERT_ICON = 25;
    public static final int ID_EDIT_RECORD_ICON   = 26;
    public static final int ID_NEW_UP_ICON        = 27;
    public static final int ID_PASTE_UP_ICON      = 28;
    public static final int ID_EXPORT_ICON        = 29;
    public static final int ID_SAVEAS_CSV_ICON    = 30;
    public static final int ID_SAVEAS_FIXED_ICON  = 31;
    public static final int ID_SAVEAS_HTML_ICON   = 32;
    public static final int ID_SAVEAS_XML_ICON    = 33;
    public static final int ID_SAVEAS_VELOCITY_ICON = 34;
    public static final int ID_SUMMARY_ICON       = 35;
    public static final int ID_SORT_SUM_ICON      = 36;
    public static final int ID_VIEW_RECORD_ICON   = 37;
    public static final int ID_VIEW_TABLE_ICON    = 38;
    public static final int ID_VIEW_COLUMN_ICON   = 39;
    public static final int ID_EXIT_ICON          = 40;
    public static final int ID_GOTO_ICON          = 41;
    public static final int ID_RELOAD_ICON        = 42;
    public static final int ID_WIZARD_ICON        = 43;
    public static final int ID_LAYOUT_CREATE_ICON = 44;
    public static final int ID_LAYOUT_EDIT_ICON   = 45;
    public static final int ID_COMBO_EDIT_ICON    = 46;
    public static final int ID_FILE_SEARCH_ICON   = 47;
    public static final int ID_DIRECTORY_SEARCH_ICON = 48;
    public static final int ID_EXPORT_SCRIPT_ICON  = 49;
    public static final int ID_SCRIPT_ICON  = 49;

    public static final int ID_MAX_ICON       = 51;

    public static final int TI_FIELD_TYPE     = 1;
	public static final int TI_RECORD_TYPE    = 2;
	public static final int TI_SYSTEMS        = 3;
	public static final int TI_FILE_STRUCTURE = 4;
	public static final int TI_FORMAT         = 5;

	//public static final String COPYBOOK_READER =  Parameters.getString(Parameters.DEFAULT_COPYBOOK_READER);
	private static int copybookWriterIndex = -121;

	private static boolean doFree = true;



	public static final int LOOKS_INDEX
		= getIntProperty(0, Parameters.PROPERTY_LOOKS_CLASS_INDEX);

//	public static final int SIGNIFICANT_CHARS_IN_FILES1
//	  = getIntProperty(6, "SignificantCharInFiles.1");
//	public static final int SIGNIFICANT_CHARS_IN_FILES2
//	  = getIntProperty(12, "SignificantCharInFiles.2");
//	public static final int SIGNIFICANT_CHARS_IN_FILES3
//	  = getIntProperty(18, "SignificantCharInFiles.3");
//
//	public static final int LAUCH_EDITOR_IF_MATCH
//	  = getIntProperty(8, "LauchEditorIfMatch");

    public static final int UNKNOWN_SYSTEM  = 0; /* System is unkown */
    //public static final int NULL_INTEGER    = Constants.NULL_INTEGER;
    //public static final int FULL_LINE       = -101;
    public static final int ALL_FIELDS      = -102;
    //private static final int LOGGER_ERROR   = 10;



    private static final boolean USE_PNG;
    private static final String[] ICON_NAMES = {"LeftM", "Left", "Up", "Down",
            									"Right", "RightM" };

    private static ImageIcon[] icon     = new ImageIcon[ICON_NAMES.length - 2];
    private static ImageIcon[] treeIcons= new ImageIcon[ICON_NAMES.length];
    private static ImageIcon[] recIcon  = new ImageIcon[ID_MAX_ICON + 1];
    private static int[] reActionRef    = new int[ReActionHandler.MAX_ACTION + 1];


	@SuppressWarnings("rawtypes")
	private static Class currClass      = Common.class;

    private static AbsSSLogger logger   = null;

	private static boolean yet2Init     = true;
	private static boolean yet2Assign   = true;
	//private static boolean toReadParm   = true;

	private static String[] driver         = new String[NUMBER_OF_COPYBOOK_SOURCES];

	private static String[] commitFlag     = new String[NUMBER_OF_COPYBOOK_SOURCES];
	private static String[] checkpointFlag = new String[NUMBER_OF_COPYBOOK_SOURCES];
	private static String[] dataSource     = new String[NUMBER_OF_COPYBOOK_SOURCES];
	private static String[] readOnlySource = new String[NUMBER_OF_COPYBOOK_SOURCES];
	private static String[] sourceId       = new String[NUMBER_OF_COPYBOOK_SOURCES];
	//static private int driverIdx[]       = new int[idxLimit];
	private static String[] userId         = new String[NUMBER_OF_COPYBOOK_SOURCES];
	private static String[] password       = new String[NUMBER_OF_COPYBOOK_SOURCES];
	private static String[] jdbcMsg        = new String[NUMBER_OF_COPYBOOK_SOURCES];
	private static Connection[] dbConnection = new Connection[NUMBER_OF_COPYBOOK_SOURCES];
	private static Connection[] dbUpdate   = new Connection[NUMBER_OF_COPYBOOK_SOURCES];
	private static boolean[] autoClose     = new boolean[NUMBER_OF_COPYBOOK_SOURCES];
	private static boolean[] dropSemi      = new boolean[NUMBER_OF_COPYBOOK_SOURCES];

	public static final String DATE_FORMAT_STR;
//	public static final String DEFAULT_FILE_DIRECTORY
//			= Parameters.getFileName("DefaultFileDirectory");
//	public static final String DEFAULT_COBOL_DIRECTORY
//			= Parameters.getFileName("DefaultCobolDirectory");
//	public static final String DEFAULT_COPYBOOK_DIRECTORY
//			= Parameters.getFileName(Parameters.COPYBOOK_DIRECTORY);
//	public static final String DEFAULT_VELOCITY_DIRECTORY
//			= Parameters.getFileName(Parameters.VELOCITY_TEMPLATE_DIRECTORY);

	public static final String USER_INIT_CLASS
			= Parameters.getString("UserInitilizeClass");


	public static final String STANDARD_CHARS = "+-.,/?\\!\'\"$%&*@()[]abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	public static final String STANDARD_CHARS0 = "+-.,abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	public static final String STANDARD_CHARS1 = STANDARD_CHARS.substring(STANDARD_CHARS.indexOf('a'));
	public static final String FILE_SEPERATOR  =  System.getProperty("file.separator");

   	public final static String[] FIELD_SEPARATOR_LIST = {
		"<Default>", "<Tab>", "<Space>", ",", ";", ":", "|", "/", "\\", "~", "!", "*", "#", "@", "x'00'", "x'01'", "x'02'", "x'FD'", "x'FE'", "x'FF"
	};
   	public final static String[] FIELD_SEPARATOR_LIST1 = {
		"<Tab>", "<Space>", ",", ";", ":", "|", "/", "\\", "~", "!", "*", "#", "@", "x'00'", "x'01'", "x'02'", "x'FD'", "x'FE'", "x'FF'"
	};
   	public final static String[] FIELD_SEPARATOR_TEXT_LIST = new String[FIELD_SEPARATOR_LIST1.length - 6];
   	public final static String[] FIELD_SEPARATOR_LIST1_VALUES;
   	static {
   		String[] l = FIELD_SEPARATOR_LIST1.clone();

   		l[0] = "\t";
   		l[1] = " ";
   		FIELD_SEPARATOR_LIST1_VALUES = l;

   		System.arraycopy(
   				FIELD_SEPARATOR_LIST1, 0,
   				FIELD_SEPARATOR_TEXT_LIST, 0, FIELD_SEPARATOR_TEXT_LIST.length);
   	}
  	public final static String QUOTE_LIST[] = {
		"<None>", "<Default>", "\"", "'", "`"
	};

 	public final static String QUOTE_VALUES[] = {
		"", "\"", "\"", "'", "`"
	};

	private static int connectionIndex = 0;
	//private static int defaultConnection = -1;
	//private static int currIdx = 0;

	private static boolean readOnly = false;

	private static String htmlDir = null;

	private static final int TABLE_WINDOW_SIZE_TO_CHECK = 200;
	private static final int MINIMUM_MAX_COLUMN_WIDTH   = 100;



	private static String[] reActionNames = new String[ReActionHandler.MAX_ACTION];
	private static String[] reActionDesc  = new String[ReActionHandler.MAX_ACTION];
	private static  boolean velocityAvailable;
	private static  boolean toCheckVelocity = true;

	private static  boolean toWarn = true;


	private static boolean dbDefined;

    static {
        String s = Parameters.getString(Parameters.PROPERTY_PGN_ICONS);
        USE_PNG = (s == null || "Y".equalsIgnoreCase(s));

        s = Parameters.getString("DateFormat");
        if (s != null) {
            try {
                new SimpleDateFormat(s);
            } catch (Exception e) {
                System.out.println("Invalid Date Format: " + s + " " + e.getMessage());
                s = null;
            }
        }
        DATE_FORMAT_STR = s;


    	spaceAtBottomOfScreen = getIntProperty(spaceAtBottomOfScreen,
        		"spaceAtBottomOfScreen");
    	spaceAtTopOfScreen = getIntProperty(spaceAtTopOfScreen,
				"spaceAtTopOfScreen");
    	spaceAtLeftOfScreen = getIntProperty(spaceAtLeftOfScreen,
				"spaceAtLeftOfScreen");
    	spaceAtRightOfScreen = getIntProperty(spaceAtRightOfScreen,
				"spaceAtRightOfScreen");

        for (int i = 0; i < ReActionHandler.MAX_ACTION; i++) {
            reActionNames[i] = "";
            reActionDesc[i]  = null;
        }
        reActionNames[ReActionHandler.CLOSE]        = "Close";
        reActionNames[ReActionHandler.CLOSE_ALL]    = "Close All";
        reActionNames[ReActionHandler.COPY_RECORD]  = "Copy Record(s)";
        //ReActionNames[ReActionHandler.CORRECT_RECORD_LENGTH]     = "Close";
        reActionNames[ReActionHandler.CUT_RECORD]    = "Cut Record(s)";
        reActionNames[ReActionHandler.DELETE]        = "Delete";
        reActionNames[ReActionHandler.DELETE_RECORD] = "Delete Record(s)";
        reActionNames[ReActionHandler.FILTER]        = "Filter";
        reActionNames[ReActionHandler.TABLE_VIEW_SELECTED]  = "Table View (Selected Records)";
        reActionNames[ReActionHandler.RECORD_VIEW_SELECTED] = "Record View (Selected Records)";
        reActionNames[ReActionHandler.COLUMN_VIEW_SELECTED] = "Column View (Selected Records)";
        reActionNames[ReActionHandler.SELECTED_VIEW] = "View Selected Records";
        reActionNames[ReActionHandler.FIND]          = "Find";
        reActionNames[ReActionHandler.HELP]          = "Help";
        reActionNames[ReActionHandler.INSERT_RECORDS]       = "Insert Record(s)";
        reActionNames[ReActionHandler.INSERT_RECORD_PRIOR]  = "Insert Record Prior";
        reActionNames[ReActionHandler.NEW]           = "New";
        reActionNames[ReActionHandler.OPEN]          = "Open";
        reActionNames[ReActionHandler.PRINT]         = "Print";
        reActionNames[ReActionHandler.PRINT_SELECTED]= "Print Selected";
        reActionNames[ReActionHandler.PASTE_RECORD]  = "Paste Record(s)";
        reActionNames[ReActionHandler.PASTE_RECORD_PRIOR] = "Paste Record(s) Prior";
        reActionNames[ReActionHandler.PASTE_TABLE_INSERT] = "Paste Table (Insert)";
        reActionNames[ReActionHandler.PASTE_TABLE_OVERWRITE] = "Paste Table Overwrite";
        reActionNames[ReActionHandler.SAVE]         = "Save";
        reActionNames[ReActionHandler.SAVE_AS]      = "Save As";
        reActionNames[ReActionHandler.EXPORT]       = "Export";
        reActionNames[ReActionHandler.EXPORT_AS_CSV]  = "Export as CSV file";
        reActionNames[ReActionHandler.EXPORT_AS_FIXED]= "Export as Fixed Length file";
        reActionNames[ReActionHandler.EXPORT_AS_HTML] = "Export as HTML 1 tbl";
        reActionNames[ReActionHandler.EXPORT_AS_HTML_TBL_PER_ROW] = "Export as HTML 1 tbl per Row";
        reActionNames[ReActionHandler.EXPORT_HTML_TREE] = "Export as HTML (tree)";
        reActionNames[ReActionHandler.EXPORT_VELOCITY] = "Export using Velocity";
        reActionNames[ReActionHandler.EXPORT_XSLT] = "Export using Xslt";
        reActionNames[ReActionHandler.EXPORT_SCRIPT] = "Export using a Script";
        reActionNames[ReActionHandler.RUN_SCRIPT] = "Run a Script";
        reActionNames[ReActionHandler.SAVE_AS_XML]  = "Save Tree as XML";
        reActionNames[ReActionHandler.SAVE_LAYOUT_XML]  = "Save Layout as XML";

        reActionNames[ReActionHandler.SORT]          = "Sort";
        reActionNames[ReActionHandler.REPEAT_RECORD] = "Repeat Record";
        reActionNames[ReActionHandler.REBUILD_TREE]  = "Rebuild Tree";

        reActionNames[ReActionHandler.BUILD_SORTED_TREE]   = "Sorted Field Tree";
        reActionNames[ReActionHandler.BUILD_FIELD_TREE]    = "Field Based Tree";
        reActionNames[ReActionHandler.BUILD_RECORD_TREE]   = "Record Based Tree";
        reActionNames[ReActionHandler.BUILD_LAYOUT_TREE]   = "Record Layout Tree";
        reActionNames[ReActionHandler.BUILD_LAYOUT_TREE_SELECTED]  = "Record Layout Tree (Selected Records)";
        reActionNames[ReActionHandler.BUILD_XML_TREE_SELECTED]  = "XML Tree (Selected Records)";
        reActionNames[ReActionHandler.FULL_TREE_REBUILD]   = "Complete Rebuild of Tree ";

        reActionNames[ReActionHandler.EXECUTE_SAVED_FILTER]      = "Execute Saved Filter";
        reActionNames[ReActionHandler.EXECUTE_SAVED_SORT_TREE]   = "Execute Sort Tree";
        reActionNames[ReActionHandler.EXECUTE_SAVED_RECORD_TREE] = "Execute Record Tree";
        reActionNames[ReActionHandler.COMPARE_WITH_DISK] = "Compare with Disk";
        reActionNames[ReActionHandler.SHOW_INVALID_ACTIONS] = "Show invalid Records";
        reActionNames[ReActionHandler.AUTOFIT_COLUMNS] = "Recalculate Column widths";


        reActionDesc[ReActionHandler.EXPORT]        = "Export in another format";
        reActionDesc[ReActionHandler.EXPORT_SCRIPT] = "Export using an external Script (Jython, JRuby, JavaScript etc)";
        reActionDesc[ReActionHandler.RUN_SCRIPT]    = "Run an external Script (Jython, JRuby, JavaScript etc)";
        reActionDesc[ReActionHandler.SAVE_AS_XML]   = "Converts a Tree View to XML";
        reActionDesc[ReActionHandler.SAVE_LAYOUT_XML]   = "Save Layout as RecordEditor XML";

        reActionDesc[ReActionHandler.TABLE_VIEW_SELECTED]  = "Create a new view from the selected records";
        reActionDesc[ReActionHandler.RECORD_VIEW_SELECTED] = "Create a new Record view from the selected records";
        reActionDesc[ReActionHandler.COLUMN_VIEW_SELECTED] = "Create a Column view from the selected records";
        reActionDesc[ReActionHandler.COPY_RECORD]   = "Copy selected records to the RecordEditor Clipboard";
        reActionDesc[ReActionHandler.CUT_RECORD]    = "Cut selected records to the RecordEditor Clipboard";
        reActionDesc[ReActionHandler.PASTE_RECORD]  = "Paste records from the RecordEditor Clipboard after the current record";
        reActionDesc[ReActionHandler.PASTE_RECORD_PRIOR] = "Paste records from the RecordEditor Clipboard before the current record";
        reActionDesc[ReActionHandler.PASTE_TABLE_INSERT]  = "Paste Table after the current line/row";
        reActionDesc[ReActionHandler.PASTE_TABLE_OVERWRITE] = "Paste Table over current data (from current position)";
        reActionDesc[ReActionHandler.DELETE_RECORD] = "Delete Selected records";
        reActionDesc[ReActionHandler.REPEAT_RECORD] = "Repeat the record under the cursor";
        reActionDesc[ReActionHandler.REBUILD_TREE]  = "Rebuild the Tree Display";

        reActionNames[ReActionHandler.ADD_ATTRIBUTES]    = "Add Attributes";

        reActionDesc[ReActionHandler.BUILD_SORTED_TREE]  = "Create Tree View by sorting on selected fields";
        reActionDesc[ReActionHandler.BUILD_FIELD_TREE]   = "Create Tree View based on changes in selected fields";
        reActionDesc[ReActionHandler.BUILD_RECORD_TREE]  = "Create Tree View based on Record Hierarchy";
        reActionDesc[ReActionHandler.BUILD_LAYOUT_TREE_SELECTED]  = "Create XML Tree View using the definition in Record Layout "
        		+ "Definition from the currently selected records";
        reActionDesc[ReActionHandler.BUILD_XML_TREE_SELECTED]  = "Create XML Tree View from the currently selected records";
        reActionDesc[ReActionHandler.FULL_TREE_REBUILD]  = "Completely rebuild the Tree from scratch";

        reActionDesc[ReActionHandler.ADD_ATTRIBUTES]     = "Add Attributes to the layout Definition";

        reActionDesc[ReActionHandler.EXECUTE_SAVED_FILTER]     = "Load and Execute a saved filter";
        reActionDesc[ReActionHandler.EXECUTE_SAVED_SORT_TREE]  = "Load and Execute a saved Sort Tree";
        reActionDesc[ReActionHandler.EXECUTE_SAVED_RECORD_TREE]= "Load and Execute a saved Record Tree";
        reActionDesc[ReActionHandler.COMPARE_WITH_DISK]        = "Compare what is being edited with what is stored on disk";
        reActionDesc[ReActionHandler.SHOW_INVALID_ACTIONS]     = "Show Invlid (incomplete) Records (Messages)";
        reActionDesc[ReActionHandler.AUTOFIT_COLUMNS]   = "Calculate the column widths based on data in the columns";
   }

	/**
	 * Static Common procedures
	 *
	 */
	private Common() {

	}


	/**
	 * Thic class stops Editing of a cell in a specified Table
	 *
	 * @param tbl Table which to stop editing
	 */
	public static final void stopCellEditing(final JTable tbl) {

	    if (tbl != null) {
	    	try {
	    		TableCellEditor cellEdit = tbl.getCellEditor();
	    		if (cellEdit != null) {
	    			cellEdit.stopCellEditing();
	    		}
	    	} catch (Exception e) {

	    	}
		}
	}



	/**
	 * Load Drivers etc from the properties file
	 */
	private static final void initVars() {
		if (yet2Assign) {
			int i;
			int j = 0;
			String s, dropSemiStr;

			yet2Assign    = false;

			for (i = 0; i < NUMBER_OF_COPYBOOK_SOURCES; i++) {
			    s = Integer.toString(i);

			    dataSource[j] = Parameters.getString(Parameters.DB_SOURCE + s);

			    if (dataSource[j] != null && ! "".equals(dataSource[j].trim())) {
			    	dropSemiStr = Parameters.getString(Parameters.DB_DROP_SEMI + s);
				    readOnlySource[j] = Parameters.getString(Parameters.DB_READ_ONLY_SOURCE + s);
			        sourceId[j]   = Parameters.getString(Parameters.DB_SOURCE_NAME + s);
			        if (DATABASE_NAME.equalsIgnoreCase(sourceId[j])) {
			        	System.out.println("Setting DB index : " + DATABASE_NAME + " " + j);
			        	connectionIndex = j;
			        }
				    driver[j]     = Parameters.getString(Parameters.DB_DRIVER + s);
			        userId[j]     = Parameters.getString(Parameters.DB_USER + s);
			        password[j]   = Parameters.getString(Parameters.DB_PASSWORD + s);
			        commitFlag[j] = Parameters.getString(Parameters.DB_COMMIT + s);
			        checkpointFlag[j] = Parameters.getString(Parameters.DB_CHECKPOINT + s);
			        autoClose[j]  = "Y".equalsIgnoreCase(Parameters.getString(Parameters.DB_CLOSE_AFTER_EXEC + s));
			        dropSemi[j]   = "Y".equalsIgnoreCase(dropSemiStr)
			                      || (   (dropSemiStr == null ||"".equals(dropSemiStr))
			                    	  && (driver[j] != null && driver[j].toLowerCase().contains("derby") ));


			        boolean expandVars = "y".equalsIgnoreCase(Parameters.getString(Parameters.DB_EXPAND_VARS + s));

			        //System.out.println(" --- " + dataSource[j] + " " + driver[j]);
			        if (expandVars) {
			        	dataSource[j] = fixVars(dataSource[j]);
			        	readOnlySource[j] = fixVars(readOnlySource[j]);
			        	System.out.println(" ---> " + j + " : " + dataSource[j] + " " + readOnlySource[j]);
			        	//System.out.print(" --- "  + dataSource[j]);
		    		}

			        j += 1;
			    }
			    dbConnection[i] = null;
			}

			dbDefined = j > 0;
			for (i = j; i < NUMBER_OF_COPYBOOK_SOURCES; i++) {
			    userId[i] = null;
			    sourceId[i] = "";
			}
		}
	}

	public static String fixVars(String var) {
		if (var != null) {
			var = var.replace("<install>", Parameters.getBaseDirectory());
			var = var.replace("<home>", Parameters.USER_HOME);
	    	if (var.indexOf("<hsqlDB>") >= 0) {
	    		//TODO get dbDir
	    		String hsqlDir=Parameters.getBaseDirectory();
	    		if (isWindows()) {
	    			hsqlDir = Parameters.getPropertiesDirectory();
	    		}
	    		hsqlDir += FILE_SEPERATOR + "Database";
	    		var = var.replace("<hsqlDB>", hsqlDir);

//	    		logMsg("" + var, null);
//	    		logMsg("Hsql Dir: " + hsqlDir, null);
//	    		logMsg("   param: " + var, null);
//	    		logMsg("" + var, null);
	    	}
		}

    	return var;
	}

	private static boolean isWindows() {
		return "\\".equals(FILE_SEPERATOR);
	}


	/**
	 * retrieve a integer resource
	 *
	 * @param defaultValue default value of int
	 * @param resource resource name
	 *
	 * @return requested integer value
	 */
	private static int getIntProperty(int defaultValue, String resource) {
        String s = Parameters.getString(resource);

        if (s != null && !s.equals("")) {
            try {
                defaultValue = Integer.parseInt(s);
            } catch (Exception e) {
            }
        }

        return defaultValue;
    }


	/**
	 * Make a database connection
	 *
	 * @param connectionIdx Database Connection Index to be made
	 *
	 * @throws SQLException SQL Errors received
	 * @throws ClassNotFoundException JDBC Class not found
	 * @throws MalformedURLException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private static final void makeConnection(int connectionIdx)
						throws SQLException, ClassNotFoundException, MalformedURLException, InstantiationException, IllegalAccessException {

		if (readOnlySource[connectionIdx] == null || "".equals(readOnlySource[connectionIdx])) {
			makeConnection(dbConnection, connectionIdx, dataSource, 1, readOnly);
			dbUpdate[connectionIdx] = dbConnection[connectionIdx];
		} else {
			makeConnection(dbConnection, connectionIdx, readOnlySource, 6, true);
		}
		//yet2Init = false;
	}

	/**
	 * Make a connection to DB
	 * @param connectionArray connection array to update
	 * @param connectionIdx connection index to be updated
	 * @param source Data source array
	 * @param trys number of try's to make a connection
	 * @param readOnlyOpt wether it is a readonly connection
	 *
	 * @throws SQLException any SQL error that occurs
	 * @throws ClassNotFoundException class not found error
	 * @throws MalformedURLException invalid URL
	 * @throws InstantiationException error that occurred during initialize
	 * @throws IllegalAccessException
	 */
	private static final void makeConnection(Connection[] connectionArray, int connectionIdx, String[] source, int trys, boolean readOnlyOpt)
	throws SQLException, ClassNotFoundException, MalformedURLException, InstantiationException, IllegalAccessException {

		if (connectionArray[connectionIdx] != null && ! connectionArray[connectionIdx].isClosed()) {
			return;
		}

		//System.out.println("Source > " + source[connectionIdx] + " JDBC Names >> " + jdbcJarNames);
		for (int j = 0; j <= trys; j++) {
			try {
				if (jdbcJarNames == null) {
					Class.forName(driver[connectionIdx]);
					if ((userId[connectionIdx] == null) || ("".equals(userId[connectionIdx]))) {
						connectionArray[connectionIdx] =
							DriverManager.getConnection(source[connectionIdx]);
					} else {
						connectionArray[connectionIdx] =
							DriverManager.getConnection(source[connectionIdx],
									userId[connectionIdx],
									password[connectionIdx]);
					}
					break;
				} else {
					URL[] urls = new URL[jdbcJarNames.length];
					String pass = password[connectionIdx];
					if (password[connectionIdx] == null) {
						pass = "";
					}

//		            System.out.println("-----------------------------------------------------------------");
//		            System.out.println("    Loading jdbcjars ...   " + jdbcJarNames.length);
//		            for (int i = 0; i <  jdbcJarNames.length; j++) {
//		            	System.out.println("\t\t...\t" + jdbcJarNames);
//		            }
//		            System.out.println("-----------------------------------------------------------------");

					for (int i =0; i < jdbcJarNames.length; i++) {
						if (jdbcJarNames[i] != null && ! "".equals(jdbcJarNames[i])) {
							System.out.println("  ### Jdbc Jars: " + jdbcJarNames[i] + " -> " + Parameters.expandVars(jdbcJarNames[i]));
							urls[i] = new URL("file:" + Parameters.expandVars(jdbcJarNames[i]));
						}
					}


					URLClassLoader urlLoader = new URLClassLoader(urls);
					Driver d = (Driver) Class.forName(driver[connectionIdx], true, urlLoader).newInstance();
					//System.out.println("  ### Driver: " + d.getClass().getName());
					Properties p = new Properties();

					p.setProperty("user", userId[connectionIdx]);

					p.setProperty("password", pass);

					connectionArray[connectionIdx] = d.connect(source[connectionIdx], p);

					System.out.println("  ~~~ Connection ok : " + connectionIdx + " " + source[connectionIdx] + " "+ (dbConnection[connectionIdx] != null) );
					//	    	} catch (Exception e) {
					//				e.printStackTrace();
					//			}
				}
				break;
			} catch (SQLException e) {
				if (j >= trys) {
					System.out.println(" ~~~ SQLException > " + connectionIdx + " " + source[connectionIdx] + "< " + e.getMessage());
					e.printStackTrace();
					throw e;
				}
				sleep();
			} catch (InstantiationException e) {
				if (j >= trys) {
					System.out.println(" ~~~ InstantiationException");
					throw e;
				}
				sleep();
			} catch (IllegalAccessException e) {
				if (j >= trys) {
					System.out.println(" ~~~ IllegalAccessException");
					throw e;
				}
				sleep();
			}
		}


		System.out.print("==> " + connectionIdx
		        		 + " " + driver[connectionIdx]
		                 + " " + source[connectionIdx]
		                 + " " + userId[connectionIdx]
		                 + " " + password[connectionIdx] + " ");
	   System.out.println( connectionArray[connectionIdx].getClass().getName()
		                 + " : " + connectionArray[connectionIdx].isReadOnly());

		connectionArray[connectionIdx].setReadOnly(readOnlyOpt);

		yet2Init = false;
		jdbcMsg[connectionIdx] = "";
	}

	private static void sleep()  {
		try {
			Thread.sleep(75);
		} catch (Exception e) {
		}
	}

	/**
	 * wether connection is defined
	 *
	 * @return
	 */
	public static boolean isDbConnectionDefined() {

		initVars();
		return dbDefined;
	}
	/**
	 * Close all Database connection
	 *
	 */
	public static final void closeConnection() {
		System.out.println("Close Connection; init=" + yet2Init);
		if (! yet2Init) {
			boolean hsql = false;
		    int i;

		    for (i = 0; i < NUMBER_OF_COPYBOOK_SOURCES; i++) {
		    	if (dbConnection[i] != null) {
			    	hsql = hsql || "org.hsqldb.jdbcDriver".equals(driver[i]);
			    	System.out.println(" >>> " + i + " ~ " + hsql + " " + driver[i]);
			        closeAConnection(i);
		    	}
		    }

		    if (hsql) {
		    	closeHSQL();
		    }
		}
	}


	/**
	 * Closes one connection id
	 *
	 * @param id connection id
	 */
	public static final void closeAConnection(final int id) {

        if (dbConnection[id] != null) {
            try {
                checkpoint(id);
                dbConnection[id].close();
                dbConnection[id] = null;
                dbUpdate[id] = null;
            } catch (Exception ex1) {
                logMsg("Close DB - " + ex1.getMessage(), null);
            }
        }
	}




	/**
	 * Get a DB connection to the required connection
	 *
	 * @param dbIdx Index (DB Number) of connection
	 * @return Request Connection
	 * @throws java.sql.SQLException the SQL error to the calling program
	 */
	public static final Connection getDBConnection(final int dbIdx)
			throws java.sql.SQLException {

	    initVars();

	    if (dbConnection[dbIdx] == null) {
			try {
				makeConnection(dbIdx);
			} catch (Exception ex) {
				dbConnection[dbIdx] = null;
			    String msg = ex.getMessage();
			    jdbcMsg[dbIdx] = "DataBase Connection error: " + msg;

			    logMsg(jdbcMsg[dbIdx] + "\nConnection Index " + dbIdx
			    		+ "\nConnection ID " + dataSource[dbIdx], ex);
			    throw new java.sql.SQLException(jdbcMsg[dbIdx]);
			}
		}

		return dbConnection[dbIdx];
	}

	public static final Connection getUpdateConnection(final int dbIdx) {

		initVars();
		if (dbUpdate[dbIdx] == null) {
			try {
				if (readOnlySource[dbIdx] == null || "".equals(readOnlySource[dbIdx])) {
					makeConnection(dbIdx);
				} else {
					if (dbConnection[dbIdx] != null) {

						dbConnection[dbIdx] .close();
						dbConnection[dbIdx]  = null;
						dbUpdate[dbIdx] = null;
						closeHSQL();
					}
					makeConnection(dbUpdate, dbIdx, dataSource, 7, false);

					dbConnection[dbIdx] = dbUpdate[dbIdx];
				}
			} catch (Exception ex) {
				dbConnection[dbIdx] = null;
				String msg = ex.getMessage();
				jdbcMsg[dbIdx] = "DataBase Connection error: " + msg;

				logMsg(jdbcMsg[dbIdx] + "\nConnection Index " + dbIdx
						+ "\nConnection ID " + dataSource[dbIdx], ex);
			}
		}

		return dbUpdate[dbIdx];
	}

	@SuppressWarnings("unchecked")
	public static void closeHSQL() {

		System.out.println("Closing HSQL");
		try {
			//  Using reflection to perform:
			//
			//      org.hsqldb.DatabaseManager.closeDatabases(0);
			//

			@SuppressWarnings("rawtypes")
			Class    runClass  = Class.forName("org.hsqldb.DatabaseManager");
	        Method   closeDB   = runClass.getMethod("closeDatabases", new Class[] {int.class});
			Object[] arguments = new Object[]{0};

	        closeDB.invoke(null, arguments);

		} catch (NoClassDefFoundError e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * This methog gets the Database Connection
	 *
	 * @param dbIndex  Database Id
	 *
	 * @return the requested Database connection
	 */
	public static final Connection getDBConnectionLogErrors(final int dbIndex) {
	    try {
	        return getDBConnection(dbIndex);
	    } catch (Exception ex) {
	        logMsg(ex.getMessage(), null);
            return null;
        }
    }


	/**
	 * Returns the user names of the various DB sources
	 *
	 * @return  names of the various DB sources
	 */
	public static final String[] getSourceId() {
		initVars();
		return sourceId;
	}


	/**
	 * Change the DB connection
	 *
	 * @param dbIdx the new Database Index
	 */
	public static final void setConnectionId(final int dbIdx) {

	    if (connectionIndex != dbIdx) {
	        closeAConnection(connectionIndex);
	    }
	    connectionIndex = dbIdx;
	}

	/**
	 * Converts some SQL to a Result Set
	 *
	 * @param pSQL - SQL
	 * @return Result Set
	 * @throws java.sql.SQLException SQL error
	 */
	public static final ResultSet getResultSet(final String pSQL)
		throws java.sql.SQLException {

		return Common.getDBConnection(connectionIndex).createStatement().executeQuery(pSQL);
	}



	/**
	 * perform a commit on a specified DB Id
	 * @param id Database id to be commited
	 */
	public static final void commit(final int id) {

	    if ("Y".equalsIgnoreCase(commitFlag[id])) {
	        try {
	            getDBConnection(id).commit();

	        } catch (Exception ex) {
	            logMsg("Commit - " + ex.getMessage(), null);
	        }
	    }
	}


	/**
	 * perform a commit on a specified DB Id
	 * @param id Database id to be commited
	 */
	public static final void checkpoint(final int id) {

        commit(id);

        if ("Y".equalsIgnoreCase(checkpointFlag[id])) {
            try {

                getDBConnection(id).createStatement().execute("checkpoint");

            } catch (Exception ex) {
                logMsg("checkpoint - " + ex.getMessage(), null);
            }
        }

	}

	public static final void freeConnection(int idx) {
		if (autoClose[idx] && doFree) {
			closeAConnection(idx);
			System.out.println("Free Connection: " + idx);
			if ("org.hsqldb.jdbcDriver".equals(driver[idx])) {
				closeHSQL();
			}
		}
	}

	public static boolean isDropSemi(int idx) {
		return dropSemi[idx];
	}

	/**
	 * This method shuts down HSQLDB Databases
	 */
	public static final void shutdownHSQLdb() {
	    int i;
	    initVars();

	    for (i = 0; i < NUMBER_OF_COPYBOOK_SOURCES; i++) {
	        if (dataSource[i] != null && dataSource[i].startsWith("jdbc:hsqldb:hsql:")) {
	            try {
	                checkpoint(i);

	                getDBConnection(i).createStatement().execute("shutdown");

	            } catch (Exception e) {
                    System.out.println("Shutdown error " + e.getMessage());
                }
	        }
	    }
    }





    /**
     * Read the icons (if not already read)
     *
     */
    private static final void readIcons() {

        if (recIcon[0] == null) {
            int i;
            recIcon[ID_SEARCH_ICON]  = getIcon("Find");
            recIcon[ID_FILTER_ICON]  = getIcon("Filter");
            recIcon[ID_SAVE_ICON]    = getIcon("Save");
            recIcon[ID_SAVE_AS_ICON] = getIcon("SaveAs");

            recIcon[ID_COPY_ICON]    = getIcon("Copy");
            recIcon[ID_CUT_ICON]     = getIcon("Cut");

            recIcon[ID_PASTE_ICON]   = getIcon("Paste");
            recIcon[ID_PASTE_PRIOR_ICON]
                    				 = getIcon("PasteUp"); //getIcon("Paste");
            recIcon[ID_NEW_ICON]     = getIcon("New");
            recIcon[ID_SETLENGTH_ICON]
                    				 = getIcon("SetLength");
            recIcon[ID_DELETE_ICON]  = getIcon("Delete");
            recIcon[ID_OPEN_ICON]    = getIcon("Open");
            recIcon[ID_SORT_ICON]    = getIcon("Sort");
            recIcon[ID_HELP_ICON]    = getIcon("Help");
            recIcon[ID_PRINT_ICON]   = getIcon("Print");
            recIcon[ID_PREF_ICON]    = getIcon("Preferences");
            recIcon[ID_TREE_ICON]    = getIcon("Tree");
            recIcon[ID_AUTOFIT_ICON] = getIcon("AutofitColumns");
            recIcon[ID_COLUMN_DTLS_ICON   ] = getIcon("ColumnDetails");
            recIcon[ID_COLUMN_VIEW_ICON   ] = getIcon("ColumnView");
            recIcon[ID_COLUMN_COPY_ICON   ] = getIcon("CopyColumn");
            recIcon[ID_COLUMN_DELETE_ICON ] = getIcon("DeleteColumn");
            recIcon[ID_COLUMN_MOVE_ICON   ] = getIcon("MoveColumn");
            recIcon[ID_COLUMN_INSERT_ICON ] = getIcon("InsertColumn");
            recIcon[ID_EDIT_RECORD_ICON   ] = getIcon("EditRecord");
            recIcon[ID_NEW_UP_ICON        ] = getIcon("NewUp");
            recIcon[ID_PASTE_UP_ICON      ] = getIcon("PasteUp");
            recIcon[ID_EXPORT_ICON        ] = getIcon("Export");
            recIcon[ID_SAVEAS_CSV_ICON    ] = getIcon("SaveAs_Csv");
            recIcon[ID_SAVEAS_FIXED_ICON  ] = getIcon("SaveAs_Fixed");
            recIcon[ID_SAVEAS_HTML_ICON   ] = getIcon("SaveAs_Html");
            recIcon[ID_SAVEAS_XML_ICON    ] = getIcon("SaveAs_xml");
            recIcon[ID_SAVEAS_VELOCITY_ICON]= getIcon("SaveAs_Velocity");
            recIcon[ID_EXPORT_SCRIPT_ICON]  = getIcon("ScriptExport");
            recIcon[ID_SCRIPT_ICON]         = getIcon("Script");
            recIcon[ID_SUMMARY_ICON       ] = getIcon("Summary");
            recIcon[ID_SORT_SUM_ICON      ] = getIcon("SortSum");

            recIcon[ID_VIEW_RECORD_ICON   ] = getIcon("RecordView");
            recIcon[ID_VIEW_TABLE_ICON    ] = getIcon("TableView");
            recIcon[ID_VIEW_COLUMN_ICON   ] = getIcon("ColumnView");
            recIcon[ID_EXIT_ICON          ] = getIcon("Exit");
            recIcon[ID_GOTO_ICON          ] = getIcon("Goto");
            recIcon[ID_RELOAD_ICON        ] = getIcon("Reload");
            recIcon[ID_WIZARD_ICON        ] = getIcon("Wizard");
            recIcon[ID_LAYOUT_CREATE_ICON ] = getIcon("LayoutCreate");
            recIcon[ID_LAYOUT_EDIT_ICON   ] = getIcon("LayoutEdit");
            recIcon[ID_COMBO_EDIT_ICON    ] = recIcon[ID_GOTO_ICON];
            recIcon[ID_FILE_SEARCH_ICON   ] = getIcon("FileSearch");
            recIcon[ID_DIRECTORY_SEARCH_ICON] = getIcon("FolderSearch");

            for (i = 0; i < ICON_NAMES.length; i++) {
                treeIcons[i] = getIcon(ICON_NAMES[i]);
            }
            icon[0] = treeIcons[0];
            icon[1] = treeIcons[1];
            icon[2] = treeIcons[4];
            icon[3] = treeIcons[5];

            recIcon[ID_PREV_ICON]    =  icon[1];
            recIcon[ID_NEXT_ICON]    =  icon[2];

            for (i = 0; i < reActionRef.length; i++) {
                reActionRef[i] = -1;
            }
            reActionRef[ReActionHandler.FIND]    = ID_SEARCH_ICON;
            reActionRef[ReActionHandler.FILTER]  = ID_FILTER_ICON;
            reActionRef[ReActionHandler.EXECUTE_SAVED_FILTER]  = ID_FILTER_ICON;
            reActionRef[ReActionHandler.TABLE_VIEW_SELECTED]  = ID_VIEW_TABLE_ICON;
            reActionRef[ReActionHandler.RECORD_VIEW_SELECTED] = ID_VIEW_RECORD_ICON;
            reActionRef[ReActionHandler.COLUMN_VIEW_SELECTED] = ID_VIEW_COLUMN_ICON;
            reActionRef[ReActionHandler.BUILD_FIELD_TREE]     = ID_SUMMARY_ICON;
            reActionRef[ReActionHandler.BUILD_LAYOUT_TREE]    = ID_TREE_ICON;
            reActionRef[ReActionHandler.BUILD_RECORD_TREE]    = ID_TREE_ICON;
            reActionRef[ReActionHandler.BUILD_SORTED_TREE]    = ID_SORT_SUM_ICON;
            reActionRef[ReActionHandler.EXECUTE_SAVED_RECORD_TREE]  = ID_TREE_ICON;
            reActionRef[ReActionHandler.EXECUTE_SAVED_SORT_TREE]    = ID_SORT_SUM_ICON;
            reActionRef[ReActionHandler.BUILD_XML_TREE_SELECTED]    = ID_TREE_ICON;
            reActionRef[ReActionHandler.BUILD_LAYOUT_TREE_SELECTED] = ID_TREE_ICON;
            reActionRef[ReActionHandler.FULL_TREE_REBUILD]    = ID_TREE_ICON;

            reActionRef[ReActionHandler.SAVE]    = ID_SAVE_ICON;
            reActionRef[ReActionHandler.SAVE_AS] = ID_SAVE_AS_ICON;
            reActionRef[ReActionHandler.EXPORT]  = ID_EXPORT_ICON;
            reActionRef[ReActionHandler.EXPORT_AS_HTML]      = ID_SAVEAS_HTML_ICON;
            reActionRef[ReActionHandler.EXPORT_AS_CSV]       = ID_SAVEAS_CSV_ICON;
            reActionRef[ReActionHandler.EXPORT_AS_FIXED]     = ID_SAVEAS_FIXED_ICON;
            reActionRef[ReActionHandler.EXPORT_AS_HTML_TBL_PER_ROW] = ID_SAVEAS_HTML_ICON;
            reActionRef[ReActionHandler.EXPORT_HTML_TREE] = ID_SAVEAS_HTML_ICON;
            reActionRef[ReActionHandler.EXPORT_VELOCITY]  = ID_SAVEAS_VELOCITY_ICON;
            reActionRef[ReActionHandler.EXPORT_XSLT]      = ID_SAVEAS_XML_ICON;
            reActionRef[ReActionHandler.EXPORT_SCRIPT]    = ID_EXPORT_SCRIPT_ICON;
            reActionRef[ReActionHandler.RUN_SCRIPT]       = ID_SCRIPT_ICON;
            reActionRef[ReActionHandler.SAVE_AS_XML]      = ID_SAVEAS_XML_ICON;
            reActionRef[ReActionHandler.SAVE_LAYOUT_XML]  = ID_SAVEAS_XML_ICON;

            reActionRef[ReActionHandler.REPEAT_RECORD] = ID_COPY_ICON;
            reActionRef[ReActionHandler.COPY_RECORD]   = ID_COPY_ICON;
            reActionRef[ReActionHandler.CUT_RECORD]    = ID_CUT_ICON;

            reActionRef[ReActionHandler.PASTE_RECORD] = ID_PASTE_ICON;
            reActionRef[ReActionHandler.PASTE_RECORD_PRIOR]
                         						  = ID_PASTE_PRIOR_ICON;
            reActionRef[ReActionHandler.PASTE_TABLE_INSERT] = ID_PASTE_ICON;
            reActionRef[ReActionHandler.INSERT_RECORDS] = ID_NEW_ICON;
            reActionRef[ReActionHandler.INSERT_RECORD_PRIOR] = ID_NEW_UP_ICON;

            reActionRef[ReActionHandler.NEW]     = ID_NEW_ICON;
            //reActionRef[ReActionHandler.] = ID_SETLENGTH_ICON;

            reActionRef[ReActionHandler.DELETE]  = ID_DELETE_ICON;
            reActionRef[ReActionHandler.DELETE_RECORD]
                         						 = ID_DELETE_ICON;
            reActionRef[ReActionHandler.OPEN]    = ID_OPEN_ICON;
            reActionRef[ReActionHandler.HELP]    = ID_HELP_ICON;
            reActionRef[ReActionHandler.SORT]    = ID_SORT_ICON;

            reActionRef[ReActionHandler.PREVIOUS_RECORD] = ID_PREV_ICON;
            reActionRef[ReActionHandler.NEXT_RECORD]     = ID_NEXT_ICON;

            reActionRef[ReActionHandler.CREATE_CHILD]    = ID_NEW_ICON;
            reActionRef[ReActionHandler.EDIT_CHILD]      = ID_OPEN_ICON;
            reActionRef[ReActionHandler.PRINT]           = ID_PRINT_ICON;
            reActionRef[ReActionHandler.PRINT_SELECTED]  = ID_PRINT_ICON;
            reActionRef[ReActionHandler.AUTOFIT_COLUMNS] = ID_AUTOFIT_ICON;
            reActionRef[ReActionHandler.CLOSE]     = ID_EXIT_ICON;
            reActionRef[ReActionHandler.CLOSE_ALL] = ID_EXIT_ICON;


            //currClass = null;
        }
    }


    /**
     * Get an icon
     *
     * @param name icon resource name
     *
     * @return icon
     */
    private static ImageIcon getIcon(String name) {
        String fullName = "/net/sf/RecordEditor/utils/" + name;
        URL url = null;

        if (USE_PNG) {
            url = currClass.getResource(fullName + ".png");
        }
        if (url == null) {
            url = currClass.getResource(fullName + ".gif");
            //System.out.println("~~ " + name + " " + USE_PNG + " " + (url == null));
            if (url == null && (! USE_PNG)) {
                url = currClass.getResource(fullName + ".png");
            }
        }

        //System.out.println(")) " + url);
        if (url == null) {
            logMsg("Can not find icon " + name, null);
            return null;
        }
    	return new ImageIcon(url);
    }



    /**
     * Get Help button
     *
     * @return HelpButton;
     */
    public static final JButton getHelpButton() {

        if (recIcon[ID_HELP_ICON] == null) {
            recIcon[ID_HELP_ICON]    = getIcon("Help");
        }

        return new JButton("Help", recIcon[ID_HELP_ICON]);
    }

    /**
     * Get the Search Icon
     *
     * @param iconNum Icon index
     *
     * @return requested icon
     */
    public static final ImageIcon getRecordIcon(int iconNum) {

        readIcons();

        if (iconNum < 0 || iconNum > recIcon.length) {
            return null;
        }

        return recIcon[iconNum];
    }


    /**
     * Get the Direction Arrow Icons
     *
     * @return the Direction Arrow Icons
     */
    public static final ImageIcon[] getArrowIcons() {

        readIcons();

        return icon;
    }



    /**
     * Get the Direction Arrow Icons
     *
     * @return the Direction Arrow Icons
     */
    public static final ImageIcon[] getArrowTreeIcons() {

        readIcons();

        return treeIcons;
    }

    /**
     * Log a message
     *
     * @param message  message being wrtten to the log
     * @param ex Exception being logged
     */
    public static final void logMsg(String message, Exception ex) {

    	logMsg(AbsSSLogger.ERROR, message, ex);

    }

    public static final void logMsg(int level, String message, Exception ex) {
	    if (logger == null) {
	        System.out.println(message);
	        if (ex != null) {
	            ex.printStackTrace();
	        }
	    } else {
	        logger.logMsg(level, message);
	        logger.logException(level, ex);
	    }
	}

	/**
	 * Sets the class in common
	 *
	 * @param obj  any object used to get the class
	 */
	public static final void setCurrClass(Object obj) {
		currClass = obj.getClass();
	}


	/**
	 * Check if Velocity is installed
	 * @return wether velocity is present
	 */
    public static final boolean isVelocityAvailable() {

        if (toCheckVelocity) {
            try {
                /*
                 * try to load CobolPreprocessor to see if the cb2xml jar is present
                 * I use the CobolPreprocessor because it only uses IO classes.
                 * This aviods loading unnessary classes before need be
                 */
                velocityAvailable = (currClass.getClassLoader().getResource("org/apache/velocity/Template.class") != null);
            } catch (Exception e) {
                velocityAvailable = false;
            }
            toCheckVelocity = false;
        }
        return velocityAvailable;
    }


    /**
     * @return Returns the logger.
     */
    public static final AbsSSLogger getLogger() {
    	if (logger == null) {
    		logger = new TextLog();
    	}
        return logger;
    }


    /**
     * @param pLogger The logger to set.
     */
    public static final void setLogger(AbsSSLogger pLogger) {
        logger = pLogger;
    }


    /**
     * Set a connection id
     * @param idx index to be set
     * @param con connection
     * @param name Name of the DB
     */
    public static final void setConnection(int idx, Connection con, String name) {
        dbConnection[idx] = con;
        sourceId[idx] = name;
        yet2Assign = false;
    }


    /**
     * Set Database ReadOnly Attribute
     *
     * @param dbIsReadOnly wether the DB is read only
     */
    public static final void setReadOnly(boolean dbIsReadOnly) {
        Common.readOnly = dbIsReadOnly;
    }


    /**
     * Gets the Open error Message
     * @param idx Database index to check
     * @return the DB open error message
     */
    public static final String getJdbcMsg(int idx) {
        return jdbcMsg[idx];
    }


    /**
     * Get the full HTML URL using Help File Id
     *
     * @param helpId Help File ID
     *
     * @return Help URL
     */
    public static final String formatHelpURL(String helpId) {

        if (htmlDir == null) {
            htmlDir = Parameters.getFileName("HelpDir");
        }

        //System.out.println("Help Directory: " + htmlDir);
        if (htmlDir == null || "".equals(htmlDir)) {
            try {
                htmlDir = "File:" + Parameters.getBaseDirectory() + "/Docs/";
            } catch (Exception e) {
                e.printStackTrace();
                htmlDir = "";
            }
        }

        //System.out.println("Help file: " + htmlDir + helpId);

        return htmlDir + helpId;
    }
    //        String dir = ClassLoader.getSystemResource("edit/EditRec.class").toString();


    public static void setBounds1(JFrame frame, String id) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        switch (OPTIONS.screenStartSizeOpt.get()) {
          case ProgramOptions.SIZE_LAST:
        	setSizeFromVars(
        			  frame, screenSize,
        			  id + Parameters.LAST_SCREEN_WIDTH,
        			  id + Parameters.LAST_SCREEN_HEIGHT);

        	break;
        case ProgramOptions.SIZE_SPACE_AROUND:
       		setStandardHeight(frame, screenSize);
       		break;
        //case ProgramOptions.SIZE_MAXIMISED:
        case ProgramOptions.SIZE_SPECIFIED:
        	setSizeFromVars(
      			  frame, screenSize,
      			  id + Parameters.SCREEN_START_WIDTH,
      			  id + Parameters.SCREEN_START_HEIGHT);

       		break;
      	default:
        	setMaximised(frame, screenSize);
	    }
    }

    private static void setSizeFromVars(JFrame frame, Dimension screenSize, String widthPrm, String heightPrm) {
	    try {
			int width = Math.min(
					screenSize.width,
					Integer.parseInt(Parameters.getString(widthPrm))),
				height= Math.min(
						screenSize.height,
						Integer.parseInt(Parameters.getString(heightPrm)));
			if (width > 0 && height > 0) {
				frame.setSize(width, height);
			} else {
				setMaximised(frame, screenSize);
			}
		} catch (Exception e2) {
			setMaximised(frame, screenSize);
		}
    }

    private static void setMaximised(JFrame frame, Dimension screenSize) {
       	setStandardHeight(frame, screenSize);
    		GraphicsEnvironment e = GraphicsEnvironment
    				.getLocalGraphicsEnvironment();
    		frame.setMaximizedBounds(e.getMaximumWindowBounds());

    		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }

    private static void setStandardHeight(JFrame frame, Dimension screenSize) {
        frame.setBounds(Common.getSpaceAtRightOfScreen(),
		          Common.getSpaceAtTopOfScreen(),
		          screenSize.width  - Common.getSpaceAtRightOfScreen()
		          		- Common.getSpaceAtLeftOfScreen(),
		          screenSize.height - Common.getSpaceAtBottomOfScreen()
		          		- Common.getSpaceAtTopOfScreen());
    }

//    public static void setBounds2(JFrame frame) {
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        frame.pack();
//        if (OPTIONS.MAXIMISE_SCREEN.isSelected()) {
//    		GraphicsEnvironment e = GraphicsEnvironment
//    				.getLocalGraphicsEnvironment();
//    		frame.setMaximizedBounds(e.getMaximumWindowBounds());
//
//    		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
//        } else {
//	        frame.setBounds(
//	        		frame.getY(), frame.getX(),
//	        		Math.min(frame.getWidth(),
//	        				 screenSize.width  - Common.getSpaceAtRightOfScreen()
//	        				 				   - frame.getY()),
//	        		Math.min(frame.getHeight(),
//	        				 screenSize.height - Common.getSpaceAtBottomOfScreen()
//	        				   - SwingUtils.NORMAL_FIELD_HEIGHT * 2
//			 				   - frame.getX()));
//	        }
//        frame.setVisible(true);
//    }

    /**
     * @return Returns the spaceAtBottomOfScreen.
     */
    public static int getSpaceAtBottomOfScreen() {
        //initVars();
        return spaceAtBottomOfScreen;
    }

    /**
     * @return Returns the spaceAtLeftOfScreen.
     */
    public static int getSpaceAtLeftOfScreen() {
        //initVars();
        return spaceAtLeftOfScreen;
    }

    /**
     * @return Returns the spaceAtRightOfScreen.
     */
    public static int getSpaceAtRightOfScreen() {
        //initVars();
        return spaceAtRightOfScreen;
    }

    /**
     * @return Returns the spaceAtTopOfScreen.
     */
    public static int getSpaceAtTopOfScreen() {
       //initVars();
       return spaceAtTopOfScreen;
    }

    /**
     * Get the Icon for a specific ReActionHandler action
     *
     * @param action forwich an icon is required
     *
     * @return Returns the requestedicon.
     */
    public static ImageIcon getReActionIcon(int action) {
        return getRecordIcon(reActionRef[action]);
    }


    /**
     * Get The Name of the Action
     * @param action action to get the name of
     * @return Returns the reActionNames.
     */
    public static String getReActionNames(int action) {
        return reActionNames[action];
    }


    /**
     * Get The Name of the Action
     * @param action action to get the name of
     * @return Returns the reActionNames.
     */
    public static String getReActionDescription(int action) {
        if (reActionDesc[action] != null) {
            return reActionDesc[action];
        }
        return reActionNames[action];
    }


    /**
     * Adjusts the column widths based on the component widths
     * @param table table to fix
     * @param minColumns minumum number of columns in table (
     */
    public static void calcColumnWidths(JTable table, int minColumns) {
    	int screenWidth = table.getVisibleRect().width;
    	int maxColWidth = Math.max(screenWidth * 2 / 3, MINIMUM_MAX_COLUMN_WIDTH);

    	calcColumnWidths(table, minColumns, maxColWidth);
    }
    public static void calcColumnWidths(JTable table, int minColumns, int maxColWidth) {
       JTableHeader header = table.getTableHeader();

        TableCellRenderer defaultHeaderRenderer = null;

        if (header != null) {
            defaultHeaderRenderer = header.getDefaultRenderer();
        }

        TableColumnModel columns = table.getColumnModel();
        TableModel data = table.getModel();

        int margin = Math.min(4, columns.getColumnMargin()); // only JDK1.3
        Rectangle visbleRect = table.getVisibleRect();
        int screenWidth = visbleRect.width;
        int screenStart = visbleRect.y;
        int screenheight = visbleRect.height;

        if (screenWidth == 0) {
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            screenWidth = dim.width - DEFAULT_ROOM_AROUND_SCREEN;
            screenheight = dim.height - DEFAULT_ROOM_AROUND_SCREEN;
        }
        int firstRow = Math.max(0, screenStart - TABLE_WINDOW_SIZE_TO_CHECK);
        int rowCount = Math.min(data.getRowCount(),
                screenStart + screenheight + TABLE_WINDOW_SIZE_TO_CHECK);
        //int totalWidth = 0;
        //System.out.println("Column Widths ==> " + visbleRect.width + " " + maxColWidth);

        //System.out.println();
        TableColumn column;
        for (int i = columns.getColumnCount() - 1; i >= 0; --i) {
            column = columns.getColumn(i);

            int columnIndex = column.getModelIndex();

            int width = -1;

            TableCellRenderer h = column.getHeaderRenderer();

            if (h == null) {
                h = defaultHeaderRenderer;
            }

            if (h != null) {
                Component c = h.getTableCellRendererComponent(table, column
                        .getHeaderValue(), false, false, -1, i);

                width = c.getPreferredSize().width;
                //System.out.print(width + " ");
            }

            for (int row = rowCount - 1; row >= firstRow; --row) {
                TableCellRenderer r = table.getCellRenderer(row, i);

                try {
	               Component c = r.getTableCellRendererComponent(
	                		table,
	                		data.getValueAt(row, columnIndex), false, false, row, i);
                	width = Math.max(width, c.getPreferredSize().width);
                } catch (Exception e) {
                	System.out.println("Error Row,col= " + row + ", " + columnIndex);
				}

            }

            if (width >= 0) {
                //System.out.println("### " + columns.getColumnCount() + " " + i + " Width=" + width);
                if (columns.getColumnCount() == minColumns) {
                    column.setPreferredWidth(width + margin); // <1.3: without margin
                } else {
                    column.setPreferredWidth(Math.min(width + margin, maxColWidth)); // <1.3: without margin
                }

                //column.setPreferredWidth(width + margin);
//                if (width > maxColWidth) {
//                    System.out.println("~~ " + width + " " + maxColWidth);
//                }
            }

            //totalWidth += column.getPreferredWidth();
        }
    }



    /**
     * Read an array of integer from the properties into an array
     * @param prefix array prefix
     * @param numberInArray number in the array
     *
     * @return array just read in (null if not present in the
     *         properties file
     */
    public static int[] readIntPropertiesArray(String prefix, int numberInArray) {
        int[] ret = null;
        String s;
        int id;

        for (int i = 0; i < numberInArray; i++) {
            s = Parameters.getString(prefix + i);
            if (s != null && ! "".equals(s)) {
                try {
                    id = Integer.parseInt(s);
                    if (ret == null) {
                        ret = new int[numberInArray];
                        for (int j = 0; j < numberInArray; j++) {
                            ret[j] = NULL_INTEGER;
                        }
                    }
                    ret[i] = id;
                } catch (Exception e) {
                    logMsg("Error Loading Index: " + i + " "
                            + s + " " + Parameters.TYPE_CLASS_PREFIX + " "
                            + e.getMessage(), null);
                    //e.printStackTrace();
                }
            }
        }

        return ret;
    }


    /**
     * Read array of class names from the properties file
     * and create an istance of them
     *
     * @param ids integer id
     * @param prefix array name (or prefix
     * @return array of objects
     */
    public static Object[] readPropertiesArray(int[] ids, String prefix) {
        Object[] ret = null;

        if (ids != null) {
            ret = readPropertiesArrayImp(ids, prefix, ids.length);
        }
        return ret;
    }

    /**
     * Read array of class names from the properties file
     * and create an istance of them
     *
     * @param prefix array name (or prefix)
     * @param numberInArray number in the array
     * @return array of objects
     */
    public static Object[] readPropertiesArray(String prefix, int numberInArray) {
        return readPropertiesArrayImp(null, prefix, numberInArray);
    }


    /**
     * Read array of class names from the properties file
     * and create an istance of them
     *
     * @param ids integer Id for the class (or null if none)
     * @param prefix array name or prefix
     * @param numberInArray number in the array
     * @return array of requested objects
     */
    private static Object[] readPropertiesArrayImp(int[] ids, String prefix, int numberInArray) {
        Object[] ret = null;

        String s;
        for (int i = 0; i < numberInArray; i++) {
            if (ids == null || ids[i] != NULL_INTEGER) {
                s = Parameters.getString(prefix + i);
                if (s != null && ! "".equals(s)) {
                    try {

                        if (ret == null) {
                            ret = new Object[numberInArray];
                            for (int j = 0; j < numberInArray; j++) {
                                ret[j] = null;
                            }
                        }
                        ret[i] = Class.forName(s).newInstance();
                    } catch (Exception e) {
                        logMsg("Error Loading Property: " + i + " "
                                + e.getMessage(), null);
                        e.printStackTrace();
                    }
                }
            }
        }
        return ret;
    }


    /**
     * Strips the directory from a filename
     * @param fileName file name
     * @return filename without directory
     */
    public static String stripDirectory(String fileName) {
        if (fileName == null) {
            return null;
        }

        return (new File(fileName)).getName();
//        int pos = fileName.lastIndexOf(FILE_SEPERATOR);
//
//        if (pos < 0) {
//            pos = fileName.lastIndexOf("/");
//        }
//
//        if (pos > 0) {
//            return fileName.substring(pos + 1);
//        }
//
//
//        return fileName;
    }


	/**
	 * @return the connectionIndex
	 */
	public static int getConnectionIndex() {

		if (searchActiveDB) {
			try {
				initVars();
				makeConnection(connectionIndex);
				searchActiveDB = false;
			} catch (Exception e) {
				boolean hsqlServer = dataSource[connectionIndex] != null
								&& dataSource[connectionIndex].toLowerCase().startsWith("jdbc:hsqldb:hsql:");
				for (int i = 0; i < NUMBER_OF_COPYBOOK_SOURCES; i++) {
					if (i != connectionIndex && dataSource[i] != null && ! "".equals(dataSource[i].trim())) {
						try {
							makeConnection(i);
							connectionIndex = i;
							searchActiveDB = false;
							break;
						} catch (Exception ex) {

						}
					}
				}
				if (hsqlServer && toWarn
				&& dataSource[connectionIndex] != null
				&& dataSource[connectionIndex].toLowerCase().startsWith("jdbc:hsqldb:file:")) {
					String message = "\t********************   Warning ***************************\n"
						+ "Tried to Connect to the HSQL Data Base Server and failed. Will run in Database embedded mode.\n"
						+ "This package works best in Data Base Server Mode (option on menu, exit this program before you start the Server).\n\n"
						+ "If you wish to use the package in imbeded Mode, You may wish to reveiw the Database\n"
						+	"Options (Menu option Edit >>> Edit Startup options) \n"
						+ " 1) Properties >>>> Defaults     then click on the Default DB tab. You can now select"
						+     " the Normal DB Connection\n"
						+ " 2) JDBC Parameters >>> JDBC Properties ~  Auto Close Connection. Setting it to Y will alow\n"
						+       " multiple versions of the RecordEditor to be run at once,"
						+ " but you may have update problems\n\n"
						+ "See \"Improving the running of RecordEditor HSQL\" section in HowTo documnetation "
						+ "for more details";

					logMsg(message, null);
				}
			}
		}

		return connectionIndex;
	}



//	/**
//	 * Get standard sized font
//	 * @return standard sized font
//	 */
//	public static Font getMonoSpacedFont() {
//		return new Font("Monospaced", Font.PLAIN,  Common.STANDARD_FONT_HEIGHT);
//	}
//

	/**
	 * @return the copybookWriterIndex
	 */
	public static final int getCopybookWriterIndex() {
		if (copybookWriterIndex < 0) {
			CopybookWriterManager manager = CopybookWriterManager.getInstance();
			String name = Parameters.getString(Parameters.DEFAULT_COPYBOOK_WRITER);
			copybookWriterIndex = 1;
			if (name != null && ! "".equals(name)) {
				for (int i = 0; i < manager.getNumberOfEntries(); i++) {
			    	if (name.equalsIgnoreCase(manager.getName(i))) {
			    		copybookWriterIndex = i;
			    		break;
			    	}
			    }
			}
		}
		return copybookWriterIndex;
	}


	public static final String trimRight(Object o) {
		if (o == null || "".equals(o) || "".equals(o.toString().trim())) {
			return "";
		}

		String s = o.toString();
		int l = s.length() - 1;

		while (s.charAt(l) == ' ') {
			l -= 1;
		}

		return s.substring(0, l+1);
	}

	public static final String fix(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}

//	/**
//	 * Load the Jars files
//	 */
//	public static void loadJdbcJars() {
//        try {
//            int j;
//            FileReader inReader = new FileReader(Parameters.getLibDirectory()
//            					+ FILE_SEPERATOR + ExternalReferenceConstants.SYSTEM_JDBC_JARS_FILENAME);
//            BufferedReader in = new BufferedReader(inReader);
//            ArrayList<String> list = new ArrayList<String>();
//            String s;
//
//             while ((s = in.readLine()) != null) {
//                if (s.trim().toLowerCase().startsWith("jdbc.")) {
//                	if ((j = s.indexOf('\t')) >= 0) {
//                        s = s.substring(j + 1);
//                    }
//                    list.add(s.trim());
//                }
//            }
//
//            if (list.size() > 0) {
//            	jdbcJarNames = new String[list.size()];
//            	jdbcJarNames = list.toArray(jdbcJarNames);
//            }
//            in.close();
//         } catch (Exception e) {
//            e.printStackTrace();
//            return;
//        }
//
//	}


	/**
	 * @return the doFree
	 */
	public static final boolean isSetDoFree(boolean free) {
		boolean ret = doFree;
		doFree = free;
		return ret;
	}


	/**
	 * @param doFree the doFree to set
	 */
	public static final void setDoFree(boolean free, int idx) {
		Common.doFree = free;
		freeConnection(idx);
	}

	public static final boolean usePrefered() {
		return OPTIONS.usePrefered.isSelected();
	}
//
//	public static final boolean isWarnBinaryFieldsAndStructureDefault() {
//		return ! "N".equalsIgnoreCase(Parameters.getString(Parameters.WARN_BINARY_FIELDS_DEFAULT));
//	}
//
//
//	public static final boolean isFieldSearchAutomatic() {
//		return ! "N".equalsIgnoreCase(Parameters.getString(Parameters.FS_RUN_AUTOMATIC));
//	}
//
//	public static final boolean isSearchForMainframeZoned() {
//		return  "Y".equalsIgnoreCase(Parameters.getString(Parameters.FS_MAINFRAME_ZONED));
//	}
//
//	public static final boolean isSearchForPcZoned() {
//		return  "Y".equalsIgnoreCase(Parameters.getString(Parameters.FS_PC_ZONED));
//	}
//
//	public static final boolean isSearchForComp3() {
//		return  "Y".equalsIgnoreCase(Parameters.getString(Parameters.FS_COMP3));
//	}
//
//	public static final boolean isSearchForCompBigEndian() {
//		return  "Y".equalsIgnoreCase(Parameters.getString(Parameters.FS_COMP_BIG_ENDIAN));
//	}
//
//	public static final boolean isSearchForCompLittleEndian() {
//		return  ! OPTIONS.searchForCompBigEndian.isSelected()
//				&& "Y".equalsIgnoreCase(Parameters.getString(Parameters.FS_COMP_Little_ENDIAN));
//	}
//
//
//	/**
//	 * @return the highlightEmptyActive
//	 */
//	public static final boolean isHighlightEmptyActive() {
//		return highlightEmptyActive;
//	}
//
//
//	/**
//	 * @param highlightEmptyActive the highlightEmptyActive to set
//	 */
//	public static final void setHighlightEmptyActive(boolean highlightEmptyActive) {
//		Common.highlightEmptyActive = highlightEmptyActive;
//	}
//
//
//	/**
//	 * @return the highlightEmpty
//	 */
//	public static final boolean isHighlightEmpty() {
//		return highlightEmpty;
//	}
//
//
//	/**
//	 * @param highlightEmpty the highlightEmpty to set
//	 */
//	public static final void setHighlightEmpty(boolean highlightEmpty) {
//		Common.highlightEmpty = highlightEmpty;
//		String value = "N";
//
//		if (highlightEmpty) {
//			value = "Y";
//		}
//
//		Parameters.setProperty(Parameters.PROPERTY_HIGHLIGHT_EMPTY, value);
//	}

	/**
	 * Check to see if the field value is Empty
	 * @param value value to check
	 * @return wether it is empty or not ??
	 */
	public static boolean isEmpty(Object value) {
		return 	value == null
			||	value == Common.MISSING_VALUE
			|| 	value == Common.MISSING_REQUIRED_VALUE;
	}

	public static long getMemoryCompare() {
		BigDecimal pct = BigDecimal.valueOf(OPTIONS.bigFilePercent.get());
		BigDecimal calc = MAX_MEMORY_BD.multiply(pct);
		calc = calc.divide(BigDecimal.valueOf(100));

		return calc.longValue();
	}

    public static int toInt(byte b) {
   	int i = b;
    	if (i < 0) {
    		i += 256;
    	}
    	return i;
    }



//	public static int getChunkSize() {
//		return getSize(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, 1048576);
//	}
//
//
//	public static int getBigFileFilterLimit() {
//		return getSize(Parameters.PROPERTY_BIG_FILE_FILTER_LIMIT, 75000);
//	}
//
//	private static int getSize(String name, int defaultValue) {
//		int size = defaultValue;
//
//		try {
//			String s = Parameters.getString(name);
//			if (s != null && ! "".equals(s)) {
//				size = 1024 * Integer.parseInt(s);
//			}
//		} catch (Exception e) {
//		}
//		return size;
//	}
}