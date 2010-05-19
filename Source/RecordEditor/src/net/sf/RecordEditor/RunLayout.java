/*
 * @Author Bruce Martin
 * Created on 21/01/2007 for version 0.60
 *
 * Purpose:
 * run the Record Layout Editor
 */
package net.sf.RecordEditor;

import net.sf.RecordEditor.utils.Run;

/**
 * run the Record Layout Editor
 *
 * @author Bruce Martin
 *
 */
public final class RunLayout {

//    private static final String[] JARS = {
//            "<lib>/JRecord.jar",
//            "<lib>/LayoutEdit.jar",
//            "<lib>/RecordEdit.jar",
//            "<lib>/properties.zip"
//    };


    /**
     * run the full record editor
     * @param args program arguments
     */
    public static void main(String[] args) {
        new Run("FullEditorFiles.txt",
                null,
                "net.sf.RecordEditor.layoutEd.LayoutEdit",
                args);
    }
}
