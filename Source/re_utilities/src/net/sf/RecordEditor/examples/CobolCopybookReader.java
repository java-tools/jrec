/*
 * Created on 27/10/2005
 *
 */
package net.sf.RecordEditor.examples;

import java.io.File;
import java.io.FilenameFilter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDecider;
import net.sf.JRecord.Log.TextLog;
import net.sf.RecordEditor.utils.CopyBookInterface;
import net.sf.RecordEditor.utils.LayoutItem;
import net.sf.RecordEditor.utils.SystemItem;

/**
 * This class is a Copybook~Reader that reads Cobol Copybooks
 * rather than the standard DB.
 *
 * @author Bruce Martin
 *
 */
public class CobolCopybookReader implements CopyBookInterface {

	public static final int SYS_UNKOWN         = 0;
	public static final int SYS_STORE_SYSTEMS  = 1;
	//public static final int SYS_DC             = 2;
	//public static final int SYS_SALES_ANALYSIS = 3;
	//public static final int SYS_STOCK_STATUS   = 4;
	public static final int SYS_AMS            = 5;
	//public static final int SYS_REPLENISHMENT  = 6;

	private static final int IDX_SPLIT         = 0;
	private static final int IDX_MACHINE       = 1;
	private static final int IDX_IO            = 2;



	public static final int[] OPT_STANDARD     = {
			CopybookToLayout.SPLIT_NONE,
			CopybookToLayout.FMT_INTEL,
			Constants.IO_DEFAULT
	};
	public static final int[] OPT_SPLIT_01     = {
			CopybookToLayout.SPLIT_01_LEVEL,
			CopybookToLayout.FMT_INTEL,
			Constants.IO_DEFAULT
	};
	public static final int[] OPT_SPLIT_REDEF  = {
			CopybookToLayout.SPLIT_REDEFINE,
			CopybookToLayout.FMT_INTEL,
			Constants.IO_DEFAULT
	};
	public static final int[] OPT_MAINFRAME    = {
			CopybookToLayout.SPLIT_NONE,
			CopybookToLayout.FMT_MAINFRAME,
			Constants.IO_DEFAULT
	};
	public static final int[] OPT_MAINFRAME_VB    = {
			CopybookToLayout.SPLIT_NONE,
			CopybookToLayout.FMT_MAINFRAME,
			Constants.IO_VB
	};
	public static final int[] OPT_MAINFRAME_VB_DUMP    = {
			CopybookToLayout.SPLIT_NONE,
			CopybookToLayout.FMT_MAINFRAME,
			Constants.IO_VB_DUMP
	};

	private ArrayList<SystemItem> systems = new ArrayList<SystemItem>();

    private HashMap<String, RecordDecider> deciderMap = new HashMap<String, RecordDecider>();
    private HashMap<String, int[]> options    = new HashMap<String, int[]>();

    private FilenameFilter filterCobol = new FilenameFilter() {
        public boolean accept(File f, String filename) {
            return filename.toUpperCase().endsWith(".CBL");
        }
    };


	/**
	 *
	 */
	public CobolCopybookReader() {
		super();

		addSystem(SYS_AMS,            "AMS");
		//addSystem(SYS_DC,             "DC interface");
		//addSystem(SYS_REPLENISHMENT,  "Replenishment");
		//addSystem(SYS_SALES_ANALYSIS, "Sales Analysis");
		//addSystem(SYS_STOCK_STATUS,   "Stock Status");
		addSystem(SYS_STORE_SYSTEMS,  "Store Interface");
		addSystem(SYS_UNKOWN,         "Unkown");

		addCopybookOptions("DTAR020", OPT_MAINFRAME);
		addCopybookOptions("DTAR107", OPT_MAINFRAME);
		addCopybookOptions("DTAR119", OPT_MAINFRAME);
		addCopybookOptions("DTAR192", OPT_MAINFRAME);
		addCopybookOptions("DTAR1000", OPT_MAINFRAME_VB);
		addCopybookOptions("DTAR1000_RECFMU", OPT_MAINFRAME_VB_DUMP);

		addCopybookOptions("AmsShpUpload",  OPT_SPLIT_REDEF);
		addCopybookOptions("AmsReceipt",    OPT_SPLIT_REDEF);
	}


