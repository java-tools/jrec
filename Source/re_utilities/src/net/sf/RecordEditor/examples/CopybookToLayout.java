/*
 * @Author Bruce Martin
 * Created on 6/04/2005
 *
 * Purpose:
 * This class will convert a XML / Cobol Copybook to a LayoutDetail
 * for use in the RecordEditor
 */
package net.sf.RecordEditor.examples;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDecider;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.External.Cb2Xml;
import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.JRecord.Types.Type;
import net.sf.RecordEditor.utils.common.Common;

//Modified to access the Record-Editor version of the CB2Xml class JFG

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



/**
 * This class will convert a XML / Cobol Copybook to a LayoutDetail
 * for use in the RecordEditor
 * 
 * This example requires Full source to run !!!
 *
 * @author Bruce Martin
 */
public class CopybookToLayout {

    public static final int FMT_INTEL      = 0;
    public static final int FMT_MAINFRAME  = 1;
    public static final int FMT_BIG_ENDIAN = 1;

    public static final int SPLIT_NONE     = 0;
    public static final int SPLIT_REDEFINE = 1;
    public static final int SPLIT_01_LEVEL = 2;

    private static final int OPT_WRITE_ELEMENT = 1;
    private static final int OPT_REDEFINES = 2;
    private static final int OPT_SAVE      = 3;
    private static final int OPT_REDEFINED = 4;

    private static final String ATTR_NAME      = "name";
    private static final String ATTR_PICTURE   = "picture";
    private static final String ATTR_NUMERIC   = "numeric";
    private static final String ATTR_REDEFINED = "redefined";
    private static final String ATTR_REDEFINES = "redefines";
    private static final String ATTR_POSITION  = "position";
    private static final String ATTR_LEVEL     = "level";
    private static final String ATTR_OCCURS    = "occurs";
    private static final String ATTR_USAGE     = "usage";
    private static final String ATTR_STORAGE_LENGTH = "storage-length";


    //private  String copybookName;
    private  ArrayList commonDetails;
    private  String redefinedField;
    private  boolean foundRedefine;
    private  int splitCopybook;

//    private  int fieldNum;
//    private  int recordNum;
//    private  int lastDBidx = -1;



    private  int binaryFormat = FMT_INTEL;
    private  String fontName = "";

    private  ArrayList fieldList  = new ArrayList();
    private  ArrayList recordList = new ArrayList();

    private  String holdName = "";

    /**
     * Load a File as a DOM Document
     *
     * @param fileName input file name
     *
     * @return DOM Document
     *
     * @throws IOException error to be handeled by calling program
     * @throws SAXException error to be handeled by calling program
     * @throws ParserConfigurationException error to be handeled by calling program
     */
    public  Document fileToDom(String fileName)
	throws IOException, SAXException, ParserConfigurationException {

        DocumentBuilderFactory factory
           		= DocumentBuilderFactory.newInstance();
        return factory.newDocumentBuilder().parse(new File(fileName));
    }


    /**
     * Insert a XML Dom Copybook into the Copybook DB
     *
     * @param copyBookFile Copy Book file Name
     * @param pSplitCopybook wether to split a copy book on a redefine / 01
     * @param pEolIndicator Indicates what EOL indicator is used
     * @param pRecordDecider what record Decider to use
     * @param pFileStructure The file structure
     * @param log log where any messages should be written
     *
     * @return requested layout Layout
     *
     * @throws Exception General Error
     */
    public final LayoutDetail readCobolCopyBook(String copyBookFile,
            						  		int pSplitCopybook,
											final String pEolIndicator,
	            							final RecordDecider pRecordDecider,
	            							final int pFileStructure,
            						  		AbsSSLogger log)
    throws Exception {

        try {
            Document xml = Cb2Xml.convertToXMLDOM(new File(copyBookFile), binaryFormat, log);
            String copyBook = getCopyBookId(copyBookFile);

            return readDOMCopyBook(xml, copyBook, pSplitCopybook,
                    	pEolIndicator, pRecordDecider, pFileStructure);
        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("problem loading Copybook: " + copyBookFile
                    		  + "    Message: " + e.getMessage());
        }
    }


