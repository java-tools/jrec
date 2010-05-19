/*
 * @Author Bruce Martin
 * Created on 12/10/2005
 *
 * Purpose:
 * Example of using VariableLengthLineReader
*
 * Requirements:
 *
 * 1) Check the values in Constants.java are correct !!!
 *
 */
package net.sf.RecordEditor.examples;

import java.io.IOException;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.RecordEditor.utils.CopyBookDbReader;


/**
 * Example of using BinaryVbLineReader
 *
 * @author Bruce Martin
 *
 */
public final class XmplLineIoVLR1 {

    private String dtar10000file    = TstConstants.RECORDEDIT_INSTALL_DIRECTORY
    								+ "SampleFiles\\DTAR1000_Store_file_std.bin";
   // private String dtar10000file2    = Constants.RECORDEDIT_INSTALL_DIRECTORY
//									+ "SampleFiles\\DTAR1000_Store_file_std_recfm_U.bin";
    private String dtar10000file2   = TstConstants.RECORDEDIT_INSTALL_DIRECTORY
	 								+ "SampleFiles\\DTAR1000_Store_file_large_Recfm_U.bin";

    private CopyBookDbReader copybook
                            		= new CopyBookDbReader();
    private LayoutDetail dtar1000Layout
    								= copybook.getLayout("DTAR1000 VB");


    private FieldDetail fldStore   = dtar1000Layout.getFieldFromName("STORE-NO");
    private FieldDetail fldRegion  = dtar1000Layout.getFieldFromName("REGION-NO");
    private FieldDetail fldStrName = dtar1000Layout.getFieldFromName("STORE-NAME");

    private AbstractLineReader reader
    	= LineIOProvider.getInstance().getLineReader(Constants.IO_VB);

    private AbstractLine line;

    /**
     * Example of using BinaryVbLineReader
     */
    private XmplLineIoVLR1() {
        super();

        try {
            reader.open(dtar10000file, dtar1000Layout);
            readFile(reader, "Std");

            System.out.println();

            reader   = LineIOProvider.getInstance()
            				.getLineReader(Constants.IO_VB_DUMP);
            reader.open(dtar10000file2, dtar1000Layout);

            readFile(reader, "recf U");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * reads a file and writes selected fields to standard out
     * @param input reader to read from
     * @param id unique Id supplied by the calling program
     *
     * @throws IOException any IO error that occurs
     */
    private void readFile(AbstractLineReader input, String id) throws IOException {
        int lineNumber = 0;

        System.out.println();
        while ((line = input.read()) != null) {
            lineNumber += 1;
            System.out.println(
            		id + "\t"
                  + lineNumber + "\t"
                  + line.getFieldValue(fldRegion).asString() + "\t"
                  + line.getFieldValue(fldStore).asString()  + "\t"
                  + line.getFieldValue(fldStrName).asString()
            );
        }
        input.close();
    }

    /**
     * LineIO example 2
     *
     * @param args program arguments
     */
    public static void main(String[] args) {
        new XmplLineIoVLR1();
    }

}
