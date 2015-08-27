package net.sf.RecordEditor.utils.fileStorage;

public interface IDocumentRecordStore extends IRecordStore {

	public abstract LineDtls getTextPosition(int textPos);

	public abstract LineDtls getLinePositionLength(int idx, int newLen);

}
