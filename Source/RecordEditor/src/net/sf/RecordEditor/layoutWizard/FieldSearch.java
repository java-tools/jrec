package net.sf.RecordEditor.layoutWizard;

import java.util.ArrayList;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Types.Type;
import net.sf.RecordEditor.utils.common.Common;

/**
 * This class is for finding Fields in supplied lines of a file
 * 
 * @author Bruce Martin
 *
 */
public class FieldSearch {

	private static final int NOT_IN_FIELD = 0;
	private static final int IN_COMP3 = 1;
	private static final int IN_ZONED = 2;
	private static final int IN_PC_ZONED = 3;
	private static final int IN_NUMERIC = 4;
	private static final int IN_BINARY = 5;
	private static final int IN_TEXT = 6;
	
    private static final int TYPE_SPACE = 0;
    private static final int TYPE_LAST_SPACE = 1;
    private static final int TYPE_COMP3 = 2;
    private static final int TYPE_COMP3_FINAL_BYTE = 3;
    private static final int TYPE_HEX_ZERO = 4;
    private static final int TYPE_ZERO = 5;
    private static final int TYPE_NUMBER = TYPE_ZERO + 1;
    private static final int POSSIBLE_MAINFRAME_ZONED_SIGN = TYPE_NUMBER + 1;
    private static final int POSSIBLE_PC_ZONED_SIGN = POSSIBLE_MAINFRAME_ZONED_SIGN + 1;
    private static final int TYPE_TEXT = POSSIBLE_PC_ZONED_SIGN + 1;
    private static final int TYPE_T = TYPE_TEXT + 1;
    private static final int TYPE_F = TYPE_TEXT + 2;
    private static final int TYPE_Y = TYPE_TEXT + 3;
    private static final int TYPE_N = TYPE_TEXT + 4;
    private static final int TYPE_NORMAL = TYPE_N + 1;
    private static final int TYPE_SIGN = TYPE_NORMAL + 1;
    private static final int TYPE_SLASH1 = TYPE_SIGN + 1;
    private static final int TYPE_SLASH2 = TYPE_SLASH1 + 1;
    private static final int TYPE_DASH = TYPE_SLASH1 + 2;
    private static final int TYPE_COLON = TYPE_SLASH1 + 3;
    private static final int TYPE_DOT = TYPE_SLASH1 + 4;
    private static final int TYPE_YEAR_CHAR_1 = TYPE_DOT + 1;
    private static final int TYPE_YEAR_CHAR_2 = TYPE_DOT + 2;
    private static final int START_BIN_FIELD = TYPE_DOT + 3;
    private static final int TYPE_OTHER = START_BIN_FIELD + 1;
    
    private static final int COUNT_IDX = TYPE_OTHER + 1;
    
    private CheckByte[] byteChecks = new CheckByte[TYPE_OTHER];
    //private int[] byteCounts = new int[COUNT_IDX + 1];
	 
    private String fontname = "";
    private RecordDefinition recordDef;
    private Details currDetails;
    
    private int[] charType, fieldIdentifier;
    private int fieldId;

    
    
