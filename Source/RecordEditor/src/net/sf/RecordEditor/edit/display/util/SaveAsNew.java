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
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.TreeNode;

import net.sf.JRecord.Details.AbstractChildDetails;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.JRecord.Details.AbstractTreeDetails;
import net.sf.JRecord.Details.RecordFilter;
import net.sf.JRecord.External.ExternalField;
import net.sf.JRecord.Types.Type;
import net.sf.RecordEditor.edit.display.SaveAs.SaveAsWrite;
import net.sf.RecordEditor.edit.display.models.LineModel;
import net.sf.RecordEditor.edit.open.StartEditor;
import net.sf.RecordEditor.edit.util.StandardLayouts;
import net.sf.RecordEditor.edit.util.WriteLinesAsXml;
import net.sf.RecordEditor.re.file.AbstractLineNode;
import net.sf.RecordEditor.re.file.AbstractTreeFrame;
import net.sf.RecordEditor.re.file.DisplayType;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.fileWriter.CsvWriter;
import net.sf.RecordEditor.re.fileWriter.FieldWriter;
import net.sf.RecordEditor.re.fileWriter.FixedWriter;
import net.sf.RecordEditor.re.openFile.RecentFiles;
import net.sf.RecordEditor.re.script.AbstractFileDisplay;
import net.sf.RecordEditor.re.script.RunVelocity;
import net.sf.RecordEditor.re.tree.ChildTreeToXml;
import net.sf.RecordEditor.utils.common.Common;

import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.FileChooser;
import net.sf.RecordEditor.utils.swing.SwingUtils;
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
 *       table).
 *   <li>Tab/Comma delimiter file.
 * </ol>
 *
 * @author Bruce Martin
 * @version 0.55
 *
 */

