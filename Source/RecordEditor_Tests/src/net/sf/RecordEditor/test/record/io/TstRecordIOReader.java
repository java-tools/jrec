/*
 * @Author Bruce Martin
 * Created on 28/08/2005
 *
 * Purpose:
 */
package net.sf.RecordEditor.test.record.io;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.JRecord.IO.TextLineReader;
import net.sf.JRecord.zTest.Common.IO;
import net.sf.RecordEditor.test.TstConstants;
import net.sf.RecordEditor.utils.CopyBookDbReader;
import net.sf.RecordEditor.utils.common.Common;



import junit.framework.TestCase;

/**
 *
 *
 * @author Bruce Martin
 *
 */
public class TstRecordIOReader extends TestCase {

	private int dbIdx = TstConstants.DB_INDEX;

	private CopyBookDbReader copybookInt = new CopyBookDbReader();

//    private String windowsEol = new String(Common.LFCR_BYTES);
//    private String macEol     = new String(Common.LF_BYTES);
    private String unixEol    = new String(Common.CR_BYTES);

    private static final String TMP_DIRECTORY = TstConstants.TEMP_DIRECTORY;
//    private boolean writeFiles = true;

    private static LayoutDetail poCopyBook = null;
    private final String poCopybookName = "ams PO Download";
    private final String poFileName = TMP_DIRECTORY + "ams_PO_Download.tmp";

	private final String[] poLines = {
	 	   "H1453490000006060286225      040909        00  200 0501020501075965        LADIES KNICFT",
	 	   "D100007000000000000000022222500000000 43314531000000054540000007       2075359        45614531       DONKEY 24-006607 SHWL WRAP CARD",
	 	   "S1504300000001504500000001506500000001507600000001507900000001515100000001507200000001    00000000    00000000    00000000",
	 	   "D100004000000000014832000000000000000 05614944000000054540000004       2075360        5614944        MILK 24-006607 SHWL WRAP CARD",
	 	   "S1504500000001507600000001507900000001333149440001    00000000    00000000    00000000    00000000    00000000    00000000"
	 	};

    private static LayoutDetail dtar020CopyBook = null;
    private final String dtar020CopybookName = "DTAR020";
    private final String dtar020FileName = TMP_DIRECTORY + dtar020CopybookName + ".tmp";
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

//	private FileView dtar020FileRep;

//	private boolean dbUpdated = false;


    /**
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        if (poCopyBook == null) {
            Common.setConnectionId(dbIdx);
            poCopyBook = copybookInt.getLayout(poCopybookName);
            dtar020CopyBook = copybookInt.getLayout(dtar020CopybookName);
        }
     }


    /**
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();

    }

    public void testTextRead() throws IOException, RecordException {
        int i, j;
        int copies = 5000;
        String[] largeFile = new String[poLines.length * copies];

        for (i = 0; i < copies; i++) {
            for (j = 0; j < poLines.length; j++) {
                largeFile[i * poLines.length + j]
                          = poLines[j];
            }
        }


        textReadTest(" Normal >> ", poLines);
        textReadTest("  Large >> ", largeFile);

        System.out.println();
        System.out.println(".. End Text Reader ..");
        System.out.println();
        System.out.println();
    }


    public void textReadTest(String id, String[] po_Lines) 
    throws IOException, RecordException {
        TextLineReader tReader = new TextLineReader();
        AbstractLine line;
        int i = 0;
        boolean b;

        System.out.println(id + "Text Read");
        IO.writeAFile(poFileName, po_Lines, unixEol);
        tReader.open(poFileName, poCopyBook);

        while ((line = tReader.read()) != null) {
            b = po_Lines[i].equals(new String(line.getData()));
            if (!b) {
                System.out.println("");
                System.out.println("id + Error Line " + i);
                System.out.println("  Expected: " + po_Lines[i]);
                System.out.println("       Got: " + new String(line.getData()));
                assertTrue("Line " + i + " is not correct ", b);
            }
            i += 1;
        }

        assertEquals(id + "Expected to read " + po_Lines.length
                   + " got " + i, po_Lines.length, i);

        tReader.close();
    }



    public void testBinRead() throws IOException, RecordException {
        int i, j;
        int copies = 5000;
        byte[][] largeFile = new byte[dtar020Lines.length * copies][];

        for (i = 0; i < copies; i++) {
            for (j = 0; j < dtar020Lines.length; j++) {
                largeFile[i * dtar020Lines.length + j]
                          = dtar020Lines[j];
            }
        }

        binReadCheck("Standard >> ", dtar020Lines);
        binReadCheck("   Large >> ", largeFile);
        System.out.println(".. end ..");
    }

    private void binReadCheck(String id, byte[][] dtar020Lines2Test)
    throws IOException, RecordException {
        AbstractLineReader tReader = LineIOProvider.getInstance()
        		.getLineReader(Constants.FIXED_LENGTH_IO);
        AbstractLine line;
        int i = 0;
        boolean b;

        System.out.println(id + "Bin Read");
        writeAFile(dtar020FileName, dtar020Lines2Test);
        tReader.open(dtar020FileName, dtar020CopyBook);

        while ((line = tReader.read()) != null) {
            b = Arrays.equals(dtar020Lines2Test[i], line.getData());
            if (!b) {
                System.out.println("");
                System.out.println(id + "Error Line " + i);
                System.out.println("  Expected: " + new String(dtar020Lines2Test[i],  "CP037"));
                System.out.println("       Got: " + new String(line.getData(), "CP037"));
                System.out.println("");

                assertTrue(id + "Bin Line " + i + " is not correct ", b);
            }
            i += 1;
        }

        assertEquals(id + "Expected to read " + dtar020Lines2Test.length
                   + " got " + i, dtar020Lines2Test.length, i);

        tReader.close();
    }


//    /**
//     * writes byte array to a file
//     *
//     * @param name major part of the file name
//     * @param line data to write to the file
//     * @param eol end of line character string
//     *
//     * @throws IOException any IO errors
//     */
//    private void writeAFile(String name, String[] line, String eol)
//    throws IOException  {
//        int i;
//        FileWriter f = new FileWriter(name);
//
//		for (i = 0; i < line.length; i++) {
//		    f.write(line[i]);
//		    f.write(eol);
//		}
//
//        f.close();
//    }



    /**
     * writes byte array to a file
     *
     * @param name major part of the file name
     * @param bytes data to write to the file
     *
     * @throws IOException any IO errors
     */
    private void writeAFile(String name, byte[][] bytes)
    throws IOException  {
        int i;
        FileOutputStream f = new FileOutputStream(name);
        BufferedOutputStream outputStream = new BufferedOutputStream(f);
        System.out.print("writeAFile " + bytes.length);

		for (i = 0; i < bytes.length; i++) {
		    outputStream.write(bytes[i]);
		}

		System.out.println("writeAFile end loop");

		outputStream.close();

        f.close();
        System.out.println("writeAFile exit");
    }

}
