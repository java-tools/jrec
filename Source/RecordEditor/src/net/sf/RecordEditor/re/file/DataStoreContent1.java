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
import net.sf.RecordEditor.utils.fileStorage.IDataStoreText;
import net.sf.RecordEditor.utils.fileStorage.IDataStorePosition;


public class DataStoreContent1 implements AbstractDocument.Content, TableModelListener {

	private final FileView file;
	private final IDataStoreText<? extends AbstractLine> datastore;

	private final ArrayList<WeakReference<DataStorePosition>> positions
						= new ArrayList<WeakReference<DataStorePosition>>(50);

//	private final AbstractDocument.BranchElement lineElements = new AbstractDocument.BranchElement(null, null);

	public DataStoreContent1(FileView file, IDataStoreText<? extends AbstractLine> ds) {
		this.datastore = ds;
		this.file = file;
		file.addTableModelListener(this);
	}

	/* (non-Javadoc)
	 * @see javax.swing.text.AbstractDocument.Content#createPosition(int)
	 */
	@Override
	public IDataStorePosition createPosition(int offset) throws BadLocationException {
		DataStorePosition pos = findPosition(offset, true);
		if (pos != null) {
			return pos;
		}
		return register(datastore.getPosition(offset));
	}

	private DataStorePosition register(DataStorePosition pos) {
		WeakReference<DataStorePosition> r = new WeakReference<DataStorePosition>(pos);
		IDataStorePosition p;
		int firstKey = getValidPosIndex(0, 0, positions.size() - 1);
		int lastKey = getValidPosIndex(positions.size() - 1, 0, positions.size() - 1);
		int offset = pos.getOffset();
		if (positions.size() == 0 || ((p = positions.get(lastKey).get()) != null && p.getOffset() < offset)) {
			System.out.print(" 6)");
			positions.add(r);
		} else if ((p = positions.get(firstKey).get()) != null && p.getOffset() > offset) {
			System.out.print(" 7)");
			positions.add(0, r);
		} else if (positions.size() < 6 ) {
			boolean toAdd = true;

			System.out.print("linear search ... " + positions.size());

			for (int i = 1; i < positions.size(); i++) {
				p = positions.get(i).get();
				if (p != null && p.getOffset() >= offset) {
					pos = addPosition(i, r);
					toAdd = false;
					System.out.print("." + i + '^');
					break;
				}
			}

			if (toAdd) {
				System.out.print(" 5)");
				positions.add(r);
			}
		} else {
			if (pos.lineNumber == 0) {
				System.out.print(" ");
			}
			int key = findPosByKey(offset, true);
			if (key < 0) {
				linearSearch(r, offset);
			} else if (key >= positions.size()) {
				System.out.println(" --- At end ");
				int idx = getValidPosIndex(positions.size()-1, 0, positions.size()-1);

//				while (positions.get(idx).get().getOffset() > offset && idx >= 0) {
//					idx = getValidPosIndex(idx--, idx, positions.size()-1);
//				}
//
//				if (positions.get(idx).get().getOffset() < offset)

				pos = addPosition(idx, r);
			} else {
				pos = addPosition(key, r);
			}

//			System.out.println();
//			System.out.println("***** Positions: " + offset + "    " + key);
		}

		if (positions.size() > 30 &&  (positions.size()  % 30 == 0) )  {
			DataStorePosition pp;
			System.out.println("     ------ Printing Positions ------");
			System.out.println();

			for (int i = 0; i < positions.size(); i++) {
				pp = positions.get(i).get();
				if (pp != null) {
					System.out.println("\t" + pp.lineNumber + "\t" + pp.getLineStart() + "\t" + pp.positionInLine  + "\t" + pp.getOffset());
				}
			}
			System.out.println();
		} else {
			int last = -1;
			System.out.println("***** Positions: " + offset);
			for (int i = 0; i < positions.size(); i++) {
				p = positions.get(i).get();
				if (p != null) {
					System.out.print("\t" + p.getOffset());
					if (last == p.getOffset()) {
						System.out.print(" ");
					}
					last = p.getOffset();
				}
			}
			System.out.println();
		}
		return pos;
	}

