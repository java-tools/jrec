package net.sf.RecordEditor.edit.file.storage;

import java.lang.ref.WeakReference;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.Line;


public class FileChunk extends FileChunkBase<LineBase, RecordStoreBase> {


	public FileChunk(FileDetails details, int theFirstLine) {
		super(details, theFirstLine);
	}


	@SuppressWarnings("unchecked")
	@Override
	public  void add(int idx, AbstractLine l) {

		add(idx, l.getData());
	}

	public  void add(int idx, byte[] rec) {

		uncompress();
		
		//synchronized (recordStore) {
			recordStore.add(idx, rec); 
			count = recordStore.getRecordCount();
			
			updateLines(idx, 1);
		//}
	}
	


	public byte[] get(int idx) {
		uncompress();
		return recordStore.get(idx);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void put(int idx, AbstractLine l) {
		put(idx, l.getData());
	}

	public void put(int idx, byte[] rec) {
		synchronized (this) {
			uncompress();
			recordStore.put(idx, rec);
			compressed = null;
		}
	} 


	@SuppressWarnings("unchecked")
	@Override
	public AbstractLine removeLine(int lineNo) {
		uncompress();
		AbstractLine l = new Line(details.getLayout(), get(lineNo));
		remove(lineNo);
		return l;
	}


	@Override
	protected LineBase getLine(int lineNo) {
		Integer cLine = (lineNo - getFirstLine());
		LineBase ret = null;
		
		getLines();
		synchronized (lines) {
			if (lines.containsKey(cLine)) {
				ret = (LineBase) lines.get(cLine).get();
			}
			if (ret == null) {
				ret = new LineChunk(details.getLayout(), this, cLine);
				lines.put(cLine, new WeakReference<LineBase>(ret));
			}
		}
		return ret;
	}
	

	@Override
	protected LineBase getTempLine(int lineNo) {
		return new LineTemp(details.getLayout(), this, (lineNo - getFirstLine()));
	}
	

	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean hasRoomForMore(AbstractLine l) {
		int len = l.getData().length;
		//System.out.println(" --> " + recordStore.getSize() + " " 
		//		+ details.len + " " + FileDetails.DATA_SIZE);
		
		uncompress();
		return (recordStore != null) 
			&& (recordStore.getSize() + len) < recordStore.getStoreSize();
	}

	
	@Override
	public int getSpaceUsed() {
		int ret = 0;
		
		if (this.recordStore != null) {
			ret = this.recordStore.getStoreSize();
		}
		if (this.compressed != null) {
			ret += this.compressed.length;
		}
		
		return ret;
	}

}
