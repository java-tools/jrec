/*  -------------------------------------------------------------------------
 *
 *            Sub-Project: RecordEditor's version of JRecord 
 *    
 *    Sub-Project purpose: Low-level IO and record translation  
 *                        code + Cobol Copybook Translation
 *    
 *                 Author: Bruce Martin
 *    
 *                License: GPL 2.1 or later
 *                
 *    Copyright (c) 2016, Bruce Martin, All Rights Reserved.
 *   
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU General Public License
 *    as published by the Free Software Foundation; either
 *    version 2.1 of the License, or (at your option) any later version.
 *   
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 * ------------------------------------------------------------------------ */
      
package net.sf.JRecord.zTest.Parser;

import junit.framework.TestCase;
import net.sf.JRecord.CsvParser.BasicCsvLineParserExtended;
import net.sf.JRecord.CsvParser.CsvDefinition;
import net.sf.JRecord.CsvParser.ICsvDefinition;
import net.sf.JRecord.Types.Type;

public class TstBasicParserExtended extends TestCase {

	private static final CsvDefinition LINES0_CSV_DEF = new CsvDefinition("," , "");
	private static final CsvDefinition LINES1_CSV_DEF = new CsvDefinition("," , "'");
	private static final CsvDefinition LINES2_CSV_DEF = new CsvDefinition("|" , "'");
	private static final CsvDefinition LINES3_CSV_DEF = new CsvDefinition("," , "`~");
	private static final CsvDefinition LINES4_CSV_DEF = new CsvDefinition("," , "!`");

	private String[] lines0 = {"field1, field2, field3",
			 "f11,f21,f31",
			 "f12,f22,f32",
			 "f13,f23,f33",
			 "f14,f24,f34",
			 "f15,f25,f35",
			 "f16,f26,f36",
			 "f17,f26,f37",
	};

	private String[] lines1 = {"field1, field2, field3",
							 "f11,'pt1, pt2, pt3',f31",
							 "f12,'pt1, pt2, pt3''',f32",
							 "f13,'pt1, ''pt2'' , pt3',f33",
							 "f14,'pt1, ''pt2'' , ''pt3''',f34",
							 "f15,'pt1, ''pt2'', ''pt3'', pt4',f35",
							 "f16,'''f2a'','' f2b''',f36",
							 "'f17','pt1, ''pt2'' , ''pt3'', pt4','f37'",
	};
	private String[] lines2 = {"field1| field2| field3",
			 "f11|'pt1| pt2| pt3'|f31",
			 "f12|'pt1| pt2| pt3'''|f32",
			 "f13|'pt1| ''pt2'' | pt3'|f33",
			 "f14|'pt1| ''pt2'' | ''pt3'''|f34",
			 "f15|'pt1| ''pt2''| ''pt3''| pt4'|f35",
			 "f16|'''f2a''|'' f2b'''|f36",
			 "'f17'|'pt1| ''pt2'' | ''pt3''| pt4'|'f37'",
	};

	private String[] lines3 = {"field1, field2, field3",
			 "f11,`~pt1, pt2, pt3`~,f31",
			 "f12,`~pt1, pt2, pt3`~`~`~,f32",
			 "f13,`~pt1, `~`~pt2`~`~ , pt3`~,f33",
			 "f14,`~pt1, `~`~pt2`~`~ , `~`~pt3`~`~`~,f34",
			 "f15,`~pt1, `~`~pt2`~`~, `~`~pt3`~`~, pt4`~,f35",
			 "f16,`~`~`~f2a`~`~,`~`~ f2b`~`~`~,f36",
			 "`~f17`~,`~pt1, `~`~pt2`~`~ , `~`~pt3`~`~, pt4`~,`~f37`~",
	};

	
	private String[] lines4 = {
			"!`v41!`!`!`,!`,v42!`!`!`,!`v43!`!`",
			"!`v1,1,!`,!`a, !`b!` , c!`,v13",
			"!`,v11,!`,!`a, !`b!` , c!`,v13",
			"!`,v11,!`,!`,a, !`!`b!`!` , c!`,v13",
	};
	
