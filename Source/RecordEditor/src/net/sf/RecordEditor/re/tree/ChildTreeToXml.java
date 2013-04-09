package net.sf.RecordEditor.re.tree;

import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.AbstractTreeDetails;
import net.sf.RecordEditor.re.util.BasicLine2Xml;

public class ChildTreeToXml<Layout extends AbstractLayoutDetails>
extends BasicLine2Xml {

//	private List<AbstractLine> list;
	private Iterator<? extends AbstractLine> lineIterator;
	private AbstractLayoutDetails layout;

	public ChildTreeToXml(final String fileName, List<? extends AbstractLine> lines) {
		super(fileName);

		lineIterator = lines.listIterator();

		super.doWork();
 	}

//	public ChildTreeToXml(final String fileName, Iterator<? extends AbstractLine> iterator) {
//		super(fileName);
//
//		lineIterator = iterator;
//
//		super.doWork();
// 	}


	/**
	 * @see net.sf.RecordEditor.re.util.BasicLine2Xml#writeDetails()
	 */
	@Override
	protected void writeDetails() throws XMLStreamException {

		if (lineIterator.hasNext()) {
			AbstractLine line = lineIterator.next();
			layout = line.getLayout();
			if (lineIterator.hasNext()) {
				writer.writeStartElement("File_" + layout.getLayoutName());
				writeNode(lineIterator.next());
				while (lineIterator.hasNext()) {
					writeNode(lineIterator.next());
				}
				writer.writeEndElement();
			} else {
				writeNode(line);
			}
		}
//		switch (list.size()) {
//		case(0): break;
//		case(1):
//			layout = list.get(0).getLayout();
//			writeNode(list.get(0));
//		break;
//		default:
//			layout = list.get(0).getLayout();
//
//			writeList("File_" + list.get(0).getLayout().getLayoutName(), list);
//			writer.writeStartElement("File_" + list.get(0).getLayout().getLayoutName());
//			for (AbstractLine l : list) {
//				writeNode(l);
//			}
//			writer.writeEndElement();
//		}
	}

	private void writeNode(AbstractLine line) throws XMLStreamException {
		int i;
		String name = fixName(
				layout.getRecord(line.getPreferredLayoutIdx()).getRecordName()
					.replace(" ", "_")
		);
		boolean leaf = true;
		AbstractTreeDetails children
				= line.getTreeDetails();
		if (children != null) {
			for (i = 0; i < children.getChildCount(); i++) {
				if (children.getLines(i).size() > 0) {
					leaf = false;
					break;
				}
			}
		}

		if (leaf) {
			writer.writeEmptyElement(name);
			writeAttributes(line);
		} else {
			List<? extends AbstractLine> childLines;
			writer.writeStartElement(name);

			writeAttributes(line);

			for (i = 0; i < children.getChildCount(); i++) {
				childLines = children.getLines(i);
				if (childLines.size() > 0) {
					if (childLines.size() > 1 || children.getChildDetails(i).isRepeated()) {
						writeList(children.getChildName(i) + "s", childLines);
					} else {
						writeNode(childLines.get(0));
					}
				}
			}
			writer.writeEndElement();
		}
	}


	private void writeList(String name, List<? extends AbstractLine> lines2write)
	throws XMLStreamException {

		writer.writeStartElement(name);
		for (AbstractLine l : lines2write) {
			writeNode(l);
		}
		writer.writeEndElement();
	}
}
