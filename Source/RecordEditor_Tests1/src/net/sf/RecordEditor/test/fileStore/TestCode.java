package net.sf.RecordEditor.test.fileStore;

import net.sf.JRecord.Details.CharLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.Line;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.fileStorage.DataStoreLarge;
import net.sf.RecordEditor.utils.fileStorage.FileDetails;

public class TestCode {


	public final static LayoutDetail schema = StdSchemas.TWENTY_BYTE_RECORD_SCHEMA;

 
	@SuppressWarnings("rawtypes")
	public static void load(FileView r, int size, String s) {
		
		if (r.getLines() instanceof DataStoreLarge && ((DataStoreLarge) r.getLines()).getFileDetails().type == FileDetails.CHAR_LINE) {
			for (int i = 0; i < size; i++) {
				r.addLine(i, new CharLine(schema , lineVal(i, s)));
			}
		} else {
			for (int i = 0; i < size; i++) {
				r.addLine(i, new Line(schema , lineVal(i, s)));
			}
		}
	}

	public static String lineVal(int i, String s) {
		return ("line " + i + s + "                      ").substring(0, 20);
	}
	

}
