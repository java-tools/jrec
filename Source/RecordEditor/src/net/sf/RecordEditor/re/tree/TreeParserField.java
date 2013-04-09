package net.sf.RecordEditor.re.tree;

import java.math.BigDecimal;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.JRecord.Details.ArrayListLine;
import net.sf.JRecord.Types.Type;
import net.sf.RecordEditor.re.file.AbstractLineNode;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.jrecord.types.ReTypeManger;


/**
 * This class will parse a file into a Tree using specified fields for grouping
 *
 * @author Bruce Martin
 *
 */
public class TreeParserField extends BaseLineNodeTreeParser implements AbstractLineNodeTreeParser {

	private int recordIdx;
	private int[] fields;
	private FieldSummaryDetails fldDetails;

	private LineNode[] levels;
	//private ArrayList<LineNode> existing;


	public TreeParserField(int recordIndex, int[] groupingFields, FieldSummaryDetails fieldDetails) {
		recordIdx = recordIndex;
		fields    = groupingFields;
		fldDetails = fieldDetails;
	}

	/**
	 * @see net.sf.RecordEditor.re.tree.AbstractLineNodeTreeParser#parseAppend(net.sf.RecordEditor.re.file.FileView, net.sf.RecordEditor.re.tree.LineNode, int, int)
	 */
	public void parseAppend(FileView view, LineNode root, int start, int end) {
		int i, j, diffAtLevel;
		int startLevel   = root.getLevel();
		int numberLevels = fields.length - startLevel + 1;
		int arraySize = fields.length + 1;
		AbstractRecordDetail rec = view.getLayout().getRecord(recordIdx);
		//System.out.println("parse: " + fields.length + " > " + root.getLevel() + " " + root.getFirstLeafLine()
		//		+ " " + root.getLastLeafLine());

		end = Math.min(end, view.getRowCount() - 1);

		levels = new LineNode[arraySize];
		//existing = buildExisting(root, start, end);

		String[] currentFields = new String[arraySize];
		String[] parentFields  = new String[arraySize];


		root.removeAllChildren();

		root.setLastLeafLine(end);
		levels[0] = root;

		parseAppend_200_SetNodeNames(parentFields, view, start, startLevel);

		parseAppend_300_addNode(view,  parentFields, start, 1, start, numberLevels);

		for (i = start + 1; i <= end; i++) {
			parseAppend_200_SetNodeNames(currentFields, view, i, startLevel);

			diffAtLevel = 0;
			while (diffAtLevel < numberLevels
			   &&  currentFields[diffAtLevel] != null
			   &&  currentFields[diffAtLevel].equals(parentFields[diffAtLevel]))  {
				diffAtLevel += 1;
			}
//			System.out.println("@@ " + i + " " + currentFields[0] + " ~~ " + parentFields[0] + " " + diffAtLevel);

			parseAppend_100_setLastLine(rec, startLevel, diffAtLevel, i - 1, numberLevels);

			for (j = 0; j < numberLevels; j++) {
				parentFields[j] = currentFields[j];
			}
			parseAppend_300_addNode(view,  parentFields, start, diffAtLevel + 1, i, numberLevels);
		}

		parseAppend_100_setLastLine(rec, startLevel, 0, end, numberLevels);

		//System.out.println();

		currentFields = null;
		parentFields  = null;
	}


