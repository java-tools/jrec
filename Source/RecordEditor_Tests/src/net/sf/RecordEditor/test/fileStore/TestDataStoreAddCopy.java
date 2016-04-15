package net.sf.RecordEditor.test.fileStore;

import junit.framework.TestCase;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.CharLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.Line;
import net.sf.RecordEditor.testcode.StdSchemas;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.fileStorage.DataStoreLarge;
import net.sf.RecordEditor.utils.fileStorage.DataStoreStd;
import net.sf.RecordEditor.utils.fileStorage.FileDetails;
import net.sf.RecordEditor.utils.fileStorage.IDataStore;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.swing.common.EmptyProgressDisplay;
import net.sf.RecordEditor.utils.swing.common.IProgressDisplay;


public class TestDataStoreAddCopy  {

	public static final int CREATE_DATASTORE = 1;
	public static final int CREATE_COPYSTORE = 2;
	
	public static final int STANDARD_STORAGE = -11;
	public static final int LARGE_VB = -12;

	private static LayoutDetail schema = StdSchemas.TWENTY_BYTE_RECORD_SCHEMA;
	private static LayoutDetail largeSchema = StdSchemas.LARGE_RECORD_SCHEMA;
	

	
	private IProgressDisplay progressDisplay = new EmptyProgressDisplay();



	public void doTst(String msg, boolean large, IDataStoreCreator c) {
		int[] sizes = {0, 1, 2, 7, 8, 9, 15, 16, 17, 50, 70, 170, 2000};
//		int[] dataSizes = {0, 1, 2, 7, 8, 9, 100, 2000};
		
		Parameters.setSavePropertyChanges(false);
		Common.OPTIONS.doCompress.set(false);		
		if (large) {
			Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
		} else {
			Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		}
		System.out.print(msg);
		for (int cs : sizes) {
			System.out.println();
			System.out.print("\t " + msg + " ~ " + cs + " - ");
			for (int ds : sizes) {
				doTst1size(msg, c, ds, cs);
			}
		}
		System.out.println();
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}

	private void doTst1size(String msg, IDataStoreCreator c, int dataSize, int cpySize) {
		String m;
		IDataStore<AbstractLine> r = load(c.create(CREATE_COPYSTORE), cpySize, " cpy");
		
		System.out.print("\t" + dataSize);
		
		for (int i = 0; i <= dataSize; i++) {
			if (i < 350 || (i > 800 && i < 1200) || i > 1750 ) {
				IDataStore<AbstractLine> d = load(c.create(CREATE_DATASTORE), dataSize, "");
				
				if (dataSize == 1 && cpySize == 0 && i % 200 == 9) {
					System.out.print("\t*" + i);
				}
				d.addCopyRE(i, r, progressDisplay);
				
	
				for (int j = 0; j < d.size(); j++) {
					m = msg + " " + dataSize + "; " + cpySize + " | " + i + ", " + j + " " + (i + cpySize) + ". ";
					String val = d.get(j).getFullLine();
	
					if (j < i) {
						String lineVal = lineVal(j, "");
						//System.out.print("\t==>" + val.length() + " " + lineVal.length() + ":" + val + lineVal);
						if (! val.equals( lineVal)) {
							for (int k = 0; k < d.size(); k++) {
								System.out.print("\t" + d.get(k).getFullLine());
							}
						}
						TestCase.assertEquals(m + "a}", lineVal, val);
					} else if (j < i + cpySize) {
						if (! val.equals( lineVal(j - i, " cpy"))) {
							for (int k = 0; k < d.size(); k++) {
								System.out.print("\t" + d.get(k).getFullLine());
							}
						}
						TestCase.assertEquals(m + "b}", lineVal(j - i, " cpy"), val);
					} else {
						String lineVal =  lineVal(j - cpySize, "");
						//System.out.print("\t==>" + val.length() + " " + lineVal.length() + ":" + val + lineVal);
						if (! val.equals( lineVal)) {
							for (int k = 0; k < d.size(); k++) {
								System.out.print("\t" + d.get(k).getFullLine());
							}
						}
						TestCase.assertEquals(m + "c}", lineVal, val);
					}
				}
			}
		}
	}
	

	public static IDataStore<AbstractLine> load(IDataStore<AbstractLine> r, int size, String s) {
		
		if (r instanceof DataStoreLarge && ((DataStoreLarge) r).getFileDetails().type == FileDetails.CHAR_LINE) {
			for (int i = 0; i < size; i++) {
				r.add(i, new CharLine(schema , lineVal(i, s)));
			}
		} else {
			for (int i = 0; i < size; i++) {
				r.add(i, new Line(schema , lineVal(i, s)));
			}
		}
		
		return r;
	}

	private static String lineVal(int i, String s) {
		return ("line " + i + s + "                      ").substring(0, 20);
	}
	
	
	public static class DataStoreCreator implements IDataStoreCreator {
		private final int storageType;
		
		public DataStoreCreator(int storageType) {
			this.storageType = storageType;
		}

		@Override public IDataStore create(int type) {
			return createStore(storageType);
		}
	}
	
	
	public static class DataStoreCreator2 implements IDataStoreCreator {
		private final int cpyStorageType, dataStorageType;
		
		public DataStoreCreator2(int cpyStorageType, int dataStorageType) {
			this.cpyStorageType = cpyStorageType;
			this.dataStorageType = dataStorageType;
		}

		@Override public IDataStore create(int type) {
			if (type == TestDataStoreAddCopy.CREATE_COPYSTORE) {
				return createStore(cpyStorageType);
			}

			return createStore(dataStorageType);
		}
	}

	private static IDataStore createStore(int storageType) {
		switch (storageType) {
		case STANDARD_STORAGE:		return DataStoreStd.newStore(schema);	
		case LARGE_VB:				return new DataStoreLarge(largeSchema, FileDetails.VARIABLE_LENGTH, largeSchema.getMaximumRecordLength());
		}
		return new DataStoreLarge(schema, storageType, schema.getMaximumRecordLength());
		
	}

	public interface IDataStoreCreator {
		IDataStore<AbstractLine> create(int type); 
	}
}
