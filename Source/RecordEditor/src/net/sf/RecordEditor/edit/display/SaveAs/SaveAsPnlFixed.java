/**
 *
 */
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
import net.sf.RecordEditor.re.fileWriter.FixedWriter;
import net.sf.RecordEditor.re.openFile.RecentFiles;
import net.sf.RecordEditor.utils.swing.BasePanel;

/**
 * @author Bruce Martin
 *
 */
public class SaveAsPnlFixed extends SaveAsPnlBase {

	private FixedWriter writer;


	/**
	 * @param commonSaveAsFields common screen fields
	 */
	public SaveAsPnlFixed(CommonSaveAsFields commonSaveAsFields) {
		super(commonSaveAsFields, ".txt", CommonSaveAsFields.FMT_FIXED, RecentFiles.RF_NONE, null);

		BasePanel pnl1 = new BasePanel();
		BasePanel pnl2 = new BasePanel();

		pnl1.addLine("names on first line", namesFirstLine);
		pnl1.addLine("space between fields", spaceBetweenFields);

		fieldTbl = new JTable();
		pnl2.addComponent(1, 5, 130, BasePanel.GAP,
		        BasePanel.FULL, BasePanel.FULL, fieldTbl);
		pnl2.setComponentName(fieldTbl, "FixedColNames");


		panel.addComponent(1, 5, BasePanel.FILL, BasePanel.GAP,
		        BasePanel.FULL, BasePanel.FULL,
		        new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnl1, pnl2));

		setupPrintDetails(true);
	}

	public void save(String selection, String outFile) throws IOException {
        String fieldSeperator = "";
        String fontname = font.getText();

        if (spaceBetweenFields.isSelected()) {
        	fieldSeperator = " ";
        }


        writer = new FixedWriter(outFile, fieldSeperator, fontname,
        					getFieldLengths(),
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

    	if (commonSaveAsFields.printRecordDetails != null) {
        	List<ExternalField> ef = new ArrayList<ExternalField>(commonSaveAsFields.printRecordDetails.getFieldCount());
	    	int pos = 1;
	    	int sepLength = 0;
	    	List<String> colNames = commonSaveAsFields.flatFileWriter.getColumnNames();
	    	int levels = commonSaveAsFields.flatFileWriter.getLevelCount();
	    	int[] lengths = writer.getColumnWidths();
        	if (spaceBetweenFields.isSelected()) {
        		sepLength = 1;
            }

	    	//TODO  Fix
        	//TODO  Fix
        	//TODO  Fix

        	for (int i = 0; i < levels; i++) {
    			ef.add(new ExternalField(
    					pos, lengths[i],
    					colNames.get(i),
    					"", Type.ftChar, 0, 0, "", "", "", 0));
    			pos += sepLength + lengths[i];
	    	}
        	for (int i = levels; i < colNames.size(); i++) {
	    	//for (int i = 0; i < fieldCount; i++) {
//	    		if (include == null || (include.length > i-levels && include[i-levels])) {
	    			ef.add(new ExternalField(
	    					pos, lengths[i],
	    					colNames.get(i),
	    					"", Type.ftChar, 0, 0, "", "", "", 0));
	    			pos += sepLength + lengths[i];
//	    		}
	    	}

	    	ret = StandardLayouts.getInstance().getFixedLayout(ef);
    	}
    	return ret;
    }


}
