package net.sf.RecordEditor.re.file;

import java.util.List;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.utils.ColumnMappingInterface;

public interface IFileAccess extends ColumnMappingInterface {

	public abstract List<AbstractLine> getLines();

	AbstractLine getTempLine(int lineNum);

	//public abstract String getFieldValue(FilePosition pos, int col);

}
