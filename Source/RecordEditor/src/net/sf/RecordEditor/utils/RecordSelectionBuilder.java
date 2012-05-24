package net.sf.RecordEditor.utils;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.detailsSelection.AbsGroup;
import net.sf.JRecord.detailsSelection.AndSelection;
import net.sf.JRecord.detailsSelection.FieldSelect;
import net.sf.JRecord.detailsSelection.FieldSelectX;
import net.sf.JRecord.detailsSelection.OrSelection;
import net.sf.JRecord.detailsSelection.RecordSel;
import net.sf.JRecord.detailsSelection.Streamline;
import net.sf.RecordEditor.utils.common.Common;

public class RecordSelectionBuilder {
	private OrSelection orSel = new OrSelection();
	private AndSelection andSel = new AndSelection();
	private AbsGroup retGroup = andSel;
	private final FieldDetail[] fields;
	private boolean first = true;


	public RecordSelectionBuilder(FieldDetail[] fields) {
		super();
		this.fields = fields;
	}

	public final void add(int booleanOp, String fieldName, String operator, String value, boolean caseSensitive) {
		int boolOp = booleanOp;
		if (first) {
			boolOp = Common.BOOLEAN_OPERATOR_AND;
			first = false;
		} else if (boolOp == Common.BOOLEAN_OPERATOR_OR) {
			orSel.add(andSel);
			andSel = new AndSelection();
		}

		FieldSelect fs = getFieldDef(fieldName, operator,  value);
		fs.setCaseSensitive(caseSensitive);
		andSel.add(fs);
	}


	public final RecordSel build() {
		RecordSel ret= andSel;
		if (orSel.getSize() > 0) {
			orSel.add(andSel);
			ret = orSel;
			retGroup = orSel;
		}
		return Streamline.getInstance().streamLine(ret);
	}

	/**
	 * @return the retGroup
	 */
	public AbsGroup getGroup() {
		return retGroup;
	}

	private FieldSelect getFieldDef(String fieldName, String operator, String fieldValue) {
		FieldDetail fieldDef = null;
		if (fields != null && fieldName != null) {
			for (FieldDetail f : fields) {
				if (fieldName.equalsIgnoreCase(f.getName())) {
					fieldDef = f;
					break;
				}
			}
		}

		return FieldSelectX.get(fieldName, fieldValue, operator, fieldDef);
	}

}
