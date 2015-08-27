package net.sf.RecordEditor.utils.charsets;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPopupMenu;

import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.common.ComboLikeObject;

/**
 * This class implements a Font-Combo box that lists
 * common fonts in a combo-box and has a font search button
 * 
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class FontCombo extends ComboLikeObject {
	
	/**
	 *  This class implements a Font-Combo box that lists
     * common fonts in a combo-box and has a font search button
	 */
	public FontCombo() {
		this(CharsetMgr.getCommonCharsets(), new JButton(""));
	}
	
	/**
	 *  This class implements a Font-Combo box that lists
     * supplied fonts in a combo-box and has a font search button
	 */
	public FontCombo(String[] fonts) {
		this(fonts, new JButton(""));
	}
	
	
	private FontCombo(String[] fonts, JButton btn) {
		super("fontTxt", btn);
		
		setFontList(fonts);
		
		this.setPreferredSize(
				new Dimension(
						Math.max(this.getPreferredSize().width, SwingUtils.CHAR_FIELD_WIDTH *25), 
						this.getPreferredSize().height));
		btn.addActionListener(new ActionListener() {	
			@Override public void actionPerformed(ActionEvent e) {
				showFontScreen();
			}
		});
	}
	
	/**
	 * Set the drop down combo to a list fonts
	 * @param fonts
	 */
	public void setFontList(String[] fonts) {
		
		if (fonts != null) {
			JPopupMenu menu = new JPopupMenu();
			
			for (String s : fonts) {
				menu.add(new FontAction(s, CharsetMgr.getDescription(s)));
			}
		
			super.setCurrentPopup(menu);
		}
	}
	
	private void showFontScreen() {
		super.setText(
				(new CharsetSelection(
						ReMainFrame.getMasterFrame(),
						super.getText()))
					.getCharset());
	}
	
	private class FontAction extends AbstractAction implements Action {
		private final String fontName;
		private FontAction(String fontName, String description) {
			super(fontName + ":  \t" + description);
			this.fontName = fontName;
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			setText(fontName);
		}
	}
}
