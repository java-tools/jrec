package net.sf.RecordEditor.re.cobol;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.stream.XMLStreamException;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.External.Cb2xmlLoader;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.Def.ExternalField;
import net.sf.RecordEditor.layoutWizard.Details;
import net.sf.RecordEditor.layoutWizard.FieldSearch;
import net.sf.RecordEditor.layoutWizard.RecordDefinition;
import net.sf.cb2xml.def.Cb2xmlConstants;
import net.sf.cb2xml.sablecc.lexer.LexerException;
import net.sf.cb2xml.sablecc.parser.ParserException;


/**
 * Check a Cobol copybook and try and work out the attributes
 * 
 * @author Bruce Martin
 *
 */
public class CobolCopybookAnalyser {

	//private final Document document;
//    private List<? extends IItem> cobolItems;
//    private ICb2XmlBuilder bldr;
	CobolCopybookReader copybookRead = new CobolCopybookReader();
	private final String copybookName;
	
	private int split = CopybookLoader.SPLIT_NONE;
//	private final int dialect;
	
	public static CobolCopybookAnalyser analyserForCopybookFile(String fileName, int dialect, boolean checkForMultipleRecords) 
	throws FileNotFoundException, ParserException, LexerException, IOException, XMLStreamException {
		return new CobolCopybookAnalyser(new FileReader(fileName), Conversion.getCopyBookId(fileName), dialect, checkForMultipleRecords);
	}
	
	
	protected CobolCopybookAnalyser(Reader copybookReader, String copybookName, int dialect, boolean checkForMultipleRecords) 
	throws ParserException, LexerException, IOException, XMLStreamException {
		
		this.copybookName = copybookName;
//		this.dialect = dialect;
//		
//		Convert conv = ConversionManager.getInstance().getConverter4code(dialect) ;
//		CopyBookAnalyzer.setNumericDetails((NumericDefinition) conv.getNumericDefinition());
//
//		document = Cb2Xml2.convert(copybookReader, copybookName, false, Cb2xmlConstants.USE_STANDARD_COLUMNS);
		
		copybookRead.readCobol(copybookReader, copybookName, dialect, Cb2xmlConstants.USE_STANDARD_COLUMNS);


		if (checkForMultipleRecords) {
			checkForMultipleRecords();
		}
	}
	
	/**
	 * Check if there are multiple Records (01's or Redefines) in the copybook
	 */
	private void checkForMultipleRecords() {
		
		int tmpSplit = copybookRead.checkForMultipleRecords();
		if (tmpSplit >= 0) {
			split = tmpSplit;
		}

//		NodeList childNodes = document.getChildNodes();
//		int count01 = 0;
//		
//		int lastNodeIdx = childNodes.getLength() - 1;
//		for (int i = lastNodeIdx; i >= 0; i--) {
//			org.w3c.dom.Node node = childNodes.item(i);
//			if (node != null && node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
//		       Element childElement = (Element) node;
//		       NodeList nl = childElement.getChildNodes();
//		       for (int j = nl.getLength() - 1; j >= 0; j--) {
//					org.w3c.dom.Node n = nl.item(j);
//					if (n != null && n.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
//			           Element ce = (Element) n;
//			           if (ce.getAttribute(Cb2xmlConstants.LEVEL).equals("01")) {
//			        	   if (count01++ > 0) {
//			        		   split = CopybookLoader.SPLIT_01_LEVEL;
//			        		   return;
//			        	   }
//			           }
//					}
//		       }
//			}
//		}
//		
//		if (childNodes.getLength() == 0) {return;};
//		boolean isRedefine = false;
//		
//		org.w3c.dom.Node node = childNodes.item(lastNodeIdx);
//		while (node != null) {
//			if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
//			    Element childElement = (Element) node;
//			    if ((childElement.hasAttribute(Cb2xmlConstants.REDEFINES))) {
//			    	String sLength = childElement.getAttribute(Cb2xmlConstants.STORAGE_LENGTH);
//			    	if (sLength != null && sLength.length() > 0) {
//				    	try {
//				    		isRedefine = Integer.parseInt(sLength) > 10;
//				    	}	catch (Exception e) { 	}
//			    	}
//			    	break;
//			    }
//			}
//		    NodeList cn = node.getChildNodes();
//		    node = cn == null || cn.getLength() == 0
//		    			? null
//		    			: cn.item(cn.getLength() - 1);
//		}
//		
//		if (isRedefine ) {
//			int i = 0;
//			NodeList childNodes2 = childNodes.item(0).getChildNodes();
//			int lastNode = childNodes2.getLength();
//			do {
//				node = childNodes2.item(i++);
//			} while (i < lastNode 
//				&&   (! nodeHasChildren(node))
//				&&   (! hasPicture(node))
//				    ); 
//			while (node != null && nodeHasChildren(node) && (! hasPicture(node))) {
//				node = node.getChildNodes().item(0);
//			}
//			
//			if (node != null && hasPicture(node)) {
//				String n = ((Element) node).getAttribute(Cb2xmlConstants.NAME);
//				if (n != null && n.toLowerCase().endsWith("-type") ) {
//					split = CopybookLoader.SPLIT_REDEFINE;
//				}
//			}
//		}
	}
	

	
	
