package net.sf.RecordEditor.edit.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JInternalFrame;

import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.IO.AbstractLineIOProvider;
import net.sf.RecordEditor.edit.open.StartEditor;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.openFile.RecentFiles;
import net.sf.RecordEditor.re.util.csv.FilePreview;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.screenManager.ReFrame;


/**
 * Class to create new File using a schema
 * 
 * @author Bruce Martin
 *
 */
public class NewFileSchemaControl implements ActionListener {

	public static void newFrame(String recentFileName, AbstractLineIOProvider pIoProvider) {
		new NewFileSchemaControl(new NewFileSchemaView(), recentFileName, pIoProvider);
	}
	public final ReFrame frame;

	private final NewFileSchemaView fileSchemaView;
	private final AbstractLineIOProvider ioProvider;
	private final String recentFileName;
	

	public NewFileSchemaControl(
			NewFileSchemaView fileSchemaView,
			String recentFileName, 
			AbstractLineIOProvider pIoProvider) {
		super();
		this.fileSchemaView = fileSchemaView;
		this.recentFileName = recentFileName;
		this.ioProvider = pIoProvider;
		this.frame = new ReFrame("","New File (defined using file schema)", null);
		
		frame.addMainComponent(fileSchemaView.pnl);
		frame.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);

		fileSchemaView.createBtn.addActionListener(this);
		
		frame.setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		String newFileName = fileSchemaView.newFileCombo.getText();
		LayoutDetail schema = fileSchemaView.getLayout();
		
		if (schema != null) {
			File f = new File(newFileName);
			
			if (f.isDirectory()) {
				fileSchemaView.msgTxt.setText("Output must be a file, not a directory");
			} else if (! f.canWrite()) {
				fileSchemaView.msgTxt.setText("Invalid file: " + newFileName + " - can not write it");
			} else {
	            FileView view
	            	= new FileView(
	                        schema,
	                        ioProvider,
	                        false);
		        String path = f.getPath();
				StartEditor startEditor = new StartEditor(view, path, false, fileSchemaView.msgTxt, 0);
				String id = FilePreview.SCHEMA_ID+ FilePreview.SEP + fileSchemaView.layoutFileCombo.getText()
							+ FilePreview.SEP + fileSchemaView.dialectCombo.getSelectedIndex()
							+ FilePreview.SEP + fileSchemaView.fontCombo.getText()
							+ FilePreview.SEP + FilePreview.NULL_STR;
		
				new RecentFiles(recentFileName, null, true, Common.OPTIONS.DEFAULT_FILE_DIRECTORY.get())
						.putFileLayout(path, id);
		
		        startEditor.doRead();
		        startEditor.done();
	
				frame.setVisible(false);
			}
		}
		
	}
}
