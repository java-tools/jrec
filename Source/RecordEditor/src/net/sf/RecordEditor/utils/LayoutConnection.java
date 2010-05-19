/*
 * @Author Bruce Martin
 * Created on 10/01/2007
 *
 * Purpose:
 */
package net.sf.RecordEditor.utils;

/**
 * This interface provides a callback procedures to retrive
 * DB / File details + Create/Select layout
 *
 * @author Bruce Martin
 *
 */
public interface LayoutConnection extends BasicLayoutCallback {

    /**
     * get the current Data Base Identifier
     * @return current Data Base Identifier
     */
    public abstract int getCurrentDbIdentifier();

    /**
     * get the current Data Base Name
     * @return current Data Base Name
     */
    public abstract String getCurrentDbName();

    /**
     * Get the current file name
     * @return current file name
     */
    public abstract String getCurrentFileName();
}
