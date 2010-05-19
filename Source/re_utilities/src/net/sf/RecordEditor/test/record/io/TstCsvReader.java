/*
 * @Author Bruce Martin
 * Created on 31/03/2007
 *
 * Purpose:
 */
package net.sf.RecordEditor.test.record.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.RecordEditor.test.TstConstants;
import net.sf.RecordEditor.utils.CopyBookDbReader;

import junit.framework.TestCase;

/**
 *
 *
 * @author Bruce Martin
 *
 */
public class TstCsvReader extends TestCase {


    public static final String[] CSV_FIELD_NAMES = {"Fld1", "Fld2", "Fld3", "Fld4", "Fld5"};
    public static final String[][] CSV_DATA = {
            {"11", "22", "33", "44", "55"},
            {"",   "",   "",   "", "5555"},
            {"", "222",  "",   "", "55"},
            {"", "",  "333",   "", "55"},
            {"", "",  "",   "444", "55"},
            {"", "",  "",   "", "555"},
            {"1", "222",  "",   "", "55"},
            {"1", "",  "333",   "", "55"},
            {"1", "",  "",   "444", "55"},
            {"", "222"},
            {"", "",  "333"},
            {"", "",  "",   "444"},
            {"", "",  "",   "", "555"},
            {"11", "22", "33", "44", "55"},
            {"",   "",   "",   "", "5555"},
   };

    public void testReadComma() throws Exception {

        oneFileTest(",", "Comma Delimited, names on the first line");
    }

    public void testReadTab() throws Exception {

        oneFileTest("\t", "Tab Delimited, names on the first line");
    }

    public void testReadXXX() throws Exception {

        String fileName = TstConstants.TEMP_DIRECTORY + "/yyCsv.txt";
        String[] names = CSV_FIELD_NAMES;
        String[][] data = CSV_DATA;

        try {
            BufferedWriter w = new BufferedWriter(new FileWriter(fileName));

            System.out.println("File: " + fileName);

            writeCSV(w, names, ",");
            for (int j = 0; j < 2000; j++) {
                for (int i = 0; i < data.length; i++) {
                    writeCSV(w, data[i], ",");
                }
            }

            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void oneFileTest(String sep, String layoutName) 
    throws IOException, RecordException {

        int i, j;
        boolean ok = true;
        AbstractLine line;
        CopyBookDbReader copybookInt = new CopyBookDbReader();
        LayoutDetail layout = copybookInt.getLayout(layoutName);
        String fileName = TstConstants.TEMP_DIRECTORY + "/csv.txt";
        AbstractLineReader r = LineIOProvider.getInstance().getLineReader(Constants.IO_NAME_1ST_LINE);

        writeCSV(fileName, CSV_FIELD_NAMES, CSV_DATA, sep);

        i = 0;
        try {
            r.open(fileName, layout);

            while ((line = r.read()) != null) {
                for (j = 0; j < CSV_DATA[i].length; j++) {
                    ok = CSV_DATA[i][j].equals(line.getField(0, j).toString());
                    if (! ok) {
                        String s = "Error line " + (i + 1) + " Field : " + (j + 1)
                        + " >" + CSV_DATA[i][j] + " != >" + line.getField(0, j);

                        System.out.println("Line = " + new String(line.getData()));
                        System.out.println(s);

                        assertTrue(s, ok);
                    }
                }
                i += 1;
            }

        } finally {
            r.close();
        }
    }


    private void writeCSV(String fileName, String[] names, String[][] data, String sep) {

        try {
            BufferedWriter w = new BufferedWriter(new FileWriter(fileName));

            System.out.println("File: " + fileName);

            writeCSV(w, names, sep);
            for (int i = 0; i < data.length; i++) {
                writeCSV(w, data[i], sep);
            }

            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeCSV(BufferedWriter w, String[] data, String sep)
    throws IOException {

        if (data != null && data.length > 0) {
            w.write(data[0]);
            for (int i = 1; i < data.length; i++) {
                w.write(sep);
                w.write(data[i]);
            }
        }
        w.newLine();
    }
}
