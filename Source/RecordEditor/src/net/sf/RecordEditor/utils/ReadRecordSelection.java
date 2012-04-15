package net.sf.RecordEditor.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.detailsSelection.AbsGroup;
import net.sf.JRecord.detailsSelection.AndSelection;
import net.sf.JRecord.detailsSelection.FieldSelect;
import net.sf.JRecord.detailsSelection.FieldSelectX;
import net.sf.JRecord.detailsSelection.OrSelection;
import net.sf.JRecord.detailsSelection.RecordSel;
import net.sf.JRecord.detailsSelection.Streamline;
import net.sf.RecordEditor.utils.common.AbsConnection;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReConnection;

public class ReadRecordSelection {
	private static ReadRecordSelection instance = new ReadRecordSelection();

	/**
	 * Read the Record selection test in from the Database
	 * 
	 * @param dbIdx Database Connection Index
	 * @param parentRecId parent Record's Identifier (part of the DB key)
	 * @param childKey Child Key (part of the DB key)
	 * @param fields Field Definitions
	 * @param tstFieldName Test Field name
	 * @param tstFieldValue Test Values
	 * @return Record selection test
	 */
	public final RecordSel getRecordSelection(
			int dbIdx, int parentRecId, int childKey, 
			FieldDetail[] fields,
			String tstFieldName, String tstFieldValue) {
		return getRecordSelection(
				new ReConnection(dbIdx), parentRecId, childKey, 
				fields, tstFieldName, tstFieldValue);
	}
	
	
	/**
	 * Read the Record selection test in from the Database
	 * 
	 * @param connect Database Connection
	 * @param parentRecId parent Record's Identifier (part of the DB key)
	 * @param childKey Child Key (part of the DB key)
	 * @param fields Field Definitions
	 * @param tstFieldName Test Field name
	 * @param tstFieldValue Test Values
	 * @return Record selection test
	 */
	public final RecordSel getRecordSelection(
			AbsConnection connect, int parentRecId, int childKey, 
			FieldDetail[] fields,
			String tstFieldName, String tstFieldValue) {

		OrSelection orSel = new OrSelection();
		AndSelection andSel = new AndSelection();
		AbsGroup retGroup = andSel;
		RecordSel ret = null;
		String sql = "Select Boolean_Operator, Field_Name, Operator, Field_Value"
				   + "  from Tbl_RFS_FieldSelection "
				   + " where RecordId = " + parentRecId
				   + "   and Child_Key= " + childKey
				   + " order by Field_No";
		
		try {
		
			ResultSet resultset = connect.getConnection().createStatement().executeQuery(sql);
			
			if (resultset.next()) {
				andSel.add(getFieldDef(resultset.getString(2), resultset.getString(3),  resultset.getString(4), fields));
			}
			while (resultset.next()) {
				if (resultset.getInt(1) == Common.BOOLEAN_OPERATOR_OR) {
					orSel.add(andSel);
					andSel = new AndSelection();
				}
				andSel.add(getFieldDef(resultset.getString(2), resultset.getString(3),  resultset.getString(4), fields));
			}
			
			ret = andSel;
			if (orSel.getSize() > 0) {
				orSel.add(andSel);
				ret = orSel;
				retGroup = orSel;
			}
			ret = Streamline.getInstance().streamLine(ret);
			
			resultset.close();
		} catch (SQLException e) {
			System.out.println(sql);
			e.printStackTrace();
		}
		
		if (tstFieldName != null && ! "".equals(tstFieldName)) {
			FieldSelect sel = getFieldDef(tstFieldName, "=", tstFieldValue, fields);
			
			if (retGroup == null || retGroup.getSize() == 0) {
				ret = sel;
			} else {
				AndSelection retSel = new AndSelection();
				retSel.add(sel);
				retSel.add(ret);
				ret = retSel;
			}
		}
		
		return ret;
	}
	
	private FieldSelect getFieldDef(String fieldName, String operator, String fieldValue, FieldDetail[] fields) {
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
	
	/**
	 * @return the instance
	 */
	public static ReadRecordSelection getInstance() {
		return instance;
	}
	
}
