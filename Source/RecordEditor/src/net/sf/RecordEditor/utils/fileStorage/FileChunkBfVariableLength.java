package net.sf.RecordEditor.utils.fileStorage;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import net.sf.JRecord.ByteIO.AbstractByteReader;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.utils.common.Common;

public class FileChunkBfVariableLength extends FileChunkLine {
	private long filePostion;
	private int length = 0;
	private boolean blockRead = false;
	
	public FileChunkBfVariableLength(FileDetails details, int theFirstLine, long filePos) {
		super(details, theFirstLine);
		
		filePostion = filePos;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public boolean hasRoomForMore(AbstractLine l) {
		if (isNormalMode()) {
			return super.hasRoomForMore(l);
		}
		
		return (length + l.getData().length < details.dataSize);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void uncompress() {
		
		if (recordStore != null) {
			details.registerUse(this);
		} else if (super.getDiskAddress() < 0 && compressed == null) {
			byte[] bytes;
			ByteArrayInputStream in = new ByteArrayInputStream(
					super.details.readFromMainFile(filePostion, length));
			AbstractByteReader r = details.getByteReader();
			
			recordStore = (RecordStoreBase) super.details.getRecordStore();
			
			try {
				int i = 0;
				r.open(in);
				while ((bytes = r.read()) != null) {
					recordStore.add(i, bytes);
					i += 1;
				}
				blockRead = true;
			} catch (IOException e) {
				Common.logMsg("Error Reading & unpacking file: " + e.getMessage(), e);
				throw new RuntimeException("Error Unpacking file",e);
			} finally {		
				try {
					r.close();
				} catch (IOException e) {

				}
			}
			
		} else {
			super.uncompress();
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public void add(int idx, AbstractLine l) {
		
		if (isNormalMode()) {
			super.add(idx, l);
		} else {
			super.count += 1;
			long len = details.getByteReader().getBytesRead() - filePostion;
			length = (int) len;
		}
	}
	
	@Override
	public int getSize() {
		if (isNormalMode()) {
			return super.getSize();
		}
		return length;
	}

	private boolean isNormalMode() {
		return (! details.isReading()) || blockRead;
	}
}
