/*
 * @Author Bruce Martin
 * Created on 25/05/2005
 *
 * Purpose:
 */
package net.sf.RecordEditor.test.record;

import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.Line;
import net.sf.RecordEditor.test.TstConstants;
import net.sf.RecordEditor.utils.CopyBookDbReader;
import net.sf.RecordEditor.utils.common.Common;
import junit.framework.TestCase;

/**
 * Tests Text Lines
 *
 * @author Bruce Martin
 *
 */
public class TstLineText extends TestCase {

    private CopyBookDbReader copybookInt = new CopyBookDbReader();

    private static String poCopyBookName = "ams PO Download";
    private static String copyBookName   = "ams PO Download: Header";
    private static String amsPoHeader =
        "H1453490000006060286225      040909        00  200 0501020501075965        LADIES KNICFT";

    private static LayoutDetail copyBook = null;

    private Line line;

    /**
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        if (copyBook == null) {
            Common.setConnectionId(TstConstants.DB_INDEX);
            copyBook = copybookInt.getLayout(copyBookName);
        }

        line = new Line(copyBook, amsPoHeader);
    }

    /**
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();

        line = null;
    }


    /**
     * Check the Line.getField function
     */
    public void testGetField() {

        assertEquals("GetField - Character Field ", "H1", line.getField(0, 0));

        assertEquals("GetField - Numeric Field assumed Decimal " + line.getField(0, 1),
                	 "45.349", line.getField(0, 1));

        assertEquals("GetField - Numeric Field ", "6060", line.getField(0, 2));

        assertEquals("GetField - Numeric Field ", "286225", line.getField(0, 3));
    }


    /**
     * check Line.getFieldText
     */
    public void testGetFieldText() {

        assertEquals("GetFieldText - Character Field ", "H1", line.getFieldText(0, 0));

        assertEquals("GetFieldText - Numeric Field assumed Decimal " + line.getFieldText(0, 1),
                	"45349", line.getFieldText(0, 1).toString());

        assertEquals("GetFieldText - Numeric Field ", "0000006060", line.getFieldText(0, 2));

        assertEquals("GetFieldText - Numeric Field " + line.getFieldText(0, 3),
                	 "286225", line.getFieldText(0, 3));

        assertEquals("GetFieldText 4 - " + line.getFieldText(0, 4),
                "040909", line.getFieldText(0, 4));
        assertEquals("GetFieldText 4 - " + line.getFieldText(0, 5),
                "", line.getFieldText(0, 5));
        assertEquals("GetFieldText 4 - " + line.getFieldText(0, 6),
                "00", line.getFieldText(0, 6));
        /*System.out.println("\"" + line.getFieldText(0, 4) + "\"");
        System.out.println("\"" + line.getFieldText(0, 5) + "\"");
        System.out.println("\"" + line.getFieldText(0, 6) + "\"");*/
    }


    /**
     * Check GetFieldHex (retrieve a valuer as Hex)
     */
    public void testGetFieldHex() {

        assertEquals("GetFieldHex - GetHex 0 ", "4831", line.getFieldHex(0, 0));
        assertEquals("GetFieldHex - GetHex 1 ", "3435333439", line.getFieldHex(0, 1));
        assertEquals("GetFieldHex - GetHex 2 ", "30303030303036303630", line.getFieldHex(0, 2));
        assertEquals("GetFieldHex - GetHex 3 ", "323836323235202020202020", line.getFieldHex(0, 3));
        assertEquals("GetFieldHex - GetHex 4 ", "303430393039", line.getFieldHex(0, 4));
        assertEquals("GetFieldHex - GetHex 5 ", "2020202020202020", line.getFieldHex(0, 5));
    }


    /**
     * Check Line.getPreferredLayout()
     */
    public void testGetPreferredLayout() {

         LayoutDetail poCopyBook = copybookInt.getLayout(poCopyBookName);

         line = new Line(poCopyBook, amsPoHeader);

         String s = poCopyBook.getRecord(line.getPreferredLayoutIdx()).getRecordName();

         assertEquals("getPrefered Layout ", copyBookName, s);

    }


    /**
     * Test Line.setField
     *
     * @throws RecordException conversion error (should not occur)
     */
    public void testSetField() throws RecordException {
        String s = "112233";
        int fld2set = 4;

        line.setField(0, fld2set, s);
        assertEquals("set Field ", s, line.getField(0, fld2set));

        s = "123";
        fld2set = 3;

        line.setField(0, fld2set, s);
        assertEquals("set Field- " + line.getField(0, fld2set),
                	 s, line.getField(0, fld2set));

        fld2set = 2;

        line.setField(0, fld2set, s);
        assertEquals("set Field- " + line.getField(0, fld2set),
                	 s, line.getField(0, fld2set));

        fld2set = 1;

        s = "12";
        line.setField(0, fld2set, s);
        assertEquals("set Field- " + line.getField(0, fld2set),
                	 s + ".000", line.getField(0, fld2set));

        s = "12.3";
        line.setField(0, fld2set, s);
        assertEquals("set Field- " + line.getField(0, fld2set),
                	 s + "00", line.getField(0, fld2set));
    }




    /**
     * Test Line.setFieldConversion
     */
    public void testSetFieldConversion() {

        checkSizeError(1, "123", "1) Did not get error when setting Assumed-Decimal field");
        checkSizeError(2, "1234567890123", "2) Did not get error when mumeric field set");
        //checkSizeError(4, "1234567", "3) Did not get error when text field is set");

        checkConversionError(1, "3) Error no error generated for non numeric value");
        checkConversionError(2, "3) Error no error generated for non numeric value");
    }


    /**
     * Test setting a field with a value that is to long
     *
     * @param fld2set field to use
     * @param value value to assign to the supplied field
     * @param msg error message to use if size error is not thrown
     */
    public void checkSizeError(int fld2set, String value, String msg) {

        try {
            line.setField(0, fld2set, value);

            throw new AssertionError(msg);
        } catch (RecordException e) {
        }
    }


    /**
     * Test setting a numeric field that is not numeric
     *
     * @param fld2set field to use
     * @param msg error message to use if size error is not thrown
     */
    public void checkConversionError(int fld2set, String msg) {

        try {
            line.setField(0, fld2set, "a");

            throw new AssertionError(msg);
        } catch (RecordException e) {
        }
    }


    /**
     * test get record
     *
     */
    public void testGetRecord() {
        assertEquals("getRecord Error ", amsPoHeader, new String(line.getData()));
    }

}