	private IDataStorePosition linearSearch(WeakReference<DataStorePosition> r, int offset) {
		IDataStorePosition pos = r.get();
		System.out.println(" --- error insert ");
		int idx = getValidPosIndex(0, 0, positions.size()-1);

		while (idx < positions.size() && positions.get(idx).get().getOffset() < offset) {
			idx = getValidPosIndex(idx++, idx, positions.size()-1);
		}

		pos = addPosition(idx, r);

		return pos;
	}

	private DataStorePosition addPosition(int idx, WeakReference<DataStorePosition> r) {
		int where = r.get().getOffset();
		DataStorePosition p;
		if (idx >= positions.size()) {
			System.out.print(" 1)");
			positions.add(r);
		} else if ((p = positions.get(idx).get()) != null && p.getOffset() == where) {
			System.out.print(" 2)");
			return p;
		} else {
			System.out.print(" 3)");

			positions.add(idx, r);
		}
		return r.get();
	}

	public IDataStorePosition createTempPosition(int offset) throws BadLocationException {
		IDataStorePosition pos = findPosition(offset, true);
		if (pos != null) {
			return pos;
		}
		return datastore.getPosition(offset);
	}

	/**
	 * @param lineNo
	 * @return
	 * @see net.sf.RecordEditor.utils.fileStorage.IDataStoreText#getLinePosition(int)
	 */
	public IDataStorePosition getLinePosition(int lineNo) {
		IDataStorePosition pos = findPosition(lineNo, false);
		if (pos != null) {
			return pos;
		}
		return register(datastore.getLinePosition(lineNo));
	}


