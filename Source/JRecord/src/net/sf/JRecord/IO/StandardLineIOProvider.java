/*
 * @Author Bruce Martin
 * Created on 29/08/2005
 *
 * Purpose:
 *
 * Modification log:
 * On 2006/06/28 by Jean-Francois Gagnon:
 *    - Added a Fujitsu Variable Length Line IO Provider
 *
 * # Version 0.60 Bruce Martin 2007/02/16
 *   - Started work on seperating Record section out, so removing
 *     all reference to the Common module and used a new Constants
 *     module
 */
package net.sf.JRecord.IO;

import net.sf.JRecord.ByteIO.AbstractByteReader;
import net.sf.JRecord.ByteIO.AbstractByteWriter;
import net.sf.JRecord.ByteIO.ByteIOProvider;
import net.sf.JRecord.Common.AbstractManager;
import net.sf.JRecord.Common.BasicManager;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Details.CharLineProvider;
import net.sf.JRecord.Details.DefaultLineProvider;
import net.sf.JRecord.Details.LineProvider;
import net.sf.JRecord.Details.XmlLineProvider;

/**
 * LineIOprovider - This class returns a LineIO class appropriate for
 * the supplied file structure. All the Files Structures are available as
 * Constants.IO_*
 *
 * <pre>
 * <b>Usage:</b>
 *
 *         CopybookLoader loader = <font color="brown"><b>new</b></font> RecordEditorXmlLoader();
 *         ExternalRecord extlayout = loader.loadCopyBook(copybookName, 0, 0, "", 0, 0, <font color="brown"><b>null</b></font>);
 *
 *         LayoutDetail layout = extlayout.asLayoutDetail();
 *         AbstractLineWriter writer = <b>LineIOProvider.getInstance()</b>.getLineWriter(layout.getFileStructure());
 * </pre>
 *
 * @author Bruce Martin
 * @version 0.55
 *
 */
public class StandardLineIOProvider implements AbstractManager, AbstractLineIOProvider {

	public int START_USER_FILE_STRUCTURES = 1000;
    //private static StandardLineIOProvider ioProvider = null;
    @SuppressWarnings("rawtypes")
	private LineProvider provider;
    private XmlLineProvider xmlProvider = null;
    private CharLineProvider charProvider = null;

    private static final int numberOfEntries;
    private static String[] names = new String [20] ;
    private static String[] externalNames = new String [20] ;
    private static int[] keys = new int[20];

    @SuppressWarnings("rawtypes")
	private BasicManager<LineProvider> lineProviderManager =
    	new BasicManager<LineProvider>(100, START_USER_FILE_STRUCTURES, new LineProvider[200]) ;

//    public static final int[] FILE_STRUCTURE_ID = {
//    	Constants.IO_DEFAULT,
//    	Constants.IO_FIXED_LENGTH,
//    	Constants.IO_BINARY,
//    	Constants.IO_VB,
//    	Constants.IO_VB_DUMP,
//    	Constants.IO_VB_FUJITSU,
//    	Constants.IO_VB_OPEN_COBOL,
//    	Constants.IO_NAME_1ST_LINE,
//    	Constants.IO_GENERIC_CSV,
//    	Constants.IO_XML_USE_LAYOUT,
//    	Constants.IO_XML_BUILD_LAYOUT};
//    public static final String[] FILE_STRUCTURE_NAME;
//	keys[i] = Constants.IO_NAME_1ST_LINE;			externalNames[i] = "CSV_NAME_1ST_LINE";  names[i++] = "Delimited, names first line";
//	keys[i] = Constants.IO_GENERIC_CSV;				externalNames[i] = "CSV_GENERIC";			names[i++] = "Generic CSV (Choose details at run time)";
//	keys[i] = Constants.IO_XML_USE_LAYOUT;		externalNames[i] = "XML_Use_Layout";       	names[i++] = "XML - Existing Layout";
//	keys[i] = Constants.IO_XML_BUILD_LAYOUT;		externalNames[i] = "XML_Build_Layout";      names[i++] = "XML - Build Layout";
//	keys[i] = Constants.NULL_INTEGER;					externalNames[i] = null;                    			names[i++] = null;

