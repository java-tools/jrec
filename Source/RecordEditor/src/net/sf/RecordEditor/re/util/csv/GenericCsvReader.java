/**
 *
 */
package net.sf.RecordEditor.re.util.csv;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JDialog;
import javax.swing.JTextField;

import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.LineProvider;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.DelegateReader;
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
@SuppressWarnings("rawtypes")
public class GenericCsvReader extends DelegateReader {

	//AbstractLineReader reader = null;
	

	/**
	 * @param provider
	 */
	public GenericCsvReader(final LineProvider provider) {
		super(provider);
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.IO.TextLineReader#open(java.io.InputStream, net.sf.JRecord.Details.LayoutDetail)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void open(InputStream inputStream, AbstractLayoutDetails layout)
			throws IOException, RecordException {
		BufferedInputStream inStream = new BufferedInputStream(inputStream);

	 	int bytesAvailable = inStream.available();
	 	int len;
		byte[] fileData;
//	 	byte[] recordSep = Common.SYSTEM_EOL_BYTES;

	 	if (bytesAvailable < 2) {
	 		bytesAvailable = 0x16000;
	 	}
	 	fileData = new byte[Math.min(0x16000, bytesAvailable)];
	 	inStream.mark(fileData.length);
	 	len = inStream.read(fileData);
	 	inStream.reset();
	 	
	 	if (fileData.length > len) {
	 		byte[] t = new byte[len];
	 		System.arraycopy(fileData, 0, t, 0, len);
	 		fileData = t;
	 	}

	 	GetCsvDetails getDetails = new GetCsvDetails(fileData, layout.getFontName());

	 	if (getDetails.ok) {
	 		FilePreview csv = getDetails.csvTab.getSelectedCsvDetails();
			layout  = csv.getLayout(csv.getFontName(), null);


			if (layout != null) {
				setLayout(layout);
			}

			AbstractLineReader reader = LineIOProvider.getInstance().getLineReader(layout);
			reader.open(inStream, layout);
			reader.setLayout(layout);
			setReader(reader);
		} else {
			throw new EditingCancelled();
		}
	}







	/**
	 * Get details of the CSV file from the user
	 * @author Bruce Martin
	 *
	 */
	@SuppressWarnings("serial")
	private static class GetCsvDetails extends JDialog implements ActionListener {


		public final KeyAdapter keyListner = new KeyAdapter() {
		        /**
		         * @see java.awt.event.KeyAdapter#keyReleased
		         */
		        public final void keyReleased(KeyEvent event) {

		        	if (event.getKeyCode() == KeyEvent.VK_ENTER) {
		        		finnished(true);
		         	}
		        }
		};

		public final BaseHelpPanel pnl = new BaseHelpPanel();
		public final CsvTabPane csvTab;
		public final JTextField msgTxt = new JTextField();
		public boolean ok = false;

		public GetCsvDetails(byte[] data, String font) throws IOException {
			super(ReMainFrame.getMasterFrame(), true);
			
			pnl.addReKeyListener(keyListner);

			csvTab = new CsvTabPane(msgTxt, false, false);
			csvTab.readCheckPreview(data, true, "", null);
			csvTab.readOtherTab("", data);

			pnl.setHelpURLre(Common.formatHelpURL(Common.HELP_GENERIC_CSV));

			csvTab.csvDetails.go.addActionListener(this);
			csvTab.csvDetails.cancel.addActionListener(this);
			csvTab.unicodeCsvDetails.go.addActionListener(this);
			csvTab.unicodeCsvDetails.cancel.addActionListener(this);

			pnl.addComponentRE(
					1, 5, BasePanel.PREFERRED, BasePanel.GAP1,
			        BasePanel.FULL, BasePanel.FULL,
			        csvTab.tab);

			pnl.addMessage(msgTxt);

			getContentPane().add(pnl);
			setResizable (true);
			pack();
			this.setVisible(true);
			csvTab.csvDetails.addReKeyListener(keyListner);
			csvTab.unicodeCsvDetails.addReKeyListener(keyListner);

		}

		@Override
		public void actionPerformed(ActionEvent e)   {

			finnished(e.getSource() == csvTab.csvDetails.go
			  || e.getSource() == csvTab.unicodeCsvDetails.go);

		}
		

		private void finnished(boolean isOk) {
			csvTab.csvDetails.go.removeActionListener(this);
			csvTab.csvDetails.cancel.removeActionListener(this);
			csvTab.unicodeCsvDetails.go.removeActionListener(this);
			csvTab.unicodeCsvDetails.cancel.removeActionListener(this);
			ok = isOk;

			this.setVisible(false); 
		}
	}
}
