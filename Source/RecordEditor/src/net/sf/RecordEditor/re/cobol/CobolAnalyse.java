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
package net.sf.RecordEditor.re.cobol;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.RecordEditor.re.file.textDocument.CobolCopybookEditorKit;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.FileUtils;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.lang.ReOptionDialog;
import net.sf.RecordEditor.utils.msg.UtMessages;
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
public class CobolAnalyse extends ReFrame implements ActionListener {
	
//	public static CobolAnalyse newCobolCopybookLoader(
//				final String pDBid,
//				final int pConnectionId,
//				final BasicLayoutCallback callback) {
//		return new CobolAnalyse(pDBid, pConnectionId, callback, null, null, null);
//	}

	private static final String COBOL_LAYOUT = LangConversion.convert(LangConversion.ST_FRAME_HEADING, "Load Cobol Record Layout - DB ");
	private static final String COBOL_GEN = LangConversion.convert(LangConversion.ST_FRAME_HEADING, "Generate Code for Cobol Copybook ");


	private final BaseHelpPanel cblDtlPnl = new BaseHelpPanel("cbli");

//	private ScreenLog msgField = new ScreenLog(pnl);

		/* input params */
	private int connectionId;

//	private BasicLayoutCallback layoutCallback;
	
	private JTextPane cblTA = new JTextPane();
	private JTabbedPane cblDtlsTab = new JTabbedPane();
	private CblRecordPnl recordTab = null;
	private CblFilePnl filePnl = null;
//	private CblGenJRecord genPnl = null;
	
	public final CblLoadData cblDtls; 

	private boolean recordPnlUsed = false, filePnlUsed = false, filePnlOnTab = true;
	
	private ISetVisible genOptions;

	

	/**
	 * This class Loads selected copybooks into the record editor DB
	 * @param chooseCopyBook is it a XML (or Cobol Copybook
	 * @param pDBid Database Name
	 * @param pConnectionId Database index (or identifier
	 */
	public CobolAnalyse(		   final String pDBid,
						   final int pConnectionId,
//						   final BasicLayoutCallback callback,
						   final JMenuBar optionMenu,
						   final JButton actionBtn,
						   final String helpUrl)  {
		super((optionMenu==null? COBOL_LAYOUT : COBOL_GEN) + " " + pDBid,
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

		this.cblDtls = new CblLoadData(connectionId, cblDtlPnl, copybookParseChanged, copybookAttrChanged, true); 

 
		init_100_BasicInit(helpUrl);
		
		init_200_LayoutScreen(optionMenu, actionBtn);
		init_300_Listners();

		setVisible(true);
		Common.setDoFree(free,pConnectionId);
	}


	private void init_100_BasicInit(String helpUrl) {

		if (helpUrl == null) {
			cblDtlPnl.setHelpURLre(Common.formatHelpURL(Common.HELP_COPYBOOK));
		} else {
			cblDtlPnl.setHelpURLre(Common.formatHelpURL(helpUrl));
		}
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
	private void init_200_LayoutScreen(JMenuBar optionMenu, JButton actionBtn) {
//		BaseHelpPanel cblPnl = new BaseHelpPanel("cblDtl");
		boolean addLoadBtn = optionMenu == null;
		CblDtlsPnl cblDetails = new CblDtlsPnl(cblDtls, addLoadBtn, actionBtn);
		ReMainFrame frame = ReMainFrame.getMasterFrame();
		int maxHeight = frame.getDesktop().getHeight() - 1;
		int maxWidth = frame.getDesktop().getWidth() - 1;
		int pnlWidth = Math.min(maxWidth, SwingUtils.STANDARD_FONT_WIDTH * 145);
		JSplitPane splitPane;
		
//		pnl.addComponentRE(1, 5, SwingUtils.STANDARD_FONT_HEIGHT * 12,
//				BasePanel.GAP0,
//		        BasePanel.FULL, BasePanel.FULL,
//				tips);

		
		cblDtlsTab.addTab("Cobol", new JScrollPane(cblTA));

		Dimension preferredSize = cblDetails.cblPnl.getPreferredSize();
		
		//System.out.println("***> " + (pnlWidth * 11 / 20) + ", " + (preferredSize.width + SwingUtils.STANDARD_FONT_WIDTH * 20));
		int minPnlWidth = Math.min(pnlWidth * 11 / 20, preferredSize.width + SwingUtils.STANDARD_FONT_WIDTH * 20);
		int height = addLoadBtn ? preferredSize.height : maxHeight;
		
		
		if (maxWidth <= 1600) {
			Dimension pnlPreferredSize = new Dimension(
					minPnlWidth, 
					height);
			cblDetails.cblPnl.setPreferredSize(pnlPreferredSize);
			splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, cblDetails.cblPnl, cblDtlsTab);
		} else {
			filePnl = new CblFilePnl(cblDtls, cblDtlPnl);
			filePnlUsed = true;
			filePnlOnTab = false;
			//splitPane.setDividerLocation(0.5);
			Dimension pnlPreferredSize = new Dimension(
					maxWidth / 3 - 2, 
					height);
			cblDetails.cblPnl.setPreferredSize(pnlPreferredSize);

			splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, cblDetails.cblPnl, cblDtlsTab);
			splitPane.setPreferredSize(new Dimension(
					maxWidth * 2 / 3, 
					height));

			splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPane, filePnl.fileTab);
			
			pnlWidth = maxWidth;
			cblDtlPnl.registerOneComponentRE(filePnl.fileTab);
		}
		cblDtlPnl.registerOneComponentRE(cblDetails.cblPnl);
		cblDtlPnl.registerOneComponentRE(cblDtlsTab);
		
