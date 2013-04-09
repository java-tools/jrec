/*
 * @Author Bruce Martin
 * Created on 2/09/2005
 *
 * Purpose: example of adding a Format to the Record Editor
 *
 * Requirements:
 *
 * 1) You must define the new format in the Format Table (in the LayoutEdit).
 * 2) You must then update a copybook XmplEditType1 to use the new copybooks
 * 3) Check the values in Constants.java are correct !!!
 */
package net.sf.RecordEditor.examples;

import net.sf.RecordEditor.edit.EditRec;
import net.sf.RecordEditor.re.jrecord.types.ReTypeManger;
import net.sf.RecordEditor.utils.CopyBookDbReader;

/**
 * example of adding a Format to the Record Editor
 *
 * To use this example you need to<ol compact>
 *   <li>Define the new format in the Format table of LayoutEdit.
 *   <li>Update a Layout (in LayoutEdit) to use the new types.
 *       I suggest updating Type "<b>ams PO Download</b>".
 * </eol>

 * @author Bruce Martin
 *
 */
public class XmplEditFormat1  {

    private static final int AMS_PO_FORMAT = 1001;
    private static final String AMS_PO_ID  = "AMS PO Type";

    private static final String[][] AMS_PO_ITEMS = {
    		{"H1", "Header Record"},
			{"D1", "Product Record"},
			{"S1", "Allocation Record"},
    };


    /**
     * Example of defining your own Types
     * @param args program arguments
     */
    public static void main(String[] args) {

        ReTypeManger typeManager = ReTypeManger.getInstance();
        CopyBookDbReader copybook = new CopyBookDbReader();
        FormatComboExample comboFormat = new FormatComboExample();

        	//   Register Combo Details
        comboFormat.register(AMS_PO_ID, AMS_PO_ITEMS);

        try {
            // register the new types  with the type manager
            typeManager.registerFormat(AMS_PO_FORMAT, comboFormat);

            new EditRec("", 1, copybook); // starting the record editor

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
