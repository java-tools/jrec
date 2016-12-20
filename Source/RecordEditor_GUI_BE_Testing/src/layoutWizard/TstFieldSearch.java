package layoutWizard;


import org.junit.Test;

import net.sf.RecordEditor.layoutWizard.ColumnDetails;
import net.sf.RecordEditor.layoutWizard.FileAnalyser;
import zCommon.ZCommonCode;

/**
 * Testing the Field-Search class
 * @author Bruce Martin
 *
 */
public class TstFieldSearch {
	
	private static final ColumnDetails[] AMS_LOCATION_FIELDS = ZCommonCode.AMS_LOCATION_FIELDS;

	
	private static final ColumnDetails[] STORE_FIELDS = {
			newColDtls(1, 2, 35),
			newColDtls(3, 2, 35),
			newColDtls(5, 2, 0),
			newColDtls(7, 48, 0),
			newColDtls(55, 6, 0),
	};
	private static final ColumnDetails[] DTAR020_FIELDS = {
			newColDtls(1, 8, 6),
			newColDtls(9, 2, 31),
			newColDtls(11, 4, 31),
			newColDtls(15, 2, 31),
			newColDtls(17, 5, 31),
			newColDtls(22, 6, 31),
	};

	private static final ColumnDetails[] DTAR107_FIELDS = {
			newColDtls(1, 2, 31),
			newColDtls(3, 4, 31),
			newColDtls(7, 15, 0),
			newColDtls(22, 1, 6),
			newColDtls(23, 5, 31),
			newColDtls(28, 5, 31),
			newColDtls(33, 2, 31),
			newColDtls(35, 3, 31),
			newColDtls(38, 3, 31),
			newColDtls(41, 4, 6),
			newColDtls(45, 4, 31),
			newColDtls(49, 4, 31),
			newColDtls(53, 2, 6),	};

	private static final ColumnDetails[] DTAR119_FIELDS = {
			newColDtls(1, 2, 31),
			newColDtls(3, 4, 31),
			newColDtls(7, 2, 31),
			newColDtls(9, 8, 0),
			newColDtls(17, 8, 6),
			newColDtls(25, 7, 6),
			newColDtls(32, 4, 31),
			newColDtls(36, 5, 31),
			newColDtls(41, 5, 31),
			newColDtls(46, 5, 31),
			newColDtls(51, 3, 31),	};




	@Test
	public void test01() {
		chkFieldSearchStd("Ams_LocDownload_20041228.txt", AMS_LOCATION_FIELDS, "");
	}

	@Test
	public void test02() {
		chkFieldSearchStd("Ams_LocDownload_20041228_Extract2.txt", AMS_LOCATION_FIELDS, "");
	}


	@Test
	public void test03() {
		chkFieldSearchStd("DTAR1000_Store_file_std.bin", STORE_FIELDS, "cp037");
	}

	@Test
	public void test04() {
		chkFieldSearchFw("DTAR020.bin", DTAR020_FIELDS, "cp037");
	}

	@Test
	public void test05() {
		chkFieldSearchFw("DTAR107.bin", DTAR107_FIELDS, "cp037");
	}


	@Test
	public void test06() {
		chkFieldSearchFw("DTAR119_keycode.bin", DTAR119_FIELDS, "cp037");
	}


	@Test
	public void test21() {
		chkFieldSearchStd("Ams_PODownload_Keycode.txt",  ZCommonCode.AMS_PO_KEYCODE_FIELDS, "");
	}


	@Test
	public void test22() {
		chkFieldSearchStd("Ams_PODownload_Header.txt", null, "");
	}

	private void chkFieldSearchStd(String fileName, ColumnDetails[] expected, String charset) {
		byte[] data = ZCommonCode.readDataFile(fileName);
		FileAnalyser fileAnalyser = FileAnalyser.getAnaylserNoLengthCheck(data, charset);
		ZCommonCode.chkFieldSearch(expected, fileAnalyser);
	}


	private void chkFieldSearchFw(String fileName, ColumnDetails[] expected, String charset) {
		byte[] data = ZCommonCode.readDataFile(fileName);
		FileAnalyser fileAnalyser = FileAnalyser.getAnaylser(data, charset);
		ZCommonCode.chkFieldSearch(expected, fileAnalyser);
	}


	
	private static ColumnDetails newColDtls(int colStart, int length, int type) {	
		return ZCommonCode.newColDtls(colStart, length, type);
	}
}
