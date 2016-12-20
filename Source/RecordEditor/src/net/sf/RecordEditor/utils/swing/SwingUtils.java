package net.sf.RecordEditor.utils.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URI;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import net.sf.RecordEditor.utils.basicStuff.BasicTable;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.params.IHasKey;


public class SwingUtils {

	private static final Color ALTERNATE_ROW_COLOR ;
	public static final int STANDARD_FONT_HEIGHT, STANDARD_FONT_WIDTH;
	public static final int TABLE_ROW_HEIGHT = getDefault((new JTable()).getRowHeight(), 16);
	private static final int HALF_TABLE_ROW_HEIGHT = TABLE_ROW_HEIGHT / 2;
	public static final int COMBO_TABLE_ROW_HEIGHT ;
	public static final int TABLE_BUTTON_WIDTH ;
	public static final int ONE_CHAR_TABLE_CELL_WIDTH ;
	public static final int NORMAL_FIELD_HEIGHT;
	public static final int CHECK_BOX_HEIGHT,  CHECK_BOX_WIDTH;
	public static final int BUTTON_HEIGHT = (new JButton("Aa")).getMinimumSize().height;
	public static final Font MONO_SPACED_FONT;
	public static final int TIP_HEIGHT;
	public static final int CHAR_FIELD_WIDTH, CHAR_FIELD_HEIGHT;

	private static String[] r = {"Aapj"};
	static {
		JTextField fld = new JTextField(r[0]);
		JCheckBox chk = new JCheckBox();
		double d = fld.getMinimumSize().getHeight();
		int sub = 2;
		if (Common.LOOKS_INDEX == 0 || Common.LOOKS_INDEX == 2) {
			sub = 4;
		}
		Color c = null;
		try { c = UIManager.getColor("Table.alternateRowColor"); } catch (Exception e) {}
		ALTERNATE_ROW_COLOR = c;
		STANDARD_FONT_HEIGHT = getDefault(fld.getFont().getSize(), 12);
		STANDARD_FONT_WIDTH  = STANDARD_FONT_HEIGHT * 3 / 4;
		COMBO_TABLE_ROW_HEIGHT = getDefault((int) ((new JComboBox(r)).getMinimumSize().getHeight()) - sub, 20);
		CHECK_BOX_HEIGHT = chk.getMinimumSize().height;
		CHECK_BOX_WIDTH = chk.getMinimumSize().width;

		if (d <= 0) {
			d = 19;
		}
		NORMAL_FIELD_HEIGHT = (int) d;
//		System.out.println(">>> " + UIManager.getSystemLookAndFeelClassName() + " " + JFrame.isDefaultLookAndFeelDecorated()
//				+ " " + BUTTON_HEIGHT
//				+ " " + ((new JButton("Aa")).getMinimumSize().height)
//				);


		MONO_SPACED_FONT = new Font("Monospaced", Font.PLAIN,  STANDARD_FONT_HEIGHT);

		int tblCellWidth = TABLE_ROW_HEIGHT;

        if (Common.NIMBUS_LAF) {
        	tblCellWidth = COMBO_TABLE_ROW_HEIGHT;
        }

		ONE_CHAR_TABLE_CELL_WIDTH = tblCellWidth;
		TABLE_BUTTON_WIDTH = ONE_CHAR_TABLE_CELL_WIDTH / 3;

		TIP_HEIGHT = SwingUtils.STANDARD_FONT_HEIGHT * 11;

		String s = "ABCDEFGHIJKLMNOPQRST abcdefghijklmnopqrst@#?&\"";

		JTextField jTextField = new JTextField(s);
		CHAR_FIELD_WIDTH  = jTextField.getPreferredSize().width / s.length();
		CHAR_FIELD_HEIGHT = jTextField.getPreferredSize().height;

		System.out.println("### '''''''''''''''''''''''''''''''");
		System.out.println("###            Font Height " + STANDARD_FONT_HEIGHT);
		System.out.println("###           Field Height " + NORMAL_FIELD_HEIGHT);
		System.out.println("###        Checkbox Height " + CHECK_BOX_HEIGHT);
		System.out.println("###       Table Row Height " + TABLE_ROW_HEIGHT);
		System.out.println("### Combo Table Row Height " + COMBO_TABLE_ROW_HEIGHT);
		System.out.println("###      1 char Cell width " + CHAR_FIELD_WIDTH);
		System.out.println("### ...............................");
	}

	private static int getDefault(int val, int defaultVal) {
		int ret = val;
		if (ret <= 0) {
			ret = defaultVal;
		}
		return ret;
	}

