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
package net.sf.RecordEditor.copy;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;

import net.sf.RecordEditor.re.openFile.AbstractLayoutSelectCreator;
import net.sf.RecordEditor.re.openFile.AbstractLayoutSelection;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;



/**
 * This class display the Copy Menu
 * <p><p>
 *
 * @author Bruce Martin
 * @version 0.67
 */
@SuppressWarnings("serial")
public class Menu extends ReFrame
               implements ActionListener {

    private static final int MENU_WIDTH = SwingUtils.STANDARD_FONT_WIDTH * 57;
    private static final int HELP_GAP   = SwingUtils.STANDARD_FONT_HEIGHT + 2;

	private JComboBox  dbCombo   = new JComboBox();

	private JButton fromFileBtn  = new JButton("*");
//	private JButton singleLayout = new JButton("*");
	private JButton copyBtn   = new JButton("*");
	private JButton cobolCopyBtn = new JButton("*");
	private JButton copyToDelimBtn = new JButton("*");
	private JButton copyToVelocityBtn = new JButton("*");
	private JButton copyToXmlBtn = new JButton("*");

	private JButton helpBtn      = SwingUtils.getHelpButton();

	private BaseHelpPanel pnl    = new BaseHelpPanel();

	private String rFiles;

	//private AbstractLayoutSelection selection;
	@SuppressWarnings("rawtypes")
	private AbstractLayoutSelectCreator layoutCreator;




	/**
	 * display a menu of actions to the user
	 *
	 * @param parentFrame parent frame
	 */
	public Menu(@SuppressWarnings("rawtypes") AbstractLayoutSelectCreator creator, String recentFiles)  {
		super("", "Menu", "Copy Menu", null);
		String[] dbs;

		layoutCreator = creator;
		rFiles = recentFiles;
		AbstractLayoutSelection selection = layoutCreator.create();
//		this.parent = parentFrame;



		pnl.setVerticalGap(BasePanel.VG_GAP2, HELP_GAP);

		pnl.setHelpURLre(Common.formatHelpURL(Common.HELP_DIFF));

		dbs = selection.getDataBaseNames();

		pnl.setGapRE(BasePanel.GAP1);
		if (dbs != null) {
			for (int i = 0; (i < dbs.length) && (dbs[i] != null) && (!dbs[i].equals("")); i++) {
				dbCombo.addItem(dbs[i]);
			}
			dbCombo.setSelectedIndex(Common.getConnectionIndex());
			pnl.addLineRE("Data Base", dbCombo, helpBtn);
		}

		pnl.setGapRE(BasePanel.GAP3);

		pnl.addMenuItemRE("Run Stored Copy", fromFileBtn);
		pnl.setGapRE(BasePanel.GAP1);

		pnl.addMenuItemRE("Standard Copy", copyBtn);
		pnl.setGapRE(BasePanel.GAP1);

		pnl.addMenuItemRE("Cobol Copy", cobolCopyBtn);
		pnl.setGapRE(BasePanel.GAP1);

		pnl.addMenuItemRE("Copy To Delimited File (Csv)", copyToDelimBtn);
		pnl.setGapRE(BasePanel.GAP1);

		pnl.addMenuItemRE("Copy To Xml", copyToXmlBtn);

		if (Common.isVelocityAvailable()) {
			pnl.setGapRE(BasePanel.GAP1);

			pnl.addMenuItemRE("Copy using Velocity Template", copyToVelocityBtn);
		}
		pnl.setGapRE(BasePanel.GAP3);


		helpBtn.addActionListener(this);
		fromFileBtn.addActionListener(this);
		copyBtn.addActionListener(this);
		cobolCopyBtn.addActionListener(this);
		copyToDelimBtn.addActionListener(this);
		copyToVelocityBtn.addActionListener(this);
		copyToXmlBtn.addActionListener(this);

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		dbCombo.requestFocusInWindow();

		//pnl.done();

		this.getContentPane().add(pnl);

		pack();

		setBounds(getY(), getX(), MENU_WIDTH, getHeight());

		show();

		dbCombo.addActionListener(this);
	}


	/**
	 * @see java.awt.event.ActionListner#actionPerformed
	 */
	public void actionPerformed(ActionEvent e) {

//		String lDBid = dbCombo.getSelectedItem().toString();
		if (e.getSource() == fromFileBtn) {
			new RunSavedCopy(layoutCreator, dbCombo.getSelectedIndex(), rFiles);
		} else if (e.getSource() == helpBtn) {
		    pnl.showHelpRE();
		} else if (e.getSource() == cobolCopyBtn) {
			new CobolCopy();
		} else if (e.getSource() == copyToDelimBtn) {
			AbstractLayoutSelection selection = layoutCreator.create();
			selection.setDatabaseIdx(dbCombo.getSelectedIndex());

			new Copy2Delim(selection);
		} else if (e.getSource() == copyToVelocityBtn) {
			AbstractLayoutSelection selection = layoutCreator.create();
			selection.setDatabaseIdx(dbCombo.getSelectedIndex());

			new Copy2Velocity(selection);
		} else if (e.getSource() == copyBtn) {
			AbstractLayoutSelection selection = layoutCreator.create();
			AbstractLayoutSelection selection1 = layoutCreator.create();

			selection.setDatabaseIdx(dbCombo.getSelectedIndex());
			selection1.setDatabaseIdx(dbCombo.getSelectedIndex());

			new CopyTwoLayouts(selection, selection1, rFiles);
		} else if (e.getSource() == copyToXmlBtn) {
			AbstractLayoutSelection selection = layoutCreator.create();
			selection.setDatabaseIdx(dbCombo.getSelectedIndex());

			new Copy2Xml(selection);
		} else if (e.getSource() == copyBtn) {
		} else if (e.getSource() == dbCombo) {
		    Common.setConnectionId(dbCombo.getSelectedIndex());
 		}
		//this.moveToBack();
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


}
