package net.sf.RecordEditor.utils.params;

import net.sf.JRecord.Common.Conversion;
import net.sf.RecordEditor.utils.common.Common;



/**
 * 
 * This class checks if user specific data exists (if not it is extracted from Zip files !!!)
 * 
 * The method checkAndCreate checks:<ul>
 * <li> If the user properties file exists; if it does not exist,
 *   User data is extracted from a zip file.
 * <li> If the DB does not exist, extrtact it from a Zip drive
 * </ul>
 * 
 * @author Bruce Martin
 *
 */
public final class CheckUserData {

	public final static int EXTRACT_DB  = 1;
	public final static int EXTRACT_ALL = 2;
	public final static int EXTRACT_USER = 3;
	

	public static void checkAndCreate() {
		checkAndCreate(EXTRACT_ALL, true, null);
	}
	
	public static void checkJavaVersion(String pgm) {

		if (Parameters.JAVA_VERSION < 1.7) {
			Common.logMsg(
					pgm + " was tested with Java 7/8. You are curretly running Java "
					+ System.getProperty("java.version") + "\n\n"
					+ "If you experience problems, try:\n"
					+ "\t1. Install the latest version of Java\n"
					+ "\t2. Reinstall " + pgm + " - This step is vital !!!, it picks up the new Java version"
					, null);
		}
		
		if (Conversion.DEFAULT_CHARSET_DETAILS.isMultiByte) {
			Common.logMsg(
					  "\nThe default character set: " + Conversion.DEFAULT_CHARSET_DETAILS.charset + " is a multi byte\n"
					+ "character-set. When editing binary file, the RecordEditor requires a single byte character set.\n"
					+ "There will be times when the RecordEditor will replace font=\"\" with cp1252.\n"
					+ "I would advise you explictly specify the font rather than leave it to the default character set. ",
					null
			);
		}
	}
	
	public static void setUseCsvLine() {
	    try {
	    	net.sf.JRecord.Common.CommonBits.setUseCsvLine(false);
		} catch (Throwable e) {
			e.printStackTrace();
		}		
	}
	/**
	 * The method checks:<ul>
	 * <li> If the user properties file exists; if it does not exist,
	 *   User data is extracted from a zip file.
	 * <li> If the DB does not exist, extrtact it from a Zip drive
	 * </ul>
	 * 
	 * @param doStdExtract wether to check / unzip the main user files 
	 * (if false only the DB is checked)
	 */
	public static void checkAndCreate(int extractType, boolean runUserDataInThread, String dbDest) {

		if (Parameters.JAVA_VERSION > 1.699) {
			net.sf.RecordEditor.utils.params.CheckUserData_V7.checkAndCreate(extractType, runUserDataInThread, dbDest);
		} else {
			net.sf.RecordEditor.utils.params.CheckUserData_V6.checkAndCreate(extractType, runUserDataInThread, dbDest);
		}
	}
	
}
