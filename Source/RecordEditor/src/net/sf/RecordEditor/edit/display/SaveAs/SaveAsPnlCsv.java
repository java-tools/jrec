package net.sf.RecordEditor.edit.display.SaveAs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JSplitPane;
import javax.swing.JTable;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.External.ExternalField;
import net.sf.JRecord.Types.Type;
import net.sf.RecordEditor.edit.util.StandardLayouts;
import net.sf.RecordEditor.re.openFile.RecentFiles;
import net.sf.RecordEditor.utils.CsvWriter;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.BasePanel;

public class SaveAsPnlCsv extends SaveAsPnlBase {

	
	public SaveAsPnlCsv(CommonSaveAsFields commonSaveAsFields) {
		super(commonSaveAsFields, ".csv", CommonSaveAsFields.FMT_CSV, RecentFiles.RF_NONE, null);
		
		BasePanel pnl1 = new BasePanel();
		BasePanel pnl2 = new BasePanel();
		
		pnl1.addLine("Delimiter", delimiterCombo);
		pnl1.addLine("Quote", quoteCombo);
		pnl1.addLine("names on first line", namesFirstLine);
		pnl1.addLine("Add Quote to all Text Fields", quoteAllTextFields);
		
		fieldTbl = new JTable();
		pnl2.addComponent(1, 5, 130, BasePanel.GAP,
		        BasePanel.FULL, BasePanel.FULL,fieldTbl);


		panel.addComponent(1, 5, BasePanel.FILL, BasePanel.GAP,
		        BasePanel.FULL, BasePanel.FULL,
		        new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnl1, pnl2));
	}

	
	public void save(String selection, String outFile) throws IOException {
		String fieldSeperator = Common.FIELD_SEPARATOR_LIST1_VALUES[delimiterCombo.getSelectedIndex()];
		String fontname = font.getText();

		
		CsvWriter writer = new CsvWriter(outFile, fieldSeperator, fontname, 
									getQuote(), quoteAllTextFields.isSelected(), 
									getIncludeFields());


		save_writeFile(writer, selection);


	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.SaveAs.SaveAsPnlBase#getEditLayout()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public AbstractLayoutDetails getEditLayout(String ext) {
		AbstractLayoutDetails ret = null;
		StandardLayouts genLayout = StandardLayouts.getInstance();
		
		if (namesFirstLine.isSelected()) {
     	   ret = genLayout.getCsvLayoutNamesFirstLine(
     			   			delimiterCombo.getSelectedItem().toString(), 
     			   			getQuote());
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
	    							delimiterCombo.getSelectedItem().toString(), 
	        			   			getQuote());
    	}
    	return ret;
	}
	
	
}
