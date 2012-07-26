package net.sf.RecordEditor.utils.swing.ComboBoxs;

import javax.swing.DefaultComboBoxModel;

import net.sf.RecordEditor.utils.swing.Combo.ComboStrOption;

public class EnglishStrModel extends DefaultComboBoxModel {


   	public void setComboItems(String[] keys, String[] values, String[] englishValues) {
   		for (int i = 0; i < keys.length; i++) {
   			super.addElement(
   							new ComboStrOption(
   							keys[i],
   							values[i],
   							englishValues[i]));
   		}
   	}
}
