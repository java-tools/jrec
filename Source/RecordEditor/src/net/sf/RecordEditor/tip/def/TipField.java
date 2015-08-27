package net.sf.RecordEditor.tip.def;

import net.sf.RecordEditor.edit.display.extension.FieldDef;

public class TipField extends FieldDef {

	public static final FieldDef name		      = new TipField("Name", 0);
	public static final FieldDef description      = new TipField("Description", 1);

	static final FieldDef[] allFields = {
		name,
		description,
	};

	public static FieldDef[] getAllfields() {
		return allFields.clone();
	}

	private final String matchChk;
	private TipField(String name, int idx) {
		super(name, idx, false, false, true, false, false);
		matchChk = "." + name.toLowerCase() + "=";
	}

	@Override
	public boolean isMatch(String s) {
		if (s == null) {
			return false;
		}
		String lcS = s.toLowerCase();
		return lcS.startsWith("tip.") && lcS.indexOf(matchChk) > 0;
	}

	
}
