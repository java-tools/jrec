package net.sf.RecordEditor.re.editProperties;

import java.util.List;

import javax.swing.JButton;

import net.sf.RecordEditor.edit.display.LineListReItems;
import net.sf.RecordEditor.re.file.FileView;

/**
 * This Class will create test versions of EditOption classes
 * These test versions allow access 
 * 
 * @author Bruce Martin
 *
 */

public class ZTstEditProperties {

	
	public static EditCsvListDetails toTestItem(EditCsvLists csvListScreen) {
		return new EditCsvListDetails(csvListScreen);
	}
	
	public static class EditCsvListDetails {
		public final EditCsvLists csvListScreen;
		public final LineListReItems listEditPnl;
		public final List<JButton> buttons;
		public final FileView view;
		
		public EditCsvListDetails(EditCsvLists csvListScreen) {
			this.csvListScreen = csvListScreen;
			this.listEditPnl = csvListScreen.listScreen;
			this.buttons = listEditPnl.buttons;
			this.view = csvListScreen.view;
		}
	}
}
