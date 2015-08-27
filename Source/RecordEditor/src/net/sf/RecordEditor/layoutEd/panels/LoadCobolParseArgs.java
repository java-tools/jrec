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
package net.sf.RecordEditor.layoutEd.panels;

import java.io.File;

import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.Numeric.ConversionManager;
import net.sf.JRecord.Numeric.ICopybookDialects;

/**
 * Parse program arguments
 *
 * @author Bruce Martin
 *
 */
public class LoadCobolParseArgs {
	private static final int IS_A_FILE = 0;
	private static final int IS_A_DIR = 3;
	private static final int IS_A_SYSTEM = 4;
	private static final int IS_A_SPLIT = 5;
	private static final int IS_A_COMPILER = 6;
	private static final int IS_A_REGEXP = 7;
	private static final int IS_A_FONT = 8;

	public final String file;
	public final String dir ;
	public final String systemName, regExp, font;
	public final int split, compiler;

    /**
     * parse the supplied program arguments
     * @param args program arguments
     */
    public LoadCobolParseArgs(final String[] args) {
        super();
    	String file = "";
    	String dir = "";
    	String systemName = "CobolBatchLoad";
    	String regExp = "";
    	String font = "";
    	int split = CopybookLoader.SPLIT_NONE, compiler = ICopybookDialects.FMT_MAINFRAME;

		int i;
		int cat = IS_A_FILE;
		String ucArg;

		if ((args != null) && (args.length > 0)) {
			for (i = 0; i < args.length; i++) {
				ucArg = args[i].toUpperCase();
				if (ucArg.equals("-F")) {
					cat = IS_A_FILE;
				} else if (ucArg.equals("-D")) {
					cat = IS_A_DIR;
				} else if (ucArg.equals("-FONT")) {
					cat = IS_A_FONT;
				} else if (ucArg.equals("-S")) {
					cat = IS_A_SYSTEM;
				} else if (ucArg.equals("-O") || ucArg.equals("-SPLIT")) {
					cat = IS_A_SPLIT;
				} else if (ucArg.equals("-R")) {
					cat = IS_A_REGEXP;
				} else if (ucArg.equals("-C")) {
					cat = IS_A_COMPILER;
				} else {
					String s = args[i];
					switch (cat) {
					case IS_A_DIR: dir = args[i]; break;
					case IS_A_SYSTEM: systemName = args[i]; break;
					case IS_A_REGEXP: regExp = args[i]; break;
					case IS_A_FONT: font = args[i]; break;
					case IS_A_SPLIT:
						if (s.toLowerCase().equals("01")) {
							split = CopybookLoader.SPLIT_01_LEVEL;
						} else if (s.toLowerCase().equals("redefine")) {
							split = CopybookLoader.SPLIT_REDEFINE;
						} else if (s.toLowerCase().equals("repeating")) {
							split = CopybookLoader.SPLIT_HIGHEST_REPEATING;
						}
						break;
					case IS_A_COMPILER:
						ConversionManager conv = ConversionManager.getInstance();
						//String compare = Conversion.replace(new StringBuilder(s), "_", " ").toString();
						for (int j = 0; j < conv.getNumberOfEntries(); j++) {
							if (s.equalsIgnoreCase(conv.getName(j))) {
								compiler = conv.getKey(i);
								break;
							}
						}
						break;
					case IS_A_FILE:
						file = args[i];
						if (file != null) {
							File f = (new File(file));
							if (f.exists()) {
								try {
									file = f.getCanonicalPath();
								} catch (Exception e) {
								}
							} 
						}
					}

					cat = -1;
				}
			}
			//System.out.println("++>> " + dfltFile + " " + (new File(dfltFile)).exists());
		}
		
		this.file = file;
		this.dir = dir;
		this.split = split;
		this.systemName = systemName;
		this.regExp = regExp;
		this.compiler = compiler;
		this.font = font;
    }


}
