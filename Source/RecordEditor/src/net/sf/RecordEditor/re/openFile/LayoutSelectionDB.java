/*
 * Created on 31 Jan 2007, 08:05  for version 0.60
 */

package net.sf.RecordEditor.re.openFile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import net.sf.JRecord.Common.CommonBits;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.RecordEditor.layoutWizard.FileAnalyser;
import net.sf.RecordEditor.utils.CopyBookInterface;
import net.sf.RecordEditor.utils.LayoutItem;
import net.sf.RecordEditor.utils.SystemItem;
import net.sf.RecordEditor.utils.charsets.FontCombo;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.StreamUtil;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.msg.UtMessages;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboFileSelect;


/**
 * This class displays Record Layout details and allows the user
 * to make a selection
 *
 * @author  bymartin
 * @version 0.60
 */
public class LayoutSelectionDB extends AbstractLayoutSelection implements ActionListener {



	private static final String ALL_SYSTEMS = LangConversion.convertComboItms("Layout Selection", "<All>");
	private final JComboBox   dbCombo     = new JComboBox();
	private final JComboBox   systemCombo = new JComboBox();
	private final JComboBox   layoutCombo = new JComboBox();
	private final JTextArea   description = new JTextArea();
	
	private final JLabel      fontLbl     = new JLabel("Encoding");
	private final FontCombo   fontCombo   = new FontCombo();
	private       JTextArea   message;

	private final JButton     reload = SwingUtils.newButton(
			"Reload from DB",
			Common.getRecordIcon(Common.ID_RELOAD_ICON));

	
	private TreeComboFileSelect filenameCombo;
	private ArrayList<SystemItem> systems = new ArrayList<SystemItem>();
	private ArrayList<LayoutItem> layouts = new ArrayList<LayoutItem>();

	private int[] layoutId = null;


	private boolean dbLink = true;

	private CopyBookInterface copyBookInterface;

	private String lastLayoutName = "";
	private AbstractLayoutDetails lastLayout = null;
	
	private boolean fontRequired = false;
	private String lastFileName, lastFontName;

	public LayoutSelectionDB(ZTstLayoutSelectionDbFields tstFlds, 
			final CopyBookInterface pInterfaceToCopyBooks, boolean doPrimingRead) {
		this(pInterfaceToCopyBooks, doPrimingRead);
		
		tstFlds.dbCombo = dbCombo;
		tstFlds.fontCombo = fontCombo;
		tstFlds.fontLbl = fontLbl;
		tstFlds.layoutCombo = layoutCombo;
		tstFlds.reload = reload;
	}

	/**
	 * Creating the File & record selection screen
	 *
	 * @param pInterfaceToCopyBooks interface to copybooks
	 * @param pMessage message field
	 */
	public LayoutSelectionDB(final CopyBookInterface pInterfaceToCopyBooks, boolean doPrimingRead) {
		super();
		//int ii = Common.getConnectionIndex();
		boolean free = Common.isSetDoFree(false);
		int con = Common.getConnectionIndex();

		copyBookInterface = pInterfaceToCopyBooks;


		loadDBs();
		if (con < dbCombo.getItemCount()) {
			dbCombo.setSelectedIndex(con);
		}

		loadSystems();
		copyBookInterface.loadLayouts(layouts);
		loadLayoutCombo();

		reload.addActionListener(this);

		dbCombo.addActionListener(this);
		systemCombo.addActionListener(this);
		layoutCombo.addActionListener(this);
		Common.setDoFree(free, con);
	}

