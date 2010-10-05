/*
 * @Author Bruce Martin
 * Created on 10/09/2005
 *
 * Purpose: Provide examples of
 *
 *    LineProvider,  LineIOProvider, AbsLineReader -etc
 *
 * Requirements:
 *
 * 1) Check the values in Constants.java are correct !!!
 *
 */
package net.sf.RecordEditor.examples;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.LineProvider;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.StandardLineReader;
import net.sf.JRecord.IO.LineIOProvider;

/**
 *
 * Purpose: Provide examples for using
 *
 *    LineProvider - LineProviders (DTAR0020provider) creates a
 *                   Line (or extension of Line). If you want to use your
 *                   own line in the record editor, you must pass a
 *                   LineProvider (which will create your line rather than
 * 					 the RecordEditor's Line).
 *
 *  LineIOProvider - LineIOProvider will return a LineReader / LineWrite
 *                   appropriate to the File structure (avaiable via
 *                   LayoutDetails.getFileStructure()
 *
 *   AbsLineReader - Line-Readers are used to read "Line's" from a file
 *                   System defined Line-Readers are:
 *                   * TextLineReader - Read Text files
 *                   * FixedLengthLineReader - read fixed record length binary
 *                     files.
 *                   * BinaryLineReader - reads a binary file with the
 *                     the Line class calculating record lengths
 *
 *   AbsLineWriter - Line-Writers write "Line's" to a file
 *                   System defined Line-Readers are:
 *                   * TextLineWriter   writes "Line's" to a text   file
 *                   * BinaryLineWriter writes "Line's" to a binary file
 *
 *            Line - Extending the Line class and using these extended
 *                   classes
 *
 * @Author Bruce Martin
 * Created on 10/09/2005
 */
public final class XmplLineIO3 {

    private static final double GST_CONVERSION
    								   = 1.1;
    private String installDir          = TstConstants.RECORDEDIT_INSTALL_DIRECTORY;
    private String salesFile           = installDir + "SampleFiles\\DTAR020.bin";

    private CobolCopybookReader copybook  = new CobolCopybookReader();
    private LayoutDetail salesCopyBook = copybook.getLayout("DTAR020");
    private int         fileStructure  = salesCopyBook.getFileStructure();

    private LineIOProvider ioProvider  = LineIOProvider.getInstance();

    private AbstractLineReader reader       = ioProvider.getLineReader(fileStructure,
            													  new DTAR0020provider());

    /**
     * Example of LineReader / LineWrite classes
     */
    private XmplLineIO3() {
        super();

        int lineNum = 0;
        double gstExclusive;
        LineDTAR0020 saleRecord;

        try {
            reader.open(salesFile, salesCopyBook);

            while ((saleRecord = (LineDTAR0020) reader.read()) != null) {
                lineNum += 1;

                System.out.print(saleRecord.getKeycode()
                        + "\t" + saleRecord.getQuantity()
                        + "\t" + saleRecord.getSalesRetail());

                gstExclusive = saleRecord.getSalesRetail() / GST_CONVERSION;
                saleRecord.setSalesRetail(gstExclusive);

                System.out.println("\t" + saleRecord.getSalesRetail());
            }

            reader.close();
        } catch (Exception e) {
            System.out.println("~~> " + lineNum + " " + e.getMessage());
            System.out.println();

            e.printStackTrace();
        }
    }



    /**
     * Create line provider for DTAR0020lines
     *
     *
     * @author Bruce Martin
     *
     */
    private class DTAR0020provider implements LineProvider<LayoutDetail> {

        /**
         * @see net.sf.JRecord.Details.LineProvider#getLine
         */
        public AbstractLine getLine(LayoutDetail recordDescription) {
            return new LineDTAR0020(recordDescription);
        }

        /**
         * @see net.sf.JRecord.Details.LineProvider#getLine
         */
        public AbstractLine getLine(LayoutDetail recordDescription, byte[] lineBytes) {
            return new LineDTAR0020(recordDescription, lineBytes);
        }

        /**
         * @see net.sf.JRecord.Details.LineProvider#getLine
         */
        public AbstractLine getLine(LayoutDetail recordDescription, String linesText) {
            return new LineDTAR0020(recordDescription, linesText);
        }
    }

    /**
     * LineIO example 2
     *
     * @param args program arguments
     */
    public static void main(String[] args) {
        new XmplLineIO3();
    }
}
