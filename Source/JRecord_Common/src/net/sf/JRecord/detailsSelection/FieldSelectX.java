package net.sf.JRecord.detailsSelection;

import java.math.BigDecimal;

import net.sf.JRecord.Common.AbstractIndexedLine;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.ExternalRecordSelection.ExternalFieldSelection;
import net.sf.JRecord.Types.TypeManager;

public abstract class FieldSelectX extends FieldSelect {
	public static final String STARTS_WITH  = Constants.STARTS_WITH;
	public static final String DOES_NOT_CONTAIN  = Constants.DOES_NOT_CONTAIN;
	public static final String CONTAINS= Constants.CONTAINS;
	public static final String NUM_EQ  = Constants.NUM_EQ;
	public static final String NUM_GT  = Constants.NUM_GT;
	public static final String NUM_GE  = Constants.NUM_GE;
	public static final String NUM_LT  = Constants.NUM_LT;
	public static final String NUM_LE  = Constants.NUM_LE;
	public static final String TEXT_EQ = Constants.TEXT_EQ;
	public static final String TEXT_GT = Constants.TEXT_GT;
	public static final String TEXT_GE = Constants.TEXT_GE;
	public static final String TEXT_LT = Constants.TEXT_LT;
	public static final String TEXT_LE = Constants.TEXT_LE;

	private boolean numeric;
	protected BigDecimal num;

	public FieldSelectX(String name, String value, String op, FieldDetail fieldDef) {
		this(	name, value, op,
				fieldDef != null && TypeManager.getInstance().getType(fieldDef.getType()).isNumeric(),
				fieldDef);
	}

	public FieldSelectX(String name, String value, String op, boolean isNumeric, FieldDetail fieldDef) {
		super(name, value, op, fieldDef);
		BigDecimal d = null;

		if (fieldDetail == null) {
			numeric = false;
		} else {
			numeric = isNumeric;

			if (numeric) {
				d = new BigDecimal(value);
			}
		}
		num = d;
	}
	public static FieldSelect get(ExternalFieldSelection fs, FieldDetail fieldDef) {
		return get(fs.getFieldName(), fs.getFieldValue(), fs.getOperator(), fieldDef);
	}

//	public static FieldSelect get(String name, String value, String op, FieldDetail fieldDef) {
//		FieldSelect ret;
//		if ("!=".equals(op) || "ne".equalsIgnoreCase(op)) {
//			ret = new FieldSelect.NotEqualsSelect(name, value, fieldDef);
//		} else if (">".equals(op) || "gt".equalsIgnoreCase(op)) {
//			ret = new FieldSelectX.GreaterThan(name, value, ">", fieldDef);
//		} else if (">=".equals(op) || "ge".equalsIgnoreCase(op)) {
//			ret = new FieldSelectX.GreaterThan(name, value,  ">=", fieldDef);
//		} else if ("<".equals(op) || "lt".equalsIgnoreCase(op)) {
//			ret = new FieldSelectX.LessThan(name, value, "<", fieldDef);
//		} else if ("<=".equals(op) || "le".equalsIgnoreCase(op)) {
//			ret = new FieldSelectX.LessThan(name, value, "<=", fieldDef);
//		} else {
//			ret = new FieldSelect.EqualsSelect(name, value, fieldDef);
//		}
//
//		return ret;
//	}

	public static FieldSelect get(String name, String value, String op, FieldDetail fieldDef) {
		FieldSelect ret ;
		if (op != null) {
			op = op.trim();
		}
		if ("!=".equals(op) || "<>".equals(op) ||"ne".equalsIgnoreCase(op)) {
			ret = new FieldSelectX.NotEqualsSelect(name, value, fieldDef);
		} else if (CONTAINS.equalsIgnoreCase(op)) {
			ret = new FieldSelect.Contains(name, value, fieldDef);
		} else if (DOES_NOT_CONTAIN.equalsIgnoreCase(op)) {
			ret = new FieldSelect.DoesntContain(name, value, fieldDef);
		} else if (STARTS_WITH.equalsIgnoreCase(op)) {
			ret = new FieldSelect.StartsWith(name, value, fieldDef);
		} else {
			ret = getBasic(name, value, op, fieldDef);
		}

		if (ret == null) {
			if (	   NUM_EQ.equalsIgnoreCase(op)	 || Constants.NUM_NE.equalsIgnoreCase(op)
					|| NUM_GT.equalsIgnoreCase(op)   || NUM_GE.equalsIgnoreCase(op)
					|| NUM_LT.equalsIgnoreCase(op)   || NUM_LE.equalsIgnoreCase(op)) {
				FieldSelectX ret1 = getBasic(name, value, op.substring(0, 2).trim(), fieldDef);
				ret1.setNumeric(true);
				ret = ret1;
			} else if (TEXT_EQ.equalsIgnoreCase(op)	 || Constants.TEXT_NE.equalsIgnoreCase(op)
					|| TEXT_GT.equalsIgnoreCase(op)	 || TEXT_GE.equalsIgnoreCase(op)
					|| TEXT_LT.equalsIgnoreCase(op)  || TEXT_LE.equalsIgnoreCase(op)) {
				FieldSelectX ret1 = getBasic(name, value, op.substring(0, 2).trim(), fieldDef);
				ret1.setNumeric(false);
				ret = ret1;
			} else {
				ret = new FieldSelectX.EqualsSelect(name, value, fieldDef);
			}
		}

		return ret;
	}

