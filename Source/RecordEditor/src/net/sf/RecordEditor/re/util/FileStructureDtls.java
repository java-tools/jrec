package net.sf.RecordEditor.re.util;

import javax.swing.JComboBox;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.swing.Combo.ComboOption;

public class FileStructureDtls {
	public static enum RowsAllowed {
		ONE_ROW,
		ONE_ROW_USED,
		MANY,
	}
	private static FileStructureOption[] fileStructureOptions;

	static {
		int [] structures = {Constants.IO_VB, Constants.IO_VB_DUMP, Constants.IO_VB_FUJITSU, Constants.IO_VB_GNU_COBOL,
				Constants.IO_FIXED_LENGTH, Constants.IO_BIN_TEXT, Constants.IO_UNICODE_TEXT,
				Constants.IO_CONTINOUS_NO_LINE_MARKER, Constants.IO_VB, };
		LineIOProvider p = ReIOProvider.getInstance();
		int idx = 0;

		fileStructureOptions = new FileStructureOption[structures.length];

		for (int structure : structures) {
			fileStructureOptions[idx++] = new FileStructureOption(
											structure,
											LangConversion.convertId(
													LangConversion.ST_EXTERNAL,
													p.getManagerName() + "_" + structure,
													p.getInternalStructureName(structure)),
											RowsAllowed.MANY,
											null);
		}
	};


	/**
	 * @param fileStructureOptions the fileStructureOptions to set
	 */
	public static final void setFileStructureOptions(
			FileStructureOption[] fileStructureOptions) {
		FileStructureDtls.fileStructureOptions = fileStructureOptions;
	}


	public static String getStructureName(int structure) {
		LineIOProvider p = ReIOProvider.getInstance();
		return p.getInternalStructureName(structure);
	}


	public static int getComboIndex(int structure) {
		for (int i = 0 ; i < fileStructureOptions.length; i++) {
			if (fileStructureOptions[i].index == structure) {
				return i;
			}
		}
		return 0;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static JComboBox getFileStructureCombo() {
		return new JComboBox(fileStructureOptions);
	}
	
	public static class FileStructureOption extends ComboOption {
		public final RowsAllowed rowsAllowed;
		public final String extension;

		public FileStructureOption(int idx, String str, RowsAllowed rowsAllowed, String extension) {
			super(idx, str);
			
			this.rowsAllowed = rowsAllowed;
			this.extension = extension;
		}
		
	}
}
