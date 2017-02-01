package re.util.csv;

import static org.junit.Assert.*;

import java.io.File;

import javax.swing.JTextArea;

import org.junit.Test;

import net.sf.RecordEditor.layoutWizard.ColumnDetails;
import net.sf.RecordEditor.re.util.csv.CsvSelectionPanel;
import net.sf.RecordEditor.re.util.csv.CsvTabPane;
import net.sf.RecordEditor.re.util.csv.FilePreview;
import net.sf.RecordEditor.re.util.fw.FixedWidthSelectionPane;
import xCommon.XCommonCode;

public class TstCsvTabPane {

	public static String[] FW_FILES = XCommonCode.FW_FILES;
	
	public static ColumnDetails[][] FILE_COLUMNS = XCommonCode.FILE_COLUMNS;
	public static final String[] CHARCTER_SETS = XCommonCode.CHARCTER_SETS;

	private static String[] BAR_DTAR020_COLUMN_NAMES = {
			"KEYCODE-NO", "STORE-NO", "DATE", "DEPT-NO", "QTY-SOLD", "SALE-PRICE"
	};
	@Test
	public void testFW1() {
		JTextArea msgField = new JTextArea();
		CsvTabPane tabPane = new CsvTabPane(msgField, true, true);
		
		for (int i = 0; i < FW_FILES.length; i++) {
			String fw = FW_FILES[i];
			String fName = XCommonCode.getFileName(fw);
			
			tabPane.readCheckPreview(new File(fName), true, "");
			
			FilePreview pane = tabPane.getSelectedCsvDetails();
			assertTrue(pane instanceof FixedWidthSelectionPane);
			XCommonCode.checkPane((FixedWidthSelectionPane)pane, FILE_COLUMNS[i], CHARCTER_SETS[i]);
		}
	}
	
	public void testCsv() {
		String fName = XCommonCode.getFileName("barDTAR020.csv");
		JTextArea msgField = new JTextArea();
		CsvTabPane tabPane = new CsvTabPane(msgField, true, true);
		
		tabPane.readCheckPreview(new File(fName), true, "");
		
		FilePreview pane = tabPane.getSelectedCsvDetails();
		assertTrue(pane instanceof CsvSelectionPanel);
		
		assertEquals("|", pane.getSeperator());
		assertEquals("", pane.getFontName());
		
		CsvSelectionPanel csvPane = (CsvSelectionPanel) pane;
		
		assertEquals(BAR_DTAR020_COLUMN_NAMES.length, csvPane.getColumnCount());
		
		for (int i = 0; i < BAR_DTAR020_COLUMN_NAMES.length; i++) {
			assertEquals(BAR_DTAR020_COLUMN_NAMES[i], csvPane.getColumnName(i));
		}
		
	}

}