   static {
	   String rdDefault = "Default";
	   String rdFixed = "Fixed Length Binary";
	   String rdLineBin = "Line based Binary";
	   String rdVb = "Mainframe VB (rdw based) Binary";
	   String rdVbDump = "Mainframe VB Dump: includes Block length";
	   String rdOcVb = "Open Cobol VB";


	   int i = 0;

	   keys[i] = Constants.IO_DEFAULT;				externalNames[i] = "Default";               names[i++] = rdDefault;
	   keys[i] = Constants.IO_TEXT_LINE;			externalNames[i] = "Text";                  names[i++] = "Text IO";
	   keys[i] = Constants.IO_BIN_TEXT;				externalNames[i] = "Byte_Text";             names[i++] = "Text IO (byte Based)";
	   keys[i] = Constants.IO_UNICODE_TEXT;			externalNames[i] = "Text_Unicode";          names[i++] = "Text IO (Unicode)";
	   keys[i] = Constants.IO_FIXED_LENGTH;			externalNames[i] = "Fixed_Length";          names[i++] = rdFixed;
	   keys[i] = Constants.IO_BINARY; 				externalNames[i] = "Binary";                names[i++] = rdLineBin;
	   keys[i] = Constants.IO_VB;					externalNames[i] = "Mainframe_VB";          names[i++] = rdVb;
	   keys[i] = Constants.IO_VB_DUMP;				externalNames[i] = "Mainframe_VB_As_RECFMU";names[i++] = rdVbDump;
	   keys[i] = Constants.IO_VB_FUJITSU;			externalNames[i] = "FUJITSU_VB";            names[i++] = "Fujitsu Variable Binary";
	   keys[i] = Constants.IO_VB_OPEN_COBOL;		externalNames[i] = "Open_Cobol_VB";      	names[i++] = rdOcVb;
	   keys[i] = Constants.IO_MICROFOCUS;			externalNames[i] = "Microfocus_Format";    	names[i++] = "Experemental Microfocus Header File";
	   keys[i] = Constants.IO_UNKOWN_FORMAT;		externalNames[i] = "UNKOWN_FORMAT";      	names[i++] = "Unknown File Format";
	   keys[i] = Constants.IO_WIZARD;		        externalNames[i] = "FILE_WIZARD";		  	names[i++] = "File Wizard";
	   keys[i] = Constants.IO_NAME_1ST_LINE;		externalNames[i] = "CSV_NAME_1ST_LINE";  	names[i++] = "Csv Name on 1st line";
	   keys[i] = Constants.IO_UNICODE_NAME_1ST_LINE;externalNames[i] = "CSV_NAME_1ST_LINE";  	names[i++] = "Unicode Name on 1st line";
	   //		keys[i] = Constants.IO_GENERIC_CSV;			externalNames[i] = "CSV_GENERIC";			names[i++] = "Generic CSV (Choose details at run time)";
	   keys[i] = Constants.IO_XML_USE_LAYOUT;		externalNames[i] = "XML_Use_Layout";       	names[i++] = "XML - Existing Layout";
	   keys[i] = Constants.IO_XML_BUILD_LAYOUT;		externalNames[i] = "XML_Build_Layout";      names[i++] = "XML - Build Layout";
	   keys[i] = Constants.NULL_INTEGER;			externalNames[i] = null;               		names[i] = null;

	   numberOfEntries = i;
    }



    /**
     * create LineIOprovider class - This class returns IO-Routines
     * appropriate for the Supplied File Structure with a LineProvider
     *
     * @param lineProvider lineProvider to use. Line providers
     * create Lines.
     */
    public StandardLineIOProvider(@SuppressWarnings("rawtypes") final LineProvider lineProvider) {
        super();

        provider = lineProvider;
        if (lineProvider == null) {
            provider = new DefaultLineProvider();
        }
    }


    /**
     * create LineIOprovider class - This class returns IO-Routines
     * appropriate for the Supplied File Structure.
     */
    protected StandardLineIOProvider() {
        super();
        provider = new DefaultLineProvider();
    }


    /**
     * Gets a Record Reader Class that is appropriate for reading the
     * supplied file-structure
     *
     * @param fileStructure File Structure of the required reader
     *
     * @return line reader
     */
    @SuppressWarnings("rawtypes")
	public AbstractLineReader getLineReader(int fileStructure) {
        return getLineReader(fileStructure, provider);
    }


