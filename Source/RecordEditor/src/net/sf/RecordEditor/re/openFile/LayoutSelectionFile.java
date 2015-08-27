package net.sf.RecordEditor.re.openFile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.External.CopybookLoaderFactory;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.JRecord.Numeric.ICopybookDialects;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.edit.ManagerRowList;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBox;
import net.sf.RecordEditor.utils.swing.BmKeyedComboModel;
import net.sf.RecordEditor.utils.swing.ComboBoxs.DelimiterCombo;
import net.sf.RecordEditor.utils.swing.ComboBoxs.ManagerCombo;
import net.sf.RecordEditor.utils.swing.ComboBoxs.QuoteCombo;
import net.sf.RecordEditor.utils.swing.treeCombo.FileSelectCombo;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboFileSelect;

public class LayoutSelectionFile extends AbstractLayoutSelection  {

	private static final String OLD_SEPERATOR = ",,";
	private static final String SEPERATOR = "~";

	private static final int MODE_NORMAL = 0;
	private static final int MODE_1ST_COBOL = 1;
	private static final int MODE_2ND_COBOL = 2;


    private static final int TAB_CSV_IDX = 8;

   	private ManagerCombo loaderOptions = null;
//	private FileChooser  copybookFile;
	private FileSelectCombo  copybookFile;
	private BmKeyedComboBox   fileStructure;
	private BmKeyedComboModel structureModel = new BmKeyedComboModel(new ManagerRowList(
			LineIOProvider.getInstance(), false));

	private SplitCombo   splitOption;
	private ComputerOptionCombo numericFormat;

    private DelimiterCombo fieldSeparator;
    private QuoteCombo quote;
	private JTextArea message = null;

	private String lastFileName = "";
	private String lastLayoutDetails = "";
	private String lastCopybookName = "";
	private AbstractLayoutDetails lastLayout;

	//private boolean isCob = false;

	private int mode = MODE_NORMAL;

	private ChangeListener copybookFocusListner;
	
	private String fileParamKey = Parameters.SCHEMA_LIST;

    public LayoutSelectionFile() {
		super();
    }


    public LayoutSelectionFile(boolean isCobol) {
		super();

		if (isCobol) {
			mode = MODE_1ST_COBOL;
			fileParamKey = Parameters.COBOL_COPYBOOK_LIST;
		}
    }

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.openFile.AbstractLayoutSelection#addLayoutSelection(net.sf.RecordEditor.utils.swing.BasePanel, net.sf.RecordEditor.utils.swing.treeCombo.TreeComboFileSelect, javax.swing.JPanel, javax.swing.JButton, javax.swing.JButton)
	 */
	@Override
	public void addLayoutSelection(BasePanel pnl, TreeComboFileSelect file,
			JPanel goPanel, JButton layoutCreate1, JButton layoutCreate2) {
		addLayoutSelection(pnl, goPanel, layoutCreate1, layoutCreate2, null);
	}

