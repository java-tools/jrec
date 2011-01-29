/**
 * 
 */
package net.sf.RecordEditor.utils.csv;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JDialog;

import net.sf.JRecord.Details.CharLineProvider;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.IO.TextLineReader;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.EditingCancelled;

/**
 * @author Bruce Martin
 *
 */
public class OldGenericCsvReaderUnicode extends TextLineReader {


	/**
	 * @param provider
	 */
	public OldGenericCsvReaderUnicode() {
		super(new CharLineProvider(), false);
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.IO.TextLineReader#open(java.io.InputStream, net.sf.JRecord.Details.LayoutDetail)
	 */
	@Override
	public void open(InputStream inputStream, LayoutDetail layout)
			throws IOException, RecordException {
		BufferedInputStream inStream = new BufferedInputStream(inputStream);
		
	 	byte[] fileData = new byte[Math.min(16000, inStream.available())];
	 	byte[] recordSep = Common.SYSTEM_EOL_BYTES;
		
	 	inStream.mark(fileData.length);
	 	inStream.read(fileData);
	 	inStream.reset();
	 	
	 	GetCsvDetails getDetails = new GetCsvDetails(fileData, layout.getFontName());
    	
	 	if (getDetails.ok) {
			layout  = getDetails.pnl.getLayout(getDetails.pnl.fontTxt.getText(), recordSep);


			if (layout != null) {
				setLayout(layout);
			}
			
			super.open(inStream, layout);
			if (getDetails.pnl.fieldNamesOnLine.isSelected()) {
				reader.readLine();
			}
		} else {
			throw new EditingCancelled();
		}
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
	@SuppressWarnings("serial")
	private static class GetCsvDetails extends JDialog implements ActionListener {
		
		public final CsvSelectionPanel pnl;
		public boolean ok = false;
		
		public GetCsvDetails(byte[] data, String font) {
			super(ReMainFrame.getMasterFrame(), true);
			
			pnl = new CsvSelectionPanel(data, "", true, "Unicode CSV File", null);
			pnl.setHelpURL(Common.formatHelpURL(Common.HELP_GENERIC_CSV));

			pnl.go.addActionListener(this);
			pnl.cancel.addActionListener(this);
			
			getContentPane().add(pnl);
			setResizable (true);
			pack();
			this.setVisible(true);
		}
		
		@Override
		public void actionPerformed(ActionEvent e)   {

			pnl.go.removeActionListener(this);
			pnl.cancel.removeActionListener(this);
			ok = e.getSource() == pnl.go;
			if (! ok) {
				System.out.println(e.getSource().getClass().getName()
						+ " " + e.getSource());
			}
			this.setVisible(false);
		}
	}

}
