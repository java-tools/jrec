/**
 *
 */
package net.sf.RecordEditor.edit.display.SaveAs;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.Options;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.IO.AbstractLineWriter;
import net.sf.JRecord.charIO.CharIOProvider;
import net.sf.JRecord.charIO.ICharWriter;
import net.sf.RecordEditor.edit.util.ReMessages;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.openFile.RecentFiles;
import net.sf.RecordEditor.re.util.FileStructureDtls;
import net.sf.RecordEditor.re.util.FileStructureDtls.FileStructureOption;
import net.sf.RecordEditor.re.util.ReIOProvider;
import net.sf.RecordEditor.utils.charsets.FontCombo;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.fileStorage.IDataStore;
import net.sf.RecordEditor.utils.lang.ReOptionDialog;
import net.sf.RecordEditor.utils.params.ProgramOptions.ProgramType;
import net.sf.RecordEditor.utils.screenManager.ReMsgId;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.Combo.ComboOption;


/**
 * @author Bruce Martin
 *
 */
public class SaveAsPnlFileStructure extends SaveAsPnlBase {

	private static  ReMsgId panelDescription = ReMessages.EXPORT_FILE_STRUCTURE;
	
	private JComboBox  fileStructures = FileStructureDtls.getFileStructureCombo();
	private JLabel fontLbl = new JLabel("Font");
	private FontCombo fontCombo = new FontCombo();
	private final CommonSaveAsFields saveAsFields;
	

	/**
	 * @param commonSaveAsFields common screen fields
	 */
	public SaveAsPnlFileStructure(CommonSaveAsFields commonSaveAsFields) {
		super(commonSaveAsFields, ".bin", CommonSaveAsFields.FMT_FILE_STRUCTURE, RecentFiles.RF_NONE, null);
		
		this.saveAsFields = commonSaveAsFields;

		JTextArea area = new JTextArea(panelDescription.get());

		panel	.addComponentRE(1, 3,BasePanel.FILL, BasePanel.GAP,
                	BasePanel.FULL, BasePanel.FULL,
                	area)
                			.setFieldsToActualSize()
                .addLineRE("new File Structure", fileStructures)
				.addLineRE(fontLbl, fontCombo)
							.setGapRE(BasePanel.GAP2);
		
		for (int i = fileStructures.getItemCount() - 1; i >= 0; i--) {
			if (((FileStructureDtls.FileStructureOption) fileStructures.getItemAt(i)).extension != null) {
				fileStructures.addActionListener(new ActionListener() {
					@Override public void actionPerformed(ActionEvent e) {
						FileStructureDtls.FileStructureOption opt = getSelectFileStructure();
						if (opt != null && opt.extension != null) {
							saveAsFields.templateListner.focusLost(null);
						}
					}
				});
				break;
			}
		}
		
		fileStructures.addActionListener(new ActionListener() {		
			@Override public void actionPerformed(ActionEvent e) {
				setFontVisiblity();
			}

		});
		setFontVisiblity();
	}
	
	/**
	 * 
	 */
	private void setFontVisiblity() {
		FileStructureDtls.FileStructureOption opt = getSelectFileStructure();
		boolean visible = opt != null && opt.index == Constants.IO_UNICODE_TEXT;
		fontLbl.setVisible(visible);
		fontCombo.setVisible(visible);
	}


	@SuppressWarnings("incomplete-switch")
	public void save(String selection, String outFile) throws Exception {

		FileStructureDtls.FileStructureOption opt = getSelectFileStructure();

		if (opt != null) {
			FileView view = commonSaveAsFields.getViewToSave(selection);

			if (view == null) {
			} else if (opt.index == Constants.IO_FIXED_LENGTH) {
				saveFixed(outFile, view);
			} else {
				int rowCount = view.getRowCount();
				String font;
				if (fontCombo.isVisible()
				&& ! (font = fontCombo.getText())
							.equals(commonSaveAsFields.file.getLayout().getFontName())) {
					
					AbstractLayoutDetails layout = view.getLayout();
					List<AbstractLine> lines = null; 

					if (layout.getOption(Options.OPT_HAS_BINARY_FIELD) == Options.NO) {
						ICharWriter cw = null;
						switch (layout.getFileStructure()) {
						case Constants.IO_FIXED_LENGTH:
						case Constants.IO_FIXED_LENGTH_CHAR:
							cw = CharIOProvider.INSTANCE.getWriter(
									Constants.IO_FIXED_LENGTH_CHAR, font, Constants.FILE_SEPERATOR, layout.getMaximumRecordLength());
						case Constants.IO_BIN_TEXT:
						case Constants.IO_TEXT_LINE:
						case Constants.IO_UNICODE_TEXT:
							cw = CharIOProvider.INSTANCE.getWriter(
									Constants.IO_UNICODE_TEXT, font, Constants.FILE_SEPERATOR, layout.getMaximumRecordLength());
						}
						lines = view.getLines();
						if (cw != null && lines != null) {
							cw.open(outFile);
							for (int i = 0; i < rowCount; i++) {
								cw.write(lines.get(i).getFullLine());
							}
							cw.close();
							return;
						}
					} else if (layout instanceof LayoutDetail) {
						LayoutDetail l = new LayoutDetail(
								layout.getLayoutName(),((LayoutDetail) layout).getRecords(), layout.getDescription(), layout.getLayoutType(), 
								layout.getRecordSep(), null, font, null, layout.getFileStructure());//, recordLength)
						
						long bytes = rowCount * layout.getMaximumRecordLength();
						IDataStore<AbstractLine> ds = view.newDataStore(rowCount > 2000, rowCount, bytes, null, null);
						
						for (int i = 0; i < rowCount; i++) {
							ds.add(view.getTempLine(i).getNewDataLine());
						}
						
						ds.setLayoutRE(l);
						lines = ds;
					}
//					BufferedWriter w = new BufferedWriter(
//							new OutputStreamWriter(new FileOutputStream(outFile), font));
//					for (int i = 0; i < rowCount; i++) {
//						w.write(view.getLine(i).getFullLine());
//						w.newLine();
//					}
//					w.close();
				}// else {
					AbstractLineWriter w = ReIOProvider.getInstance().getLineWriter(opt.index, fontCombo.getText());
	
					w.open(outFile);
					switch (opt.rowsAllowed) {
					case ONE_ROW:
						rowCount = 1;
						break;
					case ONE_ROW_USED:
						if (rowCount > 1) {
							ReOptionDialog.showMessageDialog(
									this.panel, "All records will be written, but this results in an invalid file");
						}
					}
					writeFile(view, w, rowCount);
				//}
			}
		}
	}
	