	public LayoutSelectionDB(final CopyBookInterface pInterfaceToCopyBooks, JTextArea messageFld,
			boolean doPrimingRead) {
		this(pInterfaceToCopyBooks, doPrimingRead);

		message = messageFld;
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.openFile.AbstractLayoutSelection#addLayoutSelection(net.sf.RecordEditor.utils.swing.BasePanel, net.sf.RecordEditor.utils.swing.treeCombo.TreeComboFileSelect, javax.swing.JPanel, javax.swing.JButton, javax.swing.JButton)
	 */
	@Override
	public void addLayoutSelection(BasePanel pnl, TreeComboFileSelect file,
			JPanel goPanel, JButton layoutCreate1, JButton layoutCreate2) {
		
		filenameCombo = file;

		JButton tmpBtn = new JButton("XX");
		pnl.addLineRE(    "Data Base", dbCombo, reload);
		pnl.addLineRE(       "System", systemCombo,tmpBtn);
		pnl.addLineRE("Record Layout", layoutCombo, layoutCreate1);
		if (layoutCreate2 != null) {
		    pnl.addLineRE(         "", null, layoutCreate2);
		}
		pnl.addLineRE(        fontLbl, fontCombo);
		pnl.setGapRE(BasePanel.GAP0);
		pnl.addLineRE(  "Description", description, goPanel);

		double size = 0;
		if (goPanel != null) {
			try {
				size = goPanel.getPreferredSize().getHeight();
			} catch (Exception e) {
			}
		}
		pnl.setHeightRE(Math.max(BasePanel.NORMAL_HEIGHT * 3 + 3, size));

//		fontLbl.setVisible(false);
//		fontCombo.setVisible(false);
		tmpBtn.setVisible(false);
		
		layoutCombo.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				changeOfLayout();
			}
		});
		changeOfLayout();
	}
	
	private void changeOfLayout() {
		Object selectedItem = layoutCombo.getSelectedItem();
		String selected;
		boolean visible = false;

		if (selectedItem != null
		&&  (selected = selectedItem.toString()).length() > 0) {
			LayoutDetail layout = copyBookInterface.getLayout(selected);
			
			if (layout != null) {
				visible = CommonBits.isFontRequired(layout.getFileStructure());
				
				if (visible) {	
					String fontName = layout.getFontName();
					File file;
					String filename;
					
					if (filenameCombo != null
					&& (file = new File((filename = filenameCombo.getText()))).exists()) {
						if (filename.equals(lastFileName)) {
	//						fontName = lastFontName;
						} else {
							try {
								byte[] fileBytes = StreamUtil.read(new FileInputStream(file), 0x40000);
								FileAnalyser fa = FileAnalyser.getAnaylserNoLengthCheck(fileBytes, "40");
	//							fontName = fa.getFontName();
								fontCombo.setFontList(fa.getCharsetDetails().likelyCharsets);
								
								lastFileName = filename;
								lastFontName = fa.getFontName();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					fontCombo.setText(fontName);
				}
			}
		}
		fontLbl.setVisible(visible);
		fontCombo.setVisible(visible);
	}


	/**
	 * load the various DB options available
	 *
	 */
	public void loadDBs() {
		int i;
		String[] dbs = Common.getSourceId();

		for (i = 0; (i < dbs.length) && (dbs[i] != null) && (!dbs[i].equals("")); i++) {
			dbCombo.addItem(dbs[i]);
		}
	}



	/**
	 * Load the record Layout combo
	 */
	private void loadLayoutCombo() {
		int i, j;
		int size = layouts.size();
		int sysIdx = systemCombo.getSelectedIndex();

		if ((layoutId == null) || layoutId.length < size) {
			layoutId = new int[size];
		}

		layoutCombo.removeAllItems();
		if (sysIdx <= 0) {
			for (i = 0; i < size; i++) {
				LayoutItem layout  = layouts.get(i);
				layoutCombo.addItem(layout.getRecordName());
				layoutId[i] = i;
			}
		} else {
			int sys = (systems.get(sysIdx - 1)).systemId;

			j = 0;
			for (i = 0; i < size; i++) {
				LayoutItem layout  = layouts.get(i);
				if (layout.getSystem() == sys) {
					layoutCombo.addItem(layout.getRecordName());
					layoutId[j++] = i;
				}
			}
		}
		setDescription();
	}


	/**
	 * Set the Selection screen field fram the layout array
	 *
	 */
	private void setDescription() {
		try {
			int idx = layoutId[layoutCombo.getSelectedIndex()];
			description.setText((layouts.get(idx)).getDescription());
		} catch (Exception ex) {
			description.setText("");
		}
	}


	/**
	 * Load the various systems from the DB
	 *
	 */
	private void loadSystems() {
		int i, num;
		SystemItem dtls;

		systemCombo.removeAllItems();

		systemCombo.addItem(ALL_SYSTEMS);

		dbLink = true;
		try {
			systems = copyBookInterface.getSystems();
		} catch (Exception ex) {
			if (message != null) {
				message.setText(ex.getMessage());
				message.setCaretPosition(1);
			}
			ex.printStackTrace();
			systems = new ArrayList<SystemItem>();
			dbLink = false;
		}

		num = systems.size();
		for (i = 0; i < num; i++) {
			dtls = systems.get(i);
			systemCombo.addItem(dtls.description);
		}

	}


	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public final void actionPerformed(ActionEvent e) {

		if (e.getSource() == dbCombo) {
			Common.setConnectionId(dbCombo.getSelectedIndex());

			reload();
		} else if (e.getSource() == systemCombo) {
			loadLayoutCombo();
		} else if (e.getSource() == layoutCombo) {
			setDescription();
		} else if (e.getSource() == reload) {
			reload();
		}
	}



	/**
	 * Get Layout details
	 * @return record layout
	 */
	public final AbstractLayoutDetails getRecordLayout(String fileName) {
		return getRecordLayout((String) layoutCombo.getSelectedItem(), fileName);
	}


	@Override
	public boolean isOkToLoadFile(String dataFileName) {

		getRecordLayout((String) layoutCombo.getSelectedItem(), dataFileName);
		return ! fontRequired;
	}

	@Override
	public void forceLayoutReload() {
		lastLayoutName = "";
	}

	/**
	 * Get Layout details
	 * @param layoutName record layout name
	 * @return record layout
	 */
	public final AbstractLayoutDetails getRecordLayout(String layoutName, String fileName) {
		AbstractLayoutDetails ret = null;

		fontRequired = false;
		if (layoutName == null || layoutName.equals("")) {
			Common.logMsg("No Layout Entered", null);
		} else {
			String newFont = fontCombo.getText();
			if (loadFromFile && lastLayoutName.equals(layoutName)
				   &&  lastLayout != null
				   && (  (! fontCombo.isVisible())
					  ||  lastLayout.getFontName().equalsIgnoreCase(newFont) )) {
				ret = lastLayout;
			} else {
			//	try {
				lastLayout = copyBookInterface.getLayout(layoutName);
			//	} catch (Exception e) {
			//		e.printStackTrace();
			//	}
				if (lastLayout == null) {
					message.setText(
							UtMessages.LAYOUT_CANT_BE_LOADED.get(layoutName)
							+ "\n " + copyBookInterface.getMessage());
				} else {
					lastLayoutName = layoutName;
					lastLayout = getFileBasedLayout(fileName, lastLayout);
					
					fontRequired = CommonBits.isFontRequired(lastLayout.getFileStructure());
					if (fontRequired
					&&  lastLayout.getRecordCount() > 0
					&&  lastLayout instanceof LayoutDetail) {
						lastLayout = new LayoutDetail(
										layoutName, 
										getRecords((LayoutDetail)lastLayout, newFont),
										lastLayout.getDescription(), lastLayout.getLayoutType(),
										lastLayout.getRecordSep(), lastLayout.getEolString(), 
										newFont, 
										lastLayout.getDecider(), 
										CommonBits.translateFileStructureToNotAskFont(lastLayout.getFileStructure()),
										lastLayout.getMaximumRecordLength());
					}
				}
				ret = lastLayout;
			}
		}
		return ret;
	}
	
	private RecordDetail[] getRecords(LayoutDetail layout, String newFont) {
		RecordDetail[] newRecs = new RecordDetail[layout.getRecordCount()];
		for (int i = 0; i < newRecs.length; i++) {
			RecordDetail rec = layout.getRecord(i);
			RecordDetail.FieldDetails[] newFlds = new RecordDetail.FieldDetails[rec.getFieldCount()];
			for (int j = 0; j < newFlds.length; j++) {
				RecordDetail.FieldDetails field = rec.getField(j);
				newFlds[j] = new RecordDetail.FieldDetails(
						field.getName(), field.getDescription(), field.getType(), field.getDecimal(), 
						newFont, field.getFormat(), field.getParamater());
				
				if (field.isFixedFormat()) {
					newFlds[j].setPosLen(field.getPos(), field.getLen());
				} else {
					newFlds[j].setPosOnly(field.getPos());
				}
			}
			
			newRecs[i] = new RecordDetail(
						rec.getRecordName(), rec.getRecordType(), 
						rec.getDelimiterUneditted(), rec.getQuoteUneditted(), 
						newFont, newFlds,
						rec.getRecordStyle(), rec.getRecordSelection(),
						rec.getChildId(), rec.isEmbeddedNewLine());
		}
		
		return newRecs;
	}


	/**
	 * @see net.sf.RecordEditor.utils.layoutSelection.AbstractLayoutSelection#reload()
	 */
	public void reload() {

		lastLayoutName = "";
		lastLayout = null;

		loadSystems();

	    layouts.clear();
		if 	(dbLink) {
		    copyBookInterface.loadLayouts(layouts);
		}
	    loadLayoutCombo();
	}

    /**
     * @return Returns the dbCombo.
     */
    public JComboBox getDbCombo() {
        return dbCombo;
    }

    /**
     * @return Returns the layoutCombo.
     */
    public JComboBox getLayoutCombo() {
        return layoutCombo;
    }

    /**
     * @return Returns the systemCombo.
     */
    public JComboBox getSystemCombo() {
        return systemCombo;
    }

	/**
	 * @see net.sf.RecordEditor.re.openFile.AbstractLayoutSelection#getLayoutName()
	 */
	public String getLayoutName() {
		Object o = layoutCombo.getSelectedItem();
		if (o == null) {
			return null;
		}

		return layoutCombo.getSelectedItem().toString();
	}

    /**
	 * @see net.sf.RecordEditor.re.openFile.AbstractLayoutSelection#setLayoutName(java.lang.String)
	 */
    public boolean setLayoutName(String layoutName) {
        int size = layouts.size();
        LayoutItem layout;
        for (int i = 0; i < size; i++) {
			layout  = layouts.get(i);
			if (layout.getRecordName().equalsIgnoreCase(layoutName)) {
			    systemCombo.setSelectedIndex(0);
			    layoutCombo.setSelectedItem(layoutName);
			    return true;
			}
        }
        return false;
    }


	/**
	 * @param message the message to set
	 */
	public void setMessage(JTextArea message) {
		this.message = message;
	}


	/**
	 * @see net.sf.RecordEditor.re.openFile.AbstractLayoutSelection#getDataBaseNames()
	 */
	@Override
	public String[] getDataBaseNames() {
		return Common.getSourceId();
	}


	/**
	 * @see net.sf.RecordEditor.re.openFile.AbstractLayoutSelection#setDatabaseIdx(int)
	 */
	@Override
	public void setDatabaseIdx(int idx) {
		if (dbCombo != null) {
			try {
				dbCombo.setSelectedIndex(idx);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Common.setConnectionId(idx);
	}

	/**
	 * @see net.sf.RecordEditor.re.openFile.AbstractLayoutSelection#getDatabaseIdx()
	 */
	@Override
	public int getDatabaseIdx() {
		return dbCombo.getSelectedIndex();
	}

	/**
	 * @see net.sf.RecordEditor.re.openFile.AbstractLayoutSelection#getDatabaseName()
	 */
	@Override
	public String getDatabaseName() {
		return dbCombo.getSelectedItem().toString();
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.openFile.AbstractLayoutSelection#isFileBasedLayout()
	 */
	@Override
	public boolean isFileBasedLayout() {
		return false;
	}
}