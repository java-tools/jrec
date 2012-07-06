package net.sf.RecordEditor.utils.screenManager;

import net.sf.RecordEditor.utils.lang.LangConversion;

/**
 * This class will store 1 message (english); and return it in the current
 * language with the option of parameter substitution
 *
 * @author Bruce Martin
 *
 * License GPL (any version)
 */
public final class ReMsg {
	private final String msg;

	public ReMsg(String messsage) {
		super();
		this.msg = messsage;
	}

	public final String get() {
		return LangConversion.convert(LangConversion.ST_MESSAGE, msg);
	}

	public final String get(String param) {
		return LangConversion.convert(msg, param);
	}


	public final String get(Object[] params) {
		return LangConversion.convert(msg, params);
	}

}