	public static ExternalField getSelectionField(ExternalRecord xRecord, RecordSelectionDetails recSel, String s) {
		ExternalField selField = null;
			
		for (int i = 0; i < xRecord.getNumberOfRecords(); i++) {
			ExternalRecord rec = xRecord.getRecord(i);
			ExternalField field = getField(rec, s);
			if (field == null) {
				if (rec.getNumberOfRecordFields() > 0) {
					ExternalField f = rec.getRecordField(0);
				
					String fn = f.getName().toLowerCase();
					if (f.getLen() < 5 && (fn.endsWith("id") || fn.endsWith("type"))) {
						recSel.setFieldName(i, f.getName());
					}
				}
			} else {
				recSel.setFieldName(i, s);

				if (selField == null) {
					selField = field;
				}
			}
		}
		
		if (selField != null) {
			for (int i = 0; i < xRecord.getNumberOfRecords(); i++) {
				if (recSel.getFieldName(i).length() == 0) {
					ExternalRecord r = xRecord.getRecord(i);
					for (int j = 0; j < r.getNumberOfRecordFields(); j++) {
						ExternalField f = r.getRecordField(j);
						if (f.getPos() > selField.getPos()) {
							recSel.setFieldName(i, selField.getName());
							break;
						} else if (f.getPos() == selField.getPos() && f.getLen() == selField.getLen()) {
							recSel.setFieldName(i, f.getName());
							break;
						}
					}
				}
			}
		}
		
		return selField;
	}
	
	
	
	private static ExternalField getField(ExternalRecord r, String name) {
		for (int i = 0; i < r.getNumberOfRecordFields(); i++) {
			if ((name.equalsIgnoreCase(r.getRecordField(i).getName()))) {
				return r.getRecordField(i);
			}
		}
		return null;
	}
	
	
	public static void doLineAnalysis(
			IContinueCheck continueCheck, 
			RecordSelectionDetails recSel, IGetXRecord dtls,
			LayoutDetail schema, ArrayList<AbstractLine> allLines) {
		String keyFieldName;
		IFieldDetail keyFld = null;
		
		// Find the Record-Type fields
		int recordCount = schema.getRecordCount();
		for (int i = 0; i < recordCount; i++) {
			keyFieldName = recSel.getFieldName(i);
			if (keyFieldName.length() > 0 
			&& (keyFld = schema.getFieldFromName(keyFieldName)) != null) {
				break;
			}
		}
		if (keyFld != null) {
			HashMap<String, ArrayList<AbstractLine>> linesByKey = new HashMap<String, ArrayList<AbstractLine>>(recordCount * 3 + 10);
			int intialCount = Math.max(10, allLines.size() / recordCount);
			String key;
			
			// Group the lines by record type
			for (AbstractLine l : allLines) {
				if (continueCheck != null && ! continueCheck.isOkToContinue()) { return;}
				key = l.getFieldValue(keyFld).asString();
				if (key != null) {
					key = key.toLowerCase();
					ArrayList<AbstractLine> list = linesByKey.get(key);
					if (list == null) {
						list = new ArrayList<AbstractLine>(intialCount);
						linesByKey.put(key, list);
					}
					list.add(l);
				}
			}
			Set<Entry<String, ArrayList<AbstractLine>>> keyListSet = linesByKey.entrySet();
			
			// For each record-type group, try and work out the fields 
			// and compare with the fields in the copybook.
			for (Entry<String, ArrayList<AbstractLine>> e : keyListSet) {
				ArrayList<AbstractLine> matchingLines = e.getValue();
				int numRecords = matchingLines.size();
				if (numRecords > 4) {
					Details details = new Details();
					RecordDefinition recordDefinition = details.standardRecord;
					details.fileStructure = schema.getFileStructure();
					details.fontName = schema.getFontName();
					
					recordDefinition.columnDtls.clear();
					
					if (recordDefinition.records.length < numRecords) {
						recordDefinition.records = new byte[numRecords][];
					}
					recordDefinition.numRecords = numRecords;
					
					for (int i = 0; i < numRecords; i++) { 
						recordDefinition.records[i] = matchingLines.get(i).getData();
					}
					
					FieldSearch fieldSearch = new FieldSearch(details, recordDefinition);
					
					fieldSearch.findFields(true, false, true, true, false);
					
					int currRec = -1, currMatch = -1;
					for (int j = 0; j < recordCount; j++) {
						if (continueCheck != null && ! continueCheck.isOkToContinue()) {return ;}
						RecordDetail record = schema.getRecord(j);
						int i1 =0, i2 = 0, match = 0, matchType = 0;
						while (i1 < record.getFieldCount() && i2 < recordDefinition.columnDtls.size()) {
							if (record.getField(i1).getPos() < recordDefinition.columnDtls.get(i2).getStart()) {
								i1 += 1;
							} else if (record.getField(i1).getPos() > recordDefinition.columnDtls.get(i2).getStart()) {
								i2 += 1;
							} else {
								match += 1;
				
								if (record.getField(i1).getType() == recordDefinition.columnDtls.get(i2).getType()) {
									matchType +=1;
								}
								i1 += 1;
								i2 += 1;
							}
						}
						if (match * 2 + matchType > currMatch) {
							currRec = j;
							currMatch = match * 2 + matchType;
						}
					}
					recSel.addRecordSelection(currRec,  matchingLines.get(0).getFieldValue(keyFld).asString(), false);
				}
			}
			
			LayoutDetail newSchema = dtls.getXRecord().asLayoutDetail();
			for (AbstractLine l : allLines) {
				if (continueCheck != null && ! continueCheck.isOkToContinue()) {return ;}
				l.setLayout(newSchema);
			}
		}
	}

	
	
