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


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;

import net.sf.JRecord.External.CopybookLoaderFactory;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.JRecord.Log.ScreenLog;
import net.sf.RecordEditor.layoutEd.panels.RecordEdit1Record;
import net.sf.RecordEditor.layoutEd.utils.LeMessages;
import net.sf.RecordEditor.re.db.Record.ExtendedRecordDB;
import net.sf.RecordEditor.re.db.Table.TableDB;
import net.sf.RecordEditor.re.db.Table.TableRec;
import net.sf.RecordEditor.re.util.CopybookLoaderFactoryDB;
import net.sf.RecordEditor.utils.BasicLayoutCallback;
import net.sf.RecordEditor.utils.ReadFile;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.jdbc.DBComboModel;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBox;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.treeCombo.FileSelectCombo;




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
public class LoadXmlCopyBook extends ReFrame implements ActionListener {

//	private static final String COBOL_LAYOUT = LangConversion.convert(LangConversion.ST_FRAME_HEADING, "Load Cobol Record Layout - DB ");

//	private static final String SELECTED_LOADER = LangConversion.convert(LangConversion.ST_FRAME_HEADING, "Load Record Layout using selectedLoader");

	private String formDescription;

	private TableDB         systemTable = new TableDB();
//	private BmKeyedComboModel structureModel = new BmKeyedComboModel(new ManagerRowList(
//			LineIOProvider.getInstance(), false));

	private DBComboModel<TableRec>    systemModel
			= new DBComboModel<TableRec>(systemTable, 0, 1, true, false);



		/* screen fields */
	private JEditorPane tips;

	private String copybookPrompt;

//	private ManagerCombo loaderOptions = ManagerCombo.newCopybookLoaderCombo();
	private FileSelectCombo  copybookFile  = new FileSelectCombo(Parameters.SCHEMA_LIST, 25, true, false);
//	private JTextField   fontName      = new JTextField();
//	private ComputerOptionCombo
//	                     binaryOptions = new ComputerOptionCombo();
	private JButton      go            = SwingUtils.newButton("Go");
//	private JButton      helpBtn       = SwingUtils.getHelpButton();
//	private SplitCombo   splitOptions  = new SplitCombo();
//	private BmKeyedComboBox
//						 fileStructure;

    private BmKeyedComboBox      system;

//    private DelimiterCombo fieldSeparator = DelimiterCombo.NewDelimComboWithDefault();
//    private JComboBox quote = QuoteCombo.newCombo(); //Common.QUOTE_LIST);

	private BaseHelpPanel pnl = new BaseHelpPanel();

	//private JTextField msgField = new JTextField();
	private ScreenLog msgField = new ScreenLog(pnl);

		/* input params */
	private int connectionId;
//	private boolean copybookChoice;

	private HashMap<String, Integer> tableId = null;
	private int maxKey;
	TableDB tblsDB = null;

	private BasicLayoutCallback layoutCallback;



	/**
	 * This class Loads selected copybooks into the record editor DB
	 * @param pConnectionId Database index (or identifier)
	 * @param callback Call back after DB updated
	 */
	public LoadXmlCopyBook(final int pConnectionId,
						   final BasicLayoutCallback callback)  {
		super(LangConversion.convert(LangConversion.ST_FRAME_HEADING, "Load Xml Record Layout"),
			   "", null);
		boolean free = Common.isSetDoFree(false);

		this.connectionId = pConnectionId;
		layoutCallback = callback;
		init();


		ReMainFrame frame = ReMainFrame.getMasterFrame();
		int height = frame.getDesktop().getHeight() - 1;


		pnl.setHelpURLre(Common.formatHelpURL(Common.HELP_XML_COPYBOOK));
		
		pnl.addComponentRE(1, 5, SwingUtils.STANDARD_FONT_HEIGHT * 14,
				BasePanel.GAP0,
		        BasePanel.FULL, BasePanel.FULL,
				tips);


		pnl.addLineRE(copybookPrompt, copybookFile);
		pnl.setGapRE(BasePanel.GAP0);
		pnl.addLineRE(null, null, go);


//		innerScroll = new JScrollPane(innerPnl);
//		innerScroll.setBorder(BorderFactory.createEmptyBorder());
//
//		pnl.addComponent(0, 6, BasePanel.FILL, 0,
//		        BasePanel.FULL, BasePanel.FULL,
//		        innerScroll);


		pnl.setGapRE(BasePanel.GAP1);

		pnl.addComponentRE(1, 5, 90 , 1,
		        BasePanel.FULL, BasePanel.FULL,
				msgField);
		//pnl.setGap(BasePanel.GAP1);

		//helpBtn.addActionListener(this);
		go.addActionListener(this);
		//find.addActionListener(this);

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		//pnl.done();

		this.getContentPane().add(pnl);

		pack();

		setBounds(getY(), getX(), SwingUtils.STANDARD_FONT_WIDTH * 86, Math.min(height, getHeight()));

		setVisible(true);
		Common.setDoFree(free,pConnectionId);
	}


	private void init() {

		String typeOfCopybook = "Xml";

		copybookPrompt = typeOfCopybook + " Copybook";
	    formDescription = LangConversion.convertId(
	    		LangConversion.ST_MESSAGE,
	    		"Xml Copybook Load",
	    		"<font COLOR=Blue>This option loads a <b>{0} Copybooks</b> into the <b>Record Edit DB.</b></font>"
	    	    + ReadFile.getCb2XmlHtml(),
	    		new Object[] {typeOfCopybook});

	    tips = new JEditorPane("text/html", formDescription);
	    tips.setEditable(false);
	    copybookFile.setText(Common.OPTIONS.DEFAULT_COPYBOOK_DIRECTORY.getWithStar());


	    tips.setCaretPosition(1);

		systemTable.setConnection(new ReConnection(connectionId));

		systemTable.setParams(Common.TI_SYSTEMS);


		system         = new BmKeyedComboBox(systemModel, false);
	}



	/**
	 * @see java.awt.Event.actionPerformed
	 */
	public void actionPerformed(final ActionEvent event) {

//	    if (event.getSource() == helpBtn) {
//	        pnl.showHelp();
//		} else {
			boolean free = Common.isSetDoFree(false);
			String copyBookFile = copybookFile.getText();

			try {
			    if (copyBookFile != null && !"".equals(copyBookFile)) {
			        int systemId = ((Integer) system.getSelectedItem()).intValue();
			        CopybookLoaderFactory loaders = CopybookLoaderFactoryDB.getInstance();
			        ExternalRecord rec;
			        ExtendedRecordDB db = new ExtendedRecordDB();
			        int copybookId = CopybookLoaderFactoryDB.RECORD_EDITOR_XML_LOADER;



			        rec = loaders.getLoader(copybookId).loadCopyBook(copyBookFile,
			                0,
			                connectionId,
			                "",
			                0,
			                systemId,
			                msgField);

			        if (rec == null) {
			        	msgField.logMsg(AbsSSLogger.ERROR, LeMessages.ERROR_LOADING_COPYBOOK.get());
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

//		}
	}


	/**
	 * Set the System Id based on the System Name
	 *
	 */
	private void ap100_updateSystem(ExternalRecord rec) throws SQLException {

		int i, ckey;
		String s;
		TableRec aTbl;
//        int fieldSeparatorIdx = fieldSeparator.getSelectedIndex();
//        int quoteIdx = quote.getSelectedIndex();

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
	}

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
