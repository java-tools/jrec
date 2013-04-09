/**
 *
 */
package net.sf.RecordEditor.edit.display.SaveAs;


import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.RecordEditor.re.file.DisplayType;
import net.sf.RecordEditor.re.fileWriter.HtmlColors;
import net.sf.RecordEditor.re.fileWriter.HtmlMultiTableWriter;
import net.sf.RecordEditor.re.fileWriter.HtmlWriter;
import net.sf.RecordEditor.re.openFile.RecentFiles;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.BasePanel;


/**
 * @author Bruce Martin
 *
 */
public class SaveAsPnlHtml extends SaveAsPnlBase {

	private BasePanel htmlColorPnl = new BasePanel();
    private   final ButtonGroup  htmlColorGrp    = new ButtonGroup();
    protected final JRadioButton whiteHtmlBtn    = genHtmlColorBtn("White Background");
    protected final JRadioButton standardBtn     = genHtmlColorBtn("Standard Colors");
    protected final JRadioButton dullBtn         = genHtmlColorBtn("Dull colors");
    protected final JRadioButton colorfulHtmlBtn = genHtmlColorBtn("Colorfull Table");

    ChangeListener actL = new ChangeListener() {

		@Override
		public void stateChanged(ChangeEvent e) {
			commonSaveAsFields.setVisibility(panelFormat, singleTable.isSelected());
		}
	};




	/**
	 * @param commonSaveAsFields common screen fields
	 */
	public SaveAsPnlHtml(CommonSaveAsFields commonSaveAsFields) {
		super(commonSaveAsFields, ".html", CommonSaveAsFields.FMT_HTML, RecentFiles.RF_NONE, null);

		BasePanel pnl1 = new BasePanel();


		pnl1.addLine("Table Type:", singleTable);
		pnl1.addLine("", tablePerRow);
		pnl1.addLine("", treeTable);
		pnl1.setGap(BasePanel.GAP0);
		addHtmlFields(pnl1);

		htmlColorPnl.addLine("", whiteHtmlBtn);
		htmlColorPnl.addLine("", standardBtn);
		htmlColorPnl.addLine("", dullBtn);
		htmlColorPnl.addLine("", colorfulHtmlBtn);
		colorfulHtmlBtn.setSelected(true);

		pnl1.setBorder(BorderFactory.createLoweredBevelBorder());
		htmlColorPnl.setBorder(BorderFactory.createLoweredBevelBorder());

		panel.addComponent(1, 1, BasePanel.FILL, 0, BasePanel.FULL, BasePanel.FULL, pnl1);
		panel.addComponent(3, 3, BasePanel.FILL, 0, BasePanel.FULL, BasePanel.FULL, false, htmlColorPnl);
		//panel.addComponent(5, 5, BasePanel.FILL, 0, BasePanel.FULL, BasePanel.FULL, false, new JPanel());

		namesFirstLine.setSelected(true);

		singleTable.addChangeListener(actL);
		tablePerRow.addChangeListener(actL);
	}




	public void save(String selection, String outFile)  throws Exception  {
        HtmlColors colors = HtmlColors.STANDARD_COLORS;

        if (whiteHtmlBtn.isSelected()) {
        	colors = HtmlColors.BOORING_COLORS;
        } else if (colorfulHtmlBtn.isSelected()) {
        	colors = HtmlColors.BRIGHT_COLORS;
        } else if (dullBtn.isSelected()) {
        	colors = HtmlColors.MONEY_COLORS;
        }


        try {
	        switch (getTableOption()) {
	        case SaveAsPnlBase.SINGLE_TABLE:
	        	HtmlWriter w = new HtmlWriter(
	        			outFile, showBorder.isSelected(),
	        			colors, "File: " + commonSaveAsFields.file.getFileNameNoDirectory());
	        	save_writeFile(w, selection);
	            break;
	        case SaveAsPnlBase.TABLE_PER_ROW:
	        	multiTable(DisplayType.PREFFERED, outFile, selection, colors);
	           // multiTableHtml(selection, f);
	            break;
	        case SaveAsPnlBase.TREE_TABLE:
	        	multiTable(SaveAsWrite.HTML_TREE_PRINT, outFile, selection, colors);
//	        	treeTableHtml(selection, f);
	        }
        } catch (IOException e) {
			e.printStackTrace();
			Common.logMsg(AbsSSLogger.ERROR, "Writing HTML File failed:", e.getMessage(), null);
		}
	}


