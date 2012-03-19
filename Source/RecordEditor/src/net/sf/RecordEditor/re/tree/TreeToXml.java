package net.sf.RecordEditor.re.tree;

import javax.xml.stream.XMLStreamException;

import net.sf.RecordEditor.re.file.AbstractLineNode;
import net.sf.RecordEditor.re.util.BasicLine2Xml;

public class TreeToXml extends BasicLine2Xml {
	
	private AbstractLineNode rootNode;

	public TreeToXml(final String fileName, AbstractLineNode node) {
		super(fileName);
		rootNode = node;
		
		super.doWork();
 	}
	
	/**
	 * @see net.sf.RecordEditor.re.util.BasicLine2Xml#writeDetails()
	 */
	@Override
	protected void writeDetails() throws XMLStreamException {
		
		writeNode(rootNode);
	}

	private void writeNode(AbstractLineNode node) throws XMLStreamException {
		
		String name = fixName(node.getLineType().replace(" ", "_"));
		if (node.isLeaf()) {
			writer.writeEmptyElement(name);
			writeAttributes(node.getLine());
		} else {
			String s = node.getSortValue();
			writer.writeStartElement(name);
			
			if (! "".equals(s)) {
				writer.writeAttribute("Key", s);
			}
			
			writeAttributes(node.getLine());
			
			for (int i = 0; i < node.getChildCount(); i++) {
				writeNode((LineNode) node.getChildAt(i));
			}
			writer.writeEndElement();
		}
	}
}
