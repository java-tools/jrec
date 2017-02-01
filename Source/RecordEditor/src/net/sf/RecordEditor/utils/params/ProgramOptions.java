package net.sf.RecordEditor.utils.params;

import java.io.File;

import net.sf.JRecord.Numeric.ICopybookDialects;

public class ProgramOptions { 
	public static enum ProgramType {
		RECORD_EDITOR,
		CSV_EDITOR,
		PROTOBUF_EDITOR,
		AVRO_EDITOR
	}
	public static final String FILE_SEPERATOR  =  System.getProperty("file.separator");
	
    public static final char COMPRESS_SPACE = 'S';
    public static final char COMPRESS_READ  = 'R';
    public static final char COMPRESS_READ_FAST_CPU = 'F';
    public static final char COMPRESS_NO    = 'N';
    public static final char COMPRESS_YES   = 'Y';

    public static final char LARGE_VB_YES   = 'Y';
    public static final char LARGE_VB_NO    = 'N';
    public static final char LARGE_VB_TEST  = 'T';

    public static final char SIZE_MAXIMISED = 'M';
    public static final char SIZE_LAST      = 'L';
    public static final char SIZE_LAST_FORCED = 'F';
    public static final char SIZE_SPACE_AROUND = 'S';
    public static final char SIZE_NO_RESIZE = 'N';
    public static final char SIZE_SPECIFIED = 'D';
    public static final char SIZE_SPECIFIED_FORCED = 'E';
    public static final char SIZE_SCREEN_1 = '1';
    public static final char SIZE_SCREEN_2 = '2';
    public static final char SIZE_SCREEN_3 = '3';
//    public static final char SIZE_SCREEN_1_MAX = 'Z';

    private static final char[] compressOptions = {COMPRESS_SPACE, COMPRESS_READ, COMPRESS_READ_FAST_CPU, COMPRESS_NO, COMPRESS_YES};
    private static final char[] largeOptions = {LARGE_VB_NO, LARGE_VB_YES, LARGE_VB_TEST};
    private static final char[] startSize = {SIZE_MAXIMISED, SIZE_LAST, SIZE_LAST_FORCED, SIZE_SPACE_AROUND, 
    	SIZE_SPECIFIED, SIZE_SPECIFIED_FORCED, SIZE_NO_RESIZE,
    	SIZE_SCREEN_1, SIZE_SCREEN_2, SIZE_SCREEN_3};

    public ApplicationDetails applicationDetails = new ApplicationDetails(
    		"Recordeditor", "https://sourceforge.net/projects/record-editor/");
    public ProgramType programType;
	public final BoolOpt searchAllFields = new BoolOpt(Parameters.SEARCH_ALL_FIELDS);
	public final MultiValOpt screenStartSizeOpt  = new MultiValOpt(Parameters.SCREEN_SIZE_OPTION, startSize, SIZE_MAXIMISED);

	public final BoolOpt usePrefered = new BoolOpt(Parameters.PREFERED_AS_DEFAULT);
	public final BoolOpt warnBinaryFieldsAndStructureDefault = new BoolOpt(Parameters.WARN_BINARY_FIELDS_DEFAULT);
	public final BoolOpt runFieldSearchAutomatically = new BoolOpt(Parameters.FS_RUN_AUTOMATIC);
	public final BoolOpt searchForMainframeZoned = new BoolOpt(Parameters.FS_MAINFRAME_ZONED);
	public final BoolOpt searchForPcZoned = new BoolOpt(Parameters.FS_PC_ZONED);
	public final BoolOpt searchForComp3 = new BoolOpt(Parameters.FS_COMP3);
	public final BoolOpt searchForCompBigEndian = new BoolOpt(Parameters.FS_COMP_BIG_ENDIAN);
	public final BoolOpt useNewTreeExpansion = new BoolOpt(Parameters.USE_NEW_TREE_EXPANSION);
	public final BoolOpt searchForCompLittleEndian = new BoolOpt(Parameters.FS_COMP_LITTLE_ENDIAN) {

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.utils.common.ProgramOptions.BoolOpt#isSelected()
		 */
		@Override
		public boolean isSelected() {
			return (! searchForCompBigEndian.isSelected())
				&& super.isSelected();
		}

	};

	public final BoolOpt openPoFuzzyWindow = new BoolOpt(Parameters.PO_EDIT_OPEN_FUZZY_VIEW, true);
	public final StringOpt poChildScreenPosition = new StringOpt(Parameters.PO_EDIT_CHILD_SCREEN_POS, Parameters.CHILD_SCREEN_RIGHT);

	public final BoolOpt showRecordEditorTips = new BoolOpt(Parameters.SHOW_RECORDEDITOR_TIPS, true);
	public final BoolOpt logToFront = new BoolOpt(Parameters.BRING_LOG_TO_FRONT);

