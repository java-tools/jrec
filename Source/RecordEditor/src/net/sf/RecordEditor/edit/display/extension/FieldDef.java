package net.sf.RecordEditor.edit.display.extension;

import net.sf.RecordEditor.utils.StringMatch;

public class FieldDef implements StringMatch  {

	public final String name;
	public final int fieldIdx;
	public final boolean repeating;
	public final boolean multLine;
	public final boolean single;
	public final boolean couldHaveIndex;
	public final boolean stripQuote;


	public FieldDef(String name, int idx, boolean multLine, boolean repeating,
			 boolean single, boolean couldHaveIndex, boolean stripQuote) {

		this.name = name;
		this.fieldIdx = idx;
		this.repeating = repeating;
		this.multLine = multLine;
		this.single = single;
		this.couldHaveIndex = couldHaveIndex;
		this.stripQuote = stripQuote;
	}

	@Override
	public boolean isMatch(String s) {
		boolean ret = false;
		if (s.startsWith(name)) {
			ret = true;
		} else if (name.endsWith(" ")) {
			ret = s.equals(name.trim());
		}

		return ret;
	}

}