package net.sf.RecordEditor.utils.swing.Combo;

import net.sf.RecordEditor.utils.params.IHasKey;

public class ComboStdOption<Key> implements IHasKey, IComboOption<Key> {

	protected String string;
	protected String english;
	public final Key key;

	public ComboStdOption(Key idx, String str, String englishString) {
		super();

		string = str;
		key = idx;
		english = englishString;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.Combo.IComboOption#toString()
	 */
	@Override
	public String toString() {
		return string;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.Combo.IComboOption#getString()
	 */
	@Override
	public String getString() {
		return string;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.Combo.IComboOption#getKey()
	 */
	@Override
	public Key getKey() {
		return key;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.Combo.IComboOption#setString(java.lang.String, java.lang.String)
	 */
	@Override
	public void setString(String string, String englishString) {

		this.string = string;
		this.english = englishString;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.Combo.IComboOption#getEnglish()
	 */
	@Override
	public String getEnglish() {
		return english;
	}

}