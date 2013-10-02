package net.sf.RecordEditor.utils.swing;


import java.awt.event.FocusListener;


import javax.swing.JButton;
import javax.swing.JComponent;

import net.sf.RecordEditor.utils.common.Common;

@SuppressWarnings("serial")
public abstract class FileChooserBtnSelect<Component extends JComponent>
extends FieldWithBtns<Component> implements UpdatableTextValue {

	public static final JButton OPEN_FILE_CHOOSER_POSITION = null;

	private final JButton openBtn = new JButton(Common.getRecordIcon(Common.ID_OPEN_ICON));

    private final FileChooserHelper chooseHelper;


	public FileChooserBtnSelect(Component comp, boolean isOpen, boolean isDirectory, JButton... b) {
		super(comp);

		JButton[] btns = new JButton[b.length];
		System.arraycopy(b, 0, btns, 0, b.length);


		for (int i = 0; i < btns.length; i++) {
			if (btns[i] == OPEN_FILE_CHOOSER_POSITION) {
				btns[i] = openBtn;

				chooseHelper = new FileChooserHelper(this, isOpen, isDirectory);
				super.initScreen(btns);

				openBtn.addActionListener(chooseHelper);
				return;
			}
		}

		throw new RuntimeException("You must supply the position of the open button"
				+ " (or use class FieldWithBtns instead)");
	}




    /**
     * Special file chooser Focus listner.
     * @param fcListner focus listner
     * @see java.awt.Component#addFocusListener(java.awt.event.FocusListener)
     */
    public final synchronized void addFcFocusListener(FocusListener fcListner) {

       	component.addFocusListener(fcListner);
       	chooseHelper.listner.add(fcListner);
    }


	/**
	 * @return the chooseHelper
	 */
	public final FileChooserHelper getChooseHelper() {
		return chooseHelper;
	}

	/**
	 * @param name
	 * @see java.awt.Component#setName(java.lang.String)
	 */
	public void setName(String name) {
		component.setName(name + "_Txt");
		openBtn.setName(name + "_OpenBtn");
		super.setName(name);
	}
}