package net.sf.RecordEditor.utils.fileStorage;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import net.sf.JRecord.ByteIO.AbstractByteReader;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.Parameters;
import net.sf.RecordEditor.utils.common.ProgramOptions;

public class FileDetails {
	
	public static final int FIXED_LENGTH = 1;
	public static final int VARIABLE_LENGTH = 2;
	public static final int CHAR_LINE = 3; 
	public static final int FIXED_LENGTH_BASEFILE = 4;
	public static final int VARIABLE_LENGTH_BASEFILE = 5;
	
	public static final int PROCESSORS = Runtime.getRuntime().availableProcessors();
	
	private static final int REMAINDER1;
	private static final int REMAINDER2;
	private static boolean DISK_WRITE_TEST;

	private static int RECENT_SIZE =  -1; //512000;
	
	static {
		int r1 = 3;
		int r2 = 6;
		
		if (PROCESSORS > 2 || (Common.OPTIONS.compressOption.get() == ProgramOptions.COMPRESS_READ_FAST_CPU)) {
			r1 = 2;
			r2 = 4;
		}
		
		REMAINDER1 = r1;
		REMAINDER2 = r2;
	}
	
	public final int dataSize; //512000;
	public final int type, len;
	
	private LayoutDetail layout;
	
	@SuppressWarnings("unchecked")
	private ArrayList<FileChunkBase> recent = new ArrayList<FileChunkBase>(25);

	@SuppressWarnings("unchecked")
	private ArrayList<FileChunkBase> toDiskList = new ArrayList<FileChunkBase>(200);

	//private String overflowFileName = "";
	private File overflowFile = null;
	private RandomAccessFile overflowRandomFile = null;
	private long filePos = 0;

	private String mainFileName = "";
	private RandomAccessFile mainFile = null;
	private boolean inCompressCheck = false;
	private boolean isReading       = true;
	private boolean canDoGC         = true;
	
	private AbstractByteReader byteReader = null;

	public FileDetails(LayoutDetail recordLayout, int type, int len) {
		super();
		this.type = type;
		this.len = len;
		this.layout = recordLayout;
		
		dataSize = Common.OPTIONS.chunkSize.get();
		
		init();
	}
	
	public FileDetails(LayoutDetail recordLayout, int type, int len, int chunkSize) {
		this.type = type;
		this.len = len;
		this.layout = recordLayout;
		
		dataSize = chunkSize;
		
		init();
	}
	
	private void init() {
		if (RECENT_SIZE < 0) {
			
			DISK_WRITE_TEST = "Y".equalsIgnoreCase(
					Parameters.getString(Parameters.PROPERTY_BIG_FILE_DISK_FLAG)
			);
			
			RECENT_SIZE = 25;
			if (DISK_WRITE_TEST) {
				RECENT_SIZE = 5;
			}
		}
	}
	
	public RecordStore getRecordStore() {
		switch (type) {
		case VARIABLE_LENGTH:
		case VARIABLE_LENGTH_BASEFILE:
			return new RecordStoreVariableLength(dataSize, len, 0);
		case CHAR_LINE:
			return new RecordStoreCharLine(dataSize, layout.getMaximumRecordLength(), layout.getFontName());
		} 
		return new RecordStoreFixedLength(dataSize, len, 0);
	}
	
	
	public RecordStore getEmptyRecordStore() {
		switch (type) {
		case VARIABLE_LENGTH:
		case VARIABLE_LENGTH_BASEFILE:
			return new RecordStoreVariableLength(-1, len, 0);
		case CHAR_LINE:
			return new RecordStoreCharLine(-1, 1, layout.getFontName());
		} 
		return new RecordStoreFixedLength(-1, len, 0);
	}
	
	public void registerUse(FileChunkBase ch) {
		int ii = recent.size();
		if (ch.getFirstLine() > 0
		&& ( ii == 0
		  || (recent.get(ii - 1) != ch))) {
			toDiskList.remove(ch);
			recent.remove(ch);
			if (recent.size() >= RECENT_SIZE) {
				FileChunkBase fc = recent.get(0);
				fc.compress();
				registerDisk(fc);
			}
			
			recent.add(ch);
		}
	}
	
