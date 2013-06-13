package net.sf.RecordEditor.utils.swing.saveRestore;

import net.sf.RecordEditor.jibx.compare.EditorTask;

public interface IUpdateDetails<What> {

	public abstract void update(What serialisedData);

	public abstract void setFromSavedDetails(EditorTask details);
}
