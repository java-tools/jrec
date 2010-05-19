package net.sf.RecordEditor.utils;


/**
 * This interface provides a callback procedures to retrive
 * DB / File details + Create/Select layout
 *
 * @author Bruce Martin
 *
 */
public interface BasicLayoutCallback {

    /**
     * Tell the calling process of the new Layout
     * @param layoutId Record Id of the new record
     * @param layoutName new / selected Record Layout Name
     * @param filename filename used as a basis for the new
     * record layout
     */
    public abstract void setRecordLayout(int layoutId, String layoutName, String filename);
}
