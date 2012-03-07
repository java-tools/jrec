/*
 * @Author Bruce Martin
 * Created on 13/06/2005
 *
 * Purpose:
 *     This class holds DB upgrade routines
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *   - Add Version 0.56 DB updates
 *
 * # Version 0.60 Bruce Martin 2007/02/16
 *   - Version 0.60 updates (Date and Checkbox types)
 */
package net.sf.RecordEditor.layoutEd.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.RecordEditorCsvLoader;
import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.JRecord.Types.Type;
import net.sf.RecordEditor.layoutEd.Record.ExtendedRecordDB;
import net.sf.RecordEditor.layoutEd.Record.RecordRec;
import net.sf.RecordEditor.record.format.CellFormat;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReConnection;


/**
 * This class holds DB upgrade routines
 *
 * @author Bruce Martin
 *
 */
public final class UpgradeDB {

	//private static String VERSION_691  = "0069100";
	private static String VERSION_692B = "0069200";
	private static String VERSION_670 = "0070000";
	//private static String LATEST_VERSION = VERSION_670;
	private static String VERSION_KEY  = "-101";
	
	private static String  SQL_GET_VERION = 
			"Select DETAILS from TBL_TI_INTTBLS "
		+	" where   TBLID  = "  + VERSION_KEY
		+	"   and  TBLKEY  = "  + VERSION_KEY
		+	"   and DETAILS >= '" + VERSION_692B + "'";
	
	private int[] unknownStructure = {
		Constants.IO_VB, Constants.IO_VB_DUMP, 
		Constants.IO_VB_OPEN_COBOL, Constants.IO_VB_FUJITSU,
		Constants.IO_BIN_TEXT, Constants.IO_UNICODE_TEXT
	};
	private String[] unknownFonts = {
		"cp037", "cp037", "", "", "", "utf-8", "utf-16",
	};
	private String[] unknownNames ={
		"Mainframe VB",
		"Mainframe VB Dump",
		"Open Cobol VB",
		"Fujitsu VB",
		"Text IO",
		"Text UTF-8",
		"Text UTF-16",
	};
	private String unknownLine1Pt1 = "Record\t";
	private String unknownLine1Pt2 ="\t1\t<Tab>\t0\t\tY\t";
	private String unknownLine2 ="1\t1\tData\t\t81\t0\t0\t";
		
	private String unknownFormatLines 
		= "Record\t21\t1\t<Tab>\t0\t\tY\tUnknown Format\n"
		+ "1\t1\tUnknown\t\t0\t0\t0\t";
    private String updateRecordSep
    	= "Update TBL_R_RECORDS set RECSEPLIST = 'default' "
    	+  "where RECORDTYPE <> " + Common.rtGroupOfBinaryRecords;
    private String insertSQL = "insert into TBL_TI_INTTBLS (TBLID,TBLKEY,DETAILS) values ";
    private String  deleteTbl = "delete from TBL_TI_INTTBLS where ";
    private String insertTbl
    	= "insert into Tbl_T_Table (TblId, TblName, TblDescription) values ";
    
    private String insertRecord = "INSERT INTO Tbl_R_Records "
    	+ "(RecordId,RecordName,Description,RecordType,System,ListChar,CopyBook,Delimiter,"
        + "Quote,PosRecInd,RecSepList,RecordSep,ExternalId,Canonical_Name,Record_Style,"
        + "File_Structure) VALUES ";
    private String insertRF = "INSERT INTO Tbl_RF_RecordFields "
    	+ "(RecordId,SubKey,FieldPos,FieldLength,FieldName,Description,FieldType,DecimalPos,"
    	+ "DefaultValue,CobolName,FormatDescription,Cell_Format,Parameter) VALUES ";
 /*   private String[] sql = {
       insertSQL + "(1,4,'Hex Field')",
       insertSQL + "(1,17,'Float')",
       insertSQL + "(1,18,'Double')",
       insertSQL + "(1,31,'Mainframe Packed Decimal (comp-3)')",
       insertSQL + "(1,32,'Mainframe Zoned Numeric')",
       insertSQL + "(1,35,'Binary Integer Big Endian (Mainframe, AIX etc)')",
       insertSQL + "(2,0,'Record - Binary')",

       insertSQL + "(3,32,'Mainframe')"
    };*/

