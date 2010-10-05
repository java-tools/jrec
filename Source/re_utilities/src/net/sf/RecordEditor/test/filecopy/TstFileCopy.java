package net.sf.RecordEditor.test.filecopy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import junit.framework.TestCase;

import net.sf.JRecord.ByteIO.FixedLengthByteReader;
import net.sf.JRecord.zTest.Common.TstConstants;
import net.sf.RecordEditor.copy.BatchCopy;
import net.sf.RecordEditor.utils.openFile.LayoutSelectionFile;

public class TstFileCopy extends TestCase {

	private static String testDir = TstConstants.TEMP_DIRECTORY + "FileCopyTst/";
	
	/* JRecord / RecordEditor Install directory */
	private static String InstallDir = "/home/bm/Programs/RecordEdit/HSQLDB/";
	
	public static final String DataIn    = testDir + WriteFiles.DataIn + "/";
	public static final String DataOut   = testDir + WriteFiles.DataOut + "/";
	public static final String Copybook  = testDir + WriteFiles.Copybook + "/";
	public static final String CopyParms = testDir + WriteFiles.CopyParms + "/";
	
	public static final LayoutSelectionFile selection1 = new LayoutSelectionFile();
	public static final LayoutSelectionFile selection2 = new LayoutSelectionFile();
	
	static {
		WriteFiles.writeFiles(testDir);
	}
	
	public void testCsv2Fixed() throws IOException {
		String[] prm1 = {
				CopyParms + "CopyCsvN2Fixed.xml",
				"-i",  DataIn   + "x_Customer_cn.csv",
				"-il", Copybook + "Comma_Delimited_names_on_the_first_line.Xml~2~0~0~0~0~0",
				"-o",  DataOut  + "Customer_From_csv_cn1.txt",
				"-ol", Copybook + "x_Customer_Details.Xml~2~0~0~0~0~0"
		};
		String[] prm2 = {
				CopyParms + "CopyCsv2Fixed1.xml",
				"-i",  DataIn   + "x_Customer.csv",
				"-il", Copybook + "x_Customer_Details_Csv.Xml~2~0~0~0~0~0",
				"-o",  DataOut  + "Customer_From_tabCsv.txt",
				"-ol", Copybook + "x_Customer_Details.Xml~2~0~0~0~0~0"
		};


		tstCopy(prm1, DataIn + "x_Customer.txt", "Csv2 Fixed 1");
		tstCopy(prm2, DataIn + "x_Customer.txt", "Csv2 Fixed 2");
	}
	
	
	public void testXml2Fixed() throws IOException {
		String[] prm1 = {
				CopyParms + "CopyXml2Fixed.xml",
				"-i",  DataIn   + "x_Customer.xml",
				"-il", Copybook + "XML - Build Layout.Xml~2~0~0~0~0~0",
				"-o",  DataOut  + "Customer_From_Xml1.txt",
				"-ol", Copybook + "x_Customer_Details.Xml~2~0~0~0~0~0"
		};
		String[] prm2 = {
				CopyParms + "CopyXml2Fixed.xml",
				"-i",  DataIn   + "x_Customer.xml",
				"-il", Copybook + "Description_Of_CustomerXmlFile.Xml~2~0~0~0~0~0",
				"-o",  DataOut  + "Customer_From_Xml2.txt",
				"-ol", Copybook + "x_Customer_Details.Xml~2~0~0~0~0~0"
		};
		String[] prm3 = {
				CopyParms + "CpyTabDelimToDTAR020.xml",
				"-i",  DataIn   + "x_Customer.xml",
				"-il", Copybook + "Tab Delimited names on the first line.Xml~2~0~0~0~0~0",
				"-o",  DataOut  + "DTAR020_Extract.bin.txt",
				"-ol", Copybook + "DTAR020.cbl~1~4~0~1~0~0"
		};
		
		tstCopy(prm1, DataIn + "x_Customer.txt", "Xml 2 Fixed Copy 1 ");
		tstCopy(prm2, DataIn + "x_Customer.txt", "Xml 2 Fixed Copy 2");
		tstCopyBin(prm3, InstallDir + "/SampleFiles/DTAR020_Extract.bin", "Xml 2 Fixed Copy 3", 20, 3);
	}

	
	public void testFixed2Csv() throws IOException {
		String[] prm1 = {
				CopyParms + "CopyFixed2Csv.xml",
				"-i",  DataIn   + "x_Customer.txt",
				"-il", Copybook + "x_Customer_Details.Xml~2~0~0~0~0~0",
				"-o",  DataOut  + "Csv_Customer_From_Fixed.csv",
				"-ol", Copybook + "x_Customer_Details_Csv.Xml~2~0~0~0~0~0"
		};
		String[] prm2 = {
				CopyParms + "CpyDTAR020toTabDelim.xml",
				"-i",  InstallDir + "/SampleFiles/DTAR020_Extract.bin",
				"-il", Copybook + "DTAR020.cbl~1~4~0~1~0~0",
				"-o",  DataOut  + "CsvTAR020tabDelim.txt",
		};
		//    ./runBatchCopy.sh ./CopyParms/CopyFixed2Csv.xml

		tstCopy(prm1, DataIn + "x_Customer.csv", "Fixed 2 Csv1 Copy", "\t", 3);
		tstCopy(prm2, DataIn + "CsvTAR020tabDelim.txt", "Fixed 2 Csv2 Copy", "\t", 1);

	}

	
	public void testFixed2Xml() throws IOException {
		String[] prm1 = {
				CopyParms + "CopyFixed2Xml.xml",
				"-i",  DataIn   + "x_Customer.txt",
				"-il", Copybook + "x_Customer_Details.Xml~2~0~0~0~0~0",
				"-o",  DataOut  + "Xml_Customer_From_Fixed.xml",
				"-ol", Copybook + "Description_Of_CustomerXmlFile.Xml~2~0~0~0~0~0"
		};
		String[] prm2 = {
				CopyParms + "CpyDTAR020toXml.xml",
				"-i",  InstallDir + "/SampleFiles/DTAR020_Extract.bin",
				"-il", Copybook + "DTAR020.cbl~1~4~0~1~0~0",
				"-o",  DataOut  + "xmlDtar020.xml",
		};
		//    ./runBatchCopy.sh ./CopyParms/CopyFixed2Csv.xml

		tstCopy(prm1, DataIn + "CustomerXml_From_Fixed.xml", "Fixed 2 Xml1 Copy");
		tstCopy(prm2, DataIn + "xmlDtar020.xml", "Fixed 2 Xml2 Copy", "\t", 1);
	}

