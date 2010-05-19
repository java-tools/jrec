/*
 * @Author Bruce Martin
 * Created on 26/01/2006
 *
 * Purpose:
 */
package net.sf.RecordEditor.examples.fix2csv;

import net.sf.RecordEditor.examples.CopybookToLayout;
import net.sf.RecordEditor.examples.utils.ParseArguments;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Log.TextLog;
import net.sf.RecordEditor.utils.common.Common;

/**
 * copy a "Cobol" file to a CSV file
 *
 * @author Bruce Martin
 *
 * Note: to compile this program you need to have the RecordEditor
 * Source code installed

 */
public class Cobol2Csv {

    private static final String ARG_COPYBOOK   = "-C";
    private static final String ARG_BINARY     = "-B";
    private static final String ARG_FONT       = "-F";
    private static final String ARG_STRUCTURE  = "-FS";
    private static final String ARG_IN_FILE    = "-I";
    private static final String ARG_OUT_FILE   = "-O";
    private static final String ARG_SEPERATOR  = "-S";

    private static final String[] VALID_PARAMS = {
            ARG_COPYBOOK, ARG_BINARY, ARG_STRUCTURE, ARG_SEPERATOR,
            ARG_IN_FILE, ARG_OUT_FILE, ARG_FONT
    };


    /**
     * convert a cobol file to a CSV file
     * @param arguments program arguments
     */
    public static void main(String[] arguments) {
        ParseArguments args = new ParseArguments(VALID_PARAMS, arguments);
		CopybookToLayout conv = new CopybookToLayout();

		try {
		    LayoutDetail layout;
		    int binFormat  = args.getIntArg(ARG_BINARY, 0);
		    String infile  = args.getArg(ARG_IN_FILE, "");
		    String outfile = args.getArg(ARG_OUT_FILE, "");
		    String font    = args.getArg(ARG_FONT, "");
		    String sep     = args.getArg(ARG_SEPERATOR, "\t");

		    if (! present(infile)) {
		        usage(" You must supply an input file (-i parameter ");
		    } else {
		        conv.setBinaryFormat(binFormat);
		        if (! "".equals(font)) {
		            conv.setFontName(font);
		        }

		        if ("space".equalsIgnoreCase(sep)) {
		            sep = " ";
		        } else if ("tab".equalsIgnoreCase(sep)) {
		            sep = "\t";
		        }

		        if (! present(outfile)) {
		            outfile = infile + ".csv";
		        }


		        layout = conv.readCobolCopyBook(
		                args.getArg(ARG_COPYBOOK),
		                CopybookToLayout.SPLIT_NONE,
		                Common.LINE_SEPERATOR,
		                null,
		                args.getIntArg(ARG_STRUCTURE, 0),
		                new TextLog()
		        );

		        new Fix2Csv(layout, infile, outfile, sep);
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    /**
     * test if a field has a value
     * @param s value being tested
     * @return wether there is a value there
     */
    private static boolean present(String s) {
        return (s != null &&  ! "".equals(s));
    }


    /**
     * Print parameters
     *
     * @param msg error message
     */
    private static void usage(String msg) {

        System.out.println();
        System.out.println(msg);
        System.out.println();
        System.out.println("Program arguments:");
        System.out.println();
        System.out.println("    " + ARG_IN_FILE   + "  - Input file");
        System.out.println("    " + ARG_OUT_FILE  + "  - Output file");
        System.out.println("    " + ARG_COPYBOOK  + "  - Cobol Copybook file");
        System.out.println("    " + ARG_SEPERATOR + "  - Field Seperator (space, tab or value)");
        System.out.println("    " + ARG_BINARY    + "  - Binary Format:");
        System.out.println("        " + Common.IO_DEFAULT       + " - System to determine ");
        System.out.println("        " + Common.IO_TEXT_LINE     + " - Use Text IO ");
        System.out.println("        " + Common.IO_FIXED_LENGTH
                + " - Fixed record Length binary ");
        System.out.println("        " + Common.IO_BINARY
                + " - Binary File, length based on record ");
        System.out.println("        " + Common.IO_VB
                + " - Mainframe VB File ");
        System.out.println("        " + Common.IO_VB_DUMP
                + " - Mainframe VB File including BDW (block descriptor word)");
        //System.out.println("        " +  Common.NAME_1ST_LINE_IO
        //      + " - CSV file with names on first line");

        System.out.println("    " + ARG_STRUCTURE + " - File Structure");
        System.out.println("        " + CopybookToLayout.FMT_INTEL     + " - Intel little endian ");
        System.out.println("        " + CopybookToLayout.FMT_MAINFRAME
                + " - Mainframe big endian ");
        System.out.println("        " + CopybookToLayout.FMT_BIG_ENDIAN + " - Other big endian ");
    }
}