	public final BoolOpt loadInBackgroundThread = new BoolOpt(Parameters.PROPERTY_LOAD_FILE_BACKGROUND);
	public final BoolOpt asterixInFileName  = new BoolOpt(Parameters.ASTERIX_IN_FILE_NAME);
	public final BoolOpt useBigFixedModel   = new BoolOpt(Parameters.PROPERTY_BIG_FILE_USE_SPECIAL_FIXED_MODEL);
	public final BoolOpt showAllExportPnls  = new BoolOpt(Parameters.SHOW_ALL_EXPORT_OPTIONS);
	public final BoolOpt deleteSelectedWithDelKey = new BoolOpt(Parameters.DEL_SELECTED_WITH_DEL_KEY);
	public final BoolOpt warnWhenUsingDelKey= new BoolOpt(Parameters.WARN_WHEN_USING_DEL_KEY);

	public final BoolOpt csvSearchFixed     = new BoolOpt(Parameters.CSV_LOOK_4_FIXED_WIDTH);
	public final BoolOpt useFileWizard      = new BoolOpt(Parameters.USE_FILE_WIZARD);
	public final BoolOpt useSeperateScreens = new BoolOpt(Parameters.SEPERATE_WINDOWS);
	public final BoolOpt typeOnRecordScreen = new BoolOpt(Parameters.INCLUDE_TYPE_NAME, true);
	public final BoolOpt allowTextEditting  = new BoolOpt(Parameters.EDIT_RAW_TEXT, true);
//	public final BoolOpt showCsvFChooserOptions = new BoolOpt(
//														Parameters.CSV_SHOW_FILECHOOSER_OPTIONS,
//														Parameters.isWindowsLAF());
	public final BoolOpt addFileSearchBtn   = new BoolOpt(Parameters.ADD_FILE_SEARCH_BTN, false);

	public final UpdateableBoolOpt highlightEmpty = new UpdateableBoolOpt(Parameters.PROPERTY_HIGHLIGHT_EMPTY);
	public final InternalBoolOption highlightEmptyActive = new InternalBoolOption(false);

	public final InternalBoolOption loadPoScreens       = new InternalBoolOption(false);
	public final InternalBoolOption xsltAvailable       = new InternalBoolOption(true);
	public final InternalBoolOption fileWizardAvailable = new InternalBoolOption(false);
	public final InternalBoolOption getTextPoPresent    = new InternalBoolOption(false);
	public final InternalBoolOption addTextDisplay      = new InternalBoolOption(false);
	public final InternalBoolOption standardEditor      = new InternalBoolOption(false);
	public final InternalBoolOption useRowColSelection  = new InternalBoolOption(true);
	
	public final InternalBoolOption agressiveCompress   = new InternalBoolOption(true);
	public final InternalBoolOption doCompress          = new InternalBoolOption(true);
	public final InternalBoolOption overWriteOutputFile = new InternalBoolOption(false);

	public final IntOpt significantCharInFiles1 = new IntOpt("SignificantCharInFiles.1", 6, 1);
	public final IntOpt significantCharInFiles2 = new IntOpt("SignificantCharInFiles.2", 12, 1);
	public final IntOpt significantCharInFiles3 = new IntOpt("SignificantCharInFiles.3", 18, 1);
	public final IntOpt launchIfMatch = new IntOpt("LauchEditorIfMatch", 8, 1);

	public final IntOpt cobolDialect = new IntOpt(Parameters.DEFAULT_COBOL_DIALECT, ICopybookDialects.FMT_MAINFRAME, 1);

	public final IntOpt chunkSize = new IntOpt(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, 1048576, 1024);
	public final IntOpt filterLimit = new IntOpt(Parameters.PROPERTY_BIG_FILE_FILTER_LIMIT, 10000000, 1000);
	public final IntOpt bigFilePercent = new IntOpt(Parameters.PROPERTY_BIG_FILE_PERCENT, 14, 1);


	public final MultiValOpt compressOption = new MultiValOpt(Parameters.PROPERTY_BIG_FILE_COMPRESS_OPT, compressOptions, COMPRESS_READ);
	public final MultiValOpt largeVbOption = new MultiValOpt(Parameters.PROPERTY_BIG_FILE_LARGE_VB, largeOptions, LARGE_VB_YES);

	public final StringOpt cobolOptionFile = new StringOpt(
			Parameters.COBOL_OPT_FILE,
			Parameters.getPropertiesDirectoryWithFinalSlash() + "User/CobolOpts/CobolOptions.bin");

