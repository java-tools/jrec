/*
 * @Author Bruce Martin
 * Created on 2/09/2005
 *
 * Purpose:
 * example of running the RecordEditor using the Cobol Copybook's
 * instead of using the Layout Database (i.e. program uses the
 * Class CobolCopybook instead of class CopyBookDbReader)
 *
*
 * Requirements:
 *
 * 1) Check the values in Constants.java are correct !!!
 *
 */
package net.sf.RecordEditor.examples;

import net.sf.RecordEditor.edit.EditRec;


/**
 * example of running the RecordEditor using the Cobol Copybook's
 * instead of using the Database (i.e. program uses the
 * Class CobolCopybook instead of class CopyBookDbReader)
 *
 * @author Bruce Martin
 *
 */
public class XmplEditViaCobol  {


    /**
     * Example of defining your own Types
     * @param args program arguments
     */
    public static void main(String[] args) {

        try {
            CobolCopybookReader copybook = new CobolCopybookReader();

            new EditRec("", 1, copybook); // starting the record editor

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
