package net.sf.RecordEditor.examples;

import java.util.List;

import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

public class ZScriptTest {

	
	public static void main(String[] parms) {
		ScriptEngineManager m = new ScriptEngineManager();
		List<ScriptEngineFactory> f = m.getEngineFactories();
		
		System.out.println(f.isEmpty());
	}
}
