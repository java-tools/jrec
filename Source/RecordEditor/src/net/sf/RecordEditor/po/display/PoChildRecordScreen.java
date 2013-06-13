package net.sf.RecordEditor.po.display;

import java.awt.Dimension;

import javax.swing.JEditorPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.edit.display.BaseDisplay;
import net.sf.RecordEditor.edit.display.extension.IChildScreen;
import net.sf.RecordEditor.edit.display.extension.PaneDtls;
import net.sf.RecordEditor.edit.display.extension.SplitPaneRecord;
import net.sf.RecordEditor.edit.display.util.LinePosition;
import net.sf.RecordEditor.po.def.PoField;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.array.ArrayRender;


public class PoChildRecordScreen extends BaseDisplay implements IChildScreen {

	private static final String COMMENT_STR = "Comment";
	private static final String MSGCTXT_STR = "msgctxt";
	private static final String MSGID_STR 	= "msgid";
	private static final String MSGSTR_STR  = "msgstr";
	private static final String HTML_STR    = "Html";

	private static final String MSGSTR_PLURAL_STR = "msgstr plural";
	private static final String MSGID_PLURAL_STR = "msgid plural";


	private final static int CODE_MSG_CTX = 1;
	private final static int CODE_PLURAL  = 2;
	private final static int CODE_COMMENT = 4;
	private final static int CODE_HTML    = 8;
	private final static int CODE_MSGSTR  = 16;

	private final static double[] STD_PNL_WEIGHT = {0.3, 0.3, 0.3};

//	private final static int CODE_MSG_ID  = 51;
//	private final static int CODE_MSG_STR = 52;

	private String[] STR_TRANS = {
		MSGCTXT_STR,	HTML_STR,	COMMENT_STR, 	MSGID_PLURAL_STR,	MSGSTR_PLURAL_STR, 	MSGSTR_STR,
	};
	private int[] CODE_TRANS = {
		CODE_MSG_CTX,	CODE_HTML,	CODE_COMMENT,	CODE_PLURAL,		CODE_PLURAL,		CODE_MSGSTR,
	};


	private final PaneDtls[] optionalFields = new PaneDtls[STR_TRANS.length];

	private int activeFields = 0;



	//protected final JXMultiSplitPane splitPane = new JXMultiSplitPane();

	protected SplitPaneRecord splitRecPane;

	public static PoChildRecordScreen newRightHandScreen(
			JTable parentTbl, FileView viewOfFile, int lineNo) {

		ArrayRender msgstrPlural = new ArrayRender(true, false);
		msgstrPlural.setTableDetails(parentTbl, lineNo, PoField.msgstrPlural.fieldIdx, "",
				viewOfFile.getBaseFile().getFileNameNoDirectory(), viewOfFile.getBaseFile());
		PaneDtls[] fields = {
				new PaneDtls(COMMENT_STR, 		PoField.comments, 	  new JTextArea(), 0.15),
				new PaneDtls(MSGCTXT_STR, 		PoField.msgctxt,  	  new JTextArea()),
				new PaneDtls(MSGID_STR,   		PoField.msgid,	      new JTextArea(), 0.15),
				new PaneDtls(MSGSTR_STR,  		PoField.msgstr,   	  new JTextArea(), 0.15),
				new PaneDtls(MSGID_PLURAL_STR,  PoField.msgidPlural,  new JTextArea(), 0.15),
				new PaneDtls(MSGSTR_PLURAL_STR, PoField.msgstrPlural, msgstrPlural, 0, false,    0.15),
				new PaneDtls(HTML_STR,			PoField.msgstr,   	  new JEditorPane("text/html",  ""), 0, true, 0.15),
		};

		return new PoChildRecordScreen(viewOfFile, lineNo, fields, null);
	}

	public static PoChildRecordScreen newBottomScreen(
			JTable parentTbl, FileView viewOfFile, int lineNo) {

		ArrayRender msgstrPlural = new ArrayRender(true, false);
		msgstrPlural.setTableDetails(parentTbl, lineNo, PoField.msgstrPlural.fieldIdx, "",
				viewOfFile.getBaseFile().getFileNameNoDirectory(), viewOfFile.getBaseFile());
		PaneDtls[] fields = {
				new PaneDtls(COMMENT_STR, PoField.comments, new JTextArea(), 0),
				new PaneDtls(MSGCTXT_STR, PoField.msgctxt,  new JTextArea(), 1),
				new PaneDtls(MSGID_STR,   PoField.msgid,    new JTextArea(), 1),
				new PaneDtls(MSGSTR_STR,  PoField.msgstr,   new JTextArea(), 1),
				new PaneDtls(MSGID_PLURAL_STR,  PoField.msgidPlural,  new JTextArea(), 1),
				new PaneDtls(MSGSTR_PLURAL_STR, PoField.msgstrPlural, msgstrPlural, 2, false,    0.15),
				new PaneDtls(HTML_STR,    PoField.msgstr,   new JEditorPane("text/html",  ""), 3, true),
		};

		return new PoChildRecordScreen(viewOfFile, lineNo, fields, STD_PNL_WEIGHT);
	}



