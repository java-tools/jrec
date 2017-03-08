/*
 * Created on 30/11/2004
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *   - changed to optional read properties from user properties
 *     file (as well as system properties
 */
package net.sf.RecordEditor.utils.params;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;



//import sun.security.action.GetPropertyAction;

/**
 * Interface to properties file
 *
 * @author Bruce Martin
 *
 */
public final class Parameters implements ExternalReferenceConstants {

    private static final int REHOME_VAR_LENGTH = "<rehome>".length();
	private static final int REPROPERTIES_VAR_LENGTH = "<reproperties>".length();
	public static final int NUMBER_OF_LOADERS = 20;
    public static final int NUMBER_OF_USER_FUNCTIONS = 32;


    public static final String VAL_RECORD_EDITOR_DEFAULT = "RecordEditor_Default";

    public static final String CSV_DELIMITER_CHARS = "DelimChars";
    public static final String CSV_QUOTE_CHARS = "QuoteChars";

    public static final String PROPERTY_BIG_FILE_PERCENT = "BigFilePercentage";
    public static final String PROPERTY_BIG_FILE_CHUNK_SIZE = "BigFileChunkSize";
    public static final String PROPERTY_BIG_FILE_COMPRESS_OPT = "BigFileCompressOption";
    public static final String PROPERTY_BIG_FILE_LARGE_VB = "BigFileLargeVB";
    public static final String PROPERTY_BIG_FILE_FILTER_LIMIT = "BigFileFilterLimit";
    public static final String PROPERTY_BIG_FILE_DISK_FLAG = "BigFileDiskFlag";
    public static final String PROPERTY_USE_OVERFLOW_FILE = "UseOVerflow";
    public static final String PROPERTY_BIG_FILE_USE_SPECIAL_FIXED_MODEL = "BigFileUseFixedModel";
   //public static final String PROPERTY_BIG_FILE_GZIP_WRITE = "BigFileGZipAnalyseSize";
    public static final String PROPERTY_TEST_MODE  = "TestMode";
    public static final String PROPERTY_LOAD_FILE_BACKGROUND  = "LoadFileInBackground";
    public static final String SCREEN_SIZE_OPTION  = "ScreenSizeOpt";
    public static final String LAST_SCREEN_WIDTH   = "lastScreenWidth";
    public static final String LAST_SCREEN_HEIGHT  = "lastScreenHeight";
    public static final String SCREEN_START_WIDTH  = "ScreenStartWidth";
    public static final String SCREEN_START_HEIGHT = "ScreenStartHeight";
    
    public static final String PROPERTY_DEFAULT_SINGLE_BYTE_CHARSET = "defaultSingleByteCharset";
    public static final String PROPERTY_USE_SINGLE_BYTE_CHARSET = "useDefaultSingleByteCharset";
    public static final String PROPERTY_DEFAULT_EBCDIC_CHARSET      = "defaultEbcidicCharset";


    public static final String FIELD_COLORS  = "fieldColors.";
    public static final String SPECIAL_COLORS  = "specialColors.";

    public static final String FILE_CHOOSER_NORMAL      = "N";
//    public static final String FILE_CHOOSER_OPTIONAL    = "O";
    public static final String FILE_CHOOSER_OPT_HIDE    = "V";
    public static final String FILE_CHOOSER_EXTENDED    = "E";
    public static final String FILE_CHOOSER_OPTION      = "FileChooserOpt";
    public static final String FILE_CHOOSER_CSV_EDIT    = "FileChooserCsvEdOpt";
    public static final String PO_EDIT_OPEN_FUZZY_VIEW  = "PoFuzzyView";
    public static final String PO_EDIT_CHILD_SCREEN_POS = "PoChildPos";
    public static final String CHILD_SCREEN_RIGHT = "R";
    public static final String CHILD_SCREEN_BOTTOM = "B";

    public static final String PROPERTY_COPYBOOK_NAME_PREFIX  = "CopybookLoaderName.";
    public static final String PROPERTY_COPYBOOK_CLASS_PREFIX = "CopybookloaderClass.";
    public static final String PROPERTY_LOOKS_CLASS_INDEX = "LooksClassIndex";
    public static final String PROPERTY_LOOKS_CLASS_NAME  = "LooksClassName";

    public static final String PROPERTY_PLUGIN_FUNC_NAME  = "LocalFuncName";
    public static final String PROPERTY_PLUGIN_FUNC_CLASS = "LocalFuncClass";
    public static final String PROPERTY_PLUGIN_FUNC_PARAM = "LocalFuncParam";

    public static final int    FIRST_USER_DATE_TYPE       = 90;
    public static final int    DATE_TYPE_TABLE_SIZE       = 20;
    public static final String PROPERTY_DATE_TYPE_NAME    = "DateTypeName.";
    public static final String PROPERTY_DATE_BASE_TYPE    = "DateBaseType.";
    public static final String PROPERTY_DATE_FORMAT       = "DateFormat.";

    public static final String PROPERTY_HIGHLIGHT_EMPTY   = "ShowEmpty.";

    public static final String PROPERTY_ICON_INDEX   = "IconIndex";
    public static final String PROPERTY_PGN_ICONS    = "usePgnIcons";

    public static final int    NUMBER_OF_TYPES         = 30;
    public static final String TYPE_CLASS_PREFIX       = "TypeClass.";
    public static final String TYPE_NUMBER_PREFIX      = "TypeNumber.";
    public static final String TYPE_NAME_PREFIX        = "TypeName.";
    public static final String TYPE_FORMAT_PREFIX      = "TypeFormat.";

    public static final int    NUMBER_OF_FORMATS       = 30;
    public static final String FORMAT_CLASS_PREFIX     = "FormatClass.";
    public static final String FORMAT_NUMBER_PREFIX    = "FormatNumber.";
    public static final String FORMAT_NAME_PREFIX      = "FormatName.";

