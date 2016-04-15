package net.sf.RecordEditor.test.fileStore;

import net.sf.RecordEditor.utils.fileStorage.IRecordStore;
import net.sf.RecordEditor.utils.fileStorage.RecordStoreCharLine;
import net.sf.RecordEditor.utils.fileStorage.RecordStoreFixedLength;
import net.sf.RecordEditor.utils.fileStorage.RecordStoreVariableLength;
import junit.framework.TestCase;

public class RecordStoreAdd extends TestCase {


	public void testChar() {
		doTst("Char 1 ~", new IRecordStoreCreator() {
			@Override public IRecordStore create() {
				return new RecordStoreCharLine(100, 20, "");
			}
		});
		doTst("Char 2 ~", new IRecordStoreCreator() {
			@Override public IRecordStore create() {
				return new RecordStoreCharLine(8000, 20, "");
			}
		});
		doTst("Char 3 ~", new IRecordStoreCreator() {
			@Override public IRecordStore create() {
				return new RecordStoreCharLine(80000, 20, "");
			}
		});
	}

	
	public void testFixed() {
		doTst("Fixed 1 ~", new IRecordStoreCreator() {
			@Override public IRecordStore create() {
				return new RecordStoreFixedLength(100, 20, 0);
			}
		});
		doTst("Fixed 2 ~", new IRecordStoreCreator() {
			@Override public IRecordStore create() {
				return new RecordStoreFixedLength(8000, 20, 0);
			}
		});
		doTst("Fixed 3 ~", new IRecordStoreCreator() {
			@Override public IRecordStore create() {
				return new RecordStoreFixedLength(80000, 20, 0);
			}
		});
	}
	
	
	
	public void testVariable() {
		doTst("VB 1 ~", new IRecordStoreCreator() {
			@Override public IRecordStore create() {
				return new RecordStoreVariableLength(100, 20, 1, 0);
			}
		});
		doTst("VB 2 ~", new IRecordStoreCreator() {
			@Override public IRecordStore create() {
				return new RecordStoreVariableLength(8000, 20, 1, 0);
			}
		});
		doTst("VB 3 ~", new IRecordStoreCreator() {
			@Override public IRecordStore create() {
				return new RecordStoreVariableLength(80000, 20, 1, 0);
			}
		});
	}


	private void doTst(String msg, IRecordStoreCreator c) {
		int[] sizes = {0, 1, 2, 7, 8, 9, 100, 2000};
//		int[] dataSizes = {0, 1, 2, 7, 8, 9, 100, 2000};
		
		System.out.println(msg);
		for (int cs : sizes) {
			for (int ds : sizes) {
				doTst1size(msg, c, ds, cs);
			}
		}
	}

	private void doTst1size(String msg, IRecordStoreCreator c, int dataSize, int cpySize) {
		String m;
		IRecordStore r = load(c.create(), cpySize, " cpy");
		
		for (int i = 0; i <= dataSize; i++) {
			IRecordStore d = load(c.create(), dataSize, "");
			
//			if (dataSize == 100 && cpySize == 7 && i == 8) {
//				System.out.print('*');
//			}
			d.add(i, r);
			
			if (d instanceof RecordStoreCharLine) {
				RecordStoreCharLine cd = (RecordStoreCharLine) d;
				for (int j = 0; j < d.getRecordCount(); j++) {
					m = msg + " " + dataSize + "; " + cpySize + " | " + i + ", " + j + " " + (i + cpySize) + ". ";
//					if (dataSize == 100 && cpySize == 7 && i == 8 && j == 97) {
//						System.out.print('*');
//					}
					if (j < i) {
						assertEquals(m + "a}", lineVal(j, ""), new String(cd.getChar(j)));
					} else if (j < i + cpySize) {
						assertEquals(m + "b}", lineVal(j - i, " cpy"), new String(cd.getChar(j)));
					} else {
						assertEquals(m + "c}", lineVal(j - cpySize, ""), new String(cd.getChar(j)));
					}
				}
			} else {
				for (int j = 0; j < d.getRecordCount(); j++) {
					m = msg + " " + dataSize + "; " + cpySize + " | " + i + ", " + j + " " + (i + cpySize) + ". ";

					if (j < i) {
						assertEquals(m + "a}", lineVal(j, ""), new String(d.get(j)));
					} else if (j < i + cpySize) {
						assertEquals(m + "b}", lineVal(j - i, " cpy"), new String(d.get(j)));
					} else {
						assertEquals(m + "c}", lineVal(j - cpySize, ""), new String(d.get(j)));
					}
				}
			}
		}
	}
	

	private IRecordStore load(IRecordStore r, int size, String s) {
		
		if (r instanceof RecordStoreCharLine) {
			RecordStoreCharLine recordStoreCharLine = (RecordStoreCharLine)r;
			for (int i = 0; i < size; i++) {
				recordStoreCharLine.add(i, lineVal(i, s).toCharArray());
			}
		} else {
			for (int i = 0; i < size; i++) {
				r.add(i, lineVal(i, s).getBytes());
			}
		}
		
		return r;
	}

	private static String lineVal(int i, String s) {
		return ("line " + i + s + "                      ").substring(0, 20);
	}
	

	private interface IRecordStoreCreator {
		IRecordStore create(); 
	}
}
