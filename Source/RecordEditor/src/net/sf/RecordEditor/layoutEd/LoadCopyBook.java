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
package net.sf.RecordEditor.layoutEd;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.External.CopybookLoaderFactory;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.JRecord.Log.ScreenLog;
import net.sf.RecordEditor.layoutEd.panels.RecordEdit1Record;
import net.sf.RecordEditor.layoutEd.utils.LeMessages;
import net.sf.RecordEditor.re.db.Record.ExtendedRecordDB;
import net.sf.RecordEditor.re.db.Table.TableDB;
import net.sf.RecordEditor.re.db.Table.TableRec;
import net.sf.RecordEditor.re.openFile.ComputerOptionCombo;
import net.sf.RecordEditor.re.openFile.SplitCombo;
import net.sf.RecordEditor.re.util.CopybookLoaderFactoryDB;
import net.sf.RecordEditor.utils.BasicLayoutCallback;
import net.sf.RecordEditor.utils.ReadFile;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.edit.ManagerRowList;
import net.sf.RecordEditor.utils.jdbc.DBComboModel;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBox;
import net.sf.RecordEditor.utils.swing.BmKeyedComboModel;
import net.sf.RecordEditor.utils.swing.FileChooser;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.ComboBoxs.DelimiterCombo;
import net.sf.RecordEditor.utils.swing.ComboBoxs.ManagerCombo;
import net.sf.RecordEditor.utils.swing.ComboBoxs.QuoteCombo;




/**
 * This class will load either a Cobol Copybook or XML copybook
 * into the RecordEditor Databases
 *
 * @author Bruce Martin
 * @version 0.51
 *
 * Modification log:
 * On 2006/06/28 by Jean-Francois Gagnon:
 *    - Added a Fujitsu option in COMPUTER_OPTIONS
 *    2007/01/10 Bruce Martin (version 0.56)
 *    - Changed to use new FileChooser dialogue
 *    - Changed to extend ReFrame and implement ReActionHandler
 *
 *    2007/01/10 Bruce Martin (version 0.60)
 *    - Changed to use CopybookloaderFactory
 *    - Changed to use ComputerOptionsCombo, SplitCombo's
 */
@SuppressWarnings("serial")
public class LoadCopyBook extends ReFrame implements ActionListener {

	private static final String COBOL_LAYOUT = LangConversion.convert(LangConversion.ST_FRAME_HEADING, "Load Cobol Record Layout - DB ");

	private static final String SELECTED_LOADER = LangConversion.convert(LangConversion.ST_FRAME_HEADING, "Load Record Layout using selectedLoader");

	private String formDescription;

	private TableDB         systemTable = new TableDB();
	private BmKeyedComboModel structureModel = new BmKeyedComboModel(new ManagerRowList(
			LineIOProvider.getInstance(), false));

	private DBComboModel<TableRec>    systemModel
			= new DBComboModel<TableRec>(systemTable, 0, 1, true, false);



		/* screen fields */
	private JEditorPane tips;

	private String copybookPrompt;

	private ManagerCombo loaderOptions = ManagerCombo.newCopybookLoaderCombo();
	private FileChooser  copybookFile  = new FileChooser();
	private JTextField   fontName      = new JTextField();
	private ComputerOptionCombo
	                     binaryOptions = new ComputerOptionCombo();
	private JButton      go            = SwingUtils.newButton("Go");
	private JButton      helpBtn       = SwingUtils.getHelpButton();
	private SplitCombo   splitOptions  = new SplitCombo();
	private BmKeyedComboBox
						 fileStructure;

    private BmKeyedComboBox      system;

    private DelimiterCombo fieldSeparator = DelimiterCombo.NewDelimComboWithDefault();
    private JComboBox quote = QuoteCombo.newCombo(); //Common.QUOTE_LIST);

	private BaseHelpPanel pnl = new BaseHelpPanel();

	//private JTextField msgField = new JTextField();
	private ScreenLog msgField = new ScreenLog(pnl);

		/* input params */
	private int connectionId;
	private boolean copybookChoice;

