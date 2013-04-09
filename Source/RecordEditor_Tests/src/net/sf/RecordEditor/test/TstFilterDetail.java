/*
 * @Author Bruce Martin
 * Created on 3/08/2005
 *
 * Purpose:
 */
package net.sf.RecordEditor.test;


import javax.swing.table.AbstractTableModel;

import junit.framework.TestCase;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.RecordEditor.re.file.filter.FilterDetails;
import net.sf.RecordEditor.utils.CopyBookDbReader;
import net.sf.RecordEditor.utils.common.Common;

/**
 *
 *
 * @author Bruce Martin
 *
 */
public class TstFilterDetail extends TestCase {

	private int dbIdx = TstConstants.DB_INDEX;

	private CopyBookDbReader copybookInt = new CopyBookDbReader();

    private FilterDetails filter;
    private AbstractTableModel fieldList;
    //private AbstractTableModel filterFieldList;
    //private AbstractTableModel layoutList;


    //private static final String TMP_DIRECTORY = TstConstants.TEMP_DIRECTORY;
    //private boolean writeFiles = true;

    private static LayoutDetail poCopyBook = null;
    private final String poCopybookName = "ams PO Download";
    //private final String poFileName = TMP_DIRECTORY + poCopybookName + ".tmp";


    /**
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        if (poCopyBook == null) {
            Common.setConnectionId(dbIdx);
            poCopyBook = copybookInt.getLayout(poCopybookName);
        }

        filter = new FilterDetails(poCopyBook, FilterDetails.FT_NORMAL);
        fieldList = filter.getFieldListMdl();
        //filterFieldList = filter.getFilterFieldListMdl();
        //layoutList = filter.getLayoutListMdl();
    }

    /**
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();

        filter = null;
    }


    /**
     * Test if Field Map is generated correctly
     *
     */
    public void testGetFieldMap() {

        fieldList.setValueAt(Boolean.FALSE, 0, 1);
        fieldList.setValueAt(Boolean.FALSE, 2, 1);
        fieldList.setValueAt(Boolean.FALSE, 4, 1);

        int[][] list = filter.getFieldMap();

        /*System.out.println();
        for (i = 0; i < list[0].length; i++) {
            System.out.print("  " + i + " ~ " + list[0][i]);
        }
        System.out.println();*/

        assertEquals("The Index was " + list[0][0] + " and not 1", 1, list[0][0]);
        assertEquals("The Index was " + list[0][1] + " and not 3", 3, list[0][1]);
        assertEquals("The Index was " + list[0][2] + " and not 5", 5, list[0][2]);
     }


    /**
     * checking layout index is zero
     *
     */
    public void testGetLayoutIndex() {

        assertEquals("The layout Index was " + filter.getLayoutIndex()
                + " and not 0", 0, filter.getLayoutIndex());
    }


    /**
     * checking setLayoutIndex works
     *
     */
    public void testSetLayoutIndex() {

        checkSetLayout(0, "Pack Qty");
        checkSetLayout(1, "Sequence Number");
        checkSetLayout(2, "DC Number 1");

    }


    /**
     * Checks layout set correctly
     * @param layout new layout
     * @param field expected first field
     */
    private void checkSetLayout(int layout, String field) {

        filter.setLayoutIndex(layout);
        assertEquals("The layout Index was " + filter.getLayoutIndex()
                + " and not " + layout, layout, filter.getLayoutIndex());

        String fld = fieldList.getValueAt(1, 0).toString();
        assertEquals("Expected field '" + field + "' but got " + fld,
                field, fld);
    }


    /**
     * Test wether include is set correctly
     *
     */
    public void testIsInclude() {

        //Object obj = fieldList.getValueAt(0, 1);
        //System.out.println(obj + " " + obj.getClass());
        Boolean in = (Boolean) fieldList.getValueAt(0, 1);
        assertTrue("Expecting include = True", in.booleanValue());

        fieldList.setValueAt(Boolean.FALSE, 0, 1);
        in = (Boolean) fieldList.getValueAt(0, 1);
        assertFalse("Expecting include = False", in.booleanValue());
    }

}
