package net.sf.RecordEditor.utils.swing.treeCombo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import com.zbluesoftware.java.bm.ArrowButton;

@SuppressWarnings("serial")
public class ComboLikeObject extends JPanel implements ActionListener {

	private static final int FIELD_WIDTH = 20;

	private final JTextComponent valueTxt = new JTextField();
	protected final JButton btn = new ArrowButton(ArrowButton.SOUTH);

	protected boolean visible = false;
	private JPopupMenu currentPopup;

	public ComboLikeObject() {
		this(new JPopupMenu());
	}

	public ComboLikeObject(JPopupMenu popup) {
		super();

		valueTxt.setOpaque(true);
		currentPopup = popup;
		init();
	}

	protected void init() {

		valueTxt.setMinimumSize(new Dimension(FIELD_WIDTH, valueTxt.getHeight()));

		this.setLayout(new BorderLayout());
		this.add(BorderLayout.CENTER, valueTxt);
		this.add(BorderLayout.EAST, btn);

		this.setBorder(valueTxt.getBorder());
		valueTxt.setBorder(BorderFactory.createEmptyBorder());
		btn.addActionListener(this);
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

        if (visible) {
        	currentPopup.setVisible(false);
        } else {
        	currentPopup = getPopup();
         	if (currentPopup != null) {
        		int xCoord = 0;
        		int yCoord = valueTxt.getHeight();

        		currentPopup.show(valueTxt, xCoord, yCoord);
        		if (currentPopup.getWidth() < this.getWidth()) {
        			currentPopup.setPopupSize(new Dimension(this.getWidth(), currentPopup.getHeight()));
        		}
         	}
	    }
	    visible = ! visible;
	}

	/**
	 * @return the currentPopup
	 */
	public JPopupMenu getCurrentPopup() {
		return currentPopup;
	}

	public JPopupMenu getPopup() {
		return currentPopup;
	}

	/**
	 * @param t
	 * @see javax.swing.text.JTextComponent#setText(java.lang.String)
	 */
	public void setText(String t) {
		valueTxt.setText(t);
	}

	/**
	 * @return
	 * @see javax.swing.text.JTextComponent#getText()
	 */
	public String getText() {
		return valueTxt.getText();
	}
}
