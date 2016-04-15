package net.sf.RecordEditor.interfacePgms;

import net.sf.JRecord.zTest.Common.TstConstants;
import net.sf.RecordEditor.edit.FullEditor;
import net.sf.RecordEditor.utils.edit.ParseArgs;


public class EditMultipleFiles {
	public static void main(final String[] pgmArgs) {
		ParseArgs[] args = {
			new ParseArgs(TstConstants.SAMPLE_DIRECTORY + "DTAR020.bin", "DTAR020", 5),
			new ParseArgs(TstConstants.SAMPLE_DIRECTORY + "Ams_LocDownload_20041228.txt", "ams Store", 10),
			new ParseArgs(TstConstants.SAMPLE_DIRECTORY + "Ams_PODownload_20041231.txt", "ams PO Download", 0),
		};
		FullEditor.startEditor(args);
	}
}
