package net.sf.RecordEditor.re.cobol;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.cg.common.CCode;
import net.sf.JRecord.cg.details.ArgumentOption;
import net.sf.JRecord.cg.details.ConstantVals;
import net.sf.JRecord.cg.details.IGenerateOptions;
import net.sf.JRecord.cg.details.TemplateDtls; 
import net.sf.JRecord.cg.schema.CodeGenFileName;
import net.sf.JRecord.cg.schema.LayoutDef;
import net.sf.JRecord.cg.velocity.GenerateVelocity;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.ReOptionDialog;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;

/**
 * This class asks the user for CodeGen options.
 *  
 * @author Bruce Martin
 *
 */
public class CodeGenOptions implements IGenerateOptions, ActionListener {
	
	private final BaseHelpPanel codeGenOptionPnl = new BaseHelpPanel("CodeGenOptions");
//	private final CblLoadData data;
	
	private JEditorPane tips;
	private final JTextField packageIdTxt = new JTextField(50);
	private final JCheckBox deleteOutputDirChk = new JCheckBox();
//	private FileSelectCombo outputDirectory;
//	private JTextField outputExtensionTxt = new JTextField();
	private final CodeGenExport cgx = new CodeGenExport();
	
	public final JDialog dialog = new JDialog(ReMainFrame.getMasterFrame());
	
	private final JButton goBtn = SwingUtils.newButton("Generate Code");
	
	private final TemplateDtls templateDtls;
	private String packageDir, outputDir; 
	private final LayoutDef layoutDef;
	private final LayoutDetail schema;
	private final CodeGenFileName dataFileName;
	
	public CodeGenOptions(LayoutDetail schema, String template, String templateResourceDir, String dataFileName) {
		super();

		this.schema = schema;
		this.templateDtls = new TemplateDtls(null, template, templateResourceDir);
		this.layoutDef = new LayoutDef(schema, schema.getLayoutName());
		this.dataFileName = new CodeGenFileName(dataFileName);
			
		init_100_setupFields();
		init_200_LayoutScreen();
		init_300_Finalise();
	}
	
	
	private void init_100_setupFields() {
	    tips = new JEditorPane("text/html", templateDtls.getDescriptionHtml());

//	    outputDirectory = new FileSelectCombo(Parameters.CODE_GEN_OUTPUT_LIST, 25, true, false, true);
//	    
//	    assignDir(outputDirectory, Common.OPTIONS.DEFAULT_CODEGEN_EXPORT_DIRECTORY.get());
//	    outputExtensionTxt.setText(Common.OPTIONS.DEFAULT_CODEGEN_DIRECTORY_EXTENSION.get());
	    
	    packageIdTxt.setText(Parameters.getString(Parameters.CODEGEN_PACKAGEID));
	    codeGenOptionPnl.setHelpURLre(Common.formatHelpURL(Common.HELP_GEN_4_EDIT));
	    
	    deleteOutputDirChk.setSelected(true);
	}
	
//	private void assignDir(FileSelectCombo dir, String s) {
//		if (s != null) {
//	    	dir.setText(s);
//	    }
//	}
	private void init_200_LayoutScreen() {
		
		JPanel goPnl = new JPanel(new BorderLayout());
		goPnl.add(BorderLayout.EAST, goBtn);
		codeGenOptionPnl
		  .addComponentRE(
	         	1, 3, BasePanel.FILL, BasePanel.GAP0,
		        BasePanel.FULL, BasePanel.FULL,
		        tips)
		     .setGapRE(BaseHelpPanel.GAP2);
		if (templateDtls.hasOption(TemplateDtls.T_REQUIRE_PACKAGE_ID)) {
			codeGenOptionPnl
		     .setFieldsToActualSize();
			codeGenOptionPnl
			  .addLineRE(      "Package Id", packageIdTxt)			.setFieldsToFullSize();
		}
		codeGenOptionPnl
		  .addLineRE("Output Directory", cgx.outputDirectory)
		  .addLineRE("Directory Extension", cgx.outputExtensionTxt)
		      .setGapRE(BaseHelpPanel.GAP0)
		  .addLineRE("Delete Ouput Directory", deleteOutputDirChk)
		      .setGapRE(BaseHelpPanel.GAP1)
		  .addLineRE("", goPnl);
	}
	
