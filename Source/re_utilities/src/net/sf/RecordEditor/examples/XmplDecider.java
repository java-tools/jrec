/*
 * @Author Bruce Martin
 * Created on 12/09/2005
 *
 * Purpose:  Example of Record decider class
 */
package net.sf.RecordEditor.examples;

import net.sf.RecordEditor.edit.EditRec;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.RecordDecider;
import net.sf.RecordEditor.utils.CopyBookDbReader;
import net.sf.RecordEditor.utils.common.Common;

/**
 * Example of using a Record Decider.
 * 
 * Normally the RecordEditor decides what type of record it is
 * based on one field. For more complicated record-layouts you can
 * use "<b>RecordDecider</b>" to decide which record should be used
 * to display the line.
 *
 *
 * This example uses the following Cobol copybook:
 *

        01  Product-Header.
           03 Record-Type                  PIC X.
              88 Header-Record                Value 'H'.
              88 Detail-Record                Value 'D'.
           03 Header-Details               PIC X(30).

        01  Product-Detail-1.
           03 Record-Type                  PIC X.
           03 Product-1                    PIC 9(8).
           03 Update-What                  PIC X.
              88 Update-Product-Details      Value 'P'.
              88 Update-Department-Details   Value 'D'.
           03 Product-Details              PIC X(40).

        01  Product-Detail-2.
           03 Record-Type                  PIC X.
           03 Product-2                    PIC 9(8).
           03 Update-What                  PIC X.
           03 Department-Details.
              05 Department-Number         PIC 9(4).
              05 Department-Name           PIC X(30).

 *
 * @author Bruce Martin
 *
 */
public class XmplDecider implements RecordDecider {

    private static final String HEADER_RECORD  = "H";
    private static final String DETAIL_RECORD  = "D";
    private static final String TRAILER_RECORD = "T";
    private static final String PRODUCT_RECORD = "P";
    private static final String DEPT_RECORD    = "D";

    private static final String HEADER_RECORD_NAME  = "XMPLDECIDER-Product-Header";
    private static final String PRODUCT_RECORD_NAME = "XMPLDECIDER-Product-Detail-1";
    private static final String DEPT_RECORD_NAME    = "XMPLDECIDER-Product-Detail-2";

    private static final int DETAIL_TYPE_FIELD = 2;


    /**
     * This method decides what type of
     * @see net.sf.JRecord.Details.RecordDecider#getPreferedIndex(record.Line)
     */
    public int getPreferedIndex(AbstractLine line) {
        int ret = Common.NULL_INTEGER;
        int headerIndex   = line.getLayout().getRecordIndex(HEADER_RECORD_NAME);
        int productIndex  = line.getLayout().getRecordIndex(PRODUCT_RECORD_NAME);
        int deptIndex     = line.getLayout().getRecordIndex(DEPT_RECORD_NAME);

        try {
            String recordType = line.getField(0, 0).toString();
            if (HEADER_RECORD.equals(recordType)) {
                ret = headerIndex;
                //System.out.println("Header " + line.getField(0, 0));
            } else if (DETAIL_RECORD.equals(recordType)) {
                String productType = line.getField(productIndex,
                        DETAIL_TYPE_FIELD).toString();
                //System.out.print("Detail >" + productType + "< "
                //        + DEPT_RECORD.equals(productType));
                if (PRODUCT_RECORD.equals(productType)) {
                    ret = productIndex;
                } else if (DEPT_RECORD.equals(productType)) {
                    ret = deptIndex;
                }
                //System.out.println(" >>> " + ret);
            } else if (TRAILER_RECORD.equals(recordType)) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }


    /**
     * Example of a Record Decider
     *
     * @param args program arguments
     */
    public static void main(String[] args) {
        CopyBookDbReader copybook = new CopyBookDbReader();

        copybook.registerDecider("XmplDecider", new XmplDecider());

        new EditRec("", 1, copybook);
    }

}
