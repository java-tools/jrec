/*
 * Created on 8/05/2004
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *   - improved find
 *   - basic replace actions added
 *   - new method getFileNameNoDirectory to get the filename
 *     without the directory details
 * # Version 0.60 Bruce Martin 2007/02/16
 *   - Support for sorting
 *   - new all fields search / replace function
 *   - fixed bug in setValue whcih was wrongly converting objects to
 *     strings
 *   - Better Error detection / messages
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - Support for Full line mode
 *   - hex and Text updates
 *   - Creating views from selected records
 *   - JRecord Spun off, code reorg
 *  # Version 0.61b Bruce Martin 2007/05/07
 *   - Changed heading to allow testing with Marathon
 */
package net.sf.RecordEditor.re.file;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;
import java.util.zip.GZIPInputStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.TreeNode;

import net.sf.JRecord.ByteIO.IByteReader;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractChildDetails;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.JRecord.Details.Attribute;
import net.sf.JRecord.Details.IColumnInsertDelete;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.LineCompare;
import net.sf.JRecord.Details.Options;
import net.sf.JRecord.IO.AbstractLineIOProvider;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.AbstractLineWriter;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.JRecord.IO.LineReaderWrapper;
import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.JRecord.Types.TypeManager;
import net.sf.JRecord.detailsSelection.RecordSel;
import net.sf.RecordEditor.edit.display.util.LinePosition;
import net.sf.RecordEditor.layoutWizard.FileAnalyser;
import net.sf.RecordEditor.re.file.filter.Compare;
import net.sf.RecordEditor.re.file.filter.FilterDetails;
import net.sf.RecordEditor.re.file.filter.FilterFieldBaseList;
import net.sf.RecordEditor.re.util.FileStructureDtls;
import net.sf.RecordEditor.utils.basicStuff.IRetrieveTable;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.GcManager;
import net.sf.RecordEditor.utils.common.StreamUtil;
import net.sf.RecordEditor.utils.fileStorage.DataStoreIterator;
import net.sf.RecordEditor.utils.fileStorage.DataStoreLargeView;
import net.sf.RecordEditor.utils.fileStorage.DataStoreRange;
import net.sf.RecordEditor.utils.fileStorage.IDataStore;
import net.sf.RecordEditor.utils.fileStorage.DataStoreLarge;
import net.sf.RecordEditor.utils.fileStorage.DataStoreStd;
import net.sf.RecordEditor.utils.fileStorage.FileDetails;
import net.sf.RecordEditor.utils.fileStorage.IDataStoreIterator;
import net.sf.RecordEditor.utils.fileStorage.IDataStoreView;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.lang.ReOptionDialog;
import net.sf.RecordEditor.utils.msg.UtMessages;
import net.sf.RecordEditor.utils.params.ProgramOptions;
import net.sf.RecordEditor.utils.screenManager.ReMsg;
import net.sf.RecordEditor.utils.swing.DelayedFieldValue;
import net.sf.RecordEditor.utils.swing.array.ArrayNotifyInterface;
import net.sf.RecordEditor.utils.swing.common.EmptyProgressDisplay;
import net.sf.RecordEditor.utils.swing.common.IProgressDisplay;
import net.sf.RecordEditor.utils.swing.treeTable.TreeTableNotify;


/**
 * This class is the RecordEditor's internal representation of a File and File-view. It contains
 * routines to <ul compact>
 * <li>methods to read and write a file
 * <li>access and update fields
 * <li>AbstractTableModel routines (ie access / updating fields)
 * <li>Adding / deleting records
 * <li>File search routines
 * </ul>
 *
 * @author Bruce Martin
 * @version 0.53
 */
