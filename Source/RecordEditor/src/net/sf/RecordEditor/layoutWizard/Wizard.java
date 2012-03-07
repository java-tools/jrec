package net.sf.RecordEditor.layoutWizard;

import java.awt.event.ActionEvent;

import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.RecordEditor.layoutEd.Record.ExtendedRecordDB;
import net.sf.RecordEditor.layoutEd.Record.RecordRec;
import net.sf.RecordEditor.layoutEd.Record.TypeList;
import net.sf.RecordEditor.layoutEd.Table.TableDB;
import net.sf.RecordEditor.layoutEd.Table.TableRec;
import net.sf.RecordEditor.utils.LayoutConnection;
import net.sf.RecordEditor.utils.LayoutConnectionAction;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.edit.ManagerRowList;
import net.sf.RecordEditor.utils.jdbc.DBList;
import net.sf.RecordEditor.utils.swing.AbsRowList;
import net.sf.RecordEditor.utils.wizards.AbstractWizard;
import net.sf.RecordEditor.utils.wizards.AbstractWizardPanel;


@SuppressWarnings("unchecked")
public class Wizard extends AbstractWizard<Details> {

	private AbstractWizardPanel<Details>[] panelsFixed 		= new AbstractWizardPanel[5]; 
	private AbstractWizardPanel<Details>[] panelsCsv   		= new AbstractWizardPanel[5]; 
	private AbstractWizardPanel<Details>[] panelsUnicodeCsv	= new AbstractWizardPanel[5]; 
	private AbstractWizardPanel<Details>[] panelsMulti 		= new AbstractWizardPanel[7]; 


	private int connectionIdx;
	private LayoutConnection callbackClass;


	public Wizard(final int connectionId,
            final String fileName, final LayoutConnection callback) {
		super("File Wizard", new Details());
	
		boolean free = Common.isSetDoFree(false);
		connectionIdx = connectionId;
		callbackClass = callback;
		getWizardDetails().filename = fileName;
		
	    ReConnection con = new ReConnection(connectionIdx);
		TableDB    structureTable = new TableDB();
		TableDB       systemTable = new TableDB();
		structureTable.setParams(Common.TI_FILE_STRUCTURE);
		systemTable.setParams(Common.TI_SYSTEMS);
	    AbsRowList       typeList = new TypeList(connectionIdx, true, false);
//	    AbsRowList  structureList = new DBList<TableRec>(structureTable, 0, 1, true, false);
	    AbsRowList  structureList = new ManagerRowList(LineIOProvider.getInstance(), false);
	    AbsRowList     systemList = new DBList<TableRec>(systemTable, 0, 1, true, false);
	    
	    
		structureTable.setConnection(con);
		systemTable.setConnection(con);

		
		panelsFixed[0] = new Pnl1File(structureList, typeList);
		panelsFixed[1] = new Pnl2FileFormat();
		panelsFixed[2] = new Pnl3Table(super.getMessage());
		panelsFixed[3] = new Pnl4Names(typeList); 
		panelsFixed[4] = new Pnl7SaveDbLayout(systemList); 
		
		panelsCsv[0] = panelsFixed[0];
		panelsCsv[1] = panelsFixed[1];
		panelsCsv[2] = new Pnl3CsvTable();
		panelsCsv[3] = new Pnl4CsvNames(typeList, false);
		panelsCsv[4] = panelsFixed[4];
		
		System.arraycopy(panelsCsv, 0, panelsUnicodeCsv, 0, panelsCsv.length);
		
		panelsMulti[0] = panelsFixed[0];
		panelsMulti[1] = panelsFixed[1];
		panelsMulti[2] = new Pnl3RecordType(typeList, super.getMessage());
		panelsMulti[3] = new Pnl4RecordNames();
		panelsMulti[4] = new Pnl5RecordTable(getMessage());
		panelsMulti[5] = new Pnl6RecordFieldNames(typeList);
		panelsMulti[6] = panelsFixed[4];
		
		super.setPanels(panelsFixed);
		Common.setDoFree(free, connectionId);
	}

	
	
	/**
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizard#changePanel(int)
	 */
	@Override
	public void changePanel(int inc) {
		
		if (getPanelNumber() == 0) {
			Details details = super.getWizardDetails();
			if (details.recordType == Details.RT_FIXED_LENGTH_FIELDS) {
				super.setPanels(panelsFixed);
			} else if (details.recordType == Details.RT_MULTIPLE_RECORDS) {
				super.setPanels(panelsMulti);
			} else if (details.unicode) {
				super.setPanels(panelsUnicodeCsv);
			} else {
				super.setPanels(panelsCsv);
			}
		}
		super.changePanel(inc);
	}



	@Override
	public void finished(Details details) {

        try {
	        ExternalRecord rec = details.createRecordLayout();
	        ExtendedRecordDB db = new ExtendedRecordDB();
	        db.setConnection(new ReConnection(connectionIdx));
	        
	        db.insert(new RecordRec(rec));
	
	        Common.checkpoint(connectionIdx);
	
	        if (callbackClass != null) {
	            callbackClass.setRecordLayout(rec.getRecordId(), rec.getRecordName(), details.filename);
	        }

            this.setClosed(true);
         } catch (Exception ex) {
            super.getMessage().setText(ex.getMessage());
            Common.logMsg(ex.getMessage(), ex);
        }
	}

 
  
 
	   /**
     * create Action to start this wizard
     *
     * @param callback Interface back  to invoking class
     *
     * @return requested action
     */
    @SuppressWarnings("serial")
	public static LayoutConnectionAction getAction(LayoutConnection callback) {
        return new LayoutConnectionAction(
        		"Layout Wizard", 
        		callback,
        		Common.ID_WIZARD_ICON) {
            public void actionPerformed(ActionEvent e) {
                try {
                   new Wizard(getCallback().getCurrentDbIdentifier(),
                        getCallback().getCurrentFileName(), getCallback());
                } catch (Exception ex) {
                    Common.logMsg("Cant start wizard", ex);
                }
            }
        };
    }
}
