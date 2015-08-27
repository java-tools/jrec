package net.sf.RecordEditor.re.tree;

import java.util.ArrayList;

import net.sf.JRecord.Common.XmlConstants;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.re.file.FileView;


/**
 * This class will parse an XML file into a Tree
 * 
 * @author Bruce Martin
 *
 */
public class TreeParserXml extends BaseLineNodeTreeParser implements AbstractLineNodeTreeParser {
	
	
	private static AbstractLineNodeTreeParser instance = new TreeParserXml();
	
	
	/**
	 * @see net.sf.RecordEditor.re.tree.AbstractLineNodeTreeParser#parseAppend(net.sf.RecordEditor.re.file.FileView, net.sf.RecordEditor.re.tree.LineNode, int, int)
	 */
	public void parseAppend(FileView view, LineNode root, int start, int end) {
		int i; 
		int level = 0;
		ArrayList<LineNode> levels = new ArrayList<LineNode>();
		ArrayList<LineNode> existing = buildExisting(root, start, end);
		AbstractLine line;
		String name;
		LineNode current = root;
		LineNode node;
		
//		System.out.println("Rebuilding XML : " + root.getLevel() + " > " + start + " " + end);
		
		current.removeAllChildren();
		
		root.setLastLeafLine(end);
		levels.add(root);
		
		end = Math.min(end, view.getRowCount() - 1);

		for (i = Math.max(0, start); i <= end; i++) {
			line = view.getLine(i);
			name = toString(line.getField(line.getPreferredLayoutIdx(), 0));
			
			if (name.startsWith("/")) {
				levels.get(level).setLastLeafLine(i);
				level = Math.max(0, level - 1);
				current = levels.get(level);
			} else {
				node = existing.get(i - start);
				
//				System.out.println("Adding Node ??? " + i + " " 
//						+ line.getPreferredLayoutIdx() + " "
//						+ (node != null) + "  " + (line.getPreferredLayoutIdx() >= 0));
				if (node != null || line.getPreferredLayoutIdx() >= 0) {
					if (node == null || ! name.equals(node.nodeName)) {
						node = new LineNode(name, view, i);
						//System.out.print(" --> " + i);
					} else {
						node.setLineNumberEtc(i);
					}
//					System.out.print(" > " + i + " " + level + " " + name + " " 
//							+ line.getFieldValue(XmlConstants.END_ELEMENT).asString());
					current.add(node);
					if (! (name.startsWith("XML ") 
					|| "True".equalsIgnoreCase(line.getFieldValue(XmlConstants.END_ELEMENT).asString()))) {
						level += 1;
						node.removeAllChildren();
						if (level < levels.size()) { 
							levels.set(level, node);
						} else {
							levels.add(node);
						}
						current = node;
//						System.out.print("~~ Set Parent  " + level
//								+ " " + name + " " + line.getFieldValue(XmlConstants.END_ELEMENT).asString());
					}
					//System.out.println();
				}
			}
		}
		
		for (i = 0; i <= level; i++) {
			levels.get(level).setLastLeafLine(end);
		}
		//System.out.println();
	}
	
	/**
	 * get a line node parser
	 * @return a line node parser
	 */
	public static AbstractLineNodeTreeParser getInstance() {
		return instance;
	}

}