	private void init_300_Finalise() {
		dialog.getContentPane().add(codeGenOptionPnl);
		Dimension ps = dialog.getPreferredSize();
		dialog.setPreferredSize(new Dimension( 
				Math.min(ReFrame.getDesktopWidth(), 
						 Math.max(ps.width, 70 * SwingUtils.CHAR_FIELD_WIDTH)), 
				Math.min(ReFrame.getDesktopHeight(),
						Math.max(ps.height, 17 * SwingUtils.CHAR_FIELD_HEIGHT))));

		dialog.pack();
		
		goBtn.addActionListener(this);
	}
	
	
	/**
	 * @param b
	 * @see java.awt.Dialog#setVisible(boolean)
	 */
	public void setVisible(boolean b) {
		dialog.setVisible(b);
	}


	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String packageId = packageIdTxt.getText();
		String schemaName = layoutDef.getJavaName();
		
		outputDir = cgx.getOutputDir(templateDtls.language, templateDtls.template, schemaName);
		
		
		if (isMissing(outputDir)) {
			ReOptionDialog.showMessageDialog(dialog, "You must supply an output directory");
			return;
		}

//		if (isMissing(packageId)) {
//			packageId = Parameters.getString(Parameters.CODEGEN_PACKAGEID);
//		}
		if (isMissing(packageId)) {
			packageDir = "";
			if (templateDtls.hasOption(TemplateDtls.T_REQUIRE_PACKAGE_ID)) {
				ReOptionDialog.showMessageDialog(dialog, "PackageId is requires for this Template");
				return;
			}
		} else {
			packageId = Conversion.replace(packageId,"${schema}", schemaName).toString();
			packageDir = Conversion.replace(packageId, ".", "/") + "/";
		}

		if (deleteOutputDirChk.isSelected()) {
			Parameters.doDeleteDirectory(outputDir);
		}
//		StringBuilder b = new StringBuilder(outputDir);
//		int lenM1 = b.length() - 1;
//		if (b.charAt(lenM1) == '*') {
//			b.setLength(lenM1);
//			lenM1 -=1;
//		}
//		
//		char ch = b.charAt(lenM1);
//		if (ch != '/' && ch != '\\') {
//			b.append('/');
//		}
//		b.append(layoutDef.getSchema().getLayoutName());
//		outputDir = b.toString();
		try {
			GenerateVelocity gv = new GenerateVelocity(this);
			new ShowGeneratedCode(gv.generatedFiles, layoutDef.getSchemaShortName());
			dialog.setVisible(false);
		} catch (Exception e1) {
			String msg = e1.toString();
			JOptionPane.showMessageDialog(null, msg, "Error Message", JOptionPane.ERROR_MESSAGE);
			Common.logMsg(msg, null);
			e1.printStackTrace();
		}
	}

	private boolean isMissing(String s) {
		return s == null || s.length() == 0;
	}
	


	/* (non-Javadoc)
	 * @see net.sf.JRecord.cg.details.IGenerateOptions#getDataFileName()
	 */
	@Override
	public CodeGenFileName getDataFileName() {
		return dataFileName;
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.cg.details.IGenerateOptions#getSchemaDefinition()
	 */
	@Override
	public LayoutDef getSchemaDefinition() {

		return layoutDef;
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.cg.details.IGenerateOptions#getTemplateDtls()
	 */
	@Override
	public TemplateDtls getTemplateDtls() {
		return templateDtls;
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.cg.details.IGenerateOptions#getPackageId()
	 */
	@Override
	public String getPackageId() {
		return packageIdTxt.getText();
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.cg.details.IGenerateOptions#getPackageDir()
	 */
	@Override
	public String getPackageDir() {
		return packageDir;
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.cg.details.IGenerateOptions#getFont()
	 */
	@Override
	public String getFont() {
		return schema.getFontName();
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.cg.details.IGenerateOptions#getOutputDir()
	 */
	@Override
	public String getOutputDir() {
		return outputDir;
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.cg.details.IGenerateOptions#getIo()
	 */
	@Override
	public ArgumentOption getFileStructureCode() {
		int fileStructure = schema.getFileStructure();
		for (ArgumentOption o : ArgumentOption.FILE_ORGANISATION_OPTS) {
			if (o.id == fileStructure) {
				return o;
			}
		}

		return ArgumentOption.newFileStructureOpt(Integer.toString(fileStructure), CCode.getJRecordIoTypeName(fileStructure), "", 
				fileStructure);
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.cg.details.IGenerateOptions#getSplitOption()
	 */
	@Override
	public ArgumentOption getSplitOption() {

		return null;
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.cg.details.IGenerateOptions#isDropCopybookName()
	 */
	@Override
	public boolean isDropCopybookName() {
		return false;
	}
	
	/* (non-Javadoc)
	 * @see net.sf.JRecord.cg.details.IGenerateOptions#getConstantValues()
	 */
	@Override
	public ConstantVals getConstantValues() {
		return ConstantVals.CONSTANT_VALUES;
	}


	public final ArgumentOption getDialect() {
		return null;
	}

}
