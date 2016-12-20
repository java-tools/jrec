/**
 * 
 */
package net.sf.RecordEditor.re.util.fw;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

import net.sf.JRecord.ByteIO.AbstractByteReader;
import net.sf.JRecord.ByteIO.ByteIOProvider;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.Def.ExternalField;
import net.sf.RecordEditor.layoutWizard.ColumnDetails;
import net.sf.RecordEditor.layoutWizard.Details;
import net.sf.RecordEditor.layoutWizard.FieldSearch;
import net.sf.RecordEditor.layoutWizard.FileAnalyser;
import net.sf.RecordEditor.re.util.csv.CharsetDetails;
import net.sf.RecordEditor.utils.swing.FixedWidthFieldSelection;
import net.sf.RecordEditor.utils.swing.FixedWidthFieldSelection.FieldListManager;

/**
 * @author bruce
 *
 */
public class UpdateableFileSummayModel implements IUpdateableFileSummaryModel {

	private FileAnalyser fileAnalyser;
	private int fileStructure;
	private ArrayList<byte[]> lines = new ArrayList<byte[]>();
	private Details details;  
	private int maxLineLength;
	private int[][] columnDetails;
	private FieldSearch fieldSearch;
	
	private FixedWidthFieldSelection.FieldListManager fieldMgr = new FixedWidthFieldSelection.FieldListManager();
	private byte[] lastData;
	
	private boolean mine = false;

	
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.FixedWidthFieldSelection.IFileSummaryDetails#getColumnDetails()
	 */
	@Override
	public int[][] getColumnDetails() {
		return columnDetails;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.FixedWidthFieldSelection.IFileSummaryDetails#getFileDisplay()
	 */
	@Override
	public String getFileDisplay() {
		if (mine && details != null) {
			return details.getFileDataAsString();
		}
		String ret = "";
		if (lastData !=null) {
			String font = "";
			byte[] bytes = lastData;
			if (lastData.length > 15000) {
				bytes = new byte[12000];
				System.arraycopy(lastData, 0, bytes, 0, bytes.length);
			}
			if (fileAnalyser != null) {
				font = fileAnalyser.getCharsetDetails().charset;
			}
			ret = Conversion.toString(bytes, font);
		}
		return ret;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.FixedWidthFieldSelection.IFileSummaryDetails#getMaxLineLength()
	 */
	@Override
	public int getMaxLineLength() {
		return maxLineLength;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.FixedWidthFieldSelection.IFileSummaryDetails#getType(int, int)
	 */
	@Override
	public int getType(int col, int len) {
		if (fieldSearch != null) {
			return fieldSearch.findType(col, col+len, true, false, true, true, false);
		}
		return 0;
	}
	
	@Override
	public final void setFontName(String font) {
		details.fontName = font;
	}
	

	@Override
	public ExternalRecord asExtenalRecord(String name, String fontName) {
		ExternalRecord rec = ExternalRecord.getNullRecord(name, fontName);
		ColumnDetails[] fields = fieldMgr.getFieldSelection();
		int i = 1;
		
		for (ColumnDetails f : fields) {
			if (f.include) {
				String fieldName = f.name;
				if (fieldName.length() == 0) {
					fieldName = "Fld_" + i; 
				}
				rec.addRecordField( new ExternalField(f.getStart(), f.length, fieldName, "", f.type, f.decimal, 0, "", "", "", 0));
			}
			i+= 1;
		}
		
		return rec;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.util.fw.IUpdateableFileSummary#setData(java.lang.String, byte[], boolean, java.lang.String)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public boolean setData(String filename, byte[] data, boolean checkCharset) {
		
		
		if (lastData != data) {
			fileAnalyser = FileAnalyser.getAnaylserNoLengthCheck(data, "");
			fileStructure = fileAnalyser.getFileStructure();
			lines.clear();
			
			mine = false;
			columnDetails = null;
			fieldSearch = null;
			if (fileStructure != Constants.IO_FIXED_LENGTH ) {
				AbstractByteReader r = ByteIOProvider.getInstance().getByteReader(fileStructure);
				byte[] line;
				
				try {
					r.open(new ByteArrayInputStream(data));
					while ((line = r.read()) != null) {
						lines.add(line);
					}
					r.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw new RecordException("IO Error: " + e, e);
				}
				
				details = fileAnalyser.getFileDetails(false, false);
				
				maxLineLength = details.standardRecord.getMaxRecordLength();
				
				fieldSearch = new FieldSearch(details, details.standardRecord);
				fieldSearch.findFields(true, false, true, true, false);
				
				ArrayList<ColumnDetails> columnDtls = details.standardRecord.columnDtls;
				int size = columnDtls.size();
				columnDetails = new int[2][];
				columnDetails[0] = new int[size];
				columnDetails[1] = new int[size];
				for (int i = 0; i < size; i++) {
					columnDetails[0][i] = columnDtls.get(i).getStart() - 1;
					columnDetails[1][i] = columnDtls.get(i).getType();
				}
				mine = false;
				String ss;
				if (lines.size() == 0 
				|| lines.get(0) == null
				|| lines.get(0).length == 0
				|| (ss = Conversion.toString(lines.get(0), details.fontName).trim()).toLowerCase().startsWith("<?xml")) {
					
				} else if (fileAnalyser.getTextPct() > 60) {
					mine = true;
					
					if (ss.charAt(0) == '<') {
						int xmlTagStartCount = fileAnalyser.getXmlTagStartCount();
						int xmlTagEndCount = fileAnalyser.getXmlTagEndCount();
						if (xmlTagStartCount > 1 
						&& xmlTagEndCount * 3 / 4 <= xmlTagStartCount 
						&&	xmlTagEndCount * 4 / 3 >= xmlTagStartCount) {
							mine = false;
						}
					}
				}
			}
			lastData = data;
		}
		return mine;
	}
	
	@Override
	public final CharsetDetails getCharsetDetails() {
		return fileAnalyser.getCharsetDetails();
	}

	@Override
	public FieldListManager getFieldListManager() {
		return fieldMgr;
	}

}