	public static int calculateTableHeight(int records, int maxHeight) {
		return Math.min(maxHeight, (2*records + 3) * TABLE_ROW_HEIGHT / 2 + HALF_TABLE_ROW_HEIGHT);
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


	public static void setTableCellColors(JComponent component, JTable table, int row, boolean isSelected) {

		setTableCellBackGround(component, table, row, isSelected);
		if (isSelected) {
			component.setForeground(table.getSelectionForeground());
		} else {
			component.setForeground(table.getForeground());
		}

	}

	public static void setTableCellBackGround(JComponent component, JTable table, int row, boolean isSelected) {

		Color background;
		component.setOpaque(true);

		if (isSelected) {
			background = table.getSelectionBackground();
		} else {
			background = table.getBackground();
			if (Common.NIMBUS_LAF) {
			   	background = Color.WHITE;
			    if ( row % 2 != 0 && ALTERNATE_ROW_COLOR != null) {
			    	background = ALTERNATE_ROW_COLOR;
			    }
			}
		}
		component.setBackground(background);

	}

	public static void setCombo(JComboBox combo, Object value) {

		combo.setSelectedItem(value);

		if (value != null && ! value.equals(combo.getSelectedItem())) {
			Object o;
			for (int i = 0; i < combo.getItemCount(); i++) {
				o = combo.getItemAt(i);
				if (o != null
				&& (   o.equals(value)
					|| (   o instanceof IHasKey
						&& ((IHasKey) o).getKey().equals(value)))) {
					combo.setSelectedIndex(i);
					break;
				}
			}
		}

	}
   /**
     * Get Help button
     *
     * @return HelpButton;
     */
    public static final JButton getHelpButton() {
        return SwingUtils.newButton("Help", Common.getRecordIcon(Common.ID_HELP_ICON));
    }

	public static JMenu newMenu(String s) {
		return new JMenu(LangConversion.convert(LangConversion.ST_MENU, s));
	}

	public static JMenuItem newMenuItem(String s) {
		return new JMenuItem(LangConversion.convert(LangConversion.ST_MENU, s));
	}

	public static JButton newButton(String s) {
		return new JButton(LangConversion.convert(LangConversion.ST_BUTTON, s));
	}
	
	public static JRadioButton newRadioButton(String s) {
		return new JRadioButton(LangConversion.convert(LangConversion.ST_BUTTON, s));
	}

	public static JCheckBox newCheckBox(String s) {
		return new JCheckBox(LangConversion.convert(LangConversion.ST_BUTTON, s));
	}

	public static JFlipBtn newFlipButton(String text, String altText) {
		return new JFlipBtn(LangConversion.convert(LangConversion.ST_BUTTON, text),
							LangConversion.convert(LangConversion.ST_BUTTON, altText));
	}


	public static JButton newButton(String s, Icon icon) {
		return new JButton(LangConversion.convert(LangConversion.ST_BUTTON, s), icon);
	}

	public static void addTab(JTabbedPane tab, String tabId, String name, JComponent tabComponenet) {
		tab.addTab(LangConversion.convertDesc(LangConversion.ST_TAB, name, "Tab: " + tabId), tabComponenet);
	}


	public static void insertTab(JTabbedPane tab, String tabId, String name, JComponent tabComponenet, String tip, int index) {
		tab.insertTab(
				LangConversion.convertDesc(LangConversion.ST_TAB, name, "Tab: " + tabId),
				null, tabComponenet, tip, index);
	}

	public static JPanel getPanelWith(JComponent c) {
		JPanel p = new JPanel();
		p.add(c);
		return p;
	}

	public static void clickOpenBtn(JFileChooser fileChooser, boolean doEnter) {
		String s = System.getProperty("java.version").substring(0, 4);
		try {
			if (s.startsWith("1.5.") || s.startsWith("1.6.")) {
				if (doEnter) {
					fileChooser.getActionForKeyStroke(
							KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0))
								.actionPerformed(null);
				}
			} else {
				JButton btn = fileChooser.getUI().getDefaultButton(fileChooser);
				if (btn != null) {
					btn.doClick();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void copySelectedCells(JTable dtlTbl) {
		int startRow = dtlTbl.getSelectedRow();
		int startCol = dtlTbl.getSelectedColumn();
		int endRow = startRow + dtlTbl.getSelectedRowCount();
		int endCol = startCol + dtlTbl.getSelectedColumnCount();
		StringBuilder b = new StringBuilder();
		String fldSep = ""; 
		String lineSep = "";
		Object val;
		
		if (endRow >= startRow && endCol >= startCol) {
			for (int i = startRow; i < endRow; i++) {
				b.append(lineSep);
				lineSep = "\n";
				for (int j = startCol; j < endCol; j++) {
					b.append(fldSep);
					fldSep = "\t";
					val = dtlTbl.getValueAt(i, j);
					if (val != null) {
						b.append(val.toString());
					}
				}
			}
			Clipboard system = Toolkit.getDefaultToolkit().getSystemClipboard();
			system.setContents(new StringSelection(b.toString()), null);
		}
	}

	
	public static void pasteOver(Object requestor, JTable tblDetails, int startRow, int startCol, int numRows, int numCols) {
		if (startRow >= 0) {
			Clipboard system = Toolkit.getDefaultToolkit().getSystemClipboard();
			try {
				pasteTable(tblDetails, 
						startRow, startCol,
						numRows, numCols,
						(String) (system.getContents(requestor)
								.getTransferData(DataFlavor.stringFlavor)));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void showInBrowser(URI uri) {
		if (java.awt.Desktop.isDesktopSupported()) {
			try {
				java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

                desktop.browse(uri);
            } catch ( Exception ex ) {
                System.err.println( "Error showing Web page" + ex.getMessage() );
            }
		}
	}


	public static final void pasteTable(JTable tblDetails, int startRow, int startCol, int rowCount, int colCount, String trstring) {
		String val;
		BasicTable tbl = new BasicTable(trstring);

		rowCount = Math.min(rowCount, tblDetails.getRowCount() - startRow);
		colCount = Math.min(colCount, tblDetails.getColumnCount() - startCol);

		Common.stopCellEditing(tblDetails);

		for (int i = 0; tbl.hasMoreRows() && i < rowCount ; i++) {
			tbl.nextRow();
			for (int j = 0; tbl.hasMoreColumns() && j < colCount ; j++) {
				val = tbl.nextColumn();
				if (tblDetails.isCellEditable(startRow + i, startCol + j)) {	
					tblDetails.setValueAt(val, startRow + i, startCol + j);
				}
			}
		}
	}

}
