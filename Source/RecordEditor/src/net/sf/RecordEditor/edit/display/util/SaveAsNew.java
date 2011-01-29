/*
 * @Author Bruce Martin
 * Created on 4/08/2005
 *
 * Purpose:
 *    Save the current file / selected portions of it in a
 * various formats
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *   - changed to use ReFrame (from JFrame) which keeps track
 *     of the active form
 *
 * # Version 0.60 Bruce Martin 2007/02/16
 *   - support for saving a file using a Velocity template
 *
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - Refactoring changes from moving classes to new packages
 *   
 * # Version 0.62 Bruce Martin 2007/05/01
 *   - Added <Enter> key support
 */
package net.sf.RecordEditor.edit.display.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sf.JRecord.Details.AbstractChildDetails;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.JRecord.Details.AbstractTreeDetails;
import net.sf.RecordEditor.edit.display.common.AbstractFileDisplay;
import net.sf.RecordEditor.edit.display.models.LineModel;
import net.sf.RecordEditor.edit.file.DisplayType;
import net.sf.RecordEditor.edit.file.FileView;
import net.sf.RecordEditor.edit.tree.ChildTreeToXml;
import net.sf.RecordEditor.edit.util.WriteLinesAsXml;
import net.sf.RecordEditor.utils.CsvWriter;
import net.sf.RecordEditor.utils.FieldWriter;
import net.sf.RecordEditor.utils.FixedWriter;
import net.sf.RecordEditor.utils.RunVelocity;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.FileChooser;
import net.sf.RecordEditor.utils.swing.TableModel2HTML;


/**
 * This class gives the user the option to
 * <ol compact>
 *   <li>Save the current file to a new file
 *   <li>Save the current view to a file
 *   <li>Save current selected records to a file
 * </ol>
 *
 * <p>The file can be save in the following formats
 * <ol compact> 
 *   <li>Data (ie same as the source file
 *   <li>HTML table (either Spreadsheet format or one record per
 *       table.
 *   <li>Tab/Comma delimiter file.
 * </ol>
 *
 * @author Bruce Martin
 * @version 0.55
 *
 */
