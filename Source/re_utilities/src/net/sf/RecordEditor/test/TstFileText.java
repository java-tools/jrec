/*
 * @Author Bruce Martin
 * Created on 17/06/2005
 *
 * Purpose:
 */
package net.sf.RecordEditor.test;


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.util.StringTokenizer;

import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.RecordEditor.edit.file.FileView;
import net.sf.RecordEditor.utils.CopyBookDbReader;
import net.sf.RecordEditor.utils.common.Common;



//import edit.DetailRecord;
import junit.framework.TestCase;


/**
 *
 *
 * @author Bruce Martin
 *
 */
public class TstFileText extends TestCase {
	private int dbIdx = TstConstants.DB_INDEX;

	private CopyBookDbReader copybookInt = new CopyBookDbReader();

	private String sqlUpdateRecordSep = "Update TBL_R_RECORDS set RECSEPLIST = '";
	private String sqlSet2Default  = sqlUpdateRecordSep + Common.DEFAULT_STRING + "'";
	private String sqlSet2crlf     = sqlUpdateRecordSep + Common.CRLF_STRING + "'";
	private String sqlSet2Unix     = sqlUpdateRecordSep + Common.CR_STRING + "'";
	private String sqlSet2Mac      = sqlUpdateRecordSep + Common.LF_STRING + "'";
    private String firstRecordType = "H1";

    private String windowsEol = new String(Common.LFCR_BYTES);
    private String macEol     = new String(Common.LF_BYTES);
    private String unixEol    = new String(Common.CR_BYTES);

    private static final String TMP_DIRECTORY = TstConstants.TEMP_DIRECTORY;
    private boolean writeFiles = true;

    private static LayoutDetail poCopyBook = null;
    private final String poCopybookName = "ams PO Download";
    private final String poFileName = TMP_DIRECTORY + poCopybookName + ".tmp";
	private final String[] poLines = {
	   "H1453490000006060286225      040909        00  200 0501020501075965        LADIES KNICFT",
	   "D100007000000000000000022222500000000 43314531000000054540000007       2075359        45614531       DONKEY 24-006607 SHWL WRAP CARD",
	   "S1504300000001504500000001506500000001507600000001507900000001515100000001507200000001    00000000    00000000    00000000",
	   "D100004000000000014832000000000000000 05614944000000054540000004       2075360        5614944        MILK 24-006607 SHWL WRAP CARD",
	   "S1504500000001507600000001507900000001333149440001    00000000    00000000    00000000    00000000    00000000    00000000"
	};

	private FileView poFileRep;
	private boolean dbUpdated = false;



    /**
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        if (poCopyBook == null) {
            Common.setConnectionId(dbIdx);
            runSQL(sqlSet2Default);
            poCopyBook = copybookInt.getLayout(poCopybookName);
        }

        if (writeFiles) {
            writeAFile(poFileName, poLines, windowsEol);

            writeFiles = false;
        }

        poFileRep = new FileView(poFileName, poCopyBook, false);
        //poFileRep.setCurrLayoutIdx(0);
     }

    /**
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();

        if (dbUpdated) {
            runSQL(sqlSet2Default);
        }
    }


    /**
     * writes byte array to a file
     *
     * @param name major part of the file name
     * @param line data to write to the file
     * @param eol end of line character string
     *
     * @throws IOException any IO errors
     */
    private void writeAFile(String name, String[] line, String eol)
    throws IOException  {
        int i;
        FileWriter f = new FileWriter(name);

		for (i = 0; i < line.length; i++) {
		    f.write(line[i]);
		    f.write(eol);
		}

        f.close();
    }


    /**
     * check cell is editable
     *
     */
    public void testIsCellEditable() {

        assertEquals("Is Cell Editable ", true, poFileRep.isCellEditable(0, 2));
    }


    /**
     * Test the data read in is correct
     *
     */
    public void testData() {

 		checkLines("Test File Write (String)",
		           0, poLines.length, 0, poFileRep, poLines);
    }



