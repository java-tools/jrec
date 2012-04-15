package net.sf.RecordEditor.utils.swing.Combo;

public class ComboStrOption {
	public final String string;
	public final String key;
	
	public ComboStrOption(String keyValue, String str) {
		string = str;
		key = keyValue;
	}
	
	public String toString() {
		return string;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ComboStrOption && key != null) {
			return key.equals(((ComboStrOption) obj).key);
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
