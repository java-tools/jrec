package net.sf.RecordEditor.utils.lang;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JOptionPane;

public class ReOptionDialog {

	public static Object showInputDialog(Component parentComponent,
	        String message, String title, int messageType, Icon icon,
	        Object[] selectionValues, Object initialSelectionValue) {
		return JOptionPane.showInputDialog(parentComponent,
				LangConversion.convert(LangConversion.ST_MESSAGE, message),
				LangConversion.convert(LangConversion.ST_MESSAGE, title),
				messageType,
				icon, selectionValues, initialSelectionValue);
	}


	public static String showInputDialog(Component parentComponent, String message,
            Object initialSelectionValue) {
		return JOptionPane.showInputDialog(
				parentComponent,
				LangConversion.convert(LangConversion.ST_MESSAGE, message),
				initialSelectionValue);
	}


	public static int showConfirmDialog(Component parentComponent, String message, String title, int optionType) {
		return JOptionPane.showConfirmDialog(
					parentComponent,
					LangConversion.convert(LangConversion.ST_MESSAGE, message),
					LangConversion.convert(LangConversion.ST_MESSAGE, title),
					optionType);
	}

	public static void showMessageDialog(Component parentComponent,
		        String message) {
		JOptionPane.showMessageDialog(
				 	parentComponent,
				 	LangConversion.convert(LangConversion.ST_MESSAGE, message));
	}
	

	public static void showMessageDialog(Component parentComponent,
		        String message, Object...params) {
		JOptionPane.showMessageDialog(
				 	parentComponent,
				 	LangConversion.convertMsg(LangConversion.ST_MESSAGE, message, params));
	}

}
