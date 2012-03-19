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
package net.sf.RecordEditor.edit.display.SaveAs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.edit.display.common.AbstractFileDisplay;
import net.sf.RecordEditor.re.file.DisplayType;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.openFile.RecentFiles;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.FileChooser;


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
public final class SaveAs3 extends ReFrame
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
    	CommonSaveAsFields.FMT_DATA,  CommonSaveAsFields.FMT_HTML, CommonSaveAsFields.FMT_HTML,
    	CommonSaveAsFields.FMT_HTML,  CommonSaveAsFields.FMT_CSV,  CommonSaveAsFields.FMT_FIXED, 
    	CommonSaveAsFields.FMT_XML, CommonSaveAsFields.FMT_XSLT,   CommonSaveAsFields.FMT_VELOCITY};
    private static int[] FORMAT_HTML_TRANSLATION = {
    	-1, 
    	SaveAsPnlBase.SINGLE_TABLE, 
    	SaveAsPnlBase.TABLE_PER_ROW,
    	SaveAsPnlBase.TREE_TABLE
    };
    

    CommonSaveAsFields commonSaveFields;
    private SaveAsPnlBase[] pnls = {
    		new SaveAsPnlBasic.Data(commonSaveFields),
    		new SaveAsPnlCsv(commonSaveFields),
    		new SaveAsPnlFixed(commonSaveFields),
    		new SaveAsPnlBasic.Xml(commonSaveFields),
    		new SaveAsPnlHtml(commonSaveFields),
    		new SaveAsPnlXslt(commonSaveFields),
    		new SaveAsPnlVelocity(commonSaveFields) ,
    };
    //private String[] options;

    private BaseHelpPanel pnl = new BaseHelpPanel();
    private FileChooser fileNameTxt = new FileChooser(false);

    private JButton saveFile
    	= new JButton("save file", 
    			      Common.getRecordIcon(Common.ID_SAVE_ICON));
