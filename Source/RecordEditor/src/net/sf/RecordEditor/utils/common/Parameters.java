/*
 * Created on 30/11/2004
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *   - changed to optional read properties from user properties
 *     file (as well as system properties
 */
package net.sf.RecordEditor.utils.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.HashSet;
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

    public static final int NUMBER_OF_LOADERS = 20;
    public static final int NUMBER_OF_USER_FUNCTIONS = 32;
    
    
    public static final String VAL_RECORD_EDITOR_DEFAULT = "RecordEditor_Default";
     
    public static final String PROPERTY_BIG_FILE_PERCENT = "BigFilePercentage"; 
    public static final String PROPERTY_BIG_FILE_CHUNK_SIZE = "BigFileChunkSize"; 
    public static final String PROPERTY_BIG_FILE_COMPRESS_OPT = "BigFileCompressOption"; 
    public static final String PROPERTY_BIG_FILE_LARGE_VB = "BigFileLargeVB"; 
    public static final String PROPERTY_BIG_FILE_FILTER_LIMIT = "BigFileFilterLimit"; 
    public static final String PROPERTY_BIG_FILE_DISK_FLAG = "BigFileDiskFlag"; 
    public static final String PROPERTY_BIG_FILE_USE_SPECIAL_FIXED_MODEL = "BigFileUseFixedModel"; 
   //public static final String PROPERTY_BIG_FILE_GZIP_WRITE = "BigFileGZipAnalyseSize"; 
    public static final String PROPERTY_TEST_MODE  = "TestMode";
    public static final String PROPERTY_LOAD_FILE_BACKGROUND  = "LoadFileInBackground";
    public static final String MAXIMISE_SCREEN  = "MaximiseScreen";
  
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
    
    public static final String PROPERTY_HIGHLIGHT_EMPTY       = "ShowEmpty.";

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
    
    public static final String BRING_LOG_TO_FRONT      = "LogToFront";   
    public static final String PREFERED_AS_DEFAULT      = "PreferedAsDefault"; 
    public static final String WARN_BINARY_FIELDS_DEFAULT      = "WarnBinFieldsAndDefault"; 
    public static final String COPYBOOK_DIRECTORY      = "CopybookDirectory";
    public static final String VELOCITY_COPYBOOK_DIRECTORY = "VelocityCopybookDirectory";
    public static final String VELOCITY_TEMPLATE_DIRECTORY = "VelocityTemplateDirectory";
    public static final String XSLT_TEMPLATE_DIRECTORY = "XsltTemplateDirectory";
    public static final String XSLT_JAR1 = "XsltJar1";
    public static final String XSLT_JAR2 = "XsltJar2";
    public static final String INVALID_FILE_CHARS      = "InvalidFileChars";
    public static final String FILE_REPLACEMENT_CHAR   = "FileReplChar";
    public static final String ASTERIX_IN_FILE_NAME    = "AsterixInFileName";
 //   public static final String DIRECTORY_IN_SOURCE_VAR = "DirVarInSource.";
    
    public static final String COPY_SAVE_DIRECTORY  = "CopySaveDirectory";
    public static final String COPY_SAVE_FILE  	   = "CopySaveFile";
    
    public static final String COMPARE_SAVE_DIRECTORY  = "CompareSaveDirectory";
    public static final String COMPARE_SAVE_FILE  	   = "CompareSaveFile";
    public static final String FILTER_SAVE_DIRECTORY   = "FilterSaveDirectory";
    public static final String HIDDEN_FIELDS_SAVE_DIRECTORY   = FILTER_SAVE_DIRECTORY;
    public static final String SORT_TREE_SAVE_DIRECTORY   = "SortTreeSaveDirectory";
    public static final String RECORD_TREE_SAVE_DIRECTORY = "RecordTreeSaveDirectory";
    
    public static final String DEFAULT_COPYBOOK_READER = "CopyBookReader";
    public static final String DEFAULT_COPYBOOK_WRITER = "CopyBookWriter";
    public static final String XSLT_ENGINE             = "XsltEngine";

    public static final String DEFAULT_DATABASE        = "DefaultDB";
    public static final String DEFAULT_IO       	   = "DefaultIO";
    public static final String DEFAULT_BINARY		   = "DefaultBin";

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
    
    private static final HashSet<String> defaultTrue = new HashSet<String>(10);

	private static  String bundleName = DEFAULT_BUNDLE_NAME;
	private static final String USER_PARAM_FILE      = "Params.Properties";
	private static final int SIZE_OF_FILE_URL_PREFIX = 9;

    private static String baseDirectory = null;
    private static String libDirectory = null;


	private static  ResourceBundle resourceBundle = null;
	private static Properties properties = null;
	private static Properties properties2 = null;
	private static Properties globalProperties = null;

	private static boolean useUserParamFile = ! "N".equals(getResourceString("Allow_User_Params"));
	private static boolean toInit = true;

    public static final String USER_HOME       = System.getProperty("user.home");
	private static String reHomeDirectory = USER_HOME;

	private static String applicationDirectory = null;
	private static String propertyFileName;
	private static String globalPropertyFileName = null;
	private static String systemJarFileDirectory, 
	                      userJarFileDirectory = USER_HOME;
	
	public static boolean savePropertyChanges = true;
	
	static {
		try {
			resourceBundle = ResourceBundle.getBundle(bundleName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] defTrueKeys = {
				PROPERTY_BIG_FILE_USE_SPECIAL_FIXED_MODEL,   
				PROPERTY_LOAD_FILE_BACKGROUND,
				FS_RUN_AUTOMATIC,
				BRING_LOG_TO_FRONT,
				WARN_BINARY_FIELDS_DEFAULT,
				PROPERTY_PGN_ICONS,
				USE_NEW_TREE_EXPANSION,
				SEARCH_ALL_FIELDS,
				MAXIMISE_SCREEN,
		};

		for (String s : defTrueKeys) {
			defaultTrue.add(s);
		}
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

    /**
     * initialise the directories
     *
     */
    private static void initDirectories() {
    	if (applicationDirectory == null) {
    		String s = getPropertiesDirectory();
    		
    		userJarFileDirectory = s;
    		System.out.println("!! Properties Directory ~~ " + s);
    		applicationDirectory = s + File.separator;
    		System.out.println("!! Application Directory ~~ " + applicationDirectory);

    		propertyFileName = applicationDirectory + USER_PARAM_FILE;

    		//        s = expandVars(getResourceString("JarListFilesDirectory"));
    		//        if (s == null || s.trim().equals("")) {
    		//            s = APPLICATION_DIRECTORY;
    		//        }
    		systemJarFileDirectory = getLibDirectory();

    		if (systemJarFileDirectory == null || "".equals(systemJarFileDirectory)) {
    			systemJarFileDirectory = "C:\\Program Files\\RecordEdit\\MSaccess\\lib";
    			//jarListFileDirectory = "/media/sda1/Bruces/Work/RecordEditParams";
    			//jarListFileDirectory = "/home/knoppix/RecordEdit/HSQLDB/lib";
    			//systemJarFileDirectory = "/media/sdc1/RecordEditor/USB/lib";
    		} else {
    			globalPropertyFileName = systemJarFileDirectory + "/Params.Properties";
    		}
    	}
    }
    
    public static String getPropertiesDirectory() {
		String s = expandVars(getResourceString(PROPERTIES_DIR_VAR_NAME));
		if (s == null) {
			s = USER_HOME + File.separator + ".RecordEditor/HSQLDB";
		}
		return s;
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
	    checkInit();
		try {
		    String s = null;
		    if (useUserParamFile) {
		    	if (properties != null) {
		    		s = properties.getProperty(key);
		    	}
		    	
		    	if (s == null && properties2 != null) {
		    		s = properties2.getProperty(key);
//		    		System.out.println(" ::: Got 2: " + key + " = " + s);
		    	}
		        
		        if (s == null && globalProperties != null) {
		        	s = globalProperties.getProperty(key);
		        }
		    }

            //System.out.print(key + " : " + s);

		    if (s == null) {
		        s = getResourceString(key);
		    }
			//System.out.println("Get Variable " + key + " --> " + s);
			return s;
		} catch (Exception e) {
			return getResourceString(key);
		}
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

	/**
	 * do init if neccesary
	 *
	 */
	private static void checkInit() {

	    if (toInit) {
	        if (useUserParamFile) {
	            properties = readProperties();
	            
	            if (globalPropertyFileName != null) {
	            	globalProperties = readProperties(globalPropertyFileName);
	            }

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
        } else if (lcName.startsWith("<home>")) {
            ret = USER_HOME + name.substring(6);
        } else if (lcName.startsWith("<rehome>")) {
            ret = reHomeDirectory + name.substring("<rehome>".length());
        } else if (lcName.startsWith("<reproperties>")) {
            ret = applicationDirectory + name.substring("<reproperties>".length());
        }
        
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

        if (baseDirectory == null) {
            getDirectories();
        }

        if (! "".equals(getLibDirectory())
        && lcName.startsWith(getLibDirectory().toLowerCase())) {
            ret = "<lib>"   + name.substring(getLibDirectory().length());
        } else if ((! "".equals(getBaseDirectory()))
               && lcName.startsWith(getBaseDirectory().toLowerCase())) {
            ret = "<install>" + name.substring(getBaseDirectory().length());
        } else if ((! "".equals(USER_HOME)) && (USER_HOME != null)
               && lcName.startsWith(USER_HOME)) {
            ret = "<home>" + name.substring(USER_HOME.length());
        } else if ((! "".equals(reHomeDirectory))
               && lcName.startsWith(reHomeDirectory.toLowerCase())) {
            ret = "<rehome>" + name.substring(reHomeDirectory.length());
        }

        //System.out.println("~~> " + name + " ==> " + ret);
        return ret;
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

    /**
     * Get the install and lib directories for the RecordEditor
     *
     */
    private static void getDirectories() {


        baseDirectory = "C:\\JavaPrograms\\RecordEdit\\HSQL";
        libDirectory  = "";
        URL o = Parameters.class.getClassLoader().getResource("net/sf/RecordEditor/utils/common/Parameters.class");

        if (o != null) {
            String dir = o.toString();

            if (dir.startsWith("jar:")) {
                int pos = dir.indexOf('!');
                
//                String dfltEncName = AccessController.doPrivileged(
//                	    new GetPropertyAction("file.encoding")
//                	);
                //System.out.println("     dir: " + dir);
                baseDirectory = dir.substring(SIZE_OF_FILE_URL_PREFIX, pos);
                pos = baseDirectory.lastIndexOf('/');
            	libDirectory = URLDecoder.decode(baseDirectory.substring(0, pos));

//                try {
//                	libDirectory = URLDecoder.decode(baseDirectory.substring(0, pos), dfltEncName);
//                } catch (UnsupportedEncodingException e) {
//                	System.out.println();
//                	System.out.println(">>>   Error getting Lib directory - dfltEncodeName Exception   <<<");
//                	System.out.println();
//				}
                baseDirectory = libDirectory;
                pos = baseDirectory.lastIndexOf('/');
                if (pos >= 0) {
                    baseDirectory = baseDirectory.substring(0, pos);
                }
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
		}  finally {
//          		System.out.println("ClassLoader 2  " + ClassLoader.getSystemClassLoader().getClass().getName());
//          		System.out.println("ClassLoader 2  " + Parameters.class.getClassLoader().getClass().getName());

//	           URL[] urls = ((URLClassLoader) ClassLoader.getSystemClassLoader()).getURLs();
//
//	           System.out.println();
//	            for (int i = 0; i < urls.length; i++) {
//	                System.out.println("url " + i + " = " + urls[i].getFile());
//	            }
			// Going back to system class loader
          		try {
          			ResourceBundle.getBundle(bundleName, 
              			 Locale.getDefault(),
               			 ClassLoader.getSystemClassLoader());
          		} catch (Exception e) {
				}	
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
		checkInit();
		if (properties == null) {
			properties = new Properties();
		}
		return properties;		
	}

	public static final void setProperty(String key, String value) {
		properties.setProperty(key, value);
		
		if (savePropertyChanges) {
			writeProperties();
		}
	}
	
	public static final void setProperties(Properties prop) {
		properties = prop;
	}
	
    public static final void writeProperties() {
    	
        try {
            renameFile(getPropertyFileName());
            properties.store(
                new FileOutputStream(getPropertyFileName()),
                "RecordEditor");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final void renameFile(String fileName) {
    	renameFile(fileName, fileName + "~");
    }

    /**
     * Rename a file
     * @param fileName file to be renamed
     */
    public static final void renameFile(String fileName, String newFileName) {
        File f = new File(fileName);
        File fNew;

        fNew = new File(newFileName);

        if (fNew.exists()) {
            fNew.delete();
        }

        f.renameTo(fNew);
    }


    public static boolean isDefaultTrue(String s) {
    	return defaultTrue.contains(s);
    }

	/**
	 * @param savePropertyChanges the savePropertyChanges to set
	 */
	public static void setSavePropertyChanges(boolean savePropertyChanges) {
		Parameters.savePropertyChanges = savePropertyChanges;
	}

}