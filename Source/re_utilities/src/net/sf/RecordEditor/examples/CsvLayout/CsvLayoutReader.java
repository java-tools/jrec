/*
 * @Author Bruce Martin
 * Created on 7/02/2007
 *
 * Purpose:
 * Demonstrate writing a CopybookLoader (ie a class to read
 * a record layout or Copybook from an external file).
 */
package net.sf.RecordEditor.examples.CsvLayout;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.External.ExternalField;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.JRecord.Numeric.Convert;
import net.sf.JRecord.Types.Type;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.RecordException;

/**
 * This class reads a Record Layout (Copybook) stored in a  tab delimited file.
 * Fields in the file are<ol>
 * <li>Starting Position
 * <li>Length
 * <li>Number of places after the decimal point
 * <li>Field Name
 * <li>Field Type (String [or char], num, mainframe_zoned, fuji_zoned)
 * </ol>
 *
 * @author Bruce Martin
 *
 */
public class CsvLayoutReader implements CopybookLoader {

    private static HashMap typeConv = new HashMap();

    static {
        typeConv.put("string", new Integer(Type.ftChar));
        typeConv.put("char", new Integer(Type.ftChar));
        typeConv.put("num", new Integer(Type.ftNumRightJustified));
        typeConv.put("mainframe_zoned", new Integer(Type.ftZonedNumeric));
        typeConv.put("fuji_zoned", new Integer(Type.ftFjZonedNumeric));
    }

    /**
     * @see net.sf.RecordEditor.edit.loader.CopybookLoader#loadCopyBook(java.lang.String, int, int, java.lang.String, int, int, net.sf.RecordEditor.utils.log.AbsSSLogger)
     */
    public ExternalRecord loadCopyBook(String copyBookFile,
            int splitCopybookOption, int dbIdx, String font, int binFormat,
            int systemId, AbsSSLogger log) throws IOException, SAXException,
            ParserConfigurationException, RecordException {

        int rt = Constants.rtRecordLayout;
        if (binFormat == Convert.FMT_MAINFRAME) {
            rt = Constants.rtBinaryRecord;
        }

        ExternalRecord rec = ExternalRecord.getNullRecord(copyBookFile,
                rt,
                font);

        insertFields(rec, copyBookFile);

        return rec;
    }

    /**
     * Add fields to the copybook
     * @param rec copybook
     * @param copyBookFile copybook file
     */
    private void insertFields(ExternalRecord rec, String copyBookFile) {
        String s, name, typeStr;
        StringTokenizer t;
        ExternalField field;
        int pos, len, decimal, type;
        int i = 1;
        int inputLine = 1;
        try {
            BufferedReader r = new BufferedReader(new FileReader(copyBookFile));
            while ((s = r.readLine()) != null) {
                if (!s.trim().startsWith("#")) {
                    t = new StringTokenizer(s, "\t");

                    try {
                        pos  = Integer.parseInt(t.nextToken());
                        len  = Integer.parseInt(t.nextToken());
                        decimal = Integer.parseInt(t.nextToken());
                        name = t.nextToken();
                        typeStr = t.nextToken();

                        type = Type.ftChar;
                        if (typeStr != null && ! "".equals(typeStr)) {
                            typeStr = typeStr.toLowerCase();
                            if (typeConv.containsKey(typeStr)) {
                                type = ((Integer) typeConv.get(typeStr)).intValue();
                            }
                        }

                        field = new ExternalField(pos, len, name, "", type,
                                decimal, 0, "", "", "", i);
                        rec.addRecordField(field);
                        i += 1;
                    } catch (Exception e) {
                        System.out.println("Error Adding line " + inputLine
                                + ": " + e.getMessage());
                        e.printStackTrace();
                    }
                    inputLine += 1;
                }
            }
        } catch (Exception e) {
            System.out.println("Error Adding line " + inputLine
                    + " from file " + copyBookFile
                    + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}
