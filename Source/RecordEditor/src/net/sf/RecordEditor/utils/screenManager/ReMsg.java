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
	private final int msgType;

	public ReMsg(String messsage) {
		this(LangConversion.ST_MESSAGE, messsage);
	}

	public ReMsg(int type, String messsage) {
		super();
		this.msg = messsage;
		this.msgType = type;
		LangConversion.logMsg(msg);
	}

	public final String get() {
		return LangConversion.convert(msgType, msg);
	}

	public final String get(String param) {
		return LangConversion.convert(msgType, msg, param);
	}


	public final String get(Object[] params) {
		return LangConversion.convert(msgType, msg, params);
	}

}
