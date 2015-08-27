package net.sf.RecordEditor.utils.fileStorage;

public interface IRecordStore {

	public void add(int idx, byte[] rec);

	void add(int idx, IRecordStore rStore);

	public byte[] get(int idx);

	public int getSize();
	
	public int getStoreSize();

	public int getRecordCount();

	public byte[] getCompressed();
	
	public byte[] getBytes();

	public void setCompressed(byte[] compressedData, int size, int number);

	public void setBytes(byte[] bytes, int size, int number);
	
	public void put(int idx, byte[] rec);	
	
	public int remove(int idx);
	
	public IRecordStore[] split(int blockSize);

	public IRecordStore[] splitAt(int recordNumber);
}
