/*
 * @Author Bruce Martin
 * Created on 19/03/2007
 *
 * Purpose:
 */
package net.sf.JRecord.IO;

import java.io.IOException;

import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.LineProvider;
import net.sf.JRecord.External.CobolCopybookLoader;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.External.ToLayoutDetail;
import net.sf.JRecord.Numeric.Convert;


/**
 * This Class Creates a line-reader or line-writer for a Cobol file (Cobol Copybook).
 *
 * <pre>
 * <b>Usage:</b>
 *   
 *        CobolIoProvider ioProvider = CobolIoProvider.getInstance();
 *
 *        try {
 *             AbstractLineReader reader  = ioProvider.getLineReader(
 *                 Constants.IO_TEXT_LINE, Convert.FMT_INTEL,
 *                 CopybookLoader.SPLIT_NONE, copybookName, vendorFile
 *             );
 * </pre>
 * 
 * @author Bruce Martin
 *
 */
public class CobolIoProvider {

    private static CobolIoProvider instance = new CobolIoProvider();
    private CopybookLoader copybookInt = new CobolCopybookLoader();


    /**
     * Creates a line reader for a Cobol file
     * 
     * @param fileStructure Structure of the input file
     * @param numericType Numeric Format data (options include mainframe, Fujitu, PC compiler etc)
     * @param splitOption Option to split the copybook up <ul>
     *  <li>No Split
     *  <li>Split on  redefine
     *  <li>Split on 01 level
     * </ul>
     * @param copybookName Copybook (or Layout) name
     * @param filename input file name
     * @return requested Line Reader
     * @throws Exception
     */
    public AbstractLineReader getLineReader(int fileStructure,
			   int numericType, int splitOption,
			   String copybookName, String filename)
    throws Exception {
        return getLineReader(fileStructure,
 			   numericType, splitOption,
			   copybookName, filename,
			   LineIOProvider.getInstance().getLineProvider(fileStructure));
    }

    /**
     * Creates a line reader for a Cobol file
     * 
     * @param fileStructure Structure of the input file
     * @param numericType Numeric Format data (is mainframe, Fujitu PC compiler etc)
     * @param splitOption Option to split the copybook up <ul>
     *  <li>No Split
     *  <li>Split on  redefine
     *  <li>Split on 01 level
     * </ul>
     * @param copybookName Copybook (or Layout) name
     * @param filename input file name
     * @param provider line provider (to build your own lines)
     * @return requested Line Reader
     * @throws Exception
     */
    public AbstractLineReader getLineReader(int fileStructure,
 			   int numericType, int splitOption,
 			   String copybookName, String filename,
 			   LineProvider<LayoutDetail> provider)
     throws Exception {
    	AbstractLineReader ret;
        String font = "";
        if (numericType == Convert.FMT_MAINFRAME) {
            font = "cp037";
        }
       	LayoutDetail copyBook = ToLayoutDetail.getInstance().getLayout(
       	     copybookInt.loadCopyBook(
                        copybookName,
                        splitOption, 0, font,
                        numericType, 0, null
                ));

       	ret = LineIOProvider.getInstance()
       				.getLineReader(fileStructure, provider);
       	ret.open(filename, copyBook);

       	return ret;

    }


    /**
     * Create a line writer for a Cobol File
     * 
     * @param fileStructure structure of the output file
     * @return Line writer for the file
     */
    public AbstractLineWriter getLineWriter(int fileStructure, String outputFileName)
    throws IOException {
        AbstractLineWriter ret = LineIOProvider.getInstance()
        			.getLineWriter(fileStructure);
        ret.open(outputFileName);
        return ret;
    }


    /**
     * Get a CobolIoProvider
     * 
     * @return Returns the instance.
     */
    public static CobolIoProvider getInstance() {
        return instance;
    }
}
