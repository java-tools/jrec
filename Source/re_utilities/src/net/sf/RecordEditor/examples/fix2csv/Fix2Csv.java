/*
 * @Author Bruce Martin
 * Created on 26/01/2006
 *
 * Purpose:
 *    Convert a fixed field width file to a CSV file
 */
package net.sf.RecordEditor.examples.fix2csv;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.LineIOProvider;

/**
 *
 *
 * @author Bruce Martin
 *
 * Note: to compile this program you need to have the RecordEditor
 * Source code installed
 */
public class Fix2Csv {

    /**
     * Copy a File described by a layout to a CSV file
     *
     * @param layout record layout
     * @param infile input file to be copied
     * @param outfile output file
     * @param sep field seperator
     *
     * @throws IOException any io-error that occurs when opening
     *         the input file
     */
    public Fix2Csv(final LayoutDetail layout, final String infile,
            	   final String outfile,      final String sep)
    throws IOException, RecordException {
        super();
        LineIOProvider ioProvider = new LineIOProvider();
        AbstractLineReader reader = ioProvider.getLineReader(layout.getFileStructure());

        reader.open(infile, layout);

        copyFile(reader, outfile, sep);
    }


    /**
     * Copy a File described by a layout to a CSV file
     *
     * @param reader input reader
     * @param outfile output file
     * @param sep field seperator
     */
    public Fix2Csv(final AbstractLineReader reader,
            	   final String outfile,
            	   final String sep) {
        super();
        try {
            copyFile(reader, outfile, sep);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Copy a file describe by a record-layout to a CSV file
     *
     * @param reader input reader
     * @param outfile output file
     * @param sep field seperator
     *
     * @throws IOException any error that occurs
     */
    private void copyFile(AbstractLineReader reader, String outfile, String sep)
    throws IOException {
        AbstractLine line;
        int idx, i;
        LayoutDetail layout = reader.getLayout();

        RecordDetail rec = layout.getRecord(0);
        FileWriter fileWriter = new FileWriter(outfile);
        BufferedWriter writer = new BufferedWriter(fileWriter);

        writer.write(fixName(rec.getField(0).getName()));
        for (i = 1; i < rec.getFieldCount(); i++) {
            writer.write(sep + fixName(rec.getField(i).getName()));
        }
        writer.newLine();

        while ((line = reader.read()) != null) {
            idx = line.getPreferredLayoutIdx();

            writer.write(line.getField(idx, 0).toString());
            if (idx >= 0) {
                for (i = 1; i < layout.getRecord(idx).getFieldCount(); i++) {
                    writer.write(sep + line.getField(idx, i));
                }
            }
            writer.newLine();
        }

        writer.close();
        reader.close();
    }


    /**
     * Convert Cobol/RecordEditor Name to standard name
     *
     * @param name current field name
     *
     * @return new name
     */
    private static String fixName(String name) {
        StringBuffer newName = new StringBuffer(name);

        replace(newName, "-", "_");
        replace(newName, " ", "_");

        return newName.toString();
    }


    /**
     * Replaces on string with another in a String bugffer
     *
     * @param in String buffer to be updated
     * @param from seqarch string
     * @param to replacement text
     */
    private static void replace(StringBuffer in, String from, String to) {
        int start, j;

        j = 0;
        start = in.indexOf(from, 0);
        while (start > 0) {
            j += 1;
            in.replace(start, start + 1, to);
            start = in.indexOf(from, start + 1);
        }
    }

}
