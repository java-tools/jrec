/*
 * @Author Bruce Martin
 * Created on 25/01/2007 for version 0.60
 *
 * Purpose:
 * Store properties etc. Used as a parameter by many of EditOption
 * classes
 *
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - Jars classes added
 */
package net.sf.RecordEditor.editProperties;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Properties;

import javax.swing.JTextArea;

import net.sf.RecordEditor.utils.common.Parameters;

/**
 * Store properties etc. Used as a parameter by many of EditOption
 * classes
 *
 * @author Bruce Martin
 *
 */
public final class EditParams {

    private static final String LOOKS_PREFIX = "looks";
    private static final String ICON_PREFIX  = "icon";
    protected Properties properties; // = Parameters.readProperties();
    protected boolean propertiesChanged = false;
    protected JTextArea msgFld = new JTextArea("");

    protected JarGroup     jdbcJars = new JarGroup();
    protected JarGroup   systemJars = new JarGroup();
    protected JarGroup optionalJars = new JarGroup();
    protected JarGroup     userJars = new JarGroup();

    protected String looksJar = "";
    protected String iconJar  = "";


    /**
     * Edit Options Common paramters. This class is passed to all the
     * sub panels
     */
    protected EditParams() {
        super();

        properties = Parameters.readProperties();
        System.out.println("Read Properties !!! " + (properties == null));
        if (properties == null) {
            properties = new Properties();
        }

        init_100_LoadJars();
    }


    /**
     * Load the Jar file
     */
    private void init_100_LoadJars() {
        try {
            FileReader inReader
            	= new FileReader(CommonCode.FULL_EDITOR_JAR_FILE);
            BufferedReader in = new BufferedReader(inReader);
            String type, jar;
            int j;

            while ((jar = in.readLine()) != null) {
                if (! jar.trim().startsWith("#")) {
                    type = "";
                    if ((j = jar.indexOf('\t')) >= 0) {
                        type = jar.substring(0, j);
                        jar = jar.substring(j + 1);
                    }
                    init_110_StoreJar(type, jar);
                }
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            msgFld.setText(e.getMessage());
        }
    }


    /**
     * Store 1 record from the Jar file into the appropriate holder
     *
     * @param type Type of jar
     * @param jar Jar file
     */
    private void init_110_StoreJar(String type, String jar) {
        String typeLC = type.toLowerCase();

        //System.out.println("~~> " + type + " | " + jar);
        if (typeLC.startsWith("jdbc")) {
            jdbcJars.add(type, jar, "");
        } else if (typeLC.startsWith("cb2xml")) {
            systemJars.add(type, jar, "This jar converts Cobol Copybooks from XML, "
                    + "its used when importing Cobol copybooks into the record editor");
        } else if (typeLC.startsWith("velocity")) {
            systemJars.add(type, jar, "This jar holds part of Velocity (a template engine for java). ");
        } else if (typeLC.startsWith("stax")) {
            systemJars.add(type, jar, "This is the Streaming XML API Jar (StAX)");
        } else if (typeLC.startsWith("zdate")) {
            systemJars.add(type, jar, "This jar holds the date field code.");
        } else if (typeLC.startsWith("jibx")) {
            systemJars.add(type, jar, "Holds XML interface code");
        } else if (typeLC.startsWith("jlibdiff")) {
            systemJars.add(type, jar, "Holds Compare library code");
        } else if (typeLC.startsWith("optional")) {
            optionalJars.add(type, jar, "");
        } else if (typeLC.startsWith("user")) {
            userJars.add(type, jar, "");
        } else if (typeLC.startsWith(LOOKS_PREFIX)) {
            looksJar = jar;
        } else if (typeLC.startsWith(ICON_PREFIX)) {
            iconJar = jar;
        } else {
            systemJars.add(type, jar, "");
        }
    }

    /**
     * Write the jar files
     */
    public void writeJarFiles() {

        w1100_writeOneJarFile(CommonCode.FULL_EDITOR_JAR_FILE, true);
        w1100_writeOneJarFile(CommonCode.EDITOR_JAR_FILE, false);
    }

    /**
     * Write a single jar files
     * @param filename file to write
     * @param full wether to write all groups
     */
    private void w1100_writeOneJarFile(String filename, boolean full) {

        try {
            CommonCode.renameFile(filename);
            Writer w = new FileWriter(filename);
            BufferedWriter writer = new BufferedWriter(w);

            w1110_writeOneJarGroup(writer, this.systemJars, full);
            w1110_writeOneJarGroup(writer, this.jdbcJars, true);

            w1110_writeOneJarGroup(writer, this.userJars, true);
            if (full) {
                w1110_writeOneJarGroup(writer, this.optionalJars, full);
            }

            w1120_writeSingleJar(writer, LOOKS_PREFIX, looksJar);
            w1120_writeSingleJar(writer, ICON_PREFIX,  iconJar);

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            msgFld.setText(e.getMessage());
        }
    }


    /**
     * Writes 1 group of jars
     * @param writer writer to write the jars on
     * @param jars jars to be written
     * @param full wether all jars should be written
     * @throws IOException any IO error that occurs
     */
    private void w1110_writeOneJarGroup(BufferedWriter writer, JarGroup jars, boolean full)
    throws IOException {

        for (int i = 0; i < jars.jars.length; i++) {
            if (jars.jars[i] != null) {
                if (jars.jars[i][0] == null) {
                    jars.jars[i][0] = "";
                }
                if (jars.jars[i][1] == null) {
                    jars.jars[i][1] = "";
                }
                if ((! "".equals(jars.jars[i][1]))
                &&  (full || (jars.jars[i][1].indexOf("cb2xml") < 0))) {
                    writer.write(jars.jars[i][0] + "\t" + jars.jars[i][1]);
                    writer.newLine();
                }
            }
        }
    }


    /**
     * Writes a single jar to the jar file
     * @param writer output writer
     * @param prefix jar name
     * @param jar jar file
     * @throws IOException any IO error
     */
    private void w1120_writeSingleJar(BufferedWriter writer, String prefix, String jar)
    throws IOException {

        if (! "".equals(jar)) {
            writer.write(prefix + "\t"  + jar);
            writer.newLine();
        }
    }



    /**
     * write the properties back to the external file
     *
     */
    public final void writeProperties() {

        try {
            CommonCode.renameFile(Parameters.getPropertyFileName());
            properties.store(
                new FileOutputStream(Parameters.getPropertyFileName()),
                "RecordEditor");
            Parameters.setProperties(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param key property name or key
     * @return requested property
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * @param key property name or key
     * @param defaultValue default value for the property
     * @return requested property
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * @param key property name or key
     * @param value value to be assigned to this key
     * @return value
     */
    public Object setProperty(String key, String value) {
        return properties.setProperty(key, value);
    }

    /**
     * @param key property name or key to be removed
     * @return ...
     */
    public Object remove(Object key) {
        return properties.remove(key);
    }
}
