package net.sf.RecordEditor.po.def;

import net.sf.RecordEditor.edit.display.extension.FieldDef;

public class PoField extends FieldDef {

	public static final String FUZZY = "fuzzy";

	public static final PoField msgctxt		      = new PoField("msgctxt");
	public static final PoField msgid		      = new PoField("msgid");
	public static final PoField msgidPlural	      = new PoField("msgid_plural ", "msgidPlural");
	public static final PoField msgstr		      = new PoField("msgstr");
	public static final PoField msgstrPlural	  = new PoField("msgstr", "msgstrPlural", true, true, false, true, true);
	public static final PoField comments	      = new PoField("# ", "comments", false, false, false, false, false);
	public static final PoField extractedComments = new PoField("#. ", "extractedComments", false, false, false, false, false);
	public static final PoField reference	      = new PoField("#: ", "reference", false, false, false, false, false);
	public static final PoField flags		      = new PoField("#, ", "flags", false, false, false, false, false);
	public static final PoField previousMsgctx	  = new PoField("#| msgctxt ", "previousMsgctx");
	public static final PoField previousMsgId	  = new PoField("#| msgid",    "previousMsgId");
	public static final PoField previousMsgidPlural  = new PoField("#| msgid", "previousMsgidPlural", true, false, true, true, true);
	public static final PoField fuzzy	          = new PoField(FUZZY);
	public static final PoField obsolete          = new PoField("obsolete");

	static final PoField[] allFields = {
		msgctxt,
		msgid,
		msgidPlural,
		msgstrPlural,    // Needs to be before msgstr as key is a subset of msgstr
		msgstr,
		comments,
		extractedComments,
		reference,
		flags,
		previousMsgctx,
		previousMsgId,
		previousMsgidPlural,
	};

	public PoField(String name) {
		this(name + " ", PoLayoutMgr.getIndexOf(name), false, false, true, false, true);
	}

	public PoField(String name, boolean stripQuote) {
		this(name + " ", PoLayoutMgr.getIndexOf(name), false, false, true, false, stripQuote);
	}

	public PoField(String name, String fieldName) {
		this(name, PoLayoutMgr.getIndexOf(fieldName), false, false, true, false, true);
	}

	public PoField(String name,  String fieldName, boolean multLine, boolean repeating,
			 boolean single, boolean couldHaveIndex, boolean stripQuote) {
		this(name, PoLayoutMgr.getIndexOf(fieldName), multLine, repeating, single, couldHaveIndex, stripQuote);
	}

	public PoField(String name, int idx, boolean multLine, boolean repeating,
			 boolean single, boolean couldHaveIndex, boolean stripQuote) {
		super(name, idx, multLine, repeating, single, couldHaveIndex, stripQuote);

	}


	public static PoField[] getAllfields() {
		return allFields.clone();
	}


	public boolean isMatch(String s) {
		boolean ret = super.isMatch(s);
		if (s.startsWith(name) && repeating) {
			ret = s.substring(name.length()).trim().startsWith("[");
		}

		return ret;
	}

}
