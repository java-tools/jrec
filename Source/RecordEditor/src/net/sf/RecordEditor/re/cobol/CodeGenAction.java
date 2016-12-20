package net.sf.RecordEditor.re.cobol;

import java.awt.event.ActionEvent;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.Options;
import net.sf.RecordEditor.re.display.AbstractFileDisplay;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.ReSpecificScreenAction;
import net.sf.RecordEditor.utils.msg.UtMessages;
import net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction;

@SuppressWarnings("serial")
public class CodeGenAction extends ReSpecificScreenAction implements AbstractActiveScreenAction {

	public static final int ST_TEXT_CSV   = 1;
	public static final int ST_CSV        = 2;
	public static final int ST_TEXT_FIXED = 3;
	public static final int ST_FIXED      = 4;
	public static final int ST_TEXT_CSV_FIXED = 5;
	public static final int ST_ANY        = 6;
	
	private static final String[] FILE_TYPES = {
		"",
		"Text CSV",
		"Csv",
		"Fixed Width Text",
		"Fixed Width",
		"Text File",
		"Any",
	};
	
	
	private static String TEMPLATE_DIRECTORY = "/net/sf/RecordEditor/cg/velocity/";
	
	private final int schemaType;
	private final String template;
	private final boolean multiRecord;
	private JDialog dialog;
	private Object lastSchema;
	private final boolean onlyText, onlyCsv;
	
	public CodeGenAction(String name, String template, boolean multiRecord, int schemaType) {
		super(name);
		
		this.template = template;
		this.multiRecord = multiRecord;
		this.schemaType = schemaType;
		onlyText = schemaType == ST_TEXT_CSV || schemaType == ST_TEXT_FIXED 
				|| schemaType == ST_TEXT_CSV_FIXED;
		onlyCsv  = schemaType == ST_TEXT_CSV || schemaType == ST_CSV;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		net.sf.JRecord.Details.IGetSchema schemaSource = getDisplay(net.sf.JRecord.Details.IGetSchema.class);
		AbstractLayoutDetails schema = schemaSource==null? null :schemaSource.getSchema();
		if (schema == null ||  ! (schema instanceof net.sf.JRecord.Details.LayoutDetail)) {
			Common.logMsg(UtMessages.CAN_NOT_RETRIEVE_SCHEMA_MSG.get(), null);
			return;
		}

		if ( ! schemaOk4Action(schema)) {
			String fileTypeStr = FILE_TYPES[schemaType];
			if (! multiRecord) {
				fileTypeStr += " (Single Record)";
			}
			//Common.logMsg(message, null);
			JOptionPane.showMessageDialog(null, UtMessages.INVALID_FILE_TYPE_MSG.get(fileTypeStr));
			return;
		}
		
		if (dialog == null || schema != lastSchema) {
			String ds = "";
			AbstractFileDisplay ad = getDisplay(AbstractFileDisplay.class);
			if (ad != null && ad.getFileView() != null) {
				ds = ad.getFileView().getFileName();
			}
			
			dialog = (new  net.sf.RecordEditor.re.cobol.CodeGenOptions(
					(net.sf.JRecord.Details.LayoutDetail)schema, 
					template, 
					TEMPLATE_DIRECTORY,
					ds)).dialog;
			lastSchema = schema;
		}
		dialog.setVisible(true);
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction#checkActionEnabled()
	 */
	@Override
	public void checkActionEnabled() {
		net.sf.JRecord.Details.IGetSchema schemaSource = getDisplay(net.sf.JRecord.Details.IGetSchema.class);

		boolean skipSchemaChecks = false;
		boolean ok = schemaSource != null;
		skipSchemaChecks = ok && !schemaSource.schemaAvailable4checking();
		
		//if (schemaType == ST_TEXT_FIXED) System.out.println("**Fixed  > " + ok + " " + skipSchemaChecks);
		setEnabled(ok && (skipSchemaChecks || schemaOk4Action(schemaSource.getSchema())));
	}
	
	private boolean schemaOk4Action(AbstractLayoutDetails schema) {
//		net.sf.JRecord.Details.IGetSchema schemaSource = getDisplay(net.sf.JRecord.Details.IGetSchema.class);
//		AbstractLayoutDetails schema = null;
//		if (schemaType == ST_TEXT_FIXED)
//			System.out.println("**Fixed->> " + onlyCsv + " " + (schema.getOption(Options.OPT_IS_CSV)) 
//					+ " " + (schema.getOption(Options.OPT_IS_CSV) == Options.YES));
		boolean ok = true;
		if (schema == null || (schema.isBinary() && onlyText)){
			ok = false;
		} else if (schema.getOption(Options.OPT_IS_CSV) == Options.YES) {
			ok = onlyCsv || schemaType == ST_TEXT_CSV_FIXED || schemaType == ST_ANY;
		} else {
			ok = ! onlyCsv;
		}
		
		return (ok && (multiRecord || schema == null || schema.getRecordCount() == 1));
	}
	
	
}
