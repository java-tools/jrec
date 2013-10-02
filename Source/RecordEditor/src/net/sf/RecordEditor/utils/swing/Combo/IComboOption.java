package net.sf.RecordEditor.utils.swing.Combo;

import net.sf.RecordEditor.utils.params.IHasKey;

public interface IComboOption<Key> extends IHasKey {

	public abstract String toString();

	/**
	 * @return the string
	 */
	public abstract String getString();

	/**
	 * @return the key
	 */
	public abstract Key getKey();

	/**
	 * @param string the string to set
	 */
	public abstract void setString(String string, String englishString);

	/**
	 * @return the english
	 */
	public abstract String getEnglish();
}