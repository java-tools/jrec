package edit.display.saveAs;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.swing.JComboBox;

import net.sf.JRecord.Common.Constants;
import net.sf.RecordEditor.re.util.FileStructureDtls;
import net.sf.RecordEditor.re.util.FileStructureDtls.FileStructureOption;

import org.junit.Test;

import xCommon.DDetailsFS;
import xCommon.XSchemas;



public class TstSaveAsPnlFileStructure01 {

	@Test
	public void testComboOk() throws IOException {	
		tstComboBin(DDetailsFS.getDtar020Details(XSchemas.CharSetType.EBCDIC));
	}
	@Test
	public void testComboRedefOk() throws IOException {	
		tstComboBin(DDetailsFS.getDtar023Details(XSchemas.CharSetType.EBCDIC));
	}

	@Test
	public void testTxtComboOk() throws IOException {
		tstTextSchemaOk(DDetailsFS.getDtar021Details(XSchemas.CharSetType.EBCDIC));
	}

	@Test
	public void testTxtRedefComboOk() throws IOException {
		tstTextSchemaOk(DDetailsFS.getDtar022Details(XSchemas.CharSetType.EBCDIC));
	}

	/**
	 * @param dtls
	 */
	private void tstComboBin(DDetailsFS dtls) {
		FileStructureOption[] comboItems = dtls.saveAsTst.getComboItems();
		JComboBox fileStructureCombo = FileStructureDtls.getFileStructureCombo(FileStructureDtls.FileStructureReqest.BINARY_STRUCTRES);
	
		assertEquals(fileStructureCombo.getItemCount(), comboItems.length);
		
		for (int ii = 0; ii < comboItems.length; ii++) {
			assertTrue(ii + " " + comboItems[ii].string, comboItems[ii] == fileStructureCombo.getItemAt(ii));
		}
	}

	/**
	 * @param dtls
	 */
	private void tstTextSchemaOk(DDetailsFS dtls) {
		FileStructureOption[] comboItems = dtls.saveAsTst.getComboItems();
		JComboBox fileStructureCombo = FileStructureDtls.getFileStructureCombo(FileStructureDtls.FileStructureReqest.ALL);
	
		assertEquals(fileStructureCombo.getItemCount(), comboItems.length);
		
		for (int ii = 0; ii < comboItems.length; ii++) {
			assertTrue(ii + " " + comboItems[ii].string, comboItems[ii] == fileStructureCombo.getItemAt(ii));
		}
	}

	@Test
	public void testFontDisplayedBin() throws IOException {
		int[] structures4Font = {
				 Constants.IO_VB,
				 Constants.IO_VB_FUJITSU,
				 Constants.IO_VB_GNU_COBOL,
				 Constants.IO_BIN_TEXT,
				 Constants.IO_FIXED_LENGTH
		};
		int[] structuresNoFont = {
				Constants.IO_UNICODE_TEXT, Constants.IO_FIXED_LENGTH_CHAR,
				Constants.IO_BIN_CSV
		};
		DDetailsFS dtls = DDetailsFS.getDtar020Details(XSchemas.CharSetType.EBCDIC);
		
		chkFontVisibility(structures4Font, dtls, true);
		
		chkFontVisibility(structuresNoFont, dtls, false);
	}
	
	@Test
	public void testFontDisplayedBinRedef() throws IOException {
		int[] structures4Font = {
		};
		int[] structuresNoFont = {
				 Constants.IO_VB,
				 Constants.IO_VB_FUJITSU,
				 Constants.IO_VB_GNU_COBOL,
				 Constants.IO_UNICODE_TEXT,
				 Constants.IO_FIXED_LENGTH_CHAR,
				 Constants.IO_FIXED_LENGTH,
				 Constants.IO_BIN_CSV,
				 Constants.IO_BIN_TEXT,
		};
		DDetailsFS dtls = DDetailsFS.getDtar023Details(XSchemas.CharSetType.EBCDIC);
		
		chkFontVisibility(structures4Font, dtls, true);
		
		chkFontVisibility(structuresNoFont, dtls, false);
	}


	@Test
	public void testFontDisplayedTxt() throws IOException {
		tstFontDisplayTxt(DDetailsFS.getDtar021Details(XSchemas.CharSetType.EBCDIC));
	}

	@Test
	public void testFontDisplayedTxtRedef() throws IOException {
		tstFontDisplayTxt(DDetailsFS.getDtar022Details(XSchemas.CharSetType.EBCDIC));
	}

	/**
	 * @param dtls
	 */
	private void tstFontDisplayTxt(DDetailsFS dtls) {
		int[] structures4Font = {
				 Constants.IO_VB,
				 Constants.IO_VB_FUJITSU,
				 Constants.IO_VB_GNU_COBOL,
				 Constants.IO_TEXT_LINE,
				 Constants.IO_BIN_TEXT,
				 Constants.IO_FIXED_LENGTH,
				 Constants.IO_UNICODE_TEXT, 
				 Constants.IO_FIXED_LENGTH_CHAR 
		};
		int[] structuresNoFont = { 
				Constants.IO_BIN_CSV
		};
		
		chkFontVisibility(structures4Font, dtls, true);
		
		chkFontVisibility(structuresNoFont, dtls, false);
	}

	/**
	 * @param structures
	 * @param dtls
	 * @param expected
	 */
	private void chkFontVisibility(int[] structures, DDetailsFS dtls,
			boolean expected) {
		FileStructureOption[] comboItems = dtls.saveAsTst.getComboItems();
		for (int fs : structures) {
			dtls.saveAsTst.fontCombo.setVisible(! expected);
			for (int i = 0; i < comboItems.length; i++) {
				if (fs == comboItems[i].index) {
					dtls.saveAsTst.fileStructures.setSelectedIndex(i);
					assertEquals("" + fs, expected, dtls.saveAsTst.fontCombo.isVisible());
					break;
				}
			}
		}

	}
	
	
}