    public static final String SHOW_RECORDEDITOR_TIPS  ="showRecordEditorTips";
    public static final String BRING_LOG_TO_FRONT      = "LogToFront";
    public static final String PREFERED_AS_DEFAULT     = "PreferedAsDefault";
    public static final String WARN_BINARY_FIELDS_DEFAULT      = "WarnBinFieldsAndDefault";
    
    public static final String DEFAULT_COBOL_DIRECTORY = "DefaultCobolDirectory";
    public static final String COPYBOOK_DIRECTORY      = "CopybookDirectory";
    public static final String VELOCITY_COPYBOOK_DIRECTORY = "VelocityCopybookDirectory";
    public static final String VELOCITY_TEMPLATE_DIRECTORY = "VelocityTemplateDirectory";
    public static final String DEFAULT_VELOCITY_COPYBOOK_DIRECTORY ="<reproperties>/User/VelocityTemplates/Copybook/*";
    public static final String DEFAULT_VELOCITY_TEMPLATE_DIRECTORY ="<reproperties>/User/VelocityTemplates/File/*";
    public static final String VELOCITY_SCRIPT_DIR     = "VelocityScriptDirectory";
    public static final String XSLT_TEMPLATE_DIRECTORY = "XsltTemplateDirectory";
    public static final String EXPORT_SCRIPT_DIRECTORY = "ExportScriptDirectory";
    public static final String SCRIPT_DIRECTORY        = "ScriptDirectory";
    public static final String LANG_DIRECTORY          = "LangDirectory";
    public static final String CURRENT_LANGUAGE        = "Language";
    public static final String XSLT_JAR1 = "XsltJar1";
    public static final String XSLT_JAR2 = "XsltJar2";
    public static final String INVALID_FILE_CHARS      = "InvalidFileChars";
    public static final String FILE_REPLACEMENT_CHAR   = "FileReplChar";
    public static final String ASTERIX_IN_FILE_NAME    = "AsterixInFileName";
 //   public static final String DIRECTORY_IN_SOURCE_VAR = "DirVarInSource.";

    public static final String COPY_SAVE_DIRECTORY  = "CopySaveDirectory";
    public static final String COPY_SAVE_DIRECTORY_DFLT  = "<reproperties>/User/Copy/*";
    public static final String COPY_SAVE_FILE  	    = "CopySaveFile";

    public static final String LAYOUT_EXPORT_DIRECTORY  = "LayoutExportDirectory";
    public static final String LAYOUT_EXPORT_DIRECTORY_DFLT  = "<reproperties>/User/LayoutExport/*";
    public static final String CODEGEN_DIRECTORY             = "CodeGenDir";
    public static final String CODEGEN_DIRECTORY_DFLT        = "<reproperties>/Export/*";
    public static final String CODEGEN_DIRECTORY_EXTENSION   = "CodeGenExtension";
    public static final String CODEGEN_DIRECTORY_EXTENSION_DFLT   = "$language./$schema._$template./";

    public static final String COMPARE_SAVE_DIRECTORY  = "CompareSaveDirectory";
//    public static final String COMPARE_SAVE_FILE  	   = "CompareSaveFile";
    public static final String FILTER_SAVE_DIRECTORY   = "FilterSaveDirectory";
    public static final String FIELD_SAVE_DIRECTORY    = "FieldSaveDirectory";
//    public static final String HIDDEN_FIELDS_SAVE_DIRECTORY   = FILTER_SAVE_DIRECTORY;
    public static final String SORT_TREE_SAVE_DIRECTORY   = "SortTreeSaveDirectory";
    public static final String RECORD_TREE_SAVE_DIRECTORY = "RecordTreeSaveDirectory";

    public static final String CSV_LOOK_4_FIXED_WIDTH  = "CsvSearchFixed";
    public static final String USE_FILE_WIZARD		   = "UseFileWizard";

    public static final String DEFAULT_COPYBOOK_READER = "CopyBookReader";
    public static final String DEFAULT_COPYBOOK_WRITER = "CopyBookWriter";
    public static final String XSLT_ENGINE             = "XsltEngine";

    public static final String CODEGEN_TEMPLATE        = "codegendTemplate";
    public static final String CODEGEN_PACKAGEID       = "codegendPackageId";

    public static final String DEFAULT_COBOL_DIALECT_NAME = "DefaultBin";
    public static final String DEFAULT_COBOL_DIALECT   = "CobolDialect";

    public static final String COBOL_OPT_FILE      	   = "CobolOpt";
    
    public static final String CODE_GEN_OUTPUT_LIST    = "CobGenOut.";
    public static final String TEMPLATE_DIRECTORY      = "TemplateDir";
    public static final String TEMPLATE_DIR_LIST       = "TemplateDirList.";
   
    public static final String COBOL_COPYBOOK_LIST     = "CobolCpy.";
    public static final String SAMPLE_FILE_LIST        = "SampleFiles.";
    public static final String COBOL_COPYBOOK_DIRS     = "CobolDirs.";
    public static final String SCHEMA_LIST             = "SchemaFiles.";
    public static final String SCHEMA_DIRS_LIST        = "SchemaDirs.";
    public static final String SAVED_COPY_LIST         = "SavedCpy.";
    public static final String SAVED_DIFF_LIST         = "SavedDiff.";
    public static final String ERROR_FILES             = "ErrorFiles.";
    public static final String VELOCITY_SKELS_LIST     = "VelocitySkels.";
    public static final String VELOCITY_SCHEMA_SKELS_LIST = "VelocitySchemaSkels.";
    public static final String HTML_OUTPUT_LIST        = "HtmlOut.";
    public static final String SAVE_SCRIPTS_LIST 	   = "SaveScripts.";
    public static final String XSLT_LIST 	 		   = "XsltFiles.";
    public static final String XSLT_JAR_LIST 	 	   = "XsltJars.";
    public static final String XML_EXPORT_DIRS 	 	   = "XmlExportDirs.";
    public static final String OUTPUT_SCHEMA_LIST 	   = "OutputSchemas.";

