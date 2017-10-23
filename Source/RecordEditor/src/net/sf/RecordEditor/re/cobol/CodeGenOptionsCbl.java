package net.sf.RecordEditor.re.cobol;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.JRecord.cg.common.CCode;
import net.sf.JRecord.cg.details.ArgumentOption;
import net.sf.JRecord.cg.details.ConstantVals;
import net.sf.JRecord.cg.details.IGenerateOptions;
import net.sf.JRecord.cg.details.TemplateDtls;
import net.sf.JRecord.cg.nameConversion.FieldNameConversionManager;
import net.sf.JRecord.cg.schema.CodeGenFileName;
import net.sf.JRecord.cg.schema.LayoutDef;
import net.sf.JRecord.cg.velocity.GenerateVelocity;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.ReOptionDialog;
import net.sf.RecordEditor.utils.msg.UtMessages;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.ComboBoxs.ManagerCombo;
import net.sf.RecordEditor.utils.swing.treeCombo.FileSelectCombo;

public class CodeGenOptionsCbl implements IGenerateOptions, ActionListener, ISetVisible {
	
	private final BaseHelpPanel codeGenOptionPnl = new BaseHelpPanel("CodeGenOptions");
	private final CblLoadData data;
	private static final ExtendedOpt[] TEMPLATE_OPTIONS = {
		new ExtendedOpt(ArgumentOption.TEMPLATE_STANDARD, ""),
		new ExtendedOpt(ArgumentOption.TEMPLATE_LINE_WRAPPER, ""),
		new ExtendedOpt(ArgumentOption.TEMPLATE_STD_POJO, ""),
		new ExtendedOpt(ArgumentOption.TEMPLATE_POJO, ""),
		new ExtendedOpt(ArgumentOption.TEMPLATE_POJO_INTERFACE, ""),
		new ExtendedOpt(ArgumentOption.TEMPLATE_SCHEMA_CLASS, ""),
		new ExtendedOpt(ArgumentOption.TEMPLATE_JAVA_POJO, ""),
		new ExtendedOpt(ArgumentOption.TEMPLATE_BASIC, ""),
//		new ExtendedOpt("error", "error", "", 0, ""),
	};
	
	private JEditorPane tips;
	private FileSelectCombo templateDirectory;
	private Vector<ExtendedOpt> templates = new Vector<ExtendedOpt>(Arrays.asList(TEMPLATE_OPTIONS));
	//private DefaultComboBoxModel<ExtendedOpt> templateMdl = new DefaultComboBoxModel<CodeGenOptions.ExtendedOpt>(TEMPLATE_OPTIONS);
	private JComboBox<ExtendedOpt> templateCombo = new JComboBox<ExtendedOpt>();
	private final ManagerCombo fieldNameConversionCombo = ManagerCombo.newCombo(FieldNameConversionManager.getInstance());
	private JTextField packageIdTxt = new JTextField(50);
//	private FileSelectCombo outputDirectory;
	private final CodeGenExport cgx = new CodeGenExport();
	
	private JDialog dialog = new JDialog(ReMainFrame.getMasterFrame());
	
	
	private final JCheckBox deleteOutputDirChk = new JCheckBox();

	private JButton goBtn = SwingUtils.newButton("Generate Code");
	
	private TemplateDtls templateDtls;
	private String packageDir, outputDir;
	private LayoutDef layoutDef;
	
