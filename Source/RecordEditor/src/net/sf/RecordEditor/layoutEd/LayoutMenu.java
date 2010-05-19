/*
 * @Author Bruce Martin
 * Created on 10/01/2007
 *
 * Purpose:
 * Top level dropdown menu with basic layout functions
 *
 * Changes
 * * Version 0.61
 *   - Set the callback in the create (layout) action in the
 *     setDatabaseDetails (fixes bug of create Layout menu not working
 *     in the full editor
 *   - Added try { } catch blocks around addSeperator calls to
 *     support more LookAndFeel packages
 *
 */
package net.sf.RecordEditor.layoutEd;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenu;

import net.sf.RecordEditor.layoutEd.Record.LoadXmlLayoutsIntoDB;
import net.sf.RecordEditor.layoutEd.Record.RecordEdit1Record;
import net.sf.RecordEditor.layoutEd.Record.SaveLayoutsDBAsXml;
import net.sf.RecordEditor.layoutWizard.Wizard;
import net.sf.RecordEditor.utils.LayoutConnection;
import net.sf.RecordEditor.utils.LayoutConnectionAction;


/**
 *
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class LayoutMenu extends JMenu {

    private static final int SYSTEM_TABLE = 3;
    private LayoutConnection databaseDetails;
    private LayoutConnectionAction create;
    private LayoutConnectionAction createWizard;


    /**
     * Layout menu
     * @param dbDetails Current Database details (callback)
     */
    public LayoutMenu(final LayoutConnection dbDetails) {
        super("Record Layouts");

        databaseDetails = dbDetails;

        AbstractAction edit = new AbstractAction("Edit Layout") {
            public void actionPerformed(ActionEvent e) {
                new RecordEdit(databaseDetails.getCurrentDbName(),
                               databaseDetails.getCurrentDbIdentifier());
            }
        };
        createWizard = Wizard.getAction(databaseDetails);
        AbstractAction editSystemTable = new AbstractAction("Edit System Table") {
            public void actionPerformed(ActionEvent e) {
                new TblEdit(databaseDetails.getCurrentDbName(),
                        null,
                        databaseDetails.getCurrentDbIdentifier(),
                        SYSTEM_TABLE);
            }
        };
        AbstractAction comboEdit = new AbstractAction("Edit Combo Lists") {
            public void actionPerformed(ActionEvent e) {
            	new ComboEdit(
            			databaseDetails.getCurrentDbName(),
        				databaseDetails.getCurrentDbIdentifier());
            }
        };

        AbstractAction loadCobol = new AbstractAction("Load Cobol Copybook") {
            public void actionPerformed(ActionEvent e) {
		        new LoadCopyBook(
        				false,
        						/* choosing between Cobol and XML Copybooks */
        				databaseDetails.getCurrentDbName(),
        				databaseDetails.getCurrentDbIdentifier(),
        				databaseDetails);
            }
        };
        AbstractAction loadCopybook = new AbstractAction("Load Copybook") {
            public void actionPerformed(ActionEvent e) {
		        new LoadCopyBook(
        				true,
        						/* choosing between Cobol and XML Copybooks */
        				databaseDetails.getCurrentDbName(),
        				databaseDetails.getCurrentDbIdentifier(),
        				databaseDetails);
            }
        };
        AbstractAction saveCopybooksAsXml = new AbstractAction("Save Copybooks as Xml") {
            public void actionPerformed(ActionEvent e) {
		        new SaveLayoutsDBAsXml(databaseDetails.getCurrentDbIdentifier());
            }
        };
        AbstractAction loadCopybooksFromXml = new AbstractAction("Load Copybooks from Xml") {
            public void actionPerformed(ActionEvent e) {
		        new LoadXmlLayoutsIntoDB(databaseDetails.getCurrentDbIdentifier());
            }
        };

        create = RecordEdit1Record.getAction(databaseDetails);

        this.add(edit);
        this.add(create);
        this.add(createWizard);
        
        addSeperator();
        
        this.add(comboEdit);
        
        addSeperator();
        
        this.add(loadCobol);
        this.add(loadCopybook);
        this.add(saveCopybooksAsXml);
        this.add(loadCopybooksFromXml);

        addSeperator();
        
        this.add(editSystemTable);
    }
    
    private void addSeperator() {
    	
        try {
            this.addSeparator();
        } catch (Exception e) {
        }
    }


    /**
     * Set the DB connection class
     * @param dbDetails The databaseDetails to set.
     */
    public void setDatabaseDetails(LayoutConnection dbDetails) {
        this.databaseDetails = dbDetails;
        create.setCallback(dbDetails);
        createWizard.setCallback(dbDetails);
    }
}
 