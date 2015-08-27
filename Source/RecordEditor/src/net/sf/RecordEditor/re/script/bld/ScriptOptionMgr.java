package net.sf.RecordEditor.re.script.bld;


import java.io.IOException;
import java.util.TreeMap;

import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.RecordEditor.edit.util.StandardLayouts;
import net.sf.RecordEditor.utils.common.Common;

public class ScriptOptionMgr {

	private static ScriptOptionMgr instance = new ScriptOptionMgr();
	private static TreeMap<String, ScriptOption> options = new TreeMap<String, ScriptOption>();
	private static ScriptOption DEFAULT_OPTION = new ScriptOption("", "", "", "", true, true, true, false, false);
	
	
	static {
		AbstractLayoutDetails csvSchema = StandardLayouts.getInstance().getCsvLayoutNamesFirstLine("<tab>", null, "", false);
		@SuppressWarnings("rawtypes")
		AbstractLineReader r = LineIOProvider.getInstance().getLineReader(csvSchema);
		AbstractLine l;
		String name;
		
		try {
			r.open(Common.OPTIONS.velocityScriptDir.getSlashNoStar() + "scripts.csv", csvSchema);
			
			while ((l = r.read()) != null) {
				name = l.getFieldValue(0, 0).asString().toLowerCase();
//				System.out.println(name + " ~ " + l.getFullLine());
				options.put(
						name, 
						new ScriptOption(
								name, 
								l.getFieldValue(0, 6).asString(),
								l.getFieldValue(0, 7).asString(),
								l.getFieldValue(0, 8).asString(),
								true, 
								! "n".equalsIgnoreCase( l.getFieldValue(0, 2).asString()),
								! "n".equalsIgnoreCase( l.getFieldValue(0, 3).asString()),
								  "y".equalsIgnoreCase( l.getFieldValue(0, 4).asString()),
								  "y".equalsIgnoreCase( l.getFieldValue(0, 5).asString())
						));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RecordException e) {
			e.printStackTrace();
		} finally {
			try {
				r.close();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
	}
	
	
	public ScriptOption get(String name) {
		ScriptOption ret = DEFAULT_OPTION;
		String key;
		
		if (name != null && options.containsKey((key = name.toLowerCase()))) {
			ret = options.get(key);
		}
		return ret;
	}
	
	/**
	 * @return the instance
	 */
	public static final ScriptOptionMgr getInstance() {
		return instance;
	}
}
