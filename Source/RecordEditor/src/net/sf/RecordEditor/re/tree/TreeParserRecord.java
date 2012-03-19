package net.sf.RecordEditor.re.tree;

import java.util.ArrayList;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.re.file.FileView;


/**
 * This class will parse a file into a Tree using specified fields for grouping
 * 
 * @author Bruce Martin
 *
 */
public class TreeParserRecord extends BaseLineNodeTreeParser implements AbstractLineNodeTreeParser {
	
	private int[] parent;
	
	private LineNode[] levels;
	private int[] levelRecordIdx;
	private ArrayList<LineNode> existing;

	
	public TreeParserRecord(int[] parentRecord) {
		parent    = parentRecord;
	}
	
	/** 
	 * @see net.sf.RecordEditor.re.tree.AbstractLineNodeTreeParser#parseAppend(net.sf.RecordEditor.re.file.FileView, net.sf.RecordEditor.re.tree.LineNode, int, int)
	 */
	public void parseAppend(FileView view, LineNode root, int start, int end) {
		int i, j, searchIndex, oldLevel;
		int startLevel   = root.getLevel();
		int levelIdx = startLevel;
		int numberLevels = parent.length + 2;
		LineNode parentNode;
		AbstractLine line;
		int recordIndex;
		String name;
		
		end = Math.min(end, view.getRowCount() - 1);
		
		levels = new LineNode[numberLevels];
		levelRecordIdx = new int[numberLevels];
		existing = buildExisting(root, start, end);
		
		i = startLevel;
		parentNode = root;
		
		do {
			levels[i] = parentNode;
			line = parentNode.getLine();
			
			levelRecordIdx[i] = Constants.NULL_INTEGER;
			if (line != null) {
				levelRecordIdx[i--] = line.getPreferredLayoutIdx();
			}
			parentNode = (LineNode) parentNode.getParent();
		} while (i >= 0 && parentNode != null);

		root.removeAllChildren();
		root.setLastLeafLine(end);
		
		for (i = start; i <= end; i++) {
			line = view.getLine(i);
			recordIndex = line.getPreferredLayoutIdx();

			if (recordIndex >= 0) {
				searchIndex = parent[recordIndex];
				
				oldLevel = levelIdx;
				levelIdx = 1;
				if (searchIndex >= 0) {
					//System.out.print("Searching ..  ");
					for (j = oldLevel; j > 0; j--) {
						//System.out.print(" >> " + j + " " + levelRecordIdx[j] + " " + recordIndex);
						if (levelRecordIdx[j] == searchIndex) {
							levelIdx = j + 1;
							break;
						}
					}
				}
				
				for (j = levelIdx; j < oldLevel; j++) {
					levels[j].setLastLeafLine(i - 1);
				}

				//System.out.println(" --> " + levelIdx);
				name = view.getLayout().getRecord(recordIndex).getRecordName();
				levels[levelIdx] = getNode(view, name, start, i);
				levelRecordIdx[levelIdx] = recordIndex;
				levels[levelIdx - 1].add(levels[levelIdx]);
			} else {
				levels[levelIdx].add(getNode(view, "", start, i));
			}
		}
		
		for (j = startLevel; j < levelIdx; j++) {
			levels[j].setLastLeafLine(end);
		}


		//System.out.println();
		
		levels   = null;
		existing = null;
		levelRecordIdx = null;
	}
	

	/**
	 * get a node
	 * 
	 * @param view file view
	 * @param start first line being scanned
	 * @param lineNum line Number
	 * 
	 * @return requested node
	 */
	protected final LineNode getNode(FileView view, String name, int start, int lineNum) {

		LineNode node = existing.get(lineNum - start);
		if (node == null || ! name.equals(node.nodeName)) {
			node = new LineNode(name, view, lineNum);
		} else {
			node.setLineNumberEtc(lineNum);
		}
		
		return node;
	}
}