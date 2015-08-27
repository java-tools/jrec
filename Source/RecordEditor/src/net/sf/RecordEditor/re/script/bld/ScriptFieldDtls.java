package net.sf.RecordEditor.re.script.bld;


/**
 * Class holds field details (and wether the fields are used for selection / update
 * 
 * @author Bruce Martin
 *
 */
public class ScriptFieldDtls {

	public final String name, lookupName;
	public final int fieldNumber;
	public final boolean selectOn, update, numeric, decimal;
	
	
	public ScriptFieldDtls(String name, String lookupName, int fieldNumber, 
			boolean numeric, 
			boolean hasDecimal,
			boolean selectOn,
			boolean update) {
		super();
		this.name = name;
		this.lookupName = lookupName;
		this.fieldNumber = fieldNumber;
		this.numeric = numeric;
		this.decimal = hasDecimal;
		this.selectOn = selectOn;
		this.update = update;
	}


	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}


	/**
	 * @return the lookupName
	 */
	public final String getLookupName() {
		return lookupName;
	}


	/**
	 * @return the fieldNumber
	 */
	public final int getFieldNumber() {
		return fieldNumber;
	}


	/**
	 * @return the selectOn
	 */
	public final boolean isSelectOn() {
		return selectOn;
	}


	/**
	 * @return the update
	 */
	public final boolean isUpdate() {
		return update;
	}


	/**
	 * @return the numeric
	 */
	public final boolean isNumeric() {
		return numeric;
	}


	/**
	 * @return the decimal
	 */
	public final boolean isDecimal() {
		return decimal;
	}
	
	
}
