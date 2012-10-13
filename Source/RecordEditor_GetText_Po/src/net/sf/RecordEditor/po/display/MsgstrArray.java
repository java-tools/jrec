/**
 *
 */
package net.sf.RecordEditor.po.display;


import java.util.ArrayList;
import java.util.List;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import net.sf.JRecord.Common.IEmptyTest;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.po.def.PoLine;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.TextAreaTableCellEditor;
import net.sf.RecordEditor.utils.swing.TextAreaTableCellRendor;
import net.sf.RecordEditor.utils.swing.array.ArrayInterface;

/**
 * @author mum
 *
 */
public class MsgstrArray implements ArrayInterface, IEmptyTest {

	private static final int DEFAULT_ROW_HEIGHT = SwingUtils.TABLE_ROW_HEIGHT * 3;

	private final TextAreaTableCellRendor rendor = new TextAreaTableCellRendor();
	private final PoLine line;
	private final int fieldIdx;


	private List<String> msgLines = null;

	public MsgstrArray(PoLine poLine, int idx) {
		line = poLine;
		fieldIdx = idx;

		retrieveArray();
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.array.ArrayInterface#add(int, java.lang.Object)
	 */
	@Override
	public void add(int index, Object value) {
		msgLines.add(index, toString(value));
		flush();
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.array.ArrayInterface#add(java.lang.Object)
	 */
	@Override
	public boolean add(Object value) {
		boolean ret = msgLines.add(toString(value));
		flush();
		return ret;
	}

	private String toString(Object o) {
		String ret = "";
		if (o != null) {
			ret = o.toString();
		}
		return ret;
	}
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.array.ArrayInterface#get(int, int)
	 */
	@Override
	public Object get(int index, int column) {
		return msgLines.get(index);
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.array.ArrayInterface#remove(int)
	 */
	@Override
	public Object remove(int index) {
		Object o = msgLines.remove(index);
		flush();
		return o;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.array.ArrayInterface#set(int, int, java.lang.Object)
	 */
	@Override
	public Object set(int index, int column, Object value) {
		Object o = msgLines.set(index, toString(value));
		flush();
		return o;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.array.ArrayInterface#size()
	 */
	@Override
	public int size() {
		return msgLines.size();
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.array.ArrayInterface#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return 1;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.array.ArrayInterface#getLine()
	 */
	@Override
	public AbstractLine<?> getLine() {
		return line;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.array.ArrayInterface#retrieveArray()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void retrieveArray() {
		Object o = line.getMsgstrPlural();

		if (o instanceof List) {
			msgLines = (List<String>) o;
		} else if (msgLines == null) {
			msgLines = new ArrayList<String>();
		}
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.array.ArrayInterface#flush()
	 */
	@Override
	public void flush() {

		try {
			Object o = msgLines;
			if (msgLines.size() == 0) {
				o = null;
			}
			line.setField(0, fieldIdx, o);
		} catch (RecordException e) {
			Common.logMsg("Error updating plurarl msgstr", e);
			e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.array.ArrayInterface#getReturn()
	 */
	@Override
	public Object getReturn() {
		return msgLines;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.array.ArrayInterface#getTableCellRenderer()
	 */
	@Override
	public TableCellRenderer getTableCellRenderer() {
		return rendor;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.array.ArrayInterface#getTableCellEditor()
	 */
	@Override
	public TableCellEditor getTableCellEditor() {
		return new TextAreaTableCellEditor();
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.array.ArrayInterface#getFieldHeight()
	 */
	@Override
	public int getFieldHeight() {
		return DEFAULT_ROW_HEIGHT;
	}

	/* (non-Javadoc)
	 * @see  net.sf.RecordEditor.edit.display.array.ArrayInterface#getText()
	 */
	@Override
	public String toString() {
		String s = "";
		if (msgLines != null && msgLines.size() > 0) {
			s = msgLines.get(0);
		}
		return s;
	}
	/* (non-Javadoc)
	 * @see net.sf.JRecord.Common.IEmptyTest#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return msgLines == null || msgLines.size() == 0;
	}


}
