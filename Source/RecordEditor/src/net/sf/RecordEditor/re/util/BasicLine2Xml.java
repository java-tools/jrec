package net.sf.RecordEditor.re.util;

import java.io.FileOutputStream;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.RecordEditor.utils.common.Common;

public abstract class BasicLine2Xml {
	protected	XMLStreamWriter writer;

	public BasicLine2Xml(String fileName) {
	   	XMLOutputFactory f ;

    	try {
    		 f = XMLOutputFactory.newInstance();
    	} catch (Exception e) {
    		 Object o =  XMLOutputFactory.newInstance("javax.xml.stream.XMLOutputFactory",
					  this.getClass().getClassLoader());
    		 f = (XMLOutputFactory) o;
		}

    	try {
    		writer = f.createXMLStreamWriter(new FileOutputStream(fileName));
    	} catch (Exception e) {
			e.printStackTrace();
			Common.logMsg("Error Opening XML file:", e);
		}
	}

	protected final void doWork() {
       	try {
    		writer.writeStartDocument();

    		writeDetails();

    		writer.writeEndDocument();
    	    writer.close();
    	} catch (Exception e) {
			e.printStackTrace();
			Common.logMsg("Error Writing XML: ", e);
		}
	}

	protected abstract void writeDetails() throws XMLStreamException;

	protected final void writeAttributes(AbstractLine line) throws XMLStreamException {
		if (line != null) {
			int pref = line.getPreferredLayoutIdx();
			AbstractRecordDetail rec = line.getLayout().getRecord(pref);
			int end = rec.getFieldCount();
			Object value;

			for (int i = 0; i < end; i++) {
				value = line.getField(pref, i);
				if (value != null && ! "".equals(value)) {
					 writer.writeAttribute(fixName(rec.getField(i).getName()), value.toString());
//					 System.out.println("<<< " + fixName(rec.getField(i).getName())
//							 + ": " + value.toString());

				}

			}
		}
	}

	protected final String fixName(String name) {
		return name.replace(" ", "_").replace(":","_").replace(".", "_");
	}

}
