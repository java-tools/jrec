/*
 * @Author Bruce Martin
 * Created on 4/04/2007
 *
 * Purpose:
 */
package net.sf.RecordEditor.test.record.io;

import java.io.FileWriter;
import java.io.IOException;

import junit.framework.TestCase;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.RecordEditor.test.TstConstants;
import net.sf.RecordEditor.utils.CopyBookDbReader;
import net.sf.RecordEditor.utils.common.Common;

/**
 *
 *
 * @author Bruce Martin
 *
 */
public class TstCSVReader1 extends TestCase {

    private String windowsEol = new String(Common.LFCR_BYTES);

    private String[] text = {
            "fld1,fld2,fld3,fld4,fld5",
            "value 1,value 2,value 3 ,value 4,value 5",
            "value 1,value 2,value 3 ,'value 4 , another comma',value 5",
            "value 1,value 2,'value 3 , added a comma 1',value 4,value 5",
            "value 1,value 2,value 3 ,'value 4 , another comma',value 5",
            "value 1,value 2,value 3 ,value 4,'value 22 5 ,'",
            "value 1,value 2,value 3 ,'value 4 , another 1 comma','value 5 ,'",
            "',,',''a quote',field 3,''Quote at start and end'',field 5",
            "'field 1 ,',', field 2 comma at the start','field 3 , in the middle',', multiple commas , at end ,',last field",
            "',,,,',',,__,,',''a quote',''a quote '',field 5",
            "''','''',''''','''''','''''''",
            "',, ,,',',, ,,',',, ,,',',, ,,',',, ,,'"
    };
    private String[][] data = {
            {"value 1", "value 2", "value 3", "value 4", "value 5"},
            {"value 1" ,"value 2", "value 3", "value 4 , another comma", "value 5"},
            {"value 1" ,"value 2", "value 3 , added a comma 1", "value 4", "value 5"},
            {"value 1", "value 2", "value 3", "value 4 , another comma", "value 5"},
            {"value 1", "value 2", "value 3", "value 4", "value 22 5 ,"},
            {"value 1", "value 2", "value 3", "value 4 , another 1 comma", "value 5 ,"},
            {",,", "'a quote", "field 3", "'Quote at start and end'", "field 5"},
            {"field 1 ,", ", field 2 comma at the start", "field 3 , in the middle",
             ", multiple commas , at end ,", "last field"},
            {",,,,", ",,__,,", "'a quote", "'a quote '", "field 5"},
            {"'", "''", "'''", "''''", "'''''"},
            {",, ,,", ",, ,,", ",, ,,", ",, ,,", ",, ,,"},
    };


    public void testCsvRead() 
    throws IOException, RecordException {

        tst1File("Comma Delimited, names on the first line, Quote=s");
    }

    public void testCsvUpdate() throws IOException, RecordException {

        System.out.println("Testing update step");
        tst1FileUpdate("Comma Delimited, names on the first line, Quote=s");
    }

    public void tst1File(String layoutName) 
    throws IOException, RecordException {
        int i, j;
        boolean ok = true;
        AbstractLine line;
        CopyBookDbReader copybookInt = new CopyBookDbReader();
        LayoutDetail layout = copybookInt.getLayout(layoutName);
        String fileName = TstConstants.TEMP_DIRECTORY + "csv.txt";
        AbstractLineReader r = LineIOProvider.getInstance().getLineReader(Constants.IO_NAME_1ST_LINE);

        writeAFile(fileName, text, windowsEol);

        i = 0;
        try {
            r.open(fileName, layout);

            while ((line = r.read()) != null) {
                for (j = 0; j < data[i].length; j++) {
                	//System.out.println(new String(line.getData()));
                    ok = data[i][j].equals(line.getField(0, j).toString());
                    if (! ok) {
                        String s = "Error line " + (i + 1) + " Field : " + (j + 1)
                        + " >" + data[i][j] + " != >" + line.getField(0, j);

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


    public void tst1FileUpdate(String layoutName) throws IOException, RecordException {
        int i, j;
        boolean ok = true;
        AbstractLine line;
        CopyBookDbReader copybookInt = new CopyBookDbReader();
        LayoutDetail layout = copybookInt.getLayout(layoutName);
        String fileName = TstConstants.TEMP_DIRECTORY + "csv.txt";
        AbstractLineReader r = LineIOProvider.getInstance().getLineReader(Constants.IO_NAME_1ST_LINE);

        writeAFile(fileName, text, windowsEol);

        i = 0;
        try {
            r.open(fileName, layout);

            line = r.read();
            for (i = 0; i < data.length; i++) {
                for (j = 0; j < data[i].length; j++) {
                    line.setField(0, j, "zzzzz");
                    line.setField(0, j, data[i][j]);
                }
                for (j = 0; j < data[i].length; j++) {
                    ok = data[i][j].equals(line.getField(0, j).toString());
                    System.out.println("Line = " + j + " " + new String(line.getData()));
                    if (! ok) {
                        String s = "Error line " + (i + 1) + " Field : " + (j + 1)
                        + " >" + data[i][j] + " != >" + line.getField(0, j);

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

}
