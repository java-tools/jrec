package net.sf.RecordEditor.tip.display;


import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;

import net.sf.RecordEditor.edit.display.BaseDisplay;
import net.sf.RecordEditor.edit.display.extension.PaneDtls;
import net.sf.RecordEditor.edit.display.extension.RecordSelection;
import net.sf.RecordEditor.re.display.AbstractFileDisplayWithFieldHide;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.tip.def.TipField;
import net.sf.RecordEditor.utils.swing.SwingUtils;

public  class TipRecordScreen extends RecordSelection
implements AbstractFileDisplayWithFieldHide {



	protected final JTextArea nameTxt     = new JTextArea();
	protected JEditorPane descriptionTxt = new JEditorPane("text", "");
	protected JEditorPane htmlEdt = new JEditorPane("text/html", "");


	public TipRecordScreen(FileView viewOfFile, int lineNo) {
		super("Single Tip Record", viewOfFile, lineNo);

		PaneDtls[] fields = {
				new PaneDtls("name", TipField.name, nameTxt),
				new PaneDtls("description",   TipField.description, descriptionTxt, 0.3),
				new PaneDtls("Html",  TipField.description, htmlEdt, 0, PaneDtls.HTML_IF_HTML_TAG, 0.3),
		};

		setMinSize(descriptionTxt);
		setMinSize(htmlEdt);

		splitPane.setFields(fields, null);

		init_200_layoutScreen();
	}


	@Override
	public void setScreenSize(boolean mainframe) {


		int stdWidth  = java.lang.Math.min(this.screenSize.width - 2, 160 * SwingUtils.CHAR_FIELD_WIDTH );
		int stdHeight = Math.min(
				25 * SwingUtils.CHAR_FIELD_HEIGHT,
				this.screenSize.height - 3 - getParentFrame().getY());

		setScreenSize(mainframe,  stdWidth, Math.max(getParentFrame().getPreferredSize().height, stdHeight ) );
	}


	@Override
	protected BaseDisplay getNewDisplay(FileView view) {
		return new TipRecordScreen(view, 0);
	}

	private void setMinSize(JComponent c) {
		Dimension d = c.getPreferredSize();

		d.height = Math.min(d.height, SwingUtils.CHAR_FIELD_HEIGHT * 3 + 3);
		c.setPreferredSize(d);
	}
}
