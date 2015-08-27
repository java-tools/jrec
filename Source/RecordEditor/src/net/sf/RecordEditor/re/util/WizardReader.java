/**
 *
 */
package net.sf.RecordEditor.re.util;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JDialog;

import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.LineProvider;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.DelegateReader;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.RecordEditor.layoutWizard.Details;
import net.sf.RecordEditor.layoutWizard.FileAnalyser;
import net.sf.RecordEditor.layoutWizard.Wizard;
import net.sf.RecordEditor.re.openFile.RecentFiles;
import net.sf.RecordEditor.utils.LayoutConnection;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.StreamUtil;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.EditingCancelled;

/**
 * @author Bruce Martin
 *
 */
@SuppressWarnings("rawtypes")
public class WizardReader extends DelegateReader {

	//AbstractLineReader reader = null;

	/**
	 * @param provider
	 */
	public WizardReader(final LineProvider provider) {
		super(provider);
	}




	/* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineReader#open(java.io.InputStream, java.lang.String, net.sf.JRecord.Details.AbstractLayoutDetails)
	 */
	@Override
	public void open(InputStream inputStream, String filename,
			AbstractLayoutDetails pLayout) throws IOException, RecordException {
		open(filename, pLayout);
	}




	/* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineReader#open(java.lang.String, net.sf.JRecord.Details.AbstractLayoutDetails)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void open(String fileName, AbstractLayoutDetails pLayout)
			throws IOException, RecordException {

		JDialog d = new JDialog(ReMainFrame.getMasterFrame(), true);
		ReFrame frame = ReFrame.getActiveFrame();
		LayoutConnection con = null;
		if (frame != null
		&&  frame.getContentPane() != null
		&&	frame.getContentPane().getComponent(0) instanceof LayoutConnection) {
			con = (LayoutConnection) frame.getContentPane().getComponent(0);
		}
		Wizard wiz = new Wizard(d, Common.getConnectionIndex(), fileName, con, false);

				//WizardFileLayout(d, fileName, null, false, false);

		Details details = wiz.getWizardDetails();

		FileAnalyser analyser;

		byte[] data = StreamUtil.read(new FileInputStream(fileName), 8000);

		System.out.println("Bytes Read: " + data.length);
		analyser = FileAnalyser.getAnaylser(data, "");


		details.fileStructure = analyser.getFileStructure();
		details.fontName = analyser.getFontName();
		details.recordLength = analyser.getRecordLength();
		details.generateFieldNames = true;
		details.editFile = false;

	//	wiz.changePanel(WizardFileLayout.FORWARD, false);


		d.setVisible(true);
		//AbstractLineReader reader =  details.getReader();
		ExternalRecord r = details.createRecordLayout();

	 	if (r == null || (r.getNumberOfRecordFields() == 0 && r.getNumberOfRecords() == 0)) {
			throw new EditingCancelled();
		} else {
			AbstractLayoutDetails l = r.asLayoutDetail();
			AbstractLineReader reader = LineIOProvider.getInstance().getLineReader(l);
			reader.open(fileName, l);
	 		setReader(reader);

	 		try { // just in case, it is not essential that recent files be updated
		 		if (RecentFiles.getLast() != null) {
		 			RecentFiles.getLast().putFileLayout(fileName, l.getLayoutName());
		 		}
	 		} catch (Exception e) {
				// just in case, it is not essential that recent files be updated
			}
		}

	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.IO.TextLineReader#open(java.io.InputStream, net.sf.JRecord.Details.LayoutDetail)
	 */

	@Override
	public void open(InputStream inputStream, AbstractLayoutDetails layout)
			throws IOException, RecordException {

		throw new IOException("Method not supported");
	}







}
