package net.sf.RecordEditor.re.file;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.Segment;
import javax.swing.undo.UndoableEdit;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.utils.fileStorage.DataStorePosition;
import net.sf.RecordEditor.utils.fileStorage.IDataStore;
import net.sf.RecordEditor.utils.fileStorage.IDataStorePosition;
import net.sf.RecordEditor.utils.fileStorage.ITextInterface;

/**
 * Implement a Document-Content class from a DataStoreTextContent class.
 * This class acts a layer between RecordEditor List-of-Lines model and
 * a Document Model used by  JTextArea etc.
 *
 * @author Bruce Martin
 *
 * License:  GPL 3 or latter
 */
public class DataStoreContent implements AbstractDocument.Content, TableModelListener {
	public static final int INSERT = 121;
	public static final int UPDATE = 122;
	public static final int DELETE = 123;

	private final FileView fileView;
	private final IDataStore<? extends AbstractLine> datastore;
	private final ITextInterface ti;

	private final ArrayList<RefDataStore> positions
						= new ArrayList<RefDataStore>(150);

	private long lastClearedTime = 0;
	private int checkMethod = 0;

	private DocumentUpdateListner documentUpdateListner = null;

	private boolean notifyDocument = true;

//	private final AbstractDocument.BranchElement lineElements = new AbstractDocument.BranchElement(null, null);

	public DataStoreContent(FileView file, ITextInterface ti) {
		this.datastore = ti.getDataStore();
		this.ti = ti;
		this.fileView = file;
		file.addTableModelListener(this);
	}

	/* (non-Javadoc)
	 * @see javax.swing.text.AbstractDocument.Content#createPosition(int)
	 */
	@Override
	public IDataStorePosition createPosition(int offset) throws BadLocationException {
//		IDataStorePosition pos = findPosition(offset, true);
//		if (pos != null) {
//			return pos;
//		}
		return register(ti.getTextPosition(offset));
	}

	private IDataStorePosition register(DataStorePosition pos) {

		if (pos == null) return pos;
		RefDataStore r = new RefDataStore(pos);
		IDataStorePosition ret = r.get();

		int firstKey = getValidPosIndex(0, 0, positions.size() - 1);
		int lastKey = getValidPosIndex(positions.size() - 1, 0, positions.size() - 1);
		int offset = pos.getOffset();
		if (positions.size() == 0 || (positions.get(lastKey).pos.getOffset() < offset)) {
//			System.out.print(" 6) " + lastKey + " " + positions.size());
			positions.add(r);
		} else if (positions.get(firstKey).pos.getOffset() >= offset) {
//			System.out.print(" 7) " + firstKey + " ~ " + positions.get(firstKey).pos.getOffset() + " " + offset);
			ret = addPosition(firstKey, r);
//		} else if (positions.size() < 6 ) {
//			boolean toAdd = true;
//
//			System.out.print("linear search ... " + positions.size());
//
//			for (int i = 1; i < positions.size(); i++) {
//				p = positions.get(i).pos;
//				if (p.getOffset() >= offset) {
//					System.out.print(" 8) " + i);
//					ret = addPosition(i, r);
//					toAdd = false;
//					System.out.print("." + i + '^');
//					break;
//				}
//			}
//
//			if (toAdd) {
//				System.out.print(" 5)");
//				positions.add(r);
//			}
		} else {
//			if (pos.lineNumber == 0) {
//				System.out.print(" ");
//			}
			int key = findPosByKey(offset, true);
			if (key < 0) {
				linearSearch(r, offset);
//			} else if (key >= positions.size()) {
//				System.out.println(" --- At end ");
//
//				ret = addPosition(idx, r);
			} else {
				ret = addPosition(key, r);
			}

//			System.out.println();
//			System.out.println("***** Positions: " + offset + "    " + key);
		}

//		if (positions.size() > 30 &&  (positions.size()  % 30 == 0) )  {
//			DataStorePosition pp;
//			System.out.println("     ------ Printing Positions ------");
//			System.out.println();
//
//			for (int i = 0; i < positions.size(); i++) {
//				pp = positions.get(i).pos;
//				if (pp != null) {
//					System.out.println("\t" + pp.lineNumber + "\t" + pp.getLineStart() + "\t" + pp.positionInLine  + "\t" + pp.getOffset());
//				}
//			}
//			System.out.println();
//		} else {
//			printPositions(offset);
//		}
		return ret;
	}

