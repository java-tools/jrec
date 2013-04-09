package net.sf.RecordEditor.re.file;

public interface DocumentUpdateListner {

	void fireUpdate(int type, int where, int count);

}