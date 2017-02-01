package xCommon;

import java.util.List;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.swing.common.CsvTextItem;

import junit.framework.TestCase;

public class XCsvTextItem {

	
	public static void clearParams(String id, int count) {
		Parameters.setProperty(id + ".count", null);
		for (int i = 0; i < count; i++) {
			Parameters.setProperty(id + "." + i, null);
			Parameters.setProperty(id + "text." + i, null);
		}
	}

	/**
	 * @param csvDelimiterList
	 * @param length
	 */
	public static void checkList(List<CsvTextItem> csvDelimiterList, int length, int diff) {

		checkList(csvDelimiterList, Common.FIELD_SEPARATOR_LIST_VALUES, Common.FIELD_SEPARATOR_LIST, length, diff);
	}
	

	public static void checkList(List<CsvTextItem> csvDelimiterList, String[] values, String[] text, int length, int diff) {
		for (int i = diff; i < length; i++) {
			String id = i + " ~ " + text[i];
			TestCase.assertEquals(id, values[i], csvDelimiterList.get(i-diff).value);
			TestCase.assertEquals(id, text[i], csvDelimiterList.get(i-diff).text);
			TestCase.assertEquals(! text[i].startsWith("x'"), csvDelimiterList.get(i-diff).isText);
			TestCase.assertEquals("<default>".equalsIgnoreCase(text[i]), csvDelimiterList.get(i-diff).isDefault);
		}
		
		TestCase.assertEquals(length - diff, csvDelimiterList.size());
	}


	public static void checkList(List<CsvTextItem> expectedList,  List<CsvTextItem> csvDelimiterList) {
		int length = Math.min(expectedList.size(), csvDelimiterList.size());
		for (int i = 0; i < length; i++) {
			CsvTextItem delim = csvDelimiterList.get(i);
			CsvTextItem expected = expectedList.get(i);
			String id = i + " ~ " + expected.text;
			String expectedText = expected.getText();

			TestCase.assertEquals(id, expected.value, delim.value);
			TestCase.assertEquals(id, expectedText, delim.text);
			TestCase.assertEquals(! expectedText.startsWith("x'"), delim.isText);
			TestCase.assertEquals("<default>".equalsIgnoreCase(expectedText), delim.isDefault);
		}
		
		TestCase.assertEquals(expectedList.size(), csvDelimiterList.size());
	}

}
