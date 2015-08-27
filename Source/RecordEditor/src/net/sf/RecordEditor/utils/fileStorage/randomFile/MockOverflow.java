package net.sf.RecordEditor.utils.fileStorage.randomFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;


public class MockOverflow implements IOverflowFile {

	private HashMap<Long, ByteArrayOutputStream> details = new HashMap<Long, ByteArrayOutputStream>(300);
	
	
	public MockOverflow() {
		// TODO Auto-generated constructor stub
		
	
	}

	@Override
	public DataOutput getWriter(long pos, int length) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		
		details.put(pos, os);

		return new DataOutputStream(os);
	}

	@Override
	public void free(DataOutput out) throws IOException {
//		if (out instanceof DataOutputStream) {
//			((DataOutputStream) out).close();
//		}
	}

	@Override
	public DataInput getReader(long pos) throws IOException {
		ByteArrayOutputStream os = details.get(pos);
		if (os == null) {
			throw new IOException("Position: " + pos + " was not found in mock map");
		}
		
		ByteArrayInputStream in = new ByteArrayInputStream(os.toByteArray());
		return new DataInputStream(in);
	}

	@Override
	public void free(DataInput in) throws IOException {
		
	}

	@Override
	public void clear() {
		details.clear();
	}
}
