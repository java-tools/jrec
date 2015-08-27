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

import net.sf.RecordEditor.re.openFile.LayoutSelectionCsvCreator;
import net.sf.RecordEditor.re.openFile.LayoutSelectionPoTipCreator;
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
public class MenuCsv extends ReFrame
               implements ActionListener {

    private static final int MENU_WIDTH = SwingUtils.STANDARD_FONT_WIDTH * 57;
    private static final int HELP_GAP   = 15;


	private JButton csvCmp       = new JButton("*");
	private JButton poCmpBtn     = new JButton("*");
	private JButton tipCmpBtn    = new JButton("*");

//	private JButton btnHelp      = SwingUtils.getHelpButton();

	private BaseHelpPanel pnl    = new BaseHelpPanel();

	private String rFiles;





	/**
	 * display a menu of actions to the user
	 *
	 * @param parentFrame parent frame
	 */
	public MenuCsv(String recentFiles)  {
		super("", "Menu", "Compare Menu", null);

//		layoutCreator = creator;
		rFiles = recentFiles;


		pnl.setVerticalGap(BasePanel.VG_GAP2, HELP_GAP);

		pnl.setHelpURLre(Common.formatHelpURL(Common.HELP_DIFF));

		pnl.setGapRE(BasePanel.GAP2);

		pnl.addMenuItemRE("Csv Compare", csvCmp);
		pnl.setGapRE(BasePanel.GAP1);
		pnl.addMenuItemRE("GetText-Po Compare", poCmpBtn);
		pnl.setGapRE(BasePanel.GAP1);
		pnl.addMenuItemRE("SwingX-Tip Compare", tipCmpBtn);
		
		pnl.setGapRE(BasePanel.GAP3);


//		btnHelp.addActionListener(this);
		poCmpBtn.addActionListener(this);
		tipCmpBtn.addActionListener(this);
		csvCmp.addActionListener(this);

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);


		this.getContentPane().add(pnl);

		pack();

		setBounds(getY(), getX(), MENU_WIDTH, getHeight());

		show();
	}


	/**
	 * @see java.awt.event.ActionListner#actionPerformed
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == csvCmp) {
			LayoutSelectionCsvCreator csvl = new LayoutSelectionCsvCreator();
			new CompareTwoLayouts(csvl.create(), csvl.create(), rFiles, false);
		} else if (e.getSource() == poCmpBtn) {
			LayoutSelectionPoTipCreator newPoCreator = LayoutSelectionPoTipCreator.newPoCreator();
			new CompareSingleLayout("GetText-PO Compare Wizard -", newPoCreator.create(), rFiles);
		} else if (e.getSource() == tipCmpBtn) {
			LayoutSelectionPoTipCreator newTipCreator = LayoutSelectionPoTipCreator.newTipCreator();
			new CompareSingleLayout("SwingX-Tip Compare Wizard -", newTipCreator.create(), rFiles);
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
