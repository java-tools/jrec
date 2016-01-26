/*
 * Created on 7/05/2004
 *
 * This class provides an interface to the DB for Record_Edit and
 * Layout_Edit
 */
package net.sf.RecordEditor.utils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import net.sf.JRecord.Common.CommonBits;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDecider;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.Details.RecordDetail.FieldDetails;
import net.sf.JRecord.External.LayoutGetFieldByName;
import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.JRecord.Types.Type;
import net.sf.JRecord.detailsSelection.Convert;
import net.sf.JRecord.detailsSelection.RecordSel;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;




/**
 * This class reads LayoutDetails from a DataBase for the RecordEditor
 *
 * @author Bruce Martin
 * @version 0.55
 *
 */
public class CopyBookDbReader implements CopyBookInterface {

    private static final String COPY_BOOK_ERROR = LangConversion.convert("CopyBook Error:");
	private HashMap<String, RecordDecider> deciderMap = new HashMap<String, RecordDecider>();
    private String message = "";

    private static CopyBookDbReader instance = null;

    /**
     * Register a record Decider for later use
     *
     * @param layoutName record layout name
     * @param decider record decider
     */
    public void registerDecider(String layoutName,
           						RecordDecider decider) {
        deciderMap.put(layoutName.toUpperCase(), decider);
    }


	/**
	 * This method gets all the systems that have been defined
	 *
	 * @return ArrayList of Systems
	 * @throws SQLException Error recieved
	 */
	public final ArrayList<SystemItem> getSystems()
			throws SQLException  {
		return getSystems(Common.getConnectionIndex());
	}


	public final ArrayList<SystemItem> getSystems(int dbIndex)
	throws SQLException  {

		ArrayList<SystemItem> systems = new ArrayList<SystemItem>();

		systems.clear();

		ResultSet resultset =
			Common.getDBConnection(dbIndex).createStatement().executeQuery(
					"SELECT TblKey, Details FROM Tbl_TI_IntTbls WHERE (TblId=3) order by Details");
					/* note TableId = 3 ~ System Table */

		while (resultset.next()) {
			SystemItem sys = new SystemItem();
			sys.systemId = resultset.getInt(1);
			sys.description = resultset.getString(2);

			systems.add(sys);
		}
		resultset.close();


		Common.freeConnection(dbIndex);

		return systems;
	}


	/**
	 * Loads the various Layouts
	 *
	 * @param layouts record layouts
	 */
	public final void loadLayouts(final ArrayList<LayoutItem> layouts) {
		loadLayouts(Common.getConnectionIndex(), layouts);
	}

	public final void loadLayouts(int dbIndex, final ArrayList<LayoutItem> layouts) {
		layouts.clear();
		try {
			ResultSet resultset =
				Common.getDBConnection(dbIndex).createStatement().executeQuery(
			       "SELECT RecordName, Description, System FROM Tbl_R_Records "
				 + " WHERE ListChar='Y' ORDER BY RecordName");
			while (resultset.next()) {
				LayoutItem layout  = new LayoutItem(
				        				resultset.getString(1),
				        				resultset.getString(2),
				        				resultset.getInt(3));

				layouts.add(layout);
			}
			resultset.close();

		} catch (Exception ex) {
			Common.logMsgRaw(COPY_BOOK_ERROR + ex.getMessage(), null);
		} finally {
			Common.freeConnection(dbIndex);
		}
	}



	/**
	 * Get a group of records from the name
	 *
	 * @param pName Name of the Record Group being Request
	 *
	 * @return Group of records
	 */
	public LayoutDetail getLayout(String pName) {
	    return getLayout(Common.getConnectionIndex(), pName);
	}