    public void findFields(
    		Details details, RecordDefinition recordDefinition, 
    		boolean lookMainframeZoned, boolean lookPcZoned,
    		boolean lookComp3, 
    		boolean lookCompBigEndian, boolean lookCompLittleEndian) {
    	fontname = details.fontName;
    	currDetails = details;
    	recordDef = recordDefinition;
        byteChecks[TYPE_SPACE] = new Check4Bytes(" ");
        byteChecks[TYPE_LAST_SPACE] = new LastSpace();
        byteChecks[TYPE_COMP3] = new Comp3Byte();
        byteChecks[TYPE_COMP3_FINAL_BYTE] = new Comp3SignByte();
        byteChecks[TYPE_HEX_ZERO] = new Check4Bytes((byte) 0);
        byteChecks[TYPE_NORMAL] = new NormalByte();
        byteChecks[TYPE_ZERO] = new Check4Bytes("0");
        byteChecks[TYPE_NUMBER] = new Check4Bytes("0123456789");
        byteChecks[POSSIBLE_MAINFRAME_ZONED_SIGN] = new Check4Bytes("}{ABCDEFGHIJKLMNOPQR");
        byteChecks[POSSIBLE_PC_ZONED_SIGN] = new Check4Bytes("@ABCDEFGHIPQRSTUVWXY");
        byteChecks[TYPE_SIGN] = new Check4Bytes("+-");
        byteChecks[TYPE_TEXT] = new Check4Bytes(Common.STANDARD_CHARS);
        byteChecks[TYPE_T] = new Check4Bytes("T");
        byteChecks[TYPE_F] = new Check4Bytes("F");
        byteChecks[TYPE_Y] = new Check4Bytes("Yy");
        byteChecks[TYPE_N] = new Check4Bytes("Nn");
        byteChecks[TYPE_SLASH1] = new Check4Bytes("/");
        byteChecks[TYPE_SLASH2] = new Check4Bytes("\\");
        byteChecks[TYPE_DASH] = new Check4Bytes("-");
        byteChecks[TYPE_COLON] = new Check4Bytes(":");
        byteChecks[TYPE_DOT] = new Check4Bytes(".");
        byteChecks[TYPE_YEAR_CHAR_1] = new Check4Bytes("12");
        byteChecks[TYPE_YEAR_CHAR_2] = new Check4Bytes("901");
        byteChecks[START_BIN_FIELD] = new CheckStartBinField();
 	
    	if (recordDef.records != null && recordDef.numRecords > 0) {
    		int i, limit, limit1;
    		int size = 0;
    		int[][] counts; 
    		
    		for (i = 0; i < recordDef.numRecords; i++) {
    			size = Math.max(size, recordDef.records[i].length);
    		}
 
    		counts = new int[size][];
       		fieldId = 0;
       		ff100_determineByteTypes(recordDefinition.columnDtls, size, counts);
       		
        	limit = recordDef.numRecords;
       		limit1 = recordDef.numRecords;
       		if (limit > 3) {
       			limit = (limit * 9 - 1) / 10;
       		}
       		if (limit > 1) {
       			limit = (limit * 6) / 10;
       		}
       		
       		ff200_checkForCobolNumericFields(limit, size, counts, lookMainframeZoned, lookPcZoned, lookComp3);
       		ff300_checkForDateAndTime(limit, size, counts);
       		ff400_checkForNumericFields(limit, size, counts, lookCompBigEndian, lookCompLittleEndian);
       		ff500_checkForTextFields(limit, limit1, size, counts);
       		
       		ff600_createFields(counts);
    	}
    	charType = null;
    	fieldIdentifier = null;
    	recordDef = null;
    }
    
    /**
     * Determine the "type" for each byte
     * @param size line size
     * @param counts type counts
     */
    private void ff100_determineByteTypes(
    		ArrayList<ColumnDetails> columnDtls, int size, int[][] counts) {
    	int i, j, k;
    	boolean[] foundType = null;
    	
		charType = new int[size];
		fieldIdentifier = new int[size];
		for (i = 0; i < size; i++) {
			charType[i] = 0;
			fieldIdentifier[i] = 0;
			counts[i] = new int[COUNT_IDX + 1];
			for (j = 0; j < byteChecks.length; j++) {
				counts[i][j] = 0;
			}
		}
		
		columnDtls.clear();
		if (currDetails.recordType == Details.RT_MULTIPLE_RECORDS) {
			ColumnDetails dtl;
			recordDef.addKeyField(currDetails, false);
			
			for (i = 0; i < columnDtls.size(); i++) {
				dtl = columnDtls.get(i);
				fieldId += 1;
				//System.out.println(" ## Field " + fieldId + " " + dtl.start + " " + dtl.length);
				for (j = 0; j < dtl.length; j++) {
					fieldIdentifier[dtl.start + j - 1] = fieldId;
				}
			}
		}
		
		
		for (k = 0; k < byteChecks.length; k++) {
			byteChecks[k].reset();		
		}
		
   		for (i = 0; i < recordDef.numRecords; i++) {
   			if (foundType == null 
   			|| foundType.length < recordDef.records[i].length) {
   				foundType = new boolean[recordDef.records[i].length];
   			}
  			for (j = 0; j < recordDef.records[i].length; j++) {
				counts[j][COUNT_IDX] += 1;
    			foundType[j] = false;
   				for (k = 0; k < byteChecks.length; k++) {
   					if (byteChecks[k].forward()
   					&& byteChecks[k].isAmatch(recordDef.records[i][j])) {
   						counts[j][k] += 1;
   						foundType[j] = true;
   					}
   				}
  			}
  			
  			for (j = recordDef.records[i].length-1; j >= 0; j--) {
   				for (k = byteChecks.length - 1; k >= 0; k--) {
   					if ((! byteChecks[k].forward())
   					&& byteChecks[k].isAmatch(recordDef.records[i][j])) {
   						counts[j][k] += 1;
   						foundType[j] = true;
   					}
   				}

   				if (foundType[j]) {
   					counts[j][TYPE_OTHER] += 1; 
   				}
   			}
		}

    }
    
