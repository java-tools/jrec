package net.sf.RecordEditor.test.filecopy;

import java.io.IOException;
import java.math.BigDecimal;

import junit.framework.Assert;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.Line;
import net.sf.JRecord.External.CobolCopybookLoader;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.AbstractLineWriter;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.JRecord.Log.TextLog;
import net.sf.JRecord.Numeric.ConversionManager;
import net.sf.JRecord.zTest.Common.TstConstants;
import net.sf.RecordEditor.copy.DoCopy;
import net.sf.RecordEditor.jibx.compare.CopyDefinition;
import net.sf.RecordEditor.re.openFile.LayoutSelectionFile;

public class CobolCopyCode {

	private static String primaryVar = "NumA";

	private static String[] vars = {
			"Num2",  "Num3",  "Num4",  "Num5",  "Num6",  "Num7",
			"Num8",  "Num9",  "Num10", "Num11", "Num12", "Num13",
			"Num14", "Num15", "Num16",
	};
	private static double[] len = {
			02, 03, 04, 05, 06, 07, 8, 9,
			10, 11, 12, 13, 14, 15, 16,
	};

	public static void copyAndCompare(String copyBook, boolean positive,
						int binFormat1, String fileName1, String font1,
						int binFormat2, String fileName2, String font2)
	throws Exception {
		LayoutDetail layout1 = (new CobolCopybookLoader())
						.loadCopyBook(TstConstants.COBOL_TEST_DIR + "default/" + copyBook,
									CobolCopybookLoader.SPLIT_NONE, 0, font1, binFormat1, 0, new TextLog())
								.asLayoutDetail();
		LayoutDetail layout2 = (new CobolCopybookLoader())
								.loadCopyBook(TstConstants.COBOL_TEST_DIR + "default/" + copyBook,
											CobolCopybookLoader.SPLIT_NONE, 0, font2, binFormat2, 0, new TextLog())
										.asLayoutDetail();
		writeFile(layout1, binFormat1, fileName1,  font1);

		copyCobol(copyBook, positive,
				 binFormat1,  fileName1,  font1,
				 binFormat2,  fileName2,  font2);

		System.out.println(" --- File Copy, copyBook = " + copyBook
				+ "  From ~ " + ConversionManager.getInstance().getConverter(binFormat1).getName()
				+ "    to ~ " + ConversionManager.getInstance().getConverter(binFormat2).getName());

		compareFiles( positive,
				layout1,  fileName1,
				layout2,  fileName2);
	}


	public static void writeFile(LayoutDetail layout, int binFormat, String fileName, String font)
	throws RecordException, IOException {
		int num= 100;
		int j;
		AbstractLineWriter writer = LineIOProvider.getInstance().getLineWriter(Constants.IO_FIXED_LENGTH);
		Line line = new Line(layout);

		writer.open(TstConstants.TEMP_DIRECTORY + fileName);

		for (int i = -11 ; i < 1001; i++) {
			line.getFieldValue(primaryVar).set(num);
			for (j = 0; j < 18; j++) {
				try {
					line.getFieldValue("Num" + j).set(num);
				} catch (Exception e) {
					line.getFieldValue("Num" + j).set(0);
				}
				line.getFieldValue("sep" + j).set("|");
			}
			writer.write(line);
			num += 1000;
		}

		writer.close();
	}


	public static void copyCobol(String copyBook, boolean positive,
						int binFormat1, String fileName1, String font1,
						int binFormat2, String fileName2, String font2) throws Exception {
		CopyDefinition def = new CopyDefinition();
		LayoutSelectionFile layoutReader1 = new LayoutSelectionFile(true);

		def.type = CopyDefinition.COBOL_COPY;

		System.out.println("\t\t  .... " + TstConstants.TEMP_DIRECTORY + fileName1 + " --> " + TstConstants.TEMP_DIRECTORY + fileName2);

		def.oldFile.name = TstConstants.TEMP_DIRECTORY + fileName1;
		def.oldFile.getLayoutDetails().name = TstConstants.COBOL_TEST_DIR + "default/" + copyBook
				+ ",,1,,0,,0," + binFormat1 +  ",,0,,0";
		def.newFile.name = TstConstants.TEMP_DIRECTORY + fileName2;
		def.newFile.getLayoutDetails().name = TstConstants.COBOL_TEST_DIR + "default/" + copyBook
		+ ",,1,,0,,0," + binFormat2 +  ",,0,,0";

		DoCopy.copy(layoutReader1, layoutReader1, def);
	}


	public static void compareFiles(boolean positive,
			LayoutDetail layout1,  String fileName1,
			LayoutDetail layout2, String fileName2)
	throws RecordException, IOException {

		int i, j;
		BigDecimal refVal1, cmp1, refVal2, cmp2;
		double log;
		AbstractLineReader reader1 = LineIOProvider.getInstance().getLineReader(Constants.IO_FIXED_LENGTH);
		AbstractLineReader reader2 = LineIOProvider.getInstance().getLineReader(Constants.IO_FIXED_LENGTH);
		AbstractLine line1, line2;

		reader1.open(TstConstants.TEMP_DIRECTORY + fileName1, layout1);
		reader2.open(TstConstants.TEMP_DIRECTORY + fileName2, layout2);

		i = 0;
		while ((line1 = reader1.read()) != null && (line2 = reader2.read()) != null) {
			i += 1;
			refVal1 = getStandardVal(line1, positive);
			refVal2 = getStandardVal(line2, positive);
			Assert.assertEquals("Error with refVal line " + i, refVal1, refVal2);

			log = Math.log10(Math.abs(refVal1.doubleValue()));

			for (j = 0; j < len.length; j++) {
				if (len[j] > log) {
					cmp1 = line1.getFieldValue(vars[j]).asBigDecimal();
					cmp2 = line2.getFieldValue(vars[j]).asBigDecimal();

					if (! cmp1.equals(cmp2)) {
						System.out.println( "--> " + i + " \t" + vars[j] + " \t" + log + "\t" + cmp1 + " " + cmp2);
					}
					Assert.assertEquals("Error Line: " + i + " " + vars[j], cmp1, cmp2);
				}
			}
		}

		reader1.close();
		reader2.close();
	}


	private static BigDecimal getStandardVal(AbstractLine line, boolean positive) {
		String s = line.getFieldValue(primaryVar).asString();
		String t;
		StringBuffer b = new StringBuffer("");
		int i;
		BigDecimal ret;

		for (i = 0; i < s.length(); i++) {
			t = s.substring(i, i+1);
			if (! ",".equals(t)) {
				b.append(t);
			}
		}

//		System.out.println(" --> " + s + " ==> " + b.toString());

		ret = new BigDecimal(b.toString());

		if (positive && ret.doubleValue() < 0) {
			ret = ret.multiply(BigDecimal.valueOf(-1));
		}
		return ret;
	}
}
