package re.editProperties;

//import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.ListSelectionModel;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.re.editProperties.EditCsvLists;
import net.sf.RecordEditor.re.editProperties.EditParams;
import net.sf.RecordEditor.re.editProperties.ZTstEditProperties;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.swing.common.CsvTextItem;

import org.junit.Test;

import xCommon.XCsvTextItem;

public class TstEditCsvLists {
	
	public static final int VALUE_COLUMN = 2;
	public static final int TEXT_COLUMN = 3;
	public static final int DELETE = 8;
	
//	public static final int SAVE_BUTTON_IDX

	@Test
	public void testDelim01() {
		Parameters.setSavePropertyChanges(false);
		XCsvTextItem.clearParams(Parameters.CSV_DELIMITER_CHARS, CsvTextItem.FIELD_SEPARATOR_LIST_VALUES.length);
		List<CsvTextItem> defaultCsvDelimiterList = CsvTextItem.DELIMITER.getDefaultCsvList(true, true);

		ZTstEditProperties.EditCsvListDetails tstDtls = ZTstEditProperties.toTestItem(EditCsvLists.newDelimiterEditor(new EditParams()));
		JFrame frame = new JFrame();
		frame.getContentPane().add(tstDtls.csvListScreen.panel);
		tstDtls.view.setFrame(frame);
		
		XCsvTextItem.checkList(defaultCsvDelimiterList, viewToList(tstDtls.view));

		doUpdate(tstDtls.view, defaultCsvDelimiterList, 2, VALUE_COLUMN, "#");
		doUpdate(tstDtls.view, defaultCsvDelimiterList, 2, TEXT_COLUMN, "Hash");

		doUpdate(tstDtls.view, defaultCsvDelimiterList, 4, VALUE_COLUMN, "+");
		doUpdate(tstDtls.view, defaultCsvDelimiterList, 5, TEXT_COLUMN, "****");

		doUpdate(tstDtls.view, defaultCsvDelimiterList, 12, DELETE, "****");
		doUpdate(tstDtls.view, defaultCsvDelimiterList, 12, DELETE, "****");
		
		XCsvTextItem.checkList(defaultCsvDelimiterList, viewToList(tstDtls.view));
		btnSearch(tstDtls, "Save").doClick();
		XCsvTextItem.checkList(defaultCsvDelimiterList, CsvTextItem.DELIMITER.getCsvList(true, true));
		
		btnSearch(tstDtls, "Reset").doClick();
		
		defaultCsvDelimiterList = CsvTextItem.DELIMITER.getDefaultCsvList(true, true);
		XCsvTextItem.checkList(defaultCsvDelimiterList, viewToList(tstDtls.view));

		btnSearch(tstDtls, "Save").doClick();
		XCsvTextItem.checkList(defaultCsvDelimiterList, CsvTextItem.DELIMITER.getCsvList(true, true));
		
		XCsvTextItem.clearParams(Parameters.CSV_DELIMITER_CHARS, CsvTextItem.FIELD_SEPARATOR_LIST_VALUES.length);
		Parameters.setSavePropertyChanges(true);

	}