    public static final String DEFAULT_DATABASE        = "DefaultDB";
    public static final String DEFAULT_IO       	   = "DefaultIO";
 
    public static final String DB_READ_ONLY_SOURCE	   = "ReadOnly.";
    public static final String DB_EXPAND_VARS	       = "ExpandVars.";
    public static final String DB_CLOSE_AFTER_EXEC	   = "AutoClose.";

    public static final String DB_DROP_SEMI			   = "DBDropSemi.";
    public static final String DB_SOURCE_NAME		   = "SourceName.";
    public static final String DB_SOURCE			   = "Source.";
    public static final String DB_DRIVER	 		   = "Driver.";
    public static final String DB_USER	 			   = "User.";
    public static final String DB_PASSWORD			   = "Password.";
    public static final String DB_JDBC_JAR			   = "JdbcJar.";
    public static final String DB_COMMIT			   = "Commit.";
    public static final String DB_CHECKPOINT		   = "Checkpoint.";

    public static final String FS_RUN_AUTOMATIC = "FsAutoRun";
    public static final String FS_MAINFRAME_ZONED = "FsMainframeZoned";
    public static final String FS_PC_ZONED = "FsPcZoned";
    public static final String FS_COMP3 = "FsComp3";
    public static final String FS_COMP_BIG_ENDIAN = "FsCompBigEndian";
    public static final String FS_COMP_LITTLE_ENDIAN = "FsCompLittleEndian";
    public static final String USE_NEW_TREE_EXPANSION = "NewTreeExpansion";

    public static final String SEARCH_ALL_FIELDS = "SearchAllFields";
    public static final String SHOW_ALL_EXPORT_OPTIONS = "AllExportOptions";
    public static final String NAME_FIELDS = "NameFields";
    public static final String SPECIAL_FIND_BTN_NAME = "useAltFindName";
    public static final String LOG_TEXT_FIELDS = "LogText";
    public static final String HIGHLIGHT_MISSING_TRANSLATIONS = "FlagMissingTranslations";
    public static final String SEPERATE_WINDOWS  = "SepWindows";
    public static final String INCLUDE_TYPE_NAME = "IncTypeName";
    public static final String EDIT_RAW_TEXT = "EditRawText";
    public static final String ADD_FILE_SEARCH_BTN = "AddFileSearchBtn";

    public static final String CSV_SHOW_FILECHOOSER_OPTIONS = "ShowFileChooserOptions";
    public static final String OPEN_IN_LAST_DIRECTORY = "UseLastDir";

    public static final String DEL_SELECTED_WITH_DEL_KEY = "DeleteSelectedWithDelKey";
    public static final String WARN_WHEN_USING_DEL_KEY   = "WarnWithDelKey";
//    public static final String RECENT_COPYBOOK_DIRS      = "RecentCopybookDirs";
    public static final String RECENT_PROTO_FILES        = "RecentProtoFiles.";
    public static final String RECENT_AVRO_FILES         = "RecentAvroFiles.";
    public static final String RECENT_PROTO_DIRS         = "RecentProtoImbedDirs.";
    
    public static final String LIST_SAVE_FILES           = "SaveAs.";
    
    public static final String COMMIT_COUNT = "CommitCount";
    public static final String FETCH_SIZE = "FetchSize";


	public static String LANG_FILE_PREFIX = "ReMsgs_";
	
	public static final boolean IS_MAC, IS_NIX, IS_WINDOWS;

    private static final HashSet<String> defaultTrue = new HashSet<String>(10);

	private static  String bundleName = DEFAULT_BUNDLE_NAME;
	private static final String USER_PARAM_FILE      = "Params.Properties";
	private static final String USER_PARAM_LIST_FILE = "ParamLists.Properties";
	private static final int SIZE_OF_FILE_URL_PREFIX = 9;

    private static String baseDirectory = null;
    private static String libDirectory = null;


	private static  ResourceBundle resourceBundle = null;
	private static Properties properties = null;
	private static Properties properties2 = null;
	private static Properties globalProperties = null;
	private static Properties paramProperties = null;
	private static Properties propertiesLists = null;

	private static boolean useUserParamFile = ! "N".equals(getResourceString("Allow_User_Params"));
	private static boolean toInit = true;

    private static String userHome       = System.getProperty("user.home");
    private static String appData         = userHome;
	private static String reHomeDirectory = userHome;

	private static String applicationDirectory = null;
	private static String propertyFileName, propertyListFileName;
	private static String globalPropertyFileName = null;
	private static String systemJarFileDirectory,
	                      userJarFileDirectory = userHome;
	
	private static String propertiesDir = null;

	private static ArrayList<AParameterChangeListner> paramChangeListners = new ArrayList<AParameterChangeListner>(3);

	private static final HashMap<String, String> defaultValues = new HashMap<String, String>();

	public static boolean savePropertyChanges = true;

	public static boolean windowsLAF = false;
	
