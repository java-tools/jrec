package net.sf.RecordEditor.edit.display.util;

import javax.swing.JFrame;

import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.JRecord.Log.ScreenLog;
import net.sf.RecordEditor.utils.common.Common;

public class ErrorScreen {
	public ErrorScreen(String pgm, Exception ex) {
		JFrame frame = new JFrame("Error Screen");
		ScreenLog log = new ScreenLog(frame);
		
		frame.getContentPane().add(log);
		
		log.logMsg(AbsSSLogger.ERROR, 		
				  "      Program: " + pgm + " " + Common.currentVersion() + "\n"
				+ " Java Version: " + System.getProperty("java.version") + "\n\n"
					
		);
		
		log.logException(AbsSSLogger.ERROR, ex);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		ex.printStackTrace();
	}
}