    /**
     * Insert a XML Dom Copybook into the Copybook DB
     *
     * @param copyBookFile Copy Book file Name
     * @param splitCopybookOption wether to split a copy book on a redefine
     * @param pEolIndicator Indicates what EOL indicator is used
     * @param pRecordDecider what record Decider to use
     * @param pFileStructure The file structure
     *
     * @return requested layout Layout
     *
     * @throws RecordException General errors
     * @throws IOException error to be handeled by calling program
     * @throws SAXException error to be handeled by calling program
     * @throws ParserConfigurationException error to be handeled by calling program
    */
    public LayoutDetail readXmlCopyBook(final String copyBookFile,
            							final int splitCopybookOption,
										final String pEolIndicator,
										final RecordDecider pRecordDecider,
										final int pFileStructure)
    throws RecordException, IOException,
  	       SAXException, ParserConfigurationException {

        return readDOMCopyBook(fileToDom(copyBookFile), getCopyBookId(copyBookFile),
                		splitCopybookOption, pEolIndicator, pRecordDecider, pFileStructure);

    }


    /**
     * Insert a XML Dom Copybook into the Copybook DB
     *
     * @param pCopyBookXml copy book XML DOM representation of a copybook
     * @param pCopyBook Copy Book file Name
     * @param pSplitCopybook wether to split a copy book on a redefine
     * @param pEolIndicator Indicates what EOL indicator is used
     * @param pRecordDecider what record Decider to use
     * @param pFileStructure The file structure
     *
     * @return requested layout Layout
     *
     * @throws RecordException General errors
     */
    public LayoutDetail readDOMCopyBook(final Document pCopyBookXml,
            							final String pCopyBook,
            							final int pSplitCopybook,
            							final String pEolIndicator,
            							final RecordDecider pRecordDecider,
            							final int pFileStructure)
    throws RecordException {
        int i, rt;
        String lCopyBookPref;
        RecordDetail[] layouts;

        //copybookName = pCopyBook;

        lCopyBookPref = pCopyBook.toUpperCase() + "-";
        splitCopybook = pSplitCopybook;

        redefinedField = "";
        commonDetails  = new ArrayList();
        foundRedefine  = false;
        //fieldNum       = 0;
        //recordNum      = 1;

        Element element = /*(Element)*/ pCopyBookXml.getDocumentElement();

        holdName = pCopyBook;
        if (pSplitCopybook == SPLIT_NONE) {   /* split copybook on first redefine*/
            insertXMLcopybook(lCopyBookPref, element, 0, "");
         } else {
            insertXMLcopybook(lCopyBookPref, element, 0, "");

            if ((! foundRedefine) && (commonDetails.size() > 0)) {
                for (i = 1; i < commonDetails.size(); i++) {
                    insertRecordField((FieldDetail) commonDetails.get(i));
                }
            }
        }
        createRecord();

        commonDetails = null;

        rt = Common.rtGroupOfRecords;
        if (binaryFormat == FMT_MAINFRAME) {
            rt = Common.rtGroupOfBinaryRecords;
        }

        layouts = new RecordDetail[recordList.size()];
        for (i = 0; i < layouts.length; i++) {
            layouts[i] = (RecordDetail) recordList.get(i);
        }

        return new LayoutDetail(
                pCopyBook, layouts, "", rt, null, pEolIndicator,
                fontName, pRecordDecider, pFileStructure

        );
    }


    /**
     * This method extracts a Copybookname from a file name
     *
     * @param fileName file name
     * @return Copybook name
     */
    public  final String getCopyBookId(final String fileName) {
        String lCopyBook = fileName;
        int pos = lCopyBook.lastIndexOf(Common.FILE_SEPERATOR);

        if (pos >= 0) {
            lCopyBook = lCopyBook.substring(pos + 1);
        }

        pos = lCopyBook.lastIndexOf(".");
        if (pos >= 0) {
            lCopyBook = lCopyBook.substring(0, pos);
        }

        return lCopyBook;
   }



