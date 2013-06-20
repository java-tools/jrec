package net.sf.RecordEditor.re.util.csv;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JTabbedPane;
import javax.swing.text.JTextComponent;

import net.sf.JRecord.ByteIO.ByteTextReader;
import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.RecordEditor.layoutWizard.FileStructureAnalyser;
import net.sf.RecordEditor.re.openFile.FormatFileName;
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
	public final static int NORMAL_CSV  = 0,
							UNICODE_CSV = 1,
							FIXED_FILE = 2,
							XML_FILE = 3;

	private final static byte[][] oneBlankLine = {{}};

	private	ByteTextReader r = new ByteTextReader();

	public final JTabbedPane tab = new JTabbedPane();

	public final CsvSelectionPanel csvDetails;
	public final CsvSelectionPanel unicodeCsvDetails;
	public final XmlSelectionPanel xmlSelectionPanel;
	public final FixedWidthSelection fixedSelectionPanel;

	private JTextComponent msgFld;

	private FilePreview[] csvPanels = {null, null, null, null};

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
	private CsvTabPane(JTextComponent msgField, boolean allowFixed, boolean allowXml, boolean adjustableTblHeight) {

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
			fixedSelectionPanel = new FixedWidthSelection(msgFld);
			//csvPanels[XML_FILE] = new XmlSelectionPanel("Xml Preview", msgField);
			//tab.add("Xml", xmlPanel);
			//csvPanels[XML_FILE].getPanel().setVisible(false);

			csvPanels[FIXED_FILE] = fixedSelectionPanel;
			tab.add("Fixed Width", csvPanels[FIXED_FILE].getPanel());
		} else {
			fixedSelectionPanel = null;
		}

		if (xmlTab) {
			xmlSelectionPanel = new XmlSelectionPanel("Xml Preview", msgFld);
			//csvPanels[XML_FILE] = new XmlSelectionPanel("Xml Preview", msgField);
			//tab.add("Xml", xmlPanel);
			//csvPanels[XML_FILE].getPanel().setVisible(false);

			csvPanels[XML_FILE] = xmlSelectionPanel;
			tab.add("Xml", csvPanels[XML_FILE].getPanel());
		} else {
			xmlSelectionPanel = null;
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

	public void readFilePreview(File f, boolean allowTabSwap, String layoutId) {

		if ((f != null) && (f.isFile()) ) {
			String fileName = f.getPath();
			if (fixedSelectionPanel != null) {
				fixedSelectionPanel.setFilename(fileName);
			}

			try {
				readFilePreview(readUnicodeFile(fileName), allowTabSwap, fileName, layoutId);
			} catch (IOException ex) {
				Common.logMsg("Error Reading File:", ex);
			}
		}
	}

	public void readFilePreview(byte[] data, boolean allowTabSwap, String filename, String layoutId)
	throws IOException {
		String charSet = CheckEncoding.determineCharSet(data);
		boolean couldBeXml = this.xmlTab && maybeXml(data, charSet);

		if (allowTabSwap) {
			if (layoutId != null && ! "".equals(layoutId)
			&& filename != null && ! filename.toLowerCase().endsWith(".csv")) {
				int count = Math.min(csvPanels.length, tab.getTabCount());
				for (int i = 0; i < count; i++) {
					if (csvPanels[i] != null && csvPanels[i].isMyLayout(layoutId)) {
						//Rectangle tabSize =  tab.getBounds();
						//System.out.println(" !! 1 " + tab.getPreferredSize().height + " " + tab.getHeight());
						csvPanels[i].setData(filename, data, false, layoutId);
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
				}
			}


			switch (tab.getSelectedIndex()) {
			case NORMAL_CSV:
			case UNICODE_CSV:
			case XML_FILE:
				if (! couldBeXml
				&& xmlTab
				&& Common.OPTIONS.csvSearchFixed.isSelected()
				&& FileStructureAnalyser.getTextPct(data, "") < 50
				&& FileStructureAnalyser.getTextPct(data, charSet) < 50) {
					tab.setSelectedIndex(FIXED_FILE);
				}
				break;
			case FIXED_FILE:
				if (FileStructureAnalyser.getTextPct(data, "") >= 50
				&& FileStructureAnalyser.getTextPct(data, charSet) >= 50) {
					int idx = UNICODE_CSV;
					if ("".equals(charSet)) {
						idx = NORMAL_CSV;
					}
					tab.setSelectedIndex(idx);
				}
			}
		}


		switch (tab.getSelectedIndex()) {
		case NORMAL_CSV:
			if (allowTabSwap && ! "".equals(charSet)) {
				unicodeCsvDetails.fontTxt.setText(charSet);
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
				unicodeCsvDetails.fontTxt.setText(charSet);
				unicodeCsvDetails.setData(filename, data, false, layoutId);

				if (allowTabSwap) {
					checkNormalCsv(data);
				}
			}
			break;
		case FIXED_FILE:
			//long time1 = System.nanoTime();
			csvPanels[FIXED_FILE].setData(filename, data, false, layoutId);
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
		}
	}

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
		byte[] data = StreamUtil.read(new FileInputStream(fileName), 8000); //new byte[8000];

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

	public LayoutDetail getLayout(String font, byte[] recordSep) {
		return getSelectedCsvDetails().getLayout(font, recordSep);
	}
}
