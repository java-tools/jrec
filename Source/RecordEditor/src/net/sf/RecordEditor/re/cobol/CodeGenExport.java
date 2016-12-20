package net.sf.RecordEditor.re.cobol;

import javax.swing.JTextField;

import net.sf.JRecord.Common.Conversion;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.swing.treeCombo.FileSelectCombo;

public class CodeGenExport {
	public final FileSelectCombo outputDirectory;
	public final JTextField outputExtensionTxt = new JTextField();

	CodeGenExport() {
	    outputDirectory = new FileSelectCombo(Parameters.CODE_GEN_OUTPUT_LIST, 25, true, false, true);
	    
	    assignDir(outputDirectory, Common.OPTIONS.DEFAULT_CODEGEN_EXPORT_DIRECTORY.get());
	    outputExtensionTxt.setText(Common.OPTIONS.DEFAULT_CODEGEN_DIRECTORY_EXTENSION.get());

	}
	
	public String getOutputDir(String language, String template, String schemaName) {
		StringBuilder b = fix4dir(new StringBuilder(outputDirectory.getText()));

		StringBuilder bx = Conversion.replace(outputExtensionTxt.getText(), "$language.", language);
		bx = Conversion.replace(bx, "$template.", template);
		bx = Conversion.replace(bx, "$schema.", schemaName);
		
		return fix4dir(b.append(bx)).toString();
	}

	/**
	 * @param b
	 */
	public StringBuilder fix4dir(StringBuilder b) {
		if (b.length() > 0) {
			int len = b.length() - 1;
			char lastChar = b.charAt(len);
			if (lastChar == '*') {
				b.setLength(len);
				if (len > 0) {
					lastChar = b.charAt(len-1);
				}
			}
			
			if (lastChar != '/' && lastChar != '\\') {
				b.append('/');
			}
		}
		return b;
	}
	
	static void assignDir(FileSelectCombo dir, String s) {
		if (s != null) {
	    	dir.setText(s);
	    }
	}

}
