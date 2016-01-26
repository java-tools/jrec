/*
 * Created on 5/09/2004
 *
 * Changes
 *
 * # Version 0.61 Bruce Martin 2007/04/14
 *
 *   - Changes due seperating JRecord out as a seperate package
 *   - Changed to use CopybookLoaderFactory
 *
 * # Version 0.61b Bruce Martin 2007/05/07
 *   - Fixed bug in XML load
 */
package net.sf.RecordEditor.layoutEd.load;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.RecordEditor.layoutEd.panels.RecordEdit1Record;
import net.sf.RecordEditor.layoutEd.utils.LeMessages;
import net.sf.RecordEditor.re.db.Record.ExtendedRecordDB;
import net.sf.RecordEditor.re.db.Table.TableDB;
import net.sf.RecordEditor.re.file.textDocument.CobolCopybookEditorKit;
import net.sf.RecordEditor.utils.BasicLayoutCallback;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.FileUtils;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;




/**
 * This class will load either a Cobol Copybook or XML copybook
 * into the RecordEditor Databases
 *
 * @author Bruce Martin
 * @version 0.98
 *

 */
@SuppressWarnings("serial")
public class CblLoadCopybook extends ReFrame implements ActionListener {

	private static final String COBOL_LAYOUT = LangConversion.convert(LangConversion.ST_FRAME_HEADING, "Load Cobol Record Layout - DB ");

//	private String formDescription;

	

		/* screen fields */
//	private JEditorPane tips;

	private BaseHelpPanel pnl = new BaseHelpPanel("cbli");

//	private ScreenLog msgField = new ScreenLog(pnl);

		/* input params */
	private int connectionId;

	TableDB tblsDB = null;

	private BasicLayoutCallback layoutCallback;
	
	private JTextPane cblTA = new JTextPane();
	private JTabbedPane cblDtlsTab = new JTabbedPane();
	private CblRecordPnl recordTab = null;
	private CblFilePnl filePnl = null;
	
	private final CblLoadData cblDtls; 

	private boolean recordTabUsed = false, fileTabUsed = false;
	

	

	/**
	 * This class Loads selected copybooks into the record editor DB
	 * @param chooseCopyBook is it a XML (or Cobol Copybook
	 * @param pDBid Database Name
	 * @param pConnectionId Database index (or identifier
	 */
	public CblLoadCopybook(
						   final String pDBid,
						   final int pConnectionId,
						   final BasicLayoutCallback callback)  {
		super((COBOL_LAYOUT) + " " + pDBid,
			   "", null);
		boolean free = Common.isSetDoFree(false);
		
		ChangeListener copybookParseChanged = new ChangeListener() {
			@Override public void stateChanged(ChangeEvent e) {
				checkRecords();
			}
		};
		ChangeListener copybookAttrChanged = new ChangeListener() {
			@Override public void stateChanged(ChangeEvent e) {
				updateFilesCheck();
			}
		};

		this.connectionId = pConnectionId;
		this.cblDtls = new CblLoadData(connectionId, pnl, copybookParseChanged, copybookAttrChanged); 
		this.layoutCallback = callback;

 
		init_100_BasicInit();
		init_200_LayoutScreen();
		init_300_Listners();

		setVisible(true);
		Common.setDoFree(free,pConnectionId);
	}


	private void init_100_BasicInit() {

		pnl.setHelpURLre(Common.formatHelpURL(Common.HELP_COPYBOOK));
//		copybookFile = new FileSelectCombo(Parameters.COBOL_COPYBOOK_LIST, 25, true, false, true);
		
//	    formDescription = LangConversion.convertId(
//	    		LangConversion.ST_MESSAGE,
//	    		"CopybookLoad",
//	    		"<font COLOR=Blue>This option loads a <b>{0} Copybooks</b> into the <b>Record Edit DB.</b></font>"
//	    	    + ReadFile.getCb2XmlHtml(),
//	    		new Object[] {"Cobol"});
//
//	    tips = new JEditorPane("text/html", formDescription);
//	    tips.setEditable(false);
//	    
//	    tips.setCaretPosition(1);	    
	}