	public CodeGenOptionsCbl(CblLoadData data) {
		super();
		this.data = data;
			
		init_100_setupFields();
		init_200_LayoutScreen();
		init_300_Finalise();
	}
	
	
	private void init_100_setupFields() {
	    String templateDir = Common.OPTIONS.DEFAULT_USER_TEMPLATE_DIRECTORY.get();
	    String jrecTemplate = Parameters.getString(Parameters.CODEGEN_TEMPLATE);
	    int jrecTemplateIdx = 0;
	    
	    tips = new JEditorPane("text/html", UtMessages.CODE_GEN_MSG.get());

	    templateDirectory = new FileSelectCombo(Parameters.TEMPLATE_DIR_LIST, 10, true, true, true);
	    
	    for (int i = 0; i < TEMPLATE_OPTIONS.length; i++) {
	    	if (TEMPLATE_OPTIONS[i].option.equalsIgnoreCase(jrecTemplate)) {
	    		jrecTemplateIdx = i;
	    		break;
	    	}
	    }
	    
	    update4TemplateDir();
	    templateCombo.setSelectedIndex(jrecTemplateIdx);
	    assignDir(templateDirectory, templateDir);
	    
	    packageIdTxt.setText(Parameters.getString(Parameters.CODEGEN_PACKAGEID));
	    codeGenOptionPnl.setHelpURLre(Common.formatHelpURL(Common.HELP_GEN_TEMPLATE));
	    
	    deleteOutputDirChk.setSelected(true);
	    
	    
	    try {
	    	String s = Parameters.getString(Parameters.FIELD_NAME_CONVERSION_IDX);
	    	if (s != null && s.length() > 0) {
	    		int idx = Integer.parseInt(s);
	    		if (idx >= 0 && idx < fieldNameConversionCombo.getItemCount()) {
	    			fieldNameConversionCombo.setSelectedIndex(idx);
	    		}
	    	}
	    } catch (Exception e) {
		}
	}
	
