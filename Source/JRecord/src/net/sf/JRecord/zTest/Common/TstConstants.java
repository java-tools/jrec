/*
 * @Author Bruce Martin
 * Created on 11/11/2005
 *
 * Purpose:
 */
package net.sf.JRecord.zTest.Common;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.LineIOProvider;




/**
 * Constants for testing
 *
 * @author Bruce Martin
 *
 */
public final class TstConstants {

 //   public static final String BM_DIRECTORY = "/home/bm/Programs/";
 //   public static final String TEMP_DIRECTORY = BM_DIRECTORY + "RecordEdit/Test/";
 //   public static final String BM_DIRECTORY = "C:\\Work\\Temp/";
 //   public static final String RE_DIRECTORY = "C:\\Program Files\\RecordEdit\\HSQL\\";
 //   public static final String BM_DIRECTORY = "/home/knoppix/";
 //   public static final String RE_DIRECTORY = "/media/sdc1/RecordEditor/USB/";
 //   public static final String TEMP_DIRECTORY = BM_DIRECTORY;
    public static final String BM_DIRECTORY = "/home/knoppix/";
    public static final String RE_DIRECTORY = "C:\\Users\\mum\\RecordEditor_HSQL\\";
    public static final String TEMP_DIRECTORY = "C:\\Temp\\RecordEditorTest\\";
    public static final int    DB_INDEX       = 0;
    public static final String SAMPLE_DIRECTORY = RE_DIRECTORY + "SampleFiles/";
    public static final String COBOL_DIRECTORY = RE_DIRECTORY + "CopyBook/Cobol/";
    public static final String RECORD_EDITOR_XML_DIRECTORY = RE_DIRECTORY + "CopyBook/Xml/";

    public static final String CSV_DIRECTORY = RE_DIRECTORY + "CopyBook/csv/";
    public static final String CSV_DIRECTORY_OUTPUT = RE_DIRECTORY + "CopyBook/csv/";
    public static final String XML_DIRECTORY = RE_DIRECTORY + "CopyBook/Xml/";
    public static final String XML_DIRECTORY_OUTPUT = RE_DIRECTORY + "CopyBook/Xml/";

//    public static final String COBOL_TEST_DIR = BM_DIRECTORY + "open-cobol-1.0/CobolSrc/";

    public static final String COBOL_TEST_DIR = "C:\\Users\\mum\\Bruce\\CobolSrc\\";

    public static final String TAB_CSV_LAYOUT =
			  "<?xml version=\"1.0\" ?>"
			+ "<RECORD RECORDNAME=\"Tab Delimited, names on the first line\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" DESCRIPTION=\"Tab Delimited, names on the first line\" FILESTRUCTURE=\"CSV_NAME_1ST_LINE\" STYLE=\"0\" RECORDTYPE=\"Delimited\" LIST=\"Y\" QUOTE=\"\" RecSep=\"default\">"
			+ "	<FIELDS>"
			+ "		<FIELD NAME=\"Dummy\" DESCRIPTION=\"1 field is Required for the layout to load\" POSITION=\"1\" TYPE=\"Char\"/>"
			+ "	</FIELDS>"
			+ "</RECORD>";

    public static final String TAB_CSV_LAYOUT_NUM =
			  "<?xml version=\"1.0\" ?>"
			+ "<RECORD RECORDNAME=\"Tab Delimited, names on the first line\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" DESCRIPTION=\"Tab Delimited, names on the first line\" FILESTRUCTURE=\"CSV_NAME_1ST_LINE\" STYLE=\"0\" RECORDTYPE=\"Delimited\" LIST=\"Y\" QUOTE=\"\" RecSep=\"default\">"
			+ "	<FIELDS>"
			+ "		<FIELD NAME=\"Dummy\" DESCRIPTION=\"1 field is Required for the layout to load\" POSITION=\"1\" TYPE=\"Num (Left Justified)\"/>"
			+ "	</FIELDS>"
			+ "</RECORD>";

    /**
     * tst constants
     */
    private TstConstants() {
        super();
    }



	public static ArrayList<AbstractLine<LayoutDetail>> getLines(LayoutDetail layout, String[] lines) throws Exception {
		ArrayList<AbstractLine<LayoutDetail>> ret = new ArrayList<AbstractLine<LayoutDetail>>();

		AbstractLineReader r = LineIOProvider.getInstance().getLineReader(layout.getFileStructure());
		AbstractLine<LayoutDetail> l;

		r.open(getFile(lines), layout);

		while ((l = r.read()) != null) {
			ret.add(l);
		}

		return ret;
	}


	public static InputStream getFile(String[] lines) {
		StringBuilder b = new StringBuilder();

		for (String l : lines) {
			b.append(l).append("\n");
		}

		return new ByteArrayInputStream(b.toString().getBytes());
	}
}
