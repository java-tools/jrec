package net.sf.RecordEditor.utils.csv;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


import javax.swing.JTabbedPane;
import javax.swing.text.JTextComponent;


import net.sf.JRecord.ByteIO.ByteTextReader;
import net.sf.JRecord.Details.LayoutDetail;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.csv.CheckEncoding;
import net.sf.RecordEditor.utils.csv.CsvSelectionPanel;
import net.sf.RecordEditor.utils.openFile.FormatFileName;


/**
 * 
 * @author Bruce Martin
 *
 * Class that holds a JTabbedPane of Normal / Unicode
 * CSV preview panels.
 */

public class CsvTabPane implements FormatFileName {
	public final static int NORMAL_CSV  = 0,
							UNICODE_CSV = 1;
	
	private final static byte[][] oneBlankLine = {{}};

	private	ByteTextReader r = new ByteTextReader();
	
	public final JTabbedPane tab = new JTabbedPane();

	public final CsvSelectionPanel csvDetails;
	public final CsvSelectionPanel unicodeCsvDetails;
	
	private JTextComponent msgFld;

	private CsvSelectionPanel[] csvPanels = {null, null};

	/**
	 * Class that holds a JTabbedPane of Normal / Unicode
	 * CSV preview panels.
	 * @param msgField message field to display error messages on
	 */
	public CsvTabPane(JTextComponent msgField) {
		msgFld = msgField;
		
		csvDetails = new CsvSelectionPanel(oneBlankLine, "", false, "File Preview", msgField);
		unicodeCsvDetails = new CsvSelectionPanel(oneBlankLine[0], "", false, "Unicode File Preview", msgField);
		
		csvPanels[NORMAL_CSV]  = csvDetails;
		csvPanels[UNICODE_CSV] = unicodeCsvDetails;
		
		tab.add("Normal",  csvDetails);
		tab.add("Unicode", unicodeCsvDetails);
	}

	public void readOtherTab(byte[] data) throws IOException {
		try {
			if (tab.getSelectedIndex() == NORMAL_CSV) {
				unicodeCsvDetails.setData(data, false);
			} else {
				setNormalCsv(data);
			}
		} catch (Exception e) {
			msgFld.setText("Only one tab is set: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void readFilePreview(File f, boolean allowTabSwap) {

		if ((f != null) && (f.isFile()) ) {
			String fileName = f.getPath();

			try {
				readFilePreview(readUnicodeFile(fileName), allowTabSwap);
			} catch (IOException ex) {
				Common.logMsg("Error Reading File", ex);
			}
		}
	}
	
	public void readFilePreview(byte[] data, boolean allowTabSwap)
	throws IOException {
		String charSet = CheckEncoding.determineCharSet(data);
		
		if (tab.getSelectedIndex() == NORMAL_CSV) { 
			if (allowTabSwap && ! "".equals(charSet)) {
				unicodeCsvDetails.fontTxt.setText(charSet);
				unicodeCsvDetails.setData(data, false);

				checkNormalCsv(data);					
			} else {
				setNormalCsv(data);
			}
		} else {
			if (allowTabSwap && "".equals(charSet)) {
				setNormalCsv(data);
				tab.setSelectedIndex(NORMAL_CSV);					
			} else {
				unicodeCsvDetails.fontTxt.setText(charSet);
				unicodeCsvDetails.setData(data, false);
				
				if (allowTabSwap) {
					checkNormalCsv(data);
				}
			}
		}
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
	
	public CsvSelectionPanel getSelectedCsvDetails() {
		return csvPanels[tab.getSelectedIndex()];
	}
	
	public LayoutDetail getLayout(String font, byte[] recordSep) {
		return getSelectedCsvDetails().getLayout(font, recordSep);
	}
}