	public void tstCopy(String[] prms, String cmpFile, String text) throws IOException {
		tstCopy(prms, cmpFile, text, null, 3);
	}

	public void tstCopy(String[] prms, String cmpFile, String text, 
			String startChar, int dataOutAdjust) throws IOException {
		String outfile = prms[prms.length - dataOutAdjust];
		BufferedReader reader1, reader2;
		int i = 1;
		String s1, s2;
		boolean error = false;
		
		new BatchCopy(selection1, selection2, prms);
		
		reader1 = new BufferedReader(new FileReader(outfile));
		reader2 = new BufferedReader(new FileReader(cmpFile));
		
		System.out.println("Comparing " + outfile);
		System.out.println("       to " + cmpFile);
		
		while (((s1 = reader1.readLine()) != null) & ((s2 = reader2.readLine()) != null)) {
			s1 = fix(s1, startChar);
			s2 = fix(s2, startChar);
			if (! s1.equals(s2)) {
				if (! error) {
					System.out.println();
					System.out.println("Error in " + text + " " + s1.length() + " " + s2.length()
							+ " " + s1.indexOf("\0")); 
				}
				
				System.out.println(" Line:\t" + i + "\t" + s1 + "<<<<");
				System.out.println("     :\t\t" + s2 + "<<<<");
				System.out.print("     :\t\t");
				for (int j = 0; j < Math.min(s1.length(), s2.length()); j++) {
					if (s1.substring(j,j+1).equals(s2.substring(j,j+1))) {
						System.out.print(" ");
					} else {
						System.out.print("^");
					}
				}
				System.out.println();
				System.out.println();
				error = true;
			}
			i += 1;
		}
		
		reader1.close();
		reader2.close();
		
		assertFalse("Errors in " + text, error);
	}
	
	private String fix(String s, String startChar) {
		
		if (s.indexOf('\0') >= 0) {
			//System.out.println(s);
			StringBuffer b = new StringBuffer(s);
			for (int i = 0; i < b.length(); i++) {
				if (b.charAt(i) == '\0') {
					b.replace(i, i+1, " ");
				}
			}
			s = b.toString();
			
			//System.out.println(s);
		}
		
		if (startChar != null && s.indexOf(startChar) > 0) {
			s = s.substring(s.indexOf(startChar));
		}
		
		return s;
	}
	
	public void tstCopyBin(String[] prms, String cmpFile, String text, 
			int len, int dataOutAdjust) throws IOException {
		String outfile = prms[prms.length - dataOutAdjust];
		FixedLengthByteReader reader1, reader2;
		int i = 1;
		byte[] s1, s2;
		boolean error = false;
		
		new BatchCopy(selection1, selection2, prms);
		
		reader1 = new FixedLengthByteReader(len);
		reader2 = new FixedLengthByteReader(len);
			
		reader1.open(outfile);
		reader2.open(cmpFile);
		
		System.out.println("Comparing " + outfile);
		System.out.println("       to " + cmpFile);
		
		while (((s1 = reader1.read()) != null) & ((s2 = reader2.read()) != null)) {

			if (! s1.equals(s2)) {
				if (! error) {
					System.out.println();
					System.out.println("Error in " + text); 
				}
				
				System.out.println(" Line:\t" + i + "\t" + s1 + "<<<<");
				System.out.println("     :\t\t" + s2 + "<<<<");
				System.out.print("     :\t\t");
//				for (int j = 0; j < len; j++) {
//					if (s1[j] == s2[j]) {
//						System.out.print(" ");
//					} else {
//						System.out.print("^");
//					}
//				}
				System.out.println();
				System.out.println();
				error = true;
			}
			i += 1;
		}
		
		reader1.close();
		reader2.close();
		
		assertFalse("Errors in " + text, error);
	}

}
