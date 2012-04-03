/*
 * @Author Bruce Martin
 * Created on 24/02/2007 for version 0.60
 *
 */
package net.sf.RecordEditor.re.openFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import net.sf.RecordEditor.utils.common.Common;

/**
 * This class manages the Recent Files and there corresponding
 * layouts.
 *
 * @author Bruce Martin
 *
 */
public class RecentFiles {

    public static final int RECENT_FILE_LIST  = 25;
	public static final int RF_NONE = -1;
	public static final int RF_VELOCITY = 3;
	public static final int RF_XSLT = 4;

	private static final int RF_FILE_MODE = -2;
	private static final int RECENT_SIZE = 3;
	private static final int FILE_HISTORY  = 400;
    private static final int HASH_MAP_SIZE = 600;
    private static final int LAUNCH_EDITOR_MATCH_NUMBER = 5 - Common.OPTIONS.launchIfMatch.get();
    private static final String EXTENSION_STRING   = "<extensions>";
    private static final String EXTENSION_VELOCITY = "<velocity>";
    private static final String EXTENSION_XSLT     = "<xslt>";
    private static final String[] EXTENSION_NAME = {null, null, null, EXTENSION_VELOCITY, EXTENSION_XSLT};

    private static final String SEPERATOR    = "\t\t";

    private final String recentFileName;
    private String lastFile = null;
    private int lastIdx = 0;
	//private Properties recentProp;

	private int fileNum = 0;
	//private String[] recentFiles   = new String[FILE_HISTORY];
	//private String[] recentLayouts = new String[FILE_HISTORY];

	private ArrayList<HashMap<String, String>> recentMap = new ArrayList<HashMap<String, String>>(5);

	private int[] truncSizes = {Integer.MAX_VALUE,
	        Common.OPTIONS.significantCharInFiles3.get(),
	        Common.OPTIONS.significantCharInFiles2.get(),
	        Common.OPTIONS.significantCharInFiles1.get()};

	private boolean editorLaunch = false;

	private String[] fileNames = new String[FILE_HISTORY];
	private String[] layouts   = new String[FILE_HISTORY];
	private String[] directory = new String[FILE_HISTORY];
	private int[] prevFiles = new int[RECENT_FILE_LIST];
	//private HashMap  idxLookup = new HashMap(HASH_MAP_SIZE);
	
	private FormatFileName selection;

	private static RecentFiles last = null;
	
    /**
     * Store recently used files and there layouts
     * @param fileName filename of recent files file
     */
    public RecentFiles(final String fileName, FormatFileName layoutSelection) {
        super();

        int i;
        String l;
        StringTokenizer t;
	    String recentFile, dir, recentLayout, extension;
	    HashMap<String, String> extensionMap; 
	    
	    recentMap.add(new HashMap<String, String>(HASH_MAP_SIZE));
	    recentMap.add(new HashMap<String, String>(HASH_MAP_SIZE));
	    recentMap.add(new HashMap<String, String>(HASH_MAP_SIZE));
	    
	    recentMap.add(new HashMap<String, String>(HASH_MAP_SIZE));
	    recentMap.add(new HashMap<String, String>(HASH_MAP_SIZE));

	    extensionMap=  recentMap.get(RF_VELOCITY);

        recentFileName = fileName;
        selection = layoutSelection;
        fileNum = 0;
        
        last = this;

        try {
            //Common.logMsg("Recent File: " + recentFileName, null);
            FileReader br = new FileReader(recentFileName);
            BufferedReader r = new BufferedReader(br);
            int mode = RF_FILE_MODE;

            while ((l = r.readLine()) != null) {
                t = new StringTokenizer(l, SEPERATOR);
                recentFile = correctCase(t.nextToken());
                
                if (recentFile == null || "".equals(recentFile)) {
                } else if (recentFile.toLowerCase().equals(EXTENSION_STRING)) {
                 	String s = t.nextToken();
                	
                 	mode = RF_VELOCITY;
                	if (s != null && s.toLowerCase().equals(EXTENSION_XSLT)) {
                		mode = RF_XSLT;
                	}
                	extensionMap = recentMap.get(mode);
                } else {
                	switch (mode) {
                	case RF_FILE_MODE: 
                		if (t.hasMoreElements()) {
	                		recentLayout = t.nextToken();
	                		dir = "";
	                		if (t.hasMoreElements()) {
	                			dir = t.nextToken();
	                		}
	
	                		storeFile(dir, recentFile, recentLayout);
	                	}
                		break;
	    	        default:
	    	        	//System.out.print(" --> " + l + " >" + recentFile + "< " + t.hasMoreElements()
	    	        	//		+ " " + t.hasMoreTokens());
	    	        	if (t.hasMoreElements()) {
		    	        	extension = t.nextToken();
		    	        	//System.out.print("      --> " + extension);
		    	        	if (extension != null && ! "".equals(extension)) {
		    	        		extensionMap.put(recentFile, extension);
		    	        		//System.out.print("   Done");
		    	        	}
	    	        	}
	    	        	//System.out.println(); 
                	}
                }
            }

           
            r.close();
            br.close();
            buildRecentList();
        } catch (Exception e) {
            for (i = fileNum; i < FILE_HISTORY; i++) {
                fileNames[i] = null;
                directory[i] = null;
            }
             e.printStackTrace();
       }


    }

