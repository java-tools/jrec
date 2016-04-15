package net.sf.RecordEditor.test.fileStore;

import java.util.List;

import junit.framework.TestCase;
import net.sf.JRecord.Details.CharLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.RecordEditor.testcode.StdSchemas;
import net.sf.RecordEditor.utils.fileStorage.FileChunkBfFixedLength;
import net.sf.RecordEditor.utils.fileStorage.FileChunkBfVariableLength;
import net.sf.RecordEditor.utils.fileStorage.FileChunkCharLine;
import net.sf.RecordEditor.utils.fileStorage.FileDetails;
import net.sf.RecordEditor.utils.fileStorage.IChunkLengthChangedListner;
import net.sf.RecordEditor.utils.fileStorage.IChunkLine;
import net.sf.RecordEditor.utils.fileStorage.IFileChunk;
import net.sf.RecordEditor.utils.fileStorage.IRecordStore;

public class FileChunkSplit extends TestCase {


	private static final int SMALL_TEST_DATA_SIZE = 50;
	private static final int LARGE_TEST_DATA_SIZE = 200000;
	private LayoutDetail schema = StdSchemas.TWENTY_BYTE_RECORD_SCHEMA;
	
	private IChunkLengthChangedListner chgListner = new IChunkLengthChangedListner() {
		@Override public void blockChanged(IFileChunk ch) {
		}
	};
	
	
	public void testFixedLengthSplit() {
		System.out.println("Fixed Length");
		FcCreator fcCreator = new FcCreator() {
			public IFileChunk create() {
				return new FileChunkBfFixedLength(new FileDetails(schema, FileDetails.FIXED_LENGTH, schema.getMaximumRecordLength(), chgListner), 0, 0, 0);
			}
		};
		doTest(fcCreator);
	}

	
	public void testVariableLengthSplit() {
		System.out.println("Variable Length");
		FcCreator fcCreator = new FcCreator() {
			public IFileChunk create() {
				FileDetails details = new FileDetails(schema, FileDetails.VARIABLE_LENGTH, schema.getMaximumRecordLength(), chgListner);
				details.setReading(false);
				return new FileChunkBfVariableLength(details, 0, 0, 0);
			}
		};
		doTest(fcCreator);
	}

	
	public void testCharSplit() {
		System.out.println("Char");
		FcCreator fcCreator = new FcCreator() {
			public IFileChunk create() {
				return new FileChunkCharLine(new FileDetails(schema, FileDetails.CHAR_LINE, schema.getMaximumRecordLength(), chgListner), 0, 0);
			}
		};
		doTest(fcCreator);
	}

	private void doTest(FcCreator fcCreator) {
		
		tstSplit(fcCreator, SMALL_TEST_DATA_SIZE);
		
		tstSplit(fcCreator, LARGE_TEST_DATA_SIZE);
	}


	private void tstSplit(FcCreator c, int testSize) {
//		Common.OPTIONS.doCompress.set(false);
		IFileChunk r = load(c.create(), testSize);
		IFileChunk r1 = load(c.create(), testSize);
		
		List<IFileChunk> rr =  r.split();
		rr.add(0, r);
		check(r1, rr);
	}
	
	private void check(IFileChunk r,  
			List<IFileChunk> rr) {
		System.out.println("Num FileChunks: " + rr.size());
		int c = 0;
		
		for (int i = 0; i < rr.size(); i++) {
			c += rr.get(i).getCount(); 
		}
		
		assertEquals("Check Count", r.getCount(), c);
		
		c = 0;
		for (int i = 0; i < rr.size(); i++) {
			int recordCount = rr.get(i).getCount();
			for (int j = 0; j < recordCount; j++) {
				assertEquals("Check Line: " + i + ", " + j + ", " + c, lineVal(c + j), (rr.get(i).getLine(c + j)).getFullLine());
			}
			c += recordCount;
		}
	}
	
	private IFileChunk load(IFileChunk<? extends IChunkLine<IFileChunk>, ? extends IRecordStore> r, int testSize) {
		System.out.print("Starting load " + testSize);
		long c = System.currentTimeMillis();
		for (int i = 0; i < testSize; i++) {
			r.add(i, new CharLine(schema, lineVal(i)));
		}
		System.out.println("  Finished - " + ((System.currentTimeMillis() - c) / 1000));
		
		return r;
	}

	private static String lineVal(int i) {
		return ("line " + i + "                      ").substring(0, 20);
	}
	

	private interface FcCreator {
		public IFileChunk create();
	}
}
