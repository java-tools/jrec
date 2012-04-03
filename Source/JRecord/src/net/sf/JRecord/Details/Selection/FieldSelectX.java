package net.sf.JRecord.Details.Selection;

import java.math.BigDecimal;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.ExternalRecordSelection.ExternalFieldSelection;
import net.sf.JRecord.Types.TypeManager;

public abstract class FieldSelectX extends FieldSelect {
	protected final boolean numeric;
	protected final BigDecimal num;
	
	public FieldSelectX(String name, String value, String op, FieldDetail fieldDef) {
		super(name, value, op, fieldDef);
		BigDecimal d = null;
		
		if (fieldDetail == null) {
			numeric = false;
		} else {
			numeric = TypeManager.getInstance().getType(fieldDetail.getType()).isNumeric();
			
			if (numeric) {
				d = new BigDecimal(value);
			}
		}
		num = d;
	}
	
	public static FieldSelect get(ExternalFieldSelection fs, FieldDetail fieldDef) {
		return get(fs.getFieldName(), fs.getFieldValue(), fs.getOperator(), fieldDef);
	}

	public static FieldSelect get(String name, String value, String op, FieldDetail fieldDef) {
		FieldSelect ret;
		if ("!=".equals(op) || "ne".equalsIgnoreCase(op)) {
			ret = new FieldSelect.NotEqualsSelect(name, value, fieldDef);
		} else if (">".equals(op) || "gt".equalsIgnoreCase(op)) {
			ret = new FieldSelectX.GreaterThan(name, value, ">", fieldDef);
		} else if (">=".equals(op) || "ge".equalsIgnoreCase(op)) {
			ret = new FieldSelectX.GreaterThan(name, value,  ">=", fieldDef);
		} else if ("<".equals(op) || "lt".equalsIgnoreCase(op)) {
			ret = new FieldSelectX.LessThan(name, value, "<", fieldDef);
		} else if ("<=".equals(op) || "le".equalsIgnoreCase(op)) {
			ret = new FieldSelectX.LessThan(name, value, "<=", fieldDef);
		} else {
			ret = new FieldSelect.EqualsSelect(name, value, fieldDef);
		}
		
		return ret;
	}
	
	protected final int compare(@SuppressWarnings("rawtypes") AbstractLine line) {
		int res;
		Object o = line.getField(fieldDetail);
		if (o == null) return Constants.NULL_INTEGER;
		if (numeric) {	
			if (o instanceof BigDecimal) {
				res = ((BigDecimal) o).compareTo(num);
			} else {
				res = new BigDecimal(o.toString()).compareTo(num);
			}
	 
		} else {
			res = o.toString().compareTo(getFieldValue());
		}
		return res;
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
		@SuppressWarnings("rawtypes")
		@Override
		public boolean isSelected(AbstractLine line) {
			 return compare(line) >= cmpTo;
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
		@SuppressWarnings("rawtypes")
		@Override
		public boolean isSelected(AbstractLine line) {
			int cmp = compare(line);
			return  cmp <= cmpTo && cmp != Constants.NULL_INTEGER;
		}
	}

}

