package net.sf.RecordEditor.re.cobol;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import net.sf.JRecord.External.ExternalRecord;
import net.sf.RecordEditor.re.editProperties.EditOptions;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.ReAbstractAction;
import net.sf.RecordEditor.utils.lang.ReOptionDialog;
import net.sf.RecordEditor.utils.screenManager.SmCode;

public class CodeGenMaster {

	JMenuBar bar = new JMenuBar();
	private final CobolAnalyseScreen analysis;
//	private CodeGenOptionsCbl genOptions;
	
	@SuppressWarnings("serial")
	public CodeGenMaster() {
		JMenu  generateMenu = new JMenu("Generate");
		JMenu  cbl2CsvMenu  = new JMenu("Cobol <=> Csv");
		JMenu  cbl2XmlMenu  = new JMenu("Cobol <=> Xml");
		JMenu  cbl2JsonMenu = new JMenu("Cobol ==> Json");
		JMenu  editMenu     = new JMenu("Edit");
		JMenu  helpMenu     = new JMenu("Help");

		JButton genBtn = new JButton("Generate Java~JRecord");
	//	JButton optionBtn = new JButton("Cobol/JRecord options");
		
		AbstractAction genAction = new ReAbstractAction("Java~JRecord code", Common.ID_WIZARD_ICON) {
			@Override public void actionPerformed(ActionEvent e) {
				analysis.genCobol();
			}
		};
		
		generateMenu.add(genAction);
		generateMenu.add(cbl2CsvMenu);
		generateMenu.add(cbl2XmlMenu);
		generateMenu.add(cbl2JsonMenu);
		
		cbl2CsvMenu.add(new GenAction("Windows bat file", "batCbl2Csv"));	
		cbl2CsvMenu.add(new GenAction("Shell Script", "shCbl2Csv"));
		cbl2CsvMenu.addSeparator();
		cbl2CsvMenu.add(new AboutAction("About Cobol To Csv", 
				  "<h2>CobolToCsv</h2>"
				+ "<p>The <b>CobolToCsv</b> (https://sourceforge.net/projects/coboltocsv/) project<br/>"
			    + "will convert <b>Cobol Data</b> files to/From <b>Csv</b> files."
				+ "<p>The options on this menu generate bat/shell scripts to do the<br/>"
			    + "Cobol/Csv conversion for a specified Cobol Copybook."));
		
		cbl2XmlMenu.add(new GenAction("Java Script (jjs)", "jjsCbl2Xml"));
		cbl2XmlMenu.add(new GenAction("Java", "javaCbl2Xml"));
		cbl2XmlMenu.add(new GenAction("Windows bat file", "batCbl2Xml"));
		cbl2XmlMenu.add(new GenAction("Shell Script", "shCbl2Xml"));
		cbl2XmlMenu.addSeparator();
		cbl2XmlMenu.add(new AboutAction("About Cobol To Xml", 
				  "<h2>CobolToXml</h2>"
				+ "<p>The <b>CobolToXml</b> (https://sourceforge.net/projects/coboltoxml/) project<br/>"
			    + "will convert <b>Cobol Data</b> files to/From <b>Xml</b> files."
				+ "<p>The options on this menu generate bat/shell scripts, Java script, java programs<br/>"
			    + "to do the Cobol/Xml conversion for a specified Cobol Copybook."));
		
		cbl2JsonMenu.add(new GenAction("Java Script (jjs)", "jjsCbl2Json"));
		cbl2JsonMenu.add(new GenAction("Java", "javaCbl2Json"));
		cbl2JsonMenu.add(new GenAction("Windows bat file", "batCbl2Json"));
		cbl2JsonMenu.add(new GenAction("Shell Script", "shCbl2Json"));
		cbl2JsonMenu.addSeparator();
		cbl2JsonMenu.add(new AboutAction("About Cobol To Json", 
				  "<h2>CobolToJson</h2>"
				+ "<p>The <b>CobolToJson</b> program is part of the<br/>" +
				  "JRecord (https://sourceforge.net/projects/jrecord/) project.<br/>"
			    + "The program will convert <b>Cobol Data</b> files to <b>JSon</b> files."
				+ "<p>The options on this menu generate bat/shell scripts, Java script, java programs<br/>"
			    + "to do the Cobol To Json conversion for a specified Cobol Copybook."));
		
		editMenu.add(new ReAbstractAction("Edit Cobol options") { 		
			@Override public void actionPerformed(ActionEvent e) {
				EditOptions.newEditProperties(EditOptions.SHOW_COBOL);
			}
		});
		
		helpMenu.add(new ReAbstractAction("Java~JRecord help") {	
			@Override public void actionPerformed(ActionEvent e) {
				analysis.showHelpRE();
			}
		});
		
		bar.add(generateMenu);
		bar.add(editMenu);
		bar.add(helpMenu);
		
		analysis = new CobolAnalyseScreen("", Common.getConnectionIndex(), bar, genBtn, Common.HELP_JREC_GEN, false);
	
		genBtn.addActionListener(genAction);
	}
	
//	private void genCobol() {
//		CblLoadData cblDtls = analysis.getCblDtls();
//		ExternalRecord xRecord = cblDtls.getXRecord();
//		
//		if (xRecord == null) {
//			ReOptionDialog.showMessageDialog(analysis, "Can not load the schema - check the Copybook / parameters");
//			return;
//		}
//
//		if (genOptions == null) {
//			genOptions = new CodeGenOptionsCbl(cblDtls);
//		}
//		genOptions.setVisible(true);
//	}
	
	

	@SuppressWarnings("serial")
	private class GenAction extends ReAbstractAction {
		final String skelName;
		
		private GenAction(String name, String skelName) {
			super(name);
			this.skelName = skelName;
		}
		
		@Override public void actionPerformed(ActionEvent e) {
			genUtl(null, skelName);	
		}
		
		private void genUtl(String templateBase, String template) {
			CblLoadData cblDtls = analysis.getCblDtls();
			ExternalRecord xRecord = cblDtls.getXRecord();
			
			if (xRecord == null) {
				ReOptionDialog.showMessageDialog(analysis, "Can not load the schema - check the Copybook / parameters");
				return;
			}

			if (templateBase == null) {
				templateBase = "/net/sf/RecordEditor/cg/velocity/";
			}
			(new CodeGenOptionsUtl(cblDtls, template, templateBase)).setVisible(true);
		}
	}
	
	@SuppressWarnings("serial")
	private static class AboutAction extends ReAbstractAction {
		private final String name, text;

		protected AboutAction(String name, String text) {
			super(name);
			this.name = name;
			this.text = text;
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			SmCode.showHtmlPnl(name, text);
		}
		
	}

}