	@Test
	public void testDelim02() {
		Parameters.setSavePropertyChanges(false);
		XCsvTextItem.clearParams(Parameters.CSV_DELIMITER_CHARS, CsvTextItem.FIELD_SEPARATOR_LIST_VALUES.length);
		List<CsvTextItem> defaultCsvDelimiterList = CsvTextItem.DELIMITER.getDefaultCsvList(true, true);

		ZTstEditProperties.EditCsvListDetails tstDtls = ZTstEditProperties.toTestItem(EditCsvLists.newDelimiterEditor(new EditParams()));
		JFrame frame = new JFrame();
		frame.getContentPane().add(tstDtls.csvListScreen.panel);
		tstDtls.view.setFrame(frame);
		
		XCsvTextItem.checkList(defaultCsvDelimiterList, viewToList(tstDtls.view));

		doUpdate(tstDtls.view, defaultCsvDelimiterList, 2, VALUE_COLUMN, "#");
		doUpdate(tstDtls.view, defaultCsvDelimiterList, 2, TEXT_COLUMN, "Hash");

		doUpdate(tstDtls.view, defaultCsvDelimiterList, 4, VALUE_COLUMN, "+");
		doUpdate(tstDtls.view, defaultCsvDelimiterList, 5, TEXT_COLUMN, "****");

		doUpdate(tstDtls.view, defaultCsvDelimiterList, 12, DELETE, "****");
		doUpdate(tstDtls.view, defaultCsvDelimiterList, 12, DELETE, "****");
		
		XCsvTextItem.checkList(defaultCsvDelimiterList, viewToList(tstDtls.view));
		
		btnSearch(tstDtls, "Reload").doClick();
		
		
		defaultCsvDelimiterList = CsvTextItem.DELIMITER.getDefaultCsvList(true, true);
		XCsvTextItem.checkList(defaultCsvDelimiterList, viewToList(tstDtls.view));

		btnSearch(tstDtls, "Save").doClick();
		XCsvTextItem.checkList(defaultCsvDelimiterList, CsvTextItem.DELIMITER.getCsvList(true, true));
		
		XCsvTextItem.clearParams(Parameters.CSV_DELIMITER_CHARS, CsvTextItem.FIELD_SEPARATOR_LIST_VALUES.length);
		
		Parameters.setSavePropertyChanges(true);
	}
	

	@Test
	public void testDelim03() {

		Parameters.setSavePropertyChanges(false);
		XCsvTextItem.clearParams(Parameters.CSV_DELIMITER_CHARS, CsvTextItem.FIELD_SEPARATOR_LIST_VALUES.length);
		List<CsvTextItem> defaultCsvDelimiterList = CsvTextItem.DELIMITER.getDefaultCsvList(true, true);

		ZTstEditProperties.EditCsvListDetails tstDtls = ZTstEditProperties.toTestItem(EditCsvLists.newDelimiterEditor(new EditParams()));
		JFrame frame = new JFrame();
		frame.getContentPane().add(tstDtls.csvListScreen.panel);
		tstDtls.view.setFrame(frame);
		
		ListSelectionModel selectionModel = tstDtls.listEditPnl.getJTable().getSelectionModel();
		
		selectionModel.setSelectionInterval(12, 14);
		btnSearch(tstDtls, "Delete").doClick();
		defaultCsvDelimiterList.remove(14);
		defaultCsvDelimiterList.remove(13);
		defaultCsvDelimiterList.remove(12);
		XCsvTextItem.checkList(defaultCsvDelimiterList, viewToList(tstDtls.view));
		
		selectionModel.clearSelection();
		selectionModel.setSelectionInterval(4, 4);
		btnSearch(tstDtls, "New Record").doClick();
		defaultCsvDelimiterList.add(5, new CsvTextItem("", ""));
		XCsvTextItem.checkList(defaultCsvDelimiterList, viewToList(tstDtls.view));
		
	
		XCsvTextItem.clearParams(Parameters.CSV_DELIMITER_CHARS, CsvTextItem.FIELD_SEPARATOR_LIST_VALUES.length);
		Parameters.setSavePropertyChanges(true);
	
	}
	
