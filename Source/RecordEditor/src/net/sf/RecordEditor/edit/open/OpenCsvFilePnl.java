package net.sf.RecordEditor.edit.open;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

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
import net.sf.RecordEditor.edit.file.FileView;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.csv.CsvSelectionPanel;
import net.sf.RecordEditor.utils.csv.CsvTabPane;
import net.sf.RecordEditor.utils.openFile.FormatFileName;
import net.sf.RecordEditor.utils.openFile.OpenFileInterface;
import net.sf.RecordEditor.utils.openFile.RecentFiles;
import net.sf.RecordEditor.utils.openFile.RecentFilesList;
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

	private JTextArea msgField = new JTextArea();
	private AbstractLineIOProvider ioProvider;
	private CsvTabPane csvTabDtls = new CsvTabPane(msgField);
	
	
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
		  		csvTabDtls.readFilePreview(f, true);
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
				chooser.getActionForKeyStroke(
						KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0))
				   .actionPerformed(null);
				
				csvTabDtls.tab.setSelectedIndex(idx);
				csvTabDtls.readFilePreview(chooser.getSelectedFile(), false);
				csvTabDtls.tab.addChangeListener(this);
		}
	};
	
	public OpenCsvFilePnl(
			final String fileName,
			final String propertiesFiles, 
			AbstractLineIOProvider pIoProvider) {
		ioProvider = pIoProvider;
		recent = new RecentFiles(propertiesFiles, this);
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
		registerComponent(chooser);
		registerComponent(csvTabDtls.tab);
		
		
		file = new File(fname);
		
		chooser.setControlButtonsAreShown(false);
		
		try {
			FileFilter filter = chooser.getFileFilter();
			chooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text file", "txt"));
			chooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("CSV file", "csv", "tsv"));
			chooser.setFileFilter(filter);
		} catch (NoClassDefFoundError e) {

		}
		

		SwingUtils.addKeyListnerToContainer(chooser, keyListner);
		SwingUtils.addKeyListnerToContainer(csvTabDtls.tab, keyListner);

		chooser.setSelectedFile(file);
		chooser.addPropertyChangeListener(chgListner);
		csvTabDtls.tab.addChangeListener(tabListner);
		
		if (filePresent) {
			csvTabDtls.readFilePreview(file, true);
		}
	}
	
	private void setTab(CsvSelectionPanel pnl, String helpname) {
		
		pnl.setHelpURL(helpname);

		pnl.go.setIcon(Common.getRecordIcon(Common.ID_OPEN_ICON));

		pnl.go.setText("Edit");
		pnl.go.addActionListener(goAction);
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
				csvTabDtls.readFilePreview(f, true);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		CsvSelectionPanel csvpnl = csvTabDtls.getSelectedCsvDetails();
		if (f != null && f.getPath() != null) {
			try {
				LayoutDetail l = csvpnl.getLayout(csvpnl.fontTxt.getText(), r.getEol());
				
				FileView<LayoutDetail> file 
					= new FileView<LayoutDetail>(
								l,
								ioProvider,
								false);
				StartEditor startEditor = new StartEditor(file, f.getPath(), false, msgField, 0);
				
				recent.putFileLayout(f.getPath(), csvpnl.getFileDescription());
				
				startEditor.doEdit();				
			} catch (Exception e) {
				Common.logMsg("Error Loading File: " + e.getMessage(), null);
				e.printStackTrace();
			}
			
		}
	}
	
	
	@Override
	public void done() {
		addComponent(0, 5, BasePanel.FILL, BasePanel.GAP,
		        BasePanel.FULL, BasePanel.FULL,
		        new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, chooser, csvTabDtls.tab));
		addMessage(msgField);
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
}