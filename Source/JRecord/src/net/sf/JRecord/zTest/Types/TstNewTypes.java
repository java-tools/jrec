package net.sf.JRecord.zTest.Types;

import junit.framework.TestCase;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.Line;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.RecordEditorXmlLoader;
import net.sf.JRecord.Types.Type;
import net.sf.JRecord.Types.TypeManager;
import net.sf.JRecord.Types.TypeNum;


public class TstNewTypes extends TestCase {

	private static final String DTAR020
			= "<?xml version=\"1.0\" ?>"
			+ "<RECORD RECORDNAME=\"DTAR020\" COPYBOOK=\"DTAR020\" DELIMITER=\"&lt;Tab&gt;\" "
			+ "        FONTNAME=\"\" FILESTRUCTURE=\"Default\" STYLE=\"0\" RECORDTYPE=\"RecordLayout\""
			+ "	LIST=\"Y\" QUOTE=\"\" RecSep=\"default\">"
			+ "	<FIELDS>"
			+ "		<FIELD NAME=\"KEYCODE-NO\" POSITION=\"1\"  LENGTH=\"8\" TYPE=\"Char\" />"
			+ "		<FIELD NAME=\"STORE-NO\"   POSITION=\"9\"  LENGTH=\"2\" TYPE=\"Mainframe Packed Decimal (comp-3)\" />"
			+ "		<FIELD NAME=\"DATE\"       POSITION=\"11\" LENGTH=\"4\" TYPE=\"Mainframe Packed Decimal (comp-3)\" />"
			+ "		<FIELD NAME=\"DEPT-NO\"    POSITION=\"15\" LENGTH=\"2\" TYPE=\"Mainframe Packed Decimal (comp-3)\" />"
			+ "		<FIELD NAME=\"QTY-SOLD\"   POSITION=\"17\" LENGTH=\"5\" TYPE=\"Mainframe Packed Decimal (comp-3)\" />"
			+ "		<FIELD NAME=\"SALE-PRICE\" POSITION=\"22\" LENGTH=\"6\" DECIMAL=\"2\" TYPE=\"Mainframe Packed Decimal (comp-3)\" />"
			+ "	</FIELDS>"
			+ "</RECORD>";


	final int[][] typesToTest = {
			{Type.ftNumAnyDecimal,    Type.ftPositiveNumAnyDecimal, },
			{Type.ftNumZeroPadded,    Type.ftNumZeroPaddedPositive, Type.ftNumZeroPaddedPN,},
			{Type.ftNumCommaDecimal,  Type.ftNumCommaDecimalPositive, Type.ftNumCommaDecimalPN,},
			{Type.ftAssumedDecimal,   Type.ftAssumedDecimalPositive, },
			{Type.ftNumRightJustCommaDp, -1, Type.ftNumRightJustCommaDpPN,},
	};
	final FieldDetail[][] fields = new FieldDetail[typesToTest.length][];

	{
		int decimal = 0;
		int xtra = 0;
		for (int i = 0; i < typesToTest.length; i++) {
			fields[i] = new FieldDetail[typesToTest[i].length];
			for (int j = 0; j <  typesToTest[i].length; j++) {
				if ( typesToTest[i][j] < 0) {
					fields[i][j] = null;
				} else {
					fields[i][j] = new FieldDetail(
							"Type " + typesToTest[i][j] , "", typesToTest[i][j], decimal, "", 0, "");
					fields[i][j].setPosLen(1, 8 + decimal + xtra);
					System.out.println(i + " " + (i >= 1) + " " + decimal + " " + (8 + decimal + xtra) + " " + fields[i][j].getLen());
				}
			}
			System.out.println(i + " " + (i >= 1));
			if (i >= 1) {
				decimal = 4;
				xtra = 1;
			}

		}
		System.out.println();
		System.out.println();
	}

	String[][][] data2 = {
			{{"1234"},     {"    1234",    "1234",    "1234.0000",     "1234.0000", "    1234,0000"},
				           {"    1234", "0001234", "0001234,0000",  "000012340000", "    1234,0000"},},
			{{"7"},        {"       7",       "7",       "7.0000",        "7.0000", "       7,0000"},
				           {"       7", "0000007", "0000007,0000", "000000070000", "       7,0000"},},
			{{"0"},        {"       0",       "0",       "0.0000",        "0.0000", "       0,0000"},
				           {"       0", "0000000", "0000000,0000", "000000000000", "       0,0000"},},
			{{"1231234"},  {" 1231234", "1231234", "1231234.0000",  "1231234.0000", " 1231234,0000"},
				           {" 1231234", "1231234", "1231234,0000", "012312340000", " 1231234,0000"},},
			{{"-1234"},    {"   -1234",   "-1234",   "-1234.0000",    "-1234.0000", "   -1234,0000"},
				           {"    1234", "0001234", "0001234,0000", "000012340000", "   -1234,0000"},},
			{{"-1231234"}, {"-1231234", "-1231234", "-1231234.0000",  "-1231234.0000", "-1231234,0000"},
				           {" 1231234",  "1231234",  "1231234,0000",  "012312340000", "-1231234,0000"},},
	};


