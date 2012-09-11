package net.sf.RecordEditor.utils.swing.common;

public interface UpdatableItem {

	/**
	 * Get components value
	 * @return components value
	 */
	public abstract Object getValue();

	/**
	 * Set components value
	 * @param value
	 */
	public abstract void setValue(Object value);
}