	private void multiTable(int writerId, String outFile, String selection, HtmlColors colors) throws IOException {

    	boolean showText = ! onlyData.isSelected();
    	HtmlMultiTableWriter w1 = new HtmlMultiTableWriter(
    			outFile, showBorder.isSelected(),
    			showText, showText && commonSaveAsFields.file.getLayout().isBinary(),
    			colors, "File: " + commonSaveAsFields.file.getFileNameNoDirectory());

    	(SaveAsWrite.getWriter(
    			writerId,
    			commonSaveAsFields.file,
    			commonSaveAsFields.getRecordFrame())
    	) 	.writeFile(w1, namesFirstLine.isSelected(), commonSaveAsFields.getWhatToSave(selection));
	}

	private JRadioButton genHtmlColorBtn(String s) {
    	return generateRadioButton(htmlColorGrp, s);
    }



//    /**
//     * Writes the file as one record per HTML table
//     *
//     * @param selection what part of the file to write
//     * @param currFile file storage
//     *
//     * @throws IOException any io errors
//     */
//    private void multiTableHtml(String selection, FileView currFile)
//    				throws IOException {
//
//        FileWriter writer = new FileWriter(getHtmlName());
//        LineModel mdl = new LineModel(currFile);
//        TableModel2HTML htmlOut = new TableModel2HTML(writer, "", mdl, showBorder.isSelected());
//        int i;
//
//        if (onlyData.isSelected()) {
//        	mdl.setColumnCount(4);
//        }
//
//
//        htmlOut.writeHtmlStart();
//
//
//        int count = currFile.getRowCount();
//        for (i = 0; i < count; i++) {
//            mdl.setCurrentLine(i, Common.NULL_INTEGER);
//
//            htmlOut.writeText("<p><b>Record " + (i + 1) + "</b></p>");
//            htmlOut.writeTable();
//        }
//
//        //System.out.println();
//
//
//        htmlOut.writeHtmlEnd();
//    }
//
//    /**
//     * Writes the file as one record per HTML table
//     *
//     * @param selection what part of the file to write
//     * @param currFile file storage
//     *
//     * @throws IOException any io errors
//     */
//    private void treeTableHtml(String selection, @SuppressWarnings("rawtypes") FileView currFile)
//    				throws IOException {
//
//        FileWriter writer = new FileWriter(getHtmlName());
//        LineModel mdl = new LineModel(currFile);
//        TableModel2HTML htmlOut = new TableModel2HTML(writer, "", mdl, showBorder.isSelected());
//        int i;
//
//        if (onlyData.isSelected()) {
//        	mdl.setColumnCount(4);
//        }
//
//
//        htmlOut.writeHtmlStart();
//
//
//        int count = currFile.getRowCount();
//        for (i = 0; i < count; i++) {
//        	writeTreeLineAsHtml(htmlOut, mdl, currFile.getLine(i), "", (i + 1) + "");
//        	htmlOut.writeText("<hr>");
//        }
//
//
//        htmlOut.writeHtmlEnd();
//    }
//
//    @SuppressWarnings("rawtypes")
//	private void writeTreeLineAsHtml(TableModel2HTML htmlOut, LineModel mdl, AbstractLine line,
//    		String indent, String id)
//    throws IOException {
//        mdl.setCurrentLine(line, Common.NULL_INTEGER);
//
//        htmlOut.writeText("<table><tr><td>" + indent + "</td><td><p><b>Record " + id + "</b></p>");
//        htmlOut.writeTable();
//
//        AbstractTreeDetails tree = line.getTreeDetails();
//        AbstractChildDetails childDef;
//        List<? extends AbstractLine> list;
//        int j;
//
//        for (int i =0; i < tree.getChildCount(); i++) {
//        	childDef = tree.getChildDetails(i);
//        	list = tree.getLines(i);
//
//        	if (list.size() == 1) {
//        		writeTreeLineAsHtml(htmlOut, mdl, list.get(0), "", childDef.getName());
//        	} else {
//	        	for (j = 0; j < list.size(); j++) {
//	        		writeTreeLineAsHtml(htmlOut, mdl, list.get(j), "&nbsp;&nbsp;&nbsp;&nbsp;", childDef.getName()+"."+(j+1));
//	        	}
//        	}
//        }
//
//
//        htmlOut.writeText("</td></tr></table>");
//    }
//
//
//    /**
//     * Get the HTML file name
//     *
//     * @return HTML file name
//     */
//    private String getHtmlName() {
//        String s = outfile;
//
//        if (! s.toUpperCase().endsWith(".HTML")
//        &&  ! s.toUpperCase().endsWith(".HTM")) {
//            s += ".HTML";
//        }
//
//        return s;
//    }

//	@Override
//	public void edit(String outFile, String ext) {
//		// TODO Auto-generated method stub
//
//	}
}
