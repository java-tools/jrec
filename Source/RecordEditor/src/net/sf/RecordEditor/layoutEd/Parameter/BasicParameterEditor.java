package net.sf.RecordEditor.layoutEd.Parameter;

import net.sf.RecordEditor.utils.common.Common;

import com.zbluesoftware.java.bm.AbstractPopup;

/**
 * This class will return a popup editor and check if  parameter value is
 * valid
 * 
 * @author Bruce Martin
 */
public class BasicParameterEditor {

	Class<? extends AbstractPopup> thePopupClass = null;
	
	/**
	 * Very basic editor, you need to overide it
	 *
	 */
	protected BasicParameterEditor() {
		
	}
	
	/**
	 * Create parameter editor using a special popup class
	 * @param popupClass
	 */
	public BasicParameterEditor(Class<? extends AbstractPopup> popupClass) {
		thePopupClass = popupClass;
	}
	
	/**
	 * Get the Popup editor
	 * @return Popup editor
	 */
	public AbstractPopup getPopup(int databaseId) {
		AbstractPopup ret = null;
		if (thePopupClass != null) {
			try {
				ret = thePopupClass.newInstance();
			} catch (Exception ex) {
				Common.logMsg("Can not create popup: " + thePopupClass.getName(), ex);
			}
		}
		
		return ret;
	}

	/**
	 * wether ther is a popu or not ...
	 * @return
	 */
	public boolean hasPopup() {
		return thePopupClass != null;
	}
	
	/**
	 * Check if value is valid
	 * @param value value to check
	 * @return is it valid
	 */
	public boolean isValid(String value) {
		return true;
	}

}
