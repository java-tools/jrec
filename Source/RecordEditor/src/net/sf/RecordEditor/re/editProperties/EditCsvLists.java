package net.sf.RecordEditor.re.editProperties;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.Action;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.LineProvider;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.RecordEditor.edit.display.LineListReItems;
import net.sf.RecordEditor.edit.util.StandardLayouts;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.common.DefaultLinkedActionHandler;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.lang.ReAbstractAction;
import net.sf.RecordEditor.utils.msg.UtMessages;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.common.CsvTextItem;


/**
 * This class is useed to edit:
 * <ul>
 *  <li>List of Delimiters to display in drop-down menu's & search for
 *  <li>List of Quotes to display in drop-down menu's & search for
 * </ul>
 * 
 * @author bruce
 *
 */
public class EditCsvLists implements ISaveOptions {
	
	public static EditCsvLists newDelimiterEditor(EditParams params) {
		return new EditCsvLists(
						params,
						LangConversion.convertId(
		        				LangConversion.ST_MESSAGE, 
		        				"DelimEditTip", 
		        				csvFieldDescription),
		        		UtMessages.SAVE_DELIM_CHANGES.get("Csv Field Delimiter"),
		                "Save Standard Csv Delimiters",
		                CsvTextItem.DELIMITER,
		                '\t'
				);
	}
	public static EditCsvLists newQuoteEditor(EditParams params) {
		return new EditCsvLists(
						params,
						LangConversion.convertId(
								LangConversion.ST_MESSAGE, 
								"QuoteEditTip", 
								csvQuoteDescription),
						UtMessages.SAVE_DELIM_CHANGES.get("Csv Quote Characters"),
						"Save Standard Csv Quote characters",
		                CsvTextItem.QUOTE,
		                '"');
	}
	
	
	private static String csvFieldDescription
			= "<h3>Edit Csv Delimiters</h3>"
			+ "On this screen you can edit the default csv field "
			+ "delimiters used in this program.<br\\> The 2 columns are:<ul>"
			+ "<li><b>Delimiter</b> the csv field delimiter. It can be a single character,<br\\>"
			+ "a byte (e.g. x'0F') or a unicode character (e.g. \\u00f9)."
			+ "<li><b>Delimiter Name</b> A name to be displayed by this program "
			+ "</ul>";
	
	private static String csvQuoteDescription
			= "<h3>Edit Csv Quotes</h3>"
			+ "On this screen you can edit the default csv Quotes "
			+ "used in this program.<br\\> The 2 columns are:<ul>"
			+ "<li><b>Delimiter</b> the csv field delimiter. It can be a single character,<br\\>"
			+ "a byte (e.g. x'0F') or a unicode character (e.g. \\u00f9)."
			+ "<li><b>Delimiter Name</b> A name to be displayed by this program "
			+ "</ul>";

	//JFrame frame = new JFrame();
	private JEditorPane tips;
	public final BaseHelpPanel panel = new BaseHelpPanel("EditDelimiters");
	final LayoutDetail delimSchema;// = StandardLayouts.getInstance().delimiterLayoutForEditor();
	final FileView view;
	LineListReItems listScreen;
	
	private final String askToSaveMsg, askToSaveTitle;
	private final EditParams params;
	private final CsvTextItem.CsvList listInterface;
	private final char defaultChar;
	
	private EditCsvLists(
			EditParams params, String tip,
			String askToSaveMsg, String askToSaveTitle, CsvTextItem.CsvList listInterface, char defaultChar) {
		this.params = params;
//		this.allowHexValues = allowHexValues;
		this.askToSaveMsg = askToSaveMsg;
		this.askToSaveTitle = askToSaveTitle;
		this.listInterface = listInterface;
		this.delimSchema = StandardLayouts.getInstance().getCsvCharLayoutForEditor(listInterface.name); 
		this.view = loadView(new FileView(delimSchema, LineIOProvider.getInstance(), false));
		this.defaultChar = defaultChar;
		
		init_100_defineFields(tip);
		init_200_layoutScreen();
		init_300_listners();
	}
	
	private void init_100_defineFields(String tip) {
			
		tips = new JEditorPane( "text/html", tip );
	}
	
	private void init_200_layoutScreen() {

		panel.addComponentRE(1, 5, CommonCode.TIP_HEIGHT, BasePanel.GAP1,
		        BasePanel.FULL, BasePanel.FULL,
				tips);

		listScreen = new LineListReItems(
				panel, view, true, 
				new ActionHandler(),
				getActions());
	}
	

	private void init_300_listners() {

	}
	
	private void clearView(FileView view) {
		int num = view.getRowCount() - 1;
		for (int i = num; i >= 0; i--) {
			view.removeLineFromView(i);
		}
	}
	
