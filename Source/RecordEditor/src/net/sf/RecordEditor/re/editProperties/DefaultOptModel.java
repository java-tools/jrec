package net.sf.RecordEditor.re.editProperties;

import net.sf.JRecord.Common.AbstractManager;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.swing.Combo.ComboStrOption;
import net.sf.RecordEditor.utils.swing.ComboBoxs.EnglishStrModel;

public class DefaultOptModel {

	public static EnglishStrModel newModel(AbstractManager manager) {
		EnglishStrModel mdl = new EnglishStrModel();
		String id = manager.getManagerName();
		String s;
		for (int i = 0; i < manager.getNumberOfEntries(); i++) {
            s = manager.getName(i);
            if (s != null && ! "".equals(s)) {
                mdl.addElement(getItem(id, i, s));
            }
        }
		return mdl;
	}

	public static EnglishStrModel newModel(String[] list) {
		EnglishStrModel mdl = new EnglishStrModel();
		String s;
		for (int i = 0; i < list.length; i++) {
            s = list[i];
            if (s != null && ! "".equals(s)) {
                mdl.addElement(new ComboStrOption(s, s, s));
            }
        }
		return mdl;
	}


	public static EnglishStrModel newModel(String id, String[] list) {
		EnglishStrModel mdl = new EnglishStrModel();
		String s;
		for (int i = 0; i < list.length; i++) {
            s = list[i];
            if (s != null && ! "".equals(s)) {
                mdl.addElement(getItem(id, i, s));
            }
        }
		return mdl;
	}

	private static ComboStrOption getItem(String id, int idx, String s) {
		return new ComboStrOption(s, LangConversion.convertId(LangConversion.ST_COMBO, id + "_" + idx, s), s);
	}
}