	public LayoutDetail getLayout(int dbIndex, String pName) {
	    LayoutDetail ret = null;

		message = "";
		try {
			ResultSet resultset = Common.getDBConnection(dbIndex).createStatement().executeQuery(
				"SELECT RecordId, Description, RecordType, "
			   +      " Delimiter, Quote, RecordSep, "
			   +      " Canonical_Name, RecSepList, "
			   +      " File_Structure, RECORD_STYLE "
			   + " FROM Tbl_R_Records R "
			   + "WHERE (R.RecordName='" + pName + "')");

			if (resultset.next()) {
			    RecordDetail[] layouts;
				int    recordId    = resultset.getInt(1);
				String description = resultset.getString(2);
				int    recordType  = resultset.getInt(3);
				byte[] recordSep = {};

		       	try {
		       		recordSep = resultset.getString(6).getBytes();
	        	} catch (Exception e) {
	        		try {
	        		  recordSep = resultset.getBytes(6);
	        		} catch (Exception ex) {
	        	  }
	        	}

				String fontName    = fix(resultset.getString(7));
				String recordSepString = fix(resultset.getString(8));
				int fileStructure  = resultset.getInt(9);
				int recordStyle    = resultset.getInt(10);
				RecordDecider decider  = deciderMap.get(fix(pName).toUpperCase());
				RecordDtls recdtls = null;

			    recordSep = CommonBits.getEolBytes(recordSep, recordSepString, fontName);

				switch (recordType) {
				  case Common.rtGroupOfRecords:
				  case Common.rtFixedLengthRecords:
				  case Common.rtGroupOfBinaryRecords:
					  recdtls = getGroupOfRecords(dbIndex, recordId);
				      layouts = recdtls.getRecords();
				  break;
				  default:
					FieldDetails[] fields = getFields(dbIndex, recordId, recordType, fontName);
					layouts = new RecordDetail[1];

					if (fileStructure == Constants.IO_NAME_1ST_LINE && (fields == null || fields.length == 0)) {
						fields = new FieldDetails[1];
						fields[0] = new FieldDetails("a", "", Type.ftChar, 0, "", 0, "");
						fields[0].setPosOnly(1);
					}

					layouts[0] = new RecordDetail(pName, "", "",
									recordType, fix(resultset.getString(4)),
									fix(resultset.getString(5)),
									fontName,
									fields,
									recordStyle, 0);
					layouts[0].setSourceIndex(Common.getConnectionIndex());
				}

				ret = new LayoutDetail(pName,
				        			  layouts,
				        			  description,
				        			  recordType,
				        			  recordSep,
				        			  recordSepString,
				        			  fontName,
				        			  decider,
				        			  fileStructure);
				if (recdtls != null) {
					for (int i = 0; i < recdtls.records.size(); i++)  {
					    RecordSel recordSel = recdtls.selections.get(i);
						if (recordSel != null && recdtls.selections.get(i).getSize() > 0) {
					    	layouts[i].getRecordSelection().setRecSel(
					    			(new Convert()).convert(recdtls.selections.get(i), new LayoutGetFieldByName(ret,  layouts[i])));
					    }

					}
				}
			}
			resultset.close();

		} catch (Exception ex) {
			message = ex.getMessage();
		    Common.logMsg(COPY_BOOK_ERROR + ex.getClass().getName() + ex.getMessage(), ex);
		    ex.printStackTrace();
		} finally {
			Common.freeConnection(dbIndex);
		}

		return ret;
	}


	/**
	 * Get all sub-record definition associated with a suookied Group-Record
	 *
	 * @param recordId record for which Sub Records
	 *
	 * @return Record List
	 */
	private RecordDtls getGroupOfRecords(final int dbIndex, final int recordId) {
		RecordDtls ret = new RecordDtls();
		try {
		    int subRecordId, recordType, parentId, recordStyle, childId;
		    RecordDetail.FieldDetails[] fields;
		    String tstFieldName, tstFieldValue;
		    String fontName;
			int i = 0;
			int childKey;
			boolean hasTreeDef = false;
			ReadRecordSelection readSel = ReadRecordSelection.getInstance();
			RecordSel recSel;
			//DetailRecord tmpLayouts[] = new DetailRecord[250];
			RecordDetail rec;
			//ArrayList<RecordDetail> list = new ArrayList<RecordDetail>();
			
			HashMap<Integer, Integer> id2idx = new HashMap<Integer, Integer>();


			ResultSet resultset = Common.getDBConnection(dbIndex).createStatement().executeQuery(
				 "SELECT RS.Child_Record, R.RecordName, RS.Field_Start, RS.Field_Name, "
				+       "RS.Field_Value, R.RecordType, R.Delimiter, R.Quote, "
				+       "R.Canonical_Name, RS.PARENT_RECORDID, R.RECORD_STYLE, "
				+       "Child_Key, Default_Record, Child_Id "
			    + " FROM Tbl_RS2_SubRecords RS INNER JOIN Tbl_R_Records R "
			    +   " ON RS.Child_Record = R.RecordId "
				+ "WHERE (RS.RecordId=" + recordId + ")");

			while (resultset.next()) {
			    subRecordId   = resultset.getInt(1);
			    tstFieldName  = resultset.getString(4);
			    tstFieldValue = resultset.getString(5);
			    recordType    = resultset.getInt(6);
			    fontName      = resultset.getString(9);
			    parentId      = resultset.getInt(10);
			    recordStyle   = resultset.getInt(11);
			    childKey      = resultset.getInt(12);
			    childId       = resultset.getInt(14);
			    fields        = getFields(dbIndex, subRecordId, recordType, fontName);

			    if (tstFieldValue == null) {
			    	tstFieldValue = "";
			    }
			    rec = new RecordDetail(resultset.getString(2),
//			    		  tstFieldName,
//			    		  tstFieldValue,
						  recordType,
						  resultset.getString(7),
						  resultset.getString(8),
						  fontName,
						  fields,
						  recordStyle,
						  childId,
						  false);
			    
			    recSel = readSel.getRecordSelection(
			    				dbIndex, recordId, childKey,
			    				fields, tstFieldName, tstFieldValue);
			    ret.selections.add(recSel);
//			    if (recSel != null) {		    	
//			    	rec.getRecordSelection().setRecSel(recSel);
			    	rec.getRecordSelection().setDefaultRecord(
			    				"Y".equalsIgnoreCase(resultset.getString(13))
			    			||	(  "".equals(tstFieldName)
			    				&& "*".equals(tstFieldValue))
			    	);
//			    }


			    rec.setSourceIndex(Common.getConnectionIndex());

			    //System.out.println("parent ~~> " + subRecordId + " " + parentId);
			    if (parentId >= 0) {
			    	rec.setParentRecordIndex(parentId);
			    	hasTreeDef = true;
			    }

				ret.records.add(rec);
				id2idx.put(Integer.valueOf(childId), Integer.valueOf(i));

				i += 1;
			}
			resultset.close();

			if (hasTreeDef) {
				Integer parentIdx;
				for (i = 0; i < ret.records.size(); i++) {
					rec =  ret.records.get(i);
			    	//System.out.print("setting ==> " + i + " " + rec.getParentRecordIndex());
					if (rec.getParentRecordIndex() >= 0) {
						parentIdx = id2idx.get(Integer.valueOf(rec.getParentRecordIndex()));
						//System.out.print(" > " + parentIdx);
						rec.setParentRecordIndex(Constants.NULL_INTEGER);
						if (parentIdx != null) {
							rec.setParentRecordIndex(parentIdx.intValue());
						}
					}
			    	//System.out.println("<==");
				}
			}


			//ret = new RecordDetail[i];
			//list.toArray(ret);
			//for (i = 0; i < list.size(); i++) {
			//    ret[i] = (RecordDetail) list.get(i);
			//}

		} catch (Exception ex) {
		    Common.logMsg(AbsSSLogger.ERROR, "Copybook Error: Reading Child Records:", ex.getClass().getName() + " " + ex.getMessage(), ex);
		    //System.out.println(sql);
		    ex.printStackTrace();
//		} finally {
//			Common.freeConnection(dbIndex);
		}

		return ret;
	}


