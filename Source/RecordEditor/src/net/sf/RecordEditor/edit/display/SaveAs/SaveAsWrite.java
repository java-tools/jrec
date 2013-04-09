package net.sf.RecordEditor.edit.display.SaveAs;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Common.XmlConstants;
import net.sf.JRecord.Details.AbstractChildDetails;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.JRecord.Details.AbstractTreeDetails;
import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.JRecord.Types.Type;
import net.sf.RecordEditor.re.file.AbstractLineNode;
import net.sf.RecordEditor.re.file.DisplayType;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.fileWriter.FieldWriter;
import net.sf.RecordEditor.re.script.AbstractFileDisplay;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;



public abstract class SaveAsWrite {

	public static final int HTML_TREE_PRINT = -1331;
	public static final int SAVE_FILE = 1;
	public static final int SAVE_VIEW = 2;
	public static final int SAVE_SELECTED = 3;


	public static SaveAsWrite getWriter(FileView file, AbstractFileDisplay recFrame) {
		return getWriter(DisplayType.displayTypePrint(file.getLayout(), recFrame.getLayoutIndex()), file, recFrame);
	}

	public static SaveAsWrite getWriter(int type, FileView file, AbstractFileDisplay recFrame) {
		SaveAsWrite ret;
		AbstractLayoutDetails layout = file.getLayout();
		switch(type) {
		case DisplayType.PREFFERED:
			ret = new WritePreferedLayout();
			break;
		case DisplayType.FULL_LINE:
			ret = new WriteFullLine();
			break;
		case DisplayType.HEX_LINE:
			ret = new WriteHexLine();
			break;
		case HTML_TREE_PRINT:
			ret = new WriteTree();
			break;
		default:
			ret = new WriteFromLayout();
		}

		ret.layout = layout;
		ret.layoutIdx = recFrame.getLayoutIndex();
		ret.file = file;
		ret.recFrame = recFrame;

		return ret;
	}

//	public static abstract class BaseWrite {
		protected AbstractLayoutDetails layout;
		protected FileView file;
		protected AbstractFileDisplay recFrame;
		protected int layoutIdx;

		private int levelCount,
        			firstLevel=0,
        			maximumFieldCount=0;
		private FieldWriter writer;
		private boolean printAllNodes;
		private ArrayList<String> levels = new ArrayList<String>();
		protected final ArrayList<String> columnNames = new ArrayList<String>();

		private int maxFields;



	    public final void writeFile(FieldWriter writer, boolean namesOnFirstLine, int saveWhat) throws IOException{
	    	this.writer = writer;
	    	writeAFile(writer, getViewToSave(saveWhat), layoutIdx, namesOnFirstLine);

	        writer.close();
	   	}


		public final FileView getViewToSave(int whatToSave) {
	    	FileView ret = null;
	    	switch (whatToSave) {
	    	case SaveAsWrite.SAVE_SELECTED: ret = file.getView(recFrame.getSelectedRows()); break;
	    	case SaveAsWrite.SAVE_FILE: ret = file.getBaseFile();							break;
	    	case SaveAsWrite.SAVE_VIEW: ret = file;
	    	}

	    	return ret;
	   	}

		/**
		 * @return the columnNames
		 */
		public List<String> getColumnNames() {
			return columnNames;
		}


		protected void writeAFile(
	    		FieldWriter writer,
	    		FileView view,
	    		int layoutIdx,
	    		boolean namesFirstLine) throws IOException {
			this.writer = writer;

			columnNames.clear();
			allocateColumnNames(file, layoutIdx);

			if (namesFirstLine) {
				writeColumnHeadings();
			}

    		for (int i = 0; i < view.getRowCount(); i++) {
    			writeLine(writer, view.getLine(i));
            }
		}


		protected abstract void writeLine(FieldWriter writer, AbstractLine line)
		throws IOException;

		protected void writeLine(FieldWriter writer, AbstractLine line, int count)
		throws IOException {
			writeLine(writer, line);
		}

		protected final void writeLine(FieldWriter writer, AbstractLine line, int idx, int fieldCount)
		throws IOException {
			if (line != null) {
	        	for (int j = 0; j < fieldCount; j++) {
	        		try {
						writer.writeFieldDetails(
								layout.getRecord(idx).getField(j),
								getValue(line.getField(idx, j)),
								getValue(line.getFieldText(idx, j)),
								getValue(line.getFieldHex(idx, j))
								);
					} catch (Exception e) {
					}
	        	}

	        	if (writer.printAllFields()) {
	        		for (int i = fieldCount; i < maxFields; i++) {
	        			writer.writeField("");
	        		}
	        	}
			} else if (writer.printAllFields())  {
        		for (int i = 0; i < maxFields; i++) {
        			writer.writeField("");
        		}
			}
        	writer.newLine();
		}

