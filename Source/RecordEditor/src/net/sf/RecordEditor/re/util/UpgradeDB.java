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
package net.sf.RecordEditor.re.util;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.RecordEditorCsvLoader;
import net.sf.JRecord.External.RecordEditorXmlLoader;
import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.JRecord.Types.Type;
import net.sf.RecordEditor.layoutEd.utils.LeMessages;
import net.sf.RecordEditor.re.db.Record.ChildRecordsRec;
import net.sf.RecordEditor.re.db.Record.ExtendedRecordDB;
import net.sf.RecordEditor.re.db.Record.RecordRec;
import net.sf.RecordEditor.re.jrecord.format.CellFormat;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.lang.LangConversion;


/**
 * This class holds DB upgrade routines
 *
 * @author Bruce Martin
 *
 */
public final class UpgradeDB {

	private static final String DATABASE_UPGRADED = LangConversion.convert("Database Upgraded to Version") + " ";
	//private static String VERSION_691  = "0069100";
	private static String VERSION_692B = "0069200";
	private static String VERSION_700 = "0070000";
	private static String VERSION_800 = "0080000";
	private static String VERSION_801 = "0080001";
	private static String VERSION_90  = "0090000";
	private static String VERSION_93  = "0093000";
	private static String VERSION_931 = "0093100";
	private static String VERSION_932 = "0093200";
	private static String VERSION_94  = "0094000";
	//private static String LATEST_VERSION = VERSION_670;
	private static String VERSION_KEY  = "-101";

	private List<LayoutDef> holdLayoutDetails = new ArrayList<UpgradeDB.LayoutDef>();

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

    private String[] sql71 = {
    		"Drop Table Tbl_RS2_SubRecords",
    		"Drop Table Tbl_RFS_FieldSelection",
            "Create Table Tbl_RS2_SubRecords ("
                    + "RECORDID   INTEGER, "
                    + "Child_Key          INTEGER, "
                    + "Child_Record       INTEGER, "
                    + "Field_Start        INTEGER, "
                    + "Field_Name         varchar(30), "
                    + "Field_Value        varchar(30), "
                    + "PARENT_RECORDID    INTEGER, "
                    + "Operator_Sequence  smallint, "
                    + "Default_Record     char(1), "
                    + "Child_Name         char(30), "
                    + "Child_Id           INTEGER "
             + ")",
               "CREATE UNIQUE INDEX Tbl_RS2_SubRecordsPK  ON Tbl_RS2_SubRecords(RECORDID, Child_Key);",
              // "CREATE UNIQUE INDEX Tbl_RS2_SubRecordsPK1  ON Tbl_RS2_SubRecords(RECORDID, Child_Id);",
               "Create Table Tbl_RFS_FieldSelection ("
                       + "RECORDID         INTEGER, "
                       + "Child_Key        smallint, "
                       + "Field_No         smallint, "
                       + "Boolean_Operator smallint, "
                       + "Field_Name       varchar(30), "
                       + "Operator         char(2), "
                       + "Field_Value      varchar(30) "
                + ")",
                  "CREATE UNIQUE INDEX Tbl_RFS_FieldSelectionPK  ON Tbl_RFS_FieldSelection(RECORDID, Child_Key, Field_No);",
    };
    private String[] updateVersion = {
        	deleteTbl + "TBLID = " + VERSION_KEY + " and TBLKEY = " + VERSION_KEY + ";",
        	insertSQL + "(" + VERSION_KEY + ", " + VERSION_KEY + ", ",
        			};

    private String[] sql8001 = {
    		deleteTbl + "TBLID = 3 and TBLKEY in (103)",
            insertSQL + "(3, 103, 'System Layouts')",
    };

    private String[] sql90 = {
        	deleteTbl + "TBLID = 1 and TBLKEY in (" + Type.ftCheckBoxY + ","
                + Type.ftMultiLineChar + ")",

     //      	insertSQL + "(1," + Type.ftCharRestOfFixedRecord + ",'Char Rest of Fixed Length');",
          	insertSQL + "(1," + Type.ftCheckBoxY + ",'CheckBox Y/null');",
           	insertSQL + "(1," + Type.ftMultiLineChar + ",'Char Multi Line');",
    };


