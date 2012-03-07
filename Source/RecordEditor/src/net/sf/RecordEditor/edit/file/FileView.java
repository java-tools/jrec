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
package net.sf.RecordEditor.edit.file;
 
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
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

import net.sf.JRecord.ByteIO.AbstractByteReader;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractChildDetails;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.LineCompare;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.AbstractLineIOProvider;
import net.sf.JRecord.IO.AbstractLineWriter;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.JRecord.IO.LineReaderWrapper;

import net.sf.RecordEditor.edit.file.storage.DataStore;
import net.sf.RecordEditor.edit.file.storage.FileDetails;
import net.sf.RecordEditor.edit.file.storage.DataStoreLarge;
import net.sf.RecordEditor.edit.file.storage.DataStoreStd;
import net.sf.RecordEditor.edit.tree.LineNodeChild;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ProgramOptions;
import net.sf.RecordEditor.utils.filter.ColumnMappingInterface;
import net.sf.RecordEditor.utils.filter.FilterDetails;
import net.sf.RecordEditor.utils.filter.FilterField;
import net.sf.RecordEditor.utils.filter.FilterFieldList;
import net.sf.RecordEditor.utils.filter.Compare;
import net.sf.RecordEditor.utils.swing.DelayedFieldValue;
import net.sf.RecordEditor.utils.swing.treeTable.TreeTableNotify;


