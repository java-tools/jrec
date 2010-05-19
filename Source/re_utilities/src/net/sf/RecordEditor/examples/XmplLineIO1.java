/*
 * @Author Bruce Martin
 * Created on 9/09/2005
 *
 * Purpose:
 *   This Example program demonstrates Reading a file using Line Based
 * Routines
 *
 * Requirements:
 *
 * 1) Check the values in Constants.java are correct !!!
 *
 */
package net.sf.RecordEditor.examples;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.IO.TextLineReader;
import net.sf.RecordEditor.utils.CopyBookDbReader;

/**
 * This Example program demonstrates Reading a file using Line Based
 * Routines
 *
 * @author Bruce Martin
 *
 */
public final class XmplLineIO1 {

    private String poFile           = TstConstants.RECORDEDIT_INSTALL_DIRECTORY
    								+ "SampleFiles\\Ams_PODownload_20050101.txt";

    private CopyBookDbReader copybook
                                    = new CopyBookDbReader();
    private LayoutDetail copyBookPO  = copybook.getLayout("ams PO Download");

    private int poRecordId          = copyBookPO.getRecordIndex("ams PO Download: Header");
    private RecordDetail poRecord   = copyBookPO.getRecord(poRecordId);

    private int fieldPO             = poRecord.getFieldIndex("PO");
    private int fieldVendor         = poRecord.getFieldIndex("Vendor");
    private int fieldDepartment     = poRecord.getFieldIndex("Department");
    private int fieldDepartmentName = poRecord.getFieldIndex("Department Name");
    private int fieldRecieptDate    = poRecord.getFieldIndex("Expected Reciept Date");
    private int fieldOrderType      = poRecord.getFieldIndex("Order Type");

    private TextLineReader reader   = new TextLineReader();

    private AbstractLine line;

    /**
     *
     */
    private XmplLineIO1() {
        super();

        int lineNumber = 0;

        try {
            System.out.println("     Department        PO  Type  Date  Vendor");
            System.out.println("  ===========================================");

            reader.open(poFile, copyBookPO);

            while ((line = reader.read()) != null) {
                lineNumber += 1;
                if (line.getPreferredLayoutIdx() == poRecordId) {
                    System.out.println(
                            "  " + line.getField(poRecordId, fieldDepartment)
                          + "  " + line.getField(poRecordId, fieldDepartmentName)
                          + "  " + line.getField(poRecordId, fieldPO)
                          + "  " + line.getField(poRecordId, fieldOrderType)
                          + "  " + line.getField(poRecordId, fieldRecieptDate)
                          + "  " + line.getField(poRecordId, fieldVendor)
                    );
                }
            }

            reader.close();

        } catch (Exception e) {
            System.out.println("Error Line " + lineNumber + " " + e.getMessage());
            System.out.println();
            System.out.println();
            e.printStackTrace();
        }

        System.out.println();
        System.out.println("Lines Read " + lineNumber + " PO Field " + fieldPO);
    }


    /**
     * LineIO example
     *
     * @param args program arguments
     */
    public static void main(String[] args) {
        new XmplLineIO1();
    }
}
