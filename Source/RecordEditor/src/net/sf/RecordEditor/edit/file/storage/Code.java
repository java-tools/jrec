package net.sf.RecordEditor.edit.file.storage;


import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class Code {

	public static byte[] compress(int size, byte[] store) {
		byte[] ret = null;

		Deflater deflater = new Deflater();
		deflater.setInput(store, 0, store.length);
		deflater.finish();
		byte[] buff = new byte[store.length + 50];
		
		deflater.deflate(buff);

		int compressedSize = deflater.getTotalOut();

		// Did this data compress well?
		if (deflater.getTotalIn() != store.length) {
			return null;
		}
		
		ret = new byte[compressedSize];
		System.arraycopy(buff, 0, ret, 0, compressedSize);
		
		return ret;
	}

	public static ByteArray uncompressed(byte[] compressedData, int length) {
		return uncompressed(compressedData, length, true);
	}
	

	public static ByteArray uncompressed(byte[] compressedData, int length, boolean doCheck) {

		byte[] store = new byte[length];
		int bytesUsed;
		

        try {
			Inflater inflater = new Inflater();
			inflater.setInput(compressedData, 0, compressedData.length);
			inflater.finished();
			

			bytesUsed = inflater.inflate(store);
			//bytesUsed = (int) inflater.getBytesWritten();
			
			inflater.reset();
			
		} catch (DataFormatException e) {
			throw new RuntimeException("Data Format error: " + e.getMessage(), e);
		}  
		
		return new ByteArray(bytesUsed, store, false);
	}
}
