/*
 * @Author Bruce Martin
 * Created on 29/01/2007
 *
 * Purpose:
 * Provides a Combobox  that lists all Computer/Compiler
 * options
 *
 * Changes
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - Changed to get options from ConversionManager
 *
 */
package net.sf.RecordEditor.re.openFile;

import javax.swing.JComboBox;

import net.sf.JRecord.Numeric.ConversionManager;
import net.sf.RecordEditor.utils.lang.LangConversion;

/**
 * Provides a Combobox  that lists all Computer/Compiler
 * options
 *
 * @author Bruce Martin
 *
 */
public class ComputerOptionCombo extends JComboBox {

    private static final String[] COMPUTER_OPTIONS; //= {"Intel ", "Mainframe ", "Big-Endian", "Fujitsu"};
    private static final String[] COMPUTER_OPTIONS_FOREIGN; //= {"Intel ", "Mainframe ", "Big-Endian", "Fujitsu"};
   private static final int[] COMPUTER_CONVERSION;
//    = {
//            XmlCopybookLoaderDB.FMT_INTEL,
//            XmlCopybookLoaderDB.FMT_MAINFRAME,
//            XmlCopybookLoaderDB.FMT_BIG_ENDIAN,
//            XmlCopybookLoaderDB.FMT_FUJITSU
//    };

    static {
        ConversionManager manager = ConversionManager.getInstance();
        int count = manager.getNumberOfEntries();
        String[] tmpOptions = new String[count];
        String[] tmpForeignOptions = new String[count];
        int[] tmpConversions = new int[count];
        String mgrName = manager.getManagerName();

        for (int i = 0; i < count; i++) {
            tmpOptions[i] = manager.getName(i);
            tmpConversions[i] = manager.getKey(i);
            tmpForeignOptions[i] = LangConversion.convertId(
            		LangConversion.ST_COMBO, mgrName + "_" + tmpConversions[i], tmpOptions[i]);
        }

        COMPUTER_OPTIONS = tmpOptions;
        COMPUTER_OPTIONS_FOREIGN = tmpForeignOptions;
        COMPUTER_CONVERSION = tmpConversions;
    }

    /**
     * Combobox to display machine format
     */
    public ComputerOptionCombo() {
        super(COMPUTER_OPTIONS_FOREIGN);
    }


    /**
     * Get the current selected machine Format
     * @return selected machine Format
     */
    public int getSelectedValue() {
        return COMPUTER_CONVERSION[this.getSelectedIndex()];
    }



	public void setEnglishText(Object itm) {
		if (itm != null) {
			String s = itm.toString();
			for (int i = 0; i < COMPUTER_OPTIONS.length; i++) {
				if (s.equals(COMPUTER_OPTIONS[i])) {
					super.setSelectedItem(COMPUTER_OPTIONS_FOREIGN[i]);
					return;
				}
			}
		}
		super.setSelectedItem(itm);
	}


}
