package net.sf.RecordEditor.utils.swing.editors;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.RTextScrollPane;

@SuppressWarnings("serial")
public class HtmlDisplay extends ReFrame {

	private final BaseHelpPanel pnl = new BaseHelpPanel("HtmlEdit");
	private final RSyntaxTextArea textArea = new RSyntaxTextArea();
    private final RTextScrollPane scrollPane = new RTextScrollPane(textArea, true);
    private final JEditorPane htmlPane =  new JEditorPane("text/html", "");

    private final JSplitPane splitPane = new JSplitPane(
			JSplitPane.VERTICAL_SPLIT, scrollPane, new JScrollPane(htmlPane));

	private final JTable table;
	private final int row, column;

	private final UpdateHtml task = new UpdateHtml();
	private UpdateHtml toRun = null;

	public HtmlDisplay(JTable table, int lastRow, int lastCol) {
		super("", "Html Cell Edit: " + lastCol + ", " + lastRow, table);
		//super("xxx", "xxx", null);
		this.table = table;
		this.row = lastRow;
		this.column = lastCol;


		init_100_Setup();
		init_200_layoutScreen();
		init_300_setAttributes();
		init_300_showScreen();
	}

//	private static String getPnlName(JTable table) {
//
//	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.screenManager.ReFrame#windowClosing()
	 */
	@Override
	public void windowClosing() {
		if (table != null) {
			table.setValueAt(textArea.getText(), row, column);

		}
		super.windowClosing();
	}


	private void init_100_Setup() {

		Object o = table.getValueAt(row, column);
		String s = "";
		if (o != null) {
			s = o.toString();
		}
		textArea.setText(s);
		htmlPane.setText(s);
	}


	private void init_200_layoutScreen() {


		pnl.addComponentRE(1, 5, BasePanel.FILL, BasePanel.GAP0,
		        BasePanel.FULL, BasePanel.FULL,
				splitPane);

		this.addMainComponent(pnl);

//		this.setPreferredSize(new Dimension(
//				Math.max(this.getPreferredSize().width, SwingUtils.STANDARD_FONT_WIDTH * 120),
//				Math.max(this.getPreferredSize().height, SwingUtils.STANDARD_FONT_HEIGHT * 130)));
		splitPane.setDividerLocation(0.50);
	}

	private void init_300_setAttributes() {

	    textArea.setTabSize(3);
	    textArea.setCaretPosition(0);
//	    textArea.addHyperlinkListener(this);
	    textArea.requestFocusInWindow();
	    textArea.setMarkOccurrences(true);
	    textArea.setCodeFoldingEnabled(true);
	    textArea.setClearWhitespaceLinesEnabled(false);

        Gutter gutter = scrollPane.getGutter();
        gutter.setBookmarkingEnabled(true);


		textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_HTML);


        textArea.addFocusListener(new FocusAdapter() {
			@Override public void focusLost(FocusEvent e) {
				updateHtmlText();
			}
		});
        textArea.addKeyListener(new KeyAdapter() {
			@Override public void keyReleased(KeyEvent e) {
				runUpdate();
			}
		});
	}

	private void init_300_showScreen() {


//		splitPane.setDividerLocation(0.50);
		Rectangle screenSize = ReMainFrame.getMasterFrame().getDesktop().getBounds();
		this.setToMaximum(false);
		Dimension preferredSize = new Dimension(
				Math.min(screenSize.width,
						Math.max(this.getPreferredSize().width, SwingUtils.STANDARD_FONT_WIDTH * 45)),
				Math.min(screenSize.height,
						Math.max(this.getPreferredSize().height, SwingUtils.STANDARD_FONT_HEIGHT * 40)));
		this.setPreferredSize(preferredSize);

		this.setBounds(new Rectangle(preferredSize));

		this.setVisible(true);
		this.setToMaximum(false);
	}


	private void updateHtmlText() {

		String htmlText = textArea.getText();
		htmlPane.setText(htmlText);

		if (table != null) {
			table.setValueAt(htmlText, row, column);
		}
	}

	private void runUpdate() {

		if (toRun == null) {
			synchronized (task) {
				toRun = task;
				SwingUtilities.invokeLater(toRun);
			}
		}
	}

	private class UpdateHtml implements Runnable {

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			synchronized (task) {
				toRun = null;
			}
			updateHtmlText();
		}

	}
}
