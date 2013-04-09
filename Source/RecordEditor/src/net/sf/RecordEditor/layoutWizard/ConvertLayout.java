package net.sf.RecordEditor.layoutWizard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.External.CopybookLoaderFactory;
import net.sf.JRecord.External.CopybookWriterManager;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.ToExternalRecord;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.JRecord.Log.ScreenLog;
import net.sf.RecordEditor.re.openFile.ComputerOptionCombo;
import net.sf.RecordEditor.re.openFile.ReadLayout;
import net.sf.RecordEditor.re.openFile.SplitCombo;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.edit.ManagerRowList;
import net.sf.RecordEditor.utils.msg.UtMessages;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBox;
import net.sf.RecordEditor.utils.swing.FileChooser;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.ComboBoxs.ManagerCombo;

@SuppressWarnings("serial")
public class ConvertLayout extends ReFrame {

	private ManagerCombo loaderOptions = ManagerCombo.newCopybookLoaderCombo();
	private FileChooser  copybookFile  = new FileChooser();
	private SplitCombo   splitOptions  = new SplitCombo();
	private JTextField   fontName      = new JTextField();
	private BmKeyedComboBox fileStructure     = new BmKeyedComboBox(
			new ManagerRowList(LineIOProvider.getInstance(), false), false);

	private ComputerOptionCombo binaryOptions = new ComputerOptionCombo();
	private FileChooser  sampleFile  = new FileChooser();
	private JTextField   layoutName  = new JTextField();

	private BmKeyedComboBox     writerOptions =  new BmKeyedComboBox(
			new ManagerRowList(CopybookWriterManager.getInstance(), false), false);
	private FileChooser     outputCopybookDir = new FileChooser();

	private JButton goBtn = SwingUtils.newButton("Convert");

	private ScreenLog msgField;

	private ActionListener listner = new ActionListener() {

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == goBtn) {
				copyLayout();
			} else {
				int loaderId = loaderOptions.getSelectedIndex();
				if (loaderId >= 0) {
					boolean enabled = CopybookLoaderFactory.getInstance()
							.isBasedOnInputFile(loaderId);
					sampleFile.setEnabled(enabled);
					layoutName.setEnabled(enabled);
				}
			}
		}

	};

	public ConvertLayout() {
		super("", "Convert Copybook", null);

		init_Fields();
		setup_Screen();
	}

	private void init_Fields() {
	    String dir = Parameters.getFileName(Parameters.COPYBOOK_DIRECTORY);



		loaderOptions.setEnglish(Common.OPTIONS.COPYBOOK_READER.get());
		if (loaderOptions.getSelectedIndex() < 0) {
			loaderOptions.setSelectedIndex(0);
		}

		copybookFile.setText(dir);
		outputCopybookDir.setText(dir);
		outputCopybookDir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		writerOptions.setSelectedIndex(Common.getCopybookWriterIndex());

		sampleFile.setText(Common.OPTIONS.DEFAULT_FILE_DIRECTORY.get());

		sampleFile.setEnabled(false);
		layoutName.setEnabled(false);
	}

	private void setup_Screen() {
		BasePanel pnl = new BasePanel();

		msgField = new ScreenLog(pnl);

		pnl.setGap(BasePanel.GAP2);
		pnl.addLine("Input Copybook", copybookFile, copybookFile.getChooseFileButton());
		pnl.addLine("Input Copybook Type", loaderOptions);
		pnl.addLine("Split Copybook", splitOptions);
		pnl.addLine("File Structure", fileStructure);
		pnl.addLine("Font Name", fontName);
		pnl.addLine("Binary Format", binaryOptions);
		pnl.setGap(BasePanel.GAP1);
		pnl.addLine("Sample File", sampleFile, sampleFile.getChooseFileButton());
		pnl.addLine("Layout Name", layoutName);

		pnl.setGap(BasePanel.GAP3);

		pnl.addLine("Output Copybook Directory", outputCopybookDir, outputCopybookDir.getChooseFileButton());
		pnl.addLine("Output Copybook Type", writerOptions, goBtn);
		pnl.setGap(BasePanel.GAP3);

		pnl.addMessage(msgField);
		pnl.setHeight(BasePanel.GAP4);

		this.addMainComponent(pnl);
		this.setVisible(true);

		goBtn.addActionListener(listner);
		loaderOptions.addActionListener(listner);
	}

	private void copyLayout() {
	    CopybookLoaderFactory loaders = CopybookLoaderFactory.getInstance();
	    CopybookWriterManager writers = CopybookWriterManager.getInstance();
	    int loaderId = loaderOptions.getSelectedIndex();
	    int split = splitOptions.getSelectedValue();
		try {
			if (loaders.isBasedOnInputFile(loaderId)) {
				if ("".equals(sampleFile.getText())) {
					msgField.logMsg(AbsSSLogger.ERROR, UtMessages.SAMPLE_FILE_MSG.get());
					sampleFile.requestFocus();
				} else if ("".equals(layoutName.getText())) {
					msgField.logMsg(AbsSSLogger.ERROR, UtMessages.LAYOUT_MSG.get());
					layoutName.requestFocus();
				} else {
					boolean isCsv = loaders.isCsv(loaderId);
					int struc = Common.IO_XML_BUILD_LAYOUT;
					if (isCsv) {
						struc = Common.IO_NAME_1ST_LINE;
					}
					AbstractLayoutDetails dtl
							= new ReadLayout()
												.setLoadFromFile(true)
												.buildLayoutFromSample(
																	struc, isCsv,
																	fontName.getText(),
																	loaders.getFieldDelim(loaderId),
																	sampleFile.getText()
					);
					ExternalRecord rec = ToExternalRecord.getInstance()
							.getExternalRecord(dtl, layoutName.getText(), 0);
					rec.setDelimiter(loaders.getFieldDelim(loaderId));
			        writers.get(writerOptions.getSelectedIndex()).writeCopyBook(
			        		outputCopybookDir.getText(),
			        		rec,
			        		msgField);
				}
			} else if ("".equals(copybookFile.getText())) {
				msgField.logMsg(AbsSSLogger.ERROR, UtMessages.INPUT_COPYBOOK.get());
				copybookFile.requestFocus();
			} else {

		        ExternalRecord rec = loaders.getLoader(loaderId).loadCopyBook(
		        		copybookFile.getText(),
		                split,
		                0,
		                fontName.getText(),
		                binaryOptions.getSelectedIndex(),
		                0,
		                msgField);

		        rec.setFileStructure(((Integer) fileStructure.getSelectedItem()).intValue());

		        writers.get(writerOptions.getSelectedIndex()).writeCopyBook(
		        		outputCopybookDir.getText(), rec, msgField);

			}
		} catch (NoClassDefFoundError e) {
			e.printStackTrace();

		} catch (Exception e) {
			msgField.logMsg(AbsSSLogger.ERROR, UtMessages.ERROR_CONVERTING_COPYBOOK.get());
			msgField.logException(AbsSSLogger.ERROR, e);
			e.printStackTrace();
		}
	}
}
