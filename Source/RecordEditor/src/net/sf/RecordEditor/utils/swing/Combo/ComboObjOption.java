package net.sf.RecordEditor.utils.swing.Combo;

/**
 * A simple combo option with an integer key
 * @author Bruce Martin
 *
 */
public class ComboObjOption<Key> extends ComboStdOption<Key> {
	public ComboObjOption(Key idx, String str, String englishString) {
		super(idx, str, englishString);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ComboObjOption && key != null) {
			return key.equals(((ComboObjOption) obj).key);
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
