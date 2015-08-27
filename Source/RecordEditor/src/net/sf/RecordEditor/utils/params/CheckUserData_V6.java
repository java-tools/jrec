package net.sf.RecordEditor.utils.params;

import java.io.File;
import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.lingala.zip4j.exception.ZipException;
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
public final class CheckUserData_V6 {

	public final static int EXTRACT_DB  = 1;
	public final static int EXTRACT_ALL = 2;
	public final static int EXTRACT_USER = 3;
	
	private static String[] SCRIPTS_TO_DELETE = {
		"Hello.js", "Hello.rb", 
		"AddSepToLineEnd.py",
		"EnsureCorrectNumberOfFieldSeporators.py",
	};
	
	private static final String zip1VersionVar = "CurrentZipVersion.1";
	private static final String zip2VersionVar = "CurrentZipVersion.2";


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
		JFrame msgFrame = null;
		JTextArea msgTxt = new JTextArea();
		try {
			String propertiesFileName = Parameters.getPropertyFileName();
			String userDataPathDir = Parameters.getBaseDirectory() + "/UserData";
			File userPropertiesPath = new File(propertiesFileName);

//			System.out.println(" 1 =====> " + userDataPath.toString() + " " + (Files.exists(userDataPath))
//					+ " // " + propertiesFileName + " " + Files.exists(Paths.get(propertiesFileName)));
					
			if (Parameters.exists(userDataPathDir)) {			
				//File parentFile = propertiesFile.getParentFile();
				File parentPath = userPropertiesPath.getParentFile();
//				String userDirName = parentPath.toString();
				File dbPath = new File(parentPath.getCanonicalPath() + "/Database");
				File dbCheckFilePath;

				File dbZipFilePath = new File(userDataPathDir + "/Database.zip");
				String dbZipFileName = dbZipFilePath.toString();

				
				if (dbDest != null && ! "".equals(dbDest)) {
					try {
						dbPath = new File(dbDest);
					} catch (Exception e) {
					}
				}
				
				dbCheckFilePath =  resolve(dbPath, "DBcreatedSuccessFully");
		
	//			System.out.println(" 3 =====> " + parentFile.getPath() + File.separator + "Database");
				System.out.println(" 2 =====> Extracting " + dbPath.exists() + " " +  dbCheckFilePath.exists() + " "
						+ dbZipFileName + " to " + dbPath.toString() + " " + dbZipFilePath.exists());
				if (extractType == EXTRACT_USER || ! dbZipFilePath.exists()) {
//					System.out.println(" 3 =====>");
				} else if ((! dbPath.exists())) {
					String dbDirName = dbPath.toString();
					//msgFrame = getMsgScreen(msgFrame, msgTxt, "Unzipping Database to: " + dbDirName);
					System.out.println(" 4 =====> Extracting " + dbZipFileName + " to " + dbDirName);
					try {
						extractZipFile(dbZipFilePath, dbPath);
						dbCheckFilePath.createNewFile(); // Create check file if successful.
					} catch (Exception e) {
						e.printStackTrace();
						try {
							extractZipFile(dbZipFilePath, dbPath); // Try a second time
							dbCheckFilePath.createNewFile(); // Create check file if successful.
						} catch (Exception e1) {				
						}
					}					
				} else if (! dbCheckFilePath.exists()) {
					System.out.println(" 5 =====> Selective Extracting DB " + dbZipFileName );
					selectiveUnzip(dbZipFileName, dbPath);
					dbCheckFilePath.createNewFile(); // Create check file if successful.
				}
				
				if (extractType >= EXTRACT_ALL) { 
					File zip1FilePath = new File(userDataPathDir + "/UserData1.zip");
					File zip2FilePath = new File(userDataPathDir + "/UserData2.zip");
					File userFilePath = resolve(parentPath, "User");
					String zip1FileName = zip1FilePath.toString();
					String zip2FileName = zip2FilePath.toString();
					boolean paramsFileExists = resolve(parentPath, "Params.Properties").exists();
					if (   (! parentPath.exists()) ) {	
						System.out.println(" 6 =====> Extracting " + zip1FileName);
						
						//msgFrame = getMsgScreen(msgFrame, msgTxt, "Unzipping user data to: " + userDirName);
						extractZipFile(zip1FilePath, parentPath);
						Parameters.readProperties();
						Parameters.setProperty(zip1VersionVar, Common.CURRENT_VERSION);

						doUnzip(runUserDataInThread, new UserData2UnZip(null, zip2FilePath, parentPath));
								
					} else if ((! (resolve(parentPath, "CopyBook").exists())) 
							|| (! (resolve(parentPath, "SampleFiles").exists()))) {
						System.out.println(" 7 =====> Extracting " + zip1FileName);
						doUnzip(runUserDataInThread, new UserData2UnZip(zip1FileName, zip2FilePath, parentPath));
					} else if (Common.CURRENT_VERSION.equals(Parameters.getString(zip1VersionVar))
							&& Common.CURRENT_VERSION.equals(Parameters.getString(zip2VersionVar))
							&& (paramsFileExists)
							&& userFilePath.exists()
							&& (resolve(userFilePath, "VelocityTemplates").exists())) {
						//System.out.println(" 8 =====> No Extract " + userFilePath.toString());
					} else {
						SelectiveUnZip su = null;
						
						if (selectiveUnZip1()) {
							System.out.println(" 9 =====> Selective Extracting User Data 1 & 2 " + zip1FileName + " " + zip2FileName);
							
							su = new SelectiveUnZip(zip1FileName, zip2FileName, parentPath, zip2VersionVar);
						} else if (! Common.CURRENT_VERSION.equals(Parameters.getString(zip2VersionVar))) {
							System.out.println(" 10 =====> Selective Extracting User Data 2 " + dbZipFileName + " " + zip2FileName);
							
							su = new SelectiveUnZip(null, zip2FileName, parentPath, zip2VersionVar);
						}
						selectiveScriptDelete(parentPath);
						
						doUnzip(runUserDataInThread, su);

					}
				}
				
			}
			
//			if (msgFrame != null) {
//				msgFrame.setVisible(false);
//				msgFrame = null;
//			}
		} catch (Exception e) {
			e.printStackTrace();
			msgFrame = getMsgScreen(msgFrame, msgTxt, "Error occured extracting User Files / Database: " + e);
		} 
	}
	
	/**
	 * Delete example/csv scripts from primary directory if they exist in th
	 * Example or Csv directory.
	 *  
	 * @param parentPath path where user data is installed
	 */
	private static void selectiveScriptDelete(File parentPath) {
		try {
			File scriptPath = resolve(resolve(parentPath, "User"), "Scripts");
			File scriptExamplePath = resolve(scriptPath, "Example");
			File scriptCsvPath = resolve(scriptPath, "Csv");
			File t;
			
			if (scriptPath.exists()) {
				for (String s : SCRIPTS_TO_DELETE) {
					t = resolve(scriptPath, s);
					if ((t).exists()
					&& (   (resolve(scriptExamplePath, s).exists())
						|| (resolve(scriptCsvPath, s).exists()))) {
						try {
							t.delete();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void doUnzip(boolean runUserDataInThread, Runnable r) {
		if (r == null) {
			
		} else if (runUserDataInThread) {
			new Thread(r).start();
		} else {
			r.run();
		}
	}
	
//	private void unZipUsedData(Path zipFilePath) {
//		
//	}
	
	
	/**
	 * Selectively unzip (do not replace existing files).
	 * 
	 * @param zipFileName
	 * @param destination
	 * @throws ZipException
	 * @throws IOException
	 */
	private static void selectiveUnzip(String zipFileName, File destinationPath) throws ZipException, IOException {
		net.lingala.zip4j.core.ZipFile zipFile = new net.lingala.zip4j.core.ZipFile(zipFileName);
		
		// Get the list of file headers from the zip file
		@SuppressWarnings("rawtypes")
		List fileHeaderList = zipFile.getFileHeaders();
		
		File path;
		
		String destinationFileName = destinationPath.toString();
		if (! destinationPath.exists()) {
			destinationPath.mkdirs();
		}
		
		int ij = 0;
		net.lingala.zip4j.model.FileHeader fileHeader;
		// Loop through the file headers
		for (int i = 0; i < fileHeaderList.size(); i++) {
			fileHeader = (net.lingala.zip4j.model.FileHeader)fileHeaderList.get(i);
			path = resolve(destinationPath, fileHeader.getFileName());
			
			if ( path.exists() && CheckUserData_Common.okToSkip(fileHeader.getFileName(), path)) {
			} else if (fileHeader.isDirectory()) {
				path.mkdirs();
			} else {
				zipFile.extractFile(fileHeader, destinationFileName);
			}
			
			if (++ij % 10 == 0) System.out.print("\t" + (ij));
			if (  ij % 200 == 0) System.out.println();
		}
	}
	
	private static File resolve(File f, String name) {
		return new File(f.getPath() + "/" + name);
	}
	
	private static JFrame getMsgScreen(JFrame ret, JTextArea msgTxt, String msg) {
		String currTxt = msgTxt.getText();
		if (ret == null) {
			ret = new JFrame();
		}
		
		if (msgTxt != null || ! "".equals(msgTxt)) {
			msg = currTxt + "\n\n" + msg;
		}
		msgTxt.setText(msg);

		ret.add(new JScrollPane(msgTxt));
		ret.pack();
		ret.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ret.setVisible(true);
		
		return ret;
	}

	@SuppressWarnings("unused")
	private static void extractZipFile(File f, File destination) {
		String name = f.toString();
		if (f.exists()) {
			try {
				// Initiate ZipFile object with the path/name of the zip file.
				net.lingala.zip4j.core.ZipFile zipFile = new net.lingala.zip4j.core.ZipFile(name);
				if (zipFile == null) {
					throw new ClassNotFoundException(); // in here to allow catching classnotfound exception
				}
				
				if (! destination.exists() ) {
					destination.mkdirs();
				}
				// Extracts all files to the path specified
				zipFile.extractAll(destination.toString());
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Zip File does not exist: " + name);
		}
	}
	
	private static boolean selectiveUnZip1() {
		return ! Common.CURRENT_VERSION.equals(Parameters.getString(zip1VersionVar));
	}
	
	private static void selectiveUnzip1(File destination, String zipFileName1) {
		if (zipFileName1 != null && selectiveUnZip1()) {
			try {
				selectiveUnzip(zipFileName1, destination);
				Parameters.setProperty(zip1VersionVar, Common.CURRENT_VERSION);
			} catch (Exception e) {
				e.printStackTrace();
				Common.logMsg("Error during Selective extract ???", e);
			}
		}
	}
	
	
	private static class UserData2UnZip implements Runnable {
		final String zipFileName1;
		final File zipFilePath2, destination;
		
		public UserData2UnZip(String zipFileName1, File zipFilePath2,
				File destination) {
			super();
			this.zipFileName1 = zipFileName1;
			this.zipFilePath2 = zipFilePath2;
			this.destination = destination;
		}

		@Override
		public void run() {
			selectiveUnzip1(destination, zipFileName1);
			try {
				extractZipFile(zipFilePath2, destination);
				Parameters.setProperty(zip2VersionVar, Common.CURRENT_VERSION);
			} catch (Exception e) {
				e.printStackTrace();
				Common.logMsg("Error during Selective extract ???", e);
			}
		}
	}
	
	private static class SelectiveUnZip implements Runnable {
		final String zipFileName1, zipFileName2, zipVarName;
		final File destination;
		
		public SelectiveUnZip(String zipFileName1, String zipFileName2, File destination, String zipVar) {
			super();
			this.zipFileName1 = zipFileName1;
			this.zipFileName2 = zipFileName2;
			this.zipVarName = zipVar;
			this.destination = destination;
		}
		

		@Override
		public void run() {

			System.out.println("Un Zipping: " + zipFileName1);
			selectiveUnzip1(destination, zipFileName1);
			try {
				if (zipFileName2 != null) {
					System.out.println("Un Zipping: " + zipFileName1);
					selectiveUnzip(zipFileName2, destination);
					Parameters.setProperty(zipVarName, Common.CURRENT_VERSION);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Common.logMsg("Error during Selective extract ???", e);
			}
			
		}
		
	}
}
