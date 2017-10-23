package net.sf.RecordEditor.re.cobol;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.External.Cb2xmlLoader;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.ExternalRecordSelection.ExternalFieldSelection;
import net.sf.JRecord.ExternalRecordSelection.ExternalGroupSelection;
import net.sf.JRecord.ExternalRecordSelection.ExternalSelection;
import net.sf.JRecord.Numeric.ConversionManager;
import net.sf.JRecord.Numeric.Convert;
import net.sf.cb2xml.Cb2Xml2;
import net.sf.cb2xml.Cb2Xml3;
import net.sf.cb2xml.ICb2XmlBuilder;
import net.sf.cb2xml.def.Cb2xmlConstants;
import net.sf.cb2xml.def.IItem;
import net.sf.cb2xml.sablecc.lexer.LexerException;
import net.sf.cb2xml.sablecc.parser.ParserException;

public class CobolCopybookReader {
	
    private List<? extends IItem> cobolItems;
    private ICb2XmlBuilder bldr;

    
    
    public void clear() {
    	cobolItems = null;
    }
    
    public boolean hasCobolData() {
    	return cobolItems != null;
    }
	/**
	 * @param s
	 * @return
	 */
	public void readCobol(Reader reader, String copybookName, int dialect, int copybookFormat) {
		Convert conv = ConversionManager.getInstance().getConverter4code(dialect) ;
		
		bldr = Cb2Xml3	.newBuilder(reader, copybookName)
							.setDialect(conv)
							.setLoadComments(false)
							.setXmlFormat(Cb2xmlConstants.Cb2xmlXmlFormat.CLASSIC)
							.setCobolLineFormat(copybookFormat);
		cobolItems = bldr	.asCobolItemTree()
								.getChildItems();

	}
	

	/**
	 * Check if there are multiple Records (01's or Redefines) in the copybook
	 */
	public int checkForMultipleRecords() {
		
//		NodeList childNodes = document.getChildNodes();
//		
//		int lastNodeIdx = childNodes.getLength() - 1;
		if (cobolItems != null && cobolItems.size() > 0) {
			int count01 = 0;
			int lastIdx = cobolItems.size() - 1;
			for (int i = lastIdx; i >= 0; i--) {
	           if (cobolItems.get(i).getLevelNumber() == 1) {
	        	   if (count01++ > 0) {
	        		   return CopybookLoader.SPLIT_01_LEVEL;
	        	   }
	           }
			}
			boolean isRedefine = false;
			
			IItem item = cobolItems.get(lastIdx);
			while (item != null) {
				String redefinesFieldName = item.getRedefinesFieldName();
				if (redefinesFieldName != null && redefinesFieldName.length() > 0 && item.getStorageLength() > 0) {
					isRedefine = true;
					break;
				}
				List<? extends IItem> cn = item.getChildItems();
				item = cn == null || cn.size() == 0
				    			? null
				    			: cn.get(cn.size() - 1);
			}
			
			if (isRedefine ) {
				int i = 0;
				List<? extends IItem> cn = cobolItems.get(0).getChildItems();
				int lastNode = cn == null ? 0 : cn.size();
				do {
					item = cn.get(i++);
				} while (i < lastNode 
					&&   (! itemHasChildren(item))
					&&   (  hasPicture(item))
					    ); 
				while (item != null && itemHasChildren(item) && (! hasPicture(item))) {
					item = item.getChildItems().get(0);
				}
				
				if (item != null && hasPicture(item)) {
					String n = item.getFieldName();
					if (n != null && n.toLowerCase().endsWith("-type") ) {
						return CopybookLoader.SPLIT_REDEFINE;
					}
				}
			}			
		}
		return Integer.MIN_VALUE;
	}
		
		
	/**
	 * @return the xRec
	 */
	public ExternalRecord getXRecord(
			Cb2xmlLoader cb2XmlLoader, ExternalRecord xRec, 
			int connectionId, int split, int systemId, int fileStructure, int dialectId, int defaultRow,
			String font, String copybookName,
			RecordSelectionDetails recordSelection) {

		if (cobolItems != null && xRec == null) {
			try {
				xRec = cb2XmlLoader.loadDOMCopyBook(Cb2Xml2.bldrToDocument(bldr), copybookName, split, 
								connectionId, font, 
								dialectId,
								systemId);
			} catch (XMLStreamException e) {
				e.printStackTrace();
			} catch (LexerException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserException e) {
				e.printStackTrace();
			}
		}
		
		if (xRec != null) {
			int num = Math.min(xRec.getNumberOfRecords(), recordSelection.size()); 
			
			if (fileStructure != Constants.IO_DEFAULT) {
				xRec.setFileStructure(fileStructure);
			}
			for (int i = 0; i < num; i++) {
				ExternalRecord rec = xRec.getRecord(i);
				String fieldName = recordSelection.getFieldName(i);
				if (num > 1 && fieldName.length() > 0) {
					ExternalSelection fs = null;
					//ExternalFieldSelection fs = new ExternalFieldSelection(getRecordSelection(i, 0), getRecordSelection(i, 1));
					ArrayList<ExternalFieldSelection> fl = new ArrayList<ExternalFieldSelection>(RecordSelectionDetails.FIELD_VALUE_COUNT);
					for (int j = 0; j < RecordSelectionDetails.FIELD_VALUE_COUNT; j++) {
						String fieldValue = recordSelection.getFieldValue(i, j); 
						if (fieldValue.length() > 0) {
							fl.add(new ExternalFieldSelection(fieldName, fieldValue));
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
	
	private boolean itemHasChildren(IItem item) {
		return item.getChildItems() != null && item.getChildItems().size() > 0;
	}


	/**
	 * @param node
	 * @return
	 */
	private boolean hasPicture(IItem item) {
		String picture = item.getPicture();
		return picture == null || picture.length() == 0;
	}

  
}