		//splitPane.setDividerLocation(0.6);
		cblDtlPnl.addComponentRE(0, 6, BasePanel.FILL, BasePanel.GAP1,
		        BasePanel.FULL, BasePanel.FULL,
		        splitPane
		        );


		cblDtlPnl.addComponentRE(1, 5, 90 , 1,
		        BasePanel.FULL, BasePanel.FULL,
		        cblDtls.msgField);
		//pnl.setGap(BasePanel.GAP1);

			//find.addActionListener(this);
		
//		cblDtlsTab.addTab("Cobol", new JScrollPane(cblTA));

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		//pnl.done();
		
		if (optionMenu != null) {
			this.setJMenuBar(optionMenu);
		}

		this.getContentPane().add(cblDtlPnl);

		pack();

		setBounds(getY(), getX(), 
				pnlWidth,
				Math.min(maxHeight, getHeight() + SwingUtils.STANDARD_FONT_HEIGHT * 11));
	}
	
	private void init_300_Listners() {
		
	
		
		cblTA.setEditorKit(new CobolCopybookEditorKit());
		cblTA.setFont(SwingUtils.getMonoSpacedFont());
	
		cblDtls.helpBtn.addActionListener(this); 
		cblDtls.genJRecordBtn.addActionListener(this);
		cblDtls.regenSelectionBtn.addActionListener(this);
		cblDtls.addSelectionBtn.addActionListener(this);
		//cblDtls.goBtn.addActionListener(this);

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
		
		cblDtlPnl.registerComponentRE(cblTA);
	}

	private void cobolFilenameChanged() {
		
		String f = cblDtls.copybookFileCombo.getText();
		File file;
		String s = "";
		if (f != null && f.length() > 0) {
			file = new File(f);
			if (file.isDirectory()) {
				cblDtls.msgField.logMsg(AbsSSLogger.ERROR, UtMessages.FILE_IS_DIRECTORY.get(f));
			} else if ((file = new File(f)).exists()) {
				try {
					s = FileUtils.readFile(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				cblDtls.msgField.logMsg(AbsSSLogger.ERROR, UtMessages.COPYBOOK_DOES_NOT_EXIST.get(f));
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
				cblDtlPnl.registerComponentRE(recordTab.recordPnl);
			}
			
			recordTab.notifyOfUpdate(xRecord);
			
			if (! recordPnlUsed) {
				cblDtlsTab.addTab("Records", recordTab.recordPnl);
				recordPnlUsed = true;

				//recordTab.recordPnl.requestFocus();
				cblDtlsTab.setSelectedComponent(recordTab.recordPnl);
			}
		} else if (recordTab != null) {
			cblDtlsTab.remove(recordTab.recordPnl);
			recordPnlUsed = false;
		}
		
		if (xRecord != null) {
			filesDisplay();
		}
	}
	
	private void updateFilesCheck() {
		ExternalRecord xRecord = cblDtls.getXRecord();
		
		if (xRecord != null) {
			filesDisplay();
		} else if (filePnlUsed && filePnlOnTab) {
			cblDtlsTab.remove(filePnl.fileTab);
			filePnlUsed = false;
		}
	}


	/**
	 * 
	 */
	private void filesDisplay() {
		if (cblDtls.getFileBytes() != null) {
			if (filePnl == null) {
				filePnl = new CblFilePnl(cblDtls, cblDtlPnl);
				cblDtlPnl.registerComponentRE(filePnl.fileTab);
			}
			
			filePnl.updateScreen();
			
			if (! filePnlUsed) {
				cblDtlsTab.addTab("File", filePnl.fileTab);
				filePnlUsed = true;
			}
			
			if (filePnlOnTab) {
				cblDtlsTab.setSelectedComponent(filePnl.fileTab);
			}
		} else if (filePnlUsed && filePnlOnTab) {
			cblDtlsTab.remove(filePnl.fileTab); 
			filePnlUsed = false;
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
	        cblDtlPnl.showHelpRE();
		} else if (event.getSource() == cblDtls.genJRecordBtn) {			
			genCobol();
		} else if (event.getSource() == cblDtls.regenSelectionBtn) {
			ExternalRecord xRecord = cblDtls.getXRecord();
			if (xRecord != null) {
				recordTab.notifyOfUpdate(xRecord);
			}
		} else if (event.getSource() == cblDtls.addSelectionBtn) {
			filePnl.redoAnalysis();
		}
	}

	
	public final void genCobol() {
		CblLoadData cblDtls = this.getCblDtls();
		ExternalRecord xRecord = cblDtls.getXRecord();
		
		if (xRecord == null) {
			ReOptionDialog.showMessageDialog(this, "Can not load the schema - check the Copybook / parameters");
			return;
		}

		if (genOptions == null) {
			genOptions = new net.sf.RecordEditor.re.cobol.CodeGenOptionsCbl(cblDtls);
		}
		genOptions.setVisible(true);
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
            cblDtlPnl.showHelpRE();
        }

    }

    /**
     * @see net.sf.RecordEditor.utils.common.ReActionHandler#isActionAvailable(int)
     */
    public boolean isActionAvailable(int action) {
        return action == ReActionHandler.HELP;
    }


	/**
	 * @return the cblDtls
	 */
	protected final CblLoadData getCblDtls() {
		return cblDtls;
	}


	/**
	 * 
	 * @see net.sf.RecordEditor.utils.swing.BaseHelpPanel#showHelpRE()
	 */
	public final void showHelpRE() {
		cblDtlPnl.showHelpRE();
	}
}
