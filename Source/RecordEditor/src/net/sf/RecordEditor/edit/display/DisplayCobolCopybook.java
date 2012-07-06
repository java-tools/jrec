package net.sf.RecordEditor.edit.display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Common.XmlConstants;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.External.XmlCopybookLoader;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.JRecord.Numeric.ConversionManager;
import net.sf.JRecord.Numeric.Convert;
import net.sf.JRecord.Types.Type;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.openFile.ComputerOptionCombo;
import net.sf.RecordEditor.re.tree.TreeParserXml;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.fileStorage.DataStoreStd;

import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.FileChooser;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.cb2xml.Cb2Xml;
import net.sf.cb2xml.CopyBookAnalyzer;
import net.sf.cb2xml.def.NumericDefinition;


public class DisplayCobolCopybook implements ActionListener {

	private static final String ERROR_COBOL_COPYBOOK_FILE_IS_A_DIRECTORY = LangConversion.convert("Error: cobol copybook file is a directory");
	private static final String NOTHING_TO_DISPLAY = LangConversion.convert("Nothing to Display");
	private static final String COBOL_COPYBOOK_DOES_NOT_EXIST = LangConversion.convert("Cobol Copybook does not exist");
	private static final String COBOL_COPYBOOK_NAME_MSG = LangConversion.convert("You must enter a Cobol Copybook name");

	private FileChooser copybook = new FileChooser();
	private ComputerOptionCombo cobolDialect = new ComputerOptionCombo();
	private JButton displayBtn = SwingUtils.newButton("Display");
	private JCheckBox showComments = new JCheckBox();
	private JTextField msgTxt = new JTextField();