@SuppressWarnings({ "serial", "rawtypes" })
public class FileView	extends 			AbstractTableModel
						implements 	Iterable<AbstractLine>, TableModelListener, IFileAccess,
									TreeModelListener, AbstractChangeNotify, IGetView, ArrayNotifyInterface {

	private static final String INTERNAL_ERROR_LINES_CLEARED_SAVE_IS_NOT_POSSIBLE
			= LangConversion.convert("Internal Error: lines cleared, save is not possible !!!");
	private static final String ONLY_ONE_IS_ALLOWED = "Only one {0} is allowed !!!";
	public  static final  int SPECIAL_FIELDS_AT_START  = 2;
	private static final  int BUFFER_SIZE = 65536;

    private static final  int SET_USING_FIELD = 1;
    public  static final  int SET_USING_TEXT  = 2;
    public  static final  int SET_USING_HEX   = 3;

	public  static final  int SELECTION_COLUMN = 0;
	public  static final  int LINE_NUMER_COLUMN = 1;
    private static final  int INITIAL_FILE_SIZE = 1000;
    private static final  int MINIMUM_FILE_SIZE = 512;

    private static final  int CAN_INSERT = 1;
    private static final  int FAILED_2ND_ROOT_NOT_ALLOWED = 3;
    private static final  int FAILED_CHILD_ALREADY_EXISTS = 4;
    private static final  int FAILED_CHILD_NOT_IN_PARENT = 5;

    private static final byte NO_CHANGE   = 1;
    private static final byte BEING_SAVED = 2;
    private static final byte CHANGED     = 3;

    private static final EmptyProgressDisplay EMPTY_PROGRESS = new EmptyProgressDisplay();
    private static final CheckStore NO_CHECK = new CheckStore() {
		@Override public void checkDatasStore(int numLines) {}
	};


    private static final FieldMapping NULL_MAPPING =  new FieldMapping(new int[0]);

    private static final String[] LINE_COLUMN_HEADINGS = LangConversion.convertArray(
    		LangConversion.ST_COLUMN_HEADING, "Record_ColumnHeadings", new String[] {
    	"", "Full Line",  "Hex (1 Line)", "Hex (2 Lines)", "Hex (SPF Edit Style)", "Hex (Alternative)"
    });
	private String[] lineColumns = new String[LINE_COLUMN_HEADINGS.length];

	private int maxNumColumns = -1;
	private int defaultPreferredIndex = 0;
    //TODO this class badly needs to be split up
    //TODO ---------------------------------------------

    public boolean saveAvailable = true;

	//private static AbstractLine[] copySrc = null;
	private static IDataStore<AbstractLine> copySrc = null;

	private String fileName;
	private String altName = null;

	private AbstractLineIOProvider ioProvider;

	private int currLayoutIdx = 0;
	private boolean fileRead = false;
	private boolean clearWaiting = false;
	private String  msg = "";

	private boolean toSave  = true;
	private boolean view    = false;

	//private List<AbstractLine> lines;
	private IDataStore<AbstractLine> lines;
	private AbstractLayoutDetails layout;

	private boolean browse;
	private byte changeStatus = NO_CHANGE;

	private JFrame frame = null;

	private FileView baseFile;

	private FieldMapping columnMapping = NULL_MAPPING;
	private boolean displayErrorDialog = true;
	private boolean allowMultipleRecords = true;

	private TreeTableNotify treeTableNotify = null;
	private WeakHashMap<AbstractLine, AbstractLineNode> nodes = null;


	private ArrayList<WeakReference<FileWriter>> writers = new ArrayList<WeakReference<FileWriter>>();
	
	private long memoryCompare = Common.getMemoryCompare();

	private boolean textFile;
	
	private int initialFileStructure;
	

	/**
	 * Notify File class that background save  has completed
	 */
	private PropertyChangeListener saveDoneListner = new PropertyChangeListener() {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			dropCompletedWriters();
			if (clearWaiting && writers.size() == 0) {
				clearWaiting = false;
				doClear();
			}
		}
	};

	/**
	 * Create empty View / storage
	 * @param pFd record layout
	 * @param pIoProvider ioProvider to use in reading/writing files
	 * 		  (can be null)
	 * @param pBrowse wether to browse the file
	 */
	public FileView(
     	   final AbstractLayoutDetails pFd,
		   final AbstractLineIOProvider pIoProvider,
     	   final boolean pBrowse) {
		super();

		newLines(INITIAL_FILE_SIZE, Constants.NULL_INTEGER, null, "");
		this.fileName   = null;
		this.layout     = pFd;
		this.ioProvider = pIoProvider;
		this.browse     = pBrowse;
		this.baseFile   = this;
		this.initialFileStructure = layout==null? 0 : layout.getFileStructure();
		checkIfTextFile();

		if (ioProvider == null) {
			ioProvider = LineIOProvider.getInstance();
		}
	}

	/**
	 * create a internal representation of a file
	 *
	 * @param pFileName   file being read in to the computer
	 * @param pFd         File Layout Description
	 * @param pBrowse     Browse the file (no updates)
	 *
	 * @throws IOException any error that occurs while reading the file
	 */
	public FileView(final String pFileName,
	        	   final AbstractLayoutDetails pFd,
	        	   final boolean pBrowse)  throws IOException, RecordException  {
	    this(pFileName, pFd, LineIOProvider.getInstance(), pBrowse);
	}


	/**
	 * create a internal representation of a file
	 *
	 * @param pFileName   file being read in to the computer
	 * @param pFd         File Layout Description
	 * @param pIoProvider IO routine Provider
	 * @param pBrowse     Browse the file (no updates)
	 *
	 * @throws IOException any error that occurs while reading the file
	 */
	public FileView(final String pFileName,
     	   			final AbstractLayoutDetails pFd,
     	   			final AbstractLineIOProvider pIoProvider,
     	   			final boolean pBrowse) throws IOException, RecordException {
		super();


		this.layout     = pFd;
		this.ioProvider = pIoProvider;
		this.browse     = pBrowse;
		this.baseFile   = this;
		checkIfTextFile();

		readFile(pFileName);
	}


	/**
	 * Create a view of a file from a data-store
	 *
	 * @param pLines    lines to display
	 * @param pBaseFile the basefile
	 * @param pRemapColumns column remapping
	 */
	public FileView(final IDataStore<? extends AbstractLine> pLines,
	        		final FileView pBaseFile, 
	        		final FieldMapping colMapping) {
		this(pLines, pBaseFile, pLines.getLayoutRE(), colMapping, pBaseFile == null);
	}

	/**
	 * Create a view of a file
	 *
	 * @param pLines    lines to display
	 * @param pBaseFile the basefile
	 * @param pRemapColumns column remapping
	 */
	public FileView(final IDataStore<? extends AbstractLine> pLines,
	        		final FileView pBaseFile,
	        		final FieldMapping colMapping,
	        		boolean pBrowse) {
		this(pLines, pBaseFile, pLines.getLayoutRE(), colMapping, pBrowse);
	}


	public FileView(String name, final IDataStore pLines,
    		final AbstractLayoutDetails layoutDtls) {
		this(pLines, null, layoutDtls, null, false);

		altName = name;
		saveAvailable = false;
	}

	private FileView(final IDataStore pLines,
    		final FileView pBaseFile,
    		final AbstractLayoutDetails layoutDtls,
    		final FieldMapping colMapping,
    		boolean pBrowse) {
		super();

		this.lines      = pLines;
		checkDataStore4ModeListner(lines);

		if (colMapping != null) {
			columnMapping = colMapping;
		}

	    fileRead = true;
	    view     = pBaseFile != null;

		if (pBaseFile == null) {
			baseFile = this;
			fileName = "";
			layout = (AbstractLayoutDetails) layoutDtls;
			browse = pBrowse;
			ioProvider = LineIOProvider.getInstance();
		} else {
			this.baseFile	= pBaseFile;
			this.fileName	= baseFile.getFileName();
			this.layout		= baseFile.getLayout();
			this.browse		= baseFile.isBrowse();
			this.ioProvider	= baseFile.getIoProvider();
			baseFile.addTableModelListener(this);
		}

	    initLineCols();
	    checkIfTextFile();
	}


	private void initLineCols() {
		StringBuffer buf = new StringBuffer();
		int num;

		num = layout.getMaximumRecordLength() / 10 + 1;
		String s;

		for (int i = 1; i < num + 1; i++) {
			s = "    " + i;
			buf.append("    +").append(s.substring(s.length() - 5 ));
		}

		lineColumns[1] = buf.toString();
		lineColumns[4] = lineColumns[1];

		buf = new StringBuffer();
		for (int i = 1; i < num + 1; i++) {
			s = "         " + i;
			buf.append("         +").append(s.substring(s.length() - 10 ));
		}

		lineColumns[2] = buf.toString();
		lineColumns[3] = lineColumns[2];

		buf = new StringBuffer();
		for (int i = 1; i < num + 1; i++) {
			s = "                " + i;
			buf.append("                +").append(s.substring(s.length() - 16) + " ");
		}

		lineColumns[5] = buf.toString();

		//System.out.println("Column length Summary " + num + " " + layout.getMaximumRecordLength());
	}


	public final void clear() {
		dropCompletedWriters();

		if (writers.size() > 0) {
			clearWaiting = true;
			if (! view) {
				doCompress(0);
			}
		} else {
			doClear();
		}
		nodes = null;
	}

	public final void doClear() {
		if (lines != null) {
			lines.clear();
		}
		if (baseFile != null) {
			baseFile.removeTableModelListener(this);
		}

		lines = null;
		
		GcManager.doGcIfNeccessary(0.50);
	}

	public void readFile(String pFileName)
	throws IOException, RecordException {
		AbstractLineReader reader;
		File file;
		

		this.fileName   = pFileName;
		file = new File(pFileName);
		

		if (file.exists()) {
		    if (file.isDirectory()) {
		        throw new IOException(pFileName + " is a directory not a file");
		    } else if (! file.isFile()) {
		        throw new IOException(pFileName + " is not a file");
		    } else {
		    	boolean isGZip = checkIfGZip(pFileName);
		    	int structure = layout.getFileStructure();

				if (ioProvider == null) {
					ioProvider = LineIOProvider.getInstance();
				}
				
				long fileLength = file.length();
				int bufSize = FileWriter.calcBufferSize(BUFFER_SIZE, fileLength);

				boolean checkFileStructureStd = layout.getOption(Options.OPT_CHECK_4_STANDARD_FILE_STRUCTURES) == Options.YES;
				boolean checkFileStructureX = layout.getOption(Options.OPT_CHECK_4_STANDARD_FILE_STRUCTURES2) == Options.YES;
				if (checkFileStructureStd || checkFileStructureX) {
					byte[] data = StreamUtil.read(new FileInputStream(fileName), 33000);

					if (data != null && data.length > 0) {
						FileAnalyser fileAnaylser = FileAnalyser.getAnaylserNoLengthCheck(data, "");
						int fd = fileAnaylser.getFileStructure();
						if (fd > 0 && fd != Constants.IO_BIN_TEXT
						&&  (    fileAnaylser.getLinesRead() > 6
							 || (checkFileStructureStd && fileAnaylser.getLinesRead() > 2) )) {
							structure = fd;
							layout.setAttribute(Attribute.FILE_STRUCTURE, fd);
							Common.logMsgRaw(
									AbsSSLogger.LOG,
									UtMessages.FILE_FORMAT_USED.get(
											new Object[] {
													this.getFileNameNoDirectory(),
													FileStructureDtls.getStructureName(fd)}),
									null);
						}
					}
				}


				reader = ioProvider.getLineReader(layout);

		        FileInputStream rff = new FileInputStream(pFileName);
		        if (isGZip) {
		            reader.open(new GZIPInputStream(rff, bufSize), layout);

		            readFile(reader, file, isGZip, rff, pFileName);
		        } else if (Common.OPTIONS.useBigFixedModel.isSelected()
		        	   &&  (structure == Constants.IO_FIXED_LENGTH
		        	     || structure == Constants.IO_FIXED_BYTE_ENTER_FONT)
		        	   &&  useBigFileModel(fileLength)) {
		        	lines = DataStoreLarge.getFixedLengthRecordStore((LayoutDetail)layout, pFileName);
		        } else {
		        	BufferedInputStream rf = new BufferedInputStream(rff, bufSize);

		            reader.open(rf, pFileName, layout);
		            browse |= (! reader.canWrite());
		            readFile(reader, file, isGZip, rf, pFileName);
		        }
	            rff.close();
		    }
		} else {
			reader = ioProvider.getLineReader(layout);
			reader.generateLayout(layout);
			retrieveLayout(reader);
		    toSave = false;
		    
			newLines(INITIAL_FILE_SIZE, Constants.NULL_INTEGER, null, "");
		}

		initLineCols();
	}



	/**
	 * Reads a file
	 *
	 * @param reader file reader to use
	 *
	 * @throws IOException any IOerror that occurs
	 */
	private void readFile(
			AbstractLineReader<AbstractLayoutDetails> reader,
			File file,
			boolean isGZip,
			InputStream rf,
			String fname)
	throws IOException {
	    AbstractLine line;
	    int count = 0;
	    long interval = 1000000000l;
	    long t, t1, t2, tt1, tt2;
	    long time = System.nanoTime();
	    long lastTime = time;
	    //long s1;
	    boolean isVeryBigFile = file.length() > ((long) Integer.MAX_VALUE);

	    double total = file.length();
	    double ratio;
	    long len = file.length();
	    CheckStore checkStore = NO_CHECK;

	    retrieveLayout(reader);
//	    allocateLines(file, isGZip, len, reader, fname);
	    
	    t1 = time;
	    tt1 = 0;
	    tt2 = 0;

	    line = reader.read();
	    retrieveLayout(reader);
	    allocateLines(file, isGZip, len, reader, fname);
	    
	    if (layout != null && layout.getOption(Options.OPT_SUPPORTS_BLOCK_STORAGE) == Options.YES
	    && lines instanceof ArrayList
	    && (isGZip || useBigFileModel(len * 10) )){
	    	checkStore = new ChkStore();
	    }

	    if (line != null) {
    		lines.add(line);
	    }

	    if (Common.OPTIONS.loadInBackgroundThread.isSelected()) {
	    	ProgressDisplay readProgress = new ProgressDisplay("Reading", file.getName());
	    	try {
			    while ((line = reader.read()) != null) {
			       tt1 += System.nanoTime() - t1;
			       t2 = System.nanoTime();
			       lines.add(line);
			       tt2 += System.nanoTime() - t2;
			       if (count++ > 2000) {
				       checkStore.checkDatasStore(count);

			    	   count = 0;
			    	   t = System.nanoTime();
			    	   if (t - lastTime > interval) {
			    		   if (isVeryBigFile) {
			    			   ratio = ((double)lines.getSpaceRE()) / total;
			    		   } else {
			    			   ratio = 1 - ((double)getAvailable(rf)) / total;
			    		   }
			    		   readProgress.updateDisplay(
			    				   lines.getSizeDisplayRE(),
			    				   (int) (100 * ratio));
			    		   lastTime = t;
			    	   }
			       }

			       t1 = System.nanoTime();
			    }
	    	} finally {
	    		readProgress.done();
	    	}
	    } else {
		    while ((line = reader.read()) != null) {
		       lines.add(line);
		       checkStore.checkDatasStore(1);
		    }
	    }
	    retrieveLayout(reader);
	    lines.setLayoutRE(layout);

	    reader.close();


	    time = (( System.nanoTime() - time) / 10000000);
	    double ttime = ((double) time) / 100;
//	    Common.logMsg(1,
//	    		lines.getSizeDisplay() + "\n  Load Time : " + ttime,
//	    		null);
	    System.out.println(lines.getSizeDisplayRE());
	    System.out.println("File Read Time: "  + ttime);

	    System.out.println("Read Total: " + (tt1 / 100000000));
	    System.out.println("Add  Total: " + (tt2 / 100000000));
	    System.out.println("     Lines: " + lines.size());
	}

	private int getAvailable(InputStream rf) {
		int ret = 0;
		try {
			ret =  rf.available();
		} catch (Exception e) {

		}

		return ret;
	}

	private void retrieveLayout(AbstractLineReader<AbstractLayoutDetails> reader) {
	    AbstractLayoutDetails l = reader.getLayout();
	    if (l != null) {
	    	layout = l;
	    }
	}

	private void allocateLines(File file, boolean isGZip, long len, AbstractLineReader<AbstractLayoutDetails> reader, String fname) {
    	int numLines = INITIAL_FILE_SIZE ;
    	int maxLength = layout.getMaximumRecordLength();
    	AbstractLineReader<AbstractLayoutDetails> r = reader;
    	
		if (lines != null && lines instanceof TableModelListener) {
			this.removeTableModelListener((TableModelListener) lines);
		}
    	try {
    		if (maxLength > 0) {
    			numLines = ((int) ((len / maxLength) * 11 / 10)) ;
    			if (isGZip) {
    				numLines *= 3;
    			}

    			numLines = Math.max(MINIMUM_FILE_SIZE, numLines);
    		}
			if (isGZip) {
				len *= 14;
				r = null;
			}
    		newLines(numLines, len, r, fname);
    	} catch (Exception e) {
    		newLines(INITIAL_FILE_SIZE, Constants.NULL_INTEGER, null, fname);
    	}

	}

	/**
	 * Adds a line to the Line-List
	 *
	 * @param line2add line to be added the line-list
	 */
	public void add(AbstractLine line2add) {
	    lines.add(line2add);
	}

	/**
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {

		return getLayoutColumnCount(currLayoutIdx) + SPECIAL_FIELDS_AT_START;
	}
	
	

	/**
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		if (lines == null) {
			return 0;
		}
		return lines.size();
	}

	/**
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int row, int col) {

		return getValueAt1(currLayoutIdx, row, col);
	}

	/**
	 *
	 * @param layoutIndex layout index to use
	 * @param row row to look up
	 * @param col column to look up
	 * @return requested value
	 */
	public Object getValueAt1(int layoutIndex, int row, int col) {
		if ((row >= getRowCount()) || (col >= getLayoutColumnCount(layoutIndex) + SPECIAL_FIELDS_AT_START) || (col == 0)) {
			return null;
		} else if (col == LINE_NUMER_COLUMN) {
			return Integer.toString(row + 1);
		} else {
			switch(DisplayType.displayType(layout, layoutIndex)) {
			case DisplayType.PREFFERED:
				return getValueAt(lines.get(row).getPreferredLayoutIdx(), row, col - SPECIAL_FIELDS_AT_START);
			case DisplayType.FULL_LINE:
				return lines.get(row).getFullLine();
			case DisplayType.HEX_LINE:
			//System.out.println("    - Getting data");
				return lines.get(row).getData();
			default:
				return getValueAt(layoutIndex, row, col - SPECIAL_FIELDS_AT_START);
			}
		}
	}


	/**
	 * @see javax.swing.table#setValueAt
	 */
	@Override
	public void setValueAt(Object val, int row, int col) {
		setValueAt(currLayoutIdx, val, row, col);
	}

	/**
	 * @see javax.swing.table#setValueAt
	 */
	public void setValueAt(int layoutIdx, Object val, int row, int col) {

		if (col == LINE_NUMER_COLUMN) {
			if (val != null) {
				String s = val.toString().trim().toLowerCase();
				if (s.length() == 0) { return; }
				String numStr = s.substring(1).trim();
				int num = 1;
				if (numStr.length() > 0) {
					num = 0;
					try {
						num = Integer.parseInt(numStr);
					} catch (NumberFormatException e) {
					}
				}
				
				int end = Math.min(num, lines.size() - row);
				switch (s.charAt(0)) {
				case 'd': 
					int[] linesToDel = new int[end];
					for (int i = 0; i < end; i++) {
						linesToDel[i] = row + i;
					}
					deleteLines(linesToDel);
					break;
				case 'r':
					AbstractLine l = lines.getTempLineRE(row).getNewDataLine();
					AbstractLine[] linesToIns = new AbstractLine[end];
					for (int i = 0; i < end; i++) {
						linesToIns[i] = l.getNewDataLine();
					}
					addLines(row, 0, linesToIns);
					break;
				case 'i': 
					addLines(row, 0, createLines(num));
					break;
				}
			}
		} else if ((row >= getRowCount()) || (col >= (getLayoutColumnCount(layoutIdx) + SPECIAL_FIELDS_AT_START))
		|| (col < LINE_NUMER_COLUMN)) {
//			System.out.println("**Set Value 1 "
//					+ row  + ">= " + getRowCount()
//					+ " " + col + " >= " + (getLayoutColumnCount(layoutIdx) + 2)
//					+ " " + col + " <= " + LINE_NUMER_COLUMN);
		} else if (frame != null) {
			setValueAt(frame, layoutIdx, row, col - SPECIAL_FIELDS_AT_START, val);
		}
	}


	/**
	 * Get the column name
	 *
	 * @see javax.swing.table#getColumnName
	 */
	public String getColumnName(int col) {

		switch (col) {
		case SELECTION_COLUMN:
			if (Common.TEST_MODE) {
				return "Sl";
			}
			return null;
		case LINE_NUMER_COLUMN:			return LangConversion.convert(LangConversion.ST_COLUMN_HEADING, "Line");
		default:
			switch(DisplayType.displayType(layout, currLayoutIdx)) {
			case DisplayType.FULL_LINE:
			   		return LINE_COLUMN_HEADINGS[1];
			case DisplayType.PREFFERED:
		    	//System.out.print("   Column name " + col + " " + getFieldName(this.currentPreferredIndex, col));
		    	return getFieldName(this.defaultPreferredIndex, col);
			case DisplayType.HEX_LINE:
		    	int idx = Math.min(
		    						currLayoutIdx - layout.getRecordCount(),
		    	                    LINE_COLUMN_HEADINGS.length - 1);

		    	return lineColumns[idx] + Common.COLUMN_LINE_SEP
		    			+ LINE_COLUMN_HEADINGS[idx];
		    default:
		    	return getFieldName(currLayoutIdx, col);
		    }
		}
		//return null; // getRealColumnName(currLayout, col - 2);
	}

	/**
	 * @return the defaultPreferredIndex
	 */
	public final int getDefaultPreferredIndex() {
		return defaultPreferredIndex;
	}

	/**
	 * @param currentPreferredIndex the currentPreferredIndex to set
	 */
	public final void setDefaultPreferredIndex(int currentPreferredIndex) {
		this.defaultPreferredIndex = Math.max(0, currentPreferredIndex);
	}

	/**
	 * @return the initialFileStructure
	 */
	public final int getInitialFileStructure() {
		return initialFileStructure;
	}

	/**
	 * @param initialFileStructure the initialFileStructure to set
	 */
	public final void setInitialFileStructure(int initialFileStructure) {
		this.initialFileStructure = initialFileStructure;
	}

	/**
	 *
	 * @param layoutIndex record index
	 * @param fldNum field number
	 * @return field name
	 */
	public String getFieldName(int layoutIndex, int fldNum) {
        int tCol = getRealColumn(layoutIndex, fldNum - SPECIAL_FIELDS_AT_START);
        String s;
        IFieldDetail fld = getLayoutField(layoutIndex, tCol);
        if (fld == null) {
        	return "";
        }
        int r = fld.getPos();
        int len = fld.getLen();

        if (len == Common.NULL_INTEGER) {
            s = Integer.toString(r);
        } else {
            s = r + " - " + len;
        }

        return s + Common.COLUMN_LINE_SEP
        				+ getRealColumnName(layoutIndex, tCol);
	}

	/**
	 * Get the number of fields in a specified layout
	 *
	 * @param layoutIdx layout index
	 *
	 * @return the number of fields
	 */
	public int getLayoutColumnCount(final int layoutIdx) {
        int l = layoutIdx;
        if (l < 0) {
            l = 0;
        }

        if (DisplayType.displayType(layout, l) == DisplayType.PREFFERED) {
        	if (maxNumColumns < 0) {
        		for (int i = 0; i <layout.getRecordCount(); i++) {
        			maxNumColumns = Math.max(maxNumColumns, layout.getRecord(i).getFieldCount());
//        			System.out.println(" --> " + maxNumColumns + " " + layout.getRecord(i).getFieldCount());
        		}
        	}
        	return maxNumColumns;
        } else if (l >= layout.getRecordCount()) {
            return 1;
        }
 //       System.out.println("> GetColumnCount " + layoutIdx + " " + l + " ; " + layout.getRecordCount()
 //       		+" -> " + columnMapping.getRowCount(l, layout.getRecord(l).getFieldCount()));

		return columnMapping.getColumnCount(l, layout.getRecord(l).getFieldCount());
	}


	/**
	 * Get there column name for a specified Layout / column
	 *
	 * @param layoutIdx layout which information is being requested
	 * @param col column that the name is requested for
	 *
	 * @return column name
	 */
	public String getColumnName(final int layoutIdx, final int col) {

		return getRealColumnName(layoutIdx,  getRealColumn(layoutIdx, col));
	}


	/**
	 * Get there column name for a specified Layout / column
	 *
	 * @param layoutIdx layout which information is being requested
	 * @param col column that the name is requested for
	 *
	 * @return column name
	 */
	public String getRealColumnName(final int layoutIdx, final int col) {
		String ret = null;

		if (col >= 0) {
			try {
				ret = layout.getRecord(layoutIdx).getField(col).getName();
			} catch (Exception ex1) {
				System.out.println(
					"get Col "
						+ layoutIdx
						+ " "
						+ col
						+ " "
						+ layout.getRecord(layoutIdx).getFieldCount()
						+ " ~ "
						+ ex1.getMessage());
			}
		}
		return ret;
	}

	/**
	 * Get a specified Field
	 *
	 * @param layoutIdx Records Layout that info is requested for
	 * @param idx requested field (within a layout)
	 *
	 * @return requested field
	 */
	public IFieldDetail getLayoutField(final int layoutIdx, final int idx) {
		AbstractRecordDetail rec = layout.getRecord(layoutIdx);
		if (idx >= rec.getFieldCount()) {
			return null;
		}
		return rec.getField(idx);
	}


	/**
	 * Get the value of a specified (using a supplied layout) for a
	 * row
	 *
	 * @param layoutIdx layout index to use for retrieving the field
	 * @param row row to be retrieved
	 * @param col column to be retrieved
	 * @return field values
	 */
	public Object getValueAt(final int layoutIdx, final int row, final int col) {
	    int tmpCol = getRealColumn(layoutIdx, col);

		try {
			if ((row >= getRowCount())
			|| (tmpCol < 0 && tmpCol != Constants.KEY_INDEX)
			|| layout == null
			|| layoutIdx > layout.getRecordCount()
			|| (tmpCol >= layout.getRecord(layoutIdx).getFieldCount())) {
				return null;
			}

			return lines.getTempLineRE(row).getField(layoutIdx, tmpCol);
		} catch (Exception e) {
			return null;
		}
	}





	/**
	 * Get a fields value as Text
	 *
	 * @param layoutIdx current layout
	 * @param row  required row
	 * @param col  require column
	 *
	 * @return field value (text)
	 */
	public Object getTextValueAt(int layoutIdx, int row, int col) {
	    col = getRealColumn(layoutIdx, col);

		if ((row >= getRowCount())
		|| (col < 0)
		|| (col >= layout.getRecord(layoutIdx).getFieldCount())) {
			return null;
		}
		return lines.get(row).getFieldText(layoutIdx, col);
	}


	/**
	 * Get a fields value as Hex
	 *
	 * @param layoutIdx Current record layout
	 * @param row required row
	 * @param col required column
	 *
	 * @return Fields value as Hex
	 */
	public Object getHexValueAt(int layoutIdx, int row, int col) {
	    col = getRealColumn(layoutIdx, col);

		if ((row >= getRowCount())
		|| (col < 0)
		|| (col >= layout.getRecord(layoutIdx).getFieldCount())) {
			return null;
		}
		return lines.get(row).getFieldHex(layoutIdx, col);

	}


	/**
	 * Set the value of a field
	 *
	 * @param parentFrame JFrame being displayed (used in any dialogue)
	 * @param layoutIdx Current record layout index
	 * @param row row being set
	 * @param col column being set
	 * @param val the new value of the row, column
	 */
	public void setValueAt(JFrame parentFrame, int layoutIdx, int row, int col, Object val) {
		if (row < getRowCount()) {
			setValueAt(parentFrame, layoutIdx, getLine(row), row, col, val);
		}
	}

	/**
	 * Set the value of a field
	 *
	 * @param parentFrame  JFrame being displayed (used in any dialogue)
	 * @param layoutIndex Current record layout index
	 * @param currLine currentLine being updated
	 * @param row row being set
	 * @param col column being set
	 * @param val the new value of the row, column
	 */
	public void setValueAt(JFrame parentFrame, int layoutIndex, AbstractLine currLine, int row, int col, Object val) {
		if (browse) { return; }
		int layoutIdx = layoutIndex;
		if (DisplayType.displayType(layout, layoutIdx) == DisplayType.PREFFERED) {
			layoutIdx = Math.max(0, currLine.getPreferredLayoutIdx());
		}

		col = getRealColumn(layoutIdx, col);
		if ((col < 0 && col != Constants.KEY_INDEX)
		||  (layoutIdx < layout.getRecordCount()
		   && layoutIdx >= 0
		   && col >= layout.getRecord(layoutIdx).getFieldCount())) {
			//System.out.println("Set Value 3");
		} else {
			//System.out.println("Set Value 4");
			if (val instanceof DelayedFieldValue) {
				//System.out.println("Set Value 5");
				val = ((DelayedFieldValue) val).getValue(parentFrame);
			}

			if (val == null
			|| (   layoutIdx <= layout.getRecordCount()
			    && currLine.isDefined(layoutIdx, col)
				&& val.equals(currLine.getField(layoutIdx, col)))) { return; }

		    if ((layoutIdx >= layout.getRecordCount())) {
		    	switch(DisplayType.displayType(layout, layoutIdx)) {
				case DisplayType.FULL_LINE:
		            String s;// = "";
		            //if (val != null) {
		                s = val.toString();
		            //}
		            currLine.setData(s);
		            fireRowUpdated(row, null, currLine);
		        default:
		        	if (val instanceof byte[]) {
		        		currLine.setData((byte[]) val);
		        	} else {

		        	}
		        }
		    } else if (parentFrame == null) {
			    try {
			    	Object o = null;
			    	try { o = currLine.getField(layoutIdx, col); } catch (Exception e1) { }
			        currLine.setField(layoutIdx, col, val);
			        setChangedValue(o, currLine.getField(layoutIdx, col));

		            fireRowUpdated(row, layoutIdx, col,currLine);
			    } catch (Exception e) {
			        e.printStackTrace();
                }
			} else {
			    updateField(SET_USING_FIELD, parentFrame, currLine, layoutIdx, row, col, val);
			}
		}
	}

	private void setChangedValue(Object oldValue, Object newValue) {
		boolean update =  (newValue != oldValue);
        if (oldValue != null) {
        	update = (! oldValue.equals(newValue));
        }
        if (update) {
        	setChanged(true);
        }
	}

	/**
	 * Set a field to a Hex value
	 * @param parentFrame parent fram to use
	 * @param inputType Type of input data (Text / Hex)
	 * @param recordIdx Record Layout Index
	 * @param row file row to update
	 * @param col column in the file to be updated
	 * @param val value to use
	 */
	public void setHexTextValueAt(JFrame parentFrame, int inputType, int recordIdx, int row, int col, String val) {

		if (baseFile.treeTableNotify != null) {
			return;
		}
		AbstractLine currLine = getLine(row);
		String oldValue;

		col = getRealColumn(recordIdx, col);
		if (inputType == SET_USING_TEXT) {
		    oldValue = currLine.getFieldText(recordIdx, col);
		} else {
		    oldValue = currLine.getFieldHex(recordIdx, col);
		}

		if (val.equals(oldValue)) {
		    return;
		}

	    updateField(inputType, parentFrame, currLine, recordIdx, row, col, val);
	}



	/**
	 * Try and update a field and prompt the user if ther is an error
	 * @param inputType type of input data (update type) to use (field / text hex)
	 * @param parentFrame parent frame
	 * @param currLine current line
	 * @param recordIdx record Index
	 * @param row row row to be updated
	 * @param col column being updated
	 * @param val new value
	 */
	private void updateField(int inputType, JFrame parentFrame, AbstractLine currLine,
	        int recordIdx, int row, int col, Object val) {

	    String iMsg = updateField_100_applyValue(inputType, currLine, recordIdx, row, col, val);

	    while (iMsg != null && this.displayErrorDialog) {
	        val =  JOptionPane.showInputDialog(parentFrame,
	                iMsg,
	                LangConversion.convert(LangConversion.ST_MESSAGE, "Conversion Error"),
	                JOptionPane.ERROR_MESSAGE,
	                null, null, val);

	        iMsg = null;
	        if (val != null) {
	            iMsg = updateField_100_applyValue(inputType, currLine, recordIdx, row, col, val);
	        }
	    }
	}

	/**
	 * Set the value of a field
	 *
	 * @param updateType of update to perform
	 * @param currLine line to be updated
	 * @param layoutIdx layout to be used to update line
	 * @param row row in the file being updated
	 * @param col column being updated
	 * @param val value to update thge field with
	 *
	 * @return Error message (if there is one)
	 */
	private String updateField_100_applyValue(int updateType, AbstractLine currLine, int layoutIdx, int row, int col, Object val) {

		try {
	    	Object o = null;
	    	try { o = currLine.getField(layoutIdx, col); } catch (Exception e1) { }

		    switch (updateType) {
		    	case(SET_USING_TEXT)  : currLine.setFieldText(layoutIdx, col, val.toString()); break;
		    	case(SET_USING_HEX)   : currLine.setFieldHex(layoutIdx, col, val.toString()); break;
		    	default               : currLine.setField(layoutIdx, col, val);
		    }

		    setChangedValue(o, currLine.getField(layoutIdx, col));

			fireRowUpdated(row, layoutIdx, col, currLine);

			return null;
		} catch (RecordException cex) {
			return cex.getMessage();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ex.getMessage();
		}
	}

	public final void fireTableRowsInsertedLocal(int firstLine, int lastLine) {
		if (this.treeTableNotify == null) {
			super.fireTableRowsInserted(firstLine, lastLine);
		} else {
			AbstractLineNode node;
			for (int i = firstLine; i <= lastLine; i++) {
				node = getNode(i);
				if (node == null) {
					super.fireTableRowsInserted(firstLine, lastLine);
					return;
				} else {
					treeTableNotify.fireTreeNodesInserted(node, node.getPath(), null, null);
				}
			}
			//invokeLaterDataChanged();
		}
	}

	/**
	 * Tell the world that the row has been updated
	 * @param row row
	 * @param currLine the actual line
	 */
	public void fireRowUpdated(int row, int recordIdx, int fieldIdx, AbstractLine currLine) {
		fireRowUpdated(row, layout.getField(recordIdx, fieldIdx), currLine);
	}

	/**
	 * Tell the world that the row has been updated
	 * @param row row
	 * @param currLine the actual line
	 */
	public void fireRowUpdated(int row, IFieldDetail fld, AbstractLine currLine) {

		if (baseFile.treeTableNotify == null) {
			tblFireRowUpdated(row, currLine);
		} else if (isView()) {
			baseFile.fireRowUpdated(row, fld, currLine);
		} else {
			AbstractLineNode node = getTreeNode(currLine);

			if (node == null) {
				tblFireRowUpdated(row, currLine);
			} else {
				checkForTreeDelete(node, currLine);
				baseFile.treeTableNotify.fireTreeNodesChanged(node, node.getPath(), null, null);
			}
		}
	}
	
	private final void checkForTreeDelete(AbstractLineNode node, AbstractLine currLine) {
		int option = currLine.getOption(Options.OPT_MULTIPLE_LINES_UPDATED);
		boolean updated = false;
		if ((option >= Options.YES)
		/*&& (fld != null)*/) {
			AbstractLine l;
			for (int i = node.getChildCount() - 1; i >= 0; i--) {
				TreeNode child = node.getChildAt(i);
				
				if (child instanceof AbstractLineNode && (l = ((AbstractLineNode) child).getLine()) != null) { 
					if (l.getOption(Options.OPT_LINE_DELETED) == Options.YES) {
						this.deleteOneLine(l);
						updated = true;
					}
				} else if (child != null) {
					for (int j = child.getChildCount() - 1; j >= 0; j--) {
						TreeNode c = child.getChildAt(j);
				
						if (c instanceof AbstractLineNode && (l =((AbstractLineNode) c).getLine()) != null 
						&& l.getOption(Options.OPT_LINE_DELETED) == Options.YES) {
							this.deleteOneLine(l);
							updated = true;
						}
					}
				}
			}
			if (updated) {
				super.fireTableDataChanged();
			}
		}
	}

	private void tblFireRowUpdated(int row, AbstractLine currLine) {

		if (view) {
	        int baseRecordNumber = baseFile.indexOf(currLine);
	        baseFile.fireTableRowsUpdated(baseRecordNumber, baseRecordNumber);
		} else if (row >= 0) {
		    super.fireTableRowsUpdated(row, row);
		} else {
			super.fireTableDataChanged();
		}
	}


	private AbstractLineNode getNode(int lineNumber) {
		AbstractLine l = lines.get(lineNumber);

		return getTreeNode(l);
	}

	/**
	 * wether cell is updateable
	 *
	 * @see javax.swing.table.AbstractTableModel.isCellEditable
	 */
	public boolean isCellEditable(int row, int col) {
		return col >= 1
			&& (currLayoutIdx != layout.getRecordCount() + 1
			  || (   (! getFileView().isBinaryFile())
			    	 && Common.OPTIONS.allowTextEditting.isSelected())
			    	 );
	}


	/**
	 * @return  the current layout
	 */
	public int getCurrLayoutIdx() {
		return currLayoutIdx;
	}

	/**
	 * Set the current layout
	 *
	 * @param newIdx new layout
	 */
	public void setCurrLayoutIdx(int newIdx) {

		if ((newIdx >= 0) && (newIdx < layout.getRecordCount() + 6)) {
			currLayoutIdx = newIdx;
		}
	}