	private HashMap<String, Integer> tableId = null;
	private int maxKey;
	TableDB tblsDB = null;

	private BasicLayoutCallback layoutCallback;



	/**
	 * This class Loads selected copybooks into the record editor DB
	 * @param chooseCopyBook is it a XML (or Cobol Copybook
	 * @param pDBid Database Name
	 * @param pConnectionId Database index (or identifier
	 */
	public LoadCopyBook(final boolean chooseCopyBook,
						   final String pDBid,
						   final int pConnectionId,
						   final BasicLayoutCallback callback)  {
		super((chooseCopyBook ? SELECTED_LOADER
							 : COBOL_LAYOUT) + " " + pDBid,
			   "", null);
		boolean free = Common.isSetDoFree(false);

		this.connectionId = pConnectionId;
		this.copybookChoice = chooseCopyBook;
		layoutCallback = callback;
		init(chooseCopyBook);

		BaseHelpPanel innerPnl = new BaseHelpPanel();
		JScrollPane innerScroll;
		ReMainFrame frame = ReMainFrame.getMasterFrame();
		int height = frame.getDesktop().getHeight() - 1;


		if (chooseCopyBook) {
			pnl.setHelpURL(Common.formatHelpURL(Common.HELP_COPYBOOK_CHOOSE));
		} else {
			pnl.setHelpURL(Common.formatHelpURL(Common.HELP_COPYBOOK));
		}
		//pnl.addComponent("", null, helpBtn);
		//pnl.setGap(BasePanel.GAP1);
		pnl.addComponent(1, 5, SwingUtils.STANDARD_FONT_HEIGHT * 16,
				BasePanel.GAP0,
		        BasePanel.FULL, BasePanel.FULL,
				tips);
		//innerPnl.setGap(BasePanel.GAP1);

		if (chooseCopyBook) {
			loaderOptions.setEnglish(Common.OPTIONS.COPYBOOK_READER.get());
			if (loaderOptions.getSelectedIndex() < 0) {
				loaderOptions.setSelectedIndex(0);
			}
			innerPnl.setGap(BasePanel.GAP);
			innerPnl.addLine("Copybook Type", loaderOptions);
			innerPnl.setGap(BasePanel.GAP);
		}

		innerPnl.addLine(copybookPrompt, copybookFile, copybookFile.getChooseFileButton());
		innerPnl.setGap(BasePanel.GAP0);
		innerPnl.addLine("Split Copybook", splitOptions);
		innerPnl.addLine("Font Name", fontName);
		innerPnl.addLine("Binary Format", binaryOptions, go);

		innerPnl.addLine("File Structure", fileStructure);
		innerPnl.addLine("System", system);

		if (chooseCopyBook) {
			innerPnl.addLine("Field Seperator", fieldSeparator);
			innerPnl.addLine("Quote", quote);
		}

		innerScroll = new JScrollPane(innerPnl);
		innerScroll.setBorder(BorderFactory.createEmptyBorder());

		pnl.addComponent(0, 6, BasePanel.FILL, 0,
		        BasePanel.FULL, BasePanel.FULL,
		        innerScroll);


		pnl.setGap(BasePanel.GAP1);

		pnl.addComponent(1, 5, 90 , 1,
		        BasePanel.FULL, BasePanel.FULL,
				msgField);
		//pnl.setGap(BasePanel.GAP1);

		helpBtn.addActionListener(this);
		go.addActionListener(this);
		//find.addActionListener(this);

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		//pnl.done();

		this.getContentPane().add(pnl);

		pack();

		setBounds(getY(), getX(), SwingUtils.STANDARD_FONT_WIDTH * 86, Math.min(height, getHeight()));

		setVisible(true);
		Common.setDoFree(free,pConnectionId);

		if (chooseCopyBook) {
			copybookFile.addFcFocusListener(
					new FocusAdapter() { @Override public void focusLost(FocusEvent e) {
						int loaderType = CopybookLoaderFactory.getLoaderType(copybookFile.getText());
						if (loaderType >= 0) {
							loaderOptions.setSelectedIndex(loaderType);
						}
					}}
			);
		}
	}


