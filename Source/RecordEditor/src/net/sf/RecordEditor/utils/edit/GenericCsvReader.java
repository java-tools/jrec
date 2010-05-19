/**
 * 
 */
package net.sf.RecordEditor.utils.edit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JDialog;

import net.sf.JRecord.ByteIO.ByteTextReader;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.LineProvider;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.IO.TextLineReader;
import net.sf.JRecord.Types.Type;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.CsvSelectionPanel;

/**
 * @author Bruce Martin
 *
 */
public class GenericCsvReader extends TextLineReader {

	private final int lines2read = 30;
	
    private byte[][] lines ;
    private int linesHeld;
    private int linesRead = 0;
    private ByteTextReader reader;

	/**
	 * 
	 */
	public GenericCsvReader() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param provider
	 */
	public GenericCsvReader(LineProvider provider) {
		super(provider, true);
	}

	/**
	 * @see net.sf.JRecord.IO.TextLineReader#createLayout(java.io.BufferedReader)
	 */
	@Override
	protected void createLayout(BufferedReader pReader, InputStream inputStream, String font) throws IOException , RecordException {

	//		super.createLayout(pReader);
	       LayoutDetail layout;
		    
		    int fieldType = Type.ftChar;
	        int format    = 0;
	        int i         = 0;
	        String param  = "";
	        byte[] recordSep = Constants.LFCR_BYTES;
	        byte[] bytes;
	        String s;
	        
	        reader = new ByteTextReader();
	        reader.open(inputStream);
	        
	        GetCsvDetails getDetails;
	        
	        lines = new byte[lines2read][];
	        while (i < lines2read && (bytes = reader.read()) != null) {
	        	lines[i++] = bytes;
	        }
	        linesHeld = i;
		    
		    getDetails = new GetCsvDetails(lines, super.getLayout().getFontName(), linesHeld);
		    
		    getDetails.setVisible(true);
		    
		      //-------------------------------------
		    
		    if (getDetails.ok) {
			    CsvSelectionPanel pnl = getDetails.pnl;
			    int numCols = pnl.getColumnCount();
			    int ioId = Constants.IO_TEXT_LINE;
			    
	            FieldDetail[] flds = new FieldDetail[numCols];
	            RecordDetail[] recs = new RecordDetail[1];
		   
	            if (pnl.fieldNamesOnLine.isSelected()) {
	            	ioId = Constants.IO_NAME_1ST_LINE;
	            	linesRead = 1;
	            }
			    
	            if (fieldType < 0) {
	                fieldType = Type.ftChar;
	            }
	
	  		    for (i = 0; i < numCols; i++) {
	  		    	s = pnl.getTableModel().getColumnName(i);
		            flds[i] = new FieldDetail(s, s, fieldType, 0,
		                        font, format, param);
		            flds[i].setPosOnly(i + 1);
	  		    }
	
	            recs[0] = new RecordDetail("GeneratedCsvRecord", "", "", Constants.rtDelimited,
	            		pnl.getSeperator(),  pnl.getQuote(), font, flds, 0);
	            
	            layout  =
	                new LayoutDetail(getLayout().getLayoutName(), recs, "",
	                    Constants.rtDelimited,
	                    recordSep, getLayout().getEolString(), font, null,
	                    ioId
	                );
	            
	
	
	
			    if (layout != null) {
			        setLayout(layout);
			    }
		    } else {
		    	throw new RecordException("Editing canceled ");
		    }
	}

	/**
	 * @see net.sf.JRecord.IO.TextLineReader#read()
	 */
	@Override
	public AbstractLine read() throws IOException {
		byte[] s;
		
		if (lines == null) { 
			s = reader.read();
		} else {
			s = lines[linesRead++];
			if (linesRead >= linesHeld) {
				lines = null;
			}
		}
		if (s == null) {
			return null;
		}
		//System.out.println(new String(s));
		return getLine(s);
	}
	
	
	/**
	 * @see net.sf.JRecord.IO.TextLineReader#close()
	 */
	@Override
	public void close() throws IOException {
		reader.close();
		super.close();
	}


	/**
	 * Get details of the CSV file from the user
	 * @author Bruce Martin
	 *
	 */
	private static class GetCsvDetails extends JDialog implements ActionListener {
		
		public CsvSelectionPanel pnl;
		public boolean ok = false;
		public GetCsvDetails(byte[][] datalines, String font, int linesRead) {
			super(ReMainFrame.getMasterFrame(), true);
			
			pnl = new CsvSelectionPanel(null, "", true);
			pnl.setHelpURL(Common.formatHelpURL(Common.HELP_GENERIC_CSV));
			pnl.setLines(datalines, font, linesRead);
			pnl.go.addActionListener(this);
			pnl.cancel.addActionListener(this);
			
			getContentPane().add(pnl);
			setResizable (true);
			pack();
		}
		
		@Override
		public void actionPerformed(ActionEvent e)   {

			this.setVisible(false);
			ok = e.getSource() == pnl.go;
		}
	}

}