//	/**
//	 * Get a specific field
//	 *
//	 * @param layoutIdx Layout to use
//	 * @param fieldNum Field number
//	 *
//	 * @return Requested field
//	 */
//
//	public FieldDetail getField(int layoutIdx, int fieldNum) {
//		return layout.getRecord(layoutIdx).getField(fieldNum);
//	}


	public boolean insertCells(int recordIdx, int row, int column, IRetrieveTable tbl) {
		List<String> colsToInsert;
		AbstractLine line;
		column = getRealColumn(recordIdx, column - SPECIAL_FIELDS_AT_START);
		boolean changed = false;
		while (row < lines.size() && tbl.hasMoreRows()) {
			line = getLine(row++);
			colsToInsert = tbl.nextRowAsList();
			if (line instanceof IColumnInsertDelete) {
				((IColumnInsertDelete) line)
						.insetColumns(
								column, 
								colsToInsert.toArray(new String[colsToInsert.size()]));
				changed = true;
			}
		}
		if (changed) {
//			baseFile.fireTableStructureChanged();
			setChanged(true);			
		}
		return changed;
	}

	public void deleteCells(int recordIdx, int[] recNums, int[] colNums) {
		
		if (layout.getOption(Options.OPT_CAN_ADD_DELETE_FIELD_VAlUES) == Options.YES
		&& recordIdx >= 0 && recordIdx < layout.getRecordCount()) {
			for (int i = 0; i < colNums.length; i++) {
				colNums[i] = getRealColumn(recordIdx, colNums[i] - SPECIAL_FIELDS_AT_START);
			}
			Arrays.sort(colNums);
			for (int row : recNums) {
				AbstractLine line = getLine(row);
				if (line instanceof IColumnInsertDelete) {
					((IColumnInsertDelete) line).deleteColumns(colNums);
				}
			}
			baseFile.fireTableDataChanged();
			setChanged(true);
		}
	}
	
	/**
	 * Delete a record from the file
	 *
	 * @param recNum record number to delete
	 */
	public void deleteLine(int recNum) {

		if (lines.size() >= recNum) {
		    if (view) {
//		        int baseRecordNumber = baseFile.indexOf(lines.get(recNum));
//
//				baseFile.deleteLine(baseRecordNumber);
				baseFile.deleteOneLine(lines.get(recNum));
		    } else {
		        fireTableRowsDeleted(recNum, recNum);
		        //System.out.println(" >> delete " + recNum + " " + recNum);
		        deleteNode(lines.get(recNum));
		        lines.remove(recNum);
		    }
		    setChanged(true);
		}
	}


	/**
	 * Delete a record from the file
	 *
	 * @param recNums record number to delete
	 */
	public void deleteLines(int[] recNums) {

		if (view) {
			if (baseFile.treeTableNotify == null) {
			    int[] baseRecNums = new int[recNums.length];

			    for (int i = 0; i < baseRecNums.length; i++) {
			        baseRecNums[i] = baseFile.indexOf(lines.get(recNums[i]));
			    }

			    deleteLinesFromView(recNums);
			   	baseFile.deleteLinesFromView(baseRecNums);
			} else {
				AbstractLine l;
			    deleteLinesFromView(recNums);
			    for (int i = 0; i < recNums.length; i++) {
			    	l = (AbstractLine) lines.get(recNums[i]);
			        baseFile.deleteOneLine(l);
			    }
			}
		} else {
			deleteLinesFromView(recNums);
		}
	}


	/**
	 * delete lines from view
	 * @param recNums reords to be deleted
	 */
	private final void deleteLinesFromView(int[] recNums) {

		if (recNums != null && recNums.length > 0) {
			int start, end, i;
			Arrays.sort(recNums);
			setChanged(true);

			start = recNums[recNums.length - 1];
			end   = recNums[recNums.length - 1];
//			System.out.print("FileView Deleteing from view " + isView() + " " + recNums.length + " >> ");
			for (i = recNums.length - 2; i >= 0 && recNums[i] >= 0; i--) {
				if (start - 1 == recNums[i]) {
					start -= 1;
				} else if (start != recNums[i]) {
					fireTableRowsDeleted(start, end);
					//System.out.println(" ++ delete range " + start + " " + end);
					start = recNums[i];
					end   = recNums[i];
				}
//				System.out.print(" " + recNums[i]);
			}
			if (end >= 0) {
				//System.out.println(" << delete range " + start + " " + end);
				fireTableRowsDeleted(start, end);
			}

//			System.out.println(" << starting delete  " + start + " " + end);
			int last = -1331;
			for (i = recNums.length - 1; i >= 0; i--) {
				//System.out.println("Deleting: " + i + " ~ " + recNums[i]);
				if (recNums[i] >= 0 && recNums[i] < lines.size() && recNums[i] != last) {
					if (nodes != null) {
						deleteNode(lines.get(recNums[i]));
					}
					lines.remove(recNums[i]);
					last = recNums[i];
				}
			}
//			System.out.println(" << end delete  " + start + " " + end);
		}
	}

