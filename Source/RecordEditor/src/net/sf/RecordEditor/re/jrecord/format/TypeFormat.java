package net.sf.RecordEditor.re.jrecord.format;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.External.base.ExternalConversion;
import net.sf.RecordEditor.re.util.BuildTypeComboList;
import net.sf.RecordEditor.utils.swing.AbsRowList;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboItem;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboRendor;

/**
 * RecordEditor~Format to allow the user to select a RecordEditor Type.
 * @author Bruce Martin
 *
 */
public class TypeFormat implements CellFormat {

	private final TreeComboItem[] typeComboTree;
	private final TreeComboRendor typeRendor;
	
	public TypeFormat() {
		AbsRowList rl = new AbsRowList(0, 1, false, false).loadData(ExternalConversion.getTypes(0));
		typeComboTree = BuildTypeComboList.getList(rl);
		typeRendor = new TreeComboRendor(typeComboTree);
	}

	@Override
	public int getFieldHeight() {
		return Constants.NULL_INTEGER;
	}

	@Override
	public int getFieldWidth() {
		return Constants.NULL_INTEGER;
	}

	@Override
	public TableCellEditor getTableCellEditor(IFieldDetail fld) {
		return new TreeComboRendor(typeComboTree);
	}

	@Override
	public TableCellRenderer getTableCellRenderer(IFieldDetail fld) {
		return typeRendor;
	}

}