    /**
     * Insert XML Copybook into Record Fields
     *
     * @param copyBookPref copy book name
     * @param element XML element source
     * @param basePosition base position
     * @param nameSuffix Name suffix
     */
    private  void insertXMLcopybook(final String copyBookPref,
            						final Element element,
            						final int basePosition,
            						final String nameSuffix) {

        String newSuffix;
        NodeList lNodeList = element.getChildNodes();

        for (int i = 0; i < lNodeList.getLength(); i++) {
            org.w3c.dom.Node node = lNodeList.item(i);
            if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                Element childElement = (Element) node;
                if (!childElement.getAttribute(ATTR_LEVEL).equals("88")) {
                   if (childElement.hasAttribute(ATTR_OCCURS)) {
                        int childOccurs = getIntAttribute(childElement, ATTR_OCCURS);
                        int length = getIntAttribute(childElement, ATTR_STORAGE_LENGTH);

                        for (int j = 0; j < childOccurs; j++) {
                            if (nameSuffix.equals("")) {
                                newSuffix = Integer.toString(j);
                            } else {
                                newSuffix = nameSuffix + ", " + j;
                            }

                            processElement(childElement, copyBookPref, newSuffix, j * length);
                            insertXMLcopybook(copyBookPref,  childElement, j * length, newSuffix);
                        }
                    } else {
                        processElement(childElement, copyBookPref, nameSuffix, basePosition);
                        insertXMLcopybook(copyBookPref, childElement, basePosition, nameSuffix);
                    }
                }
            }
        }
    }


    /**
     * insert an XML element (field) into the Field DB.
     *
     * @param element element to be inserted into the DB
     * @param copyBookPref Copybook name
     * @param nameSuffix suffix to be used on field names
     * @param posBase base posisition
     */
    private  void processElement(Element element,
            					 String copyBookPref,
            					 String nameSuffix,
            					 int posBase) {

       boolean print;
       int opt;

       if (element.hasAttribute(ATTR_NAME))  {
           String lName = getStringAttribute(element, ATTR_NAME);
           String lOrigName = lName;

           boolean lIsNumeric = getStringAttribute(element, ATTR_NUMERIC).equals("true");

           if (! "".equals(nameSuffix)) {
               lName += " (" + nameSuffix + ")";
           }

           if (lName.toUpperCase().startsWith(copyBookPref)) {
               lName = lName.substring(copyBookPref.length());
           }

           print = ((! "filler".equalsIgnoreCase(lOrigName))
                   &&  element.hasAttribute(ATTR_PICTURE));
           opt = OPT_WRITE_ELEMENT;
           switch (splitCopybook) {
          	  case SPLIT_REDEFINE:
          	      if (foundRedefine) {
	           	     if (getStringAttribute(element, ATTR_REDEFINES).equals(redefinedField)) {
          	            opt = OPT_REDEFINES;
                     }
          	      } else {
          	          opt = OPT_SAVE;
          	          if (getStringAttribute(element, ATTR_REDEFINED).equals("true")) {
          	              opt = OPT_REDEFINED;
          	          }
           	      }
           	  break;
          	  case SPLIT_01_LEVEL:
          	      if (getStringAttribute(element, ATTR_LEVEL).equals("01")) {
        	          opt = OPT_REDEFINED;
          	          if (foundRedefine) {
          	            opt = OPT_REDEFINES;
          	          }
           	      }
              default:
           }

           switch (opt) {
              case OPT_WRITE_ELEMENT:
                  if (print) {
                      insertRecordField(convertElement2Field(lName, lIsNumeric, posBase, element));
                  }
           	  break;
           	  case OPT_REDEFINED:
                  redefinedField = lOrigName;
                  insertCommonFields(copyBookPref, lName, true);

                  foundRedefine = true;
                  if (print) {
                      insertRecordField(convertElement2Field(lName, lIsNumeric, posBase, element));
                  }
           	  break;
           	  case OPT_REDEFINES:
                  insertCommonFields(copyBookPref, lName, false);
           	  break;
           	  case OPT_SAVE:
                  if (print) {
                      commonDetails.add(convertElement2Field(lName, lIsNumeric, posBase, element));
                  }
          	  break;
           	  default:
           }
       }
    }


    /**
     * Insert Common Record Details
     *
     * @param copyBookPref prefix to remove from field names
     * @param recordName Field Name
     * @param first wether its is the first time called
     */
    private  void insertCommonFields(String copyBookPref, String recordName, boolean first) {
        int i;
        int start = 0;
        if (splitCopybook == SPLIT_01_LEVEL) {
            start = 1;
        }

        if (! first) {
            //int rt = Common.rtGroupOfRecords;
            //if (binaryFormat == FMT_MAINFRAME) {
            //    rt = Common.rtGroupOfBinaryRecords;
            //}

            createRecord();
        }
        holdName = copyBookPref + " " + recordName;

        for (i = start; i < commonDetails.size(); i++) {
            insertRecordField((FieldDetail) commonDetails.get(i));
        }
    }



    /**
     * Create a Record
     */
    private void createRecord() {

    	int i;
    	FieldDetail[] fields = new FieldDetail[fieldList.size()];

        int rt = Common.rtRecordLayout;
        if (binaryFormat == FMT_MAINFRAME) {
            rt = Common.rtBinaryRecord;
        }

        for (i = 0; i < fields.length; i++) {
        	fields[i] = (FieldDetail) fieldList.get(i);
        }
        fieldList.clear();

        recordList.add(
                	new RecordDetail(
                	        holdName, "", "", rt, "", "",
                	        fontName, fields, 0, recordList.size()
				)
        );

    }




    /**
     * Insert Record Field Details into the DB
     *
     * @param field source field
     *
     */
    private  void insertRecordField(FieldDetail field) {


    	fieldList.add(field);
        //fieldsDB.insert(field);

    }


    /**
     * Converts a DOM element to a Record Field
     *
     * @param name Field Name
     * @param isNumeric weather it is numeric
     * @param base base position (used to handle arrays
     * @param element DOM element
     *
     * @return the Field
     */
    private  FieldDetail convertElement2Field(
            								String name,
            								boolean isNumeric,
            								int base,
            								Element element) {
        FieldDetail ret;
        int lType = Type.ftChar;
        if (isNumeric) {
            String usage = getStringAttribute(element, ATTR_USAGE);
            String picture = getStringAttribute(element, ATTR_PICTURE).toUpperCase();
            lType = Type.ftNumRightJustified;

            if ("computational".equals(usage) || "computational-5".equals(usage)) {
                lType = Type.ftBinaryBigEndian;
                if (binaryFormat == FMT_INTEL) {
                    lType = Type.ftBinaryInt;
                }
            } else if ("computational-3".equals(usage)) {
                lType = Type.ftPackedDecimal;
            } else if (picture.indexOf('Z') >= 0
                   ||  picture.indexOf('-') >= 0
                   ||  picture.indexOf('+') >= 0
                   ||  picture.indexOf('.') >= 0) {
            } else if (binaryFormat == FMT_MAINFRAME) {
                lType = Type.ftZonedNumeric;
            }
        }


        ret = new FieldDetail(name, "", lType,
                calculateDecimalSize(getStringAttribute(element, ATTR_PICTURE)),
                fontName, 0, ""

        );
        ret.setPosLen(
                getIntAttribute(element, ATTR_POSITION) + base,
    	        getIntAttribute(element, ATTR_STORAGE_LENGTH)
        );

        return ret;
    }


    /**
     * Get an integer Attribute
     *
     * @param element source element
     * @param attributeName Attribute Name
     *
     * @return Attribute value
     */
    private  int getIntAttribute(Element element, String attributeName) {
        int lRet = 0;

        if (element.hasAttribute(attributeName))  {
            lRet = Integer.parseInt(element.getAttribute(attributeName));
        }

        return lRet;
    }


    /**
     * Get a string attribute from a element
     *
     * @param element source element
     * @param attributeName Attribute Name
     *
     * @return Attribute value
     */
    public  String getStringAttribute(Element element, String attributeName) {
        String lRet = "";

        if (element.hasAttribute(attributeName))  {
            lRet = element.getAttribute(attributeName);
        }

        return lRet;
    }


    /**
     * Calculate the size of decimal portion
     * @param format Cobol Picture
     * @return decimal size
     */
    private  int calculateDecimalSize(String format) {
        int lRet = 0;
        int lPos, lNum, lBracketOpenPos, lBracketClosePos;
        String lDecimalStr;
        String lNumStr;

        lPos = Math.max(format.indexOf("V"), format.indexOf("."));
        if (lPos >= 0) {
            lDecimalStr      = format.substring(lPos + 1);
            lBracketOpenPos  = lDecimalStr.indexOf("(");
            lBracketClosePos = lDecimalStr.indexOf(")");

            if (lBracketOpenPos >= 0 && lBracketClosePos >= lBracketOpenPos) {
                try {
                    lNumStr = lDecimalStr.substring(lBracketOpenPos + 1,
                            						lBracketClosePos);

                    lNum = Integer.parseInt(lNumStr);
                    lRet = lNum + lDecimalStr.length() - (lBracketClosePos - lBracketOpenPos) - 2;

                } catch (Exception e) {
                    /* no action*/
                }
            } else {
                lRet = lDecimalStr.length();
            }
        }

        return lRet;
    }



    /**
     * Sets the font name to be used when creating a record
     *
     * @param pFontName font name to use
     */
    public  void setFontName(String pFontName) {
        fontName = pFontName;
    }


    /**
     * Binary format to use
     *
     * @param pMachine binary format to use
     */
    public  void setBinaryFormat(int pMachine) {
        binaryFormat = pMachine;
        fontName = "";
        if (binaryFormat == FMT_MAINFRAME) {
            fontName = "CP037";
        }
    }
}