package net.sf.RecordEditor.re.util.csv;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import javax.swing.JTabbedPane;
import javax.swing.text.JTextComponent;

import net.sf.JRecord.Common.Conversion;
import net.sf.RecordEditor.layoutWizard.FileAnalyser;
import net.sf.RecordEditor.re.openFile.FormatFileName;
import net.sf.RecordEditor.re.util.fw.FixedWidthSelectionPane;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.StreamUtil;
import net.sf.RecordEditor.utils.screenManager.ReFrame;


/**
 *
 * @author Bruce Martin
 *
 * Class that holds a JTabbedPane of Normal / Unicode
 * CSV preview panels.
 */

public class CsvTabPane implements FormatFileName {
	private static final int PREVIEW_SIZE = 0x80000;

	public final static int NORMAL_CSV  = 0,
							UNICODE_CSV = 1,
							FIXED_WIDTH = 2,
							SCHEMA = 3,
							XML_FILE = 4,
							OTHER_FILE = 5;

	private final static byte[][] oneBlankLine = {{}};

	//private	ByteTextReader r = new ByteTextReader();

	public final JTabbedPane tab = new JTabbedPane();

	public final CsvSelectionPanel csvDetails;
	public final CsvSelectionPanel unicodeCsvDetails;
	public final XmlSelectionPanel xmlSelectionPanel;
	public final FixedWidthSelectionPane fixedWidthPanel;
	public final SchemaSelection schemaSelectionPanel;
	public final OtherSelection otherSelectionPanel;

	private JTextComponent msgFld;

	private FilePreview[] csvPanels = {null, null, null, null, null, null};

	private final boolean fixedTab, xmlTab;

	private ReFrame parentFrame = null;


	/**
	 * Class that holds a JTabbedPane of Normal / Unicode
	 * CSV preview panels.
	 * @param msgField message field to display error messages on
	 */
	public CsvTabPane(JTextComponent msgField, boolean allowNonCsvTabs, boolean adjustableTblHeight) {
		this(msgField, allowNonCsvTabs, allowNonCsvTabs, adjustableTblHeight);
	}

	/**
	 * Class that holds a JTabbedPane of Normal / Unicode
	 * CSV preview panels.
	 * @param msgField message field to display error messages on
	 */
	public CsvTabPane(JTextComponent msgField, boolean allowFixed, boolean allowXml, boolean adjustableTblHeight) {

		msgFld = msgField;
		fixedTab = allowFixed;
		xmlTab = allowXml;


		csvDetails        = new CsvSelectionPanel(oneBlankLine, "", false, "File Preview", msgField, adjustableTblHeight);
		unicodeCsvDetails = new CsvSelectionPanel(oneBlankLine[0], "", false, "Unicode File Preview", msgField, adjustableTblHeight);

		csvPanels[NORMAL_CSV]  = csvDetails;
		csvPanels[UNICODE_CSV] = unicodeCsvDetails;

		tab.add("Normal",  csvDetails.getPanel());
		tab.add("Unicode", unicodeCsvDetails.getPanel());

		if (fixedTab) {
			fixedWidthPanel = FixedWidthSelectionPane.newPane();
			schemaSelectionPanel = new SchemaSelection(msgFld);
			//csvPanels[XML_FILE] = new XmlSelectionPanel("Xml Preview", msgField);
			//tab.add("Xml", xmlPanel);
			//csvPanels[XML_FILE].getPanel().setVisible(false);

			csvPanels[FIXED_WIDTH] = fixedWidthPanel;
			csvPanels[SCHEMA] = schemaSelectionPanel;
			tab.add("Fixed Width", csvPanels[FIXED_WIDTH].getPanel());
			tab.add("Schema", csvPanels[SCHEMA].getPanel());
		} else {
			schemaSelectionPanel = null;
			fixedWidthPanel = null;
		}

		if (xmlTab) {
			xmlSelectionPanel = new XmlSelectionPanel("Xml Preview", msgFld);
			otherSelectionPanel = new OtherSelection(msgFld);
			//csvPanels[XML_FILE] = new XmlSelectionPanel("Xml Preview", msgField);
			//tab.add("Xml", xmlPanel);
			//csvPanels[XML_FILE].getPanel().setVisible(false);

			csvPanels[XML_FILE] = xmlSelectionPanel;
			tab.add("Xml", csvPanels[XML_FILE].getPanel());
			csvPanels[OTHER_FILE] = otherSelectionPanel;
			tab.add("Other", csvPanels[OTHER_FILE].getPanel());
		} else {
			xmlSelectionPanel = null;
			otherSelectionPanel = null;
		}
	}

