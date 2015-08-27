/*
 * @Author Bruce Martin
 * Created on 21/01/2007
 *
 * Purpose:
 */
package net.sf.RecordEditor;

import net.sf.RecordEditor.utils.Run;

/**
 * run the Full Editor
 *
 * @author Bruce Martin
 *
 */
public final class RunEditor {

    private static final String[] JARS = {
            "<lib>/JRecord.jar",
            "<lib>/RecordEdit.jar",
            "<lib>/properties.zip"
    };
 
    /**
     * Default constructor; stop anyone creating this class
     *
     */
//    private RunEditor() {
//    }


    /**
     * run the full record editor
     * @param args program arguments
     */
    public static void main(String[] args) {
        new Run(null,
        		"SmallEditorFiles.txt",
                JARS,
                "net.sf.RecordEditor.edit.EditRec",
                args);
    }
}
