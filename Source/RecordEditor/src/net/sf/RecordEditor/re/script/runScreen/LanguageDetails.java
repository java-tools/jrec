package net.sf.RecordEditor.re.script.runScreen;

import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

/**
 *
 * @author Bruce Martin
 *
 * Purpose: this class as an interface between:
 *
 *   * File-name-Extension
 *   * rsyntax edit pane
 *   * script engine
 *
 * License: GPL
 */
public final class LanguageDetails {

	public static final LanguageDetails DEFAULT_LANGUAGE_DEF = new LanguageDetails("txt", "Text", SyntaxConstants.SYNTAX_STYLE_NONE, null);

   	public final String ext, langName, rSyntax;
	public String scriptLangName;

	/**
	 * Purpose: this class as an interface between:
	 *
	 *   * File-name-Extension
	 *   * rsyntax edit pane
	 *   * script engine
	 *
	 * @param ext filename extension
	 * @param langName language name
	 * @param rSyntax rSyntax identifier
	 * @param scriptLangName Script-Engine Language Name
	 */
	public LanguageDetails(String ext, String langName, String rSyntax, String scriptLangName) {
		super();
		this.ext = ext;
		this.langName = langName;
		this.rSyntax = rSyntax;
		this.scriptLangName = scriptLangName;
	}

}