	/**
	 * Set the last line number for nodes
	 * @param diffAtLevel level to start seting the last leaf at
	 * @param lineNum last leaf line
	 */
	private void parseAppend_100_setLastLine(AbstractRecordDetail layout, int startLevel, int diffAtLevel,
			int lineNum, int numberLevels) {
		int i, j;
		String s;
//		System.out.println("Diff Level --> " + diffAtLevel
//				+ " " + (numberLevels-1)
//				+ " " + lineNum + " " +  numberLevels);

		for (j = numberLevels-1; j >= diffAtLevel; j--) {
			levels[j].setLastLeafLine(lineNum);

			ArrayListLine summaryLine = new ArrayListLine(levels[diffAtLevel].getLayout(), recordIdx, 0);
			//summaryLine.setUseField4Index(false);

			for (i = 0; i < fldDetails.getFieldCount();i++) {
				Object o = "";
				int op = fldDetails.getOperator(i);
				switch (op) {
				case FieldSummaryDetails.OP_SUM:
					o = sumFields(levels[j], i);
					break;
				case FieldSummaryDetails.OP_MAX:
					o = maxFields(levels[j], i);
					break;
				case FieldSummaryDetails.OP_MIN:
					o = minFields(levels[j], i);
					break;
				case FieldSummaryDetails.OP_AVE:
					o = average(levels[j], i);
					break;

				default:
					break;
				}
				try {
					summaryLine.setField(recordIdx, i, o);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			s = "";
			if (j + startLevel == 0) {
				s = "Root";
			} else if (j + startLevel <= fields.length) {
				s = layout.getField(fields[j + startLevel -1]).getName();
			}
//			System.out.println(" << " + j + " " + startLevel + " " + fields.length + " " + s
//					+ " " + summaryLine.getField(0, 0));
			levels[j].setSummaryLine(summaryLine, s);
		}
	}

	private BigDecimal sumFields(LineNode node, int idx){

		BigDecimal sum = new BigDecimal(0);
		AbstractLine line;

		for (int j=0; j < node.getChildCount(); j++) {
			line = ((AbstractLineNode) node.getChildAt(j)).getLine();

			try {
				sum = sum.add(line.getFieldValue(recordIdx, idx).asBigDecimal());
			} catch (Exception e) {
			}
		}
		return sum;
	}


	private Double average(LineNode node, int idx){

		IFieldDetail fld = node.getLayout().getField(recordIdx, idx);
		int type = fld.getType();

		double sum = 0;
		AbstractLine line;
		FileView view = node.getView();
		int start = node.getFirstLeafLine();
		int end   = node.getLastLeafLine();


		for (int j=start; j <= end; j++) {
			line = view.getLine(j);

			try {
				sum = sum + line.getFieldValue(recordIdx, idx).asDouble();
			} catch (Exception e) {
			}
		}
		sum = sum / (end - start + 1);

		if (! (type == Type.ftFloat || type == Type.ftDouble)) {
			double m = Math.pow(10, fld.getDecimal() + 2);
			sum = Math.round(sum * m) / m;
		}
		return Double.valueOf(sum);
	}



	private Object maxFields(LineNode node, int idx){
		boolean numeric = ReTypeManger.getInstance().getType(
				node.getLayout().getField(recordIdx, idx).getType()
				).isNumeric();
		if (numeric) {
			BigDecimal initMax = new BigDecimal(Long.MIN_VALUE);
			BigDecimal max = initMax;
			AbstractLine line;

			for (int j=0; j < node.getChildCount(); j++) {
				line = ((AbstractLineNode) node.getChildAt(j)).getLine();

				try {
					max = max.max(line.getFieldValue(recordIdx, idx).asBigDecimal());
				} catch (Exception e) { }
			}
			if (max == initMax) {
				return "";
			}
			return max;
		} else {
			String max = "";
			String s;
			AbstractLine line;

			for (int j=0; j < node.getChildCount(); j++) {
				line = ((AbstractLineNode) node.getChildAt(j)).getLine();

				try {
					s = line.getFieldValue(recordIdx, idx).asString();
					if (max.compareToIgnoreCase(s) < 0) {
						max = s;
					}
				} catch (Exception e) { }
			}
			return max;
		}
	}


	private Object minFields(LineNode node, int idx){
		boolean numeric = ReTypeManger.getInstance().getType(
				node.getLayout().getField(recordIdx, idx).getType()
				).isNumeric();
		if (numeric) {
			BigDecimal initMin = BigDecimal.valueOf(Long.MAX_VALUE);
			BigDecimal min = initMin;
			AbstractLine line;

			for (int j=0; j < node.getChildCount(); j++) {
				line = ((AbstractLineNode) node.getChildAt(j)).getLine();

				try {
					min = min.min(line.getFieldValue(recordIdx, idx).asBigDecimal());
				} catch (Exception e) { }
			}
			if (min == initMin) {
				return "";
			}
			return min;
		} else {
			String min = "ZZZZ";
			String s;
			AbstractLine line;

			for (int j=0; j < node.getChildCount(); j++) {
				line = ((AbstractLineNode) node.getChildAt(j)).getLine();

				try {
					s = line.getFieldValue(recordIdx, idx).asString();
					if (min.compareToIgnoreCase(s) > 0) {
						min = s;
					}
				} catch (Exception e) { }
			}
			if (min.equals("ZZZZ")) {
				min = "";
			}
			return min;
		}
	}

	/**
	 * update the node values
	 * @param values node names
	 * @param view current file view
	 * @param lineNumber current line number
	 * @param startLevel starting level
	 */
	private void parseAppend_200_SetNodeNames(String[] values, FileView view,
			int lineNumber, int startLevel) {
		int j;

		if (lineNumber >= 0) {
			AbstractLine line = view.getLine(lineNumber);

			for (j = 0; j < fields.length - startLevel; j++) {
				values[j] = toString(line.getField(recordIdx, fields[j + startLevel]));
			}
		} else {
			for (j = 0; j < fields.length - startLevel; j++) {
				values[j] = toString("");
			}
		}
		//System.out.print("<  ");
	}

	/**
	 *
	 * @param view
	 * @param parentFields
	 * @param start
	 * @param changeLevel
	 * @param lineNum
	 */
	private void parseAppend_300_addNode(FileView view, String[] parentFields,
			int start, int changeLevel, int lineNum, int numberLevels) {
		for (int j = changeLevel; j < numberLevels; j++) {
			if (levels[j] != null) {
				levels[j].setLastLeafLine(lineNum - 1);
			}
			levels[j] = new LineNode(parentFields[j - 1], view, Constants.NULL_INTEGER);
			levels[j].setFirstLeafLine(lineNum);
			levels[j].setLastLeafLine(lineNum);
//			if (j == 1) {
//				System.out.println("Adding root ... " + levels[j].getFirstLeafLine() + " " + levels[j].getLastLeafLine()) ;
//			}
			levels[j - 1].add(levels[j]);
		}

/*		if (changeLevel < numberLevels) {
			System.out.println("Change Level " + (numberLevels - changeLevel)
					+ " " + lineNum);
		}*/
		LineNode node = null;
//		int idx = lineNum - start;
//		if (idx >= 0 && idx < existing.size()) {
//			existing.get(lineNum - start);
//		}

//		if (node == null) {
			node = new LineNode(parentFields[numberLevels - 1], view, lineNum);
			node.setFirstLeafLine(lineNum);
//		}

		levels[numberLevels - 1].add(node);
	}
}