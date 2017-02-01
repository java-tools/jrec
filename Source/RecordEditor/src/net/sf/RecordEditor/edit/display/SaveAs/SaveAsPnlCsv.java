package net.sf.RecordEditor.edit.display.SaveAs;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JSplitPane;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.External.Def.ExternalField;
import net.sf.JRecord.Types.Type;
import net.sf.RecordEditor.edit.util.StandardLayouts;
import net.sf.RecordEditor.re.fileWriter.FieldWriter;
import net.sf.RecordEditor.re.fileWriter.WriterBuilder;
import net.sf.RecordEditor.re.openFile.RecentFiles;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.BasePanel;

public class SaveAsPnlCsv extends SaveAsPnlBase {

//	final JButton selectBtn = SwingUtils.newButton("Select All Fields");
//	final JButton deSelectBtn = SwingUtils.newButton("Deselect All Fields");
	/**
	 * @param commonSaveAsFields common screen fields
	 */
	public SaveAsPnlCsv(CommonSaveAsFields commonSaveAsFields) {
		super(commonSaveAsFields, ".csv", CommonSaveAsFields.FMT_CSV, RecentFiles.RF_NONE, null, true);

		BasePanel pnl1 = new BasePanel();
		BasePanel pnl2 = new BasePanel();

		pnl1.setFieldNamePrefix("Csv");
		pnl1.addLineRE("Delimiter", delimiterCombo);
		pnl1.addLineRE("Quote", quoteCombo);
		pnl1.addLineRE("Charset", charsetCombo);
		pnl1.addLineRE("names on first line", namesFirstLine);
		pnl1.addLineRE("Add Quote to all Text Fields", quoteAllTextFields);
		
		pnl2.setGapRE(3);
		pnl2.addComponentRE(1, 5, BasePanel.PREFERRED, 1, BasePanel.FULL, BasePanel.FULL,  getSelectBtnPnl());
		pnl2.addComponentRE(1, 5, 130, BasePanel.GAP,
		        BasePanel.FULL, BasePanel.FULL, fieldTbl);
		pnl2.setComponentName(fieldTbl, "CsvColNames");


		panel.addComponentRE(1, 5, BasePanel.FILL, BasePanel.GAP,
		        BasePanel.FULL, BasePanel.FULL,
		        new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnl1, pnl2));

		setupPrintDetails(false);
	}


	public void save(String selection, OutputStream outStream) throws IOException, RecordException {
		String fontname = getCharset();
		String fieldSeperator = Conversion.decodeFieldDelim(delimiterCombo.getDelimiter(), fontname);

		if (fieldSeperator != null && fieldSeperator.toLowerCase().startsWith("x'") && Conversion.isMultiByte(fontname)) {
			throw new RecordException("Hex seperators can not be used with a multibyte charset");
		}


		FieldWriter writer = WriterBuilder.newCsvWriter(outStream, fieldSeperator, fontname,
									Conversion.decodeCharStr(getQuote(), fontname),
									quoteAllTextFields.isSelected(),
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
     			   			delimiterCombo.getDelimiter(),
     			   			charsetCombo.getText(),
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
	    							"Default", 
	    							delimiterCombo.getDelimiter(),
	    							charsetCombo.getText(),
	        			   			getQuote(),
	        			   			EmbeddedCr);
    	}
    	return ret;
	}


}