    /**
     * Check if we can write a file succesfully
     *
     * @throws IOException any error that occurs
     * @throws RecordException any general error
     */
    public void testWriteFile() throws IOException, RecordException {
        testFileWrite(poLines, Common.DEFAULT_STRING);
        testFileWrite(duplicateLines(poLines), Common.DEFAULT_STRING);
        testFileWrite(poLines, Common.CRLF_STRING);
        testFileWrite(duplicateLines(poLines), Common.CR_STRING);
        testFileWrite(poLines, Common.CR_STRING);
        testFileWrite(poLines, Common.LF_STRING);
    }

    private String[] duplicateLines(String[] lines) {
        int i, j;
        int copies = 1000;
        String[] largeFile = new String[lines.length * copies];

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
     *
     * @param lines data to write to the test file
     * @param opt file type to write
     *
     * @throws IOException an io error that occurs
     * @throws RecordException any error
     */
    private void testFileWrite(String[] lines, String opt)
    throws IOException, RecordException {
        String suffix = "win";
        String eol = windowsEol;
        String fName;
        LayoutDetail fg = poCopyBook;

        dbUpdated = true;


        if (opt == Common.DEFAULT_STRING) {
            runSQL(sqlSet2Default);
        } else if (opt == Common.CRLF_STRING) {
            suffix =  "crlf";
            runSQL(sqlSet2crlf);
        } else if (opt == Common.CR_STRING) {
            suffix =  "unix";

            eol = unixEol;

            runSQL(sqlSet2Unix);
        } else if (opt == Common.LF_STRING) {
            suffix =  "mac";

            eol = macEol;

            runSQL(sqlSet2Mac);
        }
        fg = copybookInt.getLayout(poCopybookName);
        fName  = poFileName + suffix;

        writeAFile(fName, lines, eol);

        FileView f = new FileView(fName, fg, false);

        String tmp = f.getValueAt(0, 2).toString();

        f.setValueAt(null, 0, 0, 0, "1");
        f.setValueAt(null, 0, 0, 0, tmp);

        f.writeFile();

        checkFile("Test File Write " + fName, fName, lines, eol);

        f = new FileView(fName, fg, false);
		checkLines("Test File Write " + fName,
		           0, lines.length, 0, f, lines);

		(new File(fName)).delete();
		(new File(fName + "~")).delete();
    }


    /**
     * Executs a piece of SQL
     *
     * @param sql sql to be executed
     */
    private void runSQL(String sql) {
        try {
            Connection con = Common.getDBConnection(dbIdx);
            con.createStatement().executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * check the getColumnCount
     *
     */
    public void testGetColumnCount() {
      /*  int i;
        DetailRecord[] layouts = poCopyBook.getLayouts();

        for (i = 0; i < layouts.length; i++) {
            if (layouts)
        }*/

        assertEquals("column Count expected 11 got "
				+ poFileRep.getColumnCount(),
				11, poFileRep.getColumnCount());
    }


    /**
     * check the getRowCount
     *
     */
    public void testGetRowCount() {

        assertEquals("row Count expected " + poLines.length
                			     + " got " + poFileRep.getRowCount(),
                	 poLines.length, poFileRep.getRowCount());
    }


    /**
     * check getValueAt
     *
     */
    public void testGetValueAt() {
        String s = poFileRep.getValueAt(0, 2).toString();

        assertEquals("getValue " + firstRecordType + " <> " + s, firstRecordType, s);
    }

    /**
     * Class under test for void setValueAt(Object, int, int)
     */
    public void testSetValueAtObjectintint() {
        String newVal = "12";

        poFileRep.setValueAt(null, 0, 0, 0, newVal);

        String s = poFileRep.getValueAt(0, 2).toString();
        //dtar020FileRep.setValueAt(tmp, 0, 2);

        assertEquals("setValue " + newVal + " <> " + s, newVal, s);
    }

    /**
     * Class under test for String getColumnName(int)
     */
    public void testGetColumnNameint() {
        String s = poFileRep.getColumnName(2);
        assertEquals("getColumnName expecting '1 - 2|Record Type' <> " + s,
                "1 - 2|Record Type", s);
    }


    /**
     * check getLayoutColumnCount
     *
     */
    public void testGetLayoutColumnCount() {
        assertEquals("column Count expected 9 got "
				+ poFileRep.getLayoutColumnCount(0),
				9, poFileRep.getLayoutColumnCount(0));
    }

    /**
     * Class under test for String getColumnName(int, int)
     */
    public void testGetColumnNameintint() {
        String s = poFileRep.getColumnName(0, 0);
        assertEquals("getColumnName expecting Record Type <> " + s,
                "Record Type", s);
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


    /**
     * check iBrowse function
     */
    public void testIsBrowse() throws IOException, RecordException {

        assertFalse("file should be editable ", poFileRep.isBrowse());

        FileView tmp = new FileView(poFileName, poCopyBook, true);
        assertTrue("file should not be editable ", tmp.isBrowse());

    }


    /**
     * checking the isChanged function
     *
     */
    public void testIsChanged() {

      assertFalse("file should be unchanged, it is not ", poFileRep.isChanged());
      poFileRep.setValueAt(null, 0, 0, 0, "12");
      assertTrue("file Should be changed, it is not ", poFileRep.isChanged());
    }


    /**
     * checking the isBinaryFile function
     *
     */
    public void testIsBinaryFile() {

        assertFalse("file Should be text, it is not ", poFileRep.isBinaryFile());
    }


    /**
     * Checks the contents of a file with a byte array
     *
     * @param msg message to display if there is a error
     * @param start line to start at
     * @param end line to end at
     * @param dataStart line to start in the array at
     * @param f file to check
     * @param lines what to check against against
     */
    private void checkLines(String msg, int start, int end, int dataStart,
            				FileView f, String[] lines) {

        int i, j;
        boolean equals;
        String s;

        j = dataStart;
        for (i = start; i < end; i++) {
            s = new String(f.getLine(i).getData());
            equals = lines[i].equals(s);
            if (!equals) {
                try {
                    System.out.println(">> " + lines[i].length() + " " + s.length());
                    System.out.println(lines[i]);
                    System.out.println(s);
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
     * @param lines value to compare the file with
     * @param eol end-of-line string
     *
     * @throws IOException any error that occurs
     */
    private void checkFile(String msg, String fName, String[] lines, String eol)
    throws IOException {
        int i, count;
        String record;
        String input;
        StringTokenizer tok;
        char[] inputChars = new char[800000];
        FileReader in = new FileReader(fName);

        i = in.read(inputChars);
        System.out.println("!!! " + i);
        in.close();

        input = new String(inputChars);
        tok = new StringTokenizer(input, eol);

        count = 0;
        for (i = 0; i < lines.length; i++) {
            record = tok.nextToken();
            count += record.length() + eol.length();

            if (! lines[i].equals(record)) {
                System.out.println(">>> " + lines[i].length() + " " + record.length()
                        + " " + count);
                System.out.println("-->" + lines[i] + "<");
                System.out.println("  >" + record + "<");
            }
            assertTrue(msg + " line " + i, lines[i].equals(record));
        }
   }
        /*int i;
        String record;
        FileReader in1 = new FileReader(fName);
        BufferedReader in = new BufferedReader(in1);

        for (i = 0; i < lines.length; i++) {
            record = in.readLine();

            if (! lines[i].equals(record)) {
                System.out.println(">> " + lines[i].length() + " " + record.length());
                System.out.println(lines[i]);
                System.out.println(record);
            }
            assertTrue(msg + " line " + i, lines[i].equals(record));
        }
        in.close();
        in1.close();
    }*/
}
