package net.sf.RecordEditor.edit.file.storage;

import java.util.ArrayList;

import net.sf.JRecord.Details.LayoutDetail;
import net.sf.RecordEditor.utils.common.Common;

public class FileDetails {
	
	public static final int FIXED_LENGTH = 1;
	public static final int VARIABLE_LENGTH = 2;

	private static final int RECENT_SIZE = 25; //512000;
	
	
	public final int dataSize; //512000;
	public final int type, len;
	
	private LayoutDetail layout;
	
	private ArrayList<FileChunk> recent = new ArrayList<FileChunk>(RECENT_SIZE);

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
		if (type == VARIABLE_LENGTH) {
			return new RecordStoreVariableLength(dataSize, len, 0);
		}
		return new RecordStoreFixedLength(dataSize, len, 0);
	}
	
	
	public RecordStore getEmptyRecordStore() {
		if (type == VARIABLE_LENGTH) {
			return new RecordStoreVariableLength(-1, len, 0);
		}
		return new RecordStoreFixedLength(-1, len, 0);
	}
	
	public void registerUse(FileChunk ch) {
		int ii = recent.size();
		if (ch.getFirstLine() > 0
		&& ( ii == 0
		  || (recent.get(ii - 1) != ch))) {
			recent.remove(ch);
			if (recent.size() >= RECENT_SIZE) {
				FileChunk fc = recent.get(0);
				fc.compress();
				recent.remove(fc);
			}
			
			recent.add(ch);
		}
	}
	
	public void registerCompress(FileChunk ch) {
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
