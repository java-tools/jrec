package net.sf.RecordEditor.layoutWizard;


import java.awt.Component;

import javax.swing.RootPaneContainer;

import net.sf.JRecord.External.CopybookWriterManager;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.base.CopybookWriter;
import net.sf.JRecord.External.base.ExternalConversion;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.RecordEditor.utils.BasicLayoutCallback;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.edit.ManagerRowList;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.AbsRowList;
import net.sf.RecordEditor.utils.wizards.AbstractWizard;
import net.sf.RecordEditor.utils.wizards.AbstractWizardPanel;


@SuppressWarnings("unchecked")
public class WizardFileLayout2 extends AbstractWizard<Details> {

	private AbstractWizardPanel<Details>[] panelsFixed = new AbstractWizardPanel[5];

	private AbstractWizardPanel<Details>[] panelsCsv   = new AbstractWizardPanel[5];

	private AbstractWizardPanel<Details>[] panelsMulti = new AbstractWizardPanel[7];

	private BasicLayoutCallback callbackClass;

	private ExternalRecord externalRecord = null;




	/**
	 * Wizard to create a file copybook based on a sample data file.
	 *
	 * @param fileName sample file
	 */
	public WizardFileLayout2(final String fileName) {
		this(new ReFrame("", "File Wizard", "", null), fileName, null, false, true);
	}

	/**
	 * Wizard to create a file copybook based on a sample data file.
	 *
	 * @param frame frame to display the panel
	 * @param fileName data file name
	 */
	public <T extends Component & RootPaneContainer> WizardFileLayout2(T frame,
			final String fileName, BasicLayoutCallback callback, boolean alwaysShowCsv,
			boolean visible) {
		super(frame, new Details());

		getWizardDetails().filename = fileName;
		callbackClass = callback;

	    AbsRowList       typeList = new AbsRowList(0, 1, false, false).loadData(
	    		ExternalConversion.getTypes(0)
	    );
	    AbsRowList  structureList = new ManagerRowList(LineIOProvider.getInstance(), true);

	    super.getWizardDetails().layoutDirectory = Parameters.getFileName(Parameters.COPYBOOK_DIRECTORY);


		panelsFixed[0] = Pnl1FileX.newPane(structureList, typeList);
		panelsFixed[1] = new Pnl2FileFormat();
		panelsFixed[2] = new Pnl3Table(super.getMessage());
		panelsFixed[3] = new Pnl4Names(typeList);
		panelsFixed[4] = new Pnl5SaveFileLayout();

		panelsCsv[0] = panelsFixed[0];
		panelsCsv[1] = panelsFixed[1];
		panelsCsv[2] = new Pnl3CsvTable();
		panelsCsv[3] = new Pnl4CsvNames(typeList, false);
		panelsCsv[4] = panelsFixed[4];

		panelsMulti[0] = panelsFixed[0];
		panelsMulti[1] = panelsFixed[1];
		panelsMulti[2] = new Pnl3RecordTypeMF(typeList, getMessage());
		panelsMulti[3] = new Pnl4RecordNames();
		panelsMulti[4] = new Pnl5RecordTable(getMessage());
		panelsMulti[5] = new Pnl6RecordFieldNames(typeList);
		panelsMulti[6] = panelsFixed[4];

		super.setPanels(panelsFixed, visible);
	}



	/**
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizard#changePanel(int)
	 */
	@Override
	public void changePanel(int inc) {
		changePanel(inc, true);
	}

	public void changePanel(int inc, boolean visible) {

		if (getPanelNumber() == 0) {
			if (super.getWizardDetails().recordType == Details.RT_FIXED_LENGTH_FIELDS) {
				super.setPanels(panelsFixed, visible);
			} else if (super.getWizardDetails().recordType == Details.RT_MULTIPLE_RECORDS) {
				super.setPanels(panelsMulti, visible);
			} else {
				super.setPanels(panelsCsv, visible);
			}
		}
		super.changePanel(inc);
	}


	/**
	 * Save the new layout at the end
	 */
	@Override
	public void finished(Details details) {

		//System.out.println("Finnished: " + details.layoutName);
		if ("".equals(details.layoutName)) {
			super.getMessage().setText("Layout filename must be entered");
		} else {
	        try {
	        	String copybookFile;
		        externalRecord = details.createRecordLayout();
		        CopybookWriter w = CopybookWriterManager.getInstance().get(details.layoutWriterIdx);


		        String dir = details.layoutDirectory;

		        if (dir != null && dir.length() > 0) {
		        	dir = fixDirectory(dir);

					copybookFile = w.writeCopyBook(dir, externalRecord, Common.getLogger());

			        if (callbackClass != null) {
			            callbackClass.setRecordLayout(externalRecord.getRecordId(),  copybookFile, details.filename);
			        }
		        }

	         } catch (Exception ex) {
	            super.getMessage().setText(ex.getMessage());
	            Common.logMsgRaw(ex.getMessage(), ex);
	            ex.printStackTrace();
	        } finally {
	        	try {
	        		this.setClosed(true);
	        	} catch (Exception e) {

				}
	        }
		}
	}

	private final static String fixDirectory(String dir) {
		if (dir != null && dir.length() > 0) {
			while (dir.endsWith("*")) {
				dir = dir.substring(0, dir.length() - 1);
			}
			if ((! dir.endsWith("/")) && (! dir.endsWith("\\"))) {
				dir = dir + Common.FILE_SEPERATOR;
			}
		}
		return dir;
	}

	public ExternalRecord getExternalRecord() {
		return externalRecord;
	}
	
	public static void main(String[] args) {
		ReMainFrame f = new ReMainFrame("Wizard", "", "WZ");
		
		f.setVisible(true);
		new WizardFileLayout2("");
	}
}