    private String[] sql55 = {
            insertTbl + "(4, 'FileStructure', "
            		  + "'File Structures, "
            		  + "\n user File Structures should have a"
            		  + "\n Row Key > 1000')",
            insertTbl + "(5, 'Formats', 'Table Cell Formatting')",

            insertSQL + "(3,101,'General')",

			insertSQL + "(4,0,'Default Reader')",
            insertSQL + "(4,2,'Fixed Length Binary')",
            insertSQL + "(4,3,'Line based Binary')",
            insertSQL + "(4,4,'Mainframe VB (rdw based) Binary')",
            insertSQL + "(4,5,'Mainframe VB Dump: includes Block length')",
            insertSQL + "(4,51,'Delimited, names first line')",

            insertSQL + "(5,0,'No Format')",
    };

  /*  private String[] sql56 = {
            insertSQL + "(1,9,'Num Sign Separate Leading')",
            insertSQL + "(1,3,'Char Null padded')",
            insertSQL + "(1,2,'Char Null terminated')",
            insertSQL + "(1,10,'Num Sign Separate Trailing')",
            insertSQL + "(1,41,'Fujitsu Zoned Numeric')",
            insertSQL + "(4,7,'Fujitsu Variable Binary')"
    };*/

    /*private String[] sql60 = {
            insertSQL + "(1,71,'Date - Format in Parameter field')",
            insertSQL + "(1,72,'Date - YYMMDD')",
            insertSQL + "(1,73,'Date - YYYYMMDD')",
            insertSQL + "(1,74,'Date - DDMMYY')",
            insertSQL + "(1,75,'Date - DDMMYYYY')",
            insertSQL + "(1,111,'Checkbox Y/N')",
            insertSQL + "(1,112,'Checkbox T/F')",
            insertSQL + "(5,1,'Checkbox - use Parameter')",
            insertSQL + "(5,2,'Date - Format in Parameter field')",
            insertSQL + "(5,3,'Date - DDMMYYYY')",
            insertSQL + "(5,4,'Date - YYYYMMDD')",
    };*/

