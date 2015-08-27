package net.sf.RecordEditor.layoutWizard;

import java.util.ArrayList;

import net.sf.JRecord.ByteIO.ByteTextReader;
import net.sf.JRecord.Common.Constants;
import net.sf.RecordEditor.re.util.csv.CsvSelectionPanel;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.BasePanel;

@SuppressWarnings("serial")
public class Pnl3CsvTable extends WizardPanel {

	private final static byte[][] oneBlankLine = {{}};
	private CsvSelectionPanel pnl = new CsvSelectionPanel(oneBlankLine, "", false, null);
	private Details wizardDetail;

	//private Details currentDetails;
	
	public Pnl3CsvTable() {

		this.setHelpURLre(Common.formatHelpURL(Common.HELP_WIZARD_PNL4));

		this.addComponentRE(0, 4, BasePanel.PREFERRED,0,
		        BasePanel.FULL, BasePanel.FULL,
		        pnl);
	}
	
	
	@Override
	public final Details getValues() throws Exception {
	    int i, numCols;
	    ColumnDetails colDtls;

	    wizardDetail.fieldSeperator = pnl.fieldSeparatorCombo.getSelectedItem().toString();
	    wizardDetail.actualSeperator = pnl.getSeperator();
		wizardDetail.quote = pnl.quoteCombo.getSelectedItem().toString();
		wizardDetail.actualQuote = pnl.getQuote();
		wizardDetail.parserType = ((Integer) pnl.parseType.getSelectedItem()).intValue();
		wizardDetail.fieldNamesOnLine = pnl.fieldNamesOnLine.isSelected();
		if (wizardDetail.fieldNamesOnLine) {
			wizardDetail.fileStructure = Constants.IO_NAME_1ST_LINE;
		}
	    
	    wizardDetail.standardRecord.columnDtls = new ArrayList<ColumnDetails>();
	    numCols = pnl.getColumnCount();
	    
	    
	    for (i = 0; i < numCols; i++) {
            colDtls = new ColumnDetails(i, wizardDetail.defaultType.intValue());
            colDtls.start = i + 1;
            colDtls.length = 0;
            colDtls.decimal = 0;
            colDtls.include = Boolean.TRUE;
            colDtls.name = pnl.getColumnName(i);
            wizardDetail.standardRecord.columnDtls.add(i, colDtls);
	    }
	    return wizardDetail;
	}

	@Override
	public final void setValues(Details detail) throws Exception {
		
		this.wizardDetail = detail;
		//currentDetails = detail;
		if (! "".equals(detail.fieldSeperator)) {
			pnl.fieldSeparatorCombo.setSelectedItem(detail.fieldSeperator);
		}
		if (! "".equals(detail.quote)) {
			pnl.quoteCombo.setSelectedItem(detail.quote);
		}
		pnl.parseType.setSelectedIndex(detail.parserType);
		pnl.fieldNamesOnLine.setSelected(detail.fieldNamesOnLine);
		
		setValues_100_ReadFile(detail);
	}
	
	
	private void setValues_100_ReadFile(Details detail) throws Exception {
		ByteTextReader r = new ByteTextReader();
		//BufferedReader r = new BufferedReader(new FileReader(detail.filename));
		byte[] line;
		int i = 0;
		
		r.open(detail.filename);
		while (i < detail.standardRecord.records.length && (line = r.read()) != null) {
			detail.standardRecord.records[i++] = line;
		}
		pnl.setLines(detail.standardRecord.records, detail.fontName, i);
		detail.standardRecord.numRecords = i;
	}
}
