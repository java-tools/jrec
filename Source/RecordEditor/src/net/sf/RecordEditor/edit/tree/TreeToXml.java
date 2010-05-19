package net.sf.RecordEditor.edit.tree;

import javax.xml.stream.XMLStreamException;

import net.sf.RecordEditor.edit.util.BasicLine2Xml;

public class TreeToXml extends BasicLine2Xml {
	
	private LineNode rootNode;

	public TreeToXml(final String fileName, LineNode node) {
		super(fileName);
		rootNode = node;
		
		super.doWork();
 	}
	
	/**
	 * @see net.sf.RecordEditor.edit.util.BasicLine2Xml#writeDetails()
	 */
	@Override
	protected void writeDetails() throws XMLStreamException {
		
		writeNode(rootNode);
	}

	private void writeNode(LineNode node) throws XMLStreamException {
		
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