	public static FieldSelect getTrueSelection() {
		return new FieldSelect.TrueSelect();
	}

	private static FieldSelectX getBasic(String name, String value, String op, FieldDetail fieldDef) {
		FieldSelectX ret = null;
		if ("=".equals(op)) {
			ret = new FieldSelectX.EqualsSelect(name, value, fieldDef);
		} else if (">".equals(op) || "gt".equalsIgnoreCase(op)) {
			ret = new FieldSelectX.GreaterThan(name, value, ">", fieldDef);
		} else if (">=".equals(op) || "ge".equalsIgnoreCase(op)) {
			ret = new FieldSelectX.GreaterThan(name, value,  ">=", fieldDef);
		} else if ("<".equals(op) || "lt".equalsIgnoreCase(op)) {
			ret = new FieldSelectX.LessThan(name, value, "<", fieldDef);
		} else if ("<=".equals(op) || "le".equalsIgnoreCase(op)) {
			ret = new FieldSelectX.LessThan(name, value, "<=", fieldDef);
		} else if ("<>".equals(op)) {
			ret = new FieldSelectX.NotEqualsSelect(name, value, fieldDef);
		}

		return ret;
	}

	protected final int compare(AbstractIndexedLine line, int defaultVal) {
		int res = defaultVal;
		Object o = line.getField(fieldDetail);
		if (o == null) return Constants.NULL_INTEGER;
		if (numeric) {
			if (o instanceof BigDecimal) {
				res = ((BigDecimal) o).compareTo(num);
			} else {
				try {
					res = new BigDecimal(o.toString()).compareTo(num);
					//System.out.println(" -->> " + res + " " + o.toString() + " " + num);
				} catch (Exception e) {
				}
			}
		} else if (isCaseSensitive()){
			res = o.toString().compareTo(getFieldValue());
		} else {
			res = o.toString().toLowerCase().compareTo(getFieldValue());
		}
		return res;
	}


	/**
	 * @return the numeric
	 */
	public boolean isNumeric() {
		return numeric;
	}

	/**
	 * @param numeric the numeric to set
	 */
	public void setNumeric(boolean numeric) {

		if (numeric && num == null) {
			try {
				num = new BigDecimal(super.getFieldValue());
				this.numeric = numeric;
			} catch (Exception e) {
				// TODO: handle exception
			}
		} else {
			this.numeric = numeric;
		}
	}


	public static final class GreaterThan extends FieldSelectX {
		private int cmpTo = 0;
		private GreaterThan(String name, String value, String op, FieldDetail fieldDef) {
			super(name, value, op, fieldDef);

			if (">".equals(op)) {
				cmpTo = 1;
			}
		}

		/* (non-Javadoc)
		 * @see net.sf.JRecord.Details.Selection.RecordSelection#isSelected(net.sf.JRecord.Details.AbstractLine)
		 */
		@Override
		public boolean isSelected(AbstractIndexedLine line) {
			 return compare(line, -2) >= cmpTo;
		}
	}

	public static final class LessThan extends FieldSelectX {
		private int cmpTo = 0;
		private LessThan(String name, String value, String op, FieldDetail fieldDef) {
			super(name, value, op, fieldDef);

			if ("<".equals(op)) {
				cmpTo = -1;
			}
		}

		/* (non-Javadoc)
		 * @see net.sf.JRecord.Details.Selection.RecordSelection#isSelected(net.sf.JRecord.Details.AbstractLine)
		 */
		@Override
		public boolean isSelected(AbstractIndexedLine line) {
			int cmp = compare(line, 2);
			return  cmp <= cmpTo && cmp != Constants.NULL_INTEGER;
		}
	}


	public static class EqualsSelect extends FieldSelectX {

		protected EqualsSelect(String name, String value, FieldDetail fieldDef) {
			super(name, value, "=", fieldDef);
		}



		/* (non-Javadoc)
		 * @see net.sf.JRecord.Details.Selection.RecordSelection#isSelected(net.sf.JRecord.Details.AbstractLine)
		 */
		@Override
		public boolean isSelected(AbstractIndexedLine line) {
			return compare(line, 2) == 0;
		}
	}

	public static class NotEqualsSelect extends FieldSelectX {

		protected NotEqualsSelect(String name, String value, FieldDetail fieldDef) {
			super(name, value, "!=", fieldDef);
		}



		/* (non-Javadoc)
		 * @see net.sf.JRecord.Details.Selection.RecordSelection#isSelected(net.sf.JRecord.Details.AbstractLine)
		 */
		@Override
		public boolean isSelected(AbstractIndexedLine line) {

			return compare(line, 0) != 0;		}
	}

}

