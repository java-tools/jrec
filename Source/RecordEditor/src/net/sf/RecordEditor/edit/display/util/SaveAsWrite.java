package net.sf.RecordEditor.edit.display.util;

import java.io.IOException;
import java.math.BigDecimal;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Types.Type;
import net.sf.RecordEditor.edit.display.common.AbstractFileDisplay;
import net.sf.RecordEditor.edit.file.DisplayType;
import net.sf.RecordEditor.edit.file.FileView;
import net.sf.RecordEditor.utils.FieldWriter;

public class SaveAsWrite {
	public static final int SAVE_FILE = 1; 
	public static final int SAVE_VIEW = 2; 
	public static final int SAVE_SELECTED = 3; 
	
	public static BaseWrite getWriter(FileView file, AbstractFileDisplay recFrame) {
		BaseWrite ret;
		AbstractLayoutDetails layout = file.getLayout();
		
		switch(DisplayType.displayType(layout, recFrame.getLayoutIndex())) {
		case DisplayType.PREFFERED:
			ret = new writePreferedLayout();
			break;
		case DisplayType.FULL_LINE:
			ret = new writeFullLine();
			break;
		case DisplayType.HEX_LINE:
			ret = new writeHexLine();
			break;
		default:
			ret = new writeFromLayout();
		}
		
		ret.layout = layout;
		ret.layoutIdx = recFrame.getLayoutIndex();
		ret.file = file;
		ret.recFrame = recFrame;
		
		return ret;
	}

	public static abstract class BaseWrite {
		protected AbstractLayoutDetails layout;
		protected FileView file;
		protected AbstractFileDisplay recFrame;
		protected int layoutIdx;
		
	    public void writeFile(FieldWriter writer, boolean namesOnFirstLine, int saveWhat) throws IOException{
	        //int layoutIdx = file.getCurrLayoutIdx();
	        switch (saveWhat) {
	        case SAVE_FILE:
	        	writeAFile(writer, file.getBaseFile(), layoutIdx, null, namesOnFirstLine);
	        	break;
	        case SAVE_VIEW:
            	writeAFile(writer, file, layoutIdx, null, namesOnFirstLine);
            	break;
            default:
               	writeAFile(
               			writer, file, layoutIdx, 
               			recFrame.getSelectedRows(), 
               			namesOnFirstLine);       		
	        }
//	        if (OPT_FILE.equals(selection)) {
//	        	saveFile_911_writeFile(writer, file.getBaseFile(), layoutIdx, null, activePnl.namesFirstLine.isSelected());
//	        } else  {
//	        	if (OPT_VIEW.equals(selection)) {
//	            	saveFile_911_writeFile(writer, file, layoutIdx, null, activePnl.namesFirstLine.isSelected());
//	        }
	        writer.close();
	   	}
	    
		protected void writeAFile(
	    		FieldWriter writer, FileView view, 
	    		int layoutIdx,
	    		int[] rows, boolean namesFirstLine) throws IOException {
			
	    	if (rows == null) {
	    		for (int i = 0; i < view.getRowCount(); i++) {
	    			writeLine(writer, view.getLine(i));
	            }	
	    	} else {
	            for (int i = 0; i < rows.length; i++) {
	            	writeLine(writer, view.getLine(rows[i]));
	            }
	    	}

		}
		
		protected abstract void writeLine(FieldWriter writer, AbstractLine<?> line) 
		throws IOException;
		
		public boolean[] getFieldsToInclude() {
			return null;
		}

	}
	
	public static class writeFromLayout extends BaseWrite {
		
		int colCount;


		protected void writeAFile(
	    		FieldWriter writer, FileView view, 
	    		int layoutIdx,
	    		int[] rows, boolean namesFirstLine) throws IOException {

	    	colCount = layout.getRecord(layoutIdx).getFieldCount();
	    	boolean[] numeric = new boolean[colCount];
	    	int layoutFieldNo; 
			   
	    	for (int i = 0; i < colCount; i++) {
	    		numeric[i] = false;
	    	}
	    	
	    	if (layoutIdx < layout.getRecordCount()) {
	    		Object o;
	    		
	    		boolean onlyNumeric;
	    		boolean aNumeric;
	    		int toCheck = Math.min(view.getRowCount(), 35);
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

	       	if (namesFirstLine) {
	    		for (int i = 0; i < colCount; i++) {
	    			layoutFieldNo = view.getRealColumn(layoutIdx, i);
	    			writer.writeFieldHeading(
	    					layout.getRecord(layoutIdx).getField(layoutFieldNo).getName());
	    		}
	    		writer.newLine();
	    	}
	 
	    	super.writeAFile(writer, view, layoutIdx, rows, namesFirstLine);
	    }
		
		protected void writeLine(FieldWriter writer, AbstractLine<?> line) 
		throws IOException {
			Object o;
			String s;
        	for (int j = 0; j < colCount; j++) {
        		o = line.getField(layoutIdx, j);
        		s = null;
        		if (o != null) {
        			s = o.toString();
        		}
        		writer.writeField(s);
        	}
        	writer.newLine();	
		}

		public boolean[] getFieldsToInclude() {
			return file.getFieldVisibility(layoutIdx);
		}

	}
	
	public static class writePreferedLayout extends BaseWrite {
		protected void writeLine(FieldWriter writer, AbstractLine<?> line) 
		throws IOException {
			Object o;
			String s;
			int idx = line.getPreferredLayoutIdx();
        	for (int j = 0; j < layout.getRecord(idx).getFieldCount(); j++) {
        		o = line.getField(idx, j);
        		s = null;
        		if (o != null) {
        			s = o.toString();
        		}
        		writer.writeField(s);
        	}
        	writer.newLine();	
		}

		public boolean[] getFieldsToInclude() {
			return file.getFieldVisibility(DisplayType.getRecordMaxFields(layout));
		}

	}
	
	public static class writeFullLine extends BaseWrite {
		
		int colCount;

		protected void writeAFile(
	    		FieldWriter writer, FileView view, 
	    		int layoutIdx,
	    		int[] rows, boolean namesFirstLine) throws IOException {
			
			if (namesFirstLine) {
				writer.writeFieldHeading("Full Line");
				writer.newLine();
			}
			
	    	super.writeAFile(writer, view, layoutIdx, rows, namesFirstLine);
		}
		protected void writeLine(FieldWriter writer, AbstractLine<?> line) throws IOException {
			byte[] bytes= line.getData();
			
			writer.writeField(line.getFullLine());
			writer.newLine();
		}

	}
	
	
	public static class writeHexLine extends BaseWrite {
		
		int colCount;

		protected void writeAFile(
	    		FieldWriter writer, FileView view, 
	    		int layoutIdx,
	    		int[] rows, boolean namesFirstLine) throws IOException {
			
			if (namesFirstLine) {
				writer.writeFieldHeading("Hex");
				writer.newLine();
			}
			
	    	super.writeAFile(writer, view, layoutIdx, rows, namesFirstLine);
		}
		
		protected void writeLine(FieldWriter writer, AbstractLine<?> line) throws IOException {
			byte[] bytes= line.getData();
			writer.writeField(Conversion.getDecimal(bytes, 0, bytes.length));
			writer.newLine();
		}
	}

}
