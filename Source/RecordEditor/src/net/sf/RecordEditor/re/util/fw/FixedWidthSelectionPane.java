package net.sf.RecordEditor.re.util.fw;

import java.io.File;

import javax.swing.JButton;

import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.base.ExternalConversion;
import net.sf.RecordEditor.re.util.BuildTypeComboList;
import net.sf.RecordEditor.re.util.csv.FilePreview;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.AbsRowList;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboItem;


/**
 * This class lets users define basic Fixed Width files
 * 
 * @author Bruce Martin
 *
 */
public class FixedWidthSelectionPane implements FilePreview {

	private static final String FIXED_ID = "FW";

	private static final TreeComboItem[] TEXT_TYPE_COMBO;
	private static final TreeComboItem[] ALL_TYPE_COMBO;
	static {
		 AbsRowList rl = new AbsRowList(0, 1, false, false).loadData(ExternalConversion.getTypes(0));
		 TEXT_TYPE_COMBO = BuildTypeComboList.getTextTypes(rl);
		 ALL_TYPE_COMBO = BuildTypeComboList.getList(rl);
	}
	private final JButton editBtn;
	private final IFixedWidthView fileView;
	
	private final IUpdateableFileSummaryModel fileSummaryMdl;
	
	private String filename;
	
	/**
	 * This method creates a class that lets users define basic Fixed Width files
	 * @return
	 */
	public static FixedWidthSelectionPane newPane() {
		IUpdateableFileSummaryModel mdl = new UpdateableFileSummayModel();
		JButton editBtn = SwingUtils.newButton("Edit");
		return new FixedWidthSelectionPane(
				FixedWidthSelectionView.newFWView(ALL_TYPE_COMBO, mdl, true, editBtn), 
				mdl, editBtn);
	}
	
	/**
	 * This class lets users define basic Fixed Width files
	 * @param fileDisplay
	 * @param fileSummaryMdl
	 * @param editBtn
	 */
	public FixedWidthSelectionPane(IFixedWidthView fileDisplay, IUpdateableFileSummaryModel fileSummaryMdl, JButton editBtn) {
		super();
		this.fileView = fileDisplay;
		this.fileSummaryMdl = fileSummaryMdl;
		this.editBtn = editBtn;
	}

	@Override
	public BaseHelpPanel getPanel() {
		return fileView.getPanel();
	}

	@Override
	public JButton getGoButton() {
		return editBtn;
	}

	@Override
	public boolean setData(String filename, byte[] data, boolean checkCharset, String layoutId) {
		this.filename = filename;
		
		boolean ret = fileSummaryMdl.setData(filename, data, checkCharset);
		fileView.reloadFromFileModel();
		
		File f = new File(filename);
		if (f.exists()) {
			String fname = Common.OPTIONS.DEFAULT_COPYBOOK_DIRECTORY.getNoStar()
						 + f.getName() + ".xml";
			fileView.setSchemaFileName(fname);
		}
		return ret;
	}

	@Override
	public String getSeperator() {
		return null;
	}

	@Override
	public String getQuote() {
		return null;
	}

	@Override
	public boolean setLines(byte[][] newLines, String font, int numberOfLines) {
		return true;
	}

	@Override
	public void setLines(String[] newLines, String font, int numberOfLines) {

	}


	@Override
	public LayoutDetail getLayout(String font, byte[] recordSep) {
		String schemaName = fileView.getSchemaFileName();
		String name = filename;
		File f = new File(schemaName);
		if (f.exists()) {
			name = f.getName();
		}
		ExternalRecord rec = fileSummaryMdl.asExtenalRecord(name, fileView.getFont());
		
		if (rec != null) {
			return rec.asLayoutDetail();
		}
		return null;
	}

	@Override
	public String getFileDescription() {
		if (this.fileView.isSaveSchemaSelected()) {
			return SCHEMA_ID	+ SEP + fileView.getSchemaFileName()
					+ SEP + "0"
					+ SEP + fileView.getFont()
					+ SEP + NULL_STR;
		}
		return FIXED_ID	+ SEP + NULL_STR;
	}

	@Override
	public void setFileDescription(String val) {
		
	}

	@Override
	public String getFontName() {
		return fileView.getFont();
	}

	@Override
	public boolean isMyLayout(String layout, String filename, byte[] data) {
		boolean ret = fileSummaryMdl.setData(filename, data, true);
		
		this.filename = filename;
		
		if (ret) {
			fileView.reloadFromFileModel();
		}
		return ret;
	}
}
