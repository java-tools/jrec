package net.sf.RecordEditor.utils.swingx;

import java.awt.Component;

import net.sf.RecordEditor.utils.common.Common;


public class TipsManager {

	public static void startTips(Component parent, String tipfile, String tipVariable) {
		try {
			Class.forName("org.jdesktop.swingx.JXTipOfTheDay");
			net.sf.RecordEditor.utils.swingx.TipsImpl.startTips(null, tipfile, tipVariable);
		} catch (ClassNotFoundException e) {
			Common.logMsgRaw("Tip class not found", e);
			e.printStackTrace();
		}
	}


	public static boolean tipsModulePresent() {
		try {
			return Class.forName("org.jdesktop.swingx.JXTipOfTheDay") != null;
		} catch (ClassNotFoundException e) {
			//Common.logMsgRaw("Tip class not found", e);
			e.printStackTrace();
		}
		return false;
	}

}