	public final StringOpt DEFAULT_IO_NAME = new StringOpt(Parameters.DEFAULT_IO, "");
	public final StringOpt DEFAULT_BIN_NAME = new StringOpt(Parameters.DEFAULT_COBOL_DIALECT_NAME, "");
	public final StringOpt COPYBOOK_READER = new StringOpt(Parameters.DEFAULT_COPYBOOK_READER, "");

	public final StringOpt XSLT_ENGINE = new StringOpt(Parameters.XSLT_ENGINE, "");

	public final FileNameOptWithDefault DEFAULT_CODEGEN_EXPORT_DIRECTORY
						= new FileNameOptWithDefault(Parameters.CODEGEN_DIRECTORY, Parameters.CODEGEN_DIRECTORY_DFLT); 
	public final StringOpt DEFAULT_CODEGEN_DIRECTORY_EXTENSION 
					= new StringOpt(Parameters.CODEGEN_DIRECTORY_EXTENSION, Parameters.CODEGEN_DIRECTORY_EXTENSION_DFLT);

	public final FileNameOpt DEFAULT_USER_TEMPLATE_DIRECTORY = new FileNameOpt(Parameters.TEMPLATE_DIRECTORY);

	public final FileNameOpt DEFAULT_FILE_DIRECTORY = new FileNameOpt("DefaultFileDirectory");
	public final FileNameOpt DEFAULT_COBOL_DIRECTORY = new FileNameOpt(Parameters.DEFAULT_COBOL_DIRECTORY);//"DefaultCobolDirectory");
	public final FileNameOpt DEFAULT_COPYBOOK_DIRECTORY = new FileNameOpt(Parameters.COPYBOOK_DIRECTORY);
	public final FileNameOptWithDefault DEFAULT_VELOCITY_DIRECTORY = new FileNameOptWithDefault(Parameters.VELOCITY_TEMPLATE_DIRECTORY, Parameters.DEFAULT_VELOCITY_TEMPLATE_DIRECTORY);
	public final FileNameOptWithDefault copybookVelocityDirectory  = new FileNameOptWithDefault(Parameters.VELOCITY_COPYBOOK_DIRECTORY, Parameters.DEFAULT_VELOCITY_COPYBOOK_DIRECTORY);
	public final FileNameOptWithDefault velocityScriptDir = new FileNameOptWithDefault(
			Parameters.VELOCITY_SCRIPT_DIR,
			Parameters.getPropertiesDirectoryWithFinalSlash() + "User/VelocityTemplates/Script/");
	public final FileNameOptWithDefault layoutExportDirectory = new FileNameOptWithDefault(Parameters.LAYOUT_EXPORT_DIRECTORY, Parameters.LAYOUT_EXPORT_DIRECTORY_DFLT);
	public final FileNameOptWithDefault DEFAULT_XSLT_DIRECTORY = new FileNameOptWithDefault(Parameters.XSLT_TEMPLATE_DIRECTORY, "<reproperties>/User/Xslt/");
	public final FileNameOptWithDefault DEFAULT_SCRIPT_EXPORT_DIRECTORY = new FileNameOptWithDefault(Parameters.EXPORT_SCRIPT_DIRECTORY, "<reproperties>/User/ExportScripts/");
	public final FileNameOptWithDefault DEFAULT_SCRIPT_DIRECTORY = new FileNameOptWithDefault(Parameters.SCRIPT_DIRECTORY, "<reproperties>/User/Scripts/");
	public final FileNameOpt XSLT_JAR1 = new FileNameOpt(Parameters.XSLT_JAR1);
	public final FileNameOpt XSLT_JAR2 = new FileNameOpt(Parameters.XSLT_JAR2);
	
	public final RelativeFileNameOpt schemaBuDir = new RelativeFileNameOpt(Parameters.COPYBOOK_DIRECTORY, "SchemaBu");
	
	public BoolOpt useLastDir = new BoolOpt(Parameters.OPEN_IN_LAST_DIRECTORY, true);



	public static class InternalBoolOption  {

		boolean val;


		public InternalBoolOption(boolean initialVal) {
			val = initialVal;
		}


		public boolean isSelected() {
			return val;
		}

		public void set(boolean newValue) {
			val = newValue;
		}
	}

	public static final class UpdateableBoolOpt extends InternalBoolOption {
		private String param;


		public UpdateableBoolOpt(String value) {
			super("Y".equalsIgnoreCase(Parameters.getString(value)));
			param = value;
		}

		public UpdateableBoolOpt(String value, Boolean def) {
			this(value);
			if (def) {
				Parameters.setDefaultTrue(value);
			}
		}

		public void set(boolean newVal) {
			String value = "N";

			val = newVal;
			if (val) {
				value = "Y";
			}

			Parameters.setProperty(param, value);
		}
	}

	public static final class IntOpt {
		private String param;
		private int defValue;
		private int mult;

