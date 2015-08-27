package net.sf.RecordEditor.re.openFile;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
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
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.protoGen.cobolOpt.CobolCopybookOption;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.UpdatableTextValue;
import net.sf.RecordEditor.utils.swing.treeCombo.FileSelectCombo;
import net.sf.RecordEditor.utils.swing.treeCombo.FileTreeComboItem;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboFileSelect;
import net.sf.RecordEditor.utils.swing.treeCombo.ZOld_TreeComboFileSelect2;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboItem;

public class LayoutSelectionCobolAlt extends AbstractLayoutSelection  {

	private static final int NUMBER_OF_COMBO_ENTRIES = 15;

	private static final String SEPERATOR = "~";

	private static final int MODE_NORMAL = 0;
	private static final int MODE_1ST_COBOL = 1;
	private static final int MODE_2ND_COBOL = 2;


    private static final int TAB_CSV_IDX = 8;

//   	private ManagerCombo loaderOptions = null;
//	private FileChooser  copybookFile;
    private FileSelectCombo	 copybookDir;
    private ZOld_TreeComboFileSelect2 copybookFile;
//	private BmKeyedComboBox   fileStructure;
//	private BmKeyedComboModel structureModel = new BmKeyedComboModel(new ManagerRowList(
//			LineIOProvider.getInstance(), false));

//	private SplitCombo   splitOption;
//	private ComputerOptionCombo numericFormat;

//    private DelimiterCombo fieldSeparator;
//    private QuoteCombo quote;
	private JTextArea message = null;

	private BasePanel parentPnl;

	private String lastFileName = "";
	private String lastLayoutDetails = "";
	private File lastCopybookDir = null;
	private AbstractLayoutDetails lastLayout;

	//private boolean isCob = false;

	private int mode = MODE_NORMAL;

	private FocusListener copybookFocusListner;


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
	public void addLayoutSelection(BasePanel pnl, JPanel goPanel, 	ZOld_TreeComboFileSelect2 layoutFile) {
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
			JButton layoutCreate1, JButton layoutCreate2, ZOld_TreeComboFileSelect2 layoutFile) {
  	    setupFields();

  	    parentPnl = pnl;

//	    fileStructure.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				setEditable();
//			}
//
//	    });

  	    System.out.println("+ + +  dimensions follow:");
	    if (layoutFile != null)  {
	    	copybookFile = layoutFile;

		    mode = MODE_2ND_COBOL;
	    } else if (mode == MODE_1ST_COBOL) {
			pnl.addLine3to5("Copybook Directory", copybookDir);
			pnl.addLine3to5("Copybook", copybookFile);
	    } else {
	    	pnl.setGapRE(BasePanel.GAP0);
			pnl.addLine3to5("Copybook Directory", copybookDir);
			pnl.addLine3to5("Copybook", copybookFile);

		    pnl.setGapRE(BasePanel.GAP1);
			pnl.addLineRE("", null, goPanel);
			if (goPanel != null) {
				pnl.setHeightRE(Math.max(BasePanel.NORMAL_HEIGHT * 3, goPanel.getPreferredSize().getHeight()));
			}
	    }

		setEditable();
	}



    /**
     * set editable
     */
    private void setEditable() {


    	copybookDir.setEnabled(true);
    	copybookFile.setEnabled(true);
    }


