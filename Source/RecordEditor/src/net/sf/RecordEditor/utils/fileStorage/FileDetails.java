package net.sf.RecordEditor.utils.fileStorage;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import net.sf.JRecord.ByteIO.IByteReader;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.GcManager;
import net.sf.RecordEditor.utils.fileStorage.randomFile.IOverflowFile;
import net.sf.RecordEditor.utils.fileStorage.randomFile.MockOverflow;
import net.sf.RecordEditor.utils.fileStorage.randomFile.OverflowFile;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.params.ProgramOptions;

public class FileDetails {

	public static final int NORMAL_CHECK = 1;
	public static final int AGGRESSIVE_CHECK = 2;
	
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
	public final int type, recordLength, recordOverhead;

	private LayoutDetail layout;

	@SuppressWarnings("rawtypes")
	private ArrayList<FileChunkBase> recent = new ArrayList<FileChunkBase>(25);

	@SuppressWarnings({"rawtypes" })
	private ArrayList<FileChunkBase> toDiskList = new ArrayList<FileChunkBase>(200);

	//private String overflowFileName = "";
//	private File overflowFile = null;
//	private RandomAccessFile overflowRandomFile = null;
	
	private IOverflowFile overflow;
	private long filePos = 0;

	private String mainFileName = "";
	private RandomAccessFile mainFile = null;
	private boolean inCompressCheck = false;
	private boolean isReading       = true;

	private IByteReader byteReader = null;
	private final WeakReference<IChunkLengthChangedListner> chunkLengthChangeListner;

	public FileDetails(LayoutDetail recordLayout, int type, int recLength, IChunkLengthChangedListner chunkLengthChangeListner) {
		this(recordLayout, type, recLength, Common.OPTIONS.chunkSize.get(), chunkLengthChangeListner);
	}

	public FileDetails(LayoutDetail recordLayout, int type, int len, int chunkSize, IChunkLengthChangedListner chunkLengthChangeListner) {
		this.type = type;
		this.recordLength = len;
		this.layout = recordLayout;
		this.chunkLengthChangeListner = new WeakReference<IChunkLengthChangedListner>(chunkLengthChangeListner);

		dataSize = chunkSize;
		
		switch (type) {
		case VARIABLE_LENGTH:
		case VARIABLE_LENGTH_BASEFILE:
			recordOverhead = calcBytes(recordLength);
			break;
		case CHAR_LINE:
			recordOverhead = 1;
			break;
		default:
			recordOverhead = 0;
		}

		init();
	}
	
	private static int calcBytes(int len) {
		int ret = 1;
		
		if (len > 64500) {
			ret = 3;
		} else if (len > 200) {
			ret = 2;
		}
		
		return ret;
	}


	private void init() {
		String testParam = Parameters.getString(Parameters.PROPERTY_BIG_FILE_DISK_FLAG);
		
		boolean mock =  "Y".equalsIgnoreCase(testParam);

		if (RECENT_SIZE < 0) {

			DISK_WRITE_TEST = mock || "T".equalsIgnoreCase(testParam);

			RECENT_SIZE = 25;
			if (DISK_WRITE_TEST) {
				RECENT_SIZE = 5;
			}
		}
		
		if (mock) {
			overflow = new MockOverflow();
		} else {
			overflow = new OverflowFile();
		}
	}

	public IRecordStore newRecordStore() {
		switch (type) {
		case VARIABLE_LENGTH:
		case VARIABLE_LENGTH_BASEFILE:
			return new RecordStoreVariableLength(dataSize, recordLength, recordOverhead, 0);
		case CHAR_LINE:
			return new RecordStoreCharLine(dataSize, layout.getMaximumRecordLength(), layout.getFontName());
		}
		return new RecordStoreFixedLength(dataSize, recordLength, 0);
	}


	public IRecordStore getEmptyRecordStore() {
		switch (type) {
		case VARIABLE_LENGTH:
		case VARIABLE_LENGTH_BASEFILE:
			return new RecordStoreVariableLength(-1, recordLength, recordOverhead, 0);
		case CHAR_LINE:
			return new RecordStoreCharLine(-1, 1, layout.getFontName());
		} 
		return new RecordStoreFixedLength(-1, recordLength, 0);
	}
	
	public boolean isDocumentViewAvailable() {
		switch (type) {
		case VARIABLE_LENGTH:
		case VARIABLE_LENGTH_BASEFILE:
		case CHAR_LINE:
			return true;
		} 
		return false;
	}

	@SuppressWarnings("rawtypes")
	public void registerUse(FileChunkBase ch) {
		int ii = recent.size();
		if (ch.getFirstLine() > 0
		&& ( ii == 0
		  || (recent.get(ii - 1) != ch))) {
			toDiskList.remove(ch);
//			System.out.print("\t" + ii + " " + ch.hashCode() + " " + this.hashCode());
//			if (ii == 4) {
//				System.out.print('.');
//			}
			recent.remove(ch);
			while (recent.size() >= RECENT_SIZE) {
				FileChunkBase fc = recent.get(0);
				recent.remove(0);
				fc.compress();
				registerDisk(fc);
				//recent.remove(0);
			}

			recent.add(ch);
			if (toDiskList.size() >= 7) {
				toDiskList.get(toDiskList.size() - 7).clearOldLines();
			}
		}
	}

	
	public void registerCompress(@SuppressWarnings("rawtypes") FileChunkBase ch) {
		if (ch.getFirstLine() > 0) {
			registerDisk(ch);
		}
		recent.remove(ch);
	}

