package net.sf.RecordEditor.re.script.bld;

import java.util.ArrayList;
import java.util.List;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.JRecord.Details.AbstractRecordDetail.FieldDetails;
import net.sf.JRecord.Types.TypeManager;

/**
 * This class describes a schema for use in building scripts (Macros).
 * 
 * @author Bruce Martin
 *
 */
public class ScriptSchemaDtls {

	public final String schemaName;
	public final int dataSource;
	public final List<ScriptRecordDtls> records = new ArrayList<ScriptRecordDtls>();
	

	public ScriptSchemaDtls(AbstractLayoutDetails l, int dataSrc, Boolean[] incRecords, Boolean[][][] fields) {
		super();
		this.schemaName = l.getLayoutName();
		this.dataSource = dataSrc;
		
		ScriptRecordDtls rec;
		AbstractRecordDetail schemaRecord;
		
		for (int i = 0; i < l.getRecordCount(); i++) {
			schemaRecord = l.getRecord(i);
			rec = new ScriptRecordDtls(schemaRecord.getRecordName(), i, incRecords[i]);
			for (int j = 0; j < schemaRecord.getFieldCount(); j++) {
				FieldDetails schemaField = schemaRecord.getField(j); 
				int type = schemaField.getType();
				boolean numeric = TypeManager.isNumeric(type);
				boolean hasDecimal = numeric && (schemaField.getDecimal() > 0 || TypeManager.hasFloatingDecimal(type));

				rec.fields.add(new ScriptFieldDtls(schemaField.getName(), schemaField.getLookupName(), j, numeric, hasDecimal, fields[i][j][0], fields[i][j][1]));
			}

			this.records.add(rec); 
		}
	}


	/**
	 * @return the schemaName
	 */
	public final String getSchemaName() {
		return schemaName;
	}
	
	
	/**
	 * @return the dataSource
	 */
	public final int getDataSource() {
		return dataSource;
	}


	/**
	 * @return the records
	 */
	public final List<ScriptRecordDtls> getRecords() {
		return records;
	}

	
}
