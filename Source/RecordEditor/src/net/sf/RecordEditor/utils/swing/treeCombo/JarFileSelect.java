package net.sf.RecordEditor.utils.swing.treeCombo;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.swing.filechooser.IFileChooserWrapper;
import net.sf.RecordEditor.utils.swing.filechooser.JRFileChooserWrapper;

/**
 * Class to select jar files
 * 
 * @author Bruce
 *
 */
@SuppressWarnings("serial")
public class JarFileSelect extends TreeComboFileSelect {

	private static File[] standardFiles = {
		new File(Parameters.expandVars("<lib>")),
	};
	private static final IFileChooserWrapper cw = JRFileChooserWrapper.newChooser(null, standardFiles) ;

	public JarFileSelect(boolean isOpen,  List<FileTreeComboItem> itms) {
		super(true, false, ((List<FileTreeComboItem>) null), cw, Arrays.asList(standardFiles));
		//super(isOpen, false, true, itms, null);
		
		JFileChooser fileChooser = super.getChooseHelper().getFileChooser();

		FileFilter filter = fileChooser.getFileFilter();
		
		fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Jar/Zip file", "jar", "zip"));
		fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Jar file", "jar"));

		fileChooser.addChoosableFileFilter(filter);
	}
//
//	public JarFileSelect(boolean isOpen, boolean isDirectory,
//			boolean canChangeList, List<FileTreeComboItem> itms,
//			JButton... btns) {
//		super(isOpen, isDirectory, canChangeList, itms, btns);
//		// TODO Auto-generated constructor stub
//	}

}
