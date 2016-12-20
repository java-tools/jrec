package net.sf.RecordEditor.utils.screenManager;

import javax.swing.JEditorPane;

public class SmCode {

	public static void showHtmlPnl(String name, String s) {
		ReFrame aboutFrame = new ReFrame("", name, null, null);
		JEditorPane aboutText = new JEditorPane("text/html", s);

		aboutFrame.getContentPane().add(aboutText);
		aboutFrame.pack();
		aboutFrame.setVisible(true);
	}

}