//TODO Rewrite: Create separate classes for Tap_Panels and move Save Logic there
//TODO Rewrite: Create separate classes for Tap_Panels and move Save Logic there
//TODO Rewrite: Create separate classes for Tap_Panels and move Save Logic there

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
    public static final int FORMAT_XSLT = 7;
    public static final int FORMAT_VELOCITY = 8;

    private static int[] FORMAT_TRANSLATION = {
    	SaveAsPnl.FMT_DATA,  SaveAsPnl.FMT_HTML, SaveAsPnl.FMT_HTML, SaveAsPnl.FMT_HTML,
    	SaveAsPnl.FMT_CSV,  SaveAsPnl.FMT_FIXED,  SaveAsPnl.FMT_XML, SaveAsPnl.FMT_XSLT,
    	SaveAsPnl.FMT_VELOCITY};
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
    		new SaveAsPnl(SaveAsPnl.FMT_XSLT),
    		new SaveAsPnl(SaveAsPnl.FMT_VELOCITY) ,
    };
    //private String[] options;

    private BaseHelpPanel pnl = new BaseHelpPanel();
    private FileChooser fileNameTxt = new FileChooser(false);

    private JButton saveFile
    	= SwingUtils.newButton("Save File",
    			      Common.getRecordIcon(Common.ID_SAVE_ICON));
    private JComboBox saveWhat   = new JComboBox();

    private JCheckBox treeExportChk    = new JCheckBox(),
    		          nodesWithDataChk = new JCheckBox(),
    		          keepOpenChk      = new JCheckBox(),
    		          editChk          = new JCheckBox();

    private JTabbedPane formatTab = new JTabbedPane();

    private JTextArea msg = new JTextArea();

    private AbstractFileDisplay recFrame;
    @SuppressWarnings("rawtypes")
	private AbstractTreeFrame treeFrame = null;
    private FileView<?> file;

    private int currentIndex;
    private String currentExtension = null;

    private SaveAsWrite flatFileWriter;

    private AbstractRecordDetail<?> printRecordDetails;



    private ChangeListener tabListner = new ChangeListener() {

		@Override
		public void stateChanged(ChangeEvent e) {

			int idx = formatTab.getSelectedIndex();

			if (currentIndex != idx) {
				changeExtension();

				setVisibility();
			}
		}
    };



	private FocusListener templateChg = new FocusAdapter() {

		/* (non-Javadoc)
		 * @see java.awt.event.FocusAdapter#focusLost(java.awt.event.FocusEvent)
		 */
		@Override
		public void focusLost(FocusEvent e) {
			System.out.print("Focus Lost ...");
			changeExtension();
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
        super(fileView.getFileNameNoDirectory(), "Save As",
              fileView.getBaseFile());


        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        recFrame = recordFrame;
        file = fileView;

        int len = init_100_setupFields_GetPnlLength(formatIdx);

        init_200_layoutScreen();

        init_300_SetupTabPnls(len, formatIdx, velocityTemplate);

        this.addMainComponent(pnl);
        this.setVisible(true);

        formatTab.addChangeListener(tabListner);
        pnl.addReKeyListener(listner);
    }


    private int init_100_setupFields_GetPnlLength(int formatIdx) {
        int[] selected = recFrame.getSelectedRows();
        int len = pnls.length - 1;
        String fname = file.getFileName();
        if ("".equals(fname)) {
        	fname = Common.OPTIONS.DEFAULT_FILE_DIRECTORY.get();
        }

        if (recFrame instanceof AbstractTreeFrame) {
        	treeFrame = (AbstractTreeFrame) recFrame;
        }
        flatFileWriter = SaveAsWrite.getWriter(file, recFrame);

        currentIndex = FORMAT_TRANSLATION[formatIdx];

        if (! Common.OPTIONS.xsltAvailable.isSelected()) {
        	len -= 1;
        	pnls[pnls.length - 2] = pnls[pnls.length - 1];
        }
        if (Common.isVelocityAvailable()) {
        	len += 1;
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

        keepOpenChk.setSelected(false);
        editChk.setSelected(false);
        treeExportChk.setSelected(false);
        treeExportChk.setVisible(pnls[currentIndex].panelFormat == SaveAsPnl.FMT_CSV);
        nodesWithDataChk.setVisible(pnls[currentIndex].panelFormat == SaveAsPnl.FMT_CSV);
		nodesWithDataChk.setSelected(false);

        currentExtension =  getExtension(currentIndex);
        fileNameTxt.setText(fname + currentExtension);


      //findFile.addActionListener(this);
        saveFile.addActionListener(this);

        return len;
    }


    private void init_200_layoutScreen() {
		pnl.addHelpBtn(Common.getHelpButton());

		pnl.setHelpURL(Common.formatHelpURL(Common.HELP_SAVE_AS));
        pnl.addLine("File Name", fileNameTxt, fileNameTxt.getChooseFileButton());
        pnl.addLine("What to Save", saveWhat);

        if (recFrame instanceof AbstractTreeFrame) {
        	treeFrame = (AbstractTreeFrame) recFrame;

        	treeExportChk.setSelected(true);
    		nodesWithDataChk.setSelected(true);


        	pnl.addLine("Export Tree", treeExportChk);
        	pnl.addLine("Only export Nodes with Data", nodesWithDataChk);

        	if (saveWhat.getItemCount() > 1) {
        		saveWhat.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						setVisibility();
					}
				});
        	}
        }
        pnl.addLine("Edit Output File", editChk);

        pnl.setGap(BasePanel.GAP1);
        pnl.addLine("Output Format:", null);
        pnl.addComponent(1, 5,BasePanel.FILL, BasePanel.GAP,
                BasePanel.FULL, BasePanel.FULL,
                formatTab);

        pnl.setGap(BasePanel.GAP1);
        pnl.addLine("Keep screen open", keepOpenChk, saveFile);
        pnl.setGap(BasePanel.GAP3);
        pnl.addMessage(new JScrollPane(msg));
        pnl.setHeight(BasePanel.GAP3);

    }


    private void init_300_SetupTabPnls(int len, int formatIdx, String velocityTemplate) {
        String f = file.getLayout().getFontName();
        if (f != null && f.toLowerCase().startsWith("utf")) {
        	for (int i = 0; i < len; i++) {
        		pnls[i].font.setText(f);
        	}
        }

        boolean isFixed;
       	for (int i = 0; i < len; i++) {
       		formatTab.add(pnls[i].getTitle(), pnls[i]);

   			if (pnls[i].extensionType >= 0 && pnls[i].template != null) {
   				pnls[i].template.addFcFocusListener(templateChg);
   			}

       		isFixed = false;
       		switch (pnls[i].panelFormat) {
       		case SaveAsPnl.FMT_FIXED:
      			isFixed = true;
       		case SaveAsPnl.FMT_CSV:
       			AbstractLayoutDetails<?,?> l = file.getLayout();
       			int layoutIdx = file.getCurrLayoutIdx();
       			int[] colLengths = null;
       			printRecordDetails = null;

       			switch (DisplayType.displayTypePrint(l, recFrame.getLayoutIndex())) {
   				case DisplayType.NORMAL:
   					printRecordDetails = l.getRecord(layoutIdx);
   					if (isFixed) {
   						colLengths = getFieldWidths();
   					}
   					break;
   				case DisplayType.PREFFERED:
  					printRecordDetails = l.getRecord(DisplayType.getRecordMaxFields(l));
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
       					printRecordDetails,
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
		case FORMAT_XSLT:
		case FORMAT_VELOCITY:
			if (velocityTemplate != null && ! "".equals(velocityTemplate)) {
				getSelectedPnl().template.setText(velocityTemplate);
				changeExtension();
			}
			break;
		}


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
        String ext = "";


        if (outFile.equals("")) {
            msg.setText("Please Enter a file name");
            return;
        }

        try {
        	int lastDot = outFile.lastIndexOf('.');
        	if (activePnl.extensionType >= 0 && lastDot > 0) {
        		ext = outFile.substring(lastDot);

        		RecentFiles.getLast().putFileExtension(
        				activePnl.extensionType,
        				activePnl.template.getText(),
        				ext,
        				activePnl.extension);
        	}

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
            } else if (dataFormat == SaveAsPnl.FMT_XSLT) {
            	saveFile_500_Xslt(activePnl, selection, outFile);
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

            saveFile_600_Edit(activePnl, outFile, ext);

            if (! keepOpenChk.isSelected()) {
	            formatTab.removeChangeListener(tabListner);
	            this.setVisible(false);
	            this.doDefaultCloseAction();
            }
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
        String fieldSeperator = activePnl.delimiterCombo.getSelectedEnglish();
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
        AbstractLineNode root = null;
        List<List<TreeNode>> nodeList = new ArrayList<List<TreeNode>>();
        int treeDepth=0;
        List<List<AbstractLine>> lines = new ArrayList<List<AbstractLine>>();

        lines.add(saveFile_300_getLines(selection));
        lines.add(file.getBaseFile().getLines());
        lines.add(file.getLines());

        if (treeFrame != null) {
        	root = treeFrame.getRoot();
        	treeDepth = saveFile_210_checkLevels(nodeList, root, 0);
        }

//        velocity.genSkel(
//        		activePnl.template.getText(),
//        		new ScriptData( lines,
//				        		root,
//				        		nodeList,
//				        		treeDepth,
//				        		activePnl.onlyData.isSelected(), activePnl.showBorder.isSelected(),
//				        		recFrame.getLayoutIndex(),
//				        		file.getFileName(), outFile),
//        		w);
        w.close();
    }



	private int saveFile_210_checkLevels(List<List<TreeNode>> nodeList, AbstractLineNode node, int lvl) {
		int ret = lvl + 1;
		List<TreeNode> nodes = Arrays.asList(node.getPath());
		if (nodes==null) {
			nodes=new ArrayList<TreeNode>(1);
			nodes.add(node);
		}

		nodeList.add((nodes));
		for (int i = 0; i < node.getChildCount(); i++) {
			ret = Math.max(ret, saveFile_210_checkLevels(nodeList, (AbstractLineNode) node.getChildAt(i), lvl));
		}

		return ret;
	}

    /**
     * Get the required lines as a ArrayList
     * @param selection  what records to select
     * @return requested lines
     */
    private List<AbstractLine> saveFile_300_getLines(String selection) {
    	return flatFileWriter.getViewToSave(getWhatToSave(selection)).getLines();
//        List<AbstractLine> lines;
//
//        if (OPT_SELECTED.equals(selection)) {
//            lines = recFrame.getSelectedLines();
//        } else {
//            FileView process = file;
//            if (OPT_FILE.equals(selection)) {
//                process = file.getBaseFile();
//            }
//            lines = process.getLines();
//        }
//
//        return lines;
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

    private void saveFile_500_Xslt(SaveAsPnl activePnl, String selection, String outFile)
    	    throws Exception {

    	//TODO Xslt Processing
    	//TODO Xslt Processing
    	//TODO Xslt Processing
    	javax.xml.transform.Source xmlSource, xsltSource;
    	javax.xml.transform.Result result;
    	javax.xml.transform.TransformerFactory transFact;
    	javax.xml.transform.Transformer trans;

    	FileView view = flatFileWriter.getViewToSave(getWhatToSave(selection));
    	File tempFile = File.createTempFile("reXsltInput", ".xml");
    	String xsltFileName = activePnl.template.getText(),
    	       xsltClass = activePnl.xsltTxt.getText();


    	view.writeFile(tempFile.getCanonicalPath());

		xmlSource = new javax.xml.transform.stream.StreamSource(tempFile);
		xsltSource = new javax.xml.transform.stream.StreamSource(xsltFileName);
		result = new javax.xml.transform.stream.StreamResult(outFile);

		Common.OPTIONS.XSLT_ENGINE.set(xsltClass);
		if ("".equals(xsltClass)) {
			transFact = javax.xml.transform.TransformerFactory.newInstance();
		} else {
			if ("saxon".equalsIgnoreCase(xsltClass)) {
				xsltClass = "net.sf.saxon.TransformerFactoryImpl";
			} else if ("xalan".equalsIgnoreCase(xsltClass)) {
				xsltClass = "org.apache.xalan.processor.TransformerFactoryImpl";
			}

			transFact = javax.xml.transform.TransformerFactory
							 .newInstance(xsltClass, null);
		}

		trans = transFact.newTransformer(xsltSource);

		trans.transform(xmlSource, result);
    }

    @SuppressWarnings("rawtypes")
	private void saveFile_600_Edit(SaveAsPnl activePnl, String outFile, String ext) {

    	if (editChk.isSelected()) {
	    	int dataFormat = activePnl.panelFormat;
	    	String lcExt = ext.toLowerCase();
	    	AbstractLayoutDetails layout = null;
	    	StandardLayouts genLayout = StandardLayouts.getInstance();

	    	if (dataFormat == SaveAsPnl.FMT_XML || ".xml".equals(lcExt) || ".xsl".equals(lcExt)) {
	    		layout = genLayout.getXmlLayout();
	    	} else if (dataFormat == SaveAsPnl.FMT_DATA) {
	    		AbstractLayoutDetails tl = file.getLayout();
	    		ArrayList<RecordFilter> filter = new ArrayList<RecordFilter>(tl.getRecordCount());
	    		for (int i = 0; i < tl.getRecordCount(); i++) {
	    			filter.add(new RecFilter(tl.getRecord(i).getRecordName()));
	    		}

	    		layout = tl.getFilteredLayout(filter);
	        } else if (dataFormat == SaveAsPnl.FMT_CSV) {
	        	if (activePnl.namesFirstLine.isSelected()) {
	        	   layout = genLayout.getCsvLayoutNamesFirstLine(
	        			   			activePnl.delimiterCombo.getSelectedItem().toString(),
	        			   			activePnl.getQuote());
	        	} else {
	        		layout = saveFile_610_getCsvLayout(activePnl);
	        	}
	        } else if (dataFormat == SaveAsPnl.FMT_FIXED) {
	        	layout = saveFile_620_getFixedLayout(activePnl);
	        } else if (".csv".equals(lcExt)) {
	        	layout = genLayout.getGenericCsvLayout();
	        }

	    	if (layout != null) {
	    		FileView newFile = new FileView(layout,
	        			file.getIoProvider(),
	        			false);
	    		StartEditor startEditor = new StartEditor(newFile, outFile, false, msg ,0);

	    		startEditor.doEdit();

	    	}
    	}
    }

    private AbstractLayoutDetails saveFile_610_getCsvLayout(SaveAsPnl activePnl) {
    	AbstractLayoutDetails ret = null;

    	if (printRecordDetails != null) {
        	List<ExternalField> ef = new ArrayList<ExternalField>(printRecordDetails.getFieldCount());
        	int pos = 1;
	    	boolean[] include = activePnl.getIncludeFields();
	    	int levelCount = this.flatFileWriter.getLevelCount();

	    	for (int i = 0; i < levelCount; i++) {
	    		if (include == null || (include.length < i && include[i])) {
	    			ef.add(new ExternalField(
	    					pos++, Common.NULL_INTEGER,
	    					"Level_" + i,
	    					"", Type.ftChar, 0, 0, "", "", "", 0));
	    		}
	    	}

	    	for (int i = 0; i < printRecordDetails.getFieldCount(); i++) {
	    		if (include == null || (include.length < i && include[i])) {
	    			ef.add(new ExternalField(
	    					pos++, Common.NULL_INTEGER,
	    					printRecordDetails.getField(i).getName(),
	    					"", Type.ftChar, 0, 0, "", "", "", 0));
	    		}
	    	}

	    	ret = StandardLayouts.getInstance().getCsvLayout(
	    							ef,
	    							activePnl.delimiterCombo.getSelectedItem().toString(),
	        			   			activePnl.getQuote());
    	}
    	return ret;
    }


    private AbstractLayoutDetails saveFile_620_getFixedLayout(SaveAsPnl activePnl) {
    	AbstractLayoutDetails ret = null;

    	if (printRecordDetails != null) {
        	List<ExternalField> ef = new ArrayList<ExternalField>(printRecordDetails.getFieldCount());
	    	boolean[] include = activePnl.getIncludeFields();
	    	int[] lengths = activePnl.getFieldLengths();
	    	int pos = 1;
	    	int fieldCount = Math.min(lengths.length, printRecordDetails.getFieldCount());
	    	int sepLength = 0;
        	if (activePnl.spaceBetweenFields.isSelected()) {
        		sepLength = 1;
            }


	    	for (int i = 0; i < fieldCount; i++) {
	    		if (include == null || (include.length > i && include[i])) {
	    			ef.add(new ExternalField(
	    					pos, lengths[i],
	    					printRecordDetails.getField(i).getName(),
	    					"", Type.ftChar, 0, 0, "", "", "", 0));
	    			pos += sepLength + lengths[i];
	    		}
	    	}

	    	ret = StandardLayouts.getInstance().getFixedLayout(ef);
    	}
    	return ret;
    }

    private void saveFile_910_writeFile(FieldWriter writer, SaveAsPnl activePnl, String selection) throws IOException{

    	if (this.treeExportChk.isSelected()) {
        	this.flatFileWriter.writeTree(
        			writer, treeFrame.getRoot(),
        			activePnl.namesFirstLine.isSelected(),
        			! nodesWithDataChk.isSelected(),
        			recFrame.getLayoutIndex());
        } else {
        	this.flatFileWriter.writeFile(
        			writer, activePnl.namesFirstLine.isSelected(), getWhatToSave(selection));
        }
   	}

//    private FileView getViewToSave(String selection) {
//    	FileView ret = null;
//    	switch (getWhatToSave(selection)) {
//    	case SaveAsWrite.SAVE_SELECTED: ret = file.getView(recFrame.getSelectedRows()); break;
//    	case SaveAsWrite.SAVE_FILE: ret = file.getBaseFile();
//    	case SaveAsWrite.SAVE_VIEW: ret = file;
//    	}
//
//    	return ret;
//   	}

    private int getWhatToSave(String selection) {
        int whatToSave = SaveAsWrite.SAVE_SELECTED;
        if (OPT_FILE.equals(selection)) {
        	whatToSave = SaveAsWrite.SAVE_FILE;
        } else if (OPT_VIEW.equals(selection)) {
        	whatToSave = SaveAsWrite.SAVE_VIEW;
        }
        return whatToSave;
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

    private void setVisibility() {
    	boolean visible = getSelectedPnl().panelFormat == SaveAsPnl.FMT_CSV
    			       && (  saveWhat.getItemCount() == 1
    			          || OPT_VIEW.equals(saveWhat.getSelectedItem())
    			          || (OPT_FILE.equals(saveWhat.getSelectedItem()) && ! file.isView())
    			           );

    	treeExportChk.setVisible(visible);
		nodesWithDataChk.setVisible(visible);
    }

    private void changeExtension() {
		String s = fileNameTxt.getText();
		int idx = formatTab.getSelectedIndex();
		String newExtension = getExtension(idx);

//		System.out.println("Extension: " + idx
//				+ " " + currentExtension
//				+ " ~ " + newExtension
//				+ " " + s + " # " + s.endsWith(currentExtension));
		if (currentExtension != null
		&& s.endsWith(currentExtension)
		&&  ! currentExtension.equals(newExtension)) {
			fileNameTxt.setText(
					s.substring(0, s.length() - currentExtension.length())
				+	newExtension
			);
		}

		currentIndex = idx;
		currentExtension = newExtension;

    }

    private String getExtension(int idx) {
    	if (pnls[idx].template == null) {
    		return pnls[idx].extension;
    	}
    	return RecentFiles.getLast().getFileExtension(
    			pnls[idx].extensionType,
    			pnls[idx].template.getText(),
    			pnls[idx].extension);
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