    private String[] sql61b = {
    		deleteTbl + "TBLID = 1 and TBLKEY in (2, 3, 9, 10, 41, 71, 72, 73, 74, 75, 110, 111, 112, 115, 116, 117)",
       		deleteTbl + "TBLID = 2 and TBLKEY in (6)",
       		deleteTbl + "TBLID = 3 and TBLKEY in (9, 99, 101, 102)",
      		deleteTbl + "TBLID = 4 and TBLKEY in (0, 1, 2, 3, 4, 5, 7, 51," + Constants.IO_GENERIC_CSV + "61, 62)",
    		deleteTbl + "TBLID = 5 and TBLKEY in (1, 2, 3, 4, 15)",
            insertSQL + "(1,9,'Num Sign Separate Leading')",
            insertSQL + "(1,10,'Num Sign Separate Trailing')",
            insertSQL + "(1,41,'Fujitsu Zoned Numeric')",
			insertSQL + "(4,0,'Default Reader')",
            insertSQL + "(1,71,'Date - Format in Parameter field')",
            insertSQL + "(1,72,'Date - YYMMDD')",
            insertSQL + "(1,73,'Date - YYYYMMDD')",
            insertSQL + "(1,74,'Date - DDMMYY')",
            insertSQL + "(1,75,'Date - DDMMYYYY')",
            insertSQL + "(1,111,'Checkbox Y/N')",
            insertSQL + "(1,112,'Checkbox T/F')",
            insertSQL + "(1,3,'Char Null padded')",
            insertSQL + "(1,2,'Char Null terminated')",
            insertSQL + "(1," + Type.ftXmlNameTag + ",'XML Name Tag')",
            insertSQL + "(1," + Type.ftCsvArray + ",'CSV array')",
            insertSQL + "(1," + Type.ftCheckBoxTrue + ",'Check Box True / Space')",
            insertSQL + "(1," + Type.ftMultiLineEdit + ",'Edit Multi Line field')",
            insertSQL + "(2," + Constants.RT_XML + ",'XML')",

            insertSQL + "(3,9,'Other')",
            insertSQL + "(3,99,'Generic')",
            insertSQL + "(3,101,'CSV')",
            insertSQL + "(3,102,'XML')",
            insertSQL + "(4,1,'Text IO')",
            insertSQL + "(4,2,'Fixed Length Binary')",
            insertSQL + "(4,3,'Line based Binary')",
            insertSQL + "(4,4,'Mainframe VB (rdw based) Binary')",
            insertSQL + "(4,5,'Mainframe VB Dump: includes Block length')",
            insertSQL + "(4,7,'Fujitsu Variable Binary')",
            insertSQL + "(4,51,'Delimited, names first line')",
            insertSQL + "(4," + Constants.IO_GENERIC_CSV + ",'Generic CSV (Choose details at run time)')",
            insertSQL + "(4," + Constants.IO_XML_BUILD_LAYOUT + ",'XML - Build Layout')",
            insertSQL + "(4," + Constants.IO_XML_USE_LAYOUT   + ",'XML - Existing Layout')",
            insertSQL + "(5,1,'Checkbox - use Parameter')",
            insertSQL + "(5,2,'Date - Format in Parameter field')",
            insertSQL + "(5,3,'Date - DDMMYYYY')",
            insertSQL + "(5,4,'Date - YYYYMMDD')",
            insertSQL + "(5," + CellFormat.FMT_COMBO + ",'ComboBox Format, (combo name in parameter)')",
            
            insertRecord + "(70,'zzzCsvTest3','Tab Delimited file with CSV array fields',2,101,'Y','','<Tab>','',0,'default','\n',0,'',0,0);",
            insertRecord + "(71,'zzzCsvTest4','Tab Delimited file with CSV array fields',2,101,'Y','','<Tab>','\"',0,'default','\n',0,'',0,0);",
            insertRecord + "(72,'XML - Build Layout','XML file, build the layout based on the files contents'" 
                         + ",6,101,'Y','','|','''',0,'default','\n',0,'',0,62);",
            insertRecord + "(73,'zzzCsvTest5','| Delimited file with CSV array fields',2,101,'Y','','|','\'\'',0,'default','\n',0,'',0,0);",
            insertRecord + "(74,'Generic CSV - enter details','Generic CSV - user supplies details',2,101,'Y','','|','',0,'default','\n',0,'',0,52);",
  

            insertRF + "(70,1,1,0,'Field 1','',0,0,'','',null,0,'');",
            insertRF + "(70,2,2,0,'Array 1 (; and :)','',115,0,'','',null,0,'//:/;/');",
            insertRF + "(70,3,3,0,'Field 3','',0,0,'','',null,0,'');",
            insertRF + "(70,4,4,0,'Array 2 (;)','',115,0,'','',null,0,'//;/');",
            insertRF + "(70,5,5,0,'Field 5','',0,0,'','',null,0,'');",
            insertRF + "(70,6,6,0,'Array 3 (:)','',115,0,'','',null,0,'//:/');",
            insertRF + "(70,7,7,0,'Field 7','',0,0,'','',null,0,'');",
            insertRF + "(71,1,1,0,'Field 1','',0,0,'','',null,0,'');",
            insertRF + "(71,2,2,0,'Array 1 (; and |)','',115,0,'','',null,0,'//|/;/');",
            insertRF + "(71,3,3,0,'Field 3','',0,0,'','',null,0,'');",
            insertRF + "(71,4,4,0,'Array 2 (;)','',115,0,'','',null,0,'//;/');",
            insertRF + "(71,5,5,0,'Field 5','',0,0,'','',null,0,'');",
            insertRF + "(71,6,6,0,'Array 3 (:)','',115,0,'','',null,0,'//:/');",
            insertRF + "(71,7,7,0,'Array 4 (|)','',115,0,'','',null,0,'//|/');",
            insertRF + "(72,1,1,0,'Dummy','1 field is Required for the layout to load'" 
            		 + ",0,0,'','',null,0,'');",
            insertRF + "(73,1,1,0,'Field 1','',0,0,'','',null,0,'');",
            insertRF + "(73,2,2,0,'Array 1 (; and colon)','',115,0,'','',null,0,'//:/;/');",
            insertRF + "(73,3,3,0,'Field 3','',0,0,'','',null,0,'');",
            insertRF + "(73,4,4,0,'Array 2 (;)','',115,0,'','',null,0,'//;/');",
            insertRF + "(73,5,5,0,'Field 5','',0,0,'','',null,0,'');",
            insertRF + "(73,6,6,0,'Array 3 (colon)','',115,0,'','',null,0,'//:/');",
            insertRF + "(73,7,7,0,'Field 7','',0,0,'','',null,0,'');",

            insertRF + "(74,1,1,0,'Field 1','',0,0,'','',null,0,'');",
            
            
            "Create Table Tbl_C_Combos (" 
                 + "Combo_Id   INTEGER, "
                 + "System     smallint, "
                 + "Combo_Name varchar(30), "
                 + "Column_Type smallint"
          + ");",
            "CREATE UNIQUE INDEX Tbl_C_Combos_PK  ON Tbl_C_Combos(Combo_Id);",
            "CREATE UNIQUE INDEX Tbl_C_Combos_PK1 ON Tbl_C_Combos(Combo_Name);",


            "Create Table Tbl_CI_ComboItems (" 
          		+ "Combo_Id   INTEGER, "
          		+ "Combo_Code varchar(30), "
          		+ "Combo_Value varchar(60) "
          + ");",
            "CREATE UNIQUE INDEX Tbl_CI_ComboItems_PK ON Tbl_CI_ComboItems(Combo_Id, Combo_Code);"
   };
    
//    private String[] sql67 = {
//    	deleteTbl + "TBLID = 1 and TBLKEY in (" + Type.ftPositiveBinaryBigEndian + ","
//    	                           + Type.ftBinaryBigEndian + ")",
//    	deleteTbl + "TBLID = 4 and TBLKEY in (" + Constants.IO_VB_OPEN_COBOL +")",
//    	insertSQL + "(1," + Type.ftBinaryBigEndian + ",'Binary Integer Big Endian (Mainframe, AIX etc)');",
//       	insertSQL + "(1," + Type.ftPositiveBinaryBigEndian + ",'Positive Integer (Big Endian)');",
//       	insertSQL + "(4," + Constants.IO_VB_OPEN_COBOL +",'Open Cobol VB');",
//   };
    
