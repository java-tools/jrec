package net.sf.RecordEditor.edit.util;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.CobolCopybookLoader;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.External.CopybookLoaderFactory;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.RecordEditor.edit.open.OpenCommon;
import net.sf.RecordEditor.re.openFile.ComputerOptionCombo;
import net.sf.RecordEditor.re.openFile.RecentFiles;
import net.sf.RecordEditor.re.util.csv.SchemaAnalyser;
import net.sf.RecordEditor.utils.charsets.FontCombo;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.edit.ManagerRowList;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBox;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.treeCombo.FileSelectCombo;

public class NewFileSchemaView {
	
	public static final int TIP_HEIGHT  = SwingUtils.STANDARD_FONT_HEIGHT * 10 + 5;
	
	
	public final BaseHelpPanel pnl = new BaseHelpPanel("NewFileViaSchema");

	public final FileSelectCombo newFileCombo = new FileSelectCombo("NewFiles.", 25, true, false, true);
	public final FileSelectCombo layoutFileCombo = new FileSelectCombo(Parameters.SCHEMA_LIST, 25, true, false);
	public final JLabel dialectLbl = new JLabel("Cobol Dialect");
	public final JLabel fontLbl = new JLabel("Font");
	public final JLabel structureLbl  = new JLabel("File Struicture");
	public final BmKeyedComboBox structureCombo = new BmKeyedComboBox(
    		new ManagerRowList(LineIOProvider.getInstance(), false),
    		false
    );
	public final ComputerOptionCombo dialectCombo = new ComputerOptionCombo();
	public final FontCombo fontCombo = new FontCombo();
	public final JTextArea msgTxt = new JTextArea();

	public JButton createBtn = SwingUtils.newButton("Create");
	
	public NewFileSchemaView() {
		String formDescription = LangConversion.convertId(LangConversion.ST_MESSAGE, "NFViaSchema",
				  "This function will create a file using a <i>File Schema</i> "
		    	+ "or <i>File Description</i><br\\><br\\>"
		    	+ "The <b>file schema</b> can be a:<ul>"
		    	+ "<li><b>Cobol</b> copybook"
		    	+ "<li>A <b>RecordEditor file schema</b>"
		    	+ "<li>A Tsv Schema file listing position / length.")
				+ "<\\ul>";
		JEditorPane tips = new JEditorPane("text/html", formDescription);

		pnl	.addComponentRE(1, 5, TIP_HEIGHT, BasePanel.GAP3,
		        BasePanel.FULL, BasePanel.FULL,
				tips)
		
			.addLineRE(   "New File", newFileCombo)
			.addLineRE("Schema File", layoutFileCombo)		.setFieldsToActualSize()
			
			.addLineRE(   dialectLbl, dialectCombo)
			.addLineRE( structureLbl, structureCombo)
			.addLineRE(      fontLbl, fontCombo)
				.setGapRE(BasePanel.GAP1)					.setFieldsToFullSize()
				
			.addLineRE(null, null, createBtn)
				.setGapRE(BasePanel.GAP2)
				
			.addMessage(msgTxt);

		String defaultDirectory = Common.OPTIONS.DEFAULT_FILE_DIRECTORY.getNoStar();
		String recentFileName = Parameters.getApplicationDirectory() + Common.RECENT_CSV_FILES;
		File dataFile = OpenCommon.getOpenDirectory(
								new RecentFiles(recentFileName, null, true, defaultDirectory), 
								null, defaultDirectory);
		
		layoutFileCombo.setText(Common.OPTIONS.DEFAULT_COPYBOOK_DIRECTORY.getWithStar());
		if (dataFile != null) {
			newFileCombo.setText(dataFile.getPath());
		}
		layoutFileCombo.addTextChangeListner(new ChangeListener() {
			@Override public void stateChanged(ChangeEvent e) {
				setCobolVisible();
			}
		});
		setCobolVisible();
	}
	

	private void setCobolVisible() {
		boolean isCobol = isCobolCopybook();
		
		dialectCombo.setVisible(isCobol);
		dialectLbl.setVisible(isCobol);
		fontCombo.setVisible(isCobol);
		fontLbl.setVisible(isCobol);		
		structureLbl.setVisible(isCobol);
		structureCombo.setVisible(isCobol);
	}

	private boolean isCobolCopybook() {
		String layoutFileName = layoutFileCombo.getText();

		return  	new File(layoutFileName).isFile()
				&&  CobolCopybookLoader.isAvailable()
				&&	new SchemaAnalyser().schemaType(layoutFileName) == CopybookLoaderFactory.COBOL_LOADER;
	}



	public final LayoutDetail getLayout() {

		String layoutFileName = layoutFileCombo.getText();
		if (! "".equals(layoutFileName)) {
			File f = new File(layoutFileName);

			if (f.exists() && ! f.isDirectory()) {
				try {
					int schemaId = new SchemaAnalyser().schemaType(layoutFileName);
					if (schemaId >= 0) {
						CopybookLoader loader = CopybookLoaderFactory.getInstance().getLoader(schemaId);
						ExternalRecord rec = loader.loadCopyBook(layoutFileName, 0, 0, fontCombo.getText(), 
								dialectCombo.getSelectedValue(), 0, Common.getLogger());
						if (schemaId == CopybookLoaderFactory.COBOL_LOADER && CobolCopybookLoader.isAvailable()) {
							int fileStructure = ((Integer) structureCombo.getSelectedItem()).intValue();
							if (fileStructure >= 0) {
								rec.setFileStructure(fileStructure);
							}
						}
						return rec.asLayoutDetail();
					}
				} catch (Exception e) {
					msgTxt.setText("Schema load failed: " + e);
					e.printStackTrace();
				}
			}
		}
		return null;
	}

}
