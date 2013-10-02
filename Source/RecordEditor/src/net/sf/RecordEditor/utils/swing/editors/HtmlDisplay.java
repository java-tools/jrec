package net.sf.RecordEditor.utils.swing.editors;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.RTextScrollPane;

import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;

public class HtmlDisplay {
	public final ReFrame frame;

	private final BaseHelpPanel pnl = new BaseHelpPanel("HtmlEdit");
	private final RSyntaxTextArea textArea = new RSyntaxTextArea();
    private final RTextScrollPane scrollPane = new RTextScrollPane(textArea, true);
    private JEditorPane htmlPane =  new JEditorPane("text/html", "");

	public HtmlDisplay() {
		super();
		this.frame = new ReFrame("xxx");

		init_200_layoutScreen();
		init_300_setAttributes();

		frame.setVisible(true);
	}


	private void init_200_layoutScreen() {

		JSplitPane sp = new JSplitPane(
				JSplitPane.VERTICAL_SPLIT, scrollPane, new JScrollPane(htmlPane));
		pnl.addComponent(1, 5, BasePanel.FILL, BasePanel.GAP3,
		        BasePanel.FULL, BasePanel.FULL,
				sp);

		frame.addMainComponent(pnl);
	}

	private void init_300_setAttributes() {

	    textArea.setTabSize(3);
	    textArea.setCaretPosition(0);
//	    textArea.addHyperlinkListener(this);
	    textArea.requestFocusInWindow();
	    textArea.setMarkOccurrences(true);
	    textArea.setCodeFoldingEnabled(true);
	    textArea.setClearWhitespaceLinesEnabled(false);
	    //textArea.setWhitespaceVisible(true);
	    //textArea.setPaintMatchedBracketPair(true);

        Gutter gutter = scrollPane.getGutter();
        gutter.setBookmarkingEnabled(true);

        textArea.addFocusListener(new FocusAdapter() {
			@Override public void focusLost(FocusEvent e) {
				htmlPane.setText(textArea.getText());
			}

		});
	}

}
