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
public final class RunProtoBufferEditor {


private static final String[] JARS = {
    "<lib>/JRecord.jar",
    "<lib>/RecordEdit.jar",
    "<lib>/properties.zip"
};


    /**
     * run the full record editor
     * @param args program arguments
     */
    public static void main(String[] args) {
        new Run(null,
        		Run.SYSTEM_JARS_FILENAME,
        		JARS,
                "net.sf.RecordEditor.ProtoBuf.ProtoBufEditor",
                args);
    }
}
