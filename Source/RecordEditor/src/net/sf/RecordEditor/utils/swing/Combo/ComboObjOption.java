package net.sf.RecordEditor.utils.swing.Combo;

/**
 * A simple combo option with an integer key
 * @author Bruce Martin
 *
 */
public class ComboObjOption<Key> {
	private String string;
	public final Key index;
	
	public ComboObjOption(Key idx, String str) {
		string = str;
		index = idx;
	}
	
	public String toString() {
		return string;
	}



	/**
	 * @param string the string to set
	 */
	public void setString(String string) {
		this.string = string;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ComboObjOption && index != null) {
			return index.equals(((ComboObjOption) obj).index);
		}
		return super.equals(obj);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return index.hashCode();
	}
}
