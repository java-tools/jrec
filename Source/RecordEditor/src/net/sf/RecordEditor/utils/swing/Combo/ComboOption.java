package net.sf.RecordEditor.utils.swing.Combo;

/**
 * A simple combo option
 * @author Bruce Martin
 *
 */
public class ComboOption {
	public final String string;
	public final int index;
	private final int hashCode;

	public ComboOption(int idx, String str) {
		string = str;
		index = idx;
		hashCode = Integer.valueOf(idx).hashCode();
	}

	public String toString() {
		return string;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ComboOption) {
			return index == ((ComboOption) obj).index;
		}
		return super.equals(obj);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return hashCode;
	}

}