	private IDataStorePosition linearSearch(RefDataStore r, int offset) {
		IDataStorePosition pos = r.get();
		//System.out.println(" --- error insert ");
		int idx = getValidPosIndex(0, 0, positions.size()-1);

		while (idx < positions.size() && positions.get(idx).pos.getOffset() < offset) {
			idx = getValidPosIndex(idx++, idx, positions.size()-1);
		}

		pos = addPosition(idx, r);

		return pos;
	}

	private IDataStorePosition addPosition(int idx, RefDataStore r) {
		int where = r.pos.getOffset();
		IDataStorePosition p;
		if (idx >= positions.size()) {
//			System.out.print(" 1)");
			positions.add(r);
		} else if (positions.get(idx).pos.getOffset() == where) {
			p = positions.get(idx).get();
			if (p != null) {
//				System.out.print(" 2a)");
				return p;
			}
			positions.set(idx, r);
//			System.out.print(" 2b)");
		} else {
//			System.out.print(" 3)");

			positions.add(idx, r);
		}
		return r.get();
	}

	public IDataStorePosition createTempPosition(int offset)  {
		IDataStorePosition pos = findPosition(offset, true);
		if (pos != null) {
			return pos;
		}
		return ti.getTextPosition(offset);
	}

	public IDataStorePosition getPositionByLineNumber(int lineNo) {
		return getPositionByLineNumber(lineNo, true);
	}
	/**
	 * @param lineNo
	 * @param register wether to register the position
	 * @return
	 * @see net.sf.RecordEditor.utils.fileStorage.ITextInterface#getPositionByLineNumber(int)
	 */
	public IDataStorePosition getPositionByLineNumber(int lineNo, boolean register) {
		DataStorePosition linePosition = ti.getPositionByLineNumber(lineNo);
		IDataStorePosition ret = linePosition;
		if (register) {
			ret = register(linePosition);
		}
		return ret;
	}

	public IDataStorePosition getLinePositionByOffset(long offset) {
		DataStorePosition linePosition = ti.getTextPosition(offset);
		linePosition.positionInLine = 0;
		
		return register(linePosition);
	}


	private IDataStorePosition findPosition(int searchValue, boolean offset) {
		DataStorePosition chk;
		IDataStorePosition ret = null;
		IDataStorePosition chk1;
		int key = findPosByKey(searchValue, offset);
		if (key >= 0 && key < positions.size() && (chk1 = positions.get(key).get()) != null) {

			chk = positions.get(key).pos;
			if ((offset     && chk.getOffset() == searchValue)
			||  ((! offset) && chk.getLineNumberRE() == searchValue) && chk.getPositionInLineRE() == 0 ) {
				if (chk.getLineRE() == datastore.get(chk.getLineNumberRE())) {
					try {
						chk.updatePositionRE();
						ret = chk1;
					} catch (Exception e) {
						positions.remove(key);
					}
				}
			}
		}

		return ret;
	}


