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
package net.sf.RecordEditor.layoutEd.Parameter;

/**
 * Common stuff for the layout editor
 *
 * @author Bruce Martin
 *
 */
public final class LayoutCommon {
	
	private static final char[] SEPS = {
		'\\', '/', '|', ',', ';', ':', '!', '.', '?', '@', '#', '$', '%', '^', '*',
		'(', ')', '-', '+', '=', '<', '>', '~', '`', '\t' 
	};


    
    public static final String buildCsvField(String[] fields) {
    	int i, j;
    	boolean ok;
    	char sep;
    	StringBuffer ret = new StringBuffer("");
    	
    	for (i = 0; i < SEPS.length; i++) {
    		ok = false;
    		for (j = 0; j < fields.length && ! ok; j++) {
    			ok = fields[j].indexOf(SEPS[i]) < 0;
    		}
    		if (ok) {
    			sep = SEPS[i];
    			for (j = 0; j < fields.length; j++) {
    				ret.append(sep).append(fields[j]);
    			}
    			ret.append(sep);
    			break;
    		}
    	}
    	
    	return ret.toString();
    }
}