    String[][][] data3 = {
            {{"1234.12"},     {   "1234.1200",     "1234.1200", "    1234,1200"},
                              {"0001234,1200", "000012341200", "    1234,1200"}},
            {{"7.12"},        {      "7.1200",        "7.1200", "       7,1200"},
                              {"0000007,1200", "000000071200", "       7,1200"}},
            {{"0.12"},        {      "0.1200",        "0.1200", "       0,1200"},
                              {"0000000,1200", "000000001200", "       0,1200"}},
            {{"1231234.12"},  {"1231234.1200",  "1231234.1200", " 1231234,1200"},
                              {"1231234,1200", "012312341200", " 1231234,1200"}},
            {{"-1234.12"},    {  "-1234.1200",    "-1234.1200", "   -1234,1200"},
                              {"0001234,1200", "000012341200", "   -1234,1200"}},
            {{"-1231234.12"}, {"-1231234.1200",  "-1231234.1200", "-1231234,1200"},
                              { "1231234,1200",  "012312341200", "-1231234,1200"}},
            {{"1231234.1234"},  {"1231234.1234",  "1231234.1234", " 1231234,1234"},
                                {"1231234,1234", "012312341234", " 1231234,1234"}},
            {{"0.1234"},        {      "0.1234",        "0.1234", "       0,1234"},
                                {"0000000,1234", "000000001234", "       0,1234"}},
            {{"-1234.1234"},    {  "-1234.1234",    "-1234.1234", "   -1234,1234"},
                                {"0001234,1234", "000012341234", "   -1234,1234"}},
            {{"-1231234.1234"}, {"-1231234.1234",  "-1231234.1234",  "-1231234,1234"},
                                { "1231234,1234",  "012312341234",  "-1231234,1234"}},
            {{"0.12345"},       {      "0.1234",        "0.1234", "       0,1234"},
                                {"0000000,1234", "000000001234", "       0,1234"}},
            {{"-1234.12345"},   {  "-1234.1234",    "-1234.1234", "   -1234,1234"},
                                {"0001234,1234", "000012341234", "   -1234,1234"}},
            {{"-1231234.12345"},{"-1231234.1234",  "-1231234.1234",  "-1231234,1234"},
                                { "1231234,1234",  "012312341234",  "-1231234,1234"}},
    };


	public void test1() {
		TypeManager tm = TypeManager.getInstance();
		Type t;
		int tc;
		for (int i = 0; i < typesToTest.length; i++) {
			for (int j = 0; j <  typesToTest[i].length; j++) {

				tc = typesToTest[i][j];
				if (tc >= 0) {
					t = tm.getType(tc);
					assertTrue("Check numeric: " + tc, t.isNumeric());
					assertFalse("Check Binary: " + tc, t.isBinary());
					assertEquals("Check Positive: " + tc, j == 1, ((TypeNum) t).isPositive());
				}
			}
		}
		System.out.println();
		System.out.println();
	}

	public void test2() throws Exception {
		LayoutDetail layout = getLayout(DTAR020); /* does not matter what the layout is */
		Line l = new Line(layout);
		l.setData("                                  ");

		for (int i = 0; i < fields.length - 1; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				for (int k = 0; k < data2.length; k++) {
					//System.out.println(i + " " + j + " " + (fields[i][j]).getLen() + " "+ l.getFullLine());


					if (data2[k][0][0].startsWith("-") && (j == 1)) {

					} else {
						l.setField(fields[i][j], data2[k][0][0]);
						assertEquals("Get Set: " + i + ", " + j, data2[k][1][i], l.getField(fields[i][j]));
					}
				}
			}
		}

		String c, s, expected;
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				if (fields[i][j] != null) {
					for (int k = 0; k < data2.length; k++) {

						c = "0";
						if (i == 4) {
							c = "";
						} else if (data2[k][0][0].startsWith("-")) {
							c = "-";
						} else if (i == 0) {
							c = "";
						} else if (j > 1 ) {
							c = "+";
						}

						if ("-".equals(c)
						&& (j == 1 || data2[k][2][i].startsWith(" ") )) {

						} else {
							l.setField(fields[i][j], data2[k][0][0]);
							s = l.getFullLine();
							expected = c + data2[k][2][i];
							if (i == 4 && j > 1 && (! data2[k][0][0].startsWith("-"))) {
								int p = expected.lastIndexOf(' ');
								expected = expected.substring(0, p) + "+" + expected.substring(p+1);
								System.out.print('+');
							}
							System.out.println(i + " " + j + " " + k + " >"+ s
									+ " \t >" + expected + "< " + data2[k][0][0].startsWith("-"));
							assertEquals("Check Data: " + i + ", " + j + " " + typesToTest[i][j],
									expected,
									s.substring(0, fields[i][j].getLen()));
						}
					}
				}
			}
		}
		for (int i = 2; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				if (fields[i][j] != null) {
					for (int k = 0; k < data3.length; k++) {

						c = "0";
						if (i == 4) {
							c = "";
						} else if (data3[k][0][0].startsWith("-")) {
							c = "-";
						} else if (i == 0 ) {
							c = "";
						} else if (j > 1 ) {
							c = "+";
						}

						if ("-".equals(c)
						&& (j == 1 || data3[k][2][i-2].startsWith(" ") )) {

						} else {
							expected = c +  data3[k][2][i-2];
							if (i == 4 && j > 1 && (! data3[k][0][0].startsWith("-"))) {
								int p = expected.lastIndexOf(' ');
								expected = expected.substring(0, p) + "+" + expected.substring(p+1);
								System.out.print('+');
							}
							l.setField(fields[i][j], data3[k][0][0]);
							System.out.println(i + " " + j + " " + k + " " + l.getFullLine());
							assertEquals("Check Data: " + i + ", " + j + " " + typesToTest[i][j],
									expected, l.getFullLine().substring(0, fields[i][j].getLen()));
						}
					}
				}
			}
		}
	}

	private LayoutDetail getLayout(String layout) throws RecordException, Exception {
		return getExternalLayout(layout).asLayoutDetail();
	}


	private ExternalRecord getExternalLayout(String layout) throws Exception {
		return RecordEditorXmlLoader.getExternalRecord(layout, "Csv Layout");
	}
}
