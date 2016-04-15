package net.sf.RecordEditor.utils.swing.common;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
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

import net.sf.RecordEditor.utils.common.Common;

import com.zbluesoftware.java.bm.ArrowButton;

@SuppressWarnings("serial")
public class ComboLikeObject extends JPanel implements ActionListener {

	private static final int FIELD_WIDTH = 20;

	private final JTextComponent valueTxt;

	protected final JButton showListBtn = new ArrowButton(ArrowButton.SOUTH);

	private final JButton[] buttons;

	private int popupHeight = -1;
	//protected boolean visible = false;
	
	private boolean notifyOfAllUpdates = false;
	private long popupBecameInvisibleAt = 0;
	
	private JPopupMenu currentPopup = null;
	private PopupMenuListener popupListner = new PopupMenuListener() {

		@Override 
		public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
		}

		@Override
		public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			popupBecameInvisibleAt = System.currentTimeMillis();
		}

		@Override
		public void popupMenuCanceled(PopupMenuEvent e) {
			highlightItem(currentPopup, false);
		}
	};
	

	private List<ChangeListener> chgListners = new ArrayList<ChangeListener>(5);

	private String lastVal = null;

	public ComboLikeObject() {
		this(new JPopupMenu());
	}

	public ComboLikeObject(String txtFldName, JButton... btns) {
		this(txtFldName, new JPopupMenu(), btns);
	}

	public ComboLikeObject(JPopupMenu popup) {
		this(null, popup, (JButton[]) null);
	}


	public ComboLikeObject(String txtFldName, JPopupMenu popup, JButton... btns) {
		this(new JTextField(), txtFldName, popup, btns);
	}


	public ComboLikeObject(JTextComponent txtComponent, String fieldName, JPopupMenu popup, JButton... btns) {
		super();

		valueTxt = txtComponent;
		buttons = btns;
		setCurrentPopup(popup);
		
		if (fieldName != null) {
			valueTxt.setName(fieldName);
		}
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
//			System.out.println();
//			System.out.print("==!!==>\t" + showListBtn.getPreferredSize().width);
			for (JButton btn : btns) {
				p.add(btn);
				//if (btn.getPreferredSize().width < showListBtn.getPreferredSize().width) {
					btn.setPreferredSize(showListBtn.getPreferredSize());
				//}
				//btn.setBorder(BorderFactory.createEmptyBorder());
//				System.out.print("\t" + btn.getPreferredSize().width);
//				height = Math.max(height, btn.getPreferredSize().height);
			}
			this.add(BorderLayout.EAST, p);
//			System.out.println("~~!!~~>> " + p.getPreferredSize().width);
//			p.setPreferredSize(new Dimension(p.getPreferredSize().width, height));
//			System.out.println(" +_+_+_+ " + height + " " + valueTxt.getPreferredSize().height);
////			this.setPreferredSize(new Dimension(this.getPreferredSize().width, height));
//			System.out.println(" --> --> " +  " "
//					+ valueTxt.getPreferredSize().height + " " + this.getPreferredSize().height
//					+ " ~ " + p.getPreferredSize().height + " " + p.getPreferredSize().height);
		}

		if (! Common.NIMBUS_LAF) {
			this.setBorder(valueTxt.getBorder());
		}
		valueTxt.setBorder(BorderFactory.createEmptyBorder());
		showListBtn.addActionListener(this);

		valueTxt.addFocusListener(new FocusAdapter() {
			@Override public void focusLost(FocusEvent e) {
				fireValueChangeListner(null);
			}
		});
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

    	JPopupMenu nm = getPopup();

        long timeDiff = System.currentTimeMillis() - popupBecameInvisibleAt;
        setCurrentPopup(nm);

 		if (currentPopup.isVisible() || (timeDiff > 0 && timeDiff < 650) ) {
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
	
	public void setTextSilently(String t) {
		valueTxt.setText(t);
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

	public final void addTextChangeListner(ChangeListener cl) {
		chgListners.add(cl);
	}

	public final void removeTextChangeListner(ChangeListener cl) {
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

		String textVal = valueTxt.getText();
		if ((lastVal == null || notifyOfAllUpdates || ! lastVal.equals(textVal))) {
			try {
				for (int i = 0; i < chgListners.size(); i++) {
					chgListners.get(i).stateChanged(e);
				}
				lastVal = textVal;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * @param notifyOfAllUpdates the notifyOfAllUpdates to set
	 */
	public final void setNotifyOfAllUpdates(boolean notifyOfAllUpdates) {
		this.notifyOfAllUpdates = notifyOfAllUpdates;
	}
}
