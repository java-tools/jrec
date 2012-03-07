package net.sf.RecordEditor.edit.display.util;

import java.util.ArrayList;

import net.sf.JRecord.Common.XmlConstants;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.edit.file.AbstractLineNode;
import net.sf.RecordEditor.utils.CsvWriter;
import net.sf.RecordEditor.utils.FieldWriter;
import net.sf.RecordEditor.utils.common.Common;

public class SaveAsWriteTree {

	private int maxLevels,
	            firstLevel=0,
	            currentLevel=0;
	private FieldWriter writer;
	private boolean printAllNodes;
	private ArrayList<String> levels = new ArrayList<String>();
	private AbstractLayoutDetails layout;
	
	public void writeTree(
			FieldWriter writer, AbstractLineNode root, 
			boolean namesOnFirstLine, boolean printAllNodes) {
		this.writer = writer;
		this.printAllNodes = printAllNodes;
		this.layout = root.getLayout();
		
		maxLevels = countLevels(root, 0);
		
		if (root.getLine() == null && "File".equals(root.toString())) {
			firstLevel = 1;
		}
		
		if (writer instanceof CsvWriter) {
			CsvWriter w = ((CsvWriter) writer);
			w.setNumberOfInitialFields(maxLevels-firstLevel);
			if (layout.isXml()) {
				w.setPrintField(layout.getRecord(0).getFieldIndex(XmlConstants.XML_NAME), false);
				w.setPrintField(layout.getRecord(0).getFieldIndex(XmlConstants.END_ELEMENT), false);
			}
		}
		
		if (namesOnFirstLine) {
			try {
				for (int i = firstLevel; i < maxLevels; i++) {
					writer.writeFieldHeading("Level_" + (i-firstLevel+1));
				}
				writer.newLine();
			} catch (Exception e) {
				e.printStackTrace();
				Common.logMsg("Error Writing File: " + e.getMessage(), e);
			}
		}
		
		printTree(root);
	}
	
	private int countLevels(AbstractLineNode node, int lvl) {
		int ret = lvl + 1;
		
		for (int i = 0; i < node.getChildCount(); i++) {
			ret = Math.max(ret, countLevels((AbstractLineNode) node.getChildAt(i), lvl+1));
		}
		
		return ret;
	}
	
	

	private void printTree(AbstractLineNode node) {
		
		boolean hasData = false;
		AbstractLine line = node.getLine();
		Object o;
		String s;
		int fieldCount = 0,
			prefIdx = 0;
		
		levels.add(node.toString());
		
		if (line != null) {
			prefIdx = line.getPreferredLayoutIdx();
			fieldCount = layout.getRecord(prefIdx).getFieldCount();
			
			for (int i = 0; i < fieldCount; i++) {
				o = line.getField(prefIdx, i);
				if (o != null && ! "".equals(o.toString().trim()) && includeField(prefIdx, i)) {
					hasData = true;
					break;
				}
			}
		}
		
		if (printAllNodes || hasData) {
			try {
				for (int i = firstLevel; i < levels.size(); i++) {
					writer.writeField(levels.get(i));
				}
				for (int i = levels.size(); i < maxLevels; i++) {
					writer.writeField("");
				}
				for (int i = 0; i < fieldCount; i++) {
					o = line.getField(prefIdx, i);
					s = "";
					if (o != null) {
						s = o.toString();
					}
					writer.writeField(s);
				}
				writer.newLine();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		for (int i = 0; i < node.getChildCount(); i++) {
			printTree((AbstractLineNode) node.getChildAt(i));
		}
		
		levels.remove(levels.size() - 1);
	}
	
	private boolean includeField(int prefIdx, int fieldIdx) {
		String field = layout.getRecord(prefIdx).getField(fieldIdx).getName();
		return ! (   layout.isXml()
				 &&  ( XmlConstants.XML_NAME.equalsIgnoreCase(field)
					|| XmlConstants.END_ELEMENT.equalsIgnoreCase(field)));
	}
}
