package net.sf.RecordEditor.edit.file.storage;

public interface RecordStore {

	public void add(int idx, byte[] rec);

	public byte[] get(int idx);

	public int getSize();
	
	public int getStoreSize();

	public int getRecordCount();

	public byte[] getCompressed();

	public void setCompressed(byte[] compressedData, int size, int number);
	
	public void put(int idx, byte[] rec);	
	
	public int remove(int idx);

}