		private String getValue(Object o) {
			String s = null;
    		if (o != null) {
    			s = o.toString();
    		}

        	return s;
		}

//		public int getMaxFieldCount() {
//			int ret = 0;
//			for (int i = 0; i < layout.getRecordCount(); i++) {
//				ret = Math.max(ret, layout.getRecord(i).getFieldCount());
//			}
//			return ret;
//		}


		public boolean[] getFieldsToInclude() {
			return null;
		}


		public void writeTree(
				FieldWriter writer, AbstractLineNode root,
				boolean namesOnFirstLine, boolean printAllNodes,
				int layoutIdx) {
			this.writer = writer;
			this.printAllNodes = printAllNodes;
			this.layout = root.getLayout();

			String s;
			ArrayList<Integer> levelSizes = new ArrayList<Integer>(layout.getRecordCount() + 1);


			levelCount = countLevels(levelSizes, root, 0);

			if (root.getLine() == null && "File".equals(root.toString())) {
				firstLevel = 1;
			}


			int[] prefixLen = new int[levelCount-firstLevel];
			columnNames.clear();

			for (int i = firstLevel; i < levelCount; i++) {
				s = "Level_" + (i-firstLevel+1);
				columnNames.add(s);
				prefixLen[i-firstLevel] = levelSizes.get(i);
				if (namesOnFirstLine && s.length() > prefixLen[i-firstLevel]) {
					prefixLen[i-firstLevel] = s.length();
				}
			}

			if (layout.isXml()) {
				writer.setPrintField(layout.getRecord(0).getFieldIndex(XmlConstants.XML_NAME), false);
				writer.setPrintField(layout.getRecord(0).getFieldIndex(XmlConstants.END_ELEMENT), false);
			}


			writer.setupInitialFields(levelCount-firstLevel, prefixLen);
			allocateColumnNames(file, layoutIdx);
			//st = columnNames.size();
//			for (int i = 0; i < maximumFieldCount; i++) {
//				columnNames.add("Level_" + (i-firstLevel+1));
//			}

			if (namesOnFirstLine) {
				try {
					writeColumnHeadings();
				} catch (Exception e) {
					e.printStackTrace();
					Common.logMsg(AbsSSLogger.ERROR, "Error Writing Column Headings:", e.getMessage(), e);
				}
			}

			printTree(root);

			try {
				writer.close();
			} catch (Exception e) {
				Common.logMsg(AbsSSLogger.ERROR, "Error Closing File:", e.getClass().getName() + " " + e.getMessage(), null);
				e.printStackTrace();
			}
		}

		private int countLevels(List<Integer> levelSizes, AbstractLineNode node, int lvl) {
			int ret = lvl + 1;
			int len;
			AbstractLine line = node.getLine();

			if (node == null || node.toString() == null) {
				len = 4;
			} else {
				len = node.toString().length();
			}
			if (levelSizes.size() <= lvl) {
				levelSizes.add(len);
			} else if (len > levelSizes.get(lvl)) {
				levelSizes.set(lvl, Integer.valueOf(len));
			}

			if (line != null) {
				maximumFieldCount = Math.max(maximumFieldCount, layout.getRecord(line.getPreferredLayoutIdx()).getFieldCount());
			}
			for (int i = 0; i < node.getChildCount(); i++) {
				ret = Math.max(ret, countLevels(levelSizes, (AbstractLineNode) node.getChildAt(i), lvl+1));
			}

			return ret;
		}



		/**
		 * @return the maximumFieldCount
		 */
		public int getMaximumFieldCount() {
			return maximumFieldCount;
		}