	@Test
	public void testQuote02() {
		Parameters.setSavePropertyChanges(false);
		XCsvTextItem.clearParams(Parameters.CSV_QUOTE_CHARS, CsvTextItem.CSV_QUOTE_VALUES.length);
		List<CsvTextItem> defaultCsvQuoteList = CsvTextItem.QUOTE.getDefaultCsvList(true, true );

		ZTstEditProperties.EditCsvListDetails tstDtls = ZTstEditProperties.toTestItem(EditCsvLists.newQuoteEditor(new EditParams()));
		JFrame frame = new JFrame();
		ListSelectionModel selectionModel = tstDtls.listEditPnl.getJTable().getSelectionModel();
		frame.getContentPane().add(tstDtls.csvListScreen.panel);
		tstDtls.view.setFrame(frame);
		
		XCsvTextItem.checkList(defaultCsvQuoteList, viewToList(tstDtls.view));

		doUpdate(tstDtls.view, defaultCsvQuoteList, 2, VALUE_COLUMN, "#");
		doUpdate(tstDtls.view, defaultCsvQuoteList, 2, TEXT_COLUMN, "Hash");

		doUpdate(tstDtls.view, defaultCsvQuoteList, 3, VALUE_COLUMN, "+");
		doUpdate(tstDtls.view, defaultCsvQuoteList, 4, TEXT_COLUMN, "****");

		selectionModel.clearSelection();
		selectionModel.setSelectionInterval(4, 4);
		btnSearch(tstDtls, "New Record").doClick();
		defaultCsvQuoteList.add(5, new CsvTextItem("", ""));


		
		XCsvTextItem.checkList(defaultCsvQuoteList, viewToList(tstDtls.view));
		
		btnSearch(tstDtls, "Reload").doClick();
		
		defaultCsvQuoteList = CsvTextItem.QUOTE.getDefaultCsvList(true, true );
		XCsvTextItem.checkList(defaultCsvQuoteList, viewToList(tstDtls.view));
		
		XCsvTextItem.clearParams(Parameters.CSV_DELIMITER_CHARS, CsvTextItem.FIELD_SEPARATOR_LIST_VALUES.length);
		Parameters.setSavePropertyChanges(true);

	}

	
	@Test
	public void testQuote03() {
		try {		Thread.sleep(150);		} catch (InterruptedException e) { }

		Parameters.setSavePropertyChanges(false);
		XCsvTextItem.clearParams(Parameters.CSV_QUOTE_CHARS, CsvTextItem.CSV_QUOTE_VALUES.length);
		
		List<CsvTextItem> defaultCsvQuoteList = CsvTextItem.QUOTE.getDefaultCsvList(true, true );

		ZTstEditProperties.EditCsvListDetails tstDtls = ZTstEditProperties.toTestItem(EditCsvLists.newQuoteEditor(new EditParams()));
		JFrame frame = new JFrame();
		ListSelectionModel selectionModel = tstDtls.listEditPnl.getJTable().getSelectionModel();
		frame.getContentPane().add(tstDtls.csvListScreen.panel);
		tstDtls.view.setFrame(frame);
		
		XCsvTextItem.checkList(defaultCsvQuoteList, viewToList(tstDtls.view));


		selectionModel.clearSelection();
		selectionModel.setSelectionInterval(2, 2);
		btnSearch(tstDtls, "Cut").doClick();
		try {		Thread.sleep(150);		} catch (InterruptedException e) { }

		selectionModel.setSelectionInterval(3, 3);
		btnSearch(tstDtls, "Paste").doClick();
		
		try {		Thread.sleep(150);		} catch (InterruptedException e) { }
		
		CsvTextItem itm = defaultCsvQuoteList.remove(2);
		defaultCsvQuoteList.add(itm);
		
		XCsvTextItem.checkList(defaultCsvQuoteList, viewToList(tstDtls.view));
		
		btnSearch(tstDtls, "Paste Prior").doClick();
		defaultCsvQuoteList.add(3, itm);
		
		
		XCsvTextItem.checkList(defaultCsvQuoteList, viewToList(tstDtls.view));

		
		XCsvTextItem.clearParams(Parameters.CSV_DELIMITER_CHARS, CsvTextItem.FIELD_SEPARATOR_LIST_VALUES.length);
		Parameters.setSavePropertyChanges(true);

	}


