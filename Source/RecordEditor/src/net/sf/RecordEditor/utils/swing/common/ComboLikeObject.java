package net.sf.RecordEditor.utils.swing.common;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.text.JTextComponent;

import com.zbluesoftware.java.bm.ArrowButton;

@SuppressWarnings("serial")
public class ComboLikeObject extends JPanel implements ActionListener {

	private static final int FIELD_WIDTH = 20;

	private final JTextComponent valueTxt;

	protected final JButton showListBtn = new ArrowButton(ArrowButton.SOUTH);

	private final JButton[] buttons;

	private int popupHeight = -1;
	//protected boolean visible = false;
	private JPopupMenu currentPopup = null;
	private PopupMenuListener popupListner = new PopupMenuListener() {

		@Override
		public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
		}

		@Override
		public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
		}

		@Override
		public void popupMenuCanceled(PopupMenuEvent e) {
			highlightItem(currentPopup, false);
		}
	};

	private List<ChangeListener> chgListners = new ArrayList<ChangeListener>(5);

	public ComboLikeObject() {
		this(new JPopupMenu());
	}

	public ComboLikeObject(JButton... btns) {
		this(new JPopupMenu(), btns);
	}

	public ComboLikeObject(JPopupMenu popup) {
		this(popup, (JButton[]) null);
	}


	public ComboLikeObject(JPopupMenu popup, JButton... btns) {
		this(new JTextField(), popup, btns);
	}


	public ComboLikeObject(JTextComponent txtComponent, JPopupMenu popup, JButton... btns) {
		super();

		valueTxt = txtComponent;
		buttons = btns;
		setCurrentPopup(popup);
		init(btns);
	}

	protected void init(JButton[] btns) {

		valueTxt.setOpaque(true);
		valueTxt.setMinimumSize(new Dimension(FIELD_WIDTH, valueTxt.getHeight()));
//		System.out.println(" ++> ++> " +  " "
//				+ valueTxt.getPreferredSize().height + " " + this.getPreferredSize().height);

		this.setLayout(new BorderLayout());
		this.add(BorderLayout.CENTER, valueTxt);

		if (btns == null || btns.length == 0) {
			this.add(BorderLayout.EAST, showListBtn);
		} else {
			JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
//			int height = valueTxt.getPreferredSize().height;
			p.setBorder(BorderFactory.createEmptyBorder());

			p.add(showListBtn);

			//this.add(BorderLayout.EAST, showListBtn);
			for (JButton btn : btns) {
				p.add(btn);
				btn.setBorder(BorderFactory.createEmptyBorder());
//				height = Math.max(height, btn.getPreferredSize().height);
			}
			this.add(BorderLayout.EAST, p);
//			p.setPreferredSize(new Dimension(p.getPreferredSize().width, height));
//			System.out.println(" +_+_+_+ " + height + " " + valueTxt.getPreferredSize().height);
////			this.setPreferredSize(new Dimension(this.getPreferredSize().width, height));
//			System.out.println(" --> --> " +  " "
//					+ valueTxt.getPreferredSize().height + " " + this.getPreferredSize().height
//					+ " ~ " + p.getPreferredSize().height + " " + p.getPreferredSize().height);
		}

		this.setBorder(valueTxt.getBorder());
		valueTxt.setBorder(BorderFactory.createEmptyBorder());
		showListBtn.addActionListener(this);
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

    	JPopupMenu nm = getPopup();

    	setCurrentPopup(nm);

        if (currentPopup.isVisible()) {
        	currentPopup.setVisible(false);
        	highlightItem(currentPopup, false);
        } else {
         	if (currentPopup != null) {
        		int xCoord = 0;
        		int yCoord = valueTxt.getHeight();

        		currentPopup.show(valueTxt, xCoord, yCoord);
        		if (currentPopup.getWidth() < this.getWidth()) {
        			currentPopup.setPopupSize(new Dimension(
        					this.getWidth(),
        					Math.max(currentPopup.getHeight(), popupHeight)));
        		}

        		highlightItem(currentPopup, true);
         	}
	    }
	    //visible = ! visible;
	}

	protected void hidePopup() {
		currentPopup.setVisible(false);
	}

	protected void highlightItem(JPopupMenu currentPopup, boolean visible) {

	}

	protected static JButton[] addToArray(JButton b, JButton[] btns) {
		JButton[] ret = btns;
		if (btns == null || btns.length == 0) {
			ret = new JButton[1];
		} else if (btns.length > 1 || btns[0] != null){
			ret = new JButton[btns.length + 1];
			System.arraycopy(btns, 0, ret, 1, btns.length);
		}

		ret[0] = b;

		return ret;
	}

	/**
	 * @return the currentPopup
	 */
	public JPopupMenu getCurrentPopup() {
		return currentPopup;
	}

	/**
	 * @param newPopup the currentPopup to set
	 */
	protected void setCurrentPopup(JPopupMenu newPopup) {

    	if ( this.currentPopup != newPopup) {
    		if (this.currentPopup != null) {
    			this.currentPopup.removePopupMenuListener(popupListner);
    		}
    		newPopup.addPopupMenuListener(popupListner);
    		this.currentPopup = newPopup;
    	}
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
		fireValueChangeListner(null);
	}

	/**
	 * @return
	 * @see javax.swing.text.JTextComponent#getText()
	 */
	public String getText() {
		return valueTxt.getText();
	}

	public JTextComponent getTextCompenent() {
		return valueTxt;
	}



	/**
	 * @param popupHeight the popupHeight to set
	 */
	protected void setPopupHeight(int popupHeight) {
		if (popupHeight > 0) {
			this.popupHeight = popupHeight;

			currentPopup.setPopupSize(new Dimension(
						this.getWidth(),
						popupHeight));
		}
	}

	public final void addFileChangeListner(ChangeListener cl) {
		chgListners.add(cl);
	}

	public final void removeFileChangeListner(ChangeListener cl) {
		chgListners.remove(cl);
	}

	/**
	 * @param name
	 * @see java.awt.Component#setName(java.lang.String)
	 */
	public void setName(String name) {
		valueTxt.setName(name + "_Txt");

		if (buttons != null) {
			int i = 0;
			String btnName = name + "_Btn";
			for (JButton b : buttons) {
				b.setName(btnName + i++);
			}
		}

		super.setName(name);
	}
	protected void fireValueChangeListner(ChangeEvent e) {

		for (ChangeListener cl : chgListners) {
			cl.stateChanged(e);
		}
	}
}