	private String[][] expected4 = {
			{"v41!`", ",v42!`","v43!`"},
			{"v1,1,","a, !`b!` , c","v13"},
			{",v11,","a, !`b!` , c","v13"},
			{",v11,",",a, !`b!` , c","v13"},
	};
	
	private String[] lines5 = {
			"!`,v11,!`,v13",			
			"!`,v11,!`,v13,!`,a, !`!`b!`!` , c!`,!`,a, !`!`b!`!` , c,!`",
	};
	private String[][] expected5 = {
			{",v11,", "v13"},
			{",v11,","v13",",a, !`b!` , c",",a, !`b!` , c,"},
	};
	

	public void testGetField1() {
		BasicCsvLineParserExtended p = new BasicCsvLineParserExtended(false);
		String s;

		for (int i = 1; i < lines1.length; i++) {
			s = p.getField(0, lines1[i], LINES1_CSV_DEF);
			assertEquals("Error in " + i + "a, string was " + s, "f1" + i, s);
			s = p.getField(0, lines2[i], LINES2_CSV_DEF);
			assertEquals("Error in " + i + "b, string was " + s, "f1" + i, s);
			s = p.getField(0, lines0[i], LINES0_CSV_DEF);
			assertEquals("Error in " + i + "c, string was " + s, "f1" + i, s);
			s = p.getField(0, lines3[i], LINES3_CSV_DEF);
			assertEquals("Error in " + i + "a, string was " + s, "f1" + i, s);

			s = p.getField(2, lines1[i], LINES1_CSV_DEF);
			assertEquals("Error in " + i + "d, string was " + s, "f3" + i, s);
			s = p.getField(2, lines2[i], LINES2_CSV_DEF);
			assertEquals("Error in " + i + "e, string was " + s, "f3" + i, s);
			s = p.getField(2, lines0[i], LINES0_CSV_DEF);
			assertEquals("Error in " + i + "f, string was " + s, "f3" + i, s);
			s = p.getField(2, lines3[i], LINES3_CSV_DEF);
			assertEquals("Error in " + i + "d, string was " + s, "f3" + i, s);

		}
	}

	public void testGetField2() {
		BasicCsvLineParserExtended p = new BasicCsvLineParserExtended(false);
		String s;

		s = p.getField(1, lines1[1], LINES1_CSV_DEF);
		assertEquals("Error in 11a, string was " + s, "pt1, pt2, pt3", s);
		s = p.getField(1, lines2[1], LINES2_CSV_DEF);
		assertEquals("Error in 11b, string was " + s, "pt1| pt2| pt3", s);


		s = p.getField(1, lines1[2], LINES1_CSV_DEF);
		assertEquals("Error in 12a, string was " + s, "pt1, pt2, pt3'", s);
		s = p.getField(1, lines2[2], LINES2_CSV_DEF);
		assertEquals("Error in 12b, string was " + s, "pt1| pt2| pt3'", s);

		s = p.getField(1, lines1[3], LINES1_CSV_DEF);
		assertEquals("Error in 13a, string was " + s, "pt1, 'pt2' , pt3", s);
		s = p.getField(1, lines2[3], LINES2_CSV_DEF);
		assertEquals("Error in 13b, string was " + s, "pt1| 'pt2' | pt3", s);

		s = p.getField(1, lines1[4], LINES1_CSV_DEF);
		assertEquals("Error in 14a, string was " + s, "pt1, 'pt2' , 'pt3'", s);
		s = p.getField(1, lines2[4], LINES2_CSV_DEF);
		assertEquals("Error in 14b, string was " + s, "pt1| 'pt2' | 'pt3'", s);

		s = p.getField(1, lines1[5], LINES1_CSV_DEF);
		assertEquals("Error in 15a, string was " + s, "pt1, 'pt2', 'pt3', pt4", s);
		s = p.getField(1, lines2[5], LINES2_CSV_DEF);
		assertEquals("Error in 15b, string was " + s, "pt1| 'pt2'| 'pt3'| pt4", s);

		s = p.getField(1, lines1[6], LINES1_CSV_DEF);
		assertEquals("Error in 16a, string was " + s, "'f2a',' f2b'", s);
		s = p.getField(1, lines2[6], LINES2_CSV_DEF);
		assertEquals("Error in 16b, string was " + s, "'f2a'|' f2b'", s);

		s = p.getField(1, lines1[7], LINES1_CSV_DEF);
		assertEquals("Error in 17a, string was " + s, "pt1, 'pt2' , 'pt3', pt4", s);
		s = p.getField(1, lines2[7], LINES2_CSV_DEF);
		assertEquals("Error in 17b, string was " + s, "pt1| 'pt2' | 'pt3'| pt4", s);

	}
	
