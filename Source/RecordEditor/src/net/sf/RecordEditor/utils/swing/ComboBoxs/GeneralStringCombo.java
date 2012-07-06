package net.sf.RecordEditor.utils.swing.ComboBoxs;

import net.sf.RecordEditor.utils.swing.Combo.ComboStrOption;


@SuppressWarnings("serial")
public class GeneralStringCombo extends EnglishCombo<String> {

   	public GeneralStringCombo(String[] keys, String[] values, String[] englishValues) {
   		super(new ComboStrOption("", "", ""));

   		for (int i = 0; i < keys.length; i++) {
   			super.addItem(
   					new ComboStrOption(
   							keys[i],
   							values[i],
   							englishValues[i]));
   		}
   	}
}
