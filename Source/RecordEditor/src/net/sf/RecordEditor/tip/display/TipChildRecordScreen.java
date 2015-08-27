package net.sf.RecordEditor.tip.display;

import java.awt.Dimension;

import javax.swing.JEditorPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import net.sf.RecordEditor.edit.display.AbstractCreateChildScreen;
import net.sf.RecordEditor.edit.display.BaseDisplay;
import net.sf.RecordEditor.edit.display.extension.IChildScreen;
import net.sf.RecordEditor.edit.display.extension.PaneDtls;
import net.sf.RecordEditor.edit.display.extension.SplitPaneRecord;
import net.sf.RecordEditor.edit.display.util.LinePosition;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.tip.def.TipField;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;

public class TipChildRecordScreen extends BaseDisplay implements IChildScreen {

	private static final String HTML_STR = "Html";
	private static final String NAME_STR = "Name";
	private static final String DESCRIPTION_STR = "Description";

	protected SplitPaneRecord splitPane;


	protected final JTextArea nameTxt     = new JTextArea();
//	protected final JTextArea descriptionTxt = new JTextArea();
	protected JEditorPane descriptionTxt = new JEditorPane("text", "");
	protected JEditorPane htmlEdt = new JEditorPane("text/html", "");


	public TipChildRecordScreen(FileView viewOfFile, int lineNo, int position) {
		super("Single PO Record", viewOfFile, false, false, false, false, false,
				NO_LAYOUT_LINE);

		splitPane = new SplitPaneRecord(NO_CLOSE_ACTION_PNL, viewOfFile, lineNo);

		if (position == AbstractCreateChildScreen.CS_BOTTOM) {
			splitPane.setFields(new PaneDtls[] {
				new PaneDtls(NAME_STR, TipField.name, nameTxt),
				new PaneDtls(DESCRIPTION_STR, TipField.description, descriptionTxt),
				new PaneDtls(HTML_STR, TipField.description, htmlEdt, 1, true),
			}, new double[] {0.45, 0.45});
		} else {
			splitPane.setFields(new PaneDtls[] {
					new PaneDtls(NAME_STR, TipField.name, nameTxt),
					new PaneDtls(DESCRIPTION_STR, TipField.description, descriptionTxt, 0.25),
					new PaneDtls(HTML_STR, TipField.description, htmlEdt, 0, PaneDtls.IS_HTML, 0.25),
				}, null);
		}
		setJTable(new JTable());

		init_200_layoutScreen();
	}

	private void init_200_layoutScreen() {

		splitPane.layoutFieldPane();

		actualPnl.addComponentRE(1, 3, BasePanel.FILL, BasePanel.GAP,
                BasePanel.FULL, BasePanel.FULL, splitPane.splitPane);
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
		return super.getInsertAfterLine(splitPane.getCurrRow(), prev);
	}

	@Override
	public void fireLayoutIndexChanged() {

	}

	@Override
	public int getCurrRow() {
		return splitPane.getCurrRow();
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.po.display.IChildScreen#setCurrRow(int)
	 */
	@Override
	public void setCurrRow(int newRow) {
		splitPane.setCurrRow(newRow);
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.extension.IChildScreen#flush()
	 */
	@Override
	public void flush() {
		splitPane.flush();
	}

	@Override
	public void setCurrRow(int newRow, int layoutId, int fieldNum) {
		setCurrRow(newRow);
	}


	@Override
	protected BaseDisplay getNewDisplay(FileView view) {
		return null; //new TipSingleRecordScreen(view, 0, AbstractCreateChildScreen.CS_RIGHT);
	}
}
