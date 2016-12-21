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
/*  -------------------------------------------------------------------------
 *
 *            Sub-Project: RecordEditor's version of JRecord 
 *    
 *    Sub-Project purpose: Low-level IO and record translation  
 *                        code + Cobol Copybook Translation
 *    
 *                 Author: Bruce Martin
 *    
 *                License: GPL 2.1 or later
 *                
 *    Copyright (c) 2016, Bruce Martin, All Rights Reserved.
 *   
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU General Public License
 *    as published by the Free Software Foundation; either
 *    version 2.1 of the License, or (at your option) any later version.
 *   
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 * ------------------------------------------------------------------------ */
      
package net.sf.JRecord.IO;

import net.sf.JRecord.ByteIO.AbstractByteReader;
import net.sf.JRecord.ByteIO.AbstractByteWriter;
import net.sf.JRecord.ByteIO.ByteIOProvider;
import net.sf.JRecord.ByteIO.ByteTextReader;
import net.sf.JRecord.ByteIO.CsvByteReader;
import net.sf.JRecord.ByteIO.FixedLengthByteReader;
import net.sf.JRecord.ByteIO.IByteReader;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Common.IBasicFileSchema;
import net.sf.JRecord.Details.CharLineProvider;
import net.sf.JRecord.Details.DefaultLineProvider;
import net.sf.JRecord.Details.LineProvider;
import net.sf.JRecord.Details.XmlLineProvider;
import net.sf.JRecord.External.Def.BasicConversion;
import net.sf.JRecord.charIO.CsvCharReader;
import net.sf.JRecord.charIO.ICharReader;
import net.sf.JRecord.charIO.StandardCharReader;

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
public class StandardLineIOProvider extends BasicIoProvider {

	public int START_USER_FILE_STRUCTURES = 1000;
    //private static StandardLineIOProvider ioProvider = null;
    @SuppressWarnings("rawtypes")
	private LineProvider provider;
    private XmlLineProvider xmlProvider = null;
    private CharLineProvider charProvider = null;

//    private static final int numberOfEntries;
//    private static String[] names = new String [50] ;
//    private static String[] externalNames = new String [50] ;
//    private static int[] keys = new int[50];

//	@SuppressWarnings("rawtypes")
//	private BasicManager<LineProvider> lineProviderManager =
//    	new BasicManager<LineProvider>(100, START_USER_FILE_STRUCTURES, new LineProvider[200]) ;

//    public static final int[] FILE_STRUCTURE_ID = {
//    	Constants.IO_DEFAULT,
//    	Constants.IO_FIXED_LENGTH,
//    	Constants.IO_BINARY,
//    	Constants.IO_VB,
//    	Constants.IO_VB_DUMP,
//    	Constants.IO_VB_FUJITSU,
//    	Constants.IO_VB_GNU_COBOL,
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

 
//
//    /**
//     * create LineIOprovider class - This class returns IO-Routines
//     * appropriate for the Supplied File Structure with a LineProvider
//     *
//     * @param lineProvider lineProvider to use. Line providers
//     * create Lines.
//     */
//    public StandardLineIOProvider(@SuppressWarnings("rawtypes") final LineProvider lineProvider) {
//        super();
//
//        provider = lineProvider;
//        if (lineProvider == null) {
//            provider = new DefaultLineProvider();
//        }
//    }
//

    /**
     * create LineIOprovider class - This class returns IO-Routines
     * appropriate for the Supplied File Structure.
     */
    public StandardLineIOProvider() {
        super();
        provider = new DefaultLineProvider();
    }

    /**
	 * @see net.sf.JRecord.Common.AbstractManager#getManagerName()
	 */
	@Override
	public String getManagerName() {
		return "StdLine_IO_Names";
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
        return getLineReader(fileStructure, getLineProvider(fileStructure, null));
    }


