package net.sf.RecordEditor.test.db;

import java.sql.ResultSet;

import junit.framework.TestCase;

import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.RecordEditorXmlLoader;
import net.sf.RecordEditor.re.db.Record.ChildRecordsDB;
import net.sf.RecordEditor.re.db.Record.ChildRecordsRec;
import net.sf.RecordEditor.re.db.Record.ExtendedRecordDB;
import net.sf.RecordEditor.re.db.Record.RecordRec;
import net.sf.RecordEditor.utils.common.AbsConnection;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReConnection;

public class TestRecordDelete extends TestCase {
	private final static String[] XML_LAYOUT = {
		"<?xml version=\"1.0\" ?>",
		"<RECORD RECORDNAME=\"ww1File\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" DESCRIPTION=\"ww File Def\" FILESTRUCTURE=\"Default\" STYLE=\"0\" RECORDTYPE=\"GroupOfRecords\" LIST=\"Y\" QUOTE=\"\" RecSep=\"default\" LINE_NO_FIELD_NAMES=\"1\">",
		"	<RECORDS>",
		"		<RECORD RECORDNAME=\"wwProd01\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" DESCRIPTION=\"Prod 01 Record\" FILESTRUCTURE=\"Default\" STYLE=\"0\" RECORDTYPE=\"RecordLayout\" LIST=\"N\" PARENT=\"wwProdHead\" QUOTE=\"\" RecSep=\"default\" LINE_NO_FIELD_NAMES=\"1\">",
		"			<TSTFIELDS>",
		"				<AND>",
		"					<TSTFIELD NAME=\"RecordType1\" VALUE=\"P\"/>",
		"					<TSTFIELD NAME=\"RecordType2\" VALUE=\"01\"/>",
		"				</AND>",
		"			</TSTFIELDS>",
		"			<FIELDS>",
		"				<FIELD NAME=\"RecordType1\"  POSITION=\"1\" LENGTH=\"1\" TYPE=\"Char\"/>",
		"				<FIELD NAME=\"RecordType2\"  POSITION=\"3\" LENGTH=\"2\" TYPE=\"Char\"/>",
		"				<FIELD NAME=\"Field11\"  POSITION=\"5\" LENGTH=\"4\" TYPE=\"Char\"/>",
		"				<FIELD NAME=\"Field12\" POSITION=\"9\" LENGTH=\"8\" TYPE=\"Num (Right Justified zero padded)\"/>",
		"				<FIELD NAME=\"Field13\"  POSITION=\"17\" LENGTH=\"3\" TYPE=\"Char\"/>",
		"			</FIELDS>",
		"		</RECORD>",
		"		<RECORD RECORDNAME=\"wwProd02\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" FILESTRUCTURE=\"Default\" STYLE=\"0\" RECORDTYPE=\"RecordLayout\" LIST=\"N\" PARENT=\"wwProdHead\" QUOTE=\"\" RecSep=\"default\" LINE_NO_FIELD_NAMES=\"1\">",
		"			<TSTFIELDS>",
		"				<AND>",
		"					<TSTFIELD NAME=\"RecordType1\" VALUE=\"P\"/>",
		"					<TSTFIELD NAME=\"RecordType2\" VALUE=\"02\"/>",
		"				</AND>",
		"			</TSTFIELDS>",
		"			<FIELDS>",
		"				<FIELD NAME=\"RecordType1\"  POSITION=\"1\" LENGTH=\"1\" TYPE=\"Char\"/>",
		"				<FIELD NAME=\"RecordType2\"  POSITION=\"3\" LENGTH=\"2\" TYPE=\"Char\"/>",
		"				<FIELD NAME=\"Field21\"  POSITION=\"5\" LENGTH=\"4\" TYPE=\"Char\"/>",
		"				<FIELD NAME=\"Field22\"  POSITION=\"9\" LENGTH=\"8\" TYPE=\"Num (Right Justified zero padded)\"/>",
		"				<FIELD NAME=\"Field23\"  POSITION=\"17\" LENGTH=\"3\" TYPE=\"Char\"/>",
		"			</FIELDS>",
		"		</RECORD>",
		"		<RECORD RECORDNAME=\"wwProd05\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" FILESTRUCTURE=\"Default\" STYLE=\"0\" RECORDTYPE=\"RecordLayout\" LIST=\"N\" PARENT=\"wwProdHead\" QUOTE=\"\" RecSep=\"default\" LINE_NO_FIELD_NAMES=\"1\">",
		"			<TSTFIELDS>",
		"				<AND>",
		"					<TSTFIELD NAME=\"RecordType1\" VALUE=\"P\"/>",
		"					<TSTFIELD NAME=\"RecordType2\" VALUE=\"05\"/>",
		"				</AND>",
		"			</TSTFIELDS>",
		"			<FIELDS>",
		"				<FIELD NAME=\"RecordType1\"  POSITION=\"1\" LENGTH=\"1\" TYPE=\"Char\"/>",
		"				<FIELD NAME=\"RecordType2\"  POSITION=\"3\" LENGTH=\"2\" TYPE=\"Char\"/>",
		"				<FIELD NAME=\"Field51\"  POSITION=\"5\" LENGTH=\"6\" TYPE=\"Char\"/>",
		"				<FIELD NAME=\"Field52\"  POSITION=\"11\" LENGTH=\"9\" TYPE=\"Num (Right Justified zero padded)\"/>",
		"				<FIELD NAME=\"Field53\"  POSITION=\"26\" LENGTH=\"3\" TYPE=\"Char\"/>",
		"			</FIELDS>",
		"		</RECORD>",
		"		<RECORD RECORDNAME=\"wwTrailer\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" FILESTRUCTURE=\"Default\" STYLE=\"0\" RECORDTYPE=\"RecordLayout\" LIST=\"N\" PARENT=\"wwHeader\" QUOTE=\"\" RecSep=\"default\" LINE_NO_FIELD_NAMES=\"1\">",
		"			<TSTFIELDS>",
		"				<AND>",
		"					<TSTFIELD NAME=\"RecordType1\" VALUE=\"T\"/>",
		"					<TSTFIELD NAME=\"RecordType2\" VALUE=\"TR\"/>",
		"				</AND>",
		"			</TSTFIELDS>",
		"			<FIELDS>",
		"				<FIELD NAME=\"RecordType1\"  POSITION=\"1\" LENGTH=\"1\" TYPE=\"Char\"/>",
		"				<FIELD NAME=\"RecordType2\"  POSITION=\"3\" LENGTH=\"2\" TYPE=\"Char\"/>",
		"				<FIELD NAME=\"Count\" POSITION=\"17\" LENGTH=\"8\" TYPE=\"Num (Right Justified zero padded)\"/>",
		"			</FIELDS>",
		"		</RECORD>",
		"		<RECORD RECORDNAME=\"wwProdHead\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" FILESTRUCTURE=\"Default\" STYLE=\"0\" RECORDTYPE=\"RecordLayout\" LIST=\"N\" QUOTE=\"\" RecSep=\"default\" LINE_NO_FIELD_NAMES=\"1\">",
		"			<TSTFIELDS>",
		"				<AND>",
		"					<TSTFIELD NAME=\"RecordType1\" VALUE=\"P\"/>",
		"					<TSTFIELD NAME=\"RecordType2\" VALUE=\"HD\"/>",
		"				</AND>",
		"			</TSTFIELDS>",
		"			<FIELDS>",
		"				<FIELD NAME=\"RecordType1\"  POSITION=\"1\" LENGTH=\"1\" TYPE=\"Char\"/>",
		"				<FIELD NAME=\"RecordType2\"  POSITION=\"3\" LENGTH=\"2\" TYPE=\"Char\"/>",
		"				<FIELD NAME=\"Dept\"  POSITION=\"5\" LENGTH=\"4\" TYPE=\"Char\"/>",
		"				<FIELD NAME=\"Product\"  POSITION=\"9\" LENGTH=\"8\" TYPE=\"Num (Right Justified zero padded)\"/>",
		"			</FIELDS>",
		"		</RECORD>",
		"		<RECORD RECORDNAME=\"wwHeader\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" DESCRIPTION=\"File Header\" FILESTRUCTURE=\"Default\" STYLE=\"0\" RECORDTYPE=\"RecordLayout\" LIST=\"Y\" PARENT=\"wwHeader\" QUOTE=\"\" RecSep=\"default\" LINE_NO_FIELD_NAMES=\"1\">",
		"			<TSTFIELDS>",
		"				<AND>",
		"					<TSTFIELD NAME=\"RecordType1\" VALUE=\"H\"/>",
		"					<TSTFIELD NAME=\"RecordType2\" VALUE=\"HD\"/>",
		"				</AND>",
		"			</TSTFIELDS>",
		"			<FIELDS>",
		"				<FIELD NAME=\"RecordType1\"  POSITION=\"1\" LENGTH=\"1\" TYPE=\"Char\"/>",
		"				<FIELD NAME=\"RecordType2\"  POSITION=\"3\" LENGTH=\"2\" TYPE=\"Char\"/>",
		"				<FIELD NAME=\"RunDate\" POSITION=\"5\" LENGTH=\"8\" TYPE=\"Char\"/>",
		"				<FIELD NAME=\"RunNumber\"  POSITION=\"13\" LENGTH=\"8\" TYPE=\"Num (Right Justified zero padded)\"/>",
		"			</FIELDS>",
		"		</RECORD>",
		"	</RECORDS>",
		"</RECORD>",
		

	};
	String sqlp1 = "select Count(*) from ";
	String[] tbls = {
		"TBL_R_RECORDS",
		"TBL_RF_RECORDFIELDS",
		"TBL_RS2_SUBRECORDS",
		"TBL_RFS_FIELDSELECTION"
	};
	
