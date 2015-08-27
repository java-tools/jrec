package net.sf.RecordEditor.utils.swing.color;

public class ColorGroupManager {

//	private IColorGroup[] groups = new IColorGroup[IColorGroup.RECORDS + 1];
	private static final ColorGroupManager instance = new ColorGroupManager();

	
	public IColorGroup get(int id) {
		IColorGroup ret;
		if (id == IColorGroup.SPECIAL) {
			ret = ColorGroup.getSpecialColors();
		} else {
			ret = ColorGroup.getStandardColors();
		}
		return ret;
	}

	/**
	 * @return the instance
	 */
	public static final ColorGroupManager getInstance() {
		return instance;
	}
}