	/**
	 * Layout the screen
	 */
	private void init_200_LayoutScreen() {
//		BaseHelpPanel cblPnl = new BaseHelpPanel("cblDtl");
		CblDtlsPnl cblDetails = new CblDtlsPnl(cblDtls);
		ReMainFrame frame = ReMainFrame.getMasterFrame();
		int maxHeight = frame.getDesktop().getHeight() - 1;
		int maxWidth = frame.getDesktop().getWidth() - 1;
		int pnlWidth = Math.min(maxWidth, SwingUtils.STANDARD_FONT_WIDTH * 145);
		
//		pnl.addComponentRE(1, 5, SwingUtils.STANDARD_FONT_HEIGHT * 12,
//				BasePanel.GAP0,
//		        BasePanel.FULL, BasePanel.FULL,
//				tips);

		
		cblDtlsTab.addTab("Cobol", new JScrollPane(cblTA));

		Dimension preferredSize = cblDetails.cblPnl.getPreferredSize();
		
		System.out.println("***> " + (pnlWidth * 11 / 20) + ", " + (preferredSize.width + SwingUtils.STANDARD_FONT_WIDTH * 20));
		cblDetails.cblPnl.setPreferredSize(new Dimension(
				Math.min(pnlWidth * 11 / 20, preferredSize.width + SwingUtils.STANDARD_FONT_WIDTH * 20), 
				preferredSize.height));
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, cblDetails.scrollPane, cblDtlsTab);
		//splitPane.setDividerLocation(0.6);
		pnl.addComponentRE(0, 6, BasePanel.FILL, BasePanel.GAP1,
		        BasePanel.FULL, BasePanel.FULL,
		        splitPane
		        );


		pnl.addComponentRE(1, 5, 90 , 1,
		        BasePanel.FULL, BasePanel.FULL,
		        cblDtls.msgField);
		//pnl.setGap(BasePanel.GAP1);

			//find.addActionListener(this);
		
//		cblDtlsTab.addTab("Cobol", new JScrollPane(cblTA));


		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		//pnl.done();

		this.getContentPane().add(pnl);

		pack();

