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

import junit.framework.TestCase;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.IO.Binary4680LineReader;
import net.sf.RecordEditor.test.TstConstants;
import net.sf.RecordEditor.utils.CopyBookDbReader;

/**
 * Tests BinaryLineReader
 *
 * @author Bruce Martin
 *
 */
public class TstBinRecordIOReader extends TestCase {


	private CopyBookDbReader copybookInt = new CopyBookDbReader();

 
    private static final String TMP_DIRECTORY = TstConstants.TEMP_DIRECTORY;
 

	private final byte[][] priceLines = {
	        	{  51,  50,  53,  53,  55,  56,  54,  73,  84,  69,  77
	            ,  32,  77,  65,  73,  78,  84,  69,  78,  65,  78,  67
	            ,  69,  32,  32,  32,  32,  32,  32,  32,  32,  32,  32
	            ,  32,  32,  32,  32,   4,   6,  18,   0,  48,   0 ,  13,  10 },
	            {  57,  83,  77,  32,  83,  72,  69,  76,  70,  32,  66
	            ,  82,  65,  32,  83,  73,  78,  71,  76,  69,  84,  32
	            ,  53,  55,  54,  55,  81,  32,  79,  82,  32,  32,  32
	            ,  32,  32,  32,  32,  32,  32,   0,   0,   0,   0,   0
	            ,   0,   0,  53,  35,  51,  67,-128,   0,   0,   0,   2
	            ,  53,   0,   0,   0,   0,  23,-103,   0,  83,  77,  32
	            ,  83,  72,  69,  76,  70,  32,  66,  82,  65,  32,  83
	            ,  73,  78,  71,  76,  16,   0,   0,   0,   0,   0,   0
	            ,   0 ,  13,  10 },
	            {  70,   9,  51,-126,  84,-107,  83,  40,   0,   0,   0
	            ,   0,   0,   0,   0,  53,  35,  51,  67 ,  13,  10 },
	            {  57,  83,  77,  32,  83,  72,  69,  76,  70,  32,  66
	            ,  82,  65,  32,  83,  73,  78,  71,  76,  69,  84,  32
	            ,  53,  55,  54,  55,  81,  32,  65,  81,  32,  32,  32
	            ,  32,  32,  32,  32,  32,  32,   0,   0,   0,   0,   0
	            ,   0,   0,  53,  35,  52,  53,-128,   0,   0,   0,   2
	            ,  53,   0,   0,   0,   0,  23,-103,   0,  83,  77,  32
	            ,  83,  72,  69,  76,  70,  32,  66,  82,  65,  32,  83
	            ,  73,  78,  71,  76,  16,   0,   0,   0,   0,   0,   0
	            ,   0 ,  13,  10 },
	            {  57,  70,  65,  84,  72,  69,  82,  83,  32,  68,  65
	            ,  89,  32,  32,  32,  32,  32,  32,  32,  32,  32,  32
	            ,  32,  32,  32,  32,  32,  32,  32,  32,  32,  32,  32
	            ,  32,  32,  32,  32,  32,  32,   0,   0,   0,   0,   0
	            ,   0,   0,  53,  54,   3,-124,-128,   0,   0,   0,   8
	            ,  69,   0,   0,   0,   0,   1,-107,   0,  70,  65,  84
	            ,  72,  69,  82,  83,  32,  68,  65,  89,  32,  32,  32
	            ,  32,  32,  32,  32,  16,   0,   0,   0,   0,   0,   0
	            ,   0 ,  13,  10 },
	            {  68,   9,  51,  68,  69,   3,-108,  51 ,  13,  10 },
	            {  68,   0,   8,   8, 120,   0,  40,  37 ,  13,  10 },
	            {  49,  50,  53,  53,  55,  56,  55,  82, 101, 103, 117
	            , 108,  97, 114,  32,  82, 101, 116,  97, 105, 108,  32
	            ,  32,  32,  32,  32,  32,  32,  32,  32,  32,  32,  32
	            ,  32,  32,  32,  32,   4,   6,  18,   0,  48,   0,  48
	            ,   0,   0,   0 ,  13,  10 },
	            {  56,   0,   0,   0,   0,   0,   0,   0,  53,  35,   4
	            ,  65,  75,  73,  78,  71,  32,  79,  70,  32,  67,  79
	            ,  77,  69,  68,  89,  32,  68,  86,  68,  32,  80,  71
	            ,  32,  50,  51,  54,  51,  57,  83,  68,  87,  51,   0
	            ,   0,   0,  34,-103,   0,   0,   0,   0,   0,   0 ,  13,  10 },
	            {  56,   0,   0,   0,   0,   0,   0,   0,  53,  51,  53
	            ,  49,  72,  45,  67,  65,  78,  68,  76,  69,  32,  80
	            ,  76,  65,  84,  69,  45,  67,  72,  65,  82,  67,  79
	            ,  65,  76,  32,  35,  53,  49,  55,  50,  49,  51,   0
	            ,   0,   0,  25,-103,   0,   0,   0,   0,   0,   0 ,  13,  10 },
	            {  49,  50,  53,  53,  55,  56,  57,  82, 101, 103, 117
	            , 108,  97, 114,  32,  82, 101, 116,  97, 105, 108,  32
	            ,  32,  32,  32,  32,  32,  32,  32,  32,  32,  32,  32
	            ,  32,  32,  32,  32,   4,   6,  20,   0,  48,   0,  48
	            ,   0,   0,   0 ,  13,  10 },
	            {  69 ,  13,  10 }
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



    public void testBinRead() throws IOException, RecordException {
        int i, j;
        int copies = 5000;
        byte[][] largeFile = new byte[priceLines.length * copies][];

        for (i = 0; i < copies; i++) {
            for (j = 0; j < priceLines.length; j++) {
                largeFile[i * priceLines.length + j]
                          = priceLines[j];
            }
        }

        fileTest("Standard ->> ", priceLines);
        fileTest("   Large ->> ", largeFile);
        System.out.println(". end ..");
    }


    @SuppressWarnings("unchecked")
	public void fileTest(String id, byte[][] linesPrice) throws IOException, RecordException  {
        Binary4680LineReader tReader = new Binary4680LineReader();
        int i = 0;
        boolean b;
        AbstractLine line;
        String fName;
        LayoutDetail fg;

        fName = TMP_DIRECTORY + "Price.tmpbin";

        fg = copybookInt.getLayout("Price");
        System.out.println(id + " Bin Read");
        writeAFile(fName, linesPrice);
        tReader.open(fName, fg);

        while ((line = tReader.read()) != null && i < linesPrice.length) {
            b = Arrays.equals(linesPrice[i], line.getData());
            if (!b) {
                System.out.println("");
                System.out.println(id + " Error Line " + i + " "
                        + linesPrice[i].length
                        + " " + line.getData().length);
                System.out.println("  Expected: " + new String(linesPrice[i]) + "<<");
                System.out.println("       Got: " + new String(line.getData()) + "<<");
                System.out.println("");

                assertTrue(id + " Bin Line " + i + " is not correct ", b);
            }
            i += 1;
        }

        System.out.println();

        assertEquals(id + " Expected to read " + (linesPrice.length)
                   + " got " + i, linesPrice.length, i);

        tReader.close();
    }


 


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