    private String[] sql93a = {
    		"ALTER TABLE TBL_TI_INTTBLS alter column DETAILS VARCHAR(60);",
    };

    private String[] sql93 = {

        	deleteTbl + "TBLID = 1 and TBLKEY in ("
        			     + Type.ftBinaryBigEndianPositive  + ","
        			     + Type.ftAssumedDecimalPositive + ","
                		 + Type.ftPackedDecimalPostive + ","
                		 + Type.ftBinaryIntPositive + ","
                		 + Type.ftNumZeroPaddedPN + ","
                		 + Type.ftNumZeroPaddedPositive + ","
                		 + Type.ftNumCommaDecimal + ","
                		 + Type.ftNumCommaDecimalPN + ","
                		 + Type.ftNumCommaDecimalPositive + ","
                		 + Type.ftNumRightJustifiedPN + ","
                		 + Type.ftNumRightJustCommaDp + ","
                		 + Type.ftNumRightJustCommaDpPN + ","
                		 + Type.ftNumAnyDecimal  + ","
                		 + Type.ftPositiveNumAnyDecimal
        			     + ");",

     //      	insertSQL + "(1," + Type.ftCharRestOfFixedRecord + ",'Char Rest of Fixed Length');",
          	insertSQL + "(1," + Type.ftBinaryBigEndianPositive + ",'Binary Integer Big Endian (only +ve )');",
           	insertSQL + "(1," + Type.ftAssumedDecimalPositive + ",'Num Assumed Decimal (+ve)');",
          	insertSQL + "(1," + Type.ftPackedDecimalPostive + ",'Mainframe Packed Decimal (+ve)');",
           	insertSQL + "(1," + Type.ftBinaryIntPositive + ",'Binary Integer (only +ve)');",

     //      	insertSQL + "(1," + Type.ftCharRestOfFixedRecord + ",'Char Rest of Fixed Length');",
          	insertSQL + "(1," + Type.ftNumZeroPaddedPN + ",'Zero Padded Number with sign=+/-');",
           	insertSQL + "(1," + Type.ftNumZeroPaddedPositive + ",'Positive Zero Padded Number');",
          	insertSQL + "(1," + Type.ftNumCommaDecimal + ",'Zero Padded Number decimal=\",\"');",
           	insertSQL + "(1," + Type.ftNumCommaDecimalPN + ",'Zero Padded Number decimal=\",\" sign=+/-');",
           	insertSQL + "(1," + Type.ftNumCommaDecimalPositive + ",'Zero Padded Number decimal=\",\" (only +ve)');",

           	insertSQL + "(1," + Type.ftNumRightJustifiedPN + ",'Num (Right Justified space padded) +/- sign');",
           	insertSQL + "(1," + Type.ftNumRightJustCommaDp + ",'Num (Right Just space padded, \",\" Decimal)');",
           	insertSQL + "(1," + Type.ftNumRightJustCommaDpPN + ",'Num (Right Just space padded, \",\" Decimal) +/- sig');",

          	insertSQL + "(1," + Type.ftNumAnyDecimal + ",'Number any decimal');",
          	insertSQL + "(1," + Type.ftPositiveNumAnyDecimal + ",'Number (+ve) any decimal');",

    };


