/*
 * @Author Bruce Martin
 * Created on 19/04/2007
 *
 * Purpose:
 */
/*  -------------------------------------------------------------------------
 *
 *            Sub-Project: RecordEditor's version of JRecord 
 *    
 *    Sub-Project purpose: Low-level IO and record translation  
 *                        code + Cobol Copybook Translation
 *    
 *                 Author: Bruce Martin
 *    
 *                License: GPL 2.1 or later
 *                
 *    Copyright (c) 2016, Bruce Martin, All Rights Reserved.
 *   
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU General Public License
 *    as published by the Free Software Foundation; either
 *    version 2.1 of the License, or (at your option) any later version.
 *   
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 * ------------------------------------------------------------------------ */
      
package net.sf.JRecord.IO;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.XmlConstants;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.Details.XmlLine;
import net.sf.JRecord.Types.Type;

/**
 * This class reads an XML file and returns Each Element in the file as a AbstractLine.
 *
 * @author Bruce Martin
 *
 */
public class XmlLineReader extends StandardLineReader {

	private static final int SEARCH_LIMIT = 4000;
	private static String xlmnsPrefix = "{" + javax.xml.XMLConstants.XMLNS_ATTRIBUTE_NS_URI + "}";
	//javax.xml.XMLConstants.XMLNS_ATTRIBUTE

    private static final int FOLLOWING_TEXT_INDEX = XmlConstants.FOLLOWING_TEXT_INDEX;
    private XMLStreamReader parser;
    private int type;

    private boolean buildLayout = true;

    /**
     * XML Line reader
     * @param buildTheLayout weather to build the record layout from scratch or use
     * supplied layout asis
     */
    public XmlLineReader(boolean buildTheLayout) {
        super();

        buildLayout = buildTheLayout;
    }

    /**
     * @param provider
     */
/*    public XmlLineReader(LineProvider provider) {
        super(provider);
    }
*/

    /**
     * @see net.sf.JRecord.IO.StandardLineReader#open(java.io.InputStream, net.sf.JRecord.Details.LayoutDetail)
     */
    public void open(InputStream inputStream, LayoutDetail pLayout)
            throws IOException {

    	XMLInputFactory f;
    	BufferedInputStream iStream = new BufferedInputStream(inputStream);
    	boolean startDocumentPresent;


    	try {
    		f = XMLInputFactory.newInstance();
    	} catch (Exception e) {
    		e.printStackTrace();
    		f = XMLInputFactory.newInstance("javax.xml.stream.XMLInputFactory", this.getClass().getClassLoader());
		}
    	//f.setProperty("javax.xml.stream.isReplacingEntityReferences", Boolean.FALSE);
    	//f.setProperty("javax.xml.stream.isNamespaceAware", Boolean.FALSE);

    	f.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, Boolean.FALSE);

    	//f.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, Boolean.FALSE);
    	//f.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
    	// *** f.setProperty(XMLInputFactory.IS_COALESCING, Boolean.TRUE);
    	//f.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
    	//f.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, Boolean.TRUE);
    	//javax.xml.XMLConstants.XMLNS_ATTRIBUTE_NS_URI;

    	//System.out.println("====> " + f.getClass().getName());


        if (buildLayout || pLayout == null) {
            try {
            pLayout = new LayoutDetail("XML Document", new RecordDetail[] {null, null, null, null, null},
                    			"", Constants.RT_XML, null, null, "", null, Constants.IO_XML_BUILD_LAYOUT
                      );
            } catch (Exception e) {
                e.printStackTrace();
                throw new IOException("Error Creating Layout:" + e.getMessage(), e);
            }
        }

        setLayout(pLayout);

        startDocumentPresent = open_CheckStartDocument(iStream);

        try {
            parser = f.createXMLStreamReader(iStream);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }

