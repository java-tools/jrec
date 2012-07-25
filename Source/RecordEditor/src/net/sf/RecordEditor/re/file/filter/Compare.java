package net.sf.RecordEditor.re.file.filter;

import java.math.BigDecimal;

import net.sf.JRecord.Common.Constants;
import net.sf.RecordEditor.utils.lang.LangConversion;

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

	   public static final String[] OPERATOR_STRING_FOREIGN_VALUES = convertForeign(OPERATOR_STRING_VALUES);

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
	    	return getOperator(op, OP_CONTAINS, OPERATOR_STRING_VALUES);
	    }

	    public static int getForeignOperator(String op, int ret) {
	    	return getOperator(op, ret, OPERATOR_STRING_FOREIGN_VALUES);
	    }

	    public static int getOperator(String op, int ret, String[] operatorValues) {

	    	for (int i = 0; i < operatorValues.length; i++) {
	    		if (operatorValues[i].equalsIgnoreCase(op)) {
	    			System.out.println(" ~~ >> found " + op + " " + i);
	    			ret = i;
	    			break;
	    		}
	    	}
			System.out.println(" ~~ >> not found " + op + " " + ret);
	    	return ret;
	    }

	    public static String[] getSearchOptionsForeign() {
	    	return convertForeign(OPERATOR_SEARCH_OPTIONS);
	    }

	    public static String[] convertForeign(String[] a) {
	    	String[] b = new String[a.length];
	    	for (int i = 0; i < a.length; i++) {
	    		b[i] = a[i];
	    		if (a[i].trim().length() > 2) {
	    			b[i] = LangConversion.convert(LangConversion.ST_COMBO, a[i]);
	    		}
	    	}

	    	return b;
	    }
}