	private void assignDir(FileSelectCombo dir, String s) {
		if (s != null) {
	    	dir.setText(s);
	    }
	}
	private void init_200_LayoutScreen() {
		
		JPanel goPnl = new JPanel(new BorderLayout());
		goPnl.add(BorderLayout.EAST, goBtn);
		codeGenOptionPnl
		  .addComponentRE(
	         	1, 3, BasePanel.FILL, BasePanel.GAP0,
		        BasePanel.FULL, BasePanel.FULL,
		        tips)
		     .setGapRE(BaseHelpPanel.GAP1)
		  .addLineRE("Template Directory", templateDirectory)
		  .setFieldsToActualSize();
		codeGenOptionPnl
		  .addLineRE(        "Template", templateCombo)			
		     .setGapRE(BaseHelpPanel.GAP0)
		  .addLineRE("Field Name Conversion", fieldNameConversionCombo)
		     .setGapRE(BaseHelpPanel.GAP0)
		  .addLineRE(      "Package Id", packageIdTxt)			.setFieldsToFullSize();
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
		
		dialog.pack();
		
		goBtn.addActionListener(this);
		
		templateDirectory.addFcFocusListener(new FocusAdapter() {	
			@Override public void focusLost(FocusEvent e) {
				update4TemplateDir();
			}
		});
		
		Dimension ps = dialog.getPreferredSize();
//		System.out.println(" >>>> " + ps.height + " " + (ps.height / SwingUtils.CHAR_FIELD_HEIGHT));
		dialog.setPreferredSize(new Dimension( 
				Math.min(ReFrame.getDesktopWidth(), ps.width), 
				Math.min(ReFrame.getDesktopHeight(),
						 Math.max(ps.height+ 3 * SwingUtils.CHAR_FIELD_HEIGHT,
								 35 * SwingUtils.CHAR_FIELD_HEIGHT))));
		
		codeGenOptionPnl.registerComponentRE(tips);
//		System.out.println(". " + dialog.getPreferredSize().height);
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
		try {
			String packageId = packageIdTxt.getText();
			String template = templateCombo.getSelectedItem().toString();
			
			int fldNameIdx = fieldNameConversionCombo.getSelectedIndex();
			FieldNameConversionManager.setCurrentConversion(fldNameIdx);
			
			Parameters.setProperty(Parameters.FIELD_NAME_CONVERSION_IDX, Integer.toString(fldNameIdx));
		    Object o = templateCombo.getSelectedItem();
		    if (o != null) {
		    	Parameters.setProperty(Parameters.CODEGEN_TEMPLATE, o.toString());
		    }
		    
		    
			
			ExternalRecord xRecord = data.getXRecordJR(data.dropCopybookNameChk.isSelected());
			templateDtls = new TemplateDtls(templateDirectory.getText(), template, xRecord.getNumberOfRecords() > 1);
			
			if (! templateDtls.hasOption(TemplateDtls.T_SPLIT_ALLOWED)) {
				if (data.getSplitOption() != CopybookLoader.SPLIT_NONE) {
					ReOptionDialog.showMessageDialog(dialog, "Split is not supported for this Template");
					return;
				}
			}
	
			if (isMissing(cgx.outputDirectory.getText())) {
				ReOptionDialog.showMessageDialog(dialog, "You must supply an output directory");
				cgx.outputDirectory.requestFocus();
				return;
			}
			
			
			
			LayoutDetail schema = xRecord.asLayoutDetail();
			
			if (! templateDtls.hasOption(TemplateDtls.T_DUPLICATE_FIELD_NAMES)) {
				Set<String> duplicateFieldNames = schema.getDuplicateFieldNames();
				if (duplicateFieldNames != null && duplicateFieldNames.size() > 0) {
					boolean ok = true;
					for (String fn : duplicateFieldNames) {
						List<IFieldDetail> fields = schema.getFieldListForName(fn);
						if (ok && fields.size() > 1) {
							IFieldDetail f = fields.get(0);
							IFieldDetail tf; 
							for (int i = 1; i < fields.size(); i++) {
								tf = fields.get(i);
								if (f.getPos() != tf.getPos() 
								|| f.getLen() != tf.getLen() 
								|| f.getType() != tf.getType()) {
									ok = false;
									break;
								}
							}
						}
					}
					
					if (! ok) {
						showDuplicateFieldMsg(duplicateFieldNames);
					}
					//return;				
				}
			} else if (isDuplicateArrayFields(schema.getDuplicateFieldNames())) {
				return;
			}
			layoutDef = new LayoutDef(
					schema, 
					Conversion.replace(data.copybookFileCombo.getText(), "\\", "/").toString(),
					null); 
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
				StringBuilder bb = Conversion.replace(packageId, "${template}", template);
				packageId = Conversion.replace(bb,"${schema}", layoutDef.getJavaName()).toString();
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
//		b.append(layoutDef.getCobolName())
//		 .append('_')
//		 .append(template);
//		outputDir = b.toString();
			GenerateVelocity gv = new GenerateVelocity(this, Common.OPTIONS.applicationDetails);
			
			if (gv.generatedFiles == null || gv.generatedFiles.size() == 0) {
				Common.logMsg("Nothing generated ", null);
			} else {
				new ShowGeneratedCode(gv.generatedFiles, layoutDef.getSchemaShortName());
			}
			dialog.setVisible(false);
		} catch (Exception e1) {
			String msg = e1.toString();
			JOptionPane.showMessageDialog(null, msg, "Error Message", JOptionPane.ERROR_MESSAGE);
			Common.logMsg(msg, null);
			e1.printStackTrace();
		}
	}


	/**
	 * @param duplicateFieldNames
	 */
	public void showDuplicateFieldMsg(Set<String> duplicateFieldNames) {
		HashSet<String> dups = new HashSet<String>(duplicateFieldNames.size() * 3 / 2);
		Iterator<String> dupIterator = duplicateFieldNames.iterator();
		while (dupIterator.hasNext()) {
			String s = dupIterator.next();
			int indexOf = s.indexOf('(');
			if (indexOf > 0) {
				s = s.substring(0, indexOf - 1);
			}
			dups.add(s);
		}
		
		StringBuilder b = new StringBuilder("Duplicate Field Names:");
		
		dupIterator = dups.iterator();
		for (int i = 0; dupIterator.hasNext(); i++) {
			if (i % 3 == 0) {
				b.append('\n');
			}
			b.append('\t').append(dupIterator.next());
		}

		showMessage(b.toString(), "The Cobol Copybook has duplicate Field names, you will need to make changes to the code !!!");
	}