    /* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#getLineReader(net.sf.JRecord.Common.IBasicFileSchema, net.sf.JRecord.Details.LineProvider)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public AbstractLineReader getLineReader(IBasicFileSchema schema,
			LineProvider lineProvider) {
			return getLineReader(schema.getFileStructure(), schema, lineProvider);
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#getLineReader(int, net.sf.JRecord.Common.IBasicFileSchema, net.sf.JRecord.Details.LineProvider)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public AbstractLineReader getLineReader(int fileStructure,
			IBasicFileSchema schema, LineProvider lineProvider) {

		switch (fileStructure) {
		case Constants.IO_BIN_TEXT:		return new LineReaderWrapper(new ByteTextReader(schema.getFontName()));
//       	case Constants.IO_BIN_TEXT:		return new BinTextReader(lLineProvider,  false);
       	case Constants.IO_BIN_NAME_1ST_LINE:
       									return new BinTextReader(lineProvider,  true, new ByteTextReader(schema.getFontName()));
		case Constants.IO_CSV:			return new LineReaderWrapper(getCsvReader(schema, false));
		case Constants.IO_UNICODE_CSV:	return new TextLineReader(lineProvider, false, getCsvCharReader(schema, false));
		case Constants.IO_BIN_CSV:		return new BinTextReader(lineProvider,  false, getCsvReader(schema, false));

		case Constants.IO_UNICODE_CSV_NAME_1ST_LINE:
			return  new TextLineReader(lineProvider, true, getCsvCharReader(schema, true));
		case Constants.IO_CSV_NAME_1ST_LINE:
		case Constants.IO_BIN_CSV_NAME_1ST_LINE:
			return new BinTextReader(lineProvider,  true, getCsvReader(schema, true));
		case Constants.IO_FIXED_LENGTH: new LineReaderWrapper(new FixedLengthByteReader(schema.getMaximumRecordLength()));
		default:
			AbstractByteReader byteReader
				= ByteIOProvider.getInstance().getByteReader(schema);

			if (byteReader != null) {
				return new LineReaderWrapper(lineProvider, byteReader);
			}

			return getLineReader(fileStructure, lineProvider);
		}
	}

	private static IByteReader getCsvReader(IBasicFileSchema schema, boolean namesOnFirstLine) {
		String quote = schema.getQuote();
		if (quote == null || quote.length() == 0) {
			return new ByteTextReader(schema.getFontName());
		}
		return new CsvByteReader(schema.getFontName(), schema.getDelimiter(), quote, quote + quote, namesOnFirstLine);
	}

	private static ICharReader getCsvCharReader(IBasicFileSchema schema, boolean namesOnFirstLine) {
		String quote = schema.getQuote();
		if (quote == null || quote.length() == 0) {
			return new StandardCharReader();
		}
		return new CsvCharReader(schema.getDelimiter(), quote, quote + quote, namesOnFirstLine);
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
    	case Constants.IO_CONTINOUS_NO_LINE_MARKER:	return new ContinuousLineReader(lLineProvider);
    	case Constants.IO_BINARY_IBM_4680:			return new Binary4680LineReader(lLineProvider);

		case Constants.IO_FIXED_LENGTH_CHAR:		return new FixedLengthTextReader(lLineProvider);

    	case Constants.IO_XML_BUILD_LAYOUT:
       	case Constants.IO_XML_USE_LAYOUT:			return new XmlLineReader(fileStructure == Constants.IO_XML_BUILD_LAYOUT);

       	case Constants.IO_BIN_TEXT:					return new BinTextReader(lLineProvider, false);
       	case Constants.IO_BIN_NAME_1ST_LINE:		return new BinTextReader(lLineProvider, true);
       	
       	case Constants.IO_NAME_1ST_LINE:
       	case Constants.IO_CSV_NAME_1ST_LINE:
      	case Constants.IO_UNICODE_NAME_1ST_LINE:	return new TextLineReader(lLineProvider, true);
      	case Constants.IO_UNICODE_TEXT:				return new TextLineReader(lLineProvider, false);
      	default:
            @SuppressWarnings("deprecation")
			AbstractByteReader byteReader
        			= ByteIOProvider.getInstance().getByteReader(fileStructure);

	        if (byteReader != null) {
	            return new LineReaderWrapper(lLineProvider, byteReader);
	        }
	 	}

        return new TextLineReader(lLineProvider, false);
    }


    /* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#getLineWriter(int)
	 */
    public AbstractLineWriter getLineWriter(int fileStructure) {
    	return getLineWriter(fileStructure, null);
    }


