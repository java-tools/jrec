/**
 * 
 */
package net.sf.RecordEditor.edit.display.SaveAs;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import net.sf.JRecord.Details.AbstractChildDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.AbstractTreeDetails;
import net.sf.RecordEditor.edit.display.models.LineModel;
import net.sf.RecordEditor.edit.display.util.SaveAsPnl;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.openFile.RecentFiles;
import net.sf.RecordEditor.utils.HtmlWriter;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.TableModel2HTML;

/**
 * @author mum
 *
 */
public class SaveAsPnlHtml extends SaveAsPnlBase {

	private String outfile;
	/**
	 * @param extension
	 * @param panelFormat
	 * @param extensionType
	 * @param template
	 */
	public SaveAsPnlHtml(CommonSaveAsFields commonSaveAsFields) {
		super(commonSaveAsFields, ".html", CommonSaveAsFields.FMT_HTML, RecentFiles.RF_NONE, null);
		
		panel.addLine("Table Type:", singleTable);
		panel.addLine("", tablePerRow);
		panel.addLine("", treeTable);
		panel.setGap(BasePanel.GAP0);
		addHtmlFields();
		
		namesFirstLine.setSelected(true);
	}
	
	public void save(String selection, String outFile)  throws Exception  {
        FileView<?> f = commonSaveAsFields.getViewToSave(selection);
   
        outfile = outFile;
        
        try {
	        switch (getTableOption()) {
	        case SaveAsPnl.SINGLE_TABLE:
	        	HtmlWriter w = new HtmlWriter(outFile, showBorder.isSelected());
	        	save_writeFile(w, selection);
	            break;
	        case SaveAsPnl.TABLE_PER_ROW:
	            multiTableHtml(selection, f);
	            break;
	        case SaveAsPnl.TREE_TABLE:
	        	treeTableHtml(selection, f);
	        }
        } catch (IOException e) {
			e.printStackTrace();
			Common.logMsg("WritingHTML File failed: " + e.getMessage(), null);
		}
	}
	


    /**
     * Writes the file as one record per HTML table
     *
     * @param selection what part of the file to write
     * @param currFile file storage
     *
     * @throws IOException any io errors
     */
    private void multiTableHtml(String selection, FileView<?> currFile)
    				throws IOException {

        FileWriter writer = new FileWriter(getHtmlName());
        LineModel mdl = new LineModel(currFile);
        TableModel2HTML htmlOut = new TableModel2HTML(writer, "", mdl, showBorder.isSelected());
        int i;
        
        if (onlyData.isSelected()) {
        	mdl.setColumnCount(4);
        }


        htmlOut.writeHtmlStart();


        int count = currFile.getRowCount();
        for (i = 0; i < count; i++) {
            mdl.setCurrentLine(i, Common.NULL_INTEGER);

            htmlOut.writeText("<p><b>Record " + (i + 1) + "</b></p>");
            htmlOut.writeTable();
        }
        
        //System.out.println();


        htmlOut.writeHtmlEnd();
    }

    /**
     * Writes the file as one record per HTML table
     *
     * @param selection what part of the file to write
     * @param currFile file storage
     *
     * @throws IOException any io errors
     */
    private void treeTableHtml(String selection, FileView currFile)
    				throws IOException {

        FileWriter writer = new FileWriter(getHtmlName());
        LineModel mdl = new LineModel(currFile);
        TableModel2HTML htmlOut = new TableModel2HTML(writer, "", mdl, showBorder.isSelected());
        int i;
        
        if (onlyData.isSelected()) {
        	mdl.setColumnCount(4);
        }


        htmlOut.writeHtmlStart();


        int count = currFile.getRowCount();
        for (i = 0; i < count; i++) {
        	writeTreeLineAsHtml(htmlOut, mdl, currFile.getLine(i), "", (i + 1) + "");
        	htmlOut.writeText("<hr>");
        }
        
 
        htmlOut.writeHtmlEnd();
    }

    private void writeTreeLineAsHtml(TableModel2HTML htmlOut, LineModel mdl, AbstractLine line, 
    		String indent, String id) 
    throws IOException {
        mdl.setCurrentLine(line, Common.NULL_INTEGER);

        htmlOut.writeText("<table><tr><td>" + indent + "</td><td><p><b>Record " + id + "</b></p>");
        htmlOut.writeTable();
        
        AbstractTreeDetails tree = line.getTreeDetails();
        AbstractChildDetails childDef;
        List<? extends AbstractLine> list;
        int j;
        
        for (int i =0; i < tree.getChildCount(); i++) {
        	childDef = tree.getChildDetails(i);
        	list = tree.getLines(i);
        	
        	if (list.size() == 1) {
        		writeTreeLineAsHtml(htmlOut, mdl, list.get(0), "", childDef.getName());
        	} else {
	        	for (j = 0; j < list.size(); j++) {
	        		writeTreeLineAsHtml(htmlOut, mdl, list.get(j), "&nbsp;&nbsp;&nbsp;&nbsp;", childDef.getName()+"."+(j+1));
	        	}
        	}
        }
   	
        
        htmlOut.writeText("</td></tr></table>");
    }
    
    
    /**
     * Get the HTML file name
     *
     * @return HTML file name
     */
    private String getHtmlName() {
        String s = outfile;

        if (! s.toUpperCase().endsWith(".HTML")
        &&  ! s.toUpperCase().endsWith(".HTM")) {
            s += ".HTML";
        }

        return s;
    }
    
//	@Override
//	public void edit(String outFile, String ext) {
//		// TODO Auto-generated method stub
//
//	}
}
