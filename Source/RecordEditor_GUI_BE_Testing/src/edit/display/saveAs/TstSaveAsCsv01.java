package edit.display.saveAs;

import static org.junit.Assert.assertArrayEquals;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.table.TableCellEditor;

import net.sf.JRecord.Common.RecordException;
import net.sf.RecordEditor.edit.display.SaveAs.CommonSaveAsFields;
import net.sf.RecordEditor.edit.display.SaveAs.SaveAsPnlCsv;
import net.sf.RecordEditor.edit.display.SaveAs.ZTstSaveAsBuildTestData;

import org.junit.Test;

import xCommon.DDetails;
import xCommon.XFileData;
import xCommon.XSchemas;

/**
 * Test action of select/de-select buttons for SaceCsv and SavedFixed screens
 * 
 * @author Bruce Martin
 *
 */
public class TstSaveAsCsv01 {

	
	
	@Test
	public void testSaveCsvDoSave01() throws IOException {
		DDetails dtls = DDetails.getDtar020Details(XSchemas.CharSetType.EBCDIC);

		tstSave(dtls, "#", CommonSaveAsFields.OPT_FILE, XFileData.dtar020Data());
	}

		
	@Test
	public void testSaveCsvDoSave02() throws IOException {
		int[] rows = {1, 3, 4, 6, 7};
		DDetails dtls = DDetails.getDtar020Details(XSchemas.CharSetType.EBCDIC, rows);
		
		tstSave(dtls, ";", CommonSaveAsFields.OPT_SELECTED,  XFileData.filterOnRows(XFileData.dtar020Data(), rows));
	}
	
//	@Test
//	public void testSaveCsvDoSave03() throws IOException {
//		int cols[] = {0};
//		DDetails dtls = DDetails.getDtar020Details(XSchemas.CharSetType.EBCDIC, null);
//		
//		SaveAsPnlCsv saveAsPnlCsv = new SaveAsPnlCsv(dtls.saveAsFields);
//		saveAsPnlCsv.quoteAllTextFields.setSelected(true);
//		
//		char quote = '\'';
//		
//		for (int i = 0; i < saveAsPnlCsv.)
//		tstSave("~", CommonSaveAsFields.OPT_FILE,  XFileData.filterAddQuote(XFileData.dtar020Data(), cols, quote),
//				saveAsPnlCsv);
//	}
	
	
	
	/**
	 * Select specific columns to be printed on the Csv file.
	 * 
	 * @throws IOException any IOError
	 */
	@Test
	public void testSaveCsvDoSave04() throws IOException {
		int[] cols = {0, 1, 3,  5};
		DDetails dtls = DDetails.getDtar020Details(XSchemas.CharSetType.EBCDIC);
		SaveAsPnlCsv saveAsPnlCsv = new SaveAsPnlCsv(dtls.saveAsFields);
		
		ZTstSaveAsBuildTestData.SaveAsPnlBaseTst tst = new ZTstSaveAsBuildTestData.SaveAsPnlBaseTst(saveAsPnlCsv);
		Arrays.fill(saveAsPnlCsv.getIncludeFields(), false);
		for (int col : cols) {
			Component component = null;
			tst.fieldSelectionTbl.editCellAt(col, 1);
			TableCellEditor cellEditor = tst.fieldSelectionTbl.getCellEditor(col, 1);
			if (cellEditor instanceof DefaultCellEditor 
			&& (component = ((DefaultCellEditor) cellEditor).getComponent()) instanceof JCheckBox ) {
				((JCheckBox) component).setSelected(true);
				cellEditor.stopCellEditing();
			}
			//System.out.println(cellEditor.getClass().getName() + " " + component.getClass().getName());
		}
		
		
		tstSave(";", CommonSaveAsFields.OPT_FILE,  XFileData.filterOnCols(XFileData.dtar020Data(), cols),
				saveAsPnlCsv);
	}

	/**
	 * @param dtls 
	 * @param delim field delimiter
	 * @param data test data
	 * 
	 * @throws IOException Any IOError that occurs
	 */
	private void tstSave(DDetails dtls, String delim, String saveOption, String[][] data)
			throws IOException, RecordException {
		tstSave(delim, saveOption, data, new SaveAsPnlCsv(dtls.saveAsFields));
	}


	/**
	 * @param delim
	 * @param saveOption
	 * @param data
	 * @param saveAsPnlCsv
	 * @throws IOException
	 * @throws RecordException
	 */
	private void tstSave(String delim, String saveOption, String[][] data,
			SaveAsPnlCsv saveAsPnlCsv) throws IOException, RecordException {
		saveAsPnlCsv.delimiterCombo.setText(delim);
		
		ByteArrayOutputStream os = new ByteArrayOutputStream(4000);
		
		saveAsPnlCsv.save(saveOption, os);
		
		checkCsvFile(os.toString(), delim, data);
	}

	private void checkCsvFile(String data, String sep, String[][] expected) throws IOException {
		BufferedReader reader = new BufferedReader(new StringReader(data));  
		int lineNum = 0;
		String line;
		
		while ((line = reader.readLine()) != null) {
			String[] fields = line.split(sep);
			assertArrayEquals("LineNum: " + lineNum, expected[lineNum++], fields);
		}
		reader.close();
	}
}
