/*
 * @Author Bruce Martin
 * Created on 25/01/2007 for version 0.60
 *
 * Purpose:
 * Common bits of code for the Options Editor + constants
 */
package net.sf.RecordEditor.editProperties;

import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Properties;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.Parameters;
import net.sf.RecordEditor.utils.swing.SwingUtils;

/**
 * Common bits of code for the Options Editor + constants
 *
 * @author Bruce Martin
 *
 */
public final class CommonCode {
    public static final int TIP_HEIGHT = SwingUtils.STANDARD_FONT_HEIGHT * 11;
    public static final String FULL_EDITOR_JAR_FILE = Parameters.getJarListFileDirectory()
    												+ Common.FILE_SEPERATOR
    												+ "FullEditorFiles.txt";
    public static final String      EDITOR_JAR_FILE = Parameters.getJarListFileDirectory()
													+ Common.FILE_SEPERATOR
													+ "SmallEditorFiles.txt";


    /**
     * Rename a file
     * @param fileName file to be renamed
     */
    public static final void renameFile(String fileName) {
//        File f = new File(fileName);
//        String newFileName = fileName + "~";
//        File fNew;
//
//        fNew = new File(newFileName);
//
//        if (fNew.exists()) {
//            fNew.delete();
//        }
//
//        f.renameTo(fNew);
    	
    	Parameters.renameFile(fileName);
    }


    /**
     * Creates a connection to a specified DB
     *
     * @param sourceName Name of the DB
     * @param driver driver name
     * @param source name of the source
     * @param user user name to use
     * @param password password of the user
     * @param jarName name of the jar that holds the DB Driver
     *
     * @return the requested connection
     *
     * @throws Exception any errors that occured while trying to
     * create the DB connection
     */
    public static Connection getConnection(final String sourceName,
            final String driver,
            final String source,
            final String user,
            final String password,
            final String jarName) throws Exception {

        Connection dbConnection = null;

        if (jarName == null || "".equals(jarName.trim())) {
            Class.forName(driver);
        } else {
            URL[] urls = {new URL("file:" + Parameters.expandVars(jarName))};
            URLClassLoader urlLoader = new URLClassLoader(urls);
            Driver d = (Driver) Class.forName(driver, true, urlLoader).newInstance();
//            System.out.println(d.acceptsURL(source) + " " + source);
            Properties p = new Properties();
            
            p.setProperty("user", user);
            p.setProperty("password", password);
            return d.connect(source, p);
        }

        if ((user == null) || ("".equals(user))) {
            dbConnection = DriverManager.getConnection(source);
        } else {
            dbConnection =
                DriverManager.getConnection(source,
                        user,
                        password);
        }
        return dbConnection;
     }
}
