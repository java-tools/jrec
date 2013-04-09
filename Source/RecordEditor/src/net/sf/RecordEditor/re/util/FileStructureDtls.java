package net.sf.RecordEditor.re.util;

import javax.swing.JComboBox;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.swing.Combo.ComboOption;

public class FileStructureDtls {
	private static final ComboOption[] FILE_STRUCTURES;

	static {
		int [] structures = {Constants.IO_VB, Constants.IO_VB_DUMP, Constants.IO_VB_FUJITSU, Constants.IO_VB_OPEN_COBOL,
				Constants.IO_FIXED_LENGTH, Constants.IO_BIN_TEXT};
		LineIOProvider p = ReIOProvider.getInstance();
		int idx = 0;

		FILE_STRUCTURES = new ComboOption[structures.length];

		for (int structure : structures) {
			FILE_STRUCTURES[idx++] = new ComboOption(
											structure,
											LangConversion.convertId(
													LangConversion.ST_EXTERNAL,
													p.getManagerName() + "_" + structure,
													p.getInternalStructureName(structure)));
		}
	};


	public static String getStructureName(int structure) {
		LineIOProvider p = ReIOProvider.getInstance();
		return p.getInternalStructureName(structure);
	}


	public static int getComboIndex(int structure) {
		for (int i = 0 ; i < FILE_STRUCTURES.length; i++) {
			if (FILE_STRUCTURES[i].index == structure) {
				return i;
			}
		}
		return 0;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static JComboBox getFileStructureCombo() {
		return new JComboBox(FILE_STRUCTURES);
	}
}
