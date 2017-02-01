package net.sf.RecordEditor.layoutWizard;


import java.awt.Component;
import java.io.File;

import javax.swing.RootPaneContainer;

import net.sf.JRecord.Common.CommonBits;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.base.ExternalConversion;
import net.sf.RecordEditor.utils.BasicLayoutCallback;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.swing.AbsRowList;
import net.sf.RecordEditor.utils.wizards.AbstractWizardPanel;


@SuppressWarnings("unchecked")
public class WizardFileLayoutCsv extends WizardFileLayoutBase {


	/**
	 * Wizard to create a file copybook based on a sample data file.
	 *
	 * @param frame frame to display the panel
	 * @param fileName data file name
	 */
	public <T extends Component & RootPaneContainer> WizardFileLayoutCsv(T frame,
			BasicLayoutCallback callback, final String fileName, LayoutDetail layout,
			boolean skipCsvTable,
			boolean visible) {
		super(frame, callback);

	    AbsRowList       typeList = new AbsRowList(0, 1, false, false).loadData(
	    		ExternalConversion.getTypes(0)
	    );
//	    AbsRowList  structureList = new ManagerRowList(LineIOProvider.getInstance(), true);
	    AbstractWizardPanel<Details>[] panelsCsv = new AbstractWizardPanel[3];

	    Details wizardDetails = super.getWizardDetails();
	    
	    wizardDetails.recordType = Details.RT_DELIMITERED_FIELDS;
		wizardDetails.filename = fileName;
		wizardDetails.layoutName = (new File(fileName)).getName() + ".xml";
		wizardDetails.layoutDirectory = Parameters.getFileName(Parameters.COPYBOOK_DIRECTORY);
		wizardDetails.fontName = layout.getFontName();
		wizardDetails.quote = layout.getQuote();
		wizardDetails.fieldSeperator = layout.getDelimiter();
		wizardDetails.fileStructure = layout.getFileStructure();
		wizardDetails.fieldNamesOnLine = CommonBits.areFieldNamesOnTheFirstLine(wizardDetails.fileStructure);
		wizardDetails.embeddedCr = CommonBits.isEmbeddedCrSupported(wizardDetails.fileStructure);
		


//		panelsCsv[0] = new Pnl2FileFormat();
		panelsCsv[0] = new Pnl3CsvTable();
		panelsCsv[1] = new Pnl4CsvNames(typeList, false);
		panelsCsv[2] = new Pnl5SaveFileLayout();

		super.setPanels(panelsCsv, visible);
		
		if (skipCsvTable) {
			super.changePanel(1);
		}
	}


}