        type = parser.getEventType();
        if (type == XMLStreamConstants.START_DOCUMENT && ! startDocumentPresent) {
        	g_350_Next();
        	//type = parser.getEventType();
        }
//        if (type == XMLStreamConstants.START_DOCUMENT) {
//        	boolean generatedTag = true;
//
//        	parser.
//        	System.out.println("Tag Attributes:");
//        	for (int i = 0; i < parser.getAttributeCount(); i++) {
//        		if (parser.isAttributeSpecified(i)) {
//        			generatedTag = false;
//        		}
//        		System.out.println("   " + parser.getAttributeLocalName(i) + "  " + parser.isAttributeSpecified(i));
//        	}
//
//        	if (generatedTag) {
//        		type = parser.getEventType();
//        	}
//        }
    }

    private boolean open_CheckStartDocument(BufferedInputStream iStream) throws IOException {
    	StringBuffer sb = new StringBuffer();
    	byte[] bytes = new byte[100];
    	int p = 0;


    	iStream.mark(SEARCH_LIMIT);

    	do  {
    		iStream.read(bytes);
    		sb.append(new String(bytes));
    		p = sb.indexOf("<");
    	} while ((sb.length() <= (SEARCH_LIMIT - bytes.length))
    		 && (p < 0 || p > sb.length() - 5));

    	iStream.reset();
    	return sb.indexOf("<?xml") >= 0;
    }

    /**
     * @see net.sf.JRecord.IO.StandardLineReader#read()
     */
    public AbstractLine read() throws IOException {

        XmlLine ret = null;
        do {
            //System.out.println("~~>> " + type +  " " + ret);
            switch (type) {
	            case (XMLStreamConstants.START_DOCUMENT) :
	                ret = read_100_StartDocument();
	            break;
	            case (XMLStreamConstants.START_ELEMENT) :
	                ret = read_200_Element();
	            break;
	            case (XMLStreamConstants.END_ELEMENT) :
	                ret = read_300_EndElement();
	            break;
	            case (XMLStreamConstants.COMMENT) :
	                ret = read_400_Text(XmlConstants.XML_COMMENT);
	            break;
	            case (XMLStreamConstants.DTD) :
//	                try {
//                        //System.out.print(parser.getNamespaceURI());
//                        System.out.print("==" + parser.getText());
//                        //parser.get
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
	                ret = read_400_Text(XmlConstants.XML_DTD);
	            break;
	            case (XMLStreamConstants.ENTITY_REFERENCE) :
	    	        ret = read_400_Text(XmlConstants.XML_REFERENCE);
	            break;
	            case (XMLStreamConstants.CDATA) :
	    	        ret = read_400_Text(XmlConstants.XML_CDATA);
	            break;
/*	            case (XMLStreamConstants.ENTITY_REFERENCE) :
	                System.out.println("Reference: " + parser.getLocalName()
	                        + " | " + parser.getText());
	            break;
	            case (XMLStreamConstants.CDATA) :
	                System.out.println("CDATA: " + parser.getTextLength()
	                        + " " + parser.getText());
	            break;*/
	            case (XMLStreamConstants.END_DOCUMENT): return null;
	          	default:
	          	    //System.out.println("## " + type);
	                g_350_Next();
            }

        } while (ret == null);

        return ret;
    }

    private XmlLine read_100_StartDocument() throws IOException {
        int idx = g_100_GetRecordIndex(XmlConstants.XML_START_DOCUMENT, 2);
        RecordDetail rec = getLayout().getRecord(idx);
        XmlLine ret = new XmlLine(getLayout(), idx);
        String encoding = parser.getEncoding();
        String version  = parser.getVersion();
        String followingText;


        //int fieldIndex
        g_400_SetField(ret, idx, XmlConstants.END_INDEX, "");
        g_400_SetField(ret, idx, g_500_GetFieldIndex(rec, XmlConstants.XML_NAME), XmlConstants.XML_START_DOCUMENT);

        g_400_SetField(ret, idx, g_500_GetFieldIndex(rec, XmlConstants.ENCODING), encoding);
        g_400_SetField(ret, idx, g_500_GetFieldIndex(rec, XmlConstants.VERSION), version);
        g_400_SetField(ret, idx, g_500_GetFieldIndex(rec, XmlConstants.STANDALONE), Boolean.valueOf(parser.isStandalone()));

        //g_200_LoadAttributes(ret, rec, idx);
        g_300_NextToken(ret, rec, idx);

        followingText = g_800_toString(ret.getField(idx, FOLLOWING_TEXT_INDEX));
        if ((encoding == null || "".equals(encoding))
        &&  (version  == null || "".equals(version))
        &&  ("".equals(followingText))) {
            ret = null;
        }

        return ret;
    }


    private XmlLine read_200_Element() throws IOException {
        String elementName = parser.getName().toString();

        //System.out.print(" >" + parser.getNamespaceCount());

        int idx = g_100_GetRecordIndex(elementName, parser.getAttributeCount() + 4);
        RecordDetail rec = getLayout().getRecord(idx);
        Object fieldValue;

        XmlLine ret = new XmlLine(getLayout(), idx);

        //int fieldIndex

        g_400_SetField(ret, idx, XmlConstants.END_INDEX, "");
        g_400_SetField(ret, idx, g_500_GetFieldIndex(rec, XmlConstants.XML_NAME), elementName);
        g_400_SetField(ret, idx, g_500_GetFieldIndex(rec, XmlConstants.PREFIX), parser.getPrefix());
        g_400_SetField(ret, idx, g_500_GetFieldIndex(rec, XmlConstants.NAMESPACE), parser.getNamespaceURI());

        g_200_LoadAttributes(ret, rec, idx);

        fieldValue = ret.getField(idx, FOLLOWING_TEXT_INDEX);
        if (type == XMLStreamConstants.END_ELEMENT
        && (fieldValue == null || "".equals(fieldValue.toString()) )) {
           g_400_SetField(ret, idx, g_500_GetFieldIndex(rec, XmlConstants.END_ELEMENT), "True");
           g_300_NextToken(ret, rec, idx);
        }

        return ret;
    }


    private XmlLine read_300_EndElement() throws IOException {
        String elementName = "/" + parser.getName().toString();
        int idx = g_100_GetRecordIndex(elementName, 0);
        RecordDetail rec = getLayout().getRecord(idx);
        XmlLine ret = new XmlLine(getLayout(), idx);
        //int fieldIndex

        g_400_SetField(ret, idx, g_500_GetFieldIndex(rec, XmlConstants.XML_NAME), elementName);

        g_300_NextToken(ret, rec, idx);

        return ret;
    }



    private XmlLine read_400_Text(String name) throws IOException {
        int idx = g_100_GetRecordIndex(name, 1);
        RecordDetail rec = getLayout().getRecord(idx);
        XmlLine ret = new XmlLine(getLayout(), idx);
        //int fieldIndex

        g_400_SetField(ret, idx, XmlConstants.END_INDEX, "");
        g_400_SetField(ret, idx, g_500_GetFieldIndex(rec, XmlConstants.XML_NAME), name);
        if (parser.hasText()) {
            g_400_SetField(ret, idx, g_500_GetFieldIndex(rec, XmlConstants.XML_TEXT), parser.getText());
        }

        g_300_NextToken(ret, rec, idx);

        return ret;
    }

    private int g_100_GetRecordIndex(String name, int fieldCount) {
        LayoutDetail layout = getLayout();
        int ret = layout.getRecordIndex(name);

        if (ret < 0) {
            int i;
            RecordDetail.FieldDetails[] fields = new RecordDetail.FieldDetails[fieldCount + 3];
            fields[0] = g_700_BuildField(XmlConstants.XML_NAME, Type.ftXmlNameTag);
            fields[1] = g_700_BuildField(XmlConstants.END_ELEMENT, Type.ftCheckBoxTrue);
            fields[FOLLOWING_TEXT_INDEX]
                   = g_700_BuildField(XmlConstants.FOLLOWING_TEXT, Type.ftMultiLineEdit);

            for (i = 0; i < 3; i++) {
                fields[i].setPosOnly(i);
            }

            for (i = 3; i < fields.length; i++) {
                fields[i] = null;
            }

            ret = layout.getRecordCount();
            layout.addRecord(new RecordDetail(name, XmlConstants.XML_NAME, name, Constants.RT_XML,
                    "", "", "", fields, 0, ret));
         }
        //System.out.println("~~ " + name + " ~ " + ret + " " + layout.getRecordCount());

        return ret;
    }




    private void g_200_LoadAttributes(XmlLine line, RecordDetail rec, int recordIdx) throws IOException {

//    	for (int i = 0; i < parser.getNamespaceCount(); i++) {
//    		System.out.println(" ===> " + parser.getNamespacePrefix(i) + " " + parser.getNamespaceURI(i));
//    	}

    	String s;
		for (int i = 0; i < parser.getAttributeCount(); i++) {
			s = parser.getAttributeName(i).toString();
			if (s.startsWith(xlmnsPrefix)) {
				s = javax.xml.XMLConstants.XMLNS_ATTRIBUTE + ":" +s.substring(xlmnsPrefix.length());
			}
		    g_400_SetField(line, recordIdx,
		            g_500_GetFieldIndex(rec, XmlConstants.ATTRIBUTE_PREFIX + s),
		            parser.getAttributeValue(i));
		}

        g_300_NextToken(line, rec, recordIdx);
    }

    private void g_300_NextToken(XmlLine line, AbstractRecordDetail rec, int recordIdx) throws IOException {

    	parser.getEventType();
        g_350_Next();
        if (type == XMLStreamConstants.CHARACTERS) {
            try {
                line.setRawField(recordIdx, FOLLOWING_TEXT_INDEX, parser.getText());
            } catch (Exception e) {
            }
            //       	    System.out.println(indent + "CHARACTERS: " + p.getTextLength()
            //       	            + " " + p.getText());

            g_350_Next();
        }
    }

    private void g_350_Next() throws IOException {

        try {
            type = parser.next();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("XML Next Error: " + e.getMessage());
        }
    }

    private void g_400_SetField(XmlLine line, int recordIdx, int fieldIdx, Object value) throws IOException {

        try {
            line.setRawField(recordIdx, fieldIdx, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Set Field Error:" + e.getMessage());
        }

    }

    private int g_500_GetFieldIndex(RecordDetail rec, String name) {
        int ret = rec.getFieldIndex(name);

        if (ret < 0) {
        	RecordDetail.FieldDetails f = new RecordDetail.FieldDetails(name, "", Type.ftChar, 0, "", 0, "");
            ret = rec.getFieldCount();
            f.setPosOnly(ret);
            rec.addField(f);
        }

        return ret;
    }


    private RecordDetail.FieldDetails g_700_BuildField(String name, int typeId) {
        return new RecordDetail.FieldDetails(
                name, "", typeId, 0, "", 0, ""
        );
    }

    private String g_800_toString(Object o) {
        String s = "";
        if (o != null) {
            s = o.toString();
        }
        return s;
    }


    /**
     * @see net.sf.JRecord.IO.StandardLineReader#close()
     */
    public void close() throws IOException {
        for (int i = 0; i < getLayout().getRecordCount(); i++) {
            //System.out.println("^^ " + i + " " + getLayout().getRecord(i).getRecordName()
            //        + " " + getLayout().getRecord(i).getFieldCount());
        }
        try {
            parser.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("XML Close Error:" + e.getMessage());
        }
    }

}