//	public final void deleteLines(AbstractLine[] linesTodelete) {
//		if (view) {
//			this.baseFile.deleteLines(linesTodelete);
//		} else {
//			for (int i = 0; i <linesTodelete.length; i++) {
//				deleteLine(linesTodelete[i]);
//			}
//		}
//	}

	/**
	 * Delete a single line from the table
	 * @param line to delete
	 */
	public final void deleteLine(AbstractLine line) {

		if (view) {
			baseFile.deleteOneLine(line);
		} else {
			deleteOneLine(line);
		}
	}

	private final void deleteOneLine(AbstractLine line) {

		deleteNode(line);
		if (line.getTreeDetails().getParentLine() != null) {
			line.getTreeDetails().getParentLine().getTreeDetails().removeChild(line);
		} else {
			int num = lines.indexOf(line);
			if (num >= 0) {
				try {
					fireTableRowsDeleted(num, num);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//System.out.println(" >> delete " + recNum + " " + recNum);
	        	lines.remove(num);
			}
		}

		setChanged(true);
	}

	private void deleteNode(AbstractLine line) {

		AbstractLineNode node= getTreeNode(line) ;

		if (node != null) {
			if (treeTableNotify != null) {
				treeTableNotify.fireTreeNodesRemoved(node);
			}
			node.removeFromParent();
		}
	}
	
	public void removeLineFromView(int lineNumber) {
		if (view) {
			lines.remove(lineNumber);
		} else {
			deleteLine(lineNumber);
		}
	}

	public final AbstractLineNode getTreeNode(AbstractLine line) {
		AbstractLineNode ret = null;
		if (nodes != null) {
			ret = nodes.get(line);
		}

		return ret;
	}

	public final void setNodeForLine(AbstractLine line, AbstractLineNode node) {
		if (nodes == null) {
			defineNodes();
		}
		nodes.put(line, node);
	}

	private synchronized void defineNodes() {
		if (nodes == null) {
			nodes = new WeakHashMap<AbstractLine, AbstractLineNode>(lines.size());
		}
	}
//	/**
//	 * Do the actual deletion of the lines
//	 * @param start
//	 * @param end
//	 */
//	private void deleteLinesFromView_doDelete(int start, int end) {
//		int min = Math.max(0, start);
//
//		for (int i = end;  i >= min; i--) {
//			lines.remove(start);
//		}
//		fireTableRowsDeleted(start, end);
//	}
//

	/**
	 * Repeat a line in the file
	 * @param lineNumber line number to be repeated
	 */
	public final AbstractLine repeatLine(int lineNumber) {

	    if (isView() || this.treeTableNotify == null) {
	        return stdRepeatLine(lineNumber);
	    }
	    return repeatLine(lines.get(lineNumber));
	}


	public final AbstractLine repeatLine(AbstractLine line) {
		 AbstractLine newLine = null;
		 int idx = lines.indexOf(line);
		 if (line == null) {
			 Common.logMsg("No Physical Line, so can not repeat the line", null);
		 } else if (isView() || (this.treeTableNotify == null && idx >= 0)) {
			if (idx >= 0) {
				newLine = stdRepeatLine(idx);
			} else {
				newLine = baseFile.repeatLine(line);
			}
		 } else {
			switch (checkInsertOk(line.getTreeDetails().getParentLine(), line.getTreeDetails().getChildDefinitionInParent())) {
			case (CAN_INSERT):
				AbstractLine parent =line.getTreeDetails().getParentLine();
				int childIdx = line.getTreeDetails().getChildDefinitionInParent().getChildIndex();
				int loc = parent.getTreeDetails().getLines(childIdx).indexOf(line);

				newLine = (AbstractLine) line.getNewDataLine();
				insert(parent, newLine, loc);
			break;
			case (FAILED_2ND_ROOT_NOT_ALLOWED):
				Common.logMsg("You can not Repeat the root Segment !!!", null);
			break;
			case (FAILED_CHILD_ALREADY_EXISTS):
				Common.logMsgRaw(
						LangConversion.convert(
								ONLY_ONE_IS_ALLOWED,
								line.getTreeDetails().getChildDefinitionInParent().getName()),
						null);
			}
		 }

		 return newLine;
	}

 
	public void insert(
			AbstractLine parent,
			AbstractLine newLine,
			int loc) {


		AbstractLineNode node;
		AbstractChildDetails childDef = newLine.getTreeDetails().getChildDefinitionInParent();
		switch (checkInsertOk(parent, childDef)) {
		case (CAN_INSERT):

			if (parent == null) {
				if (loc < 0)	{
					lines.add(newLine);
				} else {
					lines.add(loc, newLine);
				}
				if (treeTableNotify != null) {
					int RecordIdx = newLine.getPreferredLayoutIdx();
					String name = "root";
					if (RecordIdx > 0) {
						name = getLayout().getRecord(RecordIdx).getRecordName();
					}

					//node = new LineNodeChild(name, baseFile, newLine);

					node = ((AbstractLineNode) treeTableNotify.getRoot()).insertNode(loc, name, baseFile, newLine);
					treeTableNotify.fireTreeNodesInserted(node);
				} else {
					fireTableRowsInsertedLocal(loc, loc);
				}
			} else {
				parent.getTreeDetails().addChild(newLine, loc);

				AbstractLineNode pn;
				if (baseFile.treeTableNotify != null
				&&  (pn = getTreeNode(parent)) != null) {
					node = pn.insert(newLine, -1, loc);

					//baseFile.treeTableNotify.fireTreeNodesInserted(node, node.getPath(), null, null);
					//baseFile.treeTableNotify.fireTreeStructureChanged(pn, pn.getPath(), null, null);
					//baseFile.treeTableNotify.fireTreeNodesInserted(node, node.getPath(), null, null);
				}

//				if (baseFile.treeTableNotify != null) {
//					AbstractLineNode pn = (AbstractLineNode) parent.getTreeDetails().getTreeNode();
//
//					node = (AbstractLineNode) newLine.getTreeDetails().getTreeNode();
//
//					baseFile.treeTableNotify.fireTreeNodesInserted(node, node.getPath(), null, null);
//					baseFile.treeTableNotify.fireTreeStructureChanged(pn, pn.getPath(), null, null);
//					//baseFile.treeTableNotify.fireTreeNodesInserted(node, node.getPath(), null, null);
//				}
			}
		break;
		case (FAILED_2ND_ROOT_NOT_ALLOWED):
			Common.logMsg("You can not add a second root segment !!!", null);
		break;
		case (FAILED_CHILD_ALREADY_EXISTS):
			Common.logMsgRaw(
					LangConversion.convert(
							ONLY_ONE_IS_ALLOWED,
							newLine.getTreeDetails().getChildDefinitionInParent().getName()),
					 null);
		}
	}

	private int checkInsertOk(AbstractLine parent, AbstractChildDetails childDef) {
		int ret = CAN_INSERT;

		if (parent == null) {
			if (! allowMultipleRecords) {
				ret= FAILED_2ND_ROOT_NOT_ALLOWED;
			}
		} else if (childDef == null)  {
			ret = FAILED_CHILD_NOT_IN_PARENT;
		} else {
			if (parent.getTreeDetails().getChildCount() <= childDef.getChildIndex()
			|| parent.getTreeDetails().getChildDetails(childDef.getChildIndex()) != childDef) {
				ret = FAILED_CHILD_NOT_IN_PARENT;
			} else 	if ((! childDef.isRepeated())
					&&	parent.getTreeDetails().hasLines(childDef.getChildIndex())) {
				ret = FAILED_CHILD_ALREADY_EXISTS;
			}
		}

		return ret;
	}
	/**
	 * Repeat a line in the file
	 * @param lineNumber line number to be repeated
	 */
	private final AbstractLine stdRepeatLine(int lineNumber) {

	    AbstractLine newLine;
	    if (isView()) {
	        int l = baseFile.indexOf(lines.get(lineNumber));
	        newLine = baseFile.repeatLine(l);
		} else {
	        newLine = lines.get(lineNumber).getNewDataLine();
	        setChanged(true);
	    }
	    if (newLine != null) {
	    	lines.add(lineNumber + 1, newLine);
	    	fireTableRowsInsertedLocal(lineNumber + 1, lineNumber + 1);
	    }
	    return newLine;
	}


	/**
	 * copy records
	 *
	 * @param recNums Index of records to be copied
	 */
	public final void copyLines(int[] recNums) {

		int num = recNums.length;
		int limit = getLineHoldLimit();

		if (num > limit) {
			num = limit;
			Common.logMsgRaw(
					LangConversion.convertMsg(
							"Copy limit of {0} exceeded; only the first {1} lines copied",
							new Object[] {limit, limit}),
					null);
		}

		copySrc = newDataStore((num > 5000) || ((num > 50) && (! (baseFile.lines instanceof ArrayList))), num, 1, null, null);

		IProgressDisplay pd = newProgressDisplay(recNums.length < 50000 || SwingUtilities.isEventDispatchThread(), 
				"Copying " + this.getFileNameNoDirectory(), UtMessages.COPIED, recNums.length, 50000);
		try {
			
			lines.extractLinesRE(copySrc, recNums, pd);
//			for (i = 0; i < num; i++) {
//				copySrc.add(lines.getTempLine(recNums[i]).getNewDataLine());
//				pd.updateProgress(i);
//			}
		} finally {
    		pd.done();
    	}
	
    	doCompress(recNums[0]);
		
		GcManager.doGcIfNeccessary(0.50);
	}



	/**
	 * @param copySrc the copySrc to set
	 */
	public static final void setCopyRecords(IDataStore<AbstractLine> copySrc) {
		FileView.copySrc = copySrc;
	}

	/**
	 * paste records (that have been copied previously)
	 *
	 * @param pos position to paste records
	 */
	public final void pasteLines(final int pos) {
		if (copySrc != null) {
			if (copySrc.size() < 50000) {
				pasteLinesI(pos);
			} else {
				Common.runOptionalyInBackground(
						 new Runnable() {
							 @Override public void run() {
								 pasteLinesI(pos);
							 }
				});
			}
		}
	}

	public final void pasteLinesI(int pos) {
		AbstractLine l = null;
		
		if (pos >=0 && pos < lines.size()) {
			l = getLine(pos);
		}
		pasteLines(pos, l);
	}

	private final  IDataStore<AbstractLine> pasteLines(int pos, AbstractLine line) {

		IDataStore<AbstractLine> ret = copySrc;
		if (copySrc != null && copySrc.size() > 0) {
			if (treeTableNotify == null || pos < 0
			|| getLine(pos).getTreeDetails().getParentLine() == null) {
				ret = pasteTableLines(pos);
			} else  {
				ret = pasteTreeLines(line, false);
			}
		}
		return ret;
	}

	private final IDataStore<AbstractLine> pasteTableLines(int pos) {
		IDataStore<AbstractLine> cpy;

	    if (view) {
	    	int lineNum = Math.max(0, pos);
			AbstractLine line = null;
			int baseRecordNumber = baseFile.getRowCount();
			
			if (lineNum < lines.size()) {
				line = lines.get(lineNum);
				baseRecordNumber = baseFile.indexOf(line);
			} 

			if (pos < 0) {
				cpy = baseFile.pasteLines(baseRecordNumber - 1, null);
			} else {
				cpy = baseFile.pasteLines(baseRecordNumber, line);
			}
	    } else {
			cpy = copySrc;
	    }

	   return doPaste(pos, cpy);
	}


	public final  IDataStore<AbstractLine> pasteTreeLines(LinePosition pos) {
		return pasteTreeLines(pos.line, pos.before);
	}


	private final  IDataStore<AbstractLine> pasteTreeLines(AbstractLine pos, boolean prev) {
		IDataStore<AbstractLine> ret = null;
		if (copySrc != null && copySrc.size() > 0
		&& copySrc.get(0).getLayout() == layout ) {
			copySrc.setLayoutRE(layout);

			ret = pasteTreeLines(pos, copySrc, prev);
			this.setChanged(true);
		}

		return ret;
	}


	//TODO ~~~ implement prev !!!
	//TODO ~~~ implement prev !!!
	//TODO ~~~ implement prev !!!
	private final IDataStore<AbstractLine>  pasteTreeLines(AbstractLine pos, IDataStore<AbstractLine> copySource, boolean prev) {

		AbstractLine parent = null;
		AbstractChildDetails childDef;

		IDataStore<AbstractLine> ret = copySource;
		
		if (copySource.size() > 0) {
			IDataStore<AbstractLine> accepted = DataStoreStd.newStore(copySource.get(0).getLayout(), copySource.size());
	
			int arrayPos = -1;
	
			if (pos != null) {
				parent = pos.getTreeDetails().getParentLine();
				arrayPos = pos.getTreeDetails().getParentIndex()+1;
				if (prev && arrayPos > 0) {
					arrayPos -= 1;
				}
			}
	
			int p = -1;
			if (pos == null || prev) {
				p = 0;
			}
			int idx = lines.indexOf(pos);
			if (idx >= 0 && ! prev) {
				idx += 1;
			}
			for (int i = 0; i < copySource.size(); i++ ) {
				childDef = copySource.get(i).getTreeDetails().getChildDefinitionInParent();
				if (checkInsertOk(pos, childDef) == CAN_INSERT) {
					accepted.add(copySource.get(i));
					insert(pos, copySource.get(i), p++);
					p = inc(p);
				} else if (checkInsertOk(parent, childDef) == CAN_INSERT) {
					if (parent == null) {
						accepted.add(copySource.get(i));
						insert(null, copySource.get(i), idx);
						idx = inc(idx);
					} else {
						int id = -1;
						if (pos.getTreeDetails().getChildDefinitionInParent() == childDef) {
							id = arrayPos++;
						}
						accepted.add(copySource.get(i));
						insert(parent, copySource.get(i), id);
					}
				} else {
					String name = "";
					try {
						name = layout.getRecord(copySource.get(i).getPreferredLayoutIdx()).getRecordName();
					} catch (Exception e) {
					}
					Common.logMsg(AbsSSLogger.ERROR, "Can not paste", name + ":  " + copySource.get(i).getFullLine(), null);
				}
			}

			ret = accepted;
		
		}
		return ret;
	}

	private int inc(int val) {
		if (val >= 0) {
			val +=1;
		}
		return val;
	}



	/**
	 * paste a series of records
	 *
	 * @param pos position to copy the lines
	 * @param newLines new lines to be copied
	 */
	private  IDataStore<AbstractLine> doPaste(int pos,  IDataStore<AbstractLine> newLines) {

	    IDataStore<AbstractLine> ret= newLines;
	    int cpySize = newLines.size();
//		if (lines instanceof ArrayList) {
//	    	((ArrayList)lines).ensureCapacity(lines.size() + cpySize);
//	    }

	    if (isView()) {
	    	lines.addAll(Math.min(pos+1, lines.size()), newLines);
//	        for (i = 0; i < newLines.size(); i++) {
//				lines.add(pos + i + 1, newLines.get(i));
//			}
	    } else if (cpySize > 0){
//	    	int j = 0;
//	    	int[] lineNums = new int[cpySize];
	    	
	    	//TODO uncomment after test cases built !!!	   
	    	//TODO uncomment after test cases built !!!	 
	    	pos = Math.min(pos, lines.size() - 1);
	    	IProgressDisplay pd = newProgressDisplay(SwingUtilities.isEventDispatchThread(), "Pasting", UtMessages.PASTE, newLines.size(), 50000);
	    	try {
	    		lines.addCopyRE(pos+1, newLines, pd);
	    	} finally {
	    		pd.done();
	    	}
	    	
	    	doCompress(pos);
//	    	System.out.println();
//	    	System.out.println(newLines.getSizeDisplay());
//	    	System.out.println();
//	    	System.out.println(lines.getSizeDisplay());
	    	
	    	
//	    	//TODO comment after test cases built !!!	  
//	    	//TODO comment after test cases built !!!	  
//	    	AbstractLine tempLine;
//	    	
//	    	pos = Math.min(pos, lines.size() - 1);
//	        for (i = 0; i < cpySize; i++) {
////	        	if (i % 250000 == 0) {
////	        		System.out.println();
////	        		System.out.println("------------------ Summary at " + (i / 1000) + " thousand ----------------" );
////	        		System.out.println(" File:");
////	        		System.out.println(lines.getSizeDisplay());
////	        		System.out.println(" CopySource:");
////	        		System.out.println(newLines.getSizeDisplay());
////	        		System.out.println();
////	        	}
////      	
////	        	lineNums[i] = pos + i + 1;
//	        	
//	        	tempLine = newLines.getNewLine(i);
//	        	tempLine.setLayout(getLayout());
//				lines.add(pos + i + 1, tempLine);
////	        	ret.add(lines.addCopy(pos + i + 1, newLines.getTempLine(i)));
//			}
	    	ret = new DataStoreRange(lines, pos + 1, cpySize); 
	    }

        setChanged(true);
        fireTableRowsInsertedLocal(pos + 1, pos + cpySize);
        GcManager.doGcIfNeccessary(0.50);

        return ret;
	}


	/**
	 * add a new record
	 *
	 * @param pos suggested position to place the new record
	 *
	 * @return actual position used
	 */
	public final int newLine(int pos, int adj) {

	    if (view) {
	        int basePos = baseFile.newLine(getBasePosition(pos), adj);

	        pos = addLineInternal(pos + adj, baseFile.getLine(basePos));
	    } else {
			if (ioProvider == null) {
				ioProvider = LineIOProvider.getInstance();
			}
	        pos = addLineInternal(pos + adj, ioProvider.getLineProvider(layout).getLine(layout));
	        //changed = true;
	    }

	    fireTableRowsInsertedLocal(pos, pos);

		return pos;
	}

	/**
	 * Adds a line (& fires Table row notification)
	 * This method was added to test large views
	 * 
	 * @param pos position to insert
	 * @param line line to insert
	 * 
	 * @return line that was added.
	 */
	public final int addLine(int pos, AbstractLine line) {
		// return addLines(pos+1, -1, line);
		int ret;
		if (view) {
			int p = baseFile.getRowCount();
			if (pos >= 0 && pos < lines.size()) {
				p = baseFile.indexOf(lines.get(pos));
				if (pos < 0) {
					p = baseFile.getRowCount();
				}
			}
			
			p = baseFile.addLine(p, line);
			line = baseFile.getLine(p);
		}
		ret = addLineInternal(pos - 1, line);
		fireTableRowsInsertedLocal(ret, ret);
		return ret;
	}
	
	/**
	 * add a new record
	 *
	 * @param pos suggested position to place the new record
	 *
	 * @return actual position used
	 */
	public final int addLines(int pos, int adj, AbstractLine[] linesToAdd) {


		int initialPos = pos + adj;
		pos += adj;
		if (view) {
			linesToAdd = linesToAdd.clone();
			baseFile.addLines(getBasePosition(pos - adj), adj, linesToAdd);
			for (int i = 0; i < linesToAdd.length; i++) {
				pos = addLineInternal(pos, linesToAdd[i]);
				if (i == 0) {
					initialPos = pos;
				}
			}
		} else {

			for (int i = 0; i < linesToAdd.length; i++) {
				pos = addLineInternal(pos, linesToAdd[i]);
				linesToAdd[i] = lines.get(pos);
				if (i == 0) {
					initialPos = pos;
				}
			}
		}
		//System.out.println("Row Count 2: " + getRowCount());
		fireTableRowsInsertedLocal(initialPos, initialPos
				+ linesToAdd.length - 1);
		//System.out.println("Row Count 3: " + getRowCount());
		return pos;

	}

	private int getBasePosition(int pos) {
		int baseRecordNumber = baseFile.getRowCount();
        if (lines.size() > 0 && pos < lines.size()) {
            baseRecordNumber = baseFile.indexOf(lines.get(pos));
        }
		return baseRecordNumber;
	}

	public final AbstractLine[] createLines(int count) {
		AbstractLine[] ret = new AbstractLine[count];

		if (baseFile.ioProvider == null) {
			baseFile.ioProvider = LineIOProvider.getInstance();
		}

		for (int i = 0; i < count; i++) {
			ret[i] = baseFile.ioProvider.getLineProvider(layout).getLine(layout);
		}

		return ret;
	}
//
//	/**
//	 * Add a line
//	 *
//	 * @param pos position to add a record
//	 * @param line line to insert
//	 *
//	 * @return position a line was added
//	 */
//	public final int addLine(int pos, AbstractLine line) {
//		return addLineInternal(pos - 1, line);
//	}

	/**
	 * Add a line
	 *
	 * @param pos position to add a record
	 * @param line line to insert
	 *
	 * @return position a line was added
	 */
	private int addLineInternal(int pos, AbstractLine line) {

        if (lines.size() == 0 || pos >= lines.size() - 1) {
            pos = lines.size();
            lines.add(line);
        } else {
            pos += 1;
            lines.add(pos, line);
        }

        return pos;
	}

	/**
	 * Replace the all occurences of a Value <i>searchFor</i>
	 * with a new value <i>replaceWith</i> in the view
	 *
	 * @param searchFor string to search for
	 * @param replaceWith replacement text
	 * @param pos starting position
	 * @param ignoreCase ignore case of the string
	 * @param anyWhereInTheField wether to search the whole record
	 * @param normalSearch wether to use equals or not equals / contains or does not contain
	 *
	 * @throws RecordException any error that occurs
	 */
	public final void replaceAll(
	        String searchFor,
			String replaceWith,
			FilePosition pos,
			boolean ignoreCase,
			//boolean anyWhereInTheField,
			//boolean normalSearch
			int operator) throws RecordException {

		int num = replaceAllImplementation(
						searchFor, replaceWith, pos,
						ignoreCase, operator);
	    
	    JOptionPane.showMessageDialog(frame, UtMessages.REPLACE_ALL.get(num, searchFor));
	}

	/**
	 * @param searchFor
	 * @param replaceWith
	 * @param pos
	 * @param ignoreCase
	 * @param operator
	 * @return
	 * @throws RecordException
	 */
	public int replaceAllImplementation(String searchFor, String replaceWith,
			FilePosition pos, boolean ignoreCase, int operator)
			throws RecordException {
		int num = 0;
	    pos.col = 0;
	    pos.row = 0;

	    while (replace(searchFor, replaceWith, pos,
	            	ignoreCase, operator, false)) {
//	        System.out.print(" > " + pos.row + " " + pos.col);
	    	num += 1;
	    }
		return num;
	}

	/**
	 * Replace the next occurence of a Value <i>searchFor</i>
	 * with a new value <i>replaceWith</i>
	 *
	 * @param searchFor string to search for
	 * @param replaceWith replacement text
	 * @param pos starting position
	 * @param ignoreCase ignore case of the string
	 * @param operator Operator to use in the search
	 *
	 * @return wether the string was found
	 * @throws RecordException any error that occurs
	 */
	public final boolean replace(
		String searchFor,
		String replaceWith,
		FilePosition pos,
		boolean ignoreCase,
		int operator,
		boolean eofCheck) throws RecordException {

	    find(searchFor,
	         pos,
	         ignoreCase,
	         //anyWhereInTheField, normalSearch
	         operator,
	         eofCheck);

	    if (pos.found) {
			AbstractLine updateLine;
			String s;
			int col = getRealColumn(pos.recordId, pos.currentFieldNumber);

			if (operator == Compare.OP_CONTAINS) {
				s =  lines.get(pos.row).getField(pos.recordId, col).toString();
			    StringBuffer b = new StringBuffer(s);

			    b.replace(pos.col, pos.col + searchFor.length(), replaceWith);
			    s = b.toString();
			} else {
				s = replaceWith;
			}

			updateLine = pos.currentLine;

			if (updateLine == null) {
				updateLine = lines.get(pos.row);
			}
			updateLine.setField(pos.recordId, col, s);
			setChanged(true);

			pos.adjustPosition(replaceWith.length(), operator);
	    }
	    return pos.found && pos.row >= 0 && pos.row < lines.size();
	}


	/**
	 * Find a requested Value in the file
	 *
	 * @param searchFor string to search for
	 * @param pos starting position
	 * @param ignoreCase ignore case of the string
	 * @param operator Operator to use in the search
	 *
	 */
	public final void find(
		String searchFor,
		FilePosition pos,
		boolean ignoreCase,
		int operator,
		boolean eofCheck) {

		int size = lines.size();
		if (eofCheck 
		&& (size < 100000 || (lines instanceof DataStoreStd && lines.size() < 500000))) {
			FileSearch fs = FileSearch	.newFileSearch(this, searchFor, pos, ignoreCase, operator, false);
			fs.doSearch();
			
			if (! pos.found) {
				FilePosition startPos = pos.clone();
				startPos.gotoStart();
				FileSearch	.newFileSearch(this, searchFor, startPos, ignoreCase, operator, false)
							.doSearch();
				if (startPos.found) {
					fs.doEofSearch();
				} else { 
					ReOptionDialog.showMessageDialog(frame, "{0} was not found in the view", searchFor);
				}
			}
		} else {
			FileSearch	.newFileSearch(this, searchFor, pos, ignoreCase, operator, eofCheck)
						.doSearch();
		}
//		String icSearchFor = searchFor;
//		boolean anyWhereInTheField = (operator == Compare.OP_CONTAINS)
//								  || (operator == Compare.OP_DOESNT_CONTAIN);
//
//		if (ignoreCase) {
//			icSearchFor = searchFor.toUpperCase();
//		}
//		pos.found = false;
//
//		PositionIncrement inc = PositionIncrement.newIncrement(pos, lines, eofCheck);
//	    if (anyWhereInTheField) {
//	        while (inc.isValidPosition()
//	                && ! contains(ignoreCase, inc.pos, icSearchFor, (operator == Compare.OP_CONTAINS))) {
//	            inc.nextPosition();
//	        }
//	    } else {
//			BigDecimal testNumber = Compare.getNumericValue(operator, searchFor);
//	        while (inc.isValidPosition()
//	                && ! cmp(ignoreCase, inc.pos, icSearchFor, testNumber, operator)) {
//	            inc.nextPosition();
//	        }
//	    }
	}



	/**
	 * Return a requested line
	 * 
	 * @param lineNum Line Number
	 *
	 * @return requested line
	 */
	public final AbstractLine getLine(final int lineNum) {
		return lines.get(lineNum);
	}

	public final void setLine(final int lineNum, AbstractLine l) {
		boolean changed = lineNum < 0 || lineNum >= lines.size() || lines.get(lineNum) != l;
						
		lines.set(lineNum, l);
		setChanged(changed);
		tblFireRowUpdated(lineNum, lines.get(lineNum));
	}

	@Override
	public final AbstractLine getTempLine(final int lineNum) {
		return lines.getTempLineRE(lineNum);
	}

	/**
	 * @return Returns the browse status.
	 */
	public final boolean isBrowse() {
		return browse;
	}


	/**
	 * @return Returns the changed.
	 */
	public final boolean isChanged() {
	    if (this.isView()) {
	        return baseFile.isChanged();
	    }
		return changeStatus != NO_CHANGE;
	}


    /**
     * @param fileChanged The changed to set.
     */
    @Override
	public void setChanged(boolean fileChanged) {

        if (this.isView()) {
            baseFile.setChanged(fileChanged);
        }

        if (fileChanged) {
        	changeStatus = CHANGED;
        } else {
        	changeStatus = NO_CHANGE;
        }

    }

	public boolean isSaveAvailable() {
		return saveAvailable;
	}

	public boolean isTreeViewAvailable() {
		return lines instanceof DataStoreStd
			|| (lines != null && lines.size() < Common.OPTIONS.filterLimit.get());
	}

	/**
	 * @param aFrame The frame to set.
	 */
	public final void setFrame(JFrame aFrame) {
		this.frame = aFrame;
	}


	/**
	 * @return Returns the binaryFile.
	 */
	public boolean isBinaryFile() {
		return layout.isBinary();
	}


	/**
	 * Get File Description
	 * @return File Description
	 */
	public final AbstractLayoutDetails getLayout() {
        return layout;
    }


	/**
	 * @param layout the layout to set
	 */
	public final void setLayout(AbstractLayoutDetails layout) {
		this.layout = layout;
		checkIfTextFile();
		lines.setLayoutRE(layout);

		maxNumColumns = -1;
		columnMapping = NULL_MAPPING;
	}

	/**
	 * Writes a file back to disk
	 *
	 * @throws IOException any error that occurs
	 * @throws RecordException any local error
	 */
	public void writeFile() throws IOException, RecordException {

	    if (view) {
	        baseFile.writeFile();
	    } else if (lines == null) {
	    	Common.logMsgRaw(INTERNAL_ERROR_LINES_CLEARED_SAVE_IS_NOT_POSSIBLE, null);
//	    	Common.logMsgRaw(INTERNAL_ERROR_LINES_CLEARED_SAVE_IS_NOT_POSSIBLE, null);
//	    	Common.logMsgRaw(INTERNAL_ERROR_LINES_CLEARED_SAVE_IS_NOT_POSSIBLE, null);
	    } else {
	    	changeStatus = BEING_SAVED;
	        saveFile(lines, fileName, toSave,
	        		new PropertyChangeListener() {

						@Override
						public void propertyChange(PropertyChangeEvent arg0) {
							if (changeStatus == BEING_SAVED) {
								toSave = false;
					            changeStatus = NO_CHANGE;
					            dropCompletedWriters();
							}
						}
	        		}
	        );
	    }
	}


	/**
	 * Writes a file back to disk
	 *
	 * @param outFile output file
	 *
	 * @throws IOException any error that occurs
	 * @throws RecordException any local error
	 */
	public void writeFile(String outFile)
	throws IOException, RecordException {

	    writeLinesToFile(outFile, lines);
	    if ("".equals(this.fileName) && ! isView()) {
	    	this.fileName = outFile;
	    	this.setChanged(false);
	    }
	}


	/**
	 * Writes specified lines to a file
	 *
	 * @param outFile output file name
	 * @param lines2write lines to be written
	 *
	 * @throws IOException any error that occurs
	 * @throws RecordException any local error
	 */
	public void writeFile(String outFile, int[] lines2write)
	throws IOException, RecordException {
	    writeLinesToFile(outFile, getLines(lines2write));
	}


	/**
	 * Writes a file back to disk
	 *
	 * @param outFile output file
	 * @param lines2write lines to write
	 *
	 * @throws IOException any error that occurs
	 * @throws RecordException any local error
	 */
	public void writeLinesToFile(String outFile, List<AbstractLine> lines2write)
	throws IOException, RecordException {
	    boolean sameName = outFile.equals(fileName);
	    PropertyChangeListener chg = null;

	    if ((! sameName) && "\\".equals(Common.FILE_SEPERATOR)) {
	        sameName = outFile.equalsIgnoreCase(fileName);
	    }

	    toSave &= sameName;

	    if (sameName) {
	    	changeStatus = BEING_SAVED;
	    	chg = new PropertyChangeListener() {

				@Override
				public void propertyChange(PropertyChangeEvent arg0) {
					if (changeStatus == BEING_SAVED) {
						toSave = false;
						changeStatus = NO_CHANGE;
					}
				}
			};
	    }

	    saveFile(lines2write, outFile, toSave, chg);
	}


	/**
	 * Saves Arraylist to a file
	 *
	 * @param pLines lines to save
	 * @param pFileName output file name
	 * @param backup wether to backup the file
	 *
	 * @return wether the file was saved
	 *
	 * @throws IOException any error that occurs
	 * @throws RecordException any local error
	 */
	private void saveFile(List<AbstractLine> pLines,
	        String  pFileName,
	        boolean backup,
	        PropertyChangeListener chgListner)
	throws IOException, RecordException {

	    boolean isGZip = checkIfGZip(pFileName);
	    AbstractLineWriter writer = ioProvider.getLineWriter(layout.getFileStructure(), layout.getFontName());
	    FileWriter fileWriter = new FileWriter(pLines, layout, pFileName, backup, isGZip, writer);

	    dropCompletedWriters();

	    if (pLines.size() < 50000) {
	    	fileWriter.doWrite();
	    	if (chgListner != null) {
	    		chgListner.propertyChange(null);
	    	}
	    } else {
	    	try {
	    		net.sf.RecordEditor.re.file.FileWriterBackground fw = new net.sf.RecordEditor.re.file.FileWriterBackground(fileWriter);

				if (chgListner != null) {
					fw.addPropertyChangeListener(chgListner);
				}
				writers.add(new WeakReference<FileWriter>(fileWriter));
				fw.addPropertyChangeListener(saveDoneListner);
				fw.execute();
			} catch (NoClassDefFoundError e) {
				fileWriter.doWrite();
			}
	    }
	}

	private void dropCompletedWriters() {
	    for (int i = writers.size() - 1; i >= 0; i--) {
	    	if (writers.get(i).get() == null || writers.get(i).get().isDone()) {
	    		writers.remove(i);
	    	}
	    }
	}

//	/**
//	 * Writes the lines to using supplied writer
//	 * @param writer writer to write lines to
//	 * @param pLines linesto be written
//	 *
//	 * @throws IOException any error that occurs
//	 */
//	private void writeToFile(AbstractLineWriter writer, List<AbstractLine> pLines)
//	throws IOException {
//	    int i;
//
//	    writer.setLayout(layout);
//
//	    if (pLines instanceof DataStore) {
//	    	DataStore ds = (DataStore) pLines;
//		    for (i = 0; i < pLines.size(); i++) {
//		        writer.write(ds.getTempLine(i));
//		    }
//	    } else {
//		    for (i = 0; i < pLines.size(); i++) {
//		        writer.write(pLines.get(i));
//		    }
//	    }
//
//	    writer.close();
//	}

	/**
	 * Get the maximum number of lines that can be held
	 * @return maximum number of lines that can be held
	 */
	private int getLineHoldLimit() {
        int limit = Integer.MAX_VALUE;
        if (! (lines instanceof DataStoreStd)) {
        	limit = Common.OPTIONS.filterLimit.get();
        }
        return limit;
	}

	/**
	 * Create filtered view of a file
	 *
	 * @param filter filter to apply to a file
	 *
	 * @return filtered file view
	 */
	public FileView getFilteredView(FilterDetails filter) {

		if (filter.isNormalFilter() != FilterDetails.FT_NORMAL) {
			return getFilteredView(filter, filter.getGroupHeader());
		}
		ViewDataStore selected = new ViewDataStore(layout, lines instanceof DataStoreStd, frame, this.baseFile.lines);
        AbstractLine line;
//        FilePosition pos = new FilePosition(0, 0, 0, 0, true, this.getRowCount());
        boolean filterNonPrefered = getFilterNonPref(filter);

        FilterFieldBaseList list = filter.getFilterFieldListMdl();
        IDataStoreIterator it = getIterator();


    	IProgressDisplay pd = newProgressDisplay(
    			lines.size() < 50000 || SwingUtilities.isEventDispatchThread(), "Filtering " + this.getFileNameNoDirectory(), 
    			UtMessages.FILTER, lines.size(), 50000);
    	try {
        	int count = 0, sel = 0;
	        while (it.hasNext() && selected.continueProcessing()) {
	        	line = it.nextTempRE();
	    	   	if (check(filterNonPrefered, filter, list, line, false) >= 0) {
	    	   		selected.add(it.currentLineRE());
	    	   		sel+=1;
	    	   	}
	    	   	if (! pd.updateProgress(count++, sel)) {
	    	   		break;
	    	   	}
//	    	   	if (count % 250000 == 0) {
//    	   			System.out.println();
//	    	   		System.out.println(lines.getSizeDisplay());
//	    	   	}
	        }
	        
//	    	System.out.println();
//	    	System.out.println(lines.getSizeDisplay());

        } finally {
    		pd.done();
    	}
       
        
        doCompress(0);
//        while (it.hasNext() && selected.continueProcessing()) {
//    	   	line = it.next();
//    	   	recordType = line.getPreferredLayoutIdxAlt();
//    	   	if (filterNonPrefered && recordType == Constants.NULL_INTEGER) {
//            	for (k = 0; k < layout.getRecordCount(); k++) {
//            		if (filter.isInclude(k) && layout.getRecord(k).getOption(Options.OPT_SELECTION_PRESENT) == Options.YES) {
//            			if (getFilteredView_MatchesFilter(i, k, pos, list, false, line)) {
//            				selected.add(line);
//            				break;
//            			}
//            		}
//            	}
//            } else if (filter.isInclude(recordType)) {
//                if (getFilteredView_MatchesFilter(i, recordType, pos, list, true, line)) {
//                	selected.add(line);
//                }
//            }
//            i += 1;
//        }

        if (selected.getSelectedLines().size() > 0) {
            return new FileView(
            		selected.getSelectedLines(), baseFile,
            		new FieldMapping(filter.getFieldMap(), getFieldCounts()));
        }
        return null;
	}

	/**
	 * Create filtered view of a file
	 *
	 * @param filter filter to apply to a file
	 *
	 * @return filtered file view
	 */
	public FileView getFilteredView(FilterDetails filter, int groupHeader) {

		ViewDataStore selected = new ViewDataStore(layout, lines instanceof DataStoreStd, frame, this.baseFile.lines);
        AbstractLine line;
        ArrayList<AbstractLine> groupLines = new ArrayList<AbstractLine>();

        boolean filterNonPrefered = getFilterNonPref(filter);

        FilterFieldBaseList list = filter.getFilterFieldListMdl();
        IDataStoreIterator it = getIterator();
        RecordSel groupSelection = list.getGroupRecordSelection();
        int count = 0;
        
        while (it.hasNext()) {
        	line = it.next();
        	count += 1;
        	if (line.getPreferredLayoutIdxAlt() == groupHeader) {
        		if (filter.isInGroup(line.getPreferredLayoutIdxAlt())) {
        			groupLines.add(line);
        		}
        		break;
        	}
        }
        


    	IProgressDisplay pd = newProgressDisplay(lines.size() < 50000 || SwingUtilities.isEventDispatchThread(), "Filtering " + this.getFileNameNoDirectory(), UtMessages.FILTER, lines.size(), 50000);
       	try {
	        while (it.hasNext() && selected.continueProcessing()) {
	        	line = it.nextTempRE();
	        	if (line.getPreferredLayoutIdxAlt() == groupHeader) {
	        		processGroup(selected, filterNonPrefered, filter, groupSelection, groupLines);
	        	}
	        	if (filter.isInGroup(line.getPreferredLayoutIdxAlt())) {
	        		groupLines.add(it.currentLineRE());
	        	}
	        	
	    	   	if (! pd.updateProgress(count++, selected.size())) {
	    	   		break;
	    	   	}
	        }
       	} finally {
    		pd.done();
    	}
        
        
        processGroup(selected, filterNonPrefered, filter, groupSelection, groupLines);
        doCompress(0);
        
        if (selected.getSelectedLines().size() > 0) {
            return new FileView(
            		selected.getSelectedLines(), baseFile,
            		new FieldMapping(filter.getFieldMap(), getFieldCounts()));
        }
        return null;
	}

	private void processGroup(
			ViewDataStore selected,
			boolean filterNonPrefered,
			FilterDetails filter,
			RecordSel groupSelection,
			ArrayList<AbstractLine> groupLines) {
   		FilterFieldBaseList list = filter.getFilterFieldListMdl();
   		int recId;



	    if (groupSelection.isSelected(groupLines)) {
	    	AbstractLine l;

	    	for (int i = 0; i < groupLines.size(); i++) {
	    		l = groupLines.get(i);
	    		recId = l.getPreferredLayoutIdx();
	    		if (recId < 0) {
	    			recId = check(filterNonPrefered, filter, list, l, true) ;
	    		}

	    		if (filter.isInclude(recId)) {
	    			selected.add(l);
	    		}
	    	}
	    }

		groupLines.clear();
	}

	private int[] getFieldCounts() {
   		int[] rows = new int[layout.getRecordCount()];

	    for (int i = 0; i < layout.getRecordCount(); i++) {
	    	rows[i] = getLayoutColumnCount(i);
	    }

	    return rows;
	}

	private boolean getFilterNonPref(FilterDetails filter) {
        boolean filterNonPrefered = false;

        if (layout.getRecordCount() > 1) {
        	for (int k = 0; (!filterNonPrefered) && k < layout.getRecordCount(); k++) {
        		filterNonPrefered = filter.isInclude(k)
        						 && layout.getRecord(k).getOption(Options.OPT_SELECTION_PRESENT) == Options.NO;
        	}
        }
        return filterNonPrefered;
	}

	private IDataStoreIterator getIterator() {
		IDataStoreIterator it;

		if (layout.hasChildren()) {
			it = new TreeIteratorForward(lines, null);
	    } else {
	    	it = new DataStoreIterator(lines);
	    }
	    return it;
	}

	private int check(boolean filterNonPrefered, FilterDetails filter, FilterFieldBaseList list,
			AbstractLine line, boolean allRecs) {
		int ret = Integer.MIN_VALUE;
	   	int recordType = line.getPreferredLayoutIdxAlt();

	   	if (filterNonPrefered && recordType == Integer.MIN_VALUE) {
        	for (int k = 0; k < layout.getRecordCount(); k++) {
        		if ((allRecs || filter.isInclude(k))
        		&& layout.getRecord(k).getOption(Options.OPT_SELECTION_PRESENT) == Options.NO) {
        			if (getFilteredView_MatchesFilter(k, list, line)) {
        				ret = k;
        				break;
        			}
        		}
        	}
        } else if ((allRecs ||filter.isInclude(recordType)) && getFilteredView_MatchesFilter(recordType, list, line)) {
            ret = recordType;
        } else {
        	ret = -1 * recordType - 1;
        }
	   	return ret;
	}


	private boolean getFilteredView_MatchesFilter(int recordType,
			FilterFieldBaseList list, AbstractLine line) {
		return list.getRecordSelection(recordType).isSelected(line);
	}


	/**
	 * Get a view based on the current file
	 * @return duplicate view
	 */
	public FileView getView() {

        return new FileView(lines.newDataStoreRE(), baseFile, columnMapping);
	}


	/**
	 * Create a view of selected rows
	 * @param rows rows to add to the filtered view
	 * @return filtered view
	 */
	public FileView getView(int[] rows) {

	    if (rows == null || rows.length == 0) {
	        return null;
	    }

        return new FileView(lines.newDataStoreRE(rows), baseFile, columnMapping);
	}



	/**
	 * Create a view of selected rows
	 * @param rows rows to add to the filtered view
	 * @return filtered view
	 */
	public final FileView getViewOfErrorRecords() {


	    DataStoreStd<AbstractLine> selectedLines = DataStoreStd.newStore(layout);
	    TreeIteratorForward it = new TreeIteratorForward(lines, null);
	    AbstractLine line;
	    while (it.hasNext()) {
	    	line = it.next();
	    	if (line.isError()) {
	    		selectedLines.add(line);
	    	}
	    }

	    if (selectedLines.size() == 0) {
	    	return null;
	    }
        return new FileView(selectedLines, baseFile, columnMapping);
	}



	/**
	 * Create a view of selected rows
	 * @param rows rows to add to the filtered view
	 * @return filtered view
	 */
	public FileView getView(List<AbstractLine> list) {

	    if (list == null || list.size() == 0) {
	        return null;
	    }
	    
	    IDataStore<AbstractLine> ds; // = lines.newDataStore();
		if (lines instanceof DataStoreLarge)  {
			ds = new DataStoreLargeView(lines);
			ds.addAll(list);
		} else {
			ds = DataStoreStd.newStore(layout, list);
		}

        return new FileView(
        		ds,
        		baseFile,
        		columnMapping);
	}

	/**
	 * @see net.sf.RecordEditor.utils.ColumnMappingInterface#getRealColumn(int, int)
	 */
	public int getRealColumn(int layoutIndex, int inColumn) {
	    int ret = columnMapping.getRealColumn(layoutIndex, inColumn);

	    ret = layout.getAdjFieldNumber(layoutIndex, ret);

	    return ret;
	}


	/**
	 * @return wether the file has been read
	 */
	public boolean isFileRead() {
		return fileRead;
	}


	/**
	 * @return any error messages
	 */
	public String getMsg() {
		return msg;
	}


	/**
	 * get the index of a line
	 *
	 * @param line line to get the index of
	 *
	 * @return line-number of a line
	 */
	public int indexOf(Object line) {
        return lines.indexOf(line);
    }


    /**
     * @see javax.swing.event.TableModelListner#tableChanged
     */
    public void tableChanged(TableModelEvent event) {

    	if (lines == null) {
    		baseFile.removeTableModelListener(this);
    		return;
    	}
        if (view) {
            int lastRow  = event.getLastRow();
            int firstRow =  Math.max(0, event.getFirstRow());

			switch (event.getType()) {
				case (TableModelEvent.INSERT):
				    //this.fireTableRowsInserted(firstRecord, lastRow);
				break;
				case (TableModelEvent.DELETE):
					if (lastRow - firstRow + 1 > 0) {
						int[] recNums = new int[lastRow - firstRow + 1];
						//System.out.println("File View Delete: ");

						for (int i = firstRow; i <= lastRow; i++) {
							recNums[i - firstRow] =  lines.indexOf(baseFile.getLine(i));
							//System.out.println(">" + i + " " + recNums[i - firstRow]);
						}

						deleteLinesFromView(recNums);
					}
				break;
				default:
					if (layout != baseFile.getLayout() || event.getFirstRow() < 0) {
						setLayout(baseFile.getLayout());
						this.fireTableStructureChanged();
					} else {
						this.fireTableDataChanged();
					}
			}
			
			if (lines instanceof IDataStoreView) {
				((IDataStoreView) lines).tableChangedUpdateRE(event);
			}
        }
    }


    /**
     * Get the file name
     *
     * @return filename
     */
    public String getFileName() {
    	if (isView()) {
    		return baseFile.getFileName();
    	}
        return fileName;
    }


    /**
     * Get the file name
     *
     * @return filename
     */
    public String getFileNameNoDirectory() {
    	if (isView()) {
    		return baseFile.getFileNameNoDirectory();
    	}
    	if (altName != null) {
    		return altName;
    	}
        return Common.stripDirectory(fileName);
    }


    /**
     * Get the base file
     *
     * @return the base or parent file representation
     */
    public FileView getBaseFile() {
        return baseFile;
    }


    /**
     * wether this file is a view or a full file
     *
     * @return wether it is a file view or the full file
     */
    public boolean isView() {
        return view;
    }




	/**
	 * check if file is a GZip file
	 *
	 * @param fileName2test filename to check
	 *
	 * @return is wether it is a GZip file
	 */
	private boolean checkIfGZip(String fileName2test) {
		return fileName2test.toUpperCase().endsWith(".GZ");
	}


    /**
     * @return Returns the ioProvider.
     */
    public AbstractLineIOProvider getIoProvider() {
        return ioProvider;
    }


    /**
     * Sorts the file into the requested sequence
     * @param compare compare to use in the sort
     */
    public void sort(LineCompare compare) {
        lines.sortRE(compare);

        sortNotify();
        
        doCompress(0);
    }

    /**
     * Sorts the file into the requested sequence
     * @param compare compare to use in the sort
     */
    public void sort(int[] rows, LineCompare compare) {
    	lines.sortRE(rows, compare);
    	sortNotify();
    	
    	if (rows.length > 20000) {
    		doCompress(rows[rows.length - 1]);
    	}
    }
    
    public void oldSort(int[] rows, LineCompare compare) {
    	int i;
       	AbstractLine[] rowsToSort = new AbstractLine[rows.length];

    	for (i = 0; i < rows.length; i++) {
    		rowsToSort[i] = lines.get(rows[i]);
    	}

        Arrays.sort(rowsToSort, compare);

        //TODO    update DataStore
        //TODO    update DataStore
    	for (i = 0; i < rows.length; i++) {
    		lines.set(rows[i], rowsToSort[i]);
    	}

        sortNotify();
    }


    private void sortNotify() {

    	changeStatus = CHANGED;
    	if (this.treeTableNotify == null) {
    		this.fireTableDataChanged();
    	} else if (lines.size() > 0) {
    		Object rootObject = treeTableNotify.getRoot();

    		if (rootObject instanceof AbstractLineNode) {
				AbstractLineNode node;
				int[] childIndices = new int[lines.size()];
				AbstractLineNode[] children = new AbstractLineNode[lines.size()];
    			AbstractLineNode root = (AbstractLineNode) rootObject;
    			synchronized (rootObject) {
	    			root.removeAllChildren();

	    			for (int i =0; i < getRowCount(); i++) {
	    				node = getTreeNode(lines.get(i));
	    				childIndices[i] = i;
	    				children[i] = node;

	    				if (node != null) {
	    					root.add(node);
	    				}
	    			}
    			}
    			treeTableNotify.fireTreeNodesChanged(root, root.getPath(), childIndices, children);
    		}

    	}
    }

    public Iterator<AbstractLine> iterator() {

    	if (layout.hasChildren()) {
    		return new TreeIteratorForward(lines, lines.get(0));
    	}

    	return lines.listIterator();
    }

	/**
	 * @return the lines
	 */
    @Override
	public final List<AbstractLine> getLines() {
		return lines;
	}

	public final List<AbstractLine> getLines(int[] lineNumbers) {
	    ArrayList<AbstractLine> returnLines = new ArrayList<AbstractLine>(lineNumbers.length);
	    int i;

	    for (i = 0; i < lineNumbers.length; i++) {
	    	if (lineNumbers[i] >= 0 && lineNumbers[i] < lines.size()) {
	    		returnLines.add(lines.get(lineNumbers[i]));
	    	}
	    }
	    return returnLines;
	}

	/**
	 * @param newDisplayErrorDialog the displayErrorDialog to set
	 */
	public final void setDisplayErrorDialog(boolean newDisplayErrorDialog) {
		this.displayErrorDialog = newDisplayErrorDialog;
	}

	/**
	 * @return the allowMultipleRecords
	 */
	public final boolean isAllowMultipleRecords() {
		return allowMultipleRecords;
	}

	/**
	 * @param allowMultipleRecords the allowMultipleRecords to set
	 */
	public final void setAllowMultipleRecords(boolean allowMultipleRecords) {
		this.allowMultipleRecords = allowMultipleRecords;
	}

	/**
	 * @return the treeTableNotify
	 */
	public final TreeTableNotify getTreeTableNotify() {
		return treeTableNotify;
	}

	/**
	 * @param newTreeTableNotify the treeTableNotify to set
	 */
	public final void setTreeTableNotify(TreeTableNotify newTreeTableNotify) {
		this.treeTableNotify = newTreeTableNotify;
	}
	/**
	 * @param l
	 * @see net.sf.RecordEditor.utils.swing.treeTable.TreeTableNotify#addTreeModelListener(javax.swing.event.TreeModelListener)
	 */
	public void addTreeModelListener(TreeModelListener l) {
		if (isView()) {
			baseFile.addTreeModelListener(l);
		} else if (treeTableNotify != null) {
			treeTableNotify.addTreeModelListener(l);
		}
	}

	/**
	 * @param l
	 * @see net.sf.RecordEditor.utils.swing.treeTable.TreeTableNotify#removeTreeModelListener(javax.swing.event.TreeModelListener)
	 */
	public void removeTreeModelListener(TreeModelListener l) {
		if (isView()) {
			this.baseFile.removeTreeModelListener(l);
		} else if (treeTableNotify != null) {
			treeTableNotify.removeTreeModelListener(l);
		}
	}



	/**
	 * @see javax.swing.event.TreeModelListener#treeNodesChanged(javax.swing.event.TreeModelEvent)
	 */
	@Override
	public void treeNodesChanged(TreeModelEvent arg0) {
		invokeLaterDataChanged();
	}

	/**
	 * @see javax.swing.event.TreeModelListener#treeNodesInserted(javax.swing.event.TreeModelEvent)
	 */
	@Override
	public void treeNodesInserted(TreeModelEvent arg0) {
		invokeLaterDataChanged();
	}

	/**
	 * @see javax.swing.event.TreeModelListener#treeNodesRemoved(javax.swing.event.TreeModelEvent)
	 */
	@Override
	public void treeNodesRemoved(TreeModelEvent arg0) {
		invokeLaterDataChanged();
	}

	/**
	 * @see javax.swing.event.TreeModelListener#treeStructureChanged(javax.swing.event.TreeModelEvent)
	 */
	@Override
	public void treeStructureChanged(TreeModelEvent arg0) {
		invokeLaterDataChanged();
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.file.ArrayNotifyInterface#notifyOfChange(net.sf.JRecord.Details.AbstractLine)
	 */
	@Override
	public void notifyOfChange(AbstractLine line) {
		if (baseFile.treeTableNotify == null) {
			invokeLaterDataChanged();
		} else {
			baseFile.fireRowUpdated(0, null, line);
		}
	}


	private void invokeLaterDataChanged() {
		SwingUtilities.invokeLater(new Runnable() {

			/**
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				baseFile.fireTableDataChanged();
			}
		});
	}

	private IDataStore checkDataStore4ModeListner(IDataStore s) {
		if (s instanceof TableModelListener) {
			this.addTableModelListener((TableModelListener) s);
		}
		return s;
	}


	private void newLines(int noLines, long bytes, AbstractLineReader<AbstractLayoutDetails> reader, String fname) {
		if (lines != null && lines instanceof TableModelListener) {
			this.removeTableModelListener((TableModelListener) lines);
		}
		lines = newDataStore(useBigFileModel(bytes), noLines, bytes, reader, fname);
	}


	public final IDataStore<AbstractLine> newDataStore(boolean useBigFileMdl, int noLines, long bytes, AbstractLineReader<AbstractLayoutDetails> reader, String fname) {
		if (layout == null) {
			return new DataStoreStd.DataStoreStdBinary<AbstractLine>(layout, noLines);
		}
		if (useBigFileMdl && layout.getOption(Options.OPT_SUPPORTS_BLOCK_STORAGE) == Options.YES) {
			AbstractLayoutDetails l = (LayoutDetail) layout;
//			if (l.getOption(Options.OPT_SUPPORTS_BLOCK_STORAGE) == Options.YES) {
		 	int fileStructure = l.getFileStructure();
			if (l.getOption(Options.OPT_STORAGE_TYPE) == Options.TEXT_STORAGE) {
				System.out.println("Editing using Char-Line Big memory Model - this will reduce functionality !!!");
				//Common.logMsg(AbsSSLogger.WARNING,
				//		"\nEditing using Char-Line Big memory Model - this will reduce functionality !!!", null);
				return new DataStoreLarge(
						(LayoutDetail) layout,
						FileDetails.CHAR_LINE,
						100000);
			} else 	if (fileStructure == Constants.IO_UNKOWN_FORMAT
					||  fileStructure == Constants.IO_WIZARD
					||  l.getLayoutType()    == Constants.rtDelimited
					||  fileStructure == Constants.IO_NAME_1ST_LINE
					||  fileStructure == Constants.IO_GENERIC_CSV) {
				System.out.println("\nEditing using Big Variable Length memory Model - this will reduce functionality !!!");
//				Common.logMsg(AbsSSLogger.WARNING,
//						"\nEditing using Big Variable Length memory Model - this will reduce functionality !!!", null);
				return new DataStoreLarge(
						(LayoutDetail) layout,
						FileDetails.VARIABLE_LENGTH,
						100000);
			} else 	if (l.getOption(Options.OPT_IS_FIXED_LENGTH) == Options.YES) {
				System.out.println("Editing using Big Fixed Length memory Model - this will reduce functionality !!!");
//				Common.logMsg(AbsSSLogger.WARNING,
//						"\nEditing using Big Fixed Length memory Model - this will reduce functionality !!!", null);
				return new DataStoreLarge(
						(LayoutDetail) layout,
						FileDetails.FIXED_LENGTH,
						layout.getMaximumRecordLength());
			} else {
				System.out.println("Editing using Big Variable Length memory Model - this will reduce functionality !!!");
//				Common.logMsg("", null);
//				Common.logMsg(AbsSSLogger.WARNING,
//						"Editing using Big Variable Length memory Model - this will reduce functionality !!!", null);

				int type = FileDetails.VARIABLE_LENGTH;
				IByteReader r = null;

				char largeOpt = Common.OPTIONS.largeVbOption.get();
				if (reader != null && reader instanceof LineReaderWrapper
				&& fileStructure != Constants.IO_VB_DUMP
				&& (   ( largeOpt == ProgramOptions.LARGE_VB_TEST)
					|| ( largeOpt == ProgramOptions.LARGE_VB_YES
					  && bytes > Runtime.getRuntime().totalMemory())
				)) {
					type = FileDetails.VARIABLE_LENGTH_BASEFILE;
					r = ((LineReaderWrapper) reader).getByteReader();
				}

				return new DataStoreLarge(
						(LayoutDetail) layout,
						type,
						layout.getMaximumRecordLength(),
						r,
						fname);
			}
		}
//		}
		
		//System.out.println("Use Default Big model !!!");
		return checkDataStore4ModeListner(DataStoreStd.newStore(layout, noLines));
	}
	
	private static IProgressDisplay newProgressDisplay(boolean useEmpty, String heading, ReMsg msg,  int total, int checkCountAt) {
		if (useEmpty) {
			return EMPTY_PROGRESS;
		} 
		
		return new ProgressDialog(heading, msg, total, checkCountAt);
	}

	public final boolean useBigFileModel(long bytes) {

		if (layout != null && layout.getOption(Options.OPT_SUPPORTS_BLOCK_STORAGE) == Options.YES) {
			if (layout.getOption(Options.OPT_STORAGE_TYPE) == Options.TEXT_STORAGE) {
				bytes *= 2;
			}
			return bytes > memoryCompare;
		}
		return false;
	}

	/**
	 * @param recordIdx
	 * @return
	 * @see net.sf.RecordEditor.re.file.FieldMapping#getFieldVisibility(int)
	 */
	public boolean[] getFieldVisibility(int recordIdx) {
		return columnMapping.getFieldVisibility(recordIdx);
	}

	public boolean isSimpleCsvFile() {
		return layout.getRecordCount() == 1
		&& (   layout.getFileStructure() == Constants.IO_NAME_1ST_LINE
			|| layout.getRecord(0).getRecordType() == Constants.rtDelimited
			|| layout.getRecord(0).getRecordType() == Constants.rtDelimitedAndQuote);
	}

	private void doCompress(final int pos) {
		if ((lines instanceof DataStoreLarge && ((DataStoreLarge) lines).getFileDetails().isOkToCompress(FileDetails.AGGRESSIVE_CHECK))
		|| copySrc instanceof DataStoreLarge && ((DataStoreLarge) copySrc).getFileDetails().isOkToCompress(FileDetails.AGGRESSIVE_CHECK)) {
			new Thread(
				new Runnable() {
					
					@Override
					public void run() {
						if (copySrc instanceof DataStoreLarge) {
							((DataStoreLarge) copySrc).doCompress(1, -1);
						}
						if (lines instanceof DataStoreLarge) {
							((DataStoreLarge) lines).doCompress(4, pos);
						}
					}
				}).start();;
		}
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.file.GetView#getFileView()
	 */
	@Override
	public FileView getFileView() {
		return this;
	}

	public boolean isDocumentViewAvailable(boolean colorView) {
		return lines.isTextViewPossibleRE()
			&& (layout.getOption(Options.OPT_IS_TEXT_EDITTING_POSSIBLE) == Options.YES)
			&& textFile;
	}

	
	private void checkIfTextFile() {
		for (int i = 0; i < layout.getRecordCount(); i++) {
			AbstractRecordDetail record = layout.getRecord(i);
			for (int j = 0; j < record.getFieldCount(); j++) {
				if (TypeManager.getInstance().getType(record.getField(j).getType()).isBinary()) {
					textFile = false;
					return;
				}
			}
		}
		textFile = true;
	}
	
//	public Document asDocument() {
//
//		if (lines.isTextViewPossible()) {
//			return new FileDocument3(this, this.lines.getTextInterface());
//		}
//		return null;
//	}
//
//

	public DataStoreContent asDocumentContent() {

		if (lines.isTextViewPossibleRE()) {
			return new DataStoreContent(this, this.lines.getTextInterfaceRE());
		}
		return null;
	}

	private class ChkStore implements CheckStore {
		long checkRecsAt, checkAdj, count = 0;
		
		private ChkStore() {
			int len = layout.getMaximumRecordLength();
			
			checkRecsAt  = calcAdj(len);
			checkAdj = checkRecsAt;
		}
		
		private long calcAdj(long len) {
			if (len == 0) {
				len = 50;
			}
//			System.out.println("==> " + count + " / " + memoryCompare 
//			+ " "  + ((memoryCompare / (len * 10)) * 11) + " ~> " + (((memoryCompare / (len * 10)) * 11) / 5));
			return Math.max(10000, ((memoryCompare / (len * 10)) * 11) / 5);
		}
		
		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.re.file.CheckStore#checkDatasStore(int numLines)
		 */
		@Override
		public void checkDatasStore(int numLines) {
			count += numLines;
			if (count > checkRecsAt) {
				long bytes = lines.getSpaceRE();
				int size = lines.size();
				
				if (useBigFileModel(bytes)) {
					IDataStore<AbstractLine> newDataStore = newDataStore(true, lines.size(), bytes, null, "");
					for (int i = 0; i < size; i++) {
						newDataStore.add(lines.get(i));
						lines.set(i, null);
					}
					lines = newDataStore;
					checkRecsAt = Integer.MAX_VALUE - 1;
					count = 0;
				} else {
					checkAdj = calcAdj(bytes / size);
					
					checkRecsAt += checkAdj;
				}
			}
		}
		
	}
}