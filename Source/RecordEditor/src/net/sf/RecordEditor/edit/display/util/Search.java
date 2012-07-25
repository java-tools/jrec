/*
 * Created on 18/05/2004
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *   - added basic Replace functionality
 *   - changed to use ReFrame (from JFrame) which keeps track
 *     of the active form
 *   - changed to use ReActionHandler instead of ActionListener
 *
 * # Version 0.60 Bruce Martin 2007/02/16
 *   - new all fields search and replace options
 *
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - Refactoring changes from moving classes to new packages, and
 *     creation of JRecord
 *
 * # Version 0.62 Bruce Martin 2007/04/30
 *   - adding support for enter key
 *
 */
package net.sf.RecordEditor.edit.display.util;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.RecordEditor.edit.display.common.ILayoutChanged;
import net.sf.RecordEditor.re.file.FilePosition;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.file.filter.Compare;
import net.sf.RecordEditor.re.script.AbstractFileDisplay;
import net.sf.RecordEditor.utils.common.Common;

import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.LayoutCombo;
import net.sf.RecordEditor.utils.swing.SwingUtils;


/**
 * This class displays a search screen, then searches the file for the supplied
 * String in the file
 *
 * @author Bruce Martin
 * @version 0.51
 *
 *
 */
@SuppressWarnings("serial")
public final class Search extends ReFrame implements ActionListener, ILayoutChanged {


	public static final boolean USE_SPECIAL_NAME_FIND_BTN = "Y".equalsIgnoreCase(
			Parameters.getString(Parameters.SPECIAL_FIND_BTN_NAME));

    private static final String ALL_FIELDS = LangConversion.convertComboItms("Field List", "All Fields");
	private static final String FIND_ERROR = LangConversion.convert("Find error:");
	private static final String LINE_MSG = LangConversion.convert("Found (line, field Num, field position)=");
	private static final String YOU_MUST_SPECIFY_A_FIELD = LangConversion.convert("You must specify a specific field when using replace");
	private static final String CAN_NOT_USE_REPLACE_IN_BROWSE_MODE = LangConversion.convert("Can not use replace in browse mode");
	private static final String YOU_MUST_ENTER_TEXT_TO_SEARCH_FOR  = LangConversion.convert("You must enter text to search for");

	private static final int SILLY_INT = -101;
	private static final String[] SEARCH_DIRECTIONS = LangConversion.convertComboItms(
			"File Search Direction",
			new String[] {"Forward", "Backward"});


	private JTextField search      = new JTextField();
	private JTextField replace     = new JTextField();
	private LayoutCombo layoutList;
	private JComboBox fieldList    = new JComboBox();
	private JComboBox fieldPart    = new JComboBox(Compare.getSearchOptionsForeign());
	private JComboBox direction    = new JComboBox(SEARCH_DIRECTIONS);
	private JCheckBox ignoreCase   = new JCheckBox();
	private JButton searchBtn;
	private JButton replaceBtn     = SwingUtils.newButton("Replace");
	private JButton replaceFindBtn = SwingUtils.newButton("Replace Find");
	private JButton replaceAllBtn  = SwingUtils.newButton("Replace All");

	private JTextField msgTxt      = new JTextField();

	private FilePosition pos = new FilePosition(SILLY_INT, -SILLY_INT, SILLY_INT, SILLY_INT, true);


	private AbstractFileDisplay source;

	@SuppressWarnings("rawtypes")
	private FileView file;
	private boolean firstTimeDisplayed = true;
	//private int layout;

    private KeyAdapter listner = new KeyAdapter() {
        /**
         * @see java.awt.event.KeyAdapter#keyReleased
         */
        public final void keyReleased(KeyEvent event) {

        	switch (event.getKeyCode()) {
        	case KeyEvent.VK_ENTER:		ap_100_find();							break;
        	case KeyEvent.VK_ESCAPE:	Search.this.doDefaultCloseAction();		break;
        	}
        }
    };


