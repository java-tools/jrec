/*
 * Created on 23/09/2004
 *
 * Common stuff for the layout editor
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *   - moved code/constants to either Common class
 *     or ExtendedRecord class
 */
package net.sf.RecordEditor.layoutEd.utils;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;



/**
 * Common stuff for the layout editor
 *
 * @author Bruce Martin
 *
 */
public final class ReadFile {

	private static String cb2XmlHtml = null;

	private static ReadFile lc = new ReadFile();
	


	/**
	 * Read the contents of a resource into a String
	 *
	 * @param resourceName Resource Name
	 *
	 * @return resource as a string
	 */
     private String readContent(final String resourceName) {
        String result = "";  // Error case default.

        StringBuffer buffer = new StringBuffer();
        InputStream is = getClass().getResourceAsStream(resourceName);
        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            for (String line = br.readLine(); line != null; line = br.readLine()) {
                buffer.append(line).append("\n");
            }

            br.close();
            isr.close();
            is.close();

            result = buffer.toString().trim();
        } catch (Exception ex) {
            System.out.println("Could not fetch the text resource ["
                             + resourceName + "].");
            ex.printStackTrace();
        }

        return (result);
    }


    /**
     * @return Returns the cb2XmlHtml.
     */
    public static final String getCb2XmlHtml() {

        if (cb2XmlHtml == null) {
            cb2XmlHtml = lc.readContent("cb2XmlNotes.html");
        }

        return cb2XmlHtml;
    }
 }
