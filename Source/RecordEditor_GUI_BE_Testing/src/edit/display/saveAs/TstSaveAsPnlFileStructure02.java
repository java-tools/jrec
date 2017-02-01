package edit.display.saveAs;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.base.ExternalConversion;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.RecordEditor.edit.display.SaveAs.CommonSaveAsFields;
import net.sf.RecordEditor.re.util.FileStructureDtls.FileStructureOption;

import org.junit.Test;

import xCommon.DDetailsFS;
import xCommon.XFileData;
import xCommon.XSchemas;


/**
 * Test SaveAsPnlFileStructure - test writing the file
 * 
 * @author Bruce Martin
 *
 */
public class TstSaveAsPnlFileStructure02 {

	public static final int[] BIN_FILE_STRUCTURES = {
			 Constants.IO_VB,
			 Constants.IO_VB_FUJITSU,
			 Constants.IO_VB_GNU_COBOL,
			 Constants.IO_FIXED_LENGTH
	};

	public static final int[] TXT_FILE_STRUCTURES = {
			 Constants.IO_VB,
			 Constants.IO_VB_FUJITSU,
			 Constants.IO_VB_GNU_COBOL,
			 Constants.IO_FIXED_LENGTH,
			 Constants.IO_FIXED_LENGTH_CHAR,
			 Constants.IO_BIN_TEXT,
			 Constants.IO_UNICODE_TEXT
	};

	public static final int[] TXT_FILE_STRUCTURES_NO_FL = {
			 Constants.IO_VB,
			 Constants.IO_VB_FUJITSU,
			 Constants.IO_VB_GNU_COBOL,
			 Constants.IO_FIXED_LENGTH_CHAR,
			 Constants.IO_UNICODE_TEXT
	};

	@Test
	public void testWriteFileBin() throws Exception {
		
		DDetailsFS dtls = DDetailsFS.getDtar020Details(XSchemas.CharSetType.EBCDIC);

		tstWriteFile(BIN_FILE_STRUCTURES, dtls, XFileData.dtar020Data());
	}
	
	// Test Japanese Shift_JIS file
	@Test
	public void testWriteFileBin01() throws Exception {
		tstWriteFile(BIN_FILE_STRUCTURES, DDetailsFS.getCase2Details(), XFileData.case2Data());
	}


	@Test
	public void testWriteFileTxt() throws Exception {
		
		DDetailsFS dtls = DDetailsFS.getDtar021Details(XSchemas.CharSetType.EBCDIC);

		tstWriteFile(TXT_FILE_STRUCTURES, dtls, XFileData.dtar020Data());
	}
	
	// Test Japanese Shift_JIS file
	@Test
	public void testWriteFileTxt01() throws Exception {
		tstWriteFile(TXT_FILE_STRUCTURES_NO_FL, DDetailsFS.getCase1Details(), XFileData.case1Data());
	}


	@Test
	public void testWriteFileTxtRedef() throws Exception {
		
		DDetailsFS dtls = DDetailsFS.getDtar022Details(XSchemas.CharSetType.EBCDIC);

		tstWriteFile(TXT_FILE_STRUCTURES, dtls, XFileData.dtar020Data());
	}
	
	
	private void tstWriteFile(int[] fileStructures, DDetailsFS dtls, String[][] data) throws Exception {
		for (int ii = 0; ii < fileStructures.length; ii++) {
			tstWriteFile(dtls, fileStructures[ii], data);
		}
		for (int ii = 0; ii < fileStructures.length; ii++) {
			tstWriteFileAltCharset(dtls, fileStructures[ii], data);
		}
	}

	
	private void tstWriteFile(DDetailsFS dtls, int fileStructure, String[][] data) throws Exception {

		FileStructureOption[] comboItems = dtls.saveAsTst.getComboItems();
		
		for (int i = 0; i < comboItems.length; i++) {
			if (fileStructure == comboItems[i].index) {
				ByteArrayOutputStream os = new ByteArrayOutputStream(4000);
				dtls.saveAsTst.fileStructures.setSelectedIndex(i);
				
				dtls.saveAsTst.save(CommonSaveAsFields.OPT_FILE, os);
				
				//System.out.println(os.toString(dtls.type.charset));
				checkFile(new ByteArrayInputStream(os.toByteArray()), fileStructure, dtls.getLayout(fileStructure), data);
				return;
			}
		}
		throw new RuntimeException("File structure not found: " + fileStructure);
	}

	
	private void tstWriteFileAltCharset(DDetailsFS dtls, int fileStructure, String[][] data) throws Exception {

		FileStructureOption[] comboItems = dtls.saveAsTst.getComboItems();
		
		for (int i = 0; i < comboItems.length; i++) {
			if (fileStructure == comboItems[i].index) {
				ByteArrayOutputStream os = new ByteArrayOutputStream(4000);
				dtls.saveAsTst.fileStructures.setSelectedIndex(i);
				dtls.saveAsTst.fontCombo.setText(XSchemas.otherCharset(dtls.type).charset);
				
				dtls.saveAsTst.save(CommonSaveAsFields.OPT_FILE, os);
				
				checkFile(new ByteArrayInputStream(os.toByteArray()), fileStructure, dtls.getLayoutChgFont(fileStructure), data);
				return;
			}
		}
		throw new RuntimeException("File structure not found: " + fileStructure);
	}


	/**
	 * @param is
	 * @param fileStructure
	 * @param schema
	 * @throws IOException
	 * @throws RecordException
	 */
	private void checkFile(
			InputStream is, int fileStructure,
			LayoutDetail schema, String[][] data) throws IOException {
		AbstractLineReader lineReader = LineIOProvider.getInstance().getLineReader(schema);
		AbstractLine line;
		//String[][] dtar020Data = XFileData.dtar020Data();
		int lineNum = 0;
		
		lineReader.open(is, schema); 
		while ((line = lineReader.read()) != null) {
			for (int col = 0; col < data[lineNum].length; col++) {
				assertEquals(ExternalConversion.getFileStructureAsString(0, fileStructure) + ": " + lineNum + ", " + col, 
						data[lineNum][col], line.getFieldValue(0, col).asString());
			}
			lineNum += 1;
		}
		lineReader.close();
	}
	
}
