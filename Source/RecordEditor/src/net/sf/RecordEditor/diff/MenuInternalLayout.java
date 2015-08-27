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
package net.sf.RecordEditor.diff;


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
 * This class display the Compare Menu
 * <p><p>
 *
 * @author Bruce Martin
 * @version 0.65
 */
@SuppressWarnings("serial")
public class MenuInternalLayout extends ReFrame
               implements ActionListener {

    private static final int MENU_WIDTH = SwingUtils.STANDARD_FONT_WIDTH * 57;
    private static final int HELP_GAP   = SwingUtils.STANDARD_FONT_HEIGHT + 2;

	private JComboBox  dbCombo  = new JComboBox();

	private JButton fromFileBtn = new JButton("*");
	private JButton compare 	= new JButton("*");

	private JButton btnHelp     = SwingUtils.getHelpButton();

	private BaseHelpPanel pnl   = new BaseHelpPanel();

	private String rFiles;

	//private AbstractLayoutSelection selection;
	@SuppressWarnings("unchecked")
	private AbstractLayoutSelectCreator layoutCreator;




	/**
	 * display a menu of actions to the user
	 *
	 * @param parentFrame parent frame
	 */
	@SuppressWarnings("unchecked")
	public MenuInternalLayout(AbstractLayoutSelectCreator creator, String recentFiles)  {
		super("", "Menu", "Compare Menu", null);
		String[] dbs;

		layoutCreator = creator;
		rFiles = recentFiles;
		AbstractLayoutSelection selection = layoutCreator.create();


		pnl.setVerticalGap(BasePanel.VG_GAP2, HELP_GAP);

		pnl.setHelpURLre(Common.formatHelpURL(Common.HELP_DIFF));

		dbs = selection.getDataBaseNames();

		pnl.setGapRE(BasePanel.GAP1);
		if (dbs != null) {
			for (int i = 0; (i < dbs.length) && (dbs[i] != null) && (!dbs[i].equals("")); i++) {
				dbCombo.addItem(dbs[i]);
			}
			dbCombo.setSelectedIndex(Common.getConnectionIndex());
			pnl.addLineRE("Data Base", dbCombo, btnHelp);
		}

		pnl.setGapRE(BasePanel.GAP3);

		pnl.addMenuItemRE("Run Stored Compare", fromFileBtn);
		pnl.setGapRE(BasePanel.GAP1);
		pnl.addMenuItemRE("Compare 2 Files", compare);

		pnl.setGapRE(BasePanel.GAP3);


		btnHelp.addActionListener(this);
		fromFileBtn.addActionListener(this);
		compare.addActionListener(this);

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
	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {

//		String lDBid = dbCombo.getSelectedItem().toString();
		if (e.getSource() == fromFileBtn) {
			new RunSavedCompare(layoutCreator, dbCombo.getSelectedIndex(), rFiles);
		} else if (e.getSource() == btnHelp) {
		    pnl.showHelpRE();
		} else if (e.getSource() == dbCombo) {
		    Common.setConnectionId(dbCombo.getSelectedIndex());
		} else {
			AbstractLayoutSelection selection1 = layoutCreator.create();
			AbstractLayoutSelection selection2 = layoutCreator.create();

			selection1.setDatabaseIdx(dbCombo.getSelectedIndex());
			selection2.setDatabaseIdx(dbCombo.getSelectedIndex());

			if (e.getSource() == compare) {
				new CompareInternalLayout(selection1, selection2, rFiles);
			}
 		}
	}


    /**
     * @see net.sf.RecordEditor.utils.common.ReActionHandler#executeAction(int)
     */
    public void executeAction(int action) {

        if (action == ReActionHandler.HELP) {
            pnl.showHelpRE();
        }
    }


    /**
     * @see net.sf.RecordEditor.utils.common.ReActionHandler#isActionAvailable(int)
     */
    public boolean isActionAvailable(int action) {

        return action == ReActionHandler.HELP;
    }


}
