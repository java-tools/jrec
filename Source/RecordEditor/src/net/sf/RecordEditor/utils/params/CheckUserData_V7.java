package net.sf.RecordEditor.utils.params;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
public final class CheckUserData_V7 {

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
			Path userDataPath = new File(Parameters.getBaseDirectory()).toPath().resolve("UserData");
			Path userPropertiesPath = Paths.get(propertiesFileName);

			System.out.println(" 1 =====> " + userDataPath.toString() + " " + (Files.exists(userDataPath))
					+ " // " + propertiesFileName + " " + Files.exists(Paths.get(propertiesFileName))
					+ " " + extractType);
					
			if (Files.exists(userDataPath)) {			
				//File parentFile = propertiesFile.getParentFile();
				Path parentPath = userPropertiesPath.getParent();
//				String userDirName = parentPath.toString();
				Path dbPath = parentPath.resolve("Database");
				Path dbCheckFilePath;

				Path dbZipFilePath = userDataPath.resolve("Database.zip");
				String dbZipFileName = dbZipFilePath.toString();

				
				if (dbDest != null && ! "".equals(dbDest)) {
					try {
						dbPath = Paths.get(dbDest);
					} catch (Exception e) {
					}
				}
				
				dbCheckFilePath = dbPath.resolve("DBcreatedSuccessFully");
		
	//			System.out.println(" 3 =====> " + parentFile.getPath() + File.separator + "Database");
				System.out.println(" 2 =====> Extracting " + Files.exists(dbPath) + " " +  Files.exists(dbCheckFilePath) + " "
						+ dbZipFileName + " to " + dbPath.toString() + " " + Files.exists(dbZipFilePath));
				if (extractType == EXTRACT_USER || ! Files.exists(dbZipFilePath)) {
//					System.out.println(" 3 =====>");
				} else if ((! Files.exists(dbPath))) {
					String dbDirName = dbPath.toString();
					//msgFrame = getMsgScreen(msgFrame, msgTxt, "Unzipping Database to: " + dbDirName);
					System.out.println(" 4 =====> Extracting " + dbZipFileName + " to " + dbDirName);
					try {
						extractZipFile(dbZipFilePath, dbPath);
						Files.createFile(dbCheckFilePath); // Create check file if successful.
					} catch (Exception e) {
						e.printStackTrace();
						try {
							extractZipFile(dbZipFilePath, dbPath); // Try a second time
							Files.createFile(dbCheckFilePath); // Create check file if successful.
						} catch (Exception e1) {				
						}
					}
				} else if (! Files.exists(dbCheckFilePath)) {
					System.out.println(" 5 =====> Selective Extracting DB " + dbZipFileName );
					selectiveUnzip(dbZipFileName, dbPath);
					Files.createFile(dbCheckFilePath); // Create check file if successful.
				}
				
				if (extractType >= EXTRACT_ALL) { 
					Path zip1FilePath = userDataPath.resolve("UserData1.zip");
					Path zip2FilePath = userDataPath.resolve("UserData2.zip");
					Path userFilePath = parentPath.resolve("User");
					String zip1FileName = zip1FilePath.toString();
					String zip2FileName = zip2FilePath.toString();
					boolean paramsFileExists = Files.exists(parentPath.resolve("Params.Properties"));
					if (   (! Files.exists(parentPath))  ) {	
						System.out.println(" 6 =====> Extracting " + zip1FileName);
						
						//msgFrame = getMsgScreen(msgFrame, msgTxt, "Unzipping user data to: " + userDirName);
						extractZipFile(zip1FilePath, parentPath);
						Parameters.readProperties();

						doUnzip(runUserDataInThread, new UserData2UnZip(null, zip2FilePath, parentPath));
						//Parameters.setProperty(zip1VersionVar, Common.CURRENT_VERSION);
								
					} else if ((! Files.exists(parentPath.resolve("CopyBook"))) 
							|| (! Files.exists(parentPath.resolve("SampleFiles")))
							 ) {
						System.out.println(" 7 =====> Extracting " + zip1FileName);
						doUnzip(runUserDataInThread, new UserData2UnZip(zip1FileName, zip2FilePath, parentPath));
					} else if (Common.CURRENT_VERSION.equals(Parameters.getString(zip1VersionVar))
							&& Common.CURRENT_VERSION.equals(Parameters.getString(zip2VersionVar))
							&& (paramsFileExists)
//							&& Files.exists(userFilePath)
							&& Files.exists(userFilePath.resolve("VelocityTemplates"))) {
						System.out.println(" 8 =====> No Extract " + userFilePath.toString());
					} else {
						SelectiveUnZip su = null;
						
						if (selectiveUnZip1()) {
							System.out.println(" 9 =====> Selective Extracting User Data 1 & 2 " + zip1FileName + " " + zip2FileName);
							
							su = new SelectiveUnZip(zip1FileName, zip2FileName, parentPath, zip1VersionVar, zip2VersionVar);
						} else if (! Common.CURRENT_VERSION.equals(Parameters.getString(zip2VersionVar))) {
							System.out.println(" 10 =====> Selective Extracting User Data 2 " + dbZipFileName + " " + zip2FileName);
							
							su = new SelectiveUnZip(null, zip2FileName, parentPath, null, zip2VersionVar);
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
	private static void selectiveScriptDelete(Path parentPath) {
		try {
			Path scriptPath = parentPath.resolve("User").resolve("Scripts");
			Path scriptExamplePath = scriptPath.resolve("Example");
			Path scriptCsvPath = scriptPath.resolve("Csv");
			Path t;
			
			if (Files.exists(scriptPath)) {
				for (String s : SCRIPTS_TO_DELETE) {
					t = scriptPath.resolve(s);
					if (Files.exists(t)
					&& (   Files.exists(scriptExamplePath.resolve(s))
						|| Files.exists(scriptCsvPath.resolve(s)))) {
						try {
							Files.delete(t);
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
	private static void selectiveUnzip(String zipFileName, Path destinationPath) throws ZipException, IOException {
		net.lingala.zip4j.core.ZipFile zipFile = new net.lingala.zip4j.core.ZipFile(zipFileName);
		
		// Get the list of file headers from the zip file
		@SuppressWarnings("rawtypes")
		List fileHeaderList = zipFile.getFileHeaders();
		
		Path path;
		
		String destinationFileName = destinationPath.toString();
		if (! Files.exists(destinationPath)) {
			Files.createDirectories(destinationPath);
		}
		
		int ij = 0;
		net.lingala.zip4j.model.FileHeader fileHeader;
		// Loop through the file headers
		for (int i = 0; i < fileHeaderList.size(); i++) {
			fileHeader = (net.lingala.zip4j.model.FileHeader)fileHeaderList.get(i);
			path = destinationPath.resolve(fileHeader.getFileName());
			
			if ( Files.exists(path) && CheckUserData_Common.okToSkip(fileHeader.getFileName(), path.toFile())) {
			} else if (fileHeader.isDirectory()) {
				Files.createDirectories(path);
			} else {
				zipFile.extractFile(fileHeader, destinationFileName);
			}
			
			if (++ij % 10 == 0) System.out.print("\t" + (ij));
			if (  ij % 200 == 0) System.out.println();
		}
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
	private static void extractZipFile(Path f, Path destination) {
		String name = f.toString();
		if (Files.exists(f)) {
			try {
				// Initiate ZipFile object with the path/name of the zip file.
				net.lingala.zip4j.core.ZipFile zipFile = new net.lingala.zip4j.core.ZipFile(name);
				if (zipFile == null) {
					throw new ClassNotFoundException(); // in here to allow catching classnotfound exception
				}
				
				if (! Files.exists(destination) ) {
					Files.createDirectories(destination);
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
	
	private static void selectiveUnzip1(Path destination, String zipFileName1) {
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
		final Path zipFilePath2, destination;
		
		public UserData2UnZip(String zipFileName1, Path zipFilePath2,
				Path destination) {
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
		final String zipFileName1, zipFileName2, zipVarName1, zipVarName2;
		final Path destination;
		
		public SelectiveUnZip(String zipFileName1, String zipFileName2, Path destination, String zipVar1, String zipVar2) {
			super();
			this.zipFileName1 = zipFileName1;
			this.zipFileName2 = zipFileName2;
			this.zipVarName1 = zipVar1;
			this.zipVarName2 = zipVar2;
			this.destination = destination;
		}
		

		@Override
		public void run() {

			System.out.println("Un Zipping: " + zipFileName1);
			selectiveUnzip1(destination, zipFileName1);
			try {
				if (zipFileName1 != null && zipVarName1 != null) {
					Parameters.setProperty(zipVarName1, Common.CURRENT_VERSION);
				}
				if (zipFileName2 != null) {
					System.out.println("Un Zipping: " + zipFileName1);
					selectiveUnzip(zipFileName2, destination);
					Parameters.setProperty(zipVarName2, Common.CURRENT_VERSION);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Common.logMsg("Error during Selective extract ???", e);
			}
			
		}
		
	}
}
