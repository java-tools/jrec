package net.sf.RecordEditor.testcode;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.CobolIoProvider;
import net.sf.JRecord.Numeric.Convert;

public class ReadUserFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String copybookDir = "/home/bm/Work/Temp/User/";
		int lineNum = 0;
		AbstractLine saleRecord;
		try {
			String dataInputFileName = "Copie_de_A01.txt"; //dataInputFile.getName();
			String copybookName = copybookDir + "A01.cbl";
			//copybookName = copybookName.replace(".txt", ".cbl");
			System.out.println(copybookName + " >> " + copybookDir + dataInputFileName
					+ " " + new File(copybookName).exists());
			if (new File(copybookName).exists()) {
				int fileStructure = Constants.IO_FIXED_LENGTH;
				CobolIoProvider ioProvider = CobolIoProvider.getInstance();
				AbstractLineReader reader = ioProvider.getLineReader(
						fileStructure, Convert.FMT_MAINFRAME,
						CopybookLoader.SPLIT_NONE, copybookName,
						copybookDir + dataInputFileName);



				List columnNamesList = Arrays.asList(new String[]{"NPDV","DRMTCA","CDPCOM","CRAYON","MCARAY","CFSITU","MCARA2","CPAYS","CDEVIS","NRGCOM","X20X00"});

				while ((saleRecord = reader.read()) != null) {
					lineNum += 1;
					System.out.println();
					System.out.print(lineNum + " >> ");
					String dataOutputLine = "";
					for (Iterator iterator3 = columnNamesList.iterator(); iterator3
					.hasNext();) {
						String columnName = (String) iterator3.next();
						String columnValue = saleRecord.getFieldValue(
								columnName).asString();

						System.out.print(columnValue + ", ");
					}
					
					try {
					System.out.print(saleRecord.getFieldValue(
							"MCARAY").asBigDecimal());
					} catch (Exception e) {
						// TODO: handle exception
					}
				}

				reader.close();
			}
			// writer.close();
		} catch (Exception e) {
			System.out.println("~~> " + lineNum + " " + e.getMessage());
			System.out.println();

			e.printStackTrace();
		}
	}

}
