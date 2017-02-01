/**
 *
 */
package net.sf.RecordEditor.layoutWizard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JDialog;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.DelegateReader;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.JRecord.Types.Type;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.EditingCancelled;

/**
 * @author Bruce Martin
 *
 */
@SuppressWarnings("rawtypes")
public class UnknownFormatReader extends DelegateReader {

    // private AbstractLineReader reader = null;




	/* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineReader#open(java.io.InputStream, net.sf.JRecord.Details.AbstractLayoutDetails)
	 */
	@Override
	public void open(InputStream inputStream, AbstractLayoutDetails layout)
	throws IOException, RecordException {
		BufferedInputStream inStream = new BufferedInputStream(inputStream, 32000);
		int fileStructure = buildLayout(inStream, layout);

		AbstractLineReader reader = LineIOProvider.getInstance().getLineReader(fileStructure);
	    reader.open(inStream, getLayout());
	    setReader(reader);

	}



	/* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineReader#generateLayout(net.sf.JRecord.Details.AbstractLayoutDetails)
	 */
	@Override
	public void generateLayout(AbstractLayoutDetails layout) {
		super.generateLayout(layout);

		try {
			buildLayout(null, layout);
		} catch (Exception e) {
			Common.logMsg("Error Generating Layout", e);
		}
	}



	private int buildLayout(BufferedInputStream inStream, AbstractLayoutDetails layout)
	throws IOException, RecordException {

			int fileStructure = Constants.IO_DEFAULT;
		    int fieldType = Type.ftCharRestOfRecord;
	        int format    = 0;

	        String param  = "";
	        byte[] recordSep = Constants.CRLF_BYTES;


	        GetFileDetails getDetails = new GetFileDetails(inStream);

		    getDetails.setVisible(true);

		      //-------------------------------------

		    if (getDetails.ok) {
			    PnlUnknownFileFormat pnl = getDetails.pnl;
			    fileStructure = pnl.getFileStructure();
			    String font = pnl.fontNameCombo.getText();

			    RecordDetail.FieldDetails[] flds = new RecordDetail.FieldDetails[1];
	            RecordDetail[] recs = new RecordDetail[1];


	            if (fileStructure == Constants.IO_FIXED_LENGTH) {
		            flds[0] = new RecordDetail.FieldDetails("Data", "", Type.ftChar, 0,
	                        font, format, param);
		            flds[0].setPosLen(1, pnl.getLength());
	            } else {
		            flds[0] = new RecordDetail.FieldDetails("Data", "", fieldType, 0,
			                        font, format, param);
			        flds[0].setPosLen(1, 1);
	            }

	            recs[0] = new RecordDetail("UnknownFormatRecord", "", "", Constants.rtRecordLayout,
	            		"",  "", font, flds, 0, 0);

	            layout  =
	                new LayoutDetail(layout.getLayoutName(), recs, "",
	                    Constants.rtRecordLayout,
	                    recordSep, layout.getEolString(), font, null,
	                    fileStructure
	                );

//			    if (layout != null) {
	            setLayout(layout);
//			    }

//			    reader = LineIOProvider.getInstance().getLineReader(fileStructure);
//			    reader.open(inStream, layout);
		    } else {
		    	throw new EditingCancelled();
		    }

		    return fileStructure;
	}



	/**
	 * Get details of the CSV file from the user
	 * @author Bruce Martin
	 *
	 */
	@SuppressWarnings("serial")
	private static class GetFileDetails extends JDialog implements ActionListener {

		public final PnlUnknownFileFormat pnl;
		public boolean ok = false;


		public GetFileDetails(BufferedInputStream in) throws IOException {
			super(ReMainFrame.getMasterFrame(), true);

			pnl = new PnlUnknownFileFormat(in);
			//pnl.setHelpURL(Common.formatHelpURL(Common.HELP_GENERIC_CSV));
			//pnl.setLines(datalines, font, linesRead);
			pnl.goBtn.addActionListener(this);
			//pnl.cancel.addActionListener(this);

			getContentPane().add(pnl);
			setResizable (true);
			pack();

			if (Common.NIMBUS_LAF) {
				setBounds(getY(), getX(),
						Math.max(getWidth(), ReFrame.getDesktopWidth() * 3 / 4),
						getHeight());
			}
		}

		@Override
		public void actionPerformed(ActionEvent e)   {

			ok = e.getSource() == pnl.goBtn;
			this.setVisible(false);
		}
	}

}
