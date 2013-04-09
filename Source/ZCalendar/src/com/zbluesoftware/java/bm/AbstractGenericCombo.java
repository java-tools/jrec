package com.zbluesoftware.java.bm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

@SuppressWarnings("serial")
public abstract class AbstractGenericCombo extends JPanel implements ActionListener, PropertyChangeListener {


	private static final int FIELD_WIDTH = 20;

	private JTextField tFld = new JTextField();
	private JTextComponent fld = tFld;
	protected JButton btn = new ArrowButton(ArrowButton.SOUTH);

	private boolean visible = false;
	private AbstractPopup currentPopup;

	public AbstractGenericCombo() {
		super();

		tFld.setOpaque(true);
		init();
	}

	protected void init() {

		fld.setMinimumSize(new Dimension(FIELD_WIDTH, fld.getHeight()));

		this.setLayout(new BorderLayout());
		this.add(BorderLayout.CENTER, fld);
		this.add(BorderLayout.EAST, btn);

		this.setBorder(fld.getBorder());
		fld.setBorder(BorderFactory.createEmptyBorder());
		btn.addActionListener(this);
	}

	/**
	 * Get the Text fields value
	 * @return text value of field
	 */
	public String getText() {
	    return fld.getText();
	}

	/**
     * Set the fields value
     * @param text new text value of date
     */
    public void setText(String text) {
        fld.setText(text);
    }

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

        if (visible) {
        	currentPopup.setVisible(false);
        	//setText(toString(currentPopup.getValue()));
        	currentPopup.removePropertyChangeListener(this);
        	currentPopup = null;
        } else {
        	currentPopup = getPopup();
         	if (currentPopup != null) {//e.getSource() == btn) {
        		int xCoord = 0;
        		int yCoord = fld.getHeight();
               	currentPopup.addPropertyChangeListener(this);

        		preShowPopup();
        		//currentPopup.setValue(fld.getText());
        		currentPopup.show(fld, xCoord, yCoord);
        		if (currentPopup.getWidth() < this.getWidth()) {
        			//System.out.println("Increase Width ...   " + this.getWidth());
        			currentPopup.setPopupSize(new Dimension(this.getWidth(), currentPopup.getHeight()));
        		}
         	}
	    }
	    visible = ! visible;
	}


	protected void preShowPopup() {
		currentPopup.setValue(fld.getText());
	}


	/**
	 * Get the combos value
	 * @return combos value
	 */
	public abstract Object getValue();

	/**
	 * Set the value
	 * @param value new value
	 */
	public abstract void setValue(Object value);

	/**
	 * Get the popup to be displayed
	 * @return popup
	 */
	public abstract AbstractPopup getPopup();

//	/**
//	 * @param arg0
//	 * @see javax.swing.JTextField#addActionListener(java.awt.event.ActionListener)
//	 */
//	public void addActionListener(ActionListener arg0) {
//		tFld.addActionListener(arg0);
//	}

	/**
	 * convert object to string
	 * @param o object
	 * @return equivalent string value
	 */
	public final static String toString(Object o) {
		if (o == null) {
			return "";
		}
		return o.toString();
	}


	public void setupBackground() {
		super.setBackground(Color.WHITE);
	}

//	/* (non-Javadoc)
//	 * @see javax.swing.JComponent#setBackground(java.awt.Color)
//	 */
//	@Override
//	public void setBackground(Color bg) {
//		fld.setBackground(bg);
//		//btn.setBackground(bg);
//		super.setBackground(bg);
//	}

	public void setBackgroundOfField(Color bg) {
		fld.setBackground(bg);
		tFld.setBackground(bg);
	}

	/**
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
		 if (event.getPropertyName().equals(AbstractPopup.POPUP_CHANGED)) {
			 System.out.println(" ::: " + event.getNewValue());
			 setValue(event.getNewValue());
			 this.firePropertyChange(AbstractPopup.POPUP_CHANGED, event.getOldValue(), event.getNewValue());
		 }
	}

	/**
	 * @return the fld
	 */
	public JTextComponent getField() {
		return fld;
	}

	/**
	 * @param fld the fld to set
	 */
	public void setField(JTextComponent fld) {
		this.fld = fld;
	}

	/**
	 * Set the popup buttons visibility
	 * @param visible wether the button should be visible or not
	 */
	public final void setButtonVisible(boolean visible) {
		btn.setVisible(visible);
	}
}