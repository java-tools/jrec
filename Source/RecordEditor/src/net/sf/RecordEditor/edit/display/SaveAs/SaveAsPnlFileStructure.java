/**
 *
 */
package net.sf.RecordEditor.edit.display.SaveAs;


import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.JTextArea;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.IO.AbstractLineWriter;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.RecordEditor.edit.util.ReMessages;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.openFile.RecentFiles;
import net.sf.RecordEditor.re.util.FileStructureDtls;
import net.sf.RecordEditor.re.util.ReIOProvider;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.Combo.ComboOption;


/**
 * @author Bruce Martin
 *
 */
public class SaveAsPnlFileStructure extends SaveAsPnlBase {

	private JComboBox  fileStructures = FileStructureDtls.getFileStructureCombo();

	/**
	 * @param commonSaveAsFields common screen fields
	 */
	public SaveAsPnlFileStructure(CommonSaveAsFields commonSaveAsFields) {
		super(commonSaveAsFields, ".bin", CommonSaveAsFields.FMT_FILE_STRUCTURE, RecentFiles.RF_NONE, null);

		JTextArea area = new JTextArea(ReMessages.EXPORT_FILE_STRUCTURE.get());

		panel.addComponent(1, 3,BasePanel.FILL, BasePanel.GAP,
                BasePanel.FULL, BasePanel.FULL,
                area);

		panel.addLine("new File Structure", fileStructures);
		panel.setGap(BasePanel.GAP2);
	}


	public void save(String selection, String outFile) throws Exception {

		ComboOption opt = (ComboOption) fileStructures.getSelectedItem();

		if (opt != null) {

			FileView view = commonSaveAsFields.getViewToSave(selection);
			if (view == null) {
			} else if (opt.index == Constants.IO_FIXED_LENGTH) {
				saveFixed(outFile, view);
			} else {
				LineIOProvider p = ReIOProvider.getInstance();
				AbstractLineWriter w = p.getLineWriter(opt.index);

				w.open(outFile);

				for (int i = 0; i < view.getRowCount(); i++) {
					w.write(view.getLine(i));
				}
				w.close();
			}
		}
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


	/**
	 * @see net.sf.RecordEditor.edit.display.SaveAs.SaveAsPnlBase#isActive()
	 */
	@Override
	public boolean isActive() {
		try {
			int fileStructure = commonSaveAsFields.file.getLayout().getFileStructure();

			return Common.OPTIONS.standardEditor.isSelected()
				&& fileStructure != Constants.IO_UNICODE_NAME_1ST_LINE
				&& fileStructure != Constants.IO_UNICODE_TEXT;
		} catch (Exception e) {
			return false;
		}
	}



}
