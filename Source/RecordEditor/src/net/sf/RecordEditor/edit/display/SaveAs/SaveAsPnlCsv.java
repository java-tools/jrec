package net.sf.RecordEditor.edit.display.SaveAs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JSplitPane;
import javax.swing.JTable;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.External.Def.ExternalField;
import net.sf.JRecord.Types.Type;
import net.sf.RecordEditor.edit.util.StandardLayouts;
import net.sf.RecordEditor.re.fileWriter.CsvWriter;
import net.sf.RecordEditor.re.openFile.RecentFiles;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.BasePanel;

public class SaveAsPnlCsv extends SaveAsPnlBase {

	/**
	 * @param commonSaveAsFields common screen fields
	 */
	public SaveAsPnlCsv(CommonSaveAsFields commonSaveAsFields) {
		super(commonSaveAsFields, ".csv", CommonSaveAsFields.FMT_CSV, RecentFiles.RF_NONE, null);

		BasePanel pnl1 = new BasePanel();
		BasePanel pnl2 = new BasePanel();

		pnl1.setFieldNamePrefix("Csv");
		pnl1.addLine("Delimiter", delimiterCombo);
		pnl1.addLine("Quote", quoteCombo);
		pnl1.addLine("names on first line", namesFirstLine);
		pnl1.addLine("Add Quote to all Text Fields", quoteAllTextFields);

		fieldTbl = new JTable();
		pnl2.addComponent(1, 5, 130, BasePanel.GAP,
		        BasePanel.FULL, BasePanel.FULL,fieldTbl);
		pnl2.setComponentName(fieldTbl, "CsvColNames");


		panel.addComponent(1, 5, BasePanel.FILL, BasePanel.GAP,
		        BasePanel.FULL, BasePanel.FULL,
		        new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnl1, pnl2));

		setupPrintDetails(false);
	}


	public void save(String selection, String outFile) throws IOException {
		String fieldSeperator = delimiterCombo.getSelectedEnglish();
		String fontname = font.getText();


		CsvWriter writer = new CsvWriter(outFile, fieldSeperator, fontname,
									getQuote(), quoteAllTextFields.isSelected(),
									getIncludeFields());


		save_writeFile(writer, selection);
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.SaveAs.SaveAsPnlBase#getEditLayout()
	 */
	@Override
	public AbstractLayoutDetails getEditLayout(String ext, AbstractLayoutDetails l) {
		AbstractLayoutDetails ret = null;
		StandardLayouts genLayout = StandardLayouts.getInstance();
		boolean EmbeddedCr = false;
		if (l != null && l.getRecordCount() > 0
		&& l.getRecord(0) instanceof RecordDetail) {
			EmbeddedCr = ((RecordDetail) l.getRecord(0)).isEmbeddedNewLine();
		}

		if (namesFirstLine.isSelected()) {
     	   ret = genLayout.getCsvLayoutNamesFirstLine(
     			   			delimiterCombo.getSelectedEnglish(),
     			   			getQuote(),
     			   			EmbeddedCr);
     	} else if (commonSaveAsFields.printRecordDetails != null) {
        	List<ExternalField> ef = new ArrayList<ExternalField>(commonSaveAsFields.printRecordDetails.getFieldCount());
        	int pos = 1;
	    	List<String> colNames = commonSaveAsFields.flatFileWriter.getColumnNames();

	    	for (int i = 0; i < colNames.size(); i++) {
    			ef.add(new ExternalField(
    					pos++, Common.NULL_INTEGER,
    					colNames.get(i),
    					"", Type.ftChar, 0, 0, "", "", "", 0));
	    	}


	    	ret = genLayout.getCsvLayout(
	    							ef,
	    							delimiterCombo.getSelectedEnglish(),
	        			   			getQuote(),
	        			   			EmbeddedCr);
    	}
    	return ret;
	}


}
