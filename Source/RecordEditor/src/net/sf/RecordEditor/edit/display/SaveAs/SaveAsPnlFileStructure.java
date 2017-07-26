/**
 *
 */
package net.sf.RecordEditor.edit.display.SaveAs;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.LineProvider;
import net.sf.JRecord.Details.Options;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.Details.RecordDetail.FieldDetails;
import net.sf.JRecord.IO.AbstractLineWriter;
import net.sf.JRecord.IO.LineIOProvider;
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
	
	final JComboBox  fileStructures; //= FileStructureDtls.getFileStructureCombo();
	final JLabel fontLbl = new JLabel("Font");
	final FontCombo fontCombo = new FontCombo();
	private final CommonSaveAsFields saveAsFields;
	
	private LayoutDetail newLayout;
	

	/**
	 * @param commonSaveAsFields common screen fields
	 */
	public SaveAsPnlFileStructure(CommonSaveAsFields commonSaveAsFields) {
		super(commonSaveAsFields, ".bin", CommonSaveAsFields.FMT_FILE_STRUCTURE, RecentFiles.RF_NONE, null, false);
		
		this.saveAsFields = commonSaveAsFields;
		
		AbstractLayoutDetails layout = saveAsFields.file.getLayout();
		fileStructures = FileStructureDtls.getFileStructureCombo(calcComboType(layout));

		JTextArea area = new JTextArea(panelDescription.get());

		panel	.addComponentRE(1, 3,BasePanel.FILL, BasePanel.GAP,
                	BasePanel.FULL, BasePanel.FULL,
                	area)
                			.setFieldsToActualSize()
                .addLineRE("new File Structure", fileStructures)
				.addLineRE(fontLbl, fontCombo)
							.setGapRE(BasePanel.GAP2);
		
		
		fileStructures.addActionListener(new ActionListener() {		
			@Override public void actionPerformed(ActionEvent e) {
				FileStructureDtls.FileStructureOption opt = getSelectFileStructure();
				if (opt != null && opt.extension != null) {
					saveAsFields.templateListner.focusLost(null);
				}
				setFontVisiblity();
			}
		});
		setFontVisiblity();
	}

	/**
	 * @param layout
	 * @return
	 */
	private FileStructureDtls.FileStructureReqest calcComboType(
			AbstractLayoutDetails layout) {
		FileStructureDtls.FileStructureReqest comboType = FileStructureDtls.FileStructureReqest.ALL;
		
		if (layout.getOption(Options.OPT_HAS_BINARY_FIELD) == Options.YES || layout.isBinCSV()) {
			comboType = FileStructureDtls.FileStructureReqest.BINARY_STRUCTRES;
		}
		return comboType;
	}
	
	/**
	 * 
	 */
	private void setFontVisiblity() {
		FileStructureDtls.FileStructureOption opt = getSelectFileStructure();
		boolean visible = false;
		
		if (opt != null) { 
			AbstractLayoutDetails layout = commonSaveAsFields.file.getLayout();
			fontCombo.setText(layout.getFontName());

			switch (opt.index) {
			case Constants.IO_UNICODE_TEXT:
			case Constants.IO_FIXED_LENGTH_CHAR:
				visible = true;
				break;
			case Constants.IO_VB:
			case Constants.IO_VB_FUJITSU:
			case Constants.IO_VB_GNU_COBOL:
			case Constants.IO_TEXT_LINE:
			case Constants.IO_BIN_TEXT:
			case Constants.IO_FIXED_LENGTH:
				visible =  layout.getOption(Options.OPT_HAS_BINARY_FIELD) == Options.NO
						|| layout.getOption(Options.OPT_HAS_REDEFINE) == Options.NO;
			}
		}
		fontLbl.setVisible(visible);
		fontCombo.setVisible(visible);
	}




	@SuppressWarnings("incomplete-switch")
	protected void save(String selection, OutputStream outStream) throws Exception {
		FileStructureDtls.FileStructureOption opt = getSelectFileStructure();
		newLayout = null;
		if (opt != null) {
			FileView view = commonSaveAsFields.getViewToSave(selection);

			if (view == null) {
			} else {
				int rowCount = view.getRowCount();
				String font;
				AbstractLayoutDetails layout = view.getLayout();
				if (fontCombo.isVisible()
				&& ! (font = fontCombo.getText())
							.equals(commonSaveAsFields.file.getLayout().getFontName())) {
					ICharWriter cw;
					if (layout.getOption(Options.OPT_HAS_BINARY_FIELD) == Options.NO
					&& ( cw = getCharWritter(font, layout)) != null) {
						if (cw != null) {
							cw.open(outStream);
							for (int i = 0; i < rowCount; i++) {
								cw.write(view.getTempLine(i).getFullLine());
							}
							cw.close();
							createLayout(opt, font, layout);
							return;
						}
					} else {
						writeBinFileNewFont(opt, outStream, view, rowCount, font, layout);
						return;
					}
				}
				if (opt.index == Constants.IO_FIXED_LENGTH) {
					saveFixed(outStream, view);
				} else {
					AbstractLineWriter w = ReIOProvider.getInstance().getLineWriter(opt.index, fontCombo.getText());
	
					w.open(outStream);
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
					newLayout = newStdLayout(layout);
				}
			}
		}
	}

	/**
	 * @param outStream
	 * @param view
	 * @param rowCount
	 * @param font
	 * @param layout
	 * @throws IOException
	 */
	private void writeBinFileNewFont(FileStructureDtls.FileStructureOption opt,
			OutputStream outStream, FileView view,
			int rowCount, String font, AbstractLayoutDetails layout)
			throws IOException {
		try {
			
			createLayout(opt, font, layout);
			
			LineIOProvider ioProvider = LineIOProvider.getInstance();
			AbstractLineWriter lw = ioProvider.getLineWriter(newLayout.getFileStructure(), font);
			LineProvider lineProvider = ioProvider.getLineProvider(newLayout);
			
			lw.open(outStream);
			if (layout.getOption(Options.OPT_HAS_REDEFINE) == Options.NO) {
				writeLinesFieldCopy(view, rowCount, lw, lineProvider);
			} else {
				writeTextLines(view, rowCount, lw, lineProvider);
			}
			
			lw.close();
		} catch (Exception e) {
			Common.logMsg("Error Printing File: " + e, null);
			e.printStackTrace();
		}
	}

	/**
	 * @param view
	 * @param rowCount
	 * @param lw
	 * @param lineProvider
	 * @throws IOException
	 */
	private void writeLinesFieldCopy(FileView view, int rowCount,
			AbstractLineWriter lw, LineProvider lineProvider)
			throws IOException {
		for (int i = 0; i < rowCount; i++) {
			AbstractLine line = lineProvider.getLine(newLayout);
			AbstractLine tempLine = view.getTempLine(i);
			int recordIdx = Math.min(0, tempLine.getPreferredLayoutIdx());
			AbstractRecordDetail record = newLayout.getRecord(recordIdx);
			for (int fldNum = 0; fldNum < record.getFieldCount(); fldNum++) {
				line.setField(record.getField(fldNum), tempLine.getField(recordIdx, fldNum));
			}
			
			lw.write(line);
		}
	}

	private void writeTextLines(FileView view, int rowCount,
			AbstractLineWriter lw, LineProvider lineProvider)
			throws IOException {
		for (int i = 0; i < rowCount; i++) {
			AbstractLine line = lineProvider.getLine(newLayout);
			AbstractLine tempLine = view.getTempLine(i);
			line.setData(tempLine.getFullLine());
			
			lw.write(line);
		}
	}

	/**
	 * @param opt
	 * @param font
	 * @param layout
	 */
	private void createLayout(FileStructureDtls.FileStructureOption opt,
			String font, AbstractLayoutDetails layout) {
		RecordDetail[] records = new RecordDetail[layout.getRecordCount()];
		for (int i = 0; i < records.length; i++) {
			AbstractRecordDetail fromRecord = layout.getRecord(i);
			int childId = 0;
			if (fromRecord instanceof RecordDetail) {
				childId = ((RecordDetail) fromRecord).getChildId();
			}
			FieldDetails[] flds = new  FieldDetails[fromRecord.getFieldCount()];
			for (int j = 0; j < flds.length; j++) {
				net.sf.JRecord.Details.AbstractRecordDetail.FieldDetails fromField = fromRecord.getField(j);
				flds[j] = new FieldDetails(
						fromField.getName(), fromField.getDescription(), fromField.getType(), fromField.getDecimal(),
						font, fromField.getFormat(), fromField.getParamater());
				if (fromField.isFixedFormat()) {
					flds[j].setPosLen(fromField.getPos(), fromField.getLen());
				} else {
					flds[j].setPosOnly(fromField.getPos());
				}
			}
			records[i] = new RecordDetail(
					fromRecord.getRecordName(), fromRecord.getRecordType(),
					fromRecord.getDelimiterDetails().jrDefinition(), fromRecord.getQuoteDefinition().jrDefinition(), 
					font, flds, 
					fromRecord.getRecordStyle(), childId, false);
		}
		newLayout = new LayoutDetail(
				layout.getLayoutName(), records, layout.getDescription(), layout.getLayoutType(), 
				layout.getRecordSep(), null, font, null, opt.index);
	}

	/**
	 * @param font
	 * @param layout
	 * @return
	 */
	private ICharWriter getCharWritter(String font, AbstractLayoutDetails layout) {
		ICharWriter cw = null;
		switch (layout.getFileStructure()) {
		case Constants.IO_FIXED_LENGTH:
		case Constants.IO_FIXED_LENGTH_CHAR:
			cw = CharIOProvider.INSTANCE.getWriter(
					Constants.IO_FIXED_LENGTH_CHAR, font, Constants.LINE_SEPERATOR, layout.getMaximumRecordLength());
		case Constants.IO_BIN_TEXT:
		case Constants.IO_TEXT_LINE:
		case Constants.IO_UNICODE_TEXT:
			cw = CharIOProvider.INSTANCE.getWriter(
					Constants.IO_UNICODE_TEXT, font, Constants.LINE_SEPERATOR, layout.getMaximumRecordLength());
		}
		return cw;
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
			w.write(view.getTempLine(i));
		}
		w.close();
	}


	/**
	 * @return
	 */
	public FileStructureOption getSelectFileStructure() {
		return (FileStructureDtls.FileStructureOption) fileStructures.getSelectedItem();
	}


	private void saveFixed(OutputStream outFile, FileView view) throws IOException {
		BufferedOutputStream outStream = new BufferedOutputStream(outFile , 8192);
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
				if (l.length >= len) {
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

		return newLayout;
	}
	
	private LayoutDetail newStdLayout(AbstractLayoutDetails l) {
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