	/**
	 * Get the fields associated with a record Definition
	 *
	 * @param recordId Record Id
	 * @param recordType Type of record
	 * @param fontName font name  of the fields
	 *
	 * @return all the fields for a Record
	 */
	public final FieldDetail[] getFields(final int recordId,
	        							 final int recordType,
	        							 final String fontName) {
		return getFields(Common.getConnectionIndex(), recordId, recordType, fontName);
	}

	private final RecordDetail.FieldDetails[] getFields(
			 final int dbIndex,
			 final int recordId,
			 final int recordType,
			 final String fontName) {
		RecordDetail.FieldDetails[] fields = null;

		try {
			int i;
			RecordDetail.FieldDetails newFld;
			ArrayList<RecordDetail.FieldDetails> fieldList = new ArrayList<RecordDetail.FieldDetails>();

			ResultSet resultset = Common.getDBConnection(dbIndex).createStatement().executeQuery(
					  "Select Field_Pos, Field_Length, Field_Name, "
			        +        "Field_Type, Decimal_Pos, Field_Description, "
			        +        "Cell_Format, Field_Parameter"
			        + "  FROM Tbl_RF1_RecordFields "
					+ " WHERE (RecordId=" + recordId + ") "
					+ " order by Field_Pos ");

			while (resultset.next()) {
			    newFld = new RecordDetail.FieldDetails(resultset.getString(3).trim(),
			            				 resultset.getString(6),
			            				 resultset.getInt(4),
			            				 resultset.getInt(5),
			            				 fontName,
			            				 resultset.getInt(7),
			            				 resultset.getString(8));

				if (recordType == Common.rtDelimited
				||  recordType == Common.rtDelimitedAndQuote) {
				    newFld.setPosOnly(resultset.getInt(1));
				} else {
				    newFld.setPosLen(resultset.getInt(1),
							resultset.getInt(2));
				}

			    fieldList.add(newFld);
			}
			resultset.close();

			fields = new RecordDetail.FieldDetails[fieldList.size()];

			for (i = 0; i < fields.length; i++) {
			    fields[i] = fieldList.get(i);
			}
			//System.arraycopy(tmpFld, 0, fields, 0, i);

		} catch (Exception ex) {
			message = ex.getMessage();
			Common.logMsg(AbsSSLogger.ERROR, "CopyBook Error- Reading Fields:", message, ex);
//		} finally {
//			Common.freeConnection(dbIndex);
		}

		return fields;
    }

	/**
	 * get the error message
	 * @return last error message
	 */
	public final String getMessage() {
		return message;
	}


	 private String fix(String s) {
		 if (s == null) {
			 return null;
		 }
		 return s.trim();
	 }

	 //TODO update
	 private static class RecordDtls {
		 ArrayList<RecordDetail> records = new ArrayList<RecordDetail>();
		 ArrayList<RecordSel> selections  = new ArrayList<RecordSel>();
		 
		 private RecordDetail[] getRecords() {
			 return records.toArray(new RecordDetail[records.size()]);
		 }
	 }
	 
	 /**
	  * get instance of CopyBookDbReader
	  * @return instance of CopyBookDbReader
	  */
	 public static CopyBookDbReader getInstance() {
		 if (instance == null) {
			 instance = new CopyBookDbReader();
		 }

		 return instance;
	 }
}