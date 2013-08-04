package net.sf.RecordEditor.re.display;

import net.sf.RecordEditor.utils.swing.saveRestore.IUpdateDetails;


/**
 * Describes a class that will perform some action
 *
 * @author Bruce
 *
 * @param <What> class that will be used to save/restore the action
 * to/from a file
 */
public interface IUpdateExecute<What> extends IUpdateDetails<What> {

	public abstract AbstractFileDisplay doAction();
}
