package net.sf.RecordEditor.re.script.bld;

public class ScriptOption {
	public final String name, selectName, updateName, description;
	public final boolean use, selectOn, update, selectOneRecord, onlyOneRecord;
	
	public ScriptOption(String name, String selectName, String updateName, String description,
			boolean use, boolean selectOn,
			boolean update, boolean selectOneRecord, boolean onlyOneRecord
			) {
		super();
		this.name = name;
		this.use = use;
		this.selectOn = selectOn;
		this.update = update;
		this.selectOneRecord = selectOneRecord;
		this.onlyOneRecord = onlyOneRecord;
		this.selectName = selectName;
		this.updateName = updateName;
		this.description = description;
	}
	
	
}
