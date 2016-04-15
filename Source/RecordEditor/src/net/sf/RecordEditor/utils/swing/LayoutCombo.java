package net.sf.RecordEditor.utils.swing;


import java.awt.event.ActionListener;
import java.util.HashSet;

import javax.swing.JComboBox;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.RecordEditor.utils.lang.LangConversion;

/**
 * This class displays the Records in a Layout as a Combobox
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class LayoutCombo extends JComboBox {

	private static final String FULL_LINE = LangConversion.convertComboItms("layout", "Full Line");
	private static final String LAYOUT_HEX_2_LINE_ALT = LangConversion.convertComboItms("layout", "Hex 2 Lines (Alternative)");
	private static final String LAYOUT_HEX_3_LINE = LangConversion.convertComboItms("layout", "Hex 3 Lines (ISPF Edit Hex)");
	private static final String LAYOUT_HEX_2_LINE_MAINFRAME = LangConversion.convertComboItms("layout", "Hex 2 Lines (Mainframe Style)");
	private static final String LAYOUT_HEX_1_LINE = LangConversion.convertComboItms("layout", "Hex 1 Line");
	private static final String PREFERED_LAYOUT = LangConversion.convertComboItms("layout", "Prefered");
	private int[] layoutListMap;
	private int[] layoutListReMap;

	private  int preferedIndex;
	private  int fullLineIndex;

	private boolean addFullLine, fullList, prefered, addHex;

	private AbstractLayoutDetails recordLayout;

	/**
	 * This class displays the Records in a Layout as a Combobox
	 *
	 * @param layout Record Layout to be displayed
	 * @param addFullLine if a Full Line option should be added
	 * @param fullList wether to include all records (only used for XML)
	 */
	public LayoutCombo(AbstractLayoutDetails layout, boolean addFullLine, boolean fullList) {
		this(layout, addFullLine, fullList, false, false);
	}

	/**
	 * This class displays the Records in a Layout as a Combobox
	 *
	 * @param layout Record Layout to be displayed
	 * @param addFullLine if a Full Line option should be added
	 * @param fullList wether to include all records (only used for XML)
	 * @param prefered wether to add prefered Text
	 */
	public LayoutCombo(AbstractLayoutDetails layout,
			boolean addFullLine, boolean fullList, boolean prefered, boolean addHex) {

		this.addFullLine = addFullLine;
		this.fullList = fullList;
		this.prefered = prefered;
		this.addHex = addHex;

		setRecordLayout(layout);
	}

	/**
	 * Update The Layout
	 * @param layout new record layout
	 */
	public void setRecordLayout(AbstractLayoutDetails layout) {
		recordLayout = layout;

		int i;
		String name;
		boolean include = fullList || ! recordLayout.isXml();
		HashSet<String> usedNames = new HashSet<String>(recordLayout.getRecordCount() * 5 / 4 + 5);
		//System.out.print("Start Adding ...");

		ActionListener[] saveListners = this.getActionListeners();

		for (i = 0 ; i < saveListners.length; i++) {
			this.removeActionListener(saveListners[i]);
		}

		layoutListMap = new int[recordLayout.getRecordCount()];
		this.removeAllItems();
		for (i = 0; i < recordLayout.getRecordCount(); i++) {
			name = recordLayout.getRecord(i).getRecordName();

			layoutListMap[i] = 0;
			if (include || ! name.startsWith("/")) {
				if (usedNames.contains(name)) {
					String t = name + " " + 1;
					int k = 2;
					while (usedNames.contains(t)) {
						t = name + " " + (k++);
					}
					name = t;
				}
				this.addItem(name);
				layoutListMap[i] = this.getItemCount() - 1;
				usedNames.add(name);
			}
		}
		layoutListReMap = new int[this.getItemCount()];
		for (i = layoutListMap.length - 1; i >= 0; i--) {
			layoutListReMap[layoutListMap[i]] = i;
		}

		//System.out.print(" Preffered.");
		if (prefered) {
//			preferedIndex = this.getItemCount();
			preferedIndex = recordLayout.getRecordCount();
			this.addItem(PREFERED_LAYOUT);
		} else {
			preferedIndex = 0;
		}
		fullLineIndex = this.getItemCount();
		if (addFullLine) {
			this.addItem(FULL_LINE);
		}
		if (addHex) {
			this.addItem(LAYOUT_HEX_1_LINE);
			this.addItem(LAYOUT_HEX_2_LINE_MAINFRAME);
			this.addItem(LAYOUT_HEX_3_LINE);
			this.addItem(LAYOUT_HEX_2_LINE_ALT);
		}
		//System.out.println(" Finished add.");

		for (i = 0 ; i < saveListners.length; i++) {
			this.addActionListener(saveListners[i]);
		}

	}

	/**
	 * @return
	 * @see javax.swing.JComboBox#getSelectedIndex()
	 */
	public final int getLayoutIndex() {
		int ret = getSelectedIndex();

		if (ret < layoutListReMap.length) {
			ret = layoutListReMap[ret];
		} else if (ret == layoutListReMap.length) {
			ret = preferedIndex;
		}

		return ret;
	}


	/**
	 * @param recordIndex new record index
	 * @see javax.swing.JComboBox#setSelectedIndex(int)
	 */
	public final void setLayoutIndex(int recordIndex) {
		if (recordIndex >= 0 && recordIndex < layoutListMap.length) {
			setSelectedIndex(layoutListMap[recordIndex]);
		}
	}

	/**
	 * @return the preferedIndex
	 */
	public final int getPreferedIndex() {
		return preferedIndex;
	}

	/**
	 * @return the fullLineIndex
	 */
	public final int getFullLineIndex() {
		return fullLineIndex;
	}

}