	/**
	 * Add Selection with external layout file
	 * @param pnl panel to add fields to
	 * @param goPanel go panel to use
	 * @param layoutFile layout file
	 */
	public void addLayoutSelection(BasePanel pnl, JPanel goPanel, 	FileSelectCombo layoutFile) {

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
			JButton layoutCreate1, JButton layoutCreate2, FileSelectCombo layoutFile) {
  	    setupFields();

  	    if ("".equals(copybookFile.getText())) {
  	    	copybookFile.setText(Common.OPTIONS.DEFAULT_COPYBOOK_DIRECTORY.get());
  	    }

	    fileStructure.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setEditable();
			}

	    });

	    if (layoutFile != null)  {
	    	loaderOptions.setSelectedIndex(CopybookLoaderFactoryExtended.COBOL_LOADER);
	    	copybookFile = layoutFile;

		    pnl.addLineRE("Output File Structure", fileStructure);
		    pnl.addLineRE("Output Numeric Format", numericFormat);
		    mode = MODE_2ND_COBOL;
	    } else if (mode == MODE_1ST_COBOL) {
	    	loaderOptions.setSelectedIndex(CopybookLoaderFactoryExtended.COBOL_LOADER);

			pnl.addLineRE("Copybook", copybookFile);

		    pnl.addLineRE("Input File Structure", fileStructure);
		    pnl.addLineRE("Input Numeric Format", numericFormat);
	    } else {
		    pnl.addLineRE("File Structure", fileStructure);

		    loaderOptions.setEnglish(Common.OPTIONS.COPYBOOK_READER.get());
	    	pnl.addLineRE("Copybook Type", loaderOptions);
	    	pnl.addLineRE("Split Copybook", splitOption);

	    	pnl.setGapRE(BasePanel.GAP0);
			pnl.addLineRE("Copybook", copybookFile);
		    pnl.setGapRE(BasePanel.GAP0);

		    pnl.addLineRE("Numeric Format", numericFormat);

		    pnl.addLineRE("Field Seperator", fieldSeparator);
		    pnl.addLineRE("Quote", quote);
		    pnl.setGapRE(BasePanel.GAP1);
			pnl.addLineRE("", null, goPanel);
			if (goPanel != null) {
				pnl.setHeightRE(Math.max(BasePanel.NORMAL_HEIGHT * 3, goPanel.getPreferredSize().getHeight()));
			}

			copybookFocusListner = new ChangeListener() {		
				@Override public void stateChanged(ChangeEvent e) {
					checkCopybookType();					
				}
			};	

			copybookFile.addTextChangeListner(copybookFocusListner);
			checkCopybookType();
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

	@Override
	public void forceLayoutReload() {
		lastLayoutDetails = null;
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

        fileStruc = ((Integer) fileStructure.getSelectedItem()).intValue();

        if (loadFromFile && lastLayoutDetails != null && lastLayoutDetails.equalsIgnoreCase(layoutName)
        && ((lastFileName != null && lastFileName.equals(fileName)) || (lastFileName == fileName))) {
        	ret = lastLayout;
        } else if (! LineIOProvider.getInstance().isCopyBookFileRequired(fileStruc)) {
        	ret = buildLayoutFromSample(fileStruc,
        			fileStructure.getSelectedIndex() == TAB_CSV_IDX,
        			getFontName(), fieldSeparator.getSelectedEnglish(), fileName);
         	lastLayoutDetails = layoutName;
         	lastLayout = ret;
        } else if (layoutName == null ||  "".equals(layoutName)) {
            setMessageText("You must enter a Record Layout name ", copybookFile);
        } else {
            try {
                ret = getRecordLayout_readLayout(layoutName, fileName);

            	if (  loadFromFile
            	&&    ret != null
               	&&  ! LineIOProvider.getInstance().isCopyBookFileRequired(ret.getFileStructure())) {
            		ret = getFileBasedLayout(fileName, ret);
               	}

                lastLayoutDetails = layoutName;
                lastFileName = fileName;
             	lastLayout = ret;
            } catch (Exception e) {
	            setMessageText("You must enter a copybook name ", copybookFile);

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

        //System.out.println("Retrieve layout: " + copyBookFile);
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
            	||  rec.getFileStructure() == Constants.IO_UNICODE_NAME_1ST_LINE
            	||  rec.getFileStructure() == Constants.IO_GENERIC_CSV) {
            		//fileStructure.setSelectedIndex(4);
            	} else if (rec.getFileStructure() == Constants.IO_XML_BUILD_LAYOUT
            			|| rec.getFileStructure() == Constants.IO_XML_USE_LAYOUT
                    	|| rec.getFileStructure() == Constants.IO_WIZARD
            			|| structure == Constants.IO_DEFAULT) {
            	} else {
            		rec.setFileStructure(structure);
            	}

            	ret = rec.asLayoutDetail();

            } else {
            	System.out.println("Record Layout file does not exist: " + copy);
                setMessageText("Record Layout file does not exist: " + copy, copybookFile);
            }
        } catch (Exception e) {
        	System.out.println("Error: "+ e.getMessage());
        	setMessageText("Error: " + e.getMessage(), null);
        	e.printStackTrace();
        }

        return ret;
    }

    private String getFontName() {
    	int numFormat = numericFormat.getSelectedValue();
        String font = "";
        if (numFormat == ICopybookDialects.FMT_MAINFRAME) {
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

        if (t.countTokens() < 5) {
        	t = new StringTokenizer(layoutName, OLD_SEPERATOR);
        }


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
			String s;

	 	    loaderOptions  = ManagerCombo.newCopybookLoaderCombo();
			copybookFile   = new FileSelectCombo(fileParamKey, 25, true, false);
					//new FileChooser(true, "Choose Layout");
    		fileStructure  = new BmKeyedComboBox(structureModel, false);
    		splitOption    = new SplitCombo();
    		numericFormat  = new ComputerOptionCombo();
    		fieldSeparator = DelimiterCombo.NewDelimComboWithDefault();
    				//new JComboBox(Common.FIELD_SEPARATOR_LIST);
        	quote          = QuoteCombo.newCombo();


    	    s = Common.OPTIONS.DEFAULT_IO_NAME.get();
    	    if (! "".equals(s)) {
    	    	fileStructure.setSelectedDisplay(s);
    	    }
    	    s = Common.OPTIONS.DEFAULT_BIN_NAME.get();
    	    if (! "".equals(s)) {
    	    	numericFormat.setEnglishText(s);
    	    }
		}
	}

	/**
	 * Set the Copybook-Loader option based on the Copybook File Selected
	 */
	private void checkCopybookType() {
		String fname = copybookFile.getText();

		if (fname != null && ! fname.equals(lastCopybookName)) {
			int loaderType  = CopybookLoaderFactoryExtended.getLoaderType(fname);

			if (loaderType >= 0) {
				loaderOptions.setSelectedIndex(loaderType);
			}

			lastCopybookName = fname;
		}
	}


    /**
     * get the next integer Token
     * @param t StringTokenizer
     * @return next token as an integer
     */
    private int getIntToken(StringTokenizer t) {
        int ret = 0;

        if (t.hasMoreElements()) {
	        String s = t.nextToken();

	        try {
	            ret = Integer.parseInt(s);
	        } catch (Exception e) {
	        }
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
	 * @see net.sf.RecordEditor.re.openFile.AbstractLayoutSelection#getDataBaseNames()
	 */
	@Override
	public String[] getDataBaseNames() {
		return null;
	}


	/**
	 * @see net.sf.RecordEditor.re.openFile.AbstractLayoutSelection#setDatabaseIdx(int)
	 */
	@Override
	public void setDatabaseIdx(int idx) {

	}

	/**
	 * @see net.sf.RecordEditor.re.openFile.AbstractLayoutSelection#getDatabaseIdx()
	 */
	@Override
	public int getDatabaseIdx() {
		return 0;
	}

	/**
	 * @see net.sf.RecordEditor.re.openFile.AbstractLayoutSelection#getDatabaseName()
	 */
	@Override
	public String getDatabaseName() {
		return null;
	}

	/**
	 * @see net.sf.RecordEditor.re.openFile.AbstractLayoutSelection#formatLayoutName(java.lang.String)
	 */
	@Override
	public String formatLayoutName(String layoutName) {
		String name = layoutName;
//
//		if (name != null || name.indexOf(',') > 1) {
//			name = name.substring(0, name.indexOf(',') - 2)
//			     + name.substring(name.indexOf(','));
//		}

		return Common.OPTIONS.DEFAULT_COPYBOOK_DIRECTORY.getNoStar() + name + ".Xml"
			 + SEPERATOR + "2" + SEPERATOR + "0"
			 + SEPERATOR + "0" + SEPERATOR + "0" + SEPERATOR + "0" + SEPERATOR + "0";
	}

	private void setMessageText(String text, JComponent component) {

		if (message == null) {
			System.err.println();
			System.err.println(LangConversion.convert("Error:") + " " + text);
			System.err.println("================================================================");
			System.err.println();
		} else {
			if (component != null) {
				component.requestFocus();
			}
			message.setText(text);
		}
	}

	/**
	 * @return the copybookFile
	 */
	public final FileSelectCombo getCopybookFile() {
		return copybookFile;
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.openFile.AbstractLayoutSelection#isFileBasedLayout()
	 */
	@Override
	public boolean isFileBasedLayout() {
		return true;
	}
}