	@SuppressWarnings("serial")
	protected List<Action> getActions() {
		ReAbstractAction reLoadAction = new ReAbstractAction("Reload") {
			@Override public void actionPerformed(ActionEvent e) {
				clearView(view);
				loadView(view);
			}
		};
		ReAbstractAction resetAction = new ReAbstractAction("Reset") {
			@Override public void actionPerformed(ActionEvent e) {
				clearView(view);
				resetView(view);
			}
		};
		
		reLoadAction.putValue(Action.SHORT_DESCRIPTION, "Reload list from last save");
		resetAction.putValue( Action.SHORT_DESCRIPTION, "Reset list back to RecordEditor/ReCsvEditor Default values");
			
		return Arrays.asList((Action)reLoadAction, resetAction);
	}
	
	
	
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.editProperties.ISaveOptions#isCloseRequired()
	 */
	@Override
	public boolean isCloseRequired() {
		return view.isChanged();
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.editProperties.ISaveOptions#close()
	 */
	@Override
	public void close() {
		if (view.isChanged()) {
			int result = JOptionPane.showConfirmDialog(
					panel,
					askToSaveMsg, 
					askToSaveTitle,
                    JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION) {
            	save();
            }
		}
	}

	@Override
	public void save() {
		if (view.isChanged()) {
			int rowCount = view.getRowCount();
			List<CsvTextItem> csvDelimiterList = new ArrayList<CsvTextItem>(rowCount);
			StringBuilder errors = new StringBuilder();
			
			for (int i = 0; i < rowCount; i++) {
				boolean doAdd = true;
				AbstractLine line = view.getLine(i);
				String val = line.getFieldValue(0, 0).asString();
				String description = line.getFieldValue(0, 1).asString();
				if ("\\t".equals(val)) {
					val = "\t";
				} else if ("' '".equals(val)) {
					val = " ";
				}
				
				if (val == null || val.length() == 0) {
					if ("<none>".equalsIgnoreCase(description)) {
					} else if (description != null && description.length() == 1) {
						val = description;
					} else {
						doAdd = false;
						errors.append("Row " + i + " (" + description +") has no character value\n");
					}
				} else { //	if (allowHexValues) {
					if (val.charAt(0) != 'x' && val.charAt(0) != 'X' && Conversion.decodeChar(val, "", defaultChar).length != 1) {
						errors.append("Row " + i + " Only the first character will be used: \"" + val + "\"\n");
					}
//				} else if ((val.toLowerCase().startsWith("x'"))){
//					doAdd = false;
//					errors.append("Row " + i +"  Rejected: " + val + ", " + description + "; hex values are not allowed\n");
				}
				
				if (doAdd) {
					csvDelimiterList.add(new CsvTextItem(
							val, 
							description));
				}
			}
			if (errors.length() > 0) {
				JOptionPane.showMessageDialog(panel, "The following issues where found:\n\n" + errors);
			}
			
			listInterface.updateList(params.properties, csvDelimiterList);
			params.writeProperties();
	
			view.setChanged(false);
		}
	}

	/**
	 * 
	 */
	protected FileView loadView(FileView delimView) {
		return loadView(delimView, listInterface.getCsvList(true, true));
	}

	protected FileView resetView(FileView delimView) {
		return loadView(delimView, listInterface.getDefaultCsvList(true, true));
	}

	/**
	 * @param delimView delimiter view
	 * @param csvDelimiterList
	 * @return
	 */
	protected final FileView loadView(FileView delimView,
			List<CsvTextItem> csvDelimiterList) {
		@SuppressWarnings("rawtypes")
		LineProvider lineProvider = LineIOProvider.getInstance().getLineProvider(delimSchema);
		
		for (CsvTextItem lineFields : csvDelimiterList) {
			@SuppressWarnings("unchecked")
			AbstractLine line = lineProvider.getLine(delimSchema);
			
			String value = lineFields.value;
			if ("\t".equals(value)) {
				value = "\\t";
			} else if (" ".equals(value)) {
				value = "' '";
			} 
			line.setField(0, 0, value);
			line.setField(0, 1, lineFields.text);
			
			delimView.add(line);
		}
		return delimView;
	}

//	/**
//	 * @return ActionHandler
//	 * @see net.sf.RecordEditor.edit.display.LineListReItems#getActionHandler()
//	 */
//	@Override
//	public ReActionHandler getActionHandler() {
//		return listScreen.getActionHandler();
//	}
	
	private class ActionHandler extends DefaultLinkedActionHandler {

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.utils.common.DefaultLinkedActionHandler#executeAction(int)
		 */
		@Override
		public void executeAction(int action) {
			switch (action) {
			case ReActionHandler.INSERT_RECORDS_POPUP:
			case ReActionHandler.INSERT_RECORDS:		listScreen.insertLine(0, false, false);			break;
			case ReActionHandler.INSERT_RECORD_PRIOR_POPUP:
			case ReActionHandler.INSERT_RECORD_PRIOR:	listScreen.insertLine(-1, false, false);		break;
			case ReActionHandler.SAVE:
				save();
				break;
			default:
				super.parent.executeAction(action);
			}
		}

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.utils.common.DefaultLinkedActionHandler#isActionHandledHere()
		 */
		@Override
		protected boolean isActionHandledHere(int action) {
			boolean ret = false;
			switch (action) {
			case ReActionHandler.SAVE:
			case ReActionHandler.INSERT_RECORDS_POPUP:
			case ReActionHandler.INSERT_RECORDS:		
			case ReActionHandler.INSERT_RECORD_PRIOR_POPUP:
			case ReActionHandler.INSERT_RECORD_PRIOR:	ret = true;									break;
			
			}
			
			return ret;
		}
	}
}