	public DisplayCobolCopybook() {
		BasePanel p = new BasePanel();
		ReFrame frame = new ReFrame("", "Copybook Analysis", "Copybook Analysis", null);

		copybook.setText(Common.OPTIONS.DEFAULT_COBOL_DIRECTORY.get());
		showComments.setSelected(false);

		p.setGap(BasePanel.GAP3);
		p.addLine("Copybook", copybook, copybook.getChooseFileButton());
		p.setGap(BasePanel.GAP1);
		p.addLine("Cobol Dialect", cobolDialect);
		p.setGap(BasePanel.GAP1);
		p.addLine("Include Comments", showComments, displayBtn);
		p.setGap(BasePanel.GAP3);
		p.addMessage(msgTxt);

		frame.addMainComponent(p);
		frame.setVisible(true);

		displayBtn.addActionListener(this);
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {

		if (event.getSource() == displayBtn) {
			String name = copybook.getText();
			if ("".equals(name)) {
				msgTxt.setText(COBOL_COPYBOOK_NAME_MSG);
				copybook.requestFocus();
			} else if ((new File(name)).isDirectory()) {
				msgTxt.setText(ERROR_COBOL_COPYBOOK_FILE_IS_A_DIRECTORY);
			} else if ((new File(name)).exists()) {
				display(name, cobolDialect.getSelectedIndex(), showComments.isSelected());
			} else {
				msgTxt.setText(COBOL_COPYBOOK_DOES_NOT_EXIST);
				copybook.requestFocus();
			}

		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void display(String fileName, int dialect, boolean incComments) {

		Convert conv = ConversionManager.getInstance().getConverter(dialect) ;
		try {
			String xml;
			AbstractLineReader reader = LineIOProvider.getInstance().getLineReader(Constants.IO_XML_USE_LAYOUT);
			File file = new File(fileName);
			DataStoreStd<AbstractLine> lines = new DataStoreStd<AbstractLine>(null);
			AbstractLine aLine;



			CopyBookAnalyzer.setNumericDetails((NumericDefinition) conv.getNumericDefinition());
			xml = Cb2Xml.convertToXMLString(file);

			reader.open(new ByteArrayInputStream(xml.getBytes()), getXmlLayout(incComments));  //(LayoutDetail) null);

			while ((aLine = reader.read()) != null) {
//				System.out.println(">" + aLine.getFieldValue(XmlConstants.XML_NAME).asString() + "< >"
//						+ "XML Comment "
//						+ incComments + " " + ("XML Comment".equals(aLine.getFieldValue(XmlConstants.XML_NAME))));
				if (incComments || ! "XML Comment".equals(aLine.getFieldValue(XmlConstants.XML_NAME).asString())) {
					lines.add(aLine);
				}
			}
			lines.setLayout(reader.getLayout());

			if (lines.size() > 1) {
				new LineTree(new FileView(lines, null, null), TreeParserXml.getInstance(), true, 1).cb2xmlStuff();
			} else {
				msgTxt.setText(NOTHING_TO_DISPLAY);
			}
			reader.close();
			//new FileView(lines, null, null);
		} catch (Exception e) {
			Common.logMsgRaw(e.getMessage(), null);
			e.printStackTrace();
		}
	}


	private LayoutDetail getXmlLayout(boolean incComment) {
		//FieldDetail[] fields
		String recordName = "item";
		int idx = 0;
		FieldDetail[] fields =  {
			new FieldDetail(XmlConstants.XML_NAME, "", Type.ftXmlNameTag, 0, "", 0, "").setPosOnly(idx++),
			new FieldDetail(XmlConstants.END_ELEMENT, "", Type.ftCheckBoxTrue, 0, "", 0, "").setPosOnly(idx++),
			new FieldDetail(XmlConstants.FOLLOWING_TEXT, "", Type.ftMultiLineEdit, 0, "", 0, "").setPosOnly(idx++),
			new FieldDetail(XmlCopybookLoader.ATTR_LEVEL, "", Type.ftChar, 0, "", 0, "").setPosOnly(idx++),
			new FieldDetail(XmlCopybookLoader.ATTR_NAME, "", Type.ftChar, 0, "", 0, "").setPosOnly(idx++),
			new FieldDetail(XmlCopybookLoader.ATTR_POSITION, "", Type.ftChar, 0, "", 0, "").setPosOnly(idx++),
			new FieldDetail(XmlCopybookLoader.ATTR_STORAGE_LENGTH, "", Type.ftChar, 0, "", 0, "").setPosOnly(idx++),
			new FieldDetail("display-length", "", Type.ftChar, 0, "", 0, "").setPosOnly(idx++),
			new FieldDetail(XmlCopybookLoader.ATTR_PICTURE, "", Type.ftChar, 0, "", 0, "").setPosOnly(idx++),
			new FieldDetail(XmlCopybookLoader.ATTR_USAGE, "", Type.ftChar, 0, "", 0, "").setPosOnly(idx++),
		};
		RecordDetail item = new RecordDetail(recordName, XmlConstants.XML_NAME, recordName, Constants.RT_XML,
                "", "", "",
                fields,
                0, 0);
		RecordDetail[] recs = {item, null, null, null, null, null};

		if (incComment) {
			idx = 0;
			recordName = "XML Comment";
			FieldDetail[] fields1 =  {
					new FieldDetail(XmlConstants.XML_NAME, "", Type.ftXmlNameTag, 0, "", 0, "").setPosOnly(idx++),
					new FieldDetail(XmlConstants.END_ELEMENT, "", Type.ftCheckBoxTrue, 0, "", 0, "").setPosOnly(idx++),
					new FieldDetail(XmlConstants.FOLLOWING_TEXT, "", Type.ftMultiLineEdit, 0, "", 0, "").setPosOnly(idx++),
					new FieldDetail("zz:Dummy", "", Type.ftChar, 0, "", 0, "").setPosOnly(idx++),
			};
			RecordDetail comment = new RecordDetail(recordName, XmlConstants.XML_NAME, recordName, Constants.RT_XML,
					"", "", "",
					fields1,
					0, 0);
			RecordDetail[] r1 = {item, comment, null, null, null, null};
			recs = r1;
		}

		return new LayoutDetail("XML Document",
				recs ,
    			"", Constants.RT_XML, null, null, "", null, Constants.IO_XML_BUILD_LAYOUT
		);

	}




	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ReMainFrame("Cobol CopyBook Analysis", "", "An");
		new DisplayCobolCopybook();
		//.display("/home/bm/Programs/open-cobol-1.0/CobolSrc/default/cpyComp.cbl", Convert.FMT_OPEN_COBOL);
	}

}