    /**
     * Find Numeric fields
     * @param limit limit 
     * @param size maximum line size
     * @param counts Byte Type counts 
     */
    private void ff200_checkForCobolNumericFields(
    		int limit, int size, int[][] counts, 
    		boolean lookMainframeZoned, boolean lookPcZoned,
    		boolean lookComp3) {
       		
       		//boolean isComp3 = false;
    	boolean wasStartOfBin = false;
   		int type = NOT_IN_FIELD;
   		for (int i = size -1; i >= 0; i--) {
   			if (fieldIdentifier[i] > 0) {
   				type = NOT_IN_FIELD;
   			} else if (lookComp3 && counts[i][TYPE_COMP3_FINAL_BYTE] >= limit) {
   				type = IN_COMP3;
   				fieldId += 1;
   	   			charType[i] = Type.ftPackedDecimal;
    			fieldIdentifier[i] = fieldId;
   			} else if ((type == IN_COMP3) 
   				   && counts[i][TYPE_COMP3] >= limit
   				   && ! wasStartOfBin) {
   				fieldIdentifier[i] = fieldId;
   				charType[i] = charType[i+1]; 
   			} else if (	lookMainframeZoned
   					&&	counts[i][POSSIBLE_MAINFRAME_ZONED_SIGN] >= limit
   					&& 	i > 0 && counts[i-1][TYPE_NUMBER] >= limit){
   				type = IN_ZONED;
   				fieldId += 1;
   	   			charType[i] = Type.ftZonedNumeric;
    			fieldIdentifier[i] = fieldId;
   			} else if (lookPcZoned
   					&&	counts[i][POSSIBLE_PC_ZONED_SIGN] >= limit
   					&& 	i > 0 && counts[i-1][TYPE_NUMBER] >= limit){
   				type = IN_PC_ZONED;
   				fieldId += 1;
   	   			charType[i] = Type.ftFjZonedNumeric;
    			fieldIdentifier[i] = fieldId;
  			} else if ((type == IN_ZONED || type == IN_PC_ZONED || type == IN_NUMERIC) 
  					&& counts[i][TYPE_NUMBER] >= limit) {
   				fieldIdentifier[i] = fieldId;
   				charType[i] = charType[i+1]; 
  			} else if ((type == IN_NUMERIC) && counts[i][TYPE_SIGN] >= limit) {
   				fieldIdentifier[i] = fieldId;
   				charType[i] = charType[i+1]; 
   				type = NOT_IN_FIELD;
//  			} else if ((type == NOT_IN_FIELD) && counts[i][TYPE_NUMBER] >= limit) {
//   				type = IN_NUMERIC;
//   				fieldId += 1;
//   	   			charType[i] = Type.ftFjZonedNumeric;
//    			fieldIdentifier[i] = fieldId;
  			} else {
  				type = NOT_IN_FIELD;
  			}
   			
   			wasStartOfBin = counts[i][START_BIN_FIELD] >= limit;
//      		System.out.println(" ==> " + i + " " + type + " : " + counts[i][TYPE_TEXT]  + " ~ #" 
//      				+ " > " + fieldId + " ==> " + counts[i][TYPE_COMP3]  + " :: " + counts[i][TYPE_COMP3_FINAL_BYTE] );
    	}
    }
    