	public PoChildRecordScreen(FileView viewOfFile, int lineNo,
			PaneDtls[] newFields, double[] newWeight) {
		super("Single PO Record", viewOfFile, false, false, false, false, false,
				NO_LAYOUT_LINE);


		setJTable(new JTable());
		splitRecPane = new SplitPaneRecord(viewOfFile, lineNo);
		splitRecPane.setFields(newFields, newWeight);

		init_100_Init(newFields);
		init_200_layoutScreen();

	}

	private void init_100_Init(PaneDtls[] newFields) {
		for (PaneDtls p : newFields) {
			for (int i = 0; i < STR_TRANS.length; i++) {
				if (p.name == STR_TRANS[i]) {
					optionalFields[i] = p;
				}
			}
		}
	}

	private void init_200_layoutScreen() {

		layoutFieldPane(getFieldsUsed());
		splitRecPane.layoutFieldPane();

		actualPnl.addComponent(1, 3, BasePanel.FILL, BasePanel.GAP,
                BasePanel.FULL, BasePanel.FULL, splitRecPane.splitPane);

		actualPnl.done();
		int minWidth = java.lang.Math.min(this.screenSize.width * 3 / 20, 30 * SwingUtils.CHAR_FIELD_WIDTH );
		actualPnl.setPreferredSize(
				new Dimension(
						Math.max(minWidth, actualPnl.getPreferredSize().width),
								 actualPnl.getPreferredSize().height));
	}


	@Override
	public void setScreenSize(boolean mainframe) {

		this.actualPnl.done();
	}


	@Override
	protected int getInsertAfterPosition() {
		return getStandardPosition();
	}

	/**
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#getInsertAfterLine()
	 */
	@Override
	protected LinePosition getInsertAfterLine(boolean prev) {
		return super.getInsertAfterLine(splitRecPane.getCurrRow(), prev);
//		int currRow = splitRecPane.getCurrRow();
//		if (prev) {
//			if (currRow > 0) {
//				return fileView.getLine(currRow - 1);
//			}
//			return null;
//		}
//		return fileView.getLine(currRow);
	}

	@Override
	public void fireLayoutIndexChanged() {

	}

	@Override
	public int getCurrRow() {
		return splitRecPane.getCurrRow();
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.po.display.IChildScreen#setCurrRow(int)
	 */
	@Override
	public void setCurrRow(int newRow) {
		int oldRow = splitRecPane.getCurrRow();
		splitRecPane.setCurrRow(newRow);

		if ((newRow >= 0)) {
			if (oldRow != newRow) {
				oldRow = newRow;
				rowChanged();
			}
		}
	}

	@Override
	public void setCurrRow(int newRow, int layoutId, int fieldNum) {
		setCurrRow(newRow);
	}

	@Override
	protected BaseDisplay getNewDisplay(FileView view) {
		return null;
	}

	private void rowChanged() {
		int code = getFieldsUsed();


		if (activeFields == code ) {

			this.splitRecPane.splitPane.repaint();
		} else {
			this.splitRecPane.splitPane.removeAll();
			layoutFieldPane(code);


			this.splitRecPane.layoutFieldPane();

			this.actualPnl.revalidate();
			this.actualPnl.repaint();
		}
	}

	private void layoutFieldPane(int code) {

		activeFields = code;

		for (int i = 0; i < CODE_TRANS.length; i++) {
			if (optionalFields[i] != null) {
				optionalFields[i].setVisible((activeFields & CODE_TRANS[i]) != 0);
			}
		}
	}


//	private void setTxtFields() {
//
//		commentTxt.setText(getFieldVal(PoField.comments));
//		msgctxtTxt.setText(getFieldVal(PoField.msgctxt));
//		msgidTxt.setText(getFieldVal(PoField.msgid));
//		msgidPluralTxt.setText(getFieldVal(PoField.msgidPlural));
//		msgstrTxt.setText(getFieldVal(PoField.msgstr));
//	}
//
//	private String getFieldVal(FieldDef fld) {
//		String s = "";
//		Object o =line.getField(0, fld.fieldIdx);
//
//		if (o != null) {
//			s = o.toString();
//		}
//
//		return s;
//	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.extension.IChildScreen#flush()
	 */
	@Override
	public void flush() {
		splitRecPane.flush();
	}

	private int getFieldsUsed() {
		int ret = 0;
		AbstractLine line = splitRecPane.getLine();
		Object o = line.getField(0, PoField.msgctxt.fieldIdx);

		if (checkPresent(o)) {
			ret += CODE_MSG_CTX;
		}

		o = line.getField(0, PoField.msgidPlural.fieldIdx);
		if (checkPresent(o)) {
			ret += CODE_PLURAL;
		} else {
			ret += CODE_MSGSTR;
		}
		o = line.getField(0, PoField.comments.fieldIdx);
		if (checkPresent(o)) {
			ret += CODE_COMMENT;
		}

		o = line.getField(0, PoField.msgstr.fieldIdx);
		if (checkPresent(o)) {
			String s = o.toString();
			if (s.indexOf('<') >= 0 && s.indexOf('>') >=0 && (s.indexOf("</") >= 0 || s.indexOf("/>") >= 0)) {
				ret += CODE_HTML;
			}
		}

		return ret;
	}

	private boolean checkPresent(Object o) {
		return o != null && ! "".equals(o);
	}
}
