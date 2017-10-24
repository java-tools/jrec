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
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.cg.details.ArgumentOption;
import net.sf.JRecord.cg.details.ConstantVals;
import net.sf.JRecord.cg.details.IGenerateOptions;
import net.sf.JRecord.cg.details.TemplateDtls;
import net.sf.JRecord.cg.schema.CodeGenFileName;
import net.sf.JRecord.cg.schema.LayoutDef;
import net.sf.JRecord.cg.velocity.GenerateVelocity;
import net.sf.JRecord.cgen.support.Code2JRecordConstants;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.ReOptionDialog;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;

public class CodeGenOptionsUtl implements IGenerateOptions, ActionListener {
	
	private final BaseHelpPanel codeGenOptionPnl = new BaseHelpPanel("CodeGenOptions");
	private final CblLoadData data;
//	private static final ExtendedOpt[] TEMPLATE_OPTIONS = {
//		new ExtendedOpt(ArgumentOption.TEMPLATE_STANDARD, ""),
//		new ExtendedOpt(ArgumentOption.TEMPLATE_LINE_WRAPPER, ""),
//		new ExtendedOpt(ArgumentOption.TEMPLATE_STD_POJO, ""),
//		new ExtendedOpt(ArgumentOption.TEMPLATE_SCHEMA_CLASS, ""),
//		new ExtendedOpt(ArgumentOption.TEMPLATE_JAVA_POJO, ""),
//		new ExtendedOpt(ArgumentOption.TEMPLATE_BASIC, ""),
//		new ExtendedOpt("error", "error", "", 0, ""),
//	};
	
	private JEditorPane tips;
//	private FileSelectCombo templateDirectory;
//	private Vector<ExtendedOpt> templates = new Vector<ExtendedOpt>(Arrays.asList(TEMPLATE_OPTIONS));
	//private DefaultComboBoxModel<ExtendedOpt> templateMdl = new DefaultComboBoxModel<CodeGenOptions.ExtendedOpt>(TEMPLATE_OPTIONS);
//	private JComboBox<ExtendedOpt> templateCombo = new JComboBox<ExtendedOpt>();
	private JTextField packageIdTxt = new JTextField(50);
//	private FileSelectCombo outputDirectory;
	private final CodeGenExport cgx = new CodeGenExport();
	
	private JDialog dialog = new JDialog(ReMainFrame.getMasterFrame());
	
	private JButton goBtn = SwingUtils.newButton("Generate Code");
	
	private final JCheckBox deleteOutputDirChk = new JCheckBox();

	
	private final TemplateDtls templateDtls;
	private String packageDir, outputDir;
	private LayoutDef layoutDef;
	 
	
	public CodeGenOptionsUtl(CblLoadData data, String template, String templateBase) {
		super();
		this.data = data;
		this.templateDtls = new TemplateDtls(null, template, templateBase, false);
			
		init_100_setupFields();
		init_200_LayoutScreen();
		init_300_Finalise();
	}
	
	
	private void init_100_setupFields() {
	    
	    tips = new JEditorPane("text/html", templateDtls.getDescriptionHtml());
	    tips.setCaretPosition(1);
	    
	    codeGenOptionPnl.setHelpURLre(Common.formatHelpURL(Common.HELP_GEN_UTIL));
	    
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
		  .addLineRE("Output Extension", cgx.outputExtensionTxt)
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
						Math.max(ps.height, 18 * SwingUtils.CHAR_FIELD_HEIGHT))));
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
		
		if (! templateDtls.hasOption(TemplateDtls.T_SPLIT_ALLOWED)) {
			if (data.getSplitOption() != CopybookLoader.SPLIT_NONE) {
				ReOptionDialog.showMessageDialog(dialog, "Split is not supported for this Template");
				return;
			}
		}
		
		if (isMissing(cgx.outputDirectory.getText())) {
			ReOptionDialog.showMessageDialog(dialog, "You must supply an output directory");
			return;
		}
		
		ExternalRecord xRecord = data.getXRecordJR(data.dropCopybookNameChk.isSelected());
		templateDtls.setMultiRecord(xRecord.getNumberOfRecords() > 1);
		
		
		layoutDef = new LayoutDef(
				xRecord.asLayoutDetail(), 
				Conversion.replace(data.copybookFileCombo.getText(), "\\", "/").toString(), null); 

		String schemaName = layoutDef.getJavaName();
		
		outputDir = cgx.getOutputDir(templateDtls.getLanguage(), templateDtls.template, schemaName);

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
			StringBuilder bb = Conversion.replace(packageId, "${template}", templateDtls.template);
			packageId = Conversion.replace(bb,"${schema}", layoutDef.getJavaName()).toString();
			packageDir = Conversion.replace(packageId, ".", "/") + "/";
		}
		

		if (deleteOutputDirChk.isSelected()) {
			Parameters.doDeleteDirectory(outputDir);
		}
//		StringBuilder b = new StringBuilder(outputDir);
//
//		b.append(layoutDef.getCobolName())
//		 .append('_')
//		 .append(templateDtls.template);
//		outputDir = b.toString();
		try {
			GenerateVelocity gv = new GenerateVelocity(this, Common.OPTIONS.applicationDetails);
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
		return new CodeGenFileName(data.sampleFileCombo.getText());
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
		return data.fontNameCombo.getText();
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.cg.details.IGenerateOptions#getOutputDir()
	 */
	@Override
	public String getOutputDir() {
		return outputDir;
	}


	/**
	 * @see net.sf.JRecord.cg.details.IGenerateOptions#getFileStructureCode()
	 */
	@Override
	public ArgumentOption getFileStructureCode() {
		int fileStructure = data.getFileStructure();
		for (ArgumentOption o : ArgumentOption.FILE_ORGANISATION_OPTS) {
			if (o.id == fileStructure) {
				return o;
			}
		}

		return new ArgumentOption(Integer.toString(fileStructure), Code2JRecordConstants.getJRecordIoTypeName(fileStructure), "", fileStructure);
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.cg.details.IGenerateOptions#getSplitOption()
	 */
	@Override
	public ArgumentOption getSplitOption() {
		int splitOption = data.getSplitOption();
		for (ArgumentOption o : ArgumentOption.SPLIT_OPTS) {
			if (o.id == splitOption) {
				return o;
			}
		}
		return null;
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.cg.details.IGenerateOptions#isDropCopybookName()
	 */
	@Override
	public boolean isDropCopybookName() {
		return data.dropCopybookNameChk.isSelected();
	}
	
	/* (non-Javadoc)
	 * @see net.sf.JRecord.cg.details.IGenerateOptions#getConstantValues()
	 */
	@Override
	public ConstantVals getConstantValues() {
		return ConstantVals.CONSTANT_VALUES;
	}


	public final ArgumentOption getDialect() {
		int dialect = data.getDialect();
		for (ArgumentOption o : ArgumentOption.DIALECT_OPTS) {
			if (o.id == dialect) {
				return o;
			}
		}
		return ArgumentOption.newFileStructureOpt(Integer.toString(dialect), Code2JRecordConstants.getDialectName(dialect), "", dialect);
	}
}
