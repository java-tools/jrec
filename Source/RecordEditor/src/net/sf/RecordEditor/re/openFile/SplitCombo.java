/*
 * @Author Bruce Martin
 * Created on 29/01/2007
 *
 * Purpose:
 * A Combo box listing Copybook split options.
 */
package net.sf.RecordEditor.re.openFile;

import javax.swing.JComboBox;

import net.sf.JRecord.External.CopybookLoader;
import net.sf.RecordEditor.utils.lang.LangConversion;

/**
 * A Combo box listing Copybook split options.
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class SplitCombo extends JComboBox {

    private static final String[] SPLIT_OPTIONS = LangConversion.convertComboItms(
			"Cobol Split options",
			new String[] {"No Split", "On Redefine", "On 01 level"});
    private static final int[] SPLIT_CONVERSION = {
            CopybookLoader.SPLIT_NONE,
            CopybookLoader.SPLIT_REDEFINE,
            CopybookLoader.SPLIT_01_LEVEL
    };

    /**
     * Combobox to display machine format
     */
    public SplitCombo() {
        super(SPLIT_OPTIONS);
    }


    /**
     * Get the current selected machine Format
     * @return selected machine Format
     */
    public int getSelectedValue() {
        return SPLIT_CONVERSION[this.getSelectedIndex()];
    }
}