		setBounds(getY(), getX(), 
				pnlWidth,
				Math.min(maxHeight, getHeight() + SwingUtils.STANDARD_FONT_HEIGHT * 11));
	}
	
	private void init_300_Listners() {
		
		cblTA.setEditorKit(new CobolCopybookEditorKit());
		cblTA.setFont(SwingUtils.getMonoSpacedFont());
	
		cblDtls.helpBtn.addActionListener(this); 
		cblDtls.go.addActionListener(this);

		cblDtls.copybookFileCombo.addTextChangeListner(new ChangeListener() {
			@Override public void stateChanged(ChangeEvent e) {
				cobolFilenameChanged();
			}
		});
		cblDtls.sampleFileCombo.addTextChangeListner(new ChangeListener() {
			@Override public void stateChanged(ChangeEvent e) {
				sampleFilenameChanged();
			}
		});
	
		cblTA.addFocusListener(new FocusAdapter() {
			@Override public void focusLost(FocusEvent e) {
				copybookChanged();
			}
		});
	}

	private void cobolFilenameChanged() {
		
		String f = cblDtls.copybookFileCombo.getText();
		File file;
		String s = "";
		if (f != null && f.length() > 0) {
			file = new File(f);
			if (file.isDirectory()) {
				cblDtls.msgField.logMsg(AbsSSLogger.ERROR, LeMessages.FILE_IS_DIRECTORY.get(f));
			} else if ((file = new File(f)).exists()) {
				try {
					s = FileUtils.readFile(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				cblDtls.msgField.logMsg(AbsSSLogger.ERROR, LeMessages.COPYBOOK_DOES_NOT_EXIST.get(f));
			}
		} 
		cblTA.setText(s);
		cblTA.setCaretPosition(1);
		copybookChanged();
	}
	
	private void copybookChanged() {
		
		if (cblDtls.setCobolCopybook(cblTA.getText())) {
			checkRecords();
		}
	}





	private void checkRecords() {

		ExternalRecord xRecord = cblDtls.getXRecord();
		
		if (xRecord != null && xRecord.getNumberOfRecords() > 1) {
			if (recordTab == null) {
				recordTab = new CblRecordPnl(cblDtls);
			}
			
			recordTab.notifyOfUpdate(xRecord);
			
			if (! recordTabUsed) {
				cblDtlsTab.addTab("Records", recordTab.recordPnl);
				recordTabUsed = true;
				recordTab.recordPnl.requestFocus();
			}
		} else if (recordTab != null) {
			cblDtlsTab.remove(recordTab.recordPnl);
			recordTabUsed = false;
		}
		
		if (xRecord != null) {
			filesDisplay();
		}
	}
	
	private void updateFilesCheck() {
		ExternalRecord xRecord = cblDtls.getXRecord();
		
		if (xRecord != null) {
			filesDisplay();
		} else if (fileTabUsed) {
			cblDtlsTab.remove(filePnl.fileTab);
			fileTabUsed = false;
		}
	}


	/**
	 * 
	 */
	private void filesDisplay() {
		if (cblDtls.getFileBytes() != null) {
			if (filePnl == null) {
				filePnl = new CblFilePnl(cblDtls);
			}
			
			filePnl.updateScreen();
			
			if (! fileTabUsed) {
				cblDtlsTab.addTab("File", filePnl.fileTab);
				fileTabUsed = true;
			}
		} else if (fileTabUsed) {
			cblDtlsTab.remove(filePnl.fileTab);
			fileTabUsed = false;
		}
	}

	/**
	 * 
	 */
	private void sampleFilenameChanged() {
		cblDtls.checkDataFile();
		if (recordTab != null) {
			recordTab.setUpValues();
		}
		updateFilesCheck();
	}


	/**
	 * @see java.awt.Event.actionPerformed
	 */
	public void actionPerformed(final ActionEvent event) {

	    if (event.getSource() == cblDtls.helpBtn) {
	        pnl.showHelpRE();

		} else {
			boolean free = Common.isSetDoFree(false);
			String copyBookFile = cblDtls.copybookFileCombo.getText();
//			int dialect = cblDtls.getDialect();
//			int split = cblDtls.getSplitOption();
//			int cpybookFormat = cblDtls.getCopybookFormat();

			try {
			    if (copyBookFile != null && !"".equals(copyBookFile)) {
//			        int systemId = ((Integer) cblDtls.system.getSelectedItem()).intValue();
//			        CopybookLoaderFactory loaders = CopybookLoaderFactoryDB.getInstance();
			        ExternalRecord rec;
			        ExtendedRecordDB db = new ExtendedRecordDB();
//			        int copybookId = CopybookLoaderFactoryDB.COBOL_LOADER;
			        int fstructure = cblDtls.getFileStructure();


			        //System.out.println("~~~> " + copybookId + " " + loaders.getNumberofLoaders()
			        //        + " " + CobolCopybookLoader.isAvailable());
//			        rec = loaders.getLoader(copybookId).loadCopyBook(copyBookFile,
//			                split,
//			                connectionId,
//			                cblDtls.fontNameCombo.getText(),
//			                cpybookFormat,
//			                dialect,
//			                systemId,
//			                cblDtls.msgField);

			        rec = cblDtls.getXRecord();
			        if (rec == null) {
//			        	cblDtls.msgField.logMsg(AbsSSLogger.ERROR, LeMessages.ERROR_LOADING_COPYBOOK.get());
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
//				        System.out.println(LeMessages.COPYBOOK_LOADED.get(new Object[] {copyBookFile, rec.getRecordName()}));
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
//		        tableId = null;
		        tblsDB  = null;
	        }

		}
	}


//	/**
//	 * Set the System Id based on the System Name
//	 *
//	 */
//	private void ap100_updateSystemDelimQuote(ExtendedRecordDB db, ExternalRecord rec) throws SQLException {
//
//		db.updateSystemCode(rec);
//
//        int fieldSeparatorIdx = fieldSeparator.getSelectedIndex();
//        int quoteIdx = quote.getSelectedIndex();
//
//		for (int i = 0; i < rec.getNumberOfRecords(); i++) {
//			ap110_UpdateDelimQuote( rec.getRecord(i), fieldSeparatorIdx, quoteIdx);
//		}
//		
//		ap110_UpdateDelimQuote( rec, fieldSeparatorIdx, quoteIdx);
//	}
//	
//	private void ap110_UpdateDelimQuote(ExternalRecord rec, int fieldSeparatorIdx, int quoteIdx) {
//
//        if (fieldSeparatorIdx > 0) {
//        	rec.setDelimiter(fieldSeparator.getSelectedEnglish());
//        }
//
//        if (quoteIdx > 0) {
//        	rec.setQuote(Common.QUOTE_LIST[quoteIdx]);
//        }
//	}

    /**
     * @see net.sf.RecordEditor.utils.common.ReActionHandler#executeAction(int)
     */
    public void executeAction(int action) {

        if (action == ReActionHandler.HELP) {
            pnl.showHelpRE();
        }

    }

    /**
     * @see net.sf.RecordEditor.utils.common.ReActionHandler#isActionAvailable(int)
     */
    public boolean isActionAvailable(int action) {
        return action == ReActionHandler.HELP;
    }
}