    private String[] sql94 = {
    		"Drop Table TBL_RF1_RECORDFIELDS;",

    		"CREATE TABLE TBL_RF1_RECORDFIELDS ("
    				 + "    RECORDID            INTEGER,"
    				 + "    SUB_KEY             integer,"
    				 + "    FIELD_POS           integer,"
    				 + "    FIELD_LENGTH        integer,"
    				 + "    FIELD_NAME          varchar(120),"
    				 + "    FIELD_DESCRIPTION   varchar(250),"
    				 + "    FIELD_TYPE          integer,"
    				 + "    DECIMAL_POS         smallint,"
    				 + "    DEFAULT_VALUE       varchar(120),"
    				 + "    COBOL_NAME          varchar(30),"
    				 + "    FORMAT_DESCRIPTION  varchar(250),"
    				 + "    Cell_Format         integer,"
    				 + "    Field_Parameter     varchar(120)"
    				 + "  );",

    		"CREATE UNIQUE INDEX TBL_RF1_RECORDFIELDS_PK ON TBL_RF1_RECORDFIELDS(RECORDID, SUB_KEY); ",
    };



//    private String[] sql94 = {
//    		"Drop Table TBL_RF1_RECORDFIELDS",
//
//    		"CREATE TABLE TBL_RF1_RECORDFIELDS ("
//    				 + "    RECORDID            INTEGER,"
//    				 + "    SUB_KEY             integer,"
//    				 + "    FIELD_POS           integer,"
//    				 + "    FIELD_LENGTH        integer,"
//    				 + "    FIELD_NAME          varchar(120),"
//    				 + "    FIELD_DESCRIPTION   varchar(250),"
//    				 + "    FIELD_TYPE          integer,"
//    				 + "    DECIMAL_POS         smallint,"
//    				 + "    DEFAULT_VALUE       varchar(120),"
//    				 + "    COBOL_NAME          varchar(30),"
//    				 + "    FORMAT_DESCRIPTION  varchar(250),"
//    				 + "    Cell_Format         integer,"
//    				 + "    Field_Parameter     varchar(120),"
//    				 + " CONSTRAINT TBL_RF1_RECORDFIELDS_PK PRIMARY KEY (RECORDID,SUB_KEY)"
//    				 + "  )",
//
//    		//"CREATE UNIQUE INDEX TBL_RF1_RECORDFIELDS_PK ON TBL_RF1_RECORDFIELDS(RECORDID, SUB_KEY) ",
//    };


    private String fileWizardXmlLayout = "<?xml version=\"1.0\" ?>\n"
    		+ "<RECORD RECORDNAME=\"FileWizard\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" FILESTRUCTURE=\"FILE_WIZARD\" STYLE=\"0\" RECORDTYPE=\"RecordLayout\" LIST=\"Y\" QUOTE=\"\" RecSep=\"default\" LINE_NO_FIELD_NAMES=\"1\">\n"
    		+ "	<FIELDS><FIELD NAME=\"Dummy\" POSITION=\"1\" LENGTH=\"1\" TYPE=\"Char\"/></FIELDS>\n"
    		+ "</RECORD>";


