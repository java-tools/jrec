package net.sf.RecordEditor.utils.params;

import java.io.File;

import net.sf.JRecord.Numeric.Convert;
import net.sf.RecordEditor.utils.common.Common;

public class ProgramOptions {
    public static final char COMPRESS_SPACE = 'S';
    public static final char COMPRESS_READ = 'R';
    public static final char COMPRESS_READ_FAST_CPU = 'F';
    public static final char COMPRESS_NO = 'N';
    public static final char COMPRESS_YES = 'Y';

    public static final char LARGE_VB_YES  = 'Y';
    public static final char LARGE_VB_NO   = 'N';
    public static final char LARGE_VB_TEST = 'T';

    public static final char SIZE_MAXIMISED = 'M';
    public static final char SIZE_LAST      = 'L';
    public static final char SIZE_SPACE_AROUND = 'S';
    public static final char SIZE_SPECIFIED = 'D';

    private static final char[] compressOptions = {COMPRESS_SPACE, COMPRESS_READ, COMPRESS_READ_FAST_CPU, COMPRESS_NO, COMPRESS_YES};
    private static final char[] largeOptions = {LARGE_VB_NO, LARGE_VB_YES, LARGE_VB_TEST};
    private static final char[] startSize = {SIZE_MAXIMISED, SIZE_LAST, SIZE_SPACE_AROUND, SIZE_SPECIFIED};

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
	public final BoolOpt asterixInFileName = new BoolOpt(Parameters.ASTERIX_IN_FILE_NAME);
	public final BoolOpt useBigFixedModel  = new BoolOpt(Parameters.PROPERTY_BIG_FILE_USE_SPECIAL_FIXED_MODEL);
	public final BoolOpt showAllExportPnls = new BoolOpt(Parameters.SHOW_ALL_EXPORT_OPTIONS);
	public final BoolOpt deleteSelectedWithDelKey = new BoolOpt(Parameters.DEL_SELECTED_WITH_DEL_KEY);
	public final BoolOpt warnWhenUsingDelKey = new BoolOpt(Parameters.WARN_WHEN_USING_DEL_KEY);

	public final BoolOpt csvSearchFixed = new BoolOpt(Parameters.CSV_LOOK_4_FIXED_WIDTH);
	public final BoolOpt useFileWizard  = new BoolOpt(Parameters.USE_FILE_WIZARD);
	public final BoolOpt useSeperateScreens = new BoolOpt(Parameters.SEPERATE_WINDOWS);
	public final BoolOpt typeOnRecordScreen = new BoolOpt(Parameters.INCLUDE_TYPE_NAME, true);
	public final BoolOpt allowTextEditting  = new BoolOpt(Parameters.EDIT_RAW_TEXT, true);
	public final BoolOpt showCsvFChooserOptions = new BoolOpt(
														Parameters.CSV_SHOW_FILECHOOSER_OPTIONS,
														'\\' == File.pathSeparatorChar
													&&	(Common.LOOKS_INDEX == 1 || Common.RECORD_EDITOR_LAF));

	public final UpdateableBoolOpt highlightEmpty = new UpdateableBoolOpt(Parameters.PROPERTY_HIGHLIGHT_EMPTY);
	public final InternalBoolOption highlightEmptyActive = new InternalBoolOption(false);

	public final InternalBoolOption loadPoScreens       = new InternalBoolOption(false);
	public final InternalBoolOption xsltAvailable       = new InternalBoolOption(true);
	public final InternalBoolOption fileWizardAvailable = new InternalBoolOption(false);
	public final InternalBoolOption getTextPoPresent    = new InternalBoolOption(false);
	public final InternalBoolOption addTextDisplay      = new InternalBoolOption(false);
	public final InternalBoolOption standardEditor      = new InternalBoolOption(false);

	public final IntOpt significantCharInFiles1 = new IntOpt("SignificantCharInFiles.1", 6, 1);
	public final IntOpt significantCharInFiles2 = new IntOpt("SignificantCharInFiles.2", 12, 1);
	public final IntOpt significantCharInFiles3 = new IntOpt("SignificantCharInFiles.3", 18, 1);
	public final IntOpt launchIfMatch = new IntOpt("LauchEditorIfMatch", 8, 1);