    private void ff300_checkForDateAndTime(int limit, int size, int[][] counts) {

    	for (int i = 2; i < size - 5; i++) {
     		if (fieldIdentifier[i] > 0) {

    		} else if ((counts[i][TYPE_SLASH1] >= limit && counts[i + 3][TYPE_SLASH1] >= limit)
    				|| (counts[i][TYPE_SLASH2] >= limit && counts[i + 3][TYPE_SLASH2] >= limit)
    				|| (counts[i][TYPE_DASH] >= limit && counts[i + 3][TYPE_DASH] >= limit)
    				|| (counts[i][TYPE_COLON] >= limit && counts[i + 3][TYPE_COLON] >= limit)
    				|| (counts[i][TYPE_DOT] >= limit && counts[i + 3][TYPE_DOT] >= limit)) {
    			if (ff310_isPossibleDateOrTime(counts, i - 4, 4, limit)
    			&& ff310_isPossibleDateOrTime(counts, i + 1, 2, limit)
    			&& ff310_isPossibleDateOrTime(counts, i + 4, 2, limit) 
    			&& (	ff320_checkDayMthLast(i, limit))) {
    				ff340_setDateTimeField(i - 4, 10);
    			} else if (ff310_isPossibleDateOrTime(counts, i - 2, 2, limit)
    	    			&& ff310_isPossibleDateOrTime(counts, i + 1, 2, limit)
    	    			&& ff310_isPossibleDateOrTime(counts, i + 4, 4, limit)
    	    			&& ff330_checkDayMthFirst(i, limit)) {
    				ff340_setDateTimeField(i - 2, 10);
       			} else if (ff310_isPossibleDateOrTime(counts, i - 2, 2, limit)
    	    			&& ff310_isPossibleDateOrTime(counts, i + 1, 2, limit)
    	    			&& ff310_isPossibleDateOrTime(counts, i + 4, 2, limit)) {
    				if (ff320_checkDayMthLast(i, limit)
        	   		||	ff330_checkDayMthFirst(i, limit)) {
    					ff340_setDateTimeField(i - 2, 8);
    				}
       			}
    			
         		if (fieldIdentifier[i] > 0) {

        		} else if ((counts[i][TYPE_DASH] >= limit && counts[i + 3][TYPE_DASH] >= limit)
        				|| (counts[i][TYPE_COLON] >= limit && counts[i + 3][TYPE_COLON] >= limit)
        				|| (counts[i][TYPE_DOT] >= limit && counts[i + 3][TYPE_DOT] >= limit)) {
        			if (ff310_isPossibleDateOrTime(counts, i - 2, 2, limit)
        	    	&& ff310_isPossibleDateOrTime(counts, i + 1, 2, limit)
        	    	&& ff310_isPossibleDateOrTime(counts, i + 4, 2, limit)
        	    	&& ff340_checkTime(i, limit)) {
        				ff340_setDateTimeField(i - 2, 8);
        			}
        		}

  			}
     		
//      		System.out.println(" ::> " + i + " " + limit +  " : " + counts[i][TYPE_SLASH1]  + " ~ # " 
//      				+ ff310_isPossibleDateOrTime(counts, i - 2, 2, limit)
//	    			+ " && " + ff310_isPossibleDateOrTime(counts, i + 1, 2, limit)
//	    			+ " && " + ff310_isPossibleDateOrTime(counts, i + 3, 2, limit)
//       				 );
    	}
    }
   
    
    
    private boolean ff310_isPossibleDateOrTime(int[][] counts, int start, int len, int limit) {
    	boolean ret = false;
    	
    	//System.out.print("Date Check: " + start + " " + len); 
    		
    	if (start >= 0 && start + len <= counts.length) {
    		//System.out.print(" " + (len != 4) + " || " + (counts[start][TYPE_YEAR_CHAR_1] >= limit) + " >>> " );
    		ret  = (len != 4) 
    				|| (counts[start][TYPE_YEAR_CHAR_1] >= limit && counts[start+1][TYPE_YEAR_CHAR_2] >= limit);
    		for (int i = 0; ret && i < len; i++) {
    			//System.out.print(counts[start + i][TYPE_NUMBER] + " ");
    			ret = counts[start + i][TYPE_NUMBER] >= limit;
    		}
    	}
    	//System.out.println(" " + ret);
    	
    	return ret;
    }

    
    private boolean ff320_checkDayMthLast(int pos, int limit) {
    	//System.out.println("ff320_checkDayMthLast ");
    	return	(		ff350_checkRange(pos + 4, 2, 31, limit)
    				&&	ff350_checkRange(pos +1, 2, 12, limit))
				||  (		ff350_checkRange(pos + 4, 2, 12, limit)
    				&&	ff350_checkRange(pos +1, 2, 31, limit));
    }
    
