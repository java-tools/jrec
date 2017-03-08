/*
 * @Author Bruce Martin
 * Created on 14/10/2005
 *
 * Purpose: Parse program arguments
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *   - changed var initialRow, dfltFile to private and added get...
 *     methods
 */
package net.sf.RecordEditor.utils.edit;

import java.io.File;

/**
 * Parse program arguments
 *
 * @author Bruce Martin
 *
 */
public class ParseArgs {
	private static final int IS_A_FILE = 0;
	private static final int IS_A_LINE = 3;
	private static final int IS_A_SCHEMA = 4;
	private static final int IS_A_DB = 5;
	private static final int IS_A_PARAM = 6;
	private static final int IS_UNKNOWN = -121;

	private int  initialRow = 1;
	private String dfltFile = "";
	private String schemaName = "";
	private String db = "";
	private String paramFilename = "";
	private boolean overWiteOutputFile = false;
	
	public final boolean edit;

	
	    
	public ParseArgs(String fileName, String schemaName, int initialRow) {
		
		if (initialRow > 1) {
			this.initialRow = initialRow;
		}
		this.dfltFile = fileName;
		this.schemaName = schemaName;
		this.edit = false;
	}



	public ParseArgs(String schemaName) {
		this.schemaName = schemaName;
		this.edit = false;
	}
	
	/**
     * parse the supplied program arguments
     * @param args program arguments
     */
    public ParseArgs(final String[] args) {
        super();
		int i;
		int cat = IS_A_FILE;
		String ucArg;
		boolean edit = false;

		if ((args != null) && (args.length > 0)) {
			for (i = 0; i < args.length; i++) {
				ucArg = args[i].toUpperCase();
				if (ucArg.equals("-L")) {
					cat = IS_A_LINE;
				} else if (ucArg.equals("-F")) {
					cat = IS_A_FILE;
				} else if (ucArg.equals("-OVERWRITE")) {
					overWiteOutputFile = true;
					cat = IS_UNKNOWN;
				} else if (ucArg.equals("-EDIT")) {
					edit = true;
					cat = IS_UNKNOWN;
				} else if (ucArg.equals("-DB")) {
					cat = IS_A_DB;
				} else if (ucArg.equals("-PARAMETERFILE")) {
					cat = IS_A_PARAM;
				} else if (ucArg.equals("-SCHEMA") || ucArg.equals("-LAYOUT")) {
					cat = IS_A_SCHEMA;
				} else if (ucArg.startsWith("-") && ucArg.length() > 1) {
					cat = IS_UNKNOWN;
				} else {
					switch (cat) {
					case IS_A_LINE:
						try {
							initialRow = Integer.parseInt(args[i]);
						} catch (Exception e) {	}
						break;
					case IS_A_SCHEMA:
						schemaName = accum(schemaName, args[i]);
						break;
					case IS_A_DB:
						db = accum(db, args[i]);
						break;
					case IS_A_PARAM:
						paramFilename = accum(paramFilename, args[i]);
						break;
					case IS_A_FILE:
						dfltFile = accum(dfltFile, args[i]);
					}
				}
			}
			if (dfltFile != null) {
				File f = (new File(dfltFile));
				if (f.exists()) {
					try {
						dfltFile = f.getCanonicalPath();
					} catch (Exception e) {
					}
				} else {
					String s = System.getProperty("user.dir") 
							 + "/" + dfltFile;
					//System.out.println(">>>> " + s + " " + (new File(s)).exists());
					if ((new File(s)).exists()) {
						dfltFile = s;
					}
				}
			}
			//System.out.println("++>> " + dfltFile + " " + (new File(dfltFile)).exists());
		}
		this.edit = edit;
    }
    
    /**
	 * @return the overWiteOutputFile
	 */
	public final boolean isOverWiteOutputFile() {
		return overWiteOutputFile;
	}



	private String accum(String s1, String s2) {
    	if (s1 == "") {
    		s1 = s2;
    	} else {
    		s1 = s1 + " " + s2;
    	}
    	return s1;
    }

    /**
     * @return Returns the dfltFile.
     */
    public String getDfltFile() {
        return dfltFile;
    }
    

    public String getSchemaName() {
		return schemaName;
	}



	/**
     * @return Returns the initialRow.
     */
    public int getInitialRow() {
        return initialRow;
    }



	/**
	 * @return the db
	 */
	public final String getDb() {
		return db;
	}



	/**
	 * @return the systemParam
	 */
	public final String getParamFilename() {
		return paramFilename;
	}


}
