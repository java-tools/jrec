/*
 * @Author Bruce Martin
 * Created on 17/06/2005
 *
 * Purpose:
 */
package net.sf.RecordEditor.test;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.CopyBookDbReader;
import net.sf.RecordEditor.utils.common.Common;



import junit.framework.TestCase;


/**
 *
 *
 * @author Bruce Martin
 *
 */
public class TstFileBin extends TestCase {
	private int dbIdx = TstConstants.DB_INDEX;

	private CopyBookDbReader copybookInt = new CopyBookDbReader();

    private String firstSku = "69694158";


    private static final String TMP_DIRECTORY = TstConstants.TEMP_DIRECTORY;
    private boolean writeFiles = true;

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

	private FileView dtar020FileRep;


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
	            {  69 ,  13,  10 },
	};


	private final byte[][] fb80bytes = {
	        { -45,-119,-107,-123,  64, -15,  64,  64,  64,  64,  64,  64,  64,  64
	            ,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64
	            ,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64
	            ,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64
	            ,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64
	            ,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64 },
	            { -45,-119,-107,-123,  64, -14,  64,  77,-127,-107,-106, -93,-120,-123
	            ,-103,  64,-109,-119,-107,-123,  93,  64,  64,  64,  64,  64,  64,  64
	            ,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64
	            ,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64
	            ,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64
	            ,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64 },
	            { -29,-120,-119,-103,-124,  64, -45,-119,-107,-123,  64,  64,  64,  64
	            ,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64
	            ,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64
	            ,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64
	            ,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64
	            ,  64,  64,  64,  64,  64,  64,  64,  64,  64,  64 }

	};

    /**
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        if (dtar020CopyBook == null) {
            Common.setConnectionId(dbIdx);
            dtar020CopyBook = copybookInt.getLayout(dtar020CopybookName);
        }

        if (writeFiles) {
            writeAFile(dtar020FileName, dtar020Lines);

            writeFiles = false;
        }

        dtar020FileRep = new FileView(dtar020FileName, dtar020CopyBook, false);
        dtar020FileRep.setCurrLayoutIdx(0);
     }

    /**
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();

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


    public void testIsCellEditable() {

        assertEquals("Is Cell Editable ", true, dtar020FileRep.isCellEditable(0, 2));
    }


    /**
     * Test the data read in is correct
     *
     */
    public void testData() {

 		checkLines("Test File Write (fixed length Record)",
		           0, dtar020Lines.length, 0, dtar020FileRep, dtar020Lines);
    }



    /**
     * Check if we can write a file succesfully
     *
     * @throws IOException any error that occurs
     * @throws RecordException general error
     */
    public void testWriteFile() throws IOException, RecordException {
        String fName = dtar020FileName + "bin";
        LayoutDetail fg;
        System.out.println("testFileWrite");

        testFileWrite(fName, dtar020CopyBook, dtar020Lines);

        fg = copybookInt.getLayout("Price");
        fName = TMP_DIRECTORY + "Price.tmpbin";
        testFileWrite(fName, fg, priceLines);
        testFileWrite(fName, fg, duplicateLines(priceLines));

        fg = copybookInt.getLayout("Mainframe FB80");
        fName = TMP_DIRECTORY + "fb80.tmpbin";
        testFileWrite(fName, fg, fb80bytes);
        testFileWrite(fName, fg, duplicateLines(fb80bytes));

        fg = copybookInt.getLayout("Mainframe FB80 record");
        fName = TMP_DIRECTORY + "fb80.tmpbin1";
        testFileWrite(fName, fg, fb80bytes);
        testFileWrite(fName, fg, duplicateLines(fb80bytes));


        /*writeAFile(fName, dtar020Lines);

        FileRep f = new FileRep(fName, dtar020CopyBook, false);

        String tmp = dtar020FileRep.getValueAt(0, 2).toString();

        f.setLayoutsValueAt(null, 0, 0, 0, "12341234");
        f.setLayoutsValueAt(null, 0, 0, 0, tmp);

        f.writeFile();

        checkFile("Test File Write (fixed length Record)", fName, dtar020Lines);

        f = new FileRep(fName, dtar020CopyBook, false);
		checkLines("Test File Write (fixed length Record)",
		           0, dtar020Lines.length, 0, f, dtar020Lines);

		(new File(fName)).delete();*/
    }


    private byte[][] duplicateLines(byte[][] lines) {
        int i, j;
        int copies = 1000;
        byte[][] largeFile = new byte[lines.length * copies][];

        for (i = 0; i < copies; i++) {
            for (j = 0; j < lines.length; j++) {
                largeFile[i * lines.length + j]
                          = lines[j];
            }
        }


        return largeFile;
    }


    /**
     * Tests writing a file
     * @param fName File name to write
     * @param fg copybook to use
     * @param bytes data to write to the test file
     * @throws IOException an io error that occurs
     * @throws RecordException any general error
     */
    private void testFileWrite(String fName, LayoutDetail fg, byte[][] bytes)
    throws IOException, RecordException {

        System.out.println("testFileWrite ...  " + fName + " " + fg.getLayoutName());
        writeAFile(fName, bytes);
        System.out.println("testFileWrite ...   writeAFile end");

        FileView f = new FileView(fName, fg, false);
        System.out.println("testFileWrite ... filerep");

        String tmp = f.getValueAt(0, 2).toString();

        f.setValueAt(null, 0, 0, 0, "1");
        f.setValueAt(null, 0, 0, 0, tmp);

        f.writeFile();

        System.out.println("testFileWrite ... checkfile");

        checkFile("Test File Write " + fName, fName, bytes);
        System.out.println("testFileWrite ... checkfile end");

        f = new FileView(fName, fg, false);
        System.out.println("testFileWrite ... new file");
		checkLines("Test File Write " + fName,
		           0, bytes.length, 0, f, bytes);
	       System.out.println("testFileWrite ... checkLines");

		(new File(fName)).delete();
    }



    public void testGetColumnCount() {

        assertEquals("column Count expected 8 got "
				+ dtar020FileRep.getColumnCount(),
				8, dtar020FileRep.getColumnCount());
    }

    public void testGetRowCount() {

        assertEquals("row Count expected " + dtar020Lines.length
                			     + " got " + dtar020FileRep.getRowCount(),
                	 dtar020Lines.length, dtar020FileRep.getRowCount());
    }

    public void testGetValueAt() {
        String s = dtar020FileRep.getValueAt(0, 2).toString();

        assertEquals("getValue " + firstSku + " <> " + s, firstSku, s);
    }

    /*
     * Class under test for void setValueAt(Object, int, int)
     */
    public void testSetValueAtObjectintint() {
        String newVal = "12341234";

        dtar020FileRep.setValueAt(null, 0, 0, 0, newVal);

        String s = dtar020FileRep.getValueAt(0, 2).toString();
        //dtar020FileRep.setValueAt(tmp, 0, 2);

        assertEquals("setValue " + newVal + " <> " + s, newVal, s);
    }

    /*
     * Class under test for String getColumnName(int)
     */
    public void testGetColumnNameint() {
        String s = dtar020FileRep.getColumnName(2);
        assertEquals("getColumnName expecting '1 - 8|KEYCODE-NO' <> " + s,
                "1 - 8|KEYCODE-NO", s);
    }

    public void testGetLayoutColumnCount() {
        assertEquals("column Count expected 6 got "
				+ dtar020FileRep.getLayoutColumnCount(0),
				6, dtar020FileRep.getLayoutColumnCount(0));
    }

    /*
     * Class under test for String getColumnName(int, int)
     */
    public void testGetColumnNameintint() {
        String s = dtar020FileRep.getColumnName(0, 0);
        assertEquals("getColumnName expecting KEYCODE-NO <> " + s,
                "KEYCODE-NO", s);
    }

