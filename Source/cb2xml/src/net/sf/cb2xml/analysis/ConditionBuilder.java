package net.sf.cb2xml.analysis;

public class ConditionBuilder {

	 private String name;
	 private String through;
	 private String value;
	 
	/**
	 * @param name the name to set
	 */
	public ConditionBuilder setName(String name) {
		this.name = name;
		return this;
	}
	
	/**
	 * @param through the through to set
	 */
	public ConditionBuilder setThrough(String through) {
		this.through = through;
		return this;
	}
	
	/**
	 * @param value the value to set
	 */
	public ConditionBuilder setValue(String value) {
		this.value = value;
		return this;
	}

	
}
