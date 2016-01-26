package net.sf.RecordEditor.re.file;

import java.math.BigDecimal;
import java.util.List;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.re.file.filter.Compare;
import net.sf.RecordEditor.utils.common.Common;

public class FileSearch {

	final String searchFor;
	final FilePosition pos;
	final boolean ignoreCase;
	final int operator;
	final boolean eofCheck;
	final IFileAccess fileDtls;
	final PositionIncrement inc;
	
	public static FileSearch newFileSearch(
			IFileAccess fileDtls,
			String searchFor,
			FilePosition pos,
			boolean ignoreCase,
			int operator,
			boolean eofCheck) {
		return new FileSearch(fileDtls, searchFor, pos, ignoreCase, operator, eofCheck);
	}
	
	private FileSearch(IFileAccess fileDtls,
			String searchFor,
			FilePosition pos,
			boolean ignoreCase,
			int operator,
			boolean eofCheck) {
		
		this.fileDtls = fileDtls;
		this.searchFor = searchFor;
		this.pos = pos;
		this.ignoreCase = ignoreCase;
		this.operator = operator;
		this.eofCheck = eofCheck;
		
		inc = PositionIncrement.newIncrement(pos, fileDtls.getLines(), eofCheck);
	}
	
	public void doEofSearch() {
		inc.setEndOfFileChk(true);
		doSearch();
	}
	
	public void doSearch() {
		String icSearchFor = searchFor;
		boolean anyWhereInTheField = (operator == Compare.OP_CONTAINS)
								  || (operator == Compare.OP_DOESNT_CONTAIN);

		if (ignoreCase) {
			icSearchFor = searchFor.toUpperCase();
		}
		pos.found = false;

	    if (anyWhereInTheField) {
	        while (inc.isValidPosition()
	                && ! contains(ignoreCase, inc.pos, icSearchFor, (operator == Compare.OP_CONTAINS))) {
	            inc.nextPosition();
	        }
	    } else {
			BigDecimal testNumber = Compare.getNumericValue(operator, searchFor);
	        while (inc.isValidPosition()
	                && ! cmp(ignoreCase, inc.pos, icSearchFor, testNumber, operator)) {
	            inc.nextPosition();
	        }
	    }

	}
	
	/**
	 * Compare the test value with a field
	 * @param ignoreCase wether to ignore the case of the string
	 * @param pos file position
	 * @param testValue value the field against
	 * @param testNumber value as a bigDecimal
	 * @param operator operator to use in the comparison
	 * @return
	 */
	private boolean cmp(
			boolean ignoreCase,
			FilePosition pos,
			String testValue,
			BigDecimal testNumber,
			int operator) {
		int col = fileDtls.getRealColumn(pos.recordId, pos.currentFieldNumber);
		String s = getFieldValue(pos, col);
		boolean normalSearch = false;

		//System.out.println("==> " + pos.currentFieldNumber + " " + pos.row + " >" + s + "< " + testValue + " ~~ " + operator);

		if (pos.col > 0 && pos.col != FilePosition.END_OF_COLUMN) {
			return false;
		}

		switch (operator){
			case (Compare.OP_EQUALS): normalSearch = true;
			case (Compare.OP_NOT_EQUALS):
				if (ignoreCase) {
					pos.found = s.equalsIgnoreCase(testValue) == normalSearch;
				} else {
					pos.found = s.equals(testValue) == normalSearch;
				}
			break;
			case (Compare.OP_STARTS_WITH):
				pos.found = s != null && s.startsWith(testValue);
				break;
			case (Compare.OP_NUMERIC_EQ):
			case (Compare.OP_NUMERIC_GE):
			case (Compare.OP_NUMERIC_GT):
			case (Compare.OP_NUMERIC_LE):
			case (Compare.OP_NUMERIC_LT):
				pos.found = cmpToNum(s, testNumber,
						Compare.OPERATOR_COMPARATOR_VALUES[operator - Compare.OP_NUMERIC_GT]);
			break;
			case (Compare.OP_TEXT_GE):
			case (Compare.OP_TEXT_GT):
			case (Compare.OP_TEXT_LE):
			case (Compare.OP_TEXT_LT):
				int[] vals = Compare.OPERATOR_COMPARATOR_VALUES[operator - Compare.OP_TEXT_GT];
				int v;
				if (ignoreCase) {
					v = s.compareToIgnoreCase(testValue);
				} else {
					v = s.compareTo(testValue);
				}
				if (v > 0) {
					v = 1;
				} else if (v < 0) {
					v = -1;
				}
				//System.out.println("Compare Details -> " + s + " < >" + testValue +  "<   -->" + v +" ~ " + vals[0] + " ~ " + vals[1]);
				pos.found = v == vals[0] || v == vals[1];
			break;
		}

		if (pos.found) {
			pos.col = 0;
		}
		return pos.found;
	}

