/*
 * Created on 25/08/2004
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *   - changed to use ReMainFrame (from JIFrame)
 *     and ReActionHandler's
 *
 * # Version 0.60 Bruce Martin 2007/02/16
 *   - Added EditParameters to the Edit menu
 *   - support for the new Java run class (to replace bat files)
 *
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - Changes related to creation of seperate JRecord package
 *   - Support icon for EditOptions option
 */
package net.sf.RecordEditor.layoutEd;


import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

import net.sf.JRecord.External.CopybookLoaderFactory;
import net.sf.RecordEditor.layoutEd.panels.RecordEdit1Record;
import net.sf.RecordEditor.re.editProperties.EditOptions;
import net.sf.RecordEditor.re.script.VelocityPopup;
import net.sf.RecordEditor.re.util.CopybookLoaderFactoryDB;
import net.sf.RecordEditor.re.util.ReIOProvider;
import net.sf.RecordEditor.re.util.UpgradeDB;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.jdbc.AbsDB;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.lang.ReAbstractAction;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.screenManager.ReAction;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swingx.TipsManager;



/**
 * Main LayoutEdit class. This class lets the user
 * <ul compact>
 * <li>Edit Record Layouts
 * <li>Edit Tables DB
 * </ul>
 *
 * @author Bruce Martin
 * @version 0.51
 */
@SuppressWarnings("serial")
public class LayoutEdit extends ReMainFrame {

	private Menu menu;

	private final ReAction newAction = new ReAction(
			LangConversion.convert(LangConversion.ST_ACTION, "New"),
			LangConversion.convert(LangConversion.ST_ACTION, "New Layout"),
	        Common.getRecordIcon(Common.ID_NEW_ICON), ReActionHandler.NEW, this);

	/**
	 * @throws java.awt.HeadlessException
	 */
	public LayoutEdit() {
		super("Record Layout Definitions", "", "le");
		runInitClass(Common.PO_INIT_CLASS, null);

		ReIOProvider.register();

		AbstractAction optionAction = new ReAbstractAction(
				"Edit Options",
				Common.getRecordIcon(Common.ID_PREF_ICON)) {
			public void actionPerformed(ActionEvent e) {
				 new EditOptions(false, true, true);
			}
		};
    	AbstractAction[] toolbarActions = {
    			optionAction
    	};
		buildMenubar(VelocityPopup.getLayoutPopup(), null, null);
		buildToolbar(newAction, toolbarActions);

		buildFileMenu(null, false, true, newAction);
		super.addExit();

        CopybookLoaderFactory.setInstance(new CopybookLoaderFactoryDB());

		AbsDB.setSystemLog(getLog());

		Common.setCurrClass(this);

	    getEditMenu().addSeparator();
	    getEditMenu().add(optionAction);

	    int idx = Common.getConnectionIndex();
	    UpgradeDB.checkForUpdate(idx);
	    Common.freeConnection(idx);


        if (Common.OPTIONS.showRecordEditorTips.isSelected() && TipsManager.tipsModulePresent()) {
        	TipsManager.startTips(this, Parameters.getSytemJarFileDirectory() + "/LayoutEditor_TipOfTheDay.properties",
        					  Parameters.SHOW_RECORDEDITOR_TIPS);
        }

	}



	/**
	 * Add program specific dropdown menus
	 * @param menubar top level menu
	 */
	protected void addProgramSpecificMenus(JMenuBar menubar) {

		menu = new Menu(this);

		menubar.add(new LayoutMenu(menu));
	}


	/**
	 * Create the GUI and show it.  For thread safety,
	 * this method should be invoked from the
	 * event-dispatching thread.
	 */
	public static void createAndShowGUI() {

		//Make sure we have nice window decorations.
		//JFrame.setDefaultLookAndFeelDecorated(true);

		//Create and set up the window.
		LayoutEdit frame = new LayoutEdit();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setVisible(true);
	}



    /**
     * @see net.sf.RecordEditor.utils.common.ReActionHandler#executeAction(int)
     */
    public void executeAction(int action) {

        if (action == ReActionHandler.NEW) {
            if (ReFrame.getActiveFrame().isActionAvailable(action)) {
                ReFrame.getActiveFrame().executeAction(action);
            } else {
                new RecordEdit1Record(menu.getCurrentDbName(),
                        menu.getCurrentDbIdentifier(), null, null);
            }
        } else if (action == ReActionHandler.OPEN) {
            menu.setVisible(true);
            menu.moveToFront();
            menu.requestFocus();
        } else {
            super.executeAction(action);
        }
    }

    /**
     * @see net.sf.RecordEditor.utils.common.ReActionHandler#isActionAvailable(int)
     */
    public boolean isActionAvailable(int action) {
        return action == ReActionHandler.NEW
            || action == ReActionHandler.OPEN
            || super.isActionAvailable(action);
    }


	/**
	 * Close the application and Database (used in automated testing)
	 */
	public static void close() {
		try {
			ReFrame.closeAllFrames();
			Common.closeConnection();
		} catch (Exception e) {

		}
	}


    /**
	 * Run record layout editor
	 *
	 * @param args paramaters (none used)
	 */
	public static void main(String[] args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

}
