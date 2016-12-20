package net.sf.RecordEditor.re.util.csv;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.External.CopybookLoaderFactory;

public class SchemaAnalyser {

	private static final String COBOL_REGEX = ".*\\s*[0-9]{1,2}\\s+[a-z0-9\\-_]+\\s+pic\\s+[xa9\\-\\+s].*";
	private static Pattern cobolPattern;

	public int schemaType(String filename) {
		String lcFilename = filename.toLowerCase();
		int schemaType = -1;
		
		if (lcFilename.endsWith(".cbl") || lcFilename.endsWith(".cob")) {
			schemaType = CopybookLoaderFactory.COBOL_LOADER;
		} else if (lcFilename.endsWith(".xml")) {
			schemaType = CopybookLoaderFactory.RECORD_EDITOR_XML_LOADER;
		} else if (lcFilename.endsWith(".tsv") || lcFilename.endsWith(".retsv")) {
			schemaType = CopybookLoaderFactory.TAB_NAMES_1ST_LINE_LOADER;
		} else if (lcFilename.endsWith(".csv")) {
			schemaType = CopybookLoaderFactory.COMMA_NAMES_1ST_LINE_LOADER;
		}
		
		FileReader f = null;
		char[] chars = new char[12000];
		int pos = 0;
		
		try {
			f = new FileReader(filename);
			
			int num = f.read(chars);
			while (num > 0 && (pos += num) < chars.length) {
				num = f.read(chars, pos, chars.length - pos);
			}
			f.close();
		} catch (FileNotFoundException e) {
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (f != null) {
				try {
					f.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		if (pos > 0) {
			return checkSchemaText(schemaType, new String(chars, 0, pos));
		}
		
		return schemaType;
	}

	public static int checkSchemaText(int schemaType, String s) {
		String lc = s.toLowerCase();
		String sTrim = lc.trim();
		if (sTrim.startsWith("<")) {
			if (sTrim.startsWith("<?")) {
				int idx = sTrim.indexOf("<", 2);
				sTrim = sTrim.substring(idx);
			}
//			System.out.println("b)  *** " + sTrim.substring(0, Math.min(100, sTrim.length()))
//					+ " ~ " + CopybookLoaderFactory.RECORD_EDITOR_XML_LOADER);
			if (sTrim.startsWith("<record ")) {
				return CopybookLoaderFactory.RECORD_EDITOR_XML_LOADER;
			}
//			System.out.println("c)  *** " + sTrim.substring(0, Math.min(100, sTrim.length())));
			if (sTrim.startsWith("<copybook ")) {
				return CopybookLoaderFactory.CB2XML_LOADER;
			}
		}
		
		if (cobolPattern == null) {
			cobolPattern = Pattern.compile(COBOL_REGEX);
		}
		if (cobolPattern.matcher(Conversion.replace(lc, "\n", " "))
				.matches()) {
			return CopybookLoaderFactory.COBOL_LOADER;
		}
		
		int commaCount =0;
		int tabCount = 0;
		int lineCount = 0;
		int lineIdx = -1;
		int idx;
		
		do {
			int lineStart = lineIdx + 1;
			lineIdx = s.indexOf('\n', lineStart);
			idx = s.indexOf('\t', lineStart);
			if (idx >= 0 && idx < lineIdx) {
				tabCount += 1;
			}
			idx = s.indexOf(',', lineStart);
			if (idx >= 0 && idx  < lineIdx) {
				commaCount += 1;
			}
			lineCount += 1;
		} while (lineCount < 100 && lineIdx >= 0);
		
		if (lineIdx < 0) {
			lineCount -= 1;
		}
		
		if (lineCount > 1) {
			if (tabCount * 10 / lineCount > 8) {
				return CopybookLoaderFactory.TAB_NAMES_1ST_LINE_LOADER;
			}
			if (commaCount * 10 / lineCount > 8) {
				return CopybookLoaderFactory.COMMA_NAMES_1ST_LINE_LOADER;
			}
		}
		return schemaType;
	}
	
	
//	public static void main (String[] a) {
//		String regex = ".*\\s*[0-9]{1,2}\\s+[a-zA-Z0-9\\-_]+\\s+pic\\s+[xa9\\-\\+s].*";
//		System.out.println(
//				Pattern.matches(regex, "  pic x(12)")
//				+ " " + Pattern.matches(regex, "  pic 9(12)")
//				+ " " + Pattern.matches(regex, "  picx 9(12)")
//				+ " ! "
//				+ " " + Pattern.matches(regex, " 1 as-d pic x(12)")
//				+ " " + Pattern.matches(regex, " 1 as-d pic 9(12)")
//				+ " " + Pattern.matches(regex, " 1  as-d pic 9(12)")
//				+ " " + Pattern.matches(regex, " 1 as-d  pic 9(12)")
//				+ " " + Pattern.matches(regex, " 1\t as-d     pic 9(12)")
//				+ " " + Pattern.matches(regex, " 1 as-d picx 9(12)")
//				+ " ! "
//				+ " " + Pattern.matches(regex, " 10 as-d pic x(12)")
//				+ " " + Pattern.matches(regex, " 10 as-d pic 9(12)")
//				+ " " + Pattern.matches(regex, " 10  as-d pic 9(12)")
//				+ " " + Pattern.matches(regex, " 10 as-d  pic 9(12)")
//				+ " " + Pattern.matches(regex, " 10\t as-d     pic 9(12)")
//				+ " " + Pattern.matches(regex, " 10 as-d picx 9(12)")
//				+ " ! "
//				+ " " + Pattern.matches(regex, " 01 as-d pic x(12)")
//				+ " " + Pattern.matches(regex, " 01 as-d  pic 9(12)")
//				+ " " + Pattern.matches(regex, " 01 as-d  picx 9(12)")
//			
//				);
//		
//		regex = ".*\\s*[0-9]{1,2}\\s+[a-zA-Z0-9\\-_]+\\sPIC\\s+[XA9\\-\\+s].*";
//		regex = ".*\\sPIC\\s.*";
//		
//		System.out.println(
//					Pattern.matches(regex, " PIC ") + " " +
//					Pattern.matches(regex, "  PIC  ") + " " +
//					Pattern.matches(regex, "x PIC x") + " " +
//					Pattern.matches(regex, " x PIC x") + " " +
//					Pattern.matches(regex, "                05 RD430-PROD-NO                 PIC 9(14).") + " " +
//					Pattern.matches(regex, "                05 RD430-PROD-NO                 PIC 9(14).\n") + " ");
//		System.out.println(
//					Pattern.matches(regex,
//						Conversion.replace(
//						"000900        03  DTAR020-KCODE-STORE-KEY. \n" +                                     
//						"001000            05 DTAR020-KEYCODE-NO      PIC X(08).\n" +                       
//						"001100            05 DTAR020-STORE-NO        PIC S9(03)   COMP-3.  \n" +          
//						"001200        03  DTAR020-DATE               PIC S9(07)   COMP-3.  \n", "\n", " ")             
//					) + " " +
//					Pattern.matches(regex,
//                     Conversion.replace(
//							"000900    01  DTAR020. \n" +                                     
//							"000900        03  DTAR020-KCODE-STORE-KEY. \n" +                                     
//							"001000            05 DTAR020-KEYCODE-NO      PIC X(08).\n" +                       
//							"001100            05 DTAR020-STORE-NO        PIC S9(03)   COMP-3.  \n" +          
//							"001200        03  DTAR020-DATE               PIC S9(07)   COMP-3.  \n", "\n", " ")             
//					)  + " " +
//
//					Pattern.matches(regex,
//	                    Conversion.replace(
//						"000900    01  DTAR020. \n" +                                     
//								"001100            05 DTAR020-STORE-NO        PIC S9(03)   COMP-3.  \n" +          
//								"001200        03  DTAR020-DATE               PIC S9(07)   COMP-3.  \n" , "\n", " ")            
//					)  + " " +
//					Pattern.matches(regex,
//  Conversion.replace(
//							"        01  RD430-PROD-RECORD.\n" + 
//							"            03  RD430-PROD-NO-X.\n" +                                      
//							"                05 RD430-PROD-NO                 PIC 9(14).\n" +       
//							"            03  RD430-PROD-BRAND-ID              PIC X(3).\n" +            
//							"                88 RD430-PROD-BRAND-COLES        VALUE 'TAR'.\n"  , "\n", " ")           
//						)  + " " +
//					Pattern.matches(regex,
//  Conversion.replace(
//							"                05 RD430-PROD-NO                 PIC 9(14).\n" +       
//							"            03  RD430-PROD-BRAND-ID              PIC X(3).\n" +            
//							"                88 RD430-PROD-BRAND-COLES        VALUE 'TAR'.\n", "\n", " ")             
//						)
//				);
//	}
}