	public void registerCompress(FileChunkBase ch) {
		if (ch.getFirstLine() > 0) {
			registerDisk(ch);
		}
	}
	
	private void registerDisk(FileChunkBase ch) {
		Runtime rt = Runtime.getRuntime();
		long maxmemory = rt.maxMemory();
		long used = rt.totalMemory();
		long saved = 0;
		FileChunkBase cb;
	
		recent.remove(ch);
		toDiskList.remove(ch);
		
		//System.out.println("Register Disk: " + DISK_WRITE_TEST + " " + toDiskList.size());
		if ((used / maxmemory > 0.80) || (DISK_WRITE_TEST && toDiskList.size() > 2)) {
			for (int i = 0;  i < 3
							&& ((  toDiskList.size() > 0 
								&& (used - saved) / maxmemory > 0.85)
							  ||(DISK_WRITE_TEST && toDiskList.size() > 2));
			         i++) {
				cb = toDiskList.remove(0);
				
				if (cb != ch) {
					saved += cb.getSpaceUsed();
					cb.saveToDisk();
				}
			}
		}
		toDiskList.add(ch);
	}
	
	protected long saveToDisk(long pos, byte[] bytes, boolean compressed) throws IOException {
		int len = 0;
		int bsize = 2048;
		RandomAccessFile r = getOverflowFile();
		byte c = 1;
		if (! compressed) {
			 c = 0;
		}
		
		if (dataSize < 5000) {
			bsize = 512;
		}
	
		if (pos >= 0) {
			r.seek(pos);
			len = r.readInt();
			if (len < bytes.length + 9) {
				pos = -1;
			}
		}
		
		if (pos < 0) {
			len = ((bytes.length + 109) * 12) / (bsize * 10) +1;
			len = len * bsize;
			
			synchronized (this) {
				pos = filePos;
				filePos += len;
			}
		}
		synchronized (overflowRandomFile) {
			r.seek(pos);
			
			r.writeInt(len);
			r.writeInt(bytes.length);
			r.writeByte(c);
			r.write(bytes);
		}
		
//		System.out.println("Writing: " + pos + "\t" + len 
//				+ "\t" + bytes.length
//				+ "\t" + compressed
//				+ "\t" + c);

		if ((System.nanoTime() % 4) == 0) {
			System.gc();
		}
		return pos;
	}


	protected ByteArray readFromDisk(long pos) {
		try {
			byte[] bytes;
			//int len;
			byte c;
			
			synchronized (overflowRandomFile) {
				overflowRandomFile.seek(pos);
				overflowRandomFile.readInt();
				
				bytes = new byte[overflowRandomFile.readInt()];
				c = overflowRandomFile.readByte();
				overflowRandomFile.read(bytes);
			}
//			System.out.println("Reading: " + pos + "\t" + len 
//					+ "\t" + bytes.length + "\t" + (c == 1));

			return new ByteArray(bytes.length, bytes, c == 1);
		
		} catch (Exception e) {
			String m = "Can not Read block from disk at " + pos;
			Common.logMsg(m, e);

			throw new RuntimeException(m, e);
		}
	}

	protected byte[] readFromMainFile(long pos, int length) {
		byte[] bytes = new byte[length];
		
		try {
			if (mainFile == null) {
				mainFile = new RandomAccessFile(mainFileName, "rw");
			}
			synchronized (mainFile) {
				mainFile.seek(pos);

				mainFile.read(bytes);
			}
		} catch (IOException e) {
			String m = "Can not Read block from disk at " + pos;
			Common.logMsg(m, e);

			throw new RuntimeException(m, e);
		}
		return bytes;
	}
	
	public LayoutDetail getLayout() {
		return layout;
	}

	public void setLayout(LayoutDetail layout) {
		this.layout = layout;
	}

