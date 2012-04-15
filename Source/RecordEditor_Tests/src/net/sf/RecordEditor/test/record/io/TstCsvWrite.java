/*
 * @Author Bruce Martin
 * Created on 1/04/2007
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
import net.sf.JRecord.Details.Line;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.StandardLineReader;
import net.sf.JRecord.IO.AbstractLineWriter;
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
public class TstCsvWrite extends TestCase {

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

    public void testWriteComma() throws Exception {
        tstOneType(",", "Comma Delimited, names on the first line");
    }

    public void testWriteTab() throws Exception {
        tstOneType("\t", "Tab Delimited, names on the first line");
    }


    public void tstOneType(String sep, String layoutName) throws Exception {

        int i, j;
        boolean ok = true;
        String fileName = TstConstants.TEMP_DIRECTORY + "csv.txt";

        AbstractLineWriter w = LineIOProvider.getInstance().getLineWriter(Constants.IO_NAME_1ST_LINE);
        AbstractLineReader r = LineIOProvider.getInstance().getLineReader(Constants.IO_NAME_1ST_LINE);

        LayoutDetail layout = getLayout(sep, layoutName);
        AbstractLine line;

        try {
            w.open(fileName);
            for (i = 0; i < CSV_DATA.length; i ++) {
                line = new Line(layout);

                for (j = 0; j < CSV_DATA[i].length; j++) {
                    line.setField(0, j, CSV_DATA[i][j]);
                    if (i == 7) {
                    	System.out.println("==> " + j + " >" + CSV_DATA[i][j]
                    	  + "<   > " + new String(line.getData()));
                    }
                }
                System.out.println("Line " + i + " = " + new String(line.getData()));
                w.write(line);
            }
        } finally {
            w.close();
        }

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

    public LayoutDetail getLayout(String sep, String layoutName) 
    throws IOException, RecordException {

        LayoutDetail ret = null;
        CopyBookDbReader copybookInt = new CopyBookDbReader();
        LayoutDetail layout = copybookInt.getLayout(layoutName);
        String fileName = TstConstants.TEMP_DIRECTORY + "layoutcsv.txt";
        AbstractLineReader r = LineIOProvider.getInstance().getLineReader(Constants.IO_NAME_1ST_LINE);

        writeCSV(fileName, CSV_FIELD_NAMES, CSV_DATA, sep);

        try {
            r.open(fileName, layout);

            ret = (LayoutDetail) r.getLayout();
            System.out.println(">> " + ret.getRecord(0).getFieldCount());

        } finally {
            r.close();
        }
        return ret;
    }


    private void writeCSV(String fileName, String[] names, String[][] data, String sep) {

        try {
            BufferedWriter w = new BufferedWriter(new FileWriter(fileName));

            System.out.println("File: " + fileName);

            writeCSV(w, names, sep);
            //writeCSV(w, names, sep);

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
