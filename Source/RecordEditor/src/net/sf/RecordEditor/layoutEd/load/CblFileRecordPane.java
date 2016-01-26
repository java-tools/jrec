package net.sf.RecordEditor.layoutEd.load;

import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.RecordDetail;

@SuppressWarnings("serial")
public class CblFileRecordPane extends AbstractTableModel {
	
	private final JTable tbl = new JTable();
	public final JScrollPane pane = new JScrollPane(tbl); 

	//private final ExternalRecord rec;
	private final RecordDetail rec;
	//private final LayoutDetail layout;
	private final ArrayList<AbstractLine> lines;
	
	protected CblFileRecordPane(RecordDetail rec, ArrayList<AbstractLine> lines) {
		super();
		this.rec = rec;
		this.lines = lines;
		
		tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbl.setModel(this);
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return lines.size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return rec.getFieldCount();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return lines.get(rowIndex).getFieldValue(rec.getField(columnIndex)).asString();
	}

	
	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int column) {
		return rec.getField(column).getName();
	} 
	
	
}
