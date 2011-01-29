package net.sf.RecordEditor.jibx;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;

public class JibxCall<xmlClass> {
	
//	@SuppressWarnings("unchecked")
//	private static HashMap<Class, Class> map 
//			= new HashMap<Class, Class>();
//	
//	static {
//		map.put(net.sf.RecordEditor.jibx.base.EditorTask.class, 
//				net.sf.RecordEditor.jibx.compare.EditorTask.class);
//		map.put(net.sf.RecordEditor.jibx.compare.CopyDefinition.class, 
//				net.sf.RecordEditor.jibx.compare.CopyDefinition.class);
//		map.put(net.sf.RecordEditor.jibx.compare.DiffDefinition.class, 
//				net.sf.RecordEditor.jibx.compare.DiffDefinition.class);
//	}
	
	private Class<xmlClass> requireClass;
	
	
	
	@SuppressWarnings("unchecked")
	public JibxCall(Class requiredClass) {
		requireClass = requiredClass;
//		if (map.containsKey(requiredClass)) {
//			requireClass = map.get(requiredClass);
//		}
	}

	@SuppressWarnings("unchecked")
	public xmlClass marshal(final String filename)
	throws Exception {
	//	throws org.jibx.runtime.JiBXException, FileNotFoundException {
       
		try {
	        FileInputStream in = new FileInputStream(filename);
	        return (xmlClass) org.jibx.runtime.BindingDirectory.getFactory(requireClass).createUnmarshallingContext()
	        						.unmarshalDocument(in, null);
		} catch (NoClassDefFoundError e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void unmarshal(final String filename, xmlClass output) 
	throws Exception {

		try {
			org.jibx.runtime.IMarshallingContext mctx 
					= org.jibx.runtime.BindingDirectory.getFactory(requireClass).createMarshallingContext();
			mctx.setIndent(2);
			FileOutputStream out = new FileOutputStream(filename);
			mctx.marshalDocument(output, "UTF-8", null, out);
		} catch (NoClassDefFoundError e) {
			e.printStackTrace();
		}
	}
}