	private void init(boolean choseCopyBook) {
	    String s;

	    //CopybookLoaderFactory loaders = CopybookLoaderFactoryDB.getInstance();

		String typeOfCopybook = choseCopyBook ? "User Selected"
				  : "Cobol";

		copybookPrompt = typeOfCopybook + " Copybook";
	    formDescription = LangConversion.convertId(
	    		LangConversion.ST_MESSAGE,
	    		"CopybookLoad",
	    		"<font COLOR=Blue>This option loads a <b>{0} Copybooks</b> into the <b>Record Edit DB.</b></font>"
	    	    + ReadFile.getCb2XmlHtml(),
	    		new Object[] {typeOfCopybook});

	    tips = new JEditorPane("text/html", formDescription);
	    tips.setEditable(false);
	    copybookFile.setText(Common.OPTIONS.DEFAULT_COBOL_DIRECTORY.get());

	    splitOptions.setSelectedIndex(0);

	    fieldSeparator.setSelectedIndex(0);
	    quote.setSelectedIndex(0);

	    tips.setCaretPosition(1);

//		structureTable.setConnection(Common.getDBConnectionLogErrors(connectionId));
		systemTable.setConnection(new ReConnection(connectionId));

//		structureTable.setParams(Common.TI_FILE_STRUCTURE);
		systemTable.setParams(Common.TI_SYSTEMS);

		fileStructure  = new BmKeyedComboBox(structureModel, false);
		system         = new BmKeyedComboBox(systemModel, false);

		s = Common.OPTIONS.DEFAULT_IO_NAME.get();
	    if (! "".equals(s)) {
	    	fileStructure.setSelectedDisplay(s);
	    }
	    //System.out.println(">> File Structure: " + s + " " + fileStructure.getSelectedItem());

	    s = Common.OPTIONS.DEFAULT_BIN_NAME.get();
	    if (! "".equals(s)) {
	    	binaryOptions.setEnglishText(s);
	    }

	}



	/**
	 * @see java.awt.Event.actionPerformed
	 */
	public void actionPerformed(final ActionEvent event) {

	    if (event.getSource() == helpBtn) {
	        pnl.showHelp();
		} else {
			boolean free = Common.isSetDoFree(false);
			String copyBookFile = copybookFile.getText();
			int binaryFormat = binaryOptions.getSelectedValue();
			int split = splitOptions.getSelectedValue();

			try {
			    if (copyBookFile != null && !"".equals(copyBookFile)) {
			        int systemId = ((Integer) system.getSelectedItem()).intValue();
			        CopybookLoaderFactory loaders = CopybookLoaderFactoryDB.getInstance();
			        ExternalRecord rec;
			        ExtendedRecordDB db = new ExtendedRecordDB();
			        int copybookId = CopybookLoaderFactoryDB.COBOL_LOADER;
			        int fstructure = ((Integer) fileStructure.getSelectedItem()).intValue();

			        if (copybookChoice) {
				         //copybookId = ((Integer) loaderOptions.getSelectedItem()).intValue();
			            copybookId = loaderOptions.getSelectedIndex();
			        }

			        //System.out.println("~~~> " + copybookId + " " + loaders.getNumberofLoaders()
			        //        + " " + CobolCopybookLoader.isAvailable());
			        rec = loaders.getLoader(copybookId).loadCopyBook(copyBookFile,
			                split,
			                connectionId,
			                fontName.getText(),
			                binaryFormat,
			                systemId,
			                msgField);

			        if (rec == null) {
			        	msgField.logMsg(AbsSSLogger.ERROR, LeMessages.ERROR_LOADING_COPYBOOK.get());
			        }

			        if (fstructure != Constants.IO_DEFAULT
			        && rec.getFileStructure() <= Constants.IO_DEFAULT) {
			        	rec.setFileStructure(fstructure);
			        }

			        ap100_updateSystem(rec);

			        //System.out.println("## " + rec.getRecordId() + " " + rec.getRecordName());
			        db.setConnection(new ReConnection(connectionId));

//			        System.out.print("RecordId: " + rec.getRecordId());
			        db.checkAndUpdate(rec);
//			        System.out.println(" !! " + rec.getRecordId());

//			        msgField.logMsg(AbsSSLogger.SHOW, "-->> " + copyBookFile + " processed");
//			        msgField.logMsg(AbsSSLogger.SHOW, "      Copybook: " + rec.getRecordName());
			        msgField.logMsg(
			        		AbsSSLogger.SHOW,
			        		LeMessages.COPYBOOK_LOADED.get(new Object[] {copyBookFile, rec.getRecordName()}));
			        System.out.println(LeMessages.COPYBOOK_LOADED.get(new Object[] {copyBookFile, rec.getRecordName()}));
			        db.close();

			        if (layoutCallback != null) {
			        	layoutCallback.setRecordLayout(0, rec.getRecordName(), null);
			        }

			        if (rec.getNumberOfRecords() > 1
			        && (   copybookId == CopybookLoaderFactoryDB.COBOL_LOADER
			            || copybookId == CopybookLoaderFactoryDB.CB2XML_LOADER)) {
			        	RecordEdit1Record ep = new RecordEdit1Record(
			        			Common.getSourceId()[connectionId], connectionId,
			        			rec.getRecordId());
			        	JOptionPane.showMessageDialog(
			        			ep,
			        			LeMessages.DEFINE_RECORD_SELECTION.get());
			        }
			    }
			} catch (Exception ex) {
			    msgField.logMsg(AbsSSLogger.ERROR, ex.getMessage());
			    ex.printStackTrace();
            } finally {
            	Common.setDoFree(free, connectionId);
		        tableId = null;
		        tblsDB  = null;
	        }

		}
	}


