package net.sf.RecordEditor.po.display;


import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import net.sf.RecordEditor.edit.display.BaseDisplay;
import net.sf.RecordEditor.edit.display.common.AbstractFileDisplayWithFieldHide;
import net.sf.RecordEditor.edit.display.extension.PaneDtls;
import net.sf.RecordEditor.edit.display.extension.RecordSelection;
import net.sf.RecordEditor.po.def.PoField;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.array.ArrayRender;
import net.sf.RecordEditor.utils.swing.common.UpdatableItem;

public  class PoRecordScreen extends RecordSelection
implements AbstractFileDisplayWithFieldHide {

	protected final JTextArea commentTxt     = new JTextArea();
	protected final JTextArea msgctxtTxt     = new JTextArea();
	protected final JTextArea msgidTxt       = new JTextArea();
	protected final JTextArea msgidPluralTxt = new JTextArea();
	protected final JTextArea msgstrTxt      = new JTextArea();
	protected final JTextArea commentsTxt    = new JTextArea();
	protected final JTextArea extractedCommentsTxt   = new JTextArea();
	protected final JTextArea referenceTxt   = new JTextArea();
	protected final JTextArea flagsTxt       = new JTextArea();
	protected JEditorPane htmlEdt = new JEditorPane("text/html",  "");

	private ArrayRender msgstrPlural = new ArrayRender(true, false);
	private JTable parentTbl;


	public PoRecordScreen(@SuppressWarnings("rawtypes") FileView viewOfFile, int lineNo, JTable parentTbl) {
		super("Single PO Record", viewOfFile, lineNo);

		this.parentTbl = parentTbl;

		msgstrPlural.setTableDetails(parentTbl, lineNo, PoField.msgstrPlural.fieldIdx, "",
				viewOfFile.getBaseFile().getFileNameNoDirectory(), viewOfFile.getBaseFile());
		PaneDtls[] fields = {
				new PaneDtls("msgctxt", PoField.msgctxt, msgctxtTxt),
				new PaneDtls("msgid",   PoField.msgid, msgidTxt, 0.2),
				new PaneDtls("msgstr",  PoField.msgstr, msgstrTxt, 0.2),
				new PaneDtls("msgid plural", PoField.msgidPlural, msgidPluralTxt),
				new PaneDtls("msgstr plural", PoField.msgstrPlural, msgstrPlural),
				new PaneDtls("comments",           PoField.comments, commentsTxt, 1, false, 0.3),
				new PaneDtls("Extracted Comments", PoField.extractedComments, extractedCommentsTxt, 1),
				new PaneDtls("reference",          PoField.reference, referenceTxt, 1),
				new PaneDtls("flags", PoField.flags, flagsTxt, 1),
				new PaneDtls("fuzzy", PoField.fuzzy, new updateCheckBox("Fuzzy"), 1, false, -1),
				new PaneDtls("Obsolete", PoField.obsolete, new updateCheckBox("obsolete"), 1, false, -1),
				new PaneDtls("html", PoField.msgstr, htmlEdt, 0, true, 0.3),
		};

		splitPane.setFields(fields, new double[] {0.45, 0.45});

		init_200_layoutScreen();
	}


	@Override
	public void setScreenSize(boolean mainframe) {


		int preferedWidth = java.lang.Math.min(this.screenSize.width - 2, 160 * SwingUtils.CHAR_WIDTH );
		setScreenSize(mainframe, preferedWidth, this.screenSize.height - 3 - getParentFrame().getY());

	}

	@Override
	protected BaseDisplay getNewDisplay(@SuppressWarnings("rawtypes") FileView view) {
		return new PoRecordScreen(view, 0, parentTbl);
	}


	public class updateCheckBox extends JCheckBox implements UpdatableItem {



		public updateCheckBox(String text) {
			super(text);
		}

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.utils.swing.common.UpdatableItem#getValue()
		 */
		@Override
		public Object getValue() {
			if (isSelected()) {
				return "Y";
			}
			return null;
		}

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.utils.swing.common.UpdatableItem#setValue(java.lang.Object)
		 */
		@Override
		public void setValue(Object value) {
			super.setSelected(value != null && "Y".equalsIgnoreCase(value.toString()));
		}

	}
}
