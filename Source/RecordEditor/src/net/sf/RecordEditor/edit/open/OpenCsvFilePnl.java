package net.sf.RecordEditor.edit.open;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;

import net.sf.JRecord.ByteIO.ByteTextReader;
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
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;

@SuppressWarnings("serial")
public class OpenCsvFilePnl
extends BaseHelpPanel implements OpenFileInterface, FormatFileName {

	private final static int NORMAL_CSV  = 0,
							 UNICODE_CSV = 1;

	private JFileChooser chooser = new JFileChooser();
	protected RecentFiles recent;
	private RecentFilesList recentList;
	private	ByteTextReader r = new ByteTextReader();

	private JTextArea msgTxt = new JTextArea();
	private AbstractLineIOProvider ioProvider;
	private CsvTabPane csvTabDtls = new CsvTabPane(msgTxt, true);


	//private File file;


    public final KeyAdapter keyListner = new KeyAdapter() {
        /**
         * @see java.awt.event.KeyAdapter#keyReleased
         */
        public final void keyReleased(KeyEvent event) {

        	if (event.getKeyCode() == KeyEvent.VK_ENTER) {
        		openFile();
         	}
        }
    };

	private PropertyChangeListener chgListner = new PropertyChangeListener() {
		private String lastFileName = "";

		@Override
		public void propertyChange(PropertyChangeEvent e) {
		    String pname = e.getPropertyName();
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
				openFile();
			}
	};

	private ChangeListener tabListner = new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
			//chooser.dispatchEvent(null);

				csvTabDtls.tab.removeChangeListener(this);
				int idx = csvTabDtls.tab.getSelectedIndex();

				try {
					chooser.getActionForKeyStroke(
							KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0))
					   .actionPerformed(null);
				} catch (Exception ex) {
					//ex.printStackTrace();
				}

				csvTabDtls.tab.setSelectedIndex(idx);
				readFilePreview(chooser.getSelectedFile(), false);
				csvTabDtls.tab.addChangeListener(this);
		}
	};

	public OpenCsvFilePnl(
			final String fileName,
			final String propertiesFiles,
			AbstractLineIOProvider pIoProvider) {
		ioProvider = pIoProvider;
		recent = new RecentFiles(propertiesFiles, this, true);
		recentList = new RecentFilesList(recent, this);

		boolean filePresent = true;
		File file;
		String fname = fileName;
		String helpname = Common.formatHelpURL(Common.HELP_CSV_EDITOR);
		if (fname == null || "".equals(fname)) {
			fname = Common.OPTIONS.DEFAULT_FILE_DIRECTORY.get();
			filePresent = false;
		}

		setHelpURL(helpname);
		setTab(csvTabDtls.csvDetails, helpname);
		setTab(csvTabDtls.unicodeCsvDetails, helpname);
		setTab(csvTabDtls.xmlSelectionPanel, helpname);
		setTab(csvTabDtls.fixedSelectionPanel, helpname);

		registerComponent(chooser);
		registerComponent(csvTabDtls.tab);


		file = new File(fname);

		chooser.setControlButtonsAreShown(false);

		try {
			FileFilter filter = chooser.getFileFilter();
			chooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text file", "txt"));
			chooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("CSV file", "csv", "tsv"));
			chooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Xml file", "xml"));
			chooser.setFileFilter(filter);
		} catch (NoClassDefFoundError e) {

		}


		SwingUtils.addKeyListnerToContainer(chooser, keyListner);
		SwingUtils.addKeyListnerToContainer(csvTabDtls.tab, keyListner);

		chooser.setSelectedFile(file);
		chooser.addPropertyChangeListener(chgListner);
		csvTabDtls.tab.addChangeListener(tabListner);

		if (filePresent) {
			readFilePreview(file, true);
		}
	}

	private void setTab(FilePreview pnl, String helpname) {
		JButton go = pnl.getGoButton();

		pnl.getPanel().setHelpURL(helpname);

		go.setIcon(Common.getRecordIcon(Common.ID_OPEN_ICON));

		go.setText(LangConversion.convert(LangConversion.ST_BUTTON, "Edit"));
		go.addActionListener(goAction);
	}




	private void openFile() {
		File f = chooser.getSelectedFile();
		try {
			chooser.getActionForKeyStroke(
							KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0))
					   .actionPerformed(null);
			File f1 = chooser.getSelectedFile();
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
				LayoutDetail l = csvpnl.getLayout(csvpnl.getFontName(), r.getEol());

				if (l != null) {
					FileView<LayoutDetail> file
						= new FileView<LayoutDetail>(
									l,
									ioProvider,
									false);
					StartEditor startEditor = new StartEditor(file, f.getPath(), false, msgTxt, 0);

					recent.putFileLayout(f.getPath(), csvpnl.getFileDescription());

					startEditor.doEdit();
				}
			} catch (Exception e) {
				Common.logMsg(AbsSSLogger.ERROR, "Error Loading File:", e.getMessage(), null);
				e.printStackTrace();
			}

		}
	}


	@Override
	public void done() {
		addComponent(0, 5, BasePanel.FILL, BasePanel.GAP,
		        BasePanel.FULL, BasePanel.FULL,
		        new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, chooser, csvTabDtls.tab));
		addMessage(msgTxt);
		setHeight(BasePanel.NORMAL_HEIGHT * 3);

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
		return chooser.getSelectedFile().getPath();
	}



	@Override
	public void setRecordLayout(int layoutId, String layoutName, String filename) {
		try {
			chooser.setSelectedFile(new File(filename));

			if (layoutName != null && ! "".equals(layoutName)) {
				if (layoutName.startsWith(CsvSelectionPanel.NORMAL_CSV_STRING)) {
					csvTabDtls.csvDetails.setFileDescription(layoutName);
					csvTabDtls.tab.setSelectedIndex(NORMAL_CSV);
				} else {
					csvTabDtls.unicodeCsvDetails.setFileDescription(layoutName);
					csvTabDtls.tab.setSelectedIndex(UNICODE_CSV);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	@Override
	public BasePanel getPanel() {
		return this;
	}


	private void readFilePreview(File f, boolean allowTabSwap) {
		String layoutDtls = null;
		try {

			layoutDtls = recent.getLayoutName(f.getCanonicalPath(), f);
		} catch (Exception e) {
			// TODO: handle exception
		}
		csvTabDtls.readFilePreview(f, allowTabSwap, layoutDtls);
	}

	@Override
	public JMenu getRecentFileMenu() {
		return recentList.getMenu();
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