//    private JComboBox saveWhat   = new JComboBox();
//    
//    private JCheckBox treeExportChk    = new JCheckBox(),
//    		          nodesWithDataChk = new JCheckBox(),
//    		          keepOpenChk      = new JCheckBox(),
//    		          editChk          = new JCheckBox();
    
    private JTabbedPane formatTab = new JTabbedPane();

    private JTextArea msg = new JTextArea();


    private FileView<?> file;
    
    private int currentIndex;
    private String currentExtension = null;
    
    
   // private AbstractRecordDetail<?> printRecordDetails;
 
    ChangeListener actL = new ChangeListener() {
		
		@Override
		public void stateChanged(ChangeEvent e) {
			setVisibility();
		}
	};
    
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
    public SaveAs3(final AbstractFileDisplay recordFrame, final FileView<?> fileView) {
    	this(recordFrame, fileView, 0, "");
    }


	public SaveAs3(
    		final AbstractFileDisplay recordFrame, 
    		final FileView<?> fileView,
    		int formatIdx, String velocityTemplate) {
        super(fileView.getFileNameNoDirectory(), "Save as",
              fileView.getBaseFile());

        
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        commonSaveFields = new CommonSaveAsFields(recordFrame, fileView);
        file = fileView; 
        
        SaveAsPnlBase[] p = {
        		new SaveAsPnlBasic.Data(commonSaveFields),
        		new SaveAsPnlCsv(commonSaveFields),
        		new SaveAsPnlFixed(commonSaveFields),
        		new SaveAsPnlBasic.Xml(commonSaveFields),
        		new SaveAsPnlHtml(commonSaveFields),
        		new SaveAsPnlXslt(commonSaveFields),
        		new SaveAsPnlVelocity(commonSaveFields) ,
        };
        pnls = p;

        int len = init_100_setupFields_GetPnlLength(formatIdx);

        init_200_layoutScreen();
        //System.out.println(" !! create class 4 " + this.getClass().getName() + " " + this.isMaximum());
	
        init_300_SetupTabPnls(len, formatIdx, velocityTemplate);
        //System.out.println(" !! create class 5 " + this.getClass().getName() + " " + this.isMaximum());

        this.addMainComponent(pnl);
        //System.out.println(" !! create class 6 " + this.getClass().getName() + " " + this.isMaximum());
        this.setVisible(true);
        //System.out.println(" !! create class 7 " + this.getClass().getName() + " " + this.isMaximum());

        formatTab.addChangeListener(tabListner);
        pnl.addReKeyListener(listner);
        //System.out.println(" !! create class 8 " + this.getClass().getName() + " " + this.isMaximum());
        setToMaximum(false);
    }

    
    private int init_100_setupFields_GetPnlLength(int formatIdx) {
        int[] selected = commonSaveFields.getRecordFrame().getSelectedRows();
        int len = pnls.length - 1;
        String fname = file.getFileName();
        if ("".equals(fname)) {
        	fname = Common.OPTIONS.DEFAULT_FILE_DIRECTORY.get();
        }

        currentIndex = FORMAT_TRANSLATION[formatIdx];
       
        if (! Common.OPTIONS.XSLT_AVAILABLE.isSelected()) {
        	len -= 1;
        	pnls[pnls.length - 2] = pnls[pnls.length - 1];
        }
        if (Common.isVelocityAvailable()) {
        	len += 1;
        }

        if (file.isView()) {
            commonSaveFields.saveWhat.addItem(OPT_VIEW);
        }
        commonSaveFields.saveWhat.addItem(OPT_FILE);

        if (selected != null && selected.length > 0) {
        	commonSaveFields.saveWhat.addItem(OPT_SELECTED);
        }
 
        for (int i = 0; i < pnls.length; i++) {
        	pnls[i].onlyData.setSelected(! file.isBinaryFile());
        	pnls[i].showBorder.setSelected(true);
        }

        commonSaveFields.keepOpenChk.setSelected(false);
        commonSaveFields.editChk.setSelected(false);
        commonSaveFields.treeExportChk.setSelected(false);
        commonSaveFields.treeExportChk.setVisible(pnls[currentIndex].panelFormat == CommonSaveAsFields.FMT_CSV);
        commonSaveFields.nodesWithDataChk.setVisible(pnls[currentIndex].panelFormat == CommonSaveAsFields.FMT_CSV);
        commonSaveFields.nodesWithDataChk.setSelected(false);

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
        pnl.addLine("What to Save", commonSaveFields.saveWhat);
        
        if (commonSaveFields.getTreeFrame() != null) {
        	commonSaveFields.treeExportChk.setSelected(true);
        	commonSaveFields.nodesWithDataChk.setSelected(true);

        	
        	pnl.addLine("Export Tree", commonSaveFields.treeExportChk);
        	pnl.addLine("Only export Nodes with Data", commonSaveFields.nodesWithDataChk);
        	
        	if (commonSaveFields.saveWhat.getItemCount() > 1) {
        		commonSaveFields.saveWhat.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						setVisibility();
					}
				});
        	}
        }
        pnl.addLine("Edit Output File", commonSaveFields.editChk);

        pnl.setGap(BasePanel.GAP1);       
        pnl.addLine("Output Format:", null);
        pnl.addComponent(1, 5,BasePanel.FILL, BasePanel.GAP,
                BasePanel.FULL, BasePanel.FULL,
                formatTab);
        
        pnl.setGap(BasePanel.GAP1);
        pnl.addLine("Keep screen open", commonSaveFields.keepOpenChk, saveFile);
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
       		formatTab.add(pnls[i].getTitle(), pnls[i].panel);
       		
   			if (pnls[i].extensionType >= 0 && pnls[i].template != null) {
   				pnls[i].template.addFcFocusListener(templateChg);
   			} 

       		isFixed = false;
       		switch (pnls[i].panelFormat) {
       		case CommonSaveAsFields.FMT_HTML:
       			pnls[i].singleTable.addChangeListener(actL);
       			break;
       		case CommonSaveAsFields.FMT_FIXED:
      			isFixed = true;
       		case CommonSaveAsFields.FMT_CSV:
       			AbstractLayoutDetails<?,?> l = file.getLayout();
       			int layoutIdx = file.getCurrLayoutIdx();
       			int[] colLengths = null;
       			commonSaveFields.printRecordDetails = null;
        			
       			switch (DisplayType.displayType(l, commonSaveFields.getRecordFrame().getLayoutIndex())) {
   				case DisplayType.NORMAL:
   					commonSaveFields.printRecordDetails = l.getRecord(layoutIdx);
   					if (isFixed) {
   						colLengths = getFieldWidths();
   					}
   					break;
   				case DisplayType.PREFFERED:
   					commonSaveFields.printRecordDetails = l.getRecord(DisplayType.getRecordMaxFields(l));
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
       					commonSaveFields.printRecordDetails,
       					colLengths,
       					commonSaveFields.flatFileWriter.getFieldsToInclude()
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
        String outFile = fileNameTxt.getText();
       

        if (outFile.equals("")) {
            msg.setText("Please Enter a file name");
            return;
        }
    	SaveAsPnlBase activePnl =  getSelectedPnl();
        int dataFormat = activePnl.panelFormat;
        String selection = commonSaveFields.saveWhat.getSelectedItem().toString();
        String ext = "";

        try {
        	int lastDot = outFile.lastIndexOf('.');
        	ext = outFile.substring(lastDot);
        	if (activePnl.extensionType >= 0 && lastDot > 0) {
        		
        		RecentFiles.getLast().putFileExtension(
        				activePnl.extensionType, 
        				activePnl.template.getText(), 
        				ext,
        				activePnl.extension);
        	}
        	activePnl.save(selection, outFile);
       	
        	if (commonSaveFields.editChk.isSelected()) {
        		activePnl.edit(outFile, ext);
        	}
 
            if (! commonSaveFields.keepOpenChk.isSelected()) {
	            formatTab.removeChangeListener(tabListner);
	            this.setVisible(false);
	            this.doDefaultCloseAction();
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg.setText("Error: " + e.getMessage());
        }
    }



 
    
    private SaveAsPnlBase getSelectedPnl() {
    	int idx = formatTab.getSelectedIndex();
    	
    	return pnls[idx];
    }

 
    
    private void setVisibility() {
    	SaveAsPnlBase pnl = getSelectedPnl();
    	int pnlFormat = pnl.panelFormat;
    	boolean visible = (   pnlFormat == CommonSaveAsFields.FMT_CSV
    					   || pnlFormat == CommonSaveAsFields.FMT_FIXED
    					   || (   pnlFormat == CommonSaveAsFields.FMT_HTML
    					      &&  pnl.singleTable.isSelected())
    					   || (   pnlFormat == CommonSaveAsFields.FMT_XML
    					       && commonSaveFields.getTreeFrame() != null)
    					  ) 
    			       && (  commonSaveFields.saveWhat.getItemCount() == 1
    			          || OPT_VIEW.equals(commonSaveFields.saveWhat.getSelectedItem())
    			          || (OPT_FILE.equals(commonSaveFields.saveWhat.getSelectedItem()) && ! file.isView())
    			           );
    	
    	commonSaveFields.treeExportChk.setVisible(visible);
    	commonSaveFields.nodesWithDataChk.setVisible(visible &&  pnlFormat != CommonSaveAsFields.FMT_XML);
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
}
