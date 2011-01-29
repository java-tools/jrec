package net.sf.RecordEditor.edit.display.util;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.Types.Type;
import net.sf.RecordEditor.edit.display.common.AbstractFileDisplay;
import net.sf.RecordEditor.edit.display.common.ILayoutChanged;
import net.sf.RecordEditor.edit.file.FileView;
import net.sf.RecordEditor.utils.screenManager.ReFrame;

public class Code {

	private static final byte[] NULL_BYTES = {};
	public static void notifyFramesOfNewLayout(
			FileView masterView, 
			AbstractLayoutDetails<?,?> layout) {
		ReFrame[] frames;
		
		masterView.setLayout(layout);
		masterView.fireTableStructureChanged();

		frames = ReFrame.getAllFrames();
		for (int i = 0; i < frames.length; i++) {
			if (frames[i].getDocument() == masterView) {
				if (frames[i] instanceof ILayoutChanged) {
					((ILayoutChanged) frames[i]).layoutChanged(layout);
				} else {
					frames[i].doDefaultCloseAction();
				}
			}
		}
	}
	
	
	public static void notifyFramesOfUpdatedLayout(
			FileView masterView, 
			AbstractLayoutDetails<?,?> layout) {
		ReFrame[] frames;
		
		masterView.fireTableStructureChanged();

		frames = ReFrame.getAllFrames();
		for (int i = 0; i < frames.length; i++) {
			if (frames[i].getDocument() == masterView) {
				if (frames[i] instanceof AbstractFileDisplay) {
					((AbstractFileDisplay) frames[i]).setNewLayout(layout);
				} else if (frames[i] instanceof ILayoutChanged) {
					((ILayoutChanged) frames[i]).layoutChanged(layout);
				}
			}
		}
	}
	
	
	public static void updateFile(FileView<LayoutDetail> masterFile, LayoutDetail newLayout, int[] trans) {
		AbstractLine<LayoutDetail> newLine, oldLine;

		for (int i = 0; i < masterFile.getRowCount(); i++) {
			newLine = masterFile.getLine(i);
			oldLine = newLine.getNewDataLine();
			newLine.setLayout(newLayout);
			newLine.setData(NULL_BYTES);
			for (int j = trans.length - 1; j >= 0; j--) {		
				try {
					if (trans[j] >= 0) {
						newLine.setField(0, j, oldLine.getField(0, trans[j]));
					}
				} catch (RecordException e) {
					e.printStackTrace();
				}
			}
		}

		masterFile.setChanged(true);
		notifyFramesOfNewLayout(masterFile, newLayout);
	}

	public static void addColumn(FileView<LayoutDetail> masterFile, LayoutDetail layout, int col, int source) {
		String s = Integer.toString(col+1);
		RecordDetail rec = layout.getRecord(0);
		FieldDetail[] fields = new FieldDetail[rec.getFieldCount() + 1];
		int[] trans = new int[fields.length];
		
		for (int i = 0; i < col; i++) {
			fields[i] = cloneCsvField(rec.getField(i), i+1);
			trans[i] = i;
		}
		fields[col] = new FieldDetail(s, "", Type.ftChar, 0, rec.getFontName(), 0, ""); 
		fields[col].setPosOnly(col+1);
		trans[col] = source;

		for (int i = col+1; i < fields.length; i++) {
			fields[i] = cloneCsvField(rec.getField(i-1), i+1);
			trans[i] = i-1;
		}
		
		updateFile(masterFile, cloneCsvLayout(layout, fields), trans);
	}

	public static void moveColumn(FileView<LayoutDetail> masterFile, LayoutDetail layout, int dest, int source) {
		RecordDetail rec = layout.getRecord(0);
		FieldDetail[] fields = new FieldDetail[rec.getFieldCount()];
		int[] trans = new int[fields.length];
		int k = 0;
		int d = dest;
		if (d > source) {
			d -= 1;
		}
		
		for (int i = 0; i < fields.length; i++) {
			if (i == source) {
				
			} else {
				if (k == d) {
					k += 1;
				}
				fields[k] = cloneCsvField(rec.getField(i), k+1);
				trans[k] = i;
				k += 1;
			}
		}
		fields[d] = cloneCsvField(rec.getField(source), d+1);
		trans[d] = source;
		
		updateFile(masterFile, cloneCsvLayout(layout, fields), trans);
	}

	public static void deleteColumn(FileView<LayoutDetail> masterFile, LayoutDetail layout, int col) {
		RecordDetail rec = layout.getRecord(0);
		FieldDetail[] fields = new FieldDetail[rec.getFieldCount() - 1];
		int[] trans = new int[fields.length];
		
		for (int i = 0; i < col; i++) {
			fields[i] = cloneCsvField(rec.getField(i), i+1);
			trans[i] = i;
		}

		for (int i = col; i < fields.length; i++) {
			fields[i] = cloneCsvField(rec.getField(i + 1), i+1);
			
			trans[i] = i + 1;
		}
		
		updateFile(masterFile, cloneCsvLayout(layout, fields), trans);
	}

	private static FieldDetail cloneCsvField(FieldDetail f, int pos) {
		FieldDetail ret = new FieldDetail(
				f.getName(), f.getDescription(), f.getType(), f.getDecimal(), 
				f.getFontName(), f.getFormat(), f.getParamater());
		ret.setPosOnly(pos);
		return ret;
	}
	
	private static LayoutDetail cloneCsvLayout(LayoutDetail l, FieldDetail[] fields) {
		RecordDetail rec = l.getRecord(0);
		RecordDetail[] recs = new RecordDetail[1];
		
        recs[0] = new RecordDetail(
        		rec.getRecordName(), "", "", rec.getRecordType(),
        		rec.getDelimiter(), rec.getQuote(), 
        		l.getFontName(), fields, rec.getRecordStyle());
        
        return
            new LayoutDetail(l.getLayoutName(), recs, "",
                l.getLayoutType(),
                l.getRecordSep(), "", l.getFontName(), null,
                l.getFileStructure()
            );
	}
}
