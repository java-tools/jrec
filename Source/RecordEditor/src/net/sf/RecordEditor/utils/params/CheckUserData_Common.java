package net.sf.RecordEditor.utils.params;

import java.io.File;

public final class CheckUserData_Common {

	public static boolean okToSkip(String zipname, File file) {
		return ! (zipname.contains("VelocityTemplates/Script")); 
	}
}