    private boolean ff330_checkDayMthFirst(int pos, int limit) {
    	//System.out.println("ff330_checkDayMthFirst ");
    	return 	(		ff350_checkRange(pos - 2, 2, 31, limit)
						&&	ff350_checkRange(pos +1, 2, 12, limit))
					||  (		ff350_checkRange(pos - 2, 2, 12, limit)
						&&	ff350_checkRange(pos +1, 2, 31, limit));
    }
    private boolean ff340_checkTime(int pos, int limit) {
    	return 	(		ff350_checkRange(pos - 2, 2, 25, limit)
    					&&	ff350_checkRange(pos +1, 2, 61, limit)
						&&	ff350_checkRange(pos +4, 2, 61, limit));
    }
 
    private boolean ff350_checkRange(int start, int len, int maxValue, int limit) {
    	int count = 0;
    	int diff = recordDef.numRecords - limit;
    	//System.out.println("ff350: " + diff + " " + recordDef.numRecords + " " + limit);
    	for (int i = 0; (i - count <= diff) && i < recordDef.numRecords; i++) {
    		try {
//    			System.out.println(" :: " + start + " " + len + " " +Conversion.getString(
//    					recordDef.records[i], start, start + len, fontname)
//    					+ " " + maxValue + " " + count + " ?> " + limit + " " + diff);
    			if (maxValue > Integer.parseInt(Conversion.getString(
    					recordDef.records[i], start, start + len, fontname))) {
    				count += 1;
    			}
    		} catch (Exception e) {
    			System.out.println("Error: " + e.getMessage());
			}
    	}
    	
    	return count >= limit;
    }
     
    private void ff340_setDateTimeField(int start, int len) {
    	fieldId += 1;
    	for (int i = start; i < start + len; i++) {
			fieldIdentifier[i] = fieldId;
    	}
    }
    
    private void ff400_checkForNumericFields(int limit, int size, int[][] counts, 
    		boolean lookCompBigEndian, boolean lookCompLittleEndian) {
    	int type = NOT_IN_FIELD;
    	int len = 0;
    	boolean lastCharNonZero = true;
    	boolean allowDot = false;
    	boolean wasHexZero = false;
    	boolean holdWasHexZero = true;
    	
    	for (int i = 0; i < size; i++) {
    		holdWasHexZero = false;
     		if (fieldIdentifier[i] > 0) {
    			type = NOT_IN_FIELD;
    			allowDot = false;
    		} else if (lookCompBigEndian && counts[i][TYPE_HEX_ZERO] >= limit) {
    			holdWasHexZero = true;
    			
    			if (len == 8 || ! wasHexZero) {
	    			type = IN_BINARY;
	  				fieldId += 1;
	    			len = 1;
    			} else {
    				len += 1;
    			}
   	   			charType[i] = Type.ftBinaryBigEndian;
    			fieldIdentifier[i] = fieldId;
    		} else if ((lastCharNonZero && counts[i][TYPE_ZERO] >= limit
    				&&  i < size-1 && counts[i+1][TYPE_NUMBER] >= limit)
    				|| ((type != IN_NUMERIC) && counts[i][TYPE_NUMBER] >= limit)) {
    			type = IN_NUMERIC;
  				fieldId += 1;
   	   			charType[i] = Type.ftNumRightJustified;
    			fieldIdentifier[i] = fieldId;
    			allowDot = true;
  			} else if ((type == IN_NUMERIC) && counts[i][TYPE_NUMBER] >= limit) {
   				fieldIdentifier[i] = fieldId;
   				charType[i] = Type.ftNumRightJustified;
  			} else if ((allowDot && type == IN_NUMERIC) && counts[i][TYPE_DOT] >= limit) {
   				fieldIdentifier[i] = fieldId;
   				charType[i] = Type.ftNumRightJustified;
    			allowDot = false;
    		} else if (len == 8 || (counts[i][TYPE_TEXT] + counts[i][TYPE_SPACE] >= limit)) {
  				type = NOT_IN_FIELD;
  			} else if (type == IN_BINARY) {
  	   			charType[i] = Type.ftBinaryBigEndian;
    			fieldIdentifier[i] = fieldId;
    			len += 1;
  			}
//       		System.out.println(" --> " + i + " " + type + " : " + counts[i][TYPE_TEXT]  + " ~ " + len
//       				+ " > " + fieldId);
       		lastCharNonZero = counts[i][TYPE_ZERO] < limit;
       		wasHexZero = holdWasHexZero;
    	}
    	
    	if (lookCompLittleEndian && ! lookCompBigEndian) {
	  		type = NOT_IN_FIELD;
	   		for (int i = size -1; i >= 0; i--) {
	   			if (fieldIdentifier[i] > 0) {
	   				type = NOT_IN_FIELD;
	   	   		} else if (counts[i][TYPE_HEX_ZERO] >= limit) {
	    			type = IN_BINARY;
	  				fieldId += 1;
	   	   			charType[i] = Type.ftBinaryInt;
	    			fieldIdentifier[i] = fieldId;
	    			len = 1;
	    		} else if (len == 8 || (counts[i][TYPE_TEXT] + counts[i][TYPE_SPACE] >= limit)) {
	  				type = NOT_IN_FIELD;
	   			} else if (type == IN_BINARY) {
	  	   			charType[i] = Type.ftBinaryInt;
	    			fieldIdentifier[i] = fieldId;
	    			len += 1;
	  			}
	   		}
    	}
    }
    
