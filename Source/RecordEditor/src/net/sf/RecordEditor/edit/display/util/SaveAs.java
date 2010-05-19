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
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.sf.JRecord.Details.AbstractChildDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.AbstractTreeDetails;
import net.sf.RecordEditor.edit.display.common.AbstractFileDisplay;
import net.sf.RecordEditor.edit.display.models.LineModel;
import net.sf.RecordEditor.edit.file.FileView;
import net.sf.RecordEditor.edit.tree.ChildTreeToXml;
import net.sf.RecordEditor.edit.util.WriteLinesAsXml;
import net.sf.RecordEditor.utils.RunVelocity;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.FileChooser;
import net.sf.RecordEditor.utils.swing.TableModel2HTML;
import net.sf.RecordEditor.utils.swing.Utils;


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
public final class SaveAs extends ReFrame
				 implements ActionListener {

    //private static final int SAVE_FILE      = 0;
    //private static final int SAVE_VIEW      = 1;
    //private static final int SAVE_SELECTED  = 2;
    //private static final int FILENAME_FIELD_LENGTH = 30;

    private static final String[] DELIMITER_OPTIONS = {"<TAB>", ","};
    private static final String[] DELIMITER_LIST    = {"\t", ","};
    private static final String OPT_FILE = "File";
    private static final String OPT_VIEW = "Current View";
    private static final String OPT_SELECTED = "Selected Records";

    public static final int FORMAT_DATA      = 0;
    public static final int FORMAT_1_TABLE   = 1;
    public static final int FORMAT_MULTI_TABLE   = 2;
    public static final int FORMAT_TREE_HTML   = 3;
    public static final int FORMAT_DELIMITED = 4;
    public static final int FORMAT_XML = 5;
    public static final int FORMAT_VELOCITY = 6;
    public static final String[] DATA_FORMAT = {
            "Data",
            "HTML: 1 Table", "HTML: 1 Row per Table", "HTML: Tree",
            "Delimited", "XML"};
    private static final String[] DATA_FORMAT_VELOCITY = {
            DATA_FORMAT[0], DATA_FORMAT[1], DATA_FORMAT[2], DATA_FORMAT[3], DATA_FORMAT[4], DATA_FORMAT[5],
            "Velocity"
    };

    //private String[] options;

    private BaseHelpPanel pnl = new BaseHelpPanel();
    private FileChooser fileName = new FileChooser();
    //private JButton findFile = new JButton("Find file");
    private JButton saveFile
    	= new JButton("save file", Common.getRecordIcon(Common.ID_SAVE_ICON));
    private JComboBox saveWhat   = new JComboBox();
    private JComboBox format; // = new JComboBox(DATA_FORMAT);
    private JComboBox delimiter  = new JComboBox(DELIMITER_OPTIONS);
    private JTextField font = new JTextField();
    private JCheckBox onlyData   = new JCheckBox();
    private JCheckBox showBorder = new JCheckBox();
    private FileChooser template = null;

    private JTextArea msg = new JTextArea();

    private AbstractFileDisplay recFrame;
    private FileView file;
    
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
     * @param recordFrame record frame beinfg displayed
     * @param fileView current file view being displayed
     */
    public SaveAs(final AbstractFileDisplay recordFrame, final FileView fileView) {
    	this(recordFrame, fileView, 0, "");
    }
    
    public SaveAs(final AbstractFileDisplay recordFrame, final FileView fileView,
    		int formatIdx, String velocityTemplate) {
        super(fileView.getFileNameNoDirectory(), "Save as",
              fileView.getBaseFile());

        recFrame = recordFrame;
        file = fileView; 

        setupFields(formatIdx, velocityTemplate);

		pnl.addHelpBtn(Common.getHelpButton());

		pnl.setHelpURL(Common.formatHelpURL(Common.HELP_SAVE_AS));
        pnl.addComponent("File Name", fileName, fileName.getChooseFileButton());
        pnl.addComponent("What to Save", saveWhat);
        pnl.setGap(BasePanel.GAP1);
        pnl.addComponent("Output Format", format);
        pnl.setGap(BasePanel.GAP1);
        pnl.addComponent("Only Data Column", onlyData);
        pnl.addComponent("Show Table Border", showBorder);
        pnl.setGap(BasePanel.GAP1);
        pnl.addComponent("Delimiter", delimiter);
        pnl.addComponent("Font", font);
        pnl.setGap(BasePanel.GAP1);
        if (Common.isVelocityAvailable()) {
            pnl.addComponent("Velocity Template", template, template.getChooseFileButton());
            pnl.setGap(BasePanel.GAP1);
        }
        pnl.addComponent("", null, saveFile);
        pnl.setGap(BasePanel.GAP3);
        pnl.addMessage(new JScrollPane(msg));
        pnl.setHeight(BasePanel.GAP4 * 2);

        String f = file.getLayout().getFontName();
        if (f != null && f.toLowerCase().startsWith("utf")) {
        	font.setText(f);
        }
        this.addMainComponent(pnl);
        this.setVisible(true);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }


    /**
     * Setup screen fields
     *
     */
    private void setupFields(int formatIdx, String velocityTemplate) {
        int[] selected = recFrame.getSelectedRows();

        pnl.addReKeyListener(listner);
        if (file.isView()) {
            saveWhat.addItem(OPT_VIEW);
        }
        saveWhat.addItem(OPT_FILE);

        if (selected != null && selected.length > 0) {
            saveWhat.addItem(OPT_SELECTED);
        }

        if (Common.isVelocityAvailable()) {
            format = new JComboBox(DATA_FORMAT_VELOCITY);
            template = new FileChooser("get Velocity Template");
            
            if (velocityTemplate == null || "".equals(velocityTemplate)) {
            	template.setText(Common.DEFAULT_VELOCITY_DIRECTORY);
            } else {
            	template.setText(velocityTemplate);
            }
            
            template.setEnabled(false);
        } else {
            format = new JComboBox(DATA_FORMAT);
        }
        try {  format.setSelectedIndex(formatIdx); } catch (Exception e) { System.out.println("Idx : " + formatIdx);	}

        onlyData.setSelected(! file.isBinaryFile());
        showBorder.setSelected(true);
//        delimiter.setEnabled(false);
//       onlyData.setEnabled(false);
//        showBorder.setEnabled(false);
        setOptions();

        fileName.setText(file.getFileName() + "$");

        format.addActionListener(this);
        //findFile.addActionListener(this);
        saveFile.addActionListener(this);
    }


    /**
     * @see java.awt.event.ActionListner#actionPerformed
     */
    public final void actionPerformed(ActionEvent event) {

    	if (event.getSource() == format) {
    		setOptions();
        } else {
            saveFile();
        }

    }

    /**
     * Set the enabled status of screen fields
     */
    private void setOptions() {

    	boolean allowOnlyData = format.getSelectedIndex() == FORMAT_MULTI_TABLE
    	|| format.getSelectedIndex() == FORMAT_VELOCITY;

    	if (template != null) {
    		template.setEnabled(format.getSelectedIndex() == FORMAT_VELOCITY);
    	}
    	delimiter.setEnabled(format.getSelectedIndex() == FORMAT_DELIMITED);
    	font.setEnabled(format.getSelectedIndex() == FORMAT_DELIMITED);
    	onlyData.setEnabled(allowOnlyData);
    	showBorder.setEnabled(allowOnlyData
    			|| format.getSelectedIndex() == FORMAT_1_TABLE);
   }

    /**
     * Saves the file
     */
    private void saveFile() {
        int dataFormat = format.getSelectedIndex();
        String selection = saveWhat.getSelectedItem().toString();
        String outFile = fileName.getText();

        if (outFile.equals("")) {
            msg.setText("Please Enter a file name");
            return;
        }

        try {

            if (dataFormat == FORMAT_DATA 
            || (dataFormat == FORMAT_XML && file.getLayout().isXml())) {
                if (OPT_VIEW.equals(selection)) {
                    file.writeFile(outFile);
                } else if (OPT_SELECTED.equals(selection)) {
                    file.writeLinesToFile(outFile, recFrame.getSelectedLines());
                } else  {
                    file.getBaseFile().writeFile(outFile);
                }
            } else if (dataFormat == FORMAT_DELIMITED) {
                saveFile_100_writeTabFile(selection, outFile);
            } else if (dataFormat == FORMAT_XML) {
            	if (file.getLayout().hasChildren()) {
            		new ChildTreeToXml(outFile, saveFile_300_getLines(selection));
            	} else {
            		new WriteLinesAsXml(outFile, saveFile_300_getLines(selection));
            	}
            } else if (dataFormat == FORMAT_VELOCITY) {
                saveFile_200_Velocity(selection, outFile);
            } else {
                FileView f = file;
                if (OPT_FILE.equals(selection)) {
                    f =  file.getBaseFile();
                }

                if (dataFormat == FORMAT_1_TABLE) {
                    singleTableHtml(selection, f);
                } else if (dataFormat == FORMAT_MULTI_TABLE) {
                    multiTableHtml(selection, f);
                } else  {
                	treeTableHtml(selection, f);
                }
            }
            this.setVisible(false);
        } catch (Exception e) {
            //e.printStackTrace();
            msg.setText(e.getMessage());
        }
    }


    /**
     * Writes a TAB file
     *
     * @param selection source of the output
     * @param outFile output file name
     */
    private void saveFile_100_writeTabFile(String selection, String outFile) {
        String fieldSeperator = DELIMITER_LIST[delimiter.getSelectedIndex()];
        String fontname = font.getText();

        if (OPT_FILE.equals(selection)) {
            Utils.writeTable(outFile, file.getBaseFile(), fieldSeperator, fontname);
        } else  {
            try {
                int i;
                BufferedWriter writer;
                OutputStreamWriter tw;
                FileOutputStream  outStream = new FileOutputStream(outFile);
                
                 
                if (fontname == null || "".equals(fontname)) {
                	tw = new OutputStreamWriter(outStream);
                } else {
                	try {
                		tw = new OutputStreamWriter(outStream, fontname);
                	} catch (Exception e) {
                		tw = new OutputStreamWriter(outStream);
					}
                }
                
                writer = new BufferedWriter(tw);

                if (OPT_VIEW.equals(selection)) {
                    for (i = 0; i < file.getRowCount(); i++) {
                        Utils.writeRow(writer, file, i, fieldSeperator);
                    }
                } else {
                    int[] rows = recFrame.getSelectedRows();

                    for (i = 0; i < rows.length; i++) {
                        Utils.writeRow(writer, file, rows[i], fieldSeperator);
                    }
                }

                writer.close();
            } catch (Exception e) {
                //e.printStackTrace();
                msg.setText(e.getMessage());
            }
        }
    }


    /**
     * Generate a velocity skelton
     * @param selection what records to send to velocity
     * @param outFile output file
     *
     * @throws Exception any error that occurs
     */
    private void saveFile_200_Velocity(String selection, String outFile)
    throws Exception {
        RunVelocity velocity = RunVelocity.getInstance();

        FileWriter w = new FileWriter(outFile);
        //System.out.println(Velocity.FILE_RESOURCE_LOADER_PATH);
        velocity.genSkel(template.getText(), saveFile_300_getLines(selection), 
        		onlyData.isSelected(), showBorder.isSelected(),
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
     * Writes the file as a single HTML table
     *
     * @param selection what part of the file to write
     * @param currFile FileStorage to be written as HTML
     *
     * @throws IOException any io errors
     */
    private void singleTableHtml(String selection, FileView currFile)
    				throws IOException {

        FileWriter writer = new FileWriter(getHtmlName());
        TableModel2HTML htmlOut = new TableModel2HTML(writer,
                									  Common.COLUMN_LINE_SEP,
                									  currFile,
                									  showBorder.isSelected());

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
    private void multiTableHtml(String selection, FileView currFile)
    				throws IOException {

        FileWriter writer = new FileWriter(getHtmlName());
        LineModel mdl = new LineModel(currFile);
        TableModel2HTML htmlOut = new TableModel2HTML(writer, "", mdl, showBorder.isSelected());
        int i;
        
        if (onlyData.isSelected()) {
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

    /**
     * Get the HTML file name
     *
     * @return HTML file name
     */
    private String getHtmlName() {
        String s = fileName.getText();

        if (! s.toUpperCase().endsWith(".HTML")
        &&  ! s.toUpperCase().endsWith(".HTM")) {
            s += ".HTML";
        }

        return s;
    }
}