    /* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#getLineWriter(int)
	 */
    public AbstractLineWriter getLineWriter(int fileStructure, String charset) {

    	switch (fileStructure) {
    	case Constants.IO_CONTINOUS_NO_LINE_MARKER:	return new ContinuousLineWriter();
    	case Constants.IO_BINARY_IBM_4680:			return BinaryLineWriter.newBinary4680Writer();
    	case Constants.IO_FIXED_LENGTH:				return new FixedLengthLineWriter();

      	case Constants.IO_FIXED_LENGTH_CHAR:		return new LineWriterWrapperChar(fileStructure);
    	case Constants.IO_XML_BUILD_LAYOUT:
       	case Constants.IO_XML_USE_LAYOUT:			return new XmlLineWriter();

       	case Constants.IO_CSV:
       	case Constants.IO_BIN_CSV:
       	case Constants.IO_BIN_TEXT:					return new BinTextWriter(false);
       	case Constants.IO_CSV_NAME_1ST_LINE:
       	case Constants.IO_BIN_CSV_NAME_1ST_LINE:
       	case Constants.IO_BIN_NAME_1ST_LINE:		return new BinTextWriter(true);
       	case Constants.IO_UNICODE_TEXT:
       	case Constants.IO_UNICODE_CSV:				return new TextLineWriter(false);
       	case Constants.IO_NAME_1ST_LINE:
       	case Constants.IO_UNICODE_CSV_NAME_1ST_LINE:
       	case Constants.IO_UNICODE_NAME_1ST_LINE:	return new TextLineWriter(true);
      	case Constants.IO_MICROFOCUS:				return new MicroFocusLineWriter();

      	default:
            AbstractByteWriter byteWriter = ByteIOProvider.getInstance().getByteWriter(fileStructure, charset);

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
    	    	|| fileStructure == Constants.IO_UNICODE_CSV_NAME_1ST_LINE
    			|| fileStructure == Constants.IO_UNICODE_NAME_1ST_LINE
    			|| fileStructure == Constants.IO_CSV_NAME_1ST_LINE);
    }


    /* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#getStructureName(int)
	 */ @Override
    public String getStructureName(int fileStructure) {
		 return BasicConversion.getStructureName(fileStructure);
    }


    @Override
	public String getStructureNameForIndex(int index) {
		return BasicConversion.getStructureNameForIndex(index);
	}


	/**
     * Convert a structure-name to a file-Structure identifier
     * @param name Name of the File Structure
     * @return The file Structure
     */
    public int getStructure(String name) {
    	return BasicConversion.getStructure(name);
    }


    /* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#getLineProvider(int,String)
	 */
    @Override @SuppressWarnings("rawtypes")
	public LineProvider getLineProvider(int fileStructure, String charset) {
       	return getLineProvider(fileStructure, charset, false);
    }
        
    @Override @SuppressWarnings("rawtypes")
    protected LineProvider getLineProvider(int fileStructure, String charset, boolean binary) {

//    	LineProvider temp = lineProviderManager.get(fileStructure);
//    	if (temp != null) {
//    		return temp;
//    	}
    	
//    	System.out.print("Get Line provider " + fileStructure + " " + charset + " " + binary + " " + Conversion.isMultiByte(charset));
    	switch (fileStructure) {
    	case Constants.IO_XML_BUILD_LAYOUT:
    	case Constants.IO_XML_USE_LAYOUT:
       		if (xmlProvider == null) {
       			xmlProvider = new XmlLineProvider();
       		}
       		return xmlProvider;
    	case Constants.IO_FIXED_LENGTH_CHAR:
    	case Constants.IO_UNICODE_CSV:
    	case Constants.IO_UNICODE_CSV_NAME_1ST_LINE:
    	case Constants.IO_UNICODE_NAME_1ST_LINE:
    	case Constants.IO_UNICODE_TEXT:
//    		System.out.println(" Char provider 1");
    		return getCharProvider();
       	}
    	
    	if (charset != null && (! binary) && Conversion.isMultiByte(charset)) {
//    		System.out.println(" Char provider 2");
    		return getCharProvider();
    	}

//    	System.out.println(" normal provider");
        return provider;
    }

    @SuppressWarnings("rawtypes")
	private LineProvider getCharProvider() {
   		if (charProvider == null) {
   			charProvider = new CharLineProvider();
   		}

		return charProvider;
 	
    }

    /* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#getKey(int)
	 */
	@Override
	public int getKey(int idx) {
		return BasicConversion.getFileStructureForIndex(idx);
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineIOProvider#getName(int)
	 */
	@Override
	public String getName(int idx) {
		return BasicConversion.getFileStructureNameForIndex(idx);
	}


	/**
	 * @see net.sf.JRecord.Common.AbstractManager#getNumberOfEntries()
	 */
	@Override
	public int getNumberOfEntries() {
		return BasicConversion.getNumberOfFileStructures();
	}
}