	/**
	 * Creates new Search screen
	 * @param src Where the search came from (parent frame)
	 * @param lst Layout List
	 */
	public Search(final AbstractFileDisplay src, @SuppressWarnings("rawtypes") FileView master) {
		super(master.getFileNameNoDirectory(), "Find",
				master);

		//this.getContentPane().setLayout(null);
		this.setTitle("Search Screen");

		BaseHelpPanel pnl = new BaseHelpPanel("Find");

		pnl.addReKeyListener(listner);

		layoutList = new LayoutCombo(master.getLayout(), false, true);
		pnl.setHelpURL(Common.formatHelpURL(Common.HELP_SEARCH));
		ignoreCase.setSelected(true);

		pnl.addLine("Search For", search);
		pnl.addLine("Replace With", replace);
		pnl.addLine("Record Layout", layoutList);
		pnl.addLine("Field", fieldList);
		pnl.addLine("Operator", fieldPart);
		pnl.addLine("Direction", direction);
		pnl.addLine("Ignore Case", ignoreCase);
		pnl.setGap(BasePanel.GAP1);

		//searchBtn = pnl.addIconButton(true, 2, Common.getRecordIcon(Common.ID_SEARCH_ICON));

		String s = LangConversion.convert(LangConversion.ST_BUTTON, "Find");

		if (USE_SPECIAL_NAME_FIND_BTN) {
			s = s + " >>";
		}
		searchBtn = new JButton(s, Common.getRecordIcon(Common.ID_SEARCH_ICON));


		if (master.isBrowse()) {
			pnl.addLine("", searchBtn);
		} else {
		    JPanel p = new JPanel();
			p.setLayout(new GridLayout(2, 2));
			p.add(searchBtn);
			p.add(replaceBtn);
			p.add(replaceFindBtn);
			p.add(replaceAllBtn);

			pnl.addLine("", p);
			pnl.setHeight(BasePanel.NORMAL_HEIGHT * 2);
			replaceBtn.addActionListener(this);
			replaceFindBtn.addActionListener(this);
			replaceAllBtn.addActionListener(this);
		}
		pnl.setGap(BasePanel.GAP1);
		pnl.addMessage(msgTxt);
		//pnl.addComponent("", replaceAllBtn);

		searchBtn.addActionListener(this);
		searchBtn.setToolTipText( LangConversion.convert(LangConversion.ST_FIELD_HINT,"Start Searching the file"));

		layoutList.addActionListener(this);

		source = src;

		this.addMainComponent(pnl);
		this.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
	}

//	public void setRecordLayout(AbstractLayoutDetails layout) {
//		layoutList.removeActionListener(this);
//		layoutList.setRecordLayout(layout);
//		loadFieldList();
//		layoutList.addActionListener(this);
//	}

	/**
	 * Displays the search screen
	 *
	 * @param iFile - Internal representation of a File
	 */
	public void startSearch(@SuppressWarnings("rawtypes") final FileView iFile) {
		file = iFile;

		loadFieldList();
		layoutList.setSelectedIndex(Math.min(source.getLayoutIndex(), layoutList.getItemCount() - 1));

		this.setVisible(true);
		this.toFront();
		if (firstTimeDisplayed) {
			this.setToMaximum(false);
			firstTimeDisplayed = false;
		}
	}

	/**
	 * Handles Button Press, Combo Change
	 *
	 * @see java.awt.event.ActionListner.actionPerformed
	 */
	public final void actionPerformed(final ActionEvent event) {

	    String searchFor = search.getText();
	    msgTxt.setText("");
	    try {
	        if (event.getSource() == layoutList) {
	            loadFieldList();
	        } else if (searchFor == null || "".equals(searchFor)) {
	            ap_920_setMessage(YOU_MUST_ENTER_TEXT_TO_SEARCH_FOR);
	        } else if (event.getSource() == searchBtn) {
	            ap_100_find();
	        } else if (file.isBrowse()) {
	            ap_920_setMessage(CAN_NOT_USE_REPLACE_IN_BROWSE_MODE);
	        } else if (fieldList.getSelectedIndex() == 0) {
	            ap_920_setMessage(YOU_MUST_SPECIFY_A_FIELD);
	        } else if (event.getSource() == replaceBtn) {
	            ap_300_replace();
	        } else if (event.getSource() == replaceFindBtn) {
	            ap_200_ReplaceFind();
	        } else if (event.getSource() == replaceAllBtn) {
	        	int func = fieldPart.getSelectedIndex();
	            ap_910_setPosition();
	            file.replaceAll(
	                    searchFor,
	                    replace.getText(),
	                    pos,
	                    ignoreCase.isSelected(),
	                    //(func == 0) || (func == 2),
	    				func);
	            file.fireTableDataChanged();
	        }
	    } catch (Exception e) {
	    	e.printStackTrace();
	        ap_920_setMessage(e.getMessage());
        }
	}