    private void ff500_checkForTextFields(int limit, int limit1, int size, int[][] counts) {
    	boolean wasSpace =false;
    	int type = NOT_IN_FIELD;
    	
    	for (int i = 0; i < size; i++) {
    		if (fieldIdentifier[i] > 0) {
    			type = NOT_IN_FIELD;
    		} else if ((counts[i][TYPE_T] + counts[i][TYPE_F] >= limit
    				&& counts[i][TYPE_T] > 0 && counts[i][TYPE_F] > 0) 
    				&& (counts[i][TYPE_TEXT] + counts[i][TYPE_SPACE] >= limit)) {
    			type = NOT_IN_FIELD;
  				fieldId += 1;
   	   			charType[i] = Type.ftCheckBoxTF;
    			fieldIdentifier[i] = fieldId;
    		} else if (counts[i][TYPE_Y] + counts[i][TYPE_Y] >= limit
    				&& counts[i][TYPE_Y]  > 0 && counts[i][TYPE_N] > 0
    				&& counts[i][TYPE_Y] + counts[i][TYPE_N] >= counts[i][TYPE_TEXT]-1) {
    			type = NOT_IN_FIELD;
  				fieldId += 1;
   	   			charType[i] = Type.ftCheckBoxYN;
    			fieldIdentifier[i] = fieldId;
    		} else if (wasSpace && counts[i][TYPE_TEXT] >= limit) {
   				type = IN_TEXT;
   				fieldId += 1;
   	   			charType[i] = Type.ftChar;
    			fieldIdentifier[i] = fieldId;
   			} else if ((type == IN_TEXT) && (counts[i][TYPE_TEXT] + counts[i][TYPE_SPACE] >= limit)) {
   				fieldIdentifier[i] = fieldId;
   				charType[i] = Type.ftChar;
    		}
   		
//      		System.out.println(" ~~> " + i + " " + type + " : " + counts[i][TYPE_TEXT]  + " ~ #" 
//       				+ " > " + fieldId + " > " + fieldIdentifier[i] + " " + wasSpace + " "
//       				+ " " + (wasSpace && counts[i][TYPE_TEXT] >= limit)
//       				+ " ~ " + counts[i][TYPE_LAST_SPACE] + " " + limit);
    		
    		wasSpace = counts[i][TYPE_LAST_SPACE] >= limit;
 
    	}
    }
    