	/**
	 * @return the xRec
	 */
	public final ExternalRecord getXRecord(
			Cb2xmlLoader cb2XmlLoader, 
			ExternalRecord xRec,
			RecordSelectionDetails recordSelection,
			int dialect, 
			int fileStructure,
			String font,
			int connectionId,
			int systemId,
			int defaultRow) {
		

//		if (copybookRead.hasCobolData() && xRec == null) {
//			xRec = cb2XmlLoader.loadDOMCopyBook(
//						document, copybookName, split, 
//						connectionId, font, 
//						dialect,
//						systemId);
//		}
//		
//		if (xRec != null) {
//			int num = Math.min(xRec.getNumberOfRecords(), recordSelection.size()); 
//			
//			if (fileStructure != Constants.IO_DEFAULT) {
//				xRec.setFileStructure(fileStructure);
//			}
//			for (int i = 0; i < num; i++) {
//				ExternalRecord rec = xRec.getRecord(i);
//				String fieldName = recordSelection.getFieldName(i);
//				if (num > 1 && fieldName.length() > 0) {
//					ExternalSelection fs = null;
//					//ExternalFieldSelection fs = new ExternalFieldSelection(getRecordSelection(i, 0), getRecordSelection(i, 1));
//					ArrayList<ExternalFieldSelection> fl = new ArrayList<ExternalFieldSelection>(RecordSelectionDetails.FIELD_VALUE_COUNT);
//					for (int j = 0; j < RecordSelectionDetails.FIELD_VALUE_COUNT; j++) {
//						String fieldValue = recordSelection.getFieldValue(i, j); 
//						if (fieldValue.length() > 0) {
//							fl.add(new ExternalFieldSelection(fieldName, fieldValue));
//						}
//					}
//
//					switch (fl.size()) {
//					case 0:
//						break;
//					case 1:
//						fs = fl.get(0);
//						break;
//					default:
//						fs = ExternalGroupSelection.newOr(fl.toArray(new ExternalFieldSelection[fl.size()]));
//					}
//					rec.setRecordSelection(fs);
//				}
//				if (fileStructure != Constants.IO_DEFAULT) {
//					rec.setFileStructure(fileStructure);
//				}
//				rec.setDefaultRecord(false);
//			}
//
//			if (defaultRow >= 0 && defaultRow < xRec.getNumberOfRecords()) {
//				xRec.getRecord(defaultRow).setDefaultRecord(true);
//			}
//		}
		
		xRec = copybookRead.getXRecord(
				cb2XmlLoader, xRec, connectionId, 
				split, systemId, 
				fileStructure, dialect, defaultRow,  
				font, copybookName, recordSelection);

		return xRec;
	}

	

//
//	public Document getDocument() {
//		return document;
//	}
//
	public int getSplit() {
		return split;
	}



}