	private int  findPosByKey(int searchValue, boolean offset)  {
		synchronized (positions) {
			DataStorePosition p;
			int v;
			int high, low, 	mid;

			clearIfNecessary();
			low=0;


			if (positions.size() == 0) return 0;

			high=positions.size() - 1;

			if (calcCompare(searchValue, offset, positions.get(0).pos) >= 0) {
				return 0;
			}

			v = calcCompare(searchValue, offset, positions.get(high).pos);
			if (v == 0) {
				return high;
			} else if (v < 0){
				return high + 1;
			}

			while (high > low) {
				mid = (high + low) / 2;


				p = positions.get(mid).pos;

				v = calcCompare(searchValue, offset, p);

				if (high - 1 <= low) {
//					System.out.print("**" + v + " " + mid + " " + low + " " + high + " == " + searchValue
//							+ " " + p.getOffset() + " " + p.getLineNumber());
					if (v <= 0) {
						low = mid;
						if (v < 0) {
							v = calcCompare(searchValue, offset, positions.get(high).pos);
							if (v >= 0) {
								low = high;
//							} else {
//								low = high + 1;
							}
						}
					}
//					System.out.print(" ~~ " + low + " " + v + " ~~ " + calcCompare(searchValue, offset, positions.get(high).pos));
					break;
				} else if (v < 0) {
					low = mid;
				} else if (v > 0) {
					high = mid;
				} else {
					return mid;
				}
			}

//			if (low+2 >= positions.size()) {
//				System.out.print("..");
//				p = positions.get(low).pos;
//				if (calcCompare(searchValue, offset, p) < 0) {
//					low += 1;
//					if (calcCompare(searchValue, offset, positions.get(high).pos) < 0) {
//						low = high + 1;
//					}
//				}
//			}
//			System.out.print(" -- " + low);
			return low;
		}
	}

	private int calcCompare(int searchValue, boolean offset, DataStorePosition p) {
		int v;
		if (offset) {
			int x = p.getOffset();

			v = (x < searchValue) ? -1 : ((x == searchValue) ? 0 : 1);
					//Integer.compare(p.getOffset(), searchValue);
		} else {
			v = Integer.compare(p.lineNumber, searchValue);
			if (v == 0 && p.positionInLine > 0) {
				v = 1;
			}
		}

		return v;
	}


	private void clearIfNecessary() {

		if (positions.size() == 0) return;

		if (System.nanoTime() - lastClearedTime > 300000000
		|| (! positions.get(0).pos.isValidPositionRE())
		|| (! positions.get(positions.size() - 1).pos.isValidPositionRE())) {
			//System.out.println("%% " + System.nanoTime() + " %%");
			clearUnusedPositions();
			return;
		}

		if (positions.size() > 16) {
			int count = 0;

			switch (checkMethod) {

			case 0:
				int en = Math.max(0, positions.size() - 8);

				for (int i = positions.size() - 1; i >= en; i--) {
					if (positions.get(i).get() == null) {
						count +=1;
						positions.remove(i);
					}
				}
				break;
			case 1:
				for (int i = 7; i >= 0; i--) {
					if (positions.get(i).get() == null) {
						count +=1;
						positions.remove(i);
					}
				}

				break;
			case 2:
				int a = positions.size() / 8;
				for (int i = positions.size() - 1; i >= 0; i = i-a) {
					if (positions.get(i).get() == null) {
						count +=1;
						positions.remove(i);
					}
				}
			}

			if (count >= 3) {
				clearUnusedPositions();
			}

			checkMethod = (checkMethod + 1) % 3;
		}
	}


	public final void clearUnusedPositions() {

		for (int i =  positions.size() - 1; i >= 0; i--) {
			if (positions.get(i).get() == null || ! positions.get(i).pos.isValidPositionRE()) {
				positions.remove(i);
			}
		}
		lastClearedTime = System.nanoTime();
	}




	private int getValidPosIndex(int idx, int min, int max) {
		while ( idx >= min && idx < positions.size() && idx <= max && positions.get(idx).get() == null) {
			positions.remove(idx);
			if (idx >= positions.size() || idx > max) {
				idx -= 1;
			}
		}

		return idx;
	}


	/* (non-Javadoc)
	 * @see javax.swing.text.AbstractDocument.Content#length()
	 */
	@Override
	public int length() {
		return (int) Math.min(ti.length(), Integer.MAX_VALUE - 10000);
	}