	public static final String JAVA_VERSION_STRING; 
	public static final float JAVA_VERSION;
	private static boolean changesToSave = false;
	static {
		float f = 1;
		String vs = "";
		
		try {
			vs = System.getProperty("java.version");
			f = Float.parseFloat(vs.substring(0, vs.lastIndexOf('.')));
		} catch (Exception e) {
			// TODO: handle exception
		}
		JAVA_VERSION_STRING = vs;
		JAVA_VERSION = f;


		boolean isNix = false, isMac=false, isWin = false;
		try {
			String s = System.getProperty("os.name").toLowerCase();
			if (s != null) {
				isNix = (s.indexOf("nix") >= 0 || s.indexOf("nux") >= 0);
				isMac = s.indexOf("mac") >= 0;
				isWin = s.indexOf("win") >= 0;
			}
		} catch (Exception e) {
		}

		IS_MAC = isMac;
		IS_NIX = isNix;
		IS_WINDOWS = isWin;
		
		
		
		try {
			resourceBundle = ResourceBundle.getBundle(bundleName);
		} catch (Exception e) {
			System.out.println("---> " + bundleName + " " + e);
			//e.printStackTrace();
		}
		
		checkInit();

//		propertiesDir = getString(PROPERTIES_DIR_VAR_NAME);

		try {
			System.out.println();
			System.out.println(System.getProperty("os.name") + " " + System.getenv("APPDATA"));
			System.out.println();
			String aData = System.getenv("APPDATA");
			if (IS_WINDOWS && aData != null && ! exists(getPropertiesDirectory())) {
				String s = getString(PROPERTIES_WIN_DIR_VAR_NAME);
				if (s != null && ! "".equals(s)) {
//					propertiesDir = s;
					userHome = aData;
					appData = aData;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//reHomeDirectory = userHome;
		//userJarFileDirectory = userHome;
		
		String[] defTrueKeys = {
				PROPERTY_BIG_FILE_USE_SPECIAL_FIXED_MODEL,
				PROPERTY_LOAD_FILE_BACKGROUND,
				FS_RUN_AUTOMATIC,
				BRING_LOG_TO_FRONT,
				WARN_BINARY_FIELDS_DEFAULT,
				PROPERTY_PGN_ICONS,
				USE_NEW_TREE_EXPANSION,
				SEARCH_ALL_FIELDS,
//				DEL_SELECTED_WITH_DEL_KEY,
				WARN_WHEN_USING_DEL_KEY,
				PROPERTY_USE_OVERFLOW_FILE,
		};

		for (String s : defTrueKeys) {
			defaultTrue.add(s);
		}

		defaultValues.put(FIELD_SAVE_DIRECTORY, "<reproperties>/User/Fields/*");

	}


	/**
	 * constructor, ensure it can never be used as a class
	 */
	private Parameters() {

	}

    /**
     * Get the application directory
     * @return Returns the APPLICATION_DIRECTORY.
     */
    public static String getApplicationDirectory() {
        initDirectories();
        //System.out.println(">> Application directory >> " + applicationDirectory);
        return applicationDirectory;
    }

    /**
     * @return Returns the JAR_LIST_FILE_DIRECTORY.
     */
    public static String getSytemJarFileDirectory() {
        initDirectories();
        return systemJarFileDirectory;
    }
    /**
     * @return Returns the JAR_LIST_FILE_DIRECTORY.
     */
    public static String getUserJarFileDirectory() {
        initDirectories();
        return userJarFileDirectory;
    }


    /**
     * Get the library where the jar file is
     * @return Returns the PROPERTY_FILE_NAME.
     */
    public static String getPropertyFileName() {
        initDirectories();
        return propertyFileName;
    }
    
    public static String getPropertyListFileName() {
        initDirectories();
        return propertyListFileName;
    }


    /**
     * initialise the directories
     *
     */
    private static void initDirectories() {
    	if (applicationDirectory == null) {
    		systemJarFileDirectory = getLibDirectory();

    		if (systemJarFileDirectory == null || "".equals(systemJarFileDirectory)) {
    			systemJarFileDirectory = "G:\\Programs\\RecordEdit\\HSQL\\lib";
    			//systemJarFileDirectory = "C:\\Program Files\\RecordEdit\\MSaccess\\lib";
    			//jarListFileDirectory = "/media/sda1/Bruces/Work/RecordEditParams";
    			//jarListFileDirectory = "/home/knoppix/RecordEdit/HSQLDB/lib";
    			//systemJarFileDirectory = "/media/sdc1/RecordEditor/USB/lib";
    		} else {
    			globalPropertyFileName = systemJarFileDirectory + "/" + GLOBAL_PROPERTIES_FILE_NAME;
    			
    			globalProperties = readProperties(globalPropertyFileName);
    		}

    		String s = getPropertiesDirectoryWithFinalSlash();

    		if (s == null) {
    			s = "G:\\Users\\Bruce01\\RecordEditor_HSQL\\";
    		}
    		userJarFileDirectory = s;
    		System.out.println("!! Properties Directory ~~ " + s);

    		applicationDirectory = s;

    		System.out.println("!! Application Directory ~~ " + applicationDirectory);

    		propertyFileName = new File(applicationDirectory + USER_PARAM_FILE).toString();
    		propertyListFileName = new File(applicationDirectory + USER_PARAM_LIST_FILE).toString();

    		//        s = expandVars(getResourceString("JarListFilesDirectory"));
    		//        if (s == null || s.trim().equals("")) {
    		//            s = APPLICATION_DIRECTORY;
    		//        }
    	}
    }

    public static String getPropertiesDirectory() {
    	if (propertiesDir == null) {
    		propertiesDir = getString(PROPERTIES_DIR_VAR_NAME);
    	
			try {
				System.out.println();
				System.out.println(System.getProperty("os.name") + " " + System.getenv("APPDATA") + " " + propertiesDir);
				System.out.println();
				String aData = System.getenv("APPDATA");
				if (IS_WINDOWS && aData != null) {
					appData = aData;
					if (propertiesDir != null && ! exists(expandVars(propertiesDir))) {		
						String s = getString(PROPERTIES_WIN_DIR_VAR_NAME);
						if (s != null && ! "".equals(s)) {
							propertiesDir = s;
	//						userHome = aData;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    
		String s = expandVars(propertiesDir);
		
		if (s == null) {
			System.out.println(" -----> " + propertiesDir + " " + expandVars(propertiesDir) );
			s = userHome + File.separator + "RecordEditor_HSQL";
		} 
		return s;
    }

    //TODO
    public static String getPropertiesDirectoryWithFinalSlash() {
 		return addPathSeperator(getPropertiesDirectory());
     }

	/**
	 * get The filename
	 * @param key filename key
	 * @return the request file name
	 */
	public static String getFileName(final String key) {
	    return expandVars(getString(key));
	}


	/**
	 * Get the value of a key from the properties file
	 * @param key parameter to be retrieved
	 * @return value of the requested value
	 */
	public static String getString(final String key) {
	    //checkInit();

		try {
		    String s = null;
		    if (useUserParamFile && properties != null) {
		   		s = properties.getProperty(key);
		    }
		    
//		    if (key== PROPERTIES_DIR_VAR_NAME) {
//		    	System.out.println(" == X1 ==> " + s);
//		    }
		    
		    if (paramProperties != null && s == null) {
		    	s = paramProperties.getProperty(key);
		    }

		    if (s == null) {
		        s = getSecondayStr(key);
		    }
/*		    if (key== PROPERTIES_DIR_VAR_NAME) {
		    	System.out.println(" == X8 ==> " + s);
		    }
*/
			return s;
		} catch (Exception e) {
			return getResourceString(key);
		}
	}

	public static List<String> getStringList(final String key, int max) {
		//checkInit();
		String key2 = key + ".";
		ArrayList<String> ret = new ArrayList<String>(Math.min(25, max+1));
		String s;

		for (int i = 0; i <= max; i++) {
			s = getStringfl(key2 + i);
			if (s != null) {
				ret.add(s);
			}
		}

		return ret;
	}
	
	private static String getStringfl(String key) {
		if (propertiesLists == null) {
			propertiesLists = readProperties(getPropertyListFileName());
			if (propertiesLists == null) {
				propertiesLists = new Properties();
			}
		}
		
		return propertiesLists.getProperty(key);
	}

	public static void setArrayItem(String key, int idx, String value) {
		//checkInit();
		if (propertiesLists == null) {
			propertiesLists = new Properties();
		}

		propertiesLists.put(key + "." + idx, value);
	}
	
	public static void writeListProperties() {
		writeProperties(propertiesLists, getPropertyListFileName());
	}

	/**
	 * Get the value of a key from the properties file
	 * @param key parameter to be retrieved
	 * @return value of the requested value
	 */
	public static String getSecondayString(final String key) {
	    //checkInit();
		try {
			return getSecondayStr(key);
		} catch (Exception e) {
			return getResourceString(key);
		}
	}

	/**
	 * Get the value of a key from the properties file
	 * @param key parameter to be retrieved
	 * @return value of the requested value
	 */
	private static String getSecondayStr(final String key) {

	    String s = null;
	    if (useUserParamFile) {
	    	if (properties2 != null) {
	    		s = properties2.getProperty(key);
	    	}
	    }

//	    if (key== PROPERTIES_DIR_VAR_NAME) {
//	    	System.out.println(" == X2 ==> " + s + " " + useUserParamFile + " " + (properties2 != null));
//	    }

        //System.out.print(key + " : " + s);

        if (s == null && globalProperties != null) {
        	s = globalProperties.getProperty(key);
        }
//	    if (key== PROPERTIES_DIR_VAR_NAME) {
//	    	System.out.println(" == X3 Global ==> " + s + " " +  (globalProperties != null) + " " + globalPropertyFileName);
//	    }
	    if (s == null) {
	        s = getResourceString(key);
	    }
//	    if (key== PROPERTIES_DIR_VAR_NAME) {
//	    	System.out.println(" == X4 Resource ==> " + s + " " );
//	    }
//
	    if (s == null && defaultValues.containsKey(key)) {
	    	s = defaultValues.get(key);
	    }
//	    if (key== PROPERTIES_DIR_VAR_NAME) {
//	    	System.out.println(" == X5 Default ==> " + s + " " +  defaultValues.containsKey(key));
//	    }

		//System.out.println("Get Variable " + key + " --> " + s);
		return s;
	}

	/**
	 * Get the value of a key from the properties file
	 * @param key parameter to be retrieved
	 * @return value of the requested value
	 */
	private static String getResourceString(final String key) {

		if (resourceBundle != null) {
			try {
				return resourceBundle.getString(key);
			} catch (MissingResourceException e) {
			}
		}

		return null;
	}

	public static String getUserhome() {
		return userHome;
	}

	/**
	 * do init if neccesary
	 *
	 */
	private static void checkInit() {

	    if (toInit) {
	        if (useUserParamFile) {
	            properties = readProperties();

//	            if (globalPropertyFileName != null) {
//	            	globalProperties = readProperties(globalPropertyFileName);
//	            }

	            useUserParamFile = (properties != null) || (globalProperties != null);
	        }
	        toInit = false;
	    }
	}


	/**
	 * read the properties file
	 * @return properties file
	 */
	public static Properties readProperties() {
	    return readProperties(getPropertyFileName());
	}

	/**
	 * read the properties file
	 *
	 * @param name property filename
	 *
	 * @return properties file
	 */
	public static Properties readProperties(String name) {
	    Properties ret = null;

        File f = new File(name);
        System.out.println("Properties File: "
               + name
               + " " + f.exists());
        if (f.exists()) {
            ret = new Properties();
            try {
                ret.load(new FileInputStream(f));
            } catch (Exception e) {
                ret = null;
                e.printStackTrace();
            }
        }

	    return ret;
	}


    /**
     * @param pBundleName The BUNDLE_NAME to set.
     */
    public static void setBundleName(final String pBundleName) {

        bundleName = pBundleName;
        resourceBundle = ResourceBundle.getBundle(bundleName);
    }


    /**
     * Expand variables at the start of the file name.
     * Variables supported are<ul>
     * <li><b>&lt;lib&gt;</b> RecordEditor lib directory (ie where we are running from
     * <li><b>&lt;install&gt;</b> RecordEditor install directory
     * <li><b>&lt;home&gt;</b> User home or user parameter directory
     * <li><b>&lt;rehome&gt;</b> RecordEditor parameter directory
     * </ul>
     * @param name filename file name the includes variables
     * @return file name with variables expanded out
     */
    public static String expandVars(String name) {
        if (name == null) {
            return null;
        }

        String ret = name;
        String lcName = name.toLowerCase();


        if (lcName.startsWith("<lib>")) {
            ret = getLibDirectory() /*+ File.separator*/  + name.substring(5);
        } else if (lcName.startsWith("<install>")) {
            ret = getBaseDirectory() + name.substring("<install>".length());
            System.out.println("Expand >> " + name + " >> " + ret);
        } else if (lcName.startsWith("<home>")) {
            ret = userHome + name.substring(6);
        } else if (lcName.startsWith("<appdata>")) {
            ret = appData + name.substring(9);
        } else if (lcName.startsWith("<rehome>")) {
            ret = reHomeDirectory + name.substring(REHOME_VAR_LENGTH);
        } else if ((lcName.startsWith("<reproperties>/") || lcName.startsWith("<reproperties>\\"))
         	   && applicationDirectory != null
         	   && applicationDirectory.endsWith(File.separator)) {
           ret = applicationDirectory + name.substring(REPROPERTIES_VAR_LENGTH + 1);
        } else if (lcName.startsWith("<reproperties>")) {
            ret = applicationDirectory + name.substring(REPROPERTIES_VAR_LENGTH);
        }
        
        if (ret != null && ret.length() > 0 
        && (ret.charAt(0) == '/' || ret.indexOf(':') >= 0)) {
			ret = new File(ret).toString();
		}
        //File.separator
        return ret;
    }

    /**
     * Encode for variables
     * @param name file name to be encoded
     * @return encoded string
     */
    public static String encodeVars(String name) {

        if (name == null) {
            return null;
        }

        String ret = name;
        String lcName = name.toLowerCase();
        String reprops = getPropertiesDirectory();
        String bd;

        if (baseDirectory == null) {
            getDirectories();
        }

        if (! "".equals(getLibDirectory())
        && lcName.startsWith(getLibDirectory().toLowerCase())) {
            ret = "<lib>"   + name.substring(getLibDirectory().length());
        } else if ( check(lcName, (bd = getBaseDirectory()))) {
            ret = "<install>" + name.substring(bd.length());
        } else if (check(lcName, reprops)) {
            ret = "<reproperties>" + name.substring(reprops.length());
        } else if (check(lcName, userHome)) {
            ret = "<home>" + name.substring(userHome.length());
        } else if (check(lcName, reHomeDirectory)) {
        	ret = "<rehome>" + name.substring(reHomeDirectory.length());
        } else if (check(lcName, appData)) {
             ret = "<appdata>" + name.substring(appData.length());
        }

        //System.out.println("~~> " + name + " ==> " + ret);
        return ret;
    }

    private static boolean check(String lcName, String var) {
    	return (var != null) && (! "".equals(var))
                && lcName.startsWith(var.toLowerCase());
    }

    
    
    /**
     * get base directory
     *
     * @return RecordEditor install directory
     */
    public static final String getBaseDirectory() {

        if (baseDirectory == null) {
            getDirectories();
        }

        return baseDirectory;
    }

    /**
     * get lib directory
     *
     * @return RecordEditor lib directory
     */
    public static final String getLibDirectory() {

        if (libDirectory == null) {
            getDirectories();
        }

        return libDirectory;
    }

    public static String formatLangDir(String dir) {
 	   if (dir == null || "".equals(dir)) {
		   return "<install>/lang/";
	   }

 	   return dir;
    }
    /**
     * Get the install and lib directories for the RecordEditor
     *
     */
    private static void getDirectories() {


        baseDirectory = "G:\\Programs\\RecordEdit\\HSQL";
        libDirectory  = "";
        URL o = Parameters.class.getClassLoader().getResource("net/sf/RecordEditor/utils/params/Parameters.class");

        if (o != null) {
            String dir = o.toString();

            if (dir.startsWith("jar:")) {
                int pos = dir.indexOf('!');

//                String dfltEncName = AccessController.doPrivileged(
//                	    new GetPropertyAction("file.encoding")
//                	);
                //System.out.println("     dir: " + dir);
                //baseDirectory = new File(dir.substring(SIZE_OF_FILE_URL_PREFIX)).getParent();
                File baseFile = new File( dir.substring(SIZE_OF_FILE_URL_PREFIX, pos));
                File libFile = baseFile.getParentFile();
                String dfltCharset = Charset.defaultCharset().displayName();
                baseDirectory = baseFile.getPath();
               
                System.out.println();
                System.out.println();
                System.out.println("1 > " + baseFile.getPath());
                System.out.println("2 > " + baseFile.getAbsolutePath());
                try {
					System.out.println("3 > " + baseFile.getCanonicalPath());
					System.out.println("4 > " + URLDecoder.decode(baseFile.getPath(), dfltCharset));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                System.out.println(); 
                System.out.println();
                
 //               pos = baseDirectory.lastIndexOf('/');
                
                System.out.println();
                System.out.println(" !!! BaseDirectory = " + baseDirectory + " >" + o.getFile() + "< ");
//                System.out.println(" !!! libDirectory 1= " + baseDirectory.substring(0, pos));
                System.out.println(" !!! libDirectory 2= " + (new File(baseDirectory)).getParent());
                 
 //           	libDirectory = URLDecoder.decode(baseDirectory.substring(0, pos));
            	try {
					libDirectory = URLDecoder.decode(libFile.getPath(), dfltCharset); //baseFile.getParent();
					baseDirectory = URLDecoder.decode(libFile.getParent(), dfltCharset);
				} catch (UnsupportedEncodingException e) {
					libDirectory = libFile.getPath();
					baseDirectory = libFile.getParent();
					e.printStackTrace();
				}
            	System.out.println(" !!! libDirectory 3= " + libDirectory);
                System.out.println();

//                try {
//                	libDirectory = URLDecoder.decode(baseDirectory.substring(0, pos), dfltEncName);
//                } catch (UnsupportedEncodingException e) {
//                	System.out.println();
//                	System.out.println(">>>   Error getting Lib directory - dfltEncodeName Exception   <<<");
//                	System.out.println();
//				}
//                baseDirectory = libDirectory;
//                pos = baseDirectory.lastIndexOf('/');
//                if (pos >= 0) {
//                    baseDirectory = baseDirectory.substring(0, pos);
//                }
                
                System.out.println("     base dir: " + baseDirectory);
                System.out.println("     lib  dir: " + libDirectory);
            }
        }
    }

    /**
     * Set the Application directory (JRecord / RecordEditor) ...
     * @param dir new Application directory
     */
    public static void setApplicationDirectory(String dir) {

    	if (dir == null || "".equals(dir)) {
    		return;
    	}

    	baseDirectory = dir;
    	if ((dir.endsWith("/") || dir.endsWith("\\"))
    	&& (dir.length() > 1)) {
    		baseDirectory = dir.substring(0, dir.length() - 2);
    	}
    	libDirectory = baseDirectory + "/lib";
    	System.out.println("-- Setting Application Directory");
        System.out.println("     base dir: " + baseDirectory);
        System.out.println("     lib  dir: " + libDirectory);

        try {
        	 URL[] urls = {new File("file:" + libDirectory + "/properties.zip" ).toURI().toURL()};

        	 ResourceBundle rb = ResourceBundle.getBundle(bundleName,
        			 Locale.getDefault(),
        			 new URLClassLoader(urls));
        	 
        	 if (rb == null) {
        		 try {
        			 rb = ResourceBundle.getBundle(bundleName,
        					 Locale.getDefault(),
        					 ClassLoader.getSystemClassLoader());
        		 } catch (Exception e) {
        		 }
        	 }

             if (rb != null) {
        		resourceBundle = rb;
        		useUserParamFile = ! "N".equals(getResourceString("Allow_User_Params"));
        	 }
           	String s = expandVars(getResourceString("PropertiesDirectory")) + File.separator + USER_PARAM_FILE;
           	properties2 = readProperties(s);
           	System.out.println("   Properties 2:  " + s + " " + (properties2 != null));
         } catch (Exception e) {
        	  System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
//		}  finally {
//          		System.out.println("ClassLoader 2  " + ClassLoader.getSystemClassLoader().getClass().getName());
//          		System.out.println("ClassLoader 2  " + Parameters.class.getClassLoader().getClass().getName());

//	           URL[] urls = ((URLClassLoader) ClassLoader.getSystemClassLoader()).getURLs();
//
//	           System.out.println();
//	            for (int i = 0; i < urls.length; i++) {
//	                System.out.println("url " + i + " = " + urls[i].getFile());
//	            }
			// Going back to system class loader
//          		try {
//          			ResourceBundle.getBundle(bundleName,
//              			 Locale.getDefault(),
//               			 ClassLoader.getSystemClassLoader());
//          		} catch (Exception e) {
//				}
          		}
    }

	public static String getGlobalPropertyFileName() {
		initDirectories();
		return globalPropertyFileName;
	}

	/**
	 * @return the properties
	 */
	public static final Properties getProperties() {
		return properties;
	}

	public static final Properties getInitialisedProperties() {
		//checkInit();
		if (properties == null) {
			properties = new Properties();
		}
		return properties;
	}

	public static final void setProperty(String key, String value) {
		if (value == null) {
			if (properties != null && key != null) {
				properties.remove(key);
				notifyOfPropertyChange();
			}
		} else {		
			getInitialisedProperties()
				.setProperty(key, value);
			notifyOfPropertyChange();
		}
	}

	/**
	 * 
	 */
	public static void notifyOfPropertyChange() {
		changesToSave = true;

		if (savePropertyChanges) {
			notifyParamChg();
			writeProperties();
		}
	}

	public static final void setProperties(Properties prop) {
		properties = prop;
		changesToSave = true;
	}
	
	public static void loadParamProperties(String propertiesFilename) {
		if (propertiesFilename != null && propertiesFilename.length() > 0) {
			paramProperties = readProperties(propertiesFilename);
		}
	}

    public static final void writePropertiesIfChanged() {
    	if (changesToSave) {
    		writeProperties();
    	}
	}

    public static final void writeProperties() {
    	writeProperties(properties, getPropertyFileName());
    }
    
    private static final void writeProperties(Properties props, String propertyFileName2) {
        try {
 //           String propertyFileName2 = getPropertyFileName();
			renameFile(new File(propertyFileName2).toString());
            props.store(
                new FileOutputStream(propertyFileName2),
                "RecordEditor");
            
            if (props == properties) {
            	changesToSave = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final void renameFile(String fileName) {
    	renameFile(fileName, fileName + "~", true);
    }

    /**
     * Rename a file
     * @param fileName file to be renamed
     */
    public static final void renameFile(String fileName, String newFileName) {
    	renameFile(fileName, newFileName, false);
    }

    /**
     * Rename a file
     * @param fileName file to be renamed
     */
    private static final void renameFile(String fileName, String newFileName, boolean keeptrying) {
 //       File f = new File(fileName);
       File fNew = null;
       String newName = newFileName;
       boolean b = false;
       
       if (exists(newName)) {
    	   b = doDelete(newName);
    	   if (keeptrying) {  		   
    		   int i = 1;
	    	   while((! b) && exists(newName)) {
	    		   newName = newFileName + i++;
	    		
	    		   if (! exists(newName) ) {
	    			   break;
	    		   }
	    		   
	    		   b = doDelete(newName);
	    	   };
    	   }
       }
       System.out.println("File Delete: " + newFileName + " " + b);

//        Path oldFile = Paths.get(fileName);
//        Path dir = oldFile.getParent();        
//        Path fn = oldFile.getFileSystem().getPath(newFileName);
//        Path target = (dir == null) ? fn : dir.resolve(fn);        
//        oldFile.moveTo(target);
        
       fNew =  new File(newFileName);
       
       new File(fileName).renameTo(fNew);
    }

    public static boolean doDelete(String fileName) {
    	boolean b = false;
		try {
			File f;
			if (JAVA_VERSION > 1.699) {
				try {
					java.nio.file.Path newPath = java.nio.file.Paths.get(fileName);
				    Files.deleteIfExists(newPath);
				    b = true;
				   
				} catch (IOException e) {
					e.printStackTrace();
					
			        b = new File(fileName).delete();
				}
			} else if ((f = new File(fileName)).exists()){
				b = f.delete();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}


    	return b;
    }
    
    public static void doDeleteDirectory(String fileName) {
    	
    	File file = new File (fileName);
		if (file . exists()) {
	    	try {
	    		if (JAVA_VERSION > 1.699) {
	    			Path directory = Paths.get(fileName);
	    			Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
	    			   @Override
	    			   public FileVisitResult visitFile(Path file, java.nio.file.attribute.BasicFileAttributes attrs) throws IOException {
	    			       Files.delete(file);
	    			       return FileVisitResult.CONTINUE;
	    			   }

	    			   @Override
	    			   public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
	    			       Files.delete(dir);
	    			       return FileVisitResult.CONTINUE;
	    			   }
	    			});
	    		} else {
	    			delete6(file);
	    		}
			} catch (Throwable e) {
				e.printStackTrace();
			}
    	}
   }
    
    private static void delete6(File f) throws IOException {
    	  if (f.isDirectory()) {
    	    for (File c : f.listFiles())
    	      delete6(c);
    	  }
    	  
    	  if (!f.delete()) {
    	    throw new RuntimeException("Failed to delete file: " + f);
    	  }
    	}

    public static boolean isDefaultTrue(String s) {
    	return defaultTrue.contains(s);
    }

    public static boolean setDefaultTrue(String s) {
    	return defaultTrue.add(s);
    }

	/**
	 * @param savePropertyChanges the savePropertyChanges to set
	 */
	public static void setSavePropertyChanges(boolean savePropertyChanges) {
		Parameters.savePropertyChanges = savePropertyChanges;
	}

	public static String dropStar(String filename) {
		if (filename == null) {
			filename = "";
		} else if (filename.endsWith("*")) {
			filename = filename.substring(0, filename.length()-1);
		}
		return filename;
	}

	public static String getExtensionOnly(String filename) {
		String s = getExtension(filename);
		if (s.startsWith(".")) {
			s = s.substring(1);
		}

		return s;
	}

	public static String getExtension(String filename) {
		int lastDot;
		if (filename == null || ((lastDot = filename.lastIndexOf('.')) < 0)) {
			return "";
		}

		return filename.substring(lastDot);
	}

	private static void notifyParamChg() {

		for (AParameterChangeListner l : paramChangeListners) {
			try {
				l.notifyParamChanged();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	/**
	 * @param e
	 * @return
	 * @see java.util.ArrayList#add(java.lang.Object)
	 */
	public static boolean addParameterChangeListner(AParameterChangeListner e) {
		return paramChangeListners.add(e);
	}

	/**
	 * @param e
	 * @return
	 * @see java.util.ArrayList#add(java.lang.Object)
	 */
	public static void removeParameterChangeListner(AParameterChangeListner e) {
		paramChangeListners.remove(e);
	}

	/**
	 * Add path seperator at the end (if needed)
	 * 
	 * @param dirName directory name
	 * 
	 * @return updated directory name
	 */
	public static String addPathSeperator(String dirName) {
		String ret = dirName;
		if (doesNotEndWithPathSeperator(dirName)) {
			ret = ret + File.separatorChar;
		}
		
		return ret;
	}

	public static boolean doesNotEndWithPathSeperator(String dirName) {
		char ch;
		return (dirName != null && dirName.length() != 0
			&& (ch = dirName.charAt(dirName.length()-1)) != '\\' && ch != '/');
	}
    
    public static boolean exists(String filename) {
    	boolean ret;
    	if (JAVA_VERSION >= 1.699) {
    		ret = java.nio.file.Files.exists(java.nio.file.Paths.get(filename));
    	} else {
    		ret = new File(filename).exists();
    	}
    	
    	return ret;
    }

    
    public static final boolean isWindowsLAF() {
		return windowsLAF;
	}

    public static final void setWindowsLAF(boolean windowsLAF) {
		Parameters.windowsLAF = windowsLAF;
	}

	public static IEnvironmentValues getEnvironmentDefaults() {
    	if (IS_NIX || IS_MAC) {
    		return new EnvironmentLinux();
    	}
    	return new EnvironmentDefault();
    }
}