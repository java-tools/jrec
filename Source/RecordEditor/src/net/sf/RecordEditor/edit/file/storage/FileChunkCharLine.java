package net.sf.RecordEditor.edit.file.storage;

import java.lang.ref.WeakReference;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.CharLine;


public class FileChunkCharLine extends FileChunkBase<CharLineBase, RecordStoreCharLine> {

	//private RecordStoreCharLine rs;
	public FileChunkCharLine(FileDetails details, int theFirstLine) {
		super(details, theFirstLine);
	}


	@SuppressWarnings("unchecked")
	@Override
	public  void add(int idx, AbstractLine l) {

		add(idx, l.getFullLine().toCharArray());
	}

	public  void add(int idx, char[] rec) {

		uncompress();
		
		//synchronized (recordStore) {
		getRecordStore().add(idx, rec); 
		count = recordStore.getRecordCount();
			
		updateLines(idx, 1);
		//}
	}
	


	public byte[] get(int idx) {

		return Conversion.getBytes(getString(idx), details.getLayout().getFontName());
	}


	public char[] getChar(int idx) {
		uncompress();
		return getRecordStore().getChar(idx);
	}


	public String getString(int idx) {
		return new String(getChar(idx));
	}

	@SuppressWarnings("unchecked")
	@Override
	public void put(int idx, AbstractLine l) {

		put(idx, ((CharLine) l).getFullLine().toCharArray());
		
	}

	public void put(int idx, char[] rec) {
		synchronized (this) {
			uncompress();
			getRecordStore().put(idx, rec);
			compressed = null;
		}
	} 


	@SuppressWarnings("unchecked")
	@Override
	public AbstractLine removeLine(int lineNo) {
		uncompress();

		String s = new String(recordStore.getChar(lineNo));
		AbstractLine l = new CharLine(details.getLayout(), s);
		remove(lineNo);
		return l; 
	}


	@Override
	protected CharLineBase getLine(int lineNo) {
		Integer cLine = (lineNo - getFirstLine());
		CharLineBase ret = null;
		
		getLines();
		synchronized (lines) {
			if (lines.containsKey(cLine)) {
				ret =  lines.get(cLine).get();
			}
			if (ret == null) {
				ret = new CharLineChunk(details.getLayout(), this, cLine);
				lines.put(cLine, new WeakReference<CharLineBase>(ret));
			}
		}
		return ret;
	}
	
	


	@Override
	protected CharLineBase getTempLine(int lineNo) {
		return new CharLineTemp(details.getLayout(), this, (lineNo - getFirstLine()));
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean hasRoomForMore(AbstractLine l) {
		int len = l.getData().length;
		uncompress();
//		System.out.println(" --> hasRoom " + recordStore.getSize() + " " 
//				+ details.len + " " + recordStore.getStoreSize());
		
		return (recordStore != null) 
			&& (recordStore.getSize() + len) < recordStore.getStoreSize();
	}

	
	private RecordStoreCharLine getRecordStore() {
		return recordStore;
	}

}