@SuppressWarnings("serial")
public final class SaveAsNew extends ReFrame
				 implements ActionListener {

    //private static final int SAVE_FILE      = 0;
    //private static final int SAVE_VIEW      = 1;
    //private static final int SAVE_SELECTED  = 2;
    //private static final int FILENAME_FIELD_LENGTH = 30;

    //private static final String[] DELIMITER_OPTIONS = {"<TAB>", ","};
    //private static final String[] DELIMITER_LIST    = {"\t", ","};
    private static final String OPT_FILE = "File";
    private static final String OPT_VIEW = "Current View";
    private static final String OPT_SELECTED = "Selected Records";

    public static final int FORMAT_DATA      = 0;
    public static final int FORMAT_1_TABLE   = 1;
    public static final int FORMAT_MULTI_TABLE   = 2;
    public static final int FORMAT_TREE_HTML   = 3;
    public static final int FORMAT_DELIMITED = 4;
    public static final int FORMAT_FIXED = 5;
    public static final int FORMAT_XML = 6;
    public static final int FORMAT_VELOCITY = 7;
    
    private static int[] FORMAT_TRANSLATION = {0, 4, 4, 4, 1, 2, 3, 5};
    private static int[] FORMAT_HTML_TRANSLATION = {
    	-1, 
    	SaveAsPnl.SINGLE_TABLE, 
    	SaveAsPnl.TABLE_PER_ROW,
    	SaveAsPnl.TREE_TABLE
    };

    private SaveAsPnl[] pnls = {
    		new SaveAsPnl(SaveAsPnl.FMT_DATA),
    		new SaveAsPnl(SaveAsPnl.FMT_CSV),
    		new SaveAsPnl(SaveAsPnl.FMT_FIXED),
    		new SaveAsPnl(SaveAsPnl.FMT_XML),
    		new SaveAsPnl(SaveAsPnl.FMT_HTML),
    		new SaveAsPnl(SaveAsPnl.FMT_VELOCITY),
    };
    //private String[] options;

    private BaseHelpPanel pnl = new BaseHelpPanel();
    private FileChooser fileNameTxt = new FileChooser(false);

    private JButton saveFile
    	= new JButton("save file", 
    			      Common.getRecordIcon(Common.ID_SAVE_ICON));
    private JComboBox saveWhat   = new JComboBox();
    
    private JTabbedPane formatTab = new JTabbedPane();

    private JTextArea msg = new JTextArea();

    private AbstractFileDisplay recFrame;
    private FileView<?> file;
    
    private int currentIndex;
    
    private SaveAsWrite.BaseWrite flatFileWriter;
    
    
    private ChangeListener tabListner = new ChangeListener() {

		@Override
		public void stateChanged(ChangeEvent e) {
	
			int idx = formatTab.getSelectedIndex();
			
			if (currentIndex != idx) {
				String s = fileNameTxt.getText();
				if (s.endsWith(pnls[currentIndex].extension)) {
					fileNameTxt.setText(
							s.substring(0, s.length() - pnls[currentIndex].extension.length())
						+	pnls[idx].extension
					);
				}
				
				currentIndex = idx;
			}
		}
    	
    };
    
    private KeyAdapter listner = new KeyAdapter() {
        /**
         * @see java.awt.event.KeyAdapter#keyReleased
         */
        public final void keyReleased(KeyEvent event) {
        	
        	if (event.getKeyCode() == KeyEvent.VK_ENTER) {
        		saveFile();
        	}
        }
    };


    /**
     * Display the Save As screen (and save the file if need be)
     *
     * @param recordFrame record frame being displayed
     * @param fileView current file view being displayed
     */
    public SaveAsNew(final AbstractFileDisplay recordFrame, final FileView<?> fileView) {
    	this(recordFrame, fileView, 0, "");
    }
    
    public SaveAsNew(
    		final AbstractFileDisplay recordFrame, 
    		final FileView<?> fileView,
    		int formatIdx, String velocityTemplate) {
        super(fileView.getFileNameNoDirectory(), "Save as",
              fileView.getBaseFile());

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        recFrame = recordFrame;
        file = fileView; 
        
        currentIndex = FORMAT_TRANSLATION[formatIdx];
        int len = pnls.length - 1;
        if (Common.isVelocityAvailable()) {
        	len += 1;
        }
        
        flatFileWriter = SaveAsWrite.getWriter(fileView, recordFrame);


        setupFields(currentIndex);

		pnl.addHelpBtn(Common.getHelpButton());

		pnl.setHelpURL(Common.formatHelpURL(Common.HELP_SAVE_AS));
        pnl.addLine("File Name", fileNameTxt, fileNameTxt.getChooseFileButton());
        pnl.addLine("What to Save", saveWhat);
        pnl.setGap(BasePanel.GAP1);
        
               
        pnl.addLine("Output Format:", null);
        pnl.addComponent(1, 5,BasePanel.FILL, BasePanel.GAP,
                BasePanel.FULL, BasePanel.FULL,
                formatTab);
        
        pnl.setGap(BasePanel.GAP1);
        pnl.addLine("", null, saveFile);
        pnl.setGap(BasePanel.GAP3);
        pnl.addMessage(new JScrollPane(msg));
        pnl.setHeight(BasePanel.GAP3);

        String f = file.getLayout().getFontName();
        if (f != null && f.toLowerCase().startsWith("utf")) {
        	for (int i = 0; i < len; i++) {
        		pnls[i].font.setText(f);
        	}
        }
 
        boolean isFixed;
       	for (int i = 0; i < len; i++) {
       		formatTab.add(pnls[i].getTitle(), pnls[i]);
       		
       		isFixed = false;
       		switch (pnls[i].panelFormat) {
       		case SaveAsPnl.FMT_FIXED:
      			isFixed = true;
       		case SaveAsPnl.FMT_CSV:
       			AbstractLayoutDetails<?,?> l = file.getLayout();
       			int layoutIdx = file.getCurrLayoutIdx();
       			AbstractRecordDetail<?> recDtls = null;
       			int[] colLengths = null;
        			
       			switch (DisplayType.displayType(l, recFrame.getLayoutIndex())) {
   				case DisplayType.NORMAL:
   					recDtls = l.getRecord(layoutIdx);
   					if (isFixed) {
   						colLengths = getFieldWidths();
   					}
   					break;
   				case DisplayType.PREFFERED:
  					recDtls = l.getRecord(DisplayType.getRecordMaxFields(l));
  					if (isFixed) {
  						colLengths = getFieldWidthsPrefered();
  					}
   					break;  				
   				case DisplayType.HEX_LINE:
  					colLengths = new int[1];
  					colLengths[0] = l.getMaximumRecordLength() * 2;
   					break;  				
       			}
  
       			pnls[i].setRecordDetails(
       					recDtls,
       					colLengths,
       					flatFileWriter.getFieldsToInclude()
       			);
       		}
       	}

		formatTab.setSelectedIndex(currentIndex);
		switch (formatIdx) {
		case FORMAT_1_TABLE:
		case FORMAT_MULTI_TABLE:
		case FORMAT_TREE_HTML:
			getSelectedPnl().setTableOption(FORMAT_HTML_TRANSLATION[formatIdx]);
			break;
		case FORMAT_VELOCITY:
			if (velocityTemplate != null && ! "".equals(velocityTemplate)) {
				getSelectedPnl().template.setText(velocityTemplate);
			}
			break;
		}
    	

        this.addMainComponent(pnl);
        this.setVisible(true);

        formatTab.addChangeListener(tabListner);
        pnl.addReKeyListener(listner);
    }

    
    private int[] getFieldWidths() {
		AbstractLayoutDetails<?,?> l = file.getLayout();
   		int layoutIdx = file.getCurrLayoutIdx();
   		int[] ret = new int[l.getRecord(layoutIdx).getFieldCount()];
   		int en = Math.min(3000, file.getRowCount());
   		
   		for (int i = 0; i < en; i++) {
   			calcColLengthsForLine(ret, file.getTempLine(i), l, layoutIdx);
   		}
   		
   		return ret;
    }
    
    
    private int[] getFieldWidthsPrefered() {
		AbstractLayoutDetails<?,?> l = file.getLayout();
   		int layoutIdx = DisplayType.getRecordMaxFields(l);
   		int[] ret = new int[l.getRecord(layoutIdx).getFieldCount()];
   		int en = Math.min(3000, file.getRowCount());
   		AbstractLine<?> line;
   		
   		for (int i = 0; i < en; i++) {
   			line = file.getTempLine(i);
   			calcColLengthsForLine(ret, line, l, line.getPreferredLayoutIdx());
   		}
   		
   		return ret;
    }
   
    private void calcColLengthsForLine(
    		int[] ret, AbstractLine<?> line, 
    		AbstractLayoutDetails<?,?> layout, int layoutIdx) {
    	Object o;
    	int colCount = layout.getRecord(layoutIdx).getFieldCount();
		for (int j = 0; j < colCount; j++) {
			o = line.getField(layoutIdx,  j);
			
			if (o != null) {
				ret[j] = Math.max(ret[j], o.toString().length());
			}
		}
    }
//    private int[] getFieldWidths() {
//		AbstractLayoutDetails l = file.getLayout();
//   		int layoutIdx = file.getCurrLayoutIdx();
//   		int colCount = file.getLayoutColumnCount(layoutIdx);
//   		int[] ret = new int[colCount];
//   		Object o;
//   		int en = Math.min(3000, file.getRowCount());
//   		
//   		for (int i = 0; i < en; i++) {
//   			for (int j = 0; j < colCount; j++) {
//   				o = file.getValueAt(i, j + FileView.SPECIAL_FIELDS_AT_START);
//   				
//   				if (j == 0) {
//   					o = o;
//   				}
//   				if (o != null) {
//   					ret[j] = Math.max(ret[j], o.toString().length());
//   				}
//   			}
//   		}
//   		
//   		return ret;
//    }

    /**
     * Setup screen fields
     *
     */
    private void setupFields(int currIdx) {
        int[] selected = recFrame.getSelectedRows();
        String fname = file.getFileName();
        if ("".equals(fname)) {
        	fname = Common.OPTIONS.DEFAULT_FILE_DIRECTORY.get();
        }

        if (file.isView()) {
            saveWhat.addItem(OPT_VIEW);
        }
        saveWhat.addItem(OPT_FILE);

        if (selected != null && selected.length > 0) {
            saveWhat.addItem(OPT_SELECTED);
        }
 
        for (int i = 0; i < pnls.length; i++) {
        	pnls[i].onlyData.setSelected(! file.isBinaryFile());
        	pnls[i].showBorder.setSelected(true);
        }

        fileNameTxt.setText(fname + pnls[currIdx].extension);


      //findFile.addActionListener(this);
        saveFile.addActionListener(this);
    }


    /**
     * @see java.awt.event.ActionListner#actionPerformed
     */
    public final void actionPerformed(ActionEvent event) {

    	
        saveFile();
        

    }

 
    /**
     * Saves the file
     */
    private void saveFile() {
    	SaveAsPnl activePnl =  getSelectedPnl();
        int dataFormat = activePnl.panelFormat;
        String selection = saveWhat.getSelectedItem().toString();
        String outFile = fileNameTxt.getText();

        if (outFile.equals("")) {
            msg.setText("Please Enter a file name");
            return;
        }

        try {
            if (dataFormat == SaveAsPnl.FMT_DATA   
            || (dataFormat == SaveAsPnl.FMT_XML && file.getLayout().isXml())) {
                if (OPT_VIEW.equals(selection)) {
                    file.writeFile(outFile);
                } else if (OPT_SELECTED.equals(selection)) {
                    file.writeLinesToFile(outFile, recFrame.getSelectedLines());
                } else  {
                    file.getBaseFile().writeFile(outFile);
                }
            } else if (dataFormat == SaveAsPnl.FMT_CSV) {
                saveFile_100_writeCsvFile(activePnl, selection, outFile);
            } else if (dataFormat == SaveAsPnl.FMT_FIXED) {
                saveFile_400_writeFixedFile(activePnl, selection, outFile);
            } else if (dataFormat == SaveAsPnl.FMT_XML) {
            	if (file.getLayout().hasChildren()) {
            		new ChildTreeToXml(outFile, saveFile_300_getLines(selection));
            	} else {
            		new WriteLinesAsXml(outFile, saveFile_300_getLines(selection));
            	}
            } else if (dataFormat == SaveAsPnl.FMT_VELOCITY) {
                saveFile_200_Velocity(activePnl, selection, outFile);
            } else {
                FileView<?> f = file;
                if (OPT_FILE.equals(selection)) {
                    f =  file.getBaseFile();
                }

                switch (activePnl.getTableOption()) {
                case SaveAsPnl.SINGLE_TABLE:
                    singleTableHtml(activePnl, selection, f);
                    break;
                case SaveAsPnl.TABLE_PER_ROW:
                    multiTableHtml(activePnl, selection, f);
                    break;
                case SaveAsPnl.TREE_TABLE:
                	treeTableHtml(activePnl, selection, f);
                }
            }

            formatTab.removeChangeListener(tabListner);
            this.setVisible(false);
            this.doDefaultCloseAction();
        } catch (Exception e) {
            e.printStackTrace();
            msg.setText("Error: " + e.getMessage());
        }
    }


    /**
     * Writes a TAB file
     *
     * @param selection source of the output
     * @param outFile output file name
     */
    private void saveFile_100_writeCsvFile(SaveAsPnl activePnl, String selection, String outFile) {
        String fieldSeperator = Common.FIELD_SEPARATOR_LIST1_VALUES[activePnl.delimiterCombo.getSelectedIndex()];
        String fontname = activePnl.font.getText();
        boolean addQuote = activePnl.quoteAllTextFields.isSelected();
        
        try {
	        CsvWriter writer
	        		= new CsvWriter(outFile, fieldSeperator, fontname, 
	        					activePnl.getQuote(), addQuote, 
	        					activePnl.getIncludeFields());
	
	        saveFile_910_writeFile(writer, activePnl, selection);
        } catch (IOException e) {
			Common.logMsg("Error writing CSV file", e);
			msg.setText(e.getMessage());
		}
    }


    /**
     * Generate a velocity skelton
     * @param selection what records to send to velocity
     * @param outFile output file
     *
     * @throws Exception any error that occurs
     */
    private void saveFile_200_Velocity(SaveAsPnl activePnl, String selection, String outFile)
    throws Exception {
        RunVelocity velocity = RunVelocity.getInstance();

        FileWriter w = new FileWriter(outFile);
        //System.out.println(Velocity.FILE_RESOURCE_LOADER_PATH);
        velocity.genSkel(activePnl.template.getText(), saveFile_300_getLines(selection), 
        		activePnl.onlyData.isSelected(), activePnl.showBorder.isSelected(),
        		recFrame.getLayoutIndex(),
        		file.getFileName(), outFile, w);
        w.close();
    }
    
    
    /**
     * Get the required lines as a ArrayList
     * @param selection  what records to select
     * @return requested lines
     */
    private List<AbstractLine> saveFile_300_getLines(String selection) {
    	
        List<AbstractLine> lines;

        if (OPT_SELECTED.equals(selection)) {
            lines = recFrame.getSelectedLines();
        } else {
            FileView process = file;
            if (OPT_FILE.equals(selection)) {
                process = file.getBaseFile();
            }
            lines = process.getLines();
        }
        
        return lines;
    }
    
    /**
     * Writes a TAB file
     *
     * @param selection source of the output
     * @param outFile output file name
     */
    private void saveFile_400_writeFixedFile(SaveAsPnl activePnl, String selection, String outFile) {
        String fieldSeperator = "";
        String fontname = activePnl.font.getText();

        if (activePnl.spaceBetweenFields.isSelected()) {
        	fieldSeperator = " ";
        }
        
        try {
	        FixedWriter writer
	        		= new FixedWriter(outFile, fieldSeperator, fontname, 
	        					activePnl.getFieldLengths(),
	        					activePnl.getIncludeFields());
	
	        saveFile_910_writeFile(writer, activePnl, selection);
        } catch (IOException e) {
			Common.logMsg("Error writing CSV file", e);
			msg.setText(e.getMessage());
		}
    }


    private void saveFile_910_writeFile(FieldWriter writer, SaveAsPnl activePnl, String selection) throws IOException{

        int whatToSave = SaveAsWrite.SAVE_SELECTED;
        if (OPT_FILE.equals(selection)) {
        	whatToSave = SaveAsWrite.SAVE_FILE;
        } else if (OPT_VIEW.equals(selection)) {
        	whatToSave = SaveAsWrite.SAVE_VIEW;
        } 
        
        this.flatFileWriter.writeFile(writer, activePnl.namesFirstLine.isSelected(), whatToSave);
   	}

	
    /**
     * Writes the file as a single HTML table
     *
     * @param selection what part of the file to write
     * @param currFile FileStorage to be written as HTML
     *
     * @throws IOException any io errors
     */
    private void singleTableHtml(
    		SaveAsPnl activePnl, 
    		String selection, 
    		FileView<?> currFile) throws IOException {

        FileWriter writer = new FileWriter(getHtmlName());
        TableModel2HTML htmlOut = new TableModel2HTML(writer,
                									  Common.COLUMN_LINE_SEP,
                									  currFile,
                									  activePnl.showBorder.isSelected());

        htmlOut.setFirstColumn(1);
        htmlOut.writeHtmlStart();

        if (OPT_SELECTED.equals(selection)) {
            htmlOut.writeTable(recFrame.getSelectedRows());
        } else {
            htmlOut.writeTable();
        }


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
    private void multiTableHtml(SaveAsPnl activePnl, String selection, FileView<?> currFile)
    				throws IOException {

        FileWriter writer = new FileWriter(getHtmlName());
        LineModel mdl = new LineModel(currFile);
        TableModel2HTML htmlOut = new TableModel2HTML(writer, "", mdl, activePnl.showBorder.isSelected());
        int i;
        
        if (activePnl.onlyData.isSelected()) {
        	mdl.setColumnCount(4);
        }


        htmlOut.writeHtmlStart();

        if (OPT_SELECTED.equals(selection)) {
            int[] rows = recFrame.getSelectedRows();
            List<AbstractLine> list = recFrame.getSelectedLines();
            for (i = 0; i < rows.length; i++) {
                mdl.setCurrentLine(list.get(i), Common.NULL_INTEGER);

                htmlOut.writeText("<p><b>Record " + (rows[i] + 1) + "</b></p>");
                htmlOut.writeTable();
            }
        } else {
            int count = currFile.getRowCount();
            for (i = 0; i < count; i++) {
                mdl.setCurrentLine(i, Common.NULL_INTEGER);

                htmlOut.writeText("<p><b>Record " + (i + 1) + "</b></p>");
                htmlOut.writeTable();
            }
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
    private void treeTableHtml(SaveAsPnl activePnl, String selection, FileView currFile)
    				throws IOException {

        FileWriter writer = new FileWriter(getHtmlName());
        LineModel mdl = new LineModel(currFile);
        TableModel2HTML htmlOut = new TableModel2HTML(writer, "", mdl, activePnl.showBorder.isSelected());
        int i;
        
        if (activePnl.onlyData.isSelected()) {
        	mdl.setColumnCount(4);
        }


        htmlOut.writeHtmlStart();

        if (OPT_SELECTED.equals(selection)) {
            int[] rows = recFrame.getSelectedRows();
            List<AbstractLine> list = recFrame.getSelectedLines();
           for (i = 0; i < rows.length; i++) {
            	writeTreeLineAsHtml(htmlOut, mdl, list.get(i), "", (rows[i] + 1) + "");
            	htmlOut.writeText("<hr>");
             }
        } else {
            int count = currFile.getRowCount();
            for (i = 0; i < count; i++) {
            	writeTreeLineAsHtml(htmlOut, mdl, currFile.getLine(i), "", (i + 1) + "");
            	htmlOut.writeText("<hr>");
            }
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
    
    private SaveAsPnl getSelectedPnl() {
    	int idx = formatTab.getSelectedIndex();
    	return pnls[idx];
    }

    /**
     * Get the HTML file name
     *
     * @return HTML file name
     */
    private String getHtmlName() {
        String s = fileNameTxt.getText();

        if (! s.toUpperCase().endsWith(".HTML")
        &&  ! s.toUpperCase().endsWith(".HTM")) {
            s += ".HTML";
        }

        return s;
    }
}
