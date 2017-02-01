package net.sf.RecordEditor.re.script.runScreen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.sf.RecordEditor.utils.common.FileUtils;
import net.sf.RecordEditor.utils.msg.UtMessages;
import net.sf.RecordEditor.utils.swing.TabWithClosePnl;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.RTextScrollPane;

public final class ScriptEditPane {

	public final TabWithClosePnl tabWithClose;

	private File scriptFile = null;
	private long changeDate = 0;
	private final RSyntaxTextArea textArea = new RSyntaxTextArea();
    private final RTextScrollPane scrollPane = new RTextScrollPane(textArea, true);
    private boolean changed;
    private final ChangeListener tabChangeListner;

	public  final JComponent tabComponent = scrollPane;

	private LanguageDetails languageDetails = LanguageDetails.DEFAULT_LANGUAGE_DEF;


	private DocumentListener docListner = new DocumentListener() {

		@Override
		public void removeUpdate(DocumentEvent e) {
			setChanged(true);
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			setChanged(true);
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			setChanged(true);
		}
	};

	public ScriptEditPane(ChangeListener tabChangeListner) {

		this.tabWithClose = new TabWithClosePnl("    ", true);
		this.tabChangeListner = tabChangeListner;
		init();
		setChanged(false);
	}

	/**
	 * Script Edit Tab pane
	 * @param fullFileName file name to Edit
	 * @throws IOException any IO error
	 */
	public ScriptEditPane(ChangeListener tabChangeListner, File scriptFile) throws IOException {
		super();

		setScriptFile(scriptFile);
		this.tabWithClose = new TabWithClosePnl(scriptFile.getName(), true);
		this.tabChangeListner = tabChangeListner;
		init();

		readFile(scriptFile);

		setChanged(false);
	}

	/**
	 * Creates the text area for this application.
	 *
	 * @return The text area.
	 */
	private void  init() {

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
//      URL url = getClass().getClassLoader().getResource("bookmark.png");
//      gutter.setBookmarkIcon(new ImageIcon(url));
	}


	/**
	 * Read file into Text Area and set
	 * @param scriptFile
	 * @throws IOException any Error that occurs
	 */
	public final void readFile(File scriptFile) throws IOException {

		setScriptFile(scriptFile);

		textArea.setText(FileUtils.readFile(scriptFile));

		tabWithClose.setTabname(scriptFile.getName());

		setChanged(false);
	}

	public final void clear() {
		textArea.setText("");
		tabWithClose.setTabname("");
		setScriptFile(null);
		tabChangeListner.stateChanged(null);
		setChanged(false);
	}

	public final boolean isEmpty() {
		return scriptFile == null;
	}

	/**
	 * @return the scriptFile
	 */
	public final File getScriptFile() {
		return scriptFile;
	}


	/**
	 * @param scriptFile the scriptFile to set
	 */
	public void setScriptFile(File scriptFile) {
		this.scriptFile = scriptFile;
		setScriptChangeDate();
	}

	/**
	 * @return the textArea
	 */
	public RSyntaxTextArea getTextArea() {
		return textArea;
	}

	/**
	 * @return the scrollPane
	 */
	public RTextScrollPane getScrollPane() {
		return scrollPane;
	}

	private void setScriptChangeDate() {
		if (scriptFile != null) {
			this.changeDate = scriptFile.lastModified();
		}
	}

	/**
	 * @return
	 * @see javax.swing.text.JTextComponent#getText()
	 */
	public String getText() {
		return textArea.getText();
	}

	public void checkSave() throws IOException {

		if (scriptFile != null && changed) {
			int opt =  JOptionPane.showConfirmDialog(
					scrollPane,
					UtMessages.SAVE_FILE_NAME.get(scriptFile.getPath()),
					UtMessages.SAVE_FILE.get(),
					JOptionPane.YES_NO_OPTION);

			if (opt == JOptionPane.YES_OPTION) {
				saveFile();
			} else {
				clear();
			}
		}
	}

	public void saveFile() throws IOException {

		if (scriptFile != null) {
			FileWriter w = new FileWriter(scriptFile);
			try {
				w.write(getText());
			} finally {
				w.close();
			}

			setScriptChangeDate();
			setChanged(false);
		}
	}

	public void saveAsFile(File newFile) throws IOException {

		if (newFile != null) {
			FileWriter w = new FileWriter(newFile);
			try {
				String text = getText();
				w.write(text);

				setScriptFile(newFile);
				tabWithClose.setTabname(scriptFile.getName());
				setChanged(false);
			} finally {
				w.flush();
				w.close();
			}
		}
	}


	/**
	 * @return changed status
	 */
	public boolean isChanged() {
		return changed || (scriptFile != null && changeDate != scriptFile.lastModified());
	}

	private void setChanged(boolean b) {
		changed = b;

//		if (scriptFile != null) {
			textArea.getDocument().removeDocumentListener(docListner);

			if (! changed) {
				textArea.getDocument().addDocumentListener(docListner);
			}
//		}
	}

	/**
	 * @return the languageDetails
	 */
	public LanguageDetails getLanguageDetails() {
		return languageDetails;
	}

	/**
	 * Set the programming language
	 *
	 * @param lang programming language
	 */
	public final void setLanguageDetails(LanguageDetails langDetails) {

//		String styleKey = "text/" + lang;
//		if ("ECMAScript".equalsIgnoreCase(lang)) {
//			styleKey = SyntaxConstants.SYNTAX_STYLE_ACTIONSCRIPT;
//		}

//		TokenMakerFactory f = TokenMakerFactory.getDefaultInstance();
//		if (f != null && ! f.keySet().contains(langDetails.rSyntax)) {
//			String className = "org.fife.ui.rsyntaxtextarea.modes." + langDetails.langName + "TokenMaker";
//	
//			if (f instanceof AbstractTokenMakerFactory && this.getClass().getClassLoader().getResource(className) != null) {
//				((AbstractTokenMakerFactory) f).putMapping(langDetails.rSyntax, className);
//			}
//		}
		textArea.getDocument().removeDocumentListener(docListner);
		
		textArea.setSyntaxEditingStyle(langDetails.rSyntax);
		setChanged(changed);
		this.languageDetails = langDetails;
	}




}

