package net.sf.RecordEditor.edit.open;

import java.io.File;

import net.sf.RecordEditor.re.openFile.RecentFiles;
import net.sf.RecordEditor.utils.common.Common;

public class OpenCommon {

	public static File getOpenDirectory(RecentFiles recent, String fname, String defaultDirectory) {
        if (fname == null && Common.OPTIONS.useLastDir.isSelected()) {
        	fname = recent.getLastDirectory();
        }
        if (fname == null || "".equals(fname)) {
            fname = defaultDirectory;
        }
        
        if (fname != null && fname.endsWith("*")
        && (! fname.endsWith("/*")) && (! fname.endsWith("\\*"))) {
        	fname = fname.substring(0, fname.length() - 1);
        }

        File file = null;
        if (fname != null) {
	        file = adjustForDirectory(fname);
 	        
        }
        return file;
	}
	
    
	public static File adjustForDirectory(String filenmae) {
    	File file = new File(filenmae);
    	if (file.isDirectory()) {
    		String pathname = file.getPath() + Common.FILE_SEPERATOR +  "*";
			file = new File(pathname);
    	}
    	return file;
    }

}
