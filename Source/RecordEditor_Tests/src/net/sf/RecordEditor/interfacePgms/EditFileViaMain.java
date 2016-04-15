package net.sf.RecordEditor.interfacePgms;

import net.sf.JRecord.zTest.Common.TstConstants;
import net.sf.RecordEditor.edit.FullEditor;


public class EditFileViaMain {
	public static void main(final String[] pgmArgs) {
		final String[] args = {
				TstConstants.SAMPLE_DIRECTORY + "DTAR020.bin", "-schema", "DTAR020"
		};
		FullEditor.main(args);
	}
}
