package net.sf.RecordEditor.utils.swing.Combo;

public class ComboStdOption<Key> {

	protected String string;
	protected String english;
	public final Key key;

	public ComboStdOption(Key idx, String str, String englishString) {
		super();

		string = str;
		key = idx;
		english = englishString;
	}

	public String toString() {
		return string;
	}

	/**
	 * @param string the string to set
	 */
	public void setString(String string, String englishString) {

		this.string = string;
		this.english = englishString;
	}

	/**
	 * @return the english
	 */
	public String getEnglish() {
		return english;
	}

}