	public void testGetField4() {
		doTst(lines4, expected4);
		doTst(lines5, expected5);
	}
	
	private void doTst(String[] lines, String[][] expected ) {
		BasicCsvLineParserExtended p = new BasicCsvLineParserExtended(false);
		String s;
		for (int i = 0; i < lines.length; i++) {
			for (int j = 0; j < expected[i].length; j++) {
				s = p.getField(j, lines[i], LINES4_CSV_DEF);
				assertEquals(i + ", " + j,  expected[i][j], s);
			}
		}
	}

	public void testSetField() {
		BasicCsvLineParserExtended p = new BasicCsvLineParserExtended(false);
		int j;
		String s;
		//StringBuffer comment = new StringBuffer("12345");

		//System.out.println("@@~~ " + comment.substring(comment.length() - 1));

		s = p.setField(2, Type.NT_TEXT, lines3[1], LINES3_CSV_DEF,
				p.getField(2, lines3[1], LINES3_CSV_DEF));

		for (int i = 0; i < lines1.length - 1; i++) {
			for (j = 0; j < 3; j++) {
				s = p.setField(j, Type.NT_TEXT, lines1[i], LINES1_CSV_DEF,
						p.getField(j, lines1[i], LINES1_CSV_DEF));
				assertEquals("Error in " + i +":" + j + "a got " + s, lines1[i], s);

				s = p.setField(j, Type.NT_TEXT, lines2[i], LINES2_CSV_DEF,
						p.getField(j, lines2[i], LINES2_CSV_DEF));
				assertEquals("Error in " + i +":" + j + "b got " + s, lines2[i], s);

				s = p.setField(j, Type.NT_TEXT, lines0[i], LINES1_CSV_DEF,
						p.getField(j, lines0[i], LINES0_CSV_DEF));
				assertEquals("Error in " + i +":" + j + "c got " + s, lines0[i], s);

				s = p.setField(j, Type.NT_TEXT, lines3[i], LINES3_CSV_DEF,
						p.getField(j, lines3[i], LINES3_CSV_DEF));
				//System.out.println("--> " + p.getField(j, lines3[i], LINES3_CSV_DEF));
				assertEquals("Error in " + i +":" + j + "a got " + s, lines3[i], s);

			}
		}
	}


	public void testSetField2() {
		BasicCsvLineParserExtended p = new BasicCsvLineParserExtended(false, ICsvDefinition.NORMAL_SPLIT, false, true);
		int j;
		String s;
		//StringBuffer comment = new StringBuffer("12345");

		//System.out.println("@@~~ " + comment.substring(comment.length() - 1));

		for (int i = 0; i < lines1.length - 1; i++) {
			for (j = 0; j < 3; j++) {
				s = p.setField(j, Type.NT_NUMBER, lines1[i], LINES1_CSV_DEF,
						p.getField(j, lines1[i], LINES1_CSV_DEF));
				assertEquals("Error in " + i +":" + j + "a got " + s, lines1[i], s);

				s = p.setField(j, Type.NT_NUMBER, lines2[i], LINES2_CSV_DEF,
						p.getField(j, lines2[i], LINES2_CSV_DEF));
				assertEquals("Error in " + i +":" + j + "b got " + s, lines2[i], s);

				s = p.setField(j, Type.NT_NUMBER, lines0[i], LINES1_CSV_DEF,
						p.getField(j, lines0[i], LINES0_CSV_DEF));
				assertEquals("Error in " + i +":" + j + "c got " + s, lines0[i], s);

				s = p.setField(j, Type.NT_NUMBER, lines3[i], LINES3_CSV_DEF,
						p.getField(j, lines3[i], LINES3_CSV_DEF));
				assertEquals("Error in " + i +":" + j + "d got " + s, lines3[i], s);
			}
		}
	}


