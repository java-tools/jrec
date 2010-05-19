/*
 * @Author Bruce Martin
 * Created on 14/04/2005
 *
 * Purpose:
 */
package net.sf.JRecord.External;

import java.io.File;

import org.w3c.dom.Document;


import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.External.Cb2Xml;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.Log.AbsSSLogger;
//Modified to use the Record-Editor version of class Cb2Xml JFG

/**
 * This class holds routines to load a Cobol Copybook
 * into ExternalRecord.
 *
 * @author Bruce Martin
 *
 */
public class CobolCopybookLoader implements CopybookLoader {

    private static boolean available = true;
    private static boolean toCheck = true;

    private XmlCopybookLoader xmlLoader;

    /**
     * Create Cobol Copybook loader
     */
    public CobolCopybookLoader() {
        this(new XmlCopybookLoader());
    }

    /**
     * Create Cobol Copybook loader
     * @param loader4xml class to load a Cb2Xml XML file
     */
    public CobolCopybookLoader(XmlCopybookLoader loader4xml) {
        super();

        xmlLoader = loader4xml;
       // System.out.println("Cobol Copybook loader");
    }

    /**
     * Insert a XML Dom Copybook into the Copybook DB
     *
     * @param copyBookFile Copy Book file Name
     * @param splitCopybook wether to split a copy book on a redefine / 01
     * @param dbIdx Database Index
     * @param font font name to use
     * @param binFormat binary format to use
     * @param systemId System Identifier
     * @param log log where any messages should be written
     *
     * @return return the record that has been read in
     * @throws RecordException General Error
     */
    public final ExternalRecord loadCopyBook(String copyBookFile, //Document copyBookXml,
            						  		int splitCopybook,
            						  		int dbIdx,
                  						  final String font,
                						  final int binFormat,
                						  final int systemId,
                						  final AbsSSLogger log)
    				throws RecordException {
        ExternalRecord ret = null;
        //System.out.println("load Copybook (Cobol)");
        try {
            Document xml = Cb2Xml.convertToXMLDOM(new File(copyBookFile), binFormat, log);
            String copyBook = Conversion.getCopyBookId(copyBookFile);

            if (xml != null) {
	            ret = xmlLoader.loadDOMCopyBook(xml, copyBook,
	                    splitCopybook, dbIdx,
					    font, binFormat, systemId);
	        } else {
            	log.logMsg(AbsSSLogger.ERROR, "Error parsing Cobol File ???");
            }
        } catch (Exception e) {
            log.logMsg(AbsSSLogger.ERROR, e.getMessage());
            log.logException(AbsSSLogger.ERROR, e);
            e.printStackTrace();
            throw new RecordException("problem loading Copybook: " + copyBookFile
                    		  + "    Message: " + e.getMessage());
        }

        return ret;
    }


    /**
     * wether cb2xml is available (needed for converting a Cobol Copybook
     * to a XML Dom representation
     *
     * @return wether cb2xml is available on the class path
     */
    public static final boolean isAvailable() {

        if (toCheck) {
            try {
                /*
                 * try to load CobolPreprocessor to see if the cb2xml jar is present
                 * I use the CobolPreprocessor because it only uses IO classes.
                 * This aviods loading unnessary classes before need be
                 */
                available = ((new CobolCopybookLoader()).getClass().getClassLoader().getResource("net/sf/cb2xml/Cb2Xml.class") != null);
            } catch (Exception e) {
                available = false;
            }
            toCheck = false;
        }
        return available;
    }
}
