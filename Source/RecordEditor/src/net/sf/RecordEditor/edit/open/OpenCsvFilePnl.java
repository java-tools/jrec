package net.sf.RecordEditor.edit.open;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.sf.JRecord.ByteIO.ByteTextReader;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.IO.AbstractLineIOProvider;
import net.sf.RecordEditor.edit.file.FileView;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.csv.CheckEncoding;
import net.sf.RecordEditor.utils.csv.CsvSelectionPanel;
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
	private final static byte[][] oneBlankLine = {{}};
	private final static int NORMAL_CSV  = 0,
							 UNICODE_CSV = 1;
	
	private JFileChooser chooser = new JFileChooser();
	//private CsvSelectionPanel csvDetails = new CsvSelectionPanel(false);
	protected RecentFiles recent;
	private RecentFilesList recentList;
	private	ByteTextReader r = new ByteTextReader();
	
	private JTabbedPane tab = new JTabbedPane();
	private JTextArea msgField = new JTextArea();
	private AbstractLineIOProvider ioProvider;
	private CsvSelectionPanel csvDetails = new CsvSelectionPanel(oneBlankLine, "", false, "File Preview", msgField);
	private CsvSelectionPanel unicodeCsvDetails = new CsvSelectionPanel(oneBlankLine[0], "", false, "Unicode File Preview", msgField);
	
	private CsvSelectionPanel[] csvPanels = {csvDetails, unicodeCsvDetails};

	   
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
			chooser.getActionForKeyStroke(
					KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0))
			   .actionPerformed(null);
			readFilePreview(chooser.getSelectedFile(), false);
		}
	};
	
	public OpenCsvFilePnl(
			final String fileName,
			final String propertiesFiles, 
			AbstractLineIOProvider pIoProvider) {
		ioProvider = pIoProvider;
		recent = new RecentFiles(propertiesFiles, this);
		recentList = new RecentFilesList(recent, this);
		
		String fname = fileName;
		if (fname == null || "".equals(fname)) {
			fname = Common.OPTIONS.DEFAULT_FILE_DIRECTORY.get();
		}

		chooser.setSelectedFile(new File(fname));
		
		chooser.setControlButtonsAreShown(false);
		
		FileFilter filter = chooser.getFileFilter();
		chooser.addChoosableFileFilter(new FileNameExtensionFilter("Text file", "txt"));
		chooser.addChoosableFileFilter(new FileNameExtensionFilter("CSV file", "csv", "tsv"));
		chooser.setFileFilter(filter);
		
		setTab("Normal",  csvDetails);
		setTab("Unicode", unicodeCsvDetails);
		SwingUtils.addKeyListnerToContainer(chooser, keyListner);
		SwingUtils.addKeyListnerToContainer(tab, keyListner);

		chooser.addPropertyChangeListener(chgListner);
		tab.addChangeListener(tabListner);
	}
	
	private void setTab(String tabName, CsvSelectionPanel pnl) {
		
		
		pnl.go.setText("Edit");
		pnl.go.addActionListener(goAction);
		
		tab.add(tabName, pnl);
	}
	
	
	private void readFilePreview(File f, boolean allowSwap2Unicode) {

		if ((f != null) && (f.isFile()) ) {
			String fileName = f.getPath();

			try {
				byte[] data = readUnicodeFile(fileName);
				String charSet = CheckEncoding.determineCharSet(data);
				
				if (tab.getSelectedIndex() == NORMAL_CSV) { 
					if (allowSwap2Unicode && ! "".equals(charSet)) {
						unicodeCsvDetails.fontTxt.setText(charSet);
						unicodeCsvDetails.setData(data, false);
						tab.setSelectedIndex(UNICODE_CSV);					
					} else {
						setNormalCsv(data);
					}
				} else {
					if (allowSwap2Unicode && "".equals(charSet)) {
						setNormalCsv(data);
						tab.setSelectedIndex(NORMAL_CSV);					
					} else {
						unicodeCsvDetails.fontTxt.setText(charSet);
						unicodeCsvDetails.setData(data, true);
					}
				}
			} catch (IOException ex) {
				Common.logMsg("Error Reading File", ex);
			}
		}
	}
	
	private void setNormalCsv(byte[] data) throws IOException {
		byte[] line;
		byte[][] lines = new byte[30][];
		int i = 0;
		
		r.open(new ByteArrayInputStream(data));
		while (i < lines.length && (line = r.read()) != null) {
			lines[i++] = line;
		}
		r.close();
		csvDetails.setLines(lines, "", lines.length);
	}
	
	
	private byte[] readUnicodeFile(String fileName) 
	throws IOException {
		byte[] data = new byte[8000];
		FileInputStream in = new FileInputStream(fileName);
	    int num = in.read(data);
	    int total = num;

	    while (num >= 0 && total < data.length) {
	        num = in.read(data, total, data.length - total);
	        total += Math.max(0, num);
	    }
	    in.close();
	    
	    if (total < data.length) {
	    	byte[] t = new byte[total];
	    	System.arraycopy(data, 0, t, 0, total);
	    	data = t;
	    }
	    return data;
	}
	
	private void openFile() {
		File f = chooser.getSelectedFile(); 
		
		CsvSelectionPanel csvpnl = csvPanels[tab.getSelectedIndex()];
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
		addComponent(1, 5, BasePanel.FILL, BasePanel.GAP,
		        BasePanel.FULL, BasePanel.FULL,
		        new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, chooser, tab));
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
					csvDetails.setFileDescription(layoutName);
					tab.setSelectedIndex(NORMAL_CSV);
				} else {
					unicodeCsvDetails.setFileDescription(layoutName);
					tab.setSelectedIndex(UNICODE_CSV);	
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
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
