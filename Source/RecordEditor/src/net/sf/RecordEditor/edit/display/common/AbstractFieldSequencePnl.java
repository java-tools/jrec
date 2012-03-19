package net.sf.RecordEditor.edit.display.common;

import net.sf.RecordEditor.jibx.compare.FieldSequence;

public interface AbstractFieldSequencePnl extends AbstractFileDisplay {

	public abstract FieldSequence getFieldSequence();

	public abstract void setFieldSequence(FieldSequence seq);

}