	private boolean cmpToNum(String s, BigDecimal testValue, int[] vals) {
		int v = Compare.cNULL;
		BigDecimal fldValue = null;

		try {
			fldValue = new BigDecimal(s);
		} catch (Exception e) { }

		if (fldValue == null && testValue == null) {
			v = 0;
		} else if (fldValue == null) {
			return false;
		} else if (testValue == null) {
			v = -1;
		} else {
			v = fldValue.compareTo(testValue);
		}

//		System.out.println("Compare Details -> " + testValue + " < >" + fldValue + "< >" + s + "<   -->" + v
//				+" ~ " + vals[0] + " ~ " + vals[1]);

		return v == vals[0] || v == vals[1];
	}



	/**
	 *
	 * @param ignoreCase  wether to ignore Character case in the compare
	 * @param pos position in the file
	 * @param testValue Value to test the field against
	 * @param normalSearch wether to use equals or not equals
	 *
	 * @return wether the field contains testValue
	 */
	private boolean contains(
		boolean ignoreCase,
		FilePosition pos,
//		boolean forward,
		String testValue,
		boolean normalSearch) {

	    //System.out.println("~~ " + pos.currentFieldNumber + " ");
	    int col = fileDtls.getRealColumn(pos.recordId, pos.currentFieldNumber);
		String s = getFieldValue(pos, col);

		int fromIndex = Integer.MAX_VALUE;

		System.out.println("==> " + pos.currentFieldNumber + " " + pos.row + " >" + s + "< " + testValue);
		if (pos.isForward()) {
		    fromIndex = 0;
		}

		if (pos.col > 0 && pos.col != FilePosition.END_OF_COLUMN) {
	        fromIndex = pos.col;
		}

		if (pos.isForward()) {
		    if (ignoreCase) {
		        pos.col = s.toUpperCase().indexOf(testValue.toUpperCase(), fromIndex);
		    } else {
		        pos.col = s.indexOf(testValue, fromIndex);
		    }
		} else {
		    if (ignoreCase) {
		        pos.col = s.toUpperCase().lastIndexOf(testValue.toUpperCase(), fromIndex);
		    } else {
		        pos.col = s.lastIndexOf(testValue, fromIndex);
		    }
		}
		pos.found = (pos.col >= 0) == normalSearch;

//		if (pos.found) {
//			System.out.println("Contains " + pos.found + " row: " + pos.row
//		        + " " + pos.recordId + " " + pos.currentFieldNumber
//		        + " >" + s + "< " + testValue);
//		}
		return pos.found;
	}

	
	private String getFieldValue(FilePosition pos, int col) {
		AbstractLine l = pos.currentLine;
		String s= "";
		List<AbstractLine> lines = fileDtls.getLines();
		if (l == null && pos.row >= 0 && pos.row < lines.size()) {
			 l = fileDtls.getTempLine(pos.row);
		}

		pos.layoutIdxUsed = Common.NULL_INTEGER;
		if (l != null) {
			try {
				if (pos.getFieldId() == Common.FULL_LINE) {
					s = toStr(l.getFullLine());
				} else if (pos.currentLine != null && pos.getFieldId() == Common.ALL_FIELDS) {
					pos.layoutIdxUsed = l.getPreferredLayoutIdx();
					s = toStr(l.getField(l.getPreferredLayoutIdx(), col));
					//System.out.println("All Fields, layout: " + l.getPreferredLayoutIdx() + " Col: " + col + " > " + s);
				} else{
					pos.layoutIdxUsed = pos.recordId;
					s = toStr(l.getField(pos.recordId, col));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return s;
	}
	
	private String toStr(Object o) {
	    String s = "";
	    if (o != null) {
	        s = o.toString();
	    }
	    return s;
	}

}
