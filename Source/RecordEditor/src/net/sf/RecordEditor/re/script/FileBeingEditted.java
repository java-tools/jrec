package net.sf.RecordEditor.re.script;

import java.util.ArrayList;
import java.util.List;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.screenManager.ReFrame;

/**
 * Holds details being edited. It is used in Scripts
 * 
 * <pre>
 *    ScriptData
 *        |
 *        +-------------- List<FileBeingEdited> 
 *        |                 (getFileList)
 *        |                       |
 *        |                       +------------------------ File Details + ScriptData
 *        |
 * </pre>
 * 
 * 
 * 
 * @author Bruce01
 *
 */
public class FileBeingEditted {

	public static List<FileBeingEditted> getList(FileView sourceView) {
		ReFrame[] allFrames = ReFrame.getAllFrames();
		int num = allFrames.length < 10 ? allFrames.length: allFrames.length * 3 / 4;
		ArrayList<FileBeingEditted> list = new ArrayList<FileBeingEditted>(num);
		
		for (ReFrame f : allFrames) {
			if (f.isPrimaryView() && f.getDocument() instanceof FileView) {
				FileView document = (FileView) f.getDocument();
				list.add(new FileBeingEditted(sourceView.getBaseFile() == document.getBaseFile(), f, document));
			}
		}
		
		return list;
	}
	
	public final boolean sameAsSource;
	public final ReFrame frame;
	public final FileView view;
	public final AbstractLayoutDetails layout;
	
	private ScriptData scriptData;
	
	protected FileBeingEditted(boolean sameAsSource, ReFrame frame, FileView fileView) {
		super();
		this.sameAsSource = sameAsSource;
		this.frame = frame;
		this.view = fileView;
		this.layout = fileView.getLayout();
	}

	/**
	 * @return the scriptData
	 */
	public final ScriptData getScriptData() {
		if (scriptData == null) {
			scriptData = new ScriptData(null, view, null, false, false, 0, view.getFileName() + ".xxx", frame, "");
		}
		return scriptData;
	}

	/**
	 * @param scriptData the scriptData to set
	 */
	public final void setScriptData(ScriptData scriptData) {
		this.scriptData = scriptData;
	}


}