	ExtendedRecordDB db;

	/**
	 * Check all child segments get deleted
	 * @throws Exception
	 */
	public void testGroupRecDel() throws Exception {
		RecordRec rRec = loadRecord();
		int[] rCounts = {1,	0,	6,	12,};
		
		String sqlp2 = " where recordId = " + rRec.getRecordId();
		String sql;
		ReConnection con = ReConnection.getConnection(Common.getConnectionIndex());
		int i = 0;
		
		for (String tbl : tbls) {
			sql = sqlp1 + tbl + sqlp2;
			
			ResultSet resultset =
					con.getConnection().createStatement().executeQuery(sql);
			if (resultset.next()) {
				assertEquals("Checking 1 " + tbl, rCounts[i++], resultset.getInt(1));
			} else {
				assertTrue("Nothing returned from db 1 !!!", false);
			}
		}
		
		for (i = 0; i < rRec.getValue().getNumberOfRecords(); i++) {
			System.out.print("\t" +  rRec.getValue().getRecord(i).getRecordId());
		}
		db.delete(rRec);
		db.close();
		
		for (String tbl : tbls) {
			sql = sqlp1 + tbl + sqlp2;
		
			ResultSet resultset =
					con.getConnection().createStatement().executeQuery(sql);
			if (resultset.next()) {
				assertEquals("Checking 2 " + tbl, 0, resultset.getInt(1));
			} else {
				assertTrue("Nothing returned from db 2 !!!", false);
			}

		}
	}
	
	
	/**
	 * Checking fields get deleted with parent record
	 * @throws Exception
	 */
	public void testFieldRecDel() throws Exception {
		RecordRec rRec = loadRecord();
		ExternalRecord xRec = rRec.getValue();
		int[][] rCounts = {	{1, 5, },
				{1, 5, },
				{1, 5, },
				{1, 3, },
				{1, 4, },
				{1, 4, },
		};
		String[] tbls = {
				"TBL_R_RECORDS",
				"TBL_RF_RECORDFIELDS"
		};
		
		String sql;
		ReConnection con = ReConnection.getConnection(Common.getConnectionIndex());
		int i = 0;
		
		
		for (i = 0; i < xRec.getNumberOfRecords(); i++) {
			String sqlp2 = " where recordId = " + xRec.getRecord(i).getRecordId();
			int j = 0;
			
			for (String tbl : tbls) {
				sql = sqlp1 + tbl + sqlp2;
				
				ResultSet resultset =
						con.getConnection().createStatement().executeQuery(sql);
				if (resultset.next()) {
					assertEquals("Checking 1 " + i + " "+ tbl, rCounts[i][j++], resultset.getInt(1));
					//System.out.print(resultset.getInt(1) + ", ");
				} else {
					assertTrue("Nothing returned from db 1 !!!", false);
				}
			}
			
			
			db.delete(new RecordRec(xRec.getRecord(i)));
			
			for (String tbl : tbls) {
				sql = sqlp1 + tbl + sqlp2;
			
				ResultSet resultset =
						con.getConnection().createStatement().executeQuery(sql);
				if (resultset.next()) {
					assertEquals("Checking 2 " + i  + " " + tbl, 0, resultset.getInt(1));
				} else {
					assertTrue("Nothing returned from db 2 !!! + i", false);
				}

			}
		}
		db.delete(rRec);
		db.close();
	}
	
	
	/**
	 * Check Record Selection record get deleted with Child Records
	 * @throws Exception
	 */
	public void testChildRecDel() throws Exception {
		RecordRec rRec = loadRecord();
		//ExternalRecord xRec = rRec.getValue();
		
		ResultSet resultset;
		AbsConnection con = db.getConnect();
		
		ChildRecordsRec childRec;
		ChildRecordsDB childDb = new ChildRecordsDB(); 
		
		
		childDb.setConnection(con);
		childDb.setParams(rRec.getRecordId());
//		childDb.resetSearch();
//		childDb.setSearchArg("RecordId", ChildRecordsDB.opEquals, rRec.getRecordId() + "");
		childDb.open();

		
		
		System.out.println("Checking child DB's");
		while ((childRec = childDb.fetch()) != null) {
			String sql = sqlp1 + " TBL_RFS_FIELDSELECTION "
		        + " where recordId  = " + rRec.getRecordId()
		        + "   and child_key = " + childRec.getChildKey();
			
			resultset = con.getConnection().createStatement().executeQuery(sql);
			if (resultset.next()) {
				assertEquals("Checking 1 " , 2, resultset.getInt(1));
				//System.out.print(resultset.getInt(1) + ", ");
			} else {
				assertTrue("Nothing returned from db 1 !!!", false);
			}
			
			childDb.delete(childRec);
			
			resultset = con.getConnection().createStatement().executeQuery(sql);
			if (resultset.next()) {
				assertEquals("Checking 2 " , 0, resultset.getInt(1));
				//System.out.print(resultset.getInt(1) + ", ");
			} else {
				assertTrue("Nothing returned from db 1 !!!", false);
			}
		}
		
		db.delete(rRec);
		db.close();
	}
	
	
	
	private RecordRec loadRecord() throws Exception {
		
		ExternalRecord xRec = getExternalLayout();
		RecordRec rRec = new RecordRec(xRec);
		
		db = new ExtendedRecordDB();
		db.setConnection(ReConnection.getConnection(Common.getConnectionIndex()));
		
		db.insert(rRec);
		return rRec;
	}
	
	
	private ExternalRecord getExternalLayout() throws Exception {
		StringBuilder b = new StringBuilder();
		
		for (int i = 0; i < XML_LAYOUT.length; i++) {
			b.append(XML_LAYOUT[i]);
		}
		
		return RecordEditorXmlLoader.getExternalRecord(b.toString(), "Csv Layout");
	}
}