    private String[] sql69 = {
    	deleteTbl + "TBLID = 1 and TBLKEY in (" + Type.ftPositiveBinaryBigEndian + ","
            + Type.ftBinaryBigEndian 
            + "," + Type.ftNumAnyDecimal + "," + Type.ftPositiveNumAnyDecimal + ")",
        deleteTbl + "TBLID = 4 and TBLKEY in (" + Constants.IO_VB_OPEN_COBOL 
        	+ "," + Constants.IO_UNICODE_NAME_1ST_LINE
        	+ "," + Constants.IO_BIN_NAME_1ST_LINE
        	+")",
       	deleteTbl + "TBLID = 1 and TBLKEY in (" + Type.ftCharRestOfFixedRecord 
       		+ "," + Type.ftCharRestOfRecord 
       		+ "," + Type.ftRmComp + "," + Type.ftRmCompPositive 
       		+ ")",
      	deleteTbl + "TBLID = 5 and TBLKEY in (" + CellFormat.FMT_BOLD + ")",
 //      	insertSQL + "(1," + Type.ftCharRestOfFixedRecord + ",'Char Rest of Fixed Length');",
      	insertSQL + "(1," + Type.ftBinaryBigEndian + ",'Binary Integer Big Endian (Mainframe?)');",
       	insertSQL + "(1," + Type.ftPositiveBinaryBigEndian + ",'Positive Integer (Big Endian)');",
       	insertSQL + "(4," + Constants.IO_VB_OPEN_COBOL +",'Open Cobol VB');",
       	insertSQL + "(4," + Constants.IO_UNICODE_NAME_1ST_LINE + ",'Unicode Csv Names o First Line');",
       	insertSQL + "(4," + Constants.IO_BIN_NAME_1ST_LINE     + ",'Bin Csv Names o First Line ');",
       	insertSQL + "(1," + Type.ftCharRestOfRecord + ",'Char Rest of Record');",
       	insertSQL + "(1," + Type.ftRmComp + ",'RM Cobol Comp');",
       	insertSQL + "(1," + Type.ftRmCompPositive + ",'RM Cobol Positive Comp');",
      	insertSQL + "(1," + Type.ftNumAnyDecimal + ",'Number any decimal');",
      	insertSQL + "(1," + Type.ftPositiveNumAnyDecimal + ",'Number (+ve) any decimal');",
        insertSQL + "(5," + CellFormat.FMT_BOLD + ",'Bold Format')",
   };

