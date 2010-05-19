/*
 * @Author Bruce Martin
 * Created on 26/01/2006
 *
 * Purpose:
 */
package net.sf.RecordEditor.examples.fix2csv;

import net.sf.JRecord.Details.LayoutDetail;
import net.sf.RecordEditor.examples.utils.ParseArguments;
import net.sf.RecordEditor.utils.CopyBookDbReader;

/**
 * copy a "Cobol" file to a CSV file
 *
 * @author Bruce Martin
 *
 * Note: to compile this program you need to have the RecordEditor
 * Source code installed

 */
public class Re2Csv {

    private static final String ARG_LAYOUT     = "-L";
    private static final String ARG_IN_FILE    = "-I";
    private static final String ARG_OUT_FILE   = "-O";
    private static final String ARG_SEPERATOR  = "-S";

    private static final String[] VALID_PARAMS = {
            ARG_LAYOUT,  ARG_SEPERATOR,
            ARG_IN_FILE, ARG_OUT_FILE
    };


    /**
     * convert a cobol file to a CSV file
     * @param arguments program arguments
     */
    public static void main(String[] arguments) {
        ParseArguments args = new ParseArguments(VALID_PARAMS, arguments);
		//CopybookToLayout conv = new CopybookToLayout();

		try {
		    LayoutDetail layout;
		    String infile     = args.getArg(ARG_IN_FILE, "");
		    String outfile    = args.getArg(ARG_OUT_FILE, "");
		    String layoutName = args.getArg(ARG_LAYOUT, "");
		    String sep        = args.getArg(ARG_SEPERATOR, "\t");

		    if (! present(infile)) {
		        usage(" You must supply an input file (-i parameter ");
		    } else if ("".equals(layoutName)) {
		        usage(" You must record layout (-l parameter ");
		    } else {

		        if ("space".equalsIgnoreCase(sep)) {
		            sep = " ";
		        } else if ("tab".equalsIgnoreCase(sep)) {
		            sep = "\t";
		        }

		        if (! present(outfile)) {
		            outfile = infile + ".csv";
		        }

	            CopyBookDbReader copybookReader = new CopyBookDbReader();
	            layout = copybookReader.getLayout(layoutName);
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
        System.out.println("    " + ARG_LAYOUT  + "  - Layout Name");
        System.out.println("    " + ARG_SEPERATOR + "  - Field Seperator (space, tab or value)");
     }
}