	/**
	 * Set the System Id based on the System Name
	 *
	 */
	private void ap100_updateSystem(ExternalRecord rec) throws SQLException {

		int i, ckey;
		String s;
		TableRec aTbl;
        int fieldSeparatorIdx = fieldSeparator.getSelectedIndex();
        int quoteIdx = quote.getSelectedIndex();

		for (i = 0; i < rec.getNumberOfRecords(); i++) {
			ap100_updateSystem(rec.getRecord(i));
		}

		s =  rec.getSystemName();
		if (s != null) {
			if (tableId == null) {
				tableId = new HashMap<String, Integer>();
				maxKey = 0;
				tblsDB = new TableDB();

				tblsDB.setConnection(new ReConnection(connectionId));
				tblsDB.resetSearch();
				tblsDB.setParams(Common.TI_SYSTEMS);
				tblsDB.open();
				while ((aTbl = tblsDB.fetch()) != null) {
					ckey = aTbl.getTblKey();
					tableId.put(aTbl.getDetails(), Integer.valueOf(ckey));
					if (maxKey < ckey) {
						maxKey = ckey;
					}
				}
				tblsDB.close();
			}


			if (tableId.containsKey(s)) {
				Integer tblKey = tableId.get(s);
				rec.setSystem(tblKey.intValue());
			} else {
				maxKey += 1;
				aTbl = new TableRec(maxKey, s);
				i = 0;
				while ((i++ < 15) && (! tblsDB.tryToInsert(aTbl))) {
					maxKey += 1;
					aTbl.setTblKey(maxKey);
			    }
				tableId.put(s, Integer.valueOf(maxKey));
			}
		}

        if (fieldSeparatorIdx > 0) {
        	rec.setDelimiter(fieldSeparator.getSelectedEnglish());
        }

        if (quoteIdx > 0) {
        	rec.setQuote(Common.QUOTE_LIST[quoteIdx]);
        }
	}

    /**
     * @see net.sf.RecordEditor.utils.common.ReActionHandler#executeAction(int)
     */
    public void executeAction(int action) {

        if (action == ReActionHandler.HELP) {
            pnl.showHelp();
        }

    }

    /**
     * @see net.sf.RecordEditor.utils.common.ReActionHandler#isActionAvailable(int)
     */
    public boolean isActionAvailable(int action) {
        return action == ReActionHandler.HELP;
    }
}
