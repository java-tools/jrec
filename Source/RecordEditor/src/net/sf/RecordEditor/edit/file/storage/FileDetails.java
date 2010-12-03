package net.sf.RecordEditor.edit.file.storage;

import java.util.ArrayList;

import net.sf.JRecord.Details.LayoutDetail;
import net.sf.RecordEditor.utils.common.Common;

public class FileDetails {
	
	public static final int FIXED_LENGTH = 1;
	public static final int VARIABLE_LENGTH = 2;
	public static final int CHAR_LINE = 3;

	private static final int RECENT_SIZE = 25; //512000;
	
	
	public final int dataSize; //512000;
	public final int type, len;
	
	private LayoutDetail layout;
	
	@SuppressWarnings("unchecked")
	private ArrayList<FileChunkBase> recent = new ArrayList<FileChunkBase>(RECENT_SIZE);

	public FileDetails(LayoutDetail recordLayout, int type, int len) {
		super();
		this.type = type;
		this.len = len;
		this.layout = recordLayout;
		
		dataSize = Common.getChunkSize();
	}
	
	public FileDetails(LayoutDetail recordLayout, int type, int len, int chunkSize) {
		this.type = type;
		this.len = len;
		this.layout = recordLayout;
		
		dataSize = chunkSize;
	}
	
	public RecordStore getRecordStore() {
		switch (type) {
		case VARIABLE_LENGTH:
			return new RecordStoreVariableLength(dataSize, len, 0);
		case CHAR_LINE:
			return new RecordStoreCharLine(dataSize, layout.getMaximumRecordLength(), layout.getFontName());
		} 
		return new RecordStoreFixedLength(dataSize, len, 0);
	}
	
	
	public RecordStore getEmptyRecordStore() {
		switch (type) {
		case VARIABLE_LENGTH:
			return new RecordStoreVariableLength(-1, len, 0);
		case CHAR_LINE:
			return new RecordStoreCharLine(-1, 1, layout.getFontName());
		} 
		return new RecordStoreFixedLength(-1, len, 0);
	}
	
	public void registerUse(FileChunkBase ch) {
		int ii = recent.size();
		if (ch.getFirstLine() > 0
		&& ( ii == 0
		  || (recent.get(ii - 1) != ch))) {
			recent.remove(ch);
			if (recent.size() >= RECENT_SIZE) {
				FileChunkBase fc = recent.get(0);
				fc.compress();
				recent.remove(fc);
			}
			
			recent.add(ch);
		}
	}
	
	public void registerCompress(FileChunkBase ch) {
		if (ch.getFirstLine() > 0) {
			recent.remove(ch);
		}
	}

	public LayoutDetail getLayout() {
		return layout;
	}

	public void setLayout(LayoutDetail layout) {
		this.layout = layout;
	}
}
