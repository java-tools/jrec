package net.sf.RecordEditor.utils.swing.Combo;

/**
 * A simple combo option with an integer key
 * @author Bruce Martin
 *
 */
public class ComboKeyedOption<Key> extends ComboStdOption<Key> {
	public ComboKeyedOption(Key idx, String str, String englishString) {
		super(idx, str, englishString);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (key != null) {
			if (obj instanceof ComboKeyedOption) {
				return key.equals(((ComboKeyedOption) obj).key);
			} else if (key.equals(obj)) {
				return true;
			}
		}
		return super.equals(obj);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return key.hashCode();
	}
}
