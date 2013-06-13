package net.sf.RecordEditor.re.display;

import net.sf.RecordEditor.utils.swing.saveRestore.IUpdateDetails;

public interface IUpdateExecute<What> extends IUpdateDetails<What> {

	public abstract AbstractFileDisplay doAction();
}