	/**
	 * Execute find (note ap is short for actionPerformed)
	 *
	 */
	private final void ap_100_find() {

	    String searchFor = search.getText();
	    int func = fieldPart.getSelectedIndex();
	    //boolean nextField = func < 2;
	    //System.out.print("--0 " + pos.row + " " + pos.col + "   ");
	    ap_910_setPosition();

//      System.out.print("--1 " + pos.row + " " + pos.currentFieldNumber + " " + pos.col);
		pos.adjustPosition(searchFor.length(), func);
//	    System.out.println(" --2 " + pos.row + " " + pos.currentFieldNumber + " " + pos.col);


	    file.find(
	            searchFor,
				pos,
				ignoreCase.isSelected(),
	//			(func == 0) || (func == 2),
				func);
    	source.setCurrRow(pos);
    	if (pos.currentLine == null && pos.row >= 0) {
 	        msgTxt.setText(LINE_MSG
				    	+ (pos.row+1)
				    	+ ", " + pos.currentFieldNumber
				    	+ ", " + pos.col);
	    }

    	if (super.getActiveFrame() != this) {
    		setActiveFrame(this);

    	}
		//System.out.println("--3 " + pos.row + " " + pos.col);
	}


	/**
	 * Execute replace (note ap is short for actionPerformed)
	 *
	 * @throws RecordException any error that occurs
	 */
	private final void ap_200_ReplaceFind() throws RecordException {

		int func = fieldPart.getSelectedIndex();
	    ap_300_replace();
	    file.find(
	            search.getText(),
				pos,
				ignoreCase.isSelected(),
//				(func == 0) || (func == 2),
				func);
	    if (pos.row >= 0) {
	        source.setCurrRow(pos);
	    }
	}


	/**
	 * Execute replace (note ap is short for actionPerformed)
	 *
	 * @throws RecordException any error that occurs
	 */
	private final void ap_300_replace() throws RecordException {

	    ap_910_setPosition();
		int func = fieldPart.getSelectedIndex();

	    file.replace(
	            search.getText(),
	            replace.getText(),
				pos,
				ignoreCase.isSelected(),
//				(func == 0) || (func == 2),
				func );
	    if (pos.row >= 0) {
	        source.setCurrRow(pos);
	        file.fireTableRowsUpdated(pos.row, pos.row);
	        //System.out.println("-- " + pos.row + " " + pos.col);
	    }
	}

	/**
	 * Setup the position
	 *
	 */
	private final void ap_910_setPosition() {
	    boolean forward = direction.getSelectedIndex() == 0;
	    int row;
	    int fieldId = fieldList.getSelectedIndex() - 2;
	    if (fieldId == -2 && this.file.getLayout().isXml()) {
	    	fieldId = -1;
	    }

	    row = source.getCurrRow();

	    source.stopCellEditing();

	    pos.currentLine = source.getTreeLine();
	    //System.out.print("==>> " + row + " " + pos.row + " " + pos.col  + " " + pos.currentFieldNumber);
	    if (pos.row >= 0 && (row < 0 || pos.row == row)) {
	        pos.setForward(forward);
		    pos.setFieldId(fieldId);
		    pos.recordId = layoutList.getSelectedIndex();
		    //System.out.println(" >> " + pos.row + " " + pos.currentFieldNumber);
	    } else if (pos.row == row) {
	        pos.setAll(Math.max(0, row), Math.max(0, pos.col),
	                layoutList.getSelectedIndex(),
	                fieldId,
	                forward);
	    } else {
	        System.out.println();
	        System.out.println("==== " + "    00".indexOf("00", 1));
	        pos.setAll(Math.max(0, row), 0,
	                layoutList.getSelectedIndex(),
	                fieldId,
	                forward);
	    }
	}


	/**
	 * Setup any error message
	 *
	 * @param message error message to log
	 */
	private final void ap_920_setMessage(String message) {

        msgTxt.setText(message);
        Common.logMsgRaw(FIND_ERROR + " " + message, null);
	}


	/**
	 * Reload field list after the user changes the record layout
	 */
	private void loadFieldList() {

		if (file != null) {
			int i, fieldListIdx = 0;;
			int idx = layoutList.getSelectedIndex();
			int numCols = file.getLayoutColumnCount(idx);

			//System.out.println("search layout " + idx);

			fieldList.removeAllItems();
			fieldList.addItem("");
			fieldList.addItem(ALL_FIELDS);
			if (idx >= 0) {
				for (i = 0; i < numCols; i++) {
					fieldList.addItem(file.getColumnName(idx, i));
				}
			}
			if (Common.OPTIONS.searchAllFields.isSelected()) {
				fieldListIdx = 1;
			}
			fieldList.setSelectedIndex(fieldListIdx);
		}
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.util.ILayoutChanged#layoutChanged(net.sf.JRecord.Details.AbstractLayoutDetails)
	 */
	@Override
	public void layoutChanged(AbstractLayoutDetails layout) {
		layoutList.removeActionListener(this);
		layoutList.setRecordLayout(layout);
		loadFieldList();
		layoutList.addActionListener(this);
	}


}