	public int numberOfLines() {
		return datastore.size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.text.AbstractDocument.Content#insertString(int, java.lang.String)
	 */
	@Override
	public UndoableEdit insertString(int where, String str)
			throws BadLocationException {

		printLines();
		try {
			DataStorePosition start = ti.getTextPosition(where);
			//String lines[];  = str.split("\n");
			String tmpStr = str;
			int idx;
			ArrayList<String> lines = new ArrayList<String>();
			AbstractLine line1 = datastore.get(start.lineNumber);
			String s1 = line1.getFullLine();
			String s2 = "";


			if (start.positionInLine == 0) {
				s2 = s1;
				s1 = "";
			} else if (start.positionInLine < s1.length()) {
				s2 = s1.substring(start.positionInLine);
				s1 = s1.substring(0, start.positionInLine);
			}

			while ((idx = tmpStr.indexOf('\n')) >= 0) {
				lines.add(tmpStr.substring(0, idx));
				tmpStr = tmpStr.substring(idx + 1);
			}
			lines.add(tmpStr);

			int updateFrom = where;
			if (updateFrom == 0) {
				updateFrom += 1;
			}

			notifyDocument = false;

			if (lines.size() == 1) {
//				updatePositions(updateFrom, 0, start.lineNumber, line1, str.length());
				updateRemovedPositions(updateFrom, 0, line1, 0, start.lineNumber, str.length(), false);
				line1.setData(s1 + str + s2);
			} else {
				//TODO x
				//TODO x
				int key = findPosByKey(where, true);
				DataStorePosition p;
				ArrayList<DataStorePosition> list = new ArrayList<DataStorePosition>();

				for (int i = key; i < positions.size(); i++) {
					p = positions.get(i).pos;
					if (p.lineNumber > start.lineNumber) {
						break;
					} else if (p.positionInLine >= start.positionInLine
						&& (p.lineNumber > 0 || p.positionInLine > 0)) {
						list.add(p);
					}
				}

				line1.setData(s1 + lines.get(0));
				AbstractLine[] newLines = fileView.createLines(lines.size() - 1);
				for (int i = 1; i < lines.size() - 1; i++) {
					newLines[i-1].setData(lines.get(i));
				}
				newLines[newLines.length - 1].setData(lines.get(lines.size() - 1) + s2);

				//notifyDocument = false;
				idx = fileView.addLines(start.lineNumber+1, -1, newLines);
				//notifyDocument = true;

				if (idx >= 0 && list.size() > 0) {
					//idx = Math.min(fileView.getRowCount(), idx + newLines.length) - 1;
					AbstractLine l;
					l = fileView.getLine(idx);
//					idx = fileView.indexOf(l);
					int shift = lines.get(lines.size() - 1).length() - start.positionInLine;
					for (DataStorePosition pp : list) {
						pp.line = l;
						pp.lineNumber = idx;
						pp.setLookupRequiredRE();
						pp.positionInLine += shift;
					}
				}
			}

			printLines();
			//updateLinePos(start.lineNumber + lines.size(), 0);
			fileView.fireTableRowsUpdated(start.lineNumber, start.lineNumber + lines.size());
			if (str != null || ! "".equals(str)) {
				fileView.setChanged(true);
			}
		} finally {
			notifyDocument = true;
		}
		printLines();
		return null;
	}

	private void printLines() {

//		System.out.println();
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~ start: " + this.fileView.getRowCount());
//		for (int i = 0; i < this.fileView.getRowCount(); i++) {
//			System.out.println("\t" + fileView.getLine(i).getFullLine());
//		}
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~   end: ");
//		System.out.println();
	}

	/* (non-Javadoc)
	 * @see javax.swing.text.AbstractDocument.Content#remove(int, int)
	 */
	@Override
	public UndoableEdit remove(int where, int nitems)
			throws BadLocationException {
		DataStorePosition
				start = ti.getTextPosition(where),
				fin   = ti.getTextPosition(where + nitems);

		AbstractLine line1 = datastore.get(start.lineNumber);
		AbstractLine line2 = datastore.get(fin.lineNumber);
		String s1 = line1.getFullLine();
		String s2 = line2.getFullLine();
//		if (start.offset == 0) {
//			s1 = "";
//		} else if (start.offset > 0) {
//			s1 = s1.substring(0, start.offset);
//		}


		int st, en;

		if (start.positionInLine == 0 && fin.positionInLine < s2.length()) {
			st = start.lineNumber;
			en = fin.lineNumber - 1;

			//printPositions(0);
			updateRemovedPositions(where, nitems, line2, 0, fin.lineNumber,  -fin.positionInLine, true);
//			updatePositions(where, nitems, fin.lineNumber, line2,  -fin.positionInLine);
			//printPositions(0);
			line2.setData(s2.substring(fin.positionInLine));
			fileView.fireTableRowsUpdated(fin.lineNumber, fin.lineNumber);
		} else {
			if (fin.positionInLine >= s2.length()) {
				s2 = "";
			} else {
				s2 = s2.substring(fin.positionInLine);
			}

			st = start.lineNumber + 1;
			en = fin.lineNumber;

			//printPositions(121);
			updateRemovedPositions(where, nitems, line1, start.positionInLine, start.lineNumber, start.positionInLine - fin.positionInLine, true);
//			updatePositions(where, nitems, start.lineNumber, line1, start.positionInLine - fin.positionInLine);
			//printPositions(121);
			line1.setData(s1.substring(0, start.positionInLine) + s2);
			fileView.fireTableRowsUpdated(st, st);
		}

		if (st <= en) {
			int[] recNums = new int[en - st + 1];
			for (int i = st; i <= en; i++) {
				recNums[i - st] = i;
			}
			fileView.deleteLines(recNums);
		}

		if (nitems > 0) {
			fileView.setChanged(true);
		}
		return null;
	}


	private void updateRemovedPositions(int where, int nitems, AbstractLine line, int newOffset, int lineNo, int shift, boolean doPrev) {
		int key = findPosByKey(where + nitems, true);
		if ( key >= 0 ) {
			int start = key;
			DataStorePosition p;
	
			DataStorePosition pos = ti.getTextPosition(where + nitems);
			
			if (doPrev && key < positions.size() && positions.get(key).pos.getOffset() >= where) {
				start += 1;
			}
	
			for (int i = start; i < positions.size(); i++) {
				p = positions.get(i).pos;
				if (p.lineNumber > pos.lineNumber) {
					break;
				} else if (p.positionInLine >= pos.positionInLine) {
	//				System.out.println("= " + i + ")\t" + p.lineNumber + ",\t" + p.positionInLine + "\t ~~ \t "
	//						+ lineNum + "; " + (p.positionInLine + shift));
					p.lineNumber = lineNo;
					p.line = line;
					p.positionInLine += shift;
					p.setLookupRequiredRE();
				}
			}
	
			if (doPrev) {
				key = Math.min(key, positions.size() - 1);
				for (int i = key; i >= 0; i--) {
		
					if (positions.get(i).get() == null) {
						positions.remove(i);
					} else {
						p = positions.get(i).pos;
						if (p.getOffset() < where) {
							break;
						}
		//				System.out.println("+ " + i + ")\t" + p.lineNumber + ",\t" + p.positionInLine + "\t ~~ \t "
		//						+ lineNo + "; " + (newOffset));
						p.line = line;
						p.lineNumber = lineNo;
						p.positionInLine = newOffset;
						p.setLookupRequiredRE();
					}
				}
			}
	
			fileView.fireTableRowsUpdated(lineNo, lineNo);
		}
	}
	
	

//	private void updateRemovedPositions(int where, int nitems, AbstractLine line, int newOffset, int lineNo) {
//		int key = Math.min(findPosByKey(where + nitems, true), positions.size() - 1);
//		DataStorePosition p;
//
//		for (int i = key; i >= 0; i--) {
//
//			if (positions.get(i).get() == null) {
//				positions.remove(i);
//			} else {
//				p = positions.get(i).pos;
//				if (p.getOffset() < where) {
//					break;
//				}
////				System.out.println("+ " + i + ")\t" + p.lineNumber + ",\t" + p.positionInLine + "\t ~~ \t "
////						+ lineNo + "; " + (newOffset));
//				p.line = line;
//				p.lineNumber = lineNo;
//				p.positionInLine = newOffset;
//				p.setLookupRequired();
//			}
//		}
//		fileView.fireTableRowsUpdated(lineNo, lineNo);
//	}
//
//	private void updatePositions(int where, int nitems, int lineNum, AbstractLine line, int shift) {
//		int key = findPosByKey(where + nitems, true);
//		DataStorePosition p;
//		DataStorePosition pos = ti.getTextPosition(where + nitems);
//
//		for (int i = key; i < positions.size(); i++) {
//			p = positions.get(i).pos;
//			if (p.lineNumber > pos.lineNumber) {
//				break;
//			} else if (p.positionInLine >= pos.positionInLine) {
////				System.out.println("= " + i + ")\t" + p.lineNumber + ",\t" + p.positionInLine + "\t ~~ \t "
////						+ lineNum + "; " + (p.positionInLine + shift));
//				p.lineNumber = lineNum;
//				p.line = line;
//				p.positionInLine += shift;
//				p.setLookupRequired();
//			}
//		}
//	}


	/* (non-Javadoc)
	 * @see javax.swing.text.AbstractDocument.Content#getString(int, int)
	 */
	@Override
	public String getString(int where, int len) throws BadLocationException {
        Segment s = new Segment();
        getChars(where, len, s);
        return s.toString();
	}

	/* (non-Javadoc)
	 * @see javax.swing.text.AbstractDocument.Content#getChars(int, int, javax.swing.text.Segment)
	 */
	@Override
	public void getChars(int where, int len, Segment txt)
			throws BadLocationException {

		if (len <= 0) {
			txt.array = null;
			return;
		}
		DataStorePosition
		start = ti.getTextPosition(where),
		fin   = ti.getTextPosition(where + len);

		AbstractLine line1 = datastore.get(start.lineNumber);
		String s1 = line1.getFullLine() + "\n";

		if (fin == null) {
			txt.array = new char[0];
			txt.count = 0;
			txt.offset = 0;
			txt.offset = 0;
			return;
		}
		

//		System.out.println("## " + start.lineNumber + " "+ s1.length() + " "
//				+ " ~~ " + start.offset
//		        + Math.min(fin.offset, s1.length())
//				+ " " + len) ;

//		if (start.lineNumber == 1) {
//			System.out.println();
//		}

		if (start.lineNumber == fin.lineNumber) {
//			System.out.println(" -- 1 " + s1.length() + " " + fin.positionInLine);
			String s = s1.substring(start.positionInLine, Math.min(fin.positionInLine, s1.length()));
//			System.out.println(" -- 2 " + s1 + " " + s + " "+ s1.length());

			txt.array = s.toCharArray();
			txt.count = txt.array.length;
			txt.offset = 0;
			return;
		}

		AbstractLine line2 = datastore.get(fin.lineNumber);
		String s2 = line2.getFullLine() + '\n';
		if (start.positionInLine >= s1.length()) {
			s1 = "";
		} else if (start.positionInLine > 0) {
			s1 = s1.substring(start.positionInLine);
		}

		if (fin.positionInLine == 0) {
			s2 = "";
		} else if (fin.positionInLine < s2.length()) {
			s2 = s2.substring(0, fin.positionInLine);
//		} else if (fin.positionInLine > s2.length()) {
//			s2 += '\n';
		}

		StringBuffer b = new StringBuffer(s1);

		for (int i = start.lineNumber + 1; i < fin.lineNumber; i++) {
			b.append(datastore.get(i).getFullLine()).append('\n');
		}

		b.append(s2);

		txt.array = b.toString().toCharArray();
		txt.count = b.length();
		txt.offset = 0;
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
	 */
	@Override
	public void tableChanged(TableModelEvent event) {

		int type = UPDATE;
		switch (event.getType()) {
		case (TableModelEvent.INSERT):
//			printPositions(0);
			updateLinePos(event.getFirstRow()+1, event.getLastRow() - event.getFirstRow() + 1);
//			printPositions(0);
			type = INSERT;
		break;
//		case (TableModelEvent.UPDATE):
//			updateLinePos(event.getFirstRow(), 0);
//			break;
		case (TableModelEvent.DELETE):
//			printPositions(0);

			int en = updateLinePos(event.getLastRow() + 1, event.getFirstRow() - event.getLastRow() - 1);

			int newRow = event.getFirstRow();
			int posInLine = 0;
			AbstractLine l = null;

			type = DELETE;

			if (event.getLastRow() + 1 < datastore.size()) {
				l = datastore.get(event.getLastRow() + 1);
			} else if (event.getFirstRow() - 1 >= 0 && event.getFirstRow() - 1 < datastore.size()) {
				newRow -= 1;
				l = datastore.get(newRow);
				posInLine = l.getFullLine().length();
			} else if (datastore.size() > 0) {
				newRow = datastore.size() - 1;
				l = datastore.get(newRow);
				posInLine = l.getFullLine().length();
			}

			DataStorePosition p;
			for (int i = Math.min(en, positions.size() - 1); i >= 0; i--) {
				p = positions.get(i).pos;

				//System.out.println("\t" + i + " " + p.lineNumber + " " + p.getLineStart() + " " + p.positionInLine);
				if (p.lineNumber >= event.getFirstRow()) {
					if (p.lineNumber <= event.getLastRow()) {
						p.lineNumber = newRow;
						p.positionInLine = posInLine;
						p.setLookupRequiredRE();
						p.line = l;
					}
				} else {
					break;
				}
			}
//			printPositions(0);
		break;
		default:
			updateLinePos(event.getFirstRow(), 0);
		}

		if (documentUpdateListner != null && notifyDocument) {
			documentUpdateListner.fireUpdate(type, event.getFirstRow(), Math.min(fileView.getRowCount()-1, event.getLastRow()));
		}
	}

	private int updateLinePos(int firstLine, int amount) {
		DataStorePosition p;
//		System.out.println();
//		System.out.println("Adjust Lines: " + firstLine + "\t " + amount);
//		System.out.println();
		for (int i=positions.size() - 1; i>= 0; i--) {
			if (positions.get(i).get() == null) {
				positions.remove(i);
			} else {
				p = positions.get(i).pos;
	
				if (p.lineNumber < firstLine) {
					return i;
				}
	//			System.out.println("\t" + i + "}\t" + p.lineNumber + "\t" + p.positionInLine
	//					+ "\t~~\t" + (Math.max(p.lineNumber + amount, 0)));
				p.lineNumber = Math.max(p.lineNumber + amount, 0);
				p.setLookupRequiredRE();
			}
		}

		return -1;
	}


	public final boolean checkPositionSequence(String s)  {
		int last = -1;
		DataStorePosition p, lastP = null;
//		System.out.println();
//		System.out.println(positions.size() + " ** " );
		for (int i = 0; i < positions.size(); i++) {
			p = positions.get(i).pos;
			
			if (p.isValidPositionRE()) {
//				System.out.print(p.lineNumber + " " + p.positionInLine +  "\t");
				
				if (last > p.lineNumber * 1000 + p.positionInLine) {
					System.out.println();
					System.out.println();
					System.out.println(" ------   Error at: " + i + "\t Line Number: " + p.lineNumber + " " + p.positionInLine + " ~ " + last
							+ " > " + (p.lineNumber * 1000 + p.positionInLine));
					if (lastP != null) {
						System.out.println(" ------             \t Line Number: " + lastP.lineNumber + " " + lastP.positionInLine + " ~ " + last);
						
					}
					System.out.println(" ------ " + s);
					System.out.println();
					printPositions((int)p.lineStart - 50, (int)p.lineStart + 200);
					return false;
				}
				lastP = p;
				last = p.lineNumber * 1000 + p.positionInLine;
			}
		}
		return true;
	}

	public void printPositions(int offset) {
		if ( positions.size() > 0) {
			int last = -1;
			boolean error = false;
			DataStorePosition p;

			System.out.println("***** Positions: " + offset);
			for (int i = 0; i < positions.size(); i++) {
				String s = " ";
				if (positions.get(i).get() == null) {
					s = "^";
				}
				p = positions.get(i).pos;
				System.out.print("\t" + p.lineNumber + '~' + p.positionInLine  /* + " " + p.getOffset()*/ + s);
				if (last > p.lineNumber * 1000 + p.positionInLine) {
					System.out.print(" ");
					error = true;
				}
				last = p.lineNumber * 1000 + p.positionInLine;
			}
			System.out.println();
			if (error) {
				System.out.println(" ============== Error" );
				System.out.println();
			}
		}
	}

	

	public void printPositions(int start, int end) {
		if ( positions.size() > 0) {
			int last = -1;
			boolean error = false;
			DataStorePosition p;

			System.out.println("***** Positions: " + start);
			for (int i = 0; i < positions.size(); i++) {
				String s = " ";
				p = positions.get(i).pos;
				if (p.lineStart + p.positionInLine >= start && p.lineStart < end) {
					if (positions.get(i).get() == null) {
						s = "^";
					}
					System.out.print("\t" + p.lineNumber + '~' + p.positionInLine  /* + " " + p.getOffset()*/ + s);
					if (last > p.lineNumber * 1000 + p.positionInLine) {
						System.out.print(" ");
						error = true;
					}
					if (i % 25 == 0) System.out.println();
				}
				last = p.lineNumber * 1000 + p.positionInLine;
			}
			System.out.println();
			if (error) {
				System.out.println(" ============== Error" );
				System.out.println();
			}
		}
	}

	/**
	 * @param document the document to set
	 */
	public void setDocumentUpdateListner(DocumentUpdateListner document) {
		this.documentUpdateListner = document;
	}

	/**
	 * @return the fileView
	 */
	public FileView getFileView() {
		return fileView;
	}

	private static class RefDataStore extends WeakReference<IDataStorePosition> {
		private final DataStorePosition pos;
		//public RefDataStore next = null;
		public RefDataStore(DataStorePosition referent) {
			this(new DelegateDSPosition(referent));
		}

		public RefDataStore(DelegateDSPosition referent) {
			super(referent);
			pos = referent.pos;
		}
	}

	private static class DelegateDSPosition implements IDataStorePosition {
		private final DataStorePosition pos;

		public DelegateDSPosition(DataStorePosition pos) {
			this.pos = pos;
		}

		/**
		 * @return
		 * @see net.sf.RecordEditor.utils.fileStorage.IDataStorePosition#getOffset()
		 */
		public int getOffset() {
			return pos.getOffset();
		}

		/**
		 * @return
		 * @see net.sf.RecordEditor.utils.fileStorage.IDataStorePosition#getLineStartRE()
		 */
		public long getLineStartRE() {
			return pos.getLineStartRE();
		}

		/**
		 * @return
		 * @see net.sf.RecordEditor.utils.fileStorage.IDataStorePosition#getPositionInLineRE()
		 */
		public int getPositionInLineRE() {
			return pos.getPositionInLineRE();
		}

		/**
		 * @return
		 * @see net.sf.RecordEditor.utils.fileStorage.IDataStorePosition#getLineNumberRE()
		 */
		public int getLineNumberRE() {
			return pos.getLineNumberRE();
		}

		/**
		 * @return
		 * @see net.sf.RecordEditor.utils.fileStorage.IDataStorePosition#getLineRE()
		 */
		public AbstractLine getLineRE() {
			return pos.getLineRE();
		}
	}
}