	/**
	 * @param view
	 * @param w
	 * @param rowCount
	 * @throws IOException
	 */
	private void writeFile(FileView view, AbstractLineWriter w, int rowCount)
			throws IOException {
		for (int i = 0; i < rowCount; i++) {
			w.write(view.getLine(i));
		}
		w.close();
	}


	/**
	 * @return
	 */
	public FileStructureOption getSelectFileStructure() {
		return (FileStructureDtls.FileStructureOption) fileStructures.getSelectedItem();
	}


	private void saveFixed(String outFile, FileView view) throws IOException {
		BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(outFile) , 8192);
		AbstractLayoutDetails layout = view.getLayout();
		int len = layout.getMaximumRecordLength();
		int j;
		byte[] data = new byte[len];
		byte[] l;
		byte fill = 0;
		if (! layout.isBinary()) {
			fill = Conversion.getBytes(" ", layout.getFontName())[0];
		}


		try {
			for (int i = 0; i < view.getRowCount(); i++) {
				l = view.getLine(i).getData();
				if (l.length == len) {
					outStream.write(l);
				} else if (l.length == len) {
					outStream.write(l, 0, len);
				} else {
					for (j = l.length; j < len; j++) {
						data[j] = fill;
					}
					System.arraycopy(l, 0, data, 0, l.length-1);
					outStream.write(data);
				}
			}
		} finally {
			outStream.close();
		}
	}





	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.SaveAs.SaveAsPnlBase#getEditLayout(java.lang.String)
	 */
	@Override
	public AbstractLayoutDetails getEditLayout(String ext) {
		AbstractLayoutDetails l = commonSaveAsFields.file.getLayout();
		ComboOption opt = (ComboOption) fileStructures.getSelectedItem();


		if (opt != null && l instanceof LayoutDetail) {
			LayoutDetail layout = (LayoutDetail) l;
			RecordDetail[] recs = new RecordDetail[layout.getRecordCount()];

			for (int i = 0; i < recs.length; i++) {
				recs[i] = layout.getRecord(i);
			}

			return new LayoutDetail(l.getLayoutName(), recs, l.getDescription(),
					l.getLayoutType(), l.getRecordSep(), l.getEolString(),
					l.getFontName(), l.getDecider(), opt.index);
		}
		return null;
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.SaveAs.SaveAsPnlBase#getExtension()
	 */
	@Override
	public String getExtension() {
		FileStructureDtls.FileStructureOption opt = getSelectFileStructure();
		if (opt != null && opt.extension != null) {
			return opt.extension;
		}
		return super.getExtension();
	}


	/**
	 * @see net.sf.RecordEditor.edit.display.SaveAs.SaveAsPnlBase#isActive()
	 */
	@Override
	public boolean isActive() {
		try {
			int fileStructure = commonSaveAsFields.file.getLayout().getFileStructure();

			return (Common.OPTIONS.standardEditor.isSelected()
					|| Common.OPTIONS.programType == ProgramType.PROTOBUF_EDITOR)
				&& fileStructure != Constants.IO_UNICODE_NAME_1ST_LINE;
				//&& fileStructure != Constants.IO_UNICODE_TEXT;
		} catch (Exception e) {
			return false;
		}
	}


	/**
	 * @param panelDescription the panelDescription to set
	 */
	public static final void setPanelDescription(ReMsgId panelDescription) {
		SaveAsPnlFileStructure.panelDescription = panelDescription;
	}



}
