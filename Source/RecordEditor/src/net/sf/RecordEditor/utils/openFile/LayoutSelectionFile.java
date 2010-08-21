package net.sf.RecordEditor.utils.openFile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.External.CopybookLoaderFactory;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.JRecord.Numeric.Convert;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.edit.ManagerRowList;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBox;
import net.sf.RecordEditor.utils.swing.BmKeyedComboModel;
import net.sf.RecordEditor.utils.swing.FileChooser;

public class LayoutSelectionFile extends AbstractLayoutSelection  {
	
	private static final String SEPERATOR = ",,";
	
	private static final int MODE_NORMAL = 0;
	private static final int MODE_1ST_COBOL = 1;
	private static final int MODE_2ND_COBOL = 2;

   
    private static final int TAB_CSV_IDX = 8;

   	private JComboBox   loaderOptions = null;
	private FileChooser  copybookFile;
	private BmKeyedComboBox   fileStructure;
	private BmKeyedComboModel structureModel = new BmKeyedComboModel(new ManagerRowList(
			LineIOProvider.getInstance(), false));

	private SplitCombo   splitOption;
	private ComputerOptionCombo numericFormat;
	
    private JComboBox fieldSeparator;
    private JComboBox quote;
	private JTextArea message = null;
	
	private String lastLayoutDetails = "";
	private AbstractLayoutDetails lastLayout;
	
	//private boolean isCob = false;

	private int mode = MODE_NORMAL;

    public LayoutSelectionFile() {
		super();
    }
 

    public LayoutSelectionFile(boolean isCobol) {
		super();
		
		if (isCobol) {
			mode = MODE_1ST_COBOL;
		}
    }

	@Override
	public void addLayoutSelection(BasePanel pnl, JTextField file, JPanel goPanel,
			JButton layoutCreate1, JButton layoutCreate2) {
		addLayoutSelection(pnl, goPanel, layoutCreate1, layoutCreate2, null);
	}

	/**
	 * Add Selection with external layout file
	 * @param pnl panel to add fields to
	 * @param goPanel go panel to use
	 * @param layoutFile layout file
	 */
	public void addLayoutSelection(BasePanel pnl, JPanel goPanel, 	FileChooser layoutFile) {
		
		addLayoutSelection(pnl, goPanel, null, null, layoutFile);
	}

	/**
	 * Add layout Selection to panel
	 * @param pnl panel to be updated
	 * @param goPanel panel holding go button
	 * @param layoutCreate1 layout create button1
	 * @param layoutCreate2  layout create button2
	 * @param layoutFile layout file
	 */
	private void addLayoutSelection(BasePanel pnl, JPanel goPanel,
			JButton layoutCreate1, JButton layoutCreate2, FileChooser layoutFile) {
  	    setupFields();

 	    copybookFile.setText(Common.DEFAULT_COPYBOOK_DIRECTORY);

	    fileStructure.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setEditable();
			}

	    });

	    if (layoutFile != null)  {
	    	loaderOptions.setSelectedIndex(CopybookLoaderFactoryExtended.COBOL_LOADER);
	    	copybookFile = layoutFile;
	    	
		    pnl.addComponent("Output File Structure", fileStructure);
		    pnl.addComponent("Output Numeric Format", numericFormat);
		    mode = MODE_2ND_COBOL;
	    } else if (mode == MODE_1ST_COBOL) {
	    	loaderOptions.setSelectedIndex(CopybookLoaderFactoryExtended.COBOL_LOADER);

			pnl.addComponent("Copybook", copybookFile, copybookFile.getChooseFileButton());
			
		    pnl.addComponent("Input File Structure", fileStructure);
		    pnl.addComponent("Input Numeric Format", numericFormat);
	    } else {
		    pnl.addComponent("File Structure", fileStructure);
		    
		    loaderOptions.setSelectedItem(Common.COPYBOOK_READER);
	    	pnl.addComponent("Copybook Type", loaderOptions);
	    	pnl.addComponent("Split Copybook", splitOption);
	    	
	    	pnl.setGap(BasePanel.GAP0);
			pnl.addComponent("Copybook", copybookFile, copybookFile.getChooseFileButton());
		    pnl.setGap(BasePanel.GAP0);
		    
		    pnl.addComponent("Numeric Format", numericFormat);
	    
		    pnl.addComponent("Field Seperator", fieldSeparator);
		    pnl.addComponent("Quote", quote);
		    pnl.setGap(BasePanel.GAP1);
			pnl.addComponent("", null, goPanel);
			if (goPanel != null) {
				pnl.setHeight(Math.max(BasePanel.NORMAL_HEIGHT * 3, goPanel.getPreferredSize().getHeight()));
			}
	    }
		
