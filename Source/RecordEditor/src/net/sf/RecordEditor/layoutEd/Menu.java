/*
 * Created on 5/09/2004
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *   - changed to use ReFrame (from JInternalFrame) which keeps track
 *     of the active form
 *   - Added new Create Layout and Create Layout Wizard
 *
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - JRecord Creation
 *   - Code cleanup
 */
package net.sf.RecordEditor.layoutEd;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import net.sf.JRecord.External.CobolCopybookLoader;
import net.sf.RecordEditor.layoutEd.load.LoadCobolCopybook;
import net.sf.RecordEditor.layoutEd.load.LoadCopyBook;
import net.sf.RecordEditor.layoutEd.panels.RecordEdit1Record;
import net.sf.RecordEditor.layoutWizard.Wizard;
import net.sf.RecordEditor.utils.LayoutConnection;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;



/**
 * This class display the Menu, options are
 * <ul compact>
 * <li>Edit Record DB
 * <li>Create Record Layout
 * <li>Create Record Layout via wizard
 * <li>Edit Tables DB
 * <li>Import Cobol Copybook
 * <li>Import XML (CB2XML) Copybook
 * <li>Upgrade DB
 * </ul>
 * <p><p>
 *
 * @author Bruce Martin
 * @version 0.51
 */
@SuppressWarnings("serial")
public class Menu extends ReFrame
               implements ActionListener, LayoutConnection {

    private static final int MENU_WIDTH = SwingUtils.STANDARD_FONT_WIDTH * 50;
    private static final int HELP_GAP   = 15;

	private JComboBox  dbCombo    = new JComboBox();

	private JButton editRecLayout = new JButton("*");
	private JButton createRecLayout = new JButton("*");
	private JButton createWizard = new JButton("*");
	private JButton tables = new JButton("*");
	private JButton comboEdit = new JButton("*");
	private JButton comboCreate = new JButton("*");
	private JButton xmlCopyBook = new JButton("*");
	private JButton cobolCopyBook = new JButton("*");
	private JButton copyLayouts = new JButton("*");
	private JButton upgrade = new JButton("*");

	private JButton btnHelp    = SwingUtils.getHelpButton();


	private BaseHelpPanel pnl = new BaseHelpPanel();
	private JFrame parent;


	/**
	 * display a menu of actions to the user
	 *
	 * @param parentFrame parent frame
	 */
	public Menu(final JFrame parentFrame)  {
		this(parentFrame, null);
	}
	

	/**
	 * display a menu of actions to the user
	 *
	 * @param parentFrame parent frame
	 */
	public Menu(final JFrame parentFrame, String dbName)  {

		super("Menu");

		this.parent = parentFrame;

		loadDBs();
		dbCombo.setSelectedIndex(Common.getConnectionIndex());
		if (dbName != null && dbName.length() > 0) {
			for (int i = 0; i < dbCombo.getItemCount(); i++) {
				if (dbName.equalsIgnoreCase(dbCombo.getItemAt(i).toString())) {
					dbCombo.setSelectedIndex(i);
					break;
				}
			}
		}

		pnl.setVerticalGap(BasePanel.VG_GAP2, HELP_GAP);

		pnl.setHelpURLre(Common.formatHelpURL(Common.HELP_MENU));
		pnl.addLineRE("Data Base", dbCombo, btnHelp);
		pnl.setGapRE(BasePanel.GAP2);

		pnl.addMenuItemRE("Edit Record Layout", editRecLayout);
		pnl.addMenuItemRE("Create Record Layout", createRecLayout);
		pnl.addMenuItemRE("Create Record Layout Wizard", createWizard);
		pnl.setGapRE(BasePanel.GAP1);

		pnl.addMenuItemRE("Edit Tables", tables);

		pnl.setGapRE(BasePanel.GAP1);
		pnl.addMenuItemRE("Edit Combos", comboEdit);
		pnl.addMenuItemRE("Create Combo", comboCreate);

		pnl.setGapRE(BasePanel.GAP1);
		if (CobolCopybookLoader.isAvailable()) {
		    pnl.addMenuItemRE("Load Cobol Copybook", cobolCopyBook);
		    cobolCopyBook.addActionListener(this);
		}
		pnl.addMenuItemRE("Load copybook (choose format)", xmlCopyBook);
		pnl.addMenuItemRE("Copy copybooks", copyLayouts);

		pnl.setGapRE(BasePanel.GAP2);
		pnl.addMenuItemRE("Upgrade Database Tables", upgrade);
		pnl.setGapRE(BasePanel.GAP2);

		btnHelp.addActionListener(this);
		editRecLayout.addActionListener(this);
		createRecLayout.addActionListener(this);
		createWizard.addActionListener(this);
		tables.addActionListener(this);
		comboEdit.addActionListener(this);
		comboCreate.addActionListener(this);
		xmlCopyBook.addActionListener(this);
		upgrade.addActionListener(this);
		copyLayouts.addActionListener(this);

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		dbCombo.requestFocusInWindow();

		//pnl.done();

		this.getContentPane().add(pnl);

		pack();

		setBounds(getY(), getX(), MENU_WIDTH, getHeight());

		show();

	}


	/**
	 * @see java.awt.event.ActionListner#actionPerformed
	 */
	public void actionPerformed(ActionEvent e) {

		String lDBid = dbCombo.getSelectedItem().toString();
		Object source = e.getSource();
		if (source == editRecLayout) {
			new RecordEdit(lDBid, dbCombo.getSelectedIndex());
		} else if (source == createRecLayout) {
			new RecordEdit1Record(lDBid,  dbCombo.getSelectedIndex(), null, null);
		} else if (source == createWizard) {
            new Wizard(dbCombo.getSelectedIndex(), "", null);
		} else if (source == btnHelp) {
		    pnl.showHelpRE();
		} else if (source == tables) {
			new TblEdit(lDBid, parent, dbCombo.getSelectedIndex());
		} else if (source == comboEdit) {
			new ComboEdit(lDBid, dbCombo.getSelectedIndex());
		} else if (source == comboCreate) {
			new ComboCreate(lDBid, dbCombo.getSelectedIndex());
		} else if (source == copyLayouts) {
			new LayoutCopy();
		} else if (source == upgrade) {
		    new UpgradeDBs(lDBid, dbCombo.getSelectedIndex());
		} else if (source == cobolCopyBook) {
			new LoadCobolCopybook(lDBid, dbCombo.getSelectedIndex(), null);
		} else {

		        new LoadCopyBook(
		            				true, //e.getSource() == xmlCopyBook,
		            						/* choosing between Cobol and other Copybooks */
		            				lDBid, dbCombo.getSelectedIndex(),
		            				null);
		}
	}


	/**
	 * load all the available Databases
	 *
	 */
	private void loadDBs() {
		int i;
		String[] dbs = Common.getSourceId();

		for (i = 0; (i < dbs.length) && (dbs[i] != null) && (!dbs[i].equals("")); i++) {
			dbCombo.addItem(dbs[i]);
		}
	}


    /**
     * @see net.sf.RecordEditor.utils.common.ReActionHandler#executeAction(int)
     */
    public void executeAction(int action) {

        if (action == ReActionHandler.HELP) {
            pnl.showHelpRE();
        }
        //super.executeAction(action);
    }


    /**
     * @see net.sf.RecordEditor.utils.common.ReActionHandler#isActionAvailable(int)
     */
    public boolean isActionAvailable(int action) {

        return action == ReActionHandler.HELP;
    }


    /**
     * @see net.sf.RecordEditor.utils.LayoutConnection#getCurrentDbIdentifier()
     */
    public int getCurrentDbIdentifier() {
        return dbCombo.getSelectedIndex();
    }

    /**
     * @see net.sf.RecordEditor.utils.LayoutConnection#getCurrentDbName()
     */
    public String getCurrentDbName() {
        return dbCombo.getSelectedItem().toString();
    }


    /**
     * @see net.sf.RecordEditor.utils.LayoutConnection#getCurrentFileName()
     */
    public String getCurrentFileName() {
        return null;
    }


    /**
     * @see net.sf.RecordEditor.utils.LayoutConnection#setRecordLayout(int)
     */
    public void setRecordLayout(int recordId, String layoutName, String file) {

    }
}