	/**
	 * Define a System
	 * @param pSystemId System Id being defined
	 * @param pdescription System description
	 */
	private void addSystem(int pSystemId, String pdescription) {
		SystemItem itm = new SystemItem();

		itm.systemId = pSystemId;
		itm.description = pdescription;

		systems.add(itm);
	}


	/**
	 * Define Copybook options
	 * @param copybook copybook name for which we are defining options
	 * @param opts options to be applied to the copybook
	 */
	public void addCopybookOptions(String copybook, int[] opts) {
		options.put(copybook.toUpperCase(), opts);
	}


	/**
	 * Get a group of records from the name
	 *
	 * @param name Name of the Record Group being Request
	 *
	 * @return Group of records
	 */
	public LayoutDetail getLayout(String name) {
	    String ucName = name.toUpperCase();
		LayoutDetail ret = null;
		CopybookToLayout conv = new CopybookToLayout();
		int[] opts = OPT_STANDARD;
		RecordDecider decider  = (RecordDecider) deciderMap.get(ucName);


		if (options.containsKey(ucName)) {
		    opts = (int[]) options.get(ucName);
		}
		conv.setBinaryFormat(opts[IDX_MACHINE]);

		try {
			ret = conv.readCobolCopyBook(
				TstConstants.COBOL_DIRECTORY + "/" + name + ".Cbl",
				opts[IDX_SPLIT],
				Constants.LINE_SEPERATOR,
				decider,
				opts[IDX_IO],
				new TextLog()
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}


	/**
	 * get the error message
	 * @return last error message
	 */
	public String getMessage() {
		return null;
	}


	/**
	 * This method gets all the systems that have been defined
	 *
	 * @return ArrayList of Systems
	 * @throws SQLException Error recieved
	 */
	public ArrayList<SystemItem> getSystems() throws SQLException {
		return systems;
	}


	/**
	 * Loads the various Layouts
	 *
	 * @param layouts record layouts
	 */
	public void loadLayouts(ArrayList<LayoutItem> layouts) {
		int i;
		File dir = new File(TstConstants.COBOL_DIRECTORY);
        String[] fileList = dir.list(filterCobol);
        String name;

        Arrays.sort(fileList);
        layouts.clear();
        for (i = 0; i < fileList.length; i++) {
        	name = fileList[i].substring(0, fileList[i].length() - 4);
        	layouts.add(new LayoutItem(name,
        							   name,
									   getSystemFromName(fileList[i]))
        	);
        }
    }


	/**
     * Register a record Decider for later use
     *
     * @param layoutName record layout name
     * @param decider record decider
     */
	public void registerDecider(String layoutName, RecordDecider decider) {
        deciderMap.put(layoutName.toUpperCase(), decider);
	}


	/**
	 * Converts a copybook name to a system ID
	 *
	 * @param name copybook name
	 *
	 * @return system id
	 */
	private int getSystemFromName(String name) {
		int ret = SYS_UNKOWN;
		String ucName = name.toUpperCase();

		if (ucName.startsWith("DTA")) {
			ret = SYS_STORE_SYSTEMS;
		/*} else if (ucName.startsWith("SA")) {
			ret = SYS_SALES_ANALYSIS;
		} else if (ucName.startsWith("SU")) {
			ret = SYS_STOCK_STATUS;
		} else if (ucName.startsWith("DC")) {
			ret = SYS_DC;
		} else if (ucName.startsWith("IV")) {
			ret = SYS_REPLENISHMENT;*/
		} else if (ucName.startsWith("EQ")
		       ||  ucName.startsWith("AMS")) {
			ret = SYS_AMS;
		}
		return ret;
	}
}
