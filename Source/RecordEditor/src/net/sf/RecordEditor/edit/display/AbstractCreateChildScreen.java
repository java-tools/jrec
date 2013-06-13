package net.sf.RecordEditor.edit.display;

import net.sf.RecordEditor.edit.display.common.AbstractRowChangedListner;
import net.sf.RecordEditor.re.display.AbstractFileDisplay;

public interface AbstractCreateChildScreen extends
		AbstractRowChangedListner {

	public static int CS_RIGHT   = 1;
	public static int CS_BOTTOM  = 2;
	public static int CS_BOTH    = 3;
	public static int CS_DEFAULT = 4;

	public AbstractFileDisplay createChildScreen(int position);

	public void removeChildScreen();

	public int getAvailableChildScreenPostion();

	public int getCurrentChildScreenPostion();

}
