/**
 * 
 */
package net.sf.RecordEditor.utils.csv;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JDialog;

import net.sf.JRecord.ByteIO.ByteTextReader;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.LineProvider;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.IO.TextLineReader;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.EditingCancelled;

/**
 * @author Bruce Martin
 *
 */
public class OldGenericCsvReader extends TextLineReader {

	private final int lines2read = 30;
	
    private byte[][] lines ;
    private int linesHeld;
    private int linesRead = 0;
    private ByteTextReader reader;


	/**
	 * @param provider
	 */
	@SuppressWarnings("unchecked")
	public OldGenericCsvReader(LineProvider provider) {
		super(provider, true);
	}

	/**
	 * @see net.sf.JRecord.IO.TextLineReader#createLayout(java.io.BufferedReader)
	 */
	@Override
	protected void createLayout(BufferedReader pReader, InputStream inputStream, String font) throws IOException , RecordException {

	//		super.createLayout(pReader);
		LayoutDetail layout;


		int i = 0;
		byte[] recordSep = Common.SYSTEM_EOL_BYTES;
		byte[] bytes;


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
			layout  = getDetails.pnl.getLayout(font, recordSep);

			if (getDetails.pnl.fieldNamesOnLine.isSelected()) {
				linesRead = 1;
			}

			if (layout != null) {
				setLayout(layout);
			}
		} else {
			throw new EditingCancelled();
		}
	}

	/**
	 * @see net.sf.JRecord.IO.TextLineReader#read()
	 */
	@SuppressWarnings("unchecked")
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
	@SuppressWarnings("serial")
	private static class GetCsvDetails extends JDialog implements ActionListener {
		
		public final CsvSelectionPanel pnl;
		public boolean ok = false;
		
		public GetCsvDetails(byte[][] datalines, String font, int linesRead) {
			super(ReMainFrame.getMasterFrame(), true);
			
			pnl = new CsvSelectionPanel(null, "", true, null);
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
