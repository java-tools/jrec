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
      
package net.sf.JRecord.zTest.io.Pos4680;

import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.RecordEditorXmlLoader;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.JRecord.zTest.Common.TstConstants;
import junit.framework.TestCase;

/**
 * Run through and read the Point of Sale files
 * This will check there are no obvious errors in the
 * record read objects
 * 
 * @author Bruce Martin
 *
 */
public class TstRead4680 extends TestCase {
	private String SplXml
		= "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<RECORD RECORDNAME=\"SPL\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" DESCRIPTION=\"Shelf Price Label File\" FILESTRUCTURE=\"Default\" STYLE=\"0\" RECORDTYPE=\"GroupOfBinaryRecords\" LIST=\"Y\" QUOTE=\"\" RecSep=\"default\" SYSTEMNAME=\"Pos - Store Interface\" LINE_NO_FIELD_NAMES=\"1\">"
				+ "	<RECORDS>"
				+ "		<RECORD RECORDNAME=\"SPL HD\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" DESCRIPTION=\"SPL Header Detail\" FILESTRUCTURE=\"Default\" STYLE=\"0\" RECORDTYPE=\"RecordLayout\" LIST=\"N\" QUOTE=\"\" RecSep=\"default\" SYSTEMNAME=\"Pos - Store Interface\" TESTFIELD=\"Record Type\" LINE_NO_FIELD_NAMES=\"1\">"
				+ "			<FIELDS>"
				+ "				<FIELD NAME=\"Record Type\" POSITION=\"1\" LENGTH=\"1\" TYPE=\"Char\"/>"
				+ "				<FIELD NAME=\"Event Number\" POSITION=\"2\" LENGTH=\"6\" TYPE=\"Num (Right Justified zero padded)\"/>"
				+ "				<FIELD NAME=\"EOR\" POSITION=\"8\" LENGTH=\"2\" TYPE=\"Char\"/>"
				+ "			</FIELDS>"
				+ "		</RECORD>"
				+ "		<RECORD RECORDNAME=\"SPL 1\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" DESCRIPTION=\"SPL record type 1\n\nSPL Add Replace Batch Header Record\" FILESTRUCTURE=\"Default\" STYLE=\"0\" RECORDTYPE=\"RecordLayout\" LIST=\"N\" QUOTE=\"\" RecSep=\"default\" SYSTEMNAME=\"Pos - Store Interface\" TESTFIELD=\"record type\" TESTVALUE=\"O\" LINE_NO_FIELD_NAMES=\"1\">"
				+ "			<FIELDS>"
				+ "				<FIELD NAME=\"record type\" POSITION=\"1\" LENGTH=\"1\" TYPE=\"Char\"/>"
				+ "				<FIELD NAME=\"change code\" POSITION=\"2\" LENGTH=\"1\" TYPE=\"Char\"/>"
				+ "				<FIELD NAME=\"date_time_due\" POSITION=\"3\" LENGTH=\"10\" TYPE=\"Char\"/>"
				+ "				<FIELD NAME=\"date_time_end\" POSITION=\"13\" LENGTH=\"10\" TYPE=\"Char\"/>"
				+ "				<FIELD NAME=\"Event Id\" POSITION=\"23\" LENGTH=\"6\" TYPE=\"Char\"/>"
				+ "				<FIELD NAME=\"EOR\" POSITION=\"29\" LENGTH=\"2\" TYPE=\"Char\"/>"
				+ "			</FIELDS>"
				+ "		</RECORD>"
				+ "		<RECORD RECORDNAME=\"SPL 3\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" DESCRIPTION=\"SPL Record Type 3\nSPL Change Description Details\" FILESTRUCTURE=\"Default\" STYLE=\"0\" RECORDTYPE=\"RecordLayout\" LIST=\"N\" QUOTE=\"\" RecSep=\"default\" SYSTEMNAME=\"Pos - Store Interface\" TESTFIELD=\"Record Type\" TESTVALUE=\"3\" LINE_NO_FIELD_NAMES=\"1\">"
				+ "			<FIELDS>"
				+ "				<FIELD NAME=\"Record Type\" POSITION=\"1\" LENGTH=\"1\" TYPE=\"Char\"/>"
				+ "				<FIELD NAME=\"Keycode\" POSITION=\"2\" LENGTH=\"4\" TYPE=\"Postive Binary Integer\"/>"
				+ "				<FIELD NAME=\"Department\" POSITION=\"6\" LENGTH=\"2\" TYPE=\"Postive Binary Integer\"/>"
				+ "				<FIELD NAME=\"line 1 desc\" POSITION=\"8\" LENGTH=\"30\" TYPE=\"Char\"/>"
				+ "				<FIELD NAME=\"line 2 desc\" POSITION=\"38\" LENGTH=\"30\" TYPE=\"Char\"/>"
				+ "				<FIELD NAME=\"EOR\" POSITION=\"68\" LENGTH=\"2\" TYPE=\"Char\"/>"
				+ "			</FIELDS>"
				+ "		</RECORD>"
				+ "		<RECORD RECORDNAME=\"SPL 4\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" DESCRIPTION=\"SPL Delete Item Details (Record Type 4)\" FILESTRUCTURE=\"Default\" STYLE=\"0\" RECORDTYPE=\"RecordLayout\" LIST=\"N\" QUOTE=\"\" RecSep=\"default\" SYSTEMNAME=\"Pos - Store Interface\" TESTFIELD=\"Record Type\" TESTVALUE=\"4\" LINE_NO_FIELD_NAMES=\"1\">"
				+ "			<FIELDS>"
				+ "				<FIELD NAME=\"Record Type\" POSITION=\"1\" LENGTH=\"1\" TYPE=\"Char\"/>"
				+ "				<FIELD NAME=\"Keycode\" POSITION=\"2\" LENGTH=\"4\" TYPE=\"Postive Binary Integer\"/>"
				+ "				<FIELD NAME=\"EOR\" POSITION=\"6\" LENGTH=\"2\" TYPE=\"Char\"/>"
				+ "			</FIELDS>"
				+ "		</RECORD>"
				+ "		<RECORD RECORDNAME=\"SPL 6\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" DESCRIPTION=\"SPL Delete Batch Header Record\n\nSPL record type6\" FILESTRUCTURE=\"Default\" STYLE=\"0\" RECORDTYPE=\"RecordLayout\" LIST=\"N\" QUOTE=\"\" RecSep=\"default\" SYSTEMNAME=\"Pos - Store Interface\" TESTFIELD=\"Record Type\" TESTVALUE=\"6\" LINE_NO_FIELD_NAMES=\"1\">"
				+ "			<FIELDS>"
				+ "				<FIELD NAME=\"Record Type\" POSITION=\"1\" LENGTH=\"1\" TYPE=\"Char\"/>"
				+ "				<FIELD NAME=\"Event Number\" POSITION=\"2\" LENGTH=\"6\" TYPE=\"Num (Right Justified zero padded)\"/>"
				+ "				<FIELD NAME=\"EOR\" POSITION=\"8\" LENGTH=\"2\" TYPE=\"Char\"/>"
				+ "			</FIELDS>"
				+ "		</RECORD>"
				+ "		<RECORD RECORDNAME=\"SPL 8\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" DESCRIPTION=\"SPL Ad Hoc Addition (SPL 8)\" FILESTRUCTURE=\"Default\" STYLE=\"0\" RECORDTYPE=\"RecordLayout\" LIST=\"N\" QUOTE=\"\" RecSep=\"default\" SYSTEMNAME=\"Pos - Store Interface\" TESTFIELD=\"Record Type\" TESTVALUE=\"8\" LINE_NO_FIELD_NAMES=\"1\">"
				+ "			<FIELDS>"
				+ "				<FIELD NAME=\"Record Type\" POSITION=\"1\" LENGTH=\"1\" TYPE=\"Char\"/>"
				+ "				<FIELD NAME=\"Keycode\" POSITION=\"2\" LENGTH=\"4\" TYPE=\"Postive Binary Integer\"/>"
				+ "				<FIELD NAME=\"Department\" POSITION=\"6\" LENGTH=\"2\" TYPE=\"Postive Binary Integer\"/>"
				+ "				<FIELD NAME=\"line 1 desc\" POSITION=\"8\" LENGTH=\"30\" TYPE=\"Char\"/>"
				+ "				<FIELD NAME=\"line 2 desc\" POSITION=\"38\" LENGTH=\"30\" TYPE=\"Char\"/>"
				+ "				<FIELD NAME=\"EOR\" POSITION=\"68\" LENGTH=\"2\" TYPE=\"Char\"/>"
				+ "			</FIELDS>"
				+ "		</RECORD>"
				+ "		<RECORD RECORDNAME=\"SPL M\" COPYBOOK=\"\" DELIMITER=\"null\" DESCRIPTION=\"SPL Change Code Table Maintenance (SPL M)\" FILESTRUCTURE=\"Default\" STYLE=\"0\" RECORDTYPE=\"RecordLayout\" LIST=\"N\" QUOTE=\"\" RecSep=\"default\" SYSTEMNAME=\"Pos - Store Interface\" TESTFIELD=\"record type\" TESTVALUE=\"M\" LINE_NO_FIELD_NAMES=\"1\">"
				+ "			<FIELDS>"
				+ "				<FIELD NAME=\"record type\" POSITION=\"1\" LENGTH=\"1\" TYPE=\"Char\"/>"
				+ "				<FIELD NAME=\"change code\" POSITION=\"2\" LENGTH=\"1\" TYPE=\"Char\"/>"
				+ "				<FIELD NAME=\"date_time_due\" POSITION=\"3\" LENGTH=\"10\" TYPE=\"Char\"/>"
				+ "				<FIELD NAME=\"EOR\" POSITION=\"10\" LENGTH=\"2\" TYPE=\"Char\"/>"
				+ "			</FIELDS>"
				+ "		</RECORD>"
				+ "		<RECORD RECORDNAME=\"SPL N\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" DESCRIPTION=\"SPL Table Maintenance Details\" FILESTRUCTURE=\"Default\" STYLE=\"0\" RECORDTYPE=\"RecordLayout\" LIST=\"N\" QUOTE=\"\" RecSep=\"default\" SYSTEMNAME=\"Pos - Store Interface\" TESTFIELD=\"record type\" TESTVALUE=\"N\" LINE_NO_FIELD_NAMES=\"1\">"
				+ "			<FIELDS>"
				+ "				<FIELD NAME=\"record type\" POSITION=\"1\" LENGTH=\"1\" TYPE=\"Char\"/>"
				+ "				<FIELD NAME=\"change code\" POSITION=\"2\" LENGTH=\"1\" TYPE=\"Char\"/>"
				+ "				<FIELD NAME=\"screen desc\" POSITION=\"3\" LENGTH=\"18\" TYPE=\"Char\"/>"
				+ "				<FIELD NAME=\"label 2 desc\" POSITION=\"21\" LENGTH=\"6\" TYPE=\"Char\"/>"
				+ "				<FIELD NAME=\"label 2 desc\" POSITION=\"27\" LENGTH=\"6\" TYPE=\"Char\"/>"
				+ "				<FIELD NAME=\"special paper flag\" POSITION=\"33\" LENGTH=\"1\" TYPE=\"Char\"/>"
				+ "				<FIELD NAME=\"batchid\" POSITION=\"34\" LENGTH=\"3\" TYPE=\"Char\"/>"
				+ "				<FIELD NAME=\"EOR\" POSITION=\"37\" LENGTH=\"2\" TYPE=\"Char\"/>"
				+ "			</FIELDS>"
				+ "		</RECORD>"
				+ "		<RECORD RECORDNAME=\"SPL End\" COPYBOOK=\"\" DELIMITER=\"null\" DESCRIPTION=\"SPL End of File\" FILESTRUCTURE=\"Default\" STYLE=\"0\" RECORDTYPE=\"RecordLayout\" LIST=\"N\" QUOTE=\"\" RecSep=\"default\" SYSTEMNAME=\"Pos - Store Interface\" TESTFIELD=\"record type\" LINE_NO_FIELD_NAMES=\"1\">"
				+ "			<FIELDS>"
				+ "				<FIELD NAME=\"record type\" POSITION=\"1\" LENGTH=\"1\" TYPE=\"Char\"/>"
				+ "				<FIELD NAME=\"EOR\" POSITION=\"2\" LENGTH=\"2\" TYPE=\"Char\"/>"
				+ "			</FIELDS>"
				+ "		</RECORD>"
				+ "	</RECORDS>"
				+ "</RECORD>";

	public void testSPL() throws Exception {
		tstRead(SplXml, TstConstants.SAMPLE_DIRECTORY + "Pos_Spl_1.bin");
	}
	
	
	private void tstRead(String schemaXml, String filename) throws RecordException, Exception {
		LayoutDetail schema = RecordEditorXmlLoader.getExternalRecord(schemaXml, "Schema").asLayoutDetail();
		AbstractLineReader lineReader = LineIOProvider.getInstance().getLineReader(schema);
		AbstractLine l;
		
		lineReader.open(filename, schema);
		int expected = 0, diff = 0, unknown = 0;
		
		while ((l = lineReader.read()) != null) {
			int pref = l.getPreferredLayoutIdx();
			
			if (pref < 0) {
				unknown += 1;
			} else if (l.getData().length == schema.getRecord(pref).getLength() ) {
				expected += 1;
			} else {
				diff += 1;
			}
		}
		
		System.out.println(filename + "\t" + unknown + "\t" + expected + "\t" + diff);
		lineReader.close();
		assertEquals(0, unknown);
		assertEquals(0, diff);
	}
}
