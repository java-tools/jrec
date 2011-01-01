package net.sf.RecordEditor.edit.file.storage;


import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.LayoutDetail;


public class LineTemp extends LineBase {

	
	public LineTemp(LayoutDetail group, FileChunkLine fileChunk, int line) {
		super(group, fileChunk, line);
	}
	
	@Override 
	protected void clearData() {
		throw new RuntimeException("Can not update temporary Line");
	}

	@Override
	public void setData(byte[] newVal) {
		throw new RuntimeException("Can not update field in temporary Line");
	}

	@Override
	public void setField(FieldDetail field, Object value)
			throws RecordException {
		throw new RecordException("Can not update field in temporary Line");
	}

	@Override
	public String setFieldHex(int recordIdx, int fieldIdx, String val)
			throws RecordException {
		throw new RecordException("Can not update field in temporary Line");
	}

	@Override
	public void setFieldText(int recordIdx, int fieldIdx, String value)
			throws RecordException {
		throw new RecordException("Can not update field in temporary Line");
	}

	

}