	public void testSetField3() {
			BasicCsvLineParserExtended p = new BasicCsvLineParserExtended(false, ICsvDefinition.NORMAL_SPLIT, false, true);
			int j;
			String s, before, after;
			//StringBuffer comment = new StringBuffer("12345");

			//System.out.println("@@~~ " + comment.substring(comment.length() - 1));

			for (int i = 0; i < lines1.length - 1; i++) {
				for (j = 0; j < 3; j++) {
					before = p.getField(j, lines1[i], LINES1_CSV_DEF);
					s = p.setField(j, Type.NT_TEXT, lines1[i], LINES1_CSV_DEF, before);

					if (before.indexOf(",") == 0) {
						after = p.getField(j, s, LINES0_CSV_DEF);
						System.out.println(i + ", " + j + " > " + before + " => " + after + " <");

						assertEquals("Error in " + i +":" + j + "aa got " + after, after, "'" + before + "'");
					}
					after = p.getField(j, s, LINES1_CSV_DEF);
					assertEquals("Error in " + i +":" + j + "ab got " + after, after, before);

					before = p.getField(j, lines2[i], LINES2_CSV_DEF);
					s = p.setField(j, Type.NT_TEXT, lines2[i], LINES2_CSV_DEF, before);

					if (before.indexOf("|") == 0) {
						after = p.getField(j, s, new CsvDefinition("|", ""));
						System.out.println(i + ", " + j + " > " + before + " => " + after);

						assertEquals("Error in " + i +":" + j + "ba got " + after, after, "'" + before + "'");
					}
					after = p.getField(j, s, LINES2_CSV_DEF);
					System.out.println(i + ", " + j + " > " + before + " => " + after);
					assertEquals("Error in " + i +":" + j + "bb got " + after, after, before);

					before = p.getField(j, lines0[i], LINES0_CSV_DEF);
					s = p.setField(j, Type.NT_TEXT, lines0[i], LINES1_CSV_DEF, before);
					after = p.getField(j, s,  LINES0_CSV_DEF);
					System.out.println(i + ", " + j + " > " + before + " => " + after);

					assertEquals("Error in " + i +":" + j + "ca got " + after, after, "'" + before + "'");
					after = p.getField(j, s, LINES1_CSV_DEF);
					assertEquals("Error in " + i +":" + j + "cb got " + after, after, before);
					System.out.println();

				}
			}
	}

	
	public void testGetFieldList() {
		BasicCsvLineParserExtended p = new BasicCsvLineParserExtended(false);
		CommonCsvTests.tstGetFieldList("0: ", lines0, p, LINES0_CSV_DEF, 3);
		CommonCsvTests.tstGetFieldList("1: ", lines1, p, LINES1_CSV_DEF, 3);
		CommonCsvTests.tstGetFieldList("2: ", lines2, p, LINES2_CSV_DEF, 3);
		CommonCsvTests.tstGetFieldList("3: ", lines3, p, LINES3_CSV_DEF, 3);
		CommonCsvTests.tstGetFieldList("4: ", lines4, p, LINES4_CSV_DEF, 3);
	}

	
	public void testSetFieldList() {
		BasicCsvLineParserExtended p = new BasicCsvLineParserExtended(false);
		CommonCsvTests.tstSetFieldList("0: ", lines0, p, LINES0_CSV_DEF, 3);
		CommonCsvTests.tstSetFieldList("1: ", lines1, lines1.length - 1, p, LINES1_CSV_DEF, 3);
		CommonCsvTests.tstSetFieldList("2: ", lines2, lines2.length - 1, p, LINES2_CSV_DEF, 3);
		CommonCsvTests.tstSetFieldList("3: ", lines3, lines3.length - 1, p, LINES3_CSV_DEF, 3);
	}

//	public void testSplit() {
//		fail("Not yet implemented");
//	}

}