    private void ff600_createFields(int[][] counts) {
    	int lastField = -1;
    	int type;
    	ColumnDetails colDtls = null;
    	
    	recordDef.columnDtls.clear();
    	for (int i = 0; i < fieldIdentifier.length; i++) {
//    		System.out.print(" --> " + i + " " + fieldIdentifier[i] + " : ");
//    		for (int j = 0; j < counts[i].length; j++) {
//    			System.out.print("  " + counts[i][j]);
//    		}
//    		System.out.println();
    		
    		if (lastField != fieldIdentifier[i]) {
    			lastField = fieldIdentifier[i];
    			
    			if (colDtls != null) {
    				recordDef.columnDtls.add(colDtls);
    			}
    			type = Type.ftChar;
    			if (charType[i] > 0) {
    				type = charType[i];
    			}

				colDtls = new ColumnDetails(i+1, type);
				colDtls.length = 1;
				
				if (currDetails.recordType == Details.RT_MULTIPLE_RECORDS) {
					for (KeyField k : currDetails.keyFields) {
						if (i+1 == k.keyStart) {
							colDtls.name = k.keyName;
						}
					}
				}
    		} else {
    			colDtls.length += 1;
    		}
    	}
    	
		if (colDtls != null) {
			recordDef.columnDtls.add(colDtls);
		}

    }
    
    
    
    /* -------------------------------------------------------------------------- *
     *
     * Class's for finding fields
     * 
     * -------------------------------------------------------------------------- */
    
    /**
     * Standard Field Check definition
     */
    private interface CheckByte {
    	public void reset();
    	public boolean forward();
    	public boolean isAmatch(byte b);
    }
    
    private static class BasicCheck {
    	public void reset() {
    	
    	}
    	public boolean forward() {
    		return true;
    	}
    	
       	public boolean search(byte[] tstBytes, byte b) {
    		for (int i = 0; i < tstBytes.length; i++) {
    			if (b == tstBytes[i]) {
    				return true;
    			}
    		}
    		return false;
    	}

    }
    
    private class LastSpace implements CheckByte {
    	private byte spaceByte = Conversion.getBytes(" ", fontname)[0];
    	boolean notSpace;
    	public void reset() {
    		notSpace = false;
    	}
    	public boolean forward() {
    		return false;
    	}
    
    	public boolean isAmatch(byte b) {
    		boolean ret = notSpace && b == spaceByte;
    		notSpace = b != spaceByte;
    		return ret;
    	}
    }
    
    private class CheckStartBinField extends BasicCheck implements CheckByte {
    	private byte[] tstBytes = Conversion.getBytes(" " + Common.STANDARD_CHARS1, fontname);
    	private boolean m1 = true, m2= true;
    	
       	public boolean isAmatch(byte b) {
    		boolean ret = b == 0 && m1 && m2;
    		m1 = m2;
    		m2 = search(tstBytes, b);
    		return ret;
    	}
    }
    
    private class Check4Bytes extends BasicCheck implements CheckByte {
   	private byte[] tstBytes;
    	public Check4Bytes(String tst) {
    		tstBytes = Conversion.getBytes(tst, fontname);
    	}
    	public Check4Bytes(byte tst) {
    		byte[] b = {tst};
    		tstBytes = b;
    	}

    	public boolean isAmatch(byte b) {
    		return search(tstBytes, b);
    	}
    }
    
    private class NormalByte extends BasicCheck implements CheckByte {
    	private byte tstByte;
    	public NormalByte() {
    		byte[] b = Conversion.getBytes(" ", fontname);
    		tstByte = b[0];
    	}
 
    	public boolean isAmatch(byte b) {
    		return tstByte >= b;
    	}
    }
    
    /**
     * Test for a Comp-3 number
     * 
     * @author bm
     *
     */
    private static class Comp3Byte extends BasicCheck implements CheckByte {
    	
    	public boolean isAmatch(byte b) {
    		int bb = b;
    		if (bb < 0) {
    			bb += 256;
    		}
    		int n1 = bb & 15;
    		int n2 = bb >> 4;
    	
//    		if (n1 < 10) {
//    			System.out.println("## " + b + " " + bb + " " + n2);
//    		}
        
    		return n1 < 10 && n2 < 10;
    	}

    }
    
    /**
     * Test for a Comp-3 sign byte
     * 
     * @author bm
     *
     */
    private static class Comp3SignByte extends BasicCheck implements CheckByte {
    	
    	public boolean isAmatch(byte b) {
    		int n1 = b & 15;
    		int n2 = b >> 4;
        
    		return (n1 == 15 || n1 ==13  || n1 ==12) && n2 < 10;
    	}
    }
}