/*    public void testGetCurrLayoutIdx() {
    }

    public void testSetCurrLayoutIdx() {
    }

    public void testDeleteRecord() {
    }

    public void testDeleteRecords() {
    }

    public void testCopyRecords() {
    }

    public void testPasteRecords() {
    }

    public void testNewRecord() {
    }

    public void testFind() {
    }

    public void testGetRecord() {
    }

    public void testGetMsg() {
    }*/

    public void testIsBrowse() throws IOException, RecordException {

        assertFalse("file should be editable ", dtar020FileRep.isBrowse());


        FileView tmp = new FileView(dtar020FileName, dtar020CopyBook, true);
        assertTrue("file should not be editable ", tmp.isBrowse());

    }

    public void testIsChanged() {

      assertFalse("file should be unchanged, it is not ", dtar020FileRep.isChanged());
      dtar020FileRep.setValueAt(null, 0, 0, 0, "12341234");
      assertTrue("file Should be changed, it is not ", dtar020FileRep.isChanged());
    }

    public void testIsBinaryFile() {

        assertTrue("file Should be binary, it is not ", dtar020FileRep.isBinaryFile());
    }


    /**
     * Checks the contents of a file with a byte array
     *
     * @param msg message to display if there is a error
     * @param start line to start at
     * @param end line to end at
     * @param dataStart line to start in the array at
     * @param f file to check
     * @param bytes what to check against against
     */
    private void checkLines(String msg, int start, int end, int dataStart,
            				FileView f, byte[][] bytes) {

        int i, j;
        boolean equals;

        System.out.println("Checklines --> " + msg);
        j = dataStart;
        for (i = start; i < end; i++) {
            equals = Arrays.equals(bytes[i], f.getLine(i).getData());
            if (!equals) {
                try {
                    System.out.println(new String(bytes[i], "CP037"));
                    System.out.println(new String(f.getLine(i).getData(), "CP037"));
                } catch (Exception e) {
                }
            }
            assertTrue(msg + " line " + i, equals);
            j++;
        }
    }


    /**
     * This procedure checks the content of a file against a byte Array
     *
     * @param msg Error Message
     * @param fName File Name to check
     * @param bytes value to compare the file with
     *
     * @throws IOException any error that occurs
     */
    private void checkFile(String msg, String fName, byte[][] bytes)
    throws IOException {
        int i, j;
        byte[] record;
        FileInputStream in = new FileInputStream(fName);
        System.out.println("CheckFile --> " + msg);

        for (i = 0; i < bytes.length; i++) {
            record = new byte[bytes[i].length];
            in.read(record);

            if (! Arrays.equals(bytes[i], record)) {
                System.out.println(new String(bytes[i]));
                System.out.println(new String(record));
                System.out.println("Lengths >> " + i
                                  + " of " + bytes.length
                        		  + " " + bytes[i].length
                                  + " " + record.length);
                System.out.println();
                System.out.println();
                for (j = 0; j < bytes[i].length; j++) {
                    System.out.print(" " + bytes[i][j]);
                }
                System.out.println();
                for (j = 0; j < record.length; j++) {
                    System.out.print(" " + record[j]);
                }
                System.out.println();
            }
            assertTrue(msg + " line " + i, Arrays.equals(bytes[i], record));
        }
        in.close();
    }
}