	@SuppressWarnings("rawtypes")
	private void registerDisk(FileChunkBase ch) {
		
		if ("N".equalsIgnoreCase(Parameters.getString(Parameters.PROPERTY_USE_OVERFLOW_FILE))) {
			return;
		}
		Runtime rt = Runtime.getRuntime();
		long maxmemory = rt.maxMemory();
		long used = rt.totalMemory();
		long saved = 0;
		FileChunkBase cb;
	
//		if (recent.size() == 4 || recent.size() == 5) {
//			System.out.print("\t>" + recent.size() + " " + ch.hashCode() + " " + this.hashCode());
//			System.out.print('*');
//		}

//		recent.remove(ch);
		toDiskList.remove(ch);
		if (toDiskList.size() >= 5) {
			toDiskList.get(toDiskList.size() - 5).clearOldLines();
		}

		//System.out.println("Register Disk: " + DISK_WRITE_TEST + " " + toDiskList.size());
		if ((used / maxmemory > 0.82) || (DISK_WRITE_TEST && toDiskList.size() > 2)) {
			GcManager.doGcIfNeccessary();
		}
		if ((used / maxmemory > 0.85) || (DISK_WRITE_TEST && toDiskList.size() > 2)) {
			for (int i = 0;  i < 3
							&& ((  toDiskList.size() > 0
								&& (used - saved) / maxmemory > 0.85)
							  ||(DISK_WRITE_TEST && toDiskList.size() > 2));
			         i++) {
				cb = toDiskList.remove(0);

				if (cb != ch) {
					saved += cb.getSpaceUsed();
					cb.saveToDisk();
					recent.remove(ch);
				}
			}
		}
		toDiskList.add(ch);
	}

	protected long saveToDisk(long pos, byte[] bytes, boolean compressed) throws IOException {
		int len = 0;
		int bsize = 2048;
		//RandomAccessFile r = getOverflowFile();
		
		byte c = 1;
		if (! compressed) {
			 c = 0;
		}

		if (dataSize < 5000) {
			bsize = 512;
		}

		if (pos >= 0) {
			DataInput r = overflow.getReader(pos);
			len = r.readInt();
			overflow.free(r);
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
		synchronized (overflow) {
			DataOutput w = overflow.getWriter(pos, len);

			w.writeInt(len);
			w.writeInt(bytes.length);
			w.writeByte(c);
			w.write(bytes);
			
			overflow.free(w);
		}

//		System.out.println("Writing: " + pos + "\t" + len
//				+ "\t" + bytes.length
//				+ "\t" + compressed
//				+ "\t" + c);

		if ((System.nanoTime() % 4) == 0) {
			GcManager.doGcIfNeccessary();;
		}
		return pos;
	}


	protected ByteArray readFromDisk(long pos) {
		try {
			byte[] bytes;
			//int len;
			byte c;

			synchronized (overflow) {
				DataInput in = overflow.getReader(pos);
				in.readInt();

				bytes = new byte[in.readInt()];
				c = in.readByte();
				in.readFully(bytes);
				overflow.free(in);
			}
//			System.out.println("Reading: " + pos + "\t" + len
//					+ "\t" + bytes.length + "\t" + (c == 1));

			return new ByteArray(bytes.length, bytes, c == 1);

		} catch (Exception e) {
			String m = LangConversion.convert("Can not Read block from disk at:") + " " + pos;
			Common.logMsgRaw(m, e);

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
			String m = LangConversion.convert("Can not Read block from File at:") + " " + pos;
			Common.logMsgRaw(m, e);

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



	public void setMainFileName(String mainFileName) {
		this.mainFileName = mainFileName;
	}

	public void clear() {
		recent.clear();
		toDiskList.clear();
		overflow.clear();

		if (mainFile != null) {
			try {
				mainFile.close();
			} catch (IOException e) {
			}
		}
	}

	public final int getFixedLengthRecordsPerBlock() {
		return ((dataSize - 1) / recordLength + 1);
	}

	public final long getDiskSpaceUsed() {
		return filePos;
	}
	
	public final int getBlockType() {
		int blockType = this.type;
		switch (blockType) {
		case FIXED_LENGTH_BASEFILE:		blockType = FIXED_LENGTH;		break;
		case VARIABLE_LENGTH_BASEFILE:	blockType = VARIABLE_LENGTH;	break;
		}
		return blockType;
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

	public boolean isOkToCompress(int checkType) {
		boolean ret = ! inCompressCheck;

		if (ret) {
			double ratio = GcManager.getSpaceUsedRatio();

			inCompressCheck = true;

			if (checkType == AGGRESSIVE_CHECK) {
				ret = ratio > 0.20 && Runtime.getRuntime().totalMemory() > 100000000;
			} else {
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
			}
			GcManager.doGcIfNeccessarySupplyRatio(ratio);
			
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
				int i = 1;
				@SuppressWarnings("rawtypes")
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
			//System.out.print(" @ " + ret);
		} else if (ratio > 0.05) {
			ret = (System.nanoTime() % REMAINDER2) == 0;
			//System.out.print(" . " + ret);
		}

		return ret;
	}

	public IByteReader getByteReader() {
		return byteReader;
	}

	public void setByteReader(IByteReader byteReader) {
		this.byteReader = byteReader;
	}

	/**
	 * @return the chunkLengthChangeListner
	 */
	public final IChunkLengthChangedListner getChunkLengthChangeListner() {
		return chunkLengthChangeListner.get();
	}

	public boolean isReading() {
		return isReading;
	}
	
	public boolean isCompatible(FileDetails cmp) {
		return cmp.getBlockType() == getBlockType()
			&& cmp.recordOverhead == recordOverhead;
	}
}
