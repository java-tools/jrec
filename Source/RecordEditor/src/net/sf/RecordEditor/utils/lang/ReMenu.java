package net.sf.RecordEditor.utils.lang;

import javax.swing.JMenu;

/**
 * Purpose Foreign Language Aware JMenu
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class ReMenu extends JMenu {

	public ReMenu(String s) {
		super(LangConversion.convert(LangConversion.ST_MENU, s));
	}

}
