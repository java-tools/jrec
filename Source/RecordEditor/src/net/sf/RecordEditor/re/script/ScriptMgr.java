package net.sf.RecordEditor.re.script;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import net.sf.JRecord.Common.RecordRunTimeException;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.params.Parameters;

public class ScriptMgr implements ValidExtensionCheck {

	private  final static  List<String> languages = new ArrayList<String>();
	private        static  Map<String, String> languageExtension = null;


	private ScriptEngineManager scriptManager = new ScriptEngineManager(this.getClass().getClassLoader());

    public final void runScript(
    		String script,
    		ScriptData data) throws FileNotFoundException, ScriptException, IOException  {

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

    public final void runScript(
    		String script,
     		ScriptData data,
    		String scriptText,
    		Writer writer
   ) throws FileNotFoundException, ScriptException, IOException  {

    	String ext = Parameters.getExtensionOnly(script);
    	ScriptEngine eng = scriptManager.getEngineByExtension(ext);
    	if (eng == null) {
    		eng = scriptManager.getEngineByExtension("." + ext);
    	}
		runScript(eng, script, new StringReader(scriptText), data, writer);
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
     		ScriptData data,
     		String scriptText,
    		Writer writer
    ) throws FileNotFoundException, ScriptException  {
    	runScript(scriptManager.getEngineByName(language), script, new StringReader(scriptText), data, writer);
    }

    private final void runScript(
    		ScriptEngine eng,
    		String script,
    		ScriptData data) throws FileNotFoundException, ScriptException  {
    	
    	StringWriter w = new StringWriter();
    	runScript(eng, script, new FileReader(script), data, w);
    	
    	if (w.getBuffer().length() > 0) {
    		Common.logMsg("Script: " + script + " output:\n", null);
    		Common.logMsg(w.toString(), null);
    	}
    }

    private final void runScript(
    		ScriptEngine eng,
    		String script,
    		Reader r,
    		ScriptData data,
    		Writer writer) throws FileNotFoundException, ScriptException  {


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

    		Writer w = eng.getContext().getWriter();
    		try {
    			if (writer == null) {
    				eng.eval(r);
      			} else {
    				eng.getContext().setWriter(writer);
    				eng.eval(r);
    			}
	   			eng.eval(r);
   		   } finally {
		        try {
					r.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		        eng.getContext().setWriter(w);
		        data.fireScreenChanged(false);
    		}
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

		return extension != null
			&& (  scriptManager.getEngineByExtension(extension) != null);
	}


	/**
	 * @return the languages
	 */
	public static List<String> getLanguages() {

		loadLanguages();
		return languages;
	}

	public static String getExtension(String lang) {
		
		loadLanguages();
		String ret = languageExtension.get(lang.toLowerCase());
//		System.out.println("--> " + lang.toLowerCase() + ", " + ret);
		if (ret == null) {
			ret = "";
		} else if (ret.length() > 0 && ! ret.startsWith(".")) {
			ret = "." + ret;
		}
		return ret;
	}

	private static void loadLanguages() {
		if (languageExtension == null) {
			synchronized (languages) {
				if (languageExtension == null) {
					ScriptEngineManager manager = new ScriptEngineManager(ScriptMgr.class.getClassLoader());
					List<ScriptEngineFactory> engines = manager.getEngineFactories();
	
					languageExtension = new HashMap<String, String>();
					languageExtension.put("javascript", "js");
					languageExtension.put("python", "py");
					languageExtension.put("jython", "py");
					languageExtension.put("jruby", "rb");
					languageExtension.put("ruby", "rb");
					for (ScriptEngineFactory engine : engines) {
						languages.add(engine.getLanguageName());
						List<String> extensions = engine.getExtensions();
						if (extensions != null && extensions.size() > 0) {
							languageExtension.put(engine.getLanguageName().toLowerCase(), extensions.get(0));
						}
					}
				}
			}
		}
	}

	

	/**
	 * @return the languages
	 */
	public static List<String[]> getLanguageExt() {

		List<String[]> ret = new ArrayList<String[]>();

		ScriptEngineManager manager = new ScriptEngineManager(ScriptMgr.class.getClassLoader());
		List<ScriptEngineFactory> engines = manager.getEngineFactories();

		for (ScriptEngineFactory engine : engines) {
			List<String> extensions = engine.getExtensions();
			for (String s : extensions) {
				String[] r = {s, engine.getLanguageName()};
				ret.add(r);
			}
		}

		return ret;

	}

}
