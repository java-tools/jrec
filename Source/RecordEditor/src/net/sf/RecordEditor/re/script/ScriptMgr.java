package net.sf.RecordEditor.re.script;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import net.sf.JRecord.Common.RecordRunTimeException;
import net.sf.RecordEditor.utils.params.Parameters;

public class ScriptMgr implements ValidExtensionCheck {

	private  final static  List<String> languages = new ArrayList<String>();


	private ScriptEngineManager scriptManager = new ScriptEngineManager(this.getClass().getClassLoader());

    public final void runScript(
    		String script,
    		ScriptData data) throws FileNotFoundException, ScriptException  {

    	String ext = Parameters.getExtensionOnly(script);
    	ScriptEngine eng = scriptManager.getEngineByExtension(ext);
    	if (eng == null) {
    		eng = scriptManager.getEngineByExtension("." + ext);
    	}
		runScript(eng, script, data);
    }

	/**
     * Generate a Velocity template with the supplied records
     * @param language script language
     * @param script script file to be run
     * @param data Data to pass to the Velocity template
 	 * @throws ScriptException
	 * @throws FileNotFoundException
     *
     * @throws Exception any error that occurs
     */
    public final void runScript(
    		String language,
    		String script,
    		ScriptData data) throws FileNotFoundException, ScriptException  {

    	runScript(scriptManager.getEngineByName(language), script, data);
    }

    private final void runScript(
    		ScriptEngine eng,
    		String script,
    		ScriptData data) throws FileNotFoundException, ScriptException  {


       	if (eng == null) {
    		throw new RecordRunTimeException("No Script Engine found !!! ");
    	} else {
    		if (data != null) {
		        if (data.view != null) {
		            eng.put("records",    data.selectedLines);
		            eng.put("file",       data.fileLines);
		            eng.put("view",       data.viewLines);
		            eng.put("layout",     data.view.getLayout());
		        }

		        eng.put("treeRoot",   data.root);
		        eng.put("treeNodes",  data.nodes);
		        eng.put("treeDepth",  data.treeDepth);
		        eng.put("fileName",   data.inputFile);
		        eng.put("outputFile", data.outputFile);
		        eng.put("onlyData",   Boolean.valueOf(data.onlyData));
		        eng.put("showBorder", Boolean.valueOf(data.showBorder));
		        eng.put("recordIdx",  Integer.valueOf(data.recordIdx));
		        eng.put("RecordEditorData", data);
    		}

	        eng.eval(new FileReader(script));
    	}
    }


	/**
	 * @param extension
	 * @return
	 * @see javax.script.ScriptEngineManager#getEngineByExtension(java.lang.String)
	 */
	public String getEngineNameByExtension(String extension) {
		String s = "";
		ScriptEngine eng = getEngineByExtension(extension);
		if (eng != null) {
			s = eng.getFactory().getLanguageName();
		}
		return s;
	}

	/**
	 * @param extension
	 * @return
	 * @see javax.script.ScriptEngineManager#getEngineByExtension(java.lang.String)
	 */
	public ScriptEngine getEngineByExtension(String extension) {
		return scriptManager.getEngineByExtension(extension);
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.script.ValidExtensionCheck#isValidExtension(java.lang.String)
	 */
	@Override
	public boolean isValidExtension(String extension) {

		return scriptManager.getEngineByExtension(extension) != null;
	}


	/**
	 * @return the languages
	 */
	public static List<String> getLanguages() {

		synchronized (languages) {
			if (languages.size() == 0) {
				ScriptEngineManager manager = new ScriptEngineManager(ScriptMgr.class.getClassLoader());
				List<ScriptEngineFactory> engines = manager.getEngineFactories();

				for (ScriptEngineFactory engine : engines) {
					languages.add(engine.getLanguageName());
				}
			}
			return languages;
		}
	}

}