    /* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#getLineReader(int, net.sf.JRecord.Details.LineProvider)
	 */
    @SuppressWarnings("rawtypes")
	public AbstractLineReader getLineReader(int fileStructure,
            						   LineProvider lineProvider) {
        LineProvider lLineProvider = lineProvider;

        if (lLineProvider == null) {
            lLineProvider = provider;
        }

       	switch (fileStructure) {
    	case(Constants.IO_BINARY):					return new BinaryLineReader(lLineProvider);

    	case(Constants.IO_XML_BUILD_LAYOUT):
       	case(Constants.IO_XML_USE_LAYOUT):			return new XmlLineReader(fileStructure == Constants.IO_XML_BUILD_LAYOUT);

       	case(Constants.IO_BIN_TEXT):				return new BinTextReader(lLineProvider,  false);
       	case(Constants.IO_BIN_NAME_1ST_LINE):		return new BinTextReader(lLineProvider,  true);
       	case(Constants.IO_UNICODE_TEXT):			return new TextLineReader(lLineProvider, false);
       	case(Constants.IO_UNICODE_NAME_1ST_LINE):	return new TextLineReader(lLineProvider, true);
      	default:
            AbstractByteReader byteReader
        	= ByteIOProvider.getInstance().getByteReader(fileStructure);

	        if (byteReader != null) {
	            return new LineReaderWrapper(lLineProvider, byteReader);
	        }
	 	}

        return new TextLineReader(lLineProvider,
                				  fileStructure == Constants.IO_NAME_1ST_LINE);
    }


    /* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#getLineWriter(int)
	 */
    public AbstractLineWriter getLineWriter(int fileStructure) {

    	switch (fileStructure) {
    	case(Constants.IO_BINARY):
    	case(Constants.IO_FIXED_LENGTH):			return new BinaryLineWriter();

    	case(Constants.IO_XML_BUILD_LAYOUT):
       	case(Constants.IO_XML_USE_LAYOUT):			return new XmlLineWriter();

       	case(Constants.IO_BIN_TEXT):				return new BinTextWriter(false);
       	case(Constants.IO_BIN_NAME_1ST_LINE):		return new BinTextWriter(true);
       	case(Constants.IO_UNICODE_NAME_1ST_LINE):	return new TextLineWriter(true);
      	case(Constants.IO_MICROFOCUS):				return new MicroFocusLineWriter();
      	default:
            AbstractByteWriter byteWriter = ByteIOProvider.getInstance().getByteWriter(fileStructure);

	        if (byteWriter != null) {
	            return new LineWriterWrapper(byteWriter);
	        }
	 	}

        return new TextLineWriter(fileStructure == Constants.IO_NAME_1ST_LINE);
    }


    /* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#isCopyBookFileRequired(int)
	 */ @Override
    public boolean isCopyBookFileRequired(int fileStructure) {
    	return ! ( fileStructure == Constants.IO_XML_BUILD_LAYOUT
    			|| fileStructure == Constants.IO_GENERIC_CSV
    			|| fileStructure == Constants.IO_NAME_1ST_LINE
    			|| fileStructure == Constants.IO_UNICODE_NAME_1ST_LINE);
    }


    /* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#getStructureName(int)
	 */ @Override
    public String getStructureName(int fileStructure) {
    	for (int i = 0; i < keys.length && keys[i] != Constants.NULL_INTEGER; i++) {
    		if (keys[i] == fileStructure) {
    			return externalNames[i];
    		}
    	}
    	return "";
    }


    @Override
	public String getStructureNameForIndex(int index) {
		return externalNames[index];
	}


	/**
     * Convert a structure-name to a file-Structure identifier
     * @param name Name of the File Structure
     * @return The file Structure
     */
    public int getStructure(String name) {
    	for (int i = 0; i < keys.length && keys[i] != Constants.NULL_INTEGER; i++) {
    		if (externalNames[i].equalsIgnoreCase(name)) {
    			//System.out.println(" ~~~ getStructure ~ " +  externalNames[i] + " " + keys[i]);
    			return keys[i];
    		}
    	}
    	return Constants.NULL_INTEGER;
    }


    /* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#getLineProvider(int)
	 */
    @SuppressWarnings("rawtypes")
	public LineProvider getLineProvider(int fileStructure) {

    	LineProvider temp = lineProviderManager.get(fileStructure);
    	if (temp != null) {
    		return temp;
    	}
    	switch (fileStructure) {
    	case Constants.IO_XML_BUILD_LAYOUT:
    	case Constants.IO_XML_USE_LAYOUT:
       		if (xmlProvider == null) {
       			xmlProvider = new XmlLineProvider();
       		}
       		return xmlProvider;
    	case Constants.IO_UNICODE_NAME_1ST_LINE:
    	case Constants.IO_UNICODE_TEXT:
    		if (charProvider == null) {
       			charProvider = new CharLineProvider();
       		}

    		return charProvider;
       	}

        return provider;

    }


    /* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#getKey(int)
	 */
	@Override
	public int getKey(int idx) {
		return keys[idx];
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#getName(int)
	 */
	@Override
	public String getName(int idx) {
		return names[idx];
	}


	/**
	 * @see net.sf.JRecord.Common.AbstractManager#getNumberOfEntries()
	 */
	@Override
	public int getNumberOfEntries() {
		return numberOfEntries;
	}
}
