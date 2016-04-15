package net.sf.RecordEditor.layoutEd.load;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.RTextScrollPane;

public class EditPane {

	final RSyntaxTextArea textArea = new RSyntaxTextArea();
    final RTextScrollPane scrollPane = new RTextScrollPane(textArea, true);

	protected EditPane() {
		super();
		
	    textArea.setTabSize(3);
	    textArea.setCaretPosition(0);
//	    textArea.addHyperlinkListener(this);
	    textArea.requestFocusInWindow();
	    textArea.setMarkOccurrences(true);
	    textArea.setCodeFoldingEnabled(true);
	    textArea.setClearWhitespaceLinesEnabled(false);

	    Gutter gutter = scrollPane.getGutter();
	    gutter.setBookmarkingEnabled(true);
	    textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
	}
 
}
