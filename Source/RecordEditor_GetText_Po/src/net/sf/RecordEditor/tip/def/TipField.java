package net.sf.RecordEditor.tip.def;

import net.sf.RecordEditor.edit.display.extension.FieldDef;

public class TipField {

	public static final FieldDef name		      = new FieldDef("Name", 0, false, false, true, false, false);
	public static final FieldDef description      = new FieldDef("Description", 1, false, false, true, false, false);

	static final FieldDef[] allFields = {
		name,
		description,
	};



	public static FieldDef[] getAllfields() {
		return allFields.clone();
	}



}
