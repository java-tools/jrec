package net.sf.RecordEditor.utils.swing;


import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import net.sf.JRecord.Details.AbstractLayoutDetails;

/**
 * This class displays the Records in a Layout as a Combobox
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class LayoutCombo extends JComboBox {

	private int[] layoutListMap;
	private int[] layoutListReMap;
	
	private  int preferedIndex;
	private  int fullLineIndex;
	
	private boolean addFullLine, fullList, prefered, addHex;
	
	private AbstractLayoutDetails<?, ?> recordLayout;

	/**
	 * This class displays the Records in a Layout as a Combobox
	 * 
	 * @param layout Record Layout to be displayed
	 * @param addFullLine if a Full Line option should be added
	 * @param fullList wether to include all records (only used for XML)
	 */
	public LayoutCombo(AbstractLayoutDetails<?, ?> layout, boolean addFullLine, boolean fullList) {
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
	public LayoutCombo(AbstractLayoutDetails<?, ?> layout, 
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
	public void setRecordLayout(AbstractLayoutDetails<?, ?> layout) {
		recordLayout = layout;
		
		int i;
		String name;
		boolean include = fullList || ! recordLayout.isXml();
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
				this.addItem(name);
				layoutListMap[i] = this.getItemCount() - 1;
			}
		}
		layoutListReMap = new int[this.getItemCount()];
		for (i = layoutListMap.length - 1; i >= 0; i--) {
			layoutListReMap[layoutListMap[i]] = i;
		}

		//System.out.print(" Preffered.");
		if (prefered) {
			preferedIndex = this.getItemCount();
			this.addItem("Prefered");
		} else {
			preferedIndex = 0;
		}
		fullLineIndex = this.getItemCount();
		if (addFullLine) {
			this.addItem("Full Line");
		}
		if (addHex) {
			this.addItem("Hex 1 Line");
			this.addItem("Hex 2 Lines (Mainframe Style)");
			this.addItem("Hex 3 Lines (ISPF Edit Hex)");
			this.addItem("Hex 2 Lines (Alternative)");
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