/**
 * This class is the internal representation of a File. It contains
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

@SuppressWarnings("serial")
public class FileView<Layout extends AbstractLayoutDetails<? extends FieldDetail, ? extends AbstractRecordDetail>>
						extends 			AbstractTableModel
						implements 	TableModelListener, ColumnMappingInterface, 
												TreeModelListener, AbstractChangeNotify, GetView {

	public  static final  int SPECIAL_FIELDS_AT_START  = 2;
	private static final  int BUFFER_SIZE = 65536;
	
    private static final  int SET_USING_FIELD = 1;
    public  static final  int SET_USING_TEXT  = 2;
    public  static final  int SET_USING_HEX   = 3;
	
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
    

    
    private static final FieldMapping NULL_MAPPING =  new FieldMapping(new int[0]);
    
    private static final String[] LINE_COLUMN_HEADINGS = {
    	"", "Full Line",  "Hex (1 Line)", "Hex (2 Lines)", "Hex (SPF Edit Style)", "Hex (Alternative)" 
    };
	private String[] lineColumns = new String[LINE_COLUMN_HEADINGS.length];
    
	private int maxNumColumns = -1;
	private int defaultPreferredIndex = 0;
    //TODO this class badly needs to be split up
    //TODO ---------------------------------------------
    
    public boolean saveAvailable = true;
    
	private static AbstractLine[] copySrc = null;

	
	private String fileName;
	private String altName = null;
	
	private AbstractLineIOProvider ioProvider;

	private int currLayoutIdx = 0;
	private boolean fileRead = false;
	private boolean clearWaiting = false;
	private String  msg = "";

	private boolean toSave  = true;
	private boolean view    = false;

	//private List<AbstractLine<Layout>> lines;
	private DataStore<AbstractLine> lines;
	private Layout layout;

	private boolean browse;
	private byte changeStatus = NO_CHANGE;

	private JFrame frame = null;

	private FileView<Layout> baseFile;

	private FieldMapping columnMapping = NULL_MAPPING;
	private boolean displayErrorDialog = true;
	private boolean allowMultipleRecords = true;
	
	private TreeTableNotify treeTableNotify = null; 
	private WeakHashMap<AbstractLine<Layout>, AbstractLineNode> nodes = null;

	private ArrayList<FileWriter> writers = new ArrayList<FileWriter>();
	
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
     	   final Layout pFd,
		   final AbstractLineIOProvider pIoProvider,
     	   final boolean pBrowse) {
		super();

		this.lines = getDataStore(INITIAL_FILE_SIZE, Constants.NULL_INTEGER, null, "");
		this.fileName   = null;
		this.layout     = pFd;
		this.ioProvider = pIoProvider;
		this.browse     = pBrowse;
		this.baseFile   = this;

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
	        	   final Layout pFd,
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
     	   			final Layout pFd,
     	   			final AbstractLineIOProvider pIoProvider,
     	   			final boolean pBrowse) throws IOException, RecordException {
		super();


		this.layout     = pFd;
		this.ioProvider = pIoProvider;
		this.browse     = pBrowse;
		this.baseFile   = this;
		
		readFile(pFileName);
	}
	

	/**
	 * Create a view of a file
	 *
	 * @param pLines    lines to display
	 * @param pBaseFile the basefile
	 * @param pRemapColumns column remapping
	 */
	public FileView(final DataStore<AbstractLine> pLines,
	        		final FileView pBaseFile,
	        		final FieldMapping colMapping) {
		this(pLines, pBaseFile, pLines.get(0).getLayout(), colMapping, pBaseFile == null);
	}

	/**
	 * Create a view of a file
	 *
	 * @param pLines    lines to display
	 * @param pBaseFile the basefile
	 * @param pRemapColumns column remapping
	 */
	public FileView(final DataStore<AbstractLine> pLines,
	        		final FileView<Layout> pBaseFile,
	        		final FieldMapping colMapping,
	        		boolean pBrowse) {
		this(pLines, pBaseFile, pLines.getLayout(), colMapping, pBrowse);
	}
	
	
	public FileView(String name, final DataStore pLines,
    		final AbstractLayoutDetails layoutDtls) {
		this(pLines, null, layoutDtls, null, false);
		
		altName = name;
		saveAvailable = false;
	}

	private FileView(final DataStore pLines,
    		final FileView<Layout> pBaseFile,
    		final AbstractLayoutDetails layoutDtls,
    		final FieldMapping colMapping,
    		boolean pBrowse) {
		super();

		this.lines      = pLines;
		if (colMapping != null) {
			columnMapping = colMapping;
		}
		
	    fileRead = true;
	    view     = pBaseFile != null;

		if (pBaseFile == null) {
			baseFile = this;
			fileName = "";
			layout = (Layout) layoutDtls;
			browse = pBrowse;
			ioProvider = LineIOProvider.getInstance();
		} else {
			this.baseFile		= pBaseFile;
			this.fileName	= baseFile.getFileName();
			this.layout		= baseFile.getLayout();
			this.browse		= baseFile.isBrowse();
			this.ioProvider	= baseFile.getIoProvider();
			baseFile.addTableModelListener(this);
		}

	    initLineCols();
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
		} else {
			doClear();
		}
	}
	
	public final void doClear() {
		if (lines != null) {
			lines.clear();
		}
		copySrc = null;
		lines = null;
		nodes = null;
		System.gc();
	}
	
	public void readFile(String pFileName) 
	throws IOException, RecordException {
		AbstractLineReader<Layout> reader;
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
				reader = ioProvider.getLineReader(structure);

		        FileInputStream rff = new FileInputStream(pFileName);
		        if (isGZip) {
		            reader.open(new GZIPInputStream(rff, BUFFER_SIZE), layout);

		            readFile(reader, file, isGZip, rff, pFileName);
		        } else if (Common.OPTIONS.useBigFixedModel.isSelected()
		        	   &&  structure == Constants.IO_FIXED_LENGTH 
		        	   &&  useBigFileModel(file.length())) {
		        	lines = DataStoreLarge.getFixedLengthRecordStore((LayoutDetail)layout, pFileName);
		        } else {
		        	BufferedInputStream rf = new BufferedInputStream(rff, BUFFER_SIZE);

		            reader.open(rf, layout);
		            browse |= (! reader.canWrite());
		            readFile(reader, file, isGZip, rf, pFileName);
		        }
	            rff.close();
		    }
		} else {
			reader = ioProvider.getLineReader(layout.getFileStructure());
			reader.generateLayout(layout);
			retrieveLayout(reader);
		    toSave = false;
		    this.lines      = getDataStore(INITIAL_FILE_SIZE, Constants.NULL_INTEGER, null, "");
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
			AbstractLineReader<Layout> reader, 
			File file, 
			boolean isGZip,
			InputStream rf,
			String fname) 
	throws IOException {
	    AbstractLine<Layout> line;
	    int count = 0;
	    long interval = 1500000000l;
	    long t, t1, t2, tt1, tt2;
	    long time = System.nanoTime();
	    long lastTime = time;
	    //long s1;
	    boolean isVeryBigFile = file.length() > ((long) Integer.MAX_VALUE);
	    
	    double total = file.length();
	    double ratio;
	        
	    retrieveLayout(reader);
	    allocateLines(file, isGZip, reader, fname);
	    
	    
    	if (isGZip) {
    		total *= 6;
    	}
	    

	    t1 = time;
	    tt1 = 0;
	    tt2 = 0;
	    if (Common.OPTIONS.loadInBackgroundThread.isSelected()) {
	    	ProgressDisplay readProgress = new ProgressDisplay(file.getName());
	    	try {
			    while ((line = reader.read()) != null) {
			       tt1 += System.nanoTime() - t1;
			       t2 = System.nanoTime();
			       lines.add(line);
			       tt2 += System.nanoTime() - t2;
			       if (count++ > 2000) {
			    	   count = 0;
			    	   t = System.nanoTime();
			    	   if (t - lastTime > interval) {
			    		   if (isVeryBigFile) {
			    			   ratio = ((double)lines.getSpace()) / total;
			    		   } else {
			    			   ratio = 1 - ((double)getAvailable(rf)) / total;
			    		   }
			    		   readProgress.updateDisplay(
			    				   lines.getSizeDisplay(),
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
		    }
	    }
	    retrieveLayout(reader);
	    lines.setLayout(layout);

	    reader.close();
	    
	    
	    time = (( System.nanoTime() - time) / 10000000);
	    double ttime = ((double) time) / 100;
//	    Common.logMsg(1, 
//	    		lines.getSizeDisplay() + "\n  Load Time : " + ttime,
//	    		null);
	    System.out.println(
	    		lines.getSizeDisplay());
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
	
	private void retrieveLayout(AbstractLineReader<Layout> reader) {
	    Layout l = reader.getLayout();
	    if (l != null) {
	    	layout = l;
	    }
	}
	
	private void allocateLines(File file, boolean isGZip, AbstractLineReader reader, String fname) {
    	int numLines = INITIAL_FILE_SIZE ;
    	int maxLength = layout.getMaximumRecordLength();
    	AbstractLineReader r = reader;
    	try {
    		long len = file.length();
    		if (maxLength > 0) {
    			numLines = ((int) ((len / maxLength) * 11 / 10)) ;
    			if (isGZip) {
    				numLines *= 3;
    			}

    			numLines = Math.max(MINIMUM_FILE_SIZE, numLines);
    		}
			if (isGZip) {
				len *= 9;
				r = null;
			}
    		this.lines      = getDataStore(numLines, len, r, fname);
    	} catch (Exception e) {
    		this.lines      = getDataStore(INITIAL_FILE_SIZE, Constants.NULL_INTEGER, null, fname);
    	}

	}

	/**
	 * Adds a line to the Line-List
	 *
	 * @param line2add line to be added the line-list
	 */
	public void add(AbstractLine<Layout> line2add) {
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
	public void setValueAt(Object val, int row, int col) {

		setValueAt(currLayoutIdx, val, row, col);

	}

	/**
	 * @see javax.swing.table#setValueAt
	 */
	public void setValueAt(int layoutIdx, Object val, int row, int col) {

		if ((row >= getRowCount()) || (col >= (getLayoutColumnCount(layoutIdx) + SPECIAL_FIELDS_AT_START)) 
		|| (col <= LINE_NUMER_COLUMN)) {
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

		if (col == LINE_NUMER_COLUMN) {
			return "Line";
		} else if (col > LINE_NUMER_COLUMN) {
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
		return null; // getRealColumnName(currLayout, col - 2);
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
	 * 
	 * @param layoutIndex record index
	 * @param fldNum field number
	 * @return field name
	 */
	public String getFieldName(int layoutIndex, int fldNum) {
        int tCol = getRealColumn(layoutIndex, fldNum - SPECIAL_FIELDS_AT_START);
        String s;
        FieldDetail fld = getLayoutField(layoutIndex, tCol);
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
	public FieldDetail getLayoutField(final int layoutIdx, final int idx) {
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

		if ((row >= getRowCount())
		|| (tmpCol < 0 && tmpCol != Constants.KEY_INDEX)
		|| (tmpCol >= layout.getRecord(layoutIdx).getFieldCount())) {
			return null;
		}
		try {
			return lines.getTempLine(row).getField(layoutIdx, tmpCol);
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
			layoutIdx = currLine.getPreferredLayoutIdx();
		}
		
		col = getRealColumn(layoutIdx, col);
		if ((col < 0 && col != Constants.KEY_INDEX)
		||  (layoutIdx < layout.getRecordCount()
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
				&& val.equals(currLine.getField(layoutIdx, col)))) { return; }

		    if ((layoutIdx >= layout.getRecordCount())) {
		    	switch(DisplayType.displayType(layout, layoutIdx)) {
				case DisplayType.FULL_LINE:
		            String s;// = "";
		            //if (val != null) {
		                s = val.toString();
		            //}
		            currLine.setData(s);
		            fireRowUpdated(row, currLine);
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

		            fireRowUpdated(row, currLine);
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
	                "Conversion Error",
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

			fireRowUpdated(row, currLine);

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
	public void fireRowUpdated(int row, AbstractLine currLine) {

		if (baseFile.treeTableNotify == null) {
			tblFireRowUpdated(row, currLine);
		} else if (isView()) { 
			baseFile.fireRowUpdated(row, currLine);
		} else {
			AbstractLineNode node = getTreeNode(currLine);
			
			if (node == null) {
				tblFireRowUpdated(row, currLine);
			} else {
				baseFile.treeTableNotify.fireTreeNodesChanged(node, node.getPath(), null, null);
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
		AbstractLine<Layout> l = lines.get(lineNumber);
		
		return getTreeNode(l);
	}

	/**
	 * wether cell is updateable
	 *
	 * @see javax.swing.table.AbstractTableModel.isCellEditable
	 */
	public boolean isCellEditable(int row, int col) {
		return col > 1
			&& (currLayoutIdx != layout.getRecordCount() + 1
			  || ! isBinaryFile());
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
				AbstractLine<Layout> l;
			    deleteLinesFromView(recNums);
			    for (int i = 0; i < recNums.length; i++) {
			    	l = (AbstractLine<Layout>) lines.get(recNums[i]);
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
	protected final void deleteLinesFromView(int[] recNums) {
		
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
				} else {
					fireTableRowsDeleted(start, end);
					System.out.println(" ++ delete range " + start + " " + end);
					start = recNums[i];
					end   = recNums[i];
				}
//				System.out.print(" " + recNums[i]);
			}
			if (end >= 0) {
				System.out.println(" << delete range " + start + " " + end);
				fireTableRowsDeleted(start, end);
			}
			
//			System.out.println(" << starting delete  " + start + " " + end);
			for (i = recNums.length - 1; i >= 0; i--) {
				//System.out.println("Deleting: " + i + " ~ " + recNums[i]);
				if (recNums[i] >= 0 && recNums[i] < lines.size()) {
					deleteNode(lines.get(recNums[i]));
					lines.remove(recNums[i]);
				}
			}
//			System.out.println(" << end delete  " + start + " " + end);
		}
	}
	
//	public final void deleteLines(AbstractLine<Layout>[] linesTodelete) {
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
	public final void deleteLine(AbstractLine<Layout> line) {
		
		if (view) {
			baseFile.deleteOneLine(line);
		} else {
			deleteOneLine(line);
		}
	}
	
	private final void deleteOneLine(AbstractLine<Layout> line) {
		
		deleteNode(line);
		if (line.getTreeDetails().getParentLine() != null) {
			line.getTreeDetails().getParentLine().getTreeDetails().removeChild(line);
		} else {
			int num = lines.indexOf(line);
			if (num >= 0) {
				fireTableRowsDeleted(num, num);
				//System.out.println(" >> delete " + recNum + " " + recNum);
	        	lines.remove(num);
			}
		}
		
		setChanged(true);
	}
	
	private void deleteNode(AbstractLine<Layout> line) {
		
		AbstractLineNode node= getTreeNode(line) ;
		
		if (node != null) {
			if (treeTableNotify != null) {
				treeTableNotify.fireTreeNodesRemoved(node);
			}
			node.removeFromParent();
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
			nodes = new WeakHashMap<AbstractLine<Layout>, AbstractLineNode>(lines.size());
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
	public final AbstractLine<Layout> repeatLine(int lineNumber) {

	    AbstractLine<Layout> newLine;
	    if (isView() || this.treeTableNotify == null) {
	        return stdRepeatLine(lineNumber);
	    } 
	    return repeatLine(lines.get(lineNumber));
	}

	
	public final AbstractLine<Layout> repeatLine(AbstractLine<Layout> line) {
		 AbstractLine<Layout> newLine = null;
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
				
				newLine = (AbstractLine<Layout>) line.clone();
				insert(parent, newLine, loc);
			break;
			case (FAILED_2ND_ROOT_NOT_ALLOWED):
				Common.logMsg("You can not Repeat the root Segment !!!", null);
			break;
			case (FAILED_CHILD_ALREADY_EXISTS):
				Common.logMsg("Only one " + line.getTreeDetails().getChildDefinitionInParent().getName()
						+" is allowed !!!", null);
			}
		 }
		
		 return newLine;
	}
	

	public void insert(
			AbstractLine<Layout> parent,
			AbstractLine<Layout> newLine,
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

					node = new LineNodeChild(name, baseFile, newLine);

					((AbstractLineNode) treeTableNotify.getRoot()).insert(node, loc);
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
			Common.logMsg("Only one " + newLine.getTreeDetails().getChildDefinitionInParent().getName()
					+" is allowed !!!", null);
		}
	}
	
	private int checkInsertOk(AbstractLine<Layout> parent, AbstractChildDetails childDef) {
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
	private final AbstractLine<Layout> stdRepeatLine(int lineNumber) {

	    AbstractLine<Layout> newLine;
	    if (isView()) {
	        int l = baseFile.indexOf(lines.get(lineNumber));
	        newLine = baseFile.repeatLine(l);
		}else {
	        newLine = (AbstractLine<Layout>)  lines.get(lineNumber).clone();
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

		int i;
		int num = recNums.length;
		int limit = getLineHoldLimit();
		
		if (num > limit) {
			num = limit;
			Common.logMsg("Copy limit of " + limit
					+ " excedded; only the first " + limit
					+ " lines copied", null);
		}
		
		copySrc = new AbstractLine[num];

		for (i = 0; i < num; i++) {
			copySrc[i] = lines.getTempLine(recNums[i]).getNewDataLine();
		}
	}



	/**
	 * @param copySrc the copySrc to set
	 */
	public static final void setCopyRecords(AbstractLine[] copySrc) {
		FileView.copySrc = copySrc;
	}

	/**
	 * paste records (that have been copied previously)
	 *
	 * @param pos position to paste records
	 */
	public final void pasteLines(int pos) {
		AbstractLine l = null;
		if (pos >=0 && pos < lines.size()) {
			l = getLine(pos);
		}
		pasteLines(pos, l);
	}
	
	public final AbstractLine[] pasteLines(int pos, AbstractLine line) {
		int i;	
		AbstractLine[] ret = copySrc;
		if (copySrc != null && copySrc.length > 0) {
			if (treeTableNotify == null || pos < 0
			|| getLine(pos).getTreeDetails().getParentLine() == null) {
				ret = pasteTableLines(pos);
			} else  {
				ret = pasteTreeLines(line);
			}
		}
		return ret;
	}

	private final  AbstractLine[] pasteTableLines(int pos) {
		AbstractLine[] cpy;
		
	    if (view) {
	    	AbstractLine line = lines.get(Math.max(0, pos));
	    	int baseRecordNumber = baseFile.indexOf(line);

	        cpy = baseFile.pasteLines(baseRecordNumber, line);
	    } else {    	
			cpy = copySrc;
	    }

	   return performPaste(pos, cpy);
	}
	
	
	public final  AbstractLine[] pasteTreeLines(AbstractLine pos) {
		AbstractLine[] ret = null;
		if (copySrc != null && copySrc.length > 0 
		&& copySrc[0].getLayout() == layout ) {
			cloneCopyLines();
			ret = pasteTreeLines(pos, copySrc);
		}
		
		return ret;
	}


	private final AbstractLine[]  pasteTreeLines(AbstractLine pos, AbstractLine[] copySource) {
		
		AbstractLine parent = null;
		AbstractChildDetails childDef;
		
		AbstractLine[] ret = copySource;
		ArrayList<AbstractLine> accepted = new ArrayList<AbstractLine>(copySource.length);
		
		int arrayPos = -1;
		
		if (pos != null) {
			parent = pos.getTreeDetails().getParentLine();
			arrayPos = pos.getTreeDetails().getParentIndex()+1;
		}
		
		int p = -1;
		if (pos == null) {
			p = 0;
		}
		int idx = lines.indexOf(pos);
		if (idx >= 0) {
			idx += 1;
		}
		for (int i = 0; i < copySource.length; i++ ) {
			childDef = copySource[i].getTreeDetails().getChildDefinitionInParent();
			if (checkInsertOk(pos, childDef) == CAN_INSERT) {
				accepted.add(copySource[i]);
				insert(pos, copySource[i], p++);
			} else if (checkInsertOk(parent, childDef) == CAN_INSERT) {
				if (parent == null) {
					accepted.add(copySource[i]);
					insert(null, copySource[i], idx++);
				} else {
					int id = -1;
					if (pos.getTreeDetails().getChildDefinitionInParent() == childDef) {
						id = arrayPos++;
					} 
					accepted.add(copySource[i]);
					insert(parent, copySource[i], id);
				}
			} else {
				String name = "";
				try {
					name = layout.getRecord(copySource[i].getPreferredLayoutIdx()).getRecordName();
				} catch (Exception e) {
				}
				Common.logMsg("Can not paste " +  name + ":  " + copySource[i].getFullLine(), null);
			}
		}			
		if (accepted.size() < ret.length) {
			ret = new AbstractLine[accepted.size()];
			ret = accepted.toArray(ret);
		}
		return ret;
	}
	
	private void cloneCopyLines() {

		AbstractLine<Layout> ttt;
		for (int i = 0; i < copySrc.length; i++) {
        	ttt = (AbstractLine<Layout>) copySrc[i].clone();
        	ttt.setLayout(layout);
        	copySrc[i] = ttt;
		}
	}
	
	/**
	 * paste a series of records
	 *
	 * @param pos position to copy the lines
	 * @param newLines new lines to be copied
	 */
	private AbstractLine[] performPaste(int pos, AbstractLine[] newLines) {
	    int i;
	    AbstractLine[] ret= newLines;
	    if (lines instanceof ArrayList) {
	    	((ArrayList)lines).ensureCapacity(lines.size() + newLines.length);
	    }
		
	    if (isView()) {
	        for (i = 0; i < newLines.length; i++) {
				lines.add(pos + i + 1, newLines[i]);
			}
	    } else {
	    	ret = new AbstractLine[newLines.length];
	        for (i = 0; i < newLines.length; i++) {
	        	ret[i] = lines.addCopy(pos + i + 1, newLines[i]);
			}
	    }

        setChanged(true);
        fireTableRowsInsertedLocal(pos + 1, pos + newLines.length);
        
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
	        int baseRecordNumber = baseFile.getRowCount();
	        int basePos;
	        if (lines.size() > 0) {
	             baseRecordNumber = baseFile.indexOf(lines.get(pos));
	        }
	        basePos = baseFile.newLine(baseRecordNumber, adj);

	        pos = addLine(pos + adj, baseFile.getLine(basePos));
	    } else {
			if (ioProvider == null) {
				ioProvider = LineIOProvider.getInstance();
			}
	        pos = addLine(pos + adj, ioProvider.getLineProvider(layout.getFileStructure()).getLine(layout));
	        //changed = true;
	    }

	    fireTableRowsInsertedLocal(pos, pos);

		return pos;
	}


	/**
	 * Add a line
	 *
	 * @param pos position to add a record
	 * @param line line to insert
	 *
	 * @return position a line was added
	 */
	private int addLine(int pos, AbstractLine<Layout> line) {

        if (lines.size() == 0) {
            lines.add(line);
            pos = 0;
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

	    pos.col = 0;
	    pos.row = 0;

	    while (replace(searchFor, replaceWith, pos,
	            	ignoreCase, operator)) {
//	        System.out.print(" > " + pos.row + " " + pos.col);
	    }
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
		int operator) throws RecordException {

	    find(searchFor,
	         pos,
	         ignoreCase,
	         //anyWhereInTheField, normalSearch
	         operator);

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

			pos.adjustPosition(searchFor.length(), operator);
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
		int operator) {

		String icSearchFor = searchFor;
		pos.found = false;
		//int numRows = getRowCount();
		BigDecimal testNumber = Compare.getNumericValue(operator, searchFor);
		boolean anyWhereInTheField = (operator == Compare.OP_CONTAINS)
								  || (operator == Compare.OP_DOESNT_CONTAIN);
		boolean normalSearch = (operator == Compare.OP_CONTAINS);
		//int  inc, initCol;
		
		if (ignoreCase) {
			icSearchFor = searchFor.toUpperCase();
		}

//		//System.out.println("==> " + pos.getFieldId() + " " + pos.currentFieldNumber);
//		if (pos.getFieldId() == Common.ALL_FIELDS) {
//		    int maxField;
//		    //System.out.println("~~> " + pos.currentFieldNumber);
//		    if (anyWhereInTheField) {
//		        while ((pos.row < numRows) && (pos.row >= 0)
//		                && ! pos.found) {
//		            maxField = find_100_getMaxField(pos);
//
//		            while (pos.isValidFieldNum(maxField)
//		               && ! contains(ignoreCase, pos, icSearchFor, normalSearch)) {
//		                pos.nextField();
//		                //System.out.print('.');
//		            }
//		            if (!pos.found) {
//		                pos.nextRow();
//		            }
//		        }
//		    } else {
//		        while ((pos.row < numRows) && (pos.row >= 0)
//		                && ! pos.found) {
//		            maxField = find_100_getMaxField(pos);
//
//		            while (pos.isValidFieldNum(maxField)
//				       && ! cmp(ignoreCase, pos, icSearchFor, testNumber, operator)) {
//		                pos.nextField();
//		            }
//		            if (!pos.found) {
//		                pos.nextRow();
//		            }
//		        }
//		    }
//		} else {
//		    if (anyWhereInTheField) {
//		        while ((pos.row < numRows) && (pos.row >= 0)
//		                && ! contains(ignoreCase, pos, icSearchFor, normalSearch)) {
//		            pos.nextRow();
//		        }
//		    } else {
//		        while ((pos.row < numRows) && (pos.row >= 0)
//		                && ! cmp(ignoreCase, pos, icSearchFor, testNumber, operator)) {
//		            pos.nextRow();
//		        }
//		    }
//		}

		PositionIncrement inc = PositionIncrement.newIncrement(pos, lines);
	    if (anyWhereInTheField) {
	        while (inc.isValidPosition()
	                && ! contains(ignoreCase, inc.pos, icSearchFor, normalSearch)) {
	            inc.nextPosition();
	        }
	    } else {
	        while (inc.isValidPosition()
	                && ! cmp(ignoreCase, inc.pos, icSearchFor, testNumber, operator)) {
	            inc.nextPosition();
	        }
	    }
	}



	/**
	 * Compare the test value with a field
	 * @param ignoreCase wether to ignore the case of the string
	 * @param pos file position
	 * @param testValue value the field against
	 * @param testNumber value as a bigDecimal 
	 * @param operator operator to use in the comparison
	 * @return
	 */
	private boolean cmp(
			boolean ignoreCase,
			FilePosition pos,
			String testValue,
			BigDecimal testNumber,
			int operator) {
		int col = getRealColumn(pos.recordId, pos.currentFieldNumber);
		String s = getFieldValue(pos, col);
		boolean normalSearch = false;

		//System.out.println("==> " + pos.currentFieldNumber + " " + pos.row + " >" + s + "< " + testValue + " ~~ " + operator);

		if (pos.col > 0 && pos.col != FilePosition.END_OF_COLUMN) {
			return false;
		}

		switch (operator){
			case (Compare.OP_EQUALS): normalSearch = true;
			case (Compare.OP_NOT_EQUALS):
				if (ignoreCase) {
					pos.found = s.equalsIgnoreCase(testValue) == normalSearch;
				} else {
					pos.found = s.equals(testValue) == normalSearch;
				}
			break;
			case (Compare.OP_NUMERIC_EQ):
			case (Compare.OP_NUMERIC_GE):
			case (Compare.OP_NUMERIC_GT):
			case (Compare.OP_NUMERIC_LE):
			case (Compare.OP_NUMERIC_LT):
				pos.found = cmpToNum(s, testNumber, 
						Compare.OPERATOR_COMPARATOR_VALUES[operator - Compare.OP_NUMERIC_GT]);
			break;
			case (Compare.OP_TEXT_GE):
			case (Compare.OP_TEXT_GT):
			case (Compare.OP_TEXT_LE):
			case (Compare.OP_TEXT_LT):
				int[] vals = Compare.OPERATOR_COMPARATOR_VALUES[operator - Compare.OP_TEXT_GT];
				int v;
				if (ignoreCase) {
					v = s.compareToIgnoreCase(testValue);
				} else {
					v = s.compareTo(testValue);
				}
				if (v > 0) {
					v = 1;
				} else if (v < 0) {
					v = -1;
				}
				//System.out.println("Compare Details -> " + s + " < >" + testValue +  "<   -->" + v +" ~ " + vals[0] + " ~ " + vals[1]);
				pos.found = v == vals[0] || v == vals[1]; 
			break;
		}

		if (pos.found) {
			pos.col = 0;
		}
		return pos.found;
	}

	private boolean cmpToNum(String s, BigDecimal testValue, int[] vals) {
		int v = Compare.cNULL;
		BigDecimal fldValue = null;
		
		try {
			fldValue = new BigDecimal(s);
		} catch (Exception e) { }
		
		if (fldValue == null && testValue == null) {
			v = 0;
		} else if (fldValue == null) {
			return false;
		} else if (testValue == null) {
			v = -1;
		} else {
			v = fldValue.compareTo(testValue);
		}
		
//		System.out.println("Compare Details -> " + testValue + " < >" + fldValue + "< >" + s + "<   -->" + v
//				+" ~ " + vals[0] + " ~ " + vals[1]);
		
		return v == vals[0] || v == vals[1]; 
	}



	/**
	 *
	 * @param ignoreCase  wether to ignore Character case in the compare
	 * @param pos position in the file
	 * @param testValue Value to test the field against
	 * @param normalSearch wether to use equals or not equals
	 *
	 * @return wether the field contains testValue
	 */
	private boolean contains(
		boolean ignoreCase,
		FilePosition pos,
//		boolean forward,
		String testValue,
		boolean normalSearch) {

	    //System.out.println("~~ " + pos.currentFieldNumber + " ");
	    int col = getRealColumn(pos.recordId, pos.currentFieldNumber);
		String s = getFieldValue(pos, col);
				
		int fromIndex = Integer.MAX_VALUE;
		
		//System.out.println("==> " + pos.currentFieldNumber + " " + pos.row + " >" + s + "< " + testValue);
		if (pos.isForward()) {
		    fromIndex = 0;
		}

		if (pos.col > 0 && pos.col != FilePosition.END_OF_COLUMN) {
	        fromIndex = pos.col;
		}

		if (pos.isForward()) {
		    if (ignoreCase) {
		        pos.col = s.toUpperCase().indexOf(testValue.toUpperCase(), fromIndex);
		    } else {
		        pos.col = s.indexOf(testValue, fromIndex);
		    }
		} else {
		    if (ignoreCase) {
		        pos.col = s.toUpperCase().lastIndexOf(testValue.toUpperCase(), fromIndex);
		    } else {
		        pos.col = s.lastIndexOf(testValue, fromIndex);
		    }
		}
		pos.found = (pos.col >= 0) == normalSearch;
		
//		if (pos.found) {
//			System.out.println("Contains " + pos.found + " row: " + pos.row
//		        + " " + pos.recordId + " " + pos.currentFieldNumber
//		        + " >" + s + "< " + testValue);
//		}
		return pos.found;
	}

	private String getFieldValue(FilePosition pos, int col) {
		AbstractLine l = pos.currentLine;
		String s= "";
		if (l == null && pos.row >= 0 && pos.row < lines.size()) {
			 l = lines.getTempLine(pos.row);
		} 
		
		if (l != null) {
			try {
				if (pos.getFieldId() == Common.FULL_LINE) {
					s = toStr(l.getFullLine());
				} else if (pos.currentLine != null && pos.getFieldId() == Common.ALL_FIELDS) {
					s = toStr(l.getField(l.getPreferredLayoutIdx(), col));
					System.out.println("All Fields, layout: " + l.getPreferredLayoutIdx() + " Col: " + col + " > " + s);
				} else{
					s = toStr(l.getField(pos.recordId, col));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

				 
		return s;
	}
	
	private String toStr(Object o) {
	    String s = "";
	    if (o != null) {
	        s = o.toString();
	    }
	    return s;
	}


	/**
	 * Return a requested line
	 *
	 * @param lineNum Line Number
	 *
	 * @return requested line
	 */
	public final AbstractLine<Layout> getLine(final int lineNum) {
		return lines.get(lineNum);
	}

	public final void setLine(final int lineNum, AbstractLine<Layout> l) {
		lines.set(lineNum,l);
	}

	public final AbstractLine<Layout> getTempLine(final int lineNum) {
		return lines.getTempLine(lineNum);
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
    public void setChanged(boolean fileChanged) {

        if (this.isView()) {
            baseFile.setChanged(fileChanged);
        }
//        if (fileChanged) {
//        	System.out.println("FileChanged");
//        }
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
			|| lines.size() < Common.OPTIONS.filterLimit.get();
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
    public final Layout getLayout() {
        return layout;
    }


	/**
	 * @param layout the layout to set
	 */
	public final void setLayout(Layout layout) {
		this.layout = layout;
		lines.setLayout(layout);
		
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
	    	Common.logMsg("Internal Error: lines cleared, save is not possible !!!", null);
	    	Common.logMsg("Internal Error: lines cleared, save is not possible !!", null);
	    	Common.logMsg("Internal Error: lines cleared, save is not possible !", null);
	    } else {
	    	changeStatus = BEING_SAVED;
	        saveFile(lines, fileName, toSave,
	        		new PropertyChangeListener() {

						@Override
						public void propertyChange(PropertyChangeEvent arg0) {
							if (changeStatus == BEING_SAVED) {
								toSave = false;
					            changeStatus = NO_CHANGE;
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

	    if (! sameName && "\\".equals(Common.FILE_SEPERATOR)) {
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
	    AbstractLineWriter writer = ioProvider.getLineWriter(layout.getFileStructure());
	    FileWriter fileWriter = new FileWriter(pLines, layout, pFileName, backup, isGZip, writer);
	    
	    dropCompletedWriters();

	    if (pLines.size() < 30000) {
	    	fileWriter.doWrite();
	    	if (chgListner != null) {
	    		chgListner.propertyChange(null);
	    	}
	    } else {
	    	try {
	    		net.sf.RecordEditor.edit.file.FileWriterBackground fw = new net.sf.RecordEditor.edit.file.FileWriterBackground(fileWriter);
				
				if (chgListner != null) {
					fw.addPropertyChangeListener(chgListner);
				}
				writers.add(fileWriter);
				fw.addPropertyChangeListener(saveDoneListner);
				fw.execute();
			} catch (NoClassDefFoundError e) {
				fileWriter.doWrite();
			}
	    }
	}

	private void dropCompletedWriters() {
	    for (int i = writers.size() - 1; i>=0; i--) {
	    	if (writers.get(i).isDone()) {
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
	public FileView<Layout> getFilteredView(FilterDetails filter) {
		DataStoreStd<AbstractLine> selectedLines = new DataStoreStd<AbstractLine>(layout);
        AbstractLine<Layout> line;
        boolean ok;
        int i, k, recordType;
        int limit = getLineHoldLimit();

        FilePosition pos = new FilePosition(0, 0, 0, 0, true);
        boolean filterNonPrefered = false;
        
        if (layout.getRecordCount() > 1) {
        	for (k = 0; (!filterNonPrefered) && k < layout.getRecordCount(); k++) {
        		filterNonPrefered = filter.isInclude(k) && "".equals(layout.getRecord(k).getSelectionValue());
        	}
        }


       FilterFieldList list = filter.getFilterFieldListMdl();

       Iterator<? extends AbstractLine> it;
       if (layout.hasChildren()) {
    	   it = new TreeIteratorForward(lines, null);
       } else {
    	   it = lines.listIterator();
       }
//       for (i = 0; i < numRows; i++) {
//            line = this.getLine(i);
       i = 0;
       while (it.hasNext()) {
    	   	line = it.next();
    	   	recordType = line.getPreferredLayoutIdxAlt();
//    	   	if (recordType == 4) {
//    	   		System.out.println("~~~  " + line.getField(4, 0));
//    	   	}

    	   	if (filterNonPrefered && recordType == Constants.NULL_INTEGER) {
               	ok = false;
            	for (k = 0; (!ok) && k < layout.getRecordCount(); k++) {
            		if (filter.isInclude(k) && "".equals(layout.getRecord(k).getSelectionValue())) {
            			ok = getFilteredView_MatchesFilter(i, k, pos, list, false, line);
            		}
            	}

                if (ok) {
                    selectedLines.add(line);
                }
            } else if (filter.isInclude(recordType)) {
                if (getFilteredView_MatchesFilter(i, recordType, pos, list, true, line)) {
                    selectedLines.add(line);
                }
            }
            i += 1;
            if (selectedLines.size() >= limit) {
            	int resp = JOptionPane.showConfirmDialog(
            		    frame,
            		    "The Filter limit of " + limit +
            		    " has been reached, do you wish to continue?",
            		    "",
            		    JOptionPane.YES_NO_OPTION);
            	if (resp == JOptionPane.YES_OPTION) {
            		limit += getLineHoldLimit();
            	} else {
            		Common.logMsg("Filter limit of " + limit
        					+ " excedded; only the first " + limit
        					+ " lines in the filtered view", null);
            		break;
            	}
            }

        }

        if (selectedLines.size() > 0) {
    		int[] rows = new int[layout.getRecordCount()];
            
    	    for (i = 0; i < layout.getRecordCount(); i++) {
    	    	rows[i] = getLayoutColumnCount(i);
    	    }
    	
            return new FileView<Layout>(selectedLines, baseFile, new FieldMapping(filter.getFieldMap(), rows));
        }
        return null;
	}

	private boolean getFilteredView_MatchesFilter(int rowNum,  int recordType, FilePosition pos, 
			FilterFieldList list, boolean defaultOk, AbstractLine line) {
		boolean ok = defaultOk;
		for (int j = 0; j < FilterFieldList.NUMBER_FIELD_FILTER_ROWS; j++) {
			FilterField filterField = list.getFilterField(recordType, j);
			int fldNum = filterField.getFieldNumber();
			String val = filterField.getValue();

	         if (filterField.getFieldNumber() != FilterField.NULL_FIELD) {
	        	boolean ignorecase = filterField.getIgnoreCase().booleanValue();
	
	       		pos.setAll(rowNum, 0, recordType, fldNum, true);
	       		pos.currentLine = line;
	            int op = filterField.getOperator();
	
	            if ((op == Compare.OP_CONTAINS)
	            ||  (op == Compare.OP_DOESNT_CONTAIN)) {
	            	ok = contains(ignorecase, pos, val, op == Compare.OP_CONTAINS);
	            } else {
	            	ok = cmp(ignorecase, pos, val, Compare.getNumericValue(op, val), op);
	            }
	            if (ok != defaultOk) {
	            	return ok;
	            }
	        }
        }
		return ok;
	}
	
	
	/**
	 * Get a view based on the current file
	 * @return duplicate view
	 */
	public FileView<Layout> getView() {

        return new FileView<Layout>(lines.newDataStore(), baseFile, columnMapping);
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
	    DataStoreStd<AbstractLine> selectedLines 
	    		= new DataStoreStd<AbstractLine>(layout, rows.length);

	    for (int i = 0; i < rows.length; i++) {
	    	if (rows[i] >= 0) {
	    		selectedLines.add(this.getLine(rows[i]));
	    	}
	    }

        return new FileView(selectedLines, baseFile, columnMapping);
	}



	/**
	 * Create a view of selected rows
	 * @param rows rows to add to the filtered view
	 * @return filtered view
	 */
	public final FileView getViewOfErrorRecords() {


	    DataStoreStd<AbstractLine> selectedLines = new DataStoreStd<AbstractLine>(layout);
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

        return new FileView(
        		new DataStoreStd<AbstractLine>(layout, list), 
        		baseFile, 
        		columnMapping);
	}

	/**
	 * @see net.sf.RecordEditor.utils.filter.ColumnMappingInterface#getRealColumn(int, int)
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
						System.out.println("File View Delete: ");

						for (int i = firstRow; i <= lastRow; i++) {
							recNums[i - firstRow] =  lines.indexOf(baseFile.getLine(i));
							System.out.println(">" + i + " " + recNums[i - firstRow]);
						}
						
						deleteLinesFromView(recNums);
					}
				break;
				default:
					if (layout != baseFile.getLayout()) {
						setLayout(baseFile.getLayout());
						this.fireTableStructureChanged();
					} else {
						this.fireTableDataChanged();
					}
			}
        }
    }


    /**
     * Get the file name
     *
     * @return filename
     */
    public String getFileName() {
        return fileName;
    }


    /**
     * Get the file name
     *
     * @return filename
     */
    public String getFileNameNoDirectory() {
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
        lines.sort(compare);

        sortNotify();
    }
    
    /**
     * Sorts the file into the requested sequence
     * @param compare compare to use in the sort
     */
    public void sort(int[] rows, LineCompare compare) {
    	int i;
    	AbstractLine[] rowsToSort = new AbstractLine[rows.length];
    	
    	for (i = 0; i < rows.length; i++) {
    		rowsToSort[i] = lines.get(rows[i]);
    	}
    	
        Arrays.sort(rowsToSort, compare);
        
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
	/**
	 * @return the lines
	 */
	public final List<AbstractLine> getLines() {
		return lines;
	}

	public final List<AbstractLine> getLines(int[] lineNumbers) {
	    ArrayList<AbstractLine> returnLines = new ArrayList<AbstractLine>(lineNumbers.length);
	    int i;
	
	    for (i = 0; i < lineNumbers.length; i++) {
	        returnLines.add(lines.get(lineNumbers[i]));
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

	public void notifyOfChange(AbstractLine line) {
		if (baseFile.treeTableNotify == null) {
			invokeLaterDataChanged();
		} else {
			baseFile.fireRowUpdated(0, line);
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
	 
	private DataStore getDataStore(int lines, long bytes, AbstractLineReader reader, String fname) {
		if (useBigFileModel(bytes)) {
			LayoutDetail l = (LayoutDetail) layout;
			if (l.isXml()) {
			} else 	if (l.getFileStructure() == Constants.IO_UNICODE_TEXT
					||  l.getFileStructure() == Constants.IO_UNICODE_NAME_1ST_LINE) {
				System.out.println("Editing using Char-Line Big memory Model - this will reduce functionality !!!");
				//Common.logMsg(AbsSSLogger.WARNING,
				//		"\nEditing using Char-Line Big memory Model - this will reduce functionality !!!", null);
				return new DataStoreLarge(
						(LayoutDetail) layout, 
						FileDetails.CHAR_LINE, 
						100000);
			} else 	if (l.getFileStructure() == Constants.IO_UNKOWN_FORMAT
					||  l.getLayoutType()    == Constants.rtDelimited
					||  l.getFileStructure() == Constants.IO_NAME_1ST_LINE
					||  l.getFileStructure() == Constants.IO_GENERIC_CSV) {
				System.out.println("\nEditing using Big Variable Length memory Model - this will reduce functionality !!!");
//				Common.logMsg(AbsSSLogger.WARNING,
//						"\nEditing using Big Variable Length memory Model - this will reduce functionality !!!", null);
				return new DataStoreLarge(
						(LayoutDetail) layout, 
						FileDetails.VARIABLE_LENGTH, 
						100000);
			} else 	if (l.isFixedLength()) {
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
				AbstractByteReader r = null;
				
				char largeOpt = Common.OPTIONS.largeVbOption.get();
				if (reader != null && reader instanceof LineReaderWrapper
				&& l.getFileStructure() != Constants.IO_VB_DUMP
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
		return new DataStoreStd<AbstractLine>(layout, lines);
	}
	
	public boolean useBigFileModel(long bytes) {
//		System.out.println(
//				(layout instanceof LayoutDetail) 
//			  + " && " + bytes + " > " + Common.getMemoryCompare());
		return layout instanceof LayoutDetail && bytes > Common.getMemoryCompare();
	}

	/**
	 * @param recordIdx
	 * @return
	 * @see net.sf.RecordEditor.edit.file.FieldMapping#getFieldVisibility(int)
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
	
	
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.file.GetView#getFileView()
	 */
	@Override
	public FileView getFileView() {
		return this;
	}
}