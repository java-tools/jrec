/*
 * @Author Bruce Martin
 * Created on 20/04/2005
 *
 * Purpose:
 */
package net.sf.RecordEditor.layoutEd;

import net.sf.RecordEditor.utils.common.Common;

/**
 * This class shuts Down HSQL DB databases
 *
 * @author Bruce Martin
 *
 */
public final class ShutdownHSQLDB {

    /**
     * Static cass to sgutdon DB
     */
    private ShutdownHSQLDB() {
        super();
    }
    /**
     * Shutting down HSQL DB databases
     *
     * @param args program arguments (non at present
     */
    public static void main(String[] args) {
        Common.shutdownHSQLdb();
    }
}
