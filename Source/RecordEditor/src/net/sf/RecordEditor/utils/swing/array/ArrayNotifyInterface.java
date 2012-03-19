package net.sf.RecordEditor.utils.swing.array;

import net.sf.JRecord.Details.AbstractLine;

public interface ArrayNotifyInterface {

	public abstract void notifyOfChange(@SuppressWarnings("rawtypes") AbstractLine line);

	public abstract void setChanged(boolean fileChanged);

}