	public RandomAccessFile getOverflowFile() throws IOException {
		if (overflowRandomFile == null) {
			synchronized (this) {
			    DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");
			    
			    
			    overflowFile = File.createTempFile(
			    		"/~tmp_" 
						 + dateFormat.format(new Date()),
						".tmp~"
						);
			    overflowFile.deleteOnExit();
//			    System.out.println("~~File: " + f.getAbsolutePath()
//			    		+ " " + f.getCanonicalPath()
//			    		+ " " + f.getCanonicalFile()
//			    		+ " " + f.getName());

				overflowRandomFile = new RandomAccessFile(overflowFile, "rw");
			}
		}
		
		return overflowRandomFile;
	}
	
	public void setMainFileName(String mainFileName) {
		this.mainFileName = mainFileName;
	}

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
		
		if (mainFile != null) {
			try {
				mainFile.close();
			} catch (IOException e) {
			}
		}
	}

	public int getFixedLengthRecordsPerBlock() {
		return ((dataSize - 1) / len + 1);
	}
	
	public long getDiskSpaceUsed() {
		return filePos;
	}

	public void rename(String oldName, String newName) {
		try {
			if (mainFile != null 
			&&  mainFileName != null && mainFileName.equals(oldName)) {
				mainFile.close();
				mainFile = null;
				
				mainFileName = newName;
			}
			
			Parameters.renameFile(oldName, newName);
		} catch (Exception e) {
			Common.logMsg("Save Failed: File Rename Failed", e);
		}
	}
	
	public void setReading(boolean isReading) {
		this.isReading = isReading;
	}

	public boolean isOkToCompress() {
		boolean ret = true;
		
		if (! inCompressCheck) {
			double ratio = getSpaceUsedRatio();

			inCompressCheck = true;
			
			switch (Common.OPTIONS.compressOption.get()) {
			case ProgramOptions.COMPRESS_YES:					break;
			case ProgramOptions.COMPRESS_NO:	ret = false;	break;
			case ProgramOptions.COMPRESS_READ_FAST_CPU:
				ret = readingCheck(ratio, 0.35, 0.65);
			break;
			case ProgramOptions.COMPRESS_READ:
				ret = readingCheck(ratio, 0.70, 0.74);
			break;
			case ProgramOptions.COMPRESS_SPACE:
				ret = spaceCheck(ratio > 0.70, ratio, 0.74);
			break;
			}
			
			if (ratio > 0.8) {
				if (canDoGC) {
					System.gc();
				}
				canDoGC = false;
			} else {
				canDoGC = true;
			}
			inCompressCheck = false;
		}

		return ret;
	}
	
	private boolean readingCheck(double ratio, double checkRatio, double checkToDiskRatio) {
		boolean ret;
		if (isReading) {
			ret = spaceCheck(ratio > checkRatio, ratio, checkToDiskRatio);
		} else {
			ret = true;
		}
		
		return ret;
	}
	
	private boolean spaceCheck(boolean ret, double ratio, double checkToDiskRatio) {
		if (ret) {
			if (ratio > checkToDiskRatio) {	
				int i = 0;
				FileChunkBase ch;
				for (int j = 0; j < this.toDiskList.size(); j++) {
					ch = this.toDiskList.get(j);
					if (ch.compressed == null) {
						ch.compress();
						if (i++ >= PROCESSORS) {
							break;
						}
					}
				}
			}
		} else if (ratio > (checkToDiskRatio - 0.15)) {
			ret = (System.nanoTime() % REMAINDER1) == 0;
			System.out.print(" @ " + ret);
		} else if (ratio > 0.05) {
			ret = (System.nanoTime() % REMAINDER2) == 0;
			System.out.print(" . " + ret);
		}
		
		return ret;
	}

	public AbstractByteReader getByteReader() {
		return byteReader;
	}

	public void setByteReader(AbstractByteReader byteReader) {
		this.byteReader = byteReader;
	}

	public boolean isReading() {
		return isReading;
	}
	
	public static double getSpaceUsedRatio() {
		Runtime rt = Runtime.getRuntime();
		return ((double)rt.totalMemory()) / ((double) rt.maxMemory());
	}
}
