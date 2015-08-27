package net.sf.RecordEditor.utils.screenManager;

public interface IMessage {

	/**
	 * Get the translated value
	 * @return translated value
	 */
	public abstract String get();

	/**
	 * Get the translated value
	 * @param param parameter to use in the translated string
	 * @return translated value
	 */
	public abstract String get(String param);

	/**
	 * Get the translated value
	 * @param params parameter to use in the translated string
	 * @return translated value
	 */
	public abstract String get(Object... params);

}