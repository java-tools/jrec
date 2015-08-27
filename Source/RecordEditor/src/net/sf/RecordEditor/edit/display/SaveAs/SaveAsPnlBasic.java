package net.sf.RecordEditor.edit.display.SaveAs;

import java.util.ArrayList;

import javax.swing.JTextArea;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.RecordFilter;
import net.sf.RecordEditor.edit.util.ReMessages;
import net.sf.RecordEditor.edit.util.StandardLayouts;
import net.sf.RecordEditor.edit.util.WriteLinesAsXml;
import net.sf.RecordEditor.re.openFile.RecentFiles;
import net.sf.RecordEditor.re.tree.ChildTreeToXml;
import net.sf.RecordEditor.re.tree.TreeToXml;
import net.sf.RecordEditor.utils.swing.BasePanel;


public class SaveAsPnlBasic extends SaveAsPnlBase {

	public SaveAsPnlBasic(CommonSaveAsFields commonSaveAsFields, int panelFormat, String extension, String description) {
		super(commonSaveAsFields, extension, panelFormat, RecentFiles.RF_NONE, null);
		JTextArea area = new JTextArea(description);

		panel.addComponentRE(1, 5,BasePanel.FILL, BasePanel.GAP,
                BasePanel.FULL, BasePanel.FULL,
                area);	}

	@Override
	public void save(String selection, String outFile) throws Exception {

        if (CommonSaveAsFields.OPT_VIEW.equals(selection)) {
        	commonSaveAsFields.file.writeFile(outFile);
        } else if (CommonSaveAsFields.OPT_SELECTED.equals(selection)) {
        	commonSaveAsFields.file.writeLinesToFile(outFile, commonSaveAsFields.getRecordFrame().getSelectedLines());
        } else  {
        	commonSaveAsFields.file.getBaseFile().writeFile(outFile);
        }
	}





	public static class Data extends SaveAsPnlBasic {

		public Data(CommonSaveAsFields commonSaveAsFields) {
			super(commonSaveAsFields, CommonSaveAsFields.FMT_DATA, "$",
					ReMessages.EXPORT_DATA_DESC.get());
//				LangConversion.convertId(
//						LangConversion.ST_MESSAGE, "ExportDataDesc",
//						"Export data in native format\n\nChange the tab to change Data format"));
		}

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.edit.display.SaveAs.SaveAsPnlBase#getEditLayout()
		 */
		@Override
		public AbstractLayoutDetails getEditLayout(String ext) {

    		AbstractLayoutDetails tl = commonSaveAsFields.file.getLayout();
    		if (tl.isXml()) {
    			return StandardLayouts.getInstance().getXmlLayout();
    		}
    		ArrayList<RecordFilter> filter = new ArrayList<RecordFilter>(tl.getRecordCount());
    		for (int i = 0; i < tl.getRecordCount(); i++) {
    			filter.add(new RecFilter(tl.getRecord(i).getRecordName()));
    		}

    		return tl.getFilteredLayout(filter);
		}


	}


	public static class Xml extends SaveAsPnlBasic {

		public Xml(CommonSaveAsFields commonSaveAsFields) {
			super(commonSaveAsFields, CommonSaveAsFields.FMT_XML, ".xml",
					ReMessages.EXPORT_XML_DESC.get());
//					LangConversion.convertId(
//							LangConversion.ST_MESSAGE, "ExportXmlDesc",  "Export data as an XML file"));
		}


		@Override
		public void save(String selection, String outFile) throws Exception {
			if (commonSaveAsFields.file.getLayout().isXml()) {
				super.save(selection, outFile);
			} else if (commonSaveAsFields.file.getLayout().hasChildren()) {
            	new ChildTreeToXml(outFile, saveFile_getLines(selection));
			} else if (commonSaveAsFields.getTreeFrame() != null
				   && commonSaveAsFields.treeExportChk.isSelected()
				   && commonSaveAsFields.treeExportChk.isVisible()) {
				new TreeToXml(outFile, commonSaveAsFields.getTreeFrame().getRoot());
           	} else {
            	new WriteLinesAsXml(outFile, saveFile_getLines(selection));

			}
		}


		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.edit.display.SaveAs.SaveAsPnlBase#getEditLayout(java.lang.String)
		 */
		@Override
		public AbstractLayoutDetails getEditLayout(String ext) {
			return StandardLayouts.getInstance().getXmlLayout();
		}
	}

    private static class RecFilter implements RecordFilter {

    	private String name;


		public RecFilter(String name) {
			super();
			this.name = name;
		}

		/* (non-Javadoc)
		 * @see net.sf.JRecord.Details.RecordFilter#getRecordName()
		 */
		@Override
		public String getRecordName() {
			return name;
		}

		/* (non-Javadoc)
		 * @see net.sf.JRecord.Details.RecordFilter#getFields()
		 */
		@Override
		public String[] getFields() {
			return null;
		}
    }
}
