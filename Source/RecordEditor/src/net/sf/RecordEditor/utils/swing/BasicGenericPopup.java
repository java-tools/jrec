package net.sf.RecordEditor.utils.swing;

import com.zbluesoftware.java.bm.AbstractGenericCombo;
import com.zbluesoftware.java.bm.AbstractPopup;

@SuppressWarnings("serial")
public class BasicGenericPopup extends AbstractGenericCombo {
	
	private AbstractPopup popup = null;
	
	/**
	 * @param popup the current to set
	 */
	public void setPopup(AbstractPopup newPopup) {
		this.popup = newPopup;
	}

	/**
	 * @see com.zbluesoftware.java.bm.AbstractGenericCombo#getPopup()
	 */
	@Override
	public AbstractPopup getPopup() {
		return popup;
	}

	/**
	 * @see com.zbluesoftware.java.bm.AbstractGenericCombo#getValue()
	 */
	@Override
	public Object getValue() {
		return getText();
	}


	/**
	 * @see com.zbluesoftware.java.bm.AbstractGenericCombo#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(Object value) {
		if (popup != null) {
			popup.setValue(value);
		}
		setText(toString(value));
	}
}