//	    } else {
//		    pnl.setGap(BasePanel.GAP1);
//	    }
	    
		setEditable();
	}
	

    
    /**
     * set editable
     */
    private void setEditable() {
    	
    	int fileStruc = ((Integer) fileStructure.getSelectedItem()).intValue();
    	boolean copybookRequired = LineIOProvider.getInstance().isCopyBookFileRequired(fileStruc);
    	
//    	System.out.println("Setting Enabled: " + copybookRequired + " " + fileStruc
//    			+ " " + (fileStruc == Constants.IO_NAME_1ST_LINE)
//    			+ " " + (! (
//         			   fileStruc ==  Constants.IO_NAME_1ST_LINE
//           			|| fileStruc == Constants.IO_GENERIC_CSV
//           			|| fileStruc == Constants.IO_XML_BUILD_LAYOUT
//           	)));
  	
    	loaderOptions.setEnabled(copybookRequired);
    	copybookFile.setEnabled(copybookRequired);
    	splitOption.setEnabled(copybookRequired);
    	
    	fieldSeparator.setEnabled(fileStruc == Constants.IO_NAME_1ST_LINE);
    	quote.setEnabled(fileStruc == Constants.IO_NAME_1ST_LINE);
    	
    	numericFormat.setEnabled(! (
    			   fileStruc ==  Constants.IO_NAME_1ST_LINE
    			|| fileStruc == Constants.IO_GENERIC_CSV
    			|| fileStruc == Constants.IO_XML_BUILD_LAYOUT
    	));
    }


	@Override
	public String getLayoutName() {
		return    copybookFile.getText()
        + SEPERATOR + loaderOptions.getSelectedIndex()
        + SEPERATOR + fileStructure.getSelectedIndex()
        + SEPERATOR + splitOption.getSelectedIndex()
        + SEPERATOR + numericFormat.getSelectedIndex()
    	+ SEPERATOR + fieldSeparator.getSelectedIndex()
    	+ SEPERATOR + quote.getSelectedIndex();
	}

	/**
	 * Get Layout details
	 * @return record layout
	 */
	@Override
	public final AbstractLayoutDetails getRecordLayout(String fileName) {
		AbstractLayoutDetails ret = null;
        int fileStruc;
        String layoutName = copybookFile.getText();
        
        //setLayoutName(layoutName);
        //TODO get layout ....
        //fileStruc == Constants.IO_NAME_1ST_LINE
        
        fileStruc = ((Integer) fileStructure.getSelectedItem()).intValue();

        if (loadFromFile && lastLayoutDetails != null && lastLayoutDetails.equalsIgnoreCase(layoutName)) {
        	ret = lastLayout;
        } else if (! LineIOProvider.getInstance().isCopyBookFileRequired(fileStruc)) {
        	ret = buildLayoutFromSample(fileStruc, 
        			fileStructure.getSelectedIndex() == TAB_CSV_IDX,
        			getFontName(), fieldSeparator.getSelectedItem().toString(), fileName);
         	lastLayoutDetails = layoutName;
         	lastLayout = ret;
       	
        } else if (layoutName == null ||  "".equals(layoutName)) {
            copybookFile.requestFocus();
            message.setText("You must enter a Record Layout name ");
        } else {
            try {
                ret = getRecordLayout_readLayout(layoutName, fileName);

            	if (  loadFromFile  
            	&&    ret != null
               	&&  ! LineIOProvider.getInstance().isCopyBookFileRequired(ret.getFileStructure())) {
            		ret = getFileBasedLayout(fileName, ret);
//                   	ret = buildLayoutFromSample(ret.getFileStructure(), 
//                        			fileStructure.getSelectedIndex() == TAB_CSV_IDX,
//                        			ret.getFontName(), ret.getDelimiter(), fileName);
               	}

                lastLayoutDetails = layoutName;

             	lastLayout = ret;
            } catch (Exception e) {
                copybookFile.requestFocus();
                message.setText("You must enter a copybook name ");
                e.printStackTrace();
            }
        }

        return ret;
	}

	/**
	 * Get Layout details
	 * @param layoutName record layout name
	 * @return record layout
	 */
	@Override
	public final AbstractLayoutDetails getRecordLayout(String layoutName, String fileName) {
		setLayoutName(layoutName);
		return getRecordLayout(fileName);
	}


	
    /**
     * Read a layout
     * @param copyBookFile layout filename to be read
     * @return requested layout
     */
    private LayoutDetail getRecordLayout_readLayout(String copyBookFile, String filename) {
        LayoutDetail ret = null;

        try {
        	int loaderIdx = loaderOptions.getSelectedIndex();
        	CopybookLoaderFactory   factory = CopybookLoaderFactoryExtended.getInstance();
            CopybookLoader loader = factory.getLoader(loaderIdx);
            int structure = ((Integer) fileStructure.getSelectedItem()).intValue();
            int split = splitOption.getSelectedValue();
            int numFormat = numericFormat.getSelectedValue();
            String font = getFontName();
            String copy = copyBookFile;
            if (factory.isBasedOnInputFile(loaderIdx) && (new File(filename).exists())) {
            	copy = filename;
            }

            File f = new File(copy);
            if (f.exists()) {
            	ExternalRecord rec = loader.loadCopyBook(
                    copy, split, 0, font,
                    numFormat, 0, Common.getLogger());

            	if (rec.getFileStructure() == Constants.IO_NAME_1ST_LINE
            	||  rec.getFileStructure() == Constants.IO_BIN_NAME_1ST_LINE
            	||  rec.getFileStructure() == Constants.IO_GENERIC_CSV) {
            		//fileStructure.setSelectedIndex(4);
            	} else if (rec.getFileStructure() == Constants.IO_XML_BUILD_LAYOUT
            			|| rec.getFileStructure() == Constants.IO_XML_USE_LAYOUT
            			|| structure == Constants.IO_DEFAULT) {
            	} else {
            		rec.setFileStructure(structure);
            	}

            	ret = rec.asLayoutDetail();
            	
            } else {
                copybookFile.requestFocus();
                message.setText("Record Layout file does not exist: " + copy);
             }
       } catch (Exception e) {
        	message.setText("Error: " + e.getMessage());
        }

        return ret;
    }
    
    private String getFontName() {
    	int numFormat = numericFormat.getSelectedValue();
        String font = "";
        if (numFormat == Convert.FMT_MAINFRAME) {
            font = "cp037";
        }
        return font;
    }

	@Override
	public void reload() {

		lastLayoutDetails = "";
		lastLayout = null;
	}

	@Override
	public boolean setLayoutName(String layoutName) {
        StringTokenizer t = new StringTokenizer(layoutName, SEPERATOR);


        try {
        	int loader,split;
        	setupFields();
            copybookFile.setText(t.nextToken());
            
            loader = getIntToken(t);
            fileStructure.setSelectedIndex(getIntToken(t));
            split = getIntToken(t);
            
            if (mode == MODE_NORMAL) {
            	loaderOptions.setSelectedIndex(loader);
            	splitOption.setSelectedIndex(split);
            } else {
    	    	loaderOptions.setSelectedIndex(CopybookLoaderFactoryExtended.COBOL_LOADER);
            }
            numericFormat.setSelectedIndex(getIntToken(t));
            fieldSeparator.setSelectedIndex(getIntToken(t));
            quote.setSelectedIndex(getIntToken(t));
        } catch (Exception e) {
            e.printStackTrace();
        }
        setEditable();

		return false;
	}
	
	private void setupFields() {
		
		if (loaderOptions == null) {
	 	    CopybookLoaderFactory loaders = CopybookLoaderFactoryExtended.getInstance();

	 	    loaderOptions  = new JComboBox();
			copybookFile   = new FileChooser("Choose Layout");
    		fileStructure  = new BmKeyedComboBox(structureModel, false);
    		splitOption    = new SplitCombo();
    		numericFormat  = new ComputerOptionCombo();
    		fieldSeparator = new JComboBox(Common.FIELD_SEPARATOR_LIST);
        	quote          = new JComboBox(Common.QUOTE_LIST);
        	
    	    for (int i = 0; i < loaders.getNumberofLoaders(); i++) {
    	        loaderOptions.addItem(loaders.getName(i));
    	    }

    	    if (! "".equals(Common.DEFAULT_IO_NAME)) {
    	    	fileStructure.setSelectedDisplay(Common.DEFAULT_IO_NAME);
    	    }
    	    if (! "".equals(Common.DEFAULT_BIN_NAME)) {
    	    	numericFormat.setSelectedItem(Common.DEFAULT_BIN_NAME);
    	    }
		}
	}

    /**
     * get the next integer Token
     * @param t StringTokenizer
     * @return next token as an integer
     */
    private int getIntToken(StringTokenizer t) {
        int ret = 0;
        String s = t.nextToken();

        try { 
            ret = Integer.parseInt(s);
        } catch (Exception e) {
        }

        return ret;
    }


	/**
	 * @param message the message to set
	 */
	public void setMessage(JTextArea message) {
		this.message = message;
	}


	/**
	 * @see net.sf.RecordEditor.utils.openFile.AbstractLayoutSelection#getDataBaseNames()
	 */
	@Override
	public String[] getDataBaseNames() {
		return null;
	}


	/**
	 * @see net.sf.RecordEditor.utils.openFile.AbstractLayoutSelection#setDatabaseIdx(int)
	 */
	@Override
	public void setDatabaseIdx(int idx) {
		
	}

	/**
	 * @see net.sf.RecordEditor.utils.openFile.AbstractLayoutSelection#getDatabaseIdx()
	 */
	@Override
	public int getDatabaseIdx() {
		return 0;
	}

	/**
	 * @see net.sf.RecordEditor.utils.openFile.AbstractLayoutSelection#getDatabaseName()
	 */
	@Override
	public String getDatabaseName() {
		return null;
	}

	/**
	 * @see net.sf.RecordEditor.utils.openFile.AbstractLayoutSelection#formatLayoutName(java.lang.String)
	 */
	@Override
	public String formatLayoutName(String layoutName) {
		String name = layoutName;
//		
//		if (name != null || name.indexOf(',') > 1) {
//			name = name.substring(0, name.indexOf(',') - 2)
//			     + name.substring(name.indexOf(','));
//		}
		
		return Common.DEFAULT_COPYBOOK_DIRECTORY + name + ".Xml"
			 + SEPERATOR + "2" + SEPERATOR + "0" 
			 + SEPERATOR + "0" + SEPERATOR + "0" + SEPERATOR + "0" + SEPERATOR + "0";
	}


	/**
	 * @return the copybookFile
	 */
	public final FileChooser getCopybookFile() {
		return copybookFile;
	}
}
