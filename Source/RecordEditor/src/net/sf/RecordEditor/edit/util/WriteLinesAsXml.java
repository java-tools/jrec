package net.sf.RecordEditor.edit.util;

import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import net.sf.JRecord.Details.AbstractLine;

public class WriteLinesAsXml extends BasicLine2Xml {

	//private List<AbstractLine> linesToProcess;
	private Iterator<? extends AbstractLine> lineIterator;
	
	/**
	 * Write some lines to a file as XML
	 * @param filename file to write
	 * @param lines to be written
	 */
	public WriteLinesAsXml(String filename, List<? extends AbstractLine> lines) {
		super(filename);
		
		lineIterator =  lines.listIterator();
		
		doWork();
	}

	/**
	 * Write some lines to a file as XML
	 * @param filename file to write
	 * @param lines to be written
	 */
	public WriteLinesAsXml(String filename, Iterator<? extends AbstractLine> iterator) {
		super(filename);
		
		lineIterator =  iterator;
		
		doWork();
	}


	
	/**
	 * @see net.sf.RecordEditor.edit.util.BasicLine2Xml#writeDetails()
	 */
	@Override
	protected final void writeDetails() throws XMLStreamException {
		
		writer.writeStartElement("ExportData");

		if (lineIterator.hasNext()) {
			AbstractLine<?> line = lineIterator.next();
			String name = fixName(line.getLayout().getLayoutName());
			writer.writeEmptyElement(name);
			writeAttributes(line);

			while (lineIterator.hasNext()) {
				writer.writeEmptyElement(name);
				writeAttributes(lineIterator.next());
			}
		}

		writer.writeEndElement();
	}
	
	

}
