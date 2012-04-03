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

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDecider;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.Details.Selection.RecordSel;
import net.sf.RecordEditor.utils.common.Common;




/**
 * This class reads LayoutDetails from a DataBase for the RecordEditor
 *
 * @author Bruce Martin
 * @version 0.55
 *
 */
public class CopyBookDbReader implements CopyBookInterface {

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
			Common.logMsg("EditRec Load Layouts - " + ex.getMessage(), null);
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

				if (Common.CRLF_STRING.equals(recordSepString)
				||  Common.DEFAULT_STRING.equals(recordSepString)) {
					recordSep = Common.LFCR_BYTES;
				} else if (Common.CR_STRING.equals(recordSepString)) {
					recordSep = Common.CR_BYTES;
				} else if (Common.LF_STRING.equals(recordSepString)) {
					recordSep = Common.LF_BYTES;
				}

				switch (recordType) {
				  case Common.rtGroupOfRecords:
				  case Common.rtFixedLengthRecords:
				  case Common.rtGroupOfBinaryRecords:
				      layouts = getGroupOfRecords(dbIndex, recordId);
				  break;
				  default:
					layouts = new RecordDetail[1];
					layouts[0] = new RecordDetail(pName, "", "",
									recordType, fix(resultset.getString(4)),
									fix(resultset.getString(5)),
									fontName,
									getFields(dbIndex, recordId, recordType, fontName),
									recordStyle);
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
			}
			resultset.close();

		} catch (Exception ex) {
			message = ex.getMessage();
		    Common.logMsg("CopyBook - " + ex.getMessage(), ex);
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
	private RecordDetail[] getGroupOfRecords(final int dbIndex, final int recordId) {
	    RecordDetail[] ret = null;
		try {
		    int subRecordId, recordType, parentId, recordStyle;
		    FieldDetail[] fields;
		    String tstFieldName, tstFieldValue;
		    String fontName;
			int i = 0;
			int childKey;
			boolean hasTreeDef = false;
			ReadRecordSelection readSel = ReadRecordSelection.getInstance();
			RecordSel recSel;
			//DetailRecord tmpLayouts[] = new DetailRecord[250];
			RecordDetail rec;
			ArrayList<RecordDetail> list = new ArrayList<RecordDetail>();
			HashMap<Integer, Integer> id2idx = new HashMap<Integer, Integer>();

			ResultSet resultset = Common.getDBConnection(dbIndex).createStatement().executeQuery(
				 "SELECT RS.Child_Record, R.RecordName, RS.Field_Start, RS.Field_Name, "
				+       "RS.Field_Value, R.RecordType, R.Delimiter, R.Quote, "
				+       "R.Canonical_Name, RS.PARENT_RECORDID, R.RECORD_STYLE, Child_Key, Default_Record "
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
			    fields        = getFields(dbIndex, subRecordId, recordType, fontName);
			    rec = new RecordDetail(resultset.getString(2),
//			    		  tstFieldName,
//			    		  tstFieldValue,
						  recordType,
						  resultset.getString(7),
						  resultset.getString(8),
						  fontName,
						  fields,
						  recordStyle);
			    recSel = readSel.getRecordSelection(
			    				dbIndex, subRecordId, childKey, 
			    				fields, tstFieldName, tstFieldValue);
			    
			    if (recSel != null) {
			    	rec.getRecordSelection().setRecSel(recSel);
			    	rec.getRecordSelection().setDefaultRecord(
			    				"Y".equals(resultset.getString(9))
			    			||	(  "".equals(tstFieldName)
			    				&& "*".equals(tstFieldValue))
			    	);
			    }

			    
			    rec.setSourceIndex(Common.getConnectionIndex());
			    
			    //System.out.println("parent ~~> " + subRecordId + " " + parentId);
			    if (parentId >= 0) {
			    	rec.setParentRecordIndex(parentId);
			    	hasTreeDef = true;
			    }
			    
				list.add(rec);
				id2idx.put(Integer.valueOf(subRecordId), Integer.valueOf(i));

				i += 1;
			}
			resultset.close();
			
			if (hasTreeDef) {
				Integer parentIdx;
				for (i = 0; i < list.size(); i++) {
					rec =  list.get(i);
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


			ret = new RecordDetail[i];
			list.toArray(ret);
			//for (i = 0; i < list.size(); i++) {
			//    ret[i] = (RecordDetail) list.get(i);
			//}

		} catch (Exception ex) {
		    Common.logMsg("Copybook: loadGroupOfRecords - " + ex.getMessage(), ex);
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
	
	private final FieldDetail[] getFields(
			 final int dbIndex,
			 final int recordId,
			 final int recordType,
			 final String fontName) {
	    FieldDetail[] fields = null;

		try {
			int i;
			FieldDetail newFld;
			ArrayList<FieldDetail> fieldList = new ArrayList<FieldDetail>();

			ResultSet resultset = Common.getDBConnection(dbIndex).createStatement().executeQuery(
					  "Select FieldPos, FieldLength, FieldName, "
			        +        "FieldType, DecimalPos, Description, "
			        +        "Cell_Format, Parameter"
			        + "  FROM Tbl_RF_RecordFields "
					+ " WHERE (RecordId=" + recordId + ") "
					+ " order by FieldPos ");

			while (resultset.next()) {
			    newFld = new FieldDetail(resultset.getString(3).trim(),
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

			fields = new FieldDetail[fieldList.size()];

			for (i = 0; i < fields.length; i++) {
			    fields[i] = fieldList.get(i);
			}
			//System.arraycopy(tmpFld, 0, fields, 0, i);

		} catch (Exception ex) {
			message = ex.getMessage();
			Common.logMsg("CopyBookInterface Error - " + message, ex);
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