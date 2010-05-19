/*
 * @Author Bruce Martin
 * Created on 21/01/2007 for version 0.60
 *
 * Purpose:
 * run a class provided as a parameter to the program
 */
package net.sf.RecordEditor;

import net.sf.RecordEditor.utils.Run;

/**
 * run a class provided as a parameter to the program
 *
 * @author Bruce Martin
 *
 */
public final class RunClass {

    /**
     * run the full record editor
     * @param args program arguments
     */
    public static void main(String[] args) {
    	//System.out.print(args[0]);
    	String[] args1;
    	if (args == null || args.length < 2) {
    		args1 = new String[0];
    	} else {
   // 		int i = 0;
    		args1 = new String[args.length - 1];
    		
    		for (int i = 0; i < args.length - 1; i++) {
    			args1[i] = args[i+1];
    		}
    	}
        new Run("FullEditorFiles.txt",
                null,
                args[0],
                args1);
    }
}