    private String deleteExample =
    		  "where RECORDID in ("
    		+ "  in ("
    		+ "Select r.RECORDID from TBL_R_RECORDS r"
    		+ " where r.RECORDNAME in ("
    		+ "'Price', 'SPL', 'Line_Test_Group', 'Mainframe FB80', 'PO Master', "
    		+ "'DCR0470 S14', 'DCR0470 T31', 'DCR0470 P41', 'DCR0470 I51', 'DCR0470 I52', "
    		+ "'SAR4180B', 'SAR4180C', 'IVR0075H', 'IVR0075S', 'ams PO Download: Detail', "
    		+ "'ams PO Download: Header', 'ams PO Download: Allocation', 'ams PO Download', "
    		+ "'ams Vendor Download', 'ams Rct Upload FH Header', 'ams Rct Upload: RH', "
    		+ "'ams Rct Upload: RD', 'ams Rct Upload: FT footer', "
    		+ "'ams Receipt (Taret Fields only)', 'ams shp Upload FH Header', "
    		+ "'ams shp Upload DH', 'ams shp Upload DO', 'ams shp Upload DS',"
    		+ "'ams shp Upload AP', 'ams shp Upload AR', 'ams shp Upload DP',"
    		+ "'ams shp Upload DI', 'ams shp Upload FT', 'ams Shipping Upload', "
    		+ "'ams Store', 'ams Receipt FH Header', 'ams Receipt RH Receipt Header', "
    		+ "'ams Receipt RD Recipt Product', 'ams Receipt RS Recipt Store',"
    		+ "'ams Receipt AS', 'ams Receipt SO', 'ams Receipt SC', 'ams Receipt AP',"
    		+ "'ams Receipt AR', 'ams Receipt FT File Trailer', 'ams Receipt', 'PO Head',"
    		+ "'PO Detail', 'DCR0470 S11', 'DCR0470 S12', 'PriceR 8', 'PriceR 9', "
    		+ "'PriceR 3', 'PriceR 1', 'PriceR F', 'PriceR 5', 'PriceR L', 'SPL End',"
    		+ "'SPL M', 'SPL 1', 'SPL 8', 'PriceR 2', 'PriceR D', 'SPL HD', 'SPL 3', "
    		+ "'SPL 4', 'SPL 6', 'PriceR 4', 'PriceR 6', 'SPL N', 'SAR4180A',"
    		+ "'IVR0075D', 'Line_Test_Record', 'DTAR119', 'DTAR192', "
    		+ "'Mainframe Text', 'DTAR107', 'DTAR020', 'DCR0470 O21',"
    		+ "'Mainframe FB80 record', 'DCR0470 S13', 'EDI Sales', 'EDI ASN (DCR0470)', "
    		+ "'EDI PO', 'DTAR1000 VB', 'XmplEditType1', 'XmplDecider', "
    		+ "'XMPLDECIDER-Product-Header', 'XMPLDECIDER-Product-Detail-1', "
    		+ "'XMPLDECIDER-Product-Detail-2', 'DTAR1000 VB Dump', 'Master_Record',"
    		+ "'Rental_Record', 'Transaction_Record',  'XfeDTAR020', 'XfeDTAR020_reverse',"
    		+ "'cpyComp5Sync', 'bsCompSync', 'cpyCompPositive',"
    		+ "'cpyCompSync', 'mfCompPositive', 'mfCompSync', 'Wizard_AmsPo', "
    		+ "))";

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
     		Common.getLogger().logMsg(AbsSSLogger.SHOW, LeMessages.DELETE_LAYOUT.get(name));
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
     	Common.getLogger().logMsg(AbsSSLogger.SHOW, LeMessages.ADD_LAYOUT.get(name));
     	db.close();
    }

    public void upgrade71(int dbIdx) {

        upgrade80(dbIdx, "Tbl_RS_SubRecords");
    }


    public void upgrade80(int dbIdx, String tbl) {
        genericUpgrade(dbIdx, sql71, null);

        List<ChildRecordsRec> list = new ArrayList<ChildRecordsRec>();
        String sSQL = " Select  RecordId, ChildRecord, FieldStart, Field, FieldValue, PARENT_RECORDID"
                    + "  from " + tbl
        		    + " Order by RecordId, ChildRecord";
        String insertSQL = "Insert Into  Tbl_RS2_SubRecords  ("
                + "    RecordId"
                + "  , Child_Key"
                + "  , Child_Record"
                + "  , Field_Start"
                + "  , Field_Name"
                + "  , Field_Value"
                + "  , PARENT_RECORDID"
                + "  , Operator_Sequence"
                + "  , Default_Record"
                + "  , Child_Name"
                + "  , Child_Id"
                + ") Values ("
                +    "     ?   , ?   , ?   , ?   , ?, ?, ?, ?, ?, ?, ?"
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

			while (resultset.next()) {
				recordId = resultset.getInt(1);

			    if (recordId==lastRecordId) {
			    	childNo += 1;
			    } else {
			    	insertChildren(lastRecordId, list, insertStatement);
			    	childNo = 0;
			    	lastRecordId = recordId;
			    }
				childRecord = resultset.getInt(2);
				fieldStart = resultset.getInt(3);
				field    = resultset.getString(4);
				fieldValue    = resultset.getString(5);
			    parentId    = resultset.getInt(6);
			    list.add(new ChildRecordsRec(
			    		childRecord, fieldStart, field, fieldValue, parentId, childNo,
			    		operatorSeq, false, "", childNo));

			    count += 1;
			}
			insertChildren(lastRecordId, list, insertStatement);
			Common.logMsgRaw(AbsSSLogger.SHOW, DATABASE_UPGRADED + VERSION_800 + " , child record copied " + count, null);

			insertStatement.close();

			addFileWizardReader(dbIdx);
		} catch (SQLException e) {
			e.printStackTrace();
			Common.logMsg("Database upgrade failed !!!", null);
		} finally {
			Common.freeConnection(dbIdx);
		}
    }


    public void upgrade90(int dbIdx) {

       	try {
       		genericUpgrade(dbIdx, sql90, null);

       		loadLayout(dbIdx,  Common.GETTEXT_PO_LAYOUT, 103);
       		loadLayout(dbIdx,  Common.TIP_LAYOUT, 103);

 			upgradeVersion((new ReConnection(dbIdx)).getConnection(), dbIdx, VERSION_90);
	        Common.logMsgRaw(AbsSSLogger.SHOW, DATABASE_UPGRADED + VERSION_90, null);

	        upgrade93(dbIdx);
    	} catch (Exception e) {
			Common.logMsg("Error updating version flag", e);
		}
    }


    public void upgrade93(int dbIdx) {

       	try {
       		if (Common.alterVarChar(dbIdx)) {
       			genericUpgrade(dbIdx, sql93a, null);
       		}
       		genericUpgrade(dbIdx, sql93, null);

 			upgradeVersion((new ReConnection(dbIdx)).getConnection(), dbIdx, VERSION_932);
	        Common.logMsgRaw(AbsSSLogger.SHOW, DATABASE_UPGRADED + VERSION_932, null);
    	} catch (Exception e) {
			Common.logMsg("Error updating version flag", e);
		}
   }


    public void upgrade94(int dbIdx) {
        genericUpgrade(dbIdx, sql94, null);

        String sSQL = " Select  RECORDID, SUBKEY, FIELDPOS, FIELDLENGTH, FIELDNAME, DESCRIPTION,  "
        			+         " FIELDTYPE, DECIMALPOS, DEFAULTVALUE, COBOLNAME, FORMATDESCRIPTION, "
        			+         " Cell_Format, Parameter "
                    + "  from TBL_RF_RECORDFIELDS "
        		    + " Order by RecordId, SUBKEY";
        String insertSQL = "Insert Into  TBL_RF1_RECORDFIELDS  ("
                 + "    RECORDID"
				 + " ,  SUB_KEY"
				 + " ,  FIELD_POS"
				 + " ,  FIELD_LENGTH"
				 + " ,  FIELD_NAME"
				 + " ,  FIELD_DESCRIPTION"
				 + " ,  FIELD_TYPE"
				 + " ,  DECIMAL_POS"
				 + " ,  DEFAULT_VALUE"
				 + " ,  COBOL_NAME"
				 + " ,  FORMAT_DESCRIPTION"
				 + " ,  Cell_Format"
				 + " ,  Field_Parameter"
				 + ") Values ("
				 +    "     ?   , ?   , ?   , ?   , ?, ?, ?, ?, ?, ?, ?, ?, ?"
                 + ")";
        String copySql = " INSERT INTO TBL_RF1_RECORDFIELDS "
        		+ "          ( RECORDID, SUB_KEY, FIELD_POS, FIELD_LENGTH, FIELD_NAME, FIELD_DESCRIPTION, "
        		+ "            FIELD_TYPE, DECIMAL_POS, DEFAULT_VALUE, COBOL_NAME, FORMAT_DESCRIPTION, "
        		+ "            Cell_Format, Field_Parameter )"
        		+ " SELECT RecordId, SubKey, FieldPos, FieldLength, FieldName, Description, FieldType, "
        		+ "       DecimalPos, DefaultValue, CobolName, FormatDescription, Cell_Format, Parameter"
        		+ " FROM Tbl_RF_RecordFields;";


        int count = 0;
        int idx;
        try {
        	Connection connect = Common.getUpdateConnection(dbIdx);

			if (Common.useCopySql(dbIdx)) {
				PreparedStatement copyStatement = connect.prepareStatement(copySql);
				copyStatement.executeUpdate();
				count = copyStatement.getUpdateCount();
				System.out.println("Copy Done !!!");
				copyStatement.close();
			} else {
				ResultSet resultset = connect.createStatement().executeQuery(sSQL);
				PreparedStatement insertStatement = connect.prepareStatement(insertSQL);

				while (resultset.next()) {

					idx = 1;
				    insertStatement.setInt(idx, resultset.getInt(idx++));
				    insertStatement.setInt(idx, resultset.getInt(idx++));
				    insertStatement.setInt(idx, resultset.getInt(idx++));
				    insertStatement.setInt(idx, resultset.getInt(idx++));
				    insertStatement.setString(idx, resultset.getString(idx++));
				    insertStatement.setString(idx, resultset.getString(idx++));
				    insertStatement.setInt(idx, resultset.getInt(idx++));
				    insertStatement.setInt(idx, resultset.getInt(idx++));
				    insertStatement.setString(idx, resultset.getString(idx++));
				    insertStatement.setString(idx, resultset.getString(idx++));
				    insertStatement.setString(idx, resultset.getString(idx++));
				    insertStatement.setInt(idx, resultset.getInt(idx++));
				    insertStatement.setString(idx, resultset.getString(idx++));

				    insertStatement.executeUpdate();
				    count += 1;

				}

				insertStatement.close();
				resultset.close();
			}
			Common.logMsgRaw(AbsSSLogger.SHOW, DATABASE_UPGRADED + VERSION_94 + " , Fields copied " + count, null);

			//System.out.println("Upgrade DB");
			upgradeVersion((new ReConnection(dbIdx)).getConnection(), dbIdx, VERSION_94);
		} catch (SQLException e) {
			e.printStackTrace();
			Common.logMsg("Database upgrade failed !!!", null);
		} finally {
			Common.freeConnection(dbIdx);
		}
    }



   public void deleteExamples(int dbIdx) {
    	String[] tbls = { "TBL_RFS_FIELDSELECTION", "TBL_RF_RECORDFIELDS", "TBL_RS2_SUBRECORDS", "TBL_R_RECORDS" };
    	String sql = "";
       	try {
       		Connection con = (new ReConnection(dbIdx)).getConnection();
       		Statement statement = con.createStatement();
       		for (int i = 0; i < tbls.length; i++) {
       			sql = "Delete from " + tbls[i] + deleteExample;
       			statement.execute(sql);
       		}
    	} catch (Exception e) {
    		Common.logMsgRaw("Sql: " + sql, e);
			Common.logMsgRaw("Error deleting Examples", e);

			System.out.println("Sql: " + sql);
			e.printStackTrace();
		}
    }


   public final void addFileWizardReader(int dbIdx) throws SQLException {
    	Connection connect = Common.getUpdateConnection(dbIdx);

    	genericUpgrade(dbIdx, sql8001, null);

    	try {
    		loadLayout(dbIdx, fileWizardXmlLayout, 103);

			upgradeVersion(connect, dbIdx, VERSION_801);
	        Common.logMsgRaw(AbsSSLogger.SHOW, DATABASE_UPGRADED + VERSION_801, null);
			System.out.println("Current Version: " + VERSION_801);

			upgrade90(dbIdx);
		} catch (Exception e) {
			Common.logMsg("Could not load FileWizardReader", e);
			e.printStackTrace();
		}
    }

   private void loadLayout(int dbIdx, String xml, int systemId) {
    	holdLayoutDetails.add( new LayoutDef(dbIdx, xml, systemId));
    }

    public final void loadHeldLayouts() {
    	try {
	    	for (LayoutDef l : holdLayoutDetails) {
	    		loadALayout(l.dbIdx, l.xml, l.systemId);
	    	}
    	} catch (Exception e) {
    		Common.logMsg("Could not load Layouts: " + e.toString(), e);
		}
    }

    private void loadALayout(int dbIdx, String xml, int systemId) throws Exception {

		ExternalRecord rec = RecordEditorXmlLoader.getExternalRecord(xml, "FileWizard");
		ExtendedRecordDB db = new ExtendedRecordDB();

		rec.setSystem(systemId);
		System.out.println("FileStructure: " + rec.getFileStructure()
				+ " " + (new RecordRec(rec)).getValue().getFileStructure()
				+ " " + rec.getRecordStyle());
		db.setConnection(new ReConnection(dbIdx));

		db.insert(new RecordRec(rec));
		db.close();
    }

    private void insertChildren(int recordId, List<ChildRecordsRec> list, PreparedStatement insertStatement)
    throws SQLException {
    	if (list.size() > 0) {
    		HashMap<Integer, ChildRecordsRec> lookup = new HashMap<Integer, ChildRecordsRec>();
    		ChildRecordsRec parent;
    		int idx;
    		for (ChildRecordsRec child : list) {
    			lookup.put(child.getChildRecordId(), child);
    		}
    		for (ChildRecordsRec child : list) {
    			if (child.getParentRecord() >= 0) {
    				parent = lookup.get(child.getParentRecord());
    				if (parent == null) {
    					child.setParentRecord(-1);
    				} else {
    					child.setParentRecord(parent.getChildId());
    				}
    			}

    			idx = 1;
			    insertStatement.setInt(idx++, recordId);
			    insertStatement.setInt(idx++, child.getChildKey());
			    insertStatement.setInt(idx++, child.getChildRecordId());
			    insertStatement.setInt(idx++, child.getStart());
			    insertStatement.setString(idx++, child.getField());
			    insertStatement.setString(idx++, child.getFieldValue());
			    insertStatement.setInt(idx++, child.getParentRecord());
			    insertStatement.setInt(idx++, child.getOperatorSequence());
			    insertStatement.setString(idx++, "N");
			    insertStatement.setString(idx++, "");
			    insertStatement.setInt(idx++, child.getChildId());

			    insertStatement.executeUpdate();

    		}


    		list.clear();
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
            Common.logMsg(AbsSSLogger.SHOW, "Upgrade SQL Run !!!", null);

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
        	String oldVersion = getVersion(dbIdx);

        	try {
        		if (Integer.parseInt(oldVersion) >= Integer.parseInt(version)) {
        			return;
        		}
        	} catch (Exception e) {
			}
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
                System.out.println(" ----> " + sql);

                statement.executeUpdate(sql);
                System.out.println(" ~~ Done ~~");
            } catch (Exception e) {
            	Common.logMsgRaw(LeMessages.SQL_ERROR.get(new Object[] {sqlToRun[i], e.getMessage()}), null);
                //Common.getLogger().logMsg(AbsSSLogger.ERROR, "");
                //Common.getLogger().logMsg(AbsSSLogger.ERROR, "    SQL: " + sqlToRun[i]);
                //Common.getLogger().logMsg(AbsSSLogger.ERROR, "Message: " + e.getMessage());
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
                String msg = LeMessages.ERROR_UPDATING_TABLE.get() + " " + table + " " + e.getMessage();

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
    		String version = getVersion(dbIndex);
			UpgradeDB db = (new UpgradeDB());

    		if (version != null) {
    			System.out.print("Upgrade Version: " + version);
    			if (VERSION_692B.equals(version)) {
    				db.upgrade71(dbIndex);
       				db.upgrade94(dbIndex);
    			} else if (VERSION_700.equals(version)) {
    				db.upgrade80(dbIndex, "Tbl_RS1_SubRecords");
       				db.upgrade94(dbIndex);
    			} else if (VERSION_800.equals(version)) {
    				db.addFileWizardReader(dbIndex);
       				db.upgrade94(dbIndex);
    			} else if (VERSION_801.equals(version)) {
    				db.upgrade90(dbIndex);
       				db.upgrade94(dbIndex);
       			} else if (VERSION_90.equals(version) || VERSION_93.equals(version)
       				   || VERSION_931.equals(version) || VERSION_932.equals(version)) {
       				db.upgrade93(dbIndex);
       				db.upgrade94(dbIndex);
    			}

    			db.loadHeldLayouts();

    			//System.out.print("Already " + LATEST_VERSION);
    		} else {
    			db.upgrade69(dbIndex);
    			db.upgrade71(dbIndex);
    			db.upgrade94(dbIndex);

    			db.loadHeldLayouts();
    			Common.logMsgRaw(AbsSSLogger.SHOW, DATABASE_UPGRADED + VERSION_801, null);
    			ret = true;
    		}
    	} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Common.setDoFree(free, dbIndex);
		}
    	return ret;
    }

    private static String getVersion(int dbIndex) throws SQLException {
    	String version = null;
		ResultSet resultset =
    			Common.getDBConnection(dbIndex)
    					.createStatement()
    					.executeQuery(SQL_GET_VERION);
		if (resultset.next()) {
			version = resultset.getString(1);
			if (version != null) {
				version = version.trim();
			}
		}
		return version;

    }

    private static final class LayoutDef {
    	int dbIdx; String xml; int systemId;


		public LayoutDef(int dbIdx, String xml, int systemId) {
			super();
			this.dbIdx = dbIdx;
			this.xml = xml;
			this.systemId = systemId;
		}
    }
}
