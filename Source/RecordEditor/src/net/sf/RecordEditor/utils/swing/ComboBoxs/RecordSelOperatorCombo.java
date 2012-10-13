package net.sf.RecordEditor.utils.swing.ComboBoxs;

import net.sf.JRecord.Common.Constants;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.swing.Combo.ComboKeyedOption;

@SuppressWarnings("serial")
public final class RecordSelOperatorCombo extends EnglishCombo<String> {

	public static final String[] COMPARISON_OPERATORS =
		{"=", "!=", "<>", ">", ">=",  "<", "<=", Constants.STARTS_WITH, Constants.CONTAINS};

	private static ComboKeyedOption<String> EMPTY = new ComboKeyedOption<String>("", "", "");

	public RecordSelOperatorCombo() {
		super(EMPTY);

		String conv ;
		for (int i = 0; i < COMPARISON_OPERATORS.length; i++) {
			conv = COMPARISON_OPERATORS[i];
			if (i >= COMPARISON_OPERATORS.length - 2) {
				conv = LangConversion.convert(LangConversion.ST_COMBO, conv);
			}
			super.addItem(new ComboKeyedOption<String>(COMPARISON_OPERATORS[i], conv, COMPARISON_OPERATORS[i]));
		}
	}

}
