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

/**
 * Parse program arguments
 *
 * @author Bruce Martin
 *
 */
public class ParseArgs {
	private static final int IS_A_FILE = 0;
	private static final int IS_A_LINE = 3;

	private int  initialRow = 1;
	private String dfltFile = "";

    /**
     * parse the supplied program arguments
     * @param args program arguments
     */
    public ParseArgs(final String[] args) {
        super();
		int i;
		int cat = IS_A_FILE;
		String ucArg;

		if ((args != null) && (args.length > 0)) {
			for (i = 0; i < args.length; i++) {
				ucArg = args[i].toUpperCase();
				if (ucArg.equals("-L")) {
					cat = IS_A_LINE;
				} else {
					if (cat == IS_A_LINE) {
						try {
							initialRow = Integer.parseInt(args[i]);
						} catch (Exception e) {	}
					} else {
						dfltFile = args[i];
					}

					cat = IS_A_FILE;
				}
			}
		}
    }

    /**
     * @return Returns the dfltFile.
     */
    public String getDfltFile() {
        return dfltFile;
    }
    /**
     * @return Returns the initialRow.
     */
    public int getInitialRow() {
        return initialRow;
    }
}
