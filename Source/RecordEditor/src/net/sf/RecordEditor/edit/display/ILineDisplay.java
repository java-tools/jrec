package net.sf.RecordEditor.edit.display;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.re.script.AbstractFileDisplay;

public interface ILineDisplay extends AbstractFileDisplay {

	public abstract void setLine(@SuppressWarnings("rawtypes") AbstractLine l);

}