	/**
	 * @param logMsg
	 */
	public void showMessage(String logMsg, String msg) {
		data.msgField.logMsg(AbsSSLogger.ERROR, logMsg);
		Common.logMsg(logMsg, null);
		ReOptionDialog.showMessageDialog(
				dialog,
				msg);
	}
	
	private boolean isDuplicateArrayFields(Set<String> duplicateFieldNames) {
		if (duplicateFieldNames != null && duplicateFieldNames.size() > 0) {
			HashSet<String> dups = new HashSet<String>(duplicateFieldNames.size() * 3 / 2);
			Iterator<String> dupIterator = duplicateFieldNames.iterator();
			while (dupIterator.hasNext()) {
				String s = dupIterator.next();
				int indexOf = s.indexOf('(');
				if (indexOf > 0) {
					dups.add( s.substring(0, indexOf - 1));
				}
			}
			
			if (dups.size() > 0) {
				StringBuilder b = new StringBuilder("Duplicate Array Field Names:");
				
				dupIterator = dups.iterator();
				for (int i = 0; dupIterator.hasNext(); i++) {
					if (i % 3 == 0) {
						b.append('\n');
					}
					b.append('\t').append(dupIterator.next());
				}

				showMessage(b.toString(), "The Cobol Copybook has duplicate Array Field names which is not allowed");
				return true;
			}
		}
		return false;
	}

	private boolean isMissing(String s) {
		return s == null || s.length() == 0;
	}
	
	private void update4TemplateDir() {
		String dir = templateDirectory.getText();
		File f;
		int idx = TEMPLATE_OPTIONS.length;
		
		if ((! isMissing(dir)) && (f = new File(dir)).exists()) {
			File[] dirs = f.listFiles(new FileFilter() {
				@Override public boolean accept(File pathname) {				
					if (pathname.isDirectory()) {
						return (new File(pathname, TemplateDtls.GENERATE_PROPERTIES)).exists();
					}
					return false;
				}
			});

			for (File tf : dirs) {
				String name = tf.getName(); 
				ExtendedOpt e = new ExtendedOpt(name, name, "", 0, "");
				if (idx < templates.size()) {
					templates.set(idx, e);
				} else {
					templates.add(e);
				}
				idx += 1;
			}
		}
		
		for (int i = templates.size() -1; i >= idx; i--) {
			templates.remove(i);
		}
		templateCombo.setModel(new DefaultComboBoxModel<CodeGenOptionsCbl.ExtendedOpt>(templates));
	}

	private static class ExtendedOpt extends ArgumentOption {
//		private final String tipDetails;

		protected ExtendedOpt(ArgumentOption opt, String tipDetails) {
			this(opt.option, opt.code, opt.description, opt.id, tipDetails);
		}

		protected ExtendedOpt(String option, String code, String description,
				int id, String tipDetails) {
			super(option, code, description, id);
			
//			this.tipDetails = tipDetails;
		}
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


	/* (non-Javadoc)
	 * @see net.sf.JRecord.cg.details.IGenerateOptions#getIo()
	 */
	@Override
	public ArgumentOption getFileStructureCode() {
		int fileStructure = data.getFileStructure();
		for (ArgumentOption o : ArgumentOption.FILE_ORGANISATION_OPTS) {
			if (o.id == fileStructure) {
				return o;
			}
		}

		return ArgumentOption.newFileStructureOpt(Integer.toString(fileStructure), CCode.getJRecordIoTypeName(fileStructure), "", fileStructure);
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

		return new ArgumentOption(Integer.toString(dialect), CCode.getDialectName(dialect), "", dialect);
	}

}