    private String[] sql70 = {
    		"Drop Table Tbl_RS1_SubRecords",
    		"Drop Table Tbl_RFS_FieldSelection",
            "Create Table Tbl_RS1_SubRecords (" 
                    + "RECORDID   INTEGER, "
                    + "ChildKey     INTEGER, "
                    + "CHILDRECORD   INTEGER, "
                    + "FieldStart   INTEGER, "
                    + "Field   varchar(30), "
                    + "FieldValue   varchar(30), "
                    + "PARENT_RECORDID   INTEGER, "
                    + "operatorSequence   smallint "
             + ")",
               "CREATE UNIQUE INDEX Tbl_RS1_SubRecordsPK  ON Tbl_RS1_SubRecords(RECORDID, ChildKey);",
               "Create Table Tbl_RFS_FieldSelection (" 
                       + "RECORDID        INTEGER, "
                       + "ChildKey        smallint, "
                       + "FieldNo         smallint, "
                       + "BooleanOperator smallint, "
                       + "Field           varchar(30), "
                       + "Operator        char(2), "
                       + "FieldValue      varchar(30) "
                + ")",
                  "CREATE UNIQUE INDEX Tbl_RFS_FieldSelectionPK  ON Tbl_RFS_FieldSelection(RECORDID, ChildKey, FieldNo);",
    };
    private String[] updateVersion = {
        	deleteTbl + "TBLID = " + VERSION_KEY + " and TBLKEY = " + VERSION_KEY + ";",
        	insertSQL + "(" + VERSION_KEY + ", " + VERSION_KEY + ", ",
        			};


    /**
     * Change Record Sep list to default
     * @param dbIdx database Index
     */
    public void updateRecordSepList(int dbIdx) {

        try {
           // System.out.println(updateRecordSep);
            Connection con = Common.getDBConnection(dbIdx);
            Statement statement = con.createStatement();

            statement.executeUpdate(updateRecordSep);
            statement.close();
        } catch (Exception e) {
            Common.getLogger().logException(AbsSSLogger.ERROR, e);

            e.printStackTrace();
        }
    }

//    /**
//     * Upgrade the databases
//     *
//     * @param dbIdx database Index
//     */
//    public void upgrade(int dbIdx) {
//
//        try {
//            Connection con = Common.getDBConnection(dbIdx);
//
//            addColumnToDB(con, "TBL_R_RECORDS", "Canonical_Name", "VARCHAR(20)", "''");
//            addColumnToDB(con, "TBL_R_RECORDS", "Record_Style", "Int", "0");
//            addColumnToDB(con, "TBL_RS_SUBRECORDS", "Parent_RecordId", "Int", "-1");
//
//            runSQL(con.createStatement(), sql);
//
//        } catch (Exception e) {
//            Common.getLogger().logException(AbsSSLogger.ERROR, e);
//            e.printStackTrace();
//        }
//    }
    /**
     * Upgrade the databases
     *
     * @param dbIdx database Index
     */
    public void upgrade55(int dbIdx) {

        try {
            Connection con = Common.getDBConnection(dbIdx);
            runSQL(con.createStatement(), sql55, Common.isDropSemi(dbIdx));

            addColumnToDB(con, "TBL_R_RECORDS", "File_Structure", "Int", "0");
            addColumnToDB(con, "Tbl_RF_RecordFields", "Cell_Format", "Int", "0");
            addColumnToDB(con, "Tbl_RF_RecordFields", "Parameter", "Varchar(80)", "''");

        } catch (Exception e) {
            Common.getLogger().logException(AbsSSLogger.ERROR, e);
            e.printStackTrace();
        }
    }

//    /**
//     * Upgrade the databases
//     *
//     * @param dbIdx database Index
//     */
//    public void upgrade56(int dbIdx) {
//        genericUpgrade(dbIdx, sql56);
//    }

   // /**
   //  * Upgrade the databases
   //  *
   //  * @param dbIdx database Index
   //  */
   // public void upgrade60(int dbIdx) {
   //     genericUpgrade(dbIdx, sql60);
   // }

    /**
     * Upgrade the databases
     *
     * @param dbIdx database Index
     */
    public void upgrade61b(int dbIdx) {
        genericUpgrade(dbIdx, sql61b, null);
    }

//    public void upgrade67(int dbIdx) {
//        genericUpgrade(dbIdx, sql67);
//    }
// 

