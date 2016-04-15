package net.sf.RecordEditor.interfacePgms;

import net.sf.JRecord.zTest.Common.TstConstants;
import net.sf.RecordEditor.edit.FullEditor;


public class EditFile {
	public static void main(final String[] pgmArgs) {
		FullEditor.startEditor(TstConstants.SAMPLE_DIRECTORY + "DTAR020.bin", "DTAR020", 3);
	}
}
