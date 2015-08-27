package net.sf.RecordEditor.utils.swing.color;

import java.awt.Color;

import net.sf.RecordEditor.utils.params.Parameters;


public class ColorGroup implements IColorGroup {

	private static Color[] STD_FOREGROND_COLORS = {
//		new Color(153,  0,   0), new Color(153, 51,   0), new Color(51, 51,   0), new Color(13, 26,   0), 
//		new Color(0,  102, 102), new Color(0,   51, 153), new Color(0,     0, 153), new Color(102,  0, 204),
//		new Color(153,  0, 153), new Color(204,   0, 102), new Color(128, 128, 128), Color.BLACK
//	};
	Color.MAGENTA, 
	/* Deep Magenta	new Color(205, 0,   205),*/  Color.BLUE,  
	/* Fire brick 4 */  new Color(205, 38,   38), Color.BLACK, Color.BLACK, 
    /* purple 2 */ new Color(145, 44, 238), 
	/* sap green */ new Color(34, 139, 34), Color.RED, 
	Color.DARK_GRAY, Color.CYAN
	/* Color.PINK, Color.ORANGE*/
	};
	
	private static Color[] STD_BACKGROND_COLORS = {
		null, 	null,   null, null,  new Color(255, 255, 212), null, null, null, null, Color.BLUE
	};

	
	private static Color[] SPECIAL_FOREGROND_COLORS = {
		Color.BLUE
	};
	
	
	private static Color[] SPECIAL_BACKGROUND_COLORS = {
		new Color(204, 255, 255)
	};
	private final Color[] foreground, background;
	private final int /*size,*/ type;
	
//	public ColorGroup(int type,
//			List<Color> foregroundColors, List<Color> backgroundColors,
//			String paramVarName, boolean canChangeSize) {
//		this(type, listToArray(foregroundColors), listToArray(backgroundColors), paramVarName, canChangeSize);
//	}
	
	
	
	private ColorGroup(int type, Color[] dfltForeground, Color[] dfltBackground,
			String paramVarName, boolean canChangeSize) {
		super();
		this.type = type;
		
		int tmpSize = Math.max(
				dfltForeground == null ? 0 : dfltForeground.length, 
				dfltBackground == null ? 0 : dfltBackground.length);
		
		if (canChangeSize) {
			String countStr = Parameters.getString(paramVarName + "count");

			if (countStr != null && ! "".equals(countStr)) {
				try {
					tmpSize = Integer.parseInt(countStr);
				} catch (Exception e) {
				}
			}
		}
		
		this.foreground = adjustSize(dfltForeground, tmpSize);
		this.background = adjustSize(dfltBackground, tmpSize);
		

		for (int i = 0; i < tmpSize; i++) {
			readParams_setColor(foreground, i, Parameters.getString(paramVarName + i));
			readParams_setColor(dfltBackground, i, Parameters.getString(paramVarName + "back." + i));
		}
	}
	
	private Color[] adjustSize(Color[] dfltColors, int size) {
		
		Color[] tc = dfltColors;
		if (dfltColors == null) {
			tc = new Color[size];
		} else if (size > dfltColors.length) {
			tc = new Color[size];
			System.arraycopy(dfltColors, 0, tc, 0, dfltColors.length);
		}
		return tc;
	}
	
	
	private void readParams_setColor(Color[] colors, int idx, String colorStr) {

		if ("null".equalsIgnoreCase(colorStr)) {
			colors[idx] = null;
		} else if (colorStr != null && ! "".equals(colorStr)) {
			try {  
				colors[idx] = Color.BLACK;
				colors[idx] = Color.decode(colorStr);
				System.out.print("\t > " + colorStr + " " + Integer.decode(colorStr) + " "  + " " + colors[idx].toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


//	private static Color[] listToArray(List<Color> colors) {
//		Color[] ret = null;
//		
//		if (colors != null && colors.size() > 0) {
//			if (colors.get(colors.size() - 1) == null) {
//				for (int i = colors.size() - 2; i >= 0; i--) {
//					if (colors.get(i) != null) {
//						ret = new Color[i+1];
//						for (int j = i; j >= 0; j--) {
//							ret[j] = colors.get(i);
//						}
//						break;
//					}
//				}
//			} else {
//				ret = colors.toArray(new Color[colors.size()]);
//			}
//		}
//		
//		return ret;
//	}
//	
//	private static int getSize(Color[] c) {
//		int ret = 0;
//		if (c != null) {
//			ret = c.length;
//		}
//		return ret;
//	}
	
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.color.IColorGroup#size()
	 */
	@Override
	public int size() {
		return foreground.length;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.color.IColorGroup#getGroupType()
	 */
	@Override
	public int getGroupType() {
		return type;
	}



	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.color.IColorGroup#getForegroundColor(int)
	 */
	@Override
	public Color getForegroundColor(int idx) {
		if (foreground != null && idx >= 0 && idx < foreground.length) {
			return foreground[idx];
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.color.IColorGroup#getBackgroundColor(int)
	 */
	@Override
	public Color getBackgroundColor(int idx) {
		if (background != null && idx >= 0 && idx < background.length) {
			return background[idx];
		}
		
		return null;
	}




	public static ColorGroup getStandardColors() {
		return new ColorGroup(FIELDS, STD_FOREGROND_COLORS, STD_BACKGROND_COLORS, Parameters.FIELD_COLORS, true);
	}


	public static ColorGroup getDefaultStandardColors() {
		return new ColorGroup(FIELDS, STD_FOREGROND_COLORS, null, null, false);
	}


	public static ColorGroup getSpecialColors() {
		return new ColorGroup(SPECIAL, SPECIAL_FOREGROND_COLORS, SPECIAL_BACKGROUND_COLORS, Parameters.FIELD_COLORS, false);
	}
	

	public static ColorGroup getDefaultSpecialColors() {
		return new ColorGroup(SPECIAL, SPECIAL_FOREGROND_COLORS, SPECIAL_BACKGROUND_COLORS, null, false);
	}

}