    public void upgrade69(int dbIdx) {
        genericUpgrade(dbIdx, sql69, VERSION_692B);
        
        for (int i =0; i < unknownStructure.length; i++) {
        	addLayout(
        			dbIdx,
        			"Unknown " + unknownNames[i],
        			unknownLine1Pt1 + unknownStructure[i] 
        	   +	unknownLine1Pt2 + unknownNames[i] 
        	   +	"\n" + unknownLine2,
        	   		unknownFonts[i],
        	   		0);
        }
         
        addLayout(	dbIdx,
        			"Unknown Format",
        			unknownFormatLines,
        	   		"",
        			0);
    }

    private void addLayout(int dbIdx, String name, String txt, String font, int system) {
    	byte[] bytes = txt.getBytes();
    	ByteArrayInputStream in = new ByteArrayInputStream(bytes);
    	ExternalRecord ext;
    	RecordRec rec;
        ExtendedRecordDB db = new ExtendedRecordDB();
        db.setConnection(new ReConnection(dbIdx));
    	db.resetSearch();
     	db.setSearchRecordName(ExtendedRecordDB.opEquals, name);
     	db.open();
     	rec = db.fetch();
     	
     	if (rec != null) {
     		db.delete(rec);
     		Common.getLogger().logMsg(AbsSSLogger.SHOW, " --> Deleting Layout " + name);
     	}
     	
        ext = ExternalRecord.getNullRecord(
        		name,
        		Constants.rtBinaryRecord,
                font);
        ext.setSystem(system);

     	(new RecordEditorCsvLoader("\t"))
     			.insertFields(Common.getLogger(), ext, new InputStreamReader(in), name, dbIdx);
     	
     	rec = new RecordRec(ext);
     	db.insert(rec);
     	Common.getLogger().logMsg(AbsSSLogger.SHOW, " --> Adding Layout " + name);
     	db.close();
    }
    
    public void upgrade70(int dbIdx) {
        genericUpgrade(dbIdx, sql70, "");
        
        String sSQL = " Select  RecordId, ChildRecord, FieldStart, Field, FieldValue, PARENT_RECORDID"
                    + "  from Tbl_RS_SubRecords"
        		    + " Order by RecordId, ChildRecord";
        String insertSQL = "Insert Into  Tbl_RS1_SubRecords  ("
                + "    RecordId" 
                + "  , ChildKey"
                + "  , ChildRecord"
                + "  , FieldStart"
                + "  , Field"
                + "  , FieldValue"
                + "  , PARENT_RECORDID" 
                + "  , operatorSequence"
                + ") Values ("
                +    "     ?   , ?   , ?   , ?   , ?, ?, ?, ?"
                + ")";
        
        
        int lastRecordId=Integer.MIN_VALUE,
        	childNo=0;
        int recordId, childRecord, fieldStart, parentId,
            operatorSeq=0;
        String field, fieldValue;
        int count = 0;
        try {
        	Connection connect = Common.getUpdateConnection(dbIdx);
			ResultSet resultset = connect.createStatement().executeQuery(sSQL);
			PreparedStatement insertStatement = connect.prepareStatement(insertSQL);
			int idx;
			
			while (resultset.next()) {
				recordId = resultset.getInt(1);
				childRecord = resultset.getInt(2);
				fieldStart = resultset.getInt(3);
				field    = resultset.getString(4);
				fieldValue    = resultset.getString(5);
			    parentId    = resultset.getInt(6);
			    
			    if (recordId==lastRecordId) {
			    	childNo += 1;
			    } else {
			    	childNo = 0;
			    	lastRecordId = recordId; 
			    }
			    
			    idx = 1;
			    insertStatement.setInt(idx++, recordId);
			    insertStatement.setInt(idx++, childNo);
			    insertStatement.setInt(idx++, childRecord);
			    insertStatement.setInt(idx++, fieldStart);
			    insertStatement.setString(idx++, field);
			    insertStatement.setString(idx++, fieldValue);
			    insertStatement.setInt(idx++, parentId);
			    insertStatement.setInt(idx++, operatorSeq);
			    
			    insertStatement.executeUpdate();
			    count += 1;
			}
			
			upgradeVersion(connect, dbIdx, VERSION_670);
			insertStatement.close();
	        Common.logMsg("Database Upgraded, child record copied " + count, null);
		} catch (SQLException e) {
			e.printStackTrace();
			Common.logMsg("Database upgrade failed !!!", null);
		} finally {
			Common.freeConnection(dbIdx);
		}
        

    }
    

