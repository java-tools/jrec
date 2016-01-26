package net.sf.RecordEditor.edit;

import net.sf.RecordEditor.utils.params.CheckUserData;

public class XCreateData {

	public static void main(String[] args) {
		int extractUserParms = CheckUserData.EXTRACT_DB;
		String dbDir = null;
		if (args != null && args.length >  0 ) {
			String typeVar = args[0].toLowerCase();
			if (typeVar.startsWith("y")) {
				extractUserParms = CheckUserData.EXTRACT_ALL;
			} else if (typeVar.startsWith("u")) {
				extractUserParms = CheckUserData.EXTRACT_USER;
			}
			
			if (args.length > 1) {
				StringBuilder b = new StringBuilder();
				String sep = "";
				for (int i = 1; i < args.length; i++) {
					b.append(sep).append(args[i]);
					sep = " ";
				}
				dbDir = b.toString();
			}
		}
		CheckUserData.checkAndCreate(extractUserParms, false, dbDir);
	}
}
