/*
 * @Author Bruce Martin
 * Created on 25/01/2007 for version 0.60
 *
 * Purpose:
 * Store a group of related jar files. This class is used by the
 * editor screens
 */
package net.sf.RecordEditor.re.editProperties;

/**
 * Store a group of related jar files. This class is used by the
 * editor screens
 *
 * @author Bruce Martin
 *
 */
public class JarGroup {

    public static final  int TYPE_COLUMN = 0;
    public static final  int JAR_COLUMN  = 1;
    public static final  int DESCRIPTION_COLUMN = 2;

    protected int count = 0;
    protected String[][] jars = new String[30][3];


    /**
     * Add a column to the group of jars
     * @param type Type of jar
     * @param jar jar file name
     * @param description description of the jar
     */
    public void add(String type, String jar, String description) {
        if (count < jars.length) {
            jars[count] = new String[3];
            jars[count][TYPE_COLUMN] = type;
            jars[count][JAR_COLUMN] = jar;
            jars[count++][DESCRIPTION_COLUMN] = description;
        }
    }

}