    /**
     *
     * @param dbIdx database Index
     * @param sql2run sql to be run
     */
    private boolean genericUpgrade(int dbIdx, String[] sql2run, String version) {
    	boolean ret = false;
    	boolean dropSemi = Common.isDropSemi(dbIdx);
  
        try {
            Connection con = Common.getUpdateConnection(dbIdx);
            runSQL(con.createStatement(), sql2run, dropSemi);
            
            upgradeVersion(con, dbIdx, version);
            Common.getLogger().logMsg(AbsSSLogger.SHOW, "Upgrade SQL Run !!!");

            ret = true;
        } catch (Exception e) {
            Common.getLogger().logException(AbsSSLogger.ERROR, e);
            e.printStackTrace();
		} finally {
			Common.freeConnection(dbIdx);
        }
		
		return ret;
    }

    private void upgradeVersion(Connection con, int dbIdx, String version) throws SQLException {
            if (version != null) {
            	String[] sql = new String[2];
            	sql[0] = updateVersion[0];
            	sql[1] = updateVersion[1] + "'" + version + "');";
                runSQL(con.createStatement(), sql, Common.isDropSemi(dbIdx));
            }
   	
    }

    /**
     * Runs an array of SQL statements
     *
     * @param statement SQL stament
     * @param sqlToRun SQL to be run
     */
    private void runSQL(Statement statement, String[] sqlToRun, boolean dropSemi) {
        int i;
        String sql;

        for (i = 0; i < sqlToRun.length; i++) {
            try {
            	sql = sqlToRun[i];
                if (dropSemi && sql.trim().endsWith(";")) {
                	sql = sql.trim();
                	sql = sql.substring(0,sql.length() - 1);
                }

                statement.executeUpdate(sql);
            } catch (Exception e) {
                Common.getLogger().logMsg(AbsSSLogger.ERROR, "");
                Common.getLogger().logMsg(AbsSSLogger.ERROR, "    SQL: " + sqlToRun[i]);
                Common.getLogger().logMsg(AbsSSLogger.ERROR, "Message: " + e.getMessage());
                System.out.println();
                System.out.println("    SQL: " + sqlToRun[i]);
                System.out.println("Message: " + e.getMessage());
            }
        }
    }

    /**
     * Add to column to Table
     *
     * @param con DB Connection
     * @param table DB table upgrade
     * @param column column to add to table
     * @param type column type
     * @param initValue initial value of column
     *
     */
    private void addColumnToDB(Connection con, String table, String column,
            						  String type,    String initValue) {
        boolean upgradeDB = false;

        try {
            ResultSet resultset =
    			con.createStatement().executeQuery(
    			        "select " + column + " from " + table
    			);
            resultset.next();
            resultset.close();
        } catch (Exception e) {
            upgradeDB = true;
        }

        if (upgradeDB) {
            try {
                Statement statement = con.createStatement();

                statement.execute(
                    "ALTER TABLE " + table
                    	+ " ADD " + column + " " + type
                );

                statement.execute(
                    "update " + table
                    	+ " set " + column + " = " + initValue
                    	+ " where " + column + " is null "
                );
            } catch (Exception e) {
                String msg = "Error Table: " + table + " " + e.getMessage();

                Common.getLogger().logMsg(AbsSSLogger.ERROR, msg);
                System.out.println(msg);
            }
        }
    }
    
    public static boolean checkForUpdate(int dbIndex) {
    	boolean ret = false;
    	//System.out.print("Checking for update ");
    	boolean free = Common.isSetDoFree(false);
    	try {
    		ResultSet resultset =
    			Common.getDBConnection(dbIndex)
    					.createStatement()
    					.executeQuery(SQL_GET_VERION);
    		if (resultset.next()) {
    			String version = resultset.getString(1);
    			if (version != null) {
    				version = version.trim();
    			}
    			if (VERSION_692B.equals(version)) {
    				(new UpgradeDB()).upgrade70(dbIndex);
    				Common.logMsg("Upgraded DB to version 0.69.2b ", null);
    			}
    			System.out.println("Current Version: " + version);
    			//System.out.print("Already " + LATEST_VERSION);
    		} else {
    			//System.out.println("upgrading DB");
    			UpgradeDB upgrade = new UpgradeDB();
    			upgrade.upgrade69(dbIndex);
    			upgrade.upgrade70(dbIndex);
    			Common.logMsg("Upgraded DB to version 0.69.2b ", null);
    			ret = true;
    		}
    	} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Common.setDoFree(free, dbIndex);
		}
    	return ret;
    }
}
