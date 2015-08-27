package net.sf.RecordEditor.edit.open;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;

import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.IO.AbstractLineIOProvider;
import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.openFile.FormatFileName;
import net.sf.RecordEditor.re.openFile.OpenFileInterface;
import net.sf.RecordEditor.re.openFile.RecentFiles;
import net.sf.RecordEditor.re.openFile.RecentFilesList;
import net.sf.RecordEditor.re.util.csv.CsvSelectionPanel;
import net.sf.RecordEditor.re.util.csv.CsvTabPane;
import net.sf.RecordEditor.re.util.csv.FilePreview;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.params.BoolOpt;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.filechooser.IFileChooserWrapper;
import net.sf.RecordEditor.utils.swing.filechooser.JRFileChooserWrapper;

@SuppressWarnings("serial")
public class OpenCsvFilePnl
extends BaseHelpPanel implements OpenFileInterface, FormatFileName {


    private final static int NORMAL_CSV  = 0,
                             UNICODE_CSV = 1;

	private final BoolOpt showCsvFChooserOptions = new BoolOpt(
			Parameters.CSV_SHOW_FILECHOOSER_OPTIONS,
			Parameters.isWindowsLAF());

//    private JFileChooser chooser = new JFileChooser();
    private final IFileChooserWrapper chooser;
    protected RecentFiles recent;
    private RecentFilesList recentList;
//    private	ByteTextReader reader = new ByteTextReader();

    private JTextArea msgTxt = new JTextArea();
    private AbstractLineIOProvider ioProvider;
    private final CsvTabPane csvTabDtls; // = new CsvTabPane(msgTxt, true, true);
    private FileFilter csvFilter;



    //private File file;
    private boolean execEnter = true;

    public final KeyAdapter keyListner = new KeyAdapter() {
        /**
         * @see java.awt.event.KeyAdapter#keyReleased
         */
        public final void keyReleased(KeyEvent event) {

            if (execEnter && event.getKeyCode() == KeyEvent.VK_ENTER) {
                //System.out.println("Key Listner");
                openFile(false);
             }
        }
    };

    private PropertyChangeListener chgListner = new PropertyChangeListener() {
        private String lastFileName = "";

        @Override
        public void propertyChange(PropertyChangeEvent e) {
            String pname = e.getPropertyName();
            //System.out.println("\nProperty Change " + pname
            //		+ " " + JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(pname));
            if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(pname)) {
              File f = (File) e.getNewValue();

              if ((f != null) && (f.isFile()) &&  (! lastFileName.equals(f.getPath()))) {
                  lastFileName = f.getPath();
                  readFilePreview(f, true);
              }
            }
        }
    };

    private ActionListener goAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                //System.out.println("\nGo Action " + arg0.paramString());
                openFile(true);
            }
    };

    private ActionListener chooserListner = new ActionListener() {

        /* (non-Javadoc)
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(ActionEvent evt) {

            if (JFileChooser.APPROVE_SELECTION.equals(evt.getActionCommand())) {
                //System.out.println("\nChooser Listner");
                openFile(false);
            } else if (JFileChooser.CANCEL_SELECTION.equals(evt.getActionCommand())) {

            }
        }
    };

    private ChangeListener tabListner = new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
            //chooser.dispatchEvent(null);

                csvTabDtls.tab.removeChangeListener(this);
                int idx = csvTabDtls.tab.getSelectedIndex();
                //System.out.println("\nTab changed ");

                execEnter();

//				try {
//					execEnter = false;
//					execEnter();
//				} catch (Exception ex) {
//					//ex.printStackTrace();
//				}
                execEnter = true;

                csvTabDtls.tab.setSelectedIndex(idx);
                readFilePreview(chooser.getFileChooser().getSelectedFile(), false);
                csvTabDtls.tab.addChangeListener(this);
            }
    };

    public OpenCsvFilePnl(
            final String fileName,
            final String propertiesFiles,
            AbstractLineIOProvider pIoProvider,
            boolean fixedXmlTabs) {
        String defaultDirectory = Common.OPTIONS.DEFAULT_FILE_DIRECTORY.get();

        ioProvider = pIoProvider;
 		recent = new RecentFiles(propertiesFiles, this, true, defaultDirectory);
        recentList = new RecentFilesList(recent, this, true);
        csvTabDtls = new CsvTabPane(msgTxt, fixedXmlTabs, true);

        boolean filePresent = true;
        //File file;
        String fname = fileName;
        URL helpname = Common.formatHelpURL(Common.HELP_CSV_EDITOR);
        if (fname == null && Common.OPTIONS.useLastDir.isSelected()) {
        	fname = recent.getLastDirectory();
        }
        if (fname == null || "".equals(fname)) {
            fname = defaultDirectory;
            filePresent = false;
        }

        File file = null;
        if (fname != null) {
	        file = adjustForDirectory(fname);
 	        
	        fname = file.getPath();
        }

        chooser = JRFileChooserWrapper.newChooserCsvEditor(ReFrame.getDesktopWidth(), fname, recent.getDirectoryList());


        setHelpURLre(helpname);
        setTab(csvTabDtls.csvDetails, helpname);
        setTab(csvTabDtls.unicodeCsvDetails, helpname);

        if (fixedXmlTabs) {
            setTab(csvTabDtls.fixedSelectionPanel, helpname);
            setTab(csvTabDtls.xmlSelectionPanel, helpname);
            setTab(csvTabDtls.otherSelectionPanel, helpname);
        }

        registerComponentRE(chooser.getDisplayItem());
        registerComponentRE(csvTabDtls.tab);


    	JFileChooser fc = chooser.getFileChooser();
        fc.addActionListener(chooserListner);

        try {
            FileFilter filter = fc.getFileFilter();

            csvFilter = new javax.swing.filechooser.FileNameExtensionFilter("CSV file", "csv", "tsv");
            fc.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text file", "txt"));
            fc.addChoosableFileFilter(csvFilter);
            fc.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("CSV/Text file", "csv", "tsv", "txt"));

            if (fixedXmlTabs) {
                fc.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("CSV/XML file", "csv", "tsv", "xml"));
                fc.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Xml file", "xml"));
            }
            fc.setFileFilter(filter);
        } catch (NoClassDefFoundError e) {

        }


        SwingUtils.addKeyListnerToContainer(chooser.getDisplayItem(), keyListner);
        SwingUtils.addKeyListnerToContainer(csvTabDtls.tab, keyListner);

        fc.addPropertyChangeListener(chgListner);
        if (showCsvFChooserOptions.isSelected()) {
        	fc.setDialogType(JFileChooser.OPEN_DIALOG);
        } else {
            fc.setControlButtonsAreShown(false);
        }
        csvTabDtls.tab.addChangeListener(tabListner);

        if (filePresent && fname != null && file.isFile()) {
            readFilePreview(file, true);
        }
    }

    private void setTab(FilePreview pnl, URL helpname) {
        JButton go = pnl.getGoButton();

        pnl.getPanel().setHelpURLre(helpname);

        go.setIcon(Common.getRecordIcon(Common.ID_OPEN_ICON));

        go.setText(LangConversion.convert(LangConversion.ST_BUTTON, "Edit"));
        go.addActionListener(goAction);
    }


    private void openFile(boolean executeEnter) {
    	JFileChooser fc = chooser.getFileChooser();
        File f = fc.getSelectedFile();
        try {
            //System.out.println("openFile " + executeEnter);
            if (executeEnter) {
                execEnter();
            }
            File f1 = fc.getSelectedFile();
            if (! f1.equals(f)) {
                f = f1;
                readFilePreview(f, true);
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        FilePreview csvpnl = csvTabDtls.getSelectedCsvDetails();
        if (f != null && f.getPath() != null) {
            try {
                LayoutDetail l = csvpnl.getLayout(csvpnl.getFontName(), null);

                if (l != null) {
                    FileView file
                        = new FileView(
                                    l,
                                    ioProvider,
                                    false);
                    StartEditor startEditor = new StartEditor(file, f.getPath(), false, msgTxt, 0);

                    recent.putFileLayout(f.getPath(), csvpnl.getFileDescription());
                    chooser.updateRecentDirectories(recent.getDirectoryList());

                    startEditor.doEdit();
                }
            } catch (Exception e) {
                Common.logMsg(AbsSSLogger.ERROR, "Error Loading File:", e.getMessage(), null);
                e.printStackTrace();
            }

        }
    }

    private void execEnter() {

    	JFileChooser fc = chooser.getFileChooser();
        fc.removeActionListener(chooserListner);
        SwingUtils.clickOpenBtn(fc, true);

        chooser.addActionListener(chooserListner);
    }

    @Override
    public void done() {
        addComponentRE(0, 5, BasePanel.FILL, BasePanel.GAP,
                BasePanel.FULL, BasePanel.FULL,
                new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, chooser.getDisplayItem(), csvTabDtls.tab));
        addMessage(msgTxt);
        setHeightRE(BasePanel.NORMAL_HEIGHT * 3);

        super.done();
    }



    @Override
    public int getCurrentDbIdentifier() {
        return 0;
    }



    @Override
    public String getCurrentDbName() {
        return null;
    }



    @Override
    public String getCurrentFileName() {
        return chooser.getFileChooser().getSelectedFile().getPath();
    }



    @Override
    public void setRecordLayout(int layoutId, String layoutName, String filename) {
    	JFileChooser fc = chooser.getFileChooser();
    	try {
			fc.removeActionListener(chooserListner);
			fc.removePropertyChangeListener(chgListner);
			csvTabDtls.tab.removeChangeListener(tabListner);

			File file = adjustForDirectory(filename);
			fc.setSelectedFile(file);
    		if (layoutName == null || "".equals(layoutName)) {

    		} else {
    			if (layoutName.startsWith(CsvSelectionPanel.NORMAL_CSV_STRING)) {
    				csvTabDtls.csvDetails.setFileDescription(layoutName);
    				csvTabDtls.tab.setSelectedIndex(NORMAL_CSV);
    			} else {
    				csvTabDtls.unicodeCsvDetails.setFileDescription(layoutName);
    				csvTabDtls.tab.setSelectedIndex(UNICODE_CSV);
    			}
    			csvTabDtls.readCheckPreview(file, false, layoutName);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		chooser.addActionListener(chooserListner);
    		fc.addPropertyChangeListener(chgListner);
    		csvTabDtls.tab.addChangeListener(tabListner);
    	}
    }



    @Override
    public BasePanel getPanel() {
        return this;
    }

    public final void selectCsvExt() {
        if (csvFilter != null) {
            chooser.getFileChooser().setFileFilter(csvFilter);
        }
    }
    
    private File adjustForDirectory(String filenmae) {
    	File file = new File(filenmae);
    	if (file.isDirectory()) {
    		String pathname = file.getPath() + Common.FILE_SEPERATOR +  "*";
			file = new File(pathname);
    	}
    	return file;
    }

    private void readFilePreview(File f, boolean allowTabSwap) {
        String layoutDtls = null;
        try {
            layoutDtls = recent.getLayoutName(f.getCanonicalPath(), f);
        } catch (Exception e) {
            // TODO: handle exception
        }
        Rectangle r = this.getBounds();
        this.setPreferredSize(new Dimension(r.width, r.height));
//		System.out.println(" !%%! 1 " + this.getPreferredSize().height + " " + this.getBounds().height
//				+ " " + this.getHeight());
        csvTabDtls.readCheckPreview(f, allowTabSwap, layoutDtls);
//		this.setBounds(r);
        super.validate();

//		System.out.println(" !%%! 2 " + this.getPreferredSize().height + " " + this.getBounds().height
//				+ " " + this.getHeight());
    }

    @Override
    public JMenu getRecentFileMenu() {
        return recentList.getMenu();
    }
    

	@Override
	public JMenu getRecentDirectoryMenu() {
		return recentList.getDirectoryMenu();
	}



    /* (non-Javadoc)
     * @see net.sf.RecordEditor.utils.openFile.FormatFileName#formatLayoutName(java.lang.String)
     */
    @Override
    public String formatLayoutName(String layoutName) {
        return layoutName;
    }

    public void setParentFrame(ReFrame parentFrame) {
        csvTabDtls.setParentFrame(parentFrame);
    }
}
