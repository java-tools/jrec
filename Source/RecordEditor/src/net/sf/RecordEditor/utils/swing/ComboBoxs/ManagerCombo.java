package net.sf.RecordEditor.utils.swing.ComboBoxs;

import net.sf.JRecord.Common.AbstractManager;
import net.sf.JRecord.External.CopybookLoaderFactory;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.swing.Combo.ComboStdOption;

@SuppressWarnings("serial")
public class ManagerCombo extends EnglishCombo<Integer> {

	public static ManagerCombo newCombo(AbstractManager mgr) {
		return new ManagerCombo(mgr);
	}

	public static ManagerCombo newCopybookLoaderCombo() {
		return new ManagerCombo(CopybookLoaderFactory.getInstance());
	}

    private ManagerCombo(AbstractManager mgr) {
        super(new ComboStdOption<Integer>(-1, "", ""));

        String mgrName = mgr.getManagerName();

        for (int i = 0; i < mgr.getNumberOfEntries(); i++) {
        	super.addItem(new ComboStdOption<Integer>(
        			mgr.getKey(i),
        			LangConversion.convertId(LangConversion.ST_EXTERNAL, mgrName + "_" + i, mgr.getName(i)),
        			mgr.getName(i)));
        }
    }

}
