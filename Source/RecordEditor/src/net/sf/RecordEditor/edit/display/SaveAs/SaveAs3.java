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

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.openFile.RecentFiles;
import net.sf.RecordEditor.re.script.AbstractFileDisplay;
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

@SuppressWarnings("serial")
public final class SaveAs3 extends ReFrame
				 implements ActionListener {

	//TODO
	//TODO Convert to enum !!!!!
	//TODO Convert to enum !!!!!
	// Include FORMAT_TRANSLATION, name and RecentFile lookup key
	//TODO
    public static final int FORMAT_DATA      = 0;
    public static final int FORMAT_1_TABLE   = 1;
    public static final int FORMAT_MULTI_TABLE   = 2;
    public static final int FORMAT_TREE_HTML   = 3;
    public static final int FORMAT_DELIMITED = 4;
    public static final int FORMAT_FIXED = 5;
    public static final int FORMAT_XML = 6;
    public static final int FORMAT_SCRIPT = 7;
    public static final int FORMAT_XSLT = 8;
    public static final int FORMAT_VELOCITY = 9;



	//TODO Replace by the enum above !!!!!
    private final static int[] FORMAT_TRANSLATION = {
    	CommonSaveAsFields.FMT_DATA,  CommonSaveAsFields.FMT_HTML,   CommonSaveAsFields.FMT_HTML,
    	CommonSaveAsFields.FMT_HTML,  CommonSaveAsFields.FMT_CSV,    CommonSaveAsFields.FMT_FIXED,
    	CommonSaveAsFields.FMT_XML,   CommonSaveAsFields.FMT_SCRIPT,
    	CommonSaveAsFields.FMT_XSLT,  CommonSaveAsFields.FMT_VELOCITY};
    private static int[] FORMAT_HTML_TRANSLATION = {
    	-1,
    	SaveAsPnlBase.SINGLE_TABLE,
    	SaveAsPnlBase.TABLE_PER_ROW,
    	SaveAsPnlBase.TREE_TABLE
    };


    private CommonSaveAsFields commonSaveFields;
    private SaveAsPnlBase[] pnls;
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

    //private JTextArea msg = new JTextArea();


    private FileView<?> file;

    private int currentIndex;
    private String currentExtension = null;

    private boolean usingTab = true;


    private ChangeListener tabListner = new ChangeListener() {

		@Override
		public void stateChanged(ChangeEvent e) {

			int idx = getTabIndex();

			if (currentIndex != idx) {
				changeExtension();

				setVisibility();
			}
		}
    };





    private KeyAdapter listner = new KeyAdapter() {
        /**
         * @see java.awt.event.KeyAdapter#keyReleased
         */
        public final void keyReleased(KeyEvent event) {

        	switch (event.getKeyCode()) {
        	case KeyEvent.VK_ENTER:		saveFile();									break;
        	case KeyEvent.VK_ESCAPE:	SaveAs3.this.doDefaultCloseAction();		break;
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
    		int formatIdx, String script) {
        super(fileView.getFileNameNoDirectory(),
        	 ((formatIdx <= 0 || Common.OPTIONS.showAllExportPnls.isSelected()) ? "Export" : "Export1"),
              fileView.getBaseFile());


        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);


    	FocusListener templateChg = new FocusAdapter() {
    		@Override
    		public void focusLost(FocusEvent e) {
    			//System.out.print("Focus Lost ...");
    			changeExtension();
    		}

    	};

        commonSaveFields = new CommonSaveAsFields(recordFrame, fileView, templateChg);
        file = fileView;

        SaveAsPnlBase[] p = {
        		new SaveAsPnlBasic.Data(commonSaveFields),
        		new SaveAsPnlCsv(commonSaveFields),
        		new SaveAsPnlFixed(commonSaveFields),
        		new SaveAsPnlBasic.Xml(commonSaveFields),
        		new SaveAsPnlHtml(commonSaveFields),
        		new SaveAsPnlScript(commonSaveFields),
        		new SaveAsPnlXslt(commonSaveFields),
        		new SaveAsPnlVelocity(commonSaveFields) ,
        };
        pnls = p;

        init_100_setupFields_GetPnlLength(formatIdx);

        init_200_SetupTabPnls(formatIdx, script);
        init_300_layoutScreen();
        //System.out.println(" !! create class 4 " + this.getClass().getName() + " " + this.isMaximum());

        //System.out.println(" !! create class 5 " + this.getClass().getName() + " " + this.isMaximum());

        super.addMainComponent(pnl);
        //System.out.println(" !! create class 6 " + this.getClass().getName() + " " + this.isMaximum());
        this.setVisible(true);
        //System.out.println(" !! create class 7 " + this.getClass().getName() + " " + this.isMaximum());

        formatTab.addChangeListener(tabListner);
        pnl.addReKeyListener(listner);
        //System.out.println(" !! create class 8 " + this.getClass().getName() + " " + this.isMaximum());
        setToMaximum(false);
    }


    private void init_100_setupFields_GetPnlLength(int formatType) {
//        int len = pnls.length - 1;

    	int saveAsId = FORMAT_TRANSLATION[formatType];
    	int idx = 0;
        String fname = file.getFileName();
        if ("".equals(fname)) {
        	fname = Common.OPTIONS.DEFAULT_FILE_DIRECTORY.get();
        }


//        if (! Common.OPTIONS.xsltAvailable.isSelected()) {
//        	len -= 1;
//        	pnls[pnls.length - 2] = pnls[pnls.length - 1];
//        	FORMAT_TRANSLATION[FORMAT_TRANSLATION.length - 1] -= 1;
//        }
//        if (Common.isVelocityAvailable()) {
//        	len += 1;
//        }
        currentIndex = saveAsId;

        for (int i = 0; i < pnls.length; i++) {
        	pnls[i].onlyData.setSelected(! file.isBinaryFile());
        	pnls[i].showBorder.setSelected(true);
        	if (pnls[i].isActive()) {
        		formatTab.add(pnls[i].getTitle(), pnls[i].panel);
        		if (pnls[i].panelFormat == saveAsId) {
        			currentIndex = idx;
        		}
        		pnls[idx] = pnls[i];
        		idx += 1;
        	}
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

    }


    private void init_200_SetupTabPnls(int formatIdx, String script) {

		formatTab.setSelectedIndex(currentIndex);
		switch (formatIdx) {
		case FORMAT_1_TABLE:
		case FORMAT_MULTI_TABLE:
		case FORMAT_TREE_HTML:
			getSelectedPnl().setTableOption(FORMAT_HTML_TRANSLATION[formatIdx]);
			break;
		}

		if (script != null && ! "".equals(script) && getSelectedPnl().template != null) {
			getSelectedPnl().setTemplateText(script);
			changeExtension();
		}
    }


    private void init_300_layoutScreen() {

		pnl.addHelpBtn(Common.getHelpButton());

		pnl.setHelpURL(Common.formatHelpURL(Common.HELP_SAVE_AS));
        pnl.addLine("File Name", fileNameTxt, fileNameTxt.getChooseFileButton());
        pnl.addLine("What to Save", commonSaveFields.saveWhat);

        if (commonSaveFields.getTreeFrame() != null) {
        	commonSaveFields.treeExportChk.setSelected(true);
        	commonSaveFields.nodesWithDataChk.setSelected(false);


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

        pnl.setGap(BasePanel.GAP1);

        if (currentIndex < 1 || currentIndex >= pnls.length || Common.OPTIONS.showAllExportPnls.isSelected()) {
	        pnl.addLine("Output Format:", null);
	        pnl.addComponent(1, 5,BasePanel.FILL, BasePanel.GAP,
	                BasePanel.FULL, BasePanel.FULL,
	                formatTab);
        } else {
	        pnl.addComponent(0, 6, BasePanel.FILL, BasePanel.GAP,
	                BasePanel.FULL, BasePanel.FULL,
	                pnls[currentIndex].panel);
	        pnls[currentIndex].panel.setBorder(
	        		BorderFactory.createTitledBorder(
	        				BorderFactory.createRaisedBevelBorder(),
	        				pnls[currentIndex].getTitle() + " Options : "));
	        //formatTab.setSelectedIndex(currentIndex);
	        usingTab = false;
       }

        pnl.setGap(BasePanel.GAP1);
        pnl.addLine("Edit Output File", commonSaveFields.editChk);
        pnl.addLine("Keep screen open", commonSaveFields.keepOpenChk, saveFile);
        pnl.setGap(BasePanel.GAP1);
        pnl.addMessage(new JScrollPane(commonSaveFields.message));
        pnl.setHeight(BasePanel.GAP5);

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
        	commonSaveFields.message.setText("Please Enter a file name");
            return;
        }
    	SaveAsPnlBase activePnl =  getSelectedPnl();
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
            commonSaveFields.message.setText("Error: " + e.getMessage());
        }
    }





    private SaveAsPnlBase getSelectedPnl() {
    	return pnls[getTabIndex()];
    }



    private void setVisibility() {
    	SaveAsPnlBase pnl = getSelectedPnl();

    	commonSaveFields.setVisibility(pnl.panelFormat, pnl.singleTable.isSelected());
    }

    private void changeExtension() {
		String s = fileNameTxt.getText();
		int idx = getTabIndex();
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

    private int getTabIndex() {
    	int idx = currentIndex;
    	if (usingTab) {
    		idx = formatTab.getSelectedIndex();
    	}
    	return idx;
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
