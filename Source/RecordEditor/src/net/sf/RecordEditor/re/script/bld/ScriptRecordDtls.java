package net.sf.RecordEditor.re.script.bld;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class holds record details for us in generating Scripts (Macros).
 * 
 * @author Bruce Martin
 *
 */
public class ScriptRecordDtls {

	public final String recordName;
	public final int recordNumber;
	public final boolean included;
	
	public final List<ScriptFieldDtls> fields = new ArrayList<ScriptFieldDtls>();

	public ScriptRecordDtls(String recordName, int recordNumber,  boolean include) {
		super();
		this.recordName = recordName;
		this.recordNumber = recordNumber;
		this.included = include;
	}

	/**
	 * @return the recordName
	 */
	public final String getRecordName() {
		return recordName;
	}

	/**
	 * @return the recordNumber
	 */
	public final int getRecordNumber() {
		return recordNumber;
	}

	/**
	 * @return the include
	 */
	public final boolean isIncluded() {
		return included;
	}

	/**
	 * @return the fields
	 */
	public final List<ScriptFieldDtls> getFields() {
		return fields;
	}

	/**
	 * @return
	 * @see java.util.List#iterator()
	 */
	public Iterator<ScriptFieldDtls> fieldIterator() {
		return fields.iterator();
	}
	
	
}