	private DataStorePosition findPosition(int searchValue, boolean offset) {
		DataStorePosition chk,  ret = null;
		int key = findPosByKey(searchValue, offset);
		if (key >= 0 && key < positions.size() && (chk = getStorePositionByIndex(key)) != null) {
			if ((offset     && chk.getOffset() == searchValue)
			||  ((! offset) && chk.lineNumber == searchValue) && chk.positionInLine == 0 ) {
				if (chk.line == datastore.get(chk.lineNumber)) {
					try {
						datastore.updatePosition(chk);
						ret = chk;
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
			low=getValidPosIndex(0, 0, positions.size() - 1);


			if (positions.size() == 0) return 0;

			high=positions.size();

			while (high > low) {
				high = getValidPosIndex(high, low, positions.size() - 1);
				mid = getValidPosIndex((high + low) / 2, low, high);

				if (mid >= positions.size() -1) {
					return low;
				}
				p = positions.get(mid).get();
				if (mid < low || p == null) {
					return Math.max(0,  mid);
				}

				v = calcCompare(searchValue, offset, p);

				if (high - 1 <= low) {
					System.out.print("**" + v + " ");
					if (v < 0) {
						low += 1;
					}
					break;
				} else if (v < 0) {
					low = mid;
				} else if (v > 0) {
					high = mid;
				} else {
					return mid;
				}
			}

			if (low+1 == positions.size()) {
				System.out.print("..");
				p = positions.get(low).get();
				if (p != null && calcCompare(searchValue, offset, p) > 0) {
					low += 1;
				}
			}
			return low;
		}
	}

	private int calcCompare(int searchValue, boolean offset, DataStorePosition p) {
		int v;
		if (offset) {
			v = Integer.compare(p.getOffset(), searchValue);
		} else {
			v = Integer.compare(p.lineNumber, searchValue);
			if (v == 0 && p.positionInLine > 0) {
				v = 1;
			}
		}

		return v;
	}


	private void clearIfNecessary() {
		int en = Math.max(0, positions.size() - 8);
		int count = 0;

		for (int i = positions.size() - 1; i >= en; i--) {
			if (getStorePositionByIndex(i) == null) {
				count +=1;
			}
		}

		if (count >= 3) {
			for (int i = Math.min(en, positions.size() - 1); i >= 0; i--) {
				getStorePositionByIndex(i);
			}
		}
	}




	private DataStorePosition getValidPosition(int idx) {
		DataStorePosition ret = null;
		getValidPosIndex(idx, idx, positions.size() - 1);

		if (idx < positions.size()) {
			ret = positions.get(idx).get();
		}

		return ret;
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
		return datastore.length();
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
		DataStorePosition start = datastore.getPosition(where);
		String lines[] = str.split("\n");
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

		if (lines.length == 1) {
			line1.setData(s1 + str + s2);
			updatePositions(where, 0, str.length());
		} else {
			//TODO x
			//TODO x
			int key = findPosByKey(where, true);
			DataStorePosition p;
			ArrayList<DataStorePosition> list = new ArrayList<DataStorePosition>();

			for (int i = key; i < positions.size(); i++) {
				p = getValidPosition(i);
				if (p == null) {

				} else if (p.lineNumber > start.lineNumber) {
					break;
				} else if (p.positionInLine >= start.positionInLine) {
					list.add(p);
				}
			}

			line1.setData(s1 + lines[0]);
			for (int i = 1; i < lines.length - 1; i++) {
				addLine(start.lineNumber + i - 1, lines[i]);
			}
			int idx = addLine(start.lineNumber + lines.length - 1, lines[lines.length - 1] + s2);

			if (idx >= 0) {
				AbstractLine l = file.getLine(idx);
				int shift = lines[lines.length - 1].length() - start.positionInLine;
				for (DataStorePosition pp : list) {
					pp.line = l;
					pp.lineNumber = idx;
					pp.setLookupRequired();
					pp.positionInLine += shift;
				}
			}
		}
		return null;
	}

	private int addLine(int pos, String data) {

		int idx = file.newLine(pos, 1);
		if (idx >= 0) {
			file.getLine(idx).setData(data);
		}

		return idx;
	}

	/* (non-Javadoc)
	 * @see javax.swing.text.AbstractDocument.Content#remove(int, int)
	 */
	@Override
	public UndoableEdit remove(int where, int nitems)
			throws BadLocationException {
		DataStorePosition
				start = datastore.getPosition(where),
				fin   = datastore.getPosition(where + nitems);

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
			line2.setData(s2.substring(fin.positionInLine));

			updatePositions(where, nitems,  -fin.positionInLine);
			updateRemovedPositions(where, nitems, line2, 0, fin.lineNumber);
		} else {
			if (fin.positionInLine >= s2.length()) {
				s2 = "";
			} else {
				s2 = s2.substring(fin.positionInLine);
			}

			st = start.lineNumber + 1;
			en = fin.lineNumber;
			line1.setData(s1.substring(0, start.positionInLine) + s2);

			updatePositions(where, nitems, start.positionInLine - fin.positionInLine);
			updateRemovedPositions(where, nitems, line1, start.positionInLine, start.lineNumber);
		}

		if (st <= en) {
			int[] recNums = new int[en - st + 1];
			for (int i = st; i <= en; i++) {
				recNums[i - st] = i;
			}
			file.deleteLines(recNums);
		}

		return null;
	}


	private void updateRemovedPositions(int where, int nitems, AbstractLine line, int newOffset, int lineNo) {
		int key = findPosByKey(where + nitems, true);
		DataStorePosition p;

		for (int i = key; i >= 0; i--) {
			p = getStorePositionByIndex(i);
			if (p != null) {
				if (p.getOffset() < where) {
					break;
				}
				p.line = line;
				p.lineNumber = lineNo;
				p.positionInLine = newOffset;
				p.setLookupRequired();
			}
		}
		file.fireTableRowsUpdated(lineNo, lineNo);
	}

	private void updatePositions(int where, int nitems, int shift) {
		int key = findPosByKey(where + nitems, true);
		DataStorePosition p;
		DataStorePosition pos = datastore.getPosition(where + nitems);

		for (int i = key; i < positions.size(); i++) {
			p = getValidPosition(i);
			if (p == null) {

			} else if (p.lineNumber > pos.lineNumber) {
				break;
			} else if (p.positionInLine >= pos.positionInLine) {
				p.positionInLine += shift;
				p.setLookupRequired();
			}
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.text.AbstractDocument.Content#getString(int, int)
	 */
	@Override
	public String getString(int where, int len) throws BadLocationException {
        Segment s = new Segment();
        getChars(where, len, s);
        return new String(s.array, s.offset, s.count);
	}

	/* (non-Javadoc)
	 * @see javax.swing.text.AbstractDocument.Content#getChars(int, int, javax.swing.text.Segment)
	 */
	@Override
	public void getChars(int where, int len, Segment txt)
			throws BadLocationException {

		DataStorePosition
		start = datastore.getPosition(where),
		fin   = datastore.getPosition(where + len);

		AbstractLine line1 = datastore.get(start.lineNumber);
		String s1 = line1.getFullLine() + "\n";

		if (fin == null) return;

//		System.out.println("## " + start.lineNumber + " "+ s1.length() + " "
//				+ " ~~ " + start.offset
//		        + Math.min(fin.offset, s1.length())
//				+ " " + len) ;

//		if (start.lineNumber == 1) {
//			System.out.println();
//		}

		if (start.lineNumber == fin.lineNumber) {
			String s = s1.substring(start.positionInLine, Math.min(fin.positionInLine, s1.length()));

			txt.array = s.toCharArray();
			txt.count = txt.array.length;
			txt.offset = 0;
			return;
		}

		AbstractLine line2 = datastore.get(fin.lineNumber);
		String s2 = line2.getFullLine();
		if (start.positionInLine >= s1.length()) {
			s1 = "";
		} else if (start.positionInLine > 0) {
			s1 = s1.substring(start.positionInLine);
		}

		if (fin.positionInLine == 0) {
			s2 = "";
		} else if (fin.positionInLine < s2.length() - 1) {
			s2 = s2.substring(0, fin.positionInLine);
		}

		StringBuffer b = new StringBuffer(s1);

		for (int i = start.lineNumber + 1; i < fin.lineNumber - 1; i++) {
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

		switch (event.getType()) {
		case (TableModelEvent.INSERT):
			updateLinePos(event.getFirstRow(), event.getLastRow() - event.getFirstRow() + 1);
//			if (event.getFirstRow() <= currRow) {
//				changeRow(event.getLastRow() 	-  event.getFirstRow() + 1);
//			}
		break;
		case (TableModelEvent.DELETE):
			System.out.println("Delete: " + event.getFirstRow() + " " + event.getLastRow());
			int en = updateLinePos(event.getLastRow() + 1, event.getFirstRow() - event.getLastRow() - 1);
//			if (currRow > event.getLastRow()) {
//				currRow -= event.getLastRow() - event.getFirstRow() + 1;
//				rowChanged();
//			} else if (currRow > event.getFirstRow()) {
//				currRow -= Math.min(fileView.getRowCount(), event.getFirstRow());
//				rowChanged();
//			}

			DataStorePosition p;
			for (int i = en; i >= 0; i--) {
				p = getStorePositionByIndex(i);
				if (p != null) {
					System.out.println("\t" + i + " " + p.lineNumber + " " + p.getLineStart() + " " + p.positionInLine);
					if (p.lineNumber >= event.getFirstRow()) {
						positions.remove(i);
					} else {
						break;
					}
				} else {
					System.out.println("\tDelete " + i);
				}
			}
		break;
		default:
			updateLinePos(event.getFirstRow(), 0);
		}
	}

	private int updateLinePos(int firstLine, int amount) {
		DataStorePosition p;
		System.out.println();
		System.out.println("Adjust Lines: " + firstLine + "\t " + amount);
		System.out.println();
		for (int i=positions.size() - 1; i>= 0; i--) {
			p = getStorePositionByIndex(i);
			if (p != null) {
				if (p.lineNumber < firstLine) {
					return i;
				}
				System.out.println("\t" + p.lineNumber + "\t" + (Math.max(p.lineNumber + amount, firstLine)));
				p.lineNumber = Math.max(p.lineNumber + amount, firstLine);
				p.setLookupRequired();
			}
		}

		return 0;
	}

	private DataStorePosition getStorePositionByIndex(int idx) {
		DataStorePosition p = positions.get(idx).get();
		if (p == null) {
			positions.remove(idx);
		}
		return p;

	}
}