	@Override
	public String getLayoutName() {
		return
				  copybookDir.getText()
				+ SEPERATOR + copybookFile.getText()
//        + SEPERATOR + loaderOptions.getSelectedIndex()
//        + SEPERATOR + fileStructure.getSelectedIndex()
//        + SEPERATOR + splitOption.getSelectedIndex()
//       + SEPERATOR + numericFormat.getSelectedIndex()
//    	+ SEPERATOR + fieldSeparator.getSelectedIndex()
//    	+ SEPERATOR + quote.getSelectedIndex();
        ;
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

        String lName = copybookFile.getText();
        String copybookFileName = getFileName();
        File f;

        if (lName == null ||  "".equals(lName)) {
            setMessageText("You must enter a Record Layout name ", copybookFile);
        } else if (! (f = new File(copybookFileName)).exists()) {
            setMessageText("Copybook file does not exist ", copybookFile);
        } else {
        	CobolCopybookOption cobOpt = CobolOptionsManager.getInstance()
        										.getOption(lName, f.lastModified());
        	if (cobOpt == null) {
        		cobOpt = getCobOptions(cobOpt);
        	}

        	int fileStruc = cobOpt.getFileStructure();
        	if (loadFromFile && lastLayoutDetails != null && lastLayoutDetails.equalsIgnoreCase(copybookFileName)
        			&& ((lastFileName != null && lastFileName.equals(fileName)) || (lastFileName == fileName))) {
        		ret = lastLayout;
        	} else if (! LineIOProvider.getInstance().isCopyBookFileRequired(fileStruc)) {
        		ret = buildLayoutFromSample(fileStruc,
        				fileStruc == TAB_CSV_IDX,
        						cobOpt.getFont(), "", fileName);
        		lastLayoutDetails = copybookFileName;
        		lastLayout = ret;
        	} else {
        		try {
        			ret = getRecordLayout_readLayout(copybookFileName, fileName, cobOpt);

        			if (  loadFromFile
        					&&    ret != null
        					&&  ! LineIOProvider.getInstance().isCopyBookFileRequired(ret.getFileStructure())) {
        				ret = getFileBasedLayout(fileName, ret);
        			}

        			lastLayoutDetails = copybookFileName;
        			lastFileName = fileName;
        			lastLayout = ret;
        		} catch (Exception e) {
        			setMessageText("You must enter a copybook name ", copybookFile);

        			e.printStackTrace();
        		}
        	}
        }

        return ret;
	}

