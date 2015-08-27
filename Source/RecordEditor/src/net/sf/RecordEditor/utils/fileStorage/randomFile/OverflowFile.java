package net.sf.RecordEditor.utils.fileStorage.randomFile;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class acts as an interface to the overflow file. 
 * Having a class / interface allows it to be mocked during testing
 */

public class OverflowFile implements IOverflowFile {

	private RandomAccessFile overflowRandomFile = null;
	private File overflowFile = null;
	
	
	/**
	 * This class acts as an interface to the overflow file. 
	 * Having a class / interface allows it to be mocked during testing
	 */
	public OverflowFile() {
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.randomFile.IOverflowFile#getWriter(long, int)
	 */
	@Override
	public final DataOutput getWriter(long pos, int length) throws IOException {
		getOverflowFile().seek(pos);
		
		return getOverflowFile();
	}
	
	
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.randomFile.IOverflowFile#free(java.io.DataOutput)
	 */
	@Override
	public final void free(DataOutput out) throws IOException {
		
	}
	
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.randomFile.IOverflowFile#getReader(long)
	 */
	@Override
	public final DataInput getReader(long pos) throws IOException {
		getOverflowFile().seek(pos);
		
		return getOverflowFile();
	}

	
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.randomFile.IOverflowFile#free(java.io.DataInput)
	 */
	@Override
	public final void free(DataInput in) throws IOException {
		
	}

	private RandomAccessFile getOverflowFile() throws IOException {
		if (overflowRandomFile == null) {
			synchronized (this) {
			    DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");


			    overflowFile = File.createTempFile(
			    		"~tmp_"
						 + dateFormat.format(new Date()),
						".tmp~"
						);
			    overflowFile.deleteOnExit();
//			    System.out.println("~~File: " + overflowFile.getAbsolutePath()
//			    		+ " " + overflowFile.getCanonicalPath()
//			    		+ " " + overflowFile.getCanonicalFile()
//			    		+ " " + overflowFile.getName());

				overflowRandomFile = new RandomAccessFile(overflowFile, "rw");
			}
		}

		return overflowRandomFile;
	}
	
	
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.randomFile.IOverflowFile#clear()
	 */
	@Override
	public void clear() {
		if (overflowRandomFile != null) {
			synchronized (this) {
				try {
					overflowRandomFile.close();
					overflowFile.delete();
				} catch (IOException e) {
				}
				//(new File(overflowFileName)).delete();
				overflowRandomFile = null;
				overflowFile = null;
			}
		}
	}

}