		public IntOpt(String value, int defaultValue, int multBy) {
			param = value;
			defValue = defaultValue;
			mult = multBy;
		}

		public final int get() {
			int ret = defValue;

			try {
				String s = Parameters.getString(param);
				if (s != null && ! "".equals(s)) {
					ret = mult * Integer.parseInt(s);
				}
			} catch (Exception e) {
			}
			return ret;
		}
	}


	public static class StringOpt {
		protected final String param;
		private String defValue;


		public StringOpt(String value, String defaultValue) {
			param = value;
			defValue = defaultValue;
		}

		public final String get() {
			String ret = defValue;

			String s = Parameters.getString(param);
			if (s != null) {
				ret = s;
			}

			return ret;
		}

		public void set(String newVal) {
			Parameters.setProperty(param, newVal);
		}
	}


	public static class BasicFileNameOpt {
		protected String param;

		public BasicFileNameOpt(String value) {
			param = value;
		}

		public String get() {
			return  Parameters.getFileName(param);
		}
		public String getNoStar() {
			return  Parameters.dropStar(get());
		}
		public String getSlashNoStar() {
			String s=  Parameters.dropStar(get());
			if (s.endsWith("/") || s.endsWith("\\")) {
				
			} else {
				s = s + FILE_SEPERATOR;
			}
			return s;
		}

		public String getWithStar() {
			String s = get();
			if (s == null) {
				s = "";
			} else {
				if (s.endsWith("/") || s.endsWith("\\")) {
					s = s + "*";
				} else if (! s.endsWith("*")) {
					s = s + FILE_SEPERATOR + "*";
				}
			}
			return s;
		}
	}
	

	public static class FileNameOpt extends BasicFileNameOpt {

		public FileNameOpt(String value) {
			super(value);
		}


		public void set(String newVal) {
			Parameters.setProperty(param, newVal);
		}
	}
	
	public static class RelativeFileNameOpt extends BasicFileNameOpt {
		final String fileName;
		private RelativeFileNameOpt(String varName, String fileName) {
			super(varName);
			
			this.fileName = fileName;
		}
		
		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.utils.params.ProgramOptions.FileNameOpt#get()
		 */
		@Override
		public String get() {
			String filename = Parameters.dropStar(super.get());
			String s = "G:\\Users\\BruceTst01\\RecordEditor_HSQL\\CopyBook\\";;
			if (filename != null) {
				File file = new File(filename);
				File parentFile;
				if (file != null && (parentFile = file.getParentFile()) != null) {
					s = parentFile.toString();
				}
			}
			
			return s + File.separatorChar + fileName + File.separatorChar;
		}
	}

	public static final class FileNameOptWithDefault extends BasicFileNameOpt{
	//	private String param;
		public final String defaultValue;
		
		public FileNameOptWithDefault(String value, String defaultValue) {
			super(value); // = value;

			this.defaultValue = defaultValue;
		}

		public String get() {
			String s = Parameters.getFileName(param);

			if (s == null || "".equals(s)) {
				s = Parameters.expandVars(defaultValue);
//				try {
//					Parameters.setSavePropertyChanges(false);
//					Parameters.setProperty(param, defaultValue);
//				} finally {
//					Parameters.setSavePropertyChanges(true);
//				}
//			} else {
				
			}
			return s;
		}
		
	
		
		public String getSlashNoStar() {
			String s = get();
			
			if (s != null) {
				if (s.endsWith("*")) {
					s = s.substring(0, s.length() - 1);
				}
				if ( ! (s.endsWith("/") || s.endsWith("\\"))) {
					s = s + FILE_SEPERATOR;
				}
			}
			return s;
		}

	}

	public static final class MultiValOpt {
		private String param;
		private char[] options;
		private char defaultVal;


		public MultiValOpt(String param, char[] options, char defaultVal) {
			super();
			this.param = param;
			this.options = options;
			this.defaultVal = defaultVal;
		}

		public char get() {
			char ret = defaultVal;

			String s = Parameters.getString(param);

	    	if (s == null || "".equals(s)) {

	    	} else {
	    		s = s.toUpperCase();
	    		for (int i =0; i < options.length; i++) {
	    			if (s.charAt(0) == options[i]) {
	    				ret = options[i];
	    				break;
	    			}
	    		}
	    	}

	    	return ret;
		}
	}
	
	public static class ApplicationDetails {
		final String applicationId, website;

		public ApplicationDetails(String applicationId, String website) {
			super();
			this.applicationId = applicationId;
			this.website = website;
		}

		/**
		 * @return the applicationId
		 */
		public final String getApplicationId() {
			return applicationId;
		}

		/**
		 * @return the website
		 */
		public final String getWebsite() {
			return website;
		}
		
		
	}
}
