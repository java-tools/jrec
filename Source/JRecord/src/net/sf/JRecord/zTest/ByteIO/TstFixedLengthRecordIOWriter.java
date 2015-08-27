/*
 * @Author Bruce Martin
 * Created on 28/08/2005
 *
 * Purpose:
 */
package net.sf.JRecord.zTest.ByteIO;

import java.io.IOException;
import java.util.Arrays;

import junit.framework.TestCase;
import net.sf.JRecord.ByteIO.FixedLengthByteReader;
import net.sf.JRecord.ByteIO.FixedLengthByteWriter;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.External.CobolCopybookLoader;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.External.ToLayoutDetail;
import net.sf.JRecord.Log.TextLog;
import net.sf.JRecord.Numeric.ICopybookDialects;
import net.sf.JRecord.zTest.Common.IO;
import net.sf.JRecord.zTest.Common.TstConstants;
import net.sf.JRecord.zTest.Common.TstData;

/**
 *
 *
 * @author Bruce Martin
 *
 */
public class TstFixedLengthRecordIOWriter extends TestCase {


	private CopybookLoader copybookInt = new CobolCopybookLoader();

    private static final String TMP_DIRECTORY = TstConstants.TEMP_DIRECTORY;


    private static AbstractLayoutDetails copyBook = null;

    private static final String dtar107CopybookName = "DTAR107";
    private static final String dtar020CopybookName = "DTAR020";
    private static final String fileName = TMP_DIRECTORY + "VbTestFile.tmp";
	private final byte[][] dtar020Lines = {
	        { -10,  -7, -10,  -7, -12, -15, -11,  -8,   2,  12,   0,  64,  17,-116
	             ,  40,  12,   0,   0,   0,   0,  28,   0,   0,   0,   0,  80,  28 },
	        { -10, -13, -10, -16, -12,  -8, -16,  -8,   2,  12,   0,  64,  17,-116
	             ,  23,  12,   0,   0,   0,   0,  28,   0,   0,   0,   0,  72, 124 },
	        { -10, -14, -10,  -8, -12, -10,  -9, -15,   2,  12,   0,  64,  17,-116
	             , 104,  92,   0,   0,   0,   0,  28,   0,   0,   0,   6,-103,-100 },
	        { -10, -14, -10,  -8, -12, -10,  -9, -15,   2,  12,   0,  64,  17,-116
	             , 104,  92,   0,   0,   0,   0,  29,   0,   0,   0,   6,-103, -99 },
	        { -10, -12, -10, -13, -12, -12, -14,  -7,   2,  12,   0,  64,  17,-116
	             ,-107, 124,   0,   0,   0,   0,  28,   0,   0,   0,   0,  57,-100 }
	};

	/**
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }


    /**
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();

    }


    public void testBinWrite1() throws Exception {
        copyBook = ToLayoutDetail.getInstance().getLayout(
                copybookInt.loadCopyBook(
                        TstConstants.COBOL_DIRECTORY + dtar020CopybookName + ".cbl",
                        CopybookLoader.SPLIT_NONE, 0, "cp037",
                        ICopybookDialects.FMT_MAINFRAME, 0, new TextLog()
                ));

        tst1file(dtar020Lines, copyBook);
    }


    public void testBinWrite2() throws Exception {
    	System.out.println("Copybook: " + TstConstants.COBOL_DIRECTORY + dtar107CopybookName + ".cbl");
        copyBook = ToLayoutDetail.getInstance().getLayout(
                copybookInt.loadCopyBook(
                        TstConstants.COBOL_DIRECTORY + dtar107CopybookName + ".cbl",
                        CopybookLoader.SPLIT_NONE, 0, "cp037",
                        ICopybookDialects.FMT_MAINFRAME, 0, new TextLog()
                ));

        tst1file(TstData.DTAR107_LINES, copyBook);
    }

    public void tst1file(byte[][] lines, AbstractLayoutDetails copyBookDtl)
    throws IOException {

        int i, j;
        int copies = 5000;
        byte[][] largeFile = new byte[lines.length * copies][];

        for (i = 0; i < copies; i++) {
            for (j = 0; j < lines.length; j++) {
                largeFile[i * lines.length + j]
                          = lines[j];
            }
        }

        binReadCheck("Standard >> ", lines, copyBookDtl);
        binReadCheck("   Large >> ", largeFile, copyBookDtl);
        System.out.println(".. end ..");
    }

    private void binReadCheck(String id, byte[][] lines2Test, AbstractLayoutDetails copyBookDtl)
    throws IOException {
        FixedLengthByteReader tReader = new FixedLengthByteReader(copyBookDtl.getMaximumRecordLength());
        byte[] line;
        int i = 0;
        boolean b;

        System.out.println(id + "Bin Read, Temp File="+fileName);
        writeAFile(fileName, lines2Test, copyBookDtl);
        tReader.open(fileName);

        while ((line = tReader.read()) != null) {
            b = Arrays.equals(lines2Test[i], line);
            if (!b) {
                System.out.println("");
                System.out.println(id + "Error Line " + i + " > " + lines2Test[i].length + " > " + line.length);
                System.out.println("  Expected: " + new String(lines2Test[i],  "CP037"));
                System.out.println("       Got: " + new String(line, "CP037") );
                System.out.println("");

                assertTrue(id + "Bin Line " + i + " is not correct ", b);
            }
            i += 1;
        }

        assertEquals(id + "Expected to read " + lines2Test.length
                   + " got " + i, lines2Test.length, i);

        tReader.close();
    }


    /**
     * writes byte array to a file
     *
     * @param name major part of the file name
     * @param bytes data to write to the file
     * @param details file layout details
     *
     * @throws IOException any IO errors
     */
    private void writeAFile(String name, byte[][] bytes, AbstractLayoutDetails details)
    throws IOException  {
        FixedLengthByteWriter writer = new FixedLengthByteWriter(bytes[0].length);

        IO.writeAFile(writer, name, bytes);
    }

}
