package net.sf.RecordEditor.utils.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.KeyAdapter;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;

import net.sf.RecordEditor.utils.common.Common;


public class SwingUtils {

	public static final int STANDARD_FONT_HEIGHT, STANDARD_FONT_WIDTH;
	public static final int TABLE_ROW_HEIGHT = getDefault((new JTable()).getRowHeight(), 24);
	private static final int HALF_TABLE_ROW_HEIGHT = TABLE_ROW_HEIGHT / 2;
	public static final int COMBO_TABLE_ROW_HEIGHT ;
	public static final int ONE_CHAR_TABLE_CELL_WIDTH ;
	public static final int NORMAL_FIELD_HEIGHT;
	public static final int CHECK_BOX_HEIGHT,  CHECK_BOX_WIDTH;
	public static final int BUTTON_HEIGHT = (new JButton("Aa")).getPreferredSize().height;
	public static final Font MONO_SPACED_FONT;

	private static String[] r = {"Aapj"};
	static {
		JTextField fld = new JTextField(r[0]);
		JCheckBox chk = new JCheckBox(); 
		double d = fld.getMinimumSize().getHeight();
		STANDARD_FONT_HEIGHT = getDefault(fld.getFont().getSize(), 12);
		STANDARD_FONT_WIDTH  = STANDARD_FONT_HEIGHT * 3 / 4;
		COMBO_TABLE_ROW_HEIGHT = getDefault((int) ((new JComboBox(r)).getMinimumSize().getHeight()), 20);
		CHECK_BOX_HEIGHT = chk.getMinimumSize().height;
		CHECK_BOX_WIDTH = chk.getMinimumSize().width;

		
		if (d <= 0) {
			d = 19;
		}
		NORMAL_FIELD_HEIGHT = (int) d;

		System.out.println("### '''''''''''''''''''''''''''''''");
		System.out.println("###            Font Height " + STANDARD_FONT_HEIGHT);
		System.out.println("###           Field Height " + NORMAL_FIELD_HEIGHT);
		System.out.println("###        Checkbox Height " + CHECK_BOX_HEIGHT);
		System.out.println("###       Table Row Height " + TABLE_ROW_HEIGHT);
		System.out.println("### Combo Table Row Height " + COMBO_TABLE_ROW_HEIGHT);
		System.out.println("### ...............................");

		MONO_SPACED_FONT = new Font("Monospaced", Font.PLAIN,  STANDARD_FONT_HEIGHT);
		
		int tblCellWidth = TABLE_ROW_HEIGHT;
		
        if (Common.NIMBUS_LAF) {
        	tblCellWidth = COMBO_TABLE_ROW_HEIGHT;
        }
		
		ONE_CHAR_TABLE_CELL_WIDTH = tblCellWidth;
	}

	private static int getDefault(int val, int defaultVal) {
		int ret = val;
		if (ret <= 0) {
			ret = defaultVal;
		}
		return ret; 
	}
	
	public static int calculateTableHeight(int records, int maxHeight) {
		return Math.min(maxHeight, (records + 1) * TABLE_ROW_HEIGHT + HALF_TABLE_ROW_HEIGHT);
	}
	
	
	public static int calculateComboTableHeight(int records, int maxHeight) {
		return Math.min(
				maxHeight, 
				(records + 1) * COMBO_TABLE_ROW_HEIGHT + HALF_TABLE_ROW_HEIGHT);
	}
	
	/**
	 * Get standard sized font
	 * @return standard sized font
	 */
	public static Font getMonoSpacedFont() {
		return MONO_SPACED_FONT;
	}


	
	public static void addKeyListnerToContainer(Container c, KeyAdapter keyListner) {
		Component[] clist = c.getComponents();
		for (Component cc : clist) {
			if (cc instanceof Container) {
				addKeyListnerToContainer((Container) cc, keyListner);
			} else {
				cc.addKeyListener(keyListner);
			}
		}
		c.addKeyListener(keyListner);
	}
	

}
