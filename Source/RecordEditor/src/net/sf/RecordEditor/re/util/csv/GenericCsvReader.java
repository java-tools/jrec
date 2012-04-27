/**
 * 
 */
package net.sf.RecordEditor.re.util.csv;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JDialog;
import javax.swing.JTextField;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LineProvider;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.EditingCancelled;

/**
 * @author Bruce Martin
 *
 */
public class GenericCsvReader extends AbstractLineReader {

	AbstractLineReader reader = null;
	
	/**
	 * @param provider
	 */
	public GenericCsvReader(final LineProvider provider) {
		super(provider);
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.IO.TextLineReader#open(java.io.InputStream, net.sf.JRecord.Details.LayoutDetail)
	 */
	@Override
	public void open(InputStream inputStream, AbstractLayoutDetails layout)
			throws IOException, RecordException {
		BufferedInputStream inStream = new BufferedInputStream(inputStream);
		
	 	byte[] fileData = new byte[Math.min(16000, inStream.available())];
	 	byte[] recordSep = Common.SYSTEM_EOL_BYTES;
		
	 	inStream.mark(fileData.length);
	 	inStream.read(fileData);
	 	inStream.reset();
	 	
	 	GetCsvDetails getDetails = new GetCsvDetails(fileData, layout.getFontName());
    	
	 	if (getDetails.ok) {
	 		FilePreview csv = getDetails.csvTab.getSelectedCsvDetails();
			layout  = csv.getLayout(csv.getFontName(), recordSep);


			if (layout != null) {
				setLayout(layout);
			}
			
			reader = LineIOProvider.getInstance().getLineReader(layout.getFileStructure());
			reader.open(inStream, layout);
			reader.setLayout(layout);
		} else {
			throw new EditingCancelled();
		}
	}



	
	
	/**
	 * @return
	 * @see net.sf.JRecord.IO.AbstractLineReader#canWrite()
	 */
	@Override
	public boolean canWrite() {
		return reader.canWrite();
	}

	/**
	 * @return
	 * @throws IOException
	 * @see net.sf.JRecord.IO.AbstractLineReader#read()
	 */
	@Override
	public AbstractLine read() throws IOException {
		return reader.read();
	}

	/**
	 * @see net.sf.JRecord.IO.TextLineReader#close()
	 */
	@Override
	public void close() throws IOException {
		reader.close();
	}


	/**
	 * Get details of the CSV file from the user
	 * @author Bruce Martin
	 *
	 */
	@SuppressWarnings("serial")
	private static class GetCsvDetails extends JDialog implements ActionListener {
		
		public final BaseHelpPanel pnl = new BaseHelpPanel();
		public final CsvTabPane csvTab;
		public final JTextField msgTxt = new JTextField();
		public boolean ok = false;
		
		public GetCsvDetails(byte[] data, String font) throws IOException {
			super(ReMainFrame.getMasterFrame(), true);
			
			csvTab = new CsvTabPane(msgTxt, false);
			csvTab.readFilePreview(data, true, "", null);
			csvTab.readOtherTab("", data);

			pnl.setHelpURL(Common.formatHelpURL(Common.HELP_GENERIC_CSV));

			csvTab.csvDetails.go.addActionListener(this);
			csvTab.csvDetails.cancel.addActionListener(this);
			csvTab.unicodeCsvDetails.go.addActionListener(this);
			csvTab.unicodeCsvDetails.cancel.addActionListener(this);
			
			pnl.addComponent(
					1, 5, BasePanel.PREFERRED, BasePanel.GAP1,
			        BasePanel.FULL, BasePanel.FULL,
			        csvTab.tab);
			
			pnl.addMessage(msgTxt);
			
			getContentPane().add(pnl);
			setResizable (true);
			pack();
			this.setVisible(true);
		}
		
		@Override
		public void actionPerformed(ActionEvent e)   {

			csvTab.csvDetails.go.removeActionListener(this);
			csvTab.csvDetails.cancel.removeActionListener(this);
			csvTab.unicodeCsvDetails.go.removeActionListener(this);
			csvTab.unicodeCsvDetails.cancel.removeActionListener(this);
			ok = e.getSource() == csvTab.csvDetails.go
			  || e.getSource() == csvTab.unicodeCsvDetails.go;
			
			this.setVisible(false);
		}
	}

}
