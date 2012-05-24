package net.sf.RecordEditor.re.file.filter;

import java.math.BigDecimal;

import net.sf.JRecord.Common.Constants;

public final class Compare {

	   //public static final int NULL_FIELD      = -121;
	   public static final int OP_CONTAINS     = 0;
	   public static final int OP_EQUALS       = 1;
	   public static final int OP_DOESNT_CONTAIN = 2;
	   public static final int OP_NOT_EQUALS   = 3;
	   public static final int OP_STARTS_WITH  = 4;
	   public static final int OP_NUMERIC_GT   = 5;
	   public static final int OP_NUMERIC_GE   = 6;
	   public static final int OP_NUMERIC_LT   = 7;
	   public static final int OP_NUMERIC_LE   = 8;
	   public static final int OP_NUMERIC_EQ   = 9;
	   public static final int OP_TEXT_GT      = 10;
	   public static final int OP_TEXT_GE      = 11;
	   public static final int OP_TEXT_LT      = 12;
	   public static final int OP_TEXT_LE      = 13;
	   public static final int OP_LAST_OPERATOR = OP_TEXT_LE;

	   public static final String[] OPERATOR_SEARCH_OPTIONS = {
		    Constants.CONTAINS, " = ", Constants.DOES_NOT_CONTAIN, " <> ",
	   		Constants.STARTS_WITH,
		    ">", ">=", "<", "<= ",
		    Constants.NUM_EQ,
		    Constants.TEXT_GT, 	Constants.TEXT_GE,
		    Constants.TEXT_LT, 	Constants.TEXT_LE
	   };

	   public static final String[] OPERATOR_STRING_VALUES = {
		   		Constants.CONTAINS, " = ", Constants.DOES_NOT_CONTAIN, " <> ",
		   		Constants.STARTS_WITH,
			    ">", ">=", "<", "<= ",
			    Constants.NUM_EQ, 	Constants.NUM_GT, 	Constants.NUM_GE,
			    Constants.NUM_LT, 	Constants.NUM_LE,
			    Constants.TEXT_EQ, 	Constants.TEXT_GT, 	Constants.TEXT_GE,
			    Constants.TEXT_LT, 	Constants.TEXT_LE
	   };
//	   public static final int[]    OPERATOR_VALUES     = {OP_CONTAINS, OP_EQUALS, OP_DOESNT_CONTAIN, OP_NOT_EQUALS,
//			   OP_NUMERIC_GT , OP_NUMERIC_GE , OP_NUMERIC_LT , OP_NUMERIC_LE , OP_NUMERIC_EQ ,
//			   OP_TEXT_GT    , OP_TEXT_GE    , OP_TEXT_LT    , OP_TEXT_LE    };

	//   protected static final String[] OPERATOR_STRING_VALUES_BASIC = {OPERATOR_STRING_VALUES[0], OPERATOR_STRING_VALUES[1],
	//		   OPERATOR_STRING_VALUES[2], OPERATOR_STRING_VALUES[3]
	//   };

	   protected static final int cGT = 1;
	   protected static final int cEQ = 0;
	   protected static final int cLT = -1;
	   public static final int cNULL = -121;

	   public static final int[][] OPERATOR_COMPARATOR_VALUES = {
	    	{cGT, cNULL}, {cGT, cEQ}, {cLT, cNULL}, {cLT, cEQ}, {cEQ, cNULL}
	    };

	    private static final boolean isNumericCompare(int operator) {
	    	return (operator >= OP_NUMERIC_GT) && (operator <= OP_NUMERIC_EQ) ;
	    }

	    /**
	     * Convert a value to a numeric big decimal if necesary
	     *
	     * @param op operator
	     * @param val value
	     *
	     * @return numeric value
	     */
	    public static final BigDecimal getNumericValue(int op, String val) {
	    	BigDecimal testNumber = null;

	    	if (isNumericCompare(op)) {
	    		try {
					testNumber = new BigDecimal(val);
				} catch (Exception e) {	}
	    	}

			return testNumber;
	    }

	    /**
	     * Convert operator to String
	     * @param op operator
	     * @return String equivalent
	     */
	    public static String getOperatorAsString(int op) {
	    	return OPERATOR_STRING_VALUES[op];
	    }

	    public static int getOperator(String op) {
	    	int ret = -1;

	    	for (int i = 0; i < OPERATOR_STRING_VALUES.length; i++) {
	    		if (OPERATOR_STRING_VALUES[i].equalsIgnoreCase(op)) {
	    			ret = i;
	    			break;
	    		}
	    	}
	    	return ret;
	    }
}
