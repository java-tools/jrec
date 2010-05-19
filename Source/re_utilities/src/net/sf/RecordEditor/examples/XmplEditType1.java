/*
 * @Author Bruce Martin
 * Created on 2/09/2005
 *
 * Purpose:  example of adding a Type to the Record Editor
 *
 * Copybook: XmplEditType1
 *
 * Requirements:
 *
 * 1) You must define the 2 new types in the Type Table (in the LayoutEdit).
 * 2) You must then update copybook XmplEditType1 to use the new types
 * 3) Check the values in Constants.java are correct !!!
 *
 */
package net.sf.RecordEditor.examples;

import net.sf.RecordEditor.edit.EditRec;
import net.sf.RecordEditor.record.types.ReTypeManger;
import net.sf.RecordEditor.utils.CopyBookDbReader;


/**
 * example of adding a Type to the Record Editor
 *
 * To use this example you need to<ol compact>
 *   <li>Define the 2 new types in the Type table of LayoutEdit
 *   <li>Update a Layout (in LayoutEdit) to use the new types.
 *       I suggest updating Type XmplEditType1.
 * </eol>
 *
 * @author Bruce Martin
 *
 */
public class XmplEditType1  {

    private static final int CHECKBOX_TYPE = 1001;
    private static final int LOCATION_TYPE = 1002;


    /**
     * Example of defining your own Types
     * @param args program arguments
     */
    public static void main(String[] args) {

        ReTypeManger typeManager = ReTypeManger.getInstance();
        CopyBookDbReader copybook  = new CopyBookDbReader();
        TypeCheckBoxYN checkBox    = new TypeCheckBoxYN();
        TypeComboExample typeCombo = new TypeComboExample();

        try {
            // register the new types  with the type manager
            typeManager.registerType(CHECKBOX_TYPE, checkBox, checkBox);
            typeManager.registerType(LOCATION_TYPE, typeCombo, typeCombo);

            new EditRec("", 1, copybook); // starting the record editor

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