	/**
	 * Add a file to the recent file list
	 * @param fileName file to add
	 * @param layoutName layout name to to edit with
	 */
	public void putFileLayout(String fileName, String layoutName) {
		String strippedName = Common.stripDirectory(fileName);

	    if (strippedName != null && ! "".equals(strippedName)) {
	        String dir = fileName.substring(0, fileName.length() - strippedName.length() - 1);

	        if (correctCase(strippedName).equals(lastFile)) {
	            layouts[lastIdx] = layoutName;
	            putLayout(strippedName, layoutName);
	        } else {
	            storeFile(dir, strippedName, layoutName);
	            buildRecentList();
	        }
	        save();
	    }
	}

	
	public void putFileExtension(int type, String filename, String ext, String defaultExt) {
		String strippedName = correctCase(Common.stripDirectory(filename));
		if (isExtensionType(type) ) {
			if (ext.equals(defaultExt)) {
				if (recentMap.contains(type)) {
					recentMap.get(type).remove(strippedName);
					save();
				}
			} else {
				recentMap.get(type).put(strippedName, ext);
				save();
			}
		}
	}

	/**
	 * Save the recent files
	 */
	public void save() {

        try {
            int i;
            FileWriter wr = new FileWriter(recentFileName);
            BufferedWriter w = new BufferedWriter(wr);

            for (i = fileNum; i < FILE_HISTORY; i++) {
                if (fileNames[i] != null) {
                    w.write(fileNames[i] + SEPERATOR + layouts[i] + SEPERATOR + directory[i]);
                    w.newLine();
                }
		    }
		    for (i = 0; i < fileNum; i++) {
                if (fileNames[i] != null) {
                    w.write(fileNames[i] + SEPERATOR + layouts[i] + SEPERATOR + directory[i]);
                    w.newLine();
                }
		    }
		    
		    saveExtensions(w, RF_VELOCITY);
		    saveExtensions(w, RF_XSLT);

		    w.close();
		    wr.close();
        } catch (Exception e) {
            e.printStackTrace();
        } 
	}

	
	private void saveExtensions(BufferedWriter w, int mode) throws IOException {
		w.write(EXTENSION_STRING);
		w.write(SEPERATOR);
		w.write(EXTENSION_NAME[mode]);
		w.newLine();
		
		for (Entry<String, String> e : recentMap.get(mode).entrySet()) {
			w.write(e.getKey());
			w.write(SEPERATOR);
			w.write(e.getValue());
			w.newLine();
		}
	}

    /**
     * Get the layoutname for the file
     * @param fileName file to find the layout for
     * @return layoutname
     */
    public String getLayoutName(String fileName) {
	    String recentLayout;
	    String strippedFile = correctCase(Common.stripDirectory(fileName));

	    editorLaunch = false;
	    //System.out.println("-----> Searching ---->"  + fileName);
	    //Common.logMsg("-----> Searching ---->"  + fileName + " >> " + strippedFile + "<<"
	    //        + " >>" + strippedFile + "<<", null);
	    for (int i = 0; i < RECENT_SIZE; i++) {
	    	System.out.println(" == Checking " + i + " " + adj4lookup(i, strippedFile));
	        recentLayout = recentMap.get(i).get(adj4lookup(i, strippedFile));
	    	if (recentLayout != null && ! "".equals(recentLayout)) {
	    	    editorLaunch = (i < LAUNCH_EDITOR_MATCH_NUMBER);
//	    	    System.out.println("Found at " + i + " " + adj4lookup(i, strippedFile)
//	    	            + " " + recentLayout);
//	    	    Common.logMsg("Found at " + i + " " + adj4lookup(i, strippedFile)
//	    	            + " " + recentLayout, null);
	    	    
	    	    return recentLayout;
	    	}
	    }
	    
	    String s = strippedFile.toLowerCase();
	    if (s.endsWith("xml")  || s.endsWith("xls")) {
	    	return selection.formatLayoutName("XML - Build Layout");
	    } else if (s.endsWith("csv")) {
	    	return selection.formatLayoutName("Generic CSV - enter details");
	    }
	    return "";
    }

