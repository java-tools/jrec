package net.sf.RecordEditor.utils.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sf.RecordEditor.utils.common.Common;

@SuppressWarnings("serial")
public class FileChooser1 extends JPanel {

	private static final int FIELD_WIDTH = 20;

	private JTextField fileFld = new JTextField();
	protected JButton openBtn = new JButton(Common.getRecordIcon(Common.ID_OPEN_ICON));

    private final FileChooserHelper chooseHelper;



	public FileChooser1(boolean isOpen) {
		super();

		chooseHelper = new FileChooserHelper(fileFld, isOpen);
		init();
	}

	protected void init() {

		fileFld.setOpaque(true);
		fileFld.setMinimumSize(new Dimension(FIELD_WIDTH, fileFld.getHeight()));

		this.setLayout(new BorderLayout());
		this.add(BorderLayout.CENTER, fileFld);
		this.add(BorderLayout.EAST, openBtn);

		this.setBorder(fileFld.getBorder());
		fileFld.setBorder(BorderFactory.createEmptyBorder());
		openBtn.addActionListener(chooseHelper);
	}

	/**
	 * Get the Text fields value
	 * @return text value of field
	 */
	public String getText() {
	    return fileFld.getText();
	}

	/**
     * Set the fields value
     * @param text new text value of date
     */
    public void setText(String text) {
        fileFld.setText(text);
    }




	public void setupBackground() {
		super.setBackground(Color.WHITE);
	}


	public void setBackgroundOfField(Color bg) {
		fileFld.setBackground(bg);
	}


    /**
     * Special file chooser Focus listner.
     * @param fcListner focus listner
     * @see java.awt.Component#addFocusListener(java.awt.event.FocusListener)
     */
    public synchronized void addFcFocusListener(FocusListener fcListner) {

       	fileFld.addFocusListener(fcListner);
       	chooseHelper.listner.add(fcListner);
    }


	/**
	 * @return the chooseHelper
	 */
	public FileChooserHelper getChooseHelper() {
		return chooseHelper;
	}

	/**
	 * @param name
	 * @see java.awt.Component#setName(java.lang.String)
	 */
	public void setName(String name) {
		fileFld.setName(name + "_Txt");
		openBtn.setName(name + "_OpenBtn");
		super.setName(name);
	}
}