	@Test
	public void testQuote01() {
		Parameters.setSavePropertyChanges(false);
		XCsvTextItem.clearParams(Parameters.CSV_QUOTE_CHARS, CsvTextItem.CSV_QUOTE_VALUES.length);
		List<CsvTextItem> defaultCsvQuoteList = CsvTextItem.QUOTE.getDefaultCsvList(true, true );

		ZTstEditProperties.EditCsvListDetails tstDtls = ZTstEditProperties.toTestItem(EditCsvLists.newQuoteEditor(new EditParams()));
		JFrame frame = new JFrame();
		ListSelectionModel selectionModel = tstDtls.listEditPnl.getJTable().getSelectionModel();
		frame.getContentPane().add(tstDtls.csvListScreen.panel);
		tstDtls.view.setFrame(frame);
		
		XCsvTextItem.checkList(defaultCsvQuoteList, viewToList(tstDtls.view));

		doUpdate(tstDtls.view, defaultCsvQuoteList, 2, VALUE_COLUMN, "#");
		doUpdate(tstDtls.view, defaultCsvQuoteList, 2, TEXT_COLUMN, "Hash");

		doUpdate(tstDtls.view, defaultCsvQuoteList, 3, VALUE_COLUMN, "+");
		doUpdate(tstDtls.view, defaultCsvQuoteList, 4, TEXT_COLUMN, "****");

		selectionModel.clearSelection();
		selectionModel.setSelectionInterval(4, 4);
		btnSearch(tstDtls, "New Record").doClick();
		defaultCsvQuoteList.add(5, new CsvTextItem("", ""));


		
		XCsvTextItem.checkList(defaultCsvQuoteList, viewToList(tstDtls.view));
		btnSearch(tstDtls, "Save").doClick();
		XCsvTextItem.checkList(defaultCsvQuoteList, CsvTextItem.QUOTE.getCsvList(true, true));
		
		btnSearch(tstDtls, "Reset").doClick();
		
		defaultCsvQuoteList = CsvTextItem.QUOTE.getDefaultCsvList(true, true );
		XCsvTextItem.checkList(defaultCsvQuoteList, viewToList(tstDtls.view));

		btnSearch(tstDtls, "Save").doClick();
		XCsvTextItem.checkList(defaultCsvQuoteList, CsvTextItem.QUOTE.getDefaultCsvList(true, true ));
		
		XCsvTextItem.clearParams(Parameters.CSV_DELIMITER_CHARS, CsvTextItem.FIELD_SEPARATOR_LIST_VALUES.length);
		Parameters.setSavePropertyChanges(true);

	}

	private void doUpdate(FileView view, List<CsvTextItem> list, int row, int col, String value) {
		CsvTextItem item = list.get(row);
		
		switch (col) {
		case VALUE_COLUMN:
			view.setValueAt(value, row, col);
			//view.setValueAt(null, 0, row, col, value);
			list.set(row, new CsvTextItem(value, item.text));
			break;
		case TEXT_COLUMN:
			view.setValueAt(value, row, col);
			//view.setValueAt(null, 0, row, col, value);
			list.set(row, new CsvTextItem( item.value, value));
			break;
		case DELETE:
			view.setValueAt("d", row, 1);
			list.remove(row);
		};
	}
	
	private JButton btnSearch(ZTstEditProperties.EditCsvListDetails tstDtls, String searchFor) {
		for (JButton b : tstDtls.buttons) {
			System.out.println(b.getText() + " > " + b.getToolTipText());
			if (b.getToolTipText().indexOf(searchFor) >= 0) {
				return b;
			}
		}
		throw new RuntimeException("Button not found: " + searchFor);
	}

	private static List<CsvTextItem> viewToList(FileView view) {
		List<CsvTextItem> list = new ArrayList<CsvTextItem>(view.getRowCount());
		
		for (AbstractLine l : view) {
			String value = l.getFieldValue(0, 0).asString();
			if ("\\t".equals(value)) {
				value = "\t";
			} else if ("' '".equals(value)) {
				value = " ";
			}
	
			list.add(new CsvTextItem(value, l.getFieldValue(0, 1).asString()));
		}
		
		return list;
	}
}