	public String getFileExtension(int type, String filename, String defaultExt) {
		String strippedName = correctCase(Common.stripDirectory(filename));
		if (isExtensionType(type) && recentMap.get(type).containsKey(strippedName)) {
			return recentMap.get(type).get(strippedName);
		}
		
		return defaultExt;
	}
	
	private boolean isExtensionType(int type) {
		return type >= RECENT_SIZE || type < recentMap.size();
	}
	
	
    /**
     * Save a file name away for later lookup
     * @param filename filename
     * @param layout layout
     */
    private void storeFile(String dir, String filename, String layout) {

        putLayout(filename, layout);

        fileNames[fileNum] = filename;
        directory[fileNum] = dir;
        layouts[fileNum] = layout;

        lastIdx = fileNum;
        lastFile = correctCase(filename);
        fileNum += 1;
        if (fileNum >= FILE_HISTORY) {
            fileNum = 0;
        }
    }
    
    private void buildRecentList() {
    	int st = fileNum; 
    	int i = fileNum - 1;
    	int j = 0;
    	HashMap<String, String> used = new HashMap<String, String>();
    	
    	while (j < prevFiles.length && i != st) {
    		if (i < 0) {
    			i = FILE_HISTORY - 1;
    		}
    		if (directory[i] != null && ! "".equals(directory[i])
    		&& fileNames[i] != null && ! "".equals(fileNames[i])
    		&& ! used.containsKey(fileNames[i])) {
    			used.put(fileNames[i], fileNames[i]);

    			prevFiles[j++] = i;
    		}
    		i -= 1;
    	}

    	//System.out.println(" ## " + j);
    	for (i = j; i < prevFiles.length; i++) {
    		prevFiles[i] = -1;
    	}

    }

    /**
     * Store Layout name in maps
     * @param filename filename
     * @param layout layout
     */
	private void putLayout(String filename, String layout) {

        String lookup = correctCase(filename);
        for (int i = 0; i < RECENT_SIZE; i++) {
            recentMap.get(i).put(adj4lookup(i, lookup), layout);
        }
    }
	


    /**
     * cutting filename the size to improve the chance of finding a match
     * @param idx lookup index
     * @param fileName full file name
     * @return adjusted file name
     */
    private String adj4lookup(int idx, String fileName) {
        return fileName.substring(0, Math.min(fileName.length(), truncSizes[idx]));
    }

    /**
     * Adjust case for the OS type (ie case sensitive for UNIX,
     * not case sensitive for windows
     *
     * @param fileName file name
     *
     * @return case corrected filename
     */
    private String correctCase(String fileName) {

	    if (fileName != null && "\\".equals(Common.FILE_SEPERATOR)) {
	        return fileName.toLowerCase();
	    }
	    return fileName;
    }

    /**
     * @return Returns the editorLaunch.
     */
    public boolean isEditorLaunch() {
        return editorLaunch;
    }
    
    /**
     * Get a recent filename by index
     * @param idx index of the required file
     * @return requested file name
     */
    public String getRecentFileName(int idx) {
    	String ret = "";
    	if (idx >= 0 && idx < prevFiles.length && prevFiles[idx] >= 0) {
    		String s = fileNames[prevFiles[idx]];
    		if (s != null) {
    			ret = s;
     		}
    		
    	}
    	return ret;
    }
    
    public String getRecentFullFileName(int idx) {
    	String ret = "";
    	if (idx >= 0 && idx < prevFiles.length && prevFiles[idx] >= 0) {
    		String s = directory[prevFiles[idx]]  + Common.FILE_SEPERATOR + fileNames[prevFiles[idx]] ;
    		if (s != null) {
    			ret = s;
     		}
    		
    	}
    	return ret;
    }
 
    /**
     * Get a recent layout by index
     * @param idx index of the required layout
     * @return requested layout
     */
    public String getRecentLayoutName(int idx) {
    	String ret = "";
    	if (idx >= 0 && idx < prevFiles.length && prevFiles[idx] >= 0) {
    		String s = layouts[prevFiles[idx]];
    		if (s != null) {
    			ret = s;
     		}
    		
    	}
    	return ret;
    }

	/**
	 * @return the last
	 */
	public static RecentFiles getLast() {
		return last;
	}

}