	public void readOtherTab(String filename, byte[] data) throws IOException {
		try {
			switch (tab.getSelectedIndex()) {
			case NORMAL_CSV:
				unicodeCsvDetails.setData(filename, data, false, null);
				break;
			case UNICODE_CSV:
				setNormalCsv(data);
				break;
			}
		} catch (Exception e) {
			msgFld.setText("Only one tab is set: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void readCheckPreview(File f, boolean allowTabSwap, String layoutId) {

		if ((f != null) && (f.isFile()) ) {
			String fileName = f.getPath();
			if (schemaSelectionPanel != null) {
				schemaSelectionPanel.setFilename(fileName);
			}

			try {
				readCheckPreview(readFile(fileName), allowTabSwap, fileName, layoutId);
			} catch (IOException ex) {
				Common.logMsg("Error Reading File:", ex);
			}
		}
	}

	public void readCheckPreview(byte[] data, boolean allowTabSwap, String filename, String layoutId)
	throws IOException {
		String charSet = CheckEncoding.determineCharSet(data, false).charset;
		boolean couldBeXml = this.xmlTab && maybeXml(data, charSet);

		if (allowTabSwap) {
			if (layoutId != null // && ! "".equals(layoutId)
			&& filename != null && ! filename.toLowerCase().endsWith(".csv")) {
				int count = Math.min(csvPanels.length, tab.getTabCount());
				for (int i = 0; i < count; i++) {
					if (csvPanels[i] != null && csvPanels[i].isMyLayout(layoutId, filename, data)) {
						//Rectangle tabSize =  tab.getBounds();
						//System.out.println(" !! 1 " + tab.getPreferredSize().height + " " + tab.getHeight());
						//csvPanels[i].setData(filename, data, false, layoutId);
						tab.setSelectedIndex(i);
						//tab.setBounds(tabSize);
						//System.out.println(" !! 2 " + tab.getPreferredSize().height + " " + tab.getHeight());
						return;
					}
				}
			}
			if (xmlTab){
				if (couldBeXml) {
					//csvPanels[XML_FILE].getPanel().setVisible(true);
					tab.setSelectedIndex(XML_FILE);
					//System.out.println("Setting Xml Tab ");
//				} else if (tab.getTabCount() >= XML_FILE){
					//csvPanels[XML_FILE].getPanel().setVisible(false);
				} else if (tab.getSelectedIndex() >= XML_FILE) {
					tab.setSelectedIndex(NORMAL_CSV);
				}
			}


			switch (tab.getSelectedIndex()) {
			case NORMAL_CSV:
			case UNICODE_CSV:
			case XML_FILE:
				if (! couldBeXml
				&& xmlTab
				&& Common.OPTIONS.csvSearchFixed.isSelected()
				&& FileAnalyser.getTextPct(data, "") < 50
				&& FileAnalyser.getTextPct(data, charSet) < 50) {
					tab.setSelectedIndex(SCHEMA);
				}
				break;
			case FIXED_WIDTH:
			case SCHEMA:
				if (FileAnalyser.getTextPct(data, "") >= 50
				&& FileAnalyser.getTextPct(data, charSet) >= 50) {
					int idx = UNICODE_CSV;
					if ("".equals(charSet)) {
						idx = NORMAL_CSV;
					}
					tab.setSelectedIndex(idx);
				} else if (tab.getSelectedIndex() == FIXED_WIDTH 
					   && (! fixedWidthPanel.isMyLayout("", filename, data)) ) {
					tab.setSelectedIndex(SCHEMA);
				}
			}
		}


		switch (tab.getSelectedIndex()) {
		case NORMAL_CSV:
			if (allowTabSwap && ! "".equals(charSet)) {
				unicodeCsvDetails.setCharset(charSet);
				unicodeCsvDetails.setData(filename, data, false, layoutId);

				checkNormalCsv(data);
			} else {
				setNormalCsv(data);
			}
			break;
		case UNICODE_CSV:
			if (allowTabSwap && "".equals(charSet)) {
				setNormalCsv(data);
				tab.setSelectedIndex(NORMAL_CSV);
			} else {
				unicodeCsvDetails.setCharset(charSet);
				unicodeCsvDetails.setData(filename, data, false, layoutId);

				if (allowTabSwap) {
					checkNormalCsv(data);
				}
			}
			break;
		case FIXED_WIDTH:
		case SCHEMA:
			//long time1 = System.nanoTime();
			csvPanels[tab.getSelectedIndex()].setData(filename, data, false, layoutId);
			//System.out.println("Time Used: " + (System.nanoTime() - time1) +  " - " + time1);
			break;
		case XML_FILE:
			if (couldBeXml) {
				csvPanels[XML_FILE].setData(filename, data, false, layoutId);

				if (parentFrame != null) {
					parentFrame.pack();

					System.out.println("Done pack ....");
				}
			} else {
				setNormalCsv(data);
				checkNormalCsv(data);
			}
			break;
		case OTHER_FILE:
			otherSelectionPanel.setData(filename, data, false, layoutId);
			break;
		}
	}

//	private void setData(byte[] data, String filename, String layoutId) {
//
//		switch (tab.getSelectedIndex()) {
//		case NORMAL_CSV:
//			setNormalCsv(data);
//			break;
//		case UNICODE_CSV:
//			unicodeCsvDetails.setData(filename, data, false, layoutId);
//
//			break;
//		case FIXED_FILE:
//			//long time1 = System.nanoTime();
//			csvPanels[FIXED_FILE].setData(filename, data, false, layoutId);
//			//System.out.println("Time Used: " + (System.nanoTime() - time1) +  " - " + time1);
//			break;
//		case XML_FILE:
//
//				csvPanels[XML_FILE].setData(filename, data, false, layoutId);
//
//				if (parentFrame != null) {
//					parentFrame.pack();
//				}
//
//			break;
//		}
//
//	}

	private boolean maybeXml(byte[] data, String charSet) {
		byte[] check = Conversion.getBytes("<", charSet);
		if (data == null || data.length < check.length) return false;

		for (int i = 0; i < check.length; i++) {
			if (check[i] != data[i]) {
				return false;
			}
		}
		return true;
	}

	private void checkNormalCsv(byte[] data) throws IOException {
		if (unicodeCsvDetails.getColumnCount() == 1) {
			setNormalCsv(data);
			if (csvDetails.getColumnCount() > 1) {
				tab.setSelectedIndex(NORMAL_CSV);
				return;
			}
		}
		tab.setSelectedIndex(UNICODE_CSV);
	}

	private void setNormalCsv(byte[] data)  {
//		byte[] line;
//		byte[][] lines = new byte[30][];
//		int i = 0;
//
//		r.open(new ByteArrayInputStream(data));
//		while (i < lines.length && (line = r.read()) != null) {
//			lines[i++] = line;
//		}
//		r.close();
//		csvDetails.setLines(lines, "", lines.length);
		csvDetails.setData("", data, false, "");
	}


	private byte[] readFile(String fileName)
	throws IOException {
		byte[] data;
		FileInputStream in = null;
		
		try {
			in = new FileInputStream(fileName);
			if (fileName.toLowerCase().endsWith(".gz")) {
				GZIPInputStream inGZip = new GZIPInputStream(in);
				data = StreamUtil.read(inGZip, 16000);
				inGZip.close();
			} else {
				data = StreamUtil.read(in, PREVIEW_SIZE); //new byte[8000];
			}
		} finally {
			if (in != null) {
				in.close();
			}
		}

	    return data;
	}


//
//	private int toTabIndex(int idx) {
//		return Math.max(idx, tab.getTabCount() - 1);
//	}
//
//
//	private int tabIndexToConst(int idx) {
//		return Math.min(idx, tab.getTabCount() - 1);
//	}
//


	public void setRecordLayout(int layoutId, String layoutName) {
		try {

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


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.openFile.FormatFileName#formatLayoutName(java.lang.String)
	 */
	@Override
	public String formatLayoutName(String layoutName) {
		return layoutName;
	}

	public FilePreview getSelectedCsvDetails() {
		return csvPanels[tab.getSelectedIndex()];
	}

	public void setParentFrame(ReFrame parentFrame) {
		this.parentFrame = parentFrame;
	}
	
	public final void setGoVisible(boolean visible) {
		csvDetails.go.setVisible(visible);
		unicodeCsvDetails.go.setVisible(visible);
	}
//
//	public LayoutDetail getLayout(String font, byte[] recordSep) {
//		return getSelectedCsvDetails().getLayout(font, recordSep);
//	}
}
