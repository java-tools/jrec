package net.sf.RecordEditor.utils.fileStorage;

/**
 * A simple Record Store that is used by RecordIndexMMgr and can be implemented 
 * for testing of RecordIndexMMgr
 * 
 * @author Bruce Martin
 *
 */
public interface ISimpleRecStore {

	public int getRecordCount();

	public int getLength(int idx);
	
	public int getStoreSize();
	
	public void setSize(int size);
}
