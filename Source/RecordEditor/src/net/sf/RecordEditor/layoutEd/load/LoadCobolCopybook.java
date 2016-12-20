package net.sf.RecordEditor.layoutEd.load;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.RecordEditor.layoutEd.panels.RecordEdit1Record;
import net.sf.RecordEditor.layoutEd.utils.LeMessages;
import net.sf.RecordEditor.re.cobol.CblLoadData;
import net.sf.RecordEditor.re.cobol.CobolAnalyseScreen;
import net.sf.RecordEditor.re.db.Record.ExtendedRecordDB;
import net.sf.RecordEditor.utils.BasicLayoutCallback;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReConnection;

public class LoadCobolCopybook implements ActionListener{

	private final BasicLayoutCallback layoutCallback;
	private final int connectionId;
	
	private final CblLoadData cblDtls; 
	
	
	public LoadCobolCopybook( 
			final String pDBid,
			int connectionId,
			BasicLayoutCallback layoutCallback) {
		super();
		this.layoutCallback = layoutCallback;
		this.connectionId = connectionId;
		
		CobolAnalyseScreen cobAnalysis = new CobolAnalyseScreen(pDBid, connectionId, null, null, null);
		
		cblDtls = cobAnalysis.cblDtls;
		
		cblDtls.goBtn.addActionListener(this);
	}


	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		boolean free = Common.isSetDoFree(false);
		String copyBookFile = cblDtls.copybookFileCombo.getText();
//		int dialect = cblDtls.getDialect();
//		int split = cblDtls.getSplitOption();
//		int cpybookFormat = cblDtls.getCopybookFormat();

		try {
		    if (copyBookFile != null && !"".equals(copyBookFile)) {
//		        int systemId = ((Integer) cblDtls.system.getSelectedItem()).intValue();
//		        CopybookLoaderFactory loaders = CopybookLoaderFactoryDB.getInstance();
		        ExternalRecord rec;
		        ExtendedRecordDB db = new ExtendedRecordDB();
//		        int copybookId = CopybookLoaderFactoryDB.COBOL_LOADER;
		        int fstructure = cblDtls.getFileStructure();


		        //System.out.println("~~~> " + copybookId + " " + loaders.getNumberofLoaders()
		        //        + " " + CobolCopybookLoader.isAvailable());
//		        rec = loaders.getLoader(copybookId).loadCopyBook(copyBookFile,
//		                split,
//		                connectionId,
//		                cblDtls.fontNameCombo.getText(),
//		                cpybookFormat,
//		                dialect,
//		                systemId,
//		                cblDtls.msgField);

		        rec = cblDtls.getXRecord();
		        if (rec == null) {
//		        	cblDtls.msgField.logMsg(AbsSSLogger.ERROR, LeMessages.ERROR_LOADING_COPYBOOK.get());
		        } else {

			        if (fstructure != Constants.IO_DEFAULT
			        && rec.getFileStructure() <= Constants.IO_DEFAULT) {
			        	rec.setFileStructure(fstructure);
			        }
			        
			        

			        db.setConnection(new ReConnection(connectionId));

			        //ap100_updateSystemDelimQuote(db, rec);

			        //System.out.println("## " + rec.getRecordId() + " " + rec.getRecordName());

//			        System.out.print("RecordId: " + rec.getRecordId());
			        db.checkAndUpdate(rec);
//			        System.out.println(" !! " + rec.getRecordId());

//			        cblDtls.msgField.logMsg(AbsSSLogger.SHOW, "-->> " + copyBookFile + " processed");
//			        cblDtls.msgField.logMsg(AbsSSLogger.SHOW, "      Copybook: " + rec.getRecordName());
			        cblDtls.msgField.logMsg(
			        		AbsSSLogger.SHOW,
			        		LeMessages.COPYBOOK_LOADED.get(new Object[] {copyBookFile, rec.getRecordName()}));
//			        System.out.println(LeMessages.COPYBOOK_LOADED.get(new Object[] {copyBookFile, rec.getRecordName()}));
			        db.close();

			        if (layoutCallback != null) {
			        	layoutCallback.setRecordLayout(0, rec.getRecordName(), null);
			        }

			        if (rec.getNumberOfRecords() > 1
			        /*&& (   copybookId == CopybookLoaderFactoryDB.COBOL_LOADER
			            || copybookId == CopybookLoaderFactoryDB.CB2XML_LOADER)*/) {
			        	RecordEdit1Record ep = new RecordEdit1Record(
			        			Common.getSourceId()[connectionId], connectionId,
			        			rec.getRecordId());
			        	JOptionPane.showMessageDialog(
			        			ep,
			        			LeMessages.DEFINE_RECORD_SELECTION.get());
			        }
			    }
		    }
		} catch (Exception ex) {
		    cblDtls.msgField.logMsg(AbsSSLogger.ERROR, ex.getMessage() + '\n');
		    ex.printStackTrace();
        } finally {
        	Common.setDoFree(free, connectionId);
        }

	}

}