		private void printTree(AbstractLineNode node) {

			boolean hasData = false;
			AbstractLine line = node.getLine();
			Object o;
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
					for (int i = levels.size(); i < levelCount; i++) {
						writer.writeField("");
					}

					writeLine(writer, line, fieldCount);
//					for (int i = 0; i < fieldCount; i++) {
//						o = line.getField(prefIdx, i);
//						s = "";
//						if (o != null) {
//							s = o.toString();
//						}
//						writer.writeField(s);
//					}
				} catch (Exception e) {
					Common.logMsg(AbsSSLogger.ERROR, "Write Failed", e.getClass().getName() + " " + e.getMessage(), null);
					e.printStackTrace();
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

		/**
		 * @return the maxLevels
		 */
		public int getLevelCount() {
			return levelCount - firstLevel;
		}

		protected void allocateColumnNames(FileView fieldMapping, int idx) {
			AbstractRecordDetail rec = layout.getRecord(idx);
			int colCount = rec.getFieldCount();
			int layoutFieldNo;
			maxFields = 0;

			for (int i = 0; i < layout.getRecordCount(); i++) {
				maxFields = Math.max(maxFields, layout.getRecord(i).getFieldCount());
			}
			for (int i = maxFields; i>= 0; i--) {
				if (! writer.isFieldToBePrinted(i)) {
					maxFields -= 1;
				}
			}

			for (int i = 0; i < colCount; i++) {
				layoutFieldNo = i;// fieldMapping.getRealColumn(layoutIdx, i);
				if (writer.isFieldToBePrinted(layoutFieldNo + levelCount - firstLevel)) {
					columnNames.add(rec.getField(layoutFieldNo).getName());
				}
			}
			for (int i = colCount; i < maxFields; i++) {
				columnNames.add("Field_" + i);
			}
		}

		protected final void writeColumnHeadings() throws IOException {
			int st = 0;
			for (String s : columnNames) {
				while (! writer.isFieldToBePrinted(st++)) {
					writer.writeFieldHeading("");
				}
				writer.writeFieldHeading(s);
			}
			writer.newLine();
		}

//		public final void writeColumnHeadings(FieldWriter writer, int layoutIdx, ColumnMappingInterface fieldMapping)
//		throws IOException {
//			AbstractRecordDetail rec = layout.getRecord(layoutIdx);
//			int colCount = rec.getFieldCount();
//			int layoutFieldNo;
//			int maxFields = 0;
//
//			for (int i = 0; i < layout.getRecordCount(); i++) {
//				maxFields = Math.max(maxFields, layout.getRecord(i).getFieldCount());
//			}
//			for (int i = 0; i < colCount; i++) {
//				layoutFieldNo = fieldMapping.getRealColumn(layoutIdx, i);
//
//				writer.writeFieldHeading(
//						rec.getField(layoutFieldNo).getName());
//			}
//			for (int i = colCount; i < maxFields; i++) {
//				writer.writeFieldHeading("Field_" + i);
//			}
//		}
//	}

	private static class WriteFromLayout extends SaveAsWrite {

		int colCount;

		@Override
		protected void writeAFile(
	    		FieldWriter writer, FileView view,
	    		int layoutIdx,
	    		boolean namesFirstLine) throws IOException {

	    	colCount = layout.getRecord(layoutIdx).getFieldCount();
	    	boolean[] numeric = new boolean[colCount];

	    	for (int i = 0; i < colCount; i++) {
	    		numeric[i] = false;
	    	}

	    	if (layoutIdx < layout.getRecordCount()) {
	    		Object o;

	    		boolean onlyNumeric;
	    		boolean aNumeric;
	    		int toCheck = Math.min(view.getRowCount(), 90);
	    		int rt = Constants.NULL_INTEGER;
	    		if (layout.getRecord(layoutIdx) != null) {
	    			rt = layout.getRecord(layoutIdx).getRecordType();
	    		}
	    		boolean isCsv = layout.isBinCSV()
	    					 || layout.getFileStructure() == Constants.IO_NAME_1ST_LINE
	    					 || rt == Constants.rtDelimited
	    					 || rt == Constants.rtDelimitedAndQuote;
		    	for (int i = 0; i < colCount; i++) {
		    		numeric[i] = layout.getRecord(layoutIdx).getFieldsNumericType(i) == Type.NT_NUMBER;
			    	if ((! numeric[i]) && isCsv && toCheck > 3) {
			    		onlyNumeric = true;
			    		aNumeric = false;
			    		for (int j = 1; j < toCheck; j++) {
			    			o = view.getValueAt(j, i);
			    			if (o == null || "".equals(o.toString().trim())) {

			    			} else {
			    				try {
			    					new BigDecimal(o.toString().trim());
			    					aNumeric = true;
			    				} catch (Exception e) {
			    					onlyNumeric = false;
			    					break;
								}
			    			}
			    		}

			    		numeric[i] = aNumeric && onlyNumeric;
			    	}
		    	}
		    	writer.setNumericFields(numeric);
	    	}

	    	super.writeAFile(writer, view, layoutIdx, namesFirstLine);
	    }




		protected void writeLine(FieldWriter writer, AbstractLine line)
		throws IOException {
			writeLine(writer, line, layoutIdx, colCount);
		}

		protected void writeLine(FieldWriter writer, AbstractLine line, int fieldCount)
		throws IOException {
			writeLine(writer, line, layoutIdx, fieldCount);
		}

		public boolean[] getFieldsToInclude() {
			boolean[] t = file.getFieldVisibility(layoutIdx);
			boolean[] ret = t;
			int maxFlds = DisplayType.getMaxFields(layout);
			if ((layout.isXml() || layout.hasChildren())
			&& (t != null && maxFlds > t.length)) {
				ret = new boolean[maxFlds];

				System.arraycopy(t, 0, ret, 0, t.length);
				for (int i = t.length; i < ret.length; i++) {
					ret[i] = false;
				}
			}
			return ret;
		}

	}

	private static class WritePreferedLayout extends SaveAsWrite {


		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.edit.display.util.SaveAsWrite.BaseWrite#getColumnNames()
		 */
		@Override
		protected void allocateColumnNames(FileView fieldMapping, int index) {
			int idx=0;

			if (layout.getRecordCount() > 1) {
				int[] counts = new int[layout.getRecordCount()];
				for (int i = 0; i < counts.length; i++) {
					counts[i] = 0;
				}
				for (int i = 0; i < fieldMapping.getRowCount(); i++) {
					counts[fieldMapping.getLine(i).getPreferredLayoutIdx()] += 1;
				}

				for (int i = 1; i < counts.length; i++) {
					if (counts[i] > counts[idx]) {
						idx = i;
					}
				}

			}

			super.allocateColumnNames(fieldMapping, idx);
		}

		protected void writeLine(FieldWriter writer, AbstractLine line)
		throws IOException {

			if (line == null) {
				writeNullLine(writer);
			} else {
				int idx = line.getPreferredLayoutIdx();
				writeLine(writer, line, idx, layout.getRecord(idx).getFieldCount());
			}
		}

		protected void writeLine(FieldWriter writer, AbstractLine line, int fieldCount)
		throws IOException {

			if (line == null) {
				writeNullLine(writer);
			} else {
				int idx = line.getPreferredLayoutIdx();
				writeLine(writer, line, idx, fieldCount);
			}
		}

		private void writeNullLine(FieldWriter writer) throws IOException {

			if (writer.printAllFields()) {
				writeLine(writer, null, 0, 0);
			} else {
				writer.newLine();
			}
		}


		public boolean[] getFieldsToInclude() {
			return file.getFieldVisibility(DisplayType.getRecordMaxFields(layout));
		}

	}

	private static class WriteFullLine extends SaveAsWrite {

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.edit.display.util.SaveAsWrite.BaseWrite#getColumnNames()
		 */
		@Override
		protected void allocateColumnNames(FileView fieldMapping, int idx) {
			columnNames.add(LangConversion.convert("Full Line"));
		}



		protected void writeLine(FieldWriter writer, AbstractLine line) throws IOException {
			//byte[] bytes= line.getData();

			if (line != null) {
				writer.writeField(line.getFullLine());
			}
			writer.newLine();
		}

	}


	private static class WriteHexLine extends SaveAsWrite {


		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.edit.display.util.SaveAsWrite.BaseWrite#getColumnNames()
		 */
		@Override
		protected void allocateColumnNames(FileView fieldMapping, int idx) {
			columnNames.add("Hex");
		}

		protected void writeLine(FieldWriter writer, AbstractLine line) throws IOException {
			byte[] bytes= line.getData();
			writer.writeField(Conversion.getDecimal(bytes, 0, bytes.length));
			writer.newLine();
		}
	}


	private static class WriteTree extends SaveAsWrite {
		private int lineNo = 1;
		protected void writeLine(FieldWriter writer, AbstractLine line) throws IOException {
			writeTreeLineAsHtml(writer,line, false, (lineNo++) + "");
		}


	    @SuppressWarnings("rawtypes")
		private void writeTreeLineAsHtml(FieldWriter writer, AbstractLine line,
	    		boolean indent, String id)
	    throws IOException {

			int idx = line.getPreferredLayoutIdx();

			writer.startLevel(indent, id);
			writeLine(writer, line, idx, layout.getRecord(idx).getFieldCount());

	        AbstractTreeDetails tree = line.getTreeDetails();
	        AbstractChildDetails childDef;
	        List<? extends AbstractLine> list;
	        int j;

	        for (int i = 0; i < tree.getChildCount(); i++) {
	        	childDef = tree.getChildDetails(i);
	        	list = tree.getLines(i);

	        	if (list.size() == 1) {
	        		writeTreeLineAsHtml(writer, list.get(0), false, childDef.getName());
	        	} else {
		        	for (j = 0; j < list.size(); j++) {
		        		writeTreeLineAsHtml(writer, list.get(j), true, childDef.getName()+"."+(j+1));
		        	}
	        	}
	        }

	        writer.endLevel();
	    }

	}
}
