package net.sf.RecordEditor.layoutEd.load;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.Def.ExternalField;
import net.sf.JRecord.ExternalRecordSelection.ExternalFieldSelection;
import net.sf.JRecord.ExternalRecordSelection.ExternalGroupSelection;
import net.sf.JRecord.ExternalRecordSelection.ExternalSelection;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.JRecord.Log.ScreenLog;
import net.sf.JRecord.Numeric.ConversionManager;
import net.sf.JRecord.Numeric.Convert;
import net.sf.RecordEditor.layoutWizard.FileAnalyser;
import net.sf.RecordEditor.re.db.Table.TableDB;
import net.sf.RecordEditor.re.db.Table.TableRec;
import net.sf.RecordEditor.re.openFile.ComputerOptionCombo;
import net.sf.RecordEditor.re.openFile.SplitCombo;
import net.sf.RecordEditor.re.util.XmlCopybookLoaderDB;
import net.sf.RecordEditor.utils.charsets.FontCombo;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.common.StreamUtil;
import net.sf.RecordEditor.utils.edit.ManagerRowList;
import net.sf.RecordEditor.utils.jdbc.DBComboModel;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBox;
import net.sf.RecordEditor.utils.swing.BmKeyedComboModel;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.Combo.ComboStdOption;
import net.sf.RecordEditor.utils.swing.treeCombo.FileSelectCombo;
import net.sf.RecordEditor.utils.swing.treeCombo.NormalCombo;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeCombo;
import net.sf.RecordEditor.utils.swing2.helpers.EventDetails;
import net.sf.RecordEditor.utils.swing2.helpers.FocusActionListnerAdapter;
import net.sf.cb2xml.Cb2Xml2;
import net.sf.cb2xml.CopyBookAnalyzer;
import net.sf.cb2xml.def.Cb2xmlConstants;
import net.sf.cb2xml.def.NumericDefinition;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class CblLoadData {
	
	private final static String[] EMPTY_FIELDS = {"", "", "", "", ""};

	private TableDB         systemTable = new TableDB();
	private BmKeyedComboModel structureModel = new BmKeyedComboModel(new ManagerRowList(
			LineIOProvider.getInstance(), false));

	private DBComboModel<TableRec>    systemModel
			= new DBComboModel<TableRec>(systemTable, 0, 1, true, false);

	public final FileSelectCombo  copybookFileCombo, sampleFileCombo; 
	public final FontCombo    fontNameCombo = new FontCombo();
	private final ComputerOptionCombo
	                          dialectCombo = new ComputerOptionCombo();
	public final JButton      genJRecord    = SwingUtils.newButton("Generate JRecord Code");
	public final JButton      go            = SwingUtils.newButton("Load Cobol");
	public final JButton      helpBtn       = SwingUtils.getHelpButton();
	private final JComboBox   copybookFormatCombo 
	                                        = new JComboBox();
	private final SplitCombo  splitOptionsCombo  = new SplitCombo();
	private final BmKeyedComboBox
						      fileStructureCombo;

    public final BmKeyedComboBox      system;
    public final TreeCombo fields = new TreeCombo();
    
    public final ScreenLog msgField;// = new ScreenLog(pnl);
    
    private AbstractTableModel recordMdl = null;

	private Document document = null;

	private XmlCopybookLoaderDB loader = new XmlCopybookLoaderDB();
	private XmlCopybookLoaderDB jRecLoader = null; //new XmlCopybookLoaderDB();
	private ExternalRecord xRecord = null;
	private final int connectionId;


	private byte[] fileBytes = null;
	private String lastFile = "";
	
	private String cobolCopybook = "";
	
	private final ChangeListener copybookParseChanged, copybookAttrChanged;
	private boolean doSplitChanged = true, doAttrChanged = true;
	private int lastDialect, lastCopybookFormat, lastSplit, lastFileStructure; 
	private String lastFont;
	
	private int defaultRow = -1;
	private boolean copybookChanged = true;
	private ArrayList<String[]> recordSelect = new ArrayList<String[]>();
	
	private ArrayList<AbstractLine>[] dataLines = null;
	
	private ArrayList<NormalCombo> fieldCombos = new ArrayList<NormalCombo>();

	
	
	public CblLoadData(int connectionId, JComponent pnl, ChangeListener copybookParseChanged, ChangeListener copybookAttrChanged) {
		//String dir = Common.OPTIONS.DEFAULT_COBOL_DIRECTORY.getWithStar();
		
		this.connectionId = connectionId;
		this.copybookParseChanged = copybookParseChanged;
		this.copybookAttrChanged = copybookAttrChanged;
		this. msgField = new ScreenLog(pnl);
		
		copybookFileCombo = new FileSelectCombo(Parameters.COBOL_COPYBOOK_LIST, 25, true, false, true);
		sampleFileCombo   = new FileSelectCombo(Parameters.SAMPLE_FILE_LIST, 25, true, false, true);
	    set(copybookFileCombo, Common.OPTIONS.DEFAULT_COBOL_DIRECTORY.getWithStar());
	    set(sampleFileCombo,   Common.OPTIONS.DEFAULT_FILE_DIRECTORY.getWithStar());

	    splitOptionsCombo.setSelectedIndex(0);

		systemTable.setConnection(new ReConnection(connectionId));

//		structureTable.setParams(Common.TI_FILE_STRUCTURE);
		systemTable.setParams(Common.TI_SYSTEMS);

		fileStructureCombo  = new BmKeyedComboBox(structureModel, false);
		system         = new BmKeyedComboBox(systemModel, false);

		String s = Common.OPTIONS.DEFAULT_IO_NAME.get();
	    if (! "".equals(s)) {
	    	fileStructureCombo.setSelectedDisplay(s);
	    }

	    s = Common.OPTIONS.DEFAULT_BIN_NAME.get();
	    if (! "".equals(s)) {
	    	dialectCombo.setEnglishText(s);
	    }


	    copybookFormatCombo.addItem(getItem(Cb2xmlConstants.USE_STANDARD_COLUMNS, "Use Standard Cobol Columns (6-71)"));
	    copybookFormatCombo.addItem(getItem(Cb2xmlConstants.USE_COLS_6_TO_80, "Cobol Columns (6-80)"));
	    copybookFormatCombo.addItem(getItem(Cb2xmlConstants.USE_LONG_LINE, "Use Very long line"));
	    copybookFormatCombo.addItem(getItem(Cb2xmlConstants.FREE_FORMAT, "Free format (start column 2)"));
	    copybookFormatCombo.setSelectedIndex(0);
	    
	    dialectCombo.setSelectedIndex(1);
	    
	    lastSplit = getSplitOption();
		lastCopybookFormat = getCopybookFormat();
		
		lastFont = fontNameCombo.getText();
		lastFileStructure = getFileStructure();
		lastDialect = getDialect();
	    
		FocusActionListnerAdapter fa1 = new FocusActionListnerAdapter() {	
			@Override public void doAction(EventDetails e) {
				checkParseChange();
			}
		};
		FocusActionListnerAdapter fa2 = new FocusActionListnerAdapter() {	
			@Override public void doAction(EventDetails e) {
				checkAttrChange();			}
		};
		
		splitOptionsCombo.addFocusListener(fa1);
		splitOptionsCombo.addActionListener(fa1);
		copybookFormatCombo.addFocusListener(fa1);
		copybookFormatCombo.addActionListener(fa1);
		
		dialectCombo.addFocusListener(fa2);
		dialectCombo.addActionListener(fa2);
		fileStructureCombo.addFocusListener(fa2);
		fileStructureCombo.addActionListener(fa2);
		
		fontNameCombo.addTextChangeListner(fa2);;
	}

	private void checkParseChange() {
		int cSplit = getSplitOption();
		int cCopybookFormat = getCopybookFormat();
		if (doSplitChanged
		&& (  lastSplit != cSplit
		  ||  lastCopybookFormat != cCopybookFormat)
		) {
			xRecord = null;
			document = null;

			copybookChanged = true;
			copybookParseChanged.stateChanged(null);
			
			lastSplit = cSplit;
			lastCopybookFormat = cCopybookFormat;
		}
	}

	
	private void checkAttrChange() {
		int cFileStructure = getFileStructure();
		int cDialect = getDialect();
		String cFont = fontNameCombo.getText();
		if (doAttrChanged
		&& (  lastFileStructure != cFileStructure
		  ||  lastDialect != cDialect
		  ||  ! lastFont.equals(cFont))
		) {
			xRecord = null;
			document = null;

			copybookChanged = true;
			copybookAttrChanged.stateChanged(null);
			
			lastFileStructure = cFileStructure;
			lastDialect = cDialect;
			lastFont = cFont;
		}
	}

	/**
	 * @param dir
	 */
	private void set(FileSelectCombo fileSelect, String dir) {
		if (dir != null) {
	    	fileSelect.setText(dir);
	    }
	}
	
	

	public final ComboStdOption<Integer> getItem(int key, String s) {
		return new ComboStdOption<Integer>(key, s, LangConversion.convertComboItms("CobolLineFmt", s));
	}


	public final boolean setCobolCopybook(String s) {
		document = null;
		if (s.length() == 0) {
			xRecord = null;
			copybookChanged = true;
			return true;
		} else if (! cobolCopybook.equals(s)) {
			return readCobol(s);
		}
		return false;
	}
	
	private void readCobol() {
		if (document == null && cobolCopybook != null && cobolCopybook.length() > 0) {
			readCobol(cobolCopybook);
		}
	}

	/**
	 * @param s
	 * @return
	 */
	private boolean readCobol(String s) {
		StringReader r = new StringReader(s);
		boolean checkMultiRecords = cobolCopybook != null && (! cobolCopybook.equals(s));
		copybookChanged = true;
		xRecord = null;
		cobolCopybook = s;
		try {
			Convert conv = ConversionManager.getInstance().getConverter4code(getDialect()) ;
			CopyBookAnalyzer.setNumericDetails((NumericDefinition) conv.getNumericDefinition());

			document = Cb2Xml2.convert(r, "", false, getCopybookFormat());
			
			if (checkMultiRecords) {
				checkForMultipleRecords();
			}
//				checkRecords();
			return true;
		} catch (Exception e) {
			msgField.logMsg(AbsSSLogger.ERROR, "Error parsing Cobol: " + e);
			e.printStackTrace();
		}
		return false;
	}
	

	/**
	 * Check if there are multiple Records (01's or Redefines) in the copybook
	 */
	private void checkForMultipleRecords() {
		
		NodeList childNodes = document.getChildNodes();
		int count01 = 0;
		
		int lastNodeIdx = childNodes.getLength() - 1;
		for (int i = lastNodeIdx; i >= 0; i--) {
			org.w3c.dom.Node node = childNodes.item(i);
			if (node != null && node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
		       Element childElement = (Element) node;
		       NodeList nl = childElement.getChildNodes();
		       for (int j = nl.getLength() - 1; j >= 0; j--) {
					org.w3c.dom.Node n = nl.item(j);
					if (n != null && n.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
			           Element ce = (Element) n;
			           if (ce.getAttribute(Cb2xmlConstants.LEVEL).equals("01")) {
			        	   if (count01++ > 0) {
			        		   setSplitInternal(CopybookLoader.SPLIT_01_LEVEL);
			        		   return;
			        	   }
			           }
					}
		       }
			}
		}
		
		if (childNodes.getLength() == 0) {return;};
		boolean isRedefine = false;
		
		org.w3c.dom.Node node = childNodes.item(lastNodeIdx);
		while (node != null) {
			if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
			    Element childElement = (Element) node;
			    if ((childElement.hasAttribute(Cb2xmlConstants.REDEFINES))) {
			    	String sLength = childElement.getAttribute(Cb2xmlConstants.STORAGE_LENGTH);
			    	if (sLength != null && sLength.length() > 0) {
				    	try {
				    		isRedefine = Integer.parseInt(sLength) > 10;
				    	}	catch (Exception e) { 	}
			    	}
			    	break;
			    }
			}
		    NodeList cn = node.getChildNodes();
		    node = cn == null || cn.getLength() == 0
		    			? null
		    			: cn.item(cn.getLength() - 1);
		}
		
		if (isRedefine ) {
			int i = 0;
			NodeList childNodes2 = childNodes.item(0).getChildNodes();
			int lastNode = childNodes2.getLength();
			do {
				node = childNodes2.item(i++);
			} while (i < lastNode 
				&&   (! nodeHasChildren(node))
				&&   (! hasPicture(node))
				    ); 
			while (node != null && nodeHasChildren(node) && (! hasPicture(node))) {
				node = node.getChildNodes().item(0);
			}
			
			if (node != null && hasPicture(node)) {
				String n = ((Element) node).getAttribute(Cb2xmlConstants.NAME);
				if (n != null && n.toLowerCase().endsWith("-type") ) {
					setSplitInternal(CopybookLoader.SPLIT_REDEFINE);
				}
			}
		}
	}

	/**
	 * @param node
	 * @return
	 */
	private boolean hasPicture(org.w3c.dom.Node node) {
		return node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE
		    && ((Element) node).hasAttribute(Cb2xmlConstants.PICTURE);
	}

	/**
	 * @param node
	 * @return
	 */
	private boolean nodeHasChildren(org.w3c.dom.Node node) {
		return node.getChildNodes() != null && node.getChildNodes().getLength() > 0;
	}


	/**
	 * 
	 */
	private void setSplitInternal(int split) {
		try {
		   doSplitChanged = false;
		   splitOptionsCombo.setSplitId(split);
		} finally {
		   doSplitChanged = true;
		}
	}


	/**
	 * @return the document
	 */
	public final Document getDocument() {
		return document;
	}

	/**
	 * @return the xRec
	 */
	public final ExternalRecord getXRecord() {
		xRecord = getXRecord(loader, xRecord);
		return xRecord;
	}


	/**
	 * @return the xRec
	 */
	public final ExternalRecord getXRecordJR() {
		if (jRecLoader == null) {
			jRecLoader = new XmlCopybookLoaderDB();
			jRecLoader.setDropCopybookFromFieldNames(false);
			jRecLoader.setUseJRecordNaming(true);
		}
		return getXRecord(jRecLoader, null);
	}
	/**
	 * @return the xRec
	 */
	private final ExternalRecord getXRecord(XmlCopybookLoaderDB xmlLoader, ExternalRecord xRec ) {
		readCobol();
		if (document != null && xRec == null) {
			int split = splitOptionsCombo.getSelectedValue();
			String s = "";
			try {
				String fn = copybookFileCombo.getText();
				File f = new File(fn);
				if (f.exists()) {
					s = Conversion.getCopyBookId(fn);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			xRec = xmlLoader.loadDOMCopyBook(document, s, split, 
					connectionId, fontNameCombo.getText(), 
					dialectCombo.getSelectedValue(),
					((Integer) system.getSelectedItem()).intValue());
		}
		
		if (xRec != null) {
			int num = Math.min(xRec.getNumberOfRecords(), recordSelect.size());
			int fileStructure = getFileStructure();
			
			if (fileStructure != Constants.IO_DEFAULT) {
				xRec.setFileStructure(fileStructure);
			}
			for (int i = 0; i < num; i++) {
				ExternalRecord rec = xRec.getRecord(i);
				if (getRecordSelection(i, 0).length() > 0) {
					ExternalSelection fs = null;
					//ExternalFieldSelection fs = new ExternalFieldSelection(getRecordSelection(i, 0), getRecordSelection(i, 1));
					ArrayList<ExternalFieldSelection> fl = new ArrayList<ExternalFieldSelection>(EMPTY_FIELDS.length - 1);
					for (int j = 1; j < EMPTY_FIELDS.length; j++) {
						if (getRecordSelection(i, j).length() > 0) {
							fl.add(new ExternalFieldSelection(getRecordSelection(i, 0), getRecordSelection(i, j)));
						}
					}

					switch (fl.size()) {
					case 0:
						break;
					case 1:
						fs = fl.get(0);
						break;
					default:
						fs = ExternalGroupSelection.newOr(fl.toArray(new ExternalFieldSelection[fl.size()]));
					}
					rec.setRecordSelection(fs);
				}
				if (fileStructure != Constants.IO_DEFAULT) {
					rec.setFileStructure(fileStructure);
				}
				rec.setDefaultRecord(false);
			}

			if (defaultRow >= 0 && defaultRow < xRec.getNumberOfRecords()) {
				xRec.getRecord(defaultRow).setDefaultRecord(true);
			}
		}
		return xRec;
	}
	
	private int numberOfCriteria(int idx) {
		int num = 0;
		for (int i = 1; i < EMPTY_FIELDS.length; i++) {
			if (getRecordSelection(idx, 1).length() > 0) {
				num += 1;
			}
		}
		
		return num;
	}

	public void checkDataFile() {
		byte[] b = getFileBytes();
		if (b == null || b.length < 10) {return;}
		
		FileAnalyser analyser =  FileAnalyser.getAnaylserNoLengthCheck(b, "40");
		String font = analyser.getFontName();
		
		try {
			doAttrChanged = false;
		
			if (Conversion.isMultiByte(font)) {
				font = Conversion.getDefaultSingleByteCharacterset();
			}
			fontNameCombo.setText(font);
			
			fontNameCombo.setFontList(analyser.getCharsetDetails().getLikelyCharsets(Conversion.getDefaultSingleByteCharacterset(), 10));
			int fileStructure = analyser.getFileStructure();
			fileStructureCombo.setSelectedItem(fileStructure);
			xRecord = null;
		} finally {
			doAttrChanged = true;
		}
	}
	
	
	/**
	 * @return the fileBytes
	 */
	public final byte[] getFileBytes() {
		String samplefile = sampleFileCombo.getText();
		if (fileBytes == null || ! lastFile.equals(samplefile)) {
			fileBytes = null;
			try {
				File file = new File(samplefile);
				if (file.exists() && ! file.isDirectory()) {
					fileBytes = StreamUtil.read(new FileInputStream(file), 0x40000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
					
			lastFile = samplefile;
			copybookChanged = true;
		}
		return fileBytes;
	}


	/**
	 * @param dataLines the dataLines to set
	 */
	public final void setDataLines(ArrayList<AbstractLine>[] dataLines, boolean update, boolean updateDone) {
		this.dataLines = dataLines;
		if (update) {
			updateTestValues(updateDone);
		}
	}
	
	public final void updateTestValues(boolean updateDone) {
		if (updateDone) {
			if (recordMdl != null) {
				recordMdl.fireTableDataChanged();
			}
		} else if (dataLines != null && dataLines.length > 1
		&& recordSelect != null && recordSelect.size() == dataLines.length) {
			boolean found = false;
			
			for (int i = dataLines.length - 1; i >= 0; i--) {
				String fieldName = getRecordSelection(i, 0);
				if (dataLines[i] != null 
				&& dataLines[i].size() > 0 
				&& fieldName.length() > 0 
				&& getRecordSelection(i, 1).length() == 0
				&& (found || i > 0)) {
					setRecordSelection(i, 1, dataLines[i].get(0).getFieldValue(fieldName).asString(), false);
					found = true;
				}
			}
			
			if (found && recordMdl != null) {
				recordMdl.fireTableDataChanged();
			}
		}
	}

	/**
	 * @return the defaultRow
	 */
	public final int getDefaultRow() {
		return defaultRow;
	}

	/**
	 * @param defaultRow the defaultRow to set
	 */
	public final void setDefaultRow(int defaultRow) {
		
		if ((this.defaultRow != defaultRow)) {
			copybookChanged = true;
			this.defaultRow = defaultRow;
			checkIfAllSelectionsDefined();
		}
	}

	
	public void initRecordSelection(ExternalRecord xRec) {
		int size =  xRec.getNumberOfRecords();
		copybookChanged = true;
		recordSelect.ensureCapacity(size);
		fieldCombos.clear();
		fieldCombos.ensureCapacity(size);
		for (int i = 0; i < size; i++) {
			if (i < recordSelect.size()) {
				Arrays.fill(recordSelect.get(i), "");
			} else {
				recordSelect.add(EMPTY_FIELDS.clone());
			}
			fieldCombos.add(null);
		}

	}
	
	public String getRecordSelection(int recordIdx, int fieldIdx) {
		if (recordIdx >= recordSelect.size()) {return "";}
		return recordSelect.get(recordIdx)[fieldIdx];
	}
	
	
	public void setRecordSelection(int recordIdx, int fieldIdx, String value, boolean check) {
		initRecordSelect(recordIdx);

		if (recordSelect.get(recordIdx)[fieldIdx] != value) {
			copybookChanged = true;
			xRecord = null;
			recordSelect.get(recordIdx)[fieldIdx] = value;
			if (check) {
				checkIfAllSelectionsDefined();
			}
		}
	}

	public void addRecordSelection(int recordIdx, String value, boolean check) {
		initRecordSelect(recordIdx);

		String[] currValues = recordSelect.get(recordIdx);
		for (int i = 1; i < currValues.length; i++) {
			if ( currValues[i].equalsIgnoreCase(value)) {
				return;
			}
		}
		for (int i = 1; i < currValues.length; i++) {
			if ( currValues[i].length() == 0) {
				currValues[i] = value;
				break;
			}
		}
	}

	/**
	 * @param recordIdx
	 */
	private void initRecordSelect(int recordIdx) {
		while (recordIdx >= recordSelect.size()) {
			recordSelect.add(EMPTY_FIELDS.clone());
		}
	}

	public void checkIfAllSelectionsDefined() {
		if (xRecord != null && xRecord.getNumberOfRecords() > 1 
		&& (recordSelect.size() >= xRecord.getNumberOfRecords()
		   || (recordSelect.size() == xRecord.getNumberOfRecords()-1)
		     && defaultRow == recordSelect.size())) {
			
			for (int i = 0; i < recordSelect.size(); i++) {
				if (defaultRow == i || recordSelect.get(i)[0].length() > 0) {
					
				} else {
					return;
				}
			}
			copybookAttrChanged.stateChanged(null);
		}
	}
	/**
	 * @return the fieldCombos
	 */
	public final NormalCombo getFieldCombo(int recordIdx) {
		if (xRecord == null || recordIdx > xRecord.getNumberOfRecords()) { return null;}
		
		for (int i = fieldCombos.size(); i <= recordIdx; i++) {
			fieldCombos.add(null);
		}
		if (fieldCombos.get(recordIdx) == null) {
			ExternalRecord r = xRecord.getRecord(recordIdx);
			String dfltField = fields.getText();
			ArrayList<String> fl = new ArrayList<String>(r.getNumberOfRecordFields()+1);
			
			if (!"".equals(dfltField)) {
				fl.add(dfltField);
			}
			for (int i = 0; i < r.getNumberOfRecordFields(); i++) {
				ExternalField fld = r.getRecordField(i);
				if (! dfltField.equalsIgnoreCase(fld.getName())) {
					fl.add(fld.getName());
				}
			}
			fieldCombos.set(recordIdx, new NormalCombo(fl.toArray(new String[fl.size()])));
		}
		return fieldCombos.get(recordIdx);
	}

	/**
	 * @return the copybookChanged
	 */
	public final boolean isCopybookChanged() {
		return copybookChanged;
	}

	/**
	 * @param copybookChanged the copybookChanged to set
	 */
	public final void setCopybookChanged(boolean copybookChanged) {
		this.copybookChanged = copybookChanged;
	}

	public final int getDialect() {
		return  dialectCombo.getSelectedValue();
	}

	public final int getSplitOption() {
		return  splitOptionsCombo.getSelectedValue();
	}

	public final int getFileStructure() {
		return ((Integer) this.fileStructureCombo.getSelectedItem()).intValue();
	}
	
	public final int getCopybookFormat() {
		return  ((ComboStdOption<Integer>) this.copybookFormatCombo.getSelectedItem()).key;
	}
	
	/**
	 * @return the binaryOptions
	 */
	public final JComboBox getDialectCombo() {
		return dialectCombo;
	}


	/**
	 * @return the copybookFormatCombo
	 */
	public final JComboBox getCopybookFormatCombo() {
		return copybookFormatCombo;
	}


	/**
	 * @return the splitOptions
	 */
	public final JComboBox getSplitOptionsCombo() {
		return splitOptionsCombo;
	}


	/**
	 * @return the fileStructure
	 */
	public final BmKeyedComboBox getFileStructureCombo() {
		return fileStructureCombo;
	}

	/**
	 * @param recordMdl the recordMdl to set
	 */
	public final void setRecordMdl(AbstractTableModel recordMdl) {
		this.recordMdl = recordMdl;
	}

}