	public final IntOpt cobolDialect = new IntOpt(Parameters.COBOL_DIALECT, Convert.FMT_MAINFRAME, 1);

	public final IntOpt chunkSize = new IntOpt(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, 1048576, 1024);
	public final IntOpt filterLimit = new IntOpt(Parameters.PROPERTY_BIG_FILE_FILTER_LIMIT, 300000, 1000);
	public final IntOpt bigFilePercent = new IntOpt(Parameters.PROPERTY_BIG_FILE_PERCENT, 14, 1);


	public final MultiValOpt compressOption = new MultiValOpt(Parameters.PROPERTY_BIG_FILE_COMPRESS_OPT, compressOptions, COMPRESS_READ);
	public final MultiValOpt largeVbOption = new MultiValOpt(Parameters.PROPERTY_BIG_FILE_LARGE_VB, largeOptions, LARGE_VB_YES);

	public final StringOpt cobolOptionFile = new StringOpt(
			Parameters.COBOL_OPT_FILE,
			Parameters.getPropertiesDirectoryWithFinalSlash() + "User/CobolOpts/CobolOptions.bin");

	public final StringOpt DEFAULT_IO_NAME = new StringOpt(Parameters.DEFAULT_IO, "");
	public final StringOpt DEFAULT_BIN_NAME = new StringOpt(Parameters.DEFAULT_BINARY, "");
	public final StringOpt COPYBOOK_READER = new StringOpt(Parameters.DEFAULT_COPYBOOK_READER, "");

	public final StringOpt XSLT_ENGINE = new StringOpt(Parameters.XSLT_ENGINE, "");

	public final FileNameOpt DEFAULT_FILE_DIRECTORY = new FileNameOpt("DefaultFileDirectory");
	public final FileNameOpt DEFAULT_COBOL_DIRECTORY = new FileNameOpt("DefaultCobolDirectory");
	public final FileNameOpt DEFAULT_COPYBOOK_DIRECTORY = new FileNameOpt(Parameters.COPYBOOK_DIRECTORY);
	public final FileNameOpt DEFAULT_VELOCITY_DIRECTORY = new FileNameOpt(Parameters.VELOCITY_TEMPLATE_DIRECTORY);
	public final FileNameOpt copybookVelocityDirectory = new FileNameOpt(Parameters.VELOCITY_COPYBOOK_DIRECTORY);
	public final FileNameOpt layoutExportDirectory = new FileNameOpt(Parameters.LAYOUT_EXPORT_DIRECTORY);
	public final FileNameOptWithDefault DEFAULT_XSLT_DIRECTORY = new FileNameOptWithDefault(Parameters.XSLT_TEMPLATE_DIRECTORY, "<reproperties>/User/Xslt/");
	public final FileNameOptWithDefault DEFAULT_SCRIPT_EXPORT_DIRECTORY = new FileNameOptWithDefault(Parameters.EXPORT_SCRIPT_DIRECTORY, "<reproperties>/User/ExportScripts/");
	public final FileNameOptWithDefault DEFAULT_SCRIPT_DIRECTORY = new FileNameOptWithDefault(Parameters.SCRIPT_DIRECTORY, "<reproperties>/User/Scripts/");
	public final FileNameOpt XSLT_JAR1 = new FileNameOpt(Parameters.XSLT_JAR1);
	public final FileNameOpt XSLT_JAR2 = new FileNameOpt(Parameters.XSLT_JAR2);



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


	public static class FileNameOpt {
		private String param;

		public FileNameOpt(String value) {
			param = value;
		}

		public String get() {
			return  Parameters.getFileName(param);
		}
		public String getNoStar() {
			return  Parameters.dropStar(get());
		}

		public void set(String newVal) {
			Parameters.setProperty(param, newVal);
		}
	}

	public static final class FileNameOptWithDefault {
		private String param;
		private String defaultValue;
		public FileNameOptWithDefault(String value, String defaultValue) {
			param = value;

			this.defaultValue = defaultValue;
		}

		public String get() {
			String s = Parameters.getFileName(param);

			if (s == null || "".equals(s)) {
				s = Parameters.expandVars(defaultValue);
				Parameters.setProperty(param, defaultValue);
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
}
