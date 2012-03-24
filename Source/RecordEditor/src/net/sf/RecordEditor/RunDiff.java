/*
 * @Author Bruce Martin
 * Created on 21/01/2007 for version 0.60
 *
 * Purpose:
 * run the Full Editor
 */
package net.sf.RecordEditor;

import net.sf.RecordEditor.utils.Run;

/**
 * run the Full Editor
 *
 * @author Bruce Martin
 *
 */
public final class RunDiff {




    /**
     * run the full record editor
     * @param args program arguments
     */
    public static void main(String[] args) {
        new Run(Run.SYSTEM_JARS_FILENAME,
                null,
                "net.sf.RecordEditor.diff.CompareDBLayout",
                args);
    }
}
