package net.sf.RecordEditor.utils.fileStorage;

import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.LayoutDetail;

public class LineChunk extends LineBase {


	public LineChunk(LayoutDetail group, FileChunkLine fileChunk, int line) {
		super(group,  fileChunk, line);
		//super.setDataRaw(fileChunk.get(line));
	}


	private byte[] getLineFromChunk() {
		return chunk.get(chunkLine);
	}


	@Override
	protected void clearData() {

		super.clearData();
		updateChunk();

	}

	@Override
	public void setData(byte[] newVal) {
		chunk.putFromLine(chunkLine, newVal);
	}

	@Override
	public void setField(IFieldDetail field, Object value)
			throws RecordException {

		synchronized (this) {
			byte[] b = getLineFromChunk();
			super.setDataRaw(b);
			//System.out.print("setField - getFrom storage");
			//writeByteArray(b);
			super.setField(field, value);
			updateChunk();
		}
	}

	@Override
	public String setFieldHex(int recordIdx, int fieldIdx, String val)
			throws RecordException {
		super.setDataRaw(getLineFromChunk());
		String s = super.setFieldHex(recordIdx, fieldIdx, val);
		updateChunk();

		return s;
	}

	@Override
	public void setFieldText(int recordIdx, int fieldIdx, String value)
			throws RecordException {

		super.setDataRaw(getLineFromChunk());
		super.setFieldText(recordIdx, fieldIdx, value);
		updateChunk();
	}


	@Override
	protected byte[] getLineData() {
		byte[] b = getLineFromChunk();
		super.setDataRaw(b);
		//System.out.print("getLineData - ");
		//writeByteArray(b);
		return b;
	}
}