	private CobolCopybookOption getCobOptions(CobolCopybookOption cobOpt) {
		CobolCopybookOption ret;

		if (cobOpt == null) {
			long time = 0;
			File f = new File(getFileName());
			if (f.exists()) {
				time = f.lastModified();
			}
			cobOpt = CobolCopybookOption.newBuilder()
						.setCobolDialect(Common.OPTIONS.cobolDialect.get())
						.setCopybookDateTime(time)
						.setCopybookDir(copybookDir.getText())
						.setCopybookName(copybookFile.getText())
						.setFileStructure(Constants.IO_DEFAULT)
						.setFont("")
						.setSplitOption(CopybookLoader.SPLIT_NONE)
					.build();
		}
		CobolOptionPnl cobOptPnl = new CobolOptionPnl(cobOpt, false);
		final JDialog dialog = new JDialog(ReMainFrame.getMasterFrame(), true);

		dialog.getContentPane().add(cobOptPnl.getPanel());
		dialog.pack();

		cobOptPnl.setCloseAction(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
			}
		});

		dialog.setVisible(true);

		if (! cobOptPnl.isOk()) {
			throw new RuntimeException("Edit of Cobol Options Cancelled");
		}

		ret = cobOptPnl.getCobolOptions();

		CobolOptionsManager.getInstance().saveCobolOption(ret);

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
    private LayoutDetail getRecordLayout_readLayout(
    		String copyBookFile,
    		String filename,
    		CobolCopybookOption cobOpt) {
        LayoutDetail ret = null;

        //System.out.println("Retrieve layout: " + copyBookFile);
        try {
//        	int loaderIdx = loaderOptions.getSelectedIndex();
        	CopybookLoaderFactory   factory = CopybookLoaderFactoryExtended.getInstance();
        	int loaderIdx = CopybookLoaderFactoryExtended.getLoaderType(copyBookFile);
            CopybookLoader loader = factory.getLoader(loaderIdx);
            int structure = cobOpt.getFileStructure();
//            int split = splitOption.getSelectedValue();
            int numFormat = cobOpt.getCobolDialect();
            String font = cobOpt.getFont();
            String copy = copyBookFile;
            if (factory.isBasedOnInputFile(loaderIdx) && (new File(filename).exists())) {
            	copy = filename;
            }

            File f = new File(copy);
            if (f.exists()) {
            	ExternalRecord rec = loader.loadCopyBook(
                    copy, CopybookLoader.SPLIT_NONE, 0, font,
                    numFormat, 0, Common.getLogger());

            	switch (rec.getFileStructure()) {
            	case Constants.IO_NAME_1ST_LINE:
            	case Constants.IO_BIN_NAME_1ST_LINE:
            	case Constants.IO_UNICODE_NAME_1ST_LINE:
            	case Constants.IO_GENERIC_CSV:
            	case Constants.IO_XML_BUILD_LAYOUT:
            	case Constants.IO_XML_USE_LAYOUT:
                case Constants.IO_WIZARD:
            		break;
            	default:
            		if (structure != Constants.IO_DEFAULT) {
            			rec.setFileStructure(structure);
            		}
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

//    private String getFontName() {
//    	int numFormat = numericFormat.getSelectedValue();
//        String font = "";
//        if (numFormat == Convert.FMT_MAINFRAME) {
//            font = "cp037";
//        }
//        return font;
//    }

	@Override
	public void reload() {

		lastLayoutDetails = "";
		lastLayout = null;
	}

	@Override
	public boolean setLayoutName(String layoutName) {
        StringTokenizer t = new StringTokenizer(layoutName, SEPERATOR);

        try {
        	setupFields();
            copybookDir.setText(t.nextToken());
            if (t.hasMoreTokens()) {
            	copybookFile.setText(t.nextToken());
            }

//            loader = getIntToken(t);
//            fileStructure.setSelectedIndex(getIntToken(t));
//            split = getIntToken(t);

//            if (mode == MODE_NORMAL) {
//            	loaderOptions.setSelectedIndex(loader);
//            	splitOption.setSelectedIndex(split);
//            } else {
//    	    	loaderOptions.setSelectedIndex(CopybookLoaderFactoryExtended.COBOL_LOADER);
//            }
//            numericFormat.setSelectedIndex(getIntToken(t));
//            fieldSeparator.setSelectedIndex(getIntToken(t));
//            quote.setSelectedIndex(getIntToken(t));
        } catch (Exception e) {
            e.printStackTrace();
        }
        setEditable();

		return false;
	}

	private void setupFields() {

		if (copybookFile == null) {
//			List<FileTreeComboItem> itms
//				= CommonCode.getFileList(Parameters.RECENT_COPYBOOK_DIRS, NUMBER_OF_COMBO_ENTRIES, true);
			String s;
//			List<String> dirs = Parameters.getStringList(Parameters.RECENT_COPYBOOK_DIRS, NUMBER_OF_COMBO_ENTRIES);
//
//			ArrayList<FileTreeComboItem> itms = new ArrayList<FileTreeComboItem>(dirs.size());
//			HashSet<String> used = new HashSet<String>(dirs.size() * 3 / 2);
//			int i = 0;
//			File f;
//			for (String dir : dirs) {
//				if (! used.contains(dir) && ! "".equals(dir.trim())
//				&& (f = new File(dir)).exists() && f.isDirectory()) {
//					itms.add(new FileTreeComboItem(i++, f));
//					used.add(dir);
//				}
//			}


			UpdatableTextValue updateTxt = new UpdatableTextValue() {
				@Override public void setText(String text) {
					updateDirectory(text);
					copybookFile.setText(text);
				}

				@Override public String getText() {
					return copybookFile.getText();
				}
			};
			copybookDir = new FileSelectCombo(Parameters.COBOL_COPYBOOK_LIST, 25, true, true) ;
			//= new TreeComboFileSelect(true, true, true, itms);
			copybookFile = new ZOld_TreeComboFileSelect2(
					true, false, new ArrayList<FileTreeComboItem>(), updateTxt, false, (JButton[]) null);

			s = Common.OPTIONS.DEFAULT_COPYBOOK_DIRECTORY.get();
			
			TreeComboItem itm = copybookDir.getFirstItem();
			if (itm != null && itm instanceof FileTreeComboItem) {
				s = ((FileTreeComboItem)itm).getFullname();
			}
			copybookDir.setText(s);

			copybookDir.addTextChangeListner(new ChangeListener() {
				@Override public void stateChanged(ChangeEvent e) {
					setupCopybookCombo();
				}
			});

			setupCopybookCombo();
		}
	}

	private void updateDirectory(String text) {

		if (text != null && (! "".equals(text)) && ! text.startsWith(copybookDir.getText())) {
			File f = new File(text);
			File copybookDirFile = new File(copybookDir.getText());
			File parent;
			while ((parent = f.getParentFile()) != null
				&& parent.getName() != null
				&& !"".equals(parent.getName())) {

				if (parent.equals(copybookDirFile)) {
					return;
				}
			}

			ListIterator<FileTreeComboItem> list = copybookDir.fileListIterator();
			FileTreeComboItem ci;
			while (list.hasNext()) {
				ci = list.next();
				if (text.startsWith(ci.getFullname())) {
					copybookDir.setText(ci.getFullname());
					return;
				}
			}

			copybookDir.setText(f.getParent());
		}
	}

//	private static String getReducedName(File f) {
//		StringBuffer s = new StringBuffer(f.getName());
//		f = f.getParentFile();
//		String parentName;
//
//		while (s.length() < 30 && (parentName = f.getName()) != null && ! "".equals(parentName))  {
//			s.insert(0, parentName + "/");
//			f = f.getParentFile();
//		}
//
//		return s.toString();
//	}

	private void setupCopybookCombo() {

		File copybookDirFile = new File(copybookDir.getText());

		if (copybookDirFile.exists() && copybookDirFile.isDirectory()
		&& (! copybookDirFile.equals(lastCopybookDir))) {
			List<TreeComboItem> l = bldComboTree(copybookDirFile, copybookDirFile, 1);
			//System.out.println("***) "  + l.size());
			if (l.size() > 0) {
				copybookFile.setTree(l.toArray(new TreeComboItem[l.size()]));
			}


//			CommonCode.saveList(Parameters.COBOL_COPYBOOK_DIRS, copybookDir.fileListIterator());
			Parameters.writeProperties();
//			ListIterator<FileTreeComboItem> list = copybookDir.fileListIterator();
//			int i = 0;
//
//			Parameters.setSavePropertyChanges(false);
//			while (list.hasNext()) {
//				FileTreeComboItem itm = list.next();
//				Parameters.setArrayItem(Parameters.RECENT_COPYBOOK_DIRS, i++, itm.getFullname());
//			}
//			Parameters.setSavePropertyChanges(true);
//			Parameters.writeProperties();

			lastCopybookDir = copybookDirFile;
		}
	}


	private List<TreeComboItem> bldComboTree(File treeParent, File f, int idx) {
		if (f.exists() && f.isDirectory()) {
			File[] files = f.listFiles();
			Arrays.sort(files, new Comparator<File>() {
				@Override public int compare(File f1, File f2) {
					if (f1.isDirectory() == f2.isDirectory()) {
						f2.getName().compareTo(f1.getName());
					} else if (f1.isDirectory()){
						return -11;
					}
					return 1;
				}
			});

			List<TreeComboItem> cis = new ArrayList<TreeComboItem>(files.length);

			for (File file : files) {
				String s = f.getName();
				if (s.endsWith("~") || s.toLowerCase().endsWith(".bak")) {
				} else if (file.isDirectory()) {
					int holdIdx = idx;
					List<TreeComboItem> l = bldComboTree(treeParent, file, idx + 1);
					if (l.size() > 0) {
						cis.add(new TreeComboItem(holdIdx, s, s, l.toArray(new TreeComboItem[l.size()])));
						idx = l.get(l.size() - 1).getLastKey() + 1;
					}
				} else {
					cis.add(new FileTreeComboItem(idx++, treeParent, f, file));
				}
			}
			return cis;
		}

		return new ArrayList<TreeComboItem>(0);
	}



//	/**
//	 * Set the Copybook-Loader option based on the Copybook File Selected
//	 */
//	private void checkCopybookType() {
//		String fname = copybookFile.getText();
//
//		if (fname != null && ! fname.equals(lastCopybookName)) {
////			int loaderType  = CopybookLoaderFactoryExtended.getLoaderType(fname);
////
////			if (loaderType >= 0) {
////				loaderOptions.setSelectedIndex(loaderType);
////			}
//
//			lastCopybookName = fname;
//		}
//	}
//
//	private int getFileStructure() {
//		return CopybookLoaderFactoryExtended.getLoaderType(getFileName());
//	}

	private String getFileName() {
		//return copybookDir.getText() + Common.FILE_SEPERATOR + copybookFile.getText();
		return copybookFile.getText();
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

		return Common.OPTIONS.DEFAULT_COPYBOOK_DIRECTORY.get() + name + ".Xml";
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


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.openFile.AbstractLayoutSelection#isFileBasedLayout()
	 */
	@Override
	public boolean isFileBasedLayout() {
		return true;
	}
}
