package net.sf.RecordEditor.utils.screenManager;

import net.sf.RecordEditor.utils.lang.LangConversion;

/**
 * This class will store 1 message (english); and return it in the current
 * language with the option of parameter substitution. It will use a lookup
 * id to retrieve the foreign text
 *
 * @author Bruce Martin
 *
 * License GPL (any version)
 */
public class ReMsgId {

	private final String id;
	private final String message;


	public ReMsgId(String id, String message) {
		super();
		this.id = id;
		this.message = message;
	}

	public final String get() {
		return LangConversion.convertId(LangConversion.ST_MESSAGE, id, message);
	}

	public final String get(String param) {
		return LangConversion.convertId(LangConversion.ST_MESSAGE, id, message, new Object[] { param});
	}


	public final String get(Object[] params) {
		return LangConversion.convertId(LangConversion.ST_MESSAGE, id, message, params);
	}

}
