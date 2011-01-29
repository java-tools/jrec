/*
 * Created on 31 Jan 2007, 08:05  for version 0.60
 */

package net.sf.RecordEditor.utils.openFile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.RecordEditor.utils.CopyBookInterface;
import net.sf.RecordEditor.utils.LayoutItem;
import net.sf.RecordEditor.utils.SystemItem;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.BasePanel;


/**
 * This class displays Record Layout details and allows the user
 * to make a selection
 *
 * @author  bymartin
 * @version 0.60
 */
public class LayoutSelectionDB extends AbstractLayoutSelection implements ActionListener {



	private JComboBox   dbCombo     = new JComboBox();
	private JComboBox   systemCombo = new JComboBox();
	private JComboBox   layoutCombo = new JComboBox();
	private JTextArea   description = new JTextArea();
	private JTextArea   message = null;

	private JButton reload = new JButton(
			"Reload from DB", 
			Common.getRecordIcon(Common.ID_RELOAD_ICON));

	private ArrayList<SystemItem> systems = new ArrayList<SystemItem>();
	private ArrayList<LayoutItem> layouts = new ArrayList<LayoutItem>();

	private int[] layoutId = null;


	private boolean dbLink = true;

	private CopyBookInterface copyBookInterface;
	
	private String lastLayoutName = "";
	private AbstractLayoutDetails lastLayout = null;


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
		dbCombo.setSelectedIndex(con);

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
	 * @see net.sf.RecordEditor.utils.layoutSelection.AbstractLayoutSelection#addLayoutSelection(net.sf.RecordEditor.utils.swing.BasePanel, javax.swing.JPanel, javax.swing.JButton, javax.swing.JButton)
	 */
	@Override
	public void addLayoutSelection(BasePanel pnl, JTextField file, JPanel goPanel, final JButton layoutCreate1,
     	   final JButton layoutCreate2) {

		JButton tmpBtn = new JButton("XX");
		pnl.addLine("Data Base", dbCombo, reload);
		pnl.addLine("System", systemCombo,tmpBtn);
		pnl.addLine("Record Layout", layoutCombo, layoutCreate1);
		if (layoutCreate2 != null) {
		    pnl.addLine("", null, layoutCreate2);
		}
		pnl.setGap(BasePanel.GAP1);
		pnl.addLine("Description", description, goPanel);
		
		double size = 0;
		if (goPanel != null) {
			try {
				size = goPanel.getPreferredSize().getHeight();
			} catch (Exception e) {
			}
		}
		pnl.setHeight(Math.max(BasePanel.NORMAL_HEIGHT * 3 + 3, size));
		
		tmpBtn.setVisible(false);
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

		systemCombo.addItem("<All>");

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

	/**
	 * Get Layout details
	 * @param layoutName record layout name
	 * @return record layout
	 */
	public final AbstractLayoutDetails getRecordLayout(String layoutName, String fileName) {
		AbstractLayoutDetails ret = null;

		if (layoutName == null || layoutName.equals("")) {
			Common.logMsg("No Layout " + layoutName, null);
		} else if (loadFromFile && lastLayoutName.equals(layoutName)){
			ret = lastLayout;
		} else {
		//	try {
			lastLayout = copyBookInterface.getLayout(layoutName);
		//	} catch (Exception e) {
		//		e.printStackTrace();
		//	}
			if (lastLayout == null) {
				message.setText(
					"Record Layout \"" + layoutName + "\" can not be loaded: \n "
					+ copyBookInterface.getMessage());
			} else {
				lastLayoutName = layoutName;
				lastLayout = getFileBasedLayout(fileName, lastLayout);
			}
			ret = lastLayout;
		}
		return ret;
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
	 * @see net.sf.RecordEditor.utils.openFile.AbstractLayoutSelection#getLayoutName()
	 */
	public String getLayoutName() {
		Object o = layoutCombo.getSelectedItem();
		if (o == null) {
			return null;
		}
			
		return layoutCombo.getSelectedItem().toString();
	}

    /**
	 * @see net.sf.RecordEditor.utils.openFile.AbstractLayoutSelection#setLayoutName(java.lang.String)
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
	 * @see net.sf.RecordEditor.utils.openFile.AbstractLayoutSelection#getDataBaseNames()
	 */
	@Override
	public String[] getDataBaseNames() {
		return Common.getSourceId();
	}


	/**
	 * @see net.sf.RecordEditor.utils.openFile.AbstractLayoutSelection#setDatabaseIdx(int)
	 */
	@Override
	public void setDatabaseIdx(int idx) {
		Common.setConnectionId(idx);
	}

	/**
	 * @see net.sf.RecordEditor.utils.openFile.AbstractLayoutSelection#getDatabaseIdx()
	 */
	@Override
	public int getDatabaseIdx() {
		return dbCombo.getSelectedIndex();
	}

	/**
	 * @see net.sf.RecordEditor.utils.openFile.AbstractLayoutSelection#getDatabaseName()
	 */
	@Override
	public String getDatabaseName() {
		return dbCombo.getSelectedItem().